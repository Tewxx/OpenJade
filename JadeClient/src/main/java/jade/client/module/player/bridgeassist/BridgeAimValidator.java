// Jade recovery: original class: jade.deps.eLz.grM6Dv4TDz
package jade.client.module.player.bridgeassist;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

public final class BridgeAimValidator {
   private static final double BASE_HIT_Y_LIMIT = 0.68;
   private static final double EDGE_HIT_Y_BONUS = 0.22;
   private static final double EDGE_MARGIN = 0.12;
   private static final double SAFE_COORD_LIMIT = 0.88;

   private BridgeAimValidator() {
   }

   public static boolean isDiagonalEdgePose(EntityPlayerSP var0) {
      if (var0 != null && var0.onGround && !var0.movementInput.jump) {
         if (!(var0.movementInput.moveForward >= -0.01F) && !(Math.abs(var0.movementInput.moveStrafe) > 0.01F) && !(var0.rotationPitch < 75.0F)) {
            float var1 = MathHelper.wrapAngleTo180_float(var0.rotationYaw) % 90.0F;
            if (var1 < 0.0F) {
               var1 += 90.0F;
            }

            return var1 > 20.0F && var1 < 70.0F;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean isHitPointSafe(MovingObjectPosition var0) {
      return var0 != null && var0.typeOfHit == MovingObjectType.BLOCK && var0.hitVec != null && var0.getBlockPos() != null
         ? !isHitPointNearEdge(
            var0.hitVec.xCoord - var0.getBlockPos().getX(),
            var0.hitVec.yCoord - var0.getBlockPos().getY(),
            var0.hitVec.zCoord - var0.getBlockPos().getZ(),
            var0.sideHit
         )
         : false;
   }

   public static boolean isHitPointNearEdge(double var0, double var2, double var4, EnumFacing var6) {
      if (var6 != null && !(var2 < 0.0) && !(var2 > 1.0)) {
         double var7;
         if (var6 != EnumFacing.NORTH && var6 != EnumFacing.SOUTH) {
            if (var6 != EnumFacing.WEST && var6 != EnumFacing.EAST) {
               return false;
            }

            var7 = var4;
         } else {
            var7 = var0;
         }

         double var9;
         if (var7 <= 0.12) {
            var9 = 0.12 - var7;
         } else {
            if (!(var7 >= 0.88)) {
               return false;
            }

            var9 = var7 - 0.88;
         }

         double var11 = Math.min(1.0, Math.max(0.0, var9 / 0.12));
         double var13 = 0.68 + var11 * 0.22;
         return var2 <= var13;
      } else {
         return false;
      }
   }
}
