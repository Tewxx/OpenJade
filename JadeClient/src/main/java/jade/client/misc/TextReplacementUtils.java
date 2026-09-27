// Jade recovery: original class: jade.deps.eLz.T3dftxAddn
package jade.client.misc;

import jade.client.common.ClientUtils;

public final class TextReplacementUtils {
   private TextReplacementUtils() {
   }

   public static String replaceEachIgnoreCase(String var0, Iterable<String> var1, String var2) {
      String var3 = var0;
      if (var1 != null) {
         for (String var5 : var1) {
            var3 = ColorCodeAwareReplacer.replaceOutsideColorCodes(var3, var5, var2);
         }
      }

      return var3;
   }

   public static String replaceIgnoreCase(String var0, String var1, String var2) {
      return ColorCodeAwareReplacer.replaceOutsideColorCodes(var0, var1, var2);
   }

   public static String extractLastWord(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = ClientUtils.AOAtn(var0).trim();
         return var1.length() == 0 ? null : var1.substring(var1.lastIndexOf(32) + 1);
      }
   }
}
