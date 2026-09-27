// Jade recovery: original class: jade.deps.eLz.znB11y1d
package jade.client.module.player.blockin;

import jade.client.common.RotationUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

public final class PlacementRotation {
   private final float yaw;
   private final float pitch;
   private final PlacementHit placementHit;

   private PlacementRotation(float var1, float var2, PlacementHit var3) {
      this.yaw = var1;
      this.pitch = var2;
      this.placementHit = var3;
   }

   public static PlacementRotation RYgmVv9(Float var0, Float var1, float var2, float var3, int var4, float var5, double var6, BlockPos var8, EnumFacing var9) {
      float var10 = var0 == null ? RotationUtils.lastSentRotation[0] : var0;
      float var11 = var1 == null ? RotationUtils.lastSentRotation[1] : var1;
      float[] var12 = RotationUtils.smoothAnglesRandomized(var10, var11, var2, var3, var4, var5);
      MovingObjectPosition var13 = RotationUtils.traceBlockHit(var6, var12[0], var12[1]);
      PlacementHit var14 = null;
      if (var13 != null && var8.equals(var13.getBlockPos()) && var9 == var13.sideHit) {
         var14 = new PlacementHit(var13.getBlockPos(), var13.sideHit, var13.hitVec);
      }

      return new PlacementRotation(var12[0], var12[1], var14);
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public PlacementHit getPlacementHit() {
      return this.placementHit;
   }
}
