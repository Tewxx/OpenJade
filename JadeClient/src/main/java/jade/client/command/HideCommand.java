// Jade recovery: original class: jade.deps.eLz.Qd1teqQK
package jade.client.command;

import jade.client.Jade;
import jade.client.common.CommandInput;
import jade.client.module.Module;
import java.util.ArrayList;
import java.util.List;

public class HideCommand extends Command {
   public HideCommand() {
      super("hide", "hideall");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.ahGlioN();
      } else if (!"all".equalsIgnoreCase(var1.getArgument(0))) {
         Module var5 = Jade.getModuleManager().getModuleByName(var1.joinArgumentsFrom(0));
         if (var5 == null) {
            this.sendChatMessage("&cModule not found.");
         } else {
            var5.setHidden(true);
            this.sendChatMessage("&7Hidden &b" + var5.getName() + " &7from the HUD.");
         }
      } else {
         int var2 = 0;

         for (Module var4 : Jade.getModuleManager().getModules()) {
            if (!var4.isHidden()) {
               var4.setHidden(true);
               var2++;
            }
         }

         this.sendChatMessage("&7Hidden &c" + var2 + " &7module" + (var2 == 1 ? "" : "s") + " from HUD.");
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      ArrayList var2 = new ArrayList();
      String var3 = var1.getArgumentCount() == 0 ? "" : var1.joinArgumentsFrom(0).toLowerCase();
      if ("all".startsWith(var3)) {
         var2.add("all");
      }

      for (Module var5 : Jade.getModuleManager().getModules()) {
         if (var5.getName().toLowerCase().startsWith(var3)) {
            var2.add(var5.getName());
         }
      }

      return var2;
   }

   @Override
   public int getArgumentOffset(CommandInput var1) {
      return 0;
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Hide from HUD: &b" + this.withCommandPrefix("hide") + " [module/all]");
   }
}
