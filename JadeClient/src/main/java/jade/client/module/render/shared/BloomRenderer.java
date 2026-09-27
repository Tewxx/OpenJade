// Jade recovery: original class: jade.deps.eLz.sUjOLYSbm
package jade.client.module.render.shared;

import jade.client.common.IMinecraft;
import jade.client.common.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class BloomRenderer implements IMinecraft {
   public static ShaderProgram kawaseDownBloomShader = new ShaderProgram("kawaseDownBloom");
   public static ShaderProgram kawaseUpBloomShader = new ShaderProgram("kawaseUpBloom");
   public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
   private static final FramebufferPool framebufferPool = new FramebufferPool(2.0, false);

   private BloomRenderer() {
   }

   public static void JLYQm(int var0, int var1, float var2) {
      renderBloom(var0, var1, var2, true);
   }

   public static void renderBloom(int var0, int var1, float var2, boolean var3) {
      var1 = Math.max(1, var1);
      framebufferPool.ensurePool(mc.displayWidth, mc.displayHeight, var1);
      framebuffer = framebufferPool.getPrimaryFramebuffer();
      RenderUtils.setAlphaThreshold(0.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(1, 1);
      GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      BlurPass.WeKimn(framebufferPool.uSvo(1), var0, kawaseDownBloomShader, var2);

      for (int var4 = 1; var4 < var1; var4++) {
         float var5 = HybgOv.Ujez(var2, var4, 1.5);
         BlurPass.WeKimn(framebufferPool.uSvo(var4 + 1), framebufferPool.uSvo(var4).framebufferTexture, kawaseDownBloomShader, var5);
      }

      for (int var7 = var1; var7 > 1; var7--) {
         float var9 = HybgOv.Ujez(var2, var7 - 1, 1.5);
         BlurPass.WeKimn(framebufferPool.uSvo(var7 - 1), framebufferPool.uSvo(var7).framebufferTexture, kawaseUpBloomShader, var9);
      }

      Framebuffer var8 = framebufferPool.getPrimaryFramebuffer();
      var8.framebufferClear();
      var8.bindFramebuffer(false);
      kawaseUpBloomShader.eBml();
      kawaseUpBloomShader.setUniform("offset", var2, var2);
      kawaseUpBloomShader.lyik("inTexture", 0);
      kawaseUpBloomShader.lyik("check", var3 ? 1 : 0);
      if (var3) {
         kawaseUpBloomShader.lyik("textureToCheck", 16);
      }

      kawaseUpBloomShader.setUniform("halfpixel", 1.0F / var8.framebufferWidth, 1.0F / var8.framebufferHeight);
      kawaseUpBloomShader.setUniform("iResolution", var8.framebufferWidth, var8.framebufferHeight);
      if (var3) {
         GlStateManager.setActiveTexture(34000);
         RenderUtils.lrTz(var0);
         GlStateManager.setActiveTexture(33984);
      }

      RenderUtils.lrTz(framebufferPool.uSvo(1).framebufferTexture);
      ShaderProgram.drawFullScreenQuad();
      kawaseUpBloomShader.unbindProgram();
      GlStateManager.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
      mc.getFramebuffer().bindFramebuffer(false);
      RenderUtils.lrTz(framebufferPool.getPrimaryFramebuffer().framebufferTexture);
      RenderUtils.setAlphaThreshold(0.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      ShaderProgram.drawFullScreenQuad();
      GlStateManager.bindTexture(0);
      RenderUtils.setAlphaThreshold(0.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
   }
}
