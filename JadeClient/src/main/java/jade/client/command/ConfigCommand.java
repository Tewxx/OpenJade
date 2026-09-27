// Jade recovery: original class: jade.deps.eLz.WcVXcLOD
package jade.client.command;

import jade.client.Jade;
import jade.client.common.CommandInput;
import jade.client.common.ConfigEntry;
import java.util.ArrayList;
import java.util.List;

public class ConfigCommand extends Command {
   public ConfigCommand() {
      super("config", "configs", "c");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.ahGlioN();
      } else {
         switch (ConfigSubcommand.parseSubcommand(var1.getArgument(0))) {
            case LIST:
               this.handleList();
               break;
            case SAVE:
               this.handleSave(var1);
               break;
            case LOAD:
               this.handleLoad(var1);
               break;
            case DELETE:
               this.JnPc(var1);
               break;
            case RENAME:
               this.handleRename(var1);
               break;
            case STARTUP:
               this.handleStartup(var1);
               break;
            default:
               this.ahGlioN();
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      if (var1.getArgumentCount() == 1) {
         return this.completeFromList(var1, ConfigSubcommand.getSubcommandNames());
      } else {
         ConfigSubcommand$0 var2 = ConfigSubcommand.parseSubcommand(var1.getArgument(0));
         return (List<String>)(ConfigSubcommand.acceptsConfigName(var2, var1.getArgumentCount()) ? this.completeConfigNames(var1.joinArgumentsFrom(1)) : new ArrayList<>());
      }
   }

   @Override
   public int getArgumentOffset(CommandInput var1) {
      return ConfigSubcommand.takesConfigNameArgument(ConfigSubcommand.parseSubcommand(var1.getArgument(0))) ? 1 : super.getArgumentOffset(var1);
   }

   private void handleSave(CommandInput var1) {
      if (var1.getArgumentCount() < 2) {
         this.showSaveUsage();
      } else {
         String var2 = var1.joinArgumentsFrom(1);
         ConfigEntry var3 = Jade.configManager.jnkYch(var2);
         if (var3 == null) {
            var3 = Jade.configManager.dvuld(var2, 0);
            if (var3 == null) {
               return;
            }
         } else {
            Jade.configManager.saveProfile(var3);
         }

         this.sendChatMessage("&7saved config: &f" + var2);
      }
   }

   private void handleLoad(CommandInput var1) {
      if (var1.getArgumentCount() < 2) {
         this.showLoadUsage();
      } else {
         String var2 = var1.joinArgumentsFrom(1);
         if (Jade.configManager.jnkYch(var2) == null) {
            this.sendChatMessage("&f" + var2 + " &7does not exist.");
         } else {
            Jade.configManager.loadProfile(var2);
            this.sendChatMessage("&7loaded config: &f" + var2);
         }
      }
   }

   private void JnPc(CommandInput var1) {
      if (var1.getArgumentCount() < 2) {
         this.showDeleteUsage();
      } else {
         String var2 = var1.joinArgumentsFrom(1);
         if (Jade.configManager.jnkYch(var2) == null) {
            this.sendChatMessage("&cconfig &f" + var2 + " &7does not exist.");
         } else {
            Jade.configManager.deleteProfile(var2);
            this.sendChatMessage("&7removed config: &f" + var2);
            Jade.configManager.loadProfiles();
         }
      }
   }

   private void handleStartup(CommandInput var1) {
      if (var1.getArgumentCount() < 2) {
         String var3 = Jade.configManager.yrtW();
         this.sendChatMessage("&7startup config: &f" + var3);
         this.sendChatMessage("&7use &b" + this.withCommandPrefix("config") + " startup [name] &7to set it.");
      } else {
         String var2 = var1.joinArgumentsFrom(1);
         Jade.configManager.setStartupProfile(var2);
      }
   }

   private void handleRename(CommandInput var1) {
      if (var1.getArgumentCount() < 3) {
         this.showRenameUsage();
      } else {
         CommandInputMatch var2 = CommandInputMatch.matchLongestName(var1.joinArgumentsFrom(1), Jade.configManager.profiles);
         if (var2 == null) {
            this.sendChatMessage("&7Rename needs the old config name, then the new config name.");
         } else {
            String var3 = var2.Nq7;
            String var4 = var2.QWa;
            ConfigEntry var5 = Jade.configManager.jnkYch(var3);
            if (var5 == null) {
               this.sendChatMessage("&f" + var3 + " &7does not exist.");
            } else if (Jade.configManager.jnkYch(var4) != null) {
               this.sendChatMessage("&f" + var4 + " &7already exists.");
            } else if (Jade.configManager.renameProfile(var5, var4)) {
               this.sendChatMessage("&7renamed config &f" + var3 + " &7to &f" + var5.getName());
            }
         }
      }
   }

   private List<String> completeConfigNames(String var1) {
      String var2 = var1 == null ? "" : var1.toLowerCase();
      ArrayList var3 = new ArrayList();

      for (ConfigEntry var5 : Jade.configManager.profiles) {
         if (var5.getName().toLowerCase().startsWith(var2)) {
            var3.add(var5.getName());
         }
      }

      return var3;
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Edit configs: &b" + this.withCommandPrefix("config") + " [list/save/load/delete/rename]");
   }

   private void handleList() {
      List var1 = Jade.configManager.profiles;
      this.sendChatMessage("&f" + var1.size() + "&7 config" + (var1.size() == 1 ? "" : "s") + ".");

      for (ConfigEntry var3 : (java.lang.Iterable<ConfigEntry>) (java.lang.Iterable<?>) (var1)) {
         this.sendChatMessage(" &f" + var3.getName() + (var3 == Jade.Grq ? " &7(&fcurrent&7)" : ""));
      }
   }

   private void showSaveUsage() {
      this.sendChatMessage("&7Save config: &b" + this.withCommandPrefix("config") + " save [name]");
   }

   private void showLoadUsage() {
      this.sendChatMessage("&7Load config: &b" + this.withCommandPrefix("config") + " load [name]");
   }

   private void showDeleteUsage() {
      this.sendChatMessage("&7Delete config: &b" + this.withCommandPrefix("config") + " delete [name]");
   }

   private void showRenameUsage() {
      this.sendChatMessage("&7Rename config: &b" + this.withCommandPrefix("config") + " rename [old] [new]");
   }
}
