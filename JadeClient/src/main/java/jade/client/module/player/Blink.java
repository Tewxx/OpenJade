// Jade recovery: module: Blink (player); original class: jade.deps.eLz.ax50f41ur
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.FakePlayerRenderer;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.PlayerAttackEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.blink.ElapsedTimer;
import jade.client.module.player.blink.BlinkMode;
import jade.client.module.shared.DisabledOrNestedCondition;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import net.minecraft.util.Vec3;

@ModuleInfo
public class Blink extends Module implements ProgressBarSource {
   private static final String[] MODE_NAMES = new String[]{"Inbound", "Outbound", "Both"};
   private SliderSetting mode;
   private SliderSetting disableAfter;
   private BooleanSetting maxDuration;
   private BooleanSetting disableLagrange;
   private BooleanSetting attack;
   private BooleanSetting showInitialPosition;
   private Vec3 vec3;
   private int activeTicks;
   private final ElapsedTimer WPA = new ElapsedTimer();

   public Blink() {
      super("Blink", Category.player);
      this.registerSetting(
         this.mode = new SliderSetting(
            "Mode", 1, MODE_NAMES
         )
      );
      this.registerSetting(
         this.disableLagrange = new BooleanSetting(
            "Disable Lagrange", false
         )
      );
      this.registerSetting(
         this.maxDuration = new BooleanSetting(
            "Max duration", false
         )
      );
      this.registerSetting(
         this.disableAfter = new SliderSetting(
            "Disable after",
            "ms",
            500.0,
            50.0,
            20000.0,
            50.0
         )
      );
      this.registerSetting(new DescriptionSetting("Disable on"));
      this.registerSetting(
         this.attack = new BooleanSetting(
            "Attack", false
         )
      );
      this.registerSetting(
         this.showInitialPosition = new BooleanSetting(
            "Show initial position",
            true
         )
      );
   }

   @Override
   public void guiUpdate() {
      this.disableLagrange.setVisible(this.isOutboundMode(), this);
      this.disableAfter.setVisible(this.maxDuration.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.vec3 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
      this.activeTicks = 0;
      this.WPA.startAt(System.currentTimeMillis());
      Jade.nbT.JUlwlNu(new PacketListenerRegistration(BlinkMode.getPacketDirections(this.getModeIndex()), new DisabledOrNestedCondition(this)));
   }

   public boolean isInboundMode() {
      return BlinkMode.vTkoy(this.getModeIndex());
   }

   public boolean isOutboundMode() {
      return BlinkMode.isOutbound(this.getModeIndex());
   }

   public boolean shouldDisableLagrange() {
      return this.isEnabled() && this.disableLagrange.isToggled() && this.isOutboundMode();
   }

   @Override
   public String getInfo() {
      return String.valueOf(this.activeTicks);
   }

   @Override
   public boolean isProgressActive() {
      return this.isEnabled() && this.maxDuration.isToggled();
   }

   @Override
   public float getProgressFraction() {
      return !this.maxDuration.isToggled() ? 0.0F : this.WPA.getProgressFraction(System.currentTimeMillis(), (long)this.disableAfter.getInput());
   }

   @Override
   public String getProgressLabel() {
      return "Blink";
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      this.activeTicks++;
      if (this.maxDuration.isToggled()) {
         if (this.WPA.hasElapsed(System.currentTimeMillis(), (int)this.disableAfter.getInput())) {
            this.disable();
         }
      }
   }

   @Subscribe
   public void onPlayerAttack(PlayerAttackEvent var1) {
      if (this.isEnabled() && this.attack.isToggled() && ClientUtils.isInWorld()) {
         if (var1.entityPlayer == mc.thePlayer) {
            this.disable();
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld() && this.vec3 != null && this.showInitialPosition.isToggled() && this.isOutboundMode()) {
         FakePlayerRenderer.renderAtPosition(mc.thePlayer, this.vec3, var1.YDn0);
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Disable on", "Maximum duration", new String[]{"attack"}, new String[]{"Attack"}),
         buildSettingAlias("Visuals", "Disable on", new String[]{"show initial position", "show server position"}, new String[]{"Initial position", "Server position"})
      );
   }

   private int getModeIndex() {
      return (int)this.mode.getInput();
   }
}
