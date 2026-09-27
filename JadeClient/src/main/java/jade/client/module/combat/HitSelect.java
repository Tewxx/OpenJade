// Jade recovery: module: Hit Select (combat); original class: jade.deps.eLz.Lh8YuzlXw
package jade.client.module.combat;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.ClickMouseEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.hitselect.PlayerHitTracker;
import jade.client.module.combat.hitselect.HitSelectTracker;
import jade.client.module.combat.hitselect.HitSelectMode;
import jade.client.module.combat.hitselect.HitSelectSettings;
import jade.client.module.combat.hitselect.RaytraceHitClassifier$0;
import jade.client.module.combat.hitselect.RaytraceHitClassifier;
import jade.client.module.combat.hitselect.tIrjmLtOv;
import jade.client.module.shared.TargetFinder;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

@ModuleInfo
public class HitSelect extends Module {
   private static final double CRITICAL_FALL_DISTANCE = 3.0;
   private static final double vigo0 = 9.0;
   private static final int SERVER_ATTACK_WINDOW_TICKS = 10;
   private static final int otko = 10;
   private static final int HURT_TIME_TRACK_WINDOW_TICKS = 30;
   private static final int FLAG_FIRST_HIT_WAIT = 1;
   private static final int Qemq = 8;
   private static final int FLAG_HURT_TIME_WINDOW = 16;
   private static final int FLAG_CRITICAL_ATTACK = 32;
   private final HitSelectSettings hitSelectSettings;
   private final HitSelectTracker combatTracker = new HitSelectTracker();
   private int tickCounter;

   public HitSelect() {
      super("Hit Select", Category.combat);
      this.hitSelectSettings = new HitSelectSettings(this);
      this.initialized = true;
   }

   @Override
   public String getInfo() {
      return this.getCurrentMode().getLabel();
   }

   @Override
   public void onEnable() {
      this.tickCounter = 0;
      this.qjoj();
   }

   @Override
   public void onDisable() {
      this.qjoj();
   }

   @Subscribe
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      if (ClientUtils.isInWorld() && !mc.thePlayer.isDead && mc.theWorld != null) {
         this.tickCounter++;
         int var2 = this.tickCounter;
         this.combatTracker.pruneInvalidPlayers(mc.theWorld);
         EntityPlayer var3 = TargetFinder.findTarget(9.0);
         this.recordAttack(var3, var2);
         this.updateHurtTimeTracking(var2);
         this.trackAttackConfirmation(var2);
      } else {
         this.qjoj();
      }
   }

   @Subscribe
   public void onClickMouse(ClickMouseEvent var1) {
      if (this.kbyrwtb()) {
         int var2 = this.tickCounter;
         RaytraceHitClassifier$0 var3 = RaytraceHitClassifier.classifyRaytraceResult(var1.movingObjectPosition, 9.0);
         if (var3 != RaytraceHitClassifier$0.BLOCK) {
            if (var3 == RaytraceHitClassifier$0.MISS) {
               if (this.rollChance(this.hitSelectSettings.missedSwings.getInput())) {
                  this.cancelClick(var1);
               }
            } else {
               EntityPlayer var4 = TargetFinder.TDBVqmH(var1.movingObjectPosition == null ? null : var1.movingObjectPosition.entityHit, 9.0);
               if (var4 != null) {
                  this.recordAttack(var4, var2);
                  PlayerHitTracker var5 = this.VMsy(var4);
                  int var6 = this.computeAttackFlags(var5, var2);
                  boolean var7 = (var6 & 1) != 0 || (var6 & 16) != 0 || this.isPauseTriggered(var5, var6 & -17, var2);
                  if (var7 && this.rollChance(this.hitSelectSettings.inCombat.getInput())) {
                     this.cancelClick(var1);
                  } else {
                     this.ZUbdxZ0(var4, var2);
                  }
               }
            }
         }
      }
   }

   private boolean kbyrwtb() {
      return ClientUtils.isInWorld() && mc.theWorld != null && mc.thePlayer != null && !mc.thePlayer.isDead;
   }

   private void cancelClick(ClickMouseEvent var1) {
      if (this.hitSelectSettings.fakeSwing.isToggled() && ClientUtils.isInWorld()) {
         ClientUtils.startSwing();
      }

      var1.setCanceled(true);
   }

   private void recordAttack(EntityPlayer var1, int var2) {
      this.combatTracker.updateTrackedTarget(var1, var2, this.hitSelectSettings.useServerAttackTime.isToggled());
   }

   private void updateHurtTimeTracking(int var1) {
      int var2 = mc.thePlayer.hurtTime;
      EntityPlayer var3 = this.combatTracker.getTrackedTarget();
      if (this.combatTracker.getLocalPlayerState().YLCHh(var2, mc.thePlayer.onGround) && var3 != null) {
         this.VMsy(var3).LDYAtZ();
      }
   }

   private void trackAttackConfirmation(int var1) {
      EntityPlayer var2 = this.combatTracker.getTrackedTarget();
      if (var2 != null && this.hitSelectSettings.useServerAttackTime.isToggled()) {
         this.VMsy(var2).trackAttackConfirmation(var2.hurtTime, var1, 30, 8);
      }
   }

   private int computeAttackFlags(PlayerHitTracker var1, int var2) {
      if (this.combatTracker.getTrackedTarget() == null) {
         return 0;
      } else if (this.hitSelectSettings.disableDuringKnockback.isToggled() && this.isKnockbackActive()) {
         return 0;
      } else {
         int var3 = 0;
         if (this.hwrRxm(var2)) {
            var3 |= 1;
         }

         var3 |= this.computePauseFlags(var1, var2);
         if (this.isCriticalAttackReady(var1, var2)) {
            var3 |= 32;
         }

         return var3;
      }
   }

   private int computePauseFlags(PlayerHitTracker var1, int var2) {
      return var1.computeAttackTimingFlags(this.hitSelectSettings.useServerAttackTime.isToggled(), var2, tIrjmLtOv.millisToTicks(this.hitSelectSettings.pauseDuration.getInput()), 10, 8, 16);
   }

   private boolean isCriticalAttackReady(PlayerHitTracker var1, int var2) {
      if (this.getCurrentMode() != HitSelectMode.CRITICALS) {
         return false;
      } else if (mc.thePlayer.onGround) {
         return false;
      } else if (this.hitSelectSettings.onlyWhileDamaged.isToggled() && !var1.hasTakenDamage()) {
         return false;
      } else {
         return this.hitSelectSettings.disableDuringKnockback.isToggled() && this.isKnockbackActive() ? false : !this.canPerformCriticalHit();
      }
   }

   private HitSelectMode getCurrentMode() {
      return HitSelectMode.fromSetting(this.hitSelectSettings.mode.getInput());
   }

   private boolean hwrRxm(int var1) {
      return this.combatTracker.getLocalPlayerState().isWaitingForFirstHit(this.hitSelectSettings.waitForFirstHit.getInput(), this.combatTracker.getTrackedTarget() != null, var1);
   }

   private boolean canPerformCriticalHit() {
      return tIrjmLtOv.isCriticalSwing(
         mc.thePlayer.fallDistance,
         mc.thePlayer.onGround,
         mc.thePlayer.isOnLadder(),
         mc.thePlayer.isInWater(),
         mc.thePlayer.isPotionActive(Potion.blindness),
         mc.thePlayer.ridingEntity != null
      );
   }

   private boolean isKnockbackActive() {
      return this.combatTracker.getLocalPlayerState().isKnockbackActive(mc.thePlayer.hurtTime);
   }

   private boolean isPauseTriggered(PlayerHitTracker var1, int var2, int var3) {
      return var1.ngDbe(var2, var3, this.hitSelectSettings.pauseDuration.getInput());
   }

   private void ZUbdxZ0(EntityPlayer var1, int var2) {
      if (var1 != null) {
         this.recordAttack(var1, var2);
         this.VMsy(var1).onLocalAttack(this.hitSelectSettings.useServerAttackTime.isToggled(), var2, 10);
      }
   }

   private boolean rollChance(double var1) {
      return tIrjmLtOv.rollChancePercent(var1, Math.random());
   }

   private PlayerHitTracker VMsy(EntityPlayer var1) {
      return this.combatTracker.getOrCreatePlayerState(var1, this.hitSelectSettings.useServerAttackTime.isToggled());
   }

   private void qjoj() {
      this.combatTracker.resetAll();
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Conditions", "Mode", new String[]{"disable during knockback", "only while damaged"}, new String[]{"Not during knockback", "While damaged"}),
         buildSettingAlias("Action", "Conditions", new String[]{"use server attack time", "fake swing"}, new String[]{"Use server timing", "Fake swing"})
      );
   }
}
