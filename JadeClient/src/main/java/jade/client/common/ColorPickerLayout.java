// Jade recovery: original class: jade.deps.eLz.TKzfMTC
package jade.client.common;

public final class ColorPickerLayout {
   private static final float HEADER_HEIGHT = 12.0F;
   private static final float HEADER_PADDING = 2.0F;
   private static final float Ktaol9 = 50.0F;
   private static final float SQUARE_LEFT_OFFSET = 4.0F;
   private static final float STE = 10.0F;
   private static final float SQUARE_TOP_PADDING = 2.0F;
   public final float squareLeft;
   public final float squareTop;
   public final float squareRight;
   public final float squareBottom;
   public final float idU;
   public final float hueBarRight;
   public final float alphaBarLeft;
   public final float XUYtPq;

   private ColorPickerLayout(float var1, float var2, float var3) {
      this.squareLeft = var1 + 4.0F + var3 * 0.5F;
      this.squareTop = var2 + 12.0F + 2.0F;
      this.squareRight = this.squareLeft + 50.0F;
      this.squareBottom = this.squareTop + 50.0F;
      this.idU = this.squareRight + 4.0F;
      this.hueBarRight = this.idU + 10.0F;
      this.alphaBarLeft = this.hueBarRight + 4.0F;
      this.XUYtPq = this.alphaBarLeft + 10.0F;
   }

   public static ColorPickerLayout create(float var0, float var1, float var2) {
      return new ColorPickerLayout(var0, var1, var2);
   }

   public boolean isInsideColorSquare(float var1, float var2) {
      return isBetween(var1, this.squareLeft, this.squareRight) && isBetween(var2, this.squareTop, this.squareBottom);
   }

   public boolean jlkyC(float var1, float var2) {
      return isBetween(var1, this.idU - 2.0F, this.hueBarRight + 2.0F) && isBetween(var2, this.squareTop, this.squareBottom);
   }

   public boolean isInsideAlphaBar(float var1, float var2) {
      return isBetween(var1, this.alphaBarLeft - 2.0F, this.XUYtPq + 2.0F) && isBetween(var2, this.squareTop, this.squareBottom);
   }

   public float getSaturationAt(float var1) {
      return (var1 - this.squareLeft) / 50.0F;
   }

   public float getBrightnessAt(float var1) {
      return 1.0F - this.getVerticalFraction(var1);
   }

   public float getHueAt(float var1) {
      return this.getVerticalFraction(var1) * 360.0F;
   }

   public float getVerticalFraction(float var1) {
      return (var1 - this.squareTop) / 50.0F;
   }

   private static boolean isBetween(float var0, float var1, float var2) {
      return var0 >= var1 && var0 <= var2;
   }
}
