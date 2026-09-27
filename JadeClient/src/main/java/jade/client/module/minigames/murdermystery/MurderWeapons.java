// Jade recovery: original class: jade.deps.eLz.mUbVCZu4
package jade.client.module.minigames.murdermystery;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class MurderWeapons {
   private MurderWeapons() {
   }

   public static Item[] getDefaultWeapons() {
      return new Item[]{
         Items.iron_sword,
         Items.stone_sword,
         Items.iron_shovel,
         Items.stick,
         Items.wooden_axe,
         Items.wooden_sword,
         Items.stone_shovel,
         Items.blaze_rod,
         Items.diamond_shovel,
         Items.quartz,
         Items.pumpkin_pie,
         Items.golden_pickaxe,
         Items.apple,
         Items.name_tag,
         Items.carrot_on_a_stick,
         Items.bone,
         Items.carrot,
         Items.golden_carrot,
         Items.cookie,
         Items.diamond_axe,
         Items.prismarine_shard,
         Items.cooked_beef,
         Items.netherbrick,
         Items.cooked_chicken,
         Items.golden_sword,
         Items.diamond_sword,
         Items.diamond_hoe,
         Items.shears,
         Items.fish,
         Items.dye,
         Items.boat,
         Items.speckled_melon,
         Items.book,
         Item.getItemFromBlock(Blocks.double_plant),
         Item.getItemFromBlock(Blocks.sponge),
         Item.getItemFromBlock(Blocks.deadbush)
      };
   }
}
