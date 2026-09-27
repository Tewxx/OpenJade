// Jade recovery: original class: jade.deps.eLz.c5qhezXT
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Module;
import jade.client.module.client.ChatCommands;
import jade.client.module.client.Gui;
import jade.client.module.client.Rendering;
import jade.client.module.client.Settings;
import jade.client.setting.BooleanSetting;
import jade.client.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public final class SettingsPanelBuilder {
   public final List<SettingGroup> RpwI = new ArrayList<>();
   public final List<Setting> IAS = new ArrayList<>();
   public final Module module;

   public SettingsPanelBuilder() {
      Gui var1 = Jade.getModuleManager().getModule(Gui.class);
      Rendering var2 = Jade.getModuleManager().getModule(Rendering.class);
      Settings var3 = Jade.getModuleManager().getModule(Settings.class);
      ChatCommands var4 = Jade.getModuleManager().getModule(ChatCommands.class);
      this.module = var3;
      if (var1 != null) {
         SettingGroup var5 = new SettingGroup("GUI", var1, null);

         for (Setting var7 : var1.getSettings()) {
            if (var7 != Gui.clickGUIKey) {
               var5.settings.add(var7);
            }
         }

         this.RpwI.add(var5);
      }

      if (var2 != null) {
         SettingGroup var11 = new SettingGroup("Rendering", var2, null);
         var11.settings.addAll(var2.getSettings());
         this.RpwI.add(var11);
      }

      if (var3 != null) {
         SettingGroup var12 = new SettingGroup("IRC", var3, Settings.irc);
         var12.settings.add(Settings.ircSounds);
         var12.settings.add(Settings.defaultChatToIrc);
         var12.settings.add(Settings.ircPrefix);
         this.RpwI.add(var12);
         SettingGroup var14 = new SettingGroup("Weapons", var3, null);

         for (BooleanSetting var10 : Settings.multiSelectSetting.awwHd()) {
            if (var10 != null) {
               var14.settings.add(var10);
            }
         }

         this.RpwI.add(var14);
         SettingGroup var16 = new SettingGroup("System", var3, null);
         var16.settings.add(Settings.systemNotificationSounds);
         this.RpwI.add(var16);

         for (Setting var18 : var3.getSettings()) {
            if (var18.visible
               && var18 != Settings.irc
               && var18 != Settings.ircSounds
               && var18 != Settings.defaultChatToIrc
               && var18 != Settings.ircPrefix
               && var18 != Settings.multiSelectSetting
               && var18 != Settings.systemNotificationSounds) {
               this.IAS.add(var18);
            }
         }
      }

      if (var4 != null) {
         SettingGroup var13 = new SettingGroup("Chat Commands", var4, var4.chatCommands);
         var13.settings.add(var4.textSetting);
         this.RpwI.add(var13);
      }
   }
}
