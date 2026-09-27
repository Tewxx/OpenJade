// Jade recovery: original class: jade.deps.eLz.pDhwqG
package jade.client.common;

import java.util.function.IntToDoubleFunction;

public final class MaxIndexFinder {
   private MaxIndexFinder() {
   }

   public static int findIndexOfLargest(int var0, IntToDoubleFunction var1) {
      int var2 = -1;
      double var3 = 1.0;

      for (int var5 = 0; var5 < var0; var5++) {
         double var6 = var1.applyAsDouble(var5);
         if (var6 > var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }
}
