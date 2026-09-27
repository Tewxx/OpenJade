// Jade recovery: original class: jade.deps.eLz.tIrjmLtOv
package jade.client.module.combat.hitselect;

public final class tIrjmLtOv {
   private tIrjmLtOv() {
   }

   public static int millisToTicks(double var0) {
      return var0 <= 0.0 ? 0 : (int)Math.ceil(var0 / 50.0);
   }

   public static boolean rollChancePercent(double var0, double var2) {
      if (var0 <= 0.0) {
         return false;
      } else {
         return var0 >= 100.0 ? true : var2 * 100.0 < var0;
      }
   }

   public static boolean isCriticalSwing(float var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      return var0 > 0.0F && !var1 && !var2 && !var3 && !var4 && !var5;
   }
}
