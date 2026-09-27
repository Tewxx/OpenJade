// Jade recovery: original class: jade.deps.eLz.iMll7U
package jade.client.module.player.autotool;

import jade.deps.loader107.DoubleMultiplyConstantCipherTwo;
import jade.deps.loader107.MurmurFinalizerConstantCipherSix;

public final class TimedValueTracker<T> {
   private long SeBh = MurmurFinalizerConstantCipherSix.decodeLong(1665144797324520349L, -691624837);
   private long valueChangeTime = DoubleMultiplyConstantCipherTwo.decodeLong(5468376936595316897L, 1179937147);
   private T Kj3;

   public void updateHoldStart(boolean var1, long var2) {
      if (!var1) {
         this.SeBh = -1L;
      } else if (this.SeBh < 0L) {
         this.SeBh = var2;
      }
   }

   public void updateValue(T var1, long var2) {
      if (var1 == null) {
         this.Kj3 = null;
         this.valueChangeTime = -1L;
      } else if (!var1.equals(this.Kj3)) {
         this.Kj3 = (T)var1;
         this.valueChangeTime = var2;
      }
   }

   public boolean hasHoldElapsed(double var1, long var3) {
      return hasDelayElapsed(this.SeBh, var1, var3);
   }

   public boolean hasValueElapsed(double var1, long var3, boolean var5) {
      return hasDelayElapsed(this.valueChangeTime, var1, var3) || var5;
   }

   public void reset() {
      this.SeBh = this.valueChangeTime = -1L;
      this.Kj3 = null;
   }

   private static boolean hasDelayElapsed(long var0, double var2, long var4) {
      return var2 <= 0.0 || var0 >= 0L && var4 - var0 >= (long)Math.ceil(var2);
   }
}
