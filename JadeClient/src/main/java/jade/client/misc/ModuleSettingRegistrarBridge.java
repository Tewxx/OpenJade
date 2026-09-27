// Jade recovery: original class: jade.deps.eLz.tr9L5xS
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingRegistrarBridge {
   private ModuleSettingRegistrarBridge() {
   }

   public static void registerModuleSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
