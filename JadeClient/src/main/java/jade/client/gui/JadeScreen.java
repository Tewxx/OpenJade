// Jade recovery: original class: jade.deps.eLz.Y3qaaMOM
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.MiddleClickFriend;
import jade.client.common.OverlayConfig;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class JadeScreen extends GuiScreen {
   private GuiButton overlayToggleButton;
   private GuiButton textColorButton;
   private GuiButton duFm;
   private GuiButton outlineButton;
   private final MouseDragTracker Fsl = new MouseDragTracker();

   public void initGui() {
      int var1 = this.width / 2 - 70;
      int var2 = this.height / 2;
      this.buttonList.add(this.overlayToggleButton = new GuiButton(0, var1, var2 - 28, 140, 20, OverlaySettingLabels.getOverlayEnabledLabel(OverlayConfig.overlayEnabled)));
      this.buttonList.add(this.textColorButton = new GuiButton(1, var1, var2 - 6, 140, 20, OverlaySettingLabels.getTextColorLabel(OverlayConfig.textColorIndex)));
      this.buttonList.add(this.duFm = new GuiButton(2, var1, var2 + 16, 140, 20, OverlaySettingLabels.getShowMouseButtonsLabel(OverlayConfig.showMouseButtons)));
      this.buttonList.add(this.outlineButton = new GuiButton(3, var1, var2 + 38, 140, 20, OverlaySettingLabels.getOutlineLabel(OverlayConfig.outlineEnabled)));
   }

   public void drawScreen(int var1, int var2, float var3) {
      Jade.getHudRenderer().renderHud();
      this.drawCenteredString(this.fontRendererObj, "Jade", this.width / 2, this.height / 2 - 60, -10027162);
      super.drawScreen(var1, var2, var3);
   }

   protected void actionPerformed(GuiButton var1) {
      if (var1 == this.overlayToggleButton) {
         OverlayConfig.overlayEnabled = !OverlayConfig.overlayEnabled;
         this.overlayToggleButton.displayString = OverlaySettingLabels.getOverlayEnabledLabel(OverlayConfig.overlayEnabled);
      } else if (var1 == this.textColorButton) {
         OverlayConfig.textColorIndex = OverlaySettingLabels.getNextTextColorIndex(OverlayConfig.textColorIndex);
         this.textColorButton.displayString = OverlaySettingLabels.getTextColorLabel(OverlayConfig.textColorIndex);
      } else if (var1 == this.duFm) {
         OverlayConfig.showMouseButtons = !OverlayConfig.showMouseButtons;
         this.duFm.displayString = OverlaySettingLabels.getShowMouseButtonsLabel(OverlayConfig.showMouseButtons);
      } else if (var1 == this.outlineButton) {
         OverlayConfig.outlineEnabled = !OverlayConfig.outlineEnabled;
         this.outlineButton.displayString = OverlaySettingLabels.getOutlineLabel(OverlayConfig.outlineEnabled);
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) {
      try {
         super.mouseClicked(var1, var2, var3);
      } catch (IOException var5) {
      }

      if (var3 == 0) {
         MiddleClickFriend.gadF();
         this.Fsl.updateDragState(var1, var2, OverlayConfig.WRg, OverlayConfig.overlayY, OverlayConfig.showMouseButtons);
      } else if (var3 == 1) {
         MiddleClickFriend.ZnihnJ();
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      this.Fsl.clearHover();
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      super.mouseClickMove(var1, var2, var3, var4);
      if (this.Fsl.isHovered()) {
         OverlayConfig.WRg = OverlayConfig.WRg + this.Fsl.consumeDeltaX(var1);
         OverlayConfig.overlayY = OverlayConfig.overlayY + this.Fsl.QcPhj(var2);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
