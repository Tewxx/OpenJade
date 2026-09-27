// Jade recovery: original class: jade.deps.eLz.yyE2q2S
package jade.client.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public final class BlockIndexRecorder {
   private BlockIndexRecorder() {
   }

   public static BlockPos indexBlock(BlockPos var0, IBlockState var1, BlockListFilter var2, ChunkBlockIndex var3, boolean var4, BedBlockFilter var5, ChunkBlockIndex var6, boolean var7) {
      long var8 = ChunkKey.packCoords(var0.getX() >> 4, var0.getZ() >> 4);
      BlockPos var10 = new BlockPos(var0.getX(), var0.getY(), var0.getZ());
      if (var4) {
         boolean var11 = var2.matches(var1) && var2.matchesAt(var0, var1);
         gam5(var3, var11, var8, var10, var0);
      }

      if (var7) {
         boolean var12 = var5.matches(var1) && var5.matchesAt(var0, var1);
         gam5(var6, var12, var8, var10, var0);
      }

      return var10;
   }

   private static void gam5(ChunkBlockIndex var0, boolean var1, long var2, BlockPos var4, BlockPos var5) {
      if (var1) {
         var0.addBlock(var2, var4);
      } else {
         var0.removeBlock(var2, var5);
      }
   }
}
