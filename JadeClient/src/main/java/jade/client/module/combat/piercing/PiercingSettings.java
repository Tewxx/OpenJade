// Jade recovery: original class: jade.deps.eLz.elWo8L5d3
package jade.client.module.combat.piercing;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

public final class PiercingSettings {
   public static final String[] SORT_MODE_LABELS = new String[]{"Hurt time", "Health"};
   public final SliderSetting sortMode;
   public final BooleanSetting ignoreBlocks;
   public final BooleanSetting ignoreTeammates;
   public final BooleanSetting ignoreNonPlayers;
   public final BooleanSetting weaponOnly;
   public final BooleanSetting insideHitboxOnly;

   public PiercingSettings(Module var1) {
      this.sortMode = registerSetting(
         var1,
         new SliderSetting("Sort mode", 0, SORT_MODE_LABELS)
      );
      this.ignoreBlocks = registerSetting(var1, new BooleanSetting("Ignore blocks", false));
      this.ignoreNonPlayers = registerSetting(var1, new BooleanSetting("Ignore non-players", true));
      this.ignoreTeammates = registerSetting(var1, new BooleanSetting("Ignore teammates", true));
      this.weaponOnly = registerSetting(var1, new BooleanSetting("Weapon only", false));
      this.insideHitboxOnly = registerSetting(
         var1,
         new BooleanSetting(
            "Inside hitbox only", false
         )
      );
   }

   private static <S extends Setting> S registerSetting(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
