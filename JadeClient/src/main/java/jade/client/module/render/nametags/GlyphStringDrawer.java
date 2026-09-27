// Jade recovery: original class: jade.deps.eLz.ch2vvJN1s
package jade.client.module.render.nametags;

import jade.client.common.IFont$0;
import jade.client.common.TextRenderer;
import net.minecraft.client.gui.FontRenderer;

public final class GlyphStringDrawer {
   private final FontRenderer metrics;
   private final float scale;

   public GlyphStringDrawer(FontRenderer var1, float var2) {
      this.metrics = var1;
      this.scale = var2;
   }

   public int draw(String var1, float var2, float var3, IFont$0 var4, boolean var5, TextRenderer var6) {
      if (var1 == null) {
         return 0;
      } else {
         FormattingState var7 = new FormattingState();
         int var8 = 0;
         int var9 = 0;

         while (var8 < var1.length()) {
            char var10 = var1.charAt(var8++);
            if (var10 == 167 && var8 < var1.length()) {
               var7.accept(var1.charAt(var8++), var1x -> this.metrics.getColorCode((char)var1x));
            } else if (var10 != '\n') {
               int var11 = this.metrics.getCharWidth(var10);
               if (var11 >= 0) {
                  int var12 = var7.advance(var11);
                  float var13 = var9 * this.scale;
                  int var14 = var4.colorForGlyph(var10, var13, var12 * this.scale, var7.color());
                  var6.drawString(var7.decorate(var10), var2 + var13, var3, var14, var5);
                  var9 += var12;
               }
            }
         }

         return Math.round(var9 * this.scale);
      }
   }
}
