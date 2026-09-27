// Jade recovery: original class: jade.deps.eLz.AdtJDg
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingBinder {
   private ModuleSettingBinder() {
   }

   public static void bindSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
