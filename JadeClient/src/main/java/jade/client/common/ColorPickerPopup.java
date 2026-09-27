// Jade recovery: original class: jade.deps.eLz.ZJy2mMgfzs
package jade.client.common;

import java.awt.Color;

public final class ColorPickerPopup {
   public final GuiRect tnIwm;
   public final GuiRect DSvS;
   public final GuiRect UmD;
   public final GuiRect uKl;
   public final GuiRect confirmButtonBounds;

   public ColorPickerPopup(GuiRect var1, int var2, int var3, boolean var4) {
      int var5 = Math.max(32, Math.min(140, Math.min(var2 - 50, var3 - (var4 ? 110 : 76))));
      int var6 = var5 + 40;
      int var7 = var5 + (var4 ? 100 : 66);
      int var8 = var1.x + var1.busF + 7;
      if (var8 + var6 > var2 - 5) {
         var8 = var1.x - var6 - 7;
      }

      var8 = Math.max(5, Math.min(var2 - var6 - 5, var8));
      int var9 = Math.max(5, Math.min(var3 - var7 - 5, var1.ufe));
      this.tnIwm = new GuiRect(var8, var9, var6, var7);
      this.DSvS = new GuiRect(var8 + 10, var9 + 30, var5, var5);
      this.UmD = new GuiRect(var8 + var6 - 22, this.DSvS.ufe, 12, var5);
      this.uKl = var4 ? new GuiRect(var8 + 10, this.DSvS.ufe + var5 + 22, var6 - 20, 10) : null;
      this.confirmButtonBounds = new GuiRect(var8 + 36, var9 + var7 - 26, var6 - 46, 18);
   }

   public void EoZct(JadeClickGui var1, String var2, float var3, float var4, float var5, int var6) {
      GuiIcons.fillRect(this.tnIwm.x + 3, this.tnIwm.ufe + 3, this.tnIwm.busF, this.tnIwm.HfS, 1610612736);
      GuiIcons.fillRect(this.tnIwm.x, this.tnIwm.ufe, this.tnIwm.busF, this.tnIwm.HfS, -15724528);
      GuiIcons.drawRectBorder(this.tnIwm.x, this.tnIwm.ufe, this.tnIwm.busF, this.tnIwm.HfS, 613258909);
      RenderUtils.drawVerticalGradient(this.tnIwm.x, this.tnIwm.ufe, this.tnIwm.x + this.tnIwm.busF, this.tnIwm.ufe + 21, GuiTheme.xGoxa(), GuiTheme.getShadedAccentColor());
      var1.drawSmall(var1.trimToWidth(var2, this.tnIwm.busF - 16, var1.settingFont()), this.tnIwm.x + 8, this.tnIwm.ufe + 6, -1182988);
      int var7 = Color.HSBtoRGB(var3, 1.0F, 1.0F) | 0xFF000000;
      GuiIcons.fillRect(this.DSvS.x, this.DSvS.ufe, this.DSvS.busF, this.DSvS.HfS, var7);
      RenderUtils.drawHorizontalGradient(this.DSvS.x, this.DSvS.ufe, this.DSvS.x + this.DSvS.busF, this.DSvS.ufe + this.DSvS.HfS, -1, 16777215);
      RenderUtils.drawVerticalGradient(this.DSvS.x, this.DSvS.ufe, this.DSvS.x + this.DSvS.busF, this.DSvS.ufe + this.DSvS.HfS, 0, -16777216);
      twu25(this.DSvS);
      int var8 = this.DSvS.x + Math.round(var4 * this.DSvS.busF);
      int var9 = this.DSvS.ufe + Math.round((1.0F - var5) * this.DSvS.HfS);
      GuiIcons.drawRectBorder(var8 - 3, var9 - 3, 7.0F, 7.0F, -15724528);
      GuiIcons.drawRectBorder(var8 - 2, var9 - 2, 5.0F, 5.0F, -1);

      for (int var10 = 0; var10 < 6; var10++) {
         int var11 = this.UmD.ufe + Math.round(var10 * this.UmD.HfS / 6.0F);
         int var12 = this.UmD.ufe + Math.round((var10 + 1) * this.UmD.HfS / 6.0F);
         RenderUtils.drawVerticalGradient(
            this.UmD.x,
            var11,
            this.UmD.x + this.UmD.busF,
            var12,
            Color.HSBtoRGB(var10 / 6.0F, 1.0F, 1.0F),
            Color.HSBtoRGB((var10 + 1) / 6.0F, 1.0F, 1.0F)
         );
      }

      twu25(this.UmD);
      int var13 = this.UmD.ufe + Math.round(var3 * this.UmD.HfS);
      GuiIcons.fillRect(this.UmD.x - 2, var13 - 2, this.UmD.busF + 4, 5.0F, -15724528);
      GuiIcons.fillRect(this.UmD.x - 1, var13 - 1, this.UmD.busF + 2, 3.0F, -1);
      if (this.uKl != null) {
         var1.drawSmall("Opacity", this.uKl.x, this.uKl.ufe - 14, -4208434);
         String var14 = Math.round((var6 >>> 24) / 255.0F * 100.0F) + "%";
         var1.drawSmall(var14, this.uKl.x + this.uKl.busF - var1.textWidth(var14, var1.settingFont()), this.uKl.ufe - 14, -1182988);
         drawCheckerboard(this.uKl);
         RenderUtils.drawHorizontalGradient(this.uKl.x, this.uKl.ufe, this.uKl.x + this.uKl.busF, this.uKl.ufe + this.uKl.HfS, var6 & 16777215, var6 | 0xFF000000);
         twu25(this.uKl);
         int var16 = this.uKl.x + Math.round((var6 >>> 24) / 255.0F * this.uKl.busF);
         GuiIcons.fillRect(var16 - 2, this.uKl.ufe - 2, 5.0F, this.uKl.HfS + 4, -15724528);
         GuiIcons.fillRect(var16 - 1, this.uKl.ufe - 1, 3.0F, this.uKl.HfS + 2, -1);
      }

      GuiRect var15 = new GuiRect(this.tnIwm.x + 10, this.confirmButtonBounds.ufe, 18, 18);
      drawCheckerboard(var15);
      GuiIcons.fillRect(var15.x, var15.ufe, var15.busF, var15.HfS, var6);
      twu25(var15);
      GuiIcons.fillRect(this.confirmButtonBounds.x, this.confirmButtonBounds.ufe, this.confirmButtonBounds.busF, this.confirmButtonBounds.HfS, -435154928);
      twu25(this.confirmButtonBounds);
   }

   private static void drawCheckerboard(GuiRect var0) {
      for (byte var1 = 0; var1 < var0.HfS; var1 += 4) {
         for (byte var2 = 0; var2 < var0.busF; var2 += 4) {
            GuiIcons.fillRect(
               var0.x + var2,
               var0.ufe + var1,
               Math.min(4, var0.busF - var2),
               Math.min(4, var0.HfS - var1),
               (var2 / 4 + var1 / 4 & 1) == 0 ? -4144960 : -9408400
            );
         }
      }
   }

   private static void twu25(GuiRect var0) {
      GuiIcons.drawRectBorder(var0.x - 1, var0.ufe - 1, var0.busF + 2, var0.HfS + 2, -12237499);
   }
}
