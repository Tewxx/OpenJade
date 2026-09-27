// Jade recovery: original class: jade.deps.eLz.LuteD0
package jade.client.module.render.nametags;

import jade.client.common.IFont$0;
import jade.client.common.IFont;
import net.minecraft.client.gui.FontRenderer;

public final class MinecraftFont implements IFont {
   private final FontRenderer fontRenderer;
   private final float scale;
   private final ScaledFontMetrics metrics;

   public MinecraftFont(FontRenderer var1) {
      this(var1, 1.0F);
   }

   public MinecraftFont(FontRenderer var1, float var2) {
      this.fontRenderer = var1;
      this.scale = Math.max(0.5F, Math.min(2.0F, var2));
      this.metrics = new ScaledFontMetrics(var1, this.scale);
   }

   @Override
   public int drawString(String var1, float var2, float var3, int var4, boolean var5) {
      return ScaledStringRenderer.draw(this.fontRenderer, this.scale, var1, var2, var3, var4, var5);
   }

   @Override
   public int drawGlyphString(String var1, float var2, float var3, IFont$0 var4, boolean var5) {
      return new GlyphStringDrawer(this.fontRenderer, this.scale).draw(var1, var2, var3, var4, var5, this);
   }

   @Override
   public int getStringWidth(String var1) {
      return this.metrics.width(var1);
   }

   @Override
   public int getFontHeight() {
      return this.metrics.height();
   }

   @Override
   public int getLineHeight() {
      return this.metrics.height();
   }

   @Override
   public int getTextTopOffset() {
      return 0;
   }

   @Override
   public int getTextBottomOffset() {
      return this.metrics.bottom();
   }

   public float getScale() {
      return this.scale;
   }

   public int getUnscaledStringWidth(String var1) {
      return this.fontRenderer.getStringWidth(var1);
   }

   public boolean usesUnicodeFont() {
      return this.fontRenderer.getUnicodeFlag();
   }
}
