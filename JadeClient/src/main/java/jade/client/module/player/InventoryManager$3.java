// Jade recovery: original class: jade.deps.eLz.G70DnWvZT$3
package jade.client.module.player;

import jade.client.module.player.inventorymanager.wtKNsWqQg$0;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class InventoryManager$3 implements wtKNsWqQg$0 {
   public final InventoryManager$5 actionType;
   public final int stepCost;
   public final int hotbarSlot;
   public final int tbn;
   public final int targetCount;
   public final boolean goalAchieved;
   public final int RBEM;
   public final int itemSlot;
   public final List<InventoryManager$4> eiy;
   public final ItemStack itemStack;
   public int stepIndex;

   public InventoryManager$3(InventoryManager$5 var1, int var2, int var3, int var4, int var5, boolean var6, int var7, int var8, List<InventoryManager$4> var9) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, null);
   }

   public InventoryManager$3(InventoryManager$5 var1, int var2, int var3, int var4, int var5, boolean var6, int var7, int var8, List<InventoryManager$4> var9, ItemStack var10) {
      this.actionType = var1;
      this.stepCost = var2;
      this.hotbarSlot = var3;
      this.tbn = var4;
      this.targetCount = var5;
      this.goalAchieved = var6;
      this.RBEM = var7;
      this.itemSlot = var8;
      this.eiy = var9;
      this.itemStack = var10;
   }

   public InventoryManager$4 currentStep() {
      return this.stepIndex < this.eiy.size() ? this.eiy.get(this.stepIndex) : null;
   }

   public void xxyGo2() {
      this.stepIndex++;
   }

   public boolean IjSz() {
      return this.stepIndex >= this.eiy.size();
   }

   @Override
   public int getStepCost() {
      return this.stepCost;
   }

   @Override
   public boolean isGoalAchieved() {
      return this.goalAchieved;
   }

   @Override
   public int CREcO() {
      return this.tbn;
   }

   @Override
   public int getTieBreakRank() {
      return this.RBEM;
   }

   @Override
   public int Vykjs() {
      return this.targetCount;
   }

   @Override
   public int getHotbarSlot() {
      return this.hotbarSlot;
   }

   @Override
   public int JiCg() {
      return this.itemSlot;
   }

   @Override
   public int getActionPriority() {
      return this.actionType.ordinal();
   }
}
