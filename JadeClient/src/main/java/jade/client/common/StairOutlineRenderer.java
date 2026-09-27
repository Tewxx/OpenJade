// Jade recovery: original class: jade.deps.eLz.O8NEa4JFb
package jade.client.common;

import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public final class StairOutlineRenderer {
   private StairOutlineRenderer() {
   }

   public static void renderStairOutline(
      BlockPos var0,
      IBlockState var1,
      AxisAlignedBB var2,
      EnumFacing var3,
      double var4,
      double var6,
      double var8,
      int var10,
      int var11,
      int var12,
      int var13,
      boolean var14,
      boolean var15,
      StairOutlineRenderer$0 var16
   ) {
      EnumFacing var17 = (EnumFacing)var1.getValue(BlockStairs.FACING);
      EnumHalf var18 = (EnumHalf)var1.getValue(BlockStairs.HALF);
      StairOrientation var19 = new StairOrientation(var18, var17);
      GL11.glPushMatrix();
      GL11.glTranslated(-var4, -var6, -var8);
      GL11.glTranslated(var0.getX() + 0.5, var0.getY(), var0.getZ() + 0.5);
      GL11.glRotated(var19.getFacingRotation(), 0.0, 1.0, 0.0);
      GL11.glTranslated(0.0, 0.5, 0.0);
      GL11.glRotated(var19.Wwhgew(), 1.0, 0.0, 0.0);
      GL11.glTranslated(-var0.getX() - 0.5, -var0.getY() - 0.5, -var0.getZ() - 0.5);
      if (var3 == null) {
         FuYu1(var2, var10, var11, var12, var13, var14, var15, var16);
      } else {
         LKdSz(var2, var18, var17, var3, var10, var11, var12, var13, var14, var15, var16);
      }

      GL11.glPopMatrix();
   }

   private static void FuYu1(AxisAlignedBB var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6, StairOutlineRenderer$0 var7) {
      for (xyYyTfuzbs var11 : xyYyTfuzbs.values()) {
         var11.draw(var0, var1, var2, var3, var4, var5, var6, var7);
      }
   }

   private static void LKdSz(
      AxisAlignedBB var0,
      EnumHalf var1,
      EnumFacing var2,
      EnumFacing var3,
      int var4,
      int var5,
      int var6,
      int var7,
      boolean var8,
      boolean var9,
      StairOutlineRenderer$0 var10
   ) {
      EnumFacing var11 = new StairOrientation(var1, var2).rotateFacing(var3);
      xyYyTfuzbs.forFace(var11).draw(var0, var4, var5, var6, var7, var8, var9, var10);
   }
}
