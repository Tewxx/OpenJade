// Jade recovery: original class: jade.deps.eLz.gWoWk9epr
package jade.client.common;

import java.util.function.IntPredicate;

public final class VerticalBlockScan {
   private VerticalBlockScan() {
   }

   public static boolean isColumnBlockedUpTo(double var0, IntPredicate var2) {
      for (int var3 = (int)var0; var3 >= 0; var3--) {
         if (!var2.test(var3)) {
            return false;
         }
      }

      return true;
   }

   public static double measureDistanceToGround(double var0, boolean var2, IntPredicate var3) {
      if (var2) {
         return 0.0;
      } else {
         double var4 = var0 % 1.0 == 0.0 ? var0 - 1.0 : var0;

         for (int var6 = (int)Math.floor(var4); var6 >= 0; var6--) {
            if (!var3.test(var6)) {
               return var4 - var6 - 1.0;
            }
         }

         return -2.0;
      }
   }
}
