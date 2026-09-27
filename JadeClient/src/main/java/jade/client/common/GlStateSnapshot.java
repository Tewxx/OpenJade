// Jade recovery: original class: jade.deps.eLz.G7wkA1zwH
package jade.client.common;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public final class GlStateSnapshot implements AutoCloseable {
   private final int savedMatrixMode = GL11.glGetInteger(2976);
   private final int activeTextureUnit = GL11.glGetInteger(34016);
   private final int framebufferBinding = GL11.glGetInteger(36006);
   private final int currentProgram = GL11.glGetInteger(35725);
   private final int[] trackedCapabilities = new int[]{3042, 2929, 3008, 2884, 2896, 32826, 2903, 16384, 16385};
   private static final int GL_RESCALE_NORMAL = 32826;
   private final boolean[] capabilityStates = new boolean[this.trackedCapabilities.length];
   private final int[] unitTextureBindings = new int[2];
   private final boolean[] gbV = new boolean[2];
   private final int blendSrcRgb = GL11.glGetInteger(32969);
   private final int blendDstRgb = GL11.glGetInteger(32968);
   private final int blendDstAlpha = GL11.glGetInteger(32971);
   private final int hzS = GL11.glGetInteger(32970);
   private final int savedDepthFunc = GL11.glGetInteger(2932);
   private final int vkN8 = GL11.glGetInteger(3009);
   private final int savedShadeModel = GL11.glGetInteger(2900);
   private final float Tao = GL11.glGetFloat(3010);
   private final boolean depthMaskEnabled = GL11.glGetBoolean(2930);
   private final FloatBuffer ivw = BufferUtils.createFloatBuffer(4);
   private final FloatBuffer clearColorBuffer = BufferUtils.createFloatBuffer(4);

   public GlStateSnapshot() {
      GL11.glGetFloat(2816, this.ivw);
      GL11.glGetFloat(3106, this.clearColorBuffer);

      for (int var1 = 0; var1 < this.trackedCapabilities.length; var1++) {
         this.capabilityStates[var1] = GL11.glIsEnabled(this.trackedCapabilities[var1]);
      }

      for (int var2 = 0; var2 < 2; var2++) {
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var2);
         this.unitTextureBindings[var2] = GL11.glGetInteger(32873);
         this.gbV[var2] = GL11.glIsEnabled(3553);
      }

      GlStateManager.setActiveTexture(this.activeTextureUnit);
      GL11.glPushAttrib(1048575);
      GL11.glPushClientAttrib(1);
      GL11.glPixelStorei(3330, 0);
      GL11.glPixelStorei(3332, 0);
      GL11.glPixelStorei(3331, 0);
      GL11.glMatrixMode(5889);
      GL11.glPushMatrix();
      GL11.glMatrixMode(5888);
      GL11.glPushMatrix();
   }

   @Override
   public void close() {
      GL11.glMatrixMode(5888);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5889);
      GL11.glPopMatrix();
      GL11.glMatrixMode(this.savedMatrixMode);
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferBinding);
      GL11.glPopAttrib();
      GL11.glPopClientAttrib();

      for (int var1 = 0; var1 < this.trackedCapabilities.length; var1++) {
         applyCapabilityState(var1, !this.capabilityStates[var1]);
         applyCapabilityState(var1, this.capabilityStates[var1]);
      }

      GlStateManager.tryBlendFuncSeparate(1, 0, 1, 0);
      GlStateManager.tryBlendFuncSeparate(this.blendSrcRgb, this.blendDstRgb, this.blendDstAlpha, this.hzS);
      GlStateManager.depthMask(!this.depthMaskEnabled);
      GlStateManager.depthMask(this.depthMaskEnabled);
      GlStateManager.depthFunc(519);
      GlStateManager.depthFunc(this.savedDepthFunc);
      GlStateManager.alphaFunc(519, 0.0F);
      GlStateManager.alphaFunc(this.vkN8, this.Tao);
      GlStateManager.shadeModel(7424);
      GlStateManager.shadeModel(this.savedShadeModel);
      GlStateManager.color(0.0F, 0.0F, 0.0F, 0.0F);
      GlStateManager.color(this.ivw.get(0), this.ivw.get(1), this.ivw.get(2), this.ivw.get(3));
      GlStateManager.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.clearColor(this.clearColorBuffer.get(0), this.clearColorBuffer.get(1), this.clearColorBuffer.get(2), this.clearColorBuffer.get(3));
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + 1);

      for (int var2 = 0; var2 < 2; var2++) {
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var2);
         GlStateManager.bindTexture(0);
         GlStateManager.bindTexture(this.unitTextureBindings[var2]);
         if (this.gbV[var2]) {
            GlStateManager.disableTexture2D();
            GlStateManager.enableTexture2D();
         } else {
            GlStateManager.enableTexture2D();
            GlStateManager.disableTexture2D();
         }
      }

      GlStateManager.setActiveTexture(this.activeTextureUnit);
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferBinding);
      GL20.glUseProgram(this.currentProgram);
   }

   private static void applyCapabilityState(int var0, boolean var1) {
      switch (var0) {
         case 0:
            if (var1) {
               GlStateManager.enableBlend();
            } else {
               GlStateManager.disableBlend();
            }
            break;
         case 1:
            if (var1) {
               GlStateManager.enableDepth();
            } else {
               GlStateManager.disableDepth();
            }
            break;
         case 2:
            if (var1) {
               GlStateManager.enableAlpha();
            } else {
               GlStateManager.disableAlpha();
            }
            break;
         case 3:
            if (var1) {
               GlStateManager.enableCull();
            } else {
               GlStateManager.disableCull();
            }
            break;
         case 4:
            if (var1) {
               GlStateManager.enableLighting();
            } else {
               GlStateManager.disableLighting();
            }
            break;
         case 5:
            if (var1) {
               GlStateManager.enableRescaleNormal();
            } else {
               GlStateManager.disableRescaleNormal();
            }
            break;
         case 6:
            if (var1) {
               GlStateManager.enableColorMaterial();
            } else {
               GlStateManager.disableColorMaterial();
            }
            break;
         default:
            if (var1) {
               GlStateManager.enableLight(var0 - 7);
            } else {
               GlStateManager.disableLight(var0 - 7);
            }
      }
   }
}
