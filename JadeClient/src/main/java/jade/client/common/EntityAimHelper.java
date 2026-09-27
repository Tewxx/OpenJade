// Jade recovery: original class: jade.deps.eLz.dKByq4OKX
package jade.client.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;

public final class EntityAimHelper {
   private EntityAimHelper() {
   }

   public static void lookAtEntity(Minecraft var0, Entity var1, float var2, boolean var3) {
      float[] var4 = EntityAngles.getAnglesTo(var0.thePlayer, var1);
      if (var4 != null) {
         float var5 = var3 ? RotationUtils.lastSentRotation[0] : var0.thePlayer.rotationYaw;
         float var6 = var3 ? RotationUtils.lastSentRotation[1] : var0.thePlayer.rotationPitch;
         float[] var7 = RotationUtils.NSsr(var4[0], var4[1] + 4.0F + var2, var5, var6);
         if (var3) {
            var0.getNetHandler().addToSendQueue(new C05PacketPlayerLook(var7[0], var7[1], var0.thePlayer.onGround));
         } else {
            var0.thePlayer.rotationYaw = var7[0];
            var0.thePlayer.rotationPitch = var7[1];
         }
      }
   }
}
