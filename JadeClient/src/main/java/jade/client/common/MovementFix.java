// Jade recovery: original class: jade.deps.eLz.wkrKRw
package jade.client.common;

public final class MovementFix {
   private MovementFix() {
   }

   public static float[] fixMovementInput(float var0, float var1, float var2, float var3, boolean var4) {
      if (var2 == 0.0F && var3 == 0.0F) {
         return new float[]{var2, var3};
      } else {
         float var5 = var4 ? 0.3F : 1.0F;
         double var6 = wrapDegrees(Math.toDegrees(getMovementYaw(var0, var2, var3)));
         float var8 = 0.0F;
         float var9 = 0.0F;
         double var10 = Float.MAX_VALUE;

         for (int var12 = -1; var12 <= 1; var12++) {
            for (int var13 = -1; var13 <= 1; var13++) {
               if (var12 != 0 || var13 != 0) {
                  float var14 = var12 * var5;
                  float var15 = var13 * var5;
                  double var16 = wrapDegrees(Math.toDegrees(getMovementYaw(var1, var14, var15)));
                  double var18 = Math.abs(wrapDegrees(var6 - var16));
                  if (var18 < var10) {
                     var10 = var18;
                     var8 = var14;
                     var9 = var15;
                  }
               }
            }
         }

         return new float[]{var8, var9};
      }
   }

   public static double getMovementYaw(float var0, double var1, double var3) {
      if (var1 < 0.0) {
         var0 += 180.0F;
      }

      float var5 = 1.0F;
      if (var1 < 0.0) {
         var5 = -0.5F;
      } else if (var1 > 0.0) {
         var5 = 0.5F;
      }

      if (var3 > 0.0) {
         var0 -= 90.0F * var5;
      }

      if (var3 < 0.0) {
         var0 += 90.0F * var5;
      }

      return Math.toRadians(var0);
   }

   private static double wrapDegrees(double var0) {
      var0 %= 360.0;
      if (var0 >= 180.0) {
         var0 -= 360.0;
      }

      if (var0 < -180.0) {
         var0 += 360.0;
      }

      return var0;
   }
}
