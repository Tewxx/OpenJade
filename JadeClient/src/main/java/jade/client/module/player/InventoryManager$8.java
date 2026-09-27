// Jade recovery: original class: jade.deps.eLz.G70DnWvZT$8
package jade.client.module.player;

import jade.client.module.player.inventorymanager.InventorySnapshot;

public final class InventoryManager$8 {
   public final InventorySnapshot inventorySnapshot;
   public final InventoryManager$7[] desiredItems;

   InventoryManager$8(InventorySnapshot var1, InventoryManager$7[] var2) {
      this.inventorySnapshot = var1;
      this.desiredItems = var2;
   }

   public static InventoryManager$8 createContext(InventorySnapshot var0, InventoryManager$7[] var1) {
      return new InventoryManager$8(var0, var1);
   }
}
