// Jade recovery: original class: jade.deps.eLz.WcnKFuiwZ
package jade.client.module.shared;

public final class TargetApproachCheck {
   private static final double MIN_PLAYER_MOVE_SPEED = 0.035;
   private static final double mXue = 0.025;
   private static final double MIN_APPROACH_DOT = 0.018;
   private static final float AShP = 75.0F;

   private TargetApproachCheck() {
   }

   public static boolean isTargetClosingIn(double var0, double var2, double var4, double var6, double var8, double var10, float var12, boolean var13) {
      double var14 = Math.sqrt(var0 * var0 + var2 * var2);
      if (var14 < 0.001) {
         return false;
      } else {
         double var16 = var0 / var14;
         double var18 = var2 / var14;
         double var20 = var4 * var16 + var6 * var18;
         double var22 = var8 * var16 + var10 * var18;
         boolean var24 = var22 > 0.018 && lengthSquared(var8, var10) > 6.250000000000001E-4;
         boolean var25 = var20 > 0.018 && lengthSquared(var4, var6) > 0.0012250000000000002;
         float var26 = (float)(Math.atan2(var2, var0) * (180.0 / Math.PI)) - 90.0F;
         boolean var27 = Math.abs(normalizeAngle(var26 - var12)) <= 75.0F;
         return var24 && (var25 || var27 && var13);
      }
   }

   private static double lengthSquared(double var0, double var2) {
      return var0 * var0 + var2 * var2;
   }

   private static float normalizeAngle(float var0) {
      float var1 = var0 % 360.0F;
      if (var1 >= 180.0F) {
         var1 -= 360.0F;
      }

      if (var1 < -180.0F) {
         var1 += 360.0F;
      }

      return var1;
   }
}
