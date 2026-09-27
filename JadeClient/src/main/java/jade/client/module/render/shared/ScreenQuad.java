// Jade recovery: original class: jade.deps.eLz.NFgFaZ1
package jade.client.module.render.shared;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public final class ScreenQuad {
   private ScreenQuad() {
   }

   public static void drawFullScreenQuad() {
      ScaledResolution var0 = new ScaledResolution(Minecraft.getMinecraft());
      drawTexturedQuad(0.0F, 0.0F, (float)var0.getScaledWidth_double(), (float)var0.getScaledHeight_double(), 0.0F, 1.0F, 1.0F, 0.0F);
   }

   public static void drawQuad(float var0, float var1, float var2, float var3) {
      drawTexturedQuad(var0, var1, var2, var3, 0.0F, 0.0F, 1.0F, 1.0F);
   }

   public static void drawTexturedQuad(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GL11.glBegin(7);
      addVertex(var0, var1, var4, var5);
      addVertex(var0, var1 + var3, var4, var7);
      addVertex(var0 + var2, var1 + var3, var6, var7);
      addVertex(var0 + var2, var1, var6, var5);
      GL11.glEnd();
   }

   private static void addVertex(float var0, float var1, float var2, float var3) {
      GL11.glTexCoord2f(var2, var3);
      GL11.glVertex2f(var0, var1);
   }
}
