// Jade recovery: original class: jade.deps.eLz.JtpRPY
package jade.client.command;

import jade.client.Jade;
import jade.client.common.WhisperShortcuts;
import jade.client.common.CommandInput;
import jade.client.module.minigames.Overlay;

public class AddCommand extends Command {
   public AddCommand() {
      super("add", "add", "a");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 1) {
         this.qfZbqB();
      } else {
         WhisperShortcuts.vQwg3(var1.getArgument(0));
         Overlay var2 = this.getOverlayModule();
         if (var2 != null && var2.addManualPlayer(var1.getArgument(0))) {
            this.sendChatMessage("&7adding overlay player: &f" + var1.getArgument(0));
         } else {
            this.sendChatMessage("&7invalid overlay player: &f" + var1.getArgument(0));
         }
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Add a player to the overlay list.");
      this.vsuIw(" &b" + this.withCommandPrefix("add") + " [name]");
   }

   private Overlay getOverlayModule() {
      return Jade.getModuleManager().getModule(Overlay.class);
   }
}
