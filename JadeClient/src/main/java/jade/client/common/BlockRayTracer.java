// Jade recovery: original class: jade.deps.eLz.kFrsgwxL
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class BlockRayTracer {
   private static final int MAX_TRACE_STEPS = 201;

   private BlockRayTracer() {
   }

   public static MovingObjectPosition rayTraceBlocks(World var0, Vec3 var1, Vec3 var2, boolean var3, boolean var4) {
      if (var0 != null && !GNOm(var1) && !GNOm(var2)) {
         BlockRayTracer$1 var5 = new BlockRayTracer$1(var1);
         int var6 = MathHelper.floor_double(var2.xCoord);
         int var7 = MathHelper.floor_double(var2.yCoord);
         int var8 = MathHelper.floor_double(var2.zCoord);
         MovingObjectPosition var9 = traceCurrentBlock(var0, var5, var1, var2);
         if (iXrnu(var0, var9, var3, var4)) {
            return var9;
         } else {
            for (int var10 = 0; var10 < 201; var10++) {
               if (GNOm(BlockRayTracer$1.getRayPosition(var5))) {
                  return var9;
               }

               if (BlockRayTracer$1.isAtBlockPosition(var5, var6, var7, var8)) {
                  return var9;
               }

               BlockRayTracer$1.advanceRay(var5, var2, var6, var7, var8);
               MovingObjectPosition var11 = traceCurrentBlock(var0, var5, var1, var2);
               if (var11 != null) {
                  if (iXrnu(var0, var11, var3, var4)) {
                     return var11;
                  }

                  if (var9 == null) {
                     var9 = var11;
                  }
               }
            }

            return var9;
         }
      } else {
         return null;
      }
   }

   private static boolean GNOm(Vec3 var0) {
      return Double.isNaN(var0.xCoord) || Double.isNaN(var0.yCoord) || Double.isNaN(var0.zCoord);
   }

   private static MovingObjectPosition traceCurrentBlock(World var0, BlockRayTracer$1 var1, Vec3 var2, Vec3 var3) {
      BlockPos var4 = new BlockPos(BlockRayTracer$1.getBlockX(var1), BlockRayTracer$1.fhB1(var1), BlockRayTracer$1.HNvN(var1));
      IBlockState var5 = var0.getBlockState(var4);
      Block var6 = var5.getBlock();
      return var6.canCollideCheck(var5, false) ? var6.collisionRayTrace(var0, var4, var2, var3) : null;
   }

   private static boolean iXrnu(World var0, MovingObjectPosition var1, boolean var2, boolean var3) {
      if (var1 == null) {
         return false;
      } else {
         BlockPos var4 = var1.getBlockPos();
         boolean var5 = var0.getBlockState(var4).getBlock() instanceof BlockBed;
         return var2 && var5 || var3 && !var5 && BlockBoundsUtils.HEOdgN(var0, var4);
      }
   }
}
