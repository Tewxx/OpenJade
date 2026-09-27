// Jade recovery: original class: jade.deps.eLz.NbwpOt8$4
package jade.client.module.minigames;

import jade.client.common.QuickBuyLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

public final class Opsec$4 {
   private final ContainerChest containerChest;
   private final int windowId;
   private final List<Integer> liveGridSlots;
   private final List<String> QDNy;
   private final List<String> GEad;
   private final List<ItemStack> currentItemStacks;
   private long snapshotTimeMillis;

   Opsec$4(ContainerChest var1, List<Integer> var2, List<String> var3, List<String> var4) {
      this.containerChest = var1;
      this.windowId = var1.windowId;
      this.liveGridSlots = Collections.unmodifiableList(new ArrayList<>(var2));
      this.QDNy = Collections.unmodifiableList(new ArrayList<>(var3));
      this.GEad = Collections.unmodifiableList(new ArrayList<>(var4));
      this.currentItemStacks = new ArrayList<>(Collections.nCopies(21, null));
      this.HzmH();
      this.snapshotTimeMillis = System.currentTimeMillis();
   }

   private void HzmH() {
      for (int var1 = 0; var1 < this.QDNy.size(); var1++) {
         int var2 = QuickBuyLayout.indexOfItem(this.GEad, this.QDNy.get(var1));
         ItemStack var3 = var2 < 0 ? null : this.containerChest.getLowerChestInventory().getStackInSlot(this.liveGridSlots.get(var2));
         this.currentItemStacks.set(var1, var3 == null ? null : var3.copy());
      }
   }

   public static List getLiveGridSlots(Opsec$4 var0) {
      return var0.liveGridSlots;
   }

   public static List CrXz(Opsec$4 var0) {
      return var0.currentItemStacks;
   }

   public static List getLiveItemNames(Opsec$4 var0) {
      return var0.GEad;
   }

   public static List mhCt(Opsec$4 var0) {
      return var0.QDNy;
   }

   public static ContainerChest getContainerChest(Opsec$4 var0) {
      return var0.containerChest;
   }

   public static int getWindowId(Opsec$4 var0) {
      return var0.windowId;
   }

   public static long VbPu(Opsec$4 var0) {
      return var0.snapshotTimeMillis;
   }

   public static long setSnapshotTimeMillis(Opsec$4 var0, long var1) {
      return var0.snapshotTimeMillis = var1;
   }

   public static void refreshItemStacks(Opsec$4 var0) {
      var0.HzmH();
   }
}
