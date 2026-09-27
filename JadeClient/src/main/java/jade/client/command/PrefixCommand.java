// Jade recovery: original class: jade.deps.eLz.xXHakfv
package jade.client.command;

import jade.client.Jade;
import jade.client.common.CommandInput;
import jade.client.module.client.ChatCommands;
import java.util.List;

public class PrefixCommand extends Command {
   public PrefixCommand() {
      super("prefix");
   }

   @Override
   public void execute(CommandInput var1) {
      ChatCommands var2 = Jade.getModuleManager().getModule(ChatCommands.class);
      switch (var1.getArgumentCount()) {
         case 0:
            this.sendChatMessage("&7Current prefix: &b" + var2.getCommandPrefix());
            return;
         case 1:
            this.setChatPrefix(var1.getArgument(0), var2);
            return;
         default:
            this.qfZbqB();
      }
   }

   private void setChatPrefix(String var1, ChatCommands var2) {
      if (!ChatCommands.isValidPrefix(var1)) {
         this.sendChatMessage("&7Prefix must be a single non-space character.");
      } else {
         var2.haKnp68(var1);
         this.sendChatMessage("&7Chat command prefix set to &b" + var2.getCommandPrefix() + "&7.");
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      return this.completeFromList(var1, PrefixCharacters.CEOcwap());
   }
}
