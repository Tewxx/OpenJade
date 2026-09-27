// Jade recovery: original class: jade.deps.eLz.Sho5zPD
package jade.client.common;

import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;

public final class DuelsStatsParser {
   private DuelsStatsParser() {
   }

   public static int[] parseDuelsStats(JsonObject var0, PlayerApi$1 var1) {
      int[] var2 = new int[3];

      JsonObject var3;
      try {
         var3 = var0.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Duels");
      } catch (NullPointerException var7) {
         return var2;
      }

      String[] var4 = DuelsStatKeys.getStatKeys(var1);

      for (int var5 = 0; var5 < var2.length; var5++) {
         JsonElement var6 = var3.get(var4[var5]);
         var2[var5] = var6 == null ? 0 : var6.getAsInt();
      }

      return var2;
   }
}
