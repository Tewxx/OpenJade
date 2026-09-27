// Jade recovery: original class: jade.deps.eLz.tCHnERKsI
package jade.client.command;

import jade.client.common.CommandInput;
import jade.client.module.Module;
import java.util.List;

public class ToggleCommand extends Command {
   public ToggleCommand() {
      super("toggle", "toggle", "t");
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
            var2.toggle();
            String var3 = var2.isEnabled() ? "&aenabled" : "&cdisabled";
            this.sendChatMessage("&7" + var2.getName() + " " + var3 + "&7.");
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
      this.sendChatMessage("&7Toggle module: &b" + this.withCommandPrefix("toggle") + " [module]");
   }
}
