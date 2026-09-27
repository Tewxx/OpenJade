// Jade recovery: original class: jade.deps.eLz.WDHhrTSdi
package jade.client.module.minigames.opsec;

public final class WDHhrTSdi {
   private WDHhrTSdi() {
   }

   public static int normalizeClickMode(int var0) {
      return var0 == 6 ? 0 : var0;
   }

   public static boolean isAllowedEditClickMode(int var0) {
      int var1 = normalizeClickMode(var0);
      return var1 == 0 || var1 == 2 || var1 == 3;
   }
}
