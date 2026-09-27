// Jade recovery: original class: jade.deps.eLz.AVuFA3btch
package jade.client.gui;

public final class MouseDragTracker {
   private boolean hovered;
   private int boG9;
   private int MPQoF;

   public boolean updateDragState(int var1, int var2, int var3, int var4, boolean var5) {
      int var6 = var4 + (var5 ? 74 : 50);
      this.hovered = var1 >= var3 && var1 <= var3 + 74 && var2 >= var4 && var2 <= var6;
      if (this.hovered) {
         this.boG9 = var1;
         this.MPQoF = var2;
      }

      return this.hovered;
   }

   public void clearHover() {
      this.hovered = false;
   }

   public boolean isHovered() {
      return this.hovered;
   }

   public int consumeDeltaX(int var1) {
      int var2 = var1 - this.boG9;
      this.boG9 = var1;
      return var2;
   }

   public int QcPhj(int var1) {
      int var2 = var1 - this.MPQoF;
      this.MPQoF = var1;
      return var2;
   }
}
