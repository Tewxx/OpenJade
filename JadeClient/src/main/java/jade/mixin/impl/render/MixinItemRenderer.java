// Jade recovery: recovered class name: MixinItemRenderer; mixin target: net.minecraft.client.renderer.ItemRenderer; original class: jade.mixin.impl.render.M97eb9f1b5786525cc7d0a2a4b641e988
package jade.mixin.impl.render;

import jade.client.hook.ItemUseHelper;
import jade.client.hook.ItemRenderSession;
import jade.client.module.player.Freecam;
import jade.mixin.impl.accessor.IAccessorEntityPlayer;
import jade.mixin.interfaces.IMixinItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer implements IMixinItemRenderer {
   private boolean restoreFreecamPlayerRotation;
   private final float[] originalFreecamPlayerRotation = new float[8];
   private final ItemRenderSession jade$itemSession = new ItemRenderSession();
   @Shadow
   private ItemStack field_78453_b;
   public boolean cancelUpdate = false;
   public boolean cancelReset = false;
   private boolean renderItemInUse;
   private boolean restoreForcedUseCount;
   private int originalForcedUseCount;
   private boolean wroteForcedUseCount;
   @Shadow
   private float field_78454_c;
   @Shadow
   private float field_78451_d;

   @Inject(method = "renderItemInFirstPerson", at = @At("HEAD"))
   private void modifyRenderItemPre(float p_renderItemInFirstPerson_1_, CallbackInfo info) {
      this.field_78453_b = this.jade$itemSession.beginRenderSession(this.field_78453_b);
      ItemUseHelper.Ziacy();
      AbstractClientPlayer player = Minecraft.getMinecraft().thePlayer;
      if (Freecam.cameraEntity != null && player instanceof EntityPlayerSP) {
         EntityPlayerSP localPlayer = (EntityPlayerSP)player;
         this.originalFreecamPlayerRotation[0] = localPlayer.rotationYaw;
         this.originalFreecamPlayerRotation[1] = localPlayer.prevRotationYaw;
         this.originalFreecamPlayerRotation[2] = localPlayer.rotationPitch;
         this.originalFreecamPlayerRotation[3] = localPlayer.prevRotationPitch;
         this.originalFreecamPlayerRotation[4] = localPlayer.renderArmYaw;
         this.originalFreecamPlayerRotation[5] = localPlayer.prevRenderArmYaw;
         this.originalFreecamPlayerRotation[6] = localPlayer.renderArmPitch;
         this.originalFreecamPlayerRotation[7] = localPlayer.prevRenderArmPitch;
         localPlayer.rotationYaw = localPlayer.renderArmYaw = Freecam.cameraEntity.rotationYaw;
         localPlayer.prevRotationYaw = localPlayer.prevRenderArmYaw = Freecam.cameraEntity.prevRotationYaw;
         localPlayer.rotationPitch = localPlayer.renderArmPitch = Freecam.cameraEntity.rotationPitch;
         localPlayer.prevRotationPitch = localPlayer.prevRenderArmPitch = Freecam.cameraEntity.prevRotationPitch;
         this.restoreFreecamPlayerRotation = true;
      }

      if (ItemUseHelper.Ujb4(player, this.field_78453_b)) {
         IAccessorEntityPlayer accessor = (IAccessorEntityPlayer)player;
         this.originalForcedUseCount = accessor.getItemInUseCountField();
         this.restoreForcedUseCount = true;
         this.wroteForcedUseCount = true;
         accessor.setItemInUseCount(Math.max(1, this.originalForcedUseCount));
      }
   }

   @Inject(method = "renderItemInFirstPerson", at = @At("RETURN"))
   private void modifyRenderItemPost(float p_renderItemInFirstPerson_1_, CallbackInfo info) {
      if (this.restoreFreecamPlayerRotation) {
         EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
         player.rotationYaw = this.originalFreecamPlayerRotation[0];
         player.prevRotationYaw = this.originalFreecamPlayerRotation[1];
         player.rotationPitch = this.originalFreecamPlayerRotation[2];
         player.prevRotationPitch = this.originalFreecamPlayerRotation[3];
         player.renderArmYaw = this.originalFreecamPlayerRotation[4];
         player.prevRenderArmYaw = this.originalFreecamPlayerRotation[5];
         player.renderArmPitch = this.originalFreecamPlayerRotation[6];
         player.prevRenderArmPitch = this.originalFreecamPlayerRotation[7];
         this.restoreFreecamPlayerRotation = false;
      }

      if (this.restoreForcedUseCount) {
         ((IAccessorEntityPlayer)Minecraft.getMinecraft().thePlayer).setItemInUseCount(this.renderItemInUse ? Math.max(1, this.originalForcedUseCount) : 0);
         this.restoreForcedUseCount = false;
         this.wroteForcedUseCount = false;
      }

      this.field_78453_b = this.jade$itemSession.endRenderSession();
      ItemUseHelper.Npqilu9();
   }

   @Redirect(
      method = "renderItemInFirstPerson",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseCount()I"),
      require = 0
   )
   private int redirectFirstPersonUseCount(AbstractClientPlayer player) {
      int actualCount = player.getItemInUseCount();
      if (actualCount > 0) {
         return actualCount;
      } else {
         return ItemUseHelper.Ujb4(player, this.field_78453_b) ? 1 : actualCount;
      }
   }

   @Redirect(
      method = "renderItemInFirstPerson",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUsingItem()Z"),
      require = 0
   )
   private boolean redirectFirstPersonIsUsingItem(AbstractClientPlayer player) {
      return player.isUsingItem() || ItemUseHelper.Ujb4(player, this.field_78453_b);
   }

   @Inject(method = "updateEquippedItem", at = @At("HEAD"), cancellable = true)
   private void onUpdateEquippedItem(CallbackInfo ci) {
      if (this.cancelUpdate) {
         this.cancelUpdate = false;
         this.jade$finishEquipTransition(ci);
      }
   }

   @Inject(method = "resetEquippedProgress", at = @At("HEAD"), cancellable = true)
   public void injectResetEquippedProgress(CallbackInfo ci) {
      this.jade$consumeResetRequest(ci);
   }

   @Inject(method = "resetEquippedProgress2", at = @At("HEAD"), cancellable = true)
   public void injectResetEquippedProgress2(CallbackInfo ci) {
      this.jade$consumeResetRequest(ci);
   }

   private void jade$consumeResetRequest(CallbackInfo ci) {
      if (this.cancelReset) {
         this.cancelReset = false;
         this.jade$finishEquipTransition(ci);
      }
   }

   private void jade$finishEquipTransition(CallbackInfo ci) {
      this.field_78454_c = 1.0F;
      this.field_78451_d = 1.0F;
      ci.cancel();
   }

   @Override
   public void setCancelUpdate(boolean cancel) {
      this.cancelUpdate = cancel;
   }

   @Override
   public void setCancelReset(boolean reset) {
      this.cancelReset = reset;
   }

   @Override
   public boolean isRenderItemInUse() {
      return this.renderItemInUse;
   }

   @Override
   public void setRenderItemInUse(boolean renderItemInUse) {
      this.renderItemInUse = renderItemInUse;
      if (!renderItemInUse && this.wroteForcedUseCount) {
         ((IAccessorEntityPlayer)Minecraft.getMinecraft().thePlayer).setItemInUseCount(0);
         this.restoreForcedUseCount = false;
         this.wroteForcedUseCount = false;
      }
   }
}
