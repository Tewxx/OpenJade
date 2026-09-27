// Jade recovery: original class: jade.deps.eLz.aLeWwf
package jade.client.module.player.scaffold;

import java.util.List;
import java.util.function.BiFunction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class PlaceRotations {
   private PlaceRotations() {
   }

   public static float[] lLwku(
      BlockPos var0,
      EnumFacing var1,
      List<Vec3> var2,
      Vec3 var3,
      Vec3 var4,
      Vec3 var5,
      float var6,
      float var7,
      float var8,
      BiFunction<Vec3, Vec3, float[]> var9,
      PlaceRotations$0 var10
   ) {
      if (isTargetHitByReportedRotation(var0, var1, var3, var4, var6, var7, var6, var7, var10)) {
         return new float[]{var6, var7};
      } else {
         float[] var11 = BIutv(var0, var1, var2, var3, var8, var9, var10);
         if (var11 != null && isTargetHitByReportedRotation(var0, var1, var3, var4, var6, var7, var11[0], var11[1], var10)) {
            return var11;
         } else {
            Vec3 var12 = new Vec3(var0.getX() + 0.5, var0.getY() + 1.0, var0.getZ() + 0.5);
            float[] var13 = snapRotationTo45Degrees(var5, (float[])var9.apply(var5, var12), var8, var9);
            if (didHitBlock(var0, var10.rayTrace(var5, var13[0], var13[1]))) {
               return var13;
            } else {
               float[] var14 = BIutv(var0, var1, var2, var5, var8, var9, var10);
               return var14 != null ? var14 : var11;
            }
         }
      }
   }

   public static boolean isTargetHitByReportedRotation(BlockPos var0, EnumFacing var1, Vec3 var2, Vec3 var3, float var4, float var5, float var6, float var7, PlaceRotations$0 var8) {
      return didHitBlock(var0, var8.rayTrace(var3, var4, var5)) && hitsTargetBlockOnSide(var0, var1, var8.rayTrace(var3, var6, var7)) && hitsTargetBlockOnSide(var0, var1, var8.rayTrace(var2, var6, var7));
   }

   private static float[] BIutv(BlockPos var0, EnumFacing var1, List<Vec3> var2, Vec3 var3, float var4, BiFunction<Vec3, Vec3, float[]> var5, PlaceRotations$0 var6) {
      Vec3 var7 = new Vec3(
         var0.getX() + 0.5 + var1.getFrontOffsetX() * 0.5, var0.getY() + 0.5 + var1.getFrontOffsetY() * 0.5, var0.getZ() + 0.5 + var1.getFrontOffsetZ() * 0.5
      );
      double var8 = (var3.xCoord - var7.xCoord) * var1.getFrontOffsetX()
         + (var3.yCoord - var7.yCoord) * var1.getFrontOffsetY()
         + (var3.zCoord - var7.zCoord) * var1.getFrontOffsetZ();
      if (var8 <= 1.0E-6) {
         return null;
      } else {
         float[] var10 = snapRotationTo45Degrees(var3, (float[])var5.apply(var3, var7), var4, var5);
         MovingObjectPosition var11 = var6.rayTrace(var3, var10[0], var10[1]);
         if (hitsTargetBlockOnSide(var0, var1, var11) && var11.hitVec.squareDistanceTo(var7) < 0.0025) {
            return var10;
         } else {
            float[] var12 = null;
            boolean var13 = false;
            double var14 = Double.MAX_VALUE;

            for (Vec3 var17 : var2) {
               float[] var18 = (float[])var5.apply(var3, var17);
               float[] var19 = snapRotationTo45Degrees(var3, var18, var4, var5);

               for (int var20 = 0; var20 < 2; var20++) {
                  float[] var21 = var20 == 0 ? var19 : var18;
                  MovingObjectPosition var22 = var6.rayTrace(var3, var21[0], var21[1]);
                  if (hitsTargetBlockOnSide(var0, var1, var22)) {
                     boolean var23 = var20 == 0;
                     double var24 = var22.hitVec.squareDistanceTo(var7);
                     if (var12 == null || var23 && !var13 || var23 == var13 && var24 < var14) {
                        var12 = var21;
                        var13 = var23;
                        var14 = var24;
                     }
                  }
               }
            }

            return var12;
         }
      }
   }

   private static float[] snapRotationTo45Degrees(Vec3 var0, float[] var1, float var2, BiFunction<Vec3, Vec3, float[]> var3) {
      double var4 = Math.toRadians(var2 + Math.round((var1[0] - var2) / 45.0F) * 45.0F);
      double var6 = Math.toRadians(var1[1]);
      return (float[])var3.apply(var0, var0.addVector(-Math.sin(var4) * Math.cos(var6), -Math.sin(var6), Math.cos(var4) * Math.cos(var6)));
   }

   private static boolean hitsTargetBlockOnSide(BlockPos var0, EnumFacing var1, MovingObjectPosition var2) {
      return didHitBlock(var0, var2) && var1 == var2.sideHit;
   }

   private static boolean didHitBlock(BlockPos var0, MovingObjectPosition var1) {
      return var1 != null && var1.typeOfHit == MovingObjectType.BLOCK && var0.equals(var1.getBlockPos());
   }
}
