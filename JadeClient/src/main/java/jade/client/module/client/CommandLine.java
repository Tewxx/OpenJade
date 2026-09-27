// Jade recovery: module: Command line (client); original class: jade.deps.eLz.SVZmJrfUZ
package jade.client.module.client;

import jade.client.common.Animation;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.client.commandline.CommandLineHooks;
import jade.client.setting.BooleanSetting;

@ModuleInfo(listed = false)
public class CommandLine extends Module {
   public static boolean commandLineOpen;
   public static boolean commandLineClosing;
   public static Animation animation;
   public static BooleanSetting animate;

   public CommandLine() {
      super("Command line", Category.client);
      animate = new BooleanSetting("Animate", true);
      this.registerSetting(animate);
   }

   @Override
   public void onEnable() {
      CommandLineHooks.showCommandLine();
      commandLineOpen = true;
      commandLineClosing = false;
      animation = CommandLineHooks.createOpenAnimation();
   }

   @Override
   public void onDisable() {
      commandLineClosing = true;
      CommandLineHooks.hideCommandLine(animation);
   }
}
