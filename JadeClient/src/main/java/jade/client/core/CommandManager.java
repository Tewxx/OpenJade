// Jade recovery: original class: jade.deps.eLz.SkboWO
package jade.client.core;

import jade.client.Jade;
import jade.client.command.Command;
import jade.client.common.ClientUtils;
import jade.client.common.CommandCompleter;
import jade.client.common.CommandInput;
import jade.client.module.client.ChatCommands;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
   private final List<Command> commands = CommandRegistry.createCommandList();

   public List<Command> getCommands() {
      return new ArrayList<>(this.commands);
   }

   private ChatCommands getChatCommandsModule() {
      return Jade.getModuleManager().getModule(ChatCommands.class);
   }

   public boolean FUKg() {
      ChatCommands var1 = this.getChatCommandsModule();
      return var1 != null && var1.isEnabled();
   }

   public String puqtxnC() {
      ChatCommands var1 = this.getChatCommandsModule();
      return var1 == null ? "." : var1.getCommandPrefix();
   }

   public String applyLowercaseSetting(String var1) {
      ChatCommands var2 = this.getChatCommandsModule();
      return var2 != null && var2.isLowercaseEnabled() ? var1.toLowerCase() : var1;
   }

   public boolean isCommandMessage(String var1) {
      return var1 != null && this.FUKg() && var1.startsWith(this.puqtxnC());
   }

   private CommandInput parseArguments(String var1, boolean var2) {
      return !this.isCommandMessage(var1) ? null : CommandLineParser.parse(var1, this.puqtxnC(), var2);
   }

   private Command findCommandByName(String var1) {
      return this.commands.stream().filter((recoveredArg0) -> CommandManager.matchesCommandName(var1, (jade.client.command.Command) recoveredArg0)).findFirst().orElse(null);
   }

   public boolean DCXw(String var1) {
      if (!this.isCommandMessage(var1)) {
         return false;
      } else {
         CommandInput var2 = this.parseArguments(var1, false);
         if (var2 == null) {
            return true;
         } else {
            Command var3 = this.findCommandByName(var2.getCommandName());
            if (var3 != null) {
               var3.execute(var2);
            } else {
               ClientUtils.sendJadeMessage("Jade", this.applyLowercaseSetting("&cunknown command. use tab to browse command suggestions."));
            }

            return true;
         }
      }
   }

   public String[] getCompletions(String var1) {
      if (!this.isCommandMessage(var1)) {
         return new String[0];
      } else {
         CommandInput var2 = this.parseArguments(var1, true);
         if (var2 == null) {
            return this.findCommandNameCompletions("");
         } else {
            Command var3 = this.findCommandByName(var2.getCommandName());
            if (var3 == null) {
               return this.findCommandNameCompletions(var2.getCommandName());
            } else {
               if (var2.getArgumentCount() == 0 && !CommandLineParser.endsWithWhitespace(var1)) {
                  String[] var4 = this.findCommandNameCompletions(var2.getCommandName());
                  if (Arrays.stream(var4).anyMatch((recoveredArg0) -> CommandManager.LviBjf(var1, (java.lang.String) recoveredArg0))) {
                     return var4;
                  }
               }

               return CommandCompleter.buildCommandCompletions(var3, var2, var3.getTabCompletions(var2), this::puqtxnC);
            }
         }
      }
   }

   private String[] findCommandNameCompletions(String var1) {
      return CommandCompleter.completeCommandNames(this.commands, var1, this::puqtxnC);
   }

   public String getFirstCompletion(String var1) {
      String[] var2 = this.getCompletions(var1);
      return var2.length == 0 ? "" : var2[0];
   }

   public String[] getSuggestion(String var1) {
      String var2 = this.getFirstCompletion(var1);
      return !var2.isEmpty() && !var2.equalsIgnoreCase(var1) ? new String[]{var2} : new String[0];
   }

   private static boolean LviBjf(String var0, String var1) {
      return !var1.equalsIgnoreCase(var0);
   }

   private static boolean matchesCommandName(String var0, Command var1) {
      return var1.matchesNameOrAlias(var0);
   }
}
