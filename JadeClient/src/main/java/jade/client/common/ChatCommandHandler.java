// Jade recovery: original class: jade.deps.eLz.uR3niE
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Module;
import jade.client.module.client.Settings;

public final class ChatCommandHandler {
   private static final String UqY = "&cInvalid syntax.";

   private ChatCommandHandler() {
   }

   public static void handleChatCommand(String var0) {
      if (!var0.isEmpty()) {
         ChatCommandInput var1 = ChatCommandInput.parse(var0);
         String var2 = var1.znSty;
         if ("setkey".equals(var2)) {
            handleSetKey(var1.vlF);
         } else if ("ping".equals(var2)) {
            PingChecker.startPingCheck(false);
         } else if ("clear".equals(var2)) {
            FMMBeTEkVt.clearChatLines();
         } else if ("hide".equals(var2)) {
            handleHideShow(var1.vlF, true);
         } else if ("show".equals(var2)) {
            handleHideShow(var1.vlF, false);
         } else if ("friend".equals(var2) || "f".equals(var2)) {
            handleRelationCommand(var1.vlF, true);
         } else if ("enemy".equals(var2) || "e".equals(var2)) {
            handleRelationCommand(var1.vlF, false);
         } else if ("debug".equals(var2)) {
            Jade.profilingEnabled = !Jade.profilingEnabled;
            oKfoS("Debug " + (Jade.profilingEnabled ? "enabled" : "disabled") + ".", 1);
         } else if ("configs".equals(var2) || "config".equals(var2) || "c".equals(var2)) {
            handleConfigCommand(var1.vlF, var1.hasArguments);
         } else if (!"help".equals(var2) && !"?".equals(var2)) {
            oKfoS("&cInvalid command. (" + var1.getTruncatedInput() + ")", 1);
         } else {
            sendHelp();
         }
      }
   }

   private static void handleSetKey(String[] var0) {
      if (!dUkzq(var0, 2)) {
         oKfoS("&cInvalid syntax.", 1);
      } else {
         oKfoS("Setting...", 1);
         final String var1 = var0[1];
         Jade.getScheduler().execute(new Runnable() {
            @Override
            public void run() {
               if (HttpUtils.isValidHypixelApiKey(var1)) {
                  HttpUtils.yBgdIx = var1;
                  ChatCommandHandler.TMUe("&asuccess!", 0);
               } else {
                  ChatCommandHandler.TMUe("&cInvalid key.", 0);
               }
            }
         });
      }
   }

   private static void handleHideShow(String[] var0, boolean var1) {
      if (!dUkzq(var0, 2)) {
         oKfoS("&cInvalid syntax.", 1);
      } else {
         String var2 = var0[1].toLowerCase();

         for (Module var4 : Jade.getModuleManager().getModules()) {
            if (var4.getName().toLowerCase().replace(" ", "").equals(var2)) {
               var4.setHidden(var1);
               oKfoS("&a" + var4.getName() + " is now " + (var1 ? "hidden" : "visible") + " in HUD", 1);
            }
         }
      }
   }

   private static void handleRelationCommand(String[] var0, boolean var1) {
      if (!dUkzq(var0, 2)) {
         oKfoS("&cInvalid syntax.", 1);
      } else {
         String var2 = var0[1];
         if ("clear".equals(var2)) {
            if (var1) {
               Jade.relationManager.clearFriends();
            } else {
               Jade.relationManager.clearEnemies();
            }

            oKfoS(var1 ? "&aFriends cleared." : "&aEnemies cleared.", 1);
         } else {
            boolean var3 = var1 ? ClientUtils.addFriend(var2) : ClientUtils.addEnemy(var2);
            if (!var3) {
               if (var1) {
                  ClientUtils.removeFriend(var2);
               } else {
                  ClientUtils.removeEnemy(var2);
               }
            }

            String var4 = var1 ? "friend" : "enemy";
            oKfoS((var3 ? "&aAdded &7" : "&cRemoved &7") + var4 + ": &f" + var2, 1);
         }
      }
   }

   private static void handleConfigCommand(String[] var0, boolean var1) {
      if (!var1) {
         DjuhYr();
      } else if (var0 != null && var0.length > 1) {
         String var2 = var0[1];
         if ("save".equals(var2) || "s".equals(var2)) {
            saveConfig(var0);
         } else if ("load".equals(var2) || "l".equals(var2)) {
            loadConfig(var0);
         } else if ("remove".equals(var2) || "r".equals(var2)) {
            removeConfig(var0);
         }
      }
   }

   private static void DjuhYr() {
      oKfoS("&aAvailable configs:", 1);
      if (Jade.configManager.profiles.isEmpty()) {
         oKfoS("None", 0);
      } else {
         for (int var0 = 0; var0 < Jade.configManager.profiles.size(); var0++) {
            oKfoS(var0 + 1 + ". " + Jade.configManager.profiles.get(var0).getName(), 0);
         }
      }
   }

   private static void saveConfig(String[] var0) {
      if (!dUkzq(var0, 3)) {
         oKfoS("&cInvalid syntax.", 1);
      } else {
         String var1 = var0[2];
         if (var1.length() >= 2 && var1.length() <= 10) {
            Jade.configManager.saveProfile(new ConfigEntry(var1, 0));
            oKfoS("&aSaved config:", 1);
            oKfoS(var1, 0);
            Jade.configManager.loadProfiles();
         } else {
            oKfoS("&cInvalid name.", 1);
         }
      }
   }

   private static void loadConfig(String[] var0) {
      if (!dUkzq(var0, 3)) {
         oKfoS("&cInvalid syntax.", 1);
      } else {
         String var1 = var0[2];

         for (ConfigEntry var3 : Jade.configManager.profiles) {
            if (var3.getName().equals(var1)) {
               Jade.configManager.loadProfile(var3.getName());
               oKfoS("&aLoaded config:", 1);
               oKfoS(var1, 0);
               if (Settings.sendMessageOnEnable.isToggled()) {
                  ClientUtils.sendColoredMessage("&7Enabled config: &b" + var1);
               }

               return;
            }
         }

         oKfoS("&cInvalid config.", 1);
      }
   }

   private static void removeConfig(String[] var0) {
      if (!dUkzq(var0, 3)) {
         oKfoS("&cInvalid syntax.", 1);
      } else {
         String var1 = var0[2];

         for (ConfigEntry var3 : Jade.configManager.profiles) {
            if (var3.getName().equals(var1)) {
               Jade.configManager.deleteProfile(var3.getName());
               oKfoS("&aRemoved config:", 1);
               oKfoS(var1, 0);
               Jade.configManager.loadProfiles();
               return;
            }
         }

         oKfoS("&cInvalid config.", 1);
      }
   }

   private static void sendHelp() {
      String[] var0 = new String[]{
         "&eAvailable commands:",
         "1 setkey [key]",
         "2 friend/enemy [name/clear]",
         "3 ping",
         "4 hide/show [module]",
         "&eConfigs:",
         "1 configs",
         "2 configs save [config]",
         "3 configs load [config]",
         "4 configs remove [config]"
      };

      for (int var1 = 0; var1 < var0.length; var1++) {
         oKfoS(var0[var1], var1 == 0 ? 1 : 0);
      }
   }

   private static boolean dUkzq(String[] var0, int var1) {
      return var0 != null && var0.length == var1;
   }

   private static void oKfoS(String var0, int var1) {
      FMMBeTEkVt.appendChatLine(var0, var1);
   }

   public static void TMUe(String var0, int var1) {
      oKfoS(var0, var1);
   }
}
