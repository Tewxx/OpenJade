// Jade recovery: original class: jade.deps.eLz.WsC5Mq0
package jade.client.hook;

public final class MovementMathHelper {
   public static final double FXu = 0.3;

   private MovementMathHelper() {
   }

   public static float rkEdkDk(boolean var0, boolean var1) {
      int var2 = 0;
      if (var0) {
         var2++;
      }

      if (var1) {
         var2--;
      }

      return var2;
   }

   public static float applySneakSlowdown(float var0, boolean var1, double var2) {
      return var1 ? (float)(var0 * var2) : var0;
   }

   public static float computeMoveDirectionRadians(float var0, float var1, float var2) {
      float var3 = var1 < 0.0F ? -0.5F : (var1 > 0.0F ? 0.5F : 1.0F);
      float var4 = var1 < 0.0F ? var0 + 180.0F : var0;
      if (var2 != 0.0F) {
         if (var2 > 0.0F) {
            var4 -= 90.0F * var3;
         } else if (var2 < 0.0F) {
            var4 += 90.0F * var3;
         }
      }

      return var4 * (float) (Math.PI / 180.0);
   }

   public static boolean isYawOffAxis(float var0, boolean var1) {
      float var2 = (var0 % 360.0F + 360.0F) % 360.0F;
      if (var2 > 180.0F) {
         var2 -= 360.0F;
      }

      double var3 = var1 ? 1.5 : 10.0;
      if (var2 >= -180.0 + var3 && var2 <= 180.0 - var3) {
         for (byte var5 = -90; var5 <= 90; var5 += 90) {
            if (var2 >= var5 - var3 && var2 <= var5 + var3) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
