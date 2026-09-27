// Jade recovery: original class: jade.deps.eLz.wieTZjYh
package jade.client.setting;

import jade.client.common.ItemMatcher$0;
import jade.client.common.ItemMatcher$1;
import jade.client.common.ItemMatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public final class ItemSearchController {
   private final ItemListSetting itemListSetting;
   private final BiFunction<String, ItemListSetting, List<ItemMatcher$0>> biFunction;
   private List<ItemMatcher$0> DBJjjo = Collections.emptyList();
   private String juy;
   private String openedMatcherLabel;
   private String allItemsWildcard;
   private List<ItemMatcher$1> subEntries = Collections.emptyList();

   public ItemSearchController(ItemListSetting var1) {
      this(var1, ItemMatcher::getSuggestions);
   }

   public ItemSearchController(ItemListSetting var1, BiFunction<String, ItemListSetting, List<ItemMatcher$0>> var2) {
      this.itemListSetting = var1;
      this.biFunction = var2;
   }

   public String getDisplayTitle() {
      int var1 = this.itemListSetting.getItems().size();
      return this.itemListSetting.getName() + (var1 == 0 ? "" : " (" + var1 + ")");
   }

   public boolean kaagKg() {
      return this.juy != null;
   }

   public int getRowCount() {
      return this.kaagKg() ? this.subEntries.size() + 2 : this.DBJjjo.size();
   }

   public String getOpenedMatcherLabel() {
      return this.openedMatcherLabel == null ? this.juy : this.openedMatcherLabel;
   }

   public ItemMatcher$0 NUZQ(int var1) {
      return this.DBJjjo.get(var1);
   }

   public List<ItemMatcher$1> ywCw() {
      return this.subEntries;
   }

   public void openMatcher(int var1) {
      ItemMatcher$0 var2 = this.NUZQ(var1);
      this.juy = var2.itemId;
      this.openedMatcherLabel = var2.getDisplayName();
      this.allItemsWildcard = var2.getWildcardId();
      this.subEntries = new ArrayList<>();

      for (ItemMatcher$1 var4 : var2.variants) {
         if (!this.itemListSetting.containsItem(var4.hvDbs)) {
            this.subEntries.add(var4);
         }
      }
   }

   public String getEntryValueAt(int var1) {
      if (!this.kaagKg()) {
         return this.NUZQ(var1).variants.get(0).hvDbs;
      } else if (var1 == 1) {
         return this.allItemsWildcard == null ? this.juy + ":*" : this.allItemsWildcard;
      } else {
         return this.subEntries.get(var1 - 2).hvDbs;
      }
   }

   public ItemMatcher$1 getCycledSubEntry(long var1) {
      return this.subEntries.isEmpty() ? null : this.subEntries.get((int)(var1 / 1000L % this.subEntries.size()));
   }

   public void resetAndSearch(String var1) {
      this.closeMatcher();
      this.search(var1);
   }

   public void searchIfEmpty(String var1) {
      if (!var1.isEmpty() && this.DBJjjo.isEmpty()) {
         this.search(var1);
      }
   }

   public void search(String var1) {
      this.DBJjjo = this.biFunction.apply(var1, this.itemListSetting);
   }

   public void clearSearch() {
      this.DBJjjo = Collections.emptyList();
      this.closeMatcher();
   }

   public void closeMatcher() {
      this.juy = this.openedMatcherLabel = this.allItemsWildcard = null;
      this.subEntries = Collections.emptyList();
   }
}
