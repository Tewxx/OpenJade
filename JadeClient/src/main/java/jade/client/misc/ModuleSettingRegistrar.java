// Jade recovery: original class: jade.deps.eLz.tFHVOu14w
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingRegistrar {
   private ModuleSettingRegistrar() {
   }

   public static void registerSettingOn(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
