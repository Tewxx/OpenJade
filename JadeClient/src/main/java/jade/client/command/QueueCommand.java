// Jade recovery: original class: jade.deps.eLz.BY8PzakIB
package jade.client.command;

import jade.client.common.ClientUtils;
import jade.client.common.CommandInput;

public class QueueCommand extends Command {
   public QueueCommand() {
      super("1s", "1s", "2s", "3s", "4s", "4v4");
   }

   @Override
   public void execute(CommandInput var1) {
      if (ClientUtils.isInWorld()) {
         QueueCommand$0 var2 = this.parseQueueMode(var1.getCommandName());
         if (var2 == null) {
            this.qfZbqB();
         } else {
            mc.thePlayer.sendChatMessage("/play " + QueueCommand$0.access$000(var2));
            this.sendChatMessage("&7queued " + QueueCommand$0.access$100(var2) + QueueCommand$0.access$200(var2) + "&7.");
         }
      }
   }

   private QueueCommand$0 parseQueueMode(String var1) {
      if ("1s".equalsIgnoreCase(var1)) {
         return QueueCommand$0.SOLOS;
      } else if ("2s".equalsIgnoreCase(var1)) {
         return QueueCommand$0.DOUBLES;
      } else if ("3s".equalsIgnoreCase(var1)) {
         return QueueCommand$0.THREES;
      } else if ("4s".equalsIgnoreCase(var1)) {
         return QueueCommand$0.FOURS;
      } else {
         return "4v4".equalsIgnoreCase(var1) ? QueueCommand$0.FOUR_V_FOUR : null;
      }
   }
}
