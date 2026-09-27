// Jade recovery: original class: jade.deps.eLz.ZuFwW2c
package jade.client.common;

import net.minecraft.item.ItemStack;

public final class DurabilityUtils {
   private DurabilityUtils() {
   }

   public static double xuZl(ItemStack var0, boolean var1) {
      return var0 != null && var0.isItemStackDamageable() ? ItemScoreUtils.getDurabilityScore(var0.getItemDamage(), var0.getMaxDamage(), var1) : 0.0;
   }
}
