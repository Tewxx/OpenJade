// Jade recovery: original class: jade.deps.eLz.uMRpkvkXJ
package jade.client.common;

import jade.client.module.render.nametags.MinecraftFont;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public final class VanillaGlyphs {
   private static final String AYcNd8 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000";
   private static final Map<String, GlyphMetrics> yzcR = new LinkedHashMap<>(128, 0.75F, true);
   private static BufferedImage bufferedImage;
   private static final Map<Integer, BufferedImage> unicodePageCache = new LinkedHashMap<>();
   private static byte[] glyphSizes;

   public static GlyphMetrics emlJ(MinecraftFont var0, String var1) {
      String var2 = "cpu:" + var0.getScale() + ":" + var0.usesUnicodeFont() + ":" + var1;
      GlyphMetrics var3 = yzcR.get(var2);
      if (var3 != null) {
         return var3;
      } else {
         char var4 = 0;
         boolean var5 = false;
         boolean var6 = false;
         boolean var7 = false;
         boolean var8 = false;

         for (int var9 = 0; var9 < var1.length(); var9++) {
            char var10 = var1.charAt(var9);
            if (var10 == 167 && var9 + 1 < var1.length()) {
               char var11 = Character.toLowerCase(var1.charAt(++var9));
               if (var11 == 'l') {
                  var5 = true;
               } else if (var11 == 'o') {
                  var6 = true;
               } else if (var11 == 'n') {
                  var7 = true;
               } else if (var11 == 'm') {
                  var8 = true;
               } else if (var11 == 'r' || "0123456789abcdef".indexOf(var11) >= 0) {
                  var8 = false;
                  var7 = false;
                  var6 = false;
                  var5 = false;
               }
            } else {
               var4 = var10;
            }
         }

         if (var4 == 0) {
            return null;
         } else {
            try {
               VanillaGlyphs$0 var14 = getGlyph(var4, var0.usesUnicodeFont());
               if (var14 == null) {
                  return null;
               } else {
                  var3 = renderGlyph(var14, var0, var1, var5, var6, var7, var8);
                  if (yzcR.size() >= 512) {
                     yzcR.remove(yzcR.keySet().iterator().next());
                  }

                  yzcR.put(var2, var3);
                  return var3;
               }
            } catch (IOException var12) {
               return null;
            }
         }
      }
   }

   private static VanillaGlyphs$0 getGlyph(char var0, boolean var1) throws IOException {
      int var2 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000"
         .indexOf(var0);
      if (var2 >= 0 && !var1) {
         if (bufferedImage == null) {
            bufferedImage = readImage(new ResourceLocation("textures/font/ascii.png"));
         }

         int var12 = bufferedImage.getWidth() / 16;
         int var13 = bufferedImage.getHeight() / 16;
         return extractGlyph(bufferedImage, var2 % 16 * var12, var2 / 16 * var13, var12, var13, 8.0F, false);
      } else {
         if (glyphSizes == null) {
            glyphSizes = readResourceBytes(new ResourceLocation("font/glyph_sizes.bin"), 65536);
         }

         int var3 = glyphSizes[var0] & 255;
         if (var3 == 0) {
            return null;
         } else {
            int var4 = var3 >>> 4;
            int var5 = var3 & 15;
            if (var5 < var4) {
               return null;
            } else {
               int var6 = var0 >>> '\b';
               BufferedImage var7 = unicodePageCache.get(var6);
               if (var7 == null) {
                  var7 = readImage(new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", var6)));
                  unicodePageCache.put(var6, var7);
               }

               int var8 = var7.getWidth() / 16;
               int var9 = var7.getHeight() / 16;
               int var10 = Math.round(var4 * var8 / 16.0F);
               int var11 = Math.round((var5 + 1) * var8 / 16.0F);
               return extractGlyph(var7, (var0 & 15) * var8 + var10, (var0 & 255) / 16 * var9, Math.max(1, var11 - var10), var9, (var5 + 1 - var4) / 2.0F, true);
            }
         }
      }
   }

   private static VanillaGlyphs$0 extractGlyph(BufferedImage var0, int var1, int var2, int var3, int var4, float var5, boolean var6) {
      int var7 = Math.max(1, Math.round(var5 * 4.0F));
      byte var8 = 32;
      byte[] var9 = new byte[var7 * var8];

      for (int var10 = 0; var10 < var8; var10++) {
         int var11 = Math.min(var4 - 1, var10 * var4 / var8);

         for (int var12 = 0; var12 < var7; var12++) {
            int var13 = Math.min(var3 - 1, var12 * var3 / var7);
            var9[var10 * var7 + var12] = (byte)(var0.getRGB(var1 + var13, var2 + var11) >>> 24);
         }
      }

      return new VanillaGlyphs$0(var7, var8, var5, 8.0F, var6, var9);
   }

   private static GlyphMetrics renderGlyph(VanillaGlyphs$0 var0, MinecraftFont var1, String var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      float var7 = var0.height / var0.baseHeight;
      int var8 = var4 ? Math.max(1, Math.round(var7)) : 0;
      int var9 = var3 ? Math.max(1, Math.round(var7 * (var0.hasCustomAdvance ? 0.5F : 1.0F))) : 0;
      int var10 = Math.max(var0.height, Math.round(9.0F * var7));
      int var11 = var0.IOz + var8 * 2 + var9;
      byte[] var12 = new byte[var11 * var10];

      for (int var13 = 0; var13 < var0.height; var13++) {
         int var14 = var4 && var0.height > 1 ? Math.round(var8 * 2.0F * (var0.height - 1 - var13) / (var0.height - 1)) : var8;

         for (int var15 = 0; var15 < var0.IOz; var15++) {
            int var16 = var0.alphaData[var13 * var0.IOz + var15] & 255;
            setMaxAlpha(var12, var11, var14 + var15, var13, var16);
            if (var3) {
               setMaxAlpha(var12, var11, var14 + var15 + var9, var13, var16);
            }
         }
      }

      float var17 = var0.hasCustomAdvance ? (var0.advanceWidth + 1.0F + (var3 ? 1.0F : 0.0F)) * var1.getScale() : var1.getUnscaledStringWidth(var2) * var1.getScale();
      int var18 = Math.max(1, Math.round(var17 / var1.getScale() * var7));
      if (var6) {
         fillAlphaSpan(var12, var11, var10, Math.round(3.5F * var7), var18 + var8, var8);
      }

      if (var5) {
         fillAlphaSpan(var12, var11, var10, Math.round(8.0F * var7), var18 + var8, 0);
      }

      float var19 = var1.getScale() / var7;
      return new GlyphMetrics(var11, var10, var4 ? -var1.getScale() : 0.0F, 0.0F, var11 * var19, var10 * var19, var17, var12);
   }

   private static void setMaxAlpha(byte[] var0, int var1, int var2, int var3, int var4) {
      if (var2 >= 0 && var3 >= 0 && var2 < var1 && var3 < var0.length / var1) {
         int var5 = var3 * var1 + var2;
         if (var4 > (var0[var5] & 255)) {
            var0[var5] = (byte)var4;
         }
      }
   }

   private static void fillAlphaSpan(byte[] var0, int var1, int var2, int var3, int var4, int var5) {
      if (var3 >= 0 && var3 < var2) {
         for (int var6 = Math.max(0, var5); var6 < Math.min(var1, var4); var6++) {
            var0[var3 * var1 + var6] = -1;
         }
      }
   }

   private static BufferedImage readImage(ResourceLocation var0) throws IOException {
      IResource var1 = Minecraft.getMinecraft().getResourceManager().getResource(var0);

      BufferedImage var5;
      try (InputStream var2 = var1.getInputStream()) {
         BufferedImage var4 = ImageIO.read(var2);
         if (var4 == null) {
            throw new IOException("Invalid font texture");
         }

         var5 = var4;
      }

      return var5;
   }

   private static byte[] readResourceBytes(ResourceLocation var0, int var1) throws IOException {
      byte[] var2 = new byte[var1];
      IResource var3 = Minecraft.getMinecraft().getResourceManager().getResource(var0);

      try (InputStream var4 = var3.getInputStream()) {
         int var6 = 0;

         while (var6 < var1) {
            int var7 = var4.read(var2, var6, var1 - var6);
            if (var7 < 0) {
               break;
            }

            var6 += var7;
         }
      }

      return var2;
   }

   public static void resetCache() {
      yzcR.clear();
      bufferedImage = null;
      unicodePageCache.clear();
      glyphSizes = null;
   }
}
