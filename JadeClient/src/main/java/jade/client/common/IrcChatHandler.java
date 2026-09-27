// Jade recovery: original class: jade.deps.eLz.T6RgvZ
package jade.client.common;

import jade.client.Jade;
import jade.client.module.client.ChatCommands;
import jade.client.module.client.Settings;
import jade.deps.loader107.IrcMessageBus;
import jade.deps.loader107.SubscriptionState;

public final class IrcChatHandler {
   public static final String DEFAULT_PREFIX = "#";

   private IrcChatHandler() {
   }

   public static boolean handleChatInput(String var0) {
      String var1 = getActivePrefix();
      boolean var2 = startsWithPrefix(var0, var1);
      boolean var3 = Settings.defaultChatToIrc != null && Settings.defaultChatToIrc.isToggled();
      if (var2 || var3 && shouldRouteToIrc(var0, CgSmz())) {
         if (Settings.irc == null || Settings.irc.isToggled()) {
            String var4 = var2 ? stripPrefix(var0, var1) : IrcMessageBus.sanitizeMessage(var0.trim());
            if (var4.isEmpty()) {
               ClientUtils.sendJadeMessage("IRC", "&7usage: &f" + var1 + "message");
               return true;
            } else {
               String var5 = SubscriptionState.getDiscordUsername();
               if (var5 != null && !var5.trim().isEmpty()) {
                  IrcMessageBus.publishOutgoingMessage(var4);
                  return true;
               } else {
                  ClientUtils.sendJadeMessage("IRC", "&cbind Discord before using IRC.");
                  return true;
               }
            }
         } else if (var2) {
            ClientUtils.sendJadeMessage("IRC", "&cIRC is disabled.");
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static String getActivePrefix() {
      String var0 = Settings.ircPrefix == null ? "#" : Settings.ircPrefix.getValue();
      String var1 = CgSmz();
      if (isUsableIrcPrefix(var0, var1)) {
         return var0;
      } else {
         return "#".equals(var1) ? "@" : "#";
      }
   }

   public static boolean isValidPrefix(String var0) {
      return isUsableIrcPrefix(var0, CgSmz());
   }

   public static boolean matchesConfiguredPrefix(String var0) {
      return var0 != null && Settings.ircPrefix != null && var0.equals(Settings.ircPrefix.getValue());
   }

   public static boolean isUsableIrcPrefix(String var0, String var1) {
      if (var0 != null && var0.length() == 1) {
         char var2 = var0.charAt(0);
         return !Character.isWhitespace(var2) && !Character.isISOControl(var2) && var2 != '/' ? var1 == null || !var0.equals(var1) : false;
      } else {
         return false;
      }
   }

   public static boolean startsWithPrefix(String var0, String var1) {
      return var0 != null && var1 != null && !var1.isEmpty() && var0.startsWith(var1);
   }

   public static String stripPrefix(String var0, String var1) {
      return !startsWithPrefix(var0, var1) ? "" : IrcMessageBus.sanitizeMessage(var0.substring(var1.length()).trim());
   }

   public static boolean shouldRouteToIrc(String var0, String var1) {
      return var0 != null && !var0.trim().isEmpty() && !var0.startsWith("/") ? var1 == null || var1.isEmpty() || !var0.startsWith(var1) : false;
   }

   private static String CgSmz() {
      if (Jade.commandManager != null) {
         return Jade.commandManager.puqtxnC();
      } else {
         if (Jade.getModuleManager() != null) {
            ChatCommands var0 = Jade.getModuleManager().getModule(ChatCommands.class);
            if (var0 != null) {
               return var0.getCommandPrefix();
            }
         }

         return ".";
      }
   }
}
