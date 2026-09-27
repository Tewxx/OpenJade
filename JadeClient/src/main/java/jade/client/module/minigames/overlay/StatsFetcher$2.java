// Jade recovery: original class: jade.deps.eLz.BMK6I1Py$2
package jade.client.module.minigames.overlay;

import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StatsFetcher$2 {
   private final double ZQc;
   private final boolean KOi;
   private final boolean validScore;
   private final List<StatsFetcher$3> tagList;

   StatsFetcher$2(double var1, boolean var3, boolean var4, List<StatsFetcher$3> var5) {
      this.ZQc = var1;
      this.KOi = var3;
      this.validScore = var4;
      this.tagList = var5;
   }

   private static StatsFetcher$2 parseFromJson(String var0) {
      byte var11 = 0;

      while (true) {
         switch (var11) {
            case 0:

               var11 = 1;
               break;
            case 1:
               var11 = 2;
               break;
            default:
               JsonObject var1 = new JsonParser()
                  .parse(var0 != null && !var0.isEmpty() ? var0 : "{}")
                  .getAsJsonObject();
               boolean var2 = false;
               boolean var3 = false;
               double var4 = Double.NaN;
               if (var1.has("score")
                  && var1.get("score").isJsonObject()) {
                  JsonObject var6 = var1.getAsJsonObject("score");
                  var4 = StatsFetcher.getJsonDouble(var6, "value");
                  var2 = !Double.isNaN(var4) && !Double.isInfinite(var4);
                  var3 = "set"
                     .equalsIgnoreCase(StatsFetcher.getJsonString(var6, "mode"));
               }

               ArrayList var12 = new ArrayList();
               if (var1.has("tags") && var1.get("tags").isJsonArray()
                  )
                {
                  for (JsonElement var9 : var1.getAsJsonArray("tags")) {
                     if (var9.isJsonObject()) {
                        JsonObject var10 = var9.getAsJsonObject();
                        var12.add(
                           new StatsFetcher$3(
                              StatsFetcher.getJsonString(var10, "icon"),
                              StatsFetcher.getJsonString(var10, "text"),
                              StatsFetcher.getJsonString(var10, "tooltip"),
                              StatsFetcher.getJsonColor(var10, "color", -11184811),
                              StatsFetcher.getJsonColor(var10, "textColor", -1)
                           )
                        );
                     }
                  }
               }

               return new StatsFetcher$2(var4, var3, var2, Collections.unmodifiableList(var12));
         }
      }
   }

   public boolean NhWet() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.validScore;
         }
      }
   }

   public boolean rhWdy5() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.KOi;
         }
      }
   }

   public double getScoreValue() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.ZQc;
         }
      }
   }

   public List<StatsFetcher$3> getTags() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.tagList;
         }
      }
   }

   public static StatsFetcher$2 parseStatsJson(String var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return parseFromJson(var0);
         }
      }
   }
}
