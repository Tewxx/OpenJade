// Jade recovery: original class: jade.deps.eLz.nGxQ36
package jade.client.common;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class BlockSightChecker {
   private static final double[][] FACE_SAMPLE_OFFSETS = new double[][]{{1.0, 0.5}, {0.0, 0.5}, {0.5, 1.0}, {0.5, 0.0}};

   private BlockSightChecker() {
   }

   public static boolean hasLineOfSightToBlock(World var0, BlockPos var1, Vec3 var2, Vec3 var3) {
      MovingObjectPosition var4 = var0.rayTraceBlocks(var2, var3, false, false, false);
      return var4 == null ? true : var4.typeOfHit == MovingObjectType.BLOCK && FacingUtils.isSameBlockPos(var4.getBlockPos(), var1);
   }

   public static boolean CbjZb(World var0, BlockPos var1, Vec3 var2) {
      for (int var3 = 0; var3 < 2; var3++) {
         double var4 = var1.getY() + var3 * 0.5;

         for (double[] var9 : FACE_SAMPLE_OFFSETS) {
            Vec3 var10 = new Vec3(var1.getX() + var9[0], var4, var1.getZ() + var9[1]);
            if (hasLineOfSightToBlock(var0, var1, var2, var10)) {
               return true;
            }
         }
      }

      return false;
   }
}
