// Jade recovery: recovered class name: PlayerPortalUpdate; original class: jade.mixin.feature.player.M77c254f5164d61c58ee2878a0c442e2e
package jade.mixin.feature.player;

import jade.client.Jade;
import jade.client.module.render.AntiDebuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public final class PlayerPortalUpdate {
   private PlayerPortalUpdate() {
   }

   public static PlayerPortalUpdate$1 advance(EntityPlayerSP player, Minecraft minecraft, float portalAmount, boolean enteredPortal) {
      boolean remainsEntered = enteredPortal;
      float next;
      if (enteredPortal) {
         if (minecraft.currentScreen != null && !minecraft.currentScreen.doesGuiPauseGame()) {
            minecraft.displayGuiScreen(null);
         }

         if (portalAmount == 0.0F) {
            float pitch = player.getRNG().nextFloat() * 0.4F + 0.8F;
            minecraft.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), pitch));
         }

         next = Math.min(1.0F, portalAmount + 0.0125F);
         remainsEntered = false;
      } else if (confusionShouldShow(player)) {
         next = Math.min(1.0F, portalAmount + 0.006666667F);
      } else {
         next = Math.max(0.0F, portalAmount - 0.05F);
      }

      return new PlayerPortalUpdate$1(next, remainsEntered);
   }

   private static boolean confusionShouldShow(EntityPlayerSP player) {
      if (player.isPotionActive(Potion.confusion) && player.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
         AntiDebuff antiDebuff = Jade.getModuleManager().getModule(AntiDebuff.class);
         return antiDebuff == null || !antiDebuff.blocksConfusion(Potion.confusion);
      } else {
         return false;
      }
   }
}
