// Jade recovery: original class: jade.deps.eLz.u8qSaJ29
package jade.client.misc;

public final class ColorCodeAwareReplacer {
   private ColorCodeAwareReplacer() {
   }

   public static String replaceOutsideColorCodes(String var0, String var1, String var2) {
      if (var0 != null && var1 != null && var2 != null) {
         if (var0.length() != 0 && var1.length() != 0) {
            LegacyTextBoundary var3 = new LegacyTextBoundary(var0);
            ColorCodeTracker var4 = new ColorCodeTracker();
            StringBuilder var5 = new StringBuilder();
            int var6 = 0;
            int var7 = 0;

            while (var7 < var0.length()) {
               if (var0.charAt(var7) == 167 && var7 + 1 < var0.length()) {
                  var4.parseCode(var0.charAt(var7 + 1));
                  var7 += 2;
               } else {
                  int var8 = var7 + var1.length();
                  if (var8 <= var0.length() && var0.regionMatches(true, var7, var1, 0, var1.length()) && var3.gfN5(var7, var8)) {
                     var5.append(var0, var6, var7).append(var2);
                     var4.appendActiveCodes(var5);
                     var7 = var8;
                     var6 = var8;
                  } else {
                     var7++;
                  }
               }
            }

            return var5.append(var0, var6, var0.length()).toString();
         } else {
            return var0;
         }
      } else {
         return var0;
      }
   }
}
