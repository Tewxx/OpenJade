// Jade recovery: original class: jade.deps.eLz.Rv9GtRn6$1
package jade.client.common;

public final class ItemGridLayout$1 {
   public final float squareLeft;
   public final float top;
   public final float hueStripLeft;
   public final float Jfr;
   public final float squareRight;
   public final float bottom;

   ItemGridLayout$1(float var1, float var2, float var3, float var4) {
      this.squareLeft = var1;
      this.top = var2;
      this.hueStripLeft = var3;
      this.Jfr = var4;
      this.squareRight = var4;
      this.bottom = var2 + 54.0F;
   }

   public boolean isOverSaturationSquare(float var1, float var2) {
      return var1 >= this.squareLeft && var1 <= this.squareLeft + 54.0F && var2 >= this.top && var2 <= this.bottom;
   }

   public boolean isOverHueStrip(float var1, float var2) {
      return var1 >= this.hueStripLeft && var1 <= this.Jfr && var2 >= this.top && var2 <= this.bottom;
   }

   public boolean isOverColorPicker(float var1, float var2) {
      return var1 >= this.squareLeft - 3.0F && var1 <= this.squareRight + 3.0F && var2 >= this.top - 3.0F && var2 <= this.bottom + 3.0F;
   }

   public float getSaturationFromX(float var1) {
      return (var1 - this.squareLeft) / 54.0F;
   }

   public float getBrightnessFromY(float var1) {
      return 1.0F - (var1 - this.top) / 54.0F;
   }

   public float getHueFromY(float var1) {
      return (var1 - this.top) / 54.0F;
   }
}
