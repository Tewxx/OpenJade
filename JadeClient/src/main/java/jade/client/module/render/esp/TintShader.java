// Jade recovery: original class: jade.deps.eLz.Ay3w9sK
package jade.client.module.render.esp;

import org.lwjgl.opengl.GL20;

public class TintShader extends EspShader {
   public TintShader() {
      super(EspShaderSources.tintFragmentSource());
   }

   @Override
   public void declareUniforms() {
      this.cacheUniforms("tex", "tint");
   }

   @Override
   public void applyUniforms() {
      this.UDJo();
      int var1 = this.getUniformLocation("tex");
      if (var1 >= 0) {
         GL20.glUniform1i(var1, 0);
      }
   }

   public void BOral1(int var1, int var2, int var3, int var4) {
      int var5 = this.getUniformLocation("tint");
      if (var5 >= 0) {
         float[] var6 = ColorComponents.toFloats(var1, var2, var3, var4);
         GL20.glUniform4f(var5, var6[0], var6[1], var6[2], var6[3]);
      }
   }

   public void setTint(int var1) {
      int[] var2 = ColorComponents.NMv0(var1);
      this.BOral1(var2[0], var2[1], var2[2], var2[3]);
   }
}
