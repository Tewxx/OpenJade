// Jade recovery: original class: jade.deps.eLz.T2Vc6wj6I
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.CategoryComponent;
import jade.client.module.client.CommandLine;
import jade.client.module.client.Gui;
import jade.client.setting.KeySetting;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClickGui extends GuiScreen {
   private final ClickGuiFadeAnimator IByE = new ClickGuiFadeAnimator();
   private final GuiScale guiScaleHelper = new GuiScale();
   private final CommandLineInput juU = new CommandLineInput();
   public static ArrayList<CategoryComponent> categoryPanels;
   private double Uf2;
   private static boolean scaleInitialized;
   private boolean layoutRebuildPending;

   public ClickGui() {
      categoryPanels = CategoryPanels.TXIYOn();
   }

   public void initMain() {
      this.IByE.startFade(Jade.getScheduler());
   }

   public void initGui() {
      super.initGui();
      double var1 = this.VOwD2();
      boolean var3 = scaleInitialized && Double.compare(this.Uf2, var1) != 0;
      if (!scaleInitialized) {
         scaleInitialized = true;
         this.Uf2 = var1;
      }

      CategoryPanelLayout.jjdWycA(categoryPanels, this.width, this.height, var3);
      this.juU.initialize(this.mc, this.fontRendererObj, this.height, this.buttonList);
      this.Uf2 = var1;
   }

   public void drawScreen(int var1, int var2, float var3) {
      int var4 = this.toLogicalCoordinate(var1);
      int var5 = this.toLogicalCoordinate(var2);
      if (Gui.darkBackground.isToggled()) {
         drawRect(0, 0, this.guiScaleHelper.getScaledWidth(), this.guiScaleHelper.getScaledHeight(), this.IByE.AGNCnZs());
      }

      GlStateManager.pushMatrix();
      GlStateManager.scale(this.getRenderScale(), this.getRenderScale(), 1.0);
      CategoryPanels.drawPanels(categoryPanels, this.fontRendererObj, var4, var5);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if (this.juU.draw(this.fontRendererObj, this.height, this.guiScaleHelper.getScaleFactor())) {
         super.drawScreen(var4, var5, var3);
      }

      GlStateManager.popMatrix();
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      CategoryPanelMouseHandler.handleMouseClick(categoryPanels, var1, var2, var3);
      if (CommandLine.commandLineOpen) {
         this.juU.click(var1, var2, var3);
         super.mouseClicked(var1, var2, var3);
      }

      if (var3 == 0 || var3 == 1 || var3 == 2) {
         InputFocusManager.unfocusOthers(categoryPanels, var1, var2);
      }
   }

   public void mouseReleased(int var1, int var2, int var3) {
      CategoryPanelMouseHandler.handleMouseRelease(categoryPanels, var1, var2, var3);
      if (this.layoutRebuildPending) {
         this.layoutRebuildPending = false;
         this.rebuildLayout();
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getDWheel();
      if (var1 != 0) {
         int var2 = Mouse.getEventX() * this.width / this.mc.displayWidth;
         int var3 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

         for (CategoryComponent var5 : categoryPanels) {
            var5.onMouseScroll(var1, var2, var3);
         }
      }
   }

   public void handleKeyboardInput() throws IOException {
      int var1 = Keyboard.getEventKeyState() ? 0 : Keyboard.getEventKey();
      super.handleKeyboardInput();
      if (KeySetting.isModifierKey(var1)) {
         this.modifierKeyReleased(var1);
      }
   }

   protected void modifierKeyReleased(int var1) {
      CategoryPanels.assignModifierKey(categoryPanels, var1);
   }

   public void refreshAfterProfileLoad() {
      if (this.mc == null) {
         this.mc = Minecraft.getMinecraft();
      }

      this.rebuildLayout();
   }

   public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
      this.mc = var1;
      this.itemRender = var1.getRenderItem();
      this.fontRendererObj = var1.fontRendererObj;
      this.PbX9();
      this.buttonList.clear();
      this.initGui();
   }

   public void keyTyped(char var1, int var2) {
      FocusableTextInput var3 = InputFocusManager.findFocusedInput(categoryPanels);
      if (var2 == 1) {
         if (var3 != null) {
            var3.unfocus();
            return;
         }

         if (!InputFocusManager.CXaRpt1(categoryPanels)) {
            this.mc.displayGuiScreen(null);
            return;
         }
      }

      if (var3 != null) {
         CategoryPanels.dispatchKeyTyped(categoryPanels, var1, var2);
      } else {
         CategoryPanels.dispatchKeyTyped(categoryPanels, var1, var2);
         if (CommandLine.commandLineOpen) {
            this.juU.keyTyped(var1, var2);
         }
      }
   }

   public void actionPerformed(GuiButton var1) {
      if (this.juU.owns(var1)) {
         this.juU.submit();
      }
   }

   public void onGuiClosed() {
      this.IByE.dnw7();
      CategoryPanels.resetAllPanels(categoryPanels);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public boolean isTypingInTextInput() {
      return InputFocusManager.isAnyInputFocused(categoryPanels, this.juU.input());
   }

   public void onSliderChange() {
      CategoryPanels.refreshSliders(categoryPanels);
   }

   public void requestScaleRefresh() {
      this.layoutRebuildPending = true;
   }

   private void rebuildLayout() {
      this.PbX9();
      CategoryPanelLayout.relayoutPanels(categoryPanels, this.width, this.height);
      this.buttonList.clear();
      this.initGui();
   }

   private void PbX9() {
      this.guiScaleHelper.aZyv(this.mc, this.VOwD2());
      this.width = this.guiScaleHelper.getLogicalWidth();
      this.height = this.guiScaleHelper.getLogicalHeight();
   }

   protected int toLogicalCoordinate(int var1) {
      return this.guiScaleHelper.toLogicalCoordinate(var1);
   }

   protected double getRenderScale() {
      return this.guiScaleHelper.getLogicalScaleRatio();
   }

   public static double getActiveRenderScale() {
      Minecraft var0 = Minecraft.getMinecraft();
      return var0.currentScreen instanceof ClickGui ? ((ClickGui)var0.currentScreen).getRenderScale() : 1.0;
   }

   private double VOwD2() {
      return Gui.getGuiScale();
   }
}
