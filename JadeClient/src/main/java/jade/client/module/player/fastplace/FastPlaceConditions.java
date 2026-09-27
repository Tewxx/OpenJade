// Jade recovery: original class: jade.deps.eLz.dn7m4nOpeP
package jade.client.module.player.fastplace;

public final class FastPlaceConditions {
   private FastPlaceConditions() {
   }

   public static boolean meetsFastPlaceConditions(boolean var0, boolean var1, boolean var2, float var3, boolean var4, boolean var5, boolean var6) {
      if (var0 && !var1) {
         return false;
      } else {
         return var2 && var3 < 70.0F ? false : !var4 && !var5 && var6;
      }
   }

   public static int resolveRightClickDelay(int var0, int var1) {
      if (var0 == 4) {
         return var1;
      } else {
         return var0 != 0 && var1 <= var0 ? var1 : var0;
      }
   }
}
