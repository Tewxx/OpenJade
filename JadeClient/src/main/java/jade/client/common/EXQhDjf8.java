// Jade recovery: original class: jade.deps.eLz.EXQhDjf8
package jade.client.common;

import net.minecraft.client.gui.FontRenderer;

public final class EXQhDjf8 {
   private static final char COLOR_CODE_PREFIX = '§';
   private static final int VARIATION_SELECTOR_16 = 65039;
   private static boolean cYxz;

   private EXQhDjf8() {
   }

   public static boolean isRenderingGlyphs() {
      return cYxz;
   }

   public static boolean TQHTc3(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         int var1 = 0;

         while (var1 < var0.length()) {
            int var2 = Character.codePointAt(var0, var1);
            if (isSupportedGlyph(var2)) {
               return true;
            }

            var1 += Character.charCount(var2);
         }

         return false;
      } else {
         return false;
      }
   }

   public static int getStringWidth(FontRenderer var0, String var1) {
      if (var0 != null && var1 != null && !var1.isEmpty()) {
         cYxz = true;

         int var2;
         try {
            var2 = drawOrMeasureString(var0, var1, 0.0F, 0.0F, -1, false, false);
         } finally {
            cYxz = false;
         }

         return var2;
      } else {
         return 0;
      }
   }

   public static int drawString(FontRenderer var0, String var1, float var2, float var3, int var4, boolean var5) {
      if (var0 != null && var1 != null) {
         cYxz = true;

         int var6;
         try {
            var6 = Math.round(var2 + drawOrMeasureString(var0, var1, var2, var3, var4, var5, true));
         } finally {
            cYxz = false;
         }

         return var6;
      } else {
         return Math.round(var2);
      }
   }

   private static int drawOrMeasureString(FontRenderer var0, String var1, float var2, float var3, int var4, boolean var5, boolean var6) {
      FormattingState var7 = new FormattingState();
      String var8 = "";
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;

      while (var11 < var1.length()) {
         char var12 = var1.charAt(var11);
         if (var12 == 167 && var11 + 1 < var1.length()) {
            FormattingState.acceptFormatCode(var7, var1.charAt(var11 + 1));
            var11 += 2;
         } else {
            int var13 = Character.codePointAt(var1, var11);
            int var14 = Character.charCount(var13);
            if (!isSupportedGlyph(var13)) {
               var11 += var14;
            } else {
               String var15 = var8 + var1.substring(var9, var11);
               int var16 = var0.getStringWidth(var15);
               if (var6 && !var15.isEmpty()) {
                  var0.drawString(var15, var2 + var10, var3, var4, var5);
               }

               var10 += var16;
               int var17 = getGlyphSize(var0, FormattingState.isBold(var7));
               if (var6) {
                  drawGlyphAt(var0, var13, var2 + var10, var3, var17, var4, var7, var5);
               }

               var10 += var17;
               var11 += var14;
               if (var11 < var1.length() && var1.charAt(var11) == '️') {
                  var11++;
               }

               var9 = var11;
               var8 = FormattingState.getFormattingString(var7);
            }
         }
      }

      String var19 = var8 + var1.substring(var9);
      if (var6 && !var19.isEmpty()) {
         var0.drawString(var19, var2 + var10, var3, var4, var5);
      }

      return var10 + var0.getStringWidth(var19);
   }

   public static float MUMCHwh(FontRenderer var0, int var1, float var2, float var3, int var4) {
      int var5 = getGlyphSize(var0, false);
      float var6 = Math.max(4.0F, var5 - 1.0F);
      float var7 = var2 + (var5 - var6) * 0.5F;
      float var8 = var3 + (var0.FONT_HEIGHT - var6) * 0.5F - 0.2F;
      MzkA(var7, var8, var6, var4, QWFA(var1));
      return var5;
   }

   private static void drawGlyphAt(FontRenderer var0, int var1, float var2, float var3, int var4, int var5, FormattingState var6, boolean var7) {
      float var8 = Math.max(4.0F, var4 - 1.0F);
      float var9 = var2 + (var4 - var8) * 0.5F;
      float var10 = var3 + (var0.FONT_HEIGHT - var8) * 0.5F - 0.2F;
      int var11 = FormattingState.applyColorCode(var6, var5, var0);
      boolean var12 = QWFA(var1);
      if (var7) {
         int var13 = Rdd6(var11);
         MzkA(var9 + 0.5F, var10 + 0.5F, var8, var13, var12);
      }

      MzkA(var9, var10, var8, var11, var12);
   }

   private static void MzkA(float var0, float var1, float var2, int var3, boolean var4) {
      if (var4) {
         FallbackGlyphShapes.mnxstly(var0, var1, var2, var3);
      } else {
         FallbackGlyphShapes.drawCircle(var0, var1, var2, var3);
      }
   }

   public static int getGlyphSize(FontRenderer var0, boolean var1) {
      return Math.max(5, Math.round(var0.FONT_HEIGHT * 0.78F)) + (var1 ? 1 : 0);
   }

   private static int Rdd6(int var0) {
      int var1 = var0 >>> 24;
      int var2 = (var0 >>> 16 & 0xFF) >> 2;
      int var3 = (var0 >>> 8 & 0xFF) >> 2;
      int var4 = (var0 & 0xFF) >> 2;
      return var1 << 24 | var2 << 16 | var3 << 8 | var4;
   }

   public static boolean QWFA(int var0) {
      return var0 == 8902
         || var0 == 9733
         || var0 == 10018
         || var0 == 10019
         || var0 == 10020
         || var0 == 10021
         || var0 == 10022
         || var0 == 10024
         || var0 == 10033
         || var0 >= 10035 && var0 <= 10039
         || var0 == 10050
         || var0 == 10057
         || var0 == 10059
         || var0 == 11088
         || var0 == 127775
         || var0 == 127776
         || var0 == 128171;
   }

   public static boolean isSupportedGlyph(int var0) {
      return var0 == 8902
         || var0 == 9733
         || var0 == 9734
         || var0 == 9885
         || var0 >= 10017 && var0 <= 10039
         || var0 == 10050
         || var0 == 10057
         || var0 == 10059
         || var0 == 11088
         || var0 == 127775
         || var0 == 127776
         || var0 == 128171;
   }
}
