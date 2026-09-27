// Jade recovery: original class: jade.deps.eLz.kX2Lz1
package jade.client.common;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public final class GlyphRenderer {
   public void push() {
      GL11.glPushMatrix();
   }

   public void begin(float var1) {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glEnable(3553);
      GlStateManager.enableTexture2D();
      GL11.glTexEnvi(8960, 8704, 8448);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.scale(var1, var1, 1.0F);
   }

   public void glyph(RasterizedGlyph var1, float var2, float var3, int var4) {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.bindTexture(var1.texture);
      GlStateManager.color((var4 >>> 16 & 0xFF) / 255.0F, (var4 >>> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24 & 0xFF) / 255.0F);
      GL11.glBegin(7);
      vertex(var2, var3, 0.0F, 0.0F);
      vertex(var2, var3 + var1.height, 0.0F, 1.0F);
      vertex(var2 + var1.width, var3 + var1.height, 1.0F, 1.0F);
      vertex(var2 + var1.width, var3, 1.0F, 0.0F);
      GL11.glEnd();
   }

   public void end() {
      GlStateManager.bindTexture(0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      GlStateManager.matrixMode(5888);
   }

   private static void vertex(float var0, float var1, float var2, float var3) {
      GL11.glTexCoord2f(var2, var3);
      GL11.glVertex2f(var0, var1);
   }
}
