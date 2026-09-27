// Jade recovery: original class: jade.deps.eLz.RJBaZjcO
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Module;
import jade.client.setting.BooleanSetting;

public final class BooleanSettingToggle {
   private BooleanSettingToggle() {
   }

   public static void toggleBooleanSetting(Module var0, BooleanSetting var1) {
      if (var1.isButton) {
         var1.runAction();
      } else {
         var1.toggle();
         var0.guiButtonToggled(var1);
         if (!var0.skipSettingsPersistence && Jade.Grq != null) {
            Jade.Grq.getProfile().unmodified = false;
         }
      }
   }
}
