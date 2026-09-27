// Jade recovery: original class: jade.deps.eLz.NbzM3C
package jade.client.common;

public final class Easing {
   private static final double[] BOUNCE_THRESHOLDS = new double[]{0.36363636363636365, 0.7272727272727273, 0.9090909090909091};
   private static final double[] BOUNCE_OFFSETS = new double[]{0.0, 0.5454545454545454, 0.8181818181818182, 0.9545454545454546};
   private static final double[] YbpdY = new double[]{0.0, 0.75, 0.9375, 0.984375};

   private Easing() {
   }

   public static float easeByType(int var0, float var1) {
      switch (var0) {
         case 1:
            if (var1 < 0.5F) {
               return 4.0F * var1 * var1 * var1;
            }

            return (var1 - 1.0F) * (2.0F * var1 - 2.0F) * (2.0F * var1 - 2.0F) + 1.0F;
         case 2:
            return (float)(1.0 - Math.pow(1.0F - var1, 5.0));
         case 3:
            int var2 = 0;

            while (var2 < BOUNCE_THRESHOLDS.length && !(var1 < BOUNCE_THRESHOLDS[var2])) {
               var2++;
            }

            float var3 = var2 == 0 ? var1 : (float)(var1 - BOUNCE_OFFSETS[var2]);
            return (float)(7.5625 * var3 * var3 + YbpdY[var2]);
         case 4:
            return quadInOut(var1);
         default:
            return var1;
      }
   }

   public static float quadInOut(float var0) {
      return var0 < 0.5F ? 2.0F * var0 * var0 : -1.0F + (4.0F - 2.0F * var0) * var0;
   }
}
