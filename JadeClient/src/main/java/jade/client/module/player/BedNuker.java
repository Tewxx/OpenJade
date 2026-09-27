// Jade recovery: module: Bed Nuker (player); original class: jade.deps.eLz.Po8OIt0
package jade.client.module.player;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.RenderUtils;
import jade.client.common.RotationHandler;
import jade.client.common.RotationUtils;
import jade.client.common.PointedObjectOverrider;
import jade.client.common.Subscribe;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.ClickMouseEvent;
import jade.client.event.MouseEvent;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RotationEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.BedwarsUtils$2;
import jade.client.module.player.bednuker.BedFinder$2;
import jade.client.module.player.bednuker.BedFinder;
import jade.client.module.player.bednuker.TargetRayTracer;
import jade.client.module.player.bednuker.BreakTarget;
import jade.client.module.player.bednuker.oGkiVIewGW;
import jade.client.module.player.shared.BreakDamageHelper;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorEntityPlayerSP;
import jade.mixin.impl.accessor.IAccessorEntityRenderer;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class BedNuker extends Module implements ProgressBarSource, PointedObjectOverrider {
   private final SliderSetting fov;
   private final SliderSetting range;
   private final SliderSetting breakSpeed;
   private final BooleanSetting ignoreMiningFatigue;
   private final BooleanSetting groundSpoof;
   private final BooleanSetting blockZapper;
   private final BooleanSetting zeroTickBlockZapper;
   private final BooleanSetting prioritizeKillAura;
   private final GroupSetting swapGroup;
   private final BooleanSetting switchBackWhenDone;
   private final BooleanSetting renderOutline;
   private static final int BED_SCAN_INTERVAL_TICKS = 50;
   private static final int ovws = 50;
   private static final int MIN_RESCAN_TICKS = 0;
   private static final double MIN_RESCAN_DELAY = 1.0;
   private static final double MAX_BED_DISTANCE_SQ = 800.0;
   private static final String BLOCK_ZAPPER_NAME = "Block Zapper";
   private final BedFinder bedFinder = new BedFinder();
   private int Cnu;
   private BlockPos blockPos;
   private Vec3 vec3;
   private EnumFacing enumFacing;
   private boolean MIJ;
   private int VkxQ;
   private int sxnS5 = -1;
   private int slotSwitchDepth;
   private boolean Wjmxhu;
   private boolean IovrrP;
   private int FMJYpd = -1;
   private int kJp = -1;
   private int qb5 = -1;
   private int MxT = -1;
   private C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement;
   private final oGkiVIewGW JsY = new oGkiVIewGW();
   private final BedFinder$2 bedBlockFilter = new BedFinder$2() {
      @Override
      public boolean isBreakable(Block var1) {
         return BedNuker.canBreakBlock(BedNuker.this, var1);
      }

      @Override
      public float getBreakSpeed(Block var1) {
         return BedNuker.getMaxBreakSpeed(BedNuker.this, var1);
      }
   };

   public BedNuker() {
      super("Bed Nuker", Category.player);
      this.registerSetting(
         this.breakSpeed = new SliderSetting(
            "Break speed", "x", 1.0, 1.0, 2.0, 0.02
         )
      );
      this.registerSetting(this.ignoreMiningFatigue = new BooleanSetting("Ignore mining fatigue", false));
      this.registerSetting(this.groundSpoof = new BooleanSetting("Ground spoof", false));
      this.registerSetting(
         this.range = new SliderSetting(
            "Range", " blocks", 4.5, 2.0, 6.0, 0.1
         )
      );
      this.registerSetting(this.fov = new SliderSetting("FOV", "", 180.0, 30.0, 360.0, 1.0));
      this.registerSetting(
         this.blockZapper = new BooleanSetting(
            "Block zapper", false
         )
      );
      this.registerSetting(
         this.zeroTickBlockZapper = new BooleanSetting(
            "Zero tick block zapper",
            false
         )
      );
      this.registerSetting(
         this.prioritizeKillAura = new BooleanSetting(
            "Prioritize KillAura",
            false
         )
      );
      this.registerSetting(this.swapGroup = new GroupSetting("Swap"));
      this.registerSetting(this.switchBackWhenDone = new BooleanSetting(this.swapGroup, "Switch back when done", true, new String[]{"Swap to previous slot"}));
      this.switchBackWhenDone.enable();
      this.switchBackWhenDone.visible = false;
      this.registerSetting(this.renderOutline = new BooleanSetting("Render outline", true, new String[]{"Render block progress fade"}));
   }

   @Override
   public void guiUpdate() {
      this.zeroTickBlockZapper.setVisible(this.blockZapper.isToggled(), this);
      if (!this.switchBackWhenDone.isToggled()) {
         this.switchBackWhenDone.enable();
      }
   }

   @Override
   public void onDisable() {
      this.Jucmm();
      this.bedFinder.clearCandidates();
      this.Cnu = 0;
      this.VkxQ = 0;
      this.sxnS5 = -1;
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onTickStart(TickStartEvent var1) {
      this.VkxQ++;
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      this.updateMiningAction();
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onMouse(MouseEvent var1) {
      if (this.shouldSuppressInput()) {
         if (var1.button == 0 || var1.button == 1) {
            var1.setCanceled(true);
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onClickMouse(ClickMouseEvent var1) {
      if (this.shouldSuppressInput()) {
         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (this.shouldSuppressInput()) {
         int var2 = this.FMJYpd;
         this.resetNukeState(false);
         if (var2 != -1 && ClientUtils.isInWorld()) {
            mc.thePlayer.inventory.currentItem = var2;
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (this.shouldSuppressInput() && this.slotSwitchDepth <= 0) {
         this.resetNukeState(false);
      }
   }

   private boolean shouldSuppressInput() {
      return this.isAimedThisTick();
   }

   public void updateMiningAction() {
      if (this.canMine() && (!this.MIJ || this.isTargetBreakable()) && !this.iEjyDt()) {
         if (!this.isAimedThisTick()) {
            this.restoreKeyStates();
         } else {
            int var1 = mc.gameSettings.keyBindAttack.getKeyCode();
            int var2 = mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(var1, false);
            KeyBinding.setKeyBindState(var2, false);
            this.Wjmxhu = true;
            if (this.gxScaEc() && this.c08PacketPlayerBlockPlacement == null) {
               if (this.blockZapper.isToggled() && this.isHoldingBlockZapper()) {
                  KeyBinding.setKeyBindState(var2, true);
               } else {
                  KeyBinding.setKeyBindState(var1, true);
               }
            }
         }
      } else {
         if (this.MIJ) {
            this.Jucmm();
         }
      }
   }

   public BlockPos getTargetBlockPos() {
      return this.MIJ && this.canMine() && this.isTargetBreakable() ? this.blockPos : null;
   }

   public boolean isNukingTarget() {
      return this.MIJ && this.isEnabled() && ClientUtils.isInWorld() && mc.currentScreen == null && this.canMine() && this.isTargetBreakable() && !this.iEjyDt();
   }

   public boolean gxScaEc() {
      return this.isAimedThisTick() && this.rayTraceTarget(this.blockPos) != null;
   }

   private boolean isAimedThisTick() {
      return this.sxnS5 == this.VkxQ && this.isNukingTarget() && this.vec3 != null && this.enumFacing != null;
   }

   private MovingObjectPosition rayTraceTarget(BlockPos var1) {
      if (var1 != null && ClientUtils.isInWorld()) {
         RotationHandler var2 = RotationHandler.getInstance();
         Float var3 = var2.getTargetYaw();
         Float var4 = var2.cvZx();
         IAccessorEntityPlayerSP var5 = (IAccessorEntityPlayerSP)mc.thePlayer;
         return TargetRayTracer.rayTraceWithReportedCheck(
            BlockUtils.getSelectedBounds(var1),
            mc.thePlayer.getPositionEyes(1.0F),
            this.range.getInput(),
            var3 == null ? mc.thePlayer.rotationYaw : var3,
            var4 == null ? mc.thePlayer.rotationPitch : var4,
            var5.getLastReportedYaw(),
            var5.getLastReportedPitch()
         );
      } else {
         return null;
      }
   }

   public boolean isNuking() {
      return this.isNukingTarget();
   }

   public float xskLk() {
      return this.IKijtw();
   }

   public float TnokwE() {
      return this.isGroundSpoofing() ? 5.0F : 1.0F;
   }

   private float IKijtw() {
      float var1 = (float)this.breakSpeed.getInput();
      if (this.ignoreMiningFatigue.isToggled() && mc.thePlayer != null && mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
         ItemStack var2 = mc.thePlayer.getHeldItem();
         Item var3 = var2 != null ? var2.getItem() : null;
         if (var3 != Items.wooden_axe && var3 != Items.wooden_pickaxe) {
            var1 = 2.5F;
         } else {
            var1 = 1.8F;
         }
      }

      return var1 > 1.0F ? var1 : 1.0F;
   }

   private boolean isGroundSpoofing() {
      return this.groundSpoof.isToggled() && mc.thePlayer != null && !mc.thePlayer.onGround;
   }

   public int getBlockHitDelay() {
      return 0;
   }

   public void QLkR() {
      if (this.gxScaEc() && mc.inGameHasFocus && mc.playerController != null && this.blockPos != null) {
         IAccessorPlayerControllerMP var1 = (IAccessorPlayerControllerMP)mc.playerController;
         BlockPos var2 = var1.getCurrentBlock();
         if (var1.getIsHittingBlock() && var2 != null && this.blockPos.equals(var2)) {
            float var3 = this.getBreakDamagePerTick();
            if (!(var3 <= 0.0F)) {
               float var4 = var1.getCurBlockDamageMP();
               float var5 = BreakDamageHelper.oxOn(var4, var3);
               if (var5 > var4) {
                  var1.setCurBlockDamageMP(var5);
               }
            }
         }
      }
   }

   public float getBreakDamagePerTick() {
      return BreakDamageHelper.GeX06(this.xskLk());
   }

   public float getBreakProgress() {
      if (this.canMine() && this.MIJ && mc.playerController != null) {
         IAccessorPlayerControllerMP var1 = (IAccessorPlayerControllerMP)mc.playerController;
         BlockPos var2 = var1.getCurrentBlock();
         return this.blockPos != null && var2 != null && this.blockPos.equals(var2) ? var1.getCurBlockDamageMP() : 0.0F;
      } else {
         return 0.0F;
      }
   }

   @Override
   public boolean isProgressActive() {
      return this.isNukingTarget();
   }

   @Override
   public float getProgressFraction() {
      return Math.max(0.0F, Math.min(1.0F, this.getBreakProgress()));
   }

   @Override
   public String getProgressLabel() {
      return "Bed Nuker";
   }

   @Override
   public boolean shouldOverridePointedObject() {
      return this.gxScaEc();
   }

   @Override
   public void applyPointedObjectOverride(float var1) {
      if (this.shouldOverridePointedObject()) {
         if (mc.getRenderViewEntity() != null) {
            MovingObjectPosition var2 = this.rayTraceTarget(this.blockPos);
            if (var2 != null) {
               MovingObjectPosition var3 = new MovingObjectPosition(var2.hitVec, var2.sideHit, this.blockPos);
               mc.objectMouseOver = var3;
               mc.pointedEntity = null;
               EntityRenderer var4 = mc.entityRenderer;
               if (var4 instanceof IAccessorEntityRenderer) {
                  ((IAccessorEntityRenderer)var4).setPointedEntity(null);
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onRotation(RotationEvent var1) {
      if (!this.isEnabled() || !ClientUtils.isInWorld() || mc.currentScreen != null || !this.canMine()) {
         this.Jucmm();
      } else if (this.iEjyDt()) {
         this.Jucmm();
      } else if (var1.suppressRotations) {
         this.Jucmm();
      } else {
         double var2 = this.range.getInput();
         double var4 = var2 * var2;
         if (--this.Cnu <= 0) {
            this.Cnu = Math.max(1, 1);
            this.BcVn(var2 + 1.0);
         }

         if (this.bedFinder.isCandidateListEmpty()) {
            this.Jucmm();
         } else {
            BreakTarget var6 = this.findCurrentTarget(var4);
            if (var6 == null) {
               var6 = this.findBestTarget(var4);
            }

            if (var6 == null) {
               this.Jucmm();
            } else {
               this.clearPendingPlacement();
               BreakTarget var7 = this.getZeroTickSwapTarget(var6);
               boolean var8 = var7 != null;
               if (var8) {
                  var6 = var7;
               }

               this.blockPos = var6.getBlockPos();
               this.vec3 = var6.getHitVec();
               this.enumFacing = var6.getSide();
               this.MIJ = true;
               if (!var8) {
                  this.selectToolForBlock(BlockUtils.iepjdt(this.blockPos));
               }

               float var9 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
               float[] var10 = TargetRayTracer.LynS(BlockUtils.getSelectedBounds(this.blockPos), mc.thePlayer.getPositionEyes(1.0F), var9);
               this.sxnS5 = var1.setRotation(var10[0], var10[1], 45) ? this.VkxQ : -1;
            }
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.isEnabled() && this.renderOutline.isToggled() && this.MIJ && this.isTargetBreakable() && ClientUtils.isInWorld() && this.canMine()) {
         IBlockState var2 = mc.theWorld.getBlockState(this.blockPos);
         Block var3 = var2.getBlock();
         if (var3 != null && var3 != Blocks.air) {
            this.renderTargetOutline(this.blockPos, this.getOutlineColor());
         }
      }
   }

   private int getOutlineColor() {
      return this.JsY.getOutlineColor(this.blockPos, this.isMiningTargetBlock(), this.getBreakProgress());
   }

   private boolean isMiningTargetBlock() {
      if (this.blockPos != null && mc.playerController != null) {
         BlockPos var1 = ((IAccessorPlayerControllerMP)mc.playerController).getCurrentBlock();
         return var1 != null && this.blockPos.equals(var1);
      } else {
         return false;
      }
   }

   private void renderTargetOutline(BlockPos var1, int var2) {
      AxisAlignedBB var3 = BlockUtils.getSelectedBounds(var1);
      if (var3 != null) {
         double var4 = mc.getRenderManager().viewerPosX;
         double var6 = mc.getRenderManager().viewerPosY;
         double var8 = mc.getRenderManager().viewerPosZ;
         AxisAlignedBB var10 = var3.offset(-var4, -var6, -var8).expand(0.002, 0.002, 0.002);
         float var11 = (var2 >> 24 & 0xFF) / 255.0F;
         float var12 = (var2 >> 16 & 0xFF) / 255.0F;
         float var13 = (var2 >> 8 & 0xFF) / 255.0F;
         float var14 = (var2 & 0xFF) / 255.0F;
         GlStateManager.pushMatrix();
         GL11.glPushAttrib(1048575);

         try {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            RenderUtils.drawFilledAabb(var10, var12, var13, var14, var11);
         } finally {
            GL11.glPopAttrib();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
         }
      }
   }

   private void Jucmm() {
      this.resetNukeState(true);
   }

   private void resetNukeState(boolean var1) {
      this.MIJ = false;
      this.sxnS5 = -1;
      this.clearPendingPlacement();
      this.kJp = -1;
      if (var1 && this.FMJYpd != -1 && ClientUtils.isInWorld()) {
         this.PSfg(this.FMJYpd);
      }

      this.restoreKeyStates();
      this.slotSwitchDepth = 0;
      this.blockPos = null;
      this.vec3 = null;
      this.enumFacing = null;
      this.JsY.reset();
      this.IovrrP = false;
      this.FMJYpd = -1;
   }

   private void restoreKeyStates() {
      if (this.Wjmxhu) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), Mouse.isButtonDown(0));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), Mouse.isButtonDown(1));
         this.Wjmxhu = false;
      }
   }

   public void processPendingPlacement() {
      if (!ClientUtils.isInWorld() || mc.playerController == null) {
         this.kJp = -1;
         this.clearPendingPlacement();
      } else if (this.c08PacketPlayerBlockPlacement == null) {
         int var4 = this.kJp;
         this.kJp = -1;
         this.switchToSlot(var4);
      } else if (this.gxScaEc() && this.rayTraceTarget(this.c08PacketPlayerBlockPlacement.getPosition()) != null) {
         int var1 = this.qb5;
         int var2 = this.MxT;
         C08PacketPlayerBlockPlacement var3 = this.c08PacketPlayerBlockPlacement;
         this.kJp = -1;
         this.clearPendingPlacement();
         this.switchToSlot(var1);
         mc.thePlayer.sendQueue.addToSendQueue(var3);
         if (var2 >= 0) {
            this.switchToSlot(var2);
         }
      }
   }

   private void BcVn(double var1) {
      BlockPos var3 = BedwarsUtils$2.getSpawnBlockPos();
      if (var3 == null || ClientUtils.getBedWarsBoardType() != 2 || mc.thePlayer.getDistanceSq(var3) > 800.0) {
         var3 = null;
      }

      this.bedFinder.NNLvsH(mc.theWorld, mc.thePlayer, var1, (float)this.fov.getInput(), var3);
   }

   private BreakTarget findBestTarget(double var1) {
      IAccessorPlayerControllerMP var3 = (IAccessorPlayerControllerMP)mc.playerController;
      return this.bedFinder.findBestReachableTarget(mc.theWorld, mc.thePlayer, var1, var3.getCurBlockDamageMP(), var3.getCurrentBlock(), this.bedBlockFilter);
   }

   private BreakTarget findCurrentTarget(double var1) {
      return this.MIJ && this.isTargetBreakable() ? this.bedFinder.findTargetAtPos(mc.theWorld, mc.thePlayer, this.blockPos, var1, this.bedBlockFilter) : null;
   }

   private void selectToolForBlock(Block var1) {
      int var2 = -1;
      if (this.blockZapper.isToggled()) {
         var2 = this.findBlockZapperSlot();
      }

      if (var2 < 0) {
         var2 = this.findBestToolSlot(var1);
      }

      if (var2 >= 0) {
         if (this.FMJYpd == -1 && var2 != mc.thePlayer.inventory.currentItem) {
            this.FMJYpd = mc.thePlayer.inventory.currentItem;
         }

         if (var2 != mc.thePlayer.inventory.currentItem) {
            this.PSfg(var2);
         }
      }
   }

   private int findBlockZapperSlot() {
      if (mc.thePlayer == null) {
         return -1;
      } else {
         for (int var1 = 0; var1 < InventoryPlayer.getHotbarSize(); var1++) {
            ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
            if (var2 != null
               && var2.getItem() == Items.prismarine_shard
               && var2.hasDisplayName()
               && ClientUtils.AOAtn(var2.getDisplayName()).equals("Block Zapper")) {
               return var1;
            }
         }

         return -1;
      }
   }

   private boolean isHoldingBlockZapper() {
      if (mc.thePlayer == null) {
         return false;
      } else {
         ItemStack var1 = mc.thePlayer.getHeldItem();
         if (var1 == null || var1.getItem() != Items.prismarine_shard) {
            return false;
         } else {
            return !var1.hasDisplayName() ? false : ClientUtils.AOAtn(var1.getDisplayName()).equals("Block Zapper");
         }
      }
   }

   private float computeMaxBreakSpeed(Block var1) {
      if (mc.thePlayer == null) {
         return 0.0F;
      } else {
         float var2 = this.requiresPickaxe(var1) ? 0.0F : BlockUtils.getBreakSpeed(var1, null, this.ignoreMiningFatigue.isToggled(), this.groundSpoof.isToggled());

         for (int var3 = 0; var3 < InventoryPlayer.getHotbarSize(); var3++) {
            ItemStack var4 = mc.thePlayer.inventory.getStackInSlot(var3);
            if (this.isEffectiveTool(var4, var1)) {
               float var5 = BlockUtils.getBreakSpeed(var1, var4, this.ignoreMiningFatigue.isToggled(), this.groundSpoof.isToggled());
               if (var5 > var2) {
                  var2 = var5;
               }
            }
         }

         return var2;
      }
   }

   private int findBestToolSlot(Block var1) {
      if (mc.thePlayer == null) {
         return -1;
      } else {
         int var2 = -1;
         float var3 = this.requiresPickaxe(var1) ? 0.0F : BlockUtils.getBreakSpeed(var1, null, this.ignoreMiningFatigue.isToggled(), this.groundSpoof.isToggled());

         for (int var4 = 0; var4 < InventoryPlayer.getHotbarSize(); var4++) {
            ItemStack var5 = mc.thePlayer.inventory.getStackInSlot(var4);
            if (this.isEffectiveTool(var5, var1)) {
               float var6 = BlockUtils.getBreakSpeed(var1, var5, this.ignoreMiningFatigue.isToggled(), this.groundSpoof.isToggled());
               if (var6 > var3) {
                  var3 = var6;
                  var2 = var4;
               }
            }
         }

         return var2;
      }
   }

   private boolean IdcX(Block var1) {
      return !this.requiresPickaxe(var1) ? true : this.findBestToolSlot(var1) >= 0;
   }

   private boolean isEffectiveTool(ItemStack var1, Block var2) {
      return var1 == null ? false : !this.requiresPickaxe(var2) || var1.getItem() instanceof ItemPickaxe;
   }

   private boolean requiresPickaxe(Block var1) {
      if (var1 != null && !(var1 instanceof BlockBed)) {
         Material var2 = var1.getMaterial();
         return var2 == Material.iron || var2 == Material.anvil || var2 == Material.rock;
      } else {
         return false;
      }
   }

   private BreakTarget getZeroTickSwapTarget(BreakTarget var1) {
      if (this.blockZapper.isToggled() && this.zeroTickBlockZapper.isToggled()) {
         Block var2 = BlockUtils.iepjdt(var1.getBlockPos());
         if (var2 instanceof BlockBed) {
            return null;
         } else {
            int var3 = this.findBlockZapperSlot();
            if (var3 < 0) {
               return null;
            } else {
               BlockPos var4 = this.bedFinder.findAdjacentBedPart(var1.getBlockPos());
               if (var4 == null) {
                  return null;
               } else {
                  AxisAlignedBB var5 = BlockUtils.getSelectedBounds(var4);
                  if (var5 == null) {
                     return null;
                  } else {
                     Vec3 var6 = mc.thePlayer.getPositionEyes(1.0F);
                     float[] var7 = TargetRayTracer.LynS(var5, var6, RotationUtils.lastSentRotation[0]);
                     if (TargetRayTracer.rayTraceFromRotation(BlockUtils.getSelectedBounds(var1.getBlockPos()), var6, this.range.getInput(), var7[0], var7[1]) == null) {
                        return null;
                     } else {
                        if (this.FMJYpd == -1 && mc.thePlayer.inventory.currentItem != var3) {
                           this.FMJYpd = mc.thePlayer.inventory.currentItem;
                        }

                        ItemStack var8 = mc.thePlayer.inventory.getStackInSlot(var3);
                        this.qb5 = var3;
                        this.c08PacketPlayerBlockPlacement = new C08PacketPlayerBlockPlacement(
                           var1.getBlockPos(),
                           var1.getSide().getIndex(),
                           var8,
                           (float)(var1.getHitVec().xCoord - var1.getBlockPos().getX()),
                           (float)(var1.getHitVec().yCoord - var1.getBlockPos().getY()),
                           (float)(var1.getHitVec().zCoord - var1.getBlockPos().getZ())
                        );
                        int var9 = this.findBestToolSlot(BlockUtils.iepjdt(var4));
                        this.MxT = var9;
                        this.kJp = -1;
                        Vec3 var10 = RotationUtils.ONLLQYf(var5, var6);
                        EnumFacing var11 = BlockUtils.getFacingTowardPoint(var4, var10);
                        return new BreakTarget(var4, var10, var11);
                     }
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   private void PSfg(int var1) {
      if (var1 >= 0) {
         this.kJp = var1 == mc.thePlayer.inventory.currentItem ? -1 : var1;
      }
   }

   private void switchToSlot(int var1) {
      if (var1 != -1 && var1 != mc.thePlayer.inventory.currentItem) {
         this.slotSwitchDepth++;

         try {
            mc.thePlayer.inventory.currentItem = var1;
            this.IovrrP = true;
            ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
         } finally {
            this.slotSwitchDepth--;
         }
      }
   }

   private void clearPendingPlacement() {
      this.qb5 = -1;
      this.MxT = -1;
      this.c08PacketPlayerBlockPlacement = null;
   }

   private boolean canMine() {
      return mc.thePlayer != null
         && !mc.thePlayer.isDead
         && mc.thePlayer.getHealth() > 0.0F
         && mc.thePlayer.capabilities.allowEdit
         && !mc.thePlayer.capabilities.isCreativeMode
         && !mc.thePlayer.isSpectator();
   }

   private boolean isTargetBreakable() {
      if (this.blockPos != null && mc.theWorld != null) {
         Block var1 = BlockUtils.iepjdt(this.blockPos);
         return var1 != null && var1 != Blocks.air && var1.getBlockHardness(mc.theWorld, this.blockPos) >= 0.0F;
      } else {
         return false;
      }
   }

   private boolean iEjyDt() {
      return false;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Action",
            "FOV",
            new String[]{"ignore mining fatigue", "ground spoof", "block zapper", "zero tick block zapper", "prioritize killaura"},
            new String[]{"Ignore mining fatigue", "Ground spoof", "Block zapper", "Zero tick zapper", "Prioritise KillAura"}
         ),
         buildSettingAlias("Swap", "Action", new String[]{"switch back when done"}, new String[]{"Switch back"}),
         buildSettingAlias("Visuals", "Swap", new String[]{"render outline"}, new String[]{"Render outline"})
      );
   }

   public static boolean canBreakBlock(BedNuker var0, Block var1) {
      return var0.IdcX(var1);
   }

   public static float getMaxBreakSpeed(BedNuker var0, Block var1) {
      return var0.computeMaxBreakSpeed(var1);
   }
}
