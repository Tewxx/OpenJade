// Jade recovery: original class: jade.deps.eLz.AVKjrdkR
package jade.client.module.render.indicators;

public final class IndicatorUtils {
   private IndicatorUtils() {
   }

   public static int computeFadeAlpha(double var0, double var2) {
      if (!(var0 < 0.0) && !(var0 >= var2)) {
         int var4 = (int)Math.round(255.0 * var0 / var2);
         return var4 < 0 ? 0 : Math.min(var4, 255);
      } else {
         return 255;
      }
   }

   public static String formatSeconds(double var0) {
      int var2 = Math.max(1, (int)Math.ceil(var0 * 10.0));
      if (var2 >= 100) {
         return (int)Math.ceil(var0) + "s";
      } else {
         int var3 = var2 / 10;
         int var4 = var2 % 10;
         return var4 == 0 ? var3 + "s" : var3 + "." + var4 + "s";
      }
   }
}
