// Jade recovery: original class: jade.deps.eLz.zHiJ8r
package jade.client.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public final class TargetAngleUtils {
   private TargetAngleUtils() {
   }

   public static float[] getAnglesToEntity(Entity var0, EntityPlayer var1, int var2) {
      double var3 = extrapolate(var0.posX, var0.lastTickPosX, var2);
      double var5 = extrapolate(var0.posZ, var0.lastTickPosZ, var2);
      double var7 = var0 instanceof EntityLivingBase
         ? var0.posY + var0.getEyeHeight() * 0.9
         : (var0.getEntityBoundingBox().minY + var0.getEntityBoundingBox().maxY) * 0.5;
      double var9 = var1.posY + var1.getEyeHeight();
      return AngleUtils.computeRotations(var3 - var1.posX, var7 - var9, var5 - var1.posZ, var1.rotationYaw, var1.rotationPitch, 3.0F);
   }

   public static double extrapolate(double var0, double var2, int var4) {
      double var5 = var0;
      double var7 = var0 - var2;

      for (int var9 = 0; var9 < var4; var9++) {
         var5 += var7;
      }

      return var5;
   }
}
