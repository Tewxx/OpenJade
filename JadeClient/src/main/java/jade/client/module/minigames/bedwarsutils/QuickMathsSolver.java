// Jade recovery: original class: jade.deps.eLz.EBVkrx
package jade.client.module.minigames.bedwarsutils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class QuickMathsSolver {
   private static final String vcOi = "([+-]?(?:\\d{1,18}(?:\\.\\d{1,12})?|\\.\\d{1,12}))";
   private static final Pattern pattern = Pattern.compile(
      "^\\s*QUICK\\s+MATHS!\\s*([+-]?(?:\\d{1,18}(?:\\.\\d{1,12})?|\\.\\d{1,12}))\\s*([+\\-/*])\\s*([+-]?(?:\\d{1,18}(?:\\.\\d{1,12})?|\\.\\d{1,12}))\\s*=\\s*\\?\\s*$",
      2
   );

   private QuickMathsSolver() {
   }

   public static String solveQuickMaths(String var0) {
      if (var0 == null) {
         return null;
      } else {
         Matcher var1 = pattern.matcher(var0);
         if (!var1.matches()) {
            return null;
         } else {
            try {
               BigDecimal var2 = new BigDecimal(var1.group(1));
               BigDecimal var3 = new BigDecimal(var1.group(3));
               char var5 = var1.group(2).charAt(0);
               BigDecimal var4;
               if (var5 == '+') {
                  var4 = var2.add(var3);
               } else if (var5 == '-') {
                  var4 = var2.subtract(var3);
               } else if (var5 == '*') {
                  var4 = var2.multiply(var3);
               } else {
                  if (var5 != '/') {
                     return null;
                  }

                  if (var3.signum() == 0) {
                     return null;
                  }

                  var4 = var2.divide(var3);
               }

               return var4.signum() == 0 ? "0" : var4.stripTrailingZeros().toPlainString();
            } catch (ArithmeticException var6) {
               return null;
            } catch (NumberFormatException var7) {
               return null;
            }
         }
      }
   }
}
