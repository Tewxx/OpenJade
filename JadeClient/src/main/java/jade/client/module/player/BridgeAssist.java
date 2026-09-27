// Jade recovery: module: Bridge Assist (player); original class: jade.deps.eLz.d5oesD
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.MoveInputEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.bridgeassist.SneakMode;
import jade.client.module.player.bridgeassist.YSnTsbaqd;
import jade.client.module.player.bridgeassist.PlacementHighlightTimer;
import jade.client.module.player.bridgeassist.MovementPrediction;
import jade.client.module.player.bridgeassist.TickDelayGate;
import jade.client.module.player.bridgeassist.EdgeDistanceCalculator;
import jade.client.module.player.bridgeassist.BridgeAimValidator;
import jade.client.module.player.bridgeassist.EdgePlacementSolver$2;
import jade.client.module.player.bridgeassist.EdgePlacementSolver;
import jade.client.module.render.Arraylist;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo
public class BridgeAssist extends Module implements ExternalRenderableModule {
   private static final int rYji = 0;
   private static final int ON_JUMP_SNEAK = 1;
   private static final int ON_JUMP_AIM = 2;
   private static final String[] UGy533 = new String[]{"Nothing", "Sneak", "Aim"};
   private static final int DOUBLESHIFT_NONE = 0;
   private static final int DOUBLESHIFT_AIM = 1;
   private static final int RiyV = 2;
   private static final String[] DOUBLESHIFT_MODE_LABELS = new String[]{"None", "Aim", "Prevent"};
   private final SliderSetting mode;
   private final SliderSetting onDoubleshift;
   private final GroupSetting sneakingGroup;
   private final SliderSetting edgeOffset;
   private final SliderSetting unsneakDelay;
   private final SliderSetting onJump;
   private final SliderSetting sneakOnJump;
   private final SliderSetting sneakEvery;
   private final BooleanSetting sneakKeyPressed;
   private final BooleanSetting holdingBlocks;
   private final BooleanSetting holdingRightClick;
   private final BooleanSetting lookingDown;
   private final BooleanSetting notMovingForward;
   private final BooleanSetting blocksHud;
   private final FontSetting font;
   private final SliderSetting scale;
   private float GEud = Float.NaN;
   private float qopvs = Float.NaN;
   private float hudPixelX;
   private float hudPixelY;
   private boolean sneakInjected;
   private boolean blockJustPlaced;
   private boolean sneakRestorePending;
   private final TickDelayGate NeR = new TickDelayGate();
   private final PlacementHighlightTimer TSe = new PlacementHighlightTimer();
   private final YSnTsbaqd sneakPulse = new YSnTsbaqd();
   private boolean moduleHandlesRightClick = true;
   private boolean Jtx;
   private long QxVq = System.currentTimeMillis();

   public BridgeAssist() {
      super("Bridge Assist", Category.player);
      this.registerSetting(this.mode = new SliderSetting("Mode", SneakMode.SNEAK_INPUT.ordinal(), SneakMode.labels()));
      this.sneakingGroup = new GroupSetting("Sneaking");
      this.registerSetting(this.sneakingGroup);
      this.registerSetting(
         this.edgeOffset = new SliderSetting(
            this.sneakingGroup, "Edge offset", " blocks", 0.0, 0.0, 0.3, 0.01
         )
      );
      this.registerSetting(
         this.unsneakDelay = new SliderSetting(
            this.sneakingGroup, "Unsneak delay", "ms", 50.0, 50.0, 300.0, 5.0
         )
      );
      this.registerSetting(
         this.onDoubleshift = new SliderSetting(
            this.sneakingGroup, "On Doubleshift", 0, DOUBLESHIFT_MODE_LABELS
         )
      );
      this.registerSetting(this.onJump = new SliderSetting(this.sneakingGroup, "On Jump", 1, UGy533));
      this.registerSetting(
         this.sneakOnJump = new SliderSetting(
            this.sneakingGroup,
            "Sneak on jump",
            "ms",
            0.0,
            0.0,
            500.0,
            5.0
         )
      );
      this.registerSetting(
         this.sneakEvery = new SliderSetting(
            this.sneakingGroup, "Sneak Every", " blocks", 1.0, 1.0, 8.0, 1.0
         )
      );
      GroupSetting var1 = new GroupSetting("Conditions");
      this.registerSetting(var1);
      this.registerSetting(
         this.sneakKeyPressed = new BooleanSetting(
            var1,
            "Sneak key pressed",
            false
         )
      );
      this.registerSetting(this.holdingBlocks = new BooleanSetting(var1, "Holding blocks", false));
      this.registerSetting(
         this.holdingRightClick = new BooleanSetting(
            var1,
            "Holding right click",
            false
         )
      );
      this.registerSetting(
         this.lookingDown = new BooleanSetting(
            var1, "Looking down", false
         )
      );
      this.registerSetting(
         this.notMovingForward = new BooleanSetting(
            var1,
            "Not moving forward",
            false
         )
      );
      GroupSetting var2 = new GroupSetting("Blocks HUD");
      this.registerSetting(var2);
      this.registerSetting(
         this.blocksHud = new BooleanSetting(
            var2,
            "Blocks HUD",
            false,
            new String[]{"Blocks HUD.Enabled"}
         )
      );
      this.registerSetting(
         this.font = new FontSetting(var2, "Font", "Modern")
      );
      this.registerSetting(this.scale = new SliderSetting(var2, "Scale", 1.0, 0.5, 2.0, 0.05));
      this.scale.visible = false;
      this.font.visible = this.blocksHud.isToggled();
      this.initialized = true;
   }

   @Override
   public String getInfo() {
      double var1 = this.edgeOffset.getInput();
      return var1 == Math.rint(var1) ? Integer.toString((int)var1) : Double.toString(ClientUtils.WXYd(var1, 2));
   }

   @Override
   public void onDisable() {
      if (this.isSneakInputMode()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), this.isSneakKeyDown());
      }

      this.sneakInjected = false;
      this.sneakPulse.DQtgL();
      this.TSe.DkfRq();
      this.moduleHandlesRightClick = true;
      this.Jtx = false;
      this.QxVq = System.currentTimeMillis();
      this.clearRandomSneakDelay();
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.isSneakMode();
      this.sneakingGroup.setVisible(var1, this);
      this.edgeOffset.setVisible(var1, this);
      this.unsneakDelay.setVisible(var1, this);
      this.onDoubleshift.setVisible(var1, this);
      this.onJump.setVisible(var1, this);
      this.sneakOnJump.setVisible(var1 && this.getOnJumpMode() == 1, this);
      this.sneakEvery.setVisible(this.LnsW3(), this);
      this.sneakKeyPressed.setVisible(var1, this);
      this.font.setVisible(this.blocksHud.isToggled(), this);
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent var1) {
      if (this.LnsW3()) {
         if (this.sneakPulse.isSneakPulseActive()) {
            var1.setSneaking(true);
            if (!this.isFarFromEdge()) {
               this.sneakPulse.cancelSneakPulse();
            }
         }
      } else if (this.isSneakMode()) {
         if (ClientUtils.isInWorld() && mc.currentScreen == null && !mc.thePlayer.capabilities.isFlying) {
            boolean var2 = this.isSneakKeyDown();
            boolean var3 = this.sneakKeyPressed.isToggled();
            if (var2 && !var3) {
               this.clearRandomSneakDelay();
            } else if (var3 && !var2) {
               this.restoreSneakInput(var1);
            } else if (this.notMovingForward.isToggled() && var1.getMoveForward() > 0.0F) {
               this.WRg1(var1);
            } else if (this.lookingDown.isToggled() && mc.thePlayer.rotationPitch < 70.0F) {
               this.WRg1(var1);
            } else {
               if (this.holdingBlocks.isToggled()) {
                  ItemStack var4 = mc.thePlayer.getHeldItem();
                  if (var4 == null || !(var4.getItem() instanceof ItemBlock)) {
                     this.WRg1(var1);
                     return;
                  }
               }

               if (this.holdingRightClick.isToggled() && !ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem)) {
                  this.WRg1(var1);
               } else if (var3 && var1.getMoveForward() == 0.0F && var1.AfWugH() == 0.0F) {
                  this.restoreSneakInput(var1);
               } else if (this.shouldSneakOnJump(var1)) {
                  this.NeR.startJumpHold(mc.thePlayer.ticksExisted, this.sneakOnJump.getInput(), Math::random);
                  this.startSneak(var1, true);
               } else {
                  double var6 = this.computeEdgeDistance(MovementPrediction.getPredictedBoundingBox(mc.thePlayer));
                  if (!Double.isNaN(var6)) {
                     if (var6 > this.edgeOffset.getInput()) {
                        this.startSneak(var1, true);
                     } else if (this.sneakInjected) {
                        this.tryReleaseSneak(var1, true);
                     } else if (var3) {
                        this.restoreSneakInput(var1);
                     }
                  } else {
                     if (var1.isJumping() && (this.getOnJumpMode() != 1 || this.sneakOnJump.getInput() <= 0.0 || var1.getMoveForward() == 0.0F && var1.AfWugH() == 0.0F)) {
                        if (this.sneakInjected) {
                           this.tryReleaseSneak(var1, true);
                        }
                     } else if (mc.thePlayer.onGround) {
                        this.startSneak(var1, true);
                     } else if (this.sneakInjected) {
                        this.tryReleaseSneak(var1, true);
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onPacketSend(PacketSendEvent var1) {
      if (var1.ys98() instanceof C08PacketPlayerBlockPlacement) {
         C08PacketPlayerBlockPlacement var2 = (C08PacketPlayerBlockPlacement)var1.ys98();
         if (var2.getPlacedBlockDirection() != 255) {
            long var3 = System.currentTimeMillis();
            this.TSe.onPlacement(var3);
            this.handleBlockPlacePacket(var2);
         }

         if (var2.getPlacedBlockDirection() != 255 && this.sneakInjected && this.sneakKeyPressed.isToggled()) {
            this.blockJustPlaced = true;
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         this.mAdl();
         if (this.blocksHud.isToggled() && ClientUtils.isInWorld()) {
            long var2 = System.currentTimeMillis();
            float var4 = this.getPlacementHighlightAlpha(var2);
            if (!(var4 <= 0.0F)) {
               String var5 = this.getHudFontName();
               this.oaomJ(new ScaledResolution(mc));
               int var6 = countHotbarBlocks();
               float var7 = (float)this.scale.getInput();
               if (this.VIuQ()) {
                  renderBlocksHudToBuffer(var6, var5, var4, this.hudPixelX, this.hudPixelY, var7);
               } else {
                  drawBlocksHudWithFont(var6, var5, var4, this.hudPixelX, this.hudPixelY, var7);
               }
            }
         }
      }
   }

   @Subscribe
   public void onRightClick(RightClickEvent var1) {
      if (this.isSneakMode()) {
         this.mAdl();
         if (this.getOnDoubleShiftMode() == 2 && !this.moduleHandlesRightClick) {
            var1.setCancelled(true);
         }
      }
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      boolean var2 = this.isSneakMode() && this.getOnJumpMode() == 2;
      boolean var3 = this.shouldAimOnDoubleShift();
      if (this.LnsW3() || var2 || var3) {
         if (ClientUtils.isInWorld() && mc.currentScreen == null && !mc.thePlayer.capabilities.isFlying) {
            if (!var2 || ClientUtils.isJumpKeyDown() || var3) {
               if (Jade.getModuleManager().getModule(BedNuker.class) == null || !Jade.getModuleManager().getModule(BedNuker.class).shouldOverridePointedObject()) {
                  if (Jade.getModuleManager().getModule(BridgeNuker.class) == null || !Jade.getModuleManager().getModule(BridgeNuker.class).shouldOverridePointedObject()) {
                     ItemStack var4 = mc.thePlayer.getHeldItem();
                     if (var4 != null && var4.getItem() instanceof ItemBlock) {
                        if (!this.holdingRightClick.isToggled() || ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem)) {
                           if (!this.lookingDown.isToggled() || !(mc.thePlayer.rotationPitch < 70.0F)) {
                              if (!this.notMovingForward.isToggled() || !(mc.thePlayer.movementInput.moveForward > 0.0F)) {
                                 float var5 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
                                 double var6 = mc.playerController.getBlockReachDistance();
                                 EdgePlacementSolver$2 var8 = this.findClutchPlacement(var5, var6);
                                 if (var8 != null) {
                                    float var9 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
                                    float[] var10 = RotationUtils.smoothAnglesRandomized(var9, var5, var8.sbimzW, var8.SEAo, 15, 20.0F);
                                    var1.setRotation(var10[0], var10[1], 40);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void startSneak(MoveInputEvent var1, boolean var2) {
      if (this.isSneakInputMode()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
      }

      var1.setSneaking(true);
      this.sneakInjected = true;
      if (var2) {
         this.NeR.clearReleaseDelay();
      }

      this.SCc0(var1);
   }

   private void tryReleaseSneak(MoveInputEvent var1, boolean var2) {
      if (this.NeR.shouldKeepSneaking(mc.thePlayer.ticksExisted, this.unsneakDelay.getInput(), Math::random)) {
         this.startSneak(var1, false);
      } else {
         this.stopSneak(var1, var2);
      }
   }

   private void stopSneak(MoveInputEvent var1, boolean var2) {
      if (this.isSneakInputMode()) {
         boolean var3 = this.isPhysicalSneakPressed();
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), var3);
         var1.setSneaking(var3);
      } else if (!this.sneakKeyPressed.isToggled()) {
         var1.setSneaking(false);
      } else if (!this.sneakInjected || !this.isSneakKeyDown() || !this.blockJustPlaced && mc.thePlayer.onGround) {
         if (this.sneakRestorePending) {
            var1.setSneaking(false);
         }
      } else {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
         var1.setSneaking(false);
         this.sneakRestorePending = true;
      }

      this.sneakInjected = false;
      this.blockJustPlaced = false;
      if (var2) {
         this.clearRandomSneakDelay();
      }
   }

   private void SCc0(MoveInputEvent var1) {
      if (this.sneakRestorePending && this.isSneakKeyDown()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
         var1.setSneaking(true);
      }

      this.sneakRestorePending = false;
   }

   private void restoreSneakInput(MoveInputEvent var1) {
      this.sneakInjected = false;
      this.clearRandomSneakDelay();
      if (this.isSneakInputMode()) {
         boolean var2 = this.isPhysicalSneakPressed();
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), var2);
         var1.setSneaking(var2);
      } else {
         if (this.sneakKeyPressed.isToggled()) {
            this.SCc0(var1);
         }
      }
   }

   private void WRg1(MoveInputEvent var1) {
      this.sneakInjected = false;
      this.clearRandomSneakDelay();
      if (this.isSneakInputMode()) {
         boolean var2 = this.isSneakKeyDown();
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), var2);
         var1.setSneaking(var2);
      } else {
         if (this.sneakKeyPressed.isToggled()) {
            this.SCc0(var1);
         }
      }
   }

   private void clearRandomSneakDelay() {
      this.NeR.clearAll();
   }

   private boolean isSneakKeyDown() {
      return ClientUtils.xusXfhC(mc.gameSettings.keyBindSneak);
   }

   private boolean isPhysicalSneakPressed() {
      return this.isSneakKeyDown() && !this.sneakKeyPressed.isToggled();
   }

   private int getOnJumpMode() {
      return (int)Math.max(0.0, Math.min(2.0, this.onJump.getInput()));
   }

   private int getOnDoubleShiftMode() {
      return (int)Math.max(0.0, Math.min(2.0, this.onDoubleshift.getInput()));
   }

   private boolean shouldAimOnDoubleShift() {
      if (this.isSneakMode()
         && this.getOnDoubleShiftMode() == 1
         && ClientUtils.isInWorld()
         && mc.currentScreen == null
         && !mc.thePlayer.capabilities.isFlying
         && BridgeAimValidator.isDiagonalEdgePose(mc.thePlayer)) {
         ItemStack var1 = mc.thePlayer.getHeldItem();
         if (var1 == null || !(var1.getItem() instanceof ItemBlock)) {
            return false;
         } else if (!this.isBeyondEdgeOffset()) {
            return false;
         } else {
            MovingObjectPosition var2 = RotationUtils.traceBlockHit(
               mc.playerController.getBlockReachDistance(), mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch
            );
            return BridgeAimValidator.isHitPointSafe(var2);
         }
      } else {
         return false;
      }
   }

   private boolean isBeyondEdgeOffset() {
      double var1 = this.computeEdgeDistance(MovementPrediction.getPredictedBoundingBox(mc.thePlayer));
      return Double.isNaN(var1) || var1 > this.edgeOffset.getInput();
   }

   private void mAdl() {
      long var1 = System.currentTimeMillis();
      if (this.isSneakMode()
         && this.getOnDoubleShiftMode() == 2
         && ClientUtils.isInWorld()
         && mc.currentScreen == null
         && !mc.thePlayer.capabilities.isFlying
         && BridgeAimValidator.isDiagonalEdgePose(mc.thePlayer)) {
         ItemStack var3 = mc.thePlayer.getHeldItem();
         if (var3 != null && var3.getItem() instanceof ItemBlock) {
            MovingObjectPosition var4 = mc.objectMouseOver;
            if (var4 != null && var4.typeOfHit == MovingObjectType.BLOCK) {
               if (!BridgeAimValidator.isHitPointSafe(var4)) {
                  this.moduleHandlesRightClick = true;
                  this.Jtx = true;
                  this.QxVq = var1;
               } else if (!this.Jtx || var1 - this.QxVq >= 300L) {
                  this.moduleHandlesRightClick = false;
               }
            }
         } else {
            this.moduleHandlesRightClick = true;
            this.Jtx = false;
            this.QxVq = var1;
         }
      } else {
         this.moduleHandlesRightClick = true;
         this.Jtx = false;
         this.QxVq = var1;
      }
   }

   private SneakMode getSneakMode() {
      return SneakMode.fromSetting(this.mode.getInput());
   }

   private boolean isSneakMode() {
      return this.getSneakMode().isSneakMode();
   }

   private boolean isSneakInputMode() {
      return this.getSneakMode().isInputMode();
   }

   private boolean LnsW3() {
      return this.getSneakMode() == SneakMode.PRE_PLACE;
   }

   private boolean isFarFromEdge() {
      if (!ClientUtils.isInWorld()) {
         return false;
      } else {
         double var1 = this.computeEdgeDistance(MovementPrediction.getPredictedBoundingBox(mc.thePlayer));
         return Double.isNaN(var1) || var1 > 0.0;
      }
   }

   private void handleBlockPlacePacket(C08PacketPlayerBlockPlacement var1) {
      if (this.LnsW3() && this.canSneakForPlacement(var1)) {
         this.sneakPulse.recordPlacement((int)this.sneakEvery.getInput());
      }
   }

   private boolean canSneakForPlacement(C08PacketPlayerBlockPlacement var1) {
      if (!ClientUtils.isInWorld() || mc.currentScreen != null || mc.thePlayer.capabilities.isFlying) {
         return false;
      } else if (this.notMovingForward.isToggled() && mc.thePlayer.movementInput.moveForward > 0.0F) {
         return false;
      } else if (this.lookingDown.isToggled() && mc.thePlayer.rotationPitch < 70.0F) {
         return false;
      } else if (this.holdingRightClick.isToggled() && !ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem)) {
         return false;
      } else if (!this.holdingBlocks.isToggled()) {
         return true;
      } else {
         ItemStack var2 = var1.getStack();
         return var2 != null && var2.getItem() instanceof ItemBlock;
      }
   }

   private boolean shouldSneakOnJump(MoveInputEvent var1) {
      return this.getOnJumpMode() == 1 && var1.isJumping() && mc.thePlayer.onGround && (var1.getMoveForward() != 0.0F || var1.AfWugH() != 0.0F);
   }

   private boolean isPlacementHighlightActive(long var1) {
      return this.TSe.isRecentPlacement(var1);
   }

   private float getPlacementHighlightAlpha(long var1) {
      return this.TSe.getHighlightAlpha(var1);
   }

   public boolean shouldRenderBlocksHud() {
      return this.isEnabled() && this.blocksHud.isToggled() && ClientUtils.isInWorld() && this.getPlacementHighlightAlpha(System.currentTimeMillis()) > 0.0F;
   }

   public static int countHotbarBlocks() {
      int var0 = 0;

      for (int var1 = 0; var1 < 9; var1++) {
         ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
         if (var2 != null && var2.getItem() instanceof ItemBlock && !(((ItemBlock)var2.getItem()).getBlock() instanceof BlockLadder)) {
            var0 += var2.stackSize;
         }
      }

      return var0;
   }

   public static String SCqot() {
      return FontManager.getDefaultHudFontName();
   }

   public static void renderBlocksHudAtCenter(int var0, String var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      drawBlocksHudWithFont(var0, var1, var2, var3.getScaledWidth() / 2.0F, var3.getScaledHeight() / 2.0F + 12.0F, 1.0F);
   }

   private static float[] drawBlocksHudWithFont(int var0, String var1, float var2, float var3, float var4, float var5) {
      String var6 = Integer.toString(var0);
      String var7 = " Blocks";
      IFont var8 = FontManager.getHudRenderer(resolveBoldFamily(var1), 1.0F);
      IFont var9 = FontManager.getHudRenderer(var1, 1.0F);
      String var10 = FontManager.isMinecraftFont(var1) ? "§l" + var6 : var6;
      int var11 = var8.getStringWidth(var10);
      int var12 = var9.getStringWidth(var7);
      int var13 = Math.max(var8.getFontHeight(), var9.getFontHeight());
      float var14 = var11 + var12;
      float var15 = -var14 / 2.0F;
      int var16 = Math.max(0, Math.min(255, Math.round(var2 * 255.0F)));
      GlStateManager.pushMatrix();
      GlStateManager.translate(var3, var4, 0.0F);
      GlStateManager.scale(var5, var5, 1.0F);
      var8.drawGlyphString(var10, var15, (var13 - var8.getFontHeight()) / 2.0F, (recoveredArg0, recoveredArg1, recoveredArg2, recoveredArg3) -> BridgeAssist.getGlyphColor(var15, var16, recoveredArg0, recoveredArg1, recoveredArg2, (java.lang.Integer) recoveredArg3), true);
      var9.drawString(var7, var15 + var11, (var13 - var9.getFontHeight()) / 2.0F, ClientUtils.YVVZ(16777215, var16), true);
      GlStateManager.popMatrix();
      return new float[]{var3 - var14 * var5 / 2.0F, var4, var3 + var14 * var5 / 2.0F, var4 + var13 * var5};
   }

   private static void renderBlocksHudToBuffer(int var0, String var1, float var2, float var3, float var4, float var5) {
      ExternalRenderBuffer var6 = ExternalRenderer.getActiveRenderBuffer();
      if (var6 != null) {
         String var7 = Integer.toString(var0);
         String var8 = " Blocks";
         IFont var9 = FontManager.getHudRenderer(resolveBoldFamily(var1), 1.0F);
         IFont var10 = FontManager.getHudRenderer(var1, 1.0F);
         String var11 = FontManager.isMinecraftFont(var1) ? "§l" + var7 : var7;
         int var12 = var9.getStringWidth(var11);
         int var13 = var10.getStringWidth(var8);
         int var14 = Math.max(var9.getFontHeight(), var10.getFontHeight());
         float var15 = var3 - (var12 + var13) * var5 / 2.0F;
         int var16 = Math.max(0, Math.min(255, Math.round(var2 * 255.0F)));
         float var17 = new ScaledResolution(mc).getScaleFactor();
         float var18 = var5 * var17;
         FormattedTextRenderer.drawTextAtHeight(
            var6,
            var9,
            var11,
            var15 * var17,
            (var4 + (var14 - var9.getFontHeight()) * var5 / 2.0F) * var17,
            var9.getFontHeight() * var18,
            ClientUtils.YVVZ(Arraylist.xQec0(var15 * 0.35), var16),
            true,
            var12 * var18
         );
         FormattedTextRenderer.drawTextAtHeight(
            var6,
            var10,
            var8,
            (var15 + var12 * var5) * var17,
            (var4 + (var14 - var10.getFontHeight()) * var5 / 2.0F) * var17,
            var10.getFontHeight() * var18,
            ClientUtils.YVVZ(16777215, var16),
            true,
            var13 * var18
         );
      }
   }

   public boolean lrJygt() {
      return this.blocksHud.isToggled();
   }

   public SliderSetting getScale() {
      return this.scale;
   }

   public float getHudPixelX() {
      this.oaomJ(new ScaledResolution(mc));
      return this.hudPixelX;
   }

   public float uRaud() {
      this.oaomJ(new ScaledResolution(mc));
      return this.hudPixelY;
   }

   public float getHudFractionX() {
      this.oaomJ(new ScaledResolution(mc));
      return this.GEud;
   }

   public float getHudFractionY() {
      this.oaomJ(new ScaledResolution(mc));
      return this.qopvs;
   }

   public void setHudFraction(float var1, float var2) {
      this.GEud = Math.max(0.0F, Math.min(1.0F, var1));
      this.qopvs = Math.max(0.0F, Math.min(1.0F, var2));
      this.oaomJ(new ScaledResolution(mc));
   }

   public void resetHudPosition() {
      ScaledResolution var1 = new ScaledResolution(mc);
      this.GEud = 0.5F;
      this.qopvs = (var1.getScaledHeight() / 2.0F + 12.0F) / Math.max(1.0F, (float)var1.getScaledHeight());
      this.oaomJ(var1);
   }

   public float[] TDIk(float var1, float var2) {
      return drawBlocksHudWithFont(64, this.getHudFontName(), 1.0F, var1, var2, (float)this.scale.getInput());
   }

   private void oaomJ(ScaledResolution var1) {
      if (Float.isNaN(this.GEud) || Float.isNaN(this.qopvs)) {
         this.GEud = 0.5F;
         this.qopvs = (var1.getScaledHeight() / 2.0F + 12.0F) / Math.max(1.0F, (float)var1.getScaledHeight());
      }

      this.hudPixelX = this.GEud * var1.getScaledWidth();
      this.hudPixelY = this.qopvs * var1.getScaledHeight();
   }

   private String getHudFontName() {
      return this.font.getResolvedFontName();
   }

   private static String resolveBoldFamily(String var0) {
      return FontManager.isMinecraftFont(var0) ? var0 : FontManager.getBoldFamily(var0);
   }

   private double computeEdgeDistance(AxisAlignedBB var1) {
      List var2 = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, EdgeDistanceCalculator.Soyh(var1));
      return EdgeDistanceCalculator.computeEdgeDistance(var1, var2);
   }

   private EdgePlacementSolver$2 findClutchPlacement(float var1, double var2) {
      float var4 = mc.thePlayer.rotationYaw;
      return EdgePlacementSolver.findPlacementAngles(mc.thePlayer.getEntityBoundingBox(), var4, var1, var2, BlockUtils::isReplaceableAt, RotationUtils::traceBlockHit, Math::random);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Conditions",
            "Sneak on jump",
            new String[]{"sneak key pressed", "holding blocks", "holding right click", "looking down", "not moving forward"},
            new String[]{"Sneak key held", "Holding blocks", "Right mouse held", "Looking down", "Not moving forward"}
         ),
         buildSettingAlias("Blocks HUD", "Conditions", new String[]{"blocks hud"}, new String[]{"Show blocks HUD"})
      );
   }

   private static int getGlyphColor(float var0, int var1, char var2, float var3, float var4, Integer var5) {
      return ClientUtils.YVVZ(Arraylist.xQec0((var0 + var3) * 0.35), var1);
   }
}
