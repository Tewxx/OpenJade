// Jade recovery: original class: jade.deps.eLz.KYANljw3
package jade.client.setting;

import jade.client.common.ItemMatcher$1;
import jade.client.common.ItemMatcher;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class ItemSlotListResolver {
   private ItemSlotListResolver() {
   }

   public static List<ItemSlotListResolver$1> resolveEntries(ItemSlotListSetting var0) {
      ArrayList var1 = new ArrayList();

      for (String var3 : var0.getItems()) {
         var1.add(new ItemSlotListResolver$1(var3, ItemMatcher.getDisplayName(var3), ItemMatcher.getPreviewStack(var3), resolveItemStacks(var3), var0.getSlotForItem(var3)));
      }

      return var1;
   }

   private static List<ItemStack> resolveItemStacks(String var0) {
      if (!ItemMatcher.isValidToken(var0)) {
         return null;
      } else {
         List var1 = ItemMatcher.getEntries(var0);
         if (var1 != null && !var1.isEmpty()) {
            ArrayList var2 = new ArrayList();

            for (ItemMatcher$1 var4 : (java.lang.Iterable<ItemMatcher$1>) (java.lang.Iterable<?>) (var1)) {
               var2.add(var4.copyItemStack());
            }

            return var2;
         } else {
            return null;
         }
      }
   }
}
