// Jade recovery: original class: jade.deps.eLz.tQkEcgIYsY
package jade.client.common;

import jade.client.module.combat.attributeswap.ItemScorer;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.item.ItemStack;

public final class ItemCategoryScorerRegistry {
   private static final Map<ItemCategory, ItemCategoryScorer> izoOlf = new EnumMap<>(ItemCategory.class);

   private ItemCategoryScorerRegistry() {
   }

   public static double scoreItem(ItemCategory var0, ItemStack var1, boolean var2, boolean var3) {
      ItemCategoryScorer var4 = izoOlf.get(var0);
      return var4 == null ? 0.0 : var4.score(var1, var2, var3);
   }

   private static double scoreGenericItem(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.getStackSize(var0);
   }

   private static double GAbZty(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.ZGxM(var0);
   }

   private static double scoreFishingRod(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.scoreFishingRod(var0, var2);
   }

   private static double scoreStick(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.getStickScore(var0);
   }

   private static double scoreBlock(ItemStack var0, boolean var1, boolean var2) {
      return var1 ? ItemScorer.getStackSize(var0) : ItemScorer.getPlaceableBlockScore(var0);
   }

   private static double scoreShears(ItemStack var0, boolean var1, boolean var2) {
      return var1 ? ItemScorer.scoreShears(var0, var2) : ItemScorer.scoreWebBreaking(var0, var2);
   }

   private static double Xuagvg(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.scoreHoe(var0, var2);
   }

   private static double DawH(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.scoreDirtBreaking(var0, var2);
   }

   private static double scorePickaxe(ItemStack var0, boolean var1, boolean var2) {
      return var1 ? ItemScorer.scorePickaxe(var0, var2) : ItemScorer.scoreStoneBreaking(var0, var2);
   }

   private static double scoreAxe(ItemStack var0, boolean var1, boolean var2) {
      return var1 ? ItemScorer.scoreAxe(var0, var2) : ItemScorer.scoreWoodBreaking(var0, var2);
   }

   private static double scoreTool(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.scoreBestBreakingTool(var0, var2);
   }

   private static double scoreBow(ItemStack var0, boolean var1, boolean var2) {
      return var1 ? ItemScorer.scoreBowEnchantments(var0, var2) : ItemScorer.scoreBowDamage(var0, var2);
   }

   private static double scoreSword(ItemStack var0, boolean var1, boolean var2) {
      return ItemScorer.KbTm2(var0, var2);
   }

   static {
      izoOlf.put(ItemCategory.SWORD, ItemCategoryScorerRegistry::scoreSword);
      izoOlf.put(ItemCategory.BOW, ItemCategoryScorerRegistry::scoreBow);
      izoOlf.put(ItemCategory.TOOL, ItemCategoryScorerRegistry::scoreTool);
      izoOlf.put(ItemCategory.AXE, ItemCategoryScorerRegistry::scoreAxe);
      izoOlf.put(ItemCategory.PICKAXE, ItemCategoryScorerRegistry::scorePickaxe);
      izoOlf.put(ItemCategory.SHOVEL, ItemCategoryScorerRegistry::DawH);
      izoOlf.put(ItemCategory.HOE, ItemCategoryScorerRegistry::Xuagvg);
      izoOlf.put(ItemCategory.SHEARS, ItemCategoryScorerRegistry::scoreShears);
      izoOlf.put(ItemCategory.BLOCK, ItemCategoryScorerRegistry::scoreBlock);
      izoOlf.put(ItemCategory.STICK, ItemCategoryScorerRegistry::scoreStick);
      izoOlf.put(ItemCategory.FISHING_ROD, ItemCategoryScorerRegistry::scoreFishingRod);
      izoOlf.put(ItemCategory.FISH, ItemCategoryScorerRegistry::GAbZty);
      ItemCategoryScorer var0 = ItemCategoryScorerRegistry::scoreGenericItem;

      for (ItemCategory var4 : new ItemCategory[]{
         ItemCategory.LADDER, ItemCategory.GAPPLE, ItemCategory.FIREBALL, ItemCategory.SNOWBALL, ItemCategory.EGG, ItemCategory.ENDERPEARL, ItemCategory.WATER, ItemCategory.LAVA
      }) {
         izoOlf.put(var4, var0);
      }
   }
}
