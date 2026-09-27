// Jade recovery: original class: jade.deps.eLz.pNGaPp2k
package jade.client.module.render.shared;

import jade.client.common.RenderUtils;
import net.minecraft.client.shader.Framebuffer;

public final class BloomBlurFramebuffers {
   private Framebuffer TsK = new Framebuffer(1, 1, false);
   private Framebuffer bloomBuffer = new Framebuffer(1, 1, false);

   public void refreshBlurBuffer() {
      this.TsK = resizeAndClearBuffer(this.TsK);
   }

   public void refreshBloomBuffer() {
      this.bloomBuffer = resizeAndClearBuffer(this.bloomBuffer);
   }

   public void applyKawaseBlur(int var1, float var2) {
      this.TsK.unbindFramebuffer();
      KawaseBlur.renderBlur(this.TsK.framebufferTexture, var1, var2);
   }

   public void bsopeuR(int var1, float var2, boolean var3) {
      this.bloomBuffer.unbindFramebuffer();
      BloomRenderer.renderBloom(this.bloomBuffer.framebufferTexture, var1, var2, var3);
   }

   public void captureSceneForBloom(Runnable var1, int var2, float var3, boolean var4) {
      if (var1 != null) {
         this.refreshBloomBuffer();
         var1.run();
         this.bsopeuR(var2, var3, var4);
      }
   }

   private static Framebuffer resizeAndClearBuffer(Framebuffer var0) {
      Framebuffer var1 = RenderUtils.Gebxuy(var0);
      var1.framebufferClear();
      var1.bindFramebuffer(false);
      return var1;
   }
}
