// Jade recovery: original class: jade.deps.eLz.oYsl3F
package jade.client.core;

public final class TickAccumulator {
   private static final long fceZ = 50000000L;
   private static final int MAX_TICKS_PER_UPDATE = 10;
   private long ImW;
   private double accumulatedNanos;

   public void resetAt(long var1) {
      this.ImW = var1;
      this.accumulatedNanos = 0.0;
   }

   public TickAccumulator$1 MDBEHL(long var1) {
      this.accumulatedNanos = this.accumulatedNanos + (var1 - this.ImW);
      this.ImW = var1;

      int var3;
      for (var3 = 0; this.accumulatedNanos >= 5.0E7 && var3 < 10; var3++) {
         this.accumulatedNanos -= 5.0E7;
      }

      if (var3 >= 10) {
         this.accumulatedNanos = 0.0;
      }

      return new TickAccumulator$1(var3, (float)(this.accumulatedNanos / 5.0E7));
   }

   public void YLjRn() {
      this.accumulatedNanos = 0.0;
   }
}
