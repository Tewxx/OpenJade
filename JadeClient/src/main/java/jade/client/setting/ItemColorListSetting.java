// Jade recovery: original class: jade.deps.eLz.IQnjifou
package jade.client.setting;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class ItemColorListSetting extends ItemListSetting {
   private final Map<String, int[]> itemColors = new HashMap<>();

   public ItemColorListSetting(String var1) {
      super(var1);
   }

   public int getPrimaryColor(String var1) {
      return this.getItemColors(var1)[0];
   }

   public int jaqpR(String var1) {
      return this.getItemColors(var1)[1];
   }

   public void setPrimaryColor(String var1, int var2) {
      this.setColorComponent(var1, 0, var2);
   }

   public void setSecondaryColor(String var1, int var2) {
      this.setColorComponent(var1, 1, var2);
   }

   public int[] getItemColors(String var1) {
      int[] var2 = this.itemColors.get(var1);
      if (var2 == null) {
         var2 = CAixJ(var1);
         this.itemColors.put(var1, var2);
      }

      return var2;
   }

   @Override
   public void addItem(String var1) {
      super.addItem(var1);
      this.getItemColors(var1);
   }

   @Override
   public void removeItem(String var1) {
      super.removeItem(var1);
      this.itemColors.remove(var1);
   }

   @Override
   public JsonArray toJsonArray() {
      JsonArray var1 = new JsonArray();

      for (String var3 : this.getItems()) {
         JsonObject var4 = new JsonObject();
         int[] var5 = this.getItemColors(var3);
         var4.addProperty("id", var3);
         var4.addProperty("primary", formatHexColor(var5[0]));
         var4.addProperty("secondary", formatHexColor(var5[1]));
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
      }

      if (var2 != null && var1.get(var2).isJsonArray()) {
         this.getItems().clear();
         this.itemColors.clear();

         for (JsonElement var4 : var1.getAsJsonArray(var2)) {
            if (var4.isJsonPrimitive()) {
               this.addItem(var4.getAsString());
            } else if (var4.isJsonObject()) {
               JsonObject var5 = var4.getAsJsonObject();
               if (var5.has("id")) {
                  String var6 = var5.get("id").getAsString();
                  this.addItem(var6);
                  int var7 = var5.has("primary") ? parseHexColor(var5.get("primary").getAsString(), this.getPrimaryColor(var6)) : this.getPrimaryColor(var6);
                  int var8 = var5.has("secondary") ? parseHexColor(var5.get("secondary").getAsString(), this.jaqpR(var6)) : this.jaqpR(var6);
                  this.itemColors.put(var6, new int[]{var7, var8});
               }
            }
         }
      }
   }

   private void setColorComponent(String var1, int var2, int var3) {
      int[] var4 = this.getItemColors(var1);
      var4[var2] = 0xFF000000 | var3 & 16777215;
   }

   private static int[] CAixJ(String var0) {
      if ("minecraft:gold_ingot".equals(var0)) {
         return new int[]{-10375, -3928};
      } else if ("minecraft:diamond".equals(var0)) {
         return new int[]{-10291713, -1};
      } else if ("minecraft:emerald".equals(var0)) {
         return new int[]{-13699729, -4653104};
      } else {
         return "minecraft:iron_ingot".equals(var0) ? new int[]{-1, -3158065} : new int[]{-1, -1};
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

   private static String formatHexColor(int var0) {
      return "#" + String.format("%06X", var0 & 16777215);
   }
}
