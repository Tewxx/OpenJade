// Jade recovery: original class: jade.deps.eLz.MbsVIT$2
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.module.Module;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class Arraylist$2 extends GuiScreen {
   private static final String EXAMPLE_ARRAYLIST_TEXT = "This is an-Example-Arraylist";
   private GuiButton resetPositionButton;
   private boolean SMXu34 = false;
   private float previewAnchorX = 0.0F;
   private float NhYf = 0.0F;
   private float previewMaxX = 0.0F;
   private float Bic = 0.0F;
   private float editedAnchorX = 5.0F;
   private float editedAnchorY = 70.0F;
   private float AaNo = 0.0F;
   private float SojC = 0.0F;
   private int dragStartMouseX = 0;
   private int GEYreP = 0;
   private float previewMinX = 0.0F;

   public void initGui() {
      super.initGui();
      this.buttonList.add(this.resetPositionButton = new GuiButton(1, this.width - 90, this.height - 25, 85, 20, "Reset position"));
      Arraylist.NgJh(new ScaledResolution(this.mc));
      this.editedAnchorX = Arraylist.anchorX;
      this.editedAnchorY = Arraylist.riwe;
   }

   public void drawScreen(int var1, int var2, float var3) {
      ScaledResolution var4 = new ScaledResolution(this.mc);
      if (!this.SMXu34) {
         Arraylist.NgJh(var4);
         this.editedAnchorX = Arraylist.anchorX;
         this.editedAnchorY = Arraylist.riwe;
      }

      drawRect(0, 0, this.width, this.height, -1308622848);
      float var5 = this.editedAnchorX;
      float var6 = this.editedAnchorY;
      float var7 = var5 + 50.0F;
      float var8 = var6 + 32.0F;
      float[] var9 = this.BCrrpav("This is an-Example-Arraylist");
      this.previewAnchorX = var5;
      this.NhYf = var6;
      if (var9 == null) {
         this.previewMaxX = var7;
         this.Bic = var8;
         this.previewMinX = var5;
      } else {
         this.previewMaxX = var9[0];
         this.Bic = var9[1];
         this.previewMinX = var9[2];
      }

      Arraylist.setAbsolutePositionForResolution(var5, var6, var4);
      int var10 = var4.getScaledWidth() / 2 - 84;
      int var11 = var4.getScaledHeight() / 2 - 20;
      RenderUtils.drawRainbowText("Edit the Arraylist position by dragging.", '-', var10, var11, 2L, 0L, true, this.mc.fontRendererObj);

      try {
         this.handleInput();
      } catch (IOException var13) {
      }

      super.drawScreen(var1, var2, var3);
   }

   private float[] BCrrpav(String var1) {
      IFont var2 = Arraylist.FAEgbj();
      if (this.hasNoVisibleModules()) {
         float var41 = this.previewAnchorX;
         float var42 = this.NhYf;
         String[] var43 = var1.split("-");
         int var6 = Arraylist.getBackgroundPaddingTop();
         int var44 = Arraylist.getBackgroundPaddingBottom();
         int var45 = Arraylist.getRowHeight(var2.getTextTopOffset(), var2.getTextBottomOffset(), var6, var44);

         for (String var49 : var43) {
            if (Arraylist.isRightAligned()) {
               var41 += var2.getStringWidth(var43[0]) - var2.getStringWidth(var49);
            }

            float var50 = Arraylist.aKzlE(var42, var2.getTextTopOffset(), var6);
            Arraylist.drawEntryLabel(var2, var49, var41, var50, Color.white.getRGB());
            var42 += var45;
         }

         return null;
      } else {
         int var3 = Arraylist.getMaxModuleWidth();
         float var4 = this.NhYf;
         double var5 = 0.0;
         int var7 = var2.getTextTopOffset();
         int var8 = var2.getTextBottomOffset();
         int var9 = Arraylist.getBackgroundPaddingX();
         int var10 = Arraylist.getBackgroundPaddingTop();
         int var11 = Arraylist.getBackgroundPaddingBottom();
         int var12 = Arraylist.getOutlineThickness();
         int var13 = Arraylist.getRowHeight(var7, var8, var10, var11);
         ArrayList var14 = new ArrayList();

         try {
            for (Module var16 : Jade.getModuleManager().getSortedModules()) {
               if (var16.isEnabled() && !(var16 instanceof Arraylist) && !Arraylist.MZb9(var16, false)) {
                  String var17 = Arraylist.getModuleDisplayText(var16);
                  int var18 = var2.getStringWidth(var17);
                  float var19 = Arraylist.anchorX;
                  float var20 = Arraylist.aKzlE(var4, var7, var10);
                  double var21 = var19 - var9;
                  double var23 = var19 + var18 + var9;
                  double var25 = var4;
                  double var27 = var4 + var13;
                  double var29 = var21 - var12;
                  double var31 = var23 + var12;
                  double var33 = var25 - var12;
                  if (Arraylist.isRightAligned()) {
                     var19 -= var18;
                     var21 = var19 - var9;
                     var23 = var19 + var18 + var9;
                     var29 = var21 - var12;
                     var31 = var23 + var12;
                  }

                  double var35 = (var21 + var23) * 0.5;
                  double var37 = Arraylist.getWaveOffset(var5, var35);
                  int var39 = Arraylist.xQec0(var37);
                  var14.add(new Arraylist$4(var17, var18, var19, var20, var21, var25, var23, var27, var29, var33, var31, var39, 1.0F));
                  if (Arraylist.isWaveAxisVertical()) {
                     var5 += Arraylist.getWaveStep();
                  }

                  var4 += var13;
               }
            }

            Arraylist.HBQY(var14, var2, var12, var9, false);
         } catch (Exception var40) {
            ClientUtils.sendColoredMessage("&cAn error occurred rendering Arraylist. check your logs");
            var40.printStackTrace();
         }

         double var51 = var14.isEmpty() ? var4 : Arraylist$4.getOuterBottom((Arraylist$4)var14.get(var14.size() - 1));
         return new float[]{this.previewAnchorX + var3, (float)Math.ceil(Math.max((double)var4, var51)), this.previewAnchorX - var3};
      }
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      super.mouseClickMove(var1, var2, var3, var4);
      if (var3 == 0) {
         if (this.SMXu34) {
            this.editedAnchorX = this.AaNo + (var1 - this.dragStartMouseX);
            this.editedAnchorY = this.SojC + (var2 - this.GEYreP);
         } else if (var1 > this.previewMinX && var1 < this.previewMaxX && var2 > this.NhYf && var2 < this.Bic) {
            this.SMXu34 = true;
            this.dragStartMouseX = var1;
            this.GEYreP = var2;
            this.AaNo = this.editedAnchorX;
            this.SojC = this.editedAnchorY;
         }
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      if (var3 == 0) {
         this.SMXu34 = false;
      }
   }

   public void actionPerformed(GuiButton var1) {
      if (var1 == this.resetPositionButton) {
         Arraylist.resetAnchor(new ScaledResolution(this.mc));
         this.editedAnchorX = Arraylist.anchorX;
         this.editedAnchorY = Arraylist.riwe;
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private boolean hasNoVisibleModules() {
      for (Module var2 : Jade.getModuleManager().getSortedModules()) {
         if (var2.isEnabled() && !(var2 instanceof Arraylist) && !Arraylist.MZb9(var2, false)) {
            return false;
         }
      }

      return true;
   }
}
