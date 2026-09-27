// Jade recovery: original class: jade.deps.eLz.pbjpwO
package jade.client.common;

import jade.client.setting.ItemListSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ItemSearchHelper {
   private ItemSearchHelper() {
   }

   public static List<ItemMatcher$0> searchItems(String var0, ItemListSetting var1, boolean var2, ItemEntryIndex var3) {
      FuzzyNameMatcher var4 = new FuzzyNameMatcher(var0);
      if (!var4.iqpvb) {
         return Collections.emptyList();
      } else {
         List var5 = SearchRanker.rankMatches(var4, var3.getAllEntries(), ItemSearchHelper::Czma, ItemSearchHelper::getItemId, ItemSearchHelper::getDisplayName, (recoveredArg0) -> ItemSearchHelper.shouldIncludeItem(var2, var1, (jade.client.common.ItemMatcher$1) recoveredArg0), ItemSearchHelper::acceptAllItems);
         List var6 = SearchRanker.groupMatches(var5, ItemSearchHelper::tvNty, var3::getEntriesForId);
         ArrayList var7 = new ArrayList(var6.size());

         for (SearchGroup var9 : (java.lang.Iterable<SearchGroup>) (java.lang.Iterable<?>) (var6)) {
            String var10 = vnTaYixZGt.resolveDisplayName(var9.ICo, var9.entries);
            var7.add(new ItemMatcher$0(var9.ICo, var9.entries, var9.matchScore, var10, ItemIds.toWildcardId(var9.ICo)));
         }

         Collections.sort(var7, Comparator.comparingInt(ItemSearchHelper::getMatchScore).reversed().thenComparing(ItemMatcher$0::getDisplayName, String.CASE_INSENSITIVE_ORDER));
         return var7;
      }
   }

   private static int getMatchScore(ItemMatcher$0 var0) {
      return var0.Hrx;
   }

   private static String tvNty(ItemMatcher$1 var0) {
      return ItemIds.GlVmhe(var0.hvDbs);
   }

   private static boolean acceptAllItems(ItemMatcher$1 var0) {
      return true;
   }

   private static boolean shouldIncludeItem(boolean var0, ItemListSetting var1, ItemMatcher$1 var2) {
      return var0 || !ItemIds.spR2(var2, var1);
   }

   private static String getDisplayName(ItemMatcher$1 var0) {
      return ItemIds.getDisplayName(var0.hvDbs);
   }

   private static String getItemId(ItemMatcher$1 var0) {
      return var0.hvDbs;
   }

   private static String Czma(ItemMatcher$1 var0) {
      return var0.Qwi;
   }
}
