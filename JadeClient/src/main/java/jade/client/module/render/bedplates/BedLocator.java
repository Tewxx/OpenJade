// Jade recovery: original class: jade.deps.eLz.DVaFAb4
package jade.client.module.render.bedplates;

import jade.client.common.BlockScanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class BedLocator {
   private BedLocator() {
   }

   public static List<BlockPos[]> QBREh(BlockScanner var0, World var1, double var2, double var4, double var6, double var8) {
      ArrayList var10 = new ArrayList();

      for (Entry var12 : var0.getBedPlateEntries()) {
         for (BlockPos var14 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) ((Set)var12.getValue())) {
            double var15 = var14.getX() + 0.5;
            double var17 = var14.getY() + 0.5;
            double var19 = var14.getZ() + 0.5;
            double var21 = var15 - var2;
            double var23 = var17 - var4;
            double var25 = var19 - var6;
            double var27 = var21 * var21 + var23 * var23 + var25 * var25;
            if (var27 <= var8) {
               BlockPos[] var29 = opno(var1, var14);
               if (var29 != null && euy0(var1, var29)) {
                  var10.add(copyBedPositions(var29));
               }
            }
         }
      }

      return var10;
   }

   public static BlockPos[] copyBedPositions(BlockPos[] var0) {
      return new BlockPos[]{new BlockPos(var0[0]), new BlockPos(var0[1])};
   }

   public static boolean euy0(World var0, BlockPos[] var1) {
      return !tbkIz7(var0, var1) ? false : isBedState(var0.getBlockState(var1[0])) || isBedState(var0.getBlockState(var1[1]));
   }

   public static boolean hasFootPartFirst(World var0, BlockPos[] var1) {
      return !tbkIz7(var0, var1) ? false : sMkd(var0.getBlockState(var1[0])) && isBedState(var0.getBlockState(var1[1]));
   }

   public static AxisAlignedBB createBedBoundingBox(BlockPos var0, BlockPos var1, float var2) {
      int var3 = Math.min(var0.getX(), var1.getX());
      int var4 = Math.min(var0.getZ(), var1.getZ());
      int var5 = Math.max(var0.getX(), var1.getX()) + 1;
      int var6 = Math.max(var0.getZ(), var1.getZ()) + 1;
      return new AxisAlignedBB(var3, var0.getY(), var4, var5, var0.getY() + var2, var6);
   }

   private static BlockPos[] opno(World var0, BlockPos var1) {
      IBlockState var2 = var0.getBlockState(var1);
      if (!isBedState(var2)) {
         return null;
      } else {
         EnumFacing var3 = (EnumFacing)var2.getValue(BlockBed.FACING);
         return new BlockPos[]{var1, var1.offset(var3)};
      }
   }

   private static boolean tbkIz7(World var0, BlockPos[] var1) {
      return var0 != null && var1 != null && var1.length >= 2;
   }

   private static boolean isBedState(IBlockState var0) {
      return var0 != null && var0.getBlock() instanceof BlockBed;
   }

   private static boolean sMkd(IBlockState var0) {
      return isBedState(var0) && var0.getValue(BlockBed.PART) == EnumPartType.FOOT;
   }
}
