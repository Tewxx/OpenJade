// Jade recovery: original class: jade.deps.eLz.ikvyeORb
package jade.client.setting;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class BlockColorListSetting extends BlockListSetting {
   private static final int DEFAULT_COLOR = -43691;
   private final Map<String, Integer> blockColors = new HashMap<>();

   public BlockColorListSetting(String var1) {
      super(var1);
   }

   public int getBlockColor(String var1) {
      Integer var2 = this.blockColors.get(var1);
      if (var2 == null) {
         var2 = -43691;
         this.blockColors.put(var1, var2);
      }

      return var2;
   }

   public void setBlockColor(String var1, int var2) {
      this.blockColors.put(var1, 0xFF000000 | var2 & 16777215);
   }

   @Override
   public void addEntry(String var1) {
      super.addEntry(var1);
      this.getBlockColor(var1);
   }

   @Override
   public void removeEntry(String var1) {
      super.removeEntry(var1);
      this.blockColors.remove(var1);
   }

   @Override
   public JsonArray toJsonArray() {
      JsonArray var1 = new JsonArray();

      for (String var3 : this.getEntries()) {
         JsonObject var4 = new JsonObject();
         var4.addProperty("id", var3);
         var4.addProperty("color", PYKijLg(this.getBlockColor(var3)));
         var1.add(var4);
      }

      return var1;
   }

   @Override
   public void loadConfig(JsonObject var1) {
      String var2 = null;
      if (var1.has(this.getPath())) {
         var2 = this.getPath();
      } else if (var1.has(this.getName())) {
         var2 = this.getName();
      } else {
         for (String var6 : this.buqyQhh()) {
            if (var1.has(var6)) {
               var2 = var6;
               break;
            }
         }
      }

      if (var2 != null && var1.get(var2).isJsonArray()) {
         this.getEntries().clear();
         this.blockColors.clear();

         for (JsonElement var8 : var1.getAsJsonArray(var2)) {
            if (var8.isJsonPrimitive()) {
               this.addEntry(var8.getAsString());
            } else if (var8.isJsonObject()) {
               JsonObject var9 = var8.getAsJsonObject();
               if (var9.has("id")) {
                  String var10 = var9.get("id").getAsString();
                  this.addEntry(var10);
                  if (var9.has("color")) {
                     this.setBlockColor(var10, parseHexColor(var9.get("color").getAsString(), this.getBlockColor(var10)));
                  }
               }
            }
         }
      }
   }

   private static int parseHexColor(String var0, int var1) {
      if (var0 == null) {
         return var1;
      } else {
         String var2 = var0.trim();
         if (var2.startsWith("#")) {
            var2 = var2.substring(1);
         }

         if (var2.length() == 8) {
            var2 = var2.substring(2);
         }

         if (var2.length() != 6) {
            return var1;
         } else {
            try {
               return 0xFF000000 | (int)Long.parseLong(var2, 16);
            } catch (NumberFormatException var4) {
               return var1;
            }
         }
      }
   }

   private static String PYKijLg(int var0) {
      return "#" + String.format("%06X", var0 & 16777215);
   }
}
