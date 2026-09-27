// Jade recovery: module: Scaffold (player); original class: jade.deps.eLz.mEMfiB5g40
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.PacketUtils;
import jade.client.common.RenderUtils$1;
import jade.client.common.RenderUtils;
import jade.client.common.RotationHandler;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.LeftClickEvent;
import jade.client.event.MoveFlyingEvent;
import jade.client.event.MoveInputEvent;
import jade.client.event.MoveStateUpdateEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.RotationEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.movement.Timer;
import jade.client.module.player.scaffold.BlockSearch;
import jade.client.module.player.scaffold.PlaceRotations;
import jade.client.module.player.scaffold.MovementPrediction;
import jade.client.module.render.Arraylist;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorEntityPlayerSP;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings.GameType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class Scaffold extends Module {
   private static final double[] FACE_SAMPLE_OFFSETS;
   private static final long hba = 2200L;
   private static final int HIGHLIGHT_MAX_ALPHA = 116;
   private static final int MOTION_SIMULATION_STEPS = 7;
   private static final int ICE_SWAP_COOLDOWN_TICKS = 4;
   private static final double zkE = 1.0E-6;
   private static final int nnL3 = 21;
   private static final int bYa = 18;
   private static final int MAX_TOWER_JUMP_TICKS = 24;
   private static final int Ydmig = 2;
   private static final double CENTER_TOLERANCE = 0.2;
   private static final int ROTATION_DELAY_TICKS = 3;
   private static final float SNAP_ANGLE_LIMIT = 70.0F;
   private static final float STRAFE_YAW_OFFSET = 10.0F;
   private static final float nIls = 15.0F;
   private final SliderSetting rotation;
   private final BooleanSetting timeManipulation;
   private final SliderSetting lowTimer;
   private final SliderSetting highTimer;
   private final SliderSetting tower;
   private final SliderSetting keepY;
   private final BooleanSetting keepYOnUse;
   private final BooleanSetting autoSwap;
   private final BooleanSetting iceSwap;
   private final BooleanSetting downwardsOnShift;
   private final BooleanSetting swing;
   private final BooleanSetting blocksHud;
   private final FontSetting font;
   private final BooleanSetting highlightPlacedBlocks;
   private final String[] rotationModes = new String[]{"None", "Basic", "Accurate", "Snap"};
   private final String[] towerModes = new String[]{"None", "Telly"};
   private final String[] keepYModes = new String[]{"None", "Telly"};
   private int rotationDelayTicks;
   private int aisgr = -1;
   private int heldBlockCount = -1;
   private float targetYaw = -180.0F;
   private float AAnih1 = 0.0F;
   private boolean rotationSet;
   private int keepYJumpTicks;
   private int keepYLevel = 256;
   private boolean keepYLevelLocked;
   private boolean towerTellyActive;
   private boolean keepYTellyActive;
   private boolean useKeyPressed;
   private boolean usedWhileAirborne;
   private boolean icePlaced;
   private boolean towerJumpStarted;
   private boolean JTw;
   private boolean sneakKeyReleased;
   private boolean descendBlockPlaced;
   private boolean towerJumping;
   private boolean towerFallInProgress;
   private int downPlacedY = Integer.MIN_VALUE;
   private int towerJumpTicks;
   private int descendGroundTicks;
   private BlockPos watchedDownPos;
   private BlockPos SOkt;
   private boolean rotationsApplied;
   private RotationEvent rotationEvent;
   private int pendingSlotRestore = -1;
   private int slotRestoreDelay;
   private int iceSwapCooldown;
   private Scaffold$3 currentTarget;
   private Scaffold$3 mC23;
   private Vec3 currentHitVec;
   private BlockPos gPcd;
   private int QRk;
   private String rotationStatus = "idle";
   private String placeStatus = "none";
   private String ICx = "none";
   private String rayStatus = "none";
   private String wAp = "none";
   private long EHbJ4;
   private long lastPlaceTime;
   private boolean timerSpeedApplied;
   private boolean timerManipulating;
   private boolean j616;
   private long lastTimerTickNanos;
   private double CcUaxf;
   private float appliedTimerSpeed = 1.0F;
   private final List<Scaffold$4> placedBlocks = new ArrayList<>();

   public Scaffold() {
      super("Scaffold", Category.player);
      this.registerSetting(
         this.rotation = new SliderSetting(
            "Rotation",
            1,
            this.rotationModes,
            new String[]{"Backwards"}
         )
      );
      this.registerSetting(this.timeManipulation = new BooleanSetting("Time Manipulation", false));
      this.registerSetting(
         this.lowTimer = new SliderSetting(
            "Low Timer", "x", 0.4, 0.1, 0.8, 0.1
         )
      );
      this.registerSetting(
         this.highTimer = new SliderSetting(
            "High Timer", "x", 2.0, 1.5, 3.5, 0.1
         )
      );
      this.registerSetting(this.tower = new SliderSetting("Tower", 0, this.towerModes));
      this.registerSetting(
         this.keepY = new SliderSetting(
            "Keep-Y", 0, this.keepYModes
         )
      );
      this.registerSetting(this.keepYOnUse = new BooleanSetting("Keep-Y on use", false));
      this.registerSetting(
         this.autoSwap = new BooleanSetting(
            "Auto swap", true
         )
      );
      this.registerSetting(this.iceSwap = new BooleanSetting("Ice Swap", false));
      this.registerSetting(
         this.downwardsOnShift = new BooleanSetting(
            "Downwards on shift", false
         )
      );
      this.registerSetting(
         this.swing = new BooleanSetting(
            "Swing", true
         )
      );
      this.registerSetting(
         this.blocksHud = new BooleanSetting(
            "Blocks HUD", true
         )
      );
      this.registerSetting(this.font = new FontSetting("Font", "Modern"));
      this.registerSetting(
         this.highlightPlacedBlocks = new BooleanSetting(
            "Highlight placed blocks",
            true
         )
      );
      this.lowTimer.visible = false;
      this.highTimer.visible = false;
      this.iceSwap.visible = false;
   }

   @Override
   public void guiUpdate() {
      this.lowTimer.setVisible(this.timeManipulation.isToggled(), this);
      this.highTimer.setVisible(this.timeManipulation.isToggled(), this);
      this.keepYOnUse.setVisible((int)this.keepY.getInput() != 0, this);
      this.iceSwap.setVisible((int)this.keepY.getInput() == 1 || (int)this.tower.getInput() == 1, this);
      this.font.setVisible(this.blocksHud.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.aisgr = ClientUtils.isInWorld() ? mc.thePlayer.inventory.currentItem : -1;
      this.heldBlockCount = -1;
      this.rotationDelayTicks = (int)this.rotation.getInput() == 3 ? 0 : 3;
      this.targetYaw = -180.0F;
      this.AAnih1 = 0.0F;
      this.rotationSet = false;
      this.keepYJumpTicks = 0;
      this.keepYLevel = 256;
      this.keepYLevelLocked = false;
      this.towerTellyActive = false;
      this.keepYTellyActive = false;
      this.useKeyPressed = false;
      this.usedWhileAirborne = false;
      this.icePlaced = false;
      this.towerJumpStarted = false;
      this.JTw = false;
      this.sneakKeyReleased = false;
      this.descendBlockPlaced = false;
      this.towerJumping = false;
      this.towerFallInProgress = false;
      this.downPlacedY = Integer.MIN_VALUE;
      this.towerJumpTicks = 0;
      this.descendGroundTicks = 0;
      this.watchedDownPos = null;
      this.SOkt = null;
      this.rotationsApplied = false;
      this.rotationEvent = null;
      this.pendingSlotRestore = -1;
      this.slotRestoreDelay = 0;
      this.iceSwapCooldown = 0;
      this.currentTarget = null;
      this.mC23 = null;
      this.currentHitVec = null;
      this.gPcd = null;
      this.QRk = 0;
      this.rotationStatus = "enabled";
      this.placeStatus = "none";
      this.ICx = "none";
      this.rayStatus = "none";
      this.wAp = "none";
      this.EHbJ4 = 0L;
      this.lastPlaceTime = 0L;
      this.timerSpeedApplied = false;
      this.resetTimerManipulation();
      this.placedBlocks.clear();
   }

   @Override
   public void onDisable() {
      if (ClientUtils.isInWorld() && this.aisgr != -1) {
         mc.thePlayer.inventory.currentItem = this.aisgr;
      }

      this.aisgr = -1;
      this.currentTarget = null;
      this.currentHitVec = null;
      this.towerTellyActive = false;
      this.keepYTellyActive = false;
      this.useKeyPressed = false;
      this.usedWhileAirborne = false;
      this.icePlaced = false;
      this.towerJumpStarted = false;
      this.JTw = false;
      this.descendBlockPlaced = false;
      this.towerJumping = false;
      this.towerFallInProgress = false;
      this.restoreSneakKey();
      this.downPlacedY = Integer.MIN_VALUE;
      this.towerJumpTicks = 0;
      this.descendGroundTicks = 0;
      this.watchedDownPos = null;
      this.SOkt = null;
      this.rotationsApplied = false;
      this.rotationEvent = null;
      this.pendingSlotRestore = -1;
      this.slotRestoreDelay = 0;
      this.iceSwapCooldown = 0;
      this.mC23 = null;
      this.rotationStatus = "disabled";
      this.placeStatus = "none";
      this.ICx = "none";
      this.rayStatus = "none";
      this.wAp = "none";
      this.EHbJ4 = 0L;
      this.lastPlaceTime = 0L;
      this.restoreTimerSpeed();
      this.resetTimerManipulation();
      this.placedBlocks.clear();
   }

   @Override
   public String getInfo() {
      return this.rotationModes[(int)this.rotation.getInput()];
   }

   @Override
   public void onUpdate() {
      if (this.isActive()) {
         this.tickIceSwap();
         this.AXyp();
         this.clearDescentState();
         this.updateGroundState();
      }
   }

   @Subscribe(priority = EventPriority.LOW)
   public void onRotation(RotationEvent var1) {
      boolean var2 = this.nua3();
      float var3 = this.targetYaw;
      float var4 = this.AAnih1;
      this.rotationEvent = null;
      if (this.isActive()) {
         if (this.rotationDelayTicks > 0) {
            this.rotationDelayTicks--;
         }

         this.currentTarget = null;
         this.currentHitVec = null;
         this.rotationsApplied = false;
         int var5 = (int)this.rotation.getInput();
         boolean var6 = var5 == 3;
         boolean var7 = var5 == 2 || var6;
         if (var6) {
            this.rotationEvent = var1;
            this.rotationSet = false;
         }

         this.rotationStatus = "rot-start";
         this.placeStatus = "none";
         this.rayStatus = "ray=pending";
         this.wAp = "packet=pending";
         if (this.autoSwap.isToggled()) {
            this.selectPlaceableSlot();
         }

         if (!this.isHoldingBlock()) {
            this.rotationStatus = "no-block";
            this.towerTellyActive = false;
         } else {
            float var8 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
            float var9 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
            float var10 = this.getMovementYaw();
            float var11 = this.wrapAngleNear(var10 - 180.0F, var8);
            float var12 = this.isDiagonalYaw(var10) ? var11 : this.wrapAngleNear(var10 - 135.0F * ((var10 + 180.0F) % 90.0F < 45.0F ? 1.0F : -1.0F), var8);
            boolean var13 = this.ApCjv();
            boolean var14 = this.gln8() && this.isOnDiagonalYaw();
            this.cbnePgq();
            boolean var15 = this.isDownwardsKeyHeld();
            boolean var16 = this.shouldResetDescentState();
            boolean var17 = this.DpwCbsM();
            boolean var18 = var15 && !var16 && !var17;
            if (var16) {
               this.keepYTellyActive = false;
            }

            boolean var19 = this.isDescendingActive();
            boolean var20 = this.Jyei();
            boolean var21 = var15 && (var20 || var19);
            boolean var22 = this.isStandingOnDownPlacedBlock();
            if (var22) {
               this.rotationSet = false;
            }

            this.ICx = "keys f="
               + this.getForwardInput()
               + " s="
               + this.getStrafeInput()
               + " move="
               + this.TltejZ()
               + " airAbove="
               + this.hasHeadroom()
               + " curYaw="
               + this.lRalKqm(var10)
               + " diag="
               + var13;
            if (!var22 && !var18 && !this.rotationSet && !var21) {
               float var23 = Float.NaN;
               switch (var5) {
                  case 1:
                     var23 = var11;
                     break;
                  case 2:
                     var23 = var12;
                  case 3:
               }

               if (!Float.isNaN(var23)) {
                  if (this.targetYaw == -180.0F && this.AAnih1 == 0.0F) {
                     float[] var24 = RotationUtils.NSsr(var23, var7 ? 85.0F : 70.0F, var8, var9);
                     this.targetYaw = var24[0];
                     this.AAnih1 = var24[1];
                  } else {
                     this.targetYaw = RotationUtils.NSsr(var23, this.AAnih1, var8, var9)[0];
                  }
               }
            }

            if (!var22 && !var15 && var5 == 1 && this.QSDa()) {
               float[] var35 = RotationUtils.NSsr(var11, 70.0F, var8, var9);
               this.targetYaw = var35[0];
               this.AAnih1 = var35[1];
               this.rotationSet = true;
               this.rotationStatus = "pre-rotate";
            }

            Scaffold$3 var36 = !var20 && !var22 ? this.findTarget(this.wrapAngleNear(this.targetYaw, var8), this.AAnih1) : null;
            Scaffold$5 var37 = var36 == null ? null : Scaffold$3.getPlacement(var36);
            boolean var25 = false;
            if (var20) {
               Scaffold$3 var26 = this.NCREwW();
               if (var26 != null) {
                  float[] var27 = RotationUtils.NSsr(this.wrapAngleNear(mc.thePlayer.rotationYaw, var8), 90.0F, var8, var9);
                  var36 = var26;
                  var37 = new Scaffold$5(var27[0], var27[1], this.getBlockBottomCenter(var26));
                  this.targetYaw = Scaffold$5.getTargetYaw(var37);
                  this.AAnih1 = Scaffold$5.getTargetPitch(var37);
                  this.rotationDelayTicks = 0;
                  this.rotationStatus = "downshift-anchor " + this.formatTarget(var26);
               }
            }

            if (var36 != null) {
               if (var37 == null) {
                  var37 = this.NKFba(var36, this.wrapAngleNear(this.targetYaw, var8), this.AAnih1, var7);
               }

               if (var37 != null) {
                  boolean var38 = this.wxxowgT(var36);
                  var25 = var38;
                  this.targetYaw = Scaffold$5.getTargetYaw(var37);
                  this.AAnih1 = Scaffold$5.getTargetPitch(var37);
                  if (this.isDownwardTarget(var36)) {
                     this.AAnih1 = 90.0F;
                  }

                  this.rotationSet = true;
                  if (var38 && !this.qpLc6(var36, RotationUtils.traceBlockThroughUncollidable(mc.playerController.getBlockReachDistance(), this.targetYaw, this.AAnih1))) {
                     this.rotationStatus = "downshift-smooth " + this.formatTarget(var36);
                  } else {
                     this.currentHitVec = Scaffold$5.getHitPoint(var37);
                     this.currentTarget = var36;
                     this.rotationStatus = "rot-hit " + this.formatTarget(var36);
                  }
               } else {
                  this.rotationStatus = "rot-no-ray " + this.formatTarget(var36);
               }
            } else {
               this.rotationStatus = "no-target";
            }

            if (this.rotationSet && !var25 && !this.isMultiFaceTarget(this.currentTarget) && this.TltejZ() && var5 == 1) {
               float[] var39 = RotationUtils.NSsr(var11, 70.0F, var8, var9);
               this.targetYaw = var39[0];
               this.AAnih1 = var39[1];
            }

            boolean var40 = this.isTowerJumpReady();
            boolean var28 = var17 || var40 || !var18 && this.isKeepYJumpReady();
            boolean var29 = !var17 && !var40 && var13 && var37 != null;
            if (!var6 && !var17 && !var29 && (this.towerTellyActive || this.keepYTellyActive) && (mc.thePlayer.motionY > 0.0 || mc.thePlayer.posY > this.keepYLevel + 1.0)) {
               float var30 = MathHelper.wrapAngleTo180_float(this.targetYaw - var8);
               float var31 = this.JGv8(this.keepYTellyActive && var13);
               if (Math.abs(var30) > var31) {
                  float var32 = this.AXCHI(var30, var31);
                  float[] var33 = RotationUtils.NSsr(var8 + var32, this.AAnih1, var8, var9);
                  this.targetYaw = var33[0];
                  this.AAnih1 = var33[1];
                  this.rotationDelayTicks = Math.max(this.rotationDelayTicks, 1);
               }

               this.rotationStatus = "keepy-telly-follow";
            }

            if (var28) {
               float var41 = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - var8);
               float var42;
               if (var17) {
                  var42 = var8 + var41 * (float)ClientUtils.randomDouble(0.98, 0.99);
               } else if (var40) {
                  var42 = this.computeTellyYaw(var8, var41, var13, var14);
               } else if (var29) {
                  var42 = Scaffold$5.getTargetYaw(var37);
               } else {
                  var42 = var8 + var41 * (float)ClientUtils.randomDouble(0.98, 0.99);
               }

               float var43 = var17 ? 85.0F : (var29 ? Scaffold$5.getTargetPitch(var37) : (float)ClientUtils.randomDouble(30.0, 80.0));
               float[] var44;
               if (var40) {
                  var44 = RotationUtils.NSsr(var42, var43, var8, var9);
               } else {
                  float[] var34 = RotationUtils.smoothAnglesRandomized(var8, var9, var42, var43, var17 ? 3 : 30, 3.0F);
                  var44 = RotationUtils.NSsr(var34[0], var34[1], var8, var9);
               }

               this.targetYaw = var44[0];
               this.AAnih1 = var44[1];
               this.rotationDelayTicks = var17 ? 0 : 3;
               this.towerTellyActive = var40;
               this.keepYTellyActive = var28 && !var40;
               if (this.keepYTellyActive) {
                  this.towerTellyActive = false;
               }

               this.rotationSet = true;
               this.rotationStatus = (var17 ? "downshift-telly-flat " : (var40 ? "tower-telly " : "keepy-telly-snap ")) + "delta=" + this.lRalKqm(var41);
            } else if (var18 || !this.XDGNs()) {
               this.keepYTellyActive = false;
            }

            if (var6) {
               this.rotationDelayTicks = 0;
               this.rotationSet = var37 != null && this.hasActiveTarget() && this.drlFtk(this.currentTarget) && (!this.duEirm() || !this.isWaitingForFall(this.currentTarget));
               if (this.rotationSet) {
                  this.targetYaw = Scaffold$5.getTargetYaw(var37);
                  this.AAnih1 = Scaffold$5.getTargetPitch(var37);
               } else if (var2 && this.Qmp4() && this.hasSupportAhead()) {
                  this.targetYaw = var3;
                  this.AAnih1 = var4;
                  this.rotationSet = true;
                  this.rotationStatus = "snap holding for next gap";
               }
            }

            if (!var22 && this.rotationSet && (var5 != 0 || var21 || var40 || this.towerTellyActive)) {
               this.rotationsApplied = var1.setRotation(this.targetYaw, this.AAnih1, 60);
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPreUpdate(PreUpdateEvent var1) {
      if (this.isActive()) {
         boolean var2 = this.Qmp4();
         boolean var3 = this.DYjg();
         if (var2 || var3) {
            if ((var2 ? this.nua3() : this.rotationsApplied && this.rotationSet) && this.isHoldingBlock()) {
               Float var4 = RotationHandler.getInstance().getTargetYaw();
               Float var5 = RotationHandler.getInstance().cvZx();
               if (var4 != null && var5 != null) {
                  this.placeBlockWithRotations(var4, var5);
               }
            }

            this.currentTarget = null;
            this.currentHitVec = null;
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void ePgq3(UpdateWalkingPlayerEvent var1) {
      if (this.isActive()) {
         if (!this.Qmp4() && !this.DYjg()) {
            boolean var2 = this.isDownwardsKeyHeld();
            if (var2) {
               var1.LWId(false);
            }

            if (this.autoSwap.isToggled() && !this.selectPlaceableSlot()) {
               this.setPlaceStatus("no slot");
            } else if (!this.isHoldingBlock()) {
               this.setPlaceStatus("not holding block");
            } else {
               int var3 = (int)this.rotation.getInput();
               boolean var4 = var2 && (this.Jyei() || this.isDescendingActive());
               boolean var5 = this.isTowerJumpReady() || this.towerTellyActive;
               this.wAp = "packet yaw=" + this.lRalKqm(var1.getYaw()) + " pitch=" + this.lRalKqm(var1.getPitch());
               if ((var3 != 0 || var4 || var5) && !this.rotationsApplied) {
                  this.setPlaceStatus("rotations not applied");
               } else {
                  this.placeBlockWithRotations(var1.getYaw(), var1.getPitch());
                  if (var3 == 3) {
                     this.currentTarget = null;
                     this.currentHitVec = null;
                  }
               }
            }
         }
      }
   }

   private boolean Qmp4() {
      return (int)this.rotation.getInput() == 3 && (int)this.keepY.getInput() == 0 && !this.isDownwardsKeyHeld() && !this.isTowerJumpReady() && !this.towerTellyActive;
   }

   private boolean DYjg() {
      return (int)this.rotation.getInput() == 2 && (int)this.keepY.getInput() == 0 && !this.isDownwardsKeyHeld() && !this.isTowerJumpReady() && !this.towerTellyActive;
   }

   private boolean nua3() {
      RotationEvent var1 = this.rotationEvent;
      return var1 != null
         && this.rotationsApplied
         && this.rotationSet
         && var1.Jxhy() == 60
         && var1.MGzP2 != null
         && var1.pitch != null
         && Math.abs(MathHelper.wrapAngleTo180_float(var1.MGzP2 - this.targetYaw)) < 1.0E-4F
         && Math.abs(var1.pitch - this.AAnih1) < 1.0E-4F;
   }

   private Vec3 getLastReportedEyePos() {
      IAccessorEntityPlayerSP var1 = (IAccessorEntityPlayerSP)mc.thePlayer;
      return new Vec3(var1.getLastReportedPosX(), var1.getLastReportedPosY() + mc.thePlayer.getEyeHeight(), var1.getLastReportedPosZ());
   }

   private MovingObjectPosition rayTraceAtRotation(Vec3 var1, float var2, float var3) {
      Vec3 var4 = RotationUtils.getLookVector(var3, var2);
      double var5 = mc.playerController.getBlockReachDistance();
      return mc.theWorld.rayTraceBlocks(var1, var1.addVector(var4.xCoord * var5, var4.yCoord * var5, var4.zCoord * var5), false, false, true);
   }

   private void placeBlockWithRotations(float var1, float var2) {
      Scaffold$3 var3 = this.currentTarget;
      Vec3 var4 = this.currentHitVec;
      if (var3 != null && var4 != null && this.rotationDelayTicks <= 0) {
         if (this.duEirm() && this.isWaitingForFall(var3)) {
            this.setPlaceStatus("waiting fall");
            return;
         }

         MovingObjectPosition var5 = RotationUtils.traceBlockThroughUncollidable(mc.playerController.getBlockReachDistance(), var1, var2);
         this.rayStatus = this.formatRayResult(var5);
         if (this.Qmp4() || this.DYjg()) {
            IAccessorEntityPlayerSP var6 = (IAccessorEntityPlayerSP)mc.thePlayer;
            if (!this.drlFtk(var3)
               || !PlaceRotations.isTargetHitByReportedRotation(
                  Scaffold$3.getTargetPos(var3),
                  Scaffold$3.getFacing(var3),
                  mc.thePlayer.getPositionEyes(1.0F),
                  this.getLastReportedEyePos(),
                  var6.getLastReportedYaw(),
                  var6.getLastReportedPitch(),
                  var1,
                  var2,
                  this::rayTraceAtRotation
               )) {
               this.setPlaceStatus("waiting for reported position/rotation");
               return;
            }
         }

         if ((int)this.rotation.getInput() == 3) {
            if (!this.drlFtk(var3)) {
               this.setPlaceStatus("invalid placement target");
               return;
            }

            if (!this.qpLc6(var3, var5) && mc.thePlayer.getPositionEyes(1.0F).distanceTo(var4) > mc.playerController.getBlockReachDistance() + 0.01) {
               this.setPlaceStatus("hit out of reach");
               return;
            }
         }

         Scaffold$3.setHitVector(var3, this.isDownwardTarget(var3) ? var4 : this.resolveHitVector(var3, var5, var4));
         boolean var9 = this.LrF9(var3);
         int var7 = this.heldBlockCount;
         boolean var8 = var9 ? this.placeBlockWithIceSwap(var3) : this.placeBlock(var3);
         if (var8 && this.isDownwardTarget(var3)) {
            this.watchedDownPos = Scaffold$3.getTargetPos(var3).offset(Scaffold$3.getFacing(var3));
            this.onDownBlockPlaced(this.watchedDownPos);
            this.descendBlockPlaced = true;
         } else if (var8 && this.dZpt38(var3) && this.SOkt == null) {
            this.SOkt = Scaffold$3.getTargetPos(var3).offset(Scaffold$3.getFacing(var3));
         } else if (!var8 && this.isDescendingAirborne()) {
            this.FpT1();
         }

         if (var8 && var9) {
            this.heldBlockCount = var7;
            this.icePlaced = true;
            this.iceSwapCooldown = 4;
         }

         this.placeStatus = (var8 ? (var9 ? "placed ice " : "placed ") : "rightClick false ") + this.formatTarget(var3);
         if (!this.qpLc6(var3, var5)) {
            this.placeStatus = this.placeStatus + " packet-ray-miss";
         }
      } else if (var3 == null || var4 == null) {
         this.setPlaceStatus("no data/hit");
      } else if (this.rotationDelayTicks > 0) {
         this.setPlaceStatus("rotationTick " + this.rotationDelayTicks);
      } else {
         this.setPlaceStatus("blocked " + this.formatTarget(var3));
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onMoveInput(MoveInputEvent var1) {
      if (this.isActive()) {
         if (!this.isDownwardsKeyHeld()) {
            this.szMk();
            this.resetDescentState();
            this.restoreSneakKey();
         } else {
            this.releaseSneakKey();
            var1.setSneaking(false);
            mc.thePlayer.movementInput.sneak = false;
            boolean var2 = this.Jyei();
            boolean var3 = this.isStandingOnPlacedBlock();
            this.towerJumpStarted = var3;
            if (this.towerJumpStarted) {
               mc.thePlayer.setSprinting(true);
               this.startTowerJump();
               var1.setJumping(true);
            } else if (var2) {
               var1.setJumping(false);
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onMoveStateUpdate(MoveStateUpdateEvent var1) {
      if (this.isActive()) {
         if (this.isDownwardsKeyHeld()) {
            mc.thePlayer.movementInput.sneak = false;
            if (this.towerJumpStarted || this.isStandingOnPlacedBlock()) {
               mc.thePlayer.setSprinting(true);
               this.startTowerJump();
               mc.thePlayer.movementInput.jump = true;
            } else if (this.Jyei()) {
               mc.thePlayer.movementInput.jump = false;
            }
         }

         if (!this.isDownwardsKeyHeld() && !this.isTowerJumpReady() && mc.thePlayer.onGround && this.keepYJumpTicks > 0 && this.TltejZ() && !ClientUtils.isJumpKeyDown()) {
            mc.thePlayer.movementInput.jump = true;
         }
      }
   }

   @Subscribe
   public void onMoveFlying(MoveFlyingEvent var1) {
      if (this.isActive()) {
         this.dqmi(var1);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
      if (this.isActive() && this.pendingSlotRestore != -1) {
         if (this.slotRestoreDelay > 0) {
            this.slotRestoreDelay--;
         } else {
            int var2 = this.pendingSlotRestore;
            this.pendingSlotRestore = -1;
            this.switchToSlot(var2);
         }
      }
   }

   @Subscribe
   public void onLeftClick(LeftClickEvent var1) {
      if (this.isEnabled()) {
         var1.setCancelled(true);
      }
   }

   @Subscribe
   public void onRightClick(RightClickEvent var1) {
      if (this.isEnabled()) {
         var1.setCancelled(true);
      }
   }

   @Subscribe
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (this.isEnabled() && this.autoSwap.isToggled()) {
         var1.setCancelled(true);
      }
   }

   @Subscribe
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (this.isEnabled() && this.autoSwap.isToggled()) {
         this.aisgr = var1.slot;
         var1.setCancelled(true);
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         this.Yszd2();
      } else if (var1.eventPhase == EventPhase.END && this.isEnabled() && ClientUtils.isInWorld() && mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
         if (this.blocksHud.isToggled() && !this.isBridgeAssistEnabled()) {
            long var2 = System.currentTimeMillis();
            float var4 = this.getPlaceFlashAlpha(var2);
            if (var4 > 0.0F) {
               BridgeAssist.renderBlocksHudAtCenter(BridgeAssist.countHotbarBlocks(), this.getHudFontName(), var4);
            }
         }
      }
   }

   private void Yszd2() {
      if (this.isEnabled() && this.timeManipulation.isToggled() && ClientUtils.isInWorld() && this.GohoqM()) {
         long var1 = System.nanoTime();
         if (!this.timerManipulating) {
            this.timerManipulating = true;
            this.lastTimerTickNanos = var1;
            this.appliedTimerSpeed = 1.0F;
            this.CcUaxf = 0.0;
            this.j616 = false;
         } else {
            double var3 = Math.min((var1 - this.lastTimerTickNanos) / 1000000.0, 50.0);
            this.lastTimerTickNanos = var1;
            this.CcUaxf = this.CcUaxf + (1.0 - this.appliedTimerSpeed) * var3;
            this.CcUaxf = MathHelper.clamp_double(this.CcUaxf, 0.0, 2000.0);
         }

         this.timerSpeedApplied = true;
         float var6 = 1.0F;
         if (this.hasActiveTarget()) {
            var6 = (float)this.lowTimer.getInput();
            this.j616 = true;
         } else if (this.j616 && this.CcUaxf > 0.01) {
            float var4 = (float)this.highTimer.getInput();
            float var5 = (float)(1.0 + this.CcUaxf / 50.0);
            var6 = Math.min(var4, var5);
         }

         if (mc.currentScreen != null) {
            var6 = 1.0F;
         }

         ((IAccessorMinecraft)mc).getTimer().timerSpeed = var6;
         this.appliedTimerSpeed = var6;
      } else {
         this.restoreTimerSpeed();
         this.resetTimerManipulation();
      }
   }

   private boolean hasActiveTarget() {
      return this.currentTarget != null && this.currentHitVec != null && this.rotationDelayTicks <= 0 && this.isHoldingBlock();
   }

   private boolean GohoqM() {
      boolean var1 = (int)this.tower.getInput() == 1 && (this.towerTellyActive || this.canTowerSprint());
      boolean var2 = (int)this.keepY.getInput() == 1 && (this.keepYTellyActive || this.isKeepYJumpReady() || this.XDGNs());
      return var1 || var2 || this.DpwCbsM();
   }

   private void resetTimerManipulation() {
      this.timerManipulating = false;
      this.j616 = false;
      this.lastTimerTickNanos = 0L;
      this.CcUaxf = 0.0;
      this.appliedTimerSpeed = 1.0F;
   }

   private void restoreTimerSpeed() {
      if (this.timerSpeedApplied) {
         this.timerSpeedApplied = false;
         Timer var1 = Jade.getModuleManager().getModule(Timer.class);
         if (var1 == null || !var1.isEnabled()) {
            ClientUtils.eqyXnoq();
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.isEnabled() && this.highlightPlacedBlocks.isToggled() && ClientUtils.isInWorld() && !this.placedBlocks.isEmpty()) {
         this.renderPlacedBlockHighlights();
      }
   }

   private void updateGroundState() {
      if (this.useKeyPressed && !mc.thePlayer.onGround) {
         this.usedWhileAirborne = true;
      }

      if (mc.thePlayer.onGround) {
         if (this.isDownwardsKeyHeld()) {
            this.keepYJumpTicks = 0;
            this.keepYLevelLocked = false;
            this.keepYTellyActive = false;
            this.icePlaced = false;
            this.keepYLevel = MathHelper.floor_double(mc.thePlayer.posY);
            return;
         }

         if (this.keepYJumpTicks > 0) {
            this.keepYJumpTicks--;
         } else if (this.keepYJumpTicks < 0) {
            this.keepYJumpTicks++;
         }

         boolean var1 = (int)this.keepY.getInput() != 0 && (!this.keepYOnUse.isToggled() || this.ZOTYw()) && !mc.thePlayer.isPotionActive(Potion.jump) && !ClientUtils.isJumpKeyDown();
         if (this.keepYJumpTicks == 0 && var1) {
            this.keepYJumpTicks = 1;
         }

         if (!this.keepYLevelLocked) {
            this.keepYLevel = MathHelper.floor_double(mc.thePlayer.posY);
         }

         this.keepYLevelLocked = false;
         this.keepYTellyActive = false;
         this.icePlaced = false;
         if (this.usedWhileAirborne) {
            this.useKeyPressed = false;
            this.usedWhileAirborne = false;
         }
      }
   }

   private void tickIceSwap() {
      if (!this.iceSwap.isToggled()) {
         this.iceSwapCooldown = 0;
      } else {
         if (this.iceSwapCooldown > 0) {
            this.iceSwapCooldown--;
         }
      }
   }

   private void AXyp() {
      if (!this.isDownwardsKeyHeld()) {
         this.szMk();
         this.resetDescentState();
         this.restoreSneakKey();
      } else {
         if (mc.thePlayer.onGround) {
            this.descendGroundTicks++;
         } else {
            this.descendGroundTicks = 0;
         }

         if (this.towerJumping && this.towerJumpTicks > 0) {
            this.towerJumpTicks--;
         }

         if (this.towerJumping && this.towerJumpTicks == 0 && mc.thePlayer.onGround) {
            this.finishTowerJump();
         } else {
            if (this.watchedDownPos != null && !BlockUtils.isReplaceableAt(this.watchedDownPos)) {
               this.onDownBlockPlaced(this.watchedDownPos);
            }
         }
      }
   }

   private void onDownBlockPlaced(BlockPos var1) {
      this.JTw = true;
      this.downPlacedY = var1.getY();
      this.keepYLevel = this.downPlacedY + 1;
      this.SOkt = null;
   }

   private void clearDescentState() {
      if (this.shouldResetDescentState()) {
         this.towerJumpStarted = false;
         this.JTw = false;
         this.descendBlockPlaced = false;
         this.towerJumping = false;
         this.downPlacedY = Integer.MIN_VALUE;
         this.towerJumpTicks = 0;
         this.watchedDownPos = null;
      }
   }

   private void resetDescentState() {
      this.towerJumpStarted = false;
      this.JTw = false;
      this.descendBlockPlaced = false;
      this.towerJumping = false;
      this.downPlacedY = Integer.MIN_VALUE;
      this.towerJumpTicks = 0;
      this.descendGroundTicks = 0;
      this.watchedDownPos = null;
      this.SOkt = null;
   }

   private void finishTowerJump() {
      this.resetDescentState();
      this.towerFallInProgress = false;
      this.descendGroundTicks = 2;
      this.rotationDelayTicks = 0;
   }

   private int randomTowerJumpTicks() {
      int var1 = 21;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int var2 = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
         var1 -= Math.min(1, var2);
      }

      double var5 = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
      if (var5 > 0.48) {
         var1--;
      } else if (var5 < 0.2) {
         var1++;
      }

      var1 += (int)Math.round(ClientUtils.randomDouble(-1.0, 1.0));
      return MathHelper.clamp_int(var1, 18, 24);
   }

   private void dqmi(MoveFlyingEvent var1) {
      if (!this.canTowerSprint()) {
         this.towerTellyActive = false;
      } else {
         this.towerTellyActive = true;
      }
   }

   private Scaffold$3 findTarget(float var1, float var2) {
      int var3 = MathHelper.floor_double(mc.thePlayer.posY);
      int var4 = (this.keepYJumpTicks != 0 && !this.keepYLevelLocked ? Math.min(var3, this.keepYLevel) : var3) - 1;
      if (this.shouldResetDescentState()) {
         var4 = var3 - 1;
      } else if (this.shouldReuseDescentY()) {
         var4 = this.downPlacedY;
      }

      BlockPos var5 = new BlockPos(MathHelper.floor_double(mc.thePlayer.posX), var4, MathHelper.floor_double(mc.thePlayer.posZ));
      boolean var6 = (int)this.rotation.getInput() == 3;
      boolean var7 = var6 || this.DYjg();
      if (var7 && !this.isDownwardsKeyHeld()) {
         Vec3 var8 = this.getPredictedEyePos();

         for (BlockPos var10 : BlockSearch.buildTracePositions(
            mc.thePlayer.posX, var4, mc.thePlayer.posZ, var8.xCoord - mc.thePlayer.posX, var8.zCoord - mc.thePlayer.posZ
         )) {
            if (BlockUtils.isReplaceableAt(var10)) {
               return this.findPlacementFor(var10, true, var1, var2);
            }
         }

         return null;
      } else {
         return this.findPlacementFor(var5, var6, var1, var2);
      }
   }

   private Scaffold$3 findPlacementFor(BlockPos var1, boolean var2, float var3, float var4) {
      this.gPcd = var1;
      this.QRk = 0;
      if (!BlockUtils.isReplaceableAt(var1)) {
         this.rotationStatus = "target occupied " + var1;
         return null;
      } else {
         List<BlockPos> var5 = var2 ? BlockSearch.findSupportCandidates(var1, this::isValidSupport) : new ArrayList();
         if (!var2) {
            for (int var6 = -4; var6 <= 4; var6++) {
               for (int var7 = -4; var7 <= 0; var7++) {
                  for (int var8 = -4; var8 <= 4; var8++) {
                     BlockPos var9 = var1.add(var6, var7, var8);
                     if (this.isValidSupport(var9)) {
                        for (EnumFacing var13 : EnumFacing.values()) {
                           if (var13 != EnumFacing.DOWN && BlockUtils.isReplaceableAt(var9.offset(var13))) {
                              var5.add(var9);
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         this.QRk = var5.size();
         if (var5.isEmpty()) {
            this.rotationStatus = "no supports " + var1;
            return null;
         } else if (var2) {
            return BlockSearch.findPlacement(var1, (List<BlockPos>)var5, BlockUtils::isReplaceableAt, (recoveredArg0, recoveredArg1) -> this.createPlacementCandidate(var3, var4, recoveredArg0, recoveredArg1));
         } else {
            Scaffold$3 var14 = this.AJFio((List<BlockPos>)var5, var1);
            if (var14 != null) {
               return var14;
            } else {
               var5.sort(Comparator.comparingDouble((recoveredArg0) -> Scaffold.distanceSqToCenter(var1, (net.minecraft.util.BlockPos) recoveredArg0)));
               BlockPos var15 = (BlockPos)var5.get(0);
               EnumFacing var16 = this.findBestSupportFace(var15, var1);
               return var16 == null ? null : new Scaffold$3(var15, var16);
            }
         }
      }
   }

   private boolean isValidSupport(BlockPos var1) {
      return !BlockUtils.isReplaceableAt(var1)
         && !BlockUtils.isInteractiveBlock(BlockUtils.iepjdt(var1))
         && mc.thePlayer.getDistance(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5) <= mc.playerController.getBlockReachDistance()
         && (this.keepYJumpTicks == 0 || this.keepYLevelLocked || var1.getY() < this.keepYLevel);
   }

   private Scaffold$3 AJFio(List<BlockPos> var1, BlockPos var2) {
      if (this.mC23 != null && var1.contains(Scaffold$3.getTargetPos(this.mC23))) {
         EnumFacing var3 = this.findBestSupportFace(Scaffold$3.getTargetPos(this.mC23), var2);
         return var3 != null && var3 == Scaffold$3.getFacing(this.mC23) ? new Scaffold$3(Scaffold$3.getTargetPos(this.mC23), Scaffold$3.getFacing(this.mC23)) : null;
      } else {
         return null;
      }
   }

   private Scaffold$3 NCREwW() {
      if (!this.isHoldingBlock()) {
         return null;
      } else {
         BlockPos var1 = this.findBlockBelowPlayer();
         if (var1 == null) {
            return null;
         } else {
            BlockPos var2 = var1.down();
            ItemStack var3 = mc.thePlayer.getHeldItem();
            if (!BlockUtils.isReplaceableAt(var1)
               && !BlockUtils.isInteractiveBlock(BlockUtils.iepjdt(var1))
               && BlockUtils.isReplaceableAt(var2)
               && BlockUtils.canPlaceItemOnSide(var3, var1, EnumFacing.DOWN)) {
               return mc.thePlayer.getDistance(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5) > mc.playerController.getBlockReachDistance()
                  ? null
                  : new Scaffold$3(var1, EnumFacing.DOWN, true);
            } else {
               return null;
            }
         }
      }
   }

   private BlockPos findBlockBelowPlayer() {
      AxisAlignedBB var1 = mc.thePlayer.getEntityBoundingBox();
      int var2 = MathHelper.floor_double(mc.thePlayer.posY) - 1;
      double var3 = 0.03;
      double[] var5 = new double[]{mc.thePlayer.posX, var1.minX + var3, var1.maxX - var3};
      double[] var6 = new double[]{mc.thePlayer.posZ, var1.minZ + var3, var1.maxZ - var3};
      BlockPos var7 = null;
      double var8 = 0.0;

      for (double var13 : var5) {
         for (double var18 : var6) {
            BlockPos var20 = new BlockPos(MathHelper.floor_double(var13), var2, MathHelper.floor_double(var18));
            if (!BlockUtils.isReplaceableAt(var20) && !BlockUtils.isInteractiveBlock(BlockUtils.iepjdt(var20))) {
               double var21 = this.horizontalOverlapArea(var1, var20);
               if (var7 == null || var21 > var8) {
                  var7 = var20;
                  var8 = var21;
               }
            }
         }
      }

      return var7;
   }

   private double horizontalOverlapArea(AxisAlignedBB var1, BlockPos var2) {
      double var3 = Math.min(var1.maxX, var2.getX() + 1.0) - Math.max(var1.minX, (double)var2.getX());
      double var5 = Math.min(var1.maxZ, var2.getZ() + 1.0) - Math.max(var1.minZ, (double)var2.getZ());
      return !(var3 <= 0.0) && !(var5 <= 0.0) ? var3 * var5 : 0.0;
   }

   private Vec3 getBlockBottomCenter(Scaffold$3 var1) {
      return new Vec3(Scaffold$3.getTargetPos(var1).getX() + 0.5, Scaffold$3.getTargetPos(var1).getY(), Scaffold$3.getTargetPos(var1).getZ() + 0.5);
   }

   private EnumFacing findBestSupportFace(BlockPos var1, BlockPos var2) {
      double var3 = 0.0;
      EnumFacing var5 = null;

      for (EnumFacing var9 : EnumFacing.values()) {
         if (var9 != EnumFacing.DOWN) {
            BlockPos var10 = var1.offset(var9);
            if (var10.getY() <= var2.getY() && BlockUtils.isReplaceableAt(var10)) {
               double var11 = var10.distanceSqToCenter(var2.getX() + 0.5, var2.getY() + 0.5, var2.getZ() + 0.5);
               if (var5 == null || var11 < var3 || var11 == var3 && var9 == EnumFacing.UP) {
                  var3 = var11;
                  var5 = var9;
               }
            }
         }
      }

      return var5;
   }

   private Scaffold$5 findReportedPlacement(Scaffold$3 var1, float var2, float var3) {
      List var4 = this.YDyt(var1);
      if (var4 == null) {
         return null;
      } else {
         Vec3 var5 = mc.thePlayer.getPositionEyes(1.0F);
         Vec3 var6 = this.getPredictedEyePos();
         IAccessorEntityPlayerSP var7 = (IAccessorEntityPlayerSP)mc.thePlayer;
         float[] var8 = PlaceRotations.lLwku(
            Scaffold$3.getTargetPos(var1),
            Scaffold$3.getFacing(var1),
            var4,
            var5,
            this.getLastReportedEyePos(),
            var6,
            var7.getLastReportedYaw(),
            var7.getLastReportedPitch(),
            mc.thePlayer.rotationYaw,
            (recoveredArg0, recoveredArg1) -> this.DaCz(var2, var3, recoveredArg0, recoveredArg1),
            this::rayTraceAtRotation
         );
         if (var8 == null) {
            return null;
         } else {
            MovingObjectPosition var9 = this.rayTraceAtRotation(var5, var8[0], var8[1]);
            if (!this.isRayHittingTarget(var1, var9)) {
               var9 = this.rayTraceAtRotation(var6, var8[0], var8[1]);
            }

            return !this.isRayHittingTarget(var1, var9) ? null : new Scaffold$5(var8[0], var8[1], var9.hitVec);
         }
      }
   }

   private boolean isRayHittingTarget(Scaffold$3 var1, MovingObjectPosition var2) {
      return var2 != null && var2.typeOfHit == MovingObjectType.BLOCK && Scaffold$3.getTargetPos(var1).equals(var2.getBlockPos());
   }

   private boolean hasSupportAhead() {
      Vec3 var1 = this.getPredictedEyePos();
      return BlockSearch.hasSupportAlongTrace(
         mc.thePlayer.posX,
         MathHelper.floor_double(mc.thePlayer.posY) - 1,
         mc.thePlayer.posZ,
         var1.xCoord - mc.thePlayer.posX,
         var1.zCoord - mc.thePlayer.posZ,
         BlockUtils::isReplaceableAt
      );
   }

   private Vec3 getPredictedEyePos() {
      Vec3 var1 = MovementPrediction.predictHorizontalDelta(
         mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ, mc.thePlayer.motionX, mc.thePlayer.motionZ
      );
      double var2 = mc.thePlayer.onGround ? 0.0 : mc.thePlayer.motionY;
      return mc.thePlayer.getPositionEyes(1.0F).addVector(var1.xCoord, var2, var1.zCoord);
   }

   private Scaffold$5 NKFba(Scaffold$3 var1, float var2, float var3, boolean var4) {
      List var5 = this.YDyt(var1);
      if (var5 == null) {
         return null;
      } else {
         Scaffold$5 var6 = null;
         float var7 = Float.MAX_VALUE;
         String var8 = "ray=none";

         for (Vec3 var10 : (java.lang.Iterable<Vec3>) (java.lang.Iterable<?>) (var5)) {
            float[] var11 = this.computeRotationToPoint(var10, var2, var3);
            MovingObjectPosition var12 = RotationUtils.traceBlockThroughUncollidable(mc.playerController.getBlockReachDistance(), var11[0], var11[1]);
            var8 = this.formatRayResult(var12);
            if (var4 ? this.isRayHittingTargetSide(var1, var12) : this.qpLc6(var1, var12)) {
               Vec3 var13 = var4 ? var12.hitVec : this.resolveHitVector(var1, var12, var10);
               float var14 = Math.abs(MathHelper.wrapAngleTo180_float(var11[0] - var2)) + Math.abs(var11[1] - var3);
               if (var6 == null || var14 < var7) {
                  var6 = new Scaffold$5(var11[0], var11[1], var13);
                  var7 = var14;
                  this.rayStatus = "rot hit " + var8;
               }
            }
         }

         if (var6 == null) {
            this.rayStatus = "rot miss " + var8;
         } else {
            this.mC23 = new Scaffold$3(Scaffold$3.getTargetPos(var1), Scaffold$3.getFacing(var1));
         }

         return var6;
      }
   }

   private List<Vec3> YDyt(Scaffold$3 var1) {
      if (Scaffold$3.isMultiFace(var1)) {
         return this.getBlockFaceSamplePoints(Scaffold$3.getTargetPos(var1));
      } else {
         double[] var2 = FACE_SAMPLE_OFFSETS;
         double[] var3 = FACE_SAMPLE_OFFSETS;
         double[] var4 = FACE_SAMPLE_OFFSETS;
         switch (Scaffold$3.getFacing(var1)) {
            case UP:
               var3 = new double[]{1.0};
               break;
            case NORTH:
               var4 = new double[]{0.0};
               break;
            case EAST:
               var2 = new double[]{1.0};
               break;
            case SOUTH:
               var4 = new double[]{1.0};
               break;
            case WEST:
               var2 = new double[]{0.0};
               break;
            default:
               return null;
         }

         ArrayList var5 = new ArrayList();

         for (double var9 : var2) {
            for (double var14 : var3) {
               for (double var19 : var4) {
                  var5.add(new Vec3(Scaffold$3.getTargetPos(var1).getX() + var9, Scaffold$3.getTargetPos(var1).getY() + var14, Scaffold$3.getTargetPos(var1).getZ() + var19));
               }
            }
         }

         return var5;
      }
   }

   private List<Vec3> getBlockFaceSamplePoints(BlockPos var1) {
      ArrayList var2 = new ArrayList();

      for (double var6 : FACE_SAMPLE_OFFSETS) {
         for (double var11 : FACE_SAMPLE_OFFSETS) {
            var2.add(new Vec3(var1.getX() + var6, var1.getY() + var11, var1.getZ()));
            var2.add(new Vec3(var1.getX() + var6, var1.getY() + var11, var1.getZ() + 1.0));
         }
      }

      for (double var19 : FACE_SAMPLE_OFFSETS) {
         for (double var27 : FACE_SAMPLE_OFFSETS) {
            var2.add(new Vec3(var1.getX() + var19, var1.getY(), var1.getZ() + var27));
            var2.add(new Vec3(var1.getX() + var19, var1.getY() + 1.0, var1.getZ() + var27));
         }
      }

      for (double var20 : FACE_SAMPLE_OFFSETS) {
         for (double var28 : FACE_SAMPLE_OFFSETS) {
            var2.add(new Vec3(var1.getX(), var1.getY() + var20, var1.getZ() + var28));
            var2.add(new Vec3(var1.getX() + 1.0, var1.getY() + var20, var1.getZ() + var28));
         }
      }

      return var2;
   }

   private boolean qpLc6(Scaffold$3 var1, MovingObjectPosition var2) {
      if (var2 == null || var2.typeOfHit != MovingObjectType.BLOCK || !Scaffold$3.getTargetPos(var1).equals(var2.getBlockPos())) {
         return false;
      } else if (!Scaffold$3.isMultiFace(var1)) {
         return Scaffold$3.getFacing(var1) == var2.sideHit;
      } else {
         Scaffold$1 var3 = this.traceHitFace(Scaffold$3.getTargetPos(var1), var2.hitVec, this.getNormalizedEyeDirection(var2.hitVec));
         return var3 != null
            && Scaffold$1.getFacing(var3) == Scaffold$3.getFacing(var1)
            && BlockUtils.isReplaceableAt(Scaffold$3.getTargetPos(var1).offset(Scaffold$1.getFacing(var3)));
      }
   }

   private boolean isRayHittingTargetSide(Scaffold$3 var1, MovingObjectPosition var2) {
      return var1 != null
         && var2 != null
         && var2.typeOfHit == MovingObjectType.BLOCK
         && Scaffold$3.getTargetPos(var1).equals(var2.getBlockPos())
         && Scaffold$3.getFacing(var1) == var2.sideHit;
   }

   private Vec3 resolveHitVector(Scaffold$3 var1, MovingObjectPosition var2, Vec3 var3) {
      if (Scaffold$3.isMultiFace(var1) && var2 != null && Scaffold$3.getTargetPos(var1).equals(var2.getBlockPos())) {
         Scaffold$1 var4 = this.traceHitFace(Scaffold$3.getTargetPos(var1), var2.hitVec, this.getNormalizedEyeDirection(var2.hitVec));
         if (var4 != null && Scaffold$1.getFacing(var4) == Scaffold$3.getFacing(var1)) {
            return Scaffold$1.tkRv(var4);
         }
      }

      return !Scaffold$3.isMultiFace(var1) && this.qpLc6(var1, var2) ? var2.hitVec : var3;
   }

   private boolean drlFtk(Scaffold$3 var1) {
      return var1 != null
         && Scaffold$3.getTargetPos(var1) != null
         && Scaffold$3.getFacing(var1) != null
         && !BlockUtils.isReplaceableAt(Scaffold$3.getTargetPos(var1))
         && !BlockUtils.isInteractiveBlock(BlockUtils.iepjdt(Scaffold$3.getTargetPos(var1)))
         && BlockUtils.isReplaceableAt(Scaffold$3.getTargetPos(var1).offset(Scaffold$3.getFacing(var1)));
   }

   private boolean placeBlock(Scaffold$3 var1) {
      ItemStack var2 = mc.thePlayer.getHeldItem();
      if (!this.isPlaceableBlock(var2)) {
         return false;
      } else if (mc.playerController
         .onPlayerRightClick(mc.thePlayer, mc.theWorld, var2, Scaffold$3.getTargetPos(var1), Scaffold$3.getFacing(var1), Scaffold$3.Dtxaj(var1))) {
         if (mc.playerController.getCurrentGameType() != GameType.CREATIVE) {
            this.heldBlockCount--;
         }

         this.recordPlacement();
         this.qFgnys(Scaffold$3.getTargetPos(var1).offset(Scaffold$3.getFacing(var1)));
         if (this.swing.isToggled()) {
            mc.thePlayer.swingItem();
         } else {
            PacketUtils.sendSilently(new C0APacketAnimation());
         }

         return true;
      } else {
         return false;
      }
   }

   private void recordPlacement() {
      long var1 = System.currentTimeMillis();
      if (this.lastPlaceTime <= 0L || var1 - this.lastPlaceTime >= 1300L) {
         this.EHbJ4 = var1;
      }

      this.lastPlaceTime = var1;
   }

   private void qFgnys(BlockPos var1) {
      if (var1 != null) {
         for (int var2 = this.placedBlocks.size() - 1; var2 >= 0; var2--) {
            if (Scaffold$4.jPsg(this.placedBlocks.get(var2)).equals(var1)) {
               return;
            }
         }

         this.placedBlocks.add(new Scaffold$4(var1, System.currentTimeMillis()));
      }
   }

   private void renderPlacedBlockHighlights() {
      long var1 = System.currentTimeMillis();
      double var3 = mc.getRenderManager().viewerPosX;
      double var5 = mc.getRenderManager().viewerPosY;
      double var7 = mc.getRenderManager().viewerPosZ;
      ArrayList var9 = new ArrayList();
      int var10 = Arraylist.xQec0(0.0);
      Iterator var11 = this.placedBlocks.iterator();

      while (var11.hasNext()) {
         Scaffold$4 var12 = (Scaffold$4)var11.next();
         long var13 = var1 - Scaffold$4.getPlacedAtMillis(var12);
         float var15 = this.utqoN(var13);
         if (var15 <= 0.0F) {
            var11.remove();
         } else {
            int var16 = MathHelper.clamp_int(Math.round(116.0F * var15), 0, 255);
            AxisAlignedBB var17 = this.getBlockBounds(Scaffold$4.jPsg(var12)).expand(0.002, 0.002, 0.002).offset(-var3, -var5, -var7);
            int var18 = ClientUtils.YVVZ(var10, var16);

            for (EnumFacing var22 : EnumFacing.values()) {
               if (BlockUtils.isReplaceableAt(Scaffold$4.jPsg(var12).offset(var22))) {
                  var9.add(new Scaffold$2(var17, var22, var18, var18));
               }
            }
         }
      }

      if (!var9.isEmpty()) {
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         RenderUtils$1 var26 = null;

         try {
            var26 = RenderUtils.uyB6();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glEnable(2929);
            GL11.glEnable(2884);
            GL11.glDepthMask(true);
            GL11.glShadeModel(7424);
            GL11.glDepthFunc(515);
            GL11.glColorMask(false, false, false, false);

            for (Scaffold$2 var14 : (java.lang.Iterable<Scaffold$2>) (java.lang.Iterable<?>) (var9)) {
               this.QWSZrG(Scaffold$2.getBoundingBox(var14), Scaffold$2.getFacing(var14), -1, -1);
            }

            GL11.glColorMask(true, true, true, true);
            GL11.glDepthMask(false);

            for (Scaffold$2 var29 : (java.lang.Iterable<Scaffold$2>) (java.lang.Iterable<?>) (var9)) {
               this.QWSZrG(Scaffold$2.getBoundingBox(var29), Scaffold$2.getFacing(var29), Scaffold$2.getFillColor(var29), Scaffold$2.getOutlineColor(var29));
            }
         } finally {
            RenderUtils.restoreLightmapState(var26);
            GL11.glColorMask(true, true, true, true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            RenderUtils.resetGlPipelineState();
         }
      }
   }

   private float utqoN(long var1) {
      float var3 = MathHelper.clamp_float((float)var1 / 2200.0F, 0.0F, 1.0F);
      float var4 = 1.0F - var3;
      return var4 * var4;
   }

   private AxisAlignedBB getBlockBounds(BlockPos var1) {
      AxisAlignedBB var2 = BlockUtils.getSelectedBounds(var1);
      return var2 != null ? var2 : new AxisAlignedBB(var1.getX(), var1.getY(), var1.getZ(), var1.getX() + 1.0, var1.getY() + 1.0, var1.getZ() + 1.0);
   }

   private void QWSZrG(AxisAlignedBB var1, EnumFacing var2, int var3, int var4) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      var6.begin(7, DefaultVertexFormats.POSITION_COLOR);
      switch (var2) {
         case UP:
            this.emitColoredVertex(var6, var1.minX, var1.maxY, var1.maxZ, var3);
            this.emitColoredVertex(var6, var1.maxX, var1.maxY, var1.maxZ, var4);
            this.emitColoredVertex(var6, var1.maxX, var1.maxY, var1.minZ, var3);
            this.emitColoredVertex(var6, var1.minX, var1.maxY, var1.minZ, var4);
            break;
         case NORTH:
            this.emitColoredVertex(var6, var1.maxX, var1.maxY, var1.minZ, var3);
            this.emitColoredVertex(var6, var1.maxX, var1.minY, var1.minZ, var4);
            this.emitColoredVertex(var6, var1.minX, var1.minY, var1.minZ, var3);
            this.emitColoredVertex(var6, var1.minX, var1.maxY, var1.minZ, var4);
            break;
         case EAST:
            this.emitColoredVertex(var6, var1.maxX, var1.maxY, var1.minZ, var3);
            this.emitColoredVertex(var6, var1.maxX, var1.maxY, var1.maxZ, var4);
            this.emitColoredVertex(var6, var1.maxX, var1.minY, var1.maxZ, var3);
            this.emitColoredVertex(var6, var1.maxX, var1.minY, var1.minZ, var4);
            break;
         case SOUTH:
            this.emitColoredVertex(var6, var1.minX, var1.maxY, var1.maxZ, var3);
            this.emitColoredVertex(var6, var1.minX, var1.minY, var1.maxZ, var4);
            this.emitColoredVertex(var6, var1.maxX, var1.minY, var1.maxZ, var3);
            this.emitColoredVertex(var6, var1.maxX, var1.maxY, var1.maxZ, var4);
            break;
         case WEST:
            this.emitColoredVertex(var6, var1.minX, var1.maxY, var1.maxZ, var3);
            this.emitColoredVertex(var6, var1.minX, var1.maxY, var1.minZ, var4);
            this.emitColoredVertex(var6, var1.minX, var1.minY, var1.minZ, var3);
            this.emitColoredVertex(var6, var1.minX, var1.minY, var1.maxZ, var4);
            break;
         case DOWN:
            this.emitColoredVertex(var6, var1.maxX, var1.minY, var1.maxZ, var3);
            this.emitColoredVertex(var6, var1.minX, var1.minY, var1.maxZ, var4);
            this.emitColoredVertex(var6, var1.minX, var1.minY, var1.minZ, var3);
            this.emitColoredVertex(var6, var1.maxX, var1.minY, var1.minZ, var4);
      }

      var5.draw();
   }

   private void emitColoredVertex(WorldRenderer var1, double var2, double var4, double var6, int var8) {
      float var9 = (var8 >> 16 & 0xFF) / 255.0F;
      float var10 = (var8 >> 8 & 0xFF) / 255.0F;
      float var11 = (var8 & 0xFF) / 255.0F;
      float var12 = (var8 >> 24 & 0xFF) / 255.0F;
      var1.pos(var2, var4, var6).color(var9, var10, var11, var12).endVertex();
   }

   private boolean selectPlaceableSlot() {
      ItemStack var1 = mc.thePlayer.getHeldItem();
      int var2 = this.FiigqpM(var1) ? var1.stackSize : 0;
      this.heldBlockCount = Math.min(this.heldBlockCount, var2);
      if (this.heldBlockCount > 0 && this.isHoldingBlock()) {
         return true;
      } else {
         int var3 = mc.thePlayer.inventory.currentItem;
         if (this.heldBlockCount == 0) {
            var3--;
         }

         for (int var4 = var3; var4 > var3 - 9; var4--) {
            int var5 = (var4 % 9 + 9) % 9;
            ItemStack var6 = mc.thePlayer.inventory.getStackInSlot(var5);
            if (this.FiigqpM(var6)) {
               mc.thePlayer.inventory.currentItem = var5;
               this.heldBlockCount = var6.stackSize;
               return true;
            }
         }

         return this.isHoldingBlock();
      }
   }

   private boolean isHoldingBlock() {
      return ClientUtils.isInWorld() && this.FiigqpM(mc.thePlayer.getHeldItem());
   }

   private boolean FiigqpM(ItemStack var1) {
      return this.isPlaceableBlock(var1) && (!this.iceSwap.isToggled() || !this.isPackedIce(var1));
   }

   private boolean isPlaceableBlock(ItemStack var1) {
      if (var1 != null && var1.stackSize > 0 && var1.getItem() instanceof ItemBlock) {
         ItemBlock var2 = (ItemBlock)var1.getItem();
         Block var3 = var2.getBlock();
         return var3 != null && var3 != Blocks.air && ClientUtils.isPassableBlock(var2) && var3.isFullBlock() && var3.isCollidable();
      } else {
         return false;
      }
   }

   private boolean LrF9(Scaffold$3 var1) {
      if (this.iceSwap.isToggled() && var1 != null && (this.ZTOSmN() || this.canTowerSprintJump()) && !mc.thePlayer.onGround && !(mc.thePlayer.motionY >= 0.0)) {
         BlockPos var2 = Scaffold$3.getTargetPos(var1).offset(Scaffold$3.getFacing(var1));
         boolean var3 = !this.icePlaced;
         boolean var4 = this.icePlaced && this.iceSwapCooldown > 0;
         return var2.getY() == this.ucKv() && (var4 || var3 && this.willLandOnBlock(var2)) && this.findIceSlot() != -1;
      } else {
         return false;
      }
   }

   private boolean ZTOSmN() {
      return this.keepYJumpTicks > 0
         && this.keepYTellyActive
         && (int)this.keepY.getInput() == 1
         && (int)this.rotation.getInput() != 0
         && this.TltejZ()
         && (!this.keepYOnUse.isToggled() || this.useKeyPressed)
         && !ClientUtils.isJumpKeyDown();
   }

   private boolean canTowerSprintJump() {
      return (int)this.tower.getInput() == 1 && (this.towerTellyActive || this.canTowerSprint()) && this.TltejZ() && ClientUtils.isJumpKeyDown();
   }

   private int ucKv() {
      return this.canTowerSprintJump() ? MathHelper.floor_double(mc.thePlayer.posY) - 1 : this.keepYLevel - 1;
   }

   private boolean willLandOnBlock(BlockPos var1) {
      AxisAlignedBB var2 = mc.thePlayer.getEntityBoundingBox();
      if (var2 == null) {
         return false;
      } else {
         double var3 = var1.getY() + 1.0;
         if (var2.minY < var3 - 0.01) {
            return false;
         } else {
            double var5 = mc.thePlayer.posY;
            double var7 = mc.thePlayer.motionY;

            for (int var9 = 1; var9 <= 7; var9++) {
               var5 += var7;
               AxisAlignedBB var10 = var2.offset(mc.thePlayer.motionX * var9, var5 - mc.thePlayer.posY, mc.thePlayer.motionZ * var9);
               if (var10.minY <= var3 + 0.14 && this.overlapsBlockHorizontally(var10, var1)) {
                  return true;
               }

               var7 = (var7 - 0.08) * 0.98;
            }

            return false;
         }
      }
   }

   private boolean overlapsBlockHorizontally(AxisAlignedBB var1, BlockPos var2) {
      return var1.maxX > var2.getX() + 0.02 && var1.minX < var2.getX() + 0.98 && var1.maxZ > var2.getZ() + 0.02 && var1.minZ < var2.getZ() + 0.98;
   }

   private boolean placeBlockWithIceSwap(Scaffold$3 var1) {
      int var2 = this.findIceSlot();
      if (var2 == -1) {
         return this.placeBlock(var1);
      } else {
         int var3 = mc.thePlayer.inventory.currentItem;
         this.switchToSlot(var2);
         boolean var4 = this.placeBlock(var1);
         if (var3 != mc.thePlayer.inventory.currentItem) {
            this.pendingSlotRestore = var3;
            this.slotRestoreDelay = 0;
         }

         return var4;
      }
   }

   private int findIceSlot() {
      for (int var1 = 0; var1 < 9; var1++) {
         ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
         if (this.isPackedIce(var2)) {
            return var1;
         }
      }

      return -1;
   }

   private boolean isPackedIce(ItemStack var1) {
      return var1 != null && var1.stackSize > 0 && var1.getItem() instanceof ItemBlock && ((ItemBlock)var1.getItem()).getBlock() == Blocks.packed_ice;
   }

   private void switchToSlot(int var1) {
      if (var1 >= 0 && var1 <= 8 && var1 != mc.thePlayer.inventory.currentItem) {
         mc.thePlayer.inventory.currentItem = var1;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private boolean isKeepYJumpReady() {
      return this.keepYJumpTicks > 0
         && (int)this.keepY.getInput() == 1
         && (int)this.rotation.getInput() != 0
         && mc.thePlayer.onGround
         && this.TltejZ()
         && this.hasHeadroom()
         && (!this.keepYOnUse.isToggled() || this.ZOTYw())
         && !ClientUtils.isJumpKeyDown();
   }

   private boolean isTowerJumpReady() {
      return mc.thePlayer.onGround && this.TltejZ() && this.hasHeadroom()
         ? (int)this.tower.getInput() == 1 && ClientUtils.isJumpKeyDown() && (!this.keepYOnUse.isToggled() || this.ZOTYw())
         : false;
   }

   private boolean canTowerSprint() {
      return (int)this.tower.getInput() == 1
         && ClientUtils.vAxywR()
         && ClientUtils.isJumpKeyDown()
         && (!this.keepYOnUse.isToggled() || this.ZOTYw())
         && this.isHoldingBlock()
         && !mc.thePlayer.isCollidedHorizontally
         && !mc.thePlayer.isPotionActive(Potion.jump)
         && !mc.thePlayer.isInWater()
         && !mc.thePlayer.isInLava();
   }

   private boolean XDGNs() {
      return this.keepYJumpTicks > 0
         && (int)this.keepY.getInput() == 1
         && (int)this.rotation.getInput() != 0
         && this.TltejZ()
         && (!this.keepYOnUse.isToggled() || this.ZOTYw())
         && !ClientUtils.isJumpKeyDown();
   }

   private float[] computeRotationToPoint(Vec3 var1, float var2, float var3) {
      return this.computeRotationToTarget(mc.thePlayer.getPositionEyes(1.0F), var1, var2, var3);
   }

   private float[] computeRotationToTarget(Vec3 var1, Vec3 var2, float var3, float var4) {
      double var5 = var2.xCoord - var1.xCoord;
      double var7 = var2.yCoord - var1.yCoord;
      double var9 = var2.zCoord - var1.zCoord;
      double var11 = MathHelper.sqrt_double(var5 * var5 + var9 * var9);
      float var13 = (float)(Math.atan2(var9, var5) * (float) (180.0 / Math.PI)) - 90.0F;
      float var14 = (float)(-(Math.atan2(var7, var11) * (float) (180.0 / Math.PI)));
      return RotationUtils.NSsr(var3 + MathHelper.wrapAngleTo180_float(var13 - var3), var4 + MathHelper.wrapAngleTo180_float(var14 - var4), var3, var4);
   }

   private float getMovementYaw() {
      float var1 = mc.thePlayer.rotationYaw;
      float var2 = this.getForwardInput();
      float var3 = this.getStrafeInput();
      if (var2 < 0.0F) {
         var1 += 180.0F;
      }

      float var4 = 1.0F;
      if (var2 < 0.0F) {
         var4 = -0.5F;
      } else if (var2 > 0.0F) {
         var4 = 0.5F;
      }

      if (var3 > 0.0F) {
         var1 -= 90.0F * var4;
      } else if (var3 < 0.0F) {
         var1 += 90.0F * var4;
      }

      return MathHelper.wrapAngleTo180_float(var1);
   }

   private boolean isDiagonalYaw(float var1) {
      float var2 = Math.abs(var1 % 90.0F);
      return var2 > 20.0F && var2 < 70.0F;
   }

   private boolean ApCjv() {
      return this.getForwardInput() > 0.0F && this.getStrafeInput() != 0.0F;
   }

   private boolean gln8() {
      return this.getForwardInput() > 0.0F && this.getStrafeInput() == 0.0F;
   }

   private boolean isOnDiagonalYaw() {
      float var1 = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
      float var2 = Math.round((var1 - 45.0F) / 90.0F) * 90.0F + 45.0F;
      return Math.abs(MathHelper.wrapAngleTo180_float(var1 - var2)) <= 15.0F;
   }

   private boolean QSDa() {
      return mc.thePlayer.onGround && this.isHoldingBlock() && !ClientUtils.isJumpKeyDown() && !this.isDownwardsKeyHeld();
   }

   private float computeTellyYaw(float var1, float var2, boolean var3, boolean var4) {
      if (var3) {
         return this.applyDiagonalYawOffset(var1);
      } else {
         return var4 ? this.YWcosw(var1) : var1 + var2 * (float)ClientUtils.randomDouble(0.98, 0.99);
      }
   }

   private float applyDiagonalYawOffset(float var1) {
      float var2 = this.getStrafeInput() > 0.0F ? -10.0F : 10.0F;
      return this.wrapAngleNear(mc.thePlayer.rotationYaw + var2, var1);
   }

   private float YWcosw(float var1) {
      return this.wrapAngleNear(mc.thePlayer.rotationYaw + 45.0F - 10.0F, var1);
   }

   private float getForwardInput() {
      float var1 = 0.0F;
      if (ClientUtils.xusXfhC(mc.gameSettings.keyBindForward)) {
         var1++;
      }

      if (ClientUtils.xusXfhC(mc.gameSettings.keyBindBack)) {
         var1--;
      }

      return var1;
   }

   private boolean TltejZ() {
      boolean var1 = ClientUtils.xusXfhC(mc.gameSettings.keyBindForward);
      boolean var2 = ClientUtils.xusXfhC(mc.gameSettings.keyBindBack);
      boolean var3 = ClientUtils.xusXfhC(mc.gameSettings.keyBindLeft);
      boolean var4 = ClientUtils.xusXfhC(mc.gameSettings.keyBindRight);
      return var1 != var2 || var3 != var4;
   }

   private boolean ZOTYw() {
      if (ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem)) {
         this.useKeyPressed = true;
      }

      return this.useKeyPressed;
   }

   private float getStrafeInput() {
      float var1 = 0.0F;
      if (ClientUtils.xusXfhC(mc.gameSettings.keyBindLeft)) {
         var1++;
      }

      if (ClientUtils.xusXfhC(mc.gameSettings.keyBindRight)) {
         var1--;
      }

      return var1;
   }

   private float wrapAngleNear(float var1, float var2) {
      return var2 + MathHelper.wrapAngleTo180_float(var1 - var2);
   }

   private float JGv8(boolean var1) {
      if (var1) {
         return this.rotationDelayTicks >= 2 ? (float)ClientUtils.randomDouble(95.0, 100.0) : (float)ClientUtils.randomDouble(35.0, 40.0);
      } else {
         return this.rotationDelayTicks >= 2 ? (float)ClientUtils.randomDouble(90.0, 95.0) : (float)ClientUtils.randomDouble(30.0, 35.0);
      }
   }

   private float AXCHI(float var1, float var2) {
      return MathHelper.clamp_float(var1, -var2, var2);
   }

   private Vec3 getNormalizedEyeDirection(Vec3 var1) {
      Vec3 var2 = mc.thePlayer.getPositionEyes(1.0F);
      Vec3 var3 = new Vec3(var1.xCoord - var2.xCoord, var1.yCoord - var2.yCoord, var1.zCoord - var2.zCoord);
      double var4 = Math.sqrt(var3.xCoord * var3.xCoord + var3.yCoord * var3.yCoord + var3.zCoord * var3.zCoord);
      return var4 < 1.0E-6 ? null : new Vec3(var3.xCoord / var4, var3.yCoord / var4, var3.zCoord / var4);
   }

   private Scaffold$1 traceHitFace(BlockPos var1, Vec3 var2, Vec3 var3) {
      if (var2 != null && var3 != null) {
         double var4 = Double.MAX_VALUE;
         EnumFacing var6 = null;
         double var7 = var2.xCoord - var1.getX();
         double var9 = var2.yCoord - var1.getY();
         double var11 = var2.zCoord - var1.getZ();
         if (var3.xCoord > 1.0E-6) {
            double var13 = (1.0 - var7) / var3.xCoord;
            if (var13 > 1.0E-6 && var13 < var4) {
               var4 = var13;
               var6 = EnumFacing.EAST;
            }
         } else if (var3.xCoord < -1.0E-6) {
            double var15 = (0.0 - var7) / var3.xCoord;
            if (var15 > 1.0E-6 && var15 < var4) {
               var4 = var15;
               var6 = EnumFacing.WEST;
            }
         }

         if (var3.yCoord > 1.0E-6) {
            double var16 = (1.0 - var9) / var3.yCoord;
            if (var16 > 1.0E-6 && var16 < var4) {
               var4 = var16;
               var6 = EnumFacing.UP;
            }
         } else if (var3.yCoord < -1.0E-6) {
            double var17 = (0.0 - var9) / var3.yCoord;
            if (var17 > 1.0E-6 && var17 < var4) {
               var4 = var17;
               var6 = EnumFacing.DOWN;
            }
         }

         if (var3.zCoord > 1.0E-6) {
            double var18 = (1.0 - var11) / var3.zCoord;
            if (var18 > 1.0E-6 && var18 < var4) {
               var4 = var18;
               var6 = EnumFacing.SOUTH;
            }
         } else if (var3.zCoord < -1.0E-6) {
            double var19 = (0.0 - var11) / var3.zCoord;
            if (var19 > 1.0E-6 && var19 < var4) {
               var4 = var19;
               var6 = EnumFacing.NORTH;
            }
         }

         return var6 == null ? null : new Scaffold$1(var6, var2.addVector(var3.xCoord * var4, var3.yCoord * var4, var3.zCoord * var4));
      } else {
         return null;
      }
   }

   private void setPlaceStatus(String var1) {
      this.placeStatus = var1;
   }

   private boolean isBridgeAssistEnabled() {
      Module var1 = Jade.getModuleManager().getModule(BridgeAssist.class);
      return var1 instanceof BridgeAssist && ((BridgeAssist)var1).shouldRenderBlocksHud();
   }

   private float getPlaceFlashAlpha(long var1) {
      if (this.EHbJ4 > 0L && this.lastPlaceTime > 0L && var1 >= this.EHbJ4) {
         long var3 = var1 - this.EHbJ4;
         long var5 = var1 - this.lastPlaceTime;
         if (var3 < 120L) {
            return (float)var3 / 120.0F;
         } else if (var5 <= 1000L) {
            return 1.0F;
         } else if (var5 >= 1300L) {
            this.EHbJ4 = 0L;
            this.lastPlaceTime = 0L;
            return 0.0F;
         } else {
            return 1.0F - (float)(var5 - 1000L) / 300.0F;
         }
      } else {
         return 0.0F;
      }
   }

   public boolean IjeudZc() {
      return this.isEnabled() && this.blocksHud.isToggled() && ClientUtils.isInWorld() && this.getPlaceFlashAlpha(System.currentTimeMillis()) > 0.0F;
   }

   private String getHudFontName() {
      return this.font == null ? BridgeAssist.SCqot() : this.font.getResolvedFontName();
   }

   private String formatTarget(Scaffold$3 var1) {
      return var1 == null
         ? "null"
         : Scaffold$3.getTargetPos(var1).getX() + "," + Scaffold$3.getTargetPos(var1).getY() + "," + Scaffold$3.getTargetPos(var1).getZ() + "/" + Scaffold$3.getFacing(var1);
   }

   private String formatRayResult(MovingObjectPosition var1) {
      if (var1 == null) {
         return "ray=null";
      } else {
         return var1.typeOfHit != MovingObjectType.BLOCK
            ? "ray=" + var1.typeOfHit
            : "ray=" + var1.getBlockPos().getX() + "," + var1.getBlockPos().getY() + "," + var1.getBlockPos().getZ() + "/" + var1.sideHit;
      }
   }

   private String lRalKqm(float var1) {
      return String.valueOf(Math.round(var1 * 10.0F) / 10.0F);
   }

   private boolean hasHeadroom() {
      AxisAlignedBB var1 = mc.thePlayer.getEntityBoundingBox().offset(0.0, 1.0, 0.0);
      return mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, var1).isEmpty();
   }

   private boolean isActive() {
      return this.isEnabled() && ClientUtils.isInWorld() && mc.playerController != null && mc.theWorld != null;
   }

   private boolean isDownwardsKeyHeld() {
      return this.downwardsOnShift.isToggled() && ClientUtils.isInWorld() && mc.currentScreen == null && this.isSneakKeyDown();
   }

   private boolean Jyei() {
      return this.isDownwardsKeyHeld() && !this.JTw && !this.towerJumping && mc.thePlayer.onGround && this.isCenteredOverBlockBelow();
   }

   private boolean shouldResetDescentState() {
      return this.isDownwardsKeyHeld() && mc.thePlayer.onGround && !this.JTw && !this.descendBlockPlaced && !this.towerJumping && !this.isCenteredOverBlockBelow();
   }

   private boolean isDescendingActive() {
      return this.isDownwardsKeyHeld() && this.JTw && (!mc.thePlayer.onGround || this.isCenteredOverBlockBelow());
   }

   private boolean shouldReuseDescentY() {
      return this.isDescendingActive() && this.downPlacedY != Integer.MIN_VALUE && !mc.thePlayer.onGround;
   }

   private boolean isGroundedAfterDownPlace() {
      return this.descendBlockPlaced && this.JTw && !this.towerJumping && mc.thePlayer.onGround;
   }

   private boolean isStandingOnDownPlacedBlock() {
      return this.descendBlockPlaced && this.JTw && !this.towerJumping && mc.thePlayer.onGround;
   }

   private boolean isStandingOnPlacedBlock() {
      return this.descendBlockPlaced && this.JTw && !this.towerJumping && mc.thePlayer.onGround && this.hasHeadroom();
   }

   private void startTowerJump() {
      if (!this.towerJumping) {
         this.towerJumping = true;
         this.descendBlockPlaced = false;
         this.towerJumpTicks = this.randomTowerJumpTicks();
      }
   }

   private boolean duEirm() {
      return this.isDownwardsKeyHeld() && this.JTw && this.towerJumping && !mc.thePlayer.onGround && mc.thePlayer.motionY > 0.0;
   }

   private boolean isDescendingAirborne() {
      return this.isDownwardsKeyHeld() && this.JTw && this.towerJumping && !mc.thePlayer.onGround;
   }

   private void szMk() {
      if (this.towerJumping && !mc.thePlayer.onGround) {
         this.FpT1();
      }
   }

   private void FpT1() {
      this.towerFallInProgress = true;
      this.keepYTellyActive = true;
      this.JTw = false;
      this.descendBlockPlaced = false;
      this.downPlacedY = Integer.MIN_VALUE;
      this.watchedDownPos = null;
      this.SOkt = null;
   }

   private void cbnePgq() {
      if (this.towerFallInProgress && mc.thePlayer.onGround) {
         this.towerFallInProgress = false;
         this.keepYTellyActive = false;
      }
   }

   private boolean DpwCbsM() {
      return this.towerFallInProgress && !mc.thePlayer.onGround;
   }

   private boolean isWaitingForFall(Scaffold$3 var1) {
      return var1 != null && this.isDownwardsKeyHeld() && this.JTw && this.towerJumping && !this.isDownwardTarget(var1);
   }

   private boolean wxxowgT(Scaffold$3 var1) {
      return this.isWaitingForFall(var1) && !mc.thePlayer.onGround;
   }

   private boolean dZpt38(Scaffold$3 var1) {
      if (this.isDownwardsKeyHeld() && !this.JTw && !this.towerJumping && !this.isDownwardTarget(var1)) {
         BlockPos var2 = Scaffold$3.getTargetPos(var1).offset(Scaffold$3.getFacing(var1));
         return var2.getY() == MathHelper.floor_double(mc.thePlayer.posY) - 1;
      } else {
         return false;
      }
   }

   private boolean isCenteredOverBlockBelow() {
      BlockPos var1 = this.SOkt != null ? this.SOkt : this.NuVvku();
      return var1 != null && this.isPlayerCenteredOn(var1);
   }

   private BlockPos NuVvku() {
      return !ClientUtils.isInWorld()
         ? null
         : new BlockPos(
            MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posY) - 1, MathHelper.floor_double(mc.thePlayer.posZ)
         );
   }

   private boolean isPlayerCenteredOn(BlockPos var1) {
      return Math.abs(mc.thePlayer.posX - (var1.getX() + 0.5)) <= 0.2 && Math.abs(mc.thePlayer.posZ - (var1.getZ() + 0.5)) <= 0.2;
   }

   private void releaseSneakKey() {
      if (!this.sneakKeyReleased && this.isSneakKeyDown()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
         this.sneakKeyReleased = true;
      }
   }

   private void restoreSneakKey() {
      if (this.sneakKeyReleased) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), this.isSneakKeyDown());
      }

      this.sneakKeyReleased = false;
   }

   private boolean isSneakKeyDown() {
      int var1 = mc.gameSettings.keyBindSneak.getKeyCode();
      return var1 >= 0 && Keyboard.isKeyDown(var1);
   }

   private boolean isMultiFaceTarget(Scaffold$3 var1) {
      return var1 != null && Scaffold$3.isMultiFace(var1);
   }

   private boolean isDownwardTarget(Scaffold$3 var1) {
      return var1 != null && Scaffold$3.isMultiFace(var1) && Scaffold$3.getFacing(var1) == EnumFacing.DOWN;
   }

   public boolean isSafeWalkRequested() {
      return false;
   }

   public boolean isTowerActive() {
      return this.isEnabled() && ClientUtils.isInWorld() && (this.towerTellyActive || this.canTowerSprint());
   }

   public boolean isKeepYActive() {
      return false;
   }

   public boolean isScaffoldEnabled() {
      return this.isEnabled();
   }

   public int getRestoreSlot() {
      return this.aisgr;
   }

   private float[] DaCz(float var1, float var2, Vec3 var3, Vec3 var4) {
      return this.computeRotationToTarget(var3, var4, var1, var2);
   }

   private static double distanceSqToCenter(BlockPos var0, BlockPos var1) {
      return var1.distanceSqToCenter(var0.getX() + 0.5, var0.getY() + 0.5, var0.getZ() + 0.5);
   }

   private Scaffold$3 createPlacementCandidate(float var1, float var2, BlockPos var3, EnumFacing var4) {
      if (!BlockUtils.canPlaceItemOnSide(mc.thePlayer.getHeldItem(), var3, var4)) {
         return null;
      } else {
         Scaffold$3 var5 = new Scaffold$3(var3, var4);
         Scaffold$3.setPlacement(var5, !this.Qmp4() && !this.DYjg() ? this.NKFba(var5, var1, var2, true) : this.findReportedPlacement(var5, var1, var2));
         return Scaffold$3.getPlacement(var5) == null ? null : var5;
      }
   }

   static {
      double[] var10000 = new double[16];
      var10000[0] = 0.03125;
      var10000[1] = 0.09375;
      var10000[2] = 0.15625;
      var10000[3] = 0.21875;
      var10000[4] = 0.28125;
      var10000[5] = 0.34375;
      var10000[6] = 0.40625;
      var10000[7] = 0.46875;
      var10000[8] = 0.53125;
      var10000[9] = 0.59375;
      var10000[10] = 0.65625;
      var10000[11] = 0.71875;
      var10000[12] = 0.78125;
      var10000[13] = 0.84375;
      var10000[14] = 0.90625;
      var10000[15] = 0.96875;
      FACE_SAMPLE_OFFSETS = var10000;
   }
}
