// Jade recovery: original class: jade.deps.eLz.EjTaZDS1W$3
package jade.client.command;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;

public final class StatusCommand$3 {
   private final long UkmOr;
   private final boolean gameEnded;

   StatusCommand$3(long var1, boolean var3) {
      this.UkmOr = var1;
      this.gameEnded = var3;
   }

   private static StatusCommand$3 RZbK(JsonObject var0) {
      if (var0 != null && var0.has("games") && var0.get("games").isJsonArray()) {
         JsonArray var1 = var0.getAsJsonArray("games");
         JsonObject var2 = null;
         long var3 = Long.MIN_VALUE;

         for (JsonElement var6 : var1) {
            if (var6.isJsonObject()) {
               JsonObject var7 = var6.getAsJsonObject();
               long var8 = StatusCommand.getJsonLong(var7, "date");
               if (var8 > var3) {
                  var3 = var8;
                  var2 = var7;
               }
            }
         }

         return var2 == null ? null : new StatusCommand$3(var3, var2.has("ended"));
      } else {
         return null;
      }
   }

   public static StatusCommand$3 GjLg(JsonObject var0) {
      return RZbK(var0);
   }

   public static long getLatestGameTime(StatusCommand$3 var0) {
      return var0.UkmOr;
   }

   public static boolean isGameEnded(StatusCommand$3 var0) {
      return var0.gameEnded;
   }
}
