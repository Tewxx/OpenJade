// Jade recovery: original class: jade.deps.eLz.oWiIje
package jade.client.gui;

import jade.client.common.ItemNames;
import jade.client.common.QuickBuyLayout;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class QuickBuyItems {
   private QuickBuyItems() {
   }

   public static ItemStack createItemStack(String var0) {
      String var1 = QuickBuyLayout.normalizeItemName(var0);
      ItemStack var2 = createBaseItemStack(var1);
      var2.setStackDisplayName(ItemNames.formatSkyblockName(var1));
      return var2;
   }

   private static ItemStack createBaseItemStack(String var0) {
      if ("wool".equals(var0)) {
         return opHwy(Blocks.wool, 16);
      } else if ("hardened_clay".equals(var0)) {
         return opHwy(Blocks.hardened_clay, 16);
      } else if ("blast-proof_glass".equals(var0)) {
         return opHwy(Blocks.glass, 4);
      } else if ("end_stone".equals(var0)) {
         return opHwy(Blocks.end_stone, 12);
      } else if ("ladder".equals(var0)) {
         return opHwy(Blocks.ladder, 8);
      } else if ("wood".equals(var0)) {
         return opHwy(Blocks.planks, 16);
      } else if ("obsidian".equals(var0)) {
         return opHwy(Blocks.obsidian, 4);
      } else if ("packed_ice".equals(var0)) {
         return opHwy(Blocks.packed_ice, 8);
      } else if ("sponge".equals(var0)) {
         return opHwy(Blocks.sponge, 4);
      } else if ("compact_pop-up_tower".equals(var0)) {
         return opHwy(Blocks.chest, 1);
      } else if ("tnt".equals(var0)) {
         return opHwy(Blocks.tnt, 1);
      } else if ("stone_sword".equals(var0)) {
         return new ItemStack(Items.stone_sword);
      } else if ("iron_sword".equals(var0)) {
         return new ItemStack(Items.iron_sword);
      } else if ("diamond_sword".equals(var0)) {
         return new ItemStack(Items.diamond_sword);
      } else if ("chainmail_boots".equals(var0)) {
         return new ItemStack(Items.chainmail_boots);
      } else if ("iron_boots".equals(var0)) {
         return new ItemStack(Items.iron_boots);
      } else if ("diamond_boots".equals(var0)) {
         return new ItemStack(Items.diamond_boots);
      } else if ("shears".equals(var0)) {
         return new ItemStack(Items.shears);
      } else if ("arrow".equals(var0)) {
         return zMifU(Items.arrow, 6, 0);
      } else if ("bow".equals(var0)) {
         return new ItemStack(Items.bow);
      } else if ("golden_apple".equals(var0)) {
         return new ItemStack(Items.golden_apple);
      } else if ("bedbug".equals(var0)) {
         return new ItemStack(Items.snowball);
      } else if ("dream_defender".equals(var0)) {
         return zMifU(Items.spawn_egg, 1, 99);
      } else if ("fireball".equals(var0)) {
         return new ItemStack(Items.fire_charge);
      } else if ("ender_pearl".equals(var0)) {
         return new ItemStack(Items.ender_pearl);
      } else if ("water_bucket".equals(var0)) {
         return new ItemStack(Items.water_bucket);
      } else if ("bridge_egg".equals(var0)) {
         return new ItemStack(Items.egg);
      } else if ("magic_milk".equals(var0)) {
         return new ItemStack(Items.milk_bucket);
      } else if ("wooden_pickaxe".equals(var0)) {
         return withEnchantment(new ItemStack(Items.wooden_pickaxe), Enchantment.efficiency, 1);
      } else if ("wooden_axe".equals(var0)) {
         return withEnchantment(new ItemStack(Items.wooden_axe), Enchantment.efficiency, 1);
      } else if ("stick_(knockback_i)".equals(var0)) {
         return withEnchantment(new ItemStack(Items.stick), Enchantment.knockback, 1);
      } else if ("bow_(power_i__punch_i)".equals(var0)) {
         ItemStack var1 = withEnchantment(new ItemStack(Items.bow), Enchantment.power, 1);
         var1.addEnchantment(Enchantment.punch, 1);
         return var1;
      } else if ("speed_ii_potion_(45_seconds)".equals(var0)) {
         return createCustomPotion(8195, 1, 1, 900);
      } else if ("jump_v_potion_(45_seconds)".equals(var0)) {
         return createCustomPotion(8203, 8, 4, 900);
      } else {
         return "invisibility_potion_(30_seconds)".equals(var0) ? createCustomPotion(8206, 14, 0, 600) : new ItemStack(Blocks.barrier);
      }
   }

   private static ItemStack opHwy(Block var0, int var1) {
      return new ItemStack(var0, var1);
   }

   private static ItemStack zMifU(Item var0, int var1, int var2) {
      return new ItemStack(var0, var1, var2);
   }

   private static ItemStack withEnchantment(ItemStack var0, Enchantment var1, int var2) {
      var0.addEnchantment(var1, var2);
      return var0;
   }

   private static ItemStack createCustomPotion(int var0, int var1, int var2, int var3) {
      ItemStack var4 = zMifU(Items.potionitem, 1, var0);
      NBTTagCompound var5 = new NBTTagCompound();
      var5.setByte("Ambient", (byte)1);
      var5.setInteger("Duration", var3);
      var5.setByte("Id", (byte)var1);
      var5.setByte("Amplifier", (byte)var2);
      NBTTagList var6 = new NBTTagList();
      var6.appendTag(var5);
      var4.setTagInfo("CustomPotionEffects", var6);
      return var4;
   }
}
