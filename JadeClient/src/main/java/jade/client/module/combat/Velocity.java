// Jade recovery: module: Velocity (combat); original class: jade.deps.eLz.HfPbz80
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.PacketDirection;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.TickStartEvent;
import jade.client.event.PostWalkingUpdateEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.velocity.VelocityMode;
import jade.client.module.combat.velocity.TimeoutCondition;
import jade.client.module.movement.Stasis;
import jade.client.module.player.Blink;
import jade.client.module.player.Buffer;
import jade.client.module.shared.DisabledOrNestedCondition;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo(aliases = {"Jump Reset", "KB Delay", "Knockback Delay"})
public class Velocity extends Module {
   private static final long JUMP_HOLD_MILLIS = 90L;
   private static final long nrL = 180L;
   private static final long aGpy = 360L;
   private static final long DELAY_MARGIN_MILLIS = 50L;
   private static final long wUh2 = 50L;
   private static final long PaJc = 750L;
   private static final double NhHrvR = 0.45;
   private static final double HIGH_KNOCKBACK_THRESHOLD = 0.75;
   private final SliderSetting mode;
   private final SliderSetting resetChance;
   private final SliderSetting delayChance;
   private final SliderSetting distanceToTarget;
   private final SliderSetting maximumDelay;
   private final BooleanSetting requireMouseDown;
   private final BooleanSetting requireMovingForward;
   private final BooleanSetting requireAim;
   private final BooleanSetting inAir;
   private final BooleanSetting lookingAtPlayer;
   private final BooleanSetting requireLeftMouse;
   private final BooleanSetting restrictHeldItem;
   private final BooleanSetting delay;
   private final ItemListSetting whitelistedItems;
   private PacketListenerRegistration TWo;
   private long inboundDelayMillis;
   private boolean delayApplied;
   private boolean jumpKeyHeld;
   private boolean zGpfe;
   private boolean ignoreFallDamage;
   private long fallDamageIgnoreUntil;
   private boolean resetAlreadyTriggered;
   private int previousHurtTime;
   private double previousFallDistance;
   private double maximumFallDistance;
   private boolean Nfas;
   private long resetDueAtMillis;
   private long EPK;
   private double pendingKnockbackSpeed;
   private long forwardHoldUntilMillis;

   public Velocity() {
      super("Velocity", Category.combat);
      this.registerSetting(this.mode = new SliderSetting("Mode", VelocityMode.JUMP_RESET.ordinal(), VelocityMode.labels()));
      this.registerSetting(
         this.resetChance = new SliderSetting(
            null,
            "Reset Chance",
            "%",
            100.0,
            0.0,
            100.0,
            1.0,
            new String[]{"Jump Reset Chance", "Chance"}
         )
      );
      this.registerSetting(
         this.delayChance = new SliderSetting(
            "Delay Chance", "%", 100.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(this.distanceToTarget = new SliderSetting("Distance to target", 5.3, 3.0, 12.0, 0.1));
      this.registerSetting(
         this.maximumDelay = new SliderSetting(
            "Maximum delay", "ms", 200.0, 50.0, 1000.0, 10.0
         )
      );
      this.registerSetting(new DescriptionSetting("Reset Conditions"));
      this.registerSetting(this.requireMouseDown = new BooleanSetting("Require mouse down", false));
      this.registerSetting(
         this.requireMovingForward = new BooleanSetting(
            "Require moving forward",
            false
         )
      );
      this.registerSetting(
         this.requireAim = new BooleanSetting(
            "Require aim", false
         )
      );
      this.registerSetting(new DescriptionSetting("Delay Conditions"));
      this.registerSetting(
         this.delay = new BooleanSetting(
            "Delay", true
         )
      );
      this.registerSetting(
         this.inAir = new BooleanSetting(
            "In air", true
         )
      );
      this.registerSetting(this.lookingAtPlayer = new BooleanSetting("Looking at player", true));
      this.registerSetting(
         this.requireLeftMouse = new BooleanSetting(
            "Require Left mouse",
            true,
            new String[]{"Require mouse down"}
         )
      );
      this.registerSetting(
         this.restrictHeldItem = new BooleanSetting(
            "Restrict held item",
            false,
            new String[]{"Item whitelist", "Restrict to listed items", "Only whitelisted item"}
         )
      );
      this.registerSetting(this.whitelistedItems = new ItemListSetting("Whitelisted items"));
      this.initialized = true;
      this.DcLu8();
   }

   private void DcLu8() {
      this.delayChance.visible = false;
      this.distanceToTarget.visible = false;
      this.maximumDelay.visible = false;
      this.delay.visible = false;
      this.inAir.visible = false;
      this.lookingAtPlayer.visible = false;
      this.requireLeftMouse.visible = false;
      this.restrictHeldItem.visible = false;
      this.whitelistedItems.visible = false;
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.ymnN();
      boolean var2 = this.isHardResetMode();
      boolean var3 = var2 && this.delay.isToggled();
      this.delayChance.setVisible(var1 || var3, this);
      this.distanceToTarget.setVisible(var1 || var3, this);
      this.maximumDelay.setVisible(var1 || var3, this);
      this.delay.setVisible(var2, this);
      this.inAir.setVisible(var1 || var3, this);
      this.lookingAtPlayer.setVisible(var1 || var3, this);
      this.requireLeftMouse.setVisible(var1 || var3, this);
      this.restrictHeldItem.setVisible(var1 || var3, this);
      this.whitelistedItems.setVisible((var1 || var3) && this.restrictHeldItem.isToggled(), this);
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      if (var1 == this.mode) {
         this.clearPendingReset();
         this.cancelInboundDelay();
         this.guiUpdate();
      }
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.restrictHeldItem || var1 == this.delay) {
         if (var1 == this.delay && !this.delay.isToggled()) {
            this.clearPendingReset();
            this.cancelInboundDelay();
         }

         this.guiUpdate();
      }
   }

   @Override
   public void onEnable() {
      if ((this.ymnN() || this.isHardResetMode()) && isBlinkConflict()) {
         ClientUtils.sendColoredMessage("&cVelocity conflicts with Blink inbound / both. Disable Blink or use outbound-only.");
         this.disable();
      } else {
         this.TWo = null;
         this.inboundDelayMillis = 0L;
         this.delayApplied = false;
      }
   }

   @Override
   public void onDisable() {
      this.clearPendingReset();
      this.cancelInboundDelay();
      if (this.jumpKeyHeld && !ClientUtils.isJumpKeyDown()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), this.jumpKeyHeld = false);
      }

      if (this.zGpfe && !Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.zGpfe = false);
      }

      this.jumpKeyHeld = false;
      this.zGpfe = false;
      this.ignoreFallDamage = false;
      this.fallDamageIgnoreUntil = 0L;
      this.maximumFallDistance = 0.0;
      this.resetAlreadyTriggered = false;
      this.forwardHoldUntilMillis = 0L;
   }

   @Override
   public String getInfo() {
      VelocityMode var1 = this.getVelocityMode();
      if (var1 == VelocityMode.DELAY) {
         return (int)this.maximumDelay.getInput() + "ms";
      } else if (var1 == VelocityMode.HARD_RESET) {
         return "Hard";
      } else {
         return (int)this.resetChance.getInput() == 100 ? "" : (int)this.resetChance.getInput() + "%";
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      int var2 = mc.thePlayer.hurtTime;
      boolean var3 = mc.thePlayer.onGround;
      this.tickPendingReset();
      this.updateFallDamageTracking(var3);
      if ((this.isJumpResetMode() || this.ymnN()) && var2 > this.previousHurtTime) {
         if (this.resetAlreadyTriggered) {
            this.resetAlreadyTriggered = false;
         } else if (this.areResetConditionsMet(var3, false, false)) {
            this.clearPendingReset();
            if (this.ymnN()) {
               this.HmxO5();
            }

            this.pressJumpKey();
         }

         this.clearFallTracking();
      }

      this.previousHurtTime = var2;
      this.previousFallDistance = mc.thePlayer.fallDistance;
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.isEnabled() && !var1.isCanceled() && (this.ymnN() || this.isHardResetMode())) {
         if (var1.ys98() instanceof S08PacketPlayerPosLook) {
            this.clearPendingReset();
            this.cancelInboundDelay();
         } else if (this.isOwnHurtPacket(var1.ys98()) && this.deepod()) {
            Jade.nbT.markHandled(var1.ys98());
         } else if (var1.ys98() instanceof S12PacketEntityVelocity) {
            if (ClientUtils.isInWorld() && mc.thePlayer != null && mc.theWorld != null) {
               S12PacketEntityVelocity var2 = (S12PacketEntityVelocity)var1.ys98();
               if (var2.getEntityID() == mc.thePlayer.getEntityId()) {
                  if (!this.isPacketHandlingConflict()) {
                     if (this.isHardResetMode()) {
                        this.handleVelocityPacket(var2);
                     } else if (!this.deepod()) {
                        this.delayApplied = this.AItYgn() == null && (this.delayChance.getInput() >= 100.0 || Math.random() * 100.0 < this.delayChance.getInput());
                        if (this.delayApplied) {
                           this.inboundDelayMillis = this.getConfiguredMaximumDelay();
                           this.scheduleInboundDelay(this.inboundDelayMillis + 50L);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onTickStart(TickStartEvent var1) {
      if (this.isEnabled() && (this.ymnN() || this.isHardResetMode())) {
         if (!ClientUtils.isInWorld() || mc.thePlayer == null || mc.theWorld == null || mc.thePlayer.isDead) {
            this.clearPendingReset();
            this.cancelInboundDelay();
         } else if (!this.deepod()) {
            this.clearFinishedDelay();
         } else if (this.isHardResetMode() && this.getTargetFailureReason() != null) {
            this.clearPendingReset();
            this.cancelInboundDelay();
         } else if ((this.ymnN() || this.isHardResetMode()) && this.delayApplied && this.AItYgn() != null) {
            if (this.isHardResetMode()) {
               this.clearPendingReset();
            }

            this.cancelInboundDelay();
         } else {
            Jade.nbT.Cvuz(PacketDirection.INBOUND, this.inboundDelayMillis);
         }
      }
   }

   @Subscribe
   public void BPry(PostWalkingUpdateEvent var1) {
      if (this.jumpKeyHeld && !ClientUtils.isJumpKeyDown()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), this.jumpKeyHeld = false);
      }

      if (this.zGpfe && !Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) && System.currentTimeMillis() >= this.forwardHoldUntilMillis) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.zGpfe = false);
      }
   }

   private void handleVelocityPacket(S12PacketEntityVelocity var1) {
      String var2 = this.getResetFailureReason(mc.thePlayer.onGround, true, true, ClientUtils.randomDouble(0.0, 100.0), true);
      if (var2 == null) {
         if (this.getTargetFailureReason() == null) {
            if (!this.delay.isToggled()) {
               this.scheduleKnockbackReset(this.getKnockbackSpeed(var1), 0L);
               this.resetAlreadyTriggered = true;
            } else {
               if (!this.deepod()) {
                  this.delayApplied = this.AItYgn() == null && (this.delayChance.getInput() >= 100.0 || Math.random() * 100.0 < this.delayChance.getInput());
                  this.inboundDelayMillis = this.delayApplied ? this.getConfiguredMaximumDelay() : 50L;
                  this.scheduleInboundDelay(this.inboundDelayMillis + 50L);
               }

               this.euuY(var1);
               this.resetAlreadyTriggered = true;
            }
         }
      }
   }

   private boolean areResetConditionsMet(boolean var1, boolean var2, boolean var3) {
      return this.getResetFailureReason(var1, var2, var3, ClientUtils.randomDouble(0.0, 100.0), true) == null;
   }

   private String getResetFailureReason(boolean var1, boolean var2, boolean var3, double var4, boolean var6) {
      boolean var7 = mc.gameSettings.keyBindAttack.isKeyDown() || !this.requireMouseDown.isToggled();
      boolean var8 = !this.requireAim.isToggled() || this.isAimingAtPlayer();
      boolean var9 = mc.gameSettings.keyBindForward.isKeyDown() || !this.requireMovingForward.isToggled() || var3;
      boolean var10 = (int)this.resetChance.getInput() == 100 || var4 < this.resetChance.getInput();
      boolean var11 = !this.requireAim.isToggled() || ClientUtils.Bhr95(ClientUtils.computeInputMovementAngle(), 330.0F, RotationUtils.computeYawFromDelta(mc.thePlayer.motionX, mc.thePlayer.motionZ));
      boolean var12 = mc.thePlayer.isBurning();
      boolean var13 = this.AuVw();
      if (var6 && this.isFallDamageIgnored(var1)) {
         return "fall-damage ignore flag is active";
      } else if (var12) {
         return "player is burning";
      } else if (!var1 && !var2) {
         return "player is not on ground";
      } else if (!var8) {
         return "aim requirement failed";
      } else if (!var9) {
         return "forward requirement failed";
      } else if (!var7) {
         return "mouse-down requirement failed";
      } else if (!var10) {
         return "chance failed, rolled " + this.DTcxR(var4) + " against " + this.DTcxR(this.resetChance.getInput());
      } else if (var13) {
         return "jump, poison, or wither potion effect is active";
      } else {
         return !var11 ? "knockback direction is outside required FOV" : null;
      }
   }

   private void updateFallDamageTracking(boolean var1) {
      if (mc.thePlayer.capabilities.allowFlying) {
         this.clearFallTracking();
      } else {
         double var2 = mc.thePlayer.fallDistance;
         if (var2 > this.maximumFallDistance) {
            this.maximumFallDistance = var2;
         }

         if (var1 && Math.max(this.previousFallDistance, this.maximumFallDistance) > 3.0) {
            this.ignoreFallDamage = true;
            this.fallDamageIgnoreUntil = System.currentTimeMillis() + 750L;
            this.maximumFallDistance = 0.0;
         }

         if (this.ignoreFallDamage && System.currentTimeMillis() > this.fallDamageIgnoreUntil) {
            this.clearFallTracking();
         }

         if (var1 && !this.ignoreFallDamage) {
            this.maximumFallDistance = 0.0;
         }
      }
   }

   private boolean isFallDamageIgnored(boolean var1) {
      return this.ignoreFallDamage && System.currentTimeMillis() <= this.fallDamageIgnoreUntil
         ? true
         : var1 && !mc.thePlayer.capabilities.allowFlying && (this.previousFallDistance > 3.0 || this.maximumFallDistance > 3.0);
   }

   private void clearFallTracking() {
      this.ignoreFallDamage = false;
      this.fallDamageIgnoreUntil = 0L;
      this.maximumFallDistance = 0.0;
   }

   private String AItYgn() {
      String var1 = this.getTargetFailureReason();
      if (var1 != null) {
         return var1;
      } else {
         double var2 = this.distanceToTarget.getInput() * this.distanceToTarget.getInput();
         if (this.inAir.isToggled() && mc.thePlayer.onGround) {
            return "not in air";
         } else if (this.lookingAtPlayer.isToggled() && TargetFinder.findCrosshairTarget(var2) == null) {
            return "not looking at player";
         } else if (this.requireLeftMouse.isToggled() && !Mouse.isButtonDown(0)) {
            return "LMB not held";
         } else {
            if (this.restrictHeldItem.isToggled()) {
               ItemStack var4 = mc.thePlayer.getHeldItem();
               if (var4 == null || !this.whitelistedItems.EMuhC6(var4)) {
                  return "held item not whitelisted";
               }
            }

            return null;
         }
      }
   }

   private String getTargetFailureReason() {
      double var1 = this.distanceToTarget.getInput() * this.distanceToTarget.getInput();
      return TargetFinder.findTarget(var1) == null ? "no target in range" : null;
   }

   private void pressJumpKey() {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), this.jumpKeyHeld = true);
   }

   private void Mpih(double var1, long var3) {
      long var5 = this.getForwardHoldMillis(var1);
      if (var5 <= 0L) {
         if (this.zGpfe && !Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.zGpfe = false);
         }
      } else {
         this.forwardHoldUntilMillis = Math.max(this.forwardHoldUntilMillis, System.currentTimeMillis() + var3 + var5);
         if (!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            this.zGpfe = true;
         }
      }
   }

   private void HmxO5() {
      long var1 = 90L;
      this.forwardHoldUntilMillis = Math.max(this.forwardHoldUntilMillis, System.currentTimeMillis() + var1);
      if (!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
         this.zGpfe = true;
      }
   }

   private void euuY(S12PacketEntityVelocity var1) {
      this.applyPendingKnockback(this.getKnockbackSpeed(var1));
   }

   private void applyPendingKnockback(double var1) {
      this.scheduleKnockbackReset(var1, this.getPendingResetDelayMillis());
   }

   private void scheduleKnockbackReset(double var1, long var3) {
      this.Nfas = true;
      this.EPK = System.currentTimeMillis();
      this.resetDueAtMillis = this.EPK + Math.max(0L, var3);
      this.pendingKnockbackSpeed = var1;
   }

   private long getPendingResetDelayMillis() {
      return Math.max(0L, this.getConfiguredMaximumDelay() - 50L);
   }

   private void tickPendingReset() {
      if (this.Nfas && System.currentTimeMillis() >= this.resetDueAtMillis) {
         if (this.canResetNow()) {
            this.pressJumpKey();
            this.Mpih(this.pendingKnockbackSpeed, 0L);
         }

         this.clearPendingReset();
      }
   }

   private boolean canResetNow() {
      return this.getTargetFailureReason() == null && !mc.thePlayer.isBurning() && !this.AuVw();
   }

   private void clearPendingReset() {
      this.Nfas = false;
      this.resetDueAtMillis = 0L;
      this.EPK = 0L;
      this.pendingKnockbackSpeed = 0.0;
   }

   private double getKnockbackSpeed(S12PacketEntityVelocity var1) {
      double var2 = var1.getMotionX() / 8000.0;
      double var4 = var1.getMotionZ() / 8000.0;
      return Math.sqrt(var2 * var2 + var4 * var4);
   }

   private long getForwardHoldMillis(double var1) {
      if (var1 <= 0.45) {
         return 180L;
      } else if (var1 >= 0.75) {
         return 360L;
      } else {
         double var3 = (var1 - 0.45) / 0.3;
         return 180L + Math.round(180.0 * var3);
      }
   }

   private boolean AuVw() {
      PotionEffect var1 = mc.thePlayer.getActivePotionEffect(Potion.jump);
      PotionEffect var2 = mc.thePlayer.getActivePotionEffect(Potion.poison);
      PotionEffect var3 = mc.thePlayer.getActivePotionEffect(Potion.wither);
      return var1 != null || var2 != null || var3 != null;
   }

   private boolean isAimingAtPlayer() {
      MovingObjectPosition var1 = mc.objectMouseOver;
      return var1 != null && var1.typeOfHit == MovingObjectType.ENTITY && var1.entityHit instanceof EntityPlayer;
   }

   private boolean isOwnHurtPacket(Object var1) {
      if (var1 instanceof S19PacketEntityStatus && mc.theWorld != null && mc.thePlayer != null) {
         S19PacketEntityStatus var2 = (S19PacketEntityStatus)var1;
         return var2.getOpCode() == 2 && var2.getEntity(mc.theWorld) == mc.thePlayer;
      } else {
         return false;
      }
   }

   private boolean deepod() {
      return this.TWo != null && !this.TWo.getPacketHandler().isOpen();
   }

   public boolean isInboundDelaying() {
      return this.isEnabled() && this.deepod();
   }

   public long getConfiguredMaximumDelay() {
      return (long)this.maximumDelay.getInput();
   }

   public boolean isHardResetEnabled() {
      return this.isEnabled() && this.isHardResetMode();
   }

   private boolean isPacketHandlingConflict() {
      Buffer var1 = Jade.getModuleManager().getModule(Buffer.class);
      if (var1 != null && var1.isBufferEnabled()) {
         return true;
      } else {
         Stasis var2 = Jade.getModuleManager().getModule(Stasis.class);
         return var2 != null && var2.isHypixelModeActive();
      }
   }

   private void scheduleInboundDelay(long var1) {
      this.TWo = new PacketListenerRegistration(PacketDirection.ONLY_INBOUND, new DisabledOrNestedCondition(this, new TimeoutCondition(var1)));
      Jade.nbT.JUlwlNu(this.TWo);
   }

   private void clearFinishedDelay() {
      if (this.TWo != null && this.TWo.getPacketHandler().isOpen()) {
         this.TWo = null;
         this.inboundDelayMillis = 0L;
         this.delayApplied = false;
      }
   }

   private void cancelInboundDelay() {
      if (this.TWo != null) {
         this.TWo.getPacketHandler().forceOpen();
         this.TWo = null;
      }

      this.inboundDelayMillis = 0L;
      this.delayApplied = false;
   }

   private boolean isJumpResetMode() {
      return this.getVelocityMode() == VelocityMode.JUMP_RESET;
   }

   private boolean ymnN() {
      return this.getVelocityMode() == VelocityMode.DELAY;
   }

   private boolean isHardResetMode() {
      return this.getVelocityMode() == VelocityMode.HARD_RESET;
   }

   private VelocityMode getVelocityMode() {
      return VelocityMode.fromSetting(this.mode.getInput());
   }

   private String DTcxR(double var1) {
      return Math.round(var1) + "%";
   }

   private static boolean isBlinkConflict() {
      Blink var0 = Jade.getModuleManager().getModule(Blink.class);
      return var0 != null && var0.isEnabled() && var0.isInboundMode();
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Reset Conditions",
            "Reset Chance",
            new String[]{"require mouse down", "require moving forward", "require aim"},
            new String[]{"Mouse held", "Moving forward", "Aiming at target"}
         ),
         buildSettingAlias(
            "Delay Conditions",
            "Delay Chance",
            new String[]{"delay", "in air", "looking at player", "require left mouse", "only whitelisted item"},
            new String[]{"Delay", "In air", "Looking at player", "Left mouse held", "Whitelisted item"}
         )
      );
   }
}
