// Jade recovery: original class: jade.deps.eLz.bttQBjh2JZ
package jade.client.common;

import jade.client.event.PacketReceiveEvent;
import jade.client.setting.BlockListSetting;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;

public final class BlockScanner {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final BlockScanner blockScanner = new BlockScanner();
   private final ChunkBlockIndex trackedBlockIndex = new ChunkBlockIndex();
   private final ChunkBlockIndex bedPlateIndex = new ChunkBlockIndex();
   private final Set<BlockScanner$1> listeners = ConcurrentHashMap.newKeySet();
   private final rmMXdwyms7 pendingChunkQueue = new rmMXdwyms7();
   private BlockListFilter blockListFilter;
   private boolean bedPlateScanEnabled;
   private static final BedBlockFilter lWh = new BedBlockFilter();

   private BlockScanner() {
   }

   public static BlockScanner getInstance() {
      return blockScanner;
   }

   public void hokXq4(BlockListSetting var1) {
      this.blockListFilter = new BlockListFilter(var1);
   }

   public void vUnwA() {
      this.blockListFilter = null;
      this.trackedBlockIndex.clearAll();
   }

   public void enableBedPlateScan() {
      this.bedPlateScanEnabled = true;
   }

   public void disableBedPlateScan() {
      this.bedPlateScanEnabled = false;
      this.bedPlateIndex.clearAll();
   }

   private boolean hasBlockListFilter() {
      return this.blockListFilter != null && this.blockListFilter.isActive();
   }

   private boolean isBedPlateScanEnabled() {
      return this.bedPlateScanEnabled;
   }

   public boolean isScanningActive() {
      return this.hasBlockListFilter() || this.isBedPlateScanEnabled();
   }

   public void clearAll() {
      this.trackedBlockIndex.clearAll();
      this.bedPlateIndex.clearAll();
      this.pendingChunkQueue.clear();
      BlockScannerNotifier.atjeIss(this.listeners);
   }

   public void addListener(BlockScanner$1 var1) {
      if (var1 != null) {
         this.listeners.add(var1);
      }
   }

   public void removeListener(BlockScanner$1 var1) {
      if (var1 != null) {
         this.listeners.remove(var1);
      }
   }

   public void onChunkLoaded(int var1, int var2) {
      if (this.isScanningActive()) {
         this.pendingChunkQueue.enqueue(var1, var2);
         BlockScannerNotifier.notifyChunkLoaded(this.listeners, var1, var2);
      }
   }

   public void GZFh(int var1, int var2) {
      this.trackedBlockIndex.removeChunk(var1, var2);
      this.bedPlateIndex.removeChunk(var1, var2);
      BlockScannerNotifier.YBazm(this.listeners, var1, var2);
   }

   public void queueLoadedChunks() {
      if (this.isScanningActive()) {
         this.pendingChunkQueue.clear();
         ZCmKxQFOT9.wpNle(mc, new ZCmKxQFOT9$0() {
            @Override
            public void onChunkLoaded(int var1, int var2) {
               BlockScanner.this.onChunkLoaded(var1, var2);
            }
         });
      }
   }

   public void KOKz0(int var1) {
      if (mc.theWorld != null && this.isScanningActive()) {
         if (this.blockListFilter != null) {
            this.blockListFilter.zykH();
         }

         int var2 = var1;

         while (var2 > 0 && this.pendingChunkQueue.hasPending()) {
            ChunkKey var3 = this.pendingChunkQueue.xqcV();
            Chunk var4 = mc.theWorld.getChunkFromChunkCoords(var3.getChunkX(), var3.getChunkZ());
            if (var4 != null && !EmptyChunk.class.isInstance(var4)) {
               var2 -= this.uAe1(var4);
            }
         }
      }
   }

   public void onBlockChange(BlockPos var1, IBlockState var2) {
      if (this.blockListFilter != null) {
         this.blockListFilter.zykH();
      }

      BlockPos var3 = BlockIndexRecorder.indexBlock(var1, var2, this.blockListFilter, this.trackedBlockIndex, this.hasBlockListFilter(), lWh, this.bedPlateIndex, this.isBedPlateScanEnabled());
      BlockScannerNotifier.notifyBlockChanged(this.listeners, var3, var2);
   }

   public void Ziwu() {
      if (this.blockListFilter != null) {
         this.blockListFilter.zykH();
      }

      this.resetAndRescan();
   }

   private void resetAndRescan() {
      this.trackedBlockIndex.clearAll();
      this.pendingChunkQueue.clear();
      if (this.isScanningActive()) {
         this.queueLoadedChunks();
      }
   }

   public Iterable<Entry<Long, Set<BlockPos>>> getTrackedBlockEntries() {
      return this.trackedBlockIndex.getChunkEntries();
   }

   public Iterable<Entry<Long, Set<BlockPos>>> getBedPlateEntries() {
      return this.bedPlateIndex.getChunkEntries();
   }

   public int getTrackedBlockCount() {
      return this.trackedBlockIndex.getBlockCount();
   }

   public int getBedPlateCount() {
      return this.bedPlateIndex.getBlockCount();
   }

   public boolean isTracked(BlockPos var1) {
      return this.trackedBlockIndex.EXKuPk(var1);
   }

   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.isScanningActive()) {
         BlockChangeReader.dispatchBlockChange(var1.ys98(), new BlockScannerListener(this));
      }
   }

   private int uAe1(Chunk var1) {
      return ChunkIndexer.indexChunk(var1, this.blockListFilter, this.trackedBlockIndex, this.hasBlockListFilter(), lWh, this.bedPlateIndex, this.isBedPlateScanEnabled());
   }
}
