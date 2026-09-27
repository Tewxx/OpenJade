// Jade recovery: original class: jade.deps.eLz.dJB9rUbIMX
package jade.client.common;

import jade.client.setting.ItemListSetting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class ItemIds {
   private ItemIds() {
   }

   public static String getRegistryName(Item var0) {
      ResourceLocation var1 = var0 == null ? null : (ResourceLocation)Item.itemRegistry.getNameForObject(var0);
      return var1 == null ? null : var1.toString();
   }

   public static boolean usesMetadata(Item var0) {
      return var0 != null && var0.getHasSubtypes() && !var0.isDamageable();
   }

   public static String getItemId(ItemStack var0) {
      Item var1 = var0 == null ? null : var0.getItem();
      String var2 = getRegistryName(var1);
      if (var2 == null) {
         return null;
      } else {
         return usesMetadata(var1) && var0.getMetadata() != 0 ? var2 + ':' + var0.getMetadata() : var2;
      }
   }

   public static String GlVmhe(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         return ItemCategory.from(var0) == null ? VCvk14.stripWildcardSuffix(var0) : var0;
      } else {
         return null;
      }
   }

   public static String toWildcardId(String var0) {
      return ItemCategory.from(var0) == null ? var0 + ":*" : var0;
   }

   public static String getDisplayName(String var0) {
      ItemCategory var1 = ItemCategory.from(var0);
      return var1 == null ? VCvk14.getBlockPathName(var0) : var1.displayName;
   }

   public static boolean matchesId(String var0, ItemStack var1) {
      if (var0 != null && !var0.isEmpty()) {
         ItemCategory var2 = ItemCategory.from(var0);
         if (var2 != null) {
            return var2.matches(var1);
         } else {
            String var3 = getItemId(var1);
            if (var3 == null) {
               return false;
            } else if (var0.equals(var3)) {
               return true;
            } else {
               String var4 = GlVmhe(var3);
               return var4 != null && var0.equals(var4 + ":*");
            }
         }
      } else {
         return false;
      }
   }

   public static boolean spR2(ItemMatcher$1 var0, ItemListSetting var1) {
      if (var1 == null) {
         return false;
      } else {
         ItemCategory var2 = ItemCategory.from(var0.hvDbs);
         return var2 == null ? var1.EMuhC6(var0.copyItemStack()) : var2.coveredBy(var1.getItems());
      }
   }
}
