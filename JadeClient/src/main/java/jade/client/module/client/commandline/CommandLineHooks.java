// Jade recovery: original class: jade.deps.eLz.JQptMEMjx
package jade.client.module.client.commandline;

import jade.client.common.Animation;
import jade.client.common.CommandLineBridge;

public final class CommandLineHooks {
   private CommandLineHooks() {
   }

   public static void showCommandLine() {
      CommandLineBridge.pickConsoleBackgroundColor();
   }

   public static Animation createOpenAnimation() {
      Animation var0 = new Animation(500.0F);
      var0.restart();
      return var0;
   }

   public static void hideCommandLine(Animation var0) {
      if (var0 != null) {
         var0.restart();
      }

      CommandLineBridge.resetPingCheck();
   }
}
