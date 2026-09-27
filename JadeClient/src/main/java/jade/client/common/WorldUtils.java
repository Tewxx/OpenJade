// Jade recovery: original class: jade.deps.eLz.G1ZB14
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class WorldUtils {
   private WorldUtils() {
   }

   public static Block getBlock(World var0, BlockPos var1) {
      return getBlockState(var0, var1).getBlock();
   }

   public static Block getBlockAt(World var0, double var1, double var3, double var5) {
      return getBlock(var0, new BlockPos(var1, var3, var5));
   }

   public static Block getBlockAtVec(World var0, Vec3 var1) {
      return getBlockAt(var0, var1.xCoord, var1.yCoord, var1.zCoord);
   }

   public static IBlockState getBlockState(World var0, BlockPos var1) {
      return BlockBoundsUtils.getBlockStateSafe(var0, var1);
   }

   public static AxisAlignedBB getSelectionBounds(World var0, BlockPos var1) {
      return BlockBoundsUtils.sBdmu(var0, var1);
   }

   public static AxisAlignedBB APOS(World var0, BlockPos var1) {
      return BlockBoundsUtils.getCollisionBounds(var0, var1, true);
   }

   public static AxisAlignedBB getCollisionBounds(World var0, BlockPos var1) {
      return BlockBoundsUtils.getCollisionBounds(var0, var1, false);
   }
}
