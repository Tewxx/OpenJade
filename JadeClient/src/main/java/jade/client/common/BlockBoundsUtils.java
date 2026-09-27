// Jade recovery: original class: jade.deps.eLz.HfVLWb
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class BlockBoundsUtils {
   private BlockBoundsUtils() {
   }

   public static IBlockState getBlockStateSafe(World var0, BlockPos var1) {
      return var0 != null && var1 != null ? var0.getBlockState(var1) : Blocks.air.getDefaultState();
   }

   public static AxisAlignedBB sBdmu(World var0, BlockPos var1) {
      if (var0 != null && var1 != null) {
         IBlockState var2 = var0.getBlockState(var1);
         Block var3 = var2.getBlock();
         var3.setBlockBoundsBasedOnState(var0, var1);
         AxisAlignedBB var4 = var3.getSelectedBoundingBox(var0, var1);
         return var4 == null ? getFullCubeBounds(var1) : var4;
      } else {
         return null;
      }
   }

   public static AxisAlignedBB getCollisionBounds(World var0, BlockPos var1, boolean var2) {
      if (var0 != null && var1 != null) {
         IBlockState var3 = var0.getBlockState(var1);
         Block var4 = var3.getBlock();
         AxisAlignedBB var5 = var4.getCollisionBoundingBox(var0, var1, var3);
         if (var5 == null) {
            var5 = var4.getSelectedBoundingBox(var0, var1);
         }

         return var5 == null && var2 ? getFullCubeBounds(var1) : var5;
      } else {
         return null;
      }
   }

   public static boolean uegA(World var0, BlockPos var1, BlockPos... var2) {
      for (EnumFacing var6 : EnumFacing.values()) {
         BlockPos var7 = var1.offset(var6);
         if (var0.getBlockState(var7).getBlock() == Blocks.air && !HOmyS(var7, var2)) {
            return true;
         }
      }

      return false;
   }

   private static boolean HOmyS(BlockPos var0, BlockPos[] var1) {
      for (BlockPos var5 : var1) {
         if (var0.equals(var5)) {
            return true;
         }
      }

      return false;
   }

   public static boolean HEOdgN(World var0, BlockPos var1) {
      for (EnumFacing var5 : EnumFacing.values()) {
         if (var0.getBlockState(var1.offset(var5)).getBlock() instanceof BlockBed) {
            return true;
         }
      }

      return false;
   }

   private static AxisAlignedBB getFullCubeBounds(BlockPos var0) {
      return new AxisAlignedBB(var0.getX(), var0.getY(), var0.getZ(), var0.getX() + 1.0, var0.getY() + 1.0, var0.getZ() + 1.0);
   }
}
