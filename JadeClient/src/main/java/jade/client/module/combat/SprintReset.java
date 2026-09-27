// Jade recovery: module: Sprint Reset (combat); original class: jade.deps.eLz.d9cRUh5p
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.PlayerAttackEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.sprintreset.SprintResetMode;
import jade.client.module.combat.sprintreset.lHZRIadXQW;
import jade.client.module.combat.sprintreset.SprintResetState;
import jade.client.module.other.AntiBot;
import jade.client.setting.BooleanSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo(aliases = "WTap")
public class SprintReset extends Module {
   private SliderSetting mode;
   private SliderSetting delayBetweenReset;
   private SliderSetting delayUntilReset;
   private SliderSetting chance;
   private BooleanSetting randomisation;
   private BooleanSetting playersOnly;
   private BooleanSetting onlyOnKnockbackItem;
   private BooleanSetting damaged;
   private final lHZRIadXQW sprintStateTracker = new lHZRIadXQW();
   public static boolean resetRequested = false;
   private static final SprintResetState zTat = new SprintResetState();

   public SprintReset() {
      super("Sprint Reset", Category.combat);
      this.registerSetting(this.mode = new SliderSetting("Mode", SprintResetMode.W_TAP.ordinal(), SprintResetMode.labels()));
      this.registerSetting(
         this.chance = new SliderSetting(
            "Chance", "%", 100.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(
         this.delayBetweenReset = new SliderSetting(
            "Delay between reset", "ms", 300.0, 0.0, 1000.0, 10.0
         )
      );
      this.registerSetting(
         this.delayUntilReset = new SliderSetting(
            "Delay until reset", "ms", 150.0, 0.0, 1000.0, 10.0
         )
      );
      this.registerSetting(
         this.randomisation = new BooleanSetting(
            "Randomisation", false
         )
      );
      this.registerSetting(this.playersOnly = new BooleanSetting("Players only", true));
      this.registerSetting(
         this.onlyOnKnockbackItem = new BooleanSetting(
            "Only on Knockback Item",
            false
         )
      );
      this.registerSetting(
         this.damaged = new BooleanSetting(
            "Damaged", false
         )
      );
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.sprintStateTracker.reset();
      resetRequested = false;
      ghK0();
   }

   @Override
   public void onUpdate() {
      if (ClientUtils.isInWorld() && !mc.thePlayer.isDead) {
         long var1 = System.currentTimeMillis();
         boolean var3 = mc.thePlayer.isSprinting();
         this.sprintStateTracker.onSprintResumed(var1, var3);
         if (this.sprintStateTracker.hasResetDelayElapsed(var1)) {
            if (this.shouldUseStatefulReset()) {
               if (mc.thePlayer.isSprinting()) {
                  zTat.beginReset();
               }

               this.sprintStateTracker.beginResetCycle(var1, false);
            } else {
               resetRequested = true;
               this.sprintStateTracker.beginResetCycle(var1, true);
            }
         }

         this.sprintStateTracker.IHUk(var3);
      } else {
         this.sprintStateTracker.reset();
         resetRequested = false;
         ghK0();
      }
   }

   @Subscribe
   public void onPlayerAttack(PlayerAttackEvent var1) {
      if (ClientUtils.isInWorld() && var1.entityPlayer == mc.thePlayer && mc.thePlayer.isSprinting()) {
         if (this.chance.getInput() != 0.0) {
            if (this.playersOnly.isToggled()) {
               if (!(var1.entity instanceof EntityPlayer)) {
                  return;
               }

               if (AntiBot.shouldHideEntity(var1.entity)) {
                  return;
               }
            } else if (!(var1.entity instanceof EntityLivingBase)) {
               return;
            }

            if (((EntityLivingBase)var1.entity).deathTime == 0) {
               if (!this.onlyOnKnockbackItem.isToggled() || EnchantmentHelper.getKnockbackModifier(mc.thePlayer) > 0) {
                  if (!this.damaged.isToggled() || mc.thePlayer.hurtTime > 0) {
                     long var2 = System.currentTimeMillis();
                     long var4 = (long)this.delayBetweenReset.getInput();
                     if (this.sprintStateTracker.dgN7(var2, var4)) {
                        if (this.chance.getInput() != 100.0) {
                           double var6 = Math.random();
                           if (var6 >= this.chance.getInput() / 100.0) {
                              return;
                           }
                        }

                        this.sprintStateTracker.scheduleResetAt(var2 + this.computeResetDelayMillis(this.delayUntilReset.getInput()));
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.sprintStateTracker.reset();
      resetRequested = false;
      ghK0();
   }

   @Override
   public String getInfo() {
      return this.CxuL().getLabel();
   }

   private boolean shouldUseStatefulReset() {
      SprintResetMode var1 = this.CxuL();
      return var1 == SprintResetMode.NO_STOP || var1 == SprintResetMode.DYNAMIC && !this.isDynamicResetBlocked();
   }

   private boolean isDynamicResetBlocked() {
      return this.CxuL() != SprintResetMode.DYNAMIC ? false : mc.thePlayer.isBlocking() || this.SFHGt8();
   }

   private SprintResetMode CxuL() {
      return SprintResetMode.fromSetting(this.mode.getInput());
   }

   private boolean SFHGt8() {
      KBDisplace var1 = Jade.getModuleManager().getModule(KBDisplace.class);
      return var1 != null && var1.isDisplacing();
   }

   private long computeResetDelayMillis(double var1) {
      long var3 = Math.round(var1);
      if (this.randomisation.isToggled()) {
         var3 += (long)Math.floor(Math.random() * 101.0) - 50L;
      }

      return Math.max(0L, var3);
   }

   public static boolean isResetInProgress() {
      return zTat.AAhHl();
   }

   public static void clearResetInProgress() {
      zTat.clearResetInProgress();
   }

   public static boolean isRestartSuppressed() {
      return zTat.isRestartSuppressed();
   }

   public static boolean isAwaitingRestart() {
      return zTat.DBQv();
   }

   public static void clearAwaitingRestart() {
      zTat.clearAwaitingRestart();
   }

   public static void decrementRestartSuppression() {
      zTat.FzYu();
   }

   private static void ghK0() {
      zTat.resetState();
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Target", "Chance", new String[]{"players only"}, new String[]{"Players only"}),
         buildSettingAlias("Conditions", "Target", new String[]{"only on knockback item", "damaged"}, new String[]{"Holding knockback item", "Damaged"}),
         buildSettingAlias("Action", "Conditions", new String[]{"randomisation"}, new String[]{"Randomise timing"})
      );
   }
}
