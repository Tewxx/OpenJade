// Jade recovery: original class: jade.deps.eLz.obRqur45
package jade.client.module.render.esp;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL20;

public class GlowShader extends EspShader {
   private float kernelSize = 2.0F;

   public GlowShader() {
      super(EspShaderSources.glowFragmentSource());
   }

   @Override
   public void declareUniforms() {
      this.cacheUniforms("tex", "texelSize", "kernel");
   }

   @Override
   public void applyUniforms() {
      this.UDJo();
      int var1 = this.getUniformLocation("tex");
      if (var1 >= 0) {
         GL20.glUniform1i(var1, 0);
      }

      var1 = this.getUniformLocation("texelSize");
      if (var1 >= 0) {
         Minecraft var2 = Minecraft.getMinecraft();
         GL20.glUniform2f(var1, 1.0F / var2.displayWidth, 1.0F / var2.displayHeight);
      }

      var1 = this.getUniformLocation("kernel");
      if (var1 >= 0) {
         GL20.glUniform1f(var1, this.kernelSize);
      }
   }

   public void setKernelSize(float var1) {
      this.kernelSize = ShaderKernelRadius.clampMinimum(var1);
   }
}
