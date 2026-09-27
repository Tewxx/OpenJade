// Jade recovery: original class: jade.deps.eLz.wRlmDS
package jade.client.module.player.blockin;

import jade.client.common.RotationUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

public final class PlacementValidator {
   private PlacementValidator() {
   }

   public static PlacementTarget validateRaytrace(double var0, float var2, float var3, BlockPos var4, EnumFacing var5, BlockPos var6) {
      MovingObjectPosition var7 = RotationUtils.traceBlockHit(var0, var2, var3);
      if (var7 == null || !var4.equals(var7.getBlockPos())) {
         return null;
      } else {
         return var5 == var7.sideHit && var6.equals(var4.offset(var5)) ? new PlacementTarget(var4, var5, var2, var3) : null;
      }
   }
}
