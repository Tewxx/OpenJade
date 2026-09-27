// Jade recovery: original class: jade.deps.eLz.aBo7uyKY0
package jade.client.common;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public final class TrueTypeFont implements IFont {
   private static final String METRIC_SAMPLE = "ABCDEFGHOKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   private final Font font;
   private final float drawScale;
   private final float rawScale;
   private final GlyphCache glyphs;
   private final GlyphRenderer painter = new GlyphRenderer();
   private final float rawTop;
   private final float fontHeight;
   private final float lineHeight;

   public TrueTypeFont(Font var1, boolean var2) {
      this.rawScale = OOPXSd.resolve();
      this.drawScale = 1.0F / this.rawScale;
      this.font = var1.deriveFont(var1.getStyle(), Math.max(1.0F, var1.getSize2D() * this.rawScale));
      this.glyphs = new GlyphCache(new GlyphRasterizer(this.font, var2, this.drawScale));
      this.rawTop = this.findRawTop();
      float var3 = this.findRawBottom();
      this.fontHeight = Math.max(1.0F, (var3 - this.rawTop) * this.drawScale);
      FontRenderContext var4 = new FontRenderContext(new AffineTransform(), var2, true);
      this.lineHeight = Math.max(
         this.fontHeight, (float)this.font.getStringBounds("ABCDEFGHOKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", var4).getHeight() * this.drawScale
      );
   }

   @Override
   public int drawString(String var1, float var2, float var3, int var4, boolean var5) {
      if (this.unavailable(var1)) {
         return 0;
      } else {
         int var6 = var5 ? this.drawPass(var1, var2 + 0.5F, var3 + 0.5F, var4, null, true) : 0;
         return Math.max(var6, this.drawPass(var1, var2, var3, var4, null, false));
      }
   }

   @Override
   public int drawGlyphString(String var1, float var2, float var3, IFont$0 var4, boolean var5) {
      if (this.unavailable(var1)) {
         return 0;
      } else {
         int var6 = var5 ? this.drawPass(var1, var2 + 0.5F, var3 + 0.5F, 0, var4, true) : 0;
         return Math.max(var6, this.drawPass(var1, var2, var3, 0, var4, false));
      }
   }

   private boolean unavailable(String var1) {
      return this.glyphs.destroyed() || var1 == null || var1.isEmpty();
   }

   private int drawPass(String var1, float var2, float var3, int var4, IFont$0 var5, boolean var6) {
      int var7 = pGYEwTJtYO.opaqueAlpha(var4);
      int var8 = var6 ? pGYEwTJtYO.shadow(var4) : pGYEwTJtYO.withAlpha(var4, var7);
      Integer var9 = null;
      float var10 = var2 * this.rawScale;
      float var11 = var10;
      float var12 = var3 * this.rawScale - this.rawTop;
      this.painter.push();

      try {
         this.painter.begin(this.drawScale);

         for (int var13 = 0; var13 < var1.length(); var13++) {
            char var14 = var1.charAt(var13);
            if (!pGYEwTJtYO.malformedPrefix(var1, var13)) {
               if (var14 == 167 && var13 + 1 < var1.length()) {
                  char var20 = Character.toLowerCase(var1.charAt(++var13));
                  int var22 = "0123456789abcdefklmnor".indexOf(var20);
                  if (var22 >= 0 && var22 < 16) {
                     if (var5 == null) {
                        var8 = pGYEwTJtYO.minecraftColor(var22, var7, var6);
                     } else {
                        var9 = pGYEwTJtYO.minecraftColor(var22, 255, false);
                     }
                  } else if (var20 == 'r') {
                     if (var5 == null) {
                        var8 = var6 ? pGYEwTJtYO.shadow(var4) : pGYEwTJtYO.withAlpha(var4, var7);
                     } else {
                        var9 = null;
                     }
                  }
               } else if (var14 == '\n') {
                  var11 = var10;
                  var12 += this.lineHeight * this.rawScale;
               } else {
                  RasterizedGlyph var15 = this.glyphs.glyph(var14);
                  int var16 = var8;
                  if (var5 != null) {
                     var16 = var5.colorForGlyph(var14, (var11 - var10) * this.drawScale, var15.advance, var9);
                     var16 = pGYEwTJtYO.withAlpha(var16, pGYEwTJtYO.opaqueAlpha(var16));
                     if (var6) {
                        var16 = pGYEwTJtYO.shadow(var16);
                     }
                  }

                  if (var15.texture != 0 && var15.width > 0.0F && var15.height > 0.0F) {
                     this.painter.glyph(var15, var11 - 4.0F, var12, var16);
                  }

                  var11 += var15.rawAdvance;
               }
            }
         }
      } finally {
         this.painter.end();
      }

      return Math.round((var11 - var10) * this.drawScale);
   }

   @Override
   public int getStringWidth(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         float var2 = 0.0F;

         for (int var3 = 0; var3 < var1.length(); var3++) {
            char var4 = var1.charAt(var3);
            if (!pGYEwTJtYO.malformedPrefix(var1, var3)) {
               if (var4 == 167 && var3 + 1 < var1.length()) {
                  var3++;
               } else if (var4 != '\n') {
                  var2 += this.glyphs.glyph(var4).advance;
               }
            }
         }

         return Math.round(var2);
      } else {
         return 0;
      }
   }

   @Override
   public int getFontHeight() {
      return Math.round(this.fontHeight);
   }

   @Override
   public int getLineHeight() {
      return Math.round(this.lineHeight);
   }

   @Override
   public int getTextTopOffset() {
      return 0;
   }

   @Override
   public int getTextBottomOffset() {
      return Math.round(this.fontHeight);
   }

   @Override
   public void destroy() {
      this.glyphs.destroy();
   }

   public GlyphMetrics getExternalGlyph(char var1) {
      RasterizedGlyph var2 = this.glyphs.glyph(var1);
      if (var2.coverage == null) {
         return null;
      } else {
         if (var2.external == null) {
            var2.external = new GlyphMetrics(
               (int)var2.width,
               (int)var2.height,
               -4.0F * this.drawScale,
               -this.rawTop * this.drawScale,
               var2.width * this.drawScale,
               var2.height * this.drawScale,
               var2.advance,
               var2.coverage
            );
         }

         return var2.external;
      }
   }

   private float findRawTop() {
      float var1 = Float.MAX_VALUE;

      for (int var2 = 0; var2 < "ABCDEFGHOKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length(); var2++) {
         RasterizedGlyph var3 = this.glyphs.glyph("ABCDEFGHOKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(var2));
         if (var3.visible()) {
            var1 = Math.min(var1, (float)var3.visibleTop);
         }
      }

      return var1 == Float.MAX_VALUE ? 0.0F : var1;
   }

   private float findRawBottom() {
      float var1 = 0.0F;

      for (int var2 = 0; var2 < "ABCDEFGHOKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length(); var2++) {
         RasterizedGlyph var3 = this.glyphs.glyph("ABCDEFGHOKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(var2));
         if (var3.visible()) {
            var1 = Math.max(var1, (float)var3.visibleBottom);
         }
      }

      return var1 <= 0.0F ? Math.max(1.0F, this.font.getSize2D()) : var1;
   }
}
