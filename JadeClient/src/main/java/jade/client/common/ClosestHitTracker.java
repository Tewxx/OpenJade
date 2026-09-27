// Jade recovery: original class: jade.deps.eLz.blEiy7jhw
package jade.client.common;

import java.util.function.BooleanSupplier;

public final class ClosestHitTracker {
   private double Sws;

   public ClosestHitTracker(double var1) {
      this.Sws = var1;
   }

   public boolean icqczQ() {
      if (!(this.Sws >= 0.0)) {
         return false;
      } else {
         this.Sws = 0.0;
         return true;
      }
   }

   public boolean considerHit(double var1, BooleanSupplier var3) {
      if (!(var1 < this.Sws) && this.Sws != 0.0) {
         return false;
      } else if (var3.getAsBoolean()) {
         return this.Sws == 0.0;
      } else {
         this.Sws = var1;
         return true;
      }
   }

   public double getDistance() {
      return this.Sws;
   }

   public boolean fewhsa8(double var1, boolean var3) {
      return this.Sws < var1 || var3;
   }
}
