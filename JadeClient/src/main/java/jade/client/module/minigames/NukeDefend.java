// Jade recovery: module: Nuke Defend (minigames); original class: jade.deps.eLz.Fb8OTJ
package jade.client.module.minigames;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.RenderUtils;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RotationEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.Arraylist;
import jade.client.setting.BooleanSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class NukeDefend extends Module {
   private static final double MAX_PLACEMENT_REACH = 4.5;
   private static final double WHITELISTED_BED_SEARCH_RADIUS = 18.0;
   private static final double FEliv = 8.0;
   private static final double MAX_WHITELISTED_BED_DISTANCE_SQ = 800.0;
   private static final int PLACEMENT_CONFIRM_DELAY_TICKS = 2;
   private static final int HoI = 20;
   private static final long PLACEMENT_SUPPRESSION_MILLIS = 60000L;
   private static final long woNsa = 3000L;
   private static final long oU4 = 3000L;
   private static final float HIGHLIGHT_MAX_ALPHA = 0.46F;
   private static final EnumFacing[] PLACEMENT_FACINGS;
   private final BooleanSetting throughWalls;
   private final BooleanSetting onlyWhitelistedBed;
   private final BooleanSetting highlightPlacedBlocks;
   private final SliderSetting rotationSpeed;
   private BlockPos targetBlockPos;
   private EnumFacing TVJcT;
   private Vec3 LrwciV;
   private boolean shouldPlaceBlock;
   private BlockPos pwR;
   private int verifyDelayTicks;
   private int failedPlacementTicks;
   private long suppressionUntilMillis;
   private long IbddG;
   private int blockedPlacementCount;
   private int placementDepth;
   private boolean resetAimNextTick;
   private boolean sOa;
   private boolean Splleo;
   private int savedHotbarSlot = -1;
   private final List<NukeDefend$1> EGiVe = new ArrayList<>();

   public NukeDefend() {
      super("Nuke Defend", Category.minigames);
      this.registerSetting(this.throughWalls = new BooleanSetting("Through walls", true));
      this.registerSetting(this.onlyWhitelistedBed = new BooleanSetting("Only Whitelisted Bed", true));
      this.registerSetting(this.highlightPlacedBlocks = new BooleanSetting("Highlight placed blocks", true));
      this.registerSetting(this.rotationSpeed = new SliderSetting("Rotation speed", 30.0, 1.0, 30.0, 1.0));
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.Splleo = false;
      this.savedHotbarSlot = -1;
      this.hoAtnnV();
      this.mXfg();
      this.KmaP8();
      this.resetPlacementState();
      this.resetPendingBlockCheck();
      this.EGiVe.clear();
   }

   @Override
   public void onDisable() {
      this.restoreHotbarSlot();
      this.hoAtnnV();
      this.mXfg();
      this.KmaP8();
      this.resetPlacementState();
      this.resetPendingBlockCheck();
      this.EGiVe.clear();
   }

   @Subscribe
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (this.Splleo) {
         int var2 = Integer.compare(var1.scrollDirection, 0);
         this.savedHotbarSlot = Math.floorMod(mc.thePlayer.inventory.currentItem - var2, InventoryPlayer.getHotbarSize());
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (this.Splleo) {
         this.savedHotbarSlot = var1.slot;
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      this.restoreHotbarSlot();
      if (var1.suppressRotations || !ClientUtils.isInWorld() || mc.currentScreen != null) {
         this.hoAtnnV();
      } else if (!this.EkrsOs() && !this.isPlacementCooldown()) {
         this.hoAtnnV();
         NukeDefend$2 var2 = this.selectBestPlacement();
         if (var2 != null) {
            if (this.ensureBlockSlotSelected(NukeDefend$2.ZKNliC(var2))) {
               float var3 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
               float var4 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
               float[] var5 = RotationUtils.anglesToCoordinates(NukeDefend$2.getHitVec(var2).xCoord, NukeDefend$2.getHitVec(var2).yCoord, NukeDefend$2.getHitVec(var2).zCoord, var3, var4);
               float[] var6 = RotationUtils.smoothAngles(var3, var4, var5[0], var5[1], (int)this.rotationSpeed.getInput());
               if (!var1.setRotation(var6[0], var6[1], 45)) {
                  this.restoreHotbarSlot();
               } else {
                  if (this.Hqou(var2, var6[0], var6[1])) {
                     this.targetBlockPos = NukeDefend$2.getClickedPos(var2);
                     this.TVJcT = NukeDefend$2.getClickedSide(var2);
                     this.LrwciV = NukeDefend$2.getHitVec(var2);
                     this.shouldPlaceBlock = true;
                  } else {
                     this.restoreHotbarSlot();
                  }
               }
            }
         }
      } else {
         this.hoAtnnV();
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.hoAtnnV();
         this.resetPendingBlockCheck();
         this.resetPlacementState();
         this.restoreHotbarSlot();
      } else {
         this.tickPlacedBlockCheck();
         if (this.resetAimNextTick) {
            this.resetAimNextTick = false;
            this.hoAtnnV();
            this.restoreHotbarSlot();
         } else if (this.EkrsOs() || this.isPlacementCooldown()) {
            this.hoAtnnV();
            this.restoreHotbarSlot();
         } else if (this.shouldPlaceBlock) {
            this.shouldPlaceBlock = false;
            ItemStack var2 = mc.thePlayer.getHeldItem();
            if (this.EDJoEmj(var2) && this.targetBlockPos != null && this.TVJcT != null && this.LrwciV != null) {
               BlockPos var3 = this.targetBlockPos.offset(this.TVJcT);
               if (!this.isChosenPlacementPos(var3)) {
                  this.hoAtnnV();
                  this.restoreHotbarSlot();
               } else {
                  boolean var4 = BlockUtils.isInteractiveBlock(BlockUtils.iepjdt(this.targetBlockPos)) && !mc.thePlayer.isSneaking();
                  if (var4) {
                     KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                  }

                  try {
                     this.placementDepth++;
                     boolean var5 = mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, var2, this.targetBlockPos, this.TVJcT, this.LrwciV);
                     if (var5) {
                        mc.thePlayer.swingItem();
                        BlockPos var6 = this.targetBlockPos.offset(this.TVJcT);
                        this.schedulePlacedBlockCheck(var6);
                        this.recordPlacedBlock(var6);
                     }
                  } finally {
                     this.placementDepth--;
                     if (var4) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                     }

                     this.hoAtnnV();
                     this.restoreHotbarSlot();
                  }
               }
            } else {
               this.hoAtnnV();
               this.restoreHotbarSlot();
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (this.placementDepth <= 0 && !var1.isCanceled() && var1.ys98() instanceof C08PacketPlayerBlockPlacement) {
         C08PacketPlayerBlockPlacement var2 = (C08PacketPlayerBlockPlacement)var1.ys98();
         if (this.isValidPlacementPacket(var2)) {
            int var3 = var2.getPlacedBlockDirection();
            BlockPos var4 = var2.getPosition().offset(EnumFacing.getFront(var3));
            BlockPos[] var5 = this.findTargetBedPositions();
            if (var5 != null && this.collectAdjacentPositions(var5).contains(var4)) {
               this.XjSezI();
            }

            if (this.EkrsOs()) {
               this.blockedPlacementCount++;
               if (this.blockedPlacementCount > 20) {
                  this.mXfg();
                  this.failedPlacementTicks = 0;
               }
            }
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.isEnabled() && this.highlightPlacedBlocks.isToggled() && ClientUtils.isInWorld() && !this.EGiVe.isEmpty()) {
         this.renderPlacedBlockHighlights();
      }
   }

   private void recordPlacedBlock(BlockPos var1) {
      if (var1 != null) {
         long var2 = System.currentTimeMillis();

         for (int var4 = this.EGiVe.size() - 1; var4 >= 0; var4--) {
            if (NukeDefend$1.Kptew(this.EGiVe.get(var4)).equals(var1)) {
               this.EGiVe.set(var4, new NukeDefend$1(var1, var2));
               return;
            }
         }

         this.EGiVe.add(new NukeDefend$1(var1, var2));
      }
   }

   private void renderPlacedBlockHighlights() {
      long var1 = System.currentTimeMillis();
      int var3 = Arraylist.xQec0(0.0);
      float var4 = (var3 >> 16 & 0xFF) / 255.0F;
      float var5 = (var3 >> 8 & 0xFF) / 255.0F;
      float var6 = (var3 & 0xFF) / 255.0F;
      double var7 = mc.getRenderManager().viewerPosX;
      double var9 = mc.getRenderManager().viewerPosY;
      double var11 = mc.getRenderManager().viewerPosZ;
      GlStateManager.pushMatrix();
      GL11.glPushAttrib(1048575);

      try {
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.disableTexture2D();
         GlStateManager.disableDepth();
         GlStateManager.enableCull();
         GlStateManager.depthMask(false);
         Iterator var13 = this.EGiVe.iterator();

         while (var13.hasNext()) {
            NukeDefend$1 var14 = (NukeDefend$1)var13.next();
            long var15 = var1 - NukeDefend$1.getPlacedAtMillis(var14);
            if (var15 >= 3000L) {
               var13.remove();
            } else {
               float var17 = MathHelper.clamp_float((float)var15 / 3000.0F, 0.0F, 1.0F);
               float var18 = 1.0F - var17;
               var18 *= var18;
               AxisAlignedBB var19 = this.getBlockBoundingBox(NukeDefend$1.Kptew(var14)).expand(0.002, 0.002, 0.002).offset(-var7, -var9, -var11);
               RenderUtils.drawFilledAabb(var19, var4, var5, var6, 0.46F * var18);
            }
         }
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

   private AxisAlignedBB getBlockBoundingBox(BlockPos var1) {
      AxisAlignedBB var2 = BlockUtils.getSelectedBounds(var1);
      return var2 != null ? var2 : new AxisAlignedBB(var1.getX(), var1.getY(), var1.getZ(), var1.getX() + 1.0, var1.getY() + 1.0, var1.getZ() + 1.0);
   }

   private void schedulePlacedBlockCheck(BlockPos var1) {
      this.pwR = var1;
      this.verifyDelayTicks = 2;
   }

   private void tickPlacedBlockCheck() {
      if (this.pwR != null) {
         if (this.verifyDelayTicks > 0) {
            this.verifyDelayTicks--;
         } else {
            if (BlockUtils.isReplaceableAt(this.pwR)) {
               this.failedPlacementTicks++;
               if (this.failedPlacementTicks >= 2) {
                  this.beginPlacementSuppression();
               }
            } else {
               this.failedPlacementTicks = 0;
            }

            this.resetPendingBlockCheck();
         }
      }
   }

   private boolean EkrsOs() {
      if (this.suppressionUntilMillis <= 0L) {
         return false;
      } else if (System.currentTimeMillis() >= this.suppressionUntilMillis) {
         this.mXfg();
         this.failedPlacementTicks = 0;
         return false;
      } else {
         return true;
      }
   }

   private void beginPlacementSuppression() {
      this.suppressionUntilMillis = System.currentTimeMillis() + 60000L;
      this.blockedPlacementCount = 0;
      this.hoAtnnV();
      this.resetPendingBlockCheck();
      this.restoreHotbarSlot();
   }

   private void mXfg() {
      this.suppressionUntilMillis = 0L;
      this.blockedPlacementCount = 0;
   }

   private boolean isPlacementCooldown() {
      if (this.IbddG <= 0L) {
         return false;
      } else if (System.currentTimeMillis() - this.IbddG >= 3000L) {
         this.KmaP8();
         return false;
      } else {
         return true;
      }
   }

   private void XjSezI() {
      this.IbddG = System.currentTimeMillis();
      this.hoAtnnV();
      this.restoreHotbarSlot();
   }

   private void KmaP8() {
      this.IbddG = 0L;
   }

   private boolean isValidPlacementPacket(C08PacketPlayerBlockPlacement var1) {
      int var2 = var1.getPlacedBlockDirection();
      if (var2 >= 0 && var2 < EnumFacing.values().length) {
         ItemStack var3 = var1.getStack();
         if (var3 == null && mc.thePlayer != null) {
            var3 = mc.thePlayer.getHeldItem();
         }

         return var3 != null && var3.getItem() instanceof ItemBlock;
      } else {
         return false;
      }
   }

   public boolean shouldBlockDuplicatePlacement() {
      return this.isEnabled() && this.placementDepth == 0 && this.sOa;
   }

   public boolean MuoLy() {
      if (!this.isEnabled()) {
         return false;
      } else if (this.placementDepth > 0) {
         this.sOa = true;
         return false;
      } else if (this.sOa) {
         return true;
      } else {
         this.resetAimNextTick = true;
         return false;
      }
   }

   public void clearPendingPlacement() {
      if (this.isEnabled()) {
         this.sOa = false;
      }
   }

   private void resetPlacementState() {
      this.resetAimNextTick = false;
      this.sOa = false;
   }

   private void resetPendingBlockCheck() {
      this.pwR = null;
      this.verifyDelayTicks = 0;
   }

   private NukeDefend$2 selectBestPlacement() {
      BlockPos[] var1 = this.findTargetBedPositions();
      if (var1 != null && !this.isPlacementCooldown()) {
         BlockPos var2 = this.findUniqueReplaceableNeighbor(var1);
         if (var2 == null) {
            return null;
         } else {
            ItemStack var3 = this.getBestBlockStack();
            if (var3 == null) {
               return null;
            } else {
               Vec3 var4 = mc.thePlayer.getPositionEyes(1.0F);
               ArrayList var5 = new ArrayList();
               this.collectPlacementCandidates(var2, var3, var4, var5);
               if (var5.isEmpty()) {
                  return null;
               } else {
                  var5.sort(Comparator.comparingDouble(NukeDefend::getPlacementScore));
                  return (NukeDefend$2)var5.get(0);
               }
            }
         }
      } else {
         return null;
      }
   }

   private boolean isChosenPlacementPos(BlockPos var1) {
      if (var1 != null && !this.isPlacementCooldown()) {
         BlockPos[] var2 = this.findTargetBedPositions();
         if (var2 == null) {
            return false;
         } else {
            BlockPos var3 = this.findUniqueReplaceableNeighbor(var2);
            return var1.equals(var3);
         }
      } else {
         return false;
      }
   }

   private BlockPos findUniqueReplaceableNeighbor(BlockPos[] var1) {
      BlockPos var2 = null;

      for (BlockPos var4 : this.collectAdjacentPositions(var1)) {
         if (BlockUtils.isReplaceableAt(var4)) {
            if (var2 != null) {
               return null;
            }

            var2 = var4;
         }
      }

      return var2;
   }

   private Set<BlockPos> collectAdjacentPositions(BlockPos[] var1) {
      HashSet var2 = new HashSet();
      if (var1 != null && var1.length >= 2) {
         for (BlockPos var6 : var1) {
            for (EnumFacing var10 : PLACEMENT_FACINGS) {
               BlockPos var11 = var6.offset(var10);
               if (!var11.equals(var1[0]) && !var11.equals(var1[1])) {
                  var2.add(var11);
               }
            }
         }

         return var2;
      } else {
         return var2;
      }
   }

   private void collectPlacementCandidates(BlockPos var1, ItemStack var2, Vec3 var3, List<NukeDefend$2> var4) {
      for (EnumFacing var8 : EnumFacing.values()) {
         BlockPos var9 = var1.offset(var8.getOpposite());
         if (!BlockUtils.isReplaceableAt(var9) && BlockUtils.canPlaceItemOnSide(var2, var9, var8)) {
            Vec3 var10 = BlockUtils.sdlDajR(var9, var8);
            if (!(var3.distanceTo(var10) > 4.5)) {
               double var11 = var3.squareDistanceTo(var10);
               if (BlockUtils.iepjdt(var9) instanceof BlockBed) {
                  var11 -= 0.35;
               }

               if (var8 == EnumFacing.UP) {
                  var11 -= 0.1;
               }

               var4.add(new NukeDefend$2(var1, var9, var8, var10, var11));
            }
         }
      }
   }

   private boolean Hqou(NukeDefend$2 var1, float var2, float var3) {
      if (this.throughWalls.isToggled()) {
         return this.isAimOnTargetThroughWalls(var2, var3, NukeDefend$2.getHitVec(var1));
      } else {
         MovingObjectPosition var4 = RotationUtils.traceBlockHit(4.5, var2, var3);
         return var4 != null
            && var4.typeOfHit == MovingObjectType.BLOCK
            && NukeDefend$2.getClickedPos(var1).equals(var4.getBlockPos())
            && NukeDefend$2.getClickedSide(var1) == var4.sideHit;
      }
   }

   private boolean isAimOnTargetThroughWalls(float var1, float var2, Vec3 var3) {
      float[] var4 = RotationUtils.anglesToCoordinates(var3.xCoord, var3.yCoord, var3.zCoord, RotationUtils.lastSentRotation[0], RotationUtils.lastSentRotation[1]);
      return Math.abs(MathHelper.wrapAngleTo180_float(var4[0] - var1)) <= 1.5F && Math.abs(var4[1] - var2) <= 1.5F;
   }

   private boolean ensureBlockSlotSelected(BlockPos var1) {
      int var2 = this.findBestBlockHotbarSlot(var1);
      if (var2 < 0) {
         return false;
      } else {
         if (var2 != mc.thePlayer.inventory.currentItem) {
            this.savedHotbarSlot = mc.thePlayer.inventory.currentItem;
            this.switchHotbarSlot(var2);
         }

         return true;
      }
   }

   private void restoreHotbarSlot() {
      int var1 = this.savedHotbarSlot;
      if (var1 >= 0 && var1 < InventoryPlayer.getHotbarSize() && ClientUtils.isInWorld() && mc.playerController != null) {
         this.switchHotbarSlot(var1);
      }

      this.savedHotbarSlot = -1;
      this.Splleo = false;
   }

   private void switchHotbarSlot(int var1) {
      if (var1 >= 0 && var1 < InventoryPlayer.getHotbarSize() && var1 != mc.thePlayer.inventory.currentItem) {
         mc.thePlayer.inventory.currentItem = var1;
         this.Splleo = true;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private ItemStack getBestBlockStack() {
      int var1 = this.findBestBlockHotbarSlot(null);
      return var1 < 0 ? null : mc.thePlayer.inventory.getStackInSlot(var1);
   }

   private int findBestBlockHotbarSlot(BlockPos var1) {
      int var2 = -1;
      float var3 = -1.0F;
      int var4 = -1;
      BlockPos var5 = var1 != null ? var1 : mc.thePlayer.getPosition();

      for (int var6 = 0; var6 < InventoryPlayer.getHotbarSize(); var6++) {
         ItemStack var7 = mc.thePlayer.inventory.getStackInSlot(var6);
         if (this.EDJoEmj(var7)) {
            Block var8 = ((ItemBlock)var7.getItem()).getBlock();
            float var9 = var8.getBlockHardness(mc.theWorld, var5);
            if (!(var9 < 0.0F) && (var9 > var3 || var9 == var3 && var7.stackSize > var4)) {
               var3 = var9;
               var4 = var7.stackSize;
               var2 = var6;
            }
         }
      }

      return var2;
   }

   private boolean EDJoEmj(ItemStack var1) {
      return var1 != null && var1.stackSize > 0 && var1.getItem() instanceof ItemBlock && ClientUtils.isPassableBlock((ItemBlock)var1.getItem());
   }

   private BlockPos[] findTargetBedPositions() {
      if (this.onlyWhitelistedBed.isToggled()) {
         BlockPos var1 = BedwarsUtils$2.getSpawnBlockPos();
         if (var1 != null && ClientUtils.getBedWarsBoardType() == 2 && mc.thePlayer.getDistanceSq(var1) <= 800.0) {
            BlockPos[] var2 = this.IphG(var1, 18.0);
            if (var2 != null) {
               return var2;
            }
         }

         if (ClientUtils.getBedWarsBoardType() == 2) {
            return null;
         }
      }

      return this.IphG(mc.thePlayer.getPosition(), 8.0);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(buildSettingAlias("Conditions", "Rotation speed", new String[]{"only whitelisted bed"}, new String[]{"Only Whitelisted Bed"}));
   }

   private BlockPos[] IphG(BlockPos var1, double var2) {
      int var4 = (int)Math.ceil(var2);
      double var5 = var2 * var2;
      BlockPos var7 = null;
      BlockPos var8 = null;
      double var9 = Double.POSITIVE_INFINITY;

      for (int var11 = -var4; var11 <= var4; var11++) {
         for (int var12 = -var4; var12 <= var4; var12++) {
            for (int var13 = -var4; var13 <= var4; var13++) {
               BlockPos var14 = var1.add(var11, var12, var13);
               BlockPos[] var15 = this.findBedParts(var14);
               if (var15 != null) {
                  Vec3 var16 = this.computeBedCenter(var15);
                  double var17 = var16.squareDistanceTo(new Vec3(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5));
                  if (!(var17 > var5) && !(var17 >= var9)) {
                     var9 = var17;
                     var7 = var15[0];
                     var8 = var15[1];
                  }
               }
            }
         }
      }

      return var7 == null ? null : new BlockPos[]{var7, var8};
   }

   private BlockPos[] findBedParts(BlockPos var1) {
      IBlockState var2 = mc.theWorld.getBlockState(var1);
      if (!(var2.getBlock() instanceof BlockBed)) {
         return null;
      } else {
         EnumPartType var3 = (EnumPartType)var2.getValue(BlockBed.PART);
         EnumFacing var4 = (EnumFacing)var2.getValue(BlockBed.FACING);
         BlockPos var5 = var3 == EnumPartType.FOOT ? var1 : var1.offset(var4.getOpposite());
         IBlockState var6 = mc.theWorld.getBlockState(var5);
         if (var6.getBlock() instanceof BlockBed && var6.getValue(BlockBed.PART) == EnumPartType.FOOT) {
            EnumFacing var7 = (EnumFacing)var6.getValue(BlockBed.FACING);
            BlockPos var8 = var5.offset(var7);
            IBlockState var9 = mc.theWorld.getBlockState(var8);
            return var9.getBlock() instanceof BlockBed && var9.getValue(BlockBed.PART) == EnumPartType.HEAD && var9.getValue(BlockBed.FACING) == var7
               ? new BlockPos[]{var5, var8}
               : null;
         } else {
            return null;
         }
      }
   }

   private Vec3 computeBedCenter(BlockPos[] var1) {
      AxisAlignedBB var2 = BlockUtils.getCombinedBounds(var1[0], var1[1]);
      return new Vec3((var2.minX + var2.maxX) * 0.5, (var2.minY + var2.maxY) * 0.5, (var2.minZ + var2.maxZ) * 0.5);
   }

   private void hoAtnnV() {
      this.targetBlockPos = null;
      this.TVJcT = null;
      this.LrwciV = null;
      this.shouldPlaceBlock = false;
   }

   private static double getPlacementScore(NukeDefend$2 var0) {
      return NukeDefend$2.getDistanceScore(var0);
   }

   static {
      EnumFacing[] var10000 = new EnumFacing[5];
      var10000[0] = EnumFacing.UP;
      var10000[1] = EnumFacing.NORTH;
      var10000[2] = EnumFacing.SOUTH;
      var10000[3] = EnumFacing.EAST;
      var10000[4] = EnumFacing.WEST;
      PLACEMENT_FACINGS = var10000;
   }
}
