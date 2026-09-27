// Jade recovery: original class: jade.deps.eLz.isrMzh
package jade.client.common;

import jade.client.module.combat.attributeswap.ItemScorer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public final class HitsToKillCalculator {
   private HitsToKillCalculator() {
   }

   public static double getHitsToKill(EntityPlayer var0, ItemStack var1) {
      double var2 = 1.0;
      if (var1 != null && (var1.getItem() instanceof ItemSword || var1.getItem() instanceof ItemAxe)) {
         var2 += ItemScorer.getMeleeDamage(var1);
      }

      double var4 = 0.0;
      double var6 = 0.0;

      for (ItemStack var11 : var0.inventory.armorInventory) {
         if (var11 != null && var11.getItem() instanceof ItemArmor) {
            var4 += ((ItemArmor)var11.getItem()).damageReduceAmount * 0.04;
            int var12 = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var11);
            if (var12 != 0) {
               var6 += Math.floor(0.75 * (6 + var12 * var12) / 3.0);
            }
         }
      }

      double var14 = 0.04 * Math.min(Math.ceil(Math.min(var6, 25.0) * 0.75), 20.0);
      double var15 = var4 + var14 * (1.0 - var4);
      double var16 = var0.getHealth() + var0.getAbsorptionAmount();
      return Math.round(var16 / (var2 * (1.0 - var15)) * 10.0) / 10.0;
   }

   public static String formatHitsToKill(EntityPlayer var0, ItemStack var1) {
      int var2 = (int)Math.ceil(getHitsToKill(var0, var1));
      int var3 = var2 <= 1 ? 99 : (var2 <= 3 ? 54 : (var2 <= 5 ? 101 : 97));
      return "§" + var3 + var2;
   }
}
