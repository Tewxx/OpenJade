// Jade recovery: original class: jade.deps.eLz.QfW6ZH2Ci
package jade.client.module.player.autotool;

public final class ToolSlotController {
   private boolean gost;

   public boolean isActive() {
      return this.gost;
   }

   public boolean isActiveAndAttacking(boolean var1) {
      return this.gost && var1;
   }

   public int VORfA(int var1, int var2, int var3) {
      return var1 == -1 && this.needsSlotSwitch(var2, var3) ? var2 : var1;
   }

   public int nextSlotForScroll(int var1, int var2) {
      return Math.floorMod(var1 - Integer.signum(var2), 9);
   }

   public boolean needsSlotSwitch(int var1, int var2) {
      return var2 != -1 && var2 != var1;
   }

   public void markActive() {
      this.gost = true;
   }

   public void MhLm6() {
      this.gost = false;
   }
}
