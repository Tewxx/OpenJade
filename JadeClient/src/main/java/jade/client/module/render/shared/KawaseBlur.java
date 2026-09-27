// Jade recovery: original class: jade.deps.eLz.bS7iRXPt9d
package jade.client.module.render.shared;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public final class KawaseBlur {
   public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
   private static final FramebufferPool framebufferPool = new FramebufferPool(3.0, false);
   public static ShaderProgram kawaseUpShader = xpfoxL("kawaseUp");
   private static final Minecraft mc = Minecraft.getMinecraft();
   public static ShaderProgram kawaseDownShader = xpfoxL("kawaseDown");

   private KawaseBlur() {
   }

   public static void renderBlur(int var0, int var1, float var2) {
      framebuffer = KawaseBlurPipeline.runBlurPipeline(mc, framebufferPool, kawaseDownShader, kawaseUpShader, var0, var1, var2);
   }

   private static ShaderProgram xpfoxL(String var0) {
      return new ShaderProgram(var0);
   }
}
