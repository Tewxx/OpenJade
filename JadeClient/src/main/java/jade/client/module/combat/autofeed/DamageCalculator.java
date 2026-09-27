// Jade recovery: original class: jade.deps.eLz.g1NRZM
package jade.client.module.combat.autofeed;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public final class DamageCalculator {
   private DamageCalculator() {
   }

   public static float getAttackDamage(EntityPlayer var0, EntityLivingBase var1) {
      if (var0 != null && var1 != null) {
         float var2 = (float)var0.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         if (isCriticalHit(var0) && var2 > 0.0F) {
            var2 *= 1.5F;
         }

         ItemStack var3 = var0.getHeldItem();
         return Math.max(0.0F, var2 + EnchantmentHelper.getModifierForCreature(var3, var1.getCreatureAttribute()));
      } else {
         return 0.0F;
      }
   }

   public static float applyDamageReduction(float var0, EntityPlayer var1) {
      if (!(var0 <= 0.0F) && var1 != null) {
         float var2 = var0 * (25.0F - var1.getTotalArmorValue()) / 25.0F;
         PotionEffect var3 = var1.getActivePotionEffect(Potion.resistance);
         if (var3 != null) {
            int var4 = Math.min(20, (var3.getAmplifier() + 1) * 5);
            var2 *= (25.0F - var4) / 25.0F;
         }

         int var6 = getProtectionLevel(var1);
         if (var6 > 0) {
            int var5 = (var6 + 1) / 2;
            var2 *= (25.0F - var5) / 25.0F;
         }

         return Math.max(0.0F, var2);
      } else {
         return 0.0F;
      }
   }

   public static float applyObservedDamageRatio(float var0, float var1) {
      if (!(var0 <= 0.0F) && !(var1 <= 0.0F)) {
         float var2 = Math.min(1.0F, var1 * 1.2F + 0.05F);
         return var0 * var2;
      } else {
         return 0.0F;
      }
   }

   public static boolean isLethalDamage(float var0, float var1, float var2, float var3, float var4) {
      float var5 = Math.max(0.0F, var2 - Math.max(0.0F, var1));
      return var0 - var5 < var3 + var4;
   }

   private static int getProtectionLevel(EntityPlayer var0) {
      int var1 = 0;

      for (ItemStack var5 : var0.inventory.armorInventory) {
         if (var5 != null) {
            var1 += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var5);
         }
      }

      return Math.min(20, var1);
   }

   private static boolean isCriticalHit(EntityPlayer var0) {
      return var0.fallDistance > 0.0F
         && !var0.onGround
         && !var0.isOnLadder()
         && !var0.isInWater()
         && !var0.isPotionActive(Potion.blindness)
         && var0.ridingEntity == null;
   }
}
