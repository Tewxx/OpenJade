// Jade recovery: original class: jade.deps.eLz.oRXy6LS
package jade.client.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ReloadProfiler {
   private static Thread profilerThread;

   private ReloadProfiler() {
   }

   public static synchronized File qcYw(final Thread var0, File var1) throws IOException {
      if (profilerThread != null && profilerThread.isAlive()) {
         throw new IOException("A reload profile is already running; wait 90 seconds before starting another.");
      } else {
         Files.createDirectories(var1.toPath());
         File var2 = File.createTempFile("reload-", ".log", var1);
         final BufferedWriter var3 = Files.newBufferedWriter(var2.toPath(), StandardCharsets.UTF_8);

         try {
            var3.write("Jade reload profile: game-thread stacks every 200ms for up to 90 seconds.\n");
            var3.flush();
            profilerThread = new Thread(new Runnable() {
               @Override
               public void run() {
                  try (BufferedWriter var1x = var3) {
                     ReloadProfiler.sampleStackTrace(var0, var1x, 450, 200L);
                  } catch (InterruptedException var15) {
                     Thread.currentThread().interrupt();
                  } catch (IOException var16) {
                     Logger.getLogger(ReloadProfiler.class.getName()).log(Level.WARNING, "Could not finish the local reload profile", (Throwable)var16);
                  }
               }
            }, "jade-reload-profiler");
            profilerThread.setDaemon(true);
            profilerThread.start();
            return var2;
         } catch (RuntimeException | IOException var5) {
            var3.close();
            throw var5;
         }
      }
   }

   public static void sampleStackTrace(Thread var0, Writer var1, int var2, long var3) throws IOException, InterruptedException {
      long var5 = System.nanoTime();

      for (int var7 = 0; var7 < var2 && var0.isAlive(); var7++) {
         long var8 = (System.nanoTime() - var5) / 1000000L;
         StackTraceElement[] var10 = var0.getStackTrace();
         var1.write("\nSample " + var7 + " at " + var8 + "ms: " + var0.getName() + " " + var0.getState() + "\n");

         for (int var11 = 0; var11 < Math.min(var10.length, 64); var11++) {
            var1.write("  at " + var10[var11] + "\n");
         }

         var1.flush();
         if (var7 + 1 < var2) {
            Thread.sleep(var3);
         }
      }

      var1.write("\nProfile complete.\n");
      var1.flush();
   }
}
