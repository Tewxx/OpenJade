// Jade recovery: original class: jade.deps.eLz.ayeTXSd9Tp
package jade.client.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

public final class RecentEventCounter {
   private static final long sfc = 1000L;
   private final List<Long> yzX = new ArrayList<>();

   public void record(long var1) {
      this.yzX.add(var1);
   }

   public int pruneAndCount(LongSupplier var1) {
      this.yzX.removeIf((recoveredArg0) -> RecentEventCounter.isExpired(var1, (java.lang.Long) recoveredArg0));
      return this.yzX.size();
   }

   private static boolean isExpired(LongSupplier var0, Long var1) {
      return var1 < var0.getAsLong() - 1000L;
   }
}
