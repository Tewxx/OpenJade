// Jade recovery: original class: jade.deps.eLz.LrR0Ob
package jade.client.misc;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class ModuleSettingApplier {
   private ModuleSettingApplier() {
   }

   public static void applySetting(Module var0, Setting var1) {
      var0.registerSetting(var1);
   }
}
