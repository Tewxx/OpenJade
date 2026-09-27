// Jade recovery: original class: jade.deps.eLz.iBw65vbt
package jade.client.command;

import jade.client.common.CommandInput;
import jade.client.module.Module;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BindCommand extends Command {
   public BindCommand() {
      super("bind", "b");
   }

   @Override
   public void execute(CommandInput var1) {
      BindArguments var2 = new BindArguments(var1);
      if (var2.hasNoArguments()) {
         this.ahGlioN();
      } else if (var2.isListRequest()) {
         new BindsCommand().execute(var2.toBindsArguments());
      } else if (!var2.hasMultipleArguments()) {
         this.ahGlioN();
      } else {
         Module var3 = ModuleNameCompleter.findModule(var2.joinLeadingArguments());
         if (var3 == null) {
            this.sendChatMessage("&cModule not found.");
         } else {
            int var4 = KeyNames.parseKeyCode(var2.getLastArgument());
            if (var4 == -1) {
               this.sendChatMessage("&7Invalid key.");
            } else {
               var3.setKeycode(var4);
               if (var4 == 0) {
                  this.sendChatMessage("&7unbound &b" + var3.getName() + "&7.");
               } else {
                  this.sendChatMessage("&7bound &b" + var3.getName() + " &7to &b" + KeyNames.getKeyName(var4) + "&7.");
               }
            }
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      if (var1.getArgumentCount() <= 1) {
         ArrayList var2 = new ArrayList<>(Collections.singletonList("list"));
         var2.addAll(this.getMatchingModuleNames(var1.getArgumentCount() == 0 ? "" : var1.joinArgumentsFrom(0)));
         return this.completeFromList(var1, var2);
      } else if ("list".equalsIgnoreCase(var1.getArgument(0))) {
         return Collections.emptyList();
      } else {
         return var1.getArgumentCount() >= 2 ? this.completeFromArray(var1, "R", "F", "G", "V", "NONE") : Collections.emptyList();
      }
   }

   @Override
   public int getArgumentOffset(CommandInput var1) {
      return Math.max(0, var1.getArgumentCount() - 1);
   }

   private List<String> getMatchingModuleNames(String var1) {
      return ModuleNameCompleter.getMatchingModuleNames(var1);
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Set binds: &b" + this.withCommandPrefix("bind") + " [module] [key]");
      this.vsuIw(" &7List binds: &b" + this.withCommandPrefix("bind") + " list");
   }
}
