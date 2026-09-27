// Jade recovery: original class: jade.deps.eLz.pB7pen3i7
package jade.client.module.render.shared;

import jade.client.common.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL13;

public final class KawaseBlurPipeline {
   private KawaseBlurPipeline() {
   }

   public static Framebuffer runBlurPipeline(Minecraft var0, FramebufferPool var1, ShaderProgram var2, ShaderProgram var3, int var4, int var5, float var6) {
      var1.ensurePool(var0.displayWidth, var0.displayHeight, var5);
      runDownPass(var0, var1, var2, var5, var6);
      runUpPass(var1, var3, var5, var6);
      runCompositePass(var1, var3, var4, var6);
      PMSVgi(var0, var1.getPrimaryFramebuffer());
      return var1.getPrimaryFramebuffer();
   }

   private static void runDownPass(Minecraft var0, FramebufferPool var1, ShaderProgram var2, int var3, float var4) {
      BlurPass.WeKimn(var1.uSvo(1), var0.getFramebuffer().framebufferTexture, var2, var4);

      for (int var5 = 1; var5 < var3; var5++) {
         BlurPass.WeKimn(var1.uSvo(var5 + 1), var1.uSvo(var5).framebufferTexture, var2, var4);
      }
   }

   private static void runUpPass(FramebufferPool var0, ShaderProgram var1, int var2, float var3) {
      for (int var4 = var2; var4 > 1; var4--) {
         BlurPass.WeKimn(var0.uSvo(var4 - 1), var0.uSvo(var4).framebufferTexture, var1, var3);
      }
   }

   private static void runCompositePass(FramebufferPool var0, ShaderProgram var1, int var2, float var3) {
      Framebuffer var4 = var0.getPrimaryFramebuffer();
      var4.framebufferClear();
      var4.bindFramebuffer(false);
      var1.eBml();
      var1.setUniform("offset", var3, var3);
      var1.lyik("inTexture", 0);
      var1.lyik("check", 1);
      var1.lyik("textureToCheck", 16);
      var1.setUniform("halfpixel", 1.0F / var4.framebufferWidth, 1.0F / var4.framebufferHeight);
      var1.setUniform("iResolution", var4.framebufferWidth, var4.framebufferHeight);
      nngJh(34000, var2);
      nngJh(33984, var0.uSvo(1).framebufferTexture);
      ShaderProgram.drawFullScreenQuad();
      var1.unbindProgram();
   }

   private static void nngJh(int var0, int var1) {
      GL13.glActiveTexture(var0);
      RenderUtils.lrTz(var1);
   }

   private static void PMSVgi(Minecraft var0, Framebuffer var1) {
      var0.getFramebuffer().bindFramebuffer(true);
      RenderUtils.lrTz(var1.framebufferTexture);
      RenderUtils.setAlphaThreshold(0.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      ShaderProgram.drawFullScreenQuad();
      GlStateManager.bindTexture(0);
      GlStateManager.disableBlend();
   }
}
