// Jade recovery: original class: jade.deps.eLz.G70DnWvZT$2
package jade.client.module.player;

public final class InventoryManager$2 {
   public final int slot;
   public final double KUqy6;
   public final int sourceInHotbar;
   public final int stackSize;

   public InventoryManager$2(int var1, double var2, int var4, int var5) {
      this.slot = var1;
      this.KUqy6 = var2;
      this.sourceInHotbar = var4;
      this.stackSize = var5;
   }

   public boolean isBetterThan(InventoryManager$2 var1) {
      int var2 = Double.compare(this.KUqy6, var1.KUqy6);
      if (Math.abs(this.KUqy6 - var1.KUqy6) > 1.0E-6) {
         return var2 > 0;
      } else {
         var2 = Integer.compare(this.sourceInHotbar, var1.sourceInHotbar);
         if (var2 != 0) {
            return var2 < 0;
         } else {
            var2 = Integer.compare(this.stackSize, var1.stackSize);
            return var2 != 0 ? var2 > 0 : this.slot < var1.slot;
         }
      }
   }
}
