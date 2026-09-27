// Jade recovery: original class: jade.deps.eLz.RogHVc
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingForwarder {
   private ModuleSettingForwarder() {
   }

   public static void registerModuleSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
