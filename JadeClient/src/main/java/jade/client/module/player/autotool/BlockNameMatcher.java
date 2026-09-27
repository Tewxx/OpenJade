// Jade recovery: original class: jade.deps.eLz.e00nad
package jade.client.module.player.autotool;

import java.util.function.Predicate;

public final class BlockNameMatcher {
   private BlockNameMatcher() {
   }

   public static boolean matchesNameOrMeta(String var0, int var1, Predicate<String> var2) {
      if (var0 == null) {
         return false;
      } else {
         String var3 = var1 == 0 ? var0 : var0 + ":" + var1;
         return var2.test(var3) || var2.test(var0);
      }
   }
}
