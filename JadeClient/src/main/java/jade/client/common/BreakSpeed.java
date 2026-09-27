// Jade recovery: original class: jade.deps.eLz.Y8lZd0
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public final class BreakSpeed {
   private BreakSpeed() {
   }

   public static float computeBreakSpeed(Block var0, ItemStack var1, EntityPlayer var2, World var3, boolean var4, boolean var5) {
      float var6 = var0.getBlockHardness(var3, null);
      if (var6 < 0.0F) {
         return 0.0F;
      } else {
         float var7 = QednSn(var1, var0, var2, var4, var5);
         boolean var8 = var0.getMaterial().isToolNotRequired() || var1 != null && var1.canHarvestBlock(var0);
         return var7 / var6 / (var8 ? 30.0F : 100.0F);
      }
   }

   public static float QednSn(ItemStack var0, Block var1, EntityPlayer var2, boolean var3, boolean var4) {
      float var5 = var0 == null ? 1.0F : var0.getItem().getStrVsBlock(var0, var1);
      if (var5 > 1.0F) {
         int var6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, var0);
         if (var6 > 0 && var0 != null) {
            var5 += var6 * var6 + 1;
         }
      }

      if (var2.isPotionActive(Potion.digSpeed)) {
         var5 *= 1.0F + (var2.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (var3) {
         return var5;
      } else {
         if (var2.isPotionActive(Potion.digSlowdown)) {
            var5 *= getDigSlowdownMultiplier(var2.getActivePotionEffect(Potion.digSlowdown).getAmplifier());
         }

         if (var2.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(var2)) {
            var5 /= 5.0F;
         }

         if (!var2.onGround && !var4) {
            var5 /= 5.0F;
         }

         return var5;
      }
   }

   private static float getDigSlowdownMultiplier(int var0) {
      switch (var0) {
         case 0:
            return 0.3F;
         case 1:
            return 0.09F;
         case 2:
            return 0.0027F;
         default:
            return 8.1E-4F;
      }
   }

   public static float kXrgJ(Block var0, World var1) {
      float var2 = var0.getBlockHardness(var1, null);
      if (var2 < 0.0F) {
         return Float.MAX_VALUE;
      } else {
         return var2 == 0.0F ? 0.0F : var2 * (var0.getMaterial().isToolNotRequired() ? 30.0F : 100.0F);
      }
   }
}
