// Jade recovery: original class: jade.deps.eLz.Qv77PfhJ
package jade.client.module.player.inventorymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class InventorySorter {
   private static final int qgBsat = 9;

   private InventorySorter() {
   }

   public static StackMergePlan planStackMerge(ItemStack[] var0, boolean[] var1, int var2, ItemStack var3) {
      int var4 = YaJx(var3);
      if (var4 == 0) {
         return null;
      } else {
         List var5 = collectMergeSources(var0, var1, var2, var3);
         if (var5.isEmpty()) {
            return null;
         } else {
            int[] var6 = new int[var4 + 1];
            Arrays.fill(var6, Integer.MAX_VALUE);
            var6[0] = 0;
            ArrayList var7 = new ArrayList(var4 + 1);

            for (int var8 = 0; var8 <= var4; var8++) {
               var7.add(null);
            }

            var7.set(0, Collections.emptyList());

            for (int[] var9 : (java.lang.Iterable<int[]>) (java.lang.Iterable<?>) (var5)) {
               int[] var10 = Arrays.copyOf(var6, var6.length);
               ArrayList var11 = new ArrayList(var7);

               for (int var12 = 0; var12 < var4; var12++) {
                  List var13 = (List)var7.get(var12);
                  if (var13 != null) {
                     int var14 = Math.min(var4 - var12, var9[1]);
                     if (var14 != 0) {
                        boolean var15 = var9[2] == 0;
                        boolean var16 = !var15 && var9[1] > var14;
                        int var17 = var15 ? 1 : (var16 ? 3 : 2);
                        ArrayList var18 = new ArrayList(var13.size() + 1);
                        var18.addAll(var13);
                        var18.add(new TransferSourceSlot(var9[0], var14, var15, var16));
                        int var19 = var12 + var14;
                        int var20 = var6[var12] + var17;
                        if (isBetterMergePlan(var20, var18, var10[var19], (List<TransferSourceSlot>)var11.get(var19))) {
                           var10[var19] = var20;
                           var11.set(var19, var18);
                        }
                     }
                  }
               }

               var6 = var10;
               var7 = var11;
            }

            for (int var22 = var4; var22 > 0; var22--) {
               List var23 = (List)var7.get(var22);
               if (var23 != null) {
                  int var24 = var23.isEmpty() ? Integer.MAX_VALUE : ((TransferSourceSlot)var23.get(0)).getSourceSlot();
                  return new StackMergePlan(var3.stackSize + var22, var6[var22], var24, var23);
               }
            }

            return null;
         }
      }
   }

   private static List<int[]> collectMergeSources(ItemStack[] var0, boolean[] var1, int var2, ItemStack var3) {
      ArrayList var4 = new ArrayList();

      for (int var5 = 0; var5 < var0.length; var5++) {
         if (var5 != var2 && isSameItemType(var0[var5], var3)) {
            if (var5 < 9) {
               if (!var1[var5]) {
                  var4.add(new int[]{var5, var0[var5].stackSize, 1});
               }
            } else if (hasNoEarlierHotbarReceiver(var0, var2, var0[var5], var3)) {
               var4.add(new int[]{var5, var0[var5].stackSize, 0});
            }
         }
      }

      Collections.sort(var4, new Comparator<int[]>() {
         public int compare(int[] var1, int[] var2x) {
            if (var1[2] != var2x[2]) {
               return Integer.compare(var1[2], var2x[2]);
            } else {
               return var1[1] != var2x[1] ? Integer.compare(var2x[1], var1[1]) : Integer.compare(var1[0], var2x[0]);
            }
         }
      });
      return var4;
   }

   private static boolean isBetterMergePlan(int var0, List<TransferSourceSlot> var1, int var2, List<TransferSourceSlot> var3) {
      if (var3 == null) {
         return true;
      } else if (var0 != var2) {
         return var0 < var2;
      } else if (var1.size() != var3.size()) {
         return var1.size() < var3.size();
      } else {
         for (int var4 = 0; var4 < var1.size(); var4++) {
            TransferSourceSlot var5 = (TransferSourceSlot)var1.get(var4);
            TransferSourceSlot var6 = (TransferSourceSlot)var3.get(var4);
            int var7 = var5.isFromMainInventory() ? 0 : 1;
            int var8 = var6.isFromMainInventory() ? 0 : 1;
            if (var7 != var8) {
               return var7 < var8;
            }

            if (var5.QNq5() != var6.QNq5()) {
               return var5.QNq5() > var6.QNq5();
            }

            if (var5.getSourceSlot() != var6.getSourceSlot()) {
               return var5.getSourceSlot() < var6.getSourceSlot();
            }
         }

         return false;
      }
   }

   private static boolean hasNoEarlierHotbarReceiver(ItemStack[] var0, int var1, ItemStack var2, ItemStack var3) {
      if (isSameItemType(var2, var3) && YaJx(var3) != 0) {
         for (int var4 = 0; var4 < 9; var4++) {
            ItemStack var5 = var0[var4];
            if (var4 != var1 && var4 < var1 && var5 != null && isSameItemType(var5, var2) && YaJx(var5) > 0) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static int YaJx(ItemStack var0) {
      return var0 == null ? 0 : Math.max(0, var0.getMaxStackSize() - var0.stackSize);
   }

   private static boolean isSameItemType(ItemStack var0, ItemStack var1) {
      if (var0 == null || var1 == null || var0.getItem() != var1.getItem()) {
         return false;
      } else {
         return var0.getHasSubtypes() && var0.getMetadata() != var1.getMetadata() ? false : ItemStack.areItemStackTagsEqual(var0, var1);
      }
   }
}
