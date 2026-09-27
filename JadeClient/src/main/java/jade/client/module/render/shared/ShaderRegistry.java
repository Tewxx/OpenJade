// Jade recovery: original class: jade.deps.eLz.oLgXm1f
package jade.client.module.render.shared;

import jade.deps.loader107.CoreResourceIndex;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ShaderRegistry {
   private static final String GAQAyu = "/assets/jade/shaders/";
   private static final Map<String, String> IjM;

   private ShaderRegistry() {
   }

   public static boolean hasShader(String var0) {
      return var0 != null && IjM.containsKey(var0);
   }

   public static String ITAJb(String var0) {
      String var1 = IjM.get(var0);
      return var1 == null ? null : "/assets/jade/shaders/" + var1;
   }

   public static InputStream openShaderStream(String var0) {
      String var1 = ITAJb(var0);
      return var1 == null ? null : CoreResourceIndex.openResource(var1);
   }

   public static int getShaderCount() {
      return IjM.size();
   }

   static {
      HashMap var0 = new HashMap();
      var0.put("roundedRectRise", "rounded_rise.fsh");
      var0.put("kawaseDown", "kawase_down.fsh");
      var0.put("gradientMask", "gradient_mask.fsh");
      var0.put("glow", "glow.fsh");
      var0.put("roundRectOutline", "rounded_outline.fsh");
      var0.put("kawaseUpBloom", "kawase_up_bloom.fsh");
      var0.put("roundedRect", "rounded_rect.fsh");
      var0.put("mask", "mask.fsh");
      var0.put("roundRectTexture", "rounded_texture.fsh");
      var0.put("kawaseUpGlow", "kawase_up_glow.fsh");
      var0.put("gradient", "gradient.fsh");
      var0.put("kawaseDownBloom", "kawase_down_bloom.fsh");
      var0.put("roundedRectGradient", "rounded_gradient.fsh");
      var0.put("kawaseUp", "kawase_up.fsh");
      IjM = Collections.unmodifiableMap(var0);
   }
}
