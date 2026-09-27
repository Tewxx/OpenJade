// Jade recovery: original class: jade.deps.eLz.gXaUVQHG
package jade.client.module.player.bridgeassist;

import java.util.List;
import net.minecraft.util.AxisAlignedBB;

public final class EdgeDistanceCalculator {
   private static final double GROUND_PROBE_HEIGHT = 0.01;

   private EdgeDistanceCalculator() {
   }

   public static AxisAlignedBB Soyh(AxisAlignedBB var0) {
      return new AxisAlignedBB(var0.minX, var0.minY - 0.01, var0.minZ, var0.maxX, var0.minY, var0.maxZ);
   }

   public static double computeEdgeDistance(AxisAlignedBB var0, List<AxisAlignedBB> var1) {
      if (var1.isEmpty()) {
         return Double.NaN;
      } else {
         double var2 = axisMidpoint(var0.minX, var0.maxX);
         double var4 = axisMidpoint(var0.minZ, var0.maxZ);
         double var6 = Double.POSITIVE_INFINITY;

         for (AxisAlignedBB var9 : var1) {
            double var10 = distanceOutsideRange(var2, var9.minX, var9.maxX);
            double var12 = distanceOutsideRange(var4, var9.minZ, var9.maxZ);
            var6 = Math.min(var6, Math.max(var10, var12));
         }

         return var6;
      }
   }

   private static double axisMidpoint(double var0, double var2) {
      return var0 + (var2 - var0) * 0.5;
   }

   private static double distanceOutsideRange(double var0, double var2, double var4) {
      if (var0 < var2) {
         return var2 - var0;
      } else {
         return var0 > var4 ? var0 - var4 : 0.0;
      }
   }
}
