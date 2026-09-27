// Jade recovery: original class: jade.deps.eLz.m8yb4N9ncy
package jade.client.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public final class BlockScannerListener implements BlockChangeReader$0 {
   private final BlockScanner blockScanner;

   public BlockScannerListener(BlockScanner var1) {
      this.blockScanner = var1;
   }

   @Override
   public void onBlockChanged(BlockPos var1, IBlockState var2) {
      this.blockScanner.onBlockChange(var1, var2);
   }

   @Override
   public void onChunkLoaded(int var1, int var2) {
      this.blockScanner.onChunkLoaded(var1, var2);
   }

   @Override
   public void onChunkUnloaded(int var1, int var2) {
      this.blockScanner.GZFh(var1, var2);
   }
}
