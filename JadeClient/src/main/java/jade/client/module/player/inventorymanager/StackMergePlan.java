// Jade recovery: original class: jade.deps.eLz.Q7w2Ahz
package jade.client.module.player.inventorymanager;

import java.util.Collections;
import java.util.List;

public final class StackMergePlan {
   private final int Olymf9;
   private final int cost;
   private final int mergeTargetSlot;
   private final List<TransferSourceSlot> mergeMoves;

   public StackMergePlan(int var1, int var2, int var3, List<TransferSourceSlot> var4) {
      this.Olymf9 = var1;
      this.cost = var2;
      this.mergeTargetSlot = var3;
      this.mergeMoves = Collections.unmodifiableList(var4);
   }

   public int YaGr() {
      return this.Olymf9;
   }

   public int getCost() {
      return this.cost;
   }

   public int getMergeTargetSlot() {
      return this.mergeTargetSlot;
   }

   public List<TransferSourceSlot> getMergeMoves() {
      return this.mergeMoves;
   }
}
