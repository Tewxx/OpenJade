// Jade recovery: recovered class name: MixinGuiPlayerTabOverlay; mixin target: net.minecraft.client.gui.GuiPlayerTabOverlay; original class: jade.mixin.impl.render.M2821826a20534ea8dc9f33b9c1d2ba84
package jade.mixin.impl.render;

import jade.client.Jade;
import jade.client.module.minigames.TabStats;
import jade.client.module.other.Anticheat;
import jade.client.module.other.Denick;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiPlayerTabOverlay.class)
public class MixinGuiPlayerTabOverlay {
   @Inject(method = "renderPlayerlist", at = @At("HEAD"), cancellable = true)
   private void jade$renderOverlayStatsInTab(int width, Scoreboard scoreboard, ScoreObjective objective, CallbackInfo ci) {
      TabStats tabStats = Jade.getModuleManager().getModule(TabStats.class);
      if (tabStats != null && tabStats.renderTabStats(width, scoreboard, objective)) {
         ci.cancel();
      }
   }

   @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
   private void jade$appendDenickTabMarker(NetworkPlayerInfo info, CallbackInfoReturnable<String> cir) {
      Denick denick = Jade.getModuleManager().getModule(Denick.class);
      if (info != null && info.getGameProfile() != null) {
         String name = cir.getReturnValue();
         if (name != null) {
            String marker = denick == null ? "" : denick.getTabMarker(info);
            Anticheat anticheat = Jade.getModuleManager().getModule(Anticheat.class);
            if (anticheat != null && anticheat.WfMt(info.getGameProfile().getId(), info.getGameProfile().getName())) {
               marker = marker + " §6⚠§r";
            }

            if (!marker.isEmpty()) {
               cir.setReturnValue(name + marker);
            }
         }
      }
   }
}
