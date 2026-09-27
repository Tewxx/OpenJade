// Jade recovery: original class: jade.deps.eLz.PjgK9ir
package jade.client.module.render.shared;

import jade.client.common.ClientUtils;
import java.io.InputStream;
import org.lwjgl.opengl.GL20;

public final class ShaderLoader {
   private ShaderLoader() {
   }

   public static void attachShaders(int var0, InputStream var1, InputStream var2) {
      GL20.glAttachShader(var0, compileShader(var1, 35632));
      GL20.glAttachShader(var0, compileShader(var2, 35633));
   }

   private static int compileShader(InputStream var0, int var1) {
      int var2 = GL20.glCreateShader(var1);
      GL20.glShaderSource(var2, ClientUtils.readStreamText(var0));
      GL20.glCompileShader(var2);
      if (GL20.glGetShaderi(var2, 35713) == 0) {
         throw new IllegalStateException(String.format("Shader (%s) failed to compile! %s", var1, GL20.glGetShaderInfoLog(var2, 4096)));
      } else {
         return var2;
      }
   }
}
