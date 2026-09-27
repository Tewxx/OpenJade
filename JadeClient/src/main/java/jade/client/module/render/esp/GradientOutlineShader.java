// Jade recovery: original class: jade.deps.eLz.shj1m35
package jade.client.module.render.esp;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL20;

public class GradientOutlineShader extends EspShader {
   private static final int MAX_GRADIENT_COLORS = 8;
   private static final String FRAGMENT_SHADER_SOURCE = "#version 120\nuniform sampler2D tex;\nuniform vec2 texelSize;\nuniform float kernel;\nuniform vec3 colors[8];\nuniform float colorCount;\nuniform float phase;\nuniform float alpha;\nvec3 palette(float index, float local) {\n  if (index < 0.5) return mix(colors[0], colors[1], local);\n  if (index < 1.5) return mix(colors[1], colors[2], local);\n  if (index < 2.5) return mix(colors[2], colors[3], local);\n  if (index < 3.5) return mix(colors[3], colors[4], local);\n  if (index < 4.5) return mix(colors[4], colors[5], local);\n  if (index < 5.5) return mix(colors[5], colors[6], local);\n  return mix(colors[6], colors[7], local);\n}\nvoid main() {\n  vec2 uv = gl_TexCoord[0].xy;\n  if (texture2D(tex, uv).a > 0.0) { gl_FragColor = vec4(0.0); return; }\n  vec2 outer = texelSize * kernel;\n  vec2 inner = outer * 0.5;\n  float edge = texture2D(tex, uv + vec2( outer.x, 0.0)).a;\n  edge = max(edge, texture2D(tex, uv + vec2(-outer.x, 0.0)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0,  outer.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0, -outer.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2( outer.x,  outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-outer.x,  outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2( outer.x, -outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-outer.x, -outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2( inner.x, 0.0)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-inner.x, 0.0)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0,  inner.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0, -inner.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2( inner.x,  inner.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-inner.x,  inner.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2( inner.x, -inner.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-inner.x, -inner.y) * 0.70710678).a);\n  if (edge <= 0.0) { gl_FragColor = vec4(0.0); return; }\n  if (colorCount < 1.5) { gl_FragColor = vec4(colors[0], alpha * edge); return; }\n  float segments = colorCount - 1.0;\n  float wave = mod(phase + uv.y * 1.75, 1.0) * segments * 2.0;\n  float position = wave <= segments ? wave : segments * 2.0 - wave;\n  float index = min(segments - 0.001, floor(position));\n  gl_FragColor = vec4(palette(index, position - index), alpha * edge);\n}";
   private float kernelRadius = 2.0F;
   private float tTf = 1.0F;
   private List<Integer> RPIUhr = Collections.singletonList(-1);

   public GradientOutlineShader() {
      super(
         "#version 120\nuniform sampler2D tex;\nuniform vec2 texelSize;\nuniform float kernel;\nuniform vec3 colors[8];\nuniform float colorCount;\nuniform float phase;\nuniform float alpha;\nvec3 palette(float index, float local) {\n  if (index < 0.5) return mix(colors[0], colors[1], local);\n  if (index < 1.5) return mix(colors[1], colors[2], local);\n  if (index < 2.5) return mix(colors[2], colors[3], local);\n  if (index < 3.5) return mix(colors[3], colors[4], local);\n  if (index < 4.5) return mix(colors[4], colors[5], local);\n  if (index < 5.5) return mix(colors[5], colors[6], local);\n  return mix(colors[6], colors[7], local);\n}\nvoid main() {\n  vec2 uv = gl_TexCoord[0].xy;\n  if (texture2D(tex, uv).a > 0.0) { gl_FragColor = vec4(0.0); return; }\n  vec2 outer = texelSize * kernel;\n  vec2 inner = outer * 0.5;\n  float edge = texture2D(tex, uv + vec2( outer.x, 0.0)).a;\n  edge = max(edge, texture2D(tex, uv + vec2(-outer.x, 0.0)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0,  outer.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0, -outer.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2( outer.x,  outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-outer.x,  outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2( outer.x, -outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-outer.x, -outer.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2( inner.x, 0.0)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-inner.x, 0.0)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0,  inner.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2(0.0, -inner.y)).a);\n  edge = max(edge, texture2D(tex, uv + vec2( inner.x,  inner.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-inner.x,  inner.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2( inner.x, -inner.y) * 0.70710678).a);\n  edge = max(edge, texture2D(tex, uv + vec2(-inner.x, -inner.y) * 0.70710678).a);\n  if (edge <= 0.0) { gl_FragColor = vec4(0.0); return; }\n  if (colorCount < 1.5) { gl_FragColor = vec4(colors[0], alpha * edge); return; }\n  float segments = colorCount - 1.0;\n  float wave = mod(phase + uv.y * 1.75, 1.0) * segments * 2.0;\n  float position = wave <= segments ? wave : segments * 2.0 - wave;\n  float index = min(segments - 0.001, floor(position));\n  gl_FragColor = vec4(palette(index, position - index), alpha * edge);\n}"
      );
   }

   @Override
   public void declareUniforms() {
      this.cacheUniform("tex");
      this.cacheUniform("texelSize");
      this.cacheUniform("kernel");
      this.cacheUniform("colorCount");
      this.cacheUniform("phase");
      this.cacheUniform("alpha");

      for (int var1 = 0; var1 < 8; var1++) {
         this.cacheUniform("colors[" + var1 + "]");
      }
   }

   @Override
   public void applyUniforms() {
      GL20.glUseProgram(this.programId);
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
         GL20.glUniform1f(var1, this.kernelRadius);
      }

      var1 = this.getUniformLocation("colorCount");
      if (var1 >= 0) {
         GL20.glUniform1f(var1, Math.min(8, this.RPIUhr.size()));
      }

      var1 = this.getUniformLocation("phase");
      if (var1 >= 0) {
         GL20.glUniform1f(var1, (float)(System.currentTimeMillis() % 3000L) / 3000.0F);
      }

      var1 = this.getUniformLocation("alpha");
      if (var1 >= 0) {
         GL20.glUniform1f(var1, this.tTf);
      }

      int var13 = Math.min(8, this.RPIUhr.size());

      for (int var3 = 0; var3 < 8; var3++) {
         int var4 = Math.min(var3, var13 - 1);
         int var5 = var13 <= 1 ? 0 : Math.round(var4 * (this.RPIUhr.size() - 1.0F) / (var13 - 1.0F));
         int var6 = this.RPIUhr.get(var5);
         var1 = this.getUniformLocation("colors[" + var3 + "]");
         if (var1 >= 0) {
            GL20.glUniform3f(var1, (var6 >> 16 & 0xFF) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F);
         }
      }
   }

   public void ETNK(float var1, List<Integer> var2, int var3) {
      this.kernelRadius = Math.max(1.0F, var1);
      this.RPIUhr = var2 != null && !var2.isEmpty() ? var2 : Collections.singletonList(-1);
      this.tTf = Math.max(0, Math.min(255, var3)) / 255.0F;
   }
}
