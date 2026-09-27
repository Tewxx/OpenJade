// Jade recovery: original class: jade.deps.eLz.IrG0MnbXu
package jade.client.common;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange.BlockUpdateData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;

public final class BlockChangeReader {
   private BlockChangeReader() {
   }

   public static void dispatchBlockChange(Packet<?> var0, BlockChangeReader$0 var1) {
      if (var0 instanceof S23PacketBlockChange) {
         S23PacketBlockChange var9 = (S23PacketBlockChange)var0;
         var1.onBlockChanged(var9.getBlockPosition(), var9.getBlockState());
      } else if (var0 instanceof S22PacketMultiBlockChange) {
         S22PacketMultiBlockChange var8 = (S22PacketMultiBlockChange)var0;

         for (BlockUpdateData var6 : var8.getChangedBlocks()) {
            var1.onBlockChanged(var6.getPos(), var6.getBlockState());
         }
      } else if (var0 instanceof S21PacketChunkData) {
         S21PacketChunkData var7 = (S21PacketChunkData)var0;
         if (var7.getExtractedSize() == 0) {
            var1.onChunkUnloaded(var7.getChunkX(), var7.getChunkZ());
         } else {
            var1.onChunkLoaded(var7.getChunkX(), var7.getChunkZ());
         }
      } else {
         if (var0 instanceof S26PacketMapChunkBulk) {
            S26PacketMapChunkBulk var2 = (S26PacketMapChunkBulk)var0;

            for (int var3 = 0; var3 < var2.getChunkCount(); var3++) {
               var1.onChunkLoaded(var2.getChunkX(var3), var2.getChunkZ(var3));
            }
         }
      }
   }
}
