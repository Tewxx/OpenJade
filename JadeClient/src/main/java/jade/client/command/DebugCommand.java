// Jade recovery: original class: jade.deps.eLz.kNhyctqtaJ
package jade.client.command;

import jade.client.common.CommandInput;
import jade.deps.loader107.InjectionPaths;
import java.io.File;
import java.io.IOException;

public class DebugCommand extends Command {
   public DebugCommand() {
      super("debug");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() == 0) {
         this.sendChatMessage("&7Debug " + this.SntI(DebugToggles.toggleProfiling()) + "&7.");
      } else if (var1.getArgumentCount() != 1) {
         this.qfZbqB();
      } else {
         String var2 = var1.getArgument(0).toLowerCase();
         switch (var2) {
            case "reload":
               this.startReloadProfile();
               break;
            case "mixin":
               this.sendChatMessage("&dMixin &7debug " + this.SntI(DebugToggles.toggleMixinDebug()) + "&7.");
               break;
            case "bg":
            case "background":
               this.sendChatMessage("&6Background &7debug " + this.SntI(DebugToggles.toggleBackgroundDebug()) + "&7.");
               break;
            default:
               this.qfZbqB();
         }
      }
   }

   private String SntI(boolean var1) {
      return var1 ? "&aenabled" : "&cdisabled";
   }

   private void startReloadProfile() {
      File var1 = new File(InjectionPaths.dataDirectory(mc.mcDataDir), "diagnostics");

      try {
         File var2 = ReloadProfiler.qcYw(Thread.currentThread(), var1);
         this.sendChatMessage("&aRecording the game thread for 90 seconds. Switch texture packs now.");
         this.vsuIw("&7Local profile: &f" + var2.getAbsolutePath());
      } catch (IOException var3) {
         this.sendChatMessage("&cCould not start reload profile: " + var3.getMessage());
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Debug options.");
      this.vsuIw(" &b" + this.withCommandPrefix("debug") + " &7Toggle general debug.");
      this.vsuIw(" &b" + this.withCommandPrefix("debug") + " mixin &7Toggle mixin debug.");
      this.vsuIw(" &b" + this.withCommandPrefix("debug") + " background &7Toggle background debug.");
      this.vsuIw(" &b" + this.withCommandPrefix("debug") + " reload &7Record a local 90-second texture-pack reload profile.");
   }
}
