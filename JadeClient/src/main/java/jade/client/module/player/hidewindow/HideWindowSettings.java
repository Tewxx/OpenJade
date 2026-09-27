// Jade recovery: original class: jade.deps.eLz.hjwi4qrDw7
package jade.client.module.player.hidewindow;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;
import jade.client.setting.StringListSetting;

public final class HideWindowSettings {
   public final ColorSetting iconColor;
   public final SliderSetting iconScale;
   public final BooleanSetting onlyWhileCrouching;
   public final BooleanSetting whitelist;
   public final StringListSetting whitelistNames;

   public HideWindowSettings(Module var1) {
      this.iconColor = rkeU(
         var1,
         new ColorSetting("Icon color", 255, 255, 255)
      );
      this.iconScale = rkeU(var1, new SliderSetting("Icon scale", 1.0, 0.5, 3.0, 0.1));
      this.iconScale.visible = false;
      this.onlyWhileCrouching = rkeU(var1, new BooleanSetting("Only while crouching", false));
      this.whitelist = rkeU(
         var1,
         new BooleanSetting("Whitelist", false)
      );
      this.whitelistNames = rkeU(
         var1,
         new StringListSetting(
            "Whitelist names",
            "e.g. Upgrades & Traps",
            128
         )
      );
      this.whitelistNames.visible = false;
   }

   private static <S extends Setting> S rkeU(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
