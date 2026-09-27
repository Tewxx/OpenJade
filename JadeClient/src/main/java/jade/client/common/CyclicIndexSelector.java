// Jade recovery: original class: jade.deps.eLz.PUGSVwrv
package jade.client.common;

public final class CyclicIndexSelector {
   private CyclicIndexSelector() {
   }

   public static int getCyclicIndex(int var0) {
      long var1 = System.currentTimeMillis() / 1000L;
      return (int)(var1 % var0);
   }
}
