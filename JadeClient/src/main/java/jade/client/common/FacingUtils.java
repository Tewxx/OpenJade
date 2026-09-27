// Jade recovery: original class: jade.deps.eLz.GlYVV5
package jade.client.common;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class FacingUtils {
   private static final double FACE_INSET = 0.001;

   private FacingUtils() {
   }

   public static boolean isSameBlockPos(BlockPos var0, BlockPos var1) {
      return var0 == var1 || var0.getX() == var1.getX() && var0.getY() == var1.getY() && var0.getZ() == var1.getZ();
   }

   public static EnumFacing YWmfo(BlockPos var0, Vec3 var1) {
      double var2 = var1.xCoord - (var0.getX() + 0.5);
      double var4 = var1.yCoord - (var0.getY() + 0.5);
      double var6 = var1.zCoord - (var0.getZ() + 0.5);
      double var8 = Math.abs(var2);
      double var10 = Math.abs(var4);
      double var12 = Math.abs(var6);
      if (var8 > var10 && var8 > var12) {
         return var2 > 0.0 ? EnumFacing.EAST : EnumFacing.WEST;
      } else if (var10 > var12) {
         return var4 > 0.0 ? EnumFacing.UP : EnumFacing.DOWN;
      } else {
         return var6 > 0.0 ? EnumFacing.SOUTH : EnumFacing.NORTH;
      }
   }

   public static EnumFacing[] zauPl(Vec3 var0, BlockPos var1) {
      EnumFacing var2 = isCloserToBlockMax(var0.yCoord, var1.getY()) ? EnumFacing.UP : EnumFacing.DOWN;
      EnumFacing var3 = isCloserToBlockMax(var0.zCoord, var1.getZ()) ? EnumFacing.SOUTH : EnumFacing.NORTH;
      EnumFacing var4 = isCloserToBlockMax(var0.xCoord, var1.getX()) ? EnumFacing.EAST : EnumFacing.WEST;
      return new EnumFacing[]{var2, var3, var4};
   }

   private static boolean isCloserToBlockMax(double var0, int var2) {
      return Math.abs(var0 - (var2 + 1.0)) < Math.abs(var0 - var2);
   }

   public static boolean IuaL(EnumFacing[] var0, EnumFacing var1) {
      for (EnumFacing var5 : var0) {
         if (var5 == var1) {
            return true;
         }
      }

      return false;
   }

   public static Vec3 getFaceCenter(BlockPos var0, EnumFacing var1) {
      double var2 = var0.getX() + 0.5;
      double var4 = var0.getY() + 0.5;
      double var6 = var0.getZ() + 0.5;
      switch (var1) {
         case UP:
            return new Vec3(var2, var0.getY() + 1.0 - 0.001, var6);
         case DOWN:
            return new Vec3(var2, var0.getY() + 0.001, var6);
         case NORTH:
            return new Vec3(var2, var4, var0.getZ() + 0.001);
         case SOUTH:
            return new Vec3(var2, var4, var0.getZ() + 1.0 - 0.001);
         case EAST:
            return new Vec3(var0.getX() + 1.0 - 0.001, var4, var6);
         case WEST:
            return new Vec3(var0.getX() + 0.001, var4, var6);
         default:
            return new Vec3(var2, var4, var6);
      }
   }

   public static double getDistanceSqToBlock(Vec3 var0, BlockPos var1) {
      double var2 = clampValue(var0.xCoord, var1.getX(), var1.getX() + 1.0);
      double var4 = clampValue(var0.yCoord, var1.getY(), var1.getY() + 1.0);
      double var6 = clampValue(var0.zCoord, var1.getZ(), var1.getZ() + 1.0);
      double var8 = var0.xCoord - var2;
      double var10 = var0.yCoord - var4;
      double var12 = var0.zCoord - var6;
      return var8 * var8 + var10 * var10 + var12 * var12;
   }

   private static double clampValue(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }
}
