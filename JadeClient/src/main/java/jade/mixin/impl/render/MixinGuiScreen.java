// Jade recovery: recovered class name: MixinGuiScreen; mixin target: net.minecraft.client.gui.GuiScreen; original class: jade.mixin.impl.render.M6f1d5ae84d5d34e3ecb7db01ed20600d
package jade.mixin.impl.render;

import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.common.IrcChatHandler;
import jade.client.event.GuiKeyboardInputEvent;
import jade.client.hook.ProxyAltsButton;
import jade.client.module.minigames.AutoChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
   @Shadow
   public Minecraft field_146297_k;
   @Shadow
   public int field_146294_l;
   @Shadow
   public int field_146295_m;

   @Inject(method = "drawScreen", at = @At("RETURN"))
   private void jade$drawAltButton(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      ProxyAltsButton.WXs6(this.field_146297_k);
   }

   @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
   private void messageSend(String msg, boolean addToChat, CallbackInfo callbackInfo) {
      if (addToChat && IrcChatHandler.handleChatInput(msg)) {
         this.field_146297_k.ingameGUI.getChatGUI().addToSentMessages(msg);
         callbackInfo.cancel();
      } else {
         if (addToChat && Jade.commandManager != null && Jade.commandManager.DCXw(msg)) {
            this.field_146297_k.ingameGUI.getChatGUI().addToSentMessages(msg);
            callbackInfo.cancel();
         }
      }
   }

   @Inject(method = "handleKeyboardInput", at = @At("HEAD"), cancellable = true)
   private void injectHandleKeyboardInput(CallbackInfo callbackInfo) {
      GuiKeyboardInputEvent event = new GuiKeyboardInputEvent(Keyboard.getEventCharacter(), Keyboard.getEventKey());
      EventBus.post(event);
      if (event.isCanceled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "handleMouseInput", at = @At("HEAD"))
   private void jade$handleAutoChestScroll(CallbackInfo callbackInfo) {
      int wheelDelta = Mouse.getEventDWheel();
      if (wheelDelta != 0 && Jade.getModuleManager().getModule(AutoChest.class) != null && Jade.getModuleManager().getModule(AutoChest.class).isEnabled()) {
         Jade.getModuleManager().getModule(AutoChest.class).handleScrollTransfer(wheelDelta);
      }
   }
}
