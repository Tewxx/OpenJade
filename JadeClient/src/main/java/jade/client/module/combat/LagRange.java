// Jade recovery: module: Lag Range (combat); original class: jade.deps.eLz.n3Eentec
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.FakePlayerRenderer$1;
import jade.client.common.FakePlayerRenderer$2;
import jade.client.common.FakePlayerRenderer;
import jade.client.common.PacketDirection;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.AttackEntityEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.BedNuker;
import jade.client.module.player.Blink;
import jade.client.module.player.FakeLag;
import jade.client.module.shared.DisabledOrNestedCondition;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class LagRange extends Module {
   private static final String[] ThT = new String[]{"Outbound", "Both", "Inbound"};
   private final SliderSetting mode;
   private final SliderSetting range;
   private final SliderSetting maximumDelay;
   private final SliderSetting hurtTime;
   private final SliderSetting inboundRatio;
   private final BooleanSetting flushOnSprintReset;
   private final BooleanSetting flushOnBlock;
   private final BooleanSetting flushOnPotion;
   private final BooleanSetting holdingLeftClick;
   private final BooleanSetting holdingAWeapon;
   private final BooleanSetting lookingAtPlayer;
   private final BooleanSetting realPositionIndicator;
   private final BooleanSetting showInFirstPerson;
   private EntityPlayer entityPlayer;
   private boolean lagActive;
   private boolean PBo;
   private boolean wqB;
   private PacketListenerRegistration packetQueue;
   private final FakePlayerRenderer$1 snapshotHistory = new FakePlayerRenderer$1();

   public LagRange() {
      super("Lag Range", Category.combat);
      this.registerSetting(
         this.mode = new SliderSetting(
            "Mode", 0, ThT
         )
      );
      this.registerSetting(this.range = new SliderSetting("Range", 6.0, 3.0, 10.0, 0.1));
      this.registerSetting(
         this.maximumDelay = new SliderSetting(
            "Maximum delay", "ms", 200.0, 50.0, 1000.0, 10.0
         )
      );
      this.registerSetting(
         this.hurtTime = new SliderSetting(
            "Hurt time", "ms", 150.0, 0.0, 500.0, 50.0
         )
      );
      this.registerSetting(this.inboundRatio = new SliderSetting("Inbound ratio", 0.5, 0.1, 1.0, 0.05));
      this.registerSetting(new DescriptionSetting("Flush conditions"));
      this.registerSetting(
         this.flushOnSprintReset = new BooleanSetting(
            "Flush on Sprint Reset",
            true,
            new String[]{"Sprint reset"}
         )
      );
      this.registerSetting(
         this.flushOnBlock = new BooleanSetting(
            "Flush on Block",
            true,
            new String[]{"Block sword"}
         )
      );
      this.registerSetting(
         this.flushOnPotion = new BooleanSetting(
            "Flush on Potion",
            true,
            new String[]{"Used splash potion"}
         )
      );
      this.registerSetting(new DescriptionSetting("Indicator"));
      this.registerSetting(this.realPositionIndicator = new BooleanSetting("Real position indicator", true));
      this.registerSetting(
         this.showInFirstPerson = new BooleanSetting(
            "Show in first person",
            false
         )
      );
      this.registerSetting(new DescriptionSetting("Conditions"));
      this.registerSetting(
         this.holdingLeftClick = new BooleanSetting(
            "Holding left click", true
         )
      );
      this.registerSetting(
         this.holdingAWeapon = new BooleanSetting(
            "Holding a weapon", true
         )
      );
      this.registerSetting(this.lookingAtPlayer = new BooleanSetting("Looking at player", false));
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      this.inboundRatio.setVisible(this.gNejbO9(), this);
   }

   @Override
   public void onEnable() {
      this.resetState();
   }

   @Override
   public void onDisable() {
      this.YtpInez();
      this.resetState();
   }

   @Override
   public String getInfo() {
      return (int)this.maximumDelay.getInput() + "ms";
   }

   @Subscribe
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      if (ClientUtils.isInWorld() && !mc.thePlayer.isDead && mc.theWorld != null) {
         if (this.isOtherLagModuleActive()) {
            if (this.lagActive) {
               this.YtpInez();
            }

            this.resetState();
         } else if (Jade.getModuleManager().getModule(BedNuker.class) != null && Jade.getModuleManager().getModule(BedNuker.class).isNukingTarget()) {
            if (this.lagActive) {
               this.YtpInez();
            }
         } else if (this.TOk1()) {
            if (this.lagActive) {
               this.YtpInez();
            }
         } else if (this.isPlayerHurt()) {
            if (this.lagActive) {
               this.YtpInez();
            }

            this.PBo = mc.thePlayer.isSprinting();
            this.wqB = mc.thePlayer.isBlocking();
         } else {
            double var2 = this.range.getInput() * this.range.getInput();
            boolean var4 = this.YTKG();
            boolean var5 = !this.holdingLeftClick.isToggled() || Mouse.isButtonDown(0);
            boolean var6 = !this.holdingAWeapon.isToggled() || ClientUtils.isHoldingWeapon();
            boolean var7 = this.lookingAtPlayer.isToggled();
            EntityPlayer var8 = this.findTargetPlayer(var2);
            this.entityPlayer = var8;
            if (this.entityPlayer == null) {
               if (this.lagActive) {
                  this.YtpInez();
               }
            } else {
               double var9 = RotationUtils.getDistanceSqToEntity(this.entityPlayer);
               boolean var11 = !var7 || this.isLookingAtPlayer(this.entityPlayer);
               if (var9 > var2 || !var4 || !var5 || !var6 || !var11) {
                  if (this.lagActive) {
                     this.YtpInez();
                  }

                  this.PBo = mc.thePlayer.isSprinting();
                  this.wqB = mc.thePlayer.isBlocking();
                  return;
               }

               if (this.lagActive) {
                  if (this.isOutboundDelayEnabled()) {
                     Jade.nbT.Cvuz(PacketDirection.OUTBOUND, (long)this.maximumDelay.getInput());
                  }

                  if (this.isInboundDelayEnabled()) {
                     Jade.nbT.Cvuz(PacketDirection.INBOUND, this.getInboundDelayMillis());
                  }

                  if (this.flushOnSprintReset.isToggled()) {
                     boolean var12 = mc.thePlayer.isSprinting();
                     if (var12 && !this.PBo) {
                        this.YtpInez();
                        this.PBo = var12;
                        return;
                     }

                     this.PBo = var12;
                  }

                  if (this.flushOnBlock.isToggled()) {
                     boolean var13 = mc.thePlayer.isBlocking();
                     if (var13 && !this.wqB) {
                        this.YtpInez();
                        this.wqB = var13;
                        return;
                     }

                     this.wqB = var13;
                  }

                  if (this.flushOnPotion.isToggled() && mc.thePlayer.isUsingItem()) {
                     ItemStack var14 = mc.thePlayer.getHeldItem();
                     if (var14 != null && var14.getItem() instanceof ItemPotion && ItemPotion.isSplash(var14.getMetadata())) {
                        this.YtpInez();
                        return;
                     }
                  }

                  return;
               }

               this.PBo = mc.thePlayer.isSprinting();
               this.wqB = mc.thePlayer.isBlocking();
               this.startLag();
            }
         }
      } else {
         if (this.lagActive) {
            this.YtpInez();
         }

         this.resetState();
      }
   }

   @Subscribe
   public void onAttackEntity(AttackEntityEvent var1) {
      if (this.lagActive && var1.entityPlayer == mc.thePlayer && this.isWithinRange(var1.entity)) {
         this.YtpInez();
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onPacketSend(PacketSendEvent var1) {
      if (this.lagActive) {
         Packet var2 = var1.ys98();
         if (this.isAttackPacket(var2)) {
            C02PacketUseEntity var3 = (C02PacketUseEntity)var2;
            Entity var4 = mc.theWorld == null ? null : var3.getEntityFromWorld(mc.theWorld);
            if (this.isWithinRange(var4)) {
               this.YtpInez();
            }
         } else {
            if (this.isActionPacket(var2)) {
               this.YtpInez();
            }
         }
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (mc.currentScreen != null) {
         if (this.lagActive) {
            this.YtpInez();
         }

         this.resetState();
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (this.lagActive) {
            if (this.realPositionIndicator.isToggled()) {
               if (this.isOutboundDelayEnabled()) {
                  if (mc.gameSettings.thirdPersonView != 0 || this.showInFirstPerson.isToggled()) {
                     long var2 = (long)this.maximumDelay.getInput();
                     this.snapshotHistory.recordSnapshot(mc.thePlayer, var1.YDn0);
                     FakePlayerRenderer$2 var4 = this.snapshotHistory.YJfvaQ(var2);
                     if (var4 != null) {
                        FakePlayerRenderer.renderFakePlayerWithDefaultAlpha(mc.thePlayer, var4, var1.YDn0);
                     }
                  }
               }
            }
         }
      }
   }

   private void startLag() {
      this.packetQueue = new PacketListenerRegistration(this.getPacketDirections(), new DisabledOrNestedCondition(this));
      Jade.nbT.JUlwlNu(this.packetQueue);
      this.lagActive = true;
   }

   private boolean gNejbO9() {
      return (int)this.mode.getInput() == 1;
   }

   private boolean isInboundDelayEnabled() {
      int var1 = (int)this.mode.getInput();
      return var1 == 1 || var1 == 2;
   }

   private boolean isOutboundDelayEnabled() {
      int var1 = (int)this.mode.getInput();
      return var1 == 0 || var1 == 1;
   }

   private long getInboundDelayMillis() {
      double var1 = this.gNejbO9() ? this.inboundRatio.getInput() : 1.0;
      return (long)(this.maximumDelay.getInput() * var1);
   }

   private Set<PacketDirection> getPacketDirections() {
      switch ((int)this.mode.getInput()) {
         case 0:
         default:
            return PacketDirection.ONLY_OUTBOUND;
         case 1:
            return PacketDirection.BIDIRECTIONAL;
         case 2:
            return PacketDirection.ONLY_INBOUND;
      }
   }

   private void YtpInez() {
      if (this.lagActive) {
         if (this.packetQueue != null) {
            this.packetQueue.getPacketHandler().forceOpen();
            this.packetQueue = null;
         }

         this.lagActive = false;
         this.snapshotHistory.clearHistory();
      }
   }

   private void resetState() {
      this.entityPlayer = null;
      this.lagActive = false;
      this.PBo = false;
      this.wqB = false;
      this.packetQueue = null;
      this.snapshotHistory.clearHistory();
   }

   private boolean isAttackPacket(Packet<?> var1) {
      return var1 instanceof C02PacketUseEntity && ((C02PacketUseEntity)var1).getAction() == Action.ATTACK;
   }

   private boolean isActionPacket(Packet<?> var1) {
      return var1 instanceof C02PacketUseEntity
         ? ((C02PacketUseEntity)var1).getAction() != Action.ATTACK
         : var1 instanceof C07PacketPlayerDigging || var1 instanceof C08PacketPlayerBlockPlacement;
   }

   private static int millisToTicks(double var0) {
      return var0 <= 0.0 ? 0 : (int)Math.ceil(var0 / 50.0);
   }

   private boolean isWithinRange(Entity var1) {
      double var2 = this.range.getInput() * this.range.getInput();
      return this.isEntityInRange(var1, var2) ? true : this.QbBkhe(var2);
   }

   private boolean QbBkhe(double var1) {
      if (mc.theWorld == null) {
         return false;
      } else {
         for (EntityPlayer var4 : mc.theWorld.playerEntities) {
            if (TargetFinder.eOkw(var4, var1) && this.passesLookingCondition(var4) && this.isHurtTimeElapsed(var4)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isEntityInRange(Entity var1, double var2) {
      if (!(var1 instanceof EntityPlayer)) {
         return false;
      } else {
         EntityPlayer var4 = (EntityPlayer)var1;
         return TargetFinder.eOkw(var4, var2) && this.passesLookingCondition(var4) && this.isHurtTimeElapsed(var4);
      }
   }

   private boolean isHurtTimeElapsed(EntityPlayer var1) {
      if (var1.hurtTime <= 0) {
         return true;
      } else {
         int var2 = var1.maxHurtTime > 0 ? var1.maxHurtTime : 10;
         int var3 = Math.max(0, var2 - var1.hurtTime);
         return var3 >= millisToTicks(this.hurtTime.getInput());
      }
   }

   private boolean TOk1() {
      AutoBlock var1 = Jade.getModuleManager().getModule(AutoBlock.class);
      return var1 != null && var1.isAutoBlockActive();
   }

   private boolean isOtherLagModuleActive() {
      return Jade.getModuleManager().getModule(Blink.class) != null && Jade.getModuleManager().getModule(Blink.class).shouldDisableLagrange()
         || Jade.getModuleManager().getModule(FakeLag.class) != null && Jade.getModuleManager().getModule(FakeLag.class).isLagrangeDisableActive();
   }

   private EntityPlayer findTargetPlayer(double var1) {
      if (Jade.getModuleManager().getModule(AimAssist.class) != null && Jade.getModuleManager().getModule(AimAssist.class).isEnabled()) {
         EntityPlayer var3 = Jade.getModuleManager().getModule(AimAssist.class).getTargetPlayer();
         if (TargetFinder.eOkw(var3, var1) && this.passesLookingCondition(var3)) {
            return var3;
         }
      }

      return !this.lookingAtPlayer.isToggled() ? TargetFinder.findNearestTarget(var1) : this.findLookedAtPlayer(var1);
   }

   private EntityPlayer findLookedAtPlayer(double var1) {
      if (mc.theWorld == null) {
         return null;
      } else {
         EntityPlayer var3 = null;
         double var4 = Double.MAX_VALUE;

         for (EntityPlayer var7 : mc.theWorld.playerEntities) {
            if (TargetFinder.eOkw(var7, var1) && this.isLookingAtPlayer(var7)) {
               double var8 = RotationUtils.getDistanceSqToEntity(var7);
               if (var8 < var4) {
                  var4 = var8;
                  var3 = var7;
               }
            }
         }

         return var3;
      }
   }

   private boolean YTKG() {
      return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F;
   }

   private boolean isPlayerHurt() {
      return mc.thePlayer.hurtTime > 0;
   }

   private boolean isLookingAtPlayer(EntityPlayer var1) {
      return var1 != null && ClientUtils.Bhr95(mc.thePlayer.rotationYaw, mc.gameSettings.fovSetting, RotationUtils.ilaZ(var1.posX, var1.posZ));
   }

   private boolean passesLookingCondition(EntityPlayer var1) {
      return !this.lookingAtPlayer.isToggled() || this.isLookingAtPlayer(var1);
   }

   public boolean isLagging() {
      return this.isEnabled() && this.lagActive;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Flush on", "Delay", new String[]{"flush on sprint reset", "flush on block", "flush on potion"}, new String[]{"Sprint reset", "Block", "Potion"}
         ),
         buildSettingAlias("Indicator", "Flush on", new String[]{"real position indicator", "show in first person"}, new String[]{"Real position", "First person"}),
         buildSettingAlias(
            "Conditions",
            "Indicator",
            new String[]{"holding left click", "holding a weapon", "looking at player"},
            new String[]{"Holding left click", "Holding weapon", "Looking at player"}
         )
      );
   }
}
