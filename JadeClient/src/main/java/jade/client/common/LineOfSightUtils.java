// Jade recovery: original class: jade.deps.eLz.r8piEJ
package jade.client.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class LineOfSightUtils {
   private LineOfSightUtils() {
   }

   public static boolean isPathClear(Minecraft var0, Vec3 var1, Vec3 var2) {
      MovingObjectPosition var3 = var0.theWorld.rayTraceBlocks(var1, var2, false, false, false);
      return var3 == null || var3.typeOfHit != MovingObjectType.BLOCK;
   }

   public static boolean canSeeEntity(Minecraft var0, EntityLivingBase var1) {
      Vec3 var2 = var0.thePlayer.getPositionEyes(1.0F);
      double var3 = var1.getEyeHeight() - 0.2;
      double[][] var5 = new double[][]{{0.3, 0.0}, {-0.3, 0.0}, {0.0, 0.3}, {0.0, -0.3}};

      for (double[] var9 : var5) {
         if (isPathClear(var0, var2, new Vec3(var1.posX + var9[0], var3, var1.posZ + var9[1]))) {
            return true;
         }
      }

      for (double var10 = var1.getEyeHeight() + 0.2; var10 > 0.0; var10 -= 0.2) {
         if (isPathClear(var0, var2, new Vec3(var1.posX, var1.posY + var10, var1.posZ))) {
            return true;
         }
      }

      return false;
   }
}
