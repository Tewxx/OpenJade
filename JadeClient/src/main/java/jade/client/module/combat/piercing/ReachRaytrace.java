// Jade recovery: original class: jade.deps.eLz.L0TzCTaRA
package jade.client.module.combat.piercing;

import com.google.common.base.Predicate;
import jade.client.common.ClientUtils;
import jade.client.module.other.AntiBot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class ReachRaytrace {
   private ReachRaytrace() {
   }

   public static ReachRaytrace$2 YUam(Minecraft var0, float var1, int var2, boolean var3, boolean var4, boolean var5) {
      Entity var6 = var0.getRenderViewEntity();
      if (var6 != null && var0.theWorld != null) {
         boolean var7 = var0.playerController.extendedReach();
         double var8 = var7 ? 6.0 : var0.playerController.getBlockReachDistance();
         Vec3 var10 = var6.getPositionEyes(var1);
         Vec3 var11 = var6.getLook(var1);
         Vec3 var12 = var10.addVector(var11.xCoord * var8, var11.yCoord * var8, var11.zCoord * var8);
         AxisAlignedBB var13 = var6.getEntityBoundingBox().addCoord(var11.xCoord * var8, var11.yCoord * var8, var11.zCoord * var8).expand(1.0, 1.0, 1.0);
         ReachRaytrace$2 var14 = null;
         PiercingCandidateFilter$0 var15 = null;

         for (Entity var17 : var0.theWorld.getEntitiesInAABBexcluding(var6, var13, MGga())) {
            if (!shouldSkipEntity(var17, var3, var4)) {
               ReachRaytrace$1 var18 = computeEntityIntercept(var17, var6, var10, var12);
               if (var18 != null) {
                  double var19 = var14 == null ? Double.MAX_VALUE : var14.distance;
                  if (PiercingCandidateFilter.isCandidateInRange(var18.QfA, var8, var7, var19, var5, var18.isRiddenEntity, var14 != null)) {
                     PiercingCandidateFilter$0 var21 = createSortKey(var17, var18.QfA);
                     if (PiercingCandidateFilter.isBetterCandidate(var21, var15, var2)) {
                        var14 = new ReachRaytrace$2(var17, var18.vec3, var18.QfA, var8, var7);
                        var15 = var21;
                     }
                  }
               }
            }
         }

         return var14;
      } else {
         return null;
      }
   }

   public static void applyRaytraceResult(Minecraft var0, ReachRaytrace$2 var1) {
      if (var1 != null) {
         if (!var1.zj7 && var1.psP > 3.0 && var1.distance > 3.0) {
            var0.objectMouseOver = new MovingObjectPosition(MovingObjectType.MISS, var1.hitVec, null, new BlockPos(var1.hitVec));
         } else {
            var0.objectMouseOver = new MovingObjectPosition(var1.entity, var1.hitVec);
            if (var1.entity instanceof EntityLivingBase || var1.entity instanceof EntityItemFrame) {
               var0.pointedEntity = var1.entity;
            }
         }
      }
   }

   private static Predicate<Entity> MGga() {
      return new Predicate<Entity>() {
         public boolean apply(Entity var1) {
            return EntitySelectors.NOT_SPECTATING.apply(var1) && var1.canBeCollidedWith();
         }
      };
   }

   private static boolean shouldSkipEntity(Entity var0, boolean var1, boolean var2) {
      if (var1 && !(var0 instanceof EntityPlayer)) {
         return true;
      } else if (var2 && ClientUtils.isTeammate(var0)) {
         return true;
      } else {
         return AntiBot.shouldHideEntity(var0) ? true : var0 instanceof EntityPlayer && ClientUtils.isFriend((EntityPlayer)var0);
      }
   }

   private static ReachRaytrace$1 computeEntityIntercept(Entity var0, Entity var1, Vec3 var2, Vec3 var3) {
      float var4 = var0.getCollisionBorderSize();
      AxisAlignedBB var5 = var0.getEntityBoundingBox().expand(var4, var4, var4);
      boolean var6 = var5.isVecInside(var2);
      MovingObjectPosition var7 = var5.calculateIntercept(var2, var3);
      if (!var6 && var7 == null) {
         return null;
      } else {
         Vec3 var8 = var6 && var7 == null ? var2 : var7.hitVec;
         double var9 = var6 ? 0.0 : var2.distanceTo(var8);
         boolean var11 = var0 == var1.ridingEntity && !ClientUtils.canRiderInteract(var1);
         return new ReachRaytrace$1(var8, var9, var11);
      }
   }

   private static PiercingCandidateFilter$0 createSortKey(Entity var0, double var1) {
      if (!(var0 instanceof EntityLivingBase)) {
         return new PiercingCandidateFilter$0(false, Integer.MAX_VALUE, Float.POSITIVE_INFINITY, var1);
      } else {
         EntityLivingBase var3 = (EntityLivingBase)var0;
         return new PiercingCandidateFilter$0(true, var3.hurtTime, var3.getHealth(), var1);
      }
   }
}
