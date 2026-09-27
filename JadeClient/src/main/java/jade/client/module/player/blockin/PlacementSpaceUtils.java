// Jade recovery: original class: jade.deps.eLz.r9sI8FD0
package jade.client.module.player.blockin;

import jade.client.common.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class PlacementSpaceUtils {
   private PlacementSpaceUtils() {
   }

   public static boolean utUuoro(BlockPos var0) {
      Block var1 = BlockUtils.iepjdt(var0);
      return var1 == Blocks.fire || var1 == Blocks.water || var1 == Blocks.flowing_water || BlockUtils.isReplaceableAt(var0);
   }

   public static boolean hasExposedNeighbor(BlockPos var0, BlockPos var1, BlockPos var2) {
      for (EnumFacing var6 : EnumFacing.values()) {
         BlockPos var7 = var0.offset(var6);
         if (utUuoro(var7) && !var7.equals(var1) && !var7.equals(var2)) {
            return true;
         }
      }

      return false;
   }
}
