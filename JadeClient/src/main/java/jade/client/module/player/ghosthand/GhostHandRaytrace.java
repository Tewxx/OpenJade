// Jade recovery: original class: jade.deps.eLz.Y8xZfonP
package jade.client.module.player.ghosthand;

import com.google.common.base.Predicate;
import jade.client.common.ClientUtils;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class GhostHandRaytrace {
   private GhostHandRaytrace() {
   }

   public static Entity findEntityOnPath(World var0, Entity var1, Vec3 var2, Vec3 var3) {
      double var4 = var2.distanceTo(var3);
      List var6 = var0.getEntitiesInAABBexcluding(var1, createSearchBox(var2, var3), new Predicate<Entity>() {
         public boolean apply(Entity var1) {
            return EntitySelectors.NOT_SPECTATING.apply(var1) && var1.canBeCollidedWith();
         }
      });
      Entity var7 = null;
      double var8 = var4;

      for (Entity var11 : (java.lang.Iterable<Entity>) (java.lang.Iterable<?>) (var6)) {
         double var12 = computeInterceptDistance(var11, var1, var2, var3);
         if (var12 >= 0.0 && var12 < var8) {
            var7 = var11;
            var8 = var12;
         }
      }

      return var7;
   }

   private static AxisAlignedBB createSearchBox(Vec3 var0, Vec3 var1) {
      return new AxisAlignedBB(
         Math.min(var0.xCoord, var1.xCoord) - 1.0,
         Math.min(var0.yCoord, var1.yCoord) - 1.0,
         Math.min(var0.zCoord, var1.zCoord) - 1.0,
         Math.max(var0.xCoord, var1.xCoord) + 1.0,
         Math.max(var0.yCoord, var1.yCoord) + 1.0,
         Math.max(var0.zCoord, var1.zCoord) + 1.0
      );
   }

   private static double computeInterceptDistance(Entity var0, Entity var1, Vec3 var2, Vec3 var3) {
      if (var0 == var1) {
         return -1.0;
      } else {
         float var4 = var0.getCollisionBorderSize();
         AxisAlignedBB var5 = var0.getEntityBoundingBox().expand(var4, var4, var4);
         boolean var6 = var5.isVecInside(var2);
         MovingObjectPosition var7 = var5.calculateIntercept(var2, var3);
         if (!var6 && var7 == null) {
            return -1.0;
         } else if (var0 == var1.ridingEntity && !ClientUtils.canRiderInteract(var1)) {
            return -1.0;
         } else {
            return var6 ? 0.0 : var2.distanceTo(var7.hitVec);
         }
      }
   }
}
