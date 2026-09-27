// Jade recovery: original class: jade.deps.eLz.unbtVdB
package jade.client.setting;

public final class SliderBounds {
   private final double zaPoz;
   private final double soo;
   private final double step;

   public SliderBounds(double var1, double var3, double var5) {
      this.zaPoz = var1;
      this.soo = var3;
      this.step = var5;
   }

   public double getMin() {
      return this.zaPoz;
   }

   public double getMax() {
      return this.soo;
   }

   public double ffe0() {
      return this.step;
   }

   public double Zmuk(double var1) {
      double var3 = Math.min(this.soo, Math.max(this.zaPoz, var1));
      double var5 = 1.0 / this.step;
      return Math.round(var3 * var5) / var5;
   }

   public double toFraction(double var1) {
      return (var1 - this.zaPoz) / Math.max(1.0E-4, this.soo - this.zaPoz);
   }

   public double hcHs(double var1) {
      return this.zaPoz + var1 * (this.soo - this.zaPoz);
   }
}
