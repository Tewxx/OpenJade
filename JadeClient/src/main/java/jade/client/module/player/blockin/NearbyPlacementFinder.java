// Jade recovery: original class: jade.deps.eLz.g4krmTtk
package jade.client.module.player.blockin;

import jade.client.common.BlockUtils;
import jade.client.common.RotationUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class NearbyPlacementFinder {
   private static final double FACE_INSET_MARGIN = 0.05;

   private NearbyPlacementFinder() {
   }

   public static PlacementTarget findNearbyPlacement(EntityPlayer var0, ItemStack var1, double var2) {
      Vec3 var4 = new Vec3(var0.posX, var0.posY, var0.posZ);
      BlockPos var5 = new BlockPos(MathHelper.floor_double(var4.xCoord), MathHelper.floor_double(var4.yCoord) + 2, MathHelper.floor_double(var4.zCoord));
      if (!PlacementSpaceUtils.utUuoro(var5)) {
         return null;
      } else {
         Vec3 var6 = new Vec3(var4.xCoord, var4.yCoord + var0.getEyeHeight(), var4.zCoord);
         int var7 = MathHelper.floor_double(var6.yCoord) + 1;

         for (NearbyPlacementFinder$2 var10 : collectCandidateBlocks(var6, var2, var7)) {
            PlacementTarget var11 = findPlacementRotation(var1, NearbyPlacementFinder$2.getBlockPos(var10), var6, var2, var7);
            if (var11 != null) {
               return var11;
            }
         }

         return null;
      }
   }

   private static List<NearbyPlacementFinder$2> collectCandidateBlocks(Vec3 var0, double var1, int var3) {
      int var4 = MathHelper.floor_double(var0.yCoord + var1);
      int var5 = MathHelper.floor_double(var0.xCoord - var1);
      int var6 = MathHelper.floor_double(var0.xCoord + var1);
      int var7 = MathHelper.floor_double(var0.zCoord - var1);
      int var8 = MathHelper.floor_double(var0.zCoord + var1);
      double var9 = (var1 + 1.0) * (var1 + 1.0);
      double var11 = var1 * var1;
      ArrayList var13 = new ArrayList();

      for (int var14 = var3; var14 <= var4; var14++) {
         for (int var15 = var5; var15 <= var6; var15++) {
            for (int var16 = var7; var16 <= var8; var16++) {
               double var17 = var15 + 0.5 - var0.xCoord;
               double var19 = var14 + 0.5 - var0.yCoord;
               double var21 = var16 + 0.5 - var0.zCoord;
               if (!(var17 * var17 + var19 * var19 + var21 * var21 > var9)) {
                  BlockPos var23 = new BlockPos(var15, var14, var16);
                  if (UZfX(var23)) {
                     double var24 = BlockUtils.DKkIn(var0, var23);
                     if (var24 <= var11) {
                        var13.add(new NearbyPlacementFinder$2(var23, var24));
                     }
                  }
               }
            }
         }
      }

      Collections.sort(var13, Comparator.comparingDouble(NearbyPlacementFinder::yNeykJ));
      return var13;
   }

   private static boolean UZfX(BlockPos var0) {
      if (PlacementSpaceUtils.utUuoro(var0)) {
         return false;
      } else {
         Block var1 = BlockUtils.iepjdt(var0);
         return !BlockUtils.isInteractiveBlock(var1) && !(var1 instanceof BlockFence) && !(var1 instanceof BlockWall);
      }
   }

   private static PlacementTarget findPlacementRotation(ItemStack var0, BlockPos var1, Vec3 var2, double var3, int var5) {
      float var6 = RotationUtils.lastSentRotation[0];
      float var7 = RotationUtils.lastSentRotation[1];
      double var8 = var1.getX();
      double var10 = var1.getY();
      double var12 = var1.getZ();
      boolean var14 = Math.abs(var2.yCoord - (var10 + 1.0)) < Math.abs(var2.yCoord - var10);
      boolean var15 = Math.abs(var2.zCoord - (var12 + 1.0)) < Math.abs(var2.zCoord - var12);
      boolean var16 = Math.abs(var2.xCoord - (var8 + 1.0)) < Math.abs(var2.xCoord - var8);
      ArrayList var17 = new ArrayList(109);
      var17.add(new NearbyPlacementFinder$1(0.0, var6, var7));
      AimPointGrid.forEachJitteredPoint(false, Math::random, (recoveredArg0, recoveredArg1) -> NearbyPlacementFinder.addFaceAimCandidates(var17, var2, var8, var14, var10, var12, var6, var7, var15, var16, recoveredArg0, recoveredArg1));
      Collections.sort(var17, Comparator.comparingDouble(NearbyPlacementFinder::getCandidateScore));

      for (NearbyPlacementFinder$1 var19 : (java.lang.Iterable<NearbyPlacementFinder$1>) (java.lang.Iterable<?>) (var17)) {
         MovingObjectPosition var20 = RotationUtils.traceBlockHit(var3, NearbyPlacementFinder$1.getYaw(var19), NearbyPlacementFinder$1.getPitch(var19));
         if (var20 != null && var1.equals(var20.getBlockPos())) {
            EnumFacing var21 = var20.sideHit;
            if (var1.getY() >= var5 && (var21 != EnumFacing.DOWN || var1.getY() != var5) && BlockUtils.canPlaceItemOnSide(var0, var1, var21)) {
               return new PlacementTarget(var1, var21, NearbyPlacementFinder$1.getYaw(var19), NearbyPlacementFinder$1.getPitch(var19));
            }
         }
      }

      return null;
   }

   private static void hfJn(List<NearbyPlacementFinder$1> var0, Vec3 var1, double var2, double var4, double var6, float var8, float var9) {
      float[] var10 = RotationUtils.anglesFromPointToVec(var1, var2, var4, var6);
      double var11 = Math.abs(MathHelper.wrapAngleTo180_float(var10[0] - var8)) + Math.abs(var10[1] - var9);
      var0.add(new NearbyPlacementFinder$1(var11, var10[0], var10[1]));
   }

   private static double getCandidateScore(NearbyPlacementFinder$1 var0) {
      return NearbyPlacementFinder$1.MLGXiE(var0);
   }

   private static void addFaceAimCandidates(
      List var0,
      Vec3 var1,
      double var2,
      boolean var4,
      double var5,
      double var7,
      float var9,
      float var10,
      boolean var11,
      boolean var12,
      double var13,
      double var15
   ) {
      hfJn(var0, var1, var2 + var13, var4 ? var5 + 1.0 - 0.05 : var5 + 0.05, var7 + var15, var9, var10);
      hfJn(var0, var1, var2 + var13, var5 + var15, var11 ? var7 + 1.0 - 0.05 : var7 + 0.05, var9, var10);
      hfJn(var0, var1, var12 ? var2 + 1.0 - 0.05 : var2 + 0.05, var5 + var15, var7 + var13, var9, var10);
   }

   private static double yNeykJ(NearbyPlacementFinder$2 var0) {
      return NearbyPlacementFinder$2.getDistanceSq(var0);
   }
}
