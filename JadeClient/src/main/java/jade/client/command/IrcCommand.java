// Jade recovery: original class: jade.deps.eLz.sadMxCRD
package jade.client.command;

import jade.client.common.CommandInput;
import jade.client.module.client.Settings;
import java.util.List;

public class IrcCommand extends Command {
   public IrcCommand() {
      super("irc");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.sendChatMessage("&7IRC " + this.describeEnabledState(this.isIrcEnabled()) + "&7, default chat " + this.describeEnabledState(this.isDefaultChatToIrc()) + "&7, sounds " + this.describeEnabledState(this.KdSu()) + "&7.");
         this.sendIrcUsage();
      } else if (var1.getArgumentCount() != 1) {
         this.ahGlioN();
      } else {
         String var2 = var1.getArgument(0);
         if ("toggle".equalsIgnoreCase(var2)) {
            this.toggleDefaultChatToIrc();
         } else if ("sound".equalsIgnoreCase(var2)) {
            this.toggleIrcSounds();
         } else if ("off".equalsIgnoreCase(var2)) {
            this.toggleIrc();
         } else {
            this.ahGlioN();
         }
      }
   }

   @Override
   public List<String> getTabCompletions(CommandInput var1) {
      return this.completeFromArray(var1, "toggle", "sound", "off");
   }

   @Override
   public void ahGlioN() {
      this.sendIrcUsage();
   }

   @Override
   protected String getChatChannelName() {
      return "IRC";
   }

   private void sendIrcUsage() {
      this.sendChatMessage("&b" + this.withCommandPrefix("irc toggle") + " &7default chat, &b" + this.withCommandPrefix("irc sound") + " &7sounds, &b" + this.withCommandPrefix("irc off") + " &7IRC on/off");
   }

   private void toggleDefaultChatToIrc() {
      if (Settings.defaultChatToIrc == null) {
         this.sendIrcUnavailableMessage();
      } else {
         Settings.defaultChatToIrc.toggle();
         this.sendChatMessage(Settings.defaultChatToIrc.isToggled() ? "&aNormal chat now sends to IRC." : "&cNormal chat now sends to server chat.");
      }
   }

   private void toggleIrcSounds() {
      if (Settings.ircSounds == null) {
         this.sendIrcUnavailableMessage();
      } else {
         Settings.ircSounds.toggle();
         this.sendChatMessage("&7IRC sounds " + this.describeEnabledState(Settings.ircSounds.isToggled()) + "&7.");
      }
   }

   private void toggleIrc() {
      if (Settings.irc == null) {
         this.sendIrcUnavailableMessage();
      } else {
         Settings.irc.toggle();
         this.sendChatMessage("&7IRC " + this.describeEnabledState(Settings.irc.isToggled()) + "&7.");
      }
   }

   private void sendIrcUnavailableMessage() {
      this.sendChatMessage("&cIRC settings are unavailable.");
   }

   private String describeEnabledState(boolean var1) {
      return var1 ? "&aenabled" : "&cdisabled";
   }

   private boolean isIrcEnabled() {
      return Settings.irc == null || Settings.irc.isToggled();
   }

   private boolean isDefaultChatToIrc() {
      return Settings.defaultChatToIrc != null && Settings.defaultChatToIrc.isToggled();
   }

   private boolean KdSu() {
      return Settings.ircSounds == null || Settings.ircSounds.isToggled();
   }
}
