// Jade recovery: original class: jade.deps.eLz.dCg3iS
package jade.client.module.render.nametags;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public final class ScaledStringRenderer {
   private ScaledStringRenderer() {
   }

   public static int draw(FontRenderer var0, float var1, String var2, float var3, float var4, int var5, boolean var6) {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GL11.glEnable(3553);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      boolean var7 = var1 != 1.0F;
      if (var7) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(var3, var4, 0.0F);
         GlStateManager.scale(var1, var1, 1.0F);
      }

      int var8 = var0.drawString(var2, var7 ? 0.0F : var3, var7 ? 0.0F : var4, var5, var6);
      if (var7) {
         GlStateManager.popMatrix();
      }

      return var7 ? Math.round(var8 * var1) : var8;
   }
}
