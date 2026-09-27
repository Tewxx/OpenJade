// Jade recovery: original class: jade.deps.eLz.u7cgOZo1
package jade.client.common;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class MiningTargetCheck {
   private MiningTargetCheck() {
   }

   public static boolean isMiningBlock(Minecraft var0) {
      int var1 = var0.gameSettings.keyBindAttack.getKeyCode();
      if (var1 != 0 && Wlo914.GKjTw(var1, Keyboard::isKeyDown, Mouse::isButtonDown)) {
         double var2 = var0.playerController.getBlockReachDistance();
         float var4 = var0.thePlayer.rotationYaw;
         float var5 = var0.thePlayer.rotationPitch;
         MovingObjectPosition var6 = RotationUtils.orpg(var2, 1.0F, new float[]{var4, var5}, null);
         if (var6 != null && var6.typeOfHit == MovingObjectType.ENTITY) {
            return false;
         } else {
            MovingObjectPosition var7 = RotationUtils.traceBlockHit(var2, var4, var5);
            return var7 != null && var7.typeOfHit == MovingObjectType.BLOCK && var7.getBlockPos() != null;
         }
      } else {
         return false;
      }
   }
}
