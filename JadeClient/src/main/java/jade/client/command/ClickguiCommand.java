// Jade recovery: original class: jade.deps.eLz.pvO9gB
package jade.client.command;

import jade.client.Jade;
import jade.client.common.CommandInput;

public class ClickguiCommand extends Command {
   public ClickguiCommand() {
      super("clickgui", "gui");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() > 0) {
         this.ahGlioN();
      } else {
         Jade.requestOpenClickGui();
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Open ClickGUI: &b" + this.withCommandPrefix("clickgui") + " &7or &b" + this.withCommandPrefix("gui"));
   }
}
