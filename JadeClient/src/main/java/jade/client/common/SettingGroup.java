// Jade recovery: original class: jade.deps.eLz.M0hDHuWgK
package jade.client.common;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public final class SettingGroup {
   public final String title;
   public final Module module;
   public final BooleanSetting booleanSetting;
   public final List<Setting> settings = new ArrayList<>();
   public boolean expanded;

   public SettingGroup(String var1, Module var2, BooleanSetting var3) {
      this.title = var1;
      this.module = var2;
      this.booleanSetting = var3;
   }
}
