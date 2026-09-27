// Jade recovery: original class: jade.deps.eLz.XaFx1U
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingRegistration {
   private ModuleSettingRegistration() {
   }

   public static void registerModuleSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
