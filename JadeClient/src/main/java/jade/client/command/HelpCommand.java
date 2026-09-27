// Jade recovery: original class: jade.deps.eLz.QzxHUbFxaD
package jade.client.command;

import jade.client.common.CommandInput;

public class HelpCommand extends Command {
   public HelpCommand() {
      super("help");
   }

   @Override
   public void execute(CommandInput var1) {
      String var2 = this.withCommandPrefix("");
      this.sendChatMessage("&7Commands");
      this.vsuIw(" &b" + var2 + "gui &7open ClickGUI");
      this.vsuIw(" &b" + var2 + "toggle [module] &7toggle module");
      this.vsuIw(" &b" + var2 + "bind [module] [key] &7set bind");
      this.vsuIw(" &b" + var2 + "hide [module/all] &7hide from HUD");
      this.vsuIw(" &b" + var2 + "show [module/all] &7show in HUD");
      this.vsuIw(" &b" + var2 + "config &7edit configs");
      this.vsuIw(" &b" + var2 + "danger confirm &7accept a safety warning");
      this.vsuIw(" &b" + var2 + "irc [toggle/sound/off] &7manage IRC chat");
      this.vsuIw(" &b" + var2 + "status <name> [key] &7poll BedWars status");
      this.vsuIw(" &b" + var2 + "lastseen&7/&b" + var2 + "ls <name> &7show latest lobby sighting");
      this.vsuIw(" &b" + var2 + "tracker&7/&b" + var2 + "track <name> &7live lobby joins/leaves");
      this.vsuIw(" &b" + var2 + "friend&7/&b" + var2 + "enemy &7player lists");
      this.vsuIw(" &b" + var2 + "add&7/&b" + var2 + "remove&7/&b" + var2 + "mode &7overlay");
      this.vsuIw(" &b" + var2 + "jenga &7spawn a playable physics tower");
   }

   @Override
   public void ahGlioN() {
      this.execute(new CommandInput("", "help", new String[0]));
   }
}
