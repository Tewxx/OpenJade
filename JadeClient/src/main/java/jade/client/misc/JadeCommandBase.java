// Jade recovery: original class: jade.deps.eLz.dwe3m7CAb
package jade.client.misc;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class JadeCommandBase extends CommandBase {
   private static final String BrR = "jade";
   private static final String COMMAND_USAGE = "/jade";

   public String getCommandName() {
      return "jade";
   }

   public void processCommand(ICommandSender var1, String[] var2) {
      esRkDNFsEy.requestClientGuiOpen();
   }

   public String getCommandUsage(ICommandSender var1) {
      return "/jade";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public boolean canCommandSenderUseCommand(ICommandSender var1) {
      return this.getRequiredPermissionLevel() == 0;
   }
}
