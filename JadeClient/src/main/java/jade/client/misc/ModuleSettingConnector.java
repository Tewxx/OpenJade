// Jade recovery: original class: jade.deps.eLz.WVTToXpY
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingConnector {
   private ModuleSettingConnector() {
   }

   public static void connectSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
