// Jade recovery: module: Auto Block (combat); original class: jade.deps.eLz.c5u5m2hP
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.PacketDirection;
import jade.client.common.PacketUtils;
import jade.client.common.InputHookManager;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.AttackEntityEvent;
import jade.client.event.ClickMouseEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.TickEndEvent;
import jade.client.event.UseItemEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.autoblock.AutoBlockMode;
import jade.client.module.combat.autoblock.gieRYmjnz$3;
import jade.client.module.combat.autoblock.gieRYmjnz;
import jade.client.module.player.BedNuker;
import jade.client.module.shared.DisabledOrNestedCondition;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class AutoBlock extends Module {
   private final SliderSetting mode;
   private final SliderSetting range;
   private final DescriptionSetting description2;
   private final SliderSetting swapAps;
   private final SliderSetting hurtTime;
   private final SliderSetting maximumHurtTime;
   private final SliderSetting maximumHoldTime;
   private final DescriptionSetting description3;
   private final SliderSetting predictDistance;
   private final SliderSetting blockDuration;
   private final SliderSetting predictHurtTime;
   private final BooleanSetting requireLeftMouse;
   private final BooleanSetting requireRightMouse;
   private final BooleanSetting damaged;
   private final BooleanSetting ignoreTeammates;
   private final DescriptionSetting description;
   private final SliderSetting lagChance;
   private final SliderSetting maximumDuration;
   private final BooleanSetting preventDelayingAttacks;
   private final BooleanSetting blockAgainImmediately;
   private final BooleanSetting forceBlockAnimation;
   private boolean blocking;
   private boolean Qul;
   private boolean releaseAtTickEnd;
   private boolean lagAfterRelease;
   private boolean hasValidTarget;
   private int blockStartTick = -1;
   private EntityPlayer entityPlayer;
   private int IWL = -1;
   private int mhvu;
   private int previousHurtTime;
   private boolean lagging;
   private int lagStartTick = -1;
   private PacketListenerRegistration mtufa0;
   private int reblockTick = -1;
   private int CWvM;
   private boolean swapping;
   private boolean performingSwapAttack;
   private boolean BtLr;
   private boolean suppressClickAfterRelease;
   private int lastSwordSlot = -1;
   private double swapAccumulator;
   private final gieRYmjnz SDo = new gieRYmjnz();
   private int predictedTargetId = -1;
   private boolean trackingTarget;
   private boolean bio54;
   private int BAjXdm;
   private boolean queuedClickIssued;
   private AutoBlockMode autoBlockMode = AutoBlockMode.CUSTOM;

   public AutoBlock() {
      super("Auto Block", Category.combat);
      this.registerSetting(this.mode = new SliderSetting("Mode", AutoBlockMode.CUSTOM.ordinal(), AutoBlockMode.labels()));
      this.registerSetting(this.range = new SliderSetting("Range", 4.0, 2.0, 6.0, 0.1));
      this.registerSetting(this.description2 = new DescriptionSetting("3/3/2 ticks at 7.5 APS"));
      this.registerSetting(this.swapAps = new SliderSetting("Swap APS", 7.5, 1.0, 10.0, 0.5));
      this.registerSetting(
         this.hurtTime = new SliderSetting(
            "Hurt Time", "ms", 200.0, 50.0, 500.0, 50.0
         )
      );
      this.registerSetting(
         this.maximumHurtTime = new SliderSetting(
            "Maximum Hurt Time", "ms", 200.0, 50.0, 500.0, 50.0
         )
      );
      this.registerSetting(
         this.maximumHoldTime = new SliderSetting(
            "Maximum Hold Time", "ms", 150.0, 50.0, 500.0, 50.0
         )
      );
      this.registerSetting(this.description3 = new DescriptionSetting("Predict"));
      this.registerSetting(this.predictDistance = new SliderSetting("Predict Distance", 4.0, 2.0, 6.0, 0.1));
      this.registerSetting(
         this.blockDuration = new SliderSetting(
            "Block Duration", "ms", 100.0, 50.0, 500.0, 50.0
         )
      );
      this.registerSetting(
         this.predictHurtTime = new SliderSetting(
            "Predict Hurt Time", "ms", 200.0, 50.0, 500.0, 50.0
         )
      );
      this.registerSetting(this.description = new DescriptionSetting("Custom"));
      this.registerSetting(this.lagChance = new SliderSetting("Lag Chance", 100.0, 0.0, 100.0, 5.0, new String[]{"Chance"}));
      this.lagChance.setSuffix("%");
      this.registerSetting(
         this.maximumDuration = new SliderSetting("Maximum duration", 200.0, 50.0, 500.0, 50.0, new String[]{"Lag Max Duration"})
      );
      this.maximumDuration.setSuffix("ms");
      this.registerSetting(
         this.preventDelayingAttacks = new BooleanSetting(
            "Prevent delaying attacks",
            false
         )
      );
      this.registerSetting(
         this.blockAgainImmediately = new BooleanSetting(
            "Block again immediately",
            true
         )
      );
      this.registerSetting(
         this.forceBlockAnimation = new BooleanSetting(
            "Force block animation", true
         )
      );
      this.registerSetting(new DescriptionSetting("Conditions"));
      this.registerSetting(
         this.requireLeftMouse = new BooleanSetting(
            "Require Left mouse", true
         )
      );
      this.registerSetting(
         this.requireRightMouse = new BooleanSetting(
            "Require right mouse", false
         )
      );
      this.registerSetting(this.damaged = new BooleanSetting("Damaged", false));
      this.registerSetting(
         this.ignoreTeammates = new BooleanSetting(
            "Ignore teammates", true
         )
      );
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.CWvM = 0;
      this.autoBlockMode = this.getSelectedMode();
      this.resetBlockingState(false);
      this.suppressClickAfterRelease = false;
   }

   @Override
   public void onDisable() {
      this.resetBlockingState(true);
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.isCustomMode();
      boolean var2 = this.isSwapMode();
      boolean var3 = this.isPredictMode();
      this.range.setVisible(!var3, this);
      this.description2.setVisible(var2, this);
      this.swapAps.setVisible(var2, this);
      this.hurtTime.setVisible(var2, this);
      this.maximumHurtTime.setVisible(var1, this);
      this.maximumHoldTime.setVisible(var1, this);
      this.description3.setVisible(var3, this);
      this.predictDistance.setVisible(var3, this);
      this.blockDuration.setVisible(var3, this);
      this.predictHurtTime.setVisible(var3, this);
      this.description.setVisible(var1, this);
      this.lagChance.setVisible(var1, this);
      this.maximumDuration.setVisible(var1, this);
      this.preventDelayingAttacks.setVisible(var1, this);
      this.blockAgainImmediately.setVisible(var1, this);
   }

   @Override
   public String getInfo() {
      if (this.isSwapMode()) {
         return AutoBlockMode.SWAP.getLabel() + " " + ClientUtils.WXYd(this.swapAps.getInput(), 1);
      } else if (this.isPredictMode()) {
         return AutoBlockMode.PREDICT.getLabel();
      } else {
         double var1 = this.lagChance.getInput();
         return var1 <= 0.0 ? "" : ClientUtils.formatNumberAsString(var1) + "%";
      }
   }

   private static int millisToTicks(double var0) {
      return var0 <= 0.0 ? 0 : (int)Math.ceil(var0 / 50.0);
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRightClick(RightClickEvent var1) {
      if (this.isPredictMode()) {
         this.queuedClickIssued = false;
         if (this.BAjXdm > 0) {
            this.BAjXdm--;
            this.queuedClickIssued = true;
            return;
         }
      }

      if (this.shouldAutoBlock()) {
         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onUseItem(UseItemEvent var1) {
      if (this.isPredictMode() && this.queuedClickIssued) {
         this.queuedClickIssued = false;
      } else {
         if (this.shouldAutoBlock()) {
            var1.setCanceled(true);
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         if (ClientUtils.isInWorld()) {
            if (!this.isBedNukerActive()) {
               if (mc.currentScreen == null || !this.blocking && !this.lagging) {
                  this.updateBlockAnimation();
               } else {
                  this.resetBlockingState(true);
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onPacketSend(PacketSendEvent var1) {
      if (this.isBedNukerActive()) {
         this.stopLag();
      } else if (var1.ys98() instanceof C02PacketUseEntity) {
         C02PacketUseEntity var2 = (C02PacketUseEntity)var1.ys98();
         if (var2.getAction() == Action.ATTACK) {
            if (!this.isSwapMode() && !this.suppressClickAfterRelease) {
               if (!this.isPredictMode()) {
                  this.XMbihe();
                  if (this.lagging && this.preventDelayingAttacks.isToggled()) {
                     if (this.koen(var2)) {
                        this.stopLag();
                        if (this.blockAgainImmediately.isToggled() && ClientUtils.CqWuiK()) {
                           this.reblockTick = this.CWvM + 1;
                        }
                     }
                  }
               }
            } else {
               if (this.suppressClickAfterRelease || !this.performingSwapAttack && this.swapping) {
                  var1.setCanceled(true);
               }
            }
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END && this.releaseAtTickEnd) {
         this.releaseAtTickEnd = false;
         if (this.lagAfterRelease) {
            this.startLag(this.CWvM);
         }

         this.lagAfterRelease = false;
         this.EWOL(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onClickMouse(ClickMouseEvent var1) {
      if (this.suppressClickAfterRelease || this.isSwapMode() && this.swapping && !this.performingSwapAttack) {
         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onAttackEntity(AttackEntityEvent var1) {
      if ((this.suppressClickAfterRelease || this.isSwapMode() && this.swapping && !this.performingSwapAttack) && var1.entityPlayer == mc.thePlayer) {
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      this.syncModeFromSetting();
      if (this.isSwapMode()) {
         this.suppressClickAfterRelease = false;
         this.handleSwapMode();
      }
   }

   @Subscribe
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      this.syncModeFromSetting();
      if (!this.isSwapMode()) {
         if (this.isPredictMode()) {
            this.handlePredictMode();
         } else if (!ClientUtils.isInWorld() || mc.thePlayer.isDead || mc.currentScreen != null) {
            this.resetBlockingState(true);
         } else if (this.isBedNukerActive()) {
            this.resetBlockingState(true);
         } else {
            int var2 = mc.thePlayer.hurtTime;
            boolean var3 = var2 > this.previousHurtTime;
            this.previousHurtTime = var2;
            if (!ClientUtils.CqWuiK()) {
               this.resetBlockingState(false);
            } else {
               this.CWvM++;
               int var4 = this.CWvM;
               this.entityPlayer = TargetFinder.syisp(this.range.getInput() * this.range.getInput(), this.ignoreTeammates.isToggled());
               boolean var5 = this.IWL >= 0 && var4 >= this.IWL;
               boolean var6 = Mouse.isButtonDown(1);
               boolean var7 = Mouse.isButtonDown(0);
               if (this.reblockTick >= 0 && var4 >= this.reblockTick) {
                  this.reblockTick = -1;
                  if ((!this.requireRightMouse.isToggled() || var6) && ClientUtils.CqWuiK() && !this.blocking && !this.lagging) {
                     this.piwrP(var4);
                  }
               }

               if (!var7) {
                  this.hasValidTarget = false;
                  if (this.lagging) {
                     this.stopLag();
                  }

                  if (var6 && !this.blocking) {
                     this.piwrP(var4);
                     this.Qul = true;
                  } else if (!var6) {
                     this.resetBlockingState(true);
                  }
               } else if (!var6 && this.requireRightMouse.isToggled()) {
                  this.hasValidTarget = false;
                  this.resetBlockingState(true);
               } else {
                  if (this.Qul) {
                     this.EWOL(true);
                     this.Qul = false;
                  }

                  boolean var8 = this.entityPlayer != null;
                  boolean var9 = var8 && this.PspY(var7, var6);
                  this.hasValidTarget = var9;
                  this.updateBlockAnimation();
                  this.YCKDMU();
                  if (this.lagging) {
                     int var10 = millisToTicks(this.maximumDuration.getInput());
                     boolean var11 = var10 > 0 && this.lagStartTick >= 0 && var4 - this.lagStartTick >= var10;
                     if (var11 || !var9) {
                        this.stopLag();
                        if (var11 && this.blockAgainImmediately.isToggled() && var9) {
                           this.piwrP(var4);
                        }
                     }
                  }

                  if (!var9) {
                     this.hasValidTarget = false;
                     this.resetBlocking(var7, var6);
                     this.updateBlockAnimation();
                  } else {
                     if (!this.blocking && !this.lagging) {
                        boolean var14 = var5;
                        if (this.damaged.isToggled()) {
                           var14 = this.isAtMaximumHurtTime();
                        }

                        if (var14) {
                           this.IWL = -1;
                           this.piwrP(var4);
                        }
                     }

                     if (this.blocking) {
                        int var15 = millisToTicks(this.maximumHoldTime.getInput());
                        boolean var16 = var15 > 0 && this.blockStartTick >= 0 && var4 - this.blockStartTick >= var15;
                        boolean var12 = var16;
                        if (this.damaged.isToggled() && var3) {
                           var12 = true;
                        }

                        if (var12) {
                           boolean var13 = this.rollLagChance();
                           if (var7) {
                              this.releaseAtTickEnd = true;
                              this.lagAfterRelease = var13;
                              return;
                           }

                           if (var13) {
                              this.startLag(var4);
                           }

                           this.EWOL(true);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean PspY(boolean var1, boolean var2) {
      return this.requireLeftMouse.isToggled() && !var1 ? false : !this.requireRightMouse.isToggled() || var2;
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.isPredictMode() && ClientUtils.isInWorld() && var1.ys98() instanceof S0BPacketAnimation) {
         S0BPacketAnimation var2 = (S0BPacketAnimation)var1.ys98();
         if (var2.getAnimationType() == 0) {
            Entity var3 = mc.theWorld.getEntityByID(var2.getEntityID());
            if (var3 instanceof EntityPlayer && var3 != mc.thePlayer) {
               this.SDo.DkCma(var3.getEntityId(), System.currentTimeMillis());
            }
         }
      }
   }

   private void handlePredictMode() {
      if (!ClientUtils.isInWorld() || mc.thePlayer.isDead || mc.currentScreen != null) {
         this.resetBlockingState(true);
      } else if (this.isBedNukerActive()) {
         this.resetBlockingState(true);
      } else if (!ClientUtils.CqWuiK()) {
         this.resetBlockingState(false);
      } else {
         this.CWvM++;
         int var1 = this.CWvM;
         int var2 = mc.thePlayer.hurtTime;
         boolean var3 = var2 > this.previousHurtTime;
         this.previousHurtTime = var2;
         boolean var4 = Mouse.isButtonDown(0);
         boolean var5 = Mouse.isButtonDown(1);
         boolean var6 = this.PspY(var4, var5) && (!this.damaged.isToggled() || var2 > 0);
         if (!var6) {
            this.hasValidTarget = false;
            this.EWOL(true);
            this.updateBlockAnimation();
         } else {
            long var7 = System.currentTimeMillis();
            gieRYmjnz$3 var9 = null;
            if (this.trackingTarget) {
               var9 = this.SDo.evaluateTargetById(this.predictedTargetId, mc.thePlayer, this.predictDistance.getInput(), this.ignoreTeammates.isToggled(), var7);
               if (var9 == null) {
                  this.clearTrackedTarget(false);
                  this.resetBlocking(var4, var5);
                  return;
               }
            } else {
               var9 = this.SDo.selectBestTarget(mc.thePlayer, mc.theWorld.playerEntities, this.predictDistance.getInput(), this.ignoreTeammates.isToggled(), var7);
               if (var9 != null) {
                  this.trackingTarget = true;
                  this.predictedTargetId = var9.cCq;
               }
            }

            this.entityPlayer = var9 == null ? null : (EntityPlayer)mc.theWorld.getEntityByID(var9.cCq);
            this.hasValidTarget = var9 != null;
            this.updateBlockAnimation();
            if (var9 == null) {
               this.resetBlocking(var4, var5);
            } else if (var3) {
               this.releaseBlockingFromMouse();
               this.bio54 = true;
            } else {
               if (this.bio54) {
                  int var10 = millisToTicks(this.predictHurtTime.getInput());
                  if (var2 > var10) {
                     this.releaseBlockingFromMouse();
                     return;
                  }

                  this.bio54 = false;
               }

               if (!this.blocking) {
                  this.nMji6(var1);
               }

               int var12 = millisToTicks(this.blockDuration.getInput());
               if (var12 > 0 && this.blockStartTick >= 0 && var1 - this.blockStartTick >= var12) {
                  this.releaseBlockingFromMouse();
                  this.clearTrackedTarget(true);
               }
            }
         }
      }
   }

   private void releaseBlockingFromMouse() {
      this.resetBlocking(Mouse.isButtonDown(0), Mouse.isButtonDown(1));
   }

   private void resetBlocking(boolean var1, boolean var2) {
      this.BAjXdm = 0;
      this.queuedClickIssued = false;
      this.hasValidTarget = false;
      this.EWOL(false);
      if (ClientUtils.isInWorld() && mc.currentScreen == null) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), var2 && !var1);
      }
   }

   private void nMji6(int var1) {
      if (ClientUtils.CqWuiK()) {
         this.BAjXdm = 1;
         this.queuedClickIssued = false;
         this.piwrP(var1);
      }
   }

   private void clearTrackedTarget(boolean var1) {
      if (var1 && this.predictedTargetId >= 0) {
         this.SDo.invalidateTarget(this.predictedTargetId);
      }

      this.predictedTargetId = -1;
      this.trackingTarget = false;
      this.bio54 = false;
      this.entityPlayer = null;
      this.hasValidTarget = false;
      this.updateBlockAnimation();
   }

   private void syncModeFromSetting() {
      AutoBlockMode var1 = this.getSelectedMode();
      if (var1 != this.autoBlockMode) {
         this.resetBlockingState(true);
         this.autoBlockMode = var1;
      }
   }

   private void handleSwapMode() {
      if (!ClientUtils.isInWorld() || mc.thePlayer.isDead || mc.currentScreen != null) {
         this.resetBlockingState(true);
      } else if (this.isBedNukerActive()) {
         this.resetBlockingState(true);
      } else if (!ClientUtils.CqWuiK()) {
         this.resetBlockingState(false);
      } else {
         this.CWvM++;
         int var1 = this.CWvM;
         boolean var2 = Mouse.isButtonDown(1);
         boolean var3 = Mouse.isButtonDown(0);
         this.entityPlayer = TargetFinder.Dhg1(this.getSquaredRange(), this.ignoreTeammates.isToggled());
         if (!this.BtLr && !this.Qul && mc.thePlayer.getItemInUse() != null) {
            this.Qul = true;
            this.blocking = true;
            this.blockStartTick = var1;
         }

         if (!var3 && this.requireLeftMouse.isToggled()) {
            this.hasValidTarget = false;
            this.YCKDMU();
            if (this.BtLr) {
               this.stopBlocking(!var2);
            }

            if (var2) {
               if (!this.Qul) {
                  this.piwrP(var1);
               }

               this.Qul = true;
            } else if (this.Qul || this.blocking) {
               this.stopUserBlocking(true);
            }
         } else if (!var2 && this.requireRightMouse.isToggled()) {
            this.hasValidTarget = false;
            this.resetBlockingState(true);
         } else {
            boolean var4 = this.entityPlayer != null && this.PspY(var3, var2);
            this.hasValidTarget = var4;
            this.updateBlockAnimation();
            this.stopLag();
            this.releaseAtTickEnd = false;
            this.lagAfterRelease = false;
            this.reblockTick = -1;
            if (!this.isVelocityActive() && var4 && this.ensureSwordSlot() && this.findSwordSlot() >= 0) {
               if (this.isWithinHurtTimeWindow()) {
                  if (this.Qul) {
                     this.YCKDMU();
                  }

                  this.updateSwapTiming();
                  this.refreshBlockingItem();
               } else {
                  this.hasValidTarget = false;
                  boolean var5 = this.udbesHe(this.lastSwordSlot) && mc.thePlayer.inventory.currentItem != this.lastSwordSlot;
                  if (this.Qul) {
                     this.stopUserBlocking(!var5);
                  } else {
                     this.stopBlocking(!var5);
                  }

                  this.restoreSwordSlot();
                  this.YCKDMU();
                  this.updateBlockAnimation();
               }
            } else if (this.Qul && var2) {
               this.hasValidTarget = true;
               this.YCKDMU();
               this.updateBlockAnimation();
            } else {
               if (this.Qul) {
                  this.stopUserBlocking(true);
               }

               this.hasValidTarget = false;
               this.stopSwapBlocking(true);
            }
         }
      }
   }

   private boolean isCustomMode() {
      return this.getSelectedMode() == AutoBlockMode.CUSTOM;
   }

   private boolean isSwapMode() {
      return this.getSelectedMode() == AutoBlockMode.SWAP;
   }

   private boolean isPredictMode() {
      return this.getSelectedMode() == AutoBlockMode.PREDICT;
   }

   private AutoBlockMode getSelectedMode() {
      return AutoBlockMode.fromSetting(this.mode.getInput());
   }

   private int findSwordSlot() {
      int var1 = mc.thePlayer.inventory.currentItem;

      for (int var2 = 0; var2 < 9; var2++) {
         if (var2 != var1) {
            ItemStack var3 = mc.thePlayer.inventory.getStackInSlot(var2);
            if (var3 != null && var3.getItem() instanceof ItemSword) {
               return var2;
            }
         }
      }

      return -1;
   }

   private boolean ensureSwordSlot() {
      if (this.udbesHe(this.lastSwordSlot)) {
         return true;
      } else {
         int var1 = mc.thePlayer.inventory.currentItem;
         if (!this.udbesHe(var1)) {
            return false;
         } else {
            this.lastSwordSlot = var1;
            return true;
         }
      }
   }

   private void restoreSwordSlot() {
      if (this.udbesHe(this.lastSwordSlot) && mc.thePlayer.inventory.currentItem != this.lastSwordSlot) {
         mc.thePlayer.inventory.currentItem = this.lastSwordSlot;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private boolean udbesHe(int var1) {
      if (ClientUtils.isInWorld() && var1 >= 0 && var1 < 9) {
         ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
         return var2 != null && var2.getItem() instanceof ItemSword;
      } else {
         return false;
      }
   }

   private Vec3 getEntityCenterVec(Entity var1) {
      return new Vec3(0.0, var1.height * 0.5, 0.0);
   }

   private void startBlocking(int var1) {
      ItemStack var2 = mc.thePlayer.getHeldItem();
      if (var2 != null && var2.getItem() instanceof ItemSword) {
         this.blocking = true;
         this.BtLr = true;
         this.blockStartTick = var1;
         this.hasValidTarget = true;
         mc.thePlayer.setItemInUse(var2, var2.getMaxItemUseDuration());
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
         this.updateBlockAnimation();
      }
   }

   private void refreshBlockingItem() {
      if (this.BtLr && ClientUtils.isInWorld()) {
         ItemStack var1 = mc.thePlayer.getHeldItem();
         if (var1 != null && var1.getItem() instanceof ItemSword) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            if (mc.thePlayer.getItemInUse() != var1) {
               mc.thePlayer.setItemInUse(var1, var1.getMaxItemUseDuration());
            }
         }
      }
   }

   private void stopBlocking(boolean var1) {
      boolean var2 = this.BtLr;
      this.BtLr = false;
      this.blocking = false;
      this.blockStartTick = -1;
      this.hasValidTarget = false;
      if (var2 && ClientUtils.isInWorld()) {
         mc.thePlayer.clearItemInUse();
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), Mouse.isButtonDown(1));
         if (var1) {
            PacketUtils.sendReleaseUseItem();
            this.suppressClickAfterRelease = true;
         }
      }

      this.updateBlockAnimation();
   }

   private void stopUserBlocking(boolean var1) {
      boolean var2 = this.Qul || this.blocking;
      this.Qul = false;
      this.blocking = false;
      this.blockStartTick = -1;
      this.hasValidTarget = false;
      if (ClientUtils.isInWorld()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), Mouse.isButtonDown(1));
         if (var2) {
            mc.thePlayer.clearItemInUse();
            if (var1) {
               PacketUtils.sendReleaseUseItem();
               this.suppressClickAfterRelease = true;
            }
         }
      }

      this.updateBlockAnimation();
   }

   private void updateSwapTiming() {
      if (!this.swapping) {
         this.swapping = true;
         this.swapAccumulator = 20.0;
      }

      if (this.swapAccumulator >= 20.0) {
         this.swapAccumulator -= 20.0;
         this.performSwapAttack(this.entityPlayer);
      }

      this.swapAccumulator = this.swapAccumulator + this.swapAps.getInput();
   }

   private boolean performSwapAttack(EntityPlayer var1) {
      if (!this.isVelocityActive() && this.isValidTarget(var1) && this.PspY(Mouse.isButtonDown(0), Mouse.isButtonDown(1)) && this.ensureSwordSlot()) {
         int var2 = this.findSwordSlot();
         if (var2 < 0) {
            return false;
         } else {
            this.performingSwapAttack = true;

            boolean var5;
            try {
               this.stopLag();
               this.releaseAtTickEnd = false;
               this.lagAfterRelease = false;
               boolean var3 = this.BtLr || this.Qul;
               if (this.Qul) {
                  this.stopUserBlocking(false);
               } else {
                  this.stopBlocking(false);
               }

               if (var3) {
                  int var4 = mc.thePlayer.inventory.currentItem;
                  mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(var2));
                  mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(var4));
               }

               ClientUtils.aidx(var1, true, true);
               Vec3 var9 = this.getEntityCenterVec(var1);
               mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(var1, var9));
               mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(var1, Action.INTERACT));
               mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
               this.startBlocking(this.CWvM);
               var5 = true;
            } finally {
               this.performingSwapAttack = false;
            }

            return var5;
         }
      } else {
         return false;
      }
   }

   private void YCKDMU() {
      this.swapping = false;
      this.performingSwapAttack = false;
      this.swapAccumulator = 0.0;
   }

   private void stopSwapBlocking(boolean var1) {
      this.stopBlocking(var1);
      this.YCKDMU();
      this.lastSwordSlot = -1;
   }

   private double getSquaredRange() {
      double var1 = mc.playerController.extendedReach() ? 6.0 : 3.0;
      double var3 = Math.min(this.range.getInput(), var1);
      return var3 * var3;
   }

   private boolean isWithinHurtTimeWindow() {
      return mc.thePlayer.hurtTime <= millisToTicks(this.hurtTime.getInput());
   }

   private boolean isValidTarget(Entity var1) {
      return var1 instanceof EntityPlayer && TargetFinder.isValidTargetInRange((EntityPlayer)var1, this.getSquaredRange(), this.ignoreTeammates.isToggled());
   }

   private boolean isVelocityActive() {
      Velocity var1 = Jade.getModuleManager().getModule(Velocity.class);
      return var1 != null && var1.isHardResetEnabled() && var1.isInboundDelaying();
   }

   private boolean isAtMaximumHurtTime() {
      int var1 = mc.thePlayer.hurtTime;
      int var2 = (int)Math.round(this.maximumHurtTime.getInput() / 50.0);
      var2 = Math.max(1, Math.min(10, var2));
      return var1 == var2;
   }

   private void XMbihe() {
      if (this.isEnabled() && this.IWL < 0 && this.CWvM >= this.mhvu) {
         int var1 = millisToTicks(this.maximumHurtTime.getInput());
         int var2 = Math.max(0, 10 - var1);
         this.IWL = this.CWvM + var2;
         this.mhvu = this.CWvM + 10;
      }
   }

   private boolean shouldAutoBlock() {
      if (!this.isEnabled() || !ClientUtils.isInWorld() || !ClientUtils.CqWuiK() || mc.currentScreen != null) {
         return false;
      } else if (this.isSwapMode()) {
         boolean var1 = this.PspY(Mouse.isButtonDown(0), Mouse.isButtonDown(1));
         boolean var2 = this.requireLeftMouse.isToggled() || this.requireRightMouse.isToggled();
         return this.swapping || this.BtLr || var1 && (var2 || this.entityPlayer != null);
      } else {
         return this.isPredictMode()
            ? Mouse.isButtonDown(0) && Mouse.isButtonDown(1)
            : this.lagging || !this.blocking && Mouse.isButtonDown(0) && Mouse.isButtonDown(1) && this.entityPlayer != null;
      }
   }

   private void piwrP(int var1) {
      if (ClientUtils.CqWuiK()) {
         int var2 = mc.gameSettings.keyBindUseItem.getKeyCode();
         KeyBinding.setKeyBindState(var2, true);
         KeyBinding.onTick(var2);
         this.blocking = true;
         this.blockStartTick = var1;
         this.updateBlockAnimation();
      }
   }

   private void EWOL(boolean var1) {
      if (this.blocking || var1) {
         int var2 = mc.gameSettings.keyBindUseItem.getKeyCode();
         KeyBinding.setKeyBindState(var2, false);
         this.blocking = false;
         this.blockStartTick = -1;
         this.updateBlockAnimation();
      }
   }

   private boolean rollLagChance() {
      if (!this.isCustomMode()) {
         return false;
      } else {
         double var1 = this.lagChance.getInput();
         if (var1 <= 0.0) {
            return false;
         } else {
            return var1 >= 100.0 ? true : Math.random() * 100.0 < var1;
         }
      }
   }

   private void startLag(int var1) {
      if (!this.lagging) {
         this.mtufa0 = new PacketListenerRegistration(PacketDirection.ONLY_OUTBOUND, new DisabledOrNestedCondition(this));
         Jade.nbT.JUlwlNu(this.mtufa0);
         this.lagging = true;
         this.lagStartTick = var1;
         this.updateBlockAnimation();
      }
   }

   private void stopLag() {
      if (this.lagging) {
         if (this.mtufa0 != null) {
            this.mtufa0.getPacketHandler().forceOpen();
            this.mtufa0 = null;
         }

         this.lagging = false;
         this.lagStartTick = -1;
      }
   }

   private boolean koen(C02PacketUseEntity var1) {
      Object var2 = var1.getEntityFromWorld(mc.theWorld);
      if (!(var2 instanceof EntityPlayer)) {
         var2 = this.entityPlayer;
      }

      if (!(var2 instanceof EntityPlayer)) {
         return true;
      } else {
         EntityPlayer var3 = (EntityPlayer)var2;
         int var4 = millisToTicks(this.maximumHurtTime.getInput());
         int var5 = millisToTicks(150.0);
         return var3.hurtTime <= var4 + var5;
      }
   }

   private void updateBlockAnimation() {
      InputHookManager.setRenderItemInUse(this.forceBlockAnimation.isToggled() && ClientUtils.isInWorld() && ClientUtils.CqWuiK() && mc.currentScreen == null && this.hasValidTarget);
   }

   public boolean isAutoBlockActive() {
      return this.isEnabled() && (this.blocking || this.lagging);
   }

   public boolean isSwapBlocking() {
      return this.isEnabled() && this.isSwapMode() && this.BtLr;
   }

   private boolean isBedNukerActive() {
      return Jade.getModuleManager().getModule(BedNuker.class) != null && Jade.getModuleManager().getModule(BedNuker.class).isNukingTarget();
   }

   private void resetBlockingState(boolean var1) {
      boolean var2 = this.blocking || this.lagging;
      this.stopLag();
      if (this.BtLr) {
         this.stopBlocking(var1);
      } else if (this.isSwapMode() && this.Qul) {
         this.stopUserBlocking(var1);
      } else {
         this.EWOL(var1);
      }

      this.Qul = false;
      this.releaseAtTickEnd = false;
      this.lagAfterRelease = false;
      this.reblockTick = -1;
      this.hasValidTarget = false;
      if (this.forceBlockAnimation.isToggled() && var2) {
         InputHookManager.setRenderItemInUse(false);
      } else {
         this.updateBlockAnimation();
      }

      if (Mouse.isButtonDown(1) && ClientUtils.isInWorld() && mc.currentScreen == null) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
      }

      this.entityPlayer = null;
      this.IWL = -1;
      this.mhvu = 0;
      this.previousHurtTime = 0;
      this.SDo.GWxd();
      this.predictedTargetId = -1;
      this.trackingTarget = false;
      this.bio54 = false;
      this.BAjXdm = 0;
      this.queuedClickIssued = false;
      this.YCKDMU();
      this.lastSwordSlot = -1;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Custom",
            "Maximum duration",
            new String[]{"prevent delaying attacks", "block again immediately", "force block animation"},
            new String[]{"Prevent delayed attacks", "Instant re-block", "Force block animation"}
         ),
         buildSettingAlias("Predict", "Predict distance", new String[]{"block duration", "predict hurt time"}, new String[]{"Block duration", "Hurt time"}),
         buildSettingAlias(
            "Conditions",
            "Custom",
            new String[]{"require left mouse", "require right mouse", "damaged", "ignore teammates"},
            new String[]{"Left mouse held", "Right mouse held", "Recently damaged", "Ignore teammates"}
         )
      );
   }
}
