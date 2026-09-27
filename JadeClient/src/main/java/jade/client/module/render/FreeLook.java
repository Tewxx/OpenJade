// Jade recovery: module: Free Look (render); original class: jade.deps.eLz.Nk515Uf
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.Subscribe;
import jade.client.event.GuiOpenEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.Freecam;
import jade.client.module.render.freelook.FreeLookCamera$1;
import jade.client.module.render.freelook.FreeLookCamera;
import jade.client.module.render.freelook.KeyStateEdgeDetector;
import jade.client.module.render.freelook.PerspectiveController;
import jade.client.module.render.freelook.FreeLookMath;
import jade.client.setting.BooleanSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;

import net.minecraft.client.Minecraft;

@ModuleInfo
public class FreeLook extends Module {
   private static final String[] ElF = new String[]{"Toggle", "Hold"};
   private static final int HOLD_MODE_ORDINAL = 1;
   public static boolean DnH;
   public static float savedYaw;
   public static float savedPitch;
   private SliderSetting mode;
   private BooleanSetting customFov;
   private SliderSetting fov;
   private KeySetting key;
   private final KeyStateEdgeDetector gseb7 = new KeyStateEdgeDetector();
   private int YFs;
   private float savedFov;

   public FreeLook() {
      super("Free Look", Category.render);
      this.registerSetting(this.key = new KeySetting("Key", 56));
      this.registerSetting(
         this.mode = new SliderSetting(
            "Mode", 1, ElF, new String[]{"Hold"}
         )
      );
      this.registerSetting(
         this.customFov = new BooleanSetting(
            "Custom FOV", false
         )
      );
      this.registerSetting(this.fov = new SliderSetting("FOV", 90.0, 10.0, 150.0, 1.0));
   }

   @Override
   public void guiUpdate() {
      this.fov.setVisible(this.customFov.isToggled(), this);
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && mc.currentScreen == null && ClientUtils.isInWorld()) {
         Module var2 = Jade.getModuleManager().getModule(Freecam.class);
         if (!(var2 instanceof Freecam) || Freecam.cameraEntity == null) {
            Boolean var3 = this.gseb7.pollChange(this.key.isHeldDown());
            if (var3 != null) {
               this.updateFreeLookState(var3);
            }
         }
      }
   }

   @Subscribe
   public void onGuiOpen(GuiOpenEvent var1) {
      if (var1.guiScreen != null && DnH && this.isHoldMode()) {
         this.disableFreeLook();
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      if (DnH) {
         this.disableFreeLook();
      }
   }

   private void updateFreeLookState(boolean var1) {
      if (!this.isEnabled()) {
         if (DnH) {
            this.disableFreeLook();
         }
      } else {
         if (var1) {
            savedYaw = mc.thePlayer.rotationYaw;
            savedPitch = mc.thePlayer.rotationPitch;
            if (DnH) {
               this.disableFreeLook();
            } else {
               this.enableFreeLook();
            }
         } else if (this.isHoldMode()) {
            this.disableFreeLook();
         }
      }
   }

   public void enableFreeLook() {
      DnH = true;
      this.YFs = mc.gameSettings.thirdPersonView;
      this.setThirdPersonView(1);
      this.savedFov = mc.gameSettings.fovSetting;
   }

   public void qnGdrL() {
      if (this.isEnabled() && !DnH) {
         if (mc.thePlayer != null) {
            savedYaw = mc.thePlayer.rotationYaw;
            savedPitch = mc.thePlayer.rotationPitch;
         }

         this.enableFreeLook();
      }
   }

   public void disableFreeLook() {
      DnH = false;
      this.setThirdPersonView(this.YFs);
      if (mc.currentScreen == null && mc.inGameHasFocus) {
         mc.mouseHelper.grabMouseCursor();
      }

      if (FreeLookMath.needsFovRestore(this.isHoldMode(), mc.gameSettings.fovSetting, this.savedFov, this.customFov.isToggled())) {
         mc.gameSettings.fovSetting = this.savedFov;
      }
   }

   public static boolean updateFreeLookCamera(Minecraft var0) {
      FreeLook var1 = Jade.getModuleManager().getModule(FreeLook.class);
      if (!var0.inGameHasFocus) {
         return false;
      } else {
         boolean var2 = var1 != null && var1.isEnabled() && DnH;
         FreeLookCamera$1 var3 = FreeLookCamera.applyFreeLookRotation(var0, var2, savedYaw, savedPitch, var1 != null && var1.customFov.isToggled(), var1 == null ? 0.0F : (float)var1.fov.getInput());
         if (var3.Yat) {
            savedYaw = var3.NDTOt;
            savedPitch = var3.bYc;
         }

         return var3.normalCameraControl;
      }
   }

   @Override
   public void onDisable() {
      if (DnH) {
         DnH = false;
         this.setThirdPersonView(0);
         if (mc.currentScreen == null && mc.inGameHasFocus) {
            mc.mouseHelper.grabMouseCursor();
         }

         mc.gameSettings.fovSetting = this.savedFov;
      }
   }

   private boolean isHoldMode() {
      return (int)this.mode.getInput() == 1;
   }

   private void setThirdPersonView(int var1) {
      PerspectiveController.wxaroH(mc, var1);
   }
}
