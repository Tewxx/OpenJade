// Jade recovery: original class: jade.deps.eLz.RYb7MaSoEl
package jade.client.hook;

import net.minecraft.util.MathHelper;

public final class EntityMotionMath {
   private EntityMotionMath() {
   }

   public static EntityMotionMath$2 computeMoveFlying(double var0, double var2, float var4, float var5, float var6, float var7) {
      float var8 = var4 * var4 + var5 * var5;
      if (var8 < 1.0E-4F) {
         return new EntityMotionMath$2(var0, var2);
      } else {
         var8 = MathHelper.sqrt_float(var8);
         if (var8 < 1.0F) {
            var8 = 1.0F;
         }

         float var9 = var6 / var8;
         float var10 = var4 * var9;
         float var11 = var5 * var9;
         float var12 = var7 * (float) Math.PI / 180.0F;
         float var13 = MathHelper.sin(var12);
         float var14 = MathHelper.cos(var12);
         return new EntityMotionMath$2(var0 + var10 * var14 - var11 * var13, var2 + var11 * var14 + var10 * var13);
      }
   }

   public static EntityMotionMath$1 computeVectorForRotation(float var0, float var1) {
      float var2 = -var1 * (float) (Math.PI / 180.0) - (float) Math.PI;
      float var3 = -var0 * (float) (Math.PI / 180.0);
      float var4 = MathHelper.cos(var2);
      float var5 = MathHelper.sin(var2);
      float var6 = -MathHelper.cos(var3);
      float var7 = MathHelper.sin(var3);
      return new EntityMotionMath$1(var5 * var6, var7, var4 * var6);
   }

   public static boolean shouldEnableSafeWalk(boolean var0, boolean var1, boolean var2, boolean var3) {
      return var0 || var1 && var2 && var3;
   }
}
