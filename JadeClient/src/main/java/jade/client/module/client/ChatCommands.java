// Jade recovery: module: Chat Commands (client); original class: jade.deps.eLz.khA88rwtn
package jade.client.module.client;

import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.PrefixSetting;
import jade.client.setting.TextSetting;

@ModuleInfo
public class ChatCommands extends Module {
   public static final String XZd = ".";
   public final BooleanSetting chatCommands = new BooleanSetting("Chat commands", true);
   public final BooleanSetting lowercase;
   public final TextSetting textSetting;

   public ChatCommands() {
      super("Chat Commands", Category.client);
      this.chatCommands.onChange(this::onChatCommandsToggled);
      this.registerSetting(this.chatCommands);
      this.textSetting = new PrefixSetting();
      this.registerSetting(this.textSetting);
      this.lowercase = new BooleanSetting(
         "Lowercase", true
      );
      this.setHidden(true);
      this.setEnabled(this.chatCommands.isToggled());
      this.canBeEnabled = false;
   }

   @Override
   public void setHidden(boolean var1) {
      super.setHidden(true);
   }

   public boolean isLowercaseEnabled() {
      return this.lowercase.isToggled();
   }

   public static boolean isValidPrefix(String var0) {
      return var0 != null && var0.length() == 1 ? !Character.isWhitespace(var0.charAt(0)) : false;
   }

   public String getCommandPrefix() {
      String var1 = this.textSetting.getValue();
      return isValidPrefix(var1) ? var1 : ".";
   }

   public void haKnp68(String var1) {
      if (isValidPrefix(var1)) {
         this.textSetting.setValue(var1);
      }
   }

   private void onChatCommandsToggled() {
      this.setEnabled(this.chatCommands.isToggled());
   }
}
