// Jade recovery: original class: jade.deps.eLz.wWyLcMee
package jade.client.module.player.scaffold;

import net.minecraft.util.Vec3;

public final class MovementPrediction {
   private MovementPrediction() {
   }

   public static Vec3 predictHorizontalDelta(double var0, double var2, double var4, double var6) {
      if (Double.isFinite(var4) && Double.isFinite(var6)) {
         double var8 = var0 * var0 + var2 * var2;
         double var10 = var4 * var4 + var6 * var6;
         return Double.isFinite(var8) && var8 <= 16.0 && var8 > var10 && var0 * var4 + var2 * var6 >= 0.0
            ? new Vec3(var0, 0.0, var2)
            : new Vec3(var4, 0.0, var6);
      } else {
         return new Vec3(0.0, 0.0, 0.0);
      }
   }
}
