// Jade recovery: original class: jade.deps.eLz.HiVG3isEk
package jade.client.module.player.inventorymanager;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public final class StackUtils {
   private static final int ZEj = 9;

   private StackUtils() {
   }

   public static CursorRecoveryPlan planInsertion(ItemStack[] var0, ItemStack var1, int var2, boolean var3) {
      if (var1 == null) {
         return null;
      } else {
         ItemStack[] var4 = copyStackArray(var0);
         ItemStack var5 = var1.copy();
         ArrayList var6 = new ArrayList();
         int var7 = var4.length + 1;

         for (int var8 = var2; var5 != null && var7-- > 0; var8 = -1) {
            int var9 = findInsertSlot(var4, var5, var8);
            if (var9 < 0) {
               break;
            }

            var6.add(var9);
            var5 = mergeIntoSlot(var4, var5, var9);
         }

         if (var5 != null && !var3) {
            return null;
         } else {
            boolean var10 = var5 != null;
            return var6.isEmpty() && !var10 ? null : new CursorRecoveryPlan(var6, var10);
         }
      }
   }

   public static int findInsertSlot(ItemStack[] var0, ItemStack var1, int var2) {
      if (var2 >= 0 && var2 < var0.length && VnbY(var0[var2], var1)) {
         return var2;
      } else {
         int var3 = findMergeableSlot(var0, var1, 9, var0.length);
         if (var3 >= 0) {
            return var3;
         } else {
            var3 = findMergeableSlot(var0, var1, 0, Math.min(9, var0.length));
            if (var3 >= 0) {
               return var3;
            } else {
               var3 = GWKLvb(var0, 9, var0.length);
               return var3 >= 0 ? var3 : GWKLvb(var0, 0, Math.min(9, var0.length));
            }
         }
      }
   }

   private static int findMergeableSlot(ItemStack[] var0, ItemStack var1, int var2, int var3) {
      for (int var4 = var2; var4 < var3; var4++) {
         if (isSameItem(var0[var4], var1) && hasSpaceLeft(var0[var4])) {
            return var4;
         }
      }

      return -1;
   }

   private static int GWKLvb(ItemStack[] var0, int var1, int var2) {
      for (int var3 = var1; var3 < var2; var3++) {
         if (var0[var3] == null) {
            return var3;
         }
      }

      return -1;
   }

   private static boolean VnbY(ItemStack var0, ItemStack var1) {
      return var0 == null || isSameItem(var0, var1) && hasSpaceLeft(var0);
   }

   private static ItemStack mergeIntoSlot(ItemStack[] var0, ItemStack var1, int var2) {
      ItemStack var3 = var0[var2];
      if (var3 == null) {
         var0[var2] = var1.copy();
         return null;
      } else if (!isSameItem(var3, var1)) {
         return var1;
      } else {
         int var4 = Math.min(var3.getMaxStackSize() - var3.stackSize, var1.stackSize);
         var3.stackSize += var4;
         var1.stackSize -= var4;
         return var1.stackSize <= 0 ? null : var1;
      }
   }

   private static ItemStack[] copyStackArray(ItemStack[] var0) {
      ItemStack[] var1 = new ItemStack[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2] == null ? null : var0[var2].copy();
      }

      return var1;
   }

   private static boolean hasSpaceLeft(ItemStack var0) {
      return var0 != null && var0.isStackable() && var0.stackSize < var0.getMaxStackSize();
   }

   private static boolean isSameItem(ItemStack var0, ItemStack var1) {
      if (var0 == null || var1 == null || var0.getItem() != var1.getItem()) {
         return false;
      } else {
         return var0.getHasSubtypes() && var0.getMetadata() != var1.getMetadata() ? false : ItemStack.areItemStackTagsEqual(var0, var1);
      }
   }
}
