ed tpackage jade.loader;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.security.SecureRandom;
import java.util.*;
import java.util.jar.*;

public final class JadeLoader {
    public static void main(String[] args) {
        if (args.length == 0) { LoaderWindow.open(); return; }
        try { run(args, System.out::println); }
        catch (Exception e) {
            System.err.println("Jade: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    static void run(String[] args, java.util.function.Consumer<String> log) throws Exception {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String key = args[i];
            if (!key.matches("--(help|list|dry-run|native|pid|nonce)"))
                throw new IllegalArgumentException("Unknown option: " + key + " (see --help)");
            String value = "true";
            if (key.matches("--(pid|nonce)")) {
                if (++i == args.length || args[i].startsWith("--"))
                    throw new IllegalArgumentException("Missing value for " + key);
                value = args[i];
            }
            if (options.put(key, value) != null)
                throw new IllegalArgumentException("Duplicate option: " + key);
        }
        if (options.containsKey("--help")) {
            log.accept("JadeLoader [--pid PID] [--nonce HEX] [--dry-run] [--native]\n"
                + "JadeLoader --list\n"
                + "Requires jade.jar in the same folder as jade-loader.jar.\n"
                + "Automatically selects one Minecraft JVM; use --pid if ambiguous.\n"
                + "Requires a JDK; Windows x64 includes a native fallback for attachment-disabled JVMs.");
            return;
        }
        String pid = options.get("--pid");
        if (pid != null && !pid.matches("[1-9][0-9]*"))
            throw new IllegalArgumentException("PID must be a positive integer");
        if (options.containsKey("--list")) {
            for (VirtualMachineDescriptor vm : VirtualMachine.list())
                log.accept(vm.id() + "  " + vm.displayName());
            return;
        }
        String nonce = options.get("--nonce");
        if (nonce == null) {
            byte[] bytes = new byte[16];
            new SecureRandom().nextBytes(bytes);
            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) hex.append(String.format("%02x", b & 255));
            nonce = hex.toString();
        }
        if (!nonce.matches("[0-9a-f]{32}"))
            throw new IllegalArgumentException("Nonce must be 32 lowercase hex digits");
        File jar = resolveJar();
        boolean reportsStatus;
        try (JarFile archive = new JarFile(jar)) {
            if (archive.getManifest() == null)
                throw new IllegalArgumentException("Agent JAR has no manifest");
            Attributes manifest = archive.getManifest().getMainAttributes();
            String agent = manifest.getValue("Agent-Class");
            if (agent == null || archive.getJarEntry(agent.replace('.', '/') + ".class") == null)
                throw new IllegalArgumentException("Agent JAR has no valid Agent-Class entry");
            reportsStatus = "1".equals(manifest.getValue("Jade-Offline-Status"));
        }
        if (pid == null) {
            List<String> candidates = MinecraftFinder.find(log);
            if (candidates.size() != 1)
                throw new IllegalArgumentException(candidates.isEmpty()
                    ? "Minecraft was not found. Start the game and run the loader under the same user account."
                    : "More than one Minecraft game is running. Close the other games and try again.");
            pid = candidates.get(0);
        }
        log.accept("Agent: " + jar + "\nTarget PID: " + pid);
        if (options.containsKey("--dry-run")) {
            log.accept("Validation complete; no attachment attempted.");
            return;
        }
        boolean attachDisabled = ProcessHandle.of(Long.parseLong(pid))
            .flatMap(process -> process.info().arguments())
            .map(arguments -> Arrays.asList(arguments).contains("-XX:+DisableAttachMechanism"))
            .orElse(false);
        if (options.containsKey("--native") || attachDisabled) {
            NativeBridge.attach(pid, jar, nonce, reportsStatus, log);
            return;
        }
        VirtualMachine vm;
        try { vm = VirtualMachine.attach(pid); }
        catch (com.sun.tools.attach.AttachNotSupportedException e) {
            NativeBridge.attach(pid, jar, nonce, reportsStatus, log);
            return;
        }
        try {
            vm.loadAgent(jar.getPath(), nonce);
            if (reportsStatus) {
                waitForClient(vm, nonce, log);
                log.accept("Jade is ready.");
            } else log.accept("JVM accepted the agent. Check the game log for startup status.");
        } finally { vm.detach(); }
    }

    private static File resolveJar() throws Exception {
        File location = new File(JadeLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        File base = location.isDirectory() ? location : location.getParentFile();
        File jar = new File(base, "jade.jar");
        if (!jar.isFile())
            throw new IllegalArgumentException("Place jade.jar in the same folder as jade-loader.jar: " + base);
        return jar.getCanonicalFile();
    }
    private static void waitForClient(VirtualMachine vm, String nonce, java.util.function.Consumer<String> log) throws Exception {
        long deadline = System.nanoTime() + 30_000_000_000L;
        String lastState = "";
        while (System.nanoTime() < deadline) {
            Properties properties = vm.getSystemProperties();
            if (nonce.equals(properties.getProperty("jade.local.nonce"))) {
                String state = properties.getProperty("jade.local.status", "");
                String message = properties.getProperty("jade.local.message", "");
                if (!state.equals(lastState)) log.accept(state + ": " + message);
                lastState = state;
                if ("READY".equals(state)) return;
                if ("FAILED".equals(state)) throw new IllegalStateException("Client startup failed: " + message);
            }
            Thread.sleep(100);
        }
        throw new IllegalStateException("Client did not report READY within 30 seconds (last state: "
            + lastState + "). Check the game's [Jade] log entries.");
    }
}
