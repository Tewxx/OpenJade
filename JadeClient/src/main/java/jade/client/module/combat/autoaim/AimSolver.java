// Jade recovery: original class: jade.deps.eLz.gx7O5zKt
package jade.client.module.combat.autoaim;

import jade.client.module.shared.ProjectileMotion;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class AimSolver {
   private final MotionPredictor motionPredictor;

   public AimSolver(MotionPredictor var1) {
      this.motionPredictor = var1;
   }

   public AimResult UMZR(World var1, EntityPlayer var2, EntityPlayer var3, AimSolver$2 var4, double var5, double var7, double var9) {
      if (var2 != null && var3 != null && !(var5 <= 0.05)) {
         AimSolver$1 var11 = null;
         AimSolver$1 var12 = null;
         boolean var13 = false;
         MotionPredictor$1 var14 = this.XOBf2(var1, var3);

         for (AimSolver$1 var17 : this.buildAimCandidates(var2, var3, var4, var5, var7, var14)) {
            AimSolver$3 var18 = this.traceAimPath(var1, var2, var3, var17, var4, var5, var7, var17.travelTicks, var14);
            var17.trajectory = var18.mdr;
            var17.vec3 = var18.ful48;
            var17.hitsTarget = var18.sxg21;
            var17.pathBlocked = var18.hitTarget;
            var13 |= var18.sxg21;
            if (!var18.hitTarget || var11 != null && !(var17.exactTravelTicks < var11.exactTravelTicks)) {
               if (!var18.hitTarget && (var12 == null || var17.distanceError < var12.distanceError)) {
                  var12 = var17;
               }
            } else {
               var11 = var17;
            }
         }

         if (var11 != null) {
            AimResult$0 var20 = var11.predictedPosition.getConfidence() >= var9 ? AimResult$0.HIT : AimResult$0.LOW_CONFIDENCE;
            return new AimResult(var20, var11.yaw, var11.SLrgWv, var11.travelTicks, var11.predictedPosition.getConfidence(), var11.predictedPosition, var11.trajectory, var11.vec3);
         } else {
            AimResult$0 var19 = (var12 != null ? !var12.hitsTarget : !var13) ? AimResult$0.IMPOSSIBLE : AimResult$0.BLOCKED;
            return var12 == null
               ? new AimResult(var19, 0.0F, 0.0F, 0, 0.0, null, null, null)
               : new AimResult(var19, var12.yaw, var12.SLrgWv, var12.travelTicks, var12.predictedPosition.getConfidence(), var12.predictedPosition, var12.trajectory, var12.vec3);
         }
      } else {
         return this.createImpossibleResult();
      }
   }

   private List<AimSolver$1> buildAimCandidates(EntityPlayer var1, EntityPlayer var2, AimSolver$2 var3, double var4, double var6, MotionPredictor$1 var8) {
      ArrayList var9 = new ArrayList();
      AimSolver$1 var10 = this.solveAimAtTick(var1, var2, var3, var4, var6, 0.25, var8);
      AimSolver$1 var11 = var10;

      for (double var12 = 0.5; var12 <= var3.maxTicks; var12 += 0.25) {
         AimSolver$1 var14 = this.solveAimAtTick(var1, var2, var3, var4, var6, var12, var8);
         if (var14 != null) {
            if (var11 == null || var14.distanceError < var11.distanceError) {
               var11 = var14;
            }

            if (var10 != null && mqYli(var10.distanceDelta, var14.distanceDelta)) {
               AimSolver$1 var15 = this.refineAimCandidate(var1, var2, var3, var4, var6, var10.exactTravelTicks, var14.exactTravelTicks, var8);
               if (var15 != null && (var9.isEmpty() || Math.abs(var15.exactTravelTicks - ((AimSolver$1)var9.get(var9.size() - 1)).exactTravelTicks) > 0.2)) {
                  var9.add(var15);
               }
            }

            var10 = var14;
         }
      }

      if (var11 != null && var9.isEmpty()) {
         var9.add(var11);
      }

      return var9;
   }

   private AimSolver$1 refineAimCandidate(EntityPlayer var1, EntityPlayer var2, AimSolver$2 var3, double var4, double var6, double var8, double var10, MotionPredictor$1 var12) {
      AimSolver$1 var13 = this.solveAimAtTick(var1, var2, var3, var4, var6, var8, var12);
      AimSolver$1 var14 = this.solveAimAtTick(var1, var2, var3, var4, var6, var10, var12);
      if (var13 != null && var14 != null) {
         AimSolver$1 var15 = var13.distanceError <= var14.distanceError ? var13 : var14;

         for (int var16 = 0; var16 < 12; var16++) {
            double var17 = (var8 + var10) * 0.5;
            AimSolver$1 var19 = this.solveAimAtTick(var1, var2, var3, var4, var6, var17, var12);
            if (var19 == null) {
               break;
            }

            if (var19.distanceError < var15.distanceError) {
               var15 = var19;
            }

            if (mqYli(var13.distanceDelta, var19.distanceDelta)) {
               var10 = var17;
            } else {
               var8 = var17;
               var13 = var19;
            }
         }

         return var15;
      } else {
         return null;
      }
   }

   private static boolean mqYli(double var0, double var2) {
      return var0 == 0.0 || var2 == 0.0 || var0 < 0.0 != var2 < 0.0;
   }

   private AimSolver$1 solveAimAtTick(EntityPlayer var1, EntityPlayer var2, AimSolver$2 var3, double var4, double var6, double var8, MotionPredictor$1 var10) {
      TargetPrediction var11 = this.motionPredictor.ygp3(var2.getEntityId(), var6 + var8, var10);
      if (var11 == null) {
         return null;
      } else {
         double var12 = var11.fmmQ() + var2.height * 0.58;
         AimSolver$1 var14 = this.solveProjectileAngles(var1, var11.RAYFZ(), var12, var11.getZ(), var3, var4, var8);
         var14.predictedPosition = var11;
         return var14;
      }
   }

   private AimSolver$1 solveProjectileAngles(EntityPlayer var1, double var2, double var4, double var6, AimSolver$2 var8, double var9, double var11) {
      double var13 = Math.atan2(var6 - var1.posZ, var2 - var1.posX) - (Math.PI / 2);
      double var15 = 0.0;
      double var17 = 0.0;
      double var19 = 0.0;

      for (int var21 = 0; var21 < 3; var21++) {
         double var22 = var1.posX - Math.cos(var13) * 0.16;
         double var24 = var1.posY + var1.getEyeHeight() - 0.10000000149;
         double var26 = var1.posZ - Math.sin(var13) * 0.16;
         double[] var28 = ProjectileMotion.solveLaunchVelocity(var2 - var22, var4 - var24, var6 - var26, var8.drag, var8.gravity, var11);
         var15 = var28[0];
         var17 = var28[1];
         var19 = var28[2];
         var13 = Math.atan2(var19, var15) - (Math.PI / 2);
      }

      double var29 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
      double var23 = Math.abs(var29 - var9);
      double var25 = Math.sqrt(var15 * var15 + var19 * var19);
      float var27 = (float)(Math.atan2(var19, var15) * 180.0 / Math.PI) - 90.0F;
      float var30 = (float)(-(Math.atan2(var17, var25) * 180.0 / Math.PI));
      return new AimSolver$1(var27, var30, var11, var29 - var9);
   }

   private AimSolver$3 traceAimPath(
      World var1, EntityPlayer var2, EntityPlayer var3, AimSolver$1 var4, AimSolver$2 var5, double var6, double var8, int var10, MotionPredictor$1 var11
   ) {
      double var12 = Math.toRadians(var4.yaw);
      double var14 = Math.toRadians(var4.SLrgWv);
      double var16 = var2.posX - Math.cos(var12) * 0.16;
      double var18 = var2.posY + var2.getEyeHeight() - 0.10000000149;
      double var20 = var2.posZ - Math.sin(var12) * 0.16;
      double var22 = -Math.sin(var12) * Math.cos(var14) * var6;
      double var24 = -Math.sin(var14) * var6;
      double var26 = Math.cos(var12) * Math.cos(var14) * var6;
      ArrayList var28 = new ArrayList();
      var28.add(new Vec3(var16, var18, var20));

      for (int var29 = 1; var29 <= var10 + 2; var29++) {
         Vec3 var30 = new Vec3(var16, var18, var20);
         Vec3 var31 = new Vec3(var16 + var22, var18 + var24, var20 + var26);
         MovingObjectPosition var32 = var1 == null ? null : var1.rayTraceBlocks(var30, var31, false, true, false);
         TargetPrediction var33 = this.motionPredictor.ygp3(var3.getEntityId(), var8 + var29 - 1.0, var11);
         TargetPrediction var34 = this.motionPredictor.ygp3(var3.getEntityId(), var8 + var29, var11);
         if (var34 == null || var33 == null) {
            return new AimSolver$3(false, false, var28, null);
         }

         AxisAlignedBB var35 = this.ojWv(var3, var33, var34).expand(var5.hitExpansion, var5.hitExpansion, var5.hitExpansion);
         MovingObjectPosition var36 = var35.calculateIntercept(var30, var31);
         double var37 = var32 == null ? Double.MAX_VALUE : var30.squareDistanceTo(var32.hitVec);
         double var39 = var36 == null ? Double.MAX_VALUE : var30.squareDistanceTo(var36.hitVec);
         if (var36 != null && var39 <= var37) {
            var28.add(var36.hitVec);
            return new AimSolver$3(true, false, var28, var36.hitVec);
         }

         if (var32 != null) {
            var28.add(var32.hitVec);
            return new AimSolver$3(false, true, var28, var32.hitVec);
         }

         var16 = var31.xCoord;
         var18 = var31.yCoord;
         var20 = var31.zCoord;
         var28.add(var31);
         var22 *= var5.drag;
         var24 = var24 * var5.drag - var5.gravity;
         var26 *= var5.drag;
      }

      return new AimSolver$3(false, false, var28, null);
   }

   private MotionPredictor$1 XOBf2(final World var1, final Entity var2) {
      return var1 != null && var2 != null ? new MotionPredictor$1() {
         @Override
         public boolean isPositionClear(double var1x, double var3, double var5) {
            AxisAlignedBB var7 = var2.getEntityBoundingBox();
            double var8 = var2.width * 0.5;
            AxisAlignedBB var10 = new AxisAlignedBB(var1x - var8, var3, var5 - var8, var1x + var8, var3 + var2.height, var5 + var8);
            return var1.getCollidingBoundingBoxes(var2, var10).isEmpty();
         }
      } : null;
   }

   private AxisAlignedBB getPredictedBoundingBox(Entity var1, TargetPrediction var2) {
      double var3 = var1.width * 0.5;
      return new AxisAlignedBB(var2.RAYFZ() - var3, var2.fmmQ(), var2.getZ() - var3, var2.RAYFZ() + var3, var2.fmmQ() + var1.height, var2.getZ() + var3);
   }

   private AxisAlignedBB ojWv(Entity var1, TargetPrediction var2, TargetPrediction var3) {
      AxisAlignedBB var4 = this.getPredictedBoundingBox(var1, var2);
      AxisAlignedBB var5 = this.getPredictedBoundingBox(var1, var3);
      return new AxisAlignedBB(
         Math.min(var4.minX, var5.minX),
         Math.min(var4.minY, var5.minY),
         Math.min(var4.minZ, var5.minZ),
         Math.max(var4.maxX, var5.maxX),
         Math.max(var4.maxY, var5.maxY),
         Math.max(var4.maxZ, var5.maxZ)
      );
   }

   private AimResult createImpossibleResult() {
      return new AimResult(AimResult$0.IMPOSSIBLE, 0.0F, 0.0F, 0, 0.0, null, null, null);
   }
}
