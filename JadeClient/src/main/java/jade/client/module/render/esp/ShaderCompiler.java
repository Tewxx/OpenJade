// Jade recovery: original class: jade.deps.eLz.Mii9vickd
package jade.client.module.render.esp;

import org.lwjgl.opengl.GL20;

public final class ShaderCompiler {
   private ShaderCompiler() {
   }

   public static int linkProgram(String var0, String var1) {
      int var2 = compileShader(var0, 35633);
      int var3 = compileShader(var1, 35632);
      if (var2 != -1 && var3 != -1) {
         int var4 = GL20.glCreateProgram();
         GL20.glAttachShader(var4, var2);
         GL20.glAttachShader(var4, var3);
         GL20.glLinkProgram(var4);
         return GL20.glGetProgrami(var4, 35714) == 0 ? -1 : var4;
      } else {
         return -1;
      }
   }

   private static int compileShader(String var0, int var1) {
      int var2 = GL20.glCreateShader(var1);
      GL20.glShaderSource(var2, var0);
      GL20.glCompileShader(var2);
      return GL20.glGetShaderi(var2, 35713) == 0 ? -1 : var2;
   }
}
