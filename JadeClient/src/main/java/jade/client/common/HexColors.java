// Jade recovery: original class: jade.deps.eLz.wst0zoFmD
package jade.client.common;

import java.util.Locale;

public final class HexColors {
   private HexColors() {
   }

   public static int[] parseHexColor(String var0) {
      String var1 = var0 == null ? "" : var0.trim();
      if (var1.startsWith("#")) {
         var1 = var1.substring(1);
      } else if (var1.startsWith("0x") || var1.startsWith("0X")) {
         var1 = var1.substring(2);
      }

      if (var1.length() == 3) {
         var1 = expandShortHex(var1);
      }

      if ((var1.length() == 6 || var1.length() == 8) && isHexDigits(var1)) {
         int var2 = var1.length() / 2;
         int[] var3 = new int[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var3[var4] = Integer.parseInt(var1.substring(var4 * 2, var4 * 2 + 2), 16);
         }

         return var3;
      } else {
         return null;
      }
   }

   public static String formatHexColor(int var0, int var1, int var2, int var3, boolean var4) {
      return var4 ? String.format(Locale.ROOT, "#%02X%02X%02X%02X", var0, var1, var2, var3) : String.format(Locale.ROOT, "#%02X%02X%02X", var0, var1, var2);
   }

   private static String expandShortHex(String var0) {
      StringBuilder var1 = new StringBuilder(6);

      for (int var2 = 0; var2 < var0.length(); var2++) {
         var1.append(var0.charAt(var2)).append(var0.charAt(var2));
      }

      return var1.toString();
   }

   private static boolean isHexDigits(String var0) {
      for (int var1 = 0; var1 < var0.length(); var1++) {
         if (Character.digit(var0.charAt(var1), 16) < 0) {
            return false;
         }
      }

      return true;
   }
}
