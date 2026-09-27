// Jade recovery: original class: jade.deps.eLz.rlLoXgKh
package jade.client.module.player.inventorymanager;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public final class InventorySnapshot {
   public static final int eHs = 36;
   private final List<ItemStack> itemStacks = new ArrayList<>(36);
   public final ItemStack itemStack;

   public InventorySnapshot(ItemStack[] var1, ItemStack var2) {
      int var3 = var1 == null ? 0 : var1.length;

      for (int var4 = 0; var4 < 36; var4++) {
         this.itemStacks.add(var4 < var3 ? var1[var4] : null);
      }

      this.itemStack = var2;
   }

   public static InventorySnapshot captureFromPlayer(InventoryPlayer var0) {
      ItemStack[] var1 = new ItemStack[36];

      for (int var2 = 0; var2 < var1.length; var2++) {
         ItemStack var3 = var0.getStackInSlot(var2);
         var1[var2] = var3 == null ? null : var3.copy();
      }

      ItemStack var4 = var0.getItemStack();
      ItemStack var5 = var4 == null ? null : var4.copy();
      return new InventorySnapshot(var1, var5);
   }

   public ItemStack getStackInSlot(int var1) {
      return var1 >= 0 && var1 < this.itemStacks.size() ? this.itemStacks.get(var1) : null;
   }
}
