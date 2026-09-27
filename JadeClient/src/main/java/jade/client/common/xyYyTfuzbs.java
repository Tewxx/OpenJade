// Jade recovery: original class: jade.deps.eLz.xyYyTfuzbs
package jade.client.common;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public enum xyYyTfuzbs {
   UP(EnumFacing.UP, new double[][]{{0.0, 0.25, 0.0, 0.25}, {0.0, 0.25, -0.5, -0.25}}),
   DOWN(EnumFacing.DOWN, new double[0][]),
   NORTH(EnumFacing.NORTH, new double[][]{{0.252, 0.0, 0.252, 0.5}, {0.25, 0.0, -0.25, 0.0}}),
   EAST(EnumFacing.EAST, new double[][]{{0.252, 0.25, 0.252, 0.25}, {0.25, 0.0, -0.25, 0.0}}),
   SOUTH(EnumFacing.SOUTH, new double[0][]),
   WEST(EnumFacing.WEST, new double[][]{{0.252, 0.25, 0.252, 0.25}, {0.25, 0.0, -0.25, 0.0}});

   private final EnumFacing face;
   private final double[][] patches;

   private xyYyTfuzbs(EnumFacing var3, double[][] var4) {
      this.face = var3;
      this.patches = var4;
   }

   public static xyYyTfuzbs forFace(EnumFacing var0) {
      return valueOf(var0.name());
   }

   public void draw(AxisAlignedBB var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7, StairOutlineRenderer$0 var8) {
      if (this.patches.length == 0) {
         var8.drawFace(var1, this.face, var2, var3, var4, var5, var6, var7);
      }

      for (double[] var12 : this.patches) {
         AxisAlignedBB var13 = var1.contract(0.0, var12[0], var12[1]).offset(0.0, var12[2], var12[3]);
         var8.drawFace(var13, this.face, var2, var3, var4, var5, var6, var7);
      }
   }
}
