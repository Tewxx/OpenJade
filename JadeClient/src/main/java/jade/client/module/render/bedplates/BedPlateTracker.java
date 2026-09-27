// Jade recovery: original class: jade.deps.eLz.JFhiHaz
package jade.client.module.render.bedplates;

import jade.client.common.BlockScanner$1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public final class BedPlateTracker implements BlockScanner$1 {
   private final Map<Long, BedPlateCache> BYcW = new HashMap<>();
   private final Map<Long, BedPlateTracker$1> EHn = new HashMap<>();
   private final Map<Long, Set<Long>> XOGJcK = new HashMap<>();
   private final Map<Long, Set<Long>> chunkToTrackedBeds = new HashMap<>();
   private final Set<Long> yjl = new HashSet<>();

   @Override
   public void onBlockChanged(BlockPos var1, IBlockState var2) {
      this.markDirty(this.XOGJcK.get(var1 == null ? null : var1.toLong()));
   }

   @Override
   public void onChunkLoaded(int var1, int var2) {
      this.markDirty(this.chunkToTrackedBeds.get(chunkKey(var1, var2)));
   }

   @Override
   public void onChunkUnloaded(int var1, int var2) {
      this.markDirty(this.chunkToTrackedBeds.get(chunkKey(var1, var2)));
   }

   @Override
   public void cKj73() {
      this.VPpl();
   }

   public void WUpzQ(Minecraft var1, List<BlockPos[]> var2) {
      HashSet var3 = new HashSet();

      for (BlockPos[] var5 : var2) {
         BlockPos var6 = var5[0];
         BlockPos var7 = var5[1];
         long var8 = var6.toLong();
         var3.add(var8);
         BedPlateTracker$1 var10 = this.EHn.get(var8);
         if (var10 == null || BedPlateTracker$1.getPartnerPosLong(var10) != var7.toLong()) {
            this.unregisterBed(var8);
            this.registerBed(var6, var7);
            this.yjl.add(var8);
         }

         BedPlateCache var11 = this.BYcW.get(var8);
         if (var11 == null || !var11.matchesBedPos(var7) || this.yjl.remove(var8)) {
            this.BYcW.put(var8, BedPlateScanner.scanBedArea(var1, var1.theWorld, var6, var7));
         }
      }

      for (Long var13 : new ArrayList<>(this.EHn.keySet())) {
         if (!var3.contains(var13)) {
            this.unregisterBed(var13);
         }
      }

      this.yjl.retainAll(var3);
   }

   public BedPlateCache getCache(BlockPos var1, BlockPos var2) {
      BedPlateCache var3 = var1 == null ? null : this.BYcW.get(var1.toLong());
      return var3 != null && var3.matchesBedPos(var2) ? var3 : null;
   }

   public void VPpl() {
      this.BYcW.clear();
      this.EHn.clear();
      this.XOGJcK.clear();
      this.chunkToTrackedBeds.clear();
      this.yjl.clear();
   }

   private void registerBed(BlockPos var1, BlockPos var2) {
      long var3 = var1.toLong();
      HashSet var5 = new HashSet();
      HashSet var6 = new HashSet();

      for (BlockPos var8 : BedPlateShapes.XAwJ3(var1, var2)) {
         long var9 = var8.toLong();
         if (var5.add(var9)) {
            getOrCreateIndexSet(this.XOGJcK, var9).add(var3);
            var6.add(chunkKey(var8.getX() >> 4, var8.getZ() >> 4));
         }
      }

      for (Long var12 : (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (var6)) {
         getOrCreateIndexSet(this.chunkToTrackedBeds, var12).add(var3);
      }

      this.EHn.put(var3, new BedPlateTracker$1(var2.toLong(), Collections.unmodifiableSet(var5), Collections.unmodifiableSet(var6)));
   }

   private void unregisterBed(long var1) {
      BedPlateTracker$1 var3 = this.EHn.remove(var1);
      this.BYcW.remove(var1);
      this.yjl.remove(var1);
      if (var3 != null) {
         detachFromIndex(this.XOGJcK, BedPlateTracker$1.getCoveredPositions(var3), var1);
         detachFromIndex(this.chunkToTrackedBeds, BedPlateTracker$1.VRzuj(var3), var1);
      }
   }

   private void markDirty(Set<Long> var1) {
      if (var1 != null) {
         this.yjl.addAll(var1);
      }
   }

   private static Set<Long> getOrCreateIndexSet(Map<Long, Set<Long>> var0, long var1) {
      Set<Long> var3 = var0.get(var1);
      if (var3 == null) {
         var3 = new HashSet();
         var0.put(var1, var3);
      }

      return (Set<Long>)var3;
   }

   private static void detachFromIndex(Map<Long, Set<Long>> var0, Set<Long> var1, long var2) {
      for (Long var5 : var1) {
         Set var6 = (Set)var0.get(var5);
         if (var6 != null) {
            var6.remove(var2);
            if (var6.isEmpty()) {
               var0.remove(var5);
            }
         }
      }
   }

   private static long chunkKey(int var0, int var1) {
      return (long)var0 << 32 ^ var1 & 4294967295L;
   }
}
