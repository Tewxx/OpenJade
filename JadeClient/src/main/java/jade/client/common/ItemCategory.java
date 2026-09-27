// Jade recovery: original class: jade.deps.eLz.kpdtb2t3J
package jade.client.common;

import jade.client.module.combat.attributeswap.ItemScorer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum ItemCategory {
   FIST("@fist", "Fist", null, null),
   SWORD("@category:sword", "Sword", Items.diamond_sword, ItemSword.class),
   BOW("@category:bow", "Bow", Items.bow, ItemBow.class),
   FISHING_ROD("@category:fishing_rod", "Fishing Rod", Items.fishing_rod, ItemFishingRod.class),
   FISH("@category:fish", "Fish", Items.fish, null),
   TOOL("@category:tool", "Tool", Items.diamond_pickaxe, ItemTool.class),
   AXE("@category:axe", "Axe", Items.diamond_axe, ItemAxe.class),
   PICKAXE("@category:pickaxe", "Pickaxe", Items.diamond_pickaxe, ItemPickaxe.class),
   SHOVEL("@category:shovel", "Shovel", Items.diamond_shovel, ItemSpade.class),
   HOE("@category:hoe", "Hoe", Items.diamond_hoe, ItemHoe.class),
   SHEARS("@category:shears", "Shears", Items.shears, ItemShears.class),
   BLOCK("@category:block", "Block", Item.getItemFromBlock(Blocks.wool), null),
   LADDER("@category:ladder", "Ladder", Item.getItemFromBlock(Blocks.ladder), null),
   STICK("@category:stick", "Stick", Items.stick, null),
   GAPPLE("@category:gapple", "Golden Apple", Items.golden_apple, null),
   FIREBALL("@category:fireball", "Fireball", Items.fire_charge, null),
   SNOWBALL("@category:snowball", "Snowball", Items.snowball, null),
   EGG("@category:egg", "Egg", Items.egg, null),
   CLOCK("@category:clock", "Clock", Items.clock, null),
   ENDERPEARL("@category:enderpearl", "Ender Pearl", Items.ender_pearl, null),
   WATER("@category:water", "Water", Items.water_bucket, null),
   LAVA("@category:lava", "Lava", Items.lava_bucket, null);

   private static final Map<String, ItemCategory> BY_STORAGE = new HashMap<>();
   public final String storageId;
   public final String displayName;
   private final Item representative;
   private final Class<? extends Item> type;
   private final ItemStack preview;

   private ItemCategory(String var3, String var4, Item var5, Class<? extends Item> var6) {
      this.storageId = var3;
      this.displayName = var4;
      this.representative = var5;
      this.type = var6;
      this.preview = var3.equals("@fist") ? null : new ItemStack(var5);
   }

   public static ItemCategory from(String var0) {
      return BY_STORAGE.get(var0);
   }

   public boolean matches(ItemStack var1) {
      if (this == FIST) {
         return var1 == null;
      } else if (var1 == null) {
         return false;
      } else {
         Item var2 = var1.getItem();
         if (this == BLOCK) {
            return ItemScorer.getPlaceableBlockScore(var1) > 0.0;
         } else if (this == TOOL) {
            return var2 instanceof ItemTool || var2 instanceof ItemHoe || var2 instanceof ItemShears;
         } else if (this != FISH) {
            if (this == LADDER) {
               return var2 == Item.getItemFromBlock(Blocks.ladder);
            } else {
               return this.type == null ? var2 == this.representative : this.type.isInstance(var2);
            }
         } else {
            return var2 == Items.fish || var2 == Items.cooked_fish;
         }
      }
   }

   public boolean coveredBy(List<String> var1) {
      if (var1 != null && !var1.isEmpty()) {
         boolean var2 = this == AXE || this == PICKAXE || this == SHOVEL || this == HOE || this == SHEARS;
         return var1.contains(this.storageId) || var2 && var1.contains(TOOL.storageId);
      } else {
         return false;
      }
   }

   public ItemStack preview() {
      return this.preview == null ? null : this.preview.copy();
   }

   public ItemMatcher$1 entry() {
      return new ItemMatcher$1(null, 0, this.displayName, this.storageId, this.preview);
   }

   static {
      for (ItemCategory var3 : values()) {
         BY_STORAGE.put(var3.storageId, var3);
      }
   }
}
