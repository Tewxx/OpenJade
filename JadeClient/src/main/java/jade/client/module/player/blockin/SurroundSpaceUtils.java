// Jade recovery: original class: jade.deps.eLz.jBT044myaT
package jade.client.module.player.blockin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public final class SurroundSpaceUtils {
   private static final EnumFacing[] HORIZONTAL_FACINGS;

   private SurroundSpaceUtils() {
   }

   public static int FLOOA(EntityPlayer var0) {
      BlockPos var1 = new BlockPos(MathHelper.floor_double(var0.posX), MathHelper.floor_double(var0.posY), MathHelper.floor_double(var0.posZ));
      int var2 = PlacementSpaceUtils.utUuoro(var1.up().up()) ? 0 : 1;

      for (EnumFacing var6 : HORIZONTAL_FACINGS) {
         BlockPos var7 = var1.offset(var6);
         if (!PlacementSpaceUtils.utUuoro(var7)) {
            var2++;
         }

         if (!PlacementSpaceUtils.utUuoro(var7.up())) {
            var2++;
         }
      }

      return var2;
   }

   static {
      EnumFacing[] var10000 = new EnumFacing[]{EnumFacing.EAST, null, null, null};
      var10000[1] = EnumFacing.SOUTH;
      var10000[2] = EnumFacing.WEST;
      var10000[3] = EnumFacing.NORTH;
      HORIZONTAL_FACINGS = var10000;
   }
}
