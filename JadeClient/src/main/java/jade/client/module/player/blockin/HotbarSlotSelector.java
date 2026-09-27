// Jade recovery: original class: jade.deps.eLz.yz20yy
package jade.client.module.player.blockin;

import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;

public final class HotbarSlotSelector {
   private HotbarSlotSelector() {
   }

   public static int selectSlotByScore(boolean var0, IntPredicate var1, IntToDoubleFunction var2) {
      int var3 = -1;
      float var4 = var0 ? -1.0F : Float.MAX_VALUE;

      for (int var5 = 8; var5 >= 0; var5--) {
         if (var1.test(var5)) {
            float var6 = (float)var2.applyAsDouble(var5);
            if (var0 ? var6 > var4 : var6 < var4) {
               var4 = var6;
               var3 = var5;
            }
         }
      }

      return var3;
   }
}
