// Jade recovery: original class: jade.deps.eLz.GVMfJYO
package jade.client.module.minigames.opsec;

import jade.client.common.QuickBuyLayout;
import java.util.List;
import java.util.Map;

public final class QuickBuyCatalogue {
   private QuickBuyCatalogue() {
   }

   public static void XQxM(Map<String, Integer> var0, List<String> var1, int var2) {
      if (var0 != null && var1 != null && var2 >= 0) {
         for (String var4 : var1) {
            String var5 = QuickBuyLayout.normalizeItemName(var4);
            if (!var5.isEmpty() && !var0.containsKey(var5)) {
               var0.put(var5, var2);
            }
         }
      }
   }

   public static int incrementPageIf(int var0, boolean var1) {
      return var1 ? var0 + 1 : var0;
   }

   public static int decrementPageIf(int var0, boolean var1) {
      return var1 ? Math.max(0, var0 - 1) : var0;
   }
}
