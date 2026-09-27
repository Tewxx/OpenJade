// Jade recovery: original class: jade.deps.eLz.SruDlrB
package jade.client.hook;

import jade.client.common.RotationUtils;
import jade.client.event.UpdateWalkingPlayerEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public final class PlayerPitchOverride {
   private float savedPrevRotationPitch;
   private float savedRotationPitch;

   public void applySilentPitch(Entity var1) {
      if (isLocalPlayerDuringWalkingUpdate(var1)) {
         EntityPlayerSP var2 = (EntityPlayerSP)var1;
         this.savedPrevRotationPitch = var2.prevRotationPitch;
         this.savedRotationPitch = var2.rotationPitch;
         var2.prevRotationPitch = RotationUtils.previousPitch;
         var2.rotationPitch = RotationUtils.pN0;
      }
   }

   public void restoreOriginalPitch(Entity var1) {
      if (isLocalPlayerDuringWalkingUpdate(var1)) {
         EntityPlayerSP var2 = (EntityPlayerSP)var1;
         var2.prevRotationPitch = this.savedPrevRotationPitch;
         var2.rotationPitch = this.savedRotationPitch;
      }
   }

   private static boolean isLocalPlayerDuringWalkingUpdate(Entity var0) {
      return var0 instanceof EntityPlayerSP && UpdateWalkingPlayerEvent.isYawOverrideRequested();
   }
}
