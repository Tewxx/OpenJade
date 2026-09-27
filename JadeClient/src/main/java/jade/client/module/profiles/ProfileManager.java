// Jade recovery: module: Manager (profiles); original class: jade.deps.eLz.TDbtFsXYT6
package jade.client.module.profiles;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.ConfigEntry;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.TextSetting;

import java.awt.Desktop;
import java.io.IOException;

@ModuleInfo
public class ProfileManager extends Module {
   private final TextSetting configName;
   private BooleanSetting loadConfigs;
   private BooleanSetting openFolder;
   private BooleanSetting createConfig;

   public ProfileManager() {
      super("Manager", Category.profiles);
      this.registerSetting(
         this.configName = new TextSetting(
            "Config name",
            "",
            "Type a config name...",
            32,
            this::createNamedConfig
         )
      );
      this.registerSetting(this.createConfig = new BooleanSetting("Create config", this::onCreateConfigToggled));
      this.registerSetting(this.loadConfigs = new BooleanSetting("Load configs", ProfileManager::loadAllConfigs));
      this.registerSetting(this.openFolder = new BooleanSetting("Open folder", ProfileManager::rMuqM));
      this.skipSettingsPersistence = true;
      this.canBeEnabled = false;
      this.hidden = true;
   }

   private void createNamedConfig() {
      if (ClientUtils.isInWorld() && Jade.configManager != null) {
         ConfigEntry var1 = Jade.configManager.dvuld(this.configName.getValue(), 0);
         if (var1 != null) {
            this.configName.setValue("");
            ClientUtils.sendColoredMessage("&7Created config: &b" + var1.getName());
         }
      }
   }

   private static void rMuqM() {
      try {
         Desktop.getDesktop().open(Jade.configManager.file);
      } catch (IOException var1) {
         Jade.configManager.file.mkdirs();
         ClientUtils.sendColoredMessage("&cError locating folder, recreated.");
      }
   }

   private static void loadAllConfigs() {
      if (ClientUtils.isInWorld() && Jade.configManager != null) {
         Jade.configManager.loadProfiles();
      }
   }

   private void onCreateConfigToggled() {
      this.createNamedConfig();
   }
}
