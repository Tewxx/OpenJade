// Jade recovery: original class: jade.deps.eLz.eRTpdTV7h
package jade.client.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SmoothedValue {
   private double oyS;
   private double smoothedValue;

   public SmoothedValue(double var1) {
      this.resetValue(var1);
   }

   public void resetValue(double var1) {
      this.oyS = var1;
      this.smoothedValue = var1;
   }

   public double updateTargetFromPosition(double var1, double var3, double var5, double var7, boolean var9, double var10) {
      double var12 = Math.min(var3, Math.max(0.0, var1));
      if (var12 == 0.0 && var9) {
         this.oyS = -1.0;
      } else {
         this.oyS = roundToDecimals(var12 / var3 * (var7 - var5) + var5, 4);
      }

      this.smoothedValue = this.smoothedValue + (this.oyS - this.smoothedValue) * var10;
      return this.oyS;
   }

   public double getTargetValue() {
      return this.oyS;
   }

   public double getSmoothedValue() {
      return this.smoothedValue;
   }

   public double UphyY(double var1, double var3, double var5) {
      return this.smoothedValue == -1.0 ? 0.0 : var1 * ((this.smoothedValue - var3) / (var5 - var3));
   }

   private static double roundToDecimals(double var0, int var2) {
      return var2 < 0 ? 0.0 : new BigDecimal(var0).setScale(var2, RoundingMode.HALF_UP).doubleValue();
   }
}
