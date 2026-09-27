// Jade recovery: original class: jade.deps.eLz.g1eF2Oceei
package jade.client.module.player.fastplace;

public final class ElapsedTimerGate {
   private long MOt;

   public void reset() {
      this.MOt = 0L;
   }

   public void beginTiming(long var1) {
      if (this.MOt == 0L) {
         this.MOt = var1;
      }
   }

   public boolean hasElapsed(long var1, long var3) {
      return var1 - this.MOt >= var3;
   }
}
