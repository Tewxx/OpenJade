// Jade recovery: original class: jade.deps.eLz.k78Znx
package jade.client.common;

import jade.client.event.JumpEvent;
import jade.client.event.MoveFlyingEvent;
import jade.client.event.MoveStateUpdateEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RotationEvent;
import jade.client.event.TickStartEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class RotationHandler {
   private static final RotationHandler rotationHandler = new RotationHandler();
   private final Minecraft mc = Minecraft.getMinecraft();
   private final EntityRotationOverride renderRotationState = new EntityRotationOverride();
   private Float KdV;
   private Float targetPitch;
   private boolean Mpd16;
   private boolean suppressMovementCorrection;
   private boolean rotationEventPosted;
   public boolean renderRotationApplied;

   public static float wrapAngleNear(float var0, float var1) {
      return RotationInterpolator.tfpXj(var0, var1);
   }

   public float[] getSmoothedEventRotations(Entity var1, RotationEvent var2, float var3) {
      if (!this.canComputeRotations(var1)) {
         return null;
      } else {
         float var4 = var2.MGzP2 == null ? RotationUtils.lastSentRotation[0] : var2.MGzP2;
         float var5 = var2.pitch == null ? RotationUtils.lastSentRotation[1] : var2.pitch;
         float[] var6 = RotationUtils.QweciK(var1, var4, var5);
         return var6 == null ? null : RotationInterpolator.stepRotation(var4, var5, var6[0], var6[1], var3);
      }
   }

   public float[] getSmoothedEntityRotations(Entity var1, float var2) {
      if (!this.canComputeRotations(var1)) {
         return null;
      } else {
         float var3 = this.mc.thePlayer.rotationYaw;
         float var4 = this.mc.thePlayer.rotationPitch;
         float[] var5 = RotationUtils.QweciK(var1, var3, var4);
         return var5 == null ? null : RotationInterpolator.stepRotation(var3, var4, var5[0], var5[1], var2);
      }
   }

   public float[] getEventEntityRotations(Entity var1, RotationEvent var2, int var3) {
      return this.getEventEntityRotationsWithOffsets(var1, var2, var3, 0.0, 0.0, 0.0F);
   }

   public float[] getEventEntityRotationsWithOffsets(Entity var1, RotationEvent var2, int var3, double var4, double var6, float var8) {
      return this.getEventEntityRotationsWithRayTrace(var1, var2, var3, var4, var6, var8, false, 10.0);
   }

   public float[] getEventEntityRotationsWithRayTrace(Entity var1, RotationEvent var2, int var3, double var4, double var6, float var8, boolean var9, double var10) {
      return this.getEventEntityRotationsAdvanced(var1, var2, var3, var4, var6, var8, var9, var10, false, true);
   }

   public float[] getEventEntityRotationsAdvanced(Entity var1, RotationEvent var2, int var3, double var4, double var6, float var8, boolean var9, double var10, boolean var12, boolean var13) {
      return this.getEventEntityRotationsFull(var1, var2, var3, var4, var6, var8, var9, var10, var12, var13, var13);
   }

   public float[] getEventEntityRotationsFull(
      Entity var1, RotationEvent var2, int var3, double var4, double var6, float var8, boolean var9, double var10, boolean var12, boolean var13, boolean var14
   ) {
      if (!this.canComputeRotations(var1)) {
         return null;
      } else {
         float var15 = var2.MGzP2 == null ? RotationUtils.lastSentRotation[0] : var2.MGzP2;
         float var16 = var2.pitch == null ? RotationUtils.lastSentRotation[1] : var2.pitch;
         return this.computeEntityRotations(var1, var15, var16, var3, var4, var6, var8, var9, var10, var12, var13, var14);
      }
   }

   public float[] getEntityRotations(Entity var1, int var2) {
      return this.LMt39(var1, var2, 0.0, 0.0, 0.0F);
   }

   public float[] LMt39(Entity var1, int var2, double var3, double var5, float var7) {
      return this.getEntityRotationsWithOffsets(var1, var2, var3, var5, var7, false, 10.0);
   }

   public float[] getEntityRotationsWithOffsets(Entity var1, int var2, double var3, double var5, float var7, boolean var8, double var9) {
      return this.getEntityRotationsWithRayTrace(var1, var2, var3, var5, var7, var8, var9, false, true);
   }

   public float[] getEntityRotationsWithRayTrace(Entity var1, int var2, double var3, double var5, float var7, boolean var8, double var9, boolean var11, boolean var12) {
      return this.getEntityRotationsFull(var1, var2, var3, var5, var7, var8, var9, var11, var12, var12);
   }

   public float[] getEntityRotationsFull(Entity var1, int var2, double var3, double var5, float var7, boolean var8, double var9, boolean var11, boolean var12, boolean var13) {
      return !this.canComputeRotations(var1)
         ? null
         : this.computeEntityRotations(var1, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, var2, var3, var5, var7, var8, var9, var11, var12, var13);
   }

   private float[] computeEntityRotations(
      Entity var1,
      float var2,
      float var3,
      int var4,
      double var5,
      double var7,
      float var9,
      boolean var10,
      double var11,
      boolean var13,
      boolean var14,
      boolean var15
   ) {
      float[] var16 = var10 ? RotationUtils.anglesToEntityRayTracedFull(var1, var5, var7, var2, var3, var11, var13, var14, var15) : RotationUtils.anglesToEntity(var1, var5, var7, var2, var3);
      return var16 == null ? null : RotationUtils.smoothAnglesRandomized(var2, var3, var16[0], var16[1], var4, var9);
   }

   private boolean canComputeRotations(Entity var1) {
      return var1 != null && this.mc.thePlayer != null;
   }

   public void updateTargetRotation() {
      if (this.mc.thePlayer != null && !this.rotationEventPosted) {
         this.rotationEventPosted = true;
         RotationEvent var1 = new RotationEvent(this.KdV, this.targetPitch);
         EventBus.post(var1);
         this.KdV = var1.MGzP2;
         this.targetPitch = var1.pitch;
         if (this.KdV != null || this.targetPitch != null) {
            float[] var2 = RotationUtils.NSsr(
               this.KdV == null ? this.mc.thePlayer.rotationYaw : this.KdV,
               this.targetPitch == null ? this.mc.thePlayer.rotationPitch : this.targetPitch,
               RotationUtils.lastSentRotation[0],
               RotationUtils.lastSentRotation[1]
            );
            this.KdV = var2[0];
            this.targetPitch = var2[1];
            if (this.KdV != this.mc.thePlayer.rotationYaw && (var1.MGzP2 == null || !var1.MGzP2.isNaN())) {
               this.Mpd16 = true;
            }

            if (this.targetPitch != this.mc.thePlayer.rotationPitch && (var1.pitch == null || !var1.pitch.isNaN())) {
               this.Mpd16 = true;
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPreUpdate(PreUpdateEvent var1) {
      this.updateTargetRotation();
   }

   @Subscribe
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
      if (this.Mpd16) {
         if (this.KdV != null && !this.KdV.isNaN()) {
            var1.setYaw(this.KdV);
         }

         if (this.targetPitch != null && !this.targetPitch.isNaN()) {
            var1.setPitch(this.targetPitch);
         }
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (this.Mpd16 && this.KdV != null && this.mc.thePlayer != null) {
         float var2 = RotationUtils.lastSentRotation[0];
         float var3 = MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw);
         float var4 = RotationInterpolator.tfpXj(var3, var2);
         this.mc.thePlayer.rotationYaw = var4;
         this.mc.thePlayer.prevRotationYaw = var4;
      }

      this.KdV = null;
      this.targetPitch = null;
      this.Mpd16 = false;
      this.suppressMovementCorrection = false;
      this.rotationEventPosted = false;
      this.renderRotationApplied = false;
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.Mpd16 && this.mc.thePlayer != null) {
         this.mc.thePlayer.prevRenderArmYaw = this.mc.thePlayer.rotationYaw;
         this.mc.thePlayer.renderArmYaw = this.mc.thePlayer.rotationYaw;
      }
   }

   public boolean abxpJn() {
      return this.Mpd16 && (this.KdV != null || this.targetPitch != null);
   }

   public void mNwrQ(Entity var1, float var2, float var3, boolean var4) {
      this.renderRotationState.applyRotation(var1, var2, var3, var4);
   }

   public void restoreEntityRotation(Entity var1) {
      this.renderRotationState.oIpxn(var1);
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onMoveStateUpdate(MoveStateUpdateEvent var1) {
      if (this.sv52() && !this.suppressMovementCorrection) {
         float[] var2 = MovementFix.fixMovementInput(
            this.mc.thePlayer.rotationYaw,
            this.KdV,
            this.mc.thePlayer.movementInput.moveForward,
            this.mc.thePlayer.movementInput.moveStrafe,
            this.mc.thePlayer.movementInput.sneak
         );
         this.mc.thePlayer.movementInput.moveForward = var2[0];
         this.mc.thePlayer.movementInput.moveStrafe = var2[1];
      }
   }

   @Subscribe
   public void onMoveFlying(MoveFlyingEvent var1) {
      if (this.sv52()) {
         var1.setYaw(this.KdV);
      }
   }

   @Subscribe
   public void onJump(JumpEvent var1) {
      if (this.sv52()) {
         var1.setYaw(this.KdV);
      }
   }

   public boolean sv52() {
      return this.Mpd16;
   }

   public static double yHeu(float var0, double var1, double var3) {
      return MovementFix.getMovementYaw(var0, var1, var3);
   }

   public static RotationHandler getInstance() {
      return rotationHandler;
   }

   public void ywbo(float var1, float var2) {
      this.KdV = var1;
      this.targetPitch = var2;
      this.Mpd16 = true;
   }

   public void setTargetYaw(float var1) {
      this.KdV = var1;
      this.Mpd16 = true;
   }

   public void setTargetPitch(float var1) {
      this.targetPitch = var1;
      this.Mpd16 = true;
   }

   public void NkF5() {
      this.KdV = null;
      this.targetPitch = null;
      this.Mpd16 = false;
      this.suppressMovementCorrection = false;
      this.renderRotationApplied = false;
   }

   public void ljYma8(boolean var1) {
      this.suppressMovementCorrection = var1;
   }

   public Float getTargetYaw() {
      return this.KdV;
   }

   public Float cvZx() {
      return this.targetPitch;
   }
}
