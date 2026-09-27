// Jade recovery: original class: jade.deps.eLz.Oq1udl
package jade.client.common;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class EntityRaycast {
   private EntityRaycast() {
   }

   public static EntityLivingBase ZdwP(Minecraft var0, EntityPlayer var1, int var2, Predicate<Entity> var3) {
      MovingObjectPosition var4 = var1.rayTrace(var2, 1.0F);
      Vec3 var5 = var1.getPositionEyes(1.0F);
      Vec3 var6 = AngleUtils.getLookVector(var1.rotationYaw, var1.rotationPitch);
      double var7 = var6.xCoord * var2;
      double var9 = var6.yCoord * var2;
      double var11 = var6.zCoord * var2;
      Vec3 var13 = var5.addVector(var7, var9, var11);
      List var14 = var0.theWorld
         .getEntitiesWithinAABBExcludingEntity(
            var0.getRenderViewEntity(), var0.getRenderViewEntity().getEntityBoundingBox().addCoord(var7, var9, var11).expand(1.0, 1.0, 1.0)
         );
      ClosestHitTracker var15 = new ClosestHitTracker(var2);
      Entity var16 = null;
      Vec3 var17 = null;

      for (Entity var19 : (java.lang.Iterable<Entity>) (java.lang.Iterable<?>) (var14)) {
         if (var19.canBeCollidedWith()) {
            double var20 = var19.getCollisionBorderSize();
            AxisAlignedBB var22 = var19.getEntityBoundingBox().expand(var20, var20, var20);
            MovingObjectPosition var23 = var22.calculateIntercept(var5, var13);
            boolean var24;
            Vec3 var25;
            if (var22.isVecInside(var5)) {
               var24 = var15.icqczQ();
               var25 = var23 == null ? var5 : var23.hitVec;
            } else {
               if (var23 == null) {
                  continue;
               }

               var25 = var23.hitVec;
               var24 = var15.considerHit(var5.distanceTo(var25), () -> EntityRaycast.shouldSkipRiddenEntity(var19, var0, var3));
            }

            if (var24) {
               var16 = var19;
               var17 = var25;
            }
         }
      }

      if (var16 != null && var15.fewhsa8(var2, var4 == null)) {
         var4 = new MovingObjectPosition(var16, var17);
      }

      if (var4 != null && var4.typeOfHit == MovingObjectType.ENTITY) {
         return var4.entityHit instanceof EntityLivingBase ? (EntityLivingBase)var4.entityHit : null;
      } else {
         return null;
      }
   }

   private static boolean shouldSkipRiddenEntity(Entity var0, Minecraft var1, Predicate var2) {
      return var0 == var1.getRenderViewEntity().ridingEntity && !var2.test(var0);
   }
}
