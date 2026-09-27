// Jade recovery: original class: jade.deps.eLz.JjHUAcXw
package jade.client.module.player.inventorymanager;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public final class ItemStackMatcher {
   private static final int HOTBAR_SIZE = 9;
   private static final int MAIN_INVENTORY_SIZE = 36;

   private ItemStackMatcher() {
   }

   public static boolean qjpA(ItemStack var0, ItemStack var1) {
      return var0 != null && var1 != null
         ? var0.stackSize == var1.stackSize && var0.isItemEqual(var1) && ItemStack.areItemStackTagsEqual(var0, var1)
         : var0 == var1;
   }

   public static boolean isSameItemIgnoringCount(ItemStack var0, ItemStack var1) {
      if (var0 != null && var1 != null) {
         if (var0.getItem() != var1.getItem()) {
            return false;
         } else {
            boolean var2 = !var0.getHasSubtypes() || var0.getMetadata() == var1.getMetadata();
            return var2 && ItemStack.areItemStackTagsEqual(var0, var1);
         }
      } else {
         return false;
      }
   }

   public static boolean isSameBlockItem(ItemStack var0, ItemStack var1) {
      return var0 != null
         && var1 != null
         && var0.getItem() instanceof ItemBlock
         && var1.getItem() instanceof ItemBlock
         && var0.isItemEqual(var1)
         && ItemStack.areItemStackTagsEqual(var0, var1);
   }

   public static boolean FBbY(ItemStack var0) {
      return var0 != null && var0.isStackable() && getRemainingSpace(var0) > 0;
   }

   public static int getRemainingSpace(ItemStack var0) {
      return var0 == null ? 0 : Math.max(0, var0.getMaxStackSize() - var0.stackSize);
   }

   public static int toContainerSlot(int var0) {
      if (var0 >= 0 && var0 < 36) {
         return var0 < 9 ? var0 + 36 : var0;
      } else {
         return -1;
      }
   }

   public static boolean canMergeIntoHotbar(ItemStack[] var0, int var1, ItemStack var2, ItemStack var3) {
      if (isSameItemIgnoringCount(var2, var3) && FBbY(var3)) {
         int var4 = Math.min(9, var0.length);

         for (int var5 = 0; var5 < var4; var5++) {
            if (var5 != var1) {
               ItemStack var6 = var0[var5];
               if (var5 < var1 && var6 != null && isSameItemIgnoringCount(var6, var2) && FBbY(var6)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
