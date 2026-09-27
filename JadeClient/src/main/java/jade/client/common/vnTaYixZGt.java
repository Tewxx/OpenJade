// Jade recovery: original class: jade.deps.eLz.vnTaYixZGt
package jade.client.common;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class vnTaYixZGt {
   private final ItemEntryIndex tkV;

   public vnTaYixZGt(ItemEntryIndex var1) {
      this.tkV = var1;
   }

   public static String resolveDisplayName(String var0, List<ItemMatcher$1> var1) {
      ItemCategory var2 = ItemCategory.from(var0);
      if (var2 != null) {
         return var2.displayName;
      } else {
         return var1.isEmpty() ? var0 : ((ItemMatcher$1)var1.get(0)).Qwi;
      }
   }

   public ItemStack WjiF(String var1) {
      this.tkV.getAllEntries();
      if (var1 != null && !var1.isEmpty()) {
         ItemCategory var2 = ItemCategory.from(var1);
         if (var2 != null) {
            return var2.preview();
         } else {
            String var3 = ItemIds.GlVmhe(var1);
            if (VCvk14.hasWildcardSuffix(var1)) {
               List var8 = this.tkV.getEntriesForId(var3);
               return var8.isEmpty() ? null : ((ItemMatcher$1)var8.get(0)).copyItemStack();
            } else if (var3 == null) {
               return null;
            } else {
               for (ItemMatcher$1 var5 : this.tkV.getEntriesForId(var3)) {
                  if (var1.equals(var5.hvDbs)) {
                     return var5.copyItemStack();
                  }
               }

               Item var7;
               try {
                  var7 = (Item)Item.itemRegistry.getObject(new ResourceLocation(var3));
               } catch (Exception var6) {
                  return null;
               }

               return var7 == null ? null : new ItemStack(var7, 1, VCvk14.DPdat(var1));
            }
         }
      } else {
         return null;
      }
   }

   public String MCapuYq(String var1) {
      ItemCategory var2 = ItemCategory.from(var1);
      if (var2 != null) {
         return var2.displayName + (this.tkV.getEntriesForId(var1).size() > 1 ? " (All)" : "");
      } else if (VCvk14.hasWildcardSuffix(var1)) {
         String var5 = ItemIds.GlVmhe(var1);
         List var4 = this.tkV.getEntriesForId(var5);
         return var4.isEmpty() ? var1 : resolveDisplayName(var5, var4) + " (All)";
      } else {
         ItemStack var3 = this.WjiF(var1);
         return var3 == null ? var1 : var3.getDisplayName();
      }
   }
}
