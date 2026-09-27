// Jade recovery: original class: jade.deps.eLz.iRSBl74Tn
package jade.client.common;

import jade.client.setting.ColorSetting;
import java.awt.Color;

public final class ColorPickerRenderer {
   private static final int ITn = -12829626;
   private static final int CHECKER_LIGHT = -6710887;
   private static final int mzF = -10066330;
   private static final int GwE6 = -1;
   private static final int HUE_STEPS = 20;

   private ColorPickerRenderer() {
   }

   public static void BMJr(float var0, float var1, float var2, int var3, boolean var4) {
      RenderUtils.XNRNki(var0 - 0.5F, var1 - 0.5F, var0 + var2 + 0.5F, var1 + var2 + 0.5F, -12829626);
      if (var4) {
         drawCheckerboard(var0, var1, var0 + var2, var1 + var2, 2.0F);
      }

      RenderUtils.XNRNki(var0, var1, var0 + var2, var1 + var2, var3);
   }

   public static void drawColorPicker(ColorPickerLayout var0, ColorSetting var1, HsbColor var2, boolean var3) {
      float var4 = var3 ? var2.getBrightness() : var1.pBf3();
      float var5 = var3 ? var2.getSaturation() : var1.getSaturation();
      boolean var6 = var4 < 0.001F;
      boolean var7 = var5 < 0.001F;
      if (!var3 && !var6) {
         var2.MHxl8(var1.getHue(), var1.getSaturation(), var4);
      }

      float var8 = (!var3 && !var6 && !var7 ? var1.getHue() : var2.ecjE()) / 360.0F;
      float var9 = !var3 && !var6 ? var5 : var2.getSaturation();
      drawSaturationValuePicker(var0, var8, var9, var4);
      omcgktX(var0, var8);
      if (var1.supportsAlpha()) {
         drawAlphaBar(var0, var1);
      }
   }

   private static void drawSaturationValuePicker(ColorPickerLayout var0, float var1, float var2, float var3) {
      RenderUtils.XNRNki(var0.squareLeft, var0.squareTop, var0.squareRight, var0.squareBottom, Color.HSBtoRGB(var1, 1.0F, 1.0F) | 0xFF000000);
      RenderUtils.drawHorizontalGradient(var0.squareLeft, var0.squareTop, var0.squareRight, var0.squareBottom, -1, 16777215);
      RenderUtils.drawVerticalGradient(var0.squareLeft, var0.squareTop, var0.squareRight, var0.squareBottom, 0, -16777216);
      oHei(var0.squareLeft, var0.squareTop, var0.squareRight, var0.squareBottom);
      float var4 = var0.squareLeft + var2 * (var0.squareRight - var0.squareLeft);
      float var5 = var0.squareTop + (1.0F - var3) * (var0.squareBottom - var0.squareTop);
      RenderUtils.XNRNki(var4 - 2.0F, var5, var4 + 3.0F, var5 + 1.0F, -1);
      RenderUtils.XNRNki(var4, var5 - 2.0F, var4 + 1.0F, var5 + 3.0F, -1);
   }

   private static void omcgktX(ColorPickerLayout var0, float var1) {
      float var2 = (var0.squareBottom - var0.squareTop) / 20.0F;

      for (int var3 = 0; var3 < 20; var3++) {
         float var4 = var3 / 20.0F;
         float var5 = (var3 + 1) / 20.0F;
         RenderUtils.drawVerticalGradient(
            var0.idU,
            var0.squareTop + var3 * var2,
            var0.hueBarRight,
            var0.squareTop + (var3 + 1) * var2,
            Color.HSBtoRGB(var4, 1.0F, 1.0F) | 0xFF000000,
            Color.HSBtoRGB(var5, 1.0F, 1.0F) | 0xFF000000
         );
      }

      oHei(var0.idU, var0.squareTop, var0.hueBarRight, var0.squareBottom);
      float var6 = var0.squareTop + clampUnit(var1) * (var0.squareBottom - var0.squareTop);
      RenderUtils.XNRNki(var0.idU - 1.0F, var6 - 1.0F, var0.hueBarRight + 1.0F, var6 + 2.0F, -1);
   }

   private static void drawAlphaBar(ColorPickerLayout var0, ColorSetting var1) {
      drawCheckerboard(var0.alphaBarLeft, var0.squareTop, var0.XUYtPq, var0.squareBottom, 4.0F);
      int var2 = var1.getRgb();
      RenderUtils.drawVerticalGradient(var0.alphaBarLeft, var0.squareTop, var0.XUYtPq, var0.squareBottom, var2 & 16777215, var2 | 0xFF000000);
      oHei(var0.alphaBarLeft, var0.squareTop, var0.XUYtPq, var0.squareBottom);
      float var3 = var0.squareTop + var1.JIjrD() / 255.0F * (var0.squareBottom - var0.squareTop);
      RenderUtils.XNRNki(var0.alphaBarLeft - 1.0F, var3 - 1.0F, var0.XUYtPq + 1.0F, var3 + 2.0F, -1);
   }

   private static void drawCheckerboard(float var0, float var1, float var2, float var3, float var4) {
      int var5 = 0;

      for (float var6 = var1; var6 < var3; var5++) {
         int var7 = 0;

         for (float var8 = var0; var8 < var2; var7++) {
            RenderUtils.XNRNki(var8, var6, Math.min(var8 + var4, var2), Math.min(var6 + var4, var3), (var5 + var7 & 1) == 0 ? -10066330 : -6710887);
            var8 += var4;
         }

         var6 += var4;
      }
   }

   private static void oHei(float var0, float var1, float var2, float var3) {
      RenderUtils.drawRectOutline(var0 - 1.0F, var1 - 1.0F, var2 + 1.0F, var3 + 1.0F, 1.0F, -12829626);
   }

   private static float clampUnit(float var0) {
      return Math.max(0.0F, Math.min(1.0F, var0));
   }
}
