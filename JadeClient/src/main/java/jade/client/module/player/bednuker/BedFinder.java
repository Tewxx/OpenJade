// Jade recovery: original class: jade.deps.eLz.OiObtiwy
package jade.client.module.player.bednuker;

import jade.client.common.BlockUtils;
import jade.client.common.RotationUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class BedFinder {
   private final List<BedFinder$1> bedCandidates = new ArrayList<>();

   public void clearCandidates() {
      this.bedCandidates.clear();
   }

   public boolean isCandidateListEmpty() {
      return this.bedCandidates.isEmpty();
   }

   public void NNLvsH(World var1, EntityPlayer var2, double var3, float var5, BlockPos var6) {
      this.bedCandidates.clear();
      HashSet var7 = new HashSet();
      BlockPos var8 = new BlockPos(var2);
      int var9 = (int)Math.ceil(var3);

      for (int var10 = -var9; var10 <= var9; var10++) {
         for (int var11 = -var9; var11 <= var9; var11++) {
            for (int var12 = -var9; var12 <= var9; var12++) {
               BedFinder$1 var13 = OwN4(var1, var8.add(var10, var11, var12));
               if (var13 != null && !var7.contains(BedFinder$1.getFootPos(var13)) && isBedInReach(var13, var2, var3) && isWithinViewAngle(var13, var2, var5)) {
                  var7.add(BedFinder$1.getFootPos(var13));
                  this.bedCandidates.add(var13);
               }
            }
         }
      }

      this.removeNearestCandidate(var6);
   }

   public BreakTarget findBestReachableTarget(World var1, EntityPlayer var2, double var3, float var5, BlockPos var6, BedFinder$2 var7) {
      ArrayList var8 = new ArrayList();
      ArrayList var9 = new ArrayList();

      for (BedFinder$1 var11 : this.bedCandidates) {
         (hasAdjacentAir(var1, var11) ? var8 : var9).add(var11);
      }

      Comparator var12 = Comparator.comparingDouble((recoveredArg0) -> BedFinder.bedDistanceSq(var2, (jade.client.module.player.bednuker.BedFinder$1) recoveredArg0));
      Collections.sort(var8, var12);
      Collections.sort(var9, var12);
      BreakTarget var13 = selectBestTarget(var1, var2, var8, var3, var5, var6, var7);
      return var13 != null ? var13 : selectBestTarget(var1, var2, var9, var3, var5, var6, var7);
   }

   public BreakTarget findTargetAtPos(World var1, EntityPlayer var2, BlockPos var3, double var4, BedFinder$2 var6) {
      if (var3 == null) {
         return null;
      } else {
         for (BedFinder$1 var8 : this.bedCandidates) {
            for (BreakTarget var10 : collectBreakTargets(var1, var2, var8, var4, var6)) {
               if (var3.equals(var10.getBlockPos())) {
                  return var10;
               }
            }
         }

         return null;
      }
   }

   public BlockPos findAdjacentBedPart(BlockPos var1) {
      for (BedFinder$1 var3 : this.bedCandidates) {
         BlockPos[] var4 = new BlockPos[]{BedFinder$1.getFootPos(var3), BedFinder$1.getHeadPos(var3)};

         for (BlockPos var8 : var4) {
            for (EnumFacing var12 : EnumFacing.values()) {
               if (var1.equals(var8.offset(var12))) {
                  return var8;
               }
            }
         }
      }

      return null;
   }

   private static BedFinder$1 OwN4(World var0, BlockPos var1) {
      IBlockState var2 = var0.getBlockState(var1);
      if (!(var2.getBlock() instanceof BlockBed)) {
         return null;
      } else {
         EnumFacing var3 = (EnumFacing)var2.getValue(BlockBed.FACING);
         boolean var4 = var2.getValue(BlockBed.PART) == EnumPartType.FOOT;
         BlockPos var5 = var4 ? var1 : var1.offset(var3.getOpposite());
         IBlockState var6 = var0.getBlockState(var5);
         if (var6.getBlock() instanceof BlockBed && var6.getValue(BlockBed.PART) == EnumPartType.FOOT) {
            EnumFacing var7 = (EnumFacing)var6.getValue(BlockBed.FACING);
            BlockPos var8 = var5.offset(var7);
            IBlockState var9 = var0.getBlockState(var8);
            return var9.getBlock() instanceof BlockBed && var9.getValue(BlockBed.PART) == EnumPartType.HEAD && var9.getValue(BlockBed.FACING) == var7
               ? new BedFinder$1(var5, var8)
               : null;
         } else {
            return null;
         }
      }
   }

   private static boolean isBedInReach(BedFinder$1 var0, EntityPlayer var1, double var2) {
      Vec3 var4 = var1.getPositionEyes(1.0F);
      AxisAlignedBB var5 = computeBedBounds(var0);
      double var6 = var2 * var2 + 1.0E-4;
      return var4.squareDistanceTo(RotationUtils.ONLLQYf(var5, var4)) <= var6 ? true : var4.squareDistanceTo(computeBoxCenter(var5)) <= var6;
   }

   private static boolean isWithinViewAngle(BedFinder$1 var0, EntityPlayer var1, float var2) {
      if (var2 >= 360.0F) {
         return true;
      } else {
         Vec3 var3 = EAJUgMc(var0).subtract(var1.getPositionEyes(1.0F));
         double var4 = var3.lengthVector();
         if (var4 < 1.0E-6) {
            return true;
         } else {
            Vec3 var6 = var1.getLook(1.0F);
            double var7 = (var6.xCoord * var3.xCoord + var6.yCoord * var3.yCoord + var6.zCoord * var3.zCoord) / var4;
            double var9 = Math.acos(MathHelper.clamp_double(var7, -1.0, 1.0)) * 180.0 / Math.PI;
            return var9 <= var2 * 0.5;
         }
      }
   }

   private void removeNearestCandidate(BlockPos var1) {
      if (var1 != null && !this.bedCandidates.isEmpty()) {
         Vec3 var2 = new Vec3(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);
         BedFinder$1 var3 = null;
         double var4 = Double.POSITIVE_INFINITY;

         for (BedFinder$1 var7 : this.bedCandidates) {
            double var8 = var2.squareDistanceTo(EAJUgMc(var7));
            if (var8 < var4) {
               var4 = var8;
               var3 = var7;
            }
         }

         if (var3 != null) {
            this.bedCandidates.remove(var3);
         }
      }
   }

   private static BreakTarget selectBestTarget(World var0, EntityPlayer var1, List<BedFinder$1> var2, double var3, float var5, BlockPos var6, BedFinder$2 var7) {
      for (BedFinder$1 var9 : var2) {
         List var10 = collectBreakTargets(var0, var1, var9, var3, var7);
         if (!var10.isEmpty()) {
            BreakTarget var11 = null;
            double var12 = Double.POSITIVE_INFINITY;

            for (BreakTarget var15 : (java.lang.Iterable<BreakTarget>) (java.lang.Iterable<?>) (var10)) {
               double var16 = SRUR(var15, var1, var5, var6, var7);
               if (var16 < var12) {
                  var12 = var16;
                  var11 = var15;
               }
            }

            return var11;
         }
      }

      return null;
   }

   private static double SRUR(BreakTarget var0, EntityPlayer var1, float var2, BlockPos var3, BedFinder$2 var4) {
      float var5 = var4.getBreakSpeed(BlockUtils.iepjdt(var0.getBlockPos()));
      if (var5 <= 0.0F) {
         return Double.POSITIVE_INFINITY;
      } else {
         double var6 = 1.0 / var5;
         if (var2 > 0.02F && var0.getBlockPos().equals(var3)) {
            var6 -= var2 * 12.0;
         }

         return var6 + var1.getPositionEyes(1.0F).squareDistanceTo(var0.getHitVec()) * 0.002;
      }
   }

   private static List<BreakTarget> collectBreakTargets(World var0, EntityPlayer var1, BedFinder$1 var2, double var3, BedFinder$2 var5) {
      ArrayList var6 = new ArrayList();
      if (hasAdjacentAir(var0, var2)) {
         addBreakTargets(var6, var0, var1, BedFinder$1.getFootPos(var2), var3, var5);
         addBreakTargets(var6, var0, var1, BedFinder$1.getHeadPos(var2), var3, var5);
         return var6;
      } else {
         HashSet var7 = new HashSet();
         BlockPos[] var8 = new BlockPos[]{BedFinder$1.getFootPos(var2), BedFinder$1.getHeadPos(var2)};

         for (BlockPos var12 : var8) {
            for (EnumFacing var16 : EnumFacing.values()) {
               if (var16 != EnumFacing.DOWN) {
                  BlockPos var17 = var12.offset(var16);
                  if (!var7.contains(var17) && isBreakableBlock(var0, var17, var5)) {
                     var7.add(var17);
                     addBreakTargets(var6, var0, var1, var17, var3, var5);
                  }
               }
            }
         }

         return var6;
      }
   }

   private static boolean isBreakableBlock(World var0, BlockPos var1, BedFinder$2 var2) {
      Block var3 = var0.getBlockState(var1).getBlock();
      return var3 != Blocks.air && !(var3 instanceof BlockBed) && var3.getBlockHardness(var0, var1) >= 0.0F && var2.isBreakable(var3);
   }

   private static void addBreakTargets(List<BreakTarget> var0, World var1, EntityPlayer var2, BlockPos var3, double var4, BedFinder$2 var6) {
      Block var7 = var1.getBlockState(var3).getBlock();
      if (var7 != Blocks.air && !(var7.getBlockHardness(var1, var3) < 0.0F) && var6.isBreakable(var7)) {
         AxisAlignedBB var8 = BlockUtils.getSelectedBounds(var3);
         if (var8 != null) {
            Vec3 var9 = var2.getPositionEyes(1.0F);
            Vec3 var10 = RotationUtils.ONLLQYf(var8, var9);
            if (!(var9.squareDistanceTo(var10) > var4 + 0.001)) {
               Vec3 var11 = var10.addVector((var10.xCoord - var9.xCoord) * 0.01, (var10.yCoord - var9.yCoord) * 0.01, (var10.zCoord - var9.zCoord) * 0.01);
               MovingObjectPosition var12 = var7.collisionRayTrace(var1, var3, var9, var11);
               EnumFacing var13 = BlockUtils.getFacingTowardPoint(var3, var10);
               if (var12 != null && var12.hitVec != null && var12.sideHit != null && var3.equals(var12.getBlockPos())) {
                  var10 = var12.hitVec;
                  var13 = var12.sideHit;
               }

               if (!(var7 instanceof BlockBed) || var13 != EnumFacing.DOWN) {
                  var0.add(new BreakTarget(var3, var10, var13));
               }
            }
         }
      }
   }

   private static boolean hasAdjacentAir(World var0, BedFinder$1 var1) {
      BlockPos[] var2 = new BlockPos[]{BedFinder$1.getFootPos(var1), BedFinder$1.getHeadPos(var1)};

      for (BlockPos var6 : var2) {
         for (EnumFacing var10 : EnumFacing.values()) {
            if (var0.getBlockState(var6.offset(var10)).getBlock() == Blocks.air) {
               return true;
            }
         }
      }

      return false;
   }

   private static AxisAlignedBB computeBedBounds(BedFinder$1 var0) {
      return BlockUtils.getCombinedBounds(BedFinder$1.getFootPos(var0), BedFinder$1.getHeadPos(var0));
   }

   private static Vec3 EAJUgMc(BedFinder$1 var0) {
      return computeBoxCenter(computeBedBounds(var0));
   }

   private static Vec3 computeBoxCenter(AxisAlignedBB var0) {
      return new Vec3((var0.minX + var0.maxX) * 0.5, (var0.minY + var0.maxY) * 0.5, (var0.minZ + var0.maxZ) * 0.5);
   }

   private static double bedDistanceSq(EntityPlayer var0, BedFinder$1 var1) {
      return var0.getPositionEyes(1.0F).squareDistanceTo(EAJUgMc(var1));
   }
}
