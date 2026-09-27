// Jade recovery: original class: jade.deps.eLz.wKMCZl
package jade.client.module.render.esp;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL20;

public final class ShaderUniforms {
   private final Map<String, Integer> uniformLocationCache = new HashMap<>();

   public void cacheUniformLocation(int var1, String var2) {
      this.uniformLocationCache.put(var2, GL20.glGetUniformLocation(var1, var2));
   }

   public int EHZxQ8(String var1) {
      Integer var2 = this.uniformLocationCache.get(var1);
      return var2 == null ? -1 : var2;
   }

   public int getUniformLocation(int var1, String var2) {
      Integer var3 = this.uniformLocationCache.get(var2);
      if (var3 != null) {
         return var3;
      } else {
         int var4 = GL20.glGetUniformLocation(var1, var2);
         this.uniformLocationCache.put(var2, var4);
         return var4;
      }
   }
}
