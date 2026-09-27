// Jade recovery: original class: jade.deps.eLz.XUpBHBdv
package jade.client.module.render.shared;

import jade.client.common.RenderUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class FramebufferPool {
   private final List<Framebuffer> framebuffers = new ArrayList<>();
   private final double downscaleFactor;
   private final boolean useDepth;
   private int cachedIterations = Integer.MIN_VALUE;

   public FramebufferPool(double var1, boolean var3) {
      this.downscaleFactor = var1;
      this.useDepth = var3;
   }

   public void ensurePool(int var1, int var2, int var3) {
      if (this.cachedIterations != var3 || this.framebuffers.isEmpty() || this.framebuffers.get(0).framebufferWidth != var1 || this.framebuffers.get(0).framebufferHeight != var2) {
         this.deleteAll();
         this.framebuffers.add(RenderUtils.resizeFramebuffer(null, this.useDepth));

         for (int var4 = 1; var4 <= var3; var4++) {
            Framebuffer var5 = new Framebuffer(HybgOv.YuPwh(var1, var4, this.downscaleFactor), HybgOv.YuPwh(var2, var4, this.downscaleFactor), false);
            var5.setFramebufferFilter(9729);
            GlStateManager.bindTexture(var5.framebufferTexture);
            GL11.glTexParameteri(3553, 10242, 33648);
            GL11.glTexParameteri(3553, 10243, 33648);
            GlStateManager.bindTexture(0);
            this.framebuffers.add(var5);
         }

         this.cachedIterations = var3;
      }
   }

   public Framebuffer uSvo(int var1) {
      return this.framebuffers.get(var1);
   }

   public Framebuffer getPrimaryFramebuffer() {
      return this.uSvo(0);
   }

   private void deleteAll() {
      for (Framebuffer var2 : this.framebuffers) {
         var2.deleteFramebuffer();
      }

      this.framebuffers.clear();
   }
}
