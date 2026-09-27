// Jade recovery: original class: jade.deps.eLz.rYtW6R7
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class SettingRegistrationHelper {
   private SettingRegistrationHelper() {
   }

   public static void registerModuleSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
