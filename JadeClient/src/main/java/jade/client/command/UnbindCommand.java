// Jade recovery: original class: jade.deps.eLz.GGmAvtC
package jade.client.command;

import jade.client.common.CommandInput;
import jade.client.module.Module;
import java.util.List;

public class UnbindCommand extends Command {
   public UnbindCommand() {
      super("unbind", "ub");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.ahGlioN();
      } else {
         Module var2 = ModuleNameCompleter.findModule(var1.joinArgumentsFrom(0));
         if (var2 == null) {
            this.sendChatMessage("&cModule not found.");
         } else {
            var2.setKeycode(0);
            this.sendChatMessage("&7unbound &b" + var2.getName() + "&7.");
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      return ModuleNameCompleter.getMatchingModuleNames(var1.getArgumentCount() == 0 ? "" : var1.joinArgumentsFrom(0));
   }

   @Override
   public int getArgumentOffset(CommandInput var1) {
      return 0;
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Remove bind: &b" + this.withCommandPrefix("unbind") + " [module]");
   }
}
