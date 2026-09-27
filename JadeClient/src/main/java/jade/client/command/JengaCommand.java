// Jade recovery: original class: jade.deps.eLz.Y7cVEEeu
package jade.client.command;

import jade.client.common.JengaGame;
import jade.client.common.CommandInput;
import java.util.List;

public class JengaCommand extends Command {
   public JengaCommand() {
      super("jenga");
   }

   @Override
   public void execute(CommandInput var1) {
      JengaGame var2 = JengaGame.getInstance();
      if (var1.getArgumentCount() == 0) {
         if (var2.hasPendingDuel()) {
            this.vsuIw("&cfinish or remove the current duel before starting a solo board.");
         } else {
            if (var2.vjcD()) {
               this.vsuIw("&a spawned a new table. &7left-click a block to grab it; look or move to pull it.");
               this.vsuIw("&7left-click again to release, and right-click while holding to rotate.");
            } else {
               this.vsuIw("&cjoin a world before spawning Jenga.");
            }
         }
      } else {
         String var3 = var1.getArgument(0);
         if ("duel".equalsIgnoreCase(var3)) {
            if (var1.getArgumentCount() < 2) {
               this.vsuIw("&7usage: &b" + this.withCommandPrefix("jenga duel <username>"));
               return;
            }

            var2.requestDuel(var1.getArgument(1));
         } else if ("accept".equalsIgnoreCase(var3)) {
            var2.qtqybRw();
         } else if ("reset".equalsIgnoreCase(var3)) {
            if (var2.hasPendingDuel()) {
               this.vsuIw("&cduel boards cannot be reset independently.");
               return;
            }

            if (var2.ensureBoardReady()) {
               this.vsuIw("&areset the Jenga tower.");
            } else {
               this.vsuIw("&cjoin a world before spawning Jenga.");
            }
         } else if (!"remove".equalsIgnoreCase(var3) && !"clear".equalsIgnoreCase(var3)) {
            if ("help".equalsIgnoreCase(var3)) {
               this.ahGlioN();
            } else {
               this.ahGlioN();
            }
         } else if (!var2.isBoardActive() && !var2.hasPendingDuel()) {
            this.vsuIw("&7there is no Jenga table to remove.");
         } else {
            var2.endDuel();
            this.vsuIw("&7removed the Jenga table and ended any pending duel.");
         }
      }
   }

   @Override
   public void ahGlioN() {
      this.vsuIw("&b" + this.withCommandPrefix("jenga") + " &7spawn a table at the block you are looking near");
      this.vsuIw("&b" + this.withCommandPrefix("jenga reset") + " &7rebuild the current tower");
      this.vsuIw("&b" + this.withCommandPrefix("jenga remove") + " &7remove the game");
      this.vsuIw("&b" + this.withCommandPrefix("jenga duel <username>") + " &7send a party-chat duel request");
      this.vsuIw("&b" + this.withCommandPrefix("jenga accept") + " &7accept your pending duel request");
      this.vsuIw("&7controls: left-click grab/release; right-click rotate while held.");
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      return this.completeFromArray(var1, "duel", "accept", "reset", "remove", "help");
   }

   @Override
   protected String getChatChannelName() {
      return "Jenga";
   }
}
