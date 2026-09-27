// Jade recovery: recovered class name: MixinPlayerControllerMP; mixin target: net.minecraft.client.multiplayer.PlayerControllerMP; original class: jade.mixin.impl.client.M6dbe2961b4f4be8b769d273e96d24860
package jade.mixin.impl.client;

import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.event.AttackEntityEvent;
import jade.client.event.UseItemEvent;
import jade.client.module.combat.KeepSprint;
import jade.client.module.minigames.NukeDefend;
import jade.client.module.player.BedNuker;
import jade.client.module.player.BridgeNuker;
import jade.client.module.player.FastBreak;
import jade.client.module.player.Freecam;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
   @Shadow
   private int field_78781_i;
   @Unique
   private float jade$preDamageBlockProgress;

   @Unique
   private float jade$getActiveMiningIncrementMultiplier() {
      BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
      if (bedNuker != null && bedNuker.isNuking()) {
         return bedNuker.TnokwE();
      } else {
         BridgeNuker bridgeNuker = Jade.getModuleManager().getModule(BridgeNuker.class);
         return bridgeNuker != null && bridgeNuker.PfjH() ? bridgeNuker.getBreakSpeedMultiplier() : 1.0F;
      }
   }

   @Inject(
      method = "sendUseItem(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   public void injectUseItemEvent(EntityPlayer p_sendUseItem_1_, World p_sendUseItem_2_, ItemStack p_sendUseItem_3_, CallbackInfoReturnable<Boolean> ci) {
      if (Freecam.pqos()) {
         ci.setReturnValue(false);
      } else {
         UseItemEvent event = new UseItemEvent(p_sendUseItem_3_);
         EventBus.post(event);
         if (event.isCanceled()) {
            ci.setReturnValue(false);
         }
      }
   }

   @Inject(method = "onPlayerRightClick", at = @At("HEAD"), cancellable = true)
   private void jade$preventSecondNukeDefendPlacement(
      EntityPlayer player, World world, ItemStack stack, BlockPos pos, EnumFacing face, Vec3 hitVec, CallbackInfoReturnable<Boolean> cir
   ) {
      NukeDefend nukeDefend = Jade.getModuleManager().getModule(NukeDefend.class);
      if (nukeDefend != null && nukeDefend.shouldBlockDuplicatePlacement()) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
   private void injectAttackEntity(EntityPlayer playerIn, Entity targetEntity, CallbackInfo callbackInfo) {
      if (Freecam.pqos()) {
         callbackInfo.cancel();
      } else {
         KeepSprint keepSprint = Jade.getModuleManager().getModule(KeepSprint.class);
         if (keepSprint != null && keepSprint.isEnabled() && keepSprint.shouldCancelAttack(playerIn, targetEntity)) {
            callbackInfo.cancel();
         } else {
            AttackEntityEvent event = new AttackEntityEvent(targetEntity, playerIn, true);
            EventBus.post(event);
            if (event.isCanceled()) {
               callbackInfo.cancel();
            }
         }
      }
   }

   @Inject(method = {"clickBlock", "onPlayerDamageBlock"}, at = @At("HEAD"), cancellable = true)
   private void jade$cancelFreecamBlockInteraction(BlockPos pos, EnumFacing facing, CallbackInfoReturnable<Boolean> cir) {
      if (Freecam.pqos()) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"))
   private void jade$captureDamageProgress(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
      this.jade$preDamageBlockProgress = ((IAccessorPlayerControllerMP)(Object)this).getCurBlockDamageMP();
   }

   @Inject(method = "onPlayerDamageBlock", at = @At("RETURN"))
   private void jade$scaleDamageProgress(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
      IAccessorPlayerControllerMP accessor = (IAccessorPlayerControllerMP)(Object)this;
      float current = accessor.getCurBlockDamageMP();
      float delta = current - this.jade$preDamageBlockProgress;
      if (!(delta <= 0.0F)) {
         float multiplier = this.jade$getActiveMiningIncrementMultiplier();
         if (!(multiplier <= 1.0F)) {
            float boostedProgress = Math.min(1.0F, this.jade$preDamageBlockProgress + delta * multiplier);
            accessor.setCurBlockDamageMP(boostedProgress);
         }
      }
   }

   @Unique
   private void jade$fastMineApplyBreakDelaySlider() {
      BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
      if (bedNuker != null && bedNuker.isNuking()) {
         int delay = bedNuker.getBlockHitDelay();
         if (delay < 5) {
            this.field_78781_i = delay;
         }
      } else {
         BridgeNuker bridgeNuker = Jade.getModuleManager().getModule(BridgeNuker.class);
         if (bridgeNuker != null && bridgeNuker.PfjH()) {
            int delay = bridgeNuker.getBreakDelayTicks();
            if (delay < 5) {
               this.field_78781_i = delay;
            }
         } else {
            FastBreak fm = Jade.getModuleManager().getModule(FastBreak.class);
            if (fm != null) {
               int o = fm.getBlockHitDelayOverride();
               if (o >= 0) {
                  this.field_78781_i = o;
               }
            }
         }
      }
   }

   @Inject(
      method = "clickBlock",
      at = @At(
         value = "FIELD",
         opcode = 181,
         target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I",
         ordinal = 0,
         shift = At.Shift.AFTER
      )
   )
   private void jade$fastMineAfterClickBlockSetDelay(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
      this.jade$fastMineApplyBreakDelaySlider();
   }

   @Inject(
      method = "onPlayerDamageBlock",
      at = @At(
         value = "FIELD",
         opcode = 181,
         target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I",
         ordinal = 1,
         shift = At.Shift.AFTER
      )
   )
   private void jade$fastMineAfterCreativeMiningSetDelay(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
      this.jade$fastMineApplyBreakDelaySlider();
   }

   @Inject(
      method = "onPlayerDamageBlock",
      at = @At(
         value = "FIELD",
         opcode = 181,
         target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I",
         ordinal = 2,
         shift = At.Shift.AFTER
      )
   )
   private void jade$fastMineAfterBreakBlockSetDelay(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
      this.jade$fastMineApplyBreakDelaySlider();
   }
}
