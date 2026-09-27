// Jade recovery: original class: jade.deps.eLz.qUj5m3Ke4T
package jade.client.common;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class RaytraceUtils {
   public static final RaytraceUtils$1 KmB = new RaytraceUtils$1() {
      @Override
      public boolean shouldIgnoreEntity(Entity var1) {
         return true;
      }
   };

   private RaytraceUtils() {
   }

   public static boolean canSeeEntity(World var0, EntityPlayer var1, Vec3 var2, Vec3 var3, Entity var4, double var5, boolean var7, RaytraceUtils$1 var8) {
      if (var0 != null && var1 != null && var4 != null) {
         Vec3 var9 = var3.subtract(var2);
         double var10 = var9.lengthVector();
         if (var10 < 1.0E-6) {
            return false;
         } else {
            double var12 = var5 / var10;
            Vec3 var14 = var2.addVector(var9.xCoord * var12, var9.yCoord * var12, var9.zCoord * var12);
            AxisAlignedBB var15 = HitVecUtils.fejS(var4);
            MovingObjectPosition var16 = var15.calculateIntercept(var2, var14);
            if (var16 == null) {
               return false;
            } else {
               double var17 = var2.squareDistanceTo(var16.hitVec);
               if (!var7) {
                  MovingObjectPosition var19 = var0.rayTraceBlocks(var2, var14, false, false, false);
                  if (var19 != null && var19.typeOfHit == MovingObjectType.BLOCK && var2.squareDistanceTo(var19.hitVec) < var17) {
                     return false;
                  }
               }

               return !isLineOfSightBlockedByEntity(var0, var1, var2, var14, var4, var17, var8);
            }
         }
      } else {
         return false;
      }
   }

   public static boolean isLineOfSightBlockedByEntity(World var0, EntityPlayer var1, Vec3 var2, Vec3 var3, Entity var4, double var5, RaytraceUtils$1 var7) {
      if (var0 != null && var1 != null) {
         if (var7 == KmB) {
            return false;
         } else {
            Vec3 var8 = var3.subtract(var2);
            AxisAlignedBB var9 = var1.getEntityBoundingBox().addCoord(var8.xCoord, var8.yCoord, var8.zCoord).expand(1.0, 1.0, 1.0);

            for (Entity var12 : var0.getEntitiesInAABBexcluding(var1, var9, Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith))) {
               if (var12 != null && var12 != var4 && !var12.isDead && (var7 == null || !var7.shouldIgnoreEntity(var12))) {
                  AxisAlignedBB var13 = HitVecUtils.fejS(var12);
                  if (var13.isVecInside(var2)) {
                     return true;
                  }

                  MovingObjectPosition var14 = var13.calculateIntercept(var2, var3);
                  if (var14 != null && var2.squareDistanceTo(var14.hitVec) < var5 - 1.0E-7) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean intersectsEntityAlongRay(Vec3 var0, Vec3 var1, Entity var2, double var3) {
      Vec3 var5 = var1.subtract(var0);
      double var6 = var5.lengthVector();
      if (var6 < 1.0E-6) {
         return false;
      } else {
         double var8 = var3 / var6;
         Vec3 var10 = var0.addVector(var5.xCoord * var8, var5.yCoord * var8, var5.zCoord * var8);
         AxisAlignedBB var11 = HitVecUtils.fejS(var2);
         return var11.isVecInside(var0) || var11.calculateIntercept(var0, var10) != null;
      }
   }
}
