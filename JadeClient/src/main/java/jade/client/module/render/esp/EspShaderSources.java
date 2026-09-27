// Jade recovery: original class: jade.deps.eLz.ySh9wsur8
package jade.client.module.render.esp;

public final class EspShaderSources {
   private static final String VERTEX_SHADER_SOURCE = "#version 120\nvoid main() {\n  gl_Position = ftransform();\n  gl_TexCoord[0] = gl_MultiTexCoord0;\n}";
   private static final String TINT_FRAGMENT_SHADER_SOURCE = "#version 120\nuniform sampler2D tex;\nuniform vec4 tint;\nvoid main() {\n  vec4 mask = texture2D(tex, gl_TexCoord[0].xy);\n  vec4 outputColor = vec4(tint.rgb, 0.0);\n  if (mask.a > 0.0) outputColor.a = tint.a;\n  gl_FragColor = outputColor;\n}";
   private static final String GLOW_FRAGMENT_SHADER_SOURCE = "#version 120\nuniform sampler2D tex;\nuniform vec2 texelSize;\nuniform float kernel;\nvoid main() {\n  vec2 uv = gl_TexCoord[0].xy;\n  if (texture2D(tex, uv).a > 0.0) { gl_FragColor = vec4(0.0); return; }\n  vec4 outputColor = vec4(0.0);\n  float x = -kernel;\n  while (x <= kernel) {\n    float y = -kernel;\n    while (y <= kernel) {\n      vec4 candidate = texture2D(tex, uv + vec2(x, y) * texelSize);\n      if (candidate.a > 0.0) outputColor = candidate;\n      y += 1.0;\n    }\n    x += 1.0;\n  }\n  gl_FragColor = outputColor;\n}";

   private EspShaderSources() {
   }

   public static String vertexSource() {
      return "#version 120\nvoid main() {\n  gl_Position = ftransform();\n  gl_TexCoord[0] = gl_MultiTexCoord0;\n}";
   }

   public static String tintFragmentSource() {
      return "#version 120\nuniform sampler2D tex;\nuniform vec4 tint;\nvoid main() {\n  vec4 mask = texture2D(tex, gl_TexCoord[0].xy);\n  vec4 outputColor = vec4(tint.rgb, 0.0);\n  if (mask.a > 0.0) outputColor.a = tint.a;\n  gl_FragColor = outputColor;\n}";
   }

   public static String glowFragmentSource() {
      return "#version 120\nuniform sampler2D tex;\nuniform vec2 texelSize;\nuniform float kernel;\nvoid main() {\n  vec2 uv = gl_TexCoord[0].xy;\n  if (texture2D(tex, uv).a > 0.0) { gl_FragColor = vec4(0.0); return; }\n  vec4 outputColor = vec4(0.0);\n  float x = -kernel;\n  while (x <= kernel) {\n    float y = -kernel;\n    while (y <= kernel) {\n      vec4 candidate = texture2D(tex, uv + vec2(x, y) * texelSize);\n      if (candidate.a > 0.0) outputColor = candidate;\n      y += 1.0;\n    }\n    x += 1.0;\n  }\n  gl_FragColor = outputColor;\n}";
   }
}
