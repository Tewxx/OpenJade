// Jade recovery: original class: jade.deps.eLz.UhFBqmumF
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class BlockUtils implements IMinecraft {
   public static boolean amj2(BlockPos var0, BlockPos var1) {
      return FacingUtils.isSameBlockPos(var0, var1);
   }

   public static boolean isRightClickable(Block var0) {
      return BlockTypes.EuLv5(var0);
   }

   public static boolean isOpaqueFullBlock(Block var0) {
      return BlockTypes.ONLmva(var0);
   }

   public static BlockPos rLqn(double var0, double var2, double var4) {
      return new BlockPos(var0, var2, var4);
   }

   public static boolean isSamePosition(BlockPos var0, BlockPos var1) {
      return FacingUtils.isSameBlockPos(var0, var1);
   }

   public static BlockPos getOffsetBlockPos(MovingObjectPosition var0) {
      return var0.getBlockPos().offset(var0.sideHit);
   }

   public static boolean isLiquid(Block var0) {
      return BlockTypes.aIyuF(var0);
   }

   public static boolean isInteractiveBlock(Block var0) {
      return BlockTypes.DSLVc(var0);
   }

   public static boolean vctG(MovingObjectPosition var0) {
      return PlaceUtils.IBYK(mc, var0);
   }

   public static float getBreakSpeed(Block var0, ItemStack var1, boolean var2, boolean var3) {
      return BreakSpeed.computeBreakSpeed(var0, var1, mc.thePlayer, mc.theWorld, var2, var3);
   }

   public static float getBestHotbarBreakSpeed(Block var0, int var1) {
      return MaxBreakSpeedFinder.bPoca(var0, mc.thePlayer, mc.theWorld, var1);
   }

   public static float getToolSpeed(ItemStack var0, Block var1, boolean var2, boolean var3) {
      return BreakSpeed.QednSn(var0, var1, mc.thePlayer, var2, var3);
   }

   public static Block iepjdt(BlockPos var0) {
      return WorldUtils.getBlock(mc.theWorld, var0);
   }

   public static Block getBlockAtPosition(double var0, double var2, double var4) {
      return WorldUtils.getBlockAt(mc.theWorld, var0, var2, var4);
   }

   public static Block getBlockAtVector(Vec3 var0) {
      return WorldUtils.getBlockAtVec(mc.theWorld, var0);
   }

   public static IBlockState getBlockState(BlockPos var0) {
      return WorldUtils.getBlockState(mc.theWorld, var0);
   }

   public static AxisAlignedBB getSelectedBounds(BlockPos var0) {
      return WorldUtils.getSelectionBounds(mc.theWorld, var0);
   }

   public static AxisAlignedBB LZIP(BlockPos var0) {
      return WorldUtils.APOS(mc.theWorld, var0);
   }

   public static AxisAlignedBB getCollisionBoundsNullable(BlockPos var0) {
      return WorldUtils.getCollisionBounds(mc.theWorld, var0);
   }

   public static AxisAlignedBB getCombinedBounds(BlockPos var0, BlockPos var1) {
      AxisAlignedBB var2 = LZIP(var0);
      AxisAlignedBB var3 = LZIP(var1);
      return var2.union(var3);
   }

   public static EnumFacing getFacingTowardPoint(BlockPos var0, Vec3 var1) {
      return FacingUtils.YWmfo(var0, var1);
   }

   public static boolean nYnwtxu(BlockPos var0, Block var1) {
      return iepjdt(var0) == var1;
   }

   public static boolean isReplaceableAt(BlockPos var0) {
      return PlaceUtils.isBlockReplaceable(mc, var0);
   }

   public static boolean BEYsv(BlockPos var0, Vec3 var1, Vec3 var2) {
      return BlockSightChecker.hasLineOfSightToBlock(mc.theWorld, var0, var1, var2);
   }

   public static boolean hasClearLineOfSight(BlockPos var0) {
      Vec3 var1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      return BlockSightChecker.CbjZb(mc.theWorld, var0, var1);
   }

   public static EnumDyeColor getBlockColor(IBlockState var0) {
      return (EnumDyeColor)var0.getProperties().get(BlockColored.COLOR);
   }

   public static EnumFacing[] getClosestBlockFacings(Vec3 var0, BlockPos var1) {
      return FacingUtils.zauPl(var0, var1);
   }

   public static boolean containsFacing(EnumFacing[] var0, EnumFacing var1) {
      return FacingUtils.IuaL(var0, var1);
   }

   public static Vec3 sdlDajR(BlockPos var0, EnumFacing var1) {
      return FacingUtils.getFaceCenter(var0, var1);
   }

   public static double DKkIn(Vec3 var0, BlockPos var1) {
      return FacingUtils.getDistanceSqToBlock(var0, var1);
   }

   public static boolean canPlaceItemOnSide(ItemStack var0, BlockPos var1, EnumFacing var2) {
      return PlaceUtils.canPlaceItemOnSide(mc, var0, var1, var2);
   }

   public static float getBlockBreakTicks(Block var0) {
      return BreakSpeed.kXrgJ(var0, mc.theWorld);
   }

   public static boolean hasAdjacentAir(BlockPos var0, BlockPos... var1) {
      return BlockBoundsUtils.uegA(mc.theWorld, var0, var1);
   }

   public static boolean isNextToBed(BlockPos var0) {
      return BlockBoundsUtils.HEOdgN(mc.theWorld, var0);
   }

   public static MovingObjectPosition rayTraceBlocks(Vec3 var0, Vec3 var1, boolean var2, boolean var3) {
      return BlockRayTracer.rayTraceBlocks(mc.theWorld, var0, var1, var2, var3);
   }
}
