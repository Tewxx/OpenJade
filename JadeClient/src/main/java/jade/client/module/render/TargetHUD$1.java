// Jade recovery: original class: jade.deps.eLz.Wq6GDygnI4$1
package jade.client.module.render;

import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;

public class TargetHUD$1 extends GuiScreen {
   final TargetHUD this$0;

   private GuiButton resetButton;
   private boolean dragging;
   private int cyB;
   private int offsetY;
   private int dragStartOffsetX;
   private int HALJ;
   private int THvwC;
   private int uIdgG;
   private float siKfy6;
   private float pFypm;
   private float pEagwY;
   private float boundsBottom;

   public TargetHUD$1(TargetHUD var1) {
      this.this$0 = var1;
      this.dragging = false;
      this.cyB = this.this$0.Vilxx;
      this.offsetY = this.this$0.offsetY;
   }

   public void initGui() {
      super.initGui();
      this.buttonList.add(this.resetButton = new GuiButton(1, this.width - 90, this.height - 25, 85, 20, "Reset position"));
      this.cyB = this.this$0.Vilxx;
      this.offsetY = this.this$0.offsetY;
   }

   public void drawScreen(int var1, int var2, float var3) {
      drawRect(0, 0, this.width, this.height, -1308622848);
      ScaledResolution var4 = new ScaledResolution(this.mc);
      this.this$0.Vilxx = this.cyB;
      this.this$0.offsetY = this.offsetY;
      double var5 = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();
      TargetHUD.setHealthFraction(this.this$0, var5);
      String var7 = TargetHUD.getFontSetting(this.this$0).getResolvedFontName();
      IFont var8 = FontManager.getHudRenderer(var7, (float)TargetHUD.getFontScaleSetting(this.this$0).getInput());
      String var9 = this.mc.thePlayer.getDisplayName().getFormattedText();
      String var10 = String.valueOf(Math.round(this.mc.thePlayer.getHealth()));
      TargetHUD$2 var11 = TargetHUD.computeHudLayout(this.this$0, var8, var9, var10, TargetHUD.formatHealthDifference(0.0F));
      float var12 = var4.getScaledWidth() / 2.0F - TargetHUD$2.getWidth(var11) / 2.0F + this.cyB;
      float var13 = var4.getScaledHeight() / 2.0F + 15.0F + this.offsetY;
      if (TargetHUD.YOciJ(this.this$0) < 0.0F) {
         float var14 = var12 + TargetHUD$2.bCboH(var11) + TargetHUD$2.getHeadSize(var11) + TargetHUD$2.rL02(var11);
         float var15 = var12 + TargetHUD$2.getWidth(var11) - TargetHUD$2.bCboH(var11) - TargetHUD$2.getHealthTextWidth(var11);
         TargetHUD.QayZ7(this.this$0, var14 + (var15 - var14) * (float)var5);
      }

      EntityLivingBase var20 = TargetHUD.pasKgv(this.this$0);
      TargetHUD.setTargetEntity(this.this$0, this.mc.thePlayer);
      TargetHUD.renderTargetHud(this.this$0, null, this.mc.thePlayer, var5);
      TargetHUD.setTargetEntity(this.this$0, var20);
      this.siKfy6 = var12;
      this.pFypm = var13;
      this.pEagwY = var12 + TargetHUD$2.getWidth(var11);
      this.boundsBottom = var13 + TargetHUD$2.getHeight(var11);
      String var21 = "Edit the HUD position by dragging.";
      int var16 = var4.getScaledWidth() / 2 - this.mc.fontRendererObj.getStringWidth(var21) / 2;
      int var17 = var4.getScaledHeight() / 2 - 20;
      RenderUtils.drawRainbowText(var21, '-', var16, var17, 2L, 0L, true, this.mc.fontRendererObj);

      try {
         this.handleInput();
      } catch (IOException var19) {
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      super.mouseClickMove(var1, var2, var3, var4);
      if (var3 == 0) {
         if (this.dragging) {
            this.cyB = this.dragStartOffsetX + (var1 - this.THvwC);
            this.offsetY = this.HALJ + (var2 - this.uIdgG);
         } else if (var1 >= this.siKfy6 && var1 <= this.pEagwY && var2 >= this.pFypm && var2 <= this.boundsBottom) {
            this.dragging = true;
            this.THvwC = var1;
            this.uIdgG = var2;
            this.dragStartOffsetX = this.cyB;
            this.HALJ = this.offsetY;
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
         this.cyB = this.this$0.Vilxx = 70;
         this.offsetY = this.this$0.offsetY = 30;
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
