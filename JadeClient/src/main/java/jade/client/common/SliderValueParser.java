// Jade recovery: original class: jade.deps.eLz.o2AngEM2D
package jade.client.common;

import jade.client.setting.SliderSetting;

public final class SliderValueParser {
   private SliderValueParser() {
   }

   public static Double parseSliderValue(String var0, boolean var1, boolean var2, String[] var3, double var4, double var6) {
      String var8 = var0 == null ? "" : var0.trim();
      if (var8.isEmpty()) {
         return null;
      } else if (!var1 || !"disabled".equalsIgnoreCase(var8) && !"-1".equals(var8)) {
         if (var2) {
            for (int var9 = 0; var9 < var3.length; var9++) {
               if (var3[var9].equalsIgnoreCase(var8)) {
                  return (double)var9;
               }
            }
         }

         try {
            return SliderSetting.clamp(Double.parseDouble(var8), var4, var6);
         } catch (NumberFormatException var10) {
            return null;
         }
      } else {
         return -1.0;
      }
   }
}
