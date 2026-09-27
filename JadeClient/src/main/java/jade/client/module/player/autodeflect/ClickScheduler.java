// Jade recovery: original class: jade.deps.eLz.wgj8Kgk
package jade.client.module.player.autodeflect;

import java.util.function.LongSupplier;

public final class ClickScheduler {
   private long LjYvkH;

   public int consumeDueClicks(long var1, LongSupplier var3) {
      if (this.LjYvkH == 0L) {
         this.LjYvkH = var1;
      }

      int var4 = 0;

      while (this.LjYvkH <= var1) {
         var4++;
         this.LjYvkH = this.LjYvkH + var3.getAsLong();
      }

      return var4;
   }

   public void resetSchedule() {
      this.LjYvkH = 0L;
   }

   public static int getJitterBound(double var0) {
      int var2 = Math.max(1, (int)var0);
      int var3 = 1000 / var2;
      return Math.max(1, var3 / 3 + 1);
   }

   public static long getDelayMillis(double var0, int var2) {
      int var3 = Math.max(1, (int)var0);
      int var4 = 1000 / var3;
      int var5 = var2 - var4 / 6;
      return Math.max(33, var4 + var5);
   }
}
