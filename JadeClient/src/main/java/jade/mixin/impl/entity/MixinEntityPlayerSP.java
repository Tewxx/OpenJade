// Jade recovery: recovered class name: MixinEntityPlayerSP; mixin target: net.minecraft.client.entity.EntityPlayerSP; original class: jade.mixin.impl.entity.M198ca6b0101dd4db44348f9d806e209a
package jade.mixin.impl.entity;

import com.mojang.authlib.GameProfile;
import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventBus;
import jade.client.common.RotationUtils;
import jade.client.event.PostUpdateEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.module.combat.KeepSprint;
import jade.client.module.movement.Timer;
import jade.client.module.player.Freecam;
import jade.client.module.player.InventoryManager;
import jade.mixin.feature.player.HorseJumpCharge$1;
import jade.mixin.feature.player.HorseJumpCharge;
import jade.mixin.feature.player.PlayerFlightUpdate$1;
import jade.mixin.feature.player.PlayerFlightUpdate;
import jade.mixin.feature.player.PlayerPortalUpdate$1;
import jade.mixin.feature.player.PlayerPortalUpdate;
import jade.mixin.feature.player.PlayerSprintUpdate$1;
import jade.mixin.feature.player.PlayerSprintUpdate;
import jade.mixin.feature.player.PlayerWalkingUpdate$0;
import jade.mixin.feature.player.PlayerWalkingUpdate;
import jade.mixin.impl.accessor.IAccessorEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
   public MixinEntityPlayerSP(World p_i45074_1_, GameProfile p_i45074_2_) {
      super(p_i45074_1_, p_i45074_2_);
   }

   @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
   private void onUpdatePre(CallbackInfo c) {
      if (!ClientUtils.isInNestedUpdate() && Timer.dwrJu()) {
         this.syncPrevRenderStateToCurrent();
         c.cancel();
      } else if (!ClientUtils.isInNestedUpdate()) {
         if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            RotationUtils.previousPitch = RotationUtils.pN0;
            RotationUtils.IuZ = RotationUtils.outgoingYaw;
            EventBus.post(new PreUpdateEvent());
         }
      }
   }

   @Inject(method = "onUpdate", at = @At("RETURN"))
   private void onUpdatePost(CallbackInfo c) {
      if (!ClientUtils.isInNestedUpdate()) {
         if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            EventBus.post(new PostUpdateEvent());
         }

         int extraUpdates = Timer.getExtraUpdateCount();
         if (extraUpdates > 0) {
            double[] interpolationState = this.captureInterpolationState();

            for (int i = 0; i < extraUpdates; i++) {
               ClientUtils.beginNestedUpdate();

               try {
                  this.onUpdate();
               } finally {
                  ClientUtils.EnMd();
               }
            }

            this.restoreInterpolationState(interpolationState);
         }
      }
   }

   @Redirect(method = "updateEntityActionState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
   private boolean jade$updateActionStateForFreecam(EntityPlayerSP player) {
      return this.state().invokeIsCurrentViewEntity() || Freecam.pqos();
   }

   @Inject(method = "closeScreen", at = @At("HEAD"))
   private void jade$beforeCloseScreen(CallbackInfo callbackInfo) {
      if (Jade.getModuleManager().getModule(InventoryManager.class) != null) {
         Jade.getModuleManager().getModule(InventoryManager.class).recoverCursorOnScreenClose("EntityPlayerSP.closeScreen");
      }
   }

   @Overwrite
   public void func_175161_p() {
      EntityPlayerSP player = this.player();
      IAccessorEntityPlayerSP state = this.state();
      PlayerWalkingUpdate$0 next = PlayerWalkingUpdate.transmit(
         player,
         player.sendQueue,
         state.invokeIsCurrentViewEntity() || Freecam.pqos(),
         new PlayerWalkingUpdate$0(
            state.getServerSprintState(),
            state.getServerSneakState(),
            state.getLastReportedPosX(),
            state.getLastReportedPosY(),
            state.getLastReportedPosZ(),
            state.getLastReportedYaw(),
            state.getLastReportedPitch(),
            state.getPositionUpdateTicks()
         )
      );
      state.setServerSprintState(next.sprinting);
      state.setServerSneakState(next.sneaking);
      state.setLastReportedPosX(next.x);
      state.setLastReportedPosY(next.y);
      state.setLastReportedPosZ(next.z);
      state.setLastReportedYaw(next.yaw);
      state.setLastReportedPitch(next.pitch);
      state.setPositionUpdateTicks(next.idleTicks);
   }

   @Overwrite
   public void func_70636_d() {
      EntityPlayerSP player = this.player();
      IAccessorEntityPlayerSP state = this.state();
      Minecraft client = Minecraft.getMinecraft();
      if (player.sprintingTicksLeft > 0) {
         player.sprintingTicksLeft--;
         if (player.sprintingTicksLeft == 0) {
            player.setSprinting(false);
         }
      }

      int sprintToggle = state.getSprintToggleTimer();
      if (sprintToggle > 0) {
         state.setSprintToggleTimer(--sprintToggle);
      }

      player.prevTimeInPortal = player.timeInPortal;
      PlayerPortalUpdate$1 portal = PlayerPortalUpdate.advance(player, client, player.timeInPortal, this.inPortal);
      player.timeInPortal = portal.amount;
      this.inPortal = portal.entered;
      if (this.timeUntilPortal > 0) {
         this.timeUntilPortal--;
      }

      PlayerSprintUpdate$1 movement = PlayerSprintUpdate.begin(player);
      this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
      this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
      this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
      this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
      state.setSprintToggleTimer(PlayerSprintUpdate.apply(player, client, movement, state.getSprintToggleTimer()));
      PlayerFlightUpdate$1 flight = PlayerFlightUpdate.beforeLivingUpdate(
         player, client, movement.jumpBefore, this.flyToggleTimer, state.invokeIsCurrentViewEntity()
      );
      this.flyToggleTimer = flight.toggleTimer;
      if (flight.abilitiesChanged) {
         player.sendPlayerAbilities();
      }

      HorseJumpCharge$1 horse = HorseJumpCharge.advance(
         player.isRidingHorse(), movement.jumpBefore, player.movementInput.jump, state.getHorseJumpPowerCounter(), state.getHorseJumpPower()
      );
      state.setHorseJumpPowerCounter(horse.counter);
      state.setHorseJumpPower(horse.power);
      if (horse.sendJump) {
         state.invokeSendHorseJump();
      }

      KeepSprint keepSprint = Jade.getModuleManager().getModule(KeepSprint.class);
      if (keepSprint != null && keepSprint.isEnabled()) {
         keepSprint.applyPredictedSprintState(player);
      }

      super.onLivingUpdate();
      if (PlayerFlightUpdate.landed(player, client)) {
         player.sendPlayerAbilities();
      }
   }

   private double[] captureInterpolationState() {
      return new double[]{
         this.prevPosX,
         this.prevPosY,
         this.prevPosZ,
         this.lastTickPosX,
         this.lastTickPosY,
         this.lastTickPosZ,
         this.prevRotationYaw,
         this.prevRotationPitch,
         this.prevRotationYawHead,
         this.prevRenderYawOffset,
         this.prevCameraYaw,
         this.prevCameraPitch,
         this.prevDistanceWalkedModified,
         this.prevLimbSwingAmount,
         this.prevSwingProgress,
         this.prevChasingPosX,
         this.prevChasingPosY,
         this.prevChasingPosZ
      };
   }

   private void restoreInterpolationState(double[] values) {
      this.prevPosX = values[0];
      this.prevPosY = values[1];
      this.prevPosZ = values[2];
      this.lastTickPosX = values[3];
      this.lastTickPosY = values[4];
      this.lastTickPosZ = values[5];
      this.prevRotationYaw = (float)values[6];
      this.prevRotationPitch = (float)values[7];
      this.prevRotationYawHead = (float)values[8];
      this.prevRenderYawOffset = (float)values[9];
      this.prevCameraYaw = (float)values[10];
      this.prevCameraPitch = (float)values[11];
      this.prevDistanceWalkedModified = (float)values[12];
      this.prevLimbSwingAmount = (float)values[13];
      this.prevSwingProgress = (float)values[14];
      this.prevChasingPosX = values[15];
      this.prevChasingPosY = values[16];
      this.prevChasingPosZ = values[17];
   }

   private void syncPrevRenderStateToCurrent() {
      this.lastTickPosX = this.prevPosX = this.posX;
      this.lastTickPosY = this.prevPosY = this.posY;
      this.lastTickPosZ = this.prevPosZ = this.posZ;
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationYawHead = this.rotationYawHead;
      this.prevRenderYawOffset = this.renderYawOffset;
      this.prevCameraYaw = this.cameraYaw;
      this.prevCameraPitch = this.cameraPitch;
      this.prevDistanceWalkedModified = this.distanceWalkedModified;
      this.prevLimbSwingAmount = this.limbSwingAmount;
      this.prevSwingProgress = this.swingProgress;
      this.prevChasingPosX = this.chasingPosX;
      this.prevChasingPosY = this.chasingPosY;
      this.prevChasingPosZ = this.chasingPosZ;
      this.player().prevTimeInPortal = this.player().timeInPortal;
   }

   private EntityPlayerSP player() {
      return (EntityPlayerSP)(Object)this;
   }

   private IAccessorEntityPlayerSP state() {
      return (IAccessorEntityPlayerSP)(Object)this;
   }
}
