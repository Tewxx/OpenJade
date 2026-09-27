// Jade recovery: recovered class name: PlayerFlightUpdate; original class: jade.mixin.feature.player.Mcc4c08d5b63a532a8270e04b6e3d5be0
package jade.mixin.feature.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public final class PlayerFlightUpdate {
   private PlayerFlightUpdate() {
   }

   public static PlayerFlightUpdate$1 beforeLivingUpdate(
      EntityPlayerSP player, Minecraft minecraft, boolean jumpBefore, int toggleTimer, boolean currentView
   ) {
      boolean abilitiesChanged = false;
      if (player.capabilities.allowFlying) {
         if (minecraft.playerController.isSpectatorMode()) {
            if (!player.capabilities.isFlying) {
               player.capabilities.isFlying = true;
               abilitiesChanged = true;
            }
         } else if (!jumpBefore && player.movementInput.jump) {
            if (toggleTimer == 0) {
               toggleTimer = 7;
            } else {
               player.capabilities.isFlying = !player.capabilities.isFlying;
               abilitiesChanged = true;
               toggleTimer = 0;
            }
         }
      }

      if (player.capabilities.isFlying && currentView) {
         double vertical = player.capabilities.getFlySpeed() * 3.0F;
         if (player.movementInput.sneak) {
            player.motionY -= vertical;
         }

         if (player.movementInput.jump) {
            player.motionY += vertical;
         }
      }

      return new PlayerFlightUpdate$1(toggleTimer, abilitiesChanged);
   }

   public static boolean landed(EntityPlayerSP player, Minecraft minecraft) {
      if (player.onGround && player.capabilities.isFlying && !minecraft.playerController.isSpectatorMode()) {
         player.capabilities.isFlying = false;
         return true;
      } else {
         return false;
      }
   }
}
