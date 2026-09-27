// Jade recovery: original class: jade.deps.eLz.NbE6HPv5
package jade.client.module.player.autodeflect;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

public final class AutoDeflectSettings {
   public final SliderSetting fov;
   public final SliderSetting range;
   public final SliderSetting cps;
   public final SliderSetting aimSpeed;
   public final MultiSelectSetting multiSelectSetting;
   public final BooleanSetting onGround;
   public final BooleanSetting sneak;

   public AutoDeflectSettings(Module var1) {
      this.fov = cxnZq(var1, new SliderSetting("FOV", 360.0, 30.0, 360.0, 4.0));
      this.range = cxnZq(var1, new SliderSetting("Range", 8.0, 3.0, 15.0, 0.5));
      this.cps = cxnZq(var1, new SliderSetting("CPS", 12.0, 1.0, 20.0, 0.5, new String[]{"Target CPS"}));
      this.aimSpeed = cxnZq(var1, new SliderSetting("Aim speed", 15.0, 1.0, 30.0, 1.0, new String[]{"Rotation speed", "Rotation Speed"}));
      this.onGround = new BooleanSetting(
         "On ground", false
      );
      this.sneak = new BooleanSetting("Sneak", false, new String[]{"Sneak while active"});
      String var10004 = "Conditions";
      String[] var10005 = new String[]{"On ground", "Sneak"};
      BooleanSetting[] var10006 = new BooleanSetting[2];
      var10006[0] = this.onGround;
      var10006[1] = this.sneak;
      this.multiSelectSetting = cxnZq(var1, new MultiSelectSetting(var10004, var10005, var10006));
      this.onGround.visible = false;
      this.sneak.visible = false;
      cxnZq(var1, this.onGround);
      cxnZq(var1, this.sneak);
   }

   private static <S extends Setting> S cxnZq(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
