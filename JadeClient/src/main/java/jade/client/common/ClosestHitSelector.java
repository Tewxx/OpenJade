// Jade recovery: original class: jade.deps.eLz.FB2IsNf
package jade.client.common;

public final class ClosestHitSelector<T, P> {
   private final double EQo;
   private double hOtc;
   private T dyx2;
   private P wrz;

   public ClosestHitSelector(double var1) {
      this.EQo = var1;
      this.hOtc = var1;
   }

   public void MuJa(T var1, P var2) {
      if (!(this.hOtc < 0.0)) {
         this.dyx2 = (T)var1;
         this.wrz = (P)var2;
         this.hOtc = 0.0;
      }
   }

   public void ydMynT(T var1, P var2, double var3, boolean var5, boolean var6) {
      if (!(var3 >= this.hOtc) || this.hOtc == 0.0) {
         if (var5 && !var6) {
            if (this.hOtc == 0.0) {
               this.storeHit((T)var1, (P)var2, this.hOtc);
            }
         } else {
            this.storeHit((T)var1, (P)var2, var3);
         }
      }
   }

   public boolean hasValidHit() {
      return this.dyx2 != null && this.hOtc < this.EQo;
   }

   public T getHitEntity() {
      return this.dyx2;
   }

   public P getHitPosition() {
      return this.wrz;
   }

   private void storeHit(T var1, P var2, double var3) {
      this.dyx2 = (T)var1;
      this.wrz = (P)var2;
      this.hOtc = var3;
   }
}
