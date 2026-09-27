// Jade recovery: original class: jade.deps.eLz.oByjXT4yV
package jade.client.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public final class GlyphRasterizer {
   public static final int MARGIN = 4;
   private final Font font;
   private final boolean antiAlias;
   private final float drawScale;
   private final TextureUploader uploader = new TextureUploader();

   public GlyphRasterizer(Font var1, boolean var2, float var3) {
      this.font = var1;
      this.antiAlias = var2;
      this.drawScale = var3;
   }

   public RasterizedGlyph create(char var1) {
      if (Character.isISOControl(var1) && var1 != '\n') {
         return empty();
      } else {
         BufferedImage var2 = new BufferedImage(1, 1, 2);
         Graphics2D var3 = var2.createGraphics();

         RasterizedGlyph var22;
         try {
            this.configure(var3);
            FontMetrics var4 = var3.getFontMetrics();
            Rectangle2D var5 = var4.getStringBounds(String.valueOf(var1), var3);
            float var6 = Math.max(1.0F, (float)var5.getWidth());
            int var7 = Math.max(1, (int)Math.ceil(var6) + 8);
            int var8 = Math.max(1, var4.getHeight());
            BufferedImage var9 = new BufferedImage(var7, var8, 2);
            Graphics2D var10 = var9.createGraphics();

            try {
               this.configure(var10);
               var10.setBackground(new Color(255, 255, 255, 0));
               var10.clearRect(0, 0, var7, var8);
               var10.setColor(Color.WHITE);
               var10.drawString(String.valueOf(var1), 4, var4.getAscent());
            } finally {
               var10.dispose();
            }

            int[] var11 = visibleRows(var9);
            byte[] var12 = new byte[var7 * var8];

            for (int var13 = 0; var13 < var8; var13++) {
               for (int var14 = 0; var14 < var7; var14++) {
                  var12[var13 * var7 + var14] = (byte)(var9.getRGB(var14, var13) >>> 24);
               }
            }

            var22 = new RasterizedGlyph(this.uploader.upload(var9), var7, var8, var6, var6 * this.drawScale, var11[0], var11[1], var12);
         } finally {
            var3.dispose();
         }

         return var22;
      }
   }

   private void configure(Graphics2D var1) {
      var1.setFont(this.font);
      var1.setRenderingHint(
         RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
      );
      var1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
      var1.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
      var1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
      var1.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      var1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var1.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
   }

   private static int[] visibleRows(BufferedImage var0) {
      int var1 = -1;
      int var2 = -1;

      for (int var3 = 0; var3 < var0.getHeight(); var3++) {
         for (int var4 = 0; var4 < var0.getWidth(); var4++) {
            if ((var0.getRGB(var4, var3) >>> 24 & 0xFF) != 0) {
               if (var1 < 0) {
                  var1 = var3;
               }

               var2 = var3 + 1;
               break;
            }
         }
      }

      return var1 < 0 ? new int[]{0, 0} : new int[]{var1, var2};
   }

   public static RasterizedGlyph empty() {
      return new RasterizedGlyph(0, 0.0F, 0.0F, 0.0F, 0.0F, 0, 0, null);
   }
}
