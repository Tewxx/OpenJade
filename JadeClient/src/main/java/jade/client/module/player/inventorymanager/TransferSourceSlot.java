// Jade recovery: original class: jade.deps.eLz.AggYYjP
package jade.client.module.player.inventorymanager;

public final class TransferSourceSlot {
   private final int MkyO;
   private final int moveAmount;
   private final boolean jij1;
   private final boolean splitsStack;

   public TransferSourceSlot(int var1, int var2, boolean var3, boolean var4) {
      this.MkyO = var1;
      this.moveAmount = var2;
      this.jij1 = var3;
      this.splitsStack = var4;
   }

   public int getSourceSlot() {
      return this.MkyO;
   }

   public int QNq5() {
      return this.moveAmount;
   }

   public boolean isFromMainInventory() {
      return this.jij1;
   }

   public boolean iDurqQ() {
      return this.splitsStack;
   }
}
