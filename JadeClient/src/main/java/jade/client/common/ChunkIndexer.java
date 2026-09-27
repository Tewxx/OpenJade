// Jade recovery: original class: jade.deps.eLz.dNUelpSI
package jade.client.common;

import net.minecraft.world.chunk.Chunk;

public final class ChunkIndexer {
   private ChunkIndexer() {
   }

   public static int indexChunk(Chunk var0, BlockListFilter var1, ChunkBlockIndex var2, boolean var3, BedBlockFilter var4, ChunkBlockIndex var5, boolean var6) {
      ChunkScan var7 = ChunkScan.scanChunk(var0, var3 ? var1 : null, var6 ? var4 : null);
      long var8 = ChunkKey.packCoords(var0.xPosition, var0.zPosition);
      if (var3) {
         var2.setChunkBlocks(var8, var7.SQIOn());
      }

      if (var6) {
         var5.setChunkBlocks(var8, var7.getSecondaryMatches());
      }

      return var7.getSectionCount();
   }
}
