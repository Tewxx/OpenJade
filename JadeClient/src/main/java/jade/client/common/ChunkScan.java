// Jade recovery: original class: jade.deps.eLz.jKug0m
package jade.client.common;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public final class ChunkScan {
   private final int XkhN;
   private final Set<BlockPos> primaryMatches;
   private final Set<BlockPos> OMG;

   private ChunkScan(int var1, Set<BlockPos> var2, Set<BlockPos> var3) {
      this.XkhN = var1;
      this.primaryMatches = var2;
      this.OMG = var3;
   }

   public static ChunkScan scanChunk(Chunk var0, BlockFilter var1, BlockFilter var2) {
      KeySetView var3 = ConcurrentHashMap.newKeySet();
      KeySetView var4 = ConcurrentHashMap.newKeySet();
      ExtendedBlockStorage[] var5 = var0.getBlockStorageArray();
      int var6 = 0;
      int var7 = var0.xPosition << 4;
      int var8 = var0.zPosition << 4;

      for (int var9 = 0; var9 < var5.length; var9++) {
         ExtendedBlockStorage var10 = var5[var9];
         if (var10 != null) {
            var6++;
            int var11 = var9 << 4;

            for (int var12 = 0; var12 < 4096; var12++) {
               int var13 = var12 & 15;
               int var14 = var12 >>> 4 & 15;
               int var15 = var12 >>> 8;
               IBlockState var16 = var10.get(var13, var15, var14);
               if (var16 != null) {
                  BlockPos var17 = new BlockPos(var7 + var13, var11 + var15, var8 + var14);
                  collectMatches(var3, var1, var17, var16);
                  collectMatches(var4, var2, var17, var16);
               }
            }
         }
      }

      return new ChunkScan(Math.max(var6, 1), var3, var4);
   }

   private static void collectMatches(Set<BlockPos> var0, BlockFilter var1, BlockPos var2, IBlockState var3) {
      if (var1 != null && var1.matches(var3) && var1.matchesAt(var2, var3)) {
         var0.add(var2);
      }
   }

   public int getSectionCount() {
      return this.XkhN;
   }

   public Set<BlockPos> SQIOn() {
      return this.primaryMatches;
   }

   public Set<BlockPos> getSecondaryMatches() {
      return this.OMG;
   }
}
