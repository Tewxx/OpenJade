// Jade recovery: original class: jade.deps.eLz.zDG81JP
package jade.client.module.player.bridgeassist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public final class EdgePlacementSolver {
   private static final EnumFacing[] HORIZONTAL_FACINGS;
   private static final float y28 = 60.0F;
   private static final float MAX_PITCH_DEGREES = 90.0F;

   private EdgePlacementSolver() {
   }

   public static EdgePlacementSolver$2 findPlacementAngles(AxisAlignedBB var0, float var1, float var2, double var3, EdgePlacementSolver$1 var5, EdgePlacementSolver$0 var6, DoubleSupplier var7) {
      List var8 = STYwu(var0, var5);
      if (var8.isEmpty()) {
         return null;
      } else {
         EdgePlacementSolver$2 var9 = null;
         float var10 = Float.POSITIVE_INFINITY;
         float var11 = 60.0F;

         while (var11 <= 90.0F) {
            var11 += randomPitchStep(var7.getAsDouble());
            float var12 = Math.min(var11, 90.0F);
            MovingObjectPosition var13 = var6.JPZd(var3, var1, var12);
            if (var13 != null && isHorizontalFacing(var13.sideHit) && containsCandidate(var8, var13.getBlockPos(), var13.sideHit)) {
               float var14 = Math.abs(var12 - var2);
               if (var14 < var10) {
                  var10 = var14;
                  var9 = new EdgePlacementSolver$2(var1, var12);
               }
            }

            if (var11 >= 90.0F) {
               break;
            }
         }

         return var9;
      }
   }

   private static List<EdgePlacementSolver$3> STYwu(AxisAlignedBB var0, EdgePlacementSolver$1 var1) {
      int var2 = MathHelper.floor_double(var0.minY) - 1;
      int var3 = MathHelper.floor_double(var0.minX);
      int var4 = MathHelper.floor_double(var0.maxX);
      int var5 = MathHelper.floor_double(var0.minZ);
      int var6 = MathHelper.floor_double(var0.maxZ);
      ArrayList var7 = new ArrayList();

      for (int var8 = var3; var8 <= var4; var8++) {
         for (int var9 = var5; var9 <= var6; var9++) {
            BlockPos var10 = new BlockPos(var8, var2, var9);
            if (!var1.isReplaceable(var10)) {
               for (EnumFacing var14 : HORIZONTAL_FACINGS) {
                  if (var1.isReplaceable(var10.offset(var14))) {
                     var7.add(new EdgePlacementSolver$3(var10, var14));
                  }
               }
            }
         }
      }

      return var7;
   }

   private static float randomPitchStep(double var0) {
      float var2 = 1.0F + (float)(var0 * 2.0 - 1.0) * 0.38F;
      return Math.max(0.4F, Math.min(1.8F, var2));
   }

   private static boolean isHorizontalFacing(EnumFacing var0) {
      return var0 != null && var0 != EnumFacing.UP && var0 != EnumFacing.DOWN;
   }

   private static boolean containsCandidate(List<EdgePlacementSolver$3> var0, BlockPos var1, EnumFacing var2) {
      for (EdgePlacementSolver$3 var4 : var0) {
         if (var4.blockPos.equals(var1) && var4.enumFacing == var2) {
            return true;
         }
      }

      return false;
   }

   static {
      EnumFacing[] var10000 = new EnumFacing[4];
      var10000[0] = EnumFacing.NORTH;
      var10000[1] = EnumFacing.SOUTH;
      var10000[2] = EnumFacing.EAST;
      var10000[3] = EnumFacing.WEST;
      HORIZONTAL_FACINGS = var10000;
   }
}
