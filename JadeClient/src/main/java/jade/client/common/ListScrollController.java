// Jade recovery: original class: jade.deps.eLz.tyOoYfgD1o
package jade.client.common;

import jade.client.gui.AnimatedFloat;

public final class ListScrollController {
   private final int visibleRowCount;
   private final float IWCP;
   private final AnimatedFloat animatedFloat = new AnimatedFloat(200L);
   private float dragStartX;
   private float dragStartY;

   public ListScrollController(int var1, float var2) {
      this.visibleRowCount = var1;
      this.IWCP = var2;
   }

   public void BtQv(float var1, float var2) {
      this.dragStartX = var1;
      this.dragStartY = var2;
   }

   public void onScroll(int var1, int var2, boolean var3) {
      if (var3) {
         float var4 = 20.0F * (var1 / 120.0F);
         if (var4 != 0.0F) {
            this.animatedFloat.addToTarget(-var4);
         }

         this.updateScrollRange(var2);
      }
   }

   public void updateScrollRange(int var1) {
      float var2 = LayoutMath.overflowScaled(var1, this.visibleRowCount, this.IWCP);
      this.animatedFloat.clampTarget(0.0F, var2);
      if (this.animatedFloat.ORMWO() > var2) {
         this.animatedFloat.snapTo(var2);
      }
   }

   public void resetScroll() {
      this.animatedFloat.snapTo(0.0F);
   }

   public float lGva() {
      return this.animatedFloat.ORMWO();
   }

   public float DUpzmA() {
      return this.dragStartX;
   }

   public float getDragStartY() {
      return this.dragStartY;
   }
}
