// Jade recovery: original class: jade.deps.eLz.bT2Vk5A
package jade.client.command;

import jade.client.common.CommandInput;
import jade.deps.loader107.HypixelPlayerStats$0;
import jade.deps.loader107.HypixelPlayerStats;
import jade.deps.loader107.StatsLookupService;
import java.util.List;

public class ModeCommand extends Command {
   private static final String MODE_OPTIONS_TEXT = "1s/2s/3s/4s/4v4/overall/core";

   public ModeCommand() {
      super("mode");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.sendChatMessage(
            "&7overlay mode: &f"
               + HypixelPlayerStats.getCurrentMode().getLabel()
               + " &8("
               + "1s/2s/3s/4s/4v4/overall/core"
               + ")"
         );
      } else if (var1.getArgumentCount() != 1) {
         this.qfZbqB();
      } else {
         HypixelPlayerStats$0 var2 = HypixelPlayerStats.parseMode(var1.getArgument(0));
         if (var2 == null) {
            this.sendChatMessage("&7invalid overlay mode: &f" + var1.getArgument(0) + " &8(" + "1s/2s/3s/4s/4v4/overall/core" + ")");
         } else {
            HypixelPlayerStats.setCurrentMode(var2);
            StatsLookupService.getInstance().clearPlayerCaches();
            this.sendChatMessage("&7overlay mode set to &f" + var2.getLabel() + "&7.");
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      return this.completeFromArray(var1, "1s", "solo", "solos", "2s", "doubles", "3s", "threes", "4s", "fours", "4v4", "overall", "core");
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Choose which BedWars stats the overlay shows.");
      this.vsuIw(" &b" + this.withCommandPrefix("mode") + " [1s/2s/3s/4s/4v4/overall/core]");
   }
}
