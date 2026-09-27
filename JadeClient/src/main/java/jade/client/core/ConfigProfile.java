// Jade recovery: original class: jade.deps.eLz.ctGxX907f
package jade.client.core;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.ConfigEntry;
import jade.client.gui.ClickGui;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.client.Settings;
import jade.client.setting.BooleanSetting;
import jade.client.setting.TextSetting;

public class ConfigProfile extends Module {
   private final ConfigEntry YWKxr6;
   private final TextSetting configName;
   private String profileName;
   public boolean unmodified = true;

   public ConfigProfile(ConfigEntry var1, String var2, int var3) {
      super(var2, Category.profiles, var3);
      this.YWKxr6 = var1;
      this.profileName = var2;
      this.registerSetting(
         this.configName = new TextSetting(
            "Config name",
            var2,
            "Type a new config name...",
            32,
            this::yjn1
         )
      );
      this.registerSetting(new BooleanSetting("Save config", this::saveConfig));
      this.registerSetting(new BooleanSetting("Remove config", this::removeConfig));
      this.registerSetting(new BooleanSetting("Set as startup config", () -> ConfigProfile.setStartupConfig(var1)));
   }

   @Override
   public void toggle() {
      if (mc.currentScreen instanceof ClickGui || mc.currentScreen == null) {
         Jade.configManager.loadProfile(this.getName());
         Jade.Grq = this.YWKxr6;
         if (Settings.sendMessageOnEnable.isToggled()) {
            ClientUtils.sendJadeMessage("Jade", "&7enabled config: &f" + this.getName());
         }

         this.unmodified = true;
      }
   }

   @Override
   public boolean isEnabled() {
      return Jade.Grq == null ? false : Jade.Grq.getProfile() == this;
   }

   @Override
   public String getName() {
      return this.profileName;
   }

   public void VPe8(String var1) {
      this.profileName = var1;
      this.configName.setValue(var1);
   }

   private void yjn1() {
      if (Jade.configManager != null) {
         String var1 = this.getName();
         if (Jade.configManager.renameProfile(this.YWKxr6, this.configName.getValue())) {
            this.configName.setValue(this.YWKxr6.getName());
            if (!var1.equals(this.YWKxr6.getName())) {
               ClientUtils.sendJadeMessage("Jade", "&7renamed config: &f" + var1 + " &7to &f" + this.YWKxr6.getName());
            }
         }
      }
   }

   private static void setStartupConfig(ConfigEntry var0) {
      Jade.configManager.setStartupProfile(var0.getName());
   }

   private void removeConfig() {
      String var1 = this.getName();
      if (Jade.configManager.deleteProfile(var1)) {
         ClientUtils.sendJadeMessage("Jade", "&7removed config: &f" + var1);
      }
   }

   private void saveConfig() {
      ClientUtils.sendJadeMessage("Jade", "&7saved config: &f" + this.getName());
      Jade.configManager.saveProfile(this.YWKxr6);
      this.unmodified = true;
   }
}
