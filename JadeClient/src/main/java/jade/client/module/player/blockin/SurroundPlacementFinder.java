// Jade recovery: original class: jade.deps.eLz.v6k1WOrmL
package jade.client.module.player.blockin;

import jade.client.common.BlockUtils;
import jade.client.common.RotationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class SurroundPlacementFinder {
   private static final EnumFacing[] HORIZONTAL_FACINGS;
   private static final SurroundPlacementFinder$2[] NEIGHBOR_OFFSETS;

   private SurroundPlacementFinder() {
   }

   public static PlacementTarget findSurroundPlacement(EntityPlayer var0, ItemStack var1, Vec3 var2, double var3) {
      BlockPos var5 = new BlockPos(MathHelper.floor_double(var0.posX), MathHelper.floor_double(var0.posY), MathHelper.floor_double(var0.posZ));
      BlockPos var6 = var5.up();
      Vec3 var7 = var0.getPositionEyes(1.0F);
      List var8 = getSurroundPositions(var5, var6);
      ArrayList var9 = new ArrayList(var8.size());

      for (BlockPos var11 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (var8)) {
         if (isUsablePlacementPos(var11, var5, var6)) {
            var9.add(var11);
         }
      }

      if (var9.isEmpty()) {
         return null;
      } else {
         if (var2 != null) {
            Collections.sort(var8, Comparator.comparingDouble((recoveredArg0) -> SurroundPlacementFinder.blockCenterDistanceSq(var2, (net.minecraft.util.BlockPos) recoveredArg0)));
         }

         List var12 = var2 == null ? null : var8;
         return CandidateSearch.findWithNeighborExpansion(var9, var12, (recoveredArg0) -> SurroundPlacementFinder.isValidPlacementPos(var5, var6, (net.minecraft.util.BlockPos) recoveredArg0), (recoveredArg0) -> SurroundPlacementFinder.findBestPlacement(var1, var7, var3, (java.util.List) recoveredArg0), SurroundPlacementFinder::getNeighbors, PlacementSpaceUtils::utUuoro, BlockPos::toLong);
      }
   }

   private static List<BlockPos> getSurroundPositions(BlockPos var0, BlockPos var1) {
      ArrayList var2 = new ArrayList(8);

      for (EnumFacing var6 : HORIZONTAL_FACINGS) {
         var2.add(var0.offset(var6));
         var2.add(var1.offset(var6));
      }

      return var2;
   }

   private static boolean isUsablePlacementPos(BlockPos var0, BlockPos var1, BlockPos var2) {
      return PlacementSpaceUtils.utUuoro(var0) && PlacementSpaceUtils.hasExposedNeighbor(var0, var1, var2);
   }

   private static double qxkoF(BlockPos var0, Vec3 var1) {
      double var2 = var0.getX() + 0.5 - var1.xCoord;
      double var4 = var0.getY() + 0.5 - var1.yCoord;
      double var6 = var0.getZ() + 0.5 - var1.zCoord;
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   private static Iterable<BlockPos> getNeighbors(BlockPos var0) {
      return () -> SurroundPlacementFinder.neighborIterator(var0);
   }

   private static PlacementTarget Vhw2(List<BlockPos> var0, ItemStack var1, Vec3 var2, double var3) {
      if (var0 != null && !var0.isEmpty()) {
         float var5 = RotationUtils.lastSentRotation[0];
         float var6 = RotationUtils.lastSentRotation[1];
         MovingObjectPosition var7 = RotationUtils.traceBlockHit(var3, var5, var6);
         if (var7 != null && !PlacementSpaceUtils.utUuoro(var7.getBlockPos()) && BlockUtils.canPlaceItemOnSide(var1, var7.getBlockPos(), var7.sideHit)) {
            for (BlockPos var9 : var0) {
               PlacementTarget var10 = PlacementValidator.validateRaytrace(var3, var5, var6, var7.getBlockPos(), var7.sideHit, var9);
               if (var10 != null) {
                  return var10;
               }
            }
         }

         ArrayList var12 = new ArrayList(Math.max(16, var0.size() * 216));

         for (BlockPos var15 : var0) {
            collectPlacementCandidates(var12, var15, var1, var2, var5, var6);
         }

         Collections.sort(var12, Comparator.comparingDouble(SurroundPlacementFinder::getCandidateScore));

         for (SurroundPlacementFinder$1 var16 : (java.lang.Iterable<SurroundPlacementFinder$1>) (java.lang.Iterable<?>) (var12)) {
            PlacementTarget var11 = PlacementValidator.validateRaytrace(
               var3, SurroundPlacementFinder$1.getYaw(var16), SurroundPlacementFinder$1.getPitch(var16), SurroundPlacementFinder$1.getClickedPos(var16), SurroundPlacementFinder$1.YvqqU(var16), SurroundPlacementFinder$1.getPlacementPos(var16)
            );
            if (var11 != null) {
               return var11;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static void collectPlacementCandidates(List<SurroundPlacementFinder$1> var0, BlockPos var1, ItemStack var2, Vec3 var3, float var4, float var5) {
      for (SurroundPlacementFinder$2 var9 : NEIGHBOR_OFFSETS) {
         BlockPos var10 = var1.add(SurroundPlacementFinder$2.getOffsetX(var9), SurroundPlacementFinder$2.getOffsetY(var9), SurroundPlacementFinder$2.getOffsetZ(var9));
         if (!PlacementSpaceUtils.utUuoro(var10) && BlockUtils.canPlaceItemOnSide(var2, var10, SurroundPlacementFinder$2.getClickSide(var9))) {
            double var11 = var10.getX();
            double var13 = var10.getY();
            double var15 = var10.getZ();
            AimPointGrid.forEachJitteredPoint(true, Math::random, (recoveredArg0, recoveredArg1) -> SurroundPlacementFinder.addJitteredCandidate(var11, var13, var15, var9, var3, var4, var5, var0, var10, var1, recoveredArg0, recoveredArg1));
         }
      }
   }

   private static void addJitteredCandidate(
      double var0,
      double var2,
      double var4,
      SurroundPlacementFinder$2 var6,
      Vec3 var7,
      float var8,
      float var9,
      List var10,
      BlockPos var11,
      BlockPos var12,
      double var13,
      double var15
   ) {
      double[] var17 = SelfPlacementUtils.computeFaceHitPoint(var0, var2, var4, SurroundPlacementFinder$2.getOffsetX(var6), SurroundPlacementFinder$2.getOffsetY(var6), SurroundPlacementFinder$2.getOffsetZ(var6), var13, var15);
      float[] var18 = RotationUtils.anglesFromPointToVec(var7, var17[0], var17[1], var17[2]);
      float var19 = Math.abs(MathHelper.wrapAngleTo180_float(var18[0] - var8));
      float var20 = Math.abs(var18[1] - var9);
      if (var19 >= 0.1F || var20 >= 0.1F) {
         var10.add(new SurroundPlacementFinder$1(var19 + var20, var18[0], var18[1], var11, SurroundPlacementFinder$2.getClickSide(var6), var12));
      }
   }

   private static double getCandidateScore(SurroundPlacementFinder$1 var0) {
      return SurroundPlacementFinder$1.getScore(var0);
   }

   private static Iterator neighborIterator(final BlockPos var0) {
      return new Iterator<BlockPos>() {
         private final EnumFacing[] aXl = EnumFacing.values();
         private int facingIndex;

         @Override
         public boolean hasNext() {
            return this.facingIndex < this.aXl.length;
         }

         public BlockPos next() {
            return var0.offset(this.aXl[this.facingIndex++]);
         }
      };
   }

   private static PlacementTarget findBestPlacement(ItemStack var0, Vec3 var1, double var2, List var4) {
      return Vhw2(var4, var0, var1, var2);
   }

   private static boolean isValidPlacementPos(BlockPos var0, BlockPos var1, BlockPos var2) {
      return isUsablePlacementPos(var2, var0, var1);
   }

   private static double blockCenterDistanceSq(Vec3 var0, BlockPos var1) {
      return qxkoF(var1, var0);
   }

   static {
      EnumFacing[] var10000 = new EnumFacing[]{EnumFacing.EAST, null, null, null};
      var10000[1] = EnumFacing.SOUTH;
      var10000[2] = EnumFacing.WEST;
      var10000[3] = EnumFacing.NORTH;
      HORIZONTAL_FACINGS = var10000;
      SurroundPlacementFinder$2[] var0 = new SurroundPlacementFinder$2[6];
      var0[0] = new SurroundPlacementFinder$2(
         0,
         1,
         0,
         EnumFacing.DOWN
      );
      var0[1] = new SurroundPlacementFinder$2(
         0,
         -1,
         0,
         EnumFacing.UP
      );
      var0[2] = new SurroundPlacementFinder$2(
         0,
         0,
         -1,
         EnumFacing.NORTH
      );
      var0[3] = new SurroundPlacementFinder$2(
         0,
         0,
         1,
         EnumFacing.SOUTH
      );
      var0[4] = new SurroundPlacementFinder$2(
         1, 0, 0, EnumFacing.EAST
      );
      var0[5] = new SurroundPlacementFinder$2(
         -1,
         0,
         0,
         EnumFacing.WEST
      );
      NEIGHBOR_OFFSETS = var0;
   }
}
