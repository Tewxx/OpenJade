// Jade recovery: original class: jade.deps.eLz.pxpHNkkrFw
package jade.client.common;

import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.BlockPos;

public final class ChunkBlockIndex {
   private final Map<Long, Set<BlockPos>> blocksByChunk = new ConcurrentHashMap<>();

   public void clearAll() {
      this.blocksByChunk.clear();
   }

   public void removeChunk(int var1, int var2) {
      this.blocksByChunk.remove(ChunkKey.packCoords(var1, var2));
   }

   public void addBlock(long var1, BlockPos var3) {
      this.blocksByChunk.computeIfAbsent(var1, ChunkBlockIndex::createBlockSet).add(var3);
   }

   public void removeBlock(long var1, BlockPos var3) {
      Set var4 = this.blocksByChunk.get(var1);
      if (var4 != null) {
         var4.remove(var3);
      }
   }

   public void setChunkBlocks(long var1, Set<BlockPos> var3) {
      if (var3.isEmpty()) {
         this.blocksByChunk.remove(var1);
      } else {
         this.blocksByChunk.put(var1, var3);
      }
   }

   public boolean EXKuPk(BlockPos var1) {
      if (var1 == null) {
         return false;
      } else {
         Set var2 = this.blocksByChunk.get(ChunkKey.packCoords(var1.getX() >> 4, var1.getZ() >> 4));
         return var2 != null && var2.contains(var1);
      }
   }

   public int getBlockCount() {
      int var1 = 0;

      for (Set var3 : this.blocksByChunk.values()) {
         var1 += var3.size();
      }

      return var1;
   }

   public Iterable<Entry<Long, Set<BlockPos>>> getChunkEntries() {
      return this.blocksByChunk.entrySet();
   }

   private static Set createBlockSet(Long var0) {
      return ConcurrentHashMap.newKeySet();
   }
}
