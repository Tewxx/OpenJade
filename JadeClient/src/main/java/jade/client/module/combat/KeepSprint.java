// Jade recovery: module: Keep Sprint (combat); original class: jade.deps.eLz.ZJWc1OqK
package jade.client.module.combat;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.PreUpdateEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.keepsprint.KeepSprintState$0;
import jade.client.module.combat.keepsprint.KeepSprintState;
import jade.client.module.movement.NoSlow;
import jade.client.setting.SliderSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo(aliases = {"KeepSprint", "Keep Sprint"})
public class KeepSprint extends Module {
   private final KeepSprintState lF8 = new KeepSprintState();
   private final SliderSetting slowdown;

   public KeepSprint() {
      super("Keep Sprint", Category.combat);
      this.registerSetting(this.slowdown = new SliderSetting("Slowdown", 0.0, 0.0, 100.0, 1.0));
      this.slowdown.setSuffix("%");
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.resetPredictionState();
   }

   @Override
   public void onDisable() {
      this.resetPredictionState();
   }

   @Override
   public String getInfo() {
      return "Prediction";
   }

   public void applySprintSlowdown(EntityPlayer var1) {
      if (var1 != null && var1.isSprinting()) {
         if (this.lF8.isAwaitingRestore()) {
            double var2 = 1.0 - 0.4 * this.slowdown.getInput() / 100.0;
            var1.motionX *= var2;
            var1.motionZ *= var2;
            if ((int)this.slowdown.getInput() == 60) {
               var1.setSprinting(false);
            }
         } else {
            var1.motionX *= 0.6;
            var1.motionZ *= 0.6;
            var1.setSprinting(false);
         }
      }
   }

   @Subscribe(priority = EventPriority.NORMAL)
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         this.lF8.clearTickFlag();
      }
   }

   public boolean shouldCancelAttack(EntityPlayer var1, Entity var2) {
      if (ClientUtils.isInWorld() && var1 == mc.thePlayer) {
         KeepSprintState$0 var3 = this.lF8.onAttack(var2 instanceof EntityPlayer, mc.thePlayer.isSprinting());
         if (var3 == KeepSprintState$0.CANCEL_ATTACK) {
            return true;
         } else {
            this.CSgX1(var3);
            return false;
         }
      } else {
         return false;
      }
   }

   @Subscribe(priority = EventPriority.LOW)
   public void onPreUpdate(PreUpdateEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.resetPredictionState();
      } else {
         this.CSgX1(this.lF8.advanceState());
      }
   }

   public void applyPredictedSprintState(EntityPlayer var1) {
      if (ClientUtils.isInWorld() && var1 == mc.thePlayer) {
         this.CSgX1(this.lF8.getCurrentSprintAction());
      }
   }

   private boolean Vvguv3() {
      return !mc.thePlayer.isUsingItem()
         ? true
         : mc.thePlayer.moveForward > 0.0F
            && !mc.thePlayer.isSneaking()
            && !mc.thePlayer.isCollidedHorizontally
            && mc.thePlayer.getFoodStats().getFoodLevel() > 6
            && NoSlow.isVanillaModeActive();
   }

   private void CSgX1(KeepSprintState$0 var1) {
      if (var1 == KeepSprintState$0.STOP_SPRINT) {
         mc.thePlayer.setSprinting(false);
      } else if (var1 == KeepSprintState$0.RESTORE_SPRINT && this.Vvguv3()) {
         mc.thePlayer.setSprinting(true);
      }
   }

   private void resetPredictionState() {
      this.lF8.YWGs();
   }
}
