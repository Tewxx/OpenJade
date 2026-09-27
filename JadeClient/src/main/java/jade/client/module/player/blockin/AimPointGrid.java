// Jade recovery: original class: jade.deps.eLz.S5GyaFBc
package jade.client.module.player.blockin;

import java.util.function.DoubleSupplier;

public final class AimPointGrid {
   private AimPointGrid() {
   }

   public static void forEachJitteredPoint(boolean var0, DoubleSupplier var1, AimPointGrid$0 var2) {
      for (int var3 = 0; var3 <= 5; var3++) {
         double var4 = jitteredGridCoord(var3, var1);

         for (int var6 = 0; var6 <= 5; var6++) {
            double var7 = jitteredGridCoord(var6, var1);
            if (var0 && (var3 & 1) != 0) {
               var7 = 1.0 - var7;
            }

            var2.acceptPoint(var7, var4);
         }
      }
   }

   private static double jitteredGridCoord(int var0, DoubleSupplier var1) {
      double var2 = var0 * 0.2 + (var1.getAsDouble() * 2.0 - 1.0) * 0.020000000000000004;
      return var2 < 0.0 ? 0.0 : (var2 > 1.0 ? 1.0 : var2);
   }
}
