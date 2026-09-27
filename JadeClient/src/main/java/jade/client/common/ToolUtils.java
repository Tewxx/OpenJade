// Jade recovery: original class: jade.deps.eLz.ASjfup3c
package jade.client.common;

import jade.client.module.combat.attributeswap.ItemScorer;
import java.util.function.IntSupplier;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public final class ToolUtils {
   private ToolUtils() {
   }

   public static int XJUbym(InventoryPlayer var0, Block var1) {
      return MaxIndexFinder.findIndexOfLargest(InventoryPlayer.getHotbarSize(), (recoveredArg0) -> ToolUtils.getSlotItemScore(var0, var1, recoveredArg0));
   }

   public static float getEffectiveBlockSpeed(ItemStack var0, Block var1) {
      float var2 = var0.getStrVsBlock(var1);
      return applyEfficiencyBonus(var2, () -> ToolUtils.getEfficiencyLevel(var0));
   }

   public static float applyEfficiencyBonus(float var0, IntSupplier var1) {
      if (!(var0 > 1.0F)) {
         return var0;
      } else {
         int var2 = var1.getAsInt();
         return var2 > 0 ? var0 + (var2 * var2 + 1) : var0;
      }
   }

   public static double Mbr2(ItemStack var0, Block var1, boolean var2) {
      return var0 != null && var1 != null
         ? ItemScoreUtils.getToolScore(
            var0.getStrVsBlock(var1),
            WeaponDamage.getEnchantmentLevel(var0, Enchantment.efficiency),
            var0.canHarvestBlock(var1),
            !var1.getMaterial().isToolNotRequired(),
            DurabilityUtils.xuZl(var0, var2)
         )
         : 0.0;
   }

   public static double getHoeTotalDurability(ItemStack var0, boolean var1) {
      return var0 != null && var0.getItem() instanceof ItemHoe ? var0.getMaxDamage() + DurabilityUtils.xuZl(var0, var1) : 0.0;
   }

   private static int getEfficiencyLevel(ItemStack var0) {
      return EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, var0);
   }

   private static double getSlotItemScore(InventoryPlayer var0, Block var1, int var2) {
      ItemStack var3 = var0.getStackInSlot(var2);
      return var3 == null ? Double.NaN : ItemScorer.getBlockBreakingScore(var3, var1);
   }
}
