// Jade recovery: original class: jade.deps.eLz.rBL9mV
package jade.client.common;

import java.util.Map.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public final class WeaponDamage {
   private WeaponDamage() {
   }

   public static double getMeleeDamage(ItemStack var0) {
      return var0 == null
         ? 0.0
         : ItemScoreUtils.getSwordDamageScore(
            getAttackDamageAttribute(var0), EnchantmentHelper.getModifierForCreature(var0, EnumCreatureAttribute.UNDEFINED), getEnchantmentLevel(var0, Enchantment.fireAspect), 0, 0.0
         );
   }

   public static double getTotalMeleeDamage(ItemStack var0, boolean var1) {
      return var0 == null
         ? 0.0
         : ItemScoreUtils.getSwordDamageScore(
            getAttackDamageAttribute(var0),
            EnchantmentHelper.getModifierForCreature(var0, EnumCreatureAttribute.UNDEFINED),
            getEnchantmentLevel(var0, Enchantment.fireAspect),
            getEnchantmentLevel(var0, Enchantment.knockback),
            DurabilityUtils.xuZl(var0, var1)
         );
   }

   public static double getBowDamage(ItemStack var0) {
      return var0 != null && var0.getItem() instanceof ItemBow ? ItemScoreUtils.getBowDamageScore(getEnchantmentLevel(var0, Enchantment.power), getEnchantmentLevel(var0, Enchantment.flame)) : 0.0;
   }

   public static double TALTByo(ItemStack var0, boolean var1) {
      return var0 == null ? 0.0 : ItemScoreUtils.getBowTotalScore(getBowDamage(var0), getEnchantmentLevel(var0, Enchantment.punch), DurabilityUtils.xuZl(var0, var1));
   }

   public static int getEnchantmentLevel(ItemStack var0, Enchantment var1) {
      return EnchantmentHelper.getEnchantmentLevel(var1.effectId, var0);
   }

   private static double getAttackDamageAttribute(ItemStack var0) {
      String var1 = SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName();

      for (Entry var3 : var0.getAttributeModifiers().entries()) {
         if (var1.equals(var3.getKey())) {
            return ((AttributeModifier)var3.getValue()).getAmount();
         }
      }

      return 0.0;
   }
}
