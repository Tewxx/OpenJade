// Jade recovery: original class: jade.deps.eLz.nh7WuVDuJZ
package jade.client.setting;

import jade.client.common.FontManager;
import jade.deps.gson.JsonObject;

public class FontSetting extends SliderSetting {
   private String fontName;

   public FontSetting(String var1, String var2) {
      super(var1, findFontOptionIndex(var2), FontManager.getHudFontOptions());
      this.fontName = normalizeFontName(var2);
   }

   public FontSetting(GroupSetting var1, String var2, String var3) {
      super(var1, var2, findFontOptionIndex(var3), FontManager.getHudFontOptions());
      this.fontName = normalizeFontName(var3);
   }

   public String getResolvedFontName() {
      return FontManager.resolveHudFontName(this.fontName);
   }

   public String getFontName() {
      return this.fontName;
   }

   @Override
   public String[] getOptions() {
      return FontManager.getHudFontOptions();
   }

   @Override
   public double getInput() {
      return findFontOptionIndex(this.getResolvedFontName());
   }

   @Override
   public double getMax() {
      return Math.max(0, this.getOptions().length - 1);
   }

   @Override
   public double setValueClamped(double var1) {
      String[] var3 = this.getOptions();
      if (var3.length == 0) {
         this.fontName = FontManager.getDefaultHudFontName();
         return 0.0;
      } else {
         int var4 = (int)Math.round(clamp(var1, 0.0, var3.length - 1));
         this.fontName = var3[var4];
         return var4;
      }
   }

   @Override
   public void setValueRaw(double var1) {
      this.setValueClamped(var1);
   }

   @Override
   public void loadConfig(JsonObject var1) {
      String var2 = this.rSax(var1);
      if (var2 != null) {
         try {
            if (var1.get(var2).isJsonPrimitive() && var1.get(var2).toString().startsWith("\"")) {
               this.fontName = normalizeFontName(var1.getAsJsonPrimitive(var2).getAsString());
               return;
            }
         } catch (Exception var4) {
         }

         super.loadConfig(var1);
      }
   }

   private String rSax(JsonObject var1) {
      String var2 = this.getPath();
      String var3 = this.getName();
      if (var1.has(var2)) {
         return var2;
      } else {
         return var1.has(var3) ? var3 : null;
      }
   }

   private static int findFontOptionIndex(String var0) {
      String var1 = FontManager.resolveHudFontName(var0);
      String[] var2 = FontManager.getHudFontOptions();

      for (int var3 = 0; var3 < var2.length; var3++) {
         if (var2[var3].equals(var1)) {
            return var3;
         }
      }

      return 0;
   }

   private static String normalizeFontName(String var0) {
      return var0 != null && !var0.trim().isEmpty() ? var0.trim() : FontManager.getDefaultHudFontName();
   }
}
