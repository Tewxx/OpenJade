// Jade recovery: original class: jade.deps.eLz.vMvOoD
package jade.client.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public final class EntityAngles {
   private EntityAngles() {
   }

   public static float[] getAnglesTo(EntityPlayer var0, Entity var1) {
      if (var1 == null) {
         return null;
      } else {
         double var2 = var1 instanceof EntityLivingBase
            ? var1.posY + ((EntityLivingBase)var1).getEyeHeight() * 0.9
            : (var1.getEntityBoundingBox().minY + var1.getEntityBoundingBox().maxY) * 0.5;
         double var4 = var0.posY + var0.getEyeHeight();
         return AngleUtils.computeRotationsPrecise(var1.posX - var0.posX, var2 - var4, var1.posZ - var0.posZ, var0.rotationYaw, var0.rotationPitch, 0.0F);
      }
   }

   public static float getYawTo(EntityPlayer var0, Entity var1) {
      return AngleUtils.getYawToPoint(var0.posX, var0.posZ, var1.posX, var1.posZ);
   }

   public static float getPitchTo(EntityPlayer var0, Entity var1) {
      double var2 = var1.posX - var0.posX;
      double var4 = var1.posZ - var0.posZ;
      double var6 = var1.posY + var1.getEyeHeight() * 0.5F - (var0.posY + var0.getEyeHeight());
      return (float)(-Math.toDegrees(Math.atan2(var6, Math.sqrt(var2 * var2 + var4 * var4))));
   }

   public static double wrapAngleDifference(float var0, float var1) {
      double var2 = (var0 - var1) % 360.0;
      return (var2 + 540.0) % 360.0 - 180.0;
   }
}
