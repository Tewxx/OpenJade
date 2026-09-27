// Jade recovery: original class: jade.deps.eLz.mNWmVI5X4
package jade.client.module.combat.hitselect;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

public final class HitSelectSettings {
   public final SliderSetting pauseDuration;
   public final SliderSetting mode;
   public final SliderSetting waitForFirstHit;
   public final BooleanSetting disableDuringKnockback;
   public final BooleanSetting onlyWhileDamaged;
   public final BooleanSetting useServerAttackTime;
   public final BooleanSetting fakeSwing;
   public final SliderSetting inCombat;
   public final SliderSetting missedSwings;

   public HitSelectSettings(Module var1) {
      registerSetting(var1, new DescriptionSetting("Filters unnecessary clicks."));
      this.pauseDuration = registerSetting(
         var1,
         new SliderSetting(
            "Pause duration", "ms", 500.0, 0.0, 500.0, 50.0
         )
      );
      this.mode = registerSetting(var1, new SliderSetting("Mode", HitSelectMode.BURST.ordinal(), HitSelectMode.labels()));
      this.waitForFirstHit = registerSetting(
         var1,
         new SliderSetting(
            "Wait for first hit", "ms", 0.0, 0.0, 500.0, 50.0
         )
      );
      this.disableDuringKnockback = registerSetting(var1, new BooleanSetting("Disable during knockback", false));
      this.onlyWhileDamaged = registerSetting(var1, new BooleanSetting("Only while damaged", false));
      this.useServerAttackTime = registerSetting(
         var1,
         new BooleanSetting(
            "Use server attack time",
            false
         )
      );
      this.fakeSwing = registerSetting(
         var1,
         new BooleanSetting(
            "Fake swing", false
         )
      );
      registerSetting(var1, new DescriptionSetting("Cancel rate"));
      this.inCombat = registerSetting(
         var1,
         new SliderSetting(
            "In combat", "%", 100.0, 0.0, 100.0, 1.0
         )
      );
      this.missedSwings = registerSetting(
         var1,
         new SliderSetting(
            "Missed swings", "%", 0.0, 0.0, 100.0, 1.0
         )
      );
   }

   private static <S extends Setting> S registerSetting(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
