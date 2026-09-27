// Jade recovery: original class: jade.deps.eLz.KbqXOb
package jade.client.command;

import jade.client.common.CommandCompleter;
import jade.client.common.IMinecraft;
import jade.client.common.CommandInput;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Command implements IMinecraft {
   private final CommandAliases commandAliases;

   protected Command(String var1, String... var2) {
      this.commandAliases = new CommandAliases(var1, var2);
   }

   public final String getName() {
      return this.commandAliases.Ktja();
   }

   public final String[] getAliases() {
      return this.commandAliases.getAliasNames();
   }

   public final boolean matchesNameOrAlias(String var1) {
      return this.commandAliases.matches(var1);
   }

   public abstract void execute(CommandInput var1);

   public void ahGlioN() {
      this.sendChatMessage("&7That command needs different input. Use &b" + this.withCommandPrefix("help") + " &7for examples.");
   }

   public List<String> getTabCompletions(CommandInput var1) {
      return Collections.emptyList();
   }

   public int getArgumentOffset(CommandInput var1) {
      return Math.max(0, var1.getArgumentCount() - 1);
   }

   protected String getChatChannelName() {
      return "Jade";
   }

   protected final void sendChatMessage(String var1) {
      CommandFeedback.SNUAnH9(this.getChatChannelName(), var1);
   }

   protected final void vsuIw(String var1) {
      CommandFeedback.SNUAnH9(this.getChatChannelName(), var1);
   }

   protected final void qfZbqB() {
      this.ahGlioN();
   }

   protected final String withCommandPrefix(String var1) {
      return CommandFeedback.getCommandPrefix() + var1;
   }

   protected final List<String> completeFromList(CommandInput var1, List<String> var2) {
      return CommandCompleter.filterCompletions(var1, var2);
   }

   protected final List<String> completeFromArray(CommandInput var1, String... var2) {
      return CommandCompleter.filterCompletions(var1, Arrays.asList(var2));
   }
}
