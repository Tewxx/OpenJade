// Jade recovery: original class: jade.deps.eLz.wtKNsWqQg
package jade.client.module.player.inventorymanager;

public final class wtKNsWqQg {
   private wtKNsWqQg() {
   }

   public static int compareForSorting(wtKNsWqQg$0 var0, wtKNsWqQg$0 var1) {
      int[] var2 = adxhuvp(var0);
      int[] var3 = adxhuvp(var1);

      for (int var4 = 0; var4 < var2.length; var4++) {
         int var5 = Integer.compare(var2[var4], var3[var4]);
         if (var5 != 0) {
            return var5;
         }
      }

      return 0;
   }

   private static int[] adxhuvp(wtKNsWqQg$0 var0) {
      return new int[]{var0.getStepCost(), var0.isGoalAchieved() ? 0 : 1, var0.CREcO(), var0.getTieBreakRank(), -var0.Vykjs(), var0.getHotbarSlot(), var0.JiCg(), var0.getActionPriority()};
   }
}
