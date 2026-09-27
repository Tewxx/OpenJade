// Jade recovery: module: Piercing (combat); original class: jade.deps.eLz.omdqdGXDCs
package jade.client.module.combat;

import jade.client.common.ClientUtils;
import jade.client.common.PointedObjectOverrider;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.piercing.PiercingCandidateFilter;
import jade.client.module.combat.piercing.PiercingSettings;
import jade.client.module.combat.piercing.ReachRaytrace$2;
import jade.client.module.combat.piercing.ReachRaytrace;
import java.util.List;

@ModuleInfo
public class Piercing extends Module implements PointedObjectOverrider {
   private final PiercingSettings piercingSettings = new PiercingSettings(this);

   public Piercing() {
      super("Piercing", Category.combat);
   }

   @Override
   public String getInfo() {
      return PiercingSettings.SORT_MODE_LABELS[(int)this.piercingSettings.sortMode.getInput()];
   }

   @Override
   public boolean shouldOverridePointedObject() {
      boolean var1 = mc != null && mc.thePlayer != null && mc.theWorld != null;
      return PiercingCandidateFilter.arePiercingConditionsMet(
         this.isEnabled(), var1, this.piercingSettings.weaponOnly.isToggled(), var1 && ClientUtils.isHoldingWeapon(), this.piercingSettings.ignoreBlocks.isToggled(), mc == null ? null : mc.objectMouseOver
      );
   }

   @Override
   public void applyPointedObjectOverride(float var1) {
      if (this.shouldOverridePointedObject()) {
         this.performReachRaytrace(var1);
      }
   }

   private void performReachRaytrace(float var1) {
      ReachRaytrace$2 var2 = ReachRaytrace.YUam(mc, var1, (int)this.piercingSettings.sortMode.getInput(), this.piercingSettings.ignoreNonPlayers.isToggled(), this.piercingSettings.ignoreTeammates.isToggled(), this.piercingSettings.insideHitboxOnly.isToggled());
      ReachRaytrace.applyRaytraceResult(mc, var2);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Target",
            "Sort mode",
            new String[]{"ignore blocks", "ignore non-players", "ignore teammates"},
            new String[]{"Through blocks", "Through non-players", "Through teammates"}
         ),
         buildSettingAlias("Conditions", "Target", new String[]{"weapon only", "inside hitbox only"}, new String[]{"Holding weapon", "Aiming within hitbox"})
      );
   }
}
