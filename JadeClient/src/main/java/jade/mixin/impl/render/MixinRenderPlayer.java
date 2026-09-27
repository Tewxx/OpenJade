// Jade recovery: recovered class name: MixinRenderPlayer; mixin target: net.minecraft.client.renderer.entity.RenderPlayer; original class: jade.mixin.impl.render.M774ca7d6054aae22eeb2a199cb5cd77e
package jade.mixin.impl.render;

import jade.client.hook.RenderPlayerHook;
import jade.client.hook.HeldItemDisplayHelper;
import jade.client.module.player.Freecam;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {
   @Redirect(
      method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z")
   )
   private boolean jade$renderLocalPlayerDuringFreecam(AbstractClientPlayer player) {
      return player.isUser() && !Freecam.isHiddenLocalPlayer(player);
   }

   @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("HEAD"))
   private void onDoRenderHead(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
      RenderPlayerHook.BiN15(entity, x, y, z, partialTicks);
   }

   @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("RETURN"))
   private void onDoRenderReturn(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
      RenderPlayerHook.kkihzAr((RenderPlayer)(Object)this, entity, x, y, z, partialTicks);
   }

   @Redirect(
      method = "setModelVisibilities(Lnet/minecraft/client/entity/AbstractClientPlayer;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;")
   )
   private ItemStack redirectGetCurrentItem(InventoryPlayer inventory) {
      return HeldItemDisplayHelper.MAUYF(inventory);
   }

   @Redirect(
      method = "setModelVisibilities(Lnet/minecraft/client/entity/AbstractClientPlayer;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseCount()I")
   )
   private int redirectGetItemInUseCount(AbstractClientPlayer clientPlayer) {
      return HeldItemDisplayHelper.resolveItemInUseCount(clientPlayer);
   }
}
