// Jade recovery: module: Timer (movement); original class: jade.deps.eLz.DdpnaJIU
package jade.client.module.movement;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.GuiOpenEvent;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.SliderSetting;
import jade.client.setting.SpeedMultiplierSetting;

import jade.mixin.impl.accessor.IAccessorMinecraft;

@ModuleInfo
public class Timer extends Module {
   private static final double DEFAULT_HYPIXEL_TIMER_SPEED = 0.013;
   private static final double DEFAULT_HYPIXEL_SWITCH_TIME_MS = 3800.0;
   private static final double mejNuf = 0.015;
   private static final double LEGACY_HYPIXEL_SWITCH_TIME_MS = 3400.0;
   private final SpeedMultiplierSetting speedSetting;
   private final SliderSetting hypixelTimerSpeed;
   private final SliderSetting hypixelSwitchTime;
   private final BooleanSetting hypixel;
   private long switchCycleStart = -1L;
   private int berb;

   public Timer() {
      super("Timer", Category.movement);
      this.registerSetting(this.speedSetting = new SpeedMultiplierSetting("Speed"));
      this.registerSetting(
         this.hypixel = new BooleanSetting(
            "Hypixel", false
         )
      );
      this.registerSetting(this.hypixelTimerSpeed = new SliderSetting("Hypixel Timer Speed", 0.013, 0.01, 0.035, 0.001));
      this.registerSetting(
         this.hypixelSwitchTime = new SliderSetting(
            "Hypixel Switch Time",
            "ms",
            3800.0,
            500.0,
            10000.0,
            50.0
         )
      );
      this.hypixelTimerSpeed.visible = false;
      this.hypixelSwitchTime.visible = false;
   }

   @Override
   public void guiUpdate() {
      this.hypixelTimerSpeed.setVisible(this.hypixel.isToggled(), this);
      this.hypixelSwitchTime.setVisible(this.hypixel.isToggled(), this);
   }

   @Override
   public String getInfo() {
      return this.formatSpeed(this.speedSetting.getSpeedMultiplier()) + "x";
   }

   @Override
   public void onEnable() {
      this.migrateLegacyHypixelDefaults();
      this.resetSwitchCycle();
      if (mc.currentScreen != null) {
         mc.displayGuiScreen(null);
      }

      this.applyTimerSpeed();
   }

   @Override
   public void onDisable() {
      this.resetSwitchCycle();
      ClientUtils.eqyXnoq();
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         this.SsDx0();
         this.applyTimerSpeed();
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onGuiOpen(GuiOpenEvent var1) {
      if (var1.guiScreen != null) {
         var1.setCanceled(true);
      }
   }

   @Override
   public void onUpdate() {
      this.applyTimerSpeed();
   }

   private void SsDx0() {
      if (!this.GxFknY()) {
         this.resetSwitchCycle();
      } else {
         long var1 = System.currentTimeMillis();
         if (this.switchCycleStart < 0L) {
            this.switchCycleStart = var1;
         }

         long var3 = var1 - this.switchCycleStart;
         long var5 = (long)this.hypixelSwitchTime.getInput();
         if (var3 >= var5 + 100L) {
            this.berb = 2;
         } else if (var3 >= var5) {
            this.berb = 1;
         }
      }
   }

   private void applyTimerSpeed() {
      float var1 = this.speedSetting.getSpeedMultiplier();
      if (this.GxFknY()) {
         if (this.berb == 1) {
            var1 = 1.0F;
         } else if (this.berb == 2) {
            var1 = (float)this.hypixelTimerSpeed.getInput();
         }
      }

      ((IAccessorMinecraft)mc).getTimer().timerSpeed = var1;
   }

   private boolean GxFknY() {
      return this.hypixel.isToggled() && this.speedSetting.getSpeedMultiplier() <= 0.01F;
   }

   private void resetSwitchCycle() {
      this.switchCycleStart = -1L;
      this.berb = 0;
   }

   private void migrateLegacyHypixelDefaults() {
      if (Math.abs(this.hypixelTimerSpeed.getInput() - 0.015) < 1.0E-4 && Math.abs(this.hypixelSwitchTime.getInput() - 3400.0) < 0.1) {
         this.hypixelTimerSpeed.setValueClamped(0.013);
         this.hypixelSwitchTime.setValueClamped(3800.0);
      }
   }

   public boolean DZshJt() {
      return this.isEnabled();
   }

   private String formatSpeed(float var1) {
      return Math.abs(var1 - Math.round(var1)) < 1.0E-4F
         ? String.valueOf(Math.round(var1))
         : String.format("%.2f", var1).replaceAll("0+$", "").replaceAll("\\.$", "");
   }

   public static int getExtraUpdateCount() {
      return 0;
   }

   public static boolean dwrJu() {
      return false;
   }
}
