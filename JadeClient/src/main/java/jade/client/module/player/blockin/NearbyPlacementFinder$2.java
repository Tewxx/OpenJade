// Jade recovery: original class: jade.deps.eLz.g4krmTtk$2
package jade.client.module.player.blockin;

import net.minecraft.util.BlockPos;

public final class NearbyPlacementFinder$2 {
   private final BlockPos blockPos;
   private final double distanceSq;

   NearbyPlacementFinder$2(BlockPos var1, double var2) {
      this.blockPos = var1;
      this.distanceSq = var2;
   }

   public static BlockPos getBlockPos(NearbyPlacementFinder$2 var0) {
      return var0.blockPos;
   }

   public static double getDistanceSq(NearbyPlacementFinder$2 var0) {
      return var0.distanceSq;
   }
}
