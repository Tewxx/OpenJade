// Jade recovery: original class: jade.deps.eLz.Fah9eVedv
package jade.client.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;

public final class ItemEntryIndex {
   private List<ItemMatcher$1> allEntries;
   private Map<String, List<ItemMatcher$1>> SpjiOd;

   public List<ItemMatcher$1> getAllEntries() {
      this.buildIndex();
      return this.allEntries;
   }

   public List<ItemMatcher$1> getEntriesForId(String var1) {
      this.buildIndex();
      List var2 = this.SpjiOd == null ? null : this.SpjiOd.get(var1);
      return var2 == null ? Collections.emptyList() : var2;
   }

   private void buildIndex() {
      if (this.allEntries == null) {
         ArrayList var1 = new ArrayList();
         HashMap var2 = new HashMap();

         for (Item var4 : Item.itemRegistry) {
            String var5 = ItemIds.getRegistryName(var4);
            if (var5 != null) {
               List var6 = CreativeItemIndex.getVariants(var4);
               if (!var6.isEmpty()) {
                  var1.addAll(var6);
                  var2.put(var5, var6);
               }
            }
         }

         this.allEntries = new ArrayList<>(var1);

         for (ItemCategory var14 : ItemCategory.values()) {
            ItemMatcher$1 var7 = var14.entry();
            this.allEntries.add(var7);
            ArrayList var8 = new ArrayList();

            for (ItemMatcher$1 var10 : (java.lang.Iterable<ItemMatcher$1>) (java.lang.Iterable<?>) (var1)) {
               if (var14.matches(var10.copyItemStack())) {
                  var8.add(var10);
               }
            }

            var8.sort(Comparator.comparing(ItemEntryIndex::extractCategorySortKey, String.CASE_INSENSITIVE_ORDER));
            if (var8.isEmpty()) {
               var8.add(var7);
            }

            var2.put(var14.storageId, var8);
         }

         this.allEntries.sort(Comparator.comparing(ItemEntryIndex::extractEntrySortKey, String.CASE_INSENSITIVE_ORDER).thenComparing(ItemEntryIndex::extractEntryName));
         this.SpjiOd = var2;
      }
   }

   private static String extractEntryName(ItemMatcher$1 var0) {
      return var0.hvDbs;
   }

   private static String extractEntrySortKey(ItemMatcher$1 var0) {
      return var0.Qwi;
   }

   private static String extractCategorySortKey(ItemMatcher$1 var0) {
      return var0.Qwi;
   }
}
