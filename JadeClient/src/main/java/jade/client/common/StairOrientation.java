// Jade recovery: original class: jade.deps.eLz.EBLni7E
package jade.client.common;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing;

public final class StairOrientation {
   private static final Map<EnumFacing, Axis[]> BcLg = new EnumMap<>(EnumFacing.class);
   private static final Map<EnumFacing, Integer> FACING_ROTATIONS = new EnumMap<>(EnumFacing.class);
   private final EnumFacing enumFacing;
   private final boolean topHalf;

   public StairOrientation(EnumHalf var1, EnumFacing var2) {
      this.enumFacing = var2;
      this.topHalf = var1 == EnumHalf.TOP;
   }

   public int getFacingRotation() {
      Integer var1 = FACING_ROTATIONS.get(this.enumFacing);
      return var1 == null ? 0 : var1;
   }

   public int Wwhgew() {
      return this.topHalf ? 270 : 0;
   }

   public EnumFacing rotateFacing(EnumFacing var1) {
      if (!this.topHalf) {
         if (var1.getAxis() == Axis.Y) {
            return var1;
         } else if (this.enumFacing == EnumFacing.NORTH) {
            return var1.getOpposite();
         } else {
            return this.enumFacing != EnumFacing.EAST && this.enumFacing != EnumFacing.WEST ? var1 : var1.rotateYCCW();
         }
      } else {
         Axis[] var2 = BcLg.get(this.enumFacing);
         if (var2 != null) {
            for (Axis var6 : var2) {
               var1 = var1.rotateAround(var6);
            }
         }

         return var1;
      }
   }

   static {
      Axis var0 = Axis.X;
      Axis var1 = Axis.Y;
      Axis var2 = Axis.Z;
      BcLg.put(EnumFacing.NORTH, new Axis[]{var0, var1, var1});
      BcLg.put(EnumFacing.EAST, new Axis[]{var2, var1});
      BcLg.put(EnumFacing.SOUTH, new Axis[]{var0, var0, var0});
      BcLg.put(EnumFacing.WEST, new Axis[]{var2, var1, var2, var2});
      FACING_ROTATIONS.put(EnumFacing.NORTH, 180);
      FACING_ROTATIONS.put(EnumFacing.EAST, 90);
      FACING_ROTATIONS.put(EnumFacing.WEST, 270);
   }
}
