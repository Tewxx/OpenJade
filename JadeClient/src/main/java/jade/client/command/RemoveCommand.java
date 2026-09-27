// Jade recovery: original class: jade.deps.eLz.ydiUvzuVx
package jade.client.command;

import jade.client.Jade;
import jade.client.common.WhisperShortcuts;
import jade.client.common.CommandInput;
import jade.client.module.minigames.Overlay;

public class RemoveCommand extends Command {
   public RemoveCommand() {
      super("remove", "remove", "r");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 1) {
         this.qfZbqB();
      } else {
         WhisperShortcuts.removeWhisperShortcut(var1.getArgument(0));
         Overlay var2 = this.getOverlayModule();
         if (var2 != null && var2.removeTrackedPlayer(var1.getArgument(0))) {
            this.sendChatMessage("&7removed overlay player: &f" + var1.getArgument(0));
         } else {
            this.sendChatMessage("&7overlay player not found: &f" + var1.getArgument(0));
         }
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Remove a player from the overlay list.");
      this.vsuIw(" &b" + this.withCommandPrefix("remove") + " [name]");
   }

   private Overlay getOverlayModule() {
      return Jade.getModuleManager().getModule(Overlay.class);
   }
}
