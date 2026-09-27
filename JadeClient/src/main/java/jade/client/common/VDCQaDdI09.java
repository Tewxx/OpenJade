// Jade recovery: original class: jade.deps.eLz.VDCQaDdI09
package jade.client.common;

import java.util.ArrayList;
import java.util.List;

public final class VDCQaDdI09 {
   private VDCQaDdI09() {
   }

   public static List<String> jwaDz(String var0) {
      ArrayList var1 = new ArrayList();
      int var2 = 0;
      boolean var3 = false;

      for (String var7 : var0.split("\\r?\\n")) {
         String var8 = var7.trim();
         if (var3) {
            int var9 = var8.indexOf("*/");
            if (var9 < 0) {
               continue;
            }

            var3 = false;
            var8 = var8.substring(var9 + 2).trim();
         }

         if (!var8.startsWith("//")) {
            int var14 = var8.indexOf("/*");
            if (var14 >= 0) {
               var3 = true;
               var8 = var8.substring(0, var14).trim();
               if (var8.length() == 0) {
                  continue;
               }
            }

            int var10 = var8.indexOf("//");
            if (var10 >= 0) {
               var8 = var8.substring(0, var10).trim();
            }

            if (var8.length() != 0) {
               String var11 = nShz06(var8);

               for (int var12 = 0; var12 < var11.length(); var12++) {
                  char var13 = var11.charAt(var12);
                  if (var13 == '{') {
                     var2++;
                  } else if (var13 == '}') {
                     var2--;
                  }
               }

               if (var2 == 0 && var8.indexOf(123) < 0 && var8.indexOf(125) < 0 && !var8.startsWith("@")) {
                  var1.add(var7.trim());
               }
            }
         }
      }

      return var1;
   }

   private static String nShz06(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length());
      boolean var2 = false;

      for (int var3 = 0; var3 < var0.length(); var3++) {
         char var4 = var0.charAt(var3);
         if (var4 == '"' && (var3 == 0 || var0.charAt(var3 - 1) != '\\')) {
            var2 = !var2;
         } else if (!var2) {
            var1.append(var4);
         }
      }

      return var1.toString();
   }
}
