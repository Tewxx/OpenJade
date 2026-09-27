// Jade recovery: original class: jade.deps.eLz.RzUgdC
package jade.client.module.player.hidewindow;

import java.util.List;

public final class RzUgdC {
   private RzUgdC() {
   }

   public static boolean matchesWhitelistEntry(List<String> var0, String var1) {
      if (!var0.isEmpty() && !var1.isEmpty()) {
         String var2 = var1.toLowerCase();

         for (String var4 : var0) {
            if (var2.contains(var4.toLowerCase())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
