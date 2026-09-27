// Jade recovery: original class: jade.deps.eLz.QRoVFVb90
package jade.client.command;

import jade.client.common.CommandInput;
import jade.deps.loader107.IrcMessageBus;

public class OnlineCommand extends Command {
   public OnlineCommand() {
      super("online");
   }

   @Override
   public void execute(CommandInput var1) {
      IrcMessageBus.incrementOnlineRequests();
      this.sendChatMessage("&7checking online Jade users...");
   }
}
