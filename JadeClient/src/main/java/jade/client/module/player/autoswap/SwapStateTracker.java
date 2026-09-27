// Jade recovery: original class: jade.deps.eLz.PUx3H2Op
package jade.client.module.player.autoswap;

import net.minecraft.item.ItemStack;

public final class SwapStateTracker {
   private static final long SWAP_COOLDOWN_MILLIS = 300L;
   private ItemStack itemStack;
   private int placedSlot = -1;
   private int CdHo2 = -1;
   private long lastSwapTimeMillis;
   private boolean pendingSwap;

   public void clear() {
      this.itemStack = null;
      this.placedSlot = -1;
      this.CdHo2 = -1;
      this.lastSwapTimeMillis = 0L;
      this.pendingSwap = false;
   }

   public void recordPlacement(ItemStack var1, int var2, boolean var3) {
      this.itemStack = var1.copy();
      this.itemStack.stackSize = 1;
      this.placedSlot = var2;
      this.pendingSwap = var3;
   }

   public boolean consumePendingSwap() {
      boolean var1 = this.pendingSwap;
      this.pendingSwap = false;
      return var1;
   }

   public ItemStack getPlacedStack() {
      return this.itemStack;
   }

   public int getPlacedSlot() {
      return this.placedSlot;
   }

   public boolean canSwapToSlot(int var1, long var2) {
      return var1 != this.placedSlot && (var1 != this.CdHo2 || var2 - this.lastSwapTimeMillis >= 300L);
   }

   public void wfTy1(int var1, long var2) {
      this.CdHo2 = var1;
      this.lastSwapTimeMillis = var2;
   }
}
