// Jade recovery: original class: jade.deps.eLz.hUMO750
package jade.client.module.player.bednuker;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class TargetRayTracer {
   private TargetRayTracer() {
   }

   public static MovingObjectPosition rayTraceWithReportedCheck(AxisAlignedBB var0, Vec3 var1, double var2, float var4, float var5, float var6, float var7) {
      return rayTraceFromRotation(var0, var1, var2, var6, var7) == null ? null : rayTraceFromRotation(var0, var1, var2, var4, var5);
   }

   public static MovingObjectPosition rayTraceFromRotation(AxisAlignedBB var0, Vec3 var1, double var2, float var4, float var5) {
      if (var0 == null || var1 == null || var2 <= 0.0 || !Float.isFinite(var4) || !Float.isFinite(var5)) {
         return null;
      } else if (var0.isVecInside(var1)) {
         return new MovingObjectPosition(var1, EnumFacing.UP);
      } else {
         float var6 = (float) (Math.PI / 180.0);
         float var7 = -MathHelper.cos(-var5 * var6);
         Vec3 var8 = var1.addVector(
            MathHelper.sin(-var4 * var6 - (float) Math.PI) * var7 * var2,
            MathHelper.sin(-var5 * var6) * var2,
            MathHelper.cos(-var4 * var6 - (float) Math.PI) * var7 * var2
         );
         return var0.calculateIntercept(var1, var8);
      }
   }

   public static float[] LynS(AxisAlignedBB var0, Vec3 var1, float var2) {
      double var3 = (var0.minX + var0.maxX) * 0.5 - var1.xCoord;
      double var5 = (var0.minY + var0.maxY) * 0.5 - var1.yCoord;
      double var7 = (var0.minZ + var0.maxZ) * 0.5 - var1.zCoord;
      double var9 = Math.sqrt(var3 * var3 + var7 * var7);
      float var11 = var9 < 1.0E-6 ? var2 : var2 + MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(var7, var3)) - 90.0F - var2);
      float var12 = (float)(-Math.toDegrees(Math.atan2(var5, var9)));
      return new float[]{var11, var12};
   }
}
