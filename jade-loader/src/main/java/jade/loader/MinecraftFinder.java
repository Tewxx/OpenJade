package jade.loader;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.io.*;

final class MinecraftFinder {
    static List<String> find(Consumer<String> log) throws Exception {
        if (!System.getProperty("os.name").startsWith("Windows"))
            throw new IllegalStateException("Automatic window detection requires Windows. Use --pid on other systems.");
        String script;
        try (InputStream input = MinecraftFinder.class.getResourceAsStream("/find-minecraft.ps1")) {
            if (input == null) throw new IOException("Missing bundled game detector");
            script = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
        String encoded = Base64.getEncoder().encodeToString(script.getBytes(StandardCharsets.UTF_16LE));
        String powershell = System.getenv("SystemRoot") + "\\System32\\WindowsPowerShell\\v1.0\\powershell.exe";
        Process process = new ProcessBuilder(powershell, "-NoProfile", "-NonInteractive",
                "-WindowStyle", "Hidden", "-EncodedCommand", encoded).redirectErrorStream(true).start();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Thread reader = new Thread(() -> {
            try (InputStream input = process.getInputStream()) { input.transferTo(output); }
            catch (IOException ignored) { }
        }, "game-detection-output");
        reader.setDaemon(true);
        reader.start();
        try {
            if (!process.waitFor(20, TimeUnit.SECONDS))
                throw new IOException("Game detection timed out. Wait for Minecraft's title screen and try again.");
            reader.join(2000);
            if (reader.isAlive()) throw new IOException("Game detector output did not finish");
            if (process.exitValue() != 0)
                throw new IOException("Windows game detection failed. Check that Windows PowerShell is available and allowed to run.");
            Set<String> pids = new LinkedHashSet<>();
            for (String line : output.toString(StandardCharsets.UTF_8).split("\\R")) {
                if (line.matches("PID [1-9][0-9]*")) pids.add(line.substring(4));
                else if (line.startsWith("INFO ")) log.accept(line.substring(5));
            }
            return new ArrayList<>(pids);
        } finally { if (process.isAlive()) process.destroyForcibly(); }
    }
}
