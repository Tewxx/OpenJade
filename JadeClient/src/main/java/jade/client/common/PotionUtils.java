// Jade recovery: original class: jade.deps.eLz.oLM4pj
package jade.client.common;

import java.util.List;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionEffect;

public final class PotionUtils {
   private PotionUtils() {
   }

   public static int findBestPotionMetadata(ItemPotion var0, int var1) {
      int var2 = 0;
      int var3 = Integer.MIN_VALUE;

      for (int var4 = 0; var4 <= 16384; var4++) {
         int var5 = scorePotionEffects(var0.getEffects(var4), var1, ItemPotion.isSplash(var4));
         if (var5 > var3) {
            var3 = var5;
            var2 = var4;
         }
      }

      return var2;
   }

   public static int scorePotionEffects(List<PotionEffect> var0, int var1, boolean var2) {
      if (var0 != null && !var0.isEmpty()) {
         int var3 = 0;
         int var4 = 0;

         for (PotionEffect var6 : var0) {
            if (var6.getPotionID() == var1) {
               var3++;
               if (var6.getAmplifier() == 0) {
                  var4++;
               }
            }
         }

         if (var3 == 0) {
            return Integer.MIN_VALUE;
         } else {
            int var7 = 100 * var3 + 10 * var4 - 20 * (var0.size() - var3);
            return var7 + (var0.size() == 1 ? 50 : 0) + (var2 ? 0 : 25);
         }
      } else {
         return Integer.MIN_VALUE;
      }
   }
}
