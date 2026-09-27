// Jade recovery: original class: jade.deps.eLz.aDvbTu6yl
package jade.client.module.render.trajectories;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public final class TrajectoryMath {
   private static final double EPSILON = 1.0E-7;

   private TrajectoryMath() {
   }

   public static double computeSegmentRatio(Vec3 var0, Vec3 var1, Vec3 var2) {
      if (var2 == null) {
         return 0.0;
      } else {
         double var3 = var0.squareDistanceTo(var1);
         if (var3 <= 1.0E-7) {
            return 0.0;
         } else {
            double var5 = Math.sqrt(var0.squareDistanceTo(var2) / var3);
            return Math.max(0.0, Math.min(var5, 1.0));
         }
      }
   }

   public static Vec3 lerpVec(Vec3 var0, Vec3 var1, double var2) {
      double var4 = var0.xCoord + (var1.xCoord - var0.xCoord) * var2;
      double var6 = var0.yCoord + (var1.yCoord - var0.yCoord) * var2;
      double var8 = var0.zCoord + (var1.zCoord - var0.zCoord) * var2;
      return new Vec3(var4, var6, var8);
   }

   public static AxisAlignedBB unionBoxes(AxisAlignedBB var0, AxisAlignedBB var1) {
      if (var0 == null) {
         return var1;
      } else if (var1 == null) {
         return var0;
      } else {
         double var2 = Math.min(var0.minX, var1.minX);
         double var4 = Math.min(var0.minY, var1.minY);
         double var6 = Math.min(var0.minZ, var1.minZ);
         double var8 = Math.max(var0.maxX, var1.maxX);
         double var10 = Math.max(var0.maxY, var1.maxY);
         double var12 = Math.max(var0.maxZ, var1.maxZ);
         return new AxisAlignedBB(var2, var4, var6, var8, var10, var12);
      }
   }

   public static Vec3 esYv(AxisAlignedBB var0) {
      return new Vec3(midpoint(var0.minX, var0.maxX), midpoint(var0.minY, var0.maxY), midpoint(var0.minZ, var0.maxZ));
   }

   public static double getCenterDistanceSquared(AxisAlignedBB var0, AxisAlignedBB var1) {
      Vec3 var2 = esYv(var0);
      Vec3 var3 = esYv(var1);
      return var2.squareDistanceTo(var3);
   }

   public static float normalizeRatio(double var0, double var2) {
      if (var0 >= var2) {
         return 1.0F;
      } else {
         return var0 <= 0.0 ? 0.0F : (float)(var0 / var2);
      }
   }

   public static float getAlphaFromColor(int var0) {
      return (var0 >>> 24 & 0xFF) / 255.0F;
   }

   private static double midpoint(double var0, double var2) {
      return (var0 + var2) * 0.5;
   }
}
