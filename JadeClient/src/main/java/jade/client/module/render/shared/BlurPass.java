// Jade recovery: original class: jade.deps.eLz.wPx5DQz3s
package jade.client.module.render.shared;

import jade.client.common.RenderUtils;
import net.minecraft.client.shader.Framebuffer;

public final class BlurPass {
   private BlurPass() {
   }

   public static void WeKimn(Framebuffer var0, int var1, ShaderProgram var2, float var3) {
      var0.framebufferClear();
      var0.bindFramebuffer(false);
      var2.eBml();
      RenderUtils.lrTz(var1);
      var2.setUniform("offset", var3, var3);
      var2.lyik("inTexture", 0);
      var2.lyik("check", 0);
      var2.setUniform("halfpixel", 1.0F / var0.framebufferWidth, 1.0F / var0.framebufferHeight);
      var2.setUniform("iResolution", var0.framebufferWidth, var0.framebufferHeight);
      ShaderProgram.drawFullScreenQuad();
      var2.unbindProgram();
   }
}
