// Jade recovery: original class: jade.deps.eLz.wQzhjFJo54
package jade.client.module.render.esp;

import org.lwjgl.opengl.GL20;

public abstract class EspShader {
   protected int programId = -1;
   private final ShaderUniforms shaderUniforms = new ShaderUniforms();

   public EspShader(String var1) {
      this.programId = ShaderCompiler.linkProgram(EspShaderSources.vertexSource(), var1);
      if (this.hasValidProgram()) {
         this.declareUniforms();
      }
   }

   protected void cacheUniform(String var1) {
      if (this.hasValidProgram()) {
         this.shaderUniforms.cacheUniformLocation(this.programId, var1);
      }
   }

   protected int getUniformLocation(String var1) {
      return this.shaderUniforms.EHZxQ8(var1);
   }

   protected final void cacheUniforms(String... var1) {
      for (String var5 : var1) {
         this.cacheUniform(var5);
      }
   }

   protected final void UDJo() {
      GL20.glUseProgram(this.programId);
   }

   public abstract void declareUniforms();

   public abstract void applyUniforms();

   public void bindShader() {
      if (this.hasValidProgram()) {
         this.applyUniforms();
      }
   }

   public void WutN18() {
      GL20.glUseProgram(0);
   }

   public boolean hasValidProgram() {
      return this.programId >= 0;
   }
}
