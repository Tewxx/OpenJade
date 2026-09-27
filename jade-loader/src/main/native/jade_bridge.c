#include <windows.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>

#include <jni.h>

typedef jint(JNICALL *GetCreatedJavaVMsFn)(JavaVM **, jsize, jsize *);
typedef jint(JNICALL *AgentOnAttachFn)(JavaVM *, char *, void *);

static void report(const wchar_t *pipeName, const char *line) {
	if (!pipeName || pipeName[0] != L'\\') {
		return;
	}
	HANDLE pipe = CreateFileW(pipeName, GENERIC_WRITE, 0, NULL, OPEN_EXISTING, 0, NULL);
	if (pipe == INVALID_HANDLE_VALUE) {
		return;
	}
	DWORD written = 0;
	WriteFile(pipe, line, (DWORD)strlen(line), &written, NULL);
	FlushFileBuffers(pipe);
	CloseHandle(pipe);
}

static int instrumentPathFromJvm(wchar_t *out, size_t cap) {
	HMODULE jvm = GetModuleHandleW(L"jvm.dll");
	if (!jvm) {
		return 0;
	}
	wchar_t path[MAX_PATH];
	DWORD n = GetModuleFileNameW(jvm, path, MAX_PATH);
	if (n == 0 || n >= MAX_PATH) {
		return 0;
	}
	for (int drops = 0; drops < 2; drops++) {
		wchar_t *slash = wcsrchr(path, L'\\');
		if (!slash) {
			return 0;
		}
		*slash = 0;
	}
	if (_snwprintf(out, cap, L"%ls\\instrument.dll", path) < 0) {
		return 0;
	}
	return 1;
}

struct bridgeConfig {
	wchar_t pipe[256];
	char jar[1024];
	char nonce[128];
};

static DWORD waitForReady(JNIEnv *env, const char *nonce) {
    jclass system = (*env)->FindClass(env, "java/lang/System");
    if (!system) { (*env)->ExceptionClear(env); return 10; }
    jmethodID getter = (*env)->GetStaticMethodID(env, system, "getProperty", "(Ljava/lang/String;)Ljava/lang/String;");
    if (!getter) { (*env)->ExceptionClear(env); (*env)->DeleteLocalRef(env, system); return 10; }
    jstring nonceKey = (*env)->NewStringUTF(env, "jade.local.nonce");
    jstring stateKey = (*env)->NewStringUTF(env, "jade.local.status");
    DWORD result = 10;
    ULONGLONG deadline = GetTickCount64() + 30000;
    while (GetTickCount64() < deadline) {
        jstring value = (jstring)(*env)->CallStaticObjectMethod(env, system, getter, nonceKey);
        if ((*env)->ExceptionCheck(env)) { (*env)->ExceptionClear(env); break; }
        int matches = 0;
        if (value) {
            const char *text = (*env)->GetStringUTFChars(env, value, NULL);
            if (text) { matches = strcmp(text, nonce) == 0; (*env)->ReleaseStringUTFChars(env, value, text); }
            (*env)->DeleteLocalRef(env, value);
        }
        if (matches) {
            value = (jstring)(*env)->CallStaticObjectMethod(env, system, getter, stateKey);
            if ((*env)->ExceptionCheck(env)) { (*env)->ExceptionClear(env); break; }
            if (value) {
                const char *text = (*env)->GetStringUTFChars(env, value, NULL);
                if (text) {
                    if (!strcmp(text, "READY")) result = 0;
                    else if (!strcmp(text, "FAILED")) result = 9;
                    (*env)->ReleaseStringUTFChars(env, value, text);
                }
                (*env)->DeleteLocalRef(env, value);
            }
            if (result != 10) break;
        }
        Sleep(100);
    }
    (*env)->DeleteLocalRef(env, nonceKey);
    (*env)->DeleteLocalRef(env, stateKey);
    (*env)->DeleteLocalRef(env, system);
    return result;
}

static int parseConfig(const char *raw, struct bridgeConfig *cfg) {
	memset(cfg, 0, sizeof(*cfg));
	const char *p = raw;
	char pipeUtf8[256];
	for (int field = 0; field < 3; field++) {
		const char *nl = strchr(p, '\n');
		size_t len = nl ? (size_t)(nl - p) : strlen(p);
		if (field == 0) {
			if (len >= sizeof(pipeUtf8)) len = sizeof(pipeUtf8) - 1;
			memcpy(pipeUtf8, p, len);
			pipeUtf8[len] = 0;
		} else if (field == 1) {
			if (len >= sizeof(cfg->jar)) len = sizeof(cfg->jar) - 1;
			memcpy(cfg->jar, p, len);
			cfg->jar[len] = 0;
		} else {
			if (len >= sizeof(cfg->nonce)) len = sizeof(cfg->nonce) - 1;
			memcpy(cfg->nonce, p, len);
			cfg->nonce[len] = 0;
		}
		if (!nl) break;
		p = nl + 1;
	}
	MultiByteToWideChar(CP_UTF8, 0, pipeUtf8, -1, cfg->pipe, 256);
	return cfg->jar[0] != 0;
}

__declspec(dllexport) DWORD WINAPI JadeBridgeInit(LPVOID param) {
	struct bridgeConfig cfg;
	if (!param || !parseConfig((const char *)param, &cfg)) {
		return 1;
	}

	HMODULE jvmMod = GetModuleHandleW(L"jvm.dll");
	if (!jvmMod) {
		report(cfg.pipe, "ERR jvm.dll is not loaded in the target");
		return 2;
	}
	GetCreatedJavaVMsFn getVMs =
		(GetCreatedJavaVMsFn)(void *)GetProcAddress(jvmMod, "JNI_GetCreatedJavaVMs");
	if (!getVMs) {
		report(cfg.pipe, "ERR jvm.dll does not export JNI_GetCreatedJavaVMs");
		return 3;
	}
	JavaVM *vm = NULL;
	jsize count = 0;
	if (getVMs(&vm, 1, &count) != JNI_OK || count == 0 || !vm) {
		report(cfg.pipe, "ERR no created JavaVM in the target");
		return 4;
	}

	void *env = NULL;
	int attachedByUs = 0;
	if ((*vm)->GetEnv(vm, &env, JNI_VERSION_1_2) != JNI_OK) {
		JavaVMAttachArgs args = {JNI_VERSION_1_8, "Jade-Bridge", NULL};
		if ((*vm)->AttachCurrentThreadAsDaemon(vm, &env, &args) != JNI_OK) {
			report(cfg.pipe, "ERR could not attach the bridge thread to the JVM");
			return 5;
		}
		attachedByUs = 1;
	}

	wchar_t instrumentPath[MAX_PATH];
	HMODULE instrument = NULL;
	if (instrumentPathFromJvm(instrumentPath, MAX_PATH)) {
		instrument = LoadLibraryW(instrumentPath);
	}
	if (!instrument) {
		instrument = LoadLibraryW(L"instrument.dll");
	}
	if (!instrument) {
		report(cfg.pipe, "ERR could not load the JDK instrument.dll (JPLIS)");
		if (attachedByUs) (*vm)->DetachCurrentThread(vm);
		return 6;
	}
	AgentOnAttachFn onAttach =
		(AgentOnAttachFn)(void *)GetProcAddress(instrument, "Agent_OnAttach");
	if (!onAttach) {
		report(cfg.pipe, "ERR instrument.dll does not export Agent_OnAttach");
		if (attachedByUs) (*vm)->DetachCurrentThread(vm);
		return 7;
	}

	char options[1200];
	if (cfg.nonce[0]) {
		_snprintf(options, sizeof(options), "%s=%s", cfg.jar, cfg.nonce);
	} else {
		_snprintf(options, sizeof(options), "%s", cfg.jar);
	}
	options[sizeof(options) - 1] = 0;

	int rc = onAttach(vm, options, NULL);
	DWORD ready = 0;
	if (rc == JNI_OK && wcscmp(cfg.pipe, L"STATUS") == 0)
		ready = waitForReady((JNIEnv *)env, cfg.nonce);

	if (attachedByUs) {
		(*vm)->DetachCurrentThread(vm);
	}

	if (rc != JNI_OK) {
		char msg[128];
		_snprintf(msg, sizeof(msg), "ERR Agent_OnAttach returned %d", rc);
		msg[sizeof(msg) - 1] = 0;
		report(cfg.pipe, msg);
		return 8;
	}
	report(cfg.pipe, "OK");
	return ready;
}

BOOL WINAPI DllMain(HINSTANCE inst, DWORD reason, LPVOID reserved) {
	(void)inst;
	(void)reserved;
	if (reason == DLL_PROCESS_ATTACH) {
		DisableThreadLibraryCalls(inst);
	}
	return TRUE;
}

#include <tlhelp32.h>

static uintptr_t moduleBase(DWORD pid, const wchar_t *name) {
    HANDLE snapshot = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32, pid);
    if (snapshot == INVALID_HANDLE_VALUE) return 0;
    MODULEENTRY32W entry;
    memset(&entry, 0, sizeof(entry)); entry.dwSize = sizeof(entry);
    uintptr_t base = 0;
    if (Module32FirstW(snapshot, &entry)) do {
        if (_wcsicmp(entry.szModule, name) == 0) { base = (uintptr_t)entry.modBaseAddr; break; }
    } while (Module32NextW(snapshot, &entry));
    CloseHandle(snapshot);
    return base;
}

static void fail(JNIEnv *env, const char *message) {
    jclass error = (*env)->FindClass(env, "java/io/IOException");
    if (error) (*env)->ThrowNew(env, error, message);
}

static int remoteCall(HANDLE process, uintptr_t function, const void *data, SIZE_T length,
                      DWORD *result, char *error, size_t capacity) {
    void *remote = VirtualAllocEx(process, NULL, length, MEM_COMMIT | MEM_RESERVE, PAGE_READWRITE);
    HANDLE thread = NULL;
    SIZE_T written = 0;
    int ok = 0, retain = 0;
    if (!remote || !WriteProcessMemory(process, remote, data, length, &written) || written != length) {
        snprintf(error, capacity, "Could not write bridge data (Windows error %lu)", GetLastError());
        goto done;
    }
    thread = CreateRemoteThread(process, NULL, 0, (LPTHREAD_START_ROUTINE)function, remote, 0, NULL);
    if (!thread) {
        snprintf(error, capacity, "Could not start bridge thread (Windows error %lu)", GetLastError());
        goto done;
    }
    DWORD wait = WaitForSingleObject(thread, 60000);
    if (wait != WAIT_OBJECT_0) {
        retain = 1;
        snprintf(error, capacity, "Bridge did not finish within 60 seconds; check the game log before retrying");
        goto done;
    }
    if (!GetExitCodeThread(thread, result)) {
        snprintf(error, capacity, "Could not read bridge result (Windows error %lu)", GetLastError());
        goto done;
    }
    ok = 1;
done:
    if (thread) CloseHandle(thread);
    if (remote && !retain) VirtualFreeEx(process, remote, 0, MEM_RELEASE);
    return ok;
}

JNIEXPORT void JNICALL Java_jade_loader_NativeBridge_inject(JNIEnv *env, jclass type,
        jlong pid, jstring library, jstring jar, jstring nonce, jboolean status) {
    (void)type;
    char error[512] = "";
    HANDLE process = NULL;
    const jchar *libraryChars = NULL;
    const char *jarChars = NULL, *nonceChars = NULL;
    if (pid <= 0 || pid > 0xffffffffLL) { fail(env, "Invalid target PID"); return; }
    process = OpenProcess(PROCESS_CREATE_THREAD | PROCESS_QUERY_INFORMATION | PROCESS_VM_OPERATION
                         | PROCESS_VM_WRITE | PROCESS_VM_READ, FALSE, (DWORD)pid);
    if (!process) {
        snprintf(error, sizeof(error), "Cannot open game process (Windows error %lu). Run as the same user as the game.", GetLastError());
        goto done;
    }
    BOOL wow64 = FALSE;
    if (!IsWow64Process(process, &wow64) || wow64) {
        snprintf(error, sizeof(error), "The native bridge requires a 64-bit game JVM"); goto done;
    }
    if (!moduleBase((DWORD)pid, L"jvm.dll")) {
        snprintf(error, sizeof(error), "Target is not a running JVM"); goto done;
    }
    libraryChars = (*env)->GetStringChars(env, library, NULL);
    jarChars = (*env)->GetStringUTFChars(env, jar, NULL);
    nonceChars = (*env)->GetStringUTFChars(env, nonce, NULL);
    if (!libraryChars || !jarChars || !nonceChars) goto done;
    jsize libraryLength = (*env)->GetStringLength(env, library);
    wchar_t libraryPath[32768];
    if (libraryLength >= 32768 || strlen(jarChars) >= 1024 || strlen(nonceChars) != 32) {
        snprintf(error, sizeof(error), "Bridge path or nonce exceeds supported limits"); goto done;
    }
    memcpy(libraryPath, libraryChars, libraryLength * sizeof(wchar_t)); libraryPath[libraryLength] = 0;
    FARPROC loadLibrary = GetProcAddress(GetModuleHandleW(L"kernel32.dll"), "LoadLibraryW");
    HMODULE owner = NULL;
    wchar_t ownerPath[MAX_PATH];
    if (!loadLibrary || !GetModuleHandleExW(GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS | GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT,
            (LPCWSTR)loadLibrary, &owner) || !GetModuleFileNameW(owner, ownerPath, MAX_PATH)) {
        snprintf(error, sizeof(error), "Cannot resolve LoadLibraryW"); goto done;
    }
    wchar_t *ownerName = wcsrchr(ownerPath, L'\\'); ownerName = ownerName ? ownerName + 1 : ownerPath;
    uintptr_t remoteOwner = moduleBase((DWORD)pid, ownerName);
    DWORD result = 0;
    if (!remoteOwner) { snprintf(error, sizeof(error), "Cannot resolve target system module"); goto done; }
    if (!remoteCall(process, remoteOwner + ((uintptr_t)loadLibrary - (uintptr_t)owner),
            libraryPath, (libraryLength + 1) * sizeof(wchar_t), &result, error, sizeof(error))) goto done;
    wchar_t *libraryName = wcsrchr(libraryPath, L'\\');
    if (!libraryName) libraryName = wcsrchr(libraryPath, L'/');
    libraryName = libraryName ? libraryName + 1 : libraryPath;
    uintptr_t remoteBridge = moduleBase((DWORD)pid, libraryName);
    HMODULE localBridge = NULL;
    if (!remoteBridge || !GetModuleHandleExW(GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS | GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT,
            (LPCWSTR)JadeBridgeInit, &localBridge)) {
        snprintf(error, sizeof(error), "The game could not load the native bridge DLL"); goto done;
    }
    char config[1400];
    snprintf(config, sizeof(config), "%s\n%s\n%s", status ? "STATUS" : "", jarChars, nonceChars);
    if (!remoteCall(process, remoteBridge + ((uintptr_t)JadeBridgeInit - (uintptr_t)localBridge),
            config, strlen(config) + 1, &result, error, sizeof(error))) goto done;
    if (result) snprintf(error, sizeof(error),
        "Native bridge returned %lu%s. Check the game's [Jade] log for details.", result,
        result == 8 ? " (agent initialization failed)" : result == 9 ? " (client reported FAILED)"
        : result == 10 ? " (client did not report READY)" : "");
done:
    if (libraryChars) (*env)->ReleaseStringChars(env, library, libraryChars);
    if (jarChars) (*env)->ReleaseStringUTFChars(env, jar, jarChars);
    if (nonceChars) (*env)->ReleaseStringUTFChars(env, nonce, nonceChars);
    if (process) CloseHandle(process);
    if (error[0] && !(*env)->ExceptionCheck(env)) fail(env, error);
}
