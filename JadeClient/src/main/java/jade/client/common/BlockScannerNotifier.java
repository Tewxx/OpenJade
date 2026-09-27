// Jade recovery: original class: jade.deps.eLz.cEpb0bqD7u
package jade.client.common;

import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public final class BlockScannerNotifier {
   private BlockScannerNotifier() {
   }

   public static void atjeIss(Set<BlockScanner$1> var0) {
      for (BlockScanner$1 var2 : var0) {
         var2.cKj73();
      }
   }

   public static void notifyChunkLoaded(Set<BlockScanner$1> var0, int var1, int var2) {
      for (BlockScanner$1 var4 : var0) {
         var4.onChunkLoaded(var1, var2);
      }
   }

   public static void YBazm(Set<BlockScanner$1> var0, int var1, int var2) {
      for (BlockScanner$1 var4 : var0) {
         var4.onChunkUnloaded(var1, var2);
      }
   }

   public static void notifyBlockChanged(Set<BlockScanner$1> var0, BlockPos var1, IBlockState var2) {
      for (BlockScanner$1 var4 : var0) {
         var4.onBlockChanged(var1, var2);
      }
   }
}
