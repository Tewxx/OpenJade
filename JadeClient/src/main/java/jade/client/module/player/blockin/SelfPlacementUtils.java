// Jade recovery: original class: jade.deps.eLz.AXT49o0YH
package jade.client.module.player.blockin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class SelfPlacementUtils {
   private SelfPlacementUtils() {
   }

   public static boolean isSelfSurroundOffset(int var0, int var1, int var2) {
      if (var0 == 0 && var2 == 0) {
         return var1 == 2;
      } else {
         return var1 != 0 && var1 != 1 ? false : Math.abs(var0) == 1 && var2 == 0 || Math.abs(var2) == 1 && var0 == 0;
      }
   }

   public static boolean isSelfSurroundPosition(EntityPlayer var0, BlockPos var1) {
      int var2 = MathHelper.floor_double(var0.posX);
      int var3 = MathHelper.floor_double(var0.posY);
      int var4 = MathHelper.floor_double(var0.posZ);
      return isSelfSurroundOffset(var1.getX() - var2, var1.getY() - var3, var1.getZ() - var4);
   }

   public static double[] computeFaceHitPoint(double var0, double var2, double var4, int var6, int var7, int var8, double var9, double var11) {
      double var13 = 0.949;
      double var15 = 0.051000000000000004;
      if (var7 != 0) {
         return new double[]{var0 + var9, var2 + (var7 < 0 ? var13 : var15), var4 + var11};
      } else {
         return var8 != 0
            ? new double[]{var0 + var9, var2 + var11, var4 + (var8 < 0 ? var13 : var15)}
            : new double[]{var0 + (var6 < 0 ? var13 : var15), var2 + var11, var4 + var9};
      }
   }
}
