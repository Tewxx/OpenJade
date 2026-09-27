// Jade recovery: module: Auto Who (minigames); original class: jade.deps.eLz.GYy5o41s
package jade.client.module.minigames;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.ChatReceivedEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.autowho.DelayedStateTrigger;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

@ModuleInfo
public class AutoWho extends Module {
   private final DelayedStateTrigger delayedStateTrigger = new DelayedStateTrigger();
   private final BooleanSetting hideResponse;
   private final SliderSetting delay;

   public AutoWho() {
      super("Auto Who", Category.minigames);
      this.registerSetting(new DescriptionSetting("Automatically execute /who."));
      this.registerSetting(new DescriptionSetting(ClientUtils.translateColorCodes("Use '&enick [nick]&r' when nicked.")));
      this.delay = new SliderSetting(
         "Delay", "ms", 0.0, 0.0, 5000.0, 50.0
      );
      this.hideResponse = new BooleanSetting(
         "Hide response", false
      );
      this.registerSetting(this.delay);
      this.registerSetting(this.hideResponse);
   }

   @Override
   public void onDisable() {
      this.delayedStateTrigger.reset();
   }

   @Override
   public void onUpdate() {
      if (!ClientUtils.isInWorld()) {
         this.delayedStateTrigger.reset();
      } else {
         long var1 = System.currentTimeMillis();
         if (this.delayedStateTrigger.shouldFire(this.Vhjyk(), var1, (long)this.delay.getInput())) {
            mc.thePlayer.sendChatMessage("/who");
         }
      }
   }

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (var1.messageType != 2 && ClientUtils.isInWorld()) {
         String var2 = ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText());
         if (this.hideResponse.isToggled() && var2.startsWith("ONLINE: ")) {
            var1.setCanceled(true);
            ClientUtils.logger.info("[CHAT] " + var2);
         }
      }
   }

   private int Vhjyk() {
      int var1 = ClientUtils.getBedWarsBoardType();
      if (var1 != -1) {
         return var1;
      } else {
         int var2 = ClientUtils.getSkyWarsBoardType();
         return var2 == -1 ? 0 : var2;
      }
   }
}
