// Jade recovery: original class: jade.deps.eLz.OaLtIJ8WM
package jade.client.module.player.inventorymanager;

import jade.client.common.ItemMatcher;
import jade.client.setting.CategoryListSetting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public final class ArmorScorer {
   private static final double SCORE_EPSILON = 1.0E-6;

   private ArmorScorer() {
   }

   public static boolean isProtectedItem(ItemStack var0) {
      if (var0 == null || var0.getItem() == null) {
         return false;
      } else if (!(var0.getItem() instanceof ItemArmor) && !(var0.getItem() instanceof ItemBlock) && !(var0.getItem() instanceof ItemPotion)) {
         if (var0.getItem() != Items.arrow
            && var0.getItem() != Items.water_bucket
            && var0.getItem() != Items.lava_bucket
            && var0.getItem() != Items.milk_bucket) {
            for (String var4 : CategoryListSetting.keM) {
               if (ItemMatcher.matches(var4, var0)) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public static int getArmorType(ItemStack var0) {
      return var0 != null && var0.getItem() instanceof ItemArmor ? ((ItemArmor)var0.getItem()).armorType : -1;
   }

   public static boolean isArmorOfType(ItemStack var0, int var1) {
      return getArmorType(var0) == var1;
   }

   public static double scoreArmor(ItemStack var0, int var1, boolean var2) {
      if (!isArmorOfType(var0, var1)) {
         return 0.0;
      } else {
         ItemArmor var3 = (ItemArmor)var0.getItem();
         double var4 = var3.damageReduceAmount * 100.0;
         var4 += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var0) * 5.0;
         var4 += EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, var0);
         var4 += EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, var0);
         var4 += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, var0);
         var4 += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, var0) * 0.5;
         if (var2 && var0.isItemStackDamageable() && var0.getMaxDamage() > 0) {
            double var6 = (double)(var0.getMaxDamage() - var0.getItemDamage()) / var0.getMaxDamage();
            var4 += var6 / 1000.0;
         }

         return var4;
      }
   }

   public static int findBestArmorSlot(ItemStack[] var0, ItemStack var1, int var2, boolean var3) {
      double var4 = scoreArmor(var1, var2, false);
      double var6 = scoreArmor(var1, var2, var3);
      int var8 = -1;

      for (int var9 = 0; var9 < var0.length; var9++) {
         ItemStack var10 = var0[var9];
         if (isArmorOfType(var10, var2)) {
            double var11 = scoreArmor(var10, var2, false);
            double var13 = scoreArmor(var10, var2, var3);
            if (var11 > var4 + 1.0E-6) {
               var4 = var11;
               var6 = var13;
               var8 = var9;
            } else if (var8 >= 0 && Math.abs(var11 - var4) <= 1.0E-6 && var13 > var6 + 1.0E-6) {
               var6 = var13;
               var8 = var9;
            }
         }
      }

      return var8;
   }

   public static int wnQdxGw(ItemStack[] var0, ItemStack var1, int var2, boolean var3) {
      double var4 = scoreArmor(var1, var2, var3) + 0.001;
      int var6 = -1;

      for (int var7 = 0; var7 < var0.length; var7++) {
         ItemStack var8 = var0[var7];
         double var9 = scoreArmor(var8, var2, var3);
         if (isArmorOfType(var8, var2) && var9 > var4) {
            var4 = var9;
            var6 = var7;
         }
      }

      return var6;
   }
}
