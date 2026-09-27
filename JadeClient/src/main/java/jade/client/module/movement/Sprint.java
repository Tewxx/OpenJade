// Jade recovery: module: Sprint (movement); original class: jade.deps.eLz.EqHKzUA
package jade.client.module.movement;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.Subscribe;
import jade.client.event.ClickMouseEvent;
import jade.client.event.JumpEvent;
import jade.client.event.TickEndEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.Reduce;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo
public class Sprint extends Module {
   private static final double dKkihh = 100.0;
   private final BooleanSetting disableSprintInAir;
   private final BooleanSetting onlyWhilstChasing;
   private boolean airborneAfterJump;
   private boolean jumpTickPending;
   private boolean VkbH;
   private boolean gYo;

   public Sprint() {
      super("Sprint", Category.movement, 0);
      this.registerSetting(
         this.disableSprintInAir = new BooleanSetting(
            "Disable Sprint in Air", false
         )
      );
      this.registerSetting(
         this.onlyWhilstChasing = new BooleanSetting(
            "Only Whilst Chasing",
            false
         )
      );
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      this.onlyWhilstChasing.setVisible(this.disableSprintInAir.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.resetSprintState();
   }

   @Override
   public void onDisable() {
      this.resetSprintState();
      if (ClientUtils.isInWorld()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
      }
   }

   @Override
   public void onUpdate() {
      if (ClientUtils.isInWorld()) {
         if (mc.inGameHasFocus) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         if (mc.thePlayer.onGround || mc.thePlayer.capabilities.isFlying) {
            this.resetSprintState();
         } else if (this.shouldSuppressSprint() && !this.gYo && mc.thePlayer.isSprinting()) {
            mc.thePlayer.setSprinting(false);
         }
      }
   }

   @Subscribe
   public void onJump(JumpEvent var1) {
      if (var1.getEntityLiving() == mc.thePlayer && this.HmVp66()) {
         this.airborneAfterJump = true;
         this.jumpTickPending = true;
         this.VkbH = false;
         this.gYo = var1.isSprinting();
      }
   }

   @Subscribe
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
      if (this.shouldSuppressSprint()) {
         if (this.jumpTickPending && this.gYo) {
            this.gYo = false;
         } else {
            var1.GLd2(false);
            if (mc.thePlayer.isSprinting()) {
               mc.thePlayer.setSprinting(false);
            }

            if (this.jumpTickPending) {
               this.jumpTickPending = false;
               this.VkbH = true;
            }
         }
      }
   }

   @Subscribe
   public void onClickMouse(ClickMouseEvent var1) {
      if (this.eccwaM(var1.movingObjectPosition)) {
         var1.setCanceled(true);
      }
   }

   public boolean shouldSuppressSprint() {
      return this.isEnabled()
         && this.disableSprintInAir.isToggled()
         && this.airborneAfterJump
         && ClientUtils.isInWorld()
         && !mc.thePlayer.onGround
         && !mc.thePlayer.capabilities.isFlying
         && !this.isReduceActive();
   }

   private boolean HmVp66() {
      return this.isEnabled()
         && this.disableSprintInAir.isToggled()
         && ClientUtils.isInWorld()
         && mc.currentScreen == null
         && mc.inGameHasFocus
         && !mc.thePlayer.capabilities.isFlying
         && !this.isReduceActive()
         && this.hasNearbyTarget();
   }

   private boolean isReduceActive() {
      Reduce var1 = Jade.getModuleManager().getModule(Reduce.class);
      return var1 != null && var1.shouldCounterAttack();
   }

   private boolean hasNearbyTarget() {
      return this.onlyWhilstChasing.isToggled() ? TargetFinder.EmbK(100.0) != null : TargetFinder.findTarget(100.0) != null;
   }

   private boolean eccwaM(MovingObjectPosition var1) {
      return this.shouldSuppressSprint() && !this.VkbH && var1 != null && var1.typeOfHit == MovingObjectType.ENTITY;
   }

   private void resetSprintState() {
      this.airborneAfterJump = false;
      this.jumpTickPending = false;
      this.VkbH = false;
      this.gYo = false;
   }
}
