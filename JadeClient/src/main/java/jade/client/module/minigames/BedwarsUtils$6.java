// Jade recovery: original class: jade.deps.eLz.t1ZZKD$6
package jade.client.module.minigames;

import java.util.HashMap;
import java.util.Map;

public class BedwarsUtils$6 {
   private final Map<String, Long> SkDsu = new HashMap<>();
   private String lastAlertKey = "";

   BedwarsUtils$6() {
   }

   public static Map getItemTimers(BedwarsUtils$6 var0) {
      return var0.SkDsu;
   }

   public static String getLastAlertKey(BedwarsUtils$6 var0) {
      return var0.lastAlertKey;
   }

   public static String setLastAlertKey(BedwarsUtils$6 var0, String var1) {
      return var0.lastAlertKey = var1;
   }
}
