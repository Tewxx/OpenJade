// Jade recovery: original class: jade.deps.eLz.SPYRxWENG
package jade.client.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.lwjgl.opengl.GL11;

public final class GlyphCache {
   private final GlyphRasterizer rasterizer;
   private final RasterizedGlyph[] latin = new RasterizedGlyph[256];
   private final Map<Character, RasterizedGlyph> extended = new ConcurrentHashMap<>();
   private boolean destroyed;

   public GlyphCache(GlyphRasterizer var1) {
      this.rasterizer = var1;

      for (int var2 = 0; var2 < this.latin.length; var2++) {
         this.latin[var2] = var1.create((char)var2);
      }
   }

   public RasterizedGlyph glyph(char var1) {
      return var1 <= 255 ? this.latin[var1] : this.extended.computeIfAbsent(var1, var1x -> this.destroyed ? GlyphRasterizer.empty() : this.rasterizer.create(var1x));
   }

   public void destroy() {
      if (!this.destroyed) {
         this.destroyed = true;

         for (RasterizedGlyph var4 : this.latin) {
            delete(var4);
         }

         for (RasterizedGlyph var6 : this.extended.values()) {
            delete(var6);
         }

         this.extended.clear();
      }
   }

   public boolean destroyed() {
      return this.destroyed;
   }

   private static void delete(RasterizedGlyph var0) {
      if (var0 != null && var0.texture != 0) {
         GL11.glDeleteTextures(var0.texture);
      }
   }
}
