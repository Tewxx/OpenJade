// Jade recovery: original class: jade.deps.eLz.VxE2yqGY0
package jade.client.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class LookUtils {
   private static final double dIe = 4.0;

   private LookUtils() {
   }

   public static Vec3 computeCameraPosition(Minecraft var0, double var1, float var3, float var4, boolean var5) {
      if (var0.gameSettings.thirdPersonView == 0) {
         return new Vec3(var0.thePlayer.posX, var0.thePlayer.posY + var0.thePlayer.getEyeHeight(), var0.thePlayer.posZ);
      } else {
         Entity var6 = var0.getRenderViewEntity();
         Vec3 var7 = getInterpolatedEyePosition(var6, var1);
         Vec3 var8 = DSSZv6(var3, var4, 4.0);
         double var9 = var5 ? 4.0 : GTty9(var0, var7, var8);
         double var11 = var9 / 4.0;
         return var7.addVector(-var8.xCoord * var11, -var8.yCoord * var11, -var8.zCoord * var11);
      }
   }

   private static Vec3 getInterpolatedEyePosition(Entity var0, double var1) {
      return new Vec3(
         var0.prevPosX + (var0.posX - var0.prevPosX) * var1,
         var0.prevPosY + (var0.posY - var0.prevPosY) * var1 + var0.getEyeHeight(),
         var0.prevPosZ + (var0.posZ - var0.prevPosZ) * var1
      );
   }

   private static Vec3 DSSZv6(float var0, float var1, double var2) {
      float var4 = var0 / 180.0F * (float) Math.PI;
      float var5 = var1 / 180.0F * (float) Math.PI;
      double var6 = MathHelper.cos(var5) * var2;
      return new Vec3(-MathHelper.sin(var4) * var6, -MathHelper.sin(var5) * var2, MathHelper.cos(var4) * var6);
   }

   private static double GTty9(Minecraft var0, Vec3 var1, Vec3 var2) {
      double var3 = 4.0;

      for (int var5 = 0; var5 < 8; var5++) {
         float var6 = ((var5 & 1) * 2 - 1) * 0.1F;
         float var7 = ((var5 >> 1 & 1) * 2 - 1) * 0.1F;
         float var8 = ((var5 >> 2 & 1) * 2 - 1) * 0.1F;
         Vec3 var9 = var1.addVector(var6, var7, var8);
         Vec3 var10 = var1.addVector(-var2.xCoord + var6 + var8, -var2.yCoord + var7, -var2.zCoord + var8);
         MovingObjectPosition var11 = var0.theWorld.rayTraceBlocks(var9, var10);
         if (var11 != null) {
            var3 = Math.min(var3, var11.hitVec.distanceTo(var1));
         }
      }

      return var3;
   }
}
