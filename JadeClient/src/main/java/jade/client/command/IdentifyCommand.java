// Jade recovery: original class: jade.deps.eLz.zJpjTfA
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.CommandInput;
import jade.client.module.other.Denick;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IdentifyCommand extends Command {
   public IdentifyCommand() {
      super("identify", "identify", "idnick", "denick");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() < 1) {
         this.sendChatMessage(
            "&7use &b"
               + this.withCommandPrefix("denick")
               + " <nick> [kill_message <value>|cosmetic]&7, &b"
               + this.withCommandPrefix("denick")
               + " <nick> brute&7, &b"
               + this.withCommandPrefix("denick")
               + " pause&7, or &b"
               + this.withCommandPrefix("denick")
               + " stop&7."
         );
      } else {
         Denick var2 = Jade.getModuleManager().getModule(Denick.class);
         if (var2 == null) {
            this.sendChatMessage("&cDenick is not ready yet.");
         } else {
            String var3 = var1.getArgument(0);
            if ("pause".equalsIgnoreCase(var3) || "resume".equalsIgnoreCase(var3)) {
               var2.togglePause();
            } else if ("stop".equalsIgnoreCase(var3) || "cancel".equalsIgnoreCase(var3)) {
               var2.stopDenick();
            } else if (ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 0) {
               boolean var4 = var1.getArgumentCount() > 1 && "brute".equalsIgnoreCase(var1.getArgument(var1.getArgumentCount() - 1));
               List var5 = var1.getArgumentCount() <= 1 ? Collections.emptyList() : Arrays.asList(var1.yeqp()).subList(1, var4 ? var1.getArgumentCount() - 1 : var1.getArgumentCount());
               var2.JiXv02(var3, var5, var4);
            } else {
               this.sendChatMessage("&7please denick from BedWars lobby.");
            }
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      Denick var2 = Jade.getModuleManager().getModule(Denick.class);
      if (var2 == null) {
         return super.getTabCompletions(var1);
      } else {
         List var3 = var1.getArgumentCount() <= 1 ? var2.RjNrev() : var2.getCosmeticCompletions(var1.getArgumentCount() > 2 ? var1.getArgument(var1.getArgumentCount() - 2) : "");
         if (var1.getArgumentCount() <= 1) {
            var3.add("pause");
            var3.add("stop");
         } else {
            var3.add("brute");
         }

         return this.completeFromList(var1, var3);
      }
   }
}
