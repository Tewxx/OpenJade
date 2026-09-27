// Jade recovery: original class: jade.deps.eLz.cuGMHP
package jade.client.common;

public final class ItemScoreUtils {
   private ItemScoreUtils() {
   }

   public static double getDurabilityScore(int var0, int var1, boolean var2) {
      return var2 && var1 > 0 ? (double)(var1 - var0) / var1 / 1000.0 : 0.0;
   }

   public static double getSwordDamageScore(double var0, double var2, int var4, int var5, double var6) {
      return var0 + var2 + var4 * 4.0 + var5 * 0.1 + var6;
   }

   public static double getBowDamageScore(int var0, int var1) {
      double var2 = 2.0 + var0 * 0.5 + 0.5;
      double var4 = 3.0 * var2;
      return var4 + var4 * 0.25 + 0.5 + var1 * 4.0;
   }

   public static double getBowTotalScore(double var0, int var2, double var3) {
      return var0 + var2 * 0.15 + var3;
   }

   public static double getToolScore(float var0, int var1, boolean var2, boolean var3, double var4) {
      float var6 = var0;
      if (var0 > 1.0F && var1 > 0) {
         var6 = var0 + (var1 * var1 + 1);
      }

      if (var6 <= 1.0F) {
         return 0.0;
      } else {
         if (var3 && !var2) {
            var6 *= 0.3F;
         }

         return var6 + var4;
      }
   }

   public static double combineToolScore(int var0, int var1, int var2, double var3) {
      return var0 * 1000.0 + var1 * 10.0 + var2 + var3;
   }
}
