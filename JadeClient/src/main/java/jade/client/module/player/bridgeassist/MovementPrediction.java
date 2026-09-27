// Jade recovery: original class: jade.deps.eLz.ZSqQ3uO
package jade.client.module.player.bridgeassist;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class MovementPrediction {
   private static final float AIR_MOVE_SPEED = 0.02F;

   private MovementPrediction() {
   }

   public static AxisAlignedBB getPredictedBoundingBox(EntityPlayerSP var0) {
      float var1 = var0.movementInput.moveStrafe * 0.98F;
      float var2 = var0.movementInput.moveForward * 0.98F;
      if (var0.isUsingItem() && var0.ridingEntity == null) {
         var1 *= 0.2F;
         var2 *= 0.2F;
      }

      float var3 = getMoveSpeed(var0);
      float var4 = var1 * var1 + var2 * var2;
      double var5 = var0.motionX;
      double var7 = var0.motionZ;
      if (var4 >= 1.0E-4F) {
         var4 = MathHelper.sqrt_float(var4);
         if (var4 < 1.0F) {
            var4 = 1.0F;
         }

         var1 *= var3 / var4;
         var2 *= var3 / var4;
         float var9 = var0.rotationYaw * (float) Math.PI / 180.0F;
         float var10 = MathHelper.sin(var9);
         float var11 = MathHelper.cos(var9);
         var5 += var1 * var11 - var2 * var10;
         var7 += var2 * var11 + var1 * var10;
      }

      return resolveCollisions(var0, var0.getEntityBoundingBox(), var5, var7);
   }

   private static float getMoveSpeed(EntityPlayerSP var0) {
      if (!var0.onGround) {
         return 0.02F;
      } else {
         BlockPos var1 = new BlockPos(
            MathHelper.floor_double(var0.posX), MathHelper.floor_double(var0.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(var0.posZ)
         );
         Block var2 = var0.worldObj.getBlockState(var1).getBlock();
         float var3 = var2.slipperiness * 0.91F;
         return var0.getAIMoveSpeed() * (0.16277136F / (var3 * var3 * var3));
      }
   }

   private static AxisAlignedBB resolveCollisions(EntityPlayerSP var0, AxisAlignedBB var1, double var2, double var4) {
      AxisAlignedBB var6 = var1.addCoord(var2, 0.0, var4);
      List var7 = var0.worldObj.getCollidingBoundingBoxes(var0, var6);

      for (AxisAlignedBB var9 : (java.lang.Iterable<AxisAlignedBB>) (java.lang.Iterable<?>) (var7)) {
         var2 = var9.calculateXOffset(var1, var2);
      }

      var1 = var1.offset(var2, 0.0, 0.0);

      for (AxisAlignedBB var12 : (java.lang.Iterable<AxisAlignedBB>) (java.lang.Iterable<?>) (var7)) {
         var4 = var12.calculateZOffset(var1, var4);
      }

      return var1.offset(0.0, 0.0, var4);
   }
}
