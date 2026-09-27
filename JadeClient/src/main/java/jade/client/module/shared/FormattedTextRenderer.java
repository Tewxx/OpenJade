// Jade recovery: original class: jade.deps.eLz.XgtRM9
package jade.client.module.shared;

import jade.client.common.ChatUtils$2;
import jade.client.common.ChatUtils;
import jade.client.common.ExternalGlyphCache;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.IFont;
import jade.client.common.GlyphMetrics;
import jade.client.common.TrueTypeFont;
import jade.client.common.VanillaGlyphs;
import jade.client.module.render.nametags.MinecraftFont;
import jade.inject.InjectionAgent;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

public final class FormattedTextRenderer {
   private static final int[] COLOR_CODE_COLORS = new int[]{
      0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215
   };
   private static final int Uoua7 = 256;
   private static final int ccBp = 262144;
   private static final Map<FormattedTextRenderer$1, GlyphMetrics> PEZt = new LinkedHashMap<FormattedTextRenderer$1, GlyphMetrics>(256, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<FormattedTextRenderer$1, GlyphMetrics> var1) {
         return this.size() > 256;
      }
   };

   private FormattedTextRenderer() {
   }

   public static void drawTextAtHeight(ExternalRenderBuffer var0, IFont var1, String var2, double var3, double var5, float var7, int var8, boolean var9, float var10) {
      float var11 = var7 / Math.max(1, var1.getFontHeight());
      drawText(var0, var1, var2, var3, var5, var11, var8, var9, false);
   }

   public static void drawText(ExternalRenderBuffer var0, IFont var1, String var2, double var3, double var5, float var7, int var8, boolean var9, boolean var10) {
      if (var2 != null && var2.length() <= 2048 && Float.isFinite(var7) && !(var7 <= 0.0F) && var8 >>> 24 != 0) {
         boolean var11 = var1 instanceof MinecraftFont;
         if (var9) {
            renderTextLine(var0, var1, var2, var3 + (var11 ? 1.0 : 0.5) * var7, var5 + (var11 ? 1.0 : 0.5) * var7, var7, var8, true, var10);
         }

         renderTextLine(var0, var1, var2, var3, var5, var7, var8, false, var10);
      }
   }

   public static void QHFrl(ExternalRenderBuffer var0, IFont var1, String var2, double var3, double var5, float var7, int var8, boolean var9, long var10) {
      if (!ChatUtils.hasFlowTag(var2)) {
         drawText(var0, var1, var2, var3, var5, var7, var8, var9, false);
      } else {
         double var12 = var3;

         for (ChatUtils$2 var15 : ChatUtils.splitFlowSegments(var2)) {
            if (!var15.CREFU()) {
               drawText(var0, var1, var15.getText(), var12, var5, var7, var8, var9, false);
               var12 += var1.getStringWidth(var15.getText()) * var7;
            } else {
               String var16 = "";
               Integer var17 = null;
               String var18 = var15.getText();

               for (int var19 = 0; var19 < var18.length(); var19++) {
                  char var20 = var18.charAt(var19);
                  if (var20 == 167 && var19 + 1 < var18.length()) {
                     char var25 = Character.toLowerCase(var18.charAt(++var19));
                     int var26 = "0123456789abcdef".indexOf(var25);
                     if (var26 >= 0) {
                        var17 = COLOR_CODE_COLORS[var26];
                        var16 = "";
                     } else if (var25 == 'r') {
                        var17 = null;
                        var16 = "";
                     } else if (var25 >= 'k' && var25 <= 'o' && var16.indexOf(var25) < 0) {
                        var16 = var16 + "§" + var25;
                     }
                  } else if (var20 != '\n') {
                     String var21 = var16 + var20;
                     float var22 = var1.getStringWidth(var21) * var7;
                     int var23 = var17 == null ? ChatUtils.getGradientColor(var15.getGradientColors(), (var12 - var3 + var22 * 0.5) / var7, var10) : var17;
                     int var24 = var8 & 0xFF000000 | var23 & 16777215;
                     drawText(var0, var1, var21, var12, var5, var7, var24, var9, true);
                     var12 += var22;
                  }
               }
            }
         }
      }
   }

   private static void renderTextLine(ExternalRenderBuffer var0, IFont var1, String var2, double var3, double var5, float var7, int var8, boolean var9, boolean var10) {
      if (!(var1 instanceof TrueTypeFont) || !fTahl(var0, (TrueTypeFont)var1, var2, var3, var5, var7, var8, var9, var10)) {
         int var11 = var8;
         float var12 = 0.0F;
         String var13 = "";
         boolean var14 = var1 instanceof MinecraftFont;
         boolean var15 = var14 && isForgeRuntime();

         for (int var16 = 0; var16 < var2.length(); var16++) {
            char var17 = var2.charAt(var16);
            if (var17 == 167 && var16 + 1 < var2.length()) {
               char var21 = Character.toLowerCase(var2.charAt(++var16));
               int var23 = "0123456789abcdef".indexOf(var21);
               if (var23 >= 0) {
                  var11 = var10 ? var8 : var8 & 0xFF000000 | COLOR_CODE_COLORS[var23];
                  var13 = "";
               } else if (var21 == 'r') {
                  var11 = var8;
                  var13 = "";
               } else if (var21 >= 'k' && var21 <= 'o' && (!var15 || var21 != 'l') && var13.indexOf(var21) < 0) {
                  var13 = var13 + "§" + var21;
               }
            } else if (var17 == '\n') {
               var12 = 0.0F;
               var5 += var1.getLineHeight() * var7;
            } else {
               GlyphMetrics var18 = var1 instanceof TrueTypeFont
                  ? ((TrueTypeFont)var1).getExternalGlyph(var17)
                  : (var14 ? VanillaGlyphs.emlJ((MinecraftFont)var1, var13 + var17) : null);
               if (var18 == null) {
                  if (var14) {
                     int var19 = var9 ? var11 & 0xFF000000 | (var11 & 16579836) >> 2 : var11;
                     String var20 = var13 + var17;
                     var0.drawText(var20, var3 + var12 * var7, var5, var1.getFontHeight() * var7, var19, false, var1.getStringWidth(var20) * var7);
                     var12 += var1.getStringWidth(var20);
                  }
               } else {
                  int var22 = var9 ? var11 & 0xFF000000 | (var11 & 16579836) >> 2 : var11;
                  ExternalGlyphCache.MEMXxt(var0, var18, var3 + var12 * var7, var5, var7, var22, var1 instanceof MinecraftFont);
                  var12 += var18.advance;
               }
            }
         }
      }
   }

   private static boolean isForgeRuntime() {
      try {
         return InjectionAgent.isForgeRuntime();
      } catch (LinkageError var1) {
         return false;
      }
   }

   private static boolean fTahl(ExternalRenderBuffer var0, TrueTypeFont var1, String var2, double var3, double var5, float var7, int var8, boolean var9, boolean var10) {
      if (var2.indexOf(10) >= 0) {
         return false;
      } else {
         int var11 = var8;
         float var12 = 0.0F;
         StringBuilder var13 = new StringBuilder();

         for (int var14 = 0; var14 < var2.length(); var14++) {
            char var15 = var2.charAt(var14);
            if (var15 == 167 && var14 + 1 < var2.length()) {
               char var16 = Character.toLowerCase(var2.charAt(++var14));
               int var17 = "0123456789abcdef".indexOf(var16);
               if (var17 >= 0 || var16 == 'r') {
                  var12 += KMchji(var0, var1, var13, var3, var5, var12, var7, var11, var9);
                  var13.setLength(0);
                  var11 = var17 >= 0 && !var10 ? var8 & 0xFF000000 | COLOR_CODE_COLORS[var17] : var8;
               }
            } else {
               var13.append(var15);
            }
         }

         KMchji(var0, var1, var13, var3, var5, var12, var7, var11, var9);
         return true;
      }
   }

   private static float KMchji(ExternalRenderBuffer var0, TrueTypeFont var1, StringBuilder var2, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      if (var2.length() == 0) {
         return 0.0F;
      } else {
         GlyphMetrics var11 = getOrBuildStringGlyph(var1, var2.toString());
         if (var11 == null) {
            return drawUncachedGlyphs(var0, var1, var2, var3, var5, var7, var8, var9, var10);
         } else {
            int var12 = var10 ? var9 & 0xFF000000 | (var9 & 16579836) >> 2 : var9;
            ExternalGlyphCache.MEMXxt(var0, var11, var3 + var7 * var8, var5, var8, var12, false);
            return var11.advance;
         }
      }
   }

   private static float drawUncachedGlyphs(ExternalRenderBuffer var0, TrueTypeFont var1, StringBuilder var2, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      int var11 = var10 ? var9 & 0xFF000000 | (var9 & 16579836) >> 2 : var9;
      float var12 = 0.0F;

      for (int var13 = 0; var13 < var2.length(); var13++) {
         GlyphMetrics var14 = var1.getExternalGlyph(var2.charAt(var13));
         if (var14 != null) {
            ExternalGlyphCache.MEMXxt(var0, var14, var3 + (var7 + var12) * var8, var5, var8, var11, false);
            var12 += var14.advance;
         }
      }

      return var12;
   }

   private static GlyphMetrics getOrBuildStringGlyph(TrueTypeFont var0, String var1) {
      FormattedTextRenderer$1 var2 = new FormattedTextRenderer$1(var0, var1);
      synchronized (PEZt) {
         GlyphMetrics var4 = PEZt.get(var2);
         if (var4 != null) {
            return var4;
         }
      }

      GlyphMetrics[] var25 = new GlyphMetrics[var1.length()];
      float[] var26 = new float[var1.length()];
      float var5 = 0.0F;
      float var6 = 0.0F;
      int var7 = Integer.MAX_VALUE;
      int var8 = Integer.MAX_VALUE;
      int var9 = Integer.MIN_VALUE;
      int var10 = Integer.MIN_VALUE;

      for (int var11 = 0; var11 < var1.length(); var11++) {
         GlyphMetrics var12 = var0.getExternalGlyph(var1.charAt(var11));
         var25[var11] = var12;
         var26[var11] = var5;
         if (var12 != null) {
            if (var6 == 0.0F) {
               var6 = var12.drawWidth / var12.width;
            }

            int var13 = (int)Math.floor((var26[var11] + var12.x) / var6);
            int var14 = (int)Math.floor(var12.y / var6);
            var7 = Math.min(var7, var13);
            var8 = Math.min(var8, var14);
            var9 = Math.max(var9, var13 + var12.width);
            var10 = Math.max(var10, var14 + var12.height);
            var5 += var12.advance;
         }
      }

      if (var6 != 0.0F && var9 > var7 && var10 > var8) {
         int var27 = var9 - var7;
         int var28 = var10 - var8;
         if ((long)var27 * var28 > 262144L) {
            return null;
         } else {
            byte[] var29 = new byte[var27 * var28];

            for (int var30 = 0; var30 < var25.length; var30++) {
               GlyphMetrics var15 = var25[var30];
               if (var15 != null) {
                  int var16 = (int)Math.floor((var26[var30] + var15.x) / var6) - var7;
                  int var17 = (int)Math.floor(var15.y / var6) - var8;

                  for (int var18 = 0; var18 < var15.height; var18++) {
                     for (int var19 = 0; var19 < var15.width; var19++) {
                        int var20 = var15.alpha[var18 * var15.width + var19] & 255;
                        int var21 = (var17 + var18) * var27 + var16 + var19;
                        if (var20 > (var29[var21] & 255)) {
                           var29[var21] = (byte)var20;
                        }
                     }
                  }
               }
            }

            GlyphMetrics var31 = new GlyphMetrics(var27, var28, var7 * var6, var8 * var6, var27 * var6, var28 * var6, var5, var29);
            synchronized (PEZt) {
               PEZt.put(var2, var31);
               return var31;
            }
         }
      } else {
         return null;
      }
   }

   public static void drawMultiLineLabel(
      ExternalRenderBuffer var0,
      IFont var1,
      double var2,
      double var4,
      float var6,
      float var7,
      float var8,
      float var9,
      int var10,
      int var11,
      int var12,
      String var13,
      String var14,
      String var15
   ) {
      float var16 = var6 / 9.0F;
      String[] var17 = new String[]{var13, var14, var15};
      float var18 = (var12 & 16) != 0 ? 8.0F : 4.0F;
      float var19 = 0.0F;
      int var20 = 0;

      for (String var24 : var17) {
         if (!var24.isEmpty()) {
            var19 += var1.getStringWidth(var24);
            var20++;
         }
      }

      var19 += Math.max(0, var20 - 1) * var18;
      double var31 = var2 - var19 * var16 / 2.0F;
      double var32 = var4 - var1.getFontHeight() * var16;
      if ((var12 & 16) == 0) {
         LjZuxgB(var0, var31, var32, var19 * var16, var1.getFontHeight() * var16, var8, var9, var11, var12, var16);
      }

      for (int var25 = 0; var25 < var17.length; var25++) {
         if (!var17[var25].isEmpty()) {
            float var26 = var1.getStringWidth(var17[var25]) * var16;
            if ((var12 & 16) != 0) {
               LjZuxgB(var0, var31, var32, var26, var1.getFontHeight() * var16, var8, var9, var11, var12, var16);
            }

            drawText(
               var0,
               var1,
               var17[var25],
               var31,
               var32,
               var16,
               var25 == 0 ? var10 : var10 & 0xFF000000 | 16777215,
               (var12 & 4) != 0,
               var25 == 0 && (var12 & 32) != 0
            );
            var31 += var26 + var18 * var16;
         }
      }

      if ((var12 & 8) != 0) {
         double var33 = var2 - (var19 / 2.0F + 3.0F) * var16;
         double var27 = var2 + (var19 / 2.0F + 3.0F) * var16;
         var0.fillRoundedRect(var33, var4 + var16, var27, var4 + 1.5 * var16, -14277082, 0.0F);
         int var29 = 0xFF000000 | Math.round((1.0F - var7) * 255.0F) << 16 | Math.round(var7 * 255.0F) << 8;
         var0.fillRoundedRect(var33, var4 + var16, var33 + (var27 - var33) * var7, var4 + 1.5 * var16, var29, 0.0F);
      }
   }

   private static void LjZuxgB(ExternalRenderBuffer var0, double var1, double var3, float var5, float var6, float var7, float var8, int var9, int var10, float var11) {
      if ((var10 & 1) != 0) {
         int var12 = Math.round(Math.max(0.0F, Math.min(1.0F, var8)) * 255.0F);
         double var13 = var1 - 3.0F * var11;
         double var15 = var3 - 3.0F * var11;
         double var17 = var1 + var5 + 3.0F * var11;
         double var19 = var3 + var6 + 2.0F * var11;
         if ((var10 & 2) != 0) {
            var0.fillRoundedRect(var13 - var11, var15 - var11, var17 + var11, var19 + var11, var9, ExternalRenderBuffer.XNfyt(var7 + var11, 1.0F));
         }

         var0.fillRoundedRect(var13, var15, var17, var19, var12 << 24, ExternalRenderBuffer.XNfyt(var7, 1.0F));
      }
   }
}
