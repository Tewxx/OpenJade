// Jade recovery: original class: jade.deps.eLz.xqHsE7Caxu
package jade.client.common;

import java.util.Locale;
import java.util.Random;

public final class TextUtils {
   private static final char SECTION_SIGN = '§';
   private static final String COLOR_CODES = "0123456789abcdef";
   private static final String nNhp = "abcdefghijklmnopqrstuvwxyz0123456789";

   private TextUtils() {
   }

   public static String UNOSfz(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0;

         for (char var5 : new char[]{'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'r', 'R'}) {
            var1 = var1.replace(new String(new char[]{'§', var5}), "");
         }

         return var1;
      }
   }

   public static boolean isWholeNumber(double var0) {
      return var0 == Math.floor(var0);
   }

   public static String ASXEvc(double var0) {
      return isWholeNumber(var0) ? Integer.toString((int)var0) : Double.toString(var0);
   }

   public static int randomIntInclusive(Random var0, int var1, int var2) {
      return var1 + var0.nextInt(var2 - var1 + 1);
   }

   public static double randomDoubleInRange(Random var0, double var1, double var3) {
      return var1 + var0.nextDouble() * (var3 - var1);
   }

   public static String formatHealthDisplay(double var0, double var2, boolean var4, boolean var5) {
      double var6 = var4 ? var2 / 2.0 : var2;
      double var8 = Math.round(var6 * 10.0) / 10.0;
      StringBuilder var10 = new StringBuilder();
      var10.append('§').append(jfiol(var0)).append(ASXEvc(var8));
      if (var5) {
         var10.append('§').append('c').append('❤').append('§').append('r');
      }

      return var10.toString();
   }

   public static int getHealthColor(double var0) {
      if (var0 < 0.3) {
         return -43691;
      } else if (var0 < 0.5) {
         return -22016;
      } else {
         return var0 < 0.7 ? -171 : -11141291;
      }
   }

   private static char jfiol(double var0) {
      if (var0 < 0.3) {
         return 'c';
      } else if (var0 < 0.5) {
         return '6';
      } else {
         return (char)(var0 < 0.7 ? 'e' : 'a');
      }
   }

   public static String QJdK(String var0) {
      return var0 == null ? "" : var0.replace('&', '§');
   }

   public static String NTfs(String var0) {
      if (var0 == null) {
         return "";
      } else {
         for (int var1 = 0; var1 + 1 < var0.length(); var1++) {
            if (var0.charAt(var1) == 167) {
               char var2 = var0.charAt(var1 + 1);
               if ("0123456789abcdef".indexOf(Character.toLowerCase(var2)) >= 0) {
                  return new String(new char[]{'§', var2});
               }
            }
         }

         return "";
      }
   }

   public static int countBoldCharacters(String var0) {
      boolean var1 = false;
      int var2 = 0;
      String var3 = var0.toLowerCase(Locale.ENGLISH);

      for (int var4 = 0; var4 < var0.length(); var4++) {
         if (var0.charAt(var4) == 167 && var4 + 1 < var0.length()) {
            var1 = var3.charAt(var4 + 1) == 'l' || var1;
            var4++;
         } else if (var1) {
            var2++;
         }
      }

      return var2;
   }

   public static String randomAlphanumeric(Random var0, int var1) {
      StringBuilder var2 = new StringBuilder(var1);

      for (int var3 = 0; var3 < var1; var3++) {
         var2.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(var0.nextInt("abcdefghijklmnopqrstuvwxyz0123456789".length())));
      }

      return var2.toString();
   }

   public static String extractMiddleUnderscoreSegment(String var0) {
      int var1 = var0.indexOf(95);
      int var2 = var0.lastIndexOf(95);
      return var1 >= 0 && var2 > var1 ? var0.substring(var1 + 1, var2) : var0;
   }

   public static int withAlpha(int var0, int var1) {
      return var0 & 16777215 | var1 << 24;
   }

   public static int clampAlpha(int var0) {
      return Math.max(4, Math.min(255, var0));
   }

   public static int SxrM(int var0, double var1) {
      int[] var3 = new int[]{var0 >>> 16 & 0xFF, var0 >>> 8 & 0xFF, var0 & 0xFF};
      double var4 = (100.0 - var1) / 100.0;

      for (int var6 = 0; var6 < var3.length; var6++) {
         var3[var6] = clampAlpha((int)(var3[var6] * var4));
      }

      return var0 & 0xFF000000 | var3[0] << 16 | var3[1] << 8 | var3[2];
   }
}
