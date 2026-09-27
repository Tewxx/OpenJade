// Jade recovery: original class: jade.deps.eLz.M7Rg32KPT
package jade.client.command;

import jade.client.common.PingChecker;
import jade.client.common.CommandInput;

public class PingCommand extends Command {
   public PingCommand() {
      super("ping");
   }

   @Override
   public void execute(CommandInput var1) {
      this.checkPing();
   }

   private void checkPing() {
      PingChecker.startPingCheck(true);
   }
}
