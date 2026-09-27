// Jade recovery: original class: jade.deps.eLz.DyveFot
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingAttacher {
   private ModuleSettingAttacher() {
   }

   public static void attachSetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
