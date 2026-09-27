// Jade recovery: original class: jade.deps.eLz.VLqid0
package jade.client.module.minigames.opsec;

import jade.client.common.ItemNames;
import jade.client.common.QuickBuyLayout;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

public final class QuickBuySlotReader {
   private QuickBuySlotReader() {
   }

   public static List<Integer> findQuickBuyGridSlots(ContainerChest var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         return new ArrayList<>();
      } else {
         int var2 = var0.getLowerChestInventory().getSizeInventory();

         for (int var3 = 0; var3 < var2; var3++) {
            ItemStack var4 = var0.getLowerChestInventory().getStackInSlot(var3);
            var1.add(ItemNames.tooltipContains(var4, "this is a quick buy slot"));
         }

         return QuickBuyGridUtils.extractQuickBuyGridSlots(var1);
      }
   }

   public static List<Integer> findSlotsMatchingLayout(ContainerChest var0, List<String> var1) {
      ArrayList var2 = new ArrayList();
      if (var0 != null && QuickBuyLayout.isValidLayout(var1)) {
         int var3 = var0.getLowerChestInventory().getSizeInventory();
         ArrayList var4 = new ArrayList(var3);

         for (int var5 = 0; var5 < var3; var5++) {
            var4.add(ItemNames.getSkyblockId(var0.getLowerChestInventory().getStackInSlot(var5)));
         }

         return QuickBuyGridUtils.matchGridSlotsForLayout(var4, var1);
      } else {
         return var2;
      }
   }

   public static List<Integer> computeGridSlotIndices(ContainerChest var0) {
      return (List<Integer>)(var0 == null ? new ArrayList<>() : QuickBuyGridUtils.computeGridSlotIndices(var0.getLowerChestInventory().getSizeInventory()));
   }

   public static List<String> CNIR(ContainerChest var0, List<Integer> var1) {
      ArrayList var2 = new ArrayList();
      if (var0 != null && var1 != null) {
         int var3 = var0.getLowerChestInventory().getSizeInventory();

         for (Integer var5 : var1) {
            if (var5 == null || var5 < 0 || var5 >= var3) {
               return new ArrayList<>();
            }

            String var6 = ItemNames.getSkyblockId(var0.getLowerChestInventory().getStackInSlot(var5));
            if (var6.isEmpty()) {
               return new ArrayList<>();
            }

            var2.add(var6);
         }

         return var2;
      } else {
         return var2;
      }
   }

   public static List<String> readLayoutItemNames(ContainerChest var0, List<Integer> var1, List<String> var2) {
      ArrayList var3 = new ArrayList();
      if (var0 != null && var1 != null && var2 != null && var1.size() == var2.size()) {
         int var4 = var0.getLowerChestInventory().getSizeInventory();

         for (int var5 = 0; var5 < var1.size(); var5++) {
            Integer var6 = (Integer)var1.get(var5);
            if (var6 == null || var6 < 0 || var6 >= var4) {
               return new ArrayList<>();
            }

            ItemStack var7 = var0.getLowerChestInventory().getStackInSlot(var6);
            String var8 = ItemNames.getSkyblockId(var7);
            String var9 = QuickBuyLayout.normalizeItemName((String)var2.get(var5));
            if (!var9.equals(var8) && ItemNames.matchesToolName(var7, var9)) {
               var8 = var9;
            }

            if (var8.isEmpty()) {
               return new ArrayList<>();
            }

            var3.add(var8);
         }

         return var3;
      } else {
         return var3;
      }
   }

   public static List<String> readItemNamesAt(ContainerChest var0, List<Integer> var1) {
      ArrayList var2 = new ArrayList();
      if (var0 != null && var1 != null && var1.size() == 21) {
         int var3 = var0.getLowerChestInventory().getSizeInventory();

         for (Integer var5 : var1) {
            if (var5 == null || var5 < 0 || var5 >= var3) {
               return new ArrayList<>();
            }

            var2.add(ItemNames.getSkyblockId(var0.getLowerChestInventory().getStackInSlot(var5)));
         }

         return var2;
      } else {
         return var2;
      }
   }
}
