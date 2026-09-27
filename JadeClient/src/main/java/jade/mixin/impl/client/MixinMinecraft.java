// Jade recovery: recovered class name: MixinMinecraft; mixin target: net.minecraft.client.Minecraft; original class: jade.mixin.impl.client.Mdd2154c518475b3ef8ea0cd60b944ea9
package jade.mixin.impl.client;

import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.common.EventPhase;
import jade.client.common.RotationHandler;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.ClickMouseEvent;
import jade.client.event.GameLoopEvent;
import jade.client.event.GuiDisplayEvent;
import jade.client.event.GuiOpenEvent;
import jade.client.event.LeftClickEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.MouseEvent;
import jade.client.event.MouseOverEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RightClickDelayEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.TickEndEvent;
import jade.client.event.TickEvent;
import jade.client.event.TickStartEvent;
import jade.client.hook.RenderTickMessagePump;
import jade.client.module.player.BedNuker;
import jade.client.module.player.BridgeNuker;
import jade.client.module.player.FastBreak;
import jade.client.module.render.FreeLook;
import jade.deps.loader107.LoaderStartupProgram;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import net.jade.dev.agent.transformer.JadeAgentHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
   @Inject(method = "startGame", at = @At("RETURN"))
   private void jade$initializeForgeCore(CallbackInfo ci) {
      JadeAgentHooks.markMixinEventBridgeActive();
      LoaderStartupProgram.runStartupProgram();
   }

   @Inject(method = "runTick", at = @At("HEAD"))
   public void onRunTickStart(CallbackInfo ci) {
      JadeAgentHooks.markMixinEventBridgeActive();
      EventBus.post(new TickStartEvent());
      EventBus.post(new TickEndEvent(EventPhase.START));
   }

   @Inject(method = "runTick", at = @At("RETURN"))
   public void onRunTickEnd(CallbackInfo ci) {
      EventBus.post(new TickEndEvent(EventPhase.END));
   }

   @Inject(method = "runGameLoop", at = @At("HEAD"))
   public void onRunGameLoopStart(CallbackInfo ci) {
      Jade.refreshTimerModuleKeybind();
      Jade.openPendingScreen();
      RenderTickMessagePump.Rlzak();
      EventBus.post(new GameLoopEvent());
      EventBus.post(new RenderTickEvent(EventPhase.START, ((IAccessorMinecraft)(Object)this).getTimer().renderPartialTicks));
   }

   @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;getMouseOver(F)V", shift = At.Shift.BEFORE))
   public void onBeforeGetMouseOver(CallbackInfo ci) {
      RotationHandler.getInstance().updateTargetRotation();
   }

   @Inject(
      method = "runTick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;updateController()V", shift = At.Shift.AFTER)
   )
   public void onAfterPlayerControllerUpdate(CallbackInfo ci) {
      BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
      if (bedNuker != null) {
         bedNuker.processPendingPlacement();
      }
   }

   @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;getMouseOver(F)V", shift = At.Shift.AFTER))
   public void onRunTickMouseOver(CallbackInfo ci) {
      EventBus.post(new MouseOverEvent());
   }

   @Inject(
      method = "runTick",
      at = @At(
         value = "FIELD",
         opcode = 180,
         target = "Lnet/minecraft/client/settings/GameSettings;chatVisibility:Lnet/minecraft/entity/player/EntityPlayer$EnumChatVisibility;"
      )
   )
   private void injectBeforeChatVisibility(CallbackInfo ci) {
      EventBus.post(new PrePlayerInteractEvent());
   }

   @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2))
   private void onRunTick(CallbackInfo ci) {
      EventBus.post(new TickEvent());
   }

   @Inject(method = "clickMouse", at = @At("HEAD"), cancellable = true)
   public void injectClickMouse(CallbackInfo ci) {
      Minecraft mc = (Minecraft)(Object)this;
      MovingObjectPosition mop = mc.objectMouseOver;
      MouseEvent mouseEvent = new MouseEvent(0, true, Mouse.getX(), Mouse.getY(), 0, 0, 0);
      EventBus.post(mouseEvent);
      if (mouseEvent.isCanceled()) {
         ci.cancel();
      } else {
         ClickMouseEvent preAttack = new ClickMouseEvent(mop);
         EventBus.post(preAttack);
         if (preAttack.isCanceled()) {
            ci.cancel();
         } else {
            EventBus.post(new LeftClickEvent());
         }
      }
   }

   @Inject(method = "rightClickMouse", at = @At("HEAD"), cancellable = true)
   public void injectRightClickMouse(CallbackInfo ci) {
      if (Mouse.isButtonDown(1)) {
         MouseEvent mouseEvent = new MouseEvent(1, true, Mouse.getX(), Mouse.getY(), 0, 0, 0);
         EventBus.post(mouseEvent);
         if (mouseEvent.isCanceled()) {
            ci.cancel();
            return;
         }
      }

      RightClickEvent event = new RightClickEvent();
      EventBus.post(event);
      if (event.isCanceled()) {
         ci.cancel();
      }
   }

   @Inject(
      method = "runTick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 0, shift = At.Shift.BEFORE)
   )
   public void onRunTickAfterRightClickDelay(CallbackInfo ci) {
      EventBus.post(new RightClickDelayEvent());
   }

   @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;sendClickBlockToController(Z)V", shift = At.Shift.AFTER))
   private void jade$fastMinePassiveBlockHitDelay(CallbackInfo ci) {
      Minecraft mc = (Minecraft)(Object)this;
      BedNuker bedNuker = Jade.getModuleManager().getModule(BedNuker.class);
      if (bedNuker != null && bedNuker.isNuking()) {
         bedNuker.QLkR();
      } else {
         BridgeNuker bridgeNuker = Jade.getModuleManager().getModule(BridgeNuker.class);
         if (bridgeNuker == null || !bridgeNuker.PfjH()) {
            FastBreak fm = Jade.getModuleManager().getModule(FastBreak.class);
            if (fm != null) {
               fm.decreaseBlockHitDelay(mc);
               fm.boostBlockDamageProgress(mc);
            }
         }
      }
   }

   @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
   public void onLoadWorld(WorldClient world, String loadingMessage, CallbackInfo ci) {
      EventBus.post(new LoadWorldEvent(world));
   }

   @Inject(method = "displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", at = @At("HEAD"), cancellable = true)
   public void onDisplayGuiScreen(GuiScreen guiScreen, CallbackInfo ci) {
      Minecraft mc = (Minecraft)(Object)this;
      GuiScreen previousGui = mc.currentScreen;
      GuiScreen setGui = guiScreen;
      boolean opened = guiScreen != null;
      if (!opened) {
         setGui = previousGui;
      }

      EventBus.post(new GuiDisplayEvent(setGui, opened));
      GuiOpenEvent event = new GuiOpenEvent(guiScreen);
      EventBus.post(event);
      if (event.isCanceled()) {
         ci.cancel();
      }
   }

   @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;changeCurrentItem(I)V"))
   public void changeCurrentItem(InventoryPlayer inventoryPlayer, int slot) {
      ChangeCurrentItemEvent event = new ChangeCurrentItemEvent(slot, inventoryPlayer.currentItem);
      EventBus.post(event);
      if (!event.isCanceled()) {
         inventoryPlayer.changeCurrentItem(slot);
      }
   }

   @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;thirdPersonView:I", opcode = 181))
   private void onSetThirdPersonView(GameSettings gameSettings, int value) {
      if (Jade.getModuleManager().getModule(FreeLook.class) != null && FreeLook.DnH) {
         Jade.getModuleManager().getModule(FreeLook.class).disableFreeLook();
      } else {
         gameSettings.thirdPersonView = value;
      }
   }

   @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = 181))
   private void onSetCurrentItem(InventoryPlayer inventoryPlayer, int slot) {
      SetCurrentItemEvent e = new SetCurrentItemEvent(slot);
      EventBus.post(e);
      if (!e.isCanceled()) {
         inventoryPlayer.currentItem = slot;
      }
   }
}
