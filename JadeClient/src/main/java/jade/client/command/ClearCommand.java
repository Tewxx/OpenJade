// Jade recovery: original class: jade.deps.eLz.C2NM3T
package jade.client.command;

import jade.client.Jade;
import jade.client.common.WhisperShortcuts;
import jade.client.common.CommandInput;
import jade.client.module.minigames.Overlay;

public class ClearCommand extends Command {
   public ClearCommand() {
      super("clear");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 0) {
         this.qfZbqB();
      } else {
         Overlay var2 = Jade.getModuleManager().getModule(Overlay.class);
         int var3 = var2 == null ? 0 : var2.clearTrackedPlayers();
         WhisperShortcuts.clearWhisperShortcuts();
         this.sendChatMessage("&7cleared &f" + var3 + "&7 overlay player" + (var3 == 1 ? "." : "s."));
      }
   }
}
