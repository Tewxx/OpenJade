// Jade recovery: original class: jade.deps.eLz.cUUD3h
package jade.client.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class CreativeItemIndex {
   private CreativeItemIndex() {
   }

   public static List<ItemMatcher$1> getVariants(Item var0) {
      ArrayList var1 = new ArrayList();
      collectSubItems(var0, CreativeTabs.tabAllSearch, var1);
      if (var1.isEmpty()) {
         CreativeTabs var2 = var0.getCreativeTab();
         if (var2 != null) {
            collectSubItems(var0, var2, var1);
         }
      }

      if (var1.isEmpty()) {
         var1.add(new ItemStack(var0, 1, 0));
      }

      LinkedHashMap var8 = new LinkedHashMap();

      for (ItemStack var4 : (java.lang.Iterable<ItemStack>) (java.lang.Iterable<?>) (var1)) {
         if (var4 != null && var4.getItem() != null) {
            String var5 = ItemIds.getItemId(var4);
            if (var5 != null && !var8.containsKey(var5)) {
               String var6 = var4.getDisplayName();
               if (var6 != null && !var6.isEmpty()) {
                  int var7 = ItemIds.usesMetadata(var0) ? var4.getMetadata() : 0;
                  var8.put(var5, new ItemMatcher$1(var0, var7, var6, var5, var4));
               }
            }
         }
      }

      ArrayList var9 = new ArrayList(var8.values());
      var9.sort(Comparator.comparingInt(CreativeItemIndex::getSortIndex));
      return var9;
   }

   private static void collectSubItems(Item var0, CreativeTabs var1, List<ItemStack> var2) {
      try {
         var0.getSubItems(var0, var1, var2);
      } catch (Exception var4) {
      }
   }

   private static int getSortIndex(ItemMatcher$1 var0) {
      return var0.nfxLs;
   }
}
