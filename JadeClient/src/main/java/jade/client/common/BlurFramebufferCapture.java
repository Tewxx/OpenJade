// Jade recovery: original class: jade.deps.eLz.jT0J7DG1
package jade.client.common;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class BlurFramebufferCapture {
   private Framebuffer framebuffer;
   private int previousFramebufferId;
   private boolean captureActive;
   private final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(16);
   private boolean previousDepthMask;
   private boolean previousDepthTest;
   private boolean XQr;

   public void beginBlurCapture(float var1) {
      this.captureActive = false;
      if (!(var1 >= 1.0F) && OpenGlHelper.isFramebufferEnabled()) {
         Minecraft var2 = Minecraft.getMinecraft();
         ((Buffer)this.byteBuffer).clear();
         GL11.glGetBoolean(3107, this.byteBuffer);
         this.previousDepthMask = GL11.glGetBoolean(2930);
         this.previousDepthTest = GL11.glIsEnabled(2929);
         this.XQr = GL11.glIsEnabled(3008);
         this.previousFramebufferId = GL11.glGetInteger(36006);
         if (this.framebuffer == null || this.framebuffer.framebufferWidth != var2.displayWidth || this.framebuffer.framebufferHeight != var2.displayHeight) {
            this.jbgV();
            this.framebuffer = new Framebuffer(var2.displayWidth, var2.displayHeight, true);
            this.framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
         }

         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.previousFramebufferId);
         GlStateManager.bindTexture(this.framebuffer.framebufferTexture);
         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var2.displayWidth, var2.displayHeight);
         this.framebuffer.bindFramebuffer(false);
         boolean var3 = GL11.glIsEnabled(3089);
         GL11.glDisable(3089);
         GlStateManager.colorMask(false, false, false, true);
         GlStateManager.clearColor(0.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.depthMask(true);
         GlStateManager.clear(16640);
         GlStateManager.depthMask(false);
         GlStateManager.colorMask(true, true, true, false);
         if (var3) {
            GL11.glEnable(3089);
         }

         this.captureActive = true;
      }
   }

   public void drawCapturedFrame(float var1, float var2, float var3) {
      if (this.captureActive) {
         this.captureActive = false;
         GlStateManager.colorMask(true, true, true, true);
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.previousFramebufferId);
         GlStateManager.disableDepth();
         GlStateManager.disableLighting();
         GlStateManager.enableTexture2D();
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color(1.0F, 1.0F, 1.0F, var1);
         this.framebuffer.bindFramebufferTexture();
         float var4 = (float)this.framebuffer.framebufferWidth / this.framebuffer.framebufferTextureWidth;
         float var5 = (float)this.framebuffer.framebufferHeight / this.framebuffer.framebufferTextureHeight;
         GL11.glBegin(7);
         GL11.glTexCoord2f(0.0F, var5);
         GL11.glVertex2f(0.0F, 0.0F);
         GL11.glTexCoord2f(0.0F, 0.0F);
         GL11.glVertex2f(0.0F, var3);
         GL11.glTexCoord2f(var4, 0.0F);
         GL11.glVertex2f(var2, var3);
         GL11.glTexCoord2f(var4, var5);
         GL11.glVertex2f(var2, 0.0F);
         GL11.glEnd();
         this.framebuffer.unbindFramebufferTexture();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.colorMask(this.byteBuffer.get(0) != 0, this.byteBuffer.get(1) != 0, this.byteBuffer.get(2) != 0, this.byteBuffer.get(3) != 0);
         GlStateManager.depthMask(this.previousDepthMask);
         if (this.XQr) {
            GlStateManager.enableAlpha();
         } else {
            GlStateManager.disableAlpha();
         }

         if (this.previousDepthTest) {
            GlStateManager.enableDepth();
         } else {
            GlStateManager.disableDepth();
         }
      }
   }

   public void jbgV() {
      if (this.framebuffer != null) {
         this.framebuffer.deleteFramebuffer();
      }

      this.framebuffer = null;
   }
}
