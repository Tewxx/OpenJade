// Jade recovery: recovered class name: MixinGuiContainer; mixin target: net.minecraft.client.gui.inventory.GuiContainer; original class: jade.mixin.impl.render.M531a5ff472d0dd625ac4f9fedc073f06
package jade.mixin.impl.render;

import jade.client.Jade;
import jade.client.module.combat.AutoClicker;
import jade.client.module.minigames.BedwarsUtils;
import jade.client.module.minigames.Opsec;
import jade.client.module.player.InventoryManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class MixinGuiContainer {
   @Shadow
   private Slot field_147006_u;
   @Unique
   private Slot jade$currentDrawSlot;

   @Inject(method = "drawSlot", at = @At("HEAD"))
   private void jade$captureDrawSlot(Slot slot, CallbackInfo callbackInfo) {
      this.jade$currentDrawSlot = slot;
   }

   @Inject(method = "drawSlot", at = @At("RETURN"))
   private void jade$releaseDrawSlot(Slot slot, CallbackInfo callbackInfo) {
      this.jade$currentDrawSlot = null;
   }

   @ModifyArg(
      method = "drawSlot",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemAndEffectIntoGUI(Lnet/minecraft/item/ItemStack;II)V"),
      index = 0
   )
   private ItemStack jade$virtualQuickBuyIcon(ItemStack original) {
      return virtualStack(this.jade$currentDrawSlot, original);
   }

   @ModifyArg(
      method = "drawSlot",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemOverlayIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"
      ),
      index = 1
   )
   private ItemStack jade$virtualQuickBuyOverlay(ItemStack original) {
      return virtualStack(this.jade$currentDrawSlot, original);
   }

   @ModifyArg(
      method = "drawScreen",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;renderToolTip(Lnet/minecraft/item/ItemStack;II)V"),
      index = 0
   )
   private ItemStack jade$virtualQuickBuyTooltip(ItemStack original) {
      return virtualStack(this.field_147006_u, original);
   }

   @Inject(method = "drawScreen", at = @At("RETURN"))
   private void jade$drawManagedInventoryProgress(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
      if (Jade.getModuleManager().getModule(AutoClicker.class) != null) {
         Jade.getModuleManager().getModule(AutoClicker.class).setInventoryTarget((GuiContainer)(Object)this, this.field_147006_u);
      }

      if (Jade.getModuleManager().getModule(Opsec.class) != null) {
         Jade.getModuleManager().getModule(Opsec.class).drawQuickBuySetupText();
      }
   }

   @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
   private void jade$handleManagedInventoryEscape(char typedChar, int keyCode, CallbackInfo callbackInfo) {
      if (Jade.getModuleManager().getModule(Opsec.class) != null && Jade.getModuleManager().getModule(Opsec.class).RwheD27(keyCode)) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
   private void jade$cancelManagedInventoryMouseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo callbackInfo) {
      if (shouldCancelManualInventoryInput()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "mouseClickMove", at = @At("HEAD"), cancellable = true)
   private void jade$cancelManagedInventoryMouseDrag(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo callbackInfo) {
      if (shouldCancelManualInventoryInput()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
   private void jade$cancelManagedInventoryMouseRelease(int mouseX, int mouseY, int state, CallbackInfo callbackInfo) {
      if (shouldCancelManualInventoryInput()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "handleMouseClick", at = @At("HEAD"), cancellable = true)
   private void jade$cancelManagedInventoryWindowClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo callbackInfo) {
      if (shouldCancelManualInventoryInput()) {
         callbackInfo.cancel();
      } else if (Jade.getModuleManager().getModule(Opsec.class) != null && Jade.getModuleManager().getModule(Opsec.class).axClaj(slotIn, slotId, clickedButton, clickType)) {
         callbackInfo.cancel();
      } else {
         if (Jade.getModuleManager().getModule(BedwarsUtils.class) != null && Jade.getModuleManager().getModule(BedwarsUtils.class).handleQuickShopSlotClick(slotIn, slotId, clickedButton, clickType)) {
            callbackInfo.cancel();
         }
      }
   }

   private static boolean shouldCancelManualInventoryInput() {
      return Jade.getModuleManager().getModule(InventoryManager.class) != null && Jade.getModuleManager().getModule(InventoryManager.class).isExecutingPlan()
         || Jade.getModuleManager().getModule(Opsec.class) != null && Jade.getModuleManager().getModule(Opsec.class).isQuickBuySetupActive();
   }

   @Unique
   private static ItemStack virtualStack(Slot slot, ItemStack original) {
      return Jade.getModuleManager().getModule(Opsec.class) == null ? original : Jade.getModuleManager().getModule(Opsec.class).Bxn6(slot, original);
   }
}
