// Jade recovery: original class: jade.deps.eLz.edy7VtnA6
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;

public final class CommandFeedback {
   private CommandFeedback() {
   }

   public static String getCommandPrefix() {
      return Jade.commandManager == null ? "." : Jade.commandManager.puqtxnC();
   }

   public static void SNUAnH9(String var0, String var1) {
      String var2 = Jade.commandManager == null ? var1 : Jade.commandManager.applyLowercaseSetting(var1);
      ClientUtils.sendJadeMessage(var0, var2);
   }
}
