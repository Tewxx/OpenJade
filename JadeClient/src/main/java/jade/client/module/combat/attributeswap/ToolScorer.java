// Jade recovery: original class: jade.deps.eLz.JXLTUMsmk
package jade.client.module.combat.attributeswap;

import jade.client.common.BlockUtils;
import jade.client.common.DurabilityUtils;
import jade.client.common.WeaponDamage;
import jade.client.common.ItemScoreUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public final class ToolScorer {
   private ToolScorer() {
   }

   public static double scoreBlockStack(ItemStack var0) {
      int var1 = getBlockPriority(var0);
      return var1 == 0 ? 0.0 : var1 * 1000.0 + var0.stackSize;
   }

   public static double scoreStick(ItemStack var0) {
      return var0 != null && var0.getItem() == Items.stick ? 10.0 + WeaponDamage.getEnchantmentLevel(var0, Enchantment.knockback) * 10.0 + var0.stackSize / 100.0 : 0.0;
   }

   public static double scoreBow(ItemStack var0, boolean var1) {
      return var0 != null && var0.getItem() instanceof ItemBow
         ? WeaponDamage.getEnchantmentLevel(var0, Enchantment.punch) * 1000.0 + WeaponDamage.getEnchantmentLevel(var0, Enchantment.power) * 10.0 + DurabilityUtils.xuZl(var0, var1)
         : 0.0;
   }

   public static double scoreFishingRod(ItemStack var0, boolean var1) {
      return var0 != null && var0.getItem() instanceof ItemFishingRod
         ? WeaponDamage.getEnchantmentLevel(var0, Enchantment.knockback) * 1000.0 + 1.0 + DurabilityUtils.xuZl(var0, var1)
         : 0.0;
   }

   public static double scoreFish(ItemStack var0) {
      return var0 != null && (var0.getItem() == Items.fish || var0.getItem() == Items.cooked_fish)
         ? WeaponDamage.getEnchantmentLevel(var0, Enchantment.knockback) * 1000.0 + var0.stackSize
         : 0.0;
   }

   public static double scoreAxe(ItemStack var0, boolean var1) {
      return var0 != null && var0.getItem() instanceof ItemAxe ? scoreTool(var0, var1) : 0.0;
   }

   public static double scorePickaxe(ItemStack var0, boolean var1) {
      return var0 != null && var0.getItem() instanceof ItemPickaxe ? scoreTool(var0, var1) : 0.0;
   }

   public static double scoreShears(ItemStack var0, boolean var1) {
      return var0 != null && var0.getItem() instanceof ItemShears ? 10.0 + WeaponDamage.getEnchantmentLevel(var0, Enchantment.efficiency) + DurabilityUtils.xuZl(var0, var1) : 0.0;
   }

   private static double scoreTool(ItemStack var0, boolean var1) {
      return ItemScoreUtils.combineToolScore(getToolMaterialTier(var0), WeaponDamage.getEnchantmentLevel(var0, Enchantment.efficiency), WeaponDamage.getEnchantmentLevel(var0, Enchantment.unbreaking), DurabilityUtils.xuZl(var0, var1));
   }

   private static int getToolMaterialTier(ItemStack var0) {
      if (!(var0.getItem() instanceof ItemTool)) {
         return 0;
      } else {
         String var1 = ((ItemTool)var0.getItem()).getToolMaterialName();
         if ("EMERALD".equals(var1)) {
            return 5;
         } else if ("IRON".equals(var1)) {
            return 4;
         } else if ("STONE".equals(var1)) {
            return 3;
         } else if ("GOLD".equals(var1)) {
            return 2;
         } else {
            return "WOOD".equals(var1) ? 1 : 0;
         }
      }
   }

   private static int getBlockPriority(ItemStack var0) {
      if (var0 != null && var0.getItem() != null) {
         Block var1 = Block.getBlockFromItem(var0.getItem());
         if (var1 == null) {
            return 0;
         } else if (var1 == Blocks.wool) {
            return 4;
         } else if (var1 == Blocks.ice
            || var1 == Blocks.packed_ice
            || var1 == Blocks.hardened_clay
            || var1 == Blocks.stained_hardened_clay
            || var1 == Blocks.end_stone
            || var1 == Blocks.stone
            || var1 == Blocks.cobblestone
            || var1 == Blocks.stonebrick
            || var1 == Blocks.log
            || var1 == Blocks.log2
            || var1 == Blocks.planks) {
            return 3;
         } else if (var1 == Blocks.glass || var1 == Blocks.stained_glass) {
            return 2;
         } else if (var1 == Blocks.obsidian) {
            return 1;
         } else {
            return BlockUtils.isOpaqueFullBlock(var1) ? 2 : 0;
         }
      } else {
         return 0;
      }
   }
}
