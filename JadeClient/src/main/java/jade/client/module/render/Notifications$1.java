// Jade recovery: original class: jade.deps.eLz.n7HXWJlFWs$1
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class Notifications$1 extends GuiScreen {
   private GuiButton resetButton;
   private boolean dragging;
   private float anchorX;
   private float anchorY;
   private float previewWidth;
   private float previewHeight;
   private float dragStartMouseX;
   private float dragStartMouseY;
   private float dragStartAnchorX;
   private float dragStartAnchorY;

   public void initGui() {
      super.initGui();
      this.buttonList.add(this.resetButton = new GuiButton(1, this.width - 90, this.height - 25, 85, 20, "Reset position"));
      ScaledResolution var1 = new ScaledResolution(this.mc);
      Notifications.recomputeAnchorPosition(var1);
      this.anchorX = Notifications.getAnchorX();
      this.anchorY = Notifications.getAnchorY();
   }

   public void drawScreen(int var1, int var2, float var3) {
      ScaledResolution var4 = new ScaledResolution(this.mc);
      drawRect(0, 0, this.width, this.height, -1308622848);
      Notifications var5 = Jade.getModuleManager().getModule(Notifications.class);
      IFont var6 = var5 == null ? Arraylist.FAEgbj() : Notifications.getNotificationFont(var5);
      Notifications$3 var7 = new Notifications$3("Example", true, System.currentTimeMillis(), this.anchorY);
      Notifications$2 var8 = Notifications.wEsh(var5, var6, var7);
      boolean var9 = this.anchorX >= var4.getScaledWidth() * 0.5F;
      float var10 = var9 ? this.anchorX - Notifications$2.getWidth(var8) : this.anchorX;
      float var11 = this.anchorY;
      this.previewWidth = Notifications$2.getWidth(var8);
      this.previewHeight = Notifications$2.getHeight(var8);
      this.drawExampleNotification(var6, var10, var11, var8, var9);
      Notifications.applyAnchorPosition(this.anchorX, this.anchorY, var4);
      int var12 = var4.getScaledWidth() / 2 - 102;
      int var13 = var4.getScaledHeight() / 2 - 18;
      RenderUtils.drawRainbowText("Drag to place notifications.", '-', var12, var13, 2L, 0L, true, this.mc.fontRendererObj);
      RenderUtils.drawRainbowText("Top half stacks down, bottom half stacks up.", '-', var12 - 33, var13 + 10, 2L, 0L, true, this.mc.fontRendererObj);

      try {
         this.handleInput();
      } catch (IOException var15) {
      }

      super.drawScreen(var1, var2, var3);
   }

   private void drawExampleNotification(IFont var1, float var2, float var3, Notifications$2 var4, boolean var5) {
      Notifications var6 = Jade.getModuleManager().getModule(Notifications.class);
      if (var6 != null && Notifications.JhSgku(var6)) {
         Notifications$3 var11 = new Notifications$3("Example", true, System.currentTimeMillis(), var3);
         Notifications.renderModernCard(var6, var1, var11, var4, var2, var3, 255, 0.75F);
      } else {
         int var7 = ClientUtils.YVVZ(Notifications.getSuccessColor(), 255);
         int var8 = ClientUtils.YVVZ(Notifications.CZqbG(), var6 == null ? 185 : Notifications.getBackgroundTransparency(var6));
         RenderUtils.jxyoE(var2, var3, var2 + Notifications$2.getWidth(var4), var3 + Notifications$2.getHeight(var4), Notifications$2.nbvZi(var4), var8);
         float var9 = var2 + Notifications$2.JVPFY(var4);
         if (var6 == null) {
            var1.drawString("Example", var9, var3 + Notifications$2.getTextOffsetY(var4), ClientUtils.YVVZ(Notifications.getPrimaryTextColor(), 255), true);
         } else {
            Notifications.drawShadowedText(var6, var1, "Example", var9, var3 + Notifications$2.getTextOffsetY(var4), ClientUtils.YVVZ(Notifications.getPrimaryTextColor(), 255), true);
         }

         String var10 = var6 == null ? "Enabled" : Notifications.formatStateText(var6, true);
         if (var6 == null) {
            var1.drawString(var10, var9 + Notifications$2.getPrimaryTextWidth(var4) + Notifications$2.getStatusGap(var4), var3 + Notifications$2.getTextOffsetY(var4), var7, false);
         } else {
            Notifications.drawShadowedText(var6, var1, var10, var9 + Notifications$2.getPrimaryTextWidth(var4) + Notifications$2.getStatusGap(var4), var3 + Notifications$2.getTextOffsetY(var4), var7, true);
         }
      }
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      super.mouseClickMove(var1, var2, var3, var4);
      if (var3 == 0) {
         float var6 = this.anchorX >= this.width * 0.5F ? this.anchorX - this.previewWidth : this.anchorX;
         float var7 = var6 + this.previewWidth;
         float var8 = this.anchorY + this.previewHeight;
         if (this.dragging) {
            this.anchorX = Math.max(0.0F, Math.min((float)this.width, this.dragStartAnchorX + (var1 - this.dragStartMouseX)));
            this.anchorY = Math.max(0.0F, Math.min(this.height - this.previewHeight, this.dragStartAnchorY + (var2 - this.dragStartMouseY)));
         } else {
            if (var1 >= var6 && var1 <= var7 && var2 >= this.anchorY && var2 <= var8) {
               this.dragging = true;
               this.dragStartMouseX = var1;
               this.dragStartMouseY = var2;
               this.dragStartAnchorX = this.anchorX;
               this.dragStartAnchorY = this.anchorY;
            }
         }
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      if (var3 == 0) {
         this.dragging = false;
      }
   }

   public void actionPerformed(GuiButton var1) {
      if (var1 == this.resetButton) {
         this.anchorX = this.width * 0.985F;
         this.anchorY = this.height * 0.985F;
         Notifications.applyAnchorPosition(this.anchorX, this.anchorY, new ScaledResolution(this.mc));
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
