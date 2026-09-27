// Jade recovery: original class: jade.deps.eLz.GtOBjv0Zz
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingInstaller {
   private ModuleSettingInstaller() {
   }

   public static void installSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
