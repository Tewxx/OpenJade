// Jade recovery: original class: jade.deps.eLz.OiObtiwy$1
package jade.client.module.player.bednuker;

import net.minecraft.util.BlockPos;

public final class BedFinder$1 {
   private final BlockPos footPos;
   private final BlockPos headPos;

   BedFinder$1(BlockPos var1, BlockPos var2) {
      this.footPos = var1;
      this.headPos = var2;
   }

   public static BlockPos getFootPos(BedFinder$1 var0) {
      return var0.footPos;
   }

   public static BlockPos getHeadPos(BedFinder$1 var0) {
      return var0.headPos;
   }
}
