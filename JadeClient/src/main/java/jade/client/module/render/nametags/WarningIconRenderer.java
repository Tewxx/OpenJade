// Jade recovery: original class: jade.deps.eLz.epORIYa
package jade.client.module.render.nametags;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public final class WarningIconRenderer {
   public static final int ICON_WIDTH = 9;
   public static final int KEmt = 8;

   private WarningIconRenderer() {
   }

   public static void VyqlhA(float var0, float var1) {
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glColor4f(0.96F, 0.65F, 0.14F, 1.0F);
      GL11.glLineWidth(1.25F);
      GL11.glBegin(2);
      GL11.glVertex2f(var0 + 4.5F, var1);
      GL11.glVertex2f(var0 + 8.5F, var1 + 8.0F);
      GL11.glVertex2f(var0 + 0.5F, var1 + 8.0F);
      GL11.glEnd();
      GL11.glBegin(1);
      GL11.glVertex2f(var0 + 4.5F, var1 + 2.5F);
      GL11.glVertex2f(var0 + 4.5F, var1 + 5.3F);
      GL11.glEnd();
      GL11.glPointSize(1.8F);
      GL11.glBegin(0);
      GL11.glVertex2f(var0 + 4.5F, var1 + 6.6F);
      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
   }
}
