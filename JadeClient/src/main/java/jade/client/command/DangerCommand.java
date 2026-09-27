// Jade recovery: original class: jade.deps.eLz.wVJRTfbp6L
package jade.client.command;

import jade.client.common.DangerousModules;
import jade.client.common.CommandInput;
import java.util.List;

public final class DangerCommand extends Command {
   public DangerCommand() {
      super("danger");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 1 && "confirm".equalsIgnoreCase(var1.getArgument(0))) {
         int var2 = DangerousModules.nPtc();
         if (var2 == 0) {
            this.vsuIw("&7There are no pending dangerous modules to confirm.");
         } else {
            this.vsuIw("&6Danger acknowledged. &fEnabled " + var2 + (var2 == 1 ? " module." : " modules."));
         }
      } else {
         this.vsuIw("&7Use &b" + this.withCommandPrefix("danger confirm") + " &7to accept the pending module warnings.");
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      return this.completeFromArray(var1, "confirm");
   }
}
