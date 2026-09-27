// Jade recovery: original class: jade.deps.eLz.rUgtE3Vfu
package jade.client.module.render.nametags;

import net.minecraft.client.gui.FontRenderer;

public final class ScaledFontMetrics {
   private final FontRenderer font;
   private final float factor;

   public ScaledFontMetrics(FontRenderer var1, float var2) {
      this.font = var1;
      this.factor = var2;
   }

   public int width(String var1) {
      return this.scale(this.font.getStringWidth(var1));
   }

   public int height() {
      return this.scale(this.font.FONT_HEIGHT);
   }

   public int bottom() {
      return Math.max(1, this.scale(this.font.FONT_HEIGHT - 1.0F));
   }

   private int scale(float var1) {
      return Math.round(var1 * this.factor);
   }
}
