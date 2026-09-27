// Jade recovery: original class: jade.deps.eLz.jBFJjVgAhn
package jade.client.core;

import jade.client.module.client.Gui;
import jade.client.setting.Setting;
import jade.deps.gson.JsonObject;

public final class ThemeConfig {
   private ThemeConfig() {
   }

   public static boolean isDropdownAppearanceSetting(Setting var0) {
      return var0 != null && (var0 == Gui.dropdownColor || var0 == Gui.dropdownAccent);
   }

   public static JsonObject serialize() {
      JsonObject var0 = new JsonObject();
      var0.addProperty("source", Gui.YPPBEUf() ? "Custom" : "Theme");
      var0.addProperty("accent", Gui.dropdownAccent == null ? -10387564 : Gui.dropdownAccent.getArgb());
      return var0;
   }

   public static void LRTFvu(JsonObject var0) {
      boolean var1 = false;
      int var2 = -10387564;
      if (var0 != null && var0.has("dropdownAppearance") && var0.get("dropdownAppearance").isJsonObject()) {
         JsonObject var3 = var0.getAsJsonObject("dropdownAppearance");
         if (var3.has("source") && var3.get("source").isJsonPrimitive()) {
            var1 = "Custom".equalsIgnoreCase(var3.get("source").getAsString());
         }

         if (var3.has("accent") && var3.get("accent").isJsonPrimitive()) {
            try {
               var2 = var3.get("accent").getAsInt();
            } catch (NumberFormatException var5) {
            }
         }
      }

      if (Gui.dropdownColor != null) {
         Gui.dropdownColor.setValueClamped(var1 ? 1.0 : 0.0);
      }

      if (Gui.dropdownAccent != null) {
         Gui.dropdownAccent.setRgb(var2 >> 16 & 0xFF, var2 >> 8 & 0xFF, var2 & 0xFF);
      }
   }
}
