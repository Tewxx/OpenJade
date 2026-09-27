// Jade recovery: module: Delay Remover (player); original class: jade.deps.eLz.BYJ9q9
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.Subscribe;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;

import jade.mixin.impl.accessor.IAccessorEntityLivingBase;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@ModuleInfo
public class DelayRemover extends Module {
   public BooleanSetting legacyHitreg;
   public BooleanSetting removeJumpTicks;

   public DelayRemover() {
      super("Delay Remover", Category.player, 0);
      this.registerSetting(
         this.legacyHitreg = new BooleanSetting(
            "1.7 hitreg", true
         )
      );
      this.registerSetting(this.removeJumpTicks = new BooleanSetting("Remove jump ticks", false));
      this.initialized = true;
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (mc.inGameHasFocus && ClientUtils.isInWorld()) {
         if (var1.eventPhase == EventPhase.END && this.legacyHitreg.isToggled()) {
            ((IAccessorMinecraft)mc).setLeftClickCounter(0);
         }

         if (var1.eventPhase == EventPhase.START && this.shouldRemoveJumpTicks(mc.thePlayer)) {
            ((IAccessorEntityLivingBase)mc.thePlayer).setJumpTicks(0);
         }
      }
   }

   public boolean shouldRemoveJumpTicks(EntityPlayerSP var1) {
      return this.isEnabled() && this.removeJumpTicks.isToggled() && var1 != null && (!var1.onGround || var1.movementInput == null || !var1.movementInput.jump);
   }
}
