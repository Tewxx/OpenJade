// Jade recovery: original class: jade.deps.eLz.WendFc6
package jade.client.common;

import jade.client.setting.ItemListSetting;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemMatcher {
   private static final ItemEntryIndex BfdVpj = new ItemEntryIndex();
   private static final vnTaYixZGt itemResolver = new vnTaYixZGt(BfdVpj);

   private ItemMatcher() {
   }

   public static List<ItemMatcher$0> getSuggestions(String var0, ItemListSetting var1) {
      return getSuggestionsIncludingSelected(var0, var1, false);
   }

   public static List<ItemMatcher$0> getSuggestionsIncludingSelected(String var0, ItemListSetting var1, boolean var2) {
      return ItemSearchHelper.searchItems(var0, var1, var2, BfdVpj);
   }

   public static List<ItemMatcher$1> MksK(String var0) {
      return BfdVpj.getEntriesForId(var0);
   }

   public static String getStackItemId(ItemStack var0) {
      return ItemIds.getItemId(var0);
   }

   public static String getItemId(Item var0) {
      return ItemIds.getRegistryName(var0);
   }

   public static String toCanonicalItemId(String var0) {
      return ItemIds.GlVmhe(var0);
   }

   public static boolean hasMultipleVariants(Item var0) {
      String var1 = ItemIds.getRegistryName(var0);
      return var1 == null ? false : BfdVpj.getEntriesForId(var1).size() > 1;
   }

   public static boolean GFoM5(String var0) {
      return VCvk14.hasWildcardSuffix(var0);
   }

   public static boolean isValidToken(String var0) {
      return ItemCategory.from(var0) != null ? true : VCvk14.hasWildcardSuffix(var0);
   }

   public static String UIxUa(String var0) {
      return ItemIds.toWildcardId(var0);
   }

   public static List<ItemMatcher$1> getEntries(String var0) {
      if (var0 != null && var0.length() != 0) {
         ItemCategory var1 = ItemCategory.from(var0);
         if (var1 != null) {
            return BfdVpj.getEntriesForId(var0);
         } else {
            return !VCvk14.hasWildcardSuffix(var0) ? Collections.emptyList() : BfdVpj.getEntriesForId(ItemIds.GlVmhe(var0));
         }
      } else {
         return Collections.emptyList();
      }
   }

   public static boolean AkEm(List<String> var0, ItemStack var1) {
      if (var0 == null) {
         return false;
      } else {
         for (String var3 : var0) {
            if (ItemIds.matchesId(var3, var1)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean matches(String var0, ItemStack var1) {
      return ItemIds.matchesId(var0, var1);
   }

   public static double getMatchScore(String var0, ItemStack var1) {
      return computeMatchScore(var0, var1, false, true);
   }

   public static double OpAegv(String var0, ItemStack var1, boolean var2) {
      return computeMatchScore(var0, var1, var2, true);
   }

   public static double computeMatchScore(String var0, ItemStack var1, boolean var2, boolean var3) {
      if (!ItemIds.matchesId(var0, var1)) {
         return Double.NEGATIVE_INFINITY;
      } else {
         ItemCategory var4 = ItemCategory.from(var0);
         return var4 == null ? 0.0 : ItemCategoryScorerRegistry.scoreItem(var4, var1, var2, var3);
      }
   }

   public static ItemStack getPreviewStack(String var0) {
      return itemResolver.WjiF(var0);
   }

   public static String getDisplayName(String var0) {
      return itemResolver.MCapuYq(var0);
   }
}
