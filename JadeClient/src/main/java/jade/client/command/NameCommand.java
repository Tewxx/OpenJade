// Jade recovery: original class: jade.deps.eLz.iDlRxDrw
package jade.client.command;

import jade.client.common.CommandInput;

public class NameCommand extends Command {
   private final PlayerNameClipboardCopier playerNameCopier = new PlayerNameClipboardCopier();

   public NameCommand() {
      super("name", "ign", "name");
   }

   @Override
   public void execute(CommandInput var1) {
      String var2 = this.playerNameCopier.copyPlayerName();
      if (var2 != null) {
         this.sendChatMessage("&7Copied &b" + var2 + " &7to clipboard");
      }
   }
}
