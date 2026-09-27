// Jade recovery: original class: jade.deps.eLz.oeo0fIIS
package jade.client.module.render.blockesp;

import jade.client.common.SearchGroup;
import jade.client.common.VCvk14;
import jade.client.common.SearchRanker;
import jade.client.common.FuzzyNameMatcher;
import jade.client.setting.BlockListSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class BlockEspSearch {
   private BlockEspSearch() {
   }

   public static List<BlockEspParser$1> searchGrouped(String var0, BlockListSetting var1, boolean var2, BlockEspCatalog var3) {
      FuzzyNameMatcher var4 = new FuzzyNameMatcher(var0);
      if (!var4.iqpvb) {
         return Collections.emptyList();
      } else {
         List var5 = SearchRanker.rankMatches(var4, var3.getAllEntries(), BlockEspSearch::getEntryDisplayName, BlockEspSearch::getEntryKey, BlockEspSearch::toItemGroupKey, (recoveredArg0) -> BlockEspSearch.isEntryIncluded(var2, var1, (jade.client.module.render.blockesp.BlockEspParser$0) recoveredArg0), BlockEspSearch::acceptAllEntries);
         List var6 = SearchRanker.groupMatches(var5, BlockEspSearch::toCatalogKey, var3::getEntriesForBlock);
         ArrayList var7 = new ArrayList(var6.size());

         for (SearchGroup var9 : (java.lang.Iterable<SearchGroup>) (java.lang.Iterable<?>) (var6)) {
            var7.add(new BlockEspParser$1(var9.ICo, var9.entries, var9.matchScore));
         }

         Collections.sort(var7, Comparator.comparingInt(BlockEspSearch::getMatchCount).reversed().thenComparing(BlockEspSearch::getFirstDisplayName, String.CASE_INSENSITIVE_ORDER));
         return var7;
      }
   }

   public static List<BlockEspParser$0> searchFlat(String var0, BlockListSetting var1, BlockEspCatalog var2) {
      FuzzyNameMatcher var3 = new FuzzyNameMatcher(var0);
      if (!var3.iqpvb) {
         return Collections.emptyList();
      } else {
         List var4 = SearchRanker.rankMatches(var3, var2.getAllEntries(), BlockEspSearch::getDisplayName, BlockEspSearch::getRegistryKey, BlockEspSearch::toGroupKey, BlockEspSearch::acceptAll, (recoveredArg0) -> BlockEspSearch.isEntrySelected(var1, (jade.client.module.render.blockesp.BlockEspParser$0) recoveredArg0));
         return SearchRanker.takeTopItems(var4);
      }
   }

   private static boolean isEntryAllowed(String var0, BlockListSetting var1) {
      if (var1.containsEntry(var0)) {
         return false;
      } else {
         String var2 = VCvk14.stripWildcardSuffix(var0);
         return var2 == null || !var1.containsEntry(var2 + ":*");
      }
   }

   private static boolean isEntrySelected(BlockListSetting var0, BlockEspParser$0 var1) {
      return !var0.containsEntry(var1.registryKey);
   }

   private static boolean acceptAll(BlockEspParser$0 var0) {
      return true;
   }

   private static String toGroupKey(BlockEspParser$0 var0) {
      return VCvk14.getBlockPathName(var0.registryKey);
   }

   private static String getRegistryKey(BlockEspParser$0 var0) {
      return var0.registryKey;
   }

   private static String getDisplayName(BlockEspParser$0 var0) {
      return var0.displayName;
   }

   private static String getFirstDisplayName(BlockEspParser$1 var0) {
      return var0.nh7.get(0).displayName;
   }

   private static int getMatchCount(BlockEspParser$1 var0) {
      return var0.matchCount;
   }

   private static String toCatalogKey(BlockEspParser$0 var0) {
      return VCvk14.stripWildcardSuffix(var0.registryKey);
   }

   private static boolean acceptAllEntries(BlockEspParser$0 var0) {
      return true;
   }

   private static boolean isEntryIncluded(boolean var0, BlockListSetting var1, BlockEspParser$0 var2) {
      return var0 || isEntryAllowed(var2.registryKey, var1);
   }

   private static String toItemGroupKey(BlockEspParser$0 var0) {
      return VCvk14.getBlockPathName(var0.registryKey);
   }

   private static String getEntryKey(BlockEspParser$0 var0) {
      return var0.registryKey;
   }

   private static String getEntryDisplayName(BlockEspParser$0 var0) {
      return var0.displayName;
   }
}
