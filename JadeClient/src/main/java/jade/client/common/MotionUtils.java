// Jade recovery: original class: jade.deps.eLz.yFyTRluH
package jade.client.common;

import java.util.function.DoubleSupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public final class MotionUtils {
   private MotionUtils() {
   }

   public static void setMotionFromYaw(EntityPlayer var0, double var1, DoubleSupplier var3) {
      if (var1 == 0.0) {
         var0.motionX = 0.0;
         var0.motionZ = 0.0;
      } else {
         BLqrruy(var0, var1, var3.getAsDouble());
      }
   }

   public static void setMotionFromYawIfEnabled(EntityPlayer var0, double var1, boolean var3, DoubleSupplier var4) {
      if (var3) {
         BLqrruy(var0, var1, var4.getAsDouble());
      }
   }

   private static void BLqrruy(EntityPlayer var0, double var1, double var3) {
      var0.motionX = -Math.sin(var3) * var1;
      var0.motionZ = Math.cos(var3) * var1;
   }

   public static double getHorizontalSpeed(Entity var0) {
      return Math.hypot(var0.motionX, var0.motionZ);
   }

   public static double GaGpp(Entity var0) {
      return Math.hypot(var0.posX - var0.prevPosX, var0.posZ - var0.prevPosZ) * 20.0;
   }
}
