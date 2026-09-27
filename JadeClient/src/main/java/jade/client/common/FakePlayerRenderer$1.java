// Jade recovery: original class: jade.deps.eLz.jt60g7H48z$1
package jade.client.common;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.entity.player.EntityPlayer;

public final class FakePlayerRenderer$1 {
   private static final long HISTORY_WINDOW_MILLIS = 5000L;
   private final Deque<FakePlayerRenderer$3> AXI = new ArrayDeque<>();

   public void clearHistory() {
      this.AXI.clear();
   }

   public void recordSnapshot(EntityPlayer var1, float var2) {
      if (var1 == null) {
         this.clearHistory();
      } else {
         long var3 = this.ICripE();
         FakePlayerRenderer$2 var5 = FakePlayerRenderer$2.captureInterpolatedSnapshot(var1, var2);
         this.AXI.addLast(new FakePlayerRenderer$3(var3, var5));

         while (this.AXI.size() > 2 && var3 - FakePlayerRenderer$3.getSnapshotTimeMillis(this.AXI.peekFirst()) > 5000L) {
            this.AXI.removeFirst();
         }
      }
   }

   public FakePlayerRenderer$2 YJfvaQ(long var1) {
      if (this.AXI.isEmpty()) {
         return null;
      } else {
         long var3 = this.ICripE() - Math.max(0L, var1);
         FakePlayerRenderer$3 var5 = this.AXI.peekFirst();
         if (var3 <= FakePlayerRenderer$3.getSnapshotTimeMillis(var5)) {
            return FakePlayerRenderer$3.getRenderState(var5);
         } else {
            FakePlayerRenderer$3 var6 = var5;

            for (FakePlayerRenderer$3 var8 : this.AXI) {
               if (FakePlayerRenderer$3.getSnapshotTimeMillis(var8) >= var3) {
                  long var9 = Math.max(1L, FakePlayerRenderer$3.getSnapshotTimeMillis(var8) - FakePlayerRenderer$3.getSnapshotTimeMillis(var6));
                  float var11 = (float)(var3 - FakePlayerRenderer$3.getSnapshotTimeMillis(var6)) / (float)var9;
                  return FakePlayerRenderer$2.interpolateSnapshot(FakePlayerRenderer$3.getRenderState(var6), FakePlayerRenderer$3.getRenderState(var8), var11);
               }

               var6 = var8;
            }

            return FakePlayerRenderer$3.getRenderState(this.AXI.peekLast());
         }
      }
   }

   private long ICripE() {
      return System.nanoTime() / 1000000L;
   }
}
