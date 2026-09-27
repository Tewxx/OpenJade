// Jade recovery: original class: jade.deps.eLz.Wlo914
package jade.client.common;

import java.util.function.IntPredicate;

public final class Wlo914 {
   private Wlo914() {
   }

   public static boolean GKjTw(int var0, IntPredicate var1, IntPredicate var2) {
      return var0 >= 0 ? var1.test(var0) : var2.test(var0 + 100);
   }
}
