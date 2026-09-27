// Jade recovery: module: Hide Window (player); original class: jade.deps.eLz.HANAKzXUkd
package jade.client.module.player;

import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.GuiOpenEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.hidewindow.FloatingWindowPosition$1;
import jade.client.module.player.hidewindow.FloatingWindowPosition;
import jade.client.module.player.hidewindow.HideWindowSettings;
import jade.client.module.player.hidewindow.RzUgdC;
import jade.client.module.player.hidewindow.SquareBounds;
import jade.client.module.player.hidewindow.UKCtdVsMbi;
import jade.client.setting.SliderSetting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

@ModuleInfo
public class HideWindow extends Module {
   private static final String TV_OFF_TEXTURE_PATH = "/assets/jade/textures/gui/tv_off.png";
   private static final int ICON_TEXTURE_SIZE = 16;
   private static final float DEFAULT_FRACTION_X = 0.5F;
   private static final float KLcw = 0.05F;
   private static final int INVALID_WINDOW_ID = -1;
   private final HideWindowSettings hideWindowSettings;
   private GuiContainer guiContainer;
   private final FloatingWindowPosition hiddenWindow = new FloatingWindowPosition(0.5F, 0.05F);

   public HideWindow() {
      super("Hide Window", Category.player);
      this.hideWindowSettings = new HideWindowSettings(this);
   }

   @Override
   public String getInfo() {
      return this.isWindowHidden() ? "Hidden" : "";
   }

   public boolean isWindowHidden() {
      return this.guiContainer != null;
   }

   public GuiContainer getHiddenContainer() {
      return this.guiContainer;
   }

   @Override
   public void guiUpdate() {
      this.hideWindowSettings.whitelistNames.setVisible(this.hideWindowSettings.whitelist.isToggled(), this);
   }

   @Override
   public void onDisable() {
      if (this.guiContainer != null && mc.thePlayer != null) {
         mc.thePlayer.closeScreen();
      }

      this.guiContainer = null;
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onGuiOpen(GuiOpenEvent var1) {
      if (var1.guiScreen != null && mc.thePlayer != null) {
         if (var1.guiScreen instanceof GuiContainer && !(var1.guiScreen instanceof GuiInventory)) {
            if (mc.currentScreen instanceof GuiContainer) {
               this.guiContainer = null;
            } else {
               GuiContainer var2 = (GuiContainer)var1.guiScreen;
               if (!this.hideWindowSettings.onlyWhileCrouching.isToggled() || mc.thePlayer.isSneaking()) {
                  if (!this.hideWindowSettings.whitelist.isToggled() || this.aknCtr(var2)) {
                     this.guiContainer = var2;
                     mc.thePlayer.openContainer = this.guiContainer.inventorySlots;
                     var1.setCanceled(true);
                  }
               }
            }
         } else {
            if (var1.guiScreen instanceof GuiInventory && this.guiContainer != null) {
               var1.guiScreen = this.guiContainer;
               this.guiContainer = null;
            }
         }
      }
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (var1.ys98() instanceof S2EPacketCloseWindow) {
         this.guiContainer = null;
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.guiContainer = null;
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (this.guiContainer != null && mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
            this.drawWindowIcon(false);
         }
      }
   }

   public float getWindowPixelX() {
      this.refreshWindowBounds();
      return this.hiddenWindow.getPixelX();
   }

   public float getWindowPixelY() {
      this.refreshWindowBounds();
      return this.hiddenWindow.getPixelY();
   }

   public float yyhw2() {
      this.refreshWindowBounds();
      return this.hiddenWindow.getFractionX();
   }

   public float FJkOz() {
      this.refreshWindowBounds();
      return this.hiddenWindow.getFractionY();
   }

   public void YYiq8(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      this.hiddenWindow.setFractionPosition(var1, var2, var3.getScaledWidth(), var3.getScaledHeight());
   }

   public void setWindowFractionPosition(float var1, float var2) {
      this.setWindowPixelPosition(var1, var2, new ScaledResolution(mc));
   }

   public void fdma() {
      ScaledResolution var1 = new ScaledResolution(mc);
      this.hiddenWindow.aTls(var1.getScaledWidth(), var1.getScaledHeight());
   }

   public SliderSetting getIconScale() {
      return this.hideWindowSettings.iconScale;
   }

   public float[] renderIconAndGetBounds(float var1, float var2) {
      FloatingWindowPosition$1 var3 = this.hiddenWindow.createSnapshot();
      this.setWindowFractionPosition(var1, var2);
      this.drawWindowIcon(false);
      int var4 = UKCtdVsMbi.JqzE(this.hideWindowSettings.iconScale.getInput());
      this.hiddenWindow.LDUKrN(var3);
      return SquareBounds.ZlAtiX(var1, var2, var4, 0.0F);
   }

   private void drawWindowIcon(boolean var1) {
      this.refreshWindowBounds();
      UKCtdVsMbi.ODZybV(this.hiddenWindow, this.hideWindowSettings.iconScale.getInput(), this.hideWindowSettings.iconColor.getRgb());
   }

   private void refreshWindowBounds() {
      this.kzE9(new ScaledResolution(mc));
   }

   private void kzE9(ScaledResolution var1) {
      this.hiddenWindow.updateForScreenSize(var1.getScaledWidth(), var1.getScaledHeight());
   }

   private void setWindowPixelPosition(float var1, float var2, ScaledResolution var3) {
      this.hiddenWindow.setPixelPosition(var1, var2, var3.getScaledWidth(), var3.getScaledHeight());
   }

   private boolean aknCtr(GuiContainer var1) {
      return RzUgdC.matchesWhitelistEntry(this.hideWindowSettings.whitelistNames.getEntries(), getContainerTitle(var1));
   }

   private static String getContainerTitle(GuiContainer var0) {
      return var0.inventorySlots instanceof ContainerChest
         ? ((ContainerChest)var0.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText()
         : "";
   }
}
