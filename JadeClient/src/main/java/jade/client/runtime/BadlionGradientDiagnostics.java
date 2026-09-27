// Jade recovery: recovered class name: BadlionGradientDiagnostics; original class: jade.deps.eLz.EZ4X0rE
package jade.client.runtime;

import jade.inject.InjectionAgent;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BadlionGradientDiagnostics {
   private static final Object LOCK = new Object();
   private static final long MAX_BYTES = 524288L;
   private static final long REPEAT_INTERVAL_MS = 1500L;
   private static final Map<String, Long> LAST_EVENTS = new LinkedHashMap<>();
   private static final File FILE = new File(
      new File(
         System.getenv("APPDATA"),
         ".jade"
      ),
      "badlion-gradient-debug.log"
   );

   private BadlionGradientDiagnostics() {
   }

   public static void reset() {
      synchronized (LOCK) {
         LAST_EVENTS.clear();
         File parent = FILE.getParentFile();
         if (parent != null) {
            parent.mkdirs();
         }

         if (FILE.isFile()) {
            FILE.delete();
         }

         write(
            "session",
            "Badlion gradient trace started"
         );
      }
   }

   public static void record(String stage, String detail) {
      if (InjectionAgent.isBadlionRuntime()) {
         synchronized (LOCK) {
            long now = System.currentTimeMillis();
            String key = stage + "|" + detail;
            Long previous = LAST_EVENTS.get(key);
            if (previous == null || now - previous >= 1500L) {
               LAST_EVENTS.put(key, now);
               if (LAST_EVENTS.size() > 128) {
                  LAST_EVENTS.remove(LAST_EVENTS.keySet().iterator().next());
               }

               write(stage, detail);
            }
         }
      }
   }

   public static File file() {
      return FILE;
   }

   private static void write(String stage, String detail) {
      try {
         if (FILE.isFile() && FILE.length() >= 524288L) {
            return;
         }

         FileWriter writer = new FileWriter(FILE, true);

         try {
            writer.write(
               "["
                  + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date())
                  + "] "
                  + stage
                  + " "
                  + detail
                  + System.lineSeparator()
            );
         } finally {
            writer.close();
         }
      } catch (Throwable var7) {
      }
   }
}
