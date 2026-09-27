// Jade recovery: original class: jade.deps.eLz.aBADHjscp
package jade.client.core;

public final class ColorUtils {
   private static final int Eyd15 = -16777216;
   private static final int sAh = 2013265920;

   private ColorUtils() {
   }

   public static int withFullAlpha(int var0) {
      return 0xFF000000 | var0 & 16777215;
   }

   public static int translucentGray(int var0) {
      return 2013265920 + (var0 << 16) + (var0 << 8) + var0;
   }

   public static int scaleRgb(int var0, double var1) {
      int var3 = (int)((var0 >> 16 & 0xFF) * var1);
      int var4 = (int)((var0 >> 8 & 0xFF) * var1);
      int var5 = (int)((var0 & 0xFF) * var1);
      return -16777216 + (var3 << 16) + (var4 << 8) + var5;
   }

   public static int grayFromFraction(double var0) {
      int var2 = (int)(255.0 * var0);
      return -16777216 + (var2 << 16) + (var2 << 8) + var2;
   }
}
