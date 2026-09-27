package jade.loader;

import java.io.*;
import java.nio.file.*;
import java.util.function.Consumer;

final class NativeBridge {
    private static boolean loaded;

    private static synchronized Path load() throws IOException {
        if (!System.getProperty("os.name").startsWith("Windows")
                || !System.getProperty("os.arch").equals("amd64"))
            throw new IOException("Native attachment requires Windows x64 and a 64-bit JDK.");
        Path directory = Files.createTempDirectory("jade-bridge-");
        Path library = directory.resolve(directory.getFileName() + ".dll");
        try (InputStream input = NativeBridge.class.getResourceAsStream("/native/jade-bridge.dll")) {
            if (input == null) throw new IOException("This loader was built without the native bridge.");
            Files.copy(input, library);
        }
        if (!loaded) { System.load(library.toString()); loaded = true; }
        return library;
    }

    static void attach(String pid, File jar, String nonce, boolean status, Consumer<String> log) throws Exception {
        Path library = load();
        Path snapshot = library.getParent().resolve("jade.jar");
        Files.copy(jar.toPath(), snapshot);
        log.accept("JVM attachment is disabled; loading through the native bridge...");
        inject(Long.parseLong(pid), library.toString(), snapshot.toString(), nonce, status);
        log.accept(status ? "Jade is ready." : "JVM accepted the agent. Check the game log for startup status.");
    }

    private static native void inject(long pid, String library, String jar, String nonce, boolean status) throws IOException;
}
