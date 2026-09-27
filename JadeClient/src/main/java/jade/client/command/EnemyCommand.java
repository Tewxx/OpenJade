// Jade recovery: original class: jade.deps.eLz.PjdfACtLT
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.RelationManager$0;
import jade.client.common.RelationManager$1;
import jade.client.common.CommandInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemyCommand extends Command {
   public EnemyCommand() {
      super("enemy", "enemy", "e");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 0) {
         if (var1.getArgumentCount() != 1) {
            this.qfZbqB();
         } else {
            String var5 = var1.getArgument(0);
            if ("clear".equalsIgnoreCase(var5)) {
               int var6 = Jade.relationManager.getRelationCount(RelationManager$1.ENEMY);
               Jade.relationManager.clearEnemies();
               this.sendChatMessage("&f" + var6 + "&7 enem" + (var6 == 1 ? "y" : "ies") + " cleared.");
            } else {
               if (ClientUtils.addEnemy(var5)) {
                  this.sendChatMessage("&aadded &7enemy: &f" + var5);
               } else {
                  ClientUtils.removeEnemy(var5);
                  this.sendChatMessage("&cremoved &7enemy: &f" + var5);
               }
            }
         }
      } else {
         List var2 = Jade.relationManager.Ayorz(RelationManager$1.ENEMY);
         this.sendChatMessage("&f" + var2.size() + "&7 enem" + (var2.size() == 1 ? "y" : "ies") + ".");

         for (RelationManager$0 var4 : (java.lang.Iterable<RelationManager$0>) (java.lang.Iterable<?>) (var2)) {
            this.sendChatMessage(" &f" + var4.ZNKxZ());
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      if (var1.getArgumentCount() > 1) {
         return Collections.emptyList();
      } else {
         String var2 = var1.getArgumentCount() == 0 ? "" : var1.getArgument(0);
         ArrayList var3 = new ArrayList();
         if (var2 == null || "clear".startsWith(var2.toLowerCase())) {
            var3.add("clear");
         }

         var3.addAll(PlayerNameCompleter.completePlayerNames(var2));
         return var3;
      }
   }
}
