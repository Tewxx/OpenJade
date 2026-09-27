// Jade recovery: original class: jade.deps.eLz.eiMmnv
package jade.client.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class EntityReachTracer {
   private static final double EPSILON_SQUARED = 1.0E-6;

   private EntityReachTracer() {
   }

   public static boolean isEntityReachable(World var0, EntityPlayer var1, Entity var2, double var3, double var5, double var7, boolean var9, RaytraceUtils$1 var10) {
      if (var2 != null && var1 != null) {
         Vec3 var11 = var1.getPositionEyes(1.0F);
         Vec3 var12 = HitVecUtils.getAimPointOnEntity(var2, var1, var3, var5);
         if (var12 == null) {
            return false;
         } else if (var11.squareDistanceTo(var12) < 1.0E-6) {
            return true;
         } else {
            return !RaytraceUtils.intersectsEntityAlongRay(var11, var12, var2, var7) ? false : traceHitVector(var0, var1, var2, var11, var12, var7, var9, var10) != null;
         }
      } else {
         return false;
      }
   }

   public static Vec3 getHitVector(World var0, EntityPlayer var1, Entity var2, double var3, double var5, double var7, boolean var9, RaytraceUtils$1 var10) {
      if (var2 != null && var1 != null) {
         Vec3 var11 = var1.getPositionEyes(1.0F);
         AxisAlignedBB var12 = HitVecUtils.fejS(var2);
         if (var12.isVecInside(var11)) {
            return new Vec3((var12.minX + var12.maxX) * 0.5, var11.yCoord, (var12.minZ + var12.maxZ) * 0.5);
         } else {
            Vec3 var13 = HitVecUtils.getAimPointOnEntity(var2, var1, var3, var5);
            if (var13 == null || var11.squareDistanceTo(var13) < 1.0E-6) {
               return null;
            } else {
               return !RaytraceUtils.intersectsEntityAlongRay(var11, var13, var2, var7) ? var13 : traceHitVector(var0, var1, var2, var11, var13, var7, var9, var10);
            }
         }
      } else {
         return null;
      }
   }

   private static Vec3 traceHitVector(World var0, EntityPlayer var1, Entity var2, final Vec3 var3, Vec3 var4, double var5, boolean var7, RaytraceUtils$1 var8) {
      if (RaytraceUtils.canSeeEntity(var0, var1, var3, var4, var2, var5, var7, var8)) {
         return var4;
      } else {
         List var9 = HitVecUtils.getVisibleFaceSamples(var2, var3);
         Collections.sort(var9, new Comparator<Vec3>() {
            public int compare(Vec3 var1, Vec3 var2x) {
               return Double.compare(var3.squareDistanceTo(var1), var3.squareDistanceTo(var2x));
            }
         });

         for (Vec3 var11 : (java.lang.Iterable<Vec3>) (java.lang.Iterable<?>) (var9)) {
            if (RaytraceUtils.canSeeEntity(var0, var1, var3, var11, var2, var5, var7, var8)) {
               return var11;
            }
         }

         return null;
      }
   }
}
