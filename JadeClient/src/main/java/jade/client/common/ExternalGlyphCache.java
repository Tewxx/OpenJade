// Jade recovery: original class: jade.deps.eLz.ft9czsW
package jade.client.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExternalGlyphCache {
   private static final Map<GlyphMetrics, ExternalGlyphCache$0> glyphCache = new LinkedHashMap<>(256, 0.75F, true);
   private static final Map<Integer, ExternalGlyphCache$0> gDv = new HashMap<>();
   private static int LyS = 1073741824;

   public static void MEMXxt(ExternalRenderBuffer var0, GlyphMetrics var1, double var2, double var4, float var6, int var7, boolean var8) {
      if (var1 != null && var7 >>> 24 != 0) {
         ExternalGlyphCache$0 var9 = glyphCache.get(var1);
         if (var9 == null) {
            if (glyphCache.size() >= 512) {
               Iterator var10 = glyphCache.values().iterator();
               gDv.remove(((ExternalGlyphCache$0)var10.next()).textureId);
               var10.remove();
            }

            var9 = new ExternalGlyphCache$0(var1);
            glyphCache.put(var1, var9);
            gDv.put(var9.textureId, var9);
         }

         var0.drawTextureRect(var9.textureId, var2 + var1.x * var6, var4 + var1.y * var6, var1.drawWidth * var6, var1.drawHeight * var6, var7, var8);
      }
   }

   public static void ceFk(ExternalRenderBuffer var0, ExternalOverlaySession var1) {
      for (int var2 = 0; var2 < var0.referencedTextureCount; var2++) {
         ExternalGlyphCache$0 var3 = gDv.get(var0.referencedTextureIds[var2]);
         if (var3 != null && !var1.hasTexture(var3.textureId)) {
            var0.uploadAlphaTexture(var3.textureId, var3.TchHs.width, var3.TchHs.height, var3.TchHs.alpha);
         }
      }
   }

   public static void clearCache() {
      glyphCache.clear();
      gDv.clear();
   }

   public static int allocateTextureId() {
      return LyS++;
   }
}
