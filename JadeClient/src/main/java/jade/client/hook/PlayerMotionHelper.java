// Jade recovery: original class: jade.deps.eLz.gLlLxqt
package jade.client.hook;

import net.minecraft.util.MathHelper;

public final class PlayerMotionHelper {
   private PlayerMotionHelper() {
   }

   public static PlayerMotionHelper$1 computeBodyRotation(float var0, float var1, float var2, float var3) {
      float var4 = MathHelper.wrapAngleTo180_float(var2 - var0);
      float var5 = var0 + var4 * 0.3F;
      float var6 = MathHelper.wrapAngleTo180_float(var1 - var5);
      if (var6 < -75.0F) {
         var6 = -75.0F;
      }

      if (var6 >= 75.0F) {
         var6 = 75.0F;
      }

      var5 = var1 - var6;
      if (var6 * var6 > 2500.0F) {
         var5 += var6 * 0.2F;
      }

      return new PlayerMotionHelper$1(var5, -var3);
   }

   public static PlayerMotionHelper$2 computeJumpMotion(double var0, double var2, float var4, int var5, boolean var6, float var7) {
      double var8 = var4;
      if (var5 >= 0) {
         var8 += (var5 + 1) * 0.1F;
      }

      if (!var6) {
         return new PlayerMotionHelper$2(var0, var8, var2);
      } else {
         float var10 = var7 * (float) (Math.PI / 180.0);
         return new PlayerMotionHelper$2(var0 - MathHelper.sin(var10) * 0.2F, var8, var2 + MathHelper.cos(var10) * 0.2F);
      }
   }
}
