// Jade recovery: module: Bed Defender (minigames); original class: jade.deps.eLz.FV47yBX
package jade.client.module.minigames;

import jade.client.common.ClientUtils;
import jade.client.common.RenderUtils;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.MouseEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo
public class BedDefender extends Module implements ProgressBarSource {
   private static final String[] DEFENSE_MODE_NAMES = new String[]{"End Long", "End Wool Long", "1 Layer Wool"};
   private static final int WUa = 0;
   private static final int END_WOOL_LONG_MODE = 1;
   private static final double WHITELISTED_BED_SEARCH_RADIUS = 18.0;
   private static final double NEARBY_BED_SEARCH_RADIUS = 8.0;
   private static final Object[][] END_LONG_PATTERN;
   private static final Object[][] END_WOOL_LONG_PATTERN;
   private static final Object[][] ONE_LAYER_WOOL_PATTERN;
   private static final Object[][][] DEFENSE_PATTERNS;
   private final BooleanSetting onlyWhitelistedBed;
   private final SliderSetting swapSpeed;
   private final SliderSetting aimSpeed;
   private final SliderSetting sneakSpeed;
   private final SliderSetting fov;
   private final SliderSetting defense;
   private Object[][] fRjalY;
   private boolean[] WQfe;
   private int[] placementOrder;
   private boolean usesLongPattern;
   private boolean PWhcE;
   private boolean isSneaking;
   private boolean pjqR;
   private BlockPos patternOriginPos;
   private EnumFacing patternAdvanceFacing;
   private EnumFacing patternRowFacing;
   private BlockPos OYCYqD;
   private EnumFacing ftq;
   private Vec3 vec3;
   private BlockPos Tzq;
   private BlockPos wkI;
   private int activeLayerIndex = -1;
   private int swapDelayTicks;
   private int PJp;
   private int iP6;
   private final Map<String, Integer> materialSlotCache = new HashMap<>();

   public BedDefender() {
      super("Bed Defender", Category.minigames);
      this.registerSetting(
         this.onlyWhitelistedBed = new BooleanSetting(
            "Only Whitelisted Bed",
            false
         )
      );
      this.registerSetting(
         this.swapSpeed = new SliderSetting(
            "Swap Speed", "%", 100.0, 0.0, 100.0, 5.0
         )
      );
      this.registerSetting(
         this.aimSpeed = new SliderSetting(
            "Aim Speed", "%", 100.0, 0.0, 100.0, 5.0
         )
      );
      this.registerSetting(
         this.sneakSpeed = new SliderSetting(
            "Sneak Speed", "%", 100.0, 0.0, 100.0, 5.0
         )
      );
      this.registerSetting(this.fov = new SliderSetting("FOV", "", 180.0, 0.0, 180.0, 1.0));
      this.registerSetting(
         this.defense = new SliderSetting(
            "Defense", 0, DEFENSE_MODE_NAMES
         )
      );
      this.initialized = true;
   }

   @Override
   public String getInfo() {
      return DEFENSE_MODE_NAMES[this.getDefenseModeIndex()];
   }

   @Override
   public boolean isProgressActive() {
      return this.isEnabled() && this.fRjalY != null && this.WQfe != null && !this.isDefenseComplete();
   }

   @Override
   public float getProgressFraction() {
      if (this.fRjalY != null && this.WQfe != null && this.fRjalY.length != 0) {
         int var1 = 0;

         for (boolean var5 : this.WQfe) {
            if (var5) {
               var1++;
            }
         }

         return Math.max(0.0F, Math.min(1.0F, (float)var1 / this.fRjalY.length));
      } else {
         return 0.0F;
      }
   }

   @Override
   public String getProgressLabel() {
      return "Bed Defender";
   }

   @Override
   public void onEnable() {
      if (!ClientUtils.isInWorld()) {
         this.disable();
      } else {
         int var1 = this.getDefenseModeIndex();
         this.usesLongPattern = var1 == 0 || var1 == 1;
         this.fRjalY = DEFENSE_PATTERNS[var1];
         this.WQfe = new boolean[this.fRjalY.length];
         this.placementOrder = this.mssXc(this.fRjalY, this.usesLongPattern);
         this.PWhcE = false;
         this.isSneaking = false;
         this.pjqR = false;
         this.patternOriginPos = null;
         this.Tzq = null;
         this.wkI = null;
         this.activeLayerIndex = -1;
         this.swapDelayTicks = 0;
         this.PJp = 0;
         this.iP6 = 0;
         this.materialSlotCache.clear();
      }
   }

   @Override
   public void onDisable() {
      this.releaseSneak();
      this.resetRuntimeState();
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      if (!var1.suppressRotations) {
         if (ClientUtils.isInWorld() && mc.currentScreen == null && this.fRjalY != null) {
            float[] var2 = this.computeTargetRotation();
            if (var2 != null && var2[0] != -999.0F) {
               var1.setRotation(var2[0], var2[1], 45);
            }
         }
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (!this.pjqR) {
            if (this.isSneaking && this.activeLayerIndex < 0) {
               this.releaseSneak();
            }
         } else {
            this.pjqR = false;
            ItemStack var2 = mc.thePlayer.getHeldItem();
            if (var2 != null && this.OYCYqD != null && this.ftq != null && this.vec3 != null) {
               if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, var2, this.OYCYqD, this.ftq, this.vec3)) {
                  mc.thePlayer.swingItem();
                  if (this.activeLayerIndex >= 0 && this.activeLayerIndex < this.WQfe.length) {
                     this.wkI = this.getLayerTargetPos(this.activeLayerIndex);
                     this.WQfe[this.activeLayerIndex] = true;
                  }

                  this.activeLayerIndex = -1;
                  this.releaseSneak();
                  this.finishIfDefenseComplete();
               }
            }
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.wkI != null) {
         RenderUtils.drawBlockBoundsBox(this.wkI, 1728000048, false, true);
      }

      if (this.Tzq != null && !this.Tzq.equals(this.wkI)) {
         RenderUtils.drawBlockBoundsBox(this.Tzq, 1711341397, false, true);
      }
   }

   @Subscribe
   public void onMouse(MouseEvent var1) {
      if (var1.button == 0 || var1.button == 1) {
         var1.setCanceled(true);
      }
   }

   private float[] computeTargetRotation() {
      if (!this.PWhcE) {
         BlockPos var1 = this.findNearbyBedFoot(8);
         if (var1 == null) {
            this.Tzq = null;
            return null;
         }

         if (!this.HqhaDrp(var1)) {
            this.Tzq = null;
            return null;
         }

         if (this.usesLongPattern) {
            this.patternOriginPos = this.patternOriginPos.offset(this.patternAdvanceFacing);
            this.patternAdvanceFacing = this.patternAdvanceFacing.getOpposite();
            this.patternRowFacing = this.rcAn(this.patternAdvanceFacing);
         }

         this.PWhcE = true;
      }

      this.skipUnreachableLayers();
      if (this.activeLayerIndex >= 0 && this.WQfe[this.activeLayerIndex]) {
         this.activeLayerIndex = -1;
         this.releaseSneak();
      }

      if (this.isDefenseComplete()) {
         this.disable();
         return null;
      } else {
         BedDefender$1 var2 = this.selectNextPlacement();
         if (var2 == null) {
            this.activeLayerIndex = -1;
            if (!this.usesLongPattern) {
               this.Tzq = null;
            }

            this.releaseSneak();
            return null;
         } else {
            this.Tzq = BedDefender$1.getBedPos(var2);
            return this.dsIp(var2);
         }
      }
   }

   private BedDefender$1 selectNextPlacement() {
      float var1 = (float)this.fov.getInput();
      float var2 = Math.min(var1, 90.0F);
      float var3 = this.normalizeAngleDegrees(mc.thePlayer.rotationYaw);
      float var4 = mc.thePlayer.rotationPitch;
      float var5 = this.normalizeAngleDegrees(RotationUtils.lastSentRotation[0]);
      float var6 = RotationUtils.lastSentRotation[1];
      Vec3 var7 = mc.thePlayer.getPositionEyes(1.0F);

      for (int var8 = 0; var8 < this.placementOrder.length; var8++) {
         int var9 = this.placementOrder[var8];
         if (!this.WQfe[var9]) {
            BlockPos var10 = this.getLayerTargetPos(var9);
            if (!this.isReplaceableAt(var10)) {
               this.WQfe[var9] = true;
            } else {
               if (this.usesLongPattern) {
                  this.Tzq = var10;
               }

               int var11 = this.findMaterialSlot(this.HOTech(var9));
               if (var11 == -1) {
                  if (this.usesLongPattern) {
                     return null;
                  }
               } else {
                  ItemStack var12 = mc.thePlayer.inventory.getStackInSlot(var11);
                  ArrayList<BedDefender$1> var13 = new ArrayList<>();

                  for (EnumFacing var17 : EnumFacing.values()) {
                     BlockPos var18 = var10.offset(var17.getOpposite());
                     if (!this.isReplaceableAt(var18) && this.canPlaceStackOnSide(var12, var18, var17)) {
                        this.collectFaceCandidates(var13, var9, var10, var18, var17, var11, var7, var3, var4, var5, var6, var1, var2);
                     }
                  }

                  if (!var13.isEmpty()) {
                     var13.sort(BedDefender::compareCandidateScores);

                     for (BedDefender$1 var20 : (java.lang.Iterable<BedDefender$1>) (java.lang.Iterable<?>) (var13)) {
                        if (this.canReachPositionWithRotation(BedDefender$1.getYaw(var20), BedDefender$1.getPitch(var20), BedDefender$1.getBedPos(var20))) {
                           return var20;
                        }
                     }
                  }

                  if (this.usesLongPattern) {
                     return null;
                  }
               }
            }
         }
      }

      return null;
   }

   private void collectFaceCandidates(
      List<BedDefender$1> var1,
      int var2,
      BlockPos var3,
      BlockPos var4,
      EnumFacing var5,
      int var6,
      Vec3 var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13
   ) {
      AxisAlignedBB var14 = this.getBlockBoundingBox(var4);

      for (double var15 = 0.1; var15 <= 0.9; var15 += 0.2) {
         for (double var17 = 0.1; var17 <= 0.9; var17 += 0.2) {
            double var19;
            double var21;
            double var23;
            if (var5.getAxis() == Axis.Y) {
               var19 = this.interpolateBetween(var14.minX, var14.maxX, var15);
               var23 = this.interpolateBetween(var14.minZ, var14.maxZ, var17);
               var21 = var5 == EnumFacing.UP ? var14.maxY - 0.001 : var14.minY + 0.001;
            } else if (var5.getAxis() == Axis.Z) {
               var19 = this.interpolateBetween(var14.minX, var14.maxX, var15);
               var21 = this.interpolateBetween(var14.minY, var14.maxY, var17);
               var23 = var5 == EnumFacing.SOUTH ? var14.maxZ - 0.001 : var14.minZ + 0.001;
            } else {
               var23 = this.interpolateBetween(var14.minZ, var14.maxZ, var15);
               var21 = this.interpolateBetween(var14.minY, var14.maxY, var17);
               var19 = var5 == EnumFacing.EAST ? var14.maxX - 0.001 : var14.minX + 0.001;
            }

            float[] var25 = this.KqZw(var7, var19, var21, var23);
            float var26 = this.normalizeAngleDegrees(var25[0]);
            float var27 = var25[1];
            if (!(Math.abs(this.vmkS2(var8, var26)) > var12) && !(Math.abs(var27 - var9) > var13) && !(Math.abs(var27) > 90.0F)) {
               float var28 = this.unwrapYawNear(var26, RotationUtils.lastSentRotation[0]);
               double var29 = Math.abs(this.vmkS2(var10, var26)) + Math.abs(var27 - var11);
               var29 += var2 * 0.05;
               if (var2 == this.activeLayerIndex) {
                  var29 -= 4.0;
               }

               if (var5 == EnumFacing.UP) {
                  var29 -= 0.25;
               }

               var1.add(new BedDefender$1(var2, var3, var6, var28, var27, var29));
            }
         }
      }
   }

   private AxisAlignedBB getBlockBoundingBox(BlockPos var1) {
      Block var2 = mc.theWorld.getBlockState(var1).getBlock();
      AxisAlignedBB var3 = var2.getSelectedBoundingBox(mc.theWorld, var1);
      return var3 != null ? var3 : new AxisAlignedBB(var1.getX(), var1.getY(), var1.getZ(), var1.getX() + 1.0, var1.getY() + 1.0, var1.getZ() + 1.0);
   }

   private double interpolateBetween(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }

   private float[] dsIp(BedDefender$1 var1) {
      if (BedDefender$1.getSlotIndex(var1) != this.activeLayerIndex) {
         this.releaseSneak();
         this.activeLayerIndex = BedDefender$1.getSlotIndex(var1);
         this.PJp = this.getSpeedDelayTicks(this.aimSpeed);
         this.iP6 = 0;
      }

      if (mc.thePlayer.inventory.currentItem != BedDefender$1.getHotbarSlot(var1)) {
         this.switchHotbarSlot(BedDefender$1.getHotbarSlot(var1));
         this.swapDelayTicks = this.getSpeedDelayTicks(this.swapSpeed);
      }

      if (this.swapDelayTicks > 0) {
         this.swapDelayTicks--;
         return new float[]{-999.0F, -999.0F};
      } else {
         MovingObjectPosition var2 = this.SMaB(4.5, BedDefender$1.getYaw(var1), BedDefender$1.getPitch(var1));
         if (var2 != null && var2.typeOfHit == MovingObjectType.BLOCK) {
            BlockPos var3 = var2.getBlockPos();
            EnumFacing var4 = var2.sideHit;
            if (!var3.offset(var4).equals(BedDefender$1.getBedPos(var1))) {
               return null;
            } else if (!this.canPlaceStackOnSide(mc.thePlayer.getHeldItem(), var3, var4)) {
               return null;
            } else {
               if (mc.theWorld.getBlockState(var3).getBlock() instanceof BlockBed) {
                  if (!this.isSneaking) {
                     KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                     this.isSneaking = true;
                     this.iP6 = Math.max(1, this.getSpeedDelayTicks(this.sneakSpeed));
                  }

                  if (this.iP6 > 0) {
                     this.iP6--;
                     return new float[]{BedDefender$1.getYaw(var1), BedDefender$1.getPitch(var1)};
                  }
               }

               if (this.PJp <= 0
                  && !(Math.abs(BedDefender$1.getYaw(var1) - RotationUtils.lastSentRotation[0]) > 25.0F)
                  && !(Math.abs(BedDefender$1.getPitch(var1) - RotationUtils.lastSentRotation[1]) > 25.0F)) {
                  this.OYCYqD = var3;
                  this.ftq = var4;
                  this.vec3 = var2.hitVec;
                  this.pjqR = true;
                  return new float[]{BedDefender$1.getYaw(var1), BedDefender$1.getPitch(var1)};
               } else {
                  if (this.PJp > 0) {
                     this.PJp--;
                  }

                  return new float[]{BedDefender$1.getYaw(var1), BedDefender$1.getPitch(var1)};
               }
            }
         } else {
            return null;
         }
      }
   }

   private boolean canReachPositionWithRotation(float var1, float var2, BlockPos var3) {
      MovingObjectPosition var4 = this.SMaB(4.5, var1, var2);
      return var4 != null && var4.typeOfHit == MovingObjectType.BLOCK && var4.getBlockPos().offset(var4.sideHit).equals(var3);
   }

   private MovingObjectPosition SMaB(double var1, float var3, float var4) {
      float var5 = MathHelper.cos(-var3 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var6 = MathHelper.sin(-var3 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var7 = -MathHelper.cos(-var4 * (float) (Math.PI / 180.0));
      float var8 = MathHelper.sin(-var4 * (float) (Math.PI / 180.0));
      Vec3 var9 = mc.thePlayer.getPositionEyes(1.0F);
      Vec3 var10 = var9.addVector(var6 * var7 * var1, var8 * var1, var5 * var7 * var1);
      return mc.theWorld.rayTraceBlocks(var9, var10, false, false, false);
   }

   private void skipUnreachableLayers() {
      for (int var1 = 0; var1 < this.fRjalY.length; var1++) {
         if (!this.WQfe[var1] && !this.isReplaceableAt(this.getLayerTargetPos(var1))) {
            this.WQfe[var1] = true;
         }
      }
   }

   private void finishIfDefenseComplete() {
      if (this.isDefenseComplete()) {
         this.Tzq = null;
         this.pjqR = false;
         this.disable();
      }
   }

   private void resetRuntimeState() {
      this.pjqR = false;
      this.Tzq = null;
      this.wkI = null;
      this.activeLayerIndex = -1;
      this.swapDelayTicks = 0;
      this.PJp = 0;
      this.iP6 = 0;
   }

   private boolean isDefenseComplete() {
      if (this.WQfe == null) {
         return true;
      } else {
         for (boolean var4 : this.WQfe) {
            if (!var4) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isReplaceableAt(BlockPos var1) {
      return var1 != null && mc.theWorld != null ? mc.theWorld.getBlockState(var1).getBlock().isReplaceable(mc.theWorld, var1) : false;
   }

   private BlockPos findNearbyBedFoot(int var1) {
      if (this.onlyWhitelistedBed.isToggled()) {
         return this.findWhitelistedBedFoot(var1);
      } else {
         BlockPos var2 = mc.thePlayer.getPosition();

         for (int var3 = -var1; var3 <= var1; var3++) {
            for (int var4 = -var1; var4 <= var1; var4++) {
               for (int var5 = -var1; var5 <= var1; var5++) {
                  BlockPos var6 = var2.add(var3, var4, var5);
                  BlockPos[] var7 = this.findBedParts(var6);
                  if (var7 != null) {
                     return var7[0];
                  }
               }
            }
         }

         return null;
      }
   }

   private BlockPos findWhitelistedBedFoot(int var1) {
      if (ClientUtils.getBedWarsBoardType() != 2) {
         return null;
      } else {
         BlockPos var2 = BedwarsUtils$2.getSpawnBlockPos();
         if (var2 == null) {
            return null;
         } else {
            BlockPos[] var3 = this.NJwfX(var2, 18.0);
            return var3 != null && this.isBedWithinReach(var3, Math.max((double)var1, 8.0)) ? var3[0] : null;
         }
      }
   }

   private BlockPos[] NJwfX(BlockPos var1, double var2) {
      int var4 = (int)Math.ceil(var2);
      double var5 = var2 * var2;
      BlockPos[] var7 = null;
      double var8 = Double.POSITIVE_INFINITY;
      Vec3 var10 = new Vec3(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);

      for (int var11 = -var4; var11 <= var4; var11++) {
         for (int var12 = -var4; var12 <= var4; var12++) {
            for (int var13 = -var4; var13 <= var4; var13++) {
               BlockPos[] var14 = this.findBedParts(var1.add(var11, var12, var13));
               if (var14 != null) {
                  Vec3 var15 = this.computeBedCenter(var14);
                  double var16 = var15.squareDistanceTo(var10);
                  if (!(var16 > var5) && !(var16 >= var8)) {
                     var8 = var16;
                     var7 = var14;
                  }
               }
            }
         }
      }

      return var7;
   }

   private BlockPos[] findBedParts(BlockPos var1) {
      IBlockState var2 = mc.theWorld.getBlockState(var1);
      if (!(var2.getBlock() instanceof BlockBed)) {
         return null;
      } else {
         EnumFacing var3 = (EnumFacing)var2.getValue(BlockBed.FACING);
         EnumPartType var4 = (EnumPartType)var2.getValue(BlockBed.PART);
         BlockPos var5 = var4 == EnumPartType.FOOT ? var1 : var1.offset(var3.getOpposite());
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

   private boolean isBedWithinReach(BlockPos[] var1, double var2) {
      Vec3 var4 = this.computeBedCenter(var1);
      double var5 = var2 * var2;
      return mc.thePlayer.getPositionEyes(1.0F).squareDistanceTo(var4) <= var5;
   }

   private Vec3 computeBedCenter(BlockPos[] var1) {
      double var2 = Math.min(var1[0].getX(), var1[1].getX());
      double var4 = Math.min(var1[0].getY(), var1[1].getY());
      double var6 = Math.min(var1[0].getZ(), var1[1].getZ());
      double var8 = Math.max(var1[0].getX(), var1[1].getX()) + 1.0;
      double var10 = Math.max(var1[0].getY(), var1[1].getY()) + 1.0;
      double var12 = Math.max(var1[0].getZ(), var1[1].getZ()) + 1.0;
      return new Vec3((var2 + var8) * 0.5, (var4 + var10) * 0.5, (var6 + var12) * 0.5);
   }

   private boolean HqhaDrp(BlockPos var1) {
      BlockPos[] var2 = this.findBedParts(var1);
      if (var2 == null) {
         return false;
      } else {
         BlockPos var3 = var2[0];
         BlockPos var4 = var2[1];
         if (var3.getX() != var4.getX()) {
            this.patternOriginPos = var3.getX() > var4.getX() ? var3 : var4;
            this.patternAdvanceFacing = EnumFacing.WEST;
         } else {
            this.patternOriginPos = var3.getZ() < var4.getZ() ? var3 : var4;
            this.patternAdvanceFacing = EnumFacing.SOUTH;
         }

         this.patternRowFacing = this.rcAn(this.patternAdvanceFacing);
         return true;
      }
   }

   private BlockPos getLayerTargetPos(int var1) {
      int[] var2 = this.getLayerOffsets(var1);
      return this.offsetByPattern(this.patternOriginPos, var2[2], var2[0]).up(var2[1]);
   }

   private int findMaterialSlot(String var1) {
      String var2 = this.wdceQ(var1);
      Integer var3 = this.materialSlotCache.get(var2);
      if (var3 != null) {
         ItemStack var4 = mc.thePlayer.inventory.getStackInSlot(var3);
         if (var4 != null && this.isMaterialStack(var4, var2)) {
            return var3;
         }
      }

      for (int var6 = 0; var6 < 9; var6++) {
         ItemStack var5 = mc.thePlayer.inventory.getStackInSlot(var6);
         if (var5 != null && this.isMaterialStack(var5, var2)) {
            this.materialSlotCache.put(var2, var6);
            return var6;
         }
      }

      return -1;
   }

   private boolean isMaterialStack(ItemStack var1, String var2) {
      if (var1 != null && var1.getItem() instanceof ItemBlock) {
         ItemBlock var3 = (ItemBlock)var1.getItem();
         Block var4 = var3.getBlock();
         String var5 = this.wdceQ(var1.getItem().getUnlocalizedName());
         String var6 = this.wdceQ(var1.getDisplayName());
         String var7 = this.wdceQ(String.valueOf(Item.itemRegistry.getNameForObject(var1.getItem())));
         String var8 = this.wdceQ(String.valueOf(Block.blockRegistry.getNameForObject(var4)));
         return this.containsNormalized(var5, var2) || this.containsNormalized(var6, var2) || this.containsNormalized(var7, var2) || this.containsNormalized(var8, var2) || this.matchesBlockAlias(var4, var2);
      } else {
         return false;
      }
   }

   private boolean canPlaceStackOnSide(ItemStack var1, BlockPos var2, EnumFacing var3) {
      return var1 != null
         && var1.getItem() instanceof ItemBlock
         && ((ItemBlock)var1.getItem()).canPlaceBlockOnSide(mc.theWorld, var2, var3, mc.thePlayer, var1);
   }

   private void switchHotbarSlot(int var1) {
      if (var1 >= 0 && var1 <= 8 && mc.thePlayer.inventory.currentItem != var1) {
         mc.thePlayer.inventory.currentItem = var1;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private void releaseSneak() {
      if (this.isSneaking) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
         this.isSneaking = false;
         this.iP6 = 0;
      }
   }

   private int getSpeedDelayTicks(SliderSetting var1) {
      double var2 = var1.getInput();
      if (var2 < 0.0) {
         var2 = 0.0;
      }

      if (var2 > 100.0) {
         var2 = 100.0;
      }

      return (int)Math.round((100.0 - var2) / 10.0);
   }

   private String wdceQ(String var1) {
      if (var1 == null) {
         return "";
      } else {
         String var2 = var1.toLowerCase(Locale.ROOT);
         StringBuilder var3 = new StringBuilder(var2.length());

         for (int var4 = 0; var4 < var2.length(); var4++) {
            char var5 = var2.charAt(var4);
            if (var5 >= 'a' && var5 <= 'z' || var5 >= '0' && var5 <= '9') {
               var3.append(var5);
            }
         }

         return var3.toString();
      }
   }

   private boolean containsNormalized(String var1, String var2) {
      return !var1.isEmpty() && !var2.isEmpty() && var1.contains(var2);
   }

   private boolean matchesBlockAlias(Block var1, String var2) {
      if (var1 == Blocks.wool) {
         return "wool".equals(var2) || "cloth".equals(var2);
      } else if (var1 == Blocks.end_stone) {
         return "endstone".equals(var2) || "whitestone".equals(var2);
      } else if (var1 == Blocks.planks) {
         return "wood".equals(var2) || "planks".equals(var2);
      } else if (var1 == Blocks.stained_hardened_clay) {
         return "stainedclay".equals(var2) || "stainedhardenedclay".equals(var2);
      } else {
         return var1 != Blocks.hardened_clay ? false : "clay".equals(var2) || "hardenedclay".equals(var2);
      }
   }

   private String HOTech(int var1) {
      return (String)this.fRjalY[var1][0];
   }

   private int[] getLayerOffsets(int var1) {
      return new int[]{((Number)this.fRjalY[var1][1]).intValue(), ((Number)this.fRjalY[var1][2]).intValue(), ((Number)this.fRjalY[var1][3]).intValue()};
   }

   private int[] getPatternOffsets(Object[][] var1, int var2) {
      return new int[]{((Number)var1[var2][1]).intValue(), ((Number)var1[var2][2]).intValue(), ((Number)var1[var2][3]).intValue()};
   }

   private int[] mssXc(Object[][] var1, boolean var2) {
      if (var2) {
         int[] var6 = new int[var1.length];
         int var8 = 0;

         while (var8 < var6.length) {
            var6[var8] = var8++;
         }

         return var6;
      } else {
         Integer[] var3 = new Integer[var1.length];

         for (int var4 = 0; var4 < var3.length; var4++) {
            var3[var4] = var4;
         }

         Arrays.sort(var3, (recoveredArg0, recoveredArg1) -> this.HFNHm(var1, recoveredArg0, recoveredArg1));
         int[] var7 = new int[var3.length];

         for (int var5 = 0; var5 < var3.length; var5++) {
            var7[var5] = var3[var5];
         }

         return var7;
      }
   }

   private BlockPos offsetByPattern(BlockPos var1, int var2, int var3) {
      BlockPos var4 = this.offsetHorizontally(var1, this.patternAdvanceFacing, var2);
      return this.offsetHorizontally(var4, this.patternRowFacing, var3);
   }

   private BlockPos offsetHorizontally(BlockPos var1, EnumFacing var2, int var3) {
      return var2 != null && var3 != 0 ? var1.add(var2.getFrontOffsetX() * var3, 0, var2.getFrontOffsetZ() * var3) : var1;
   }

    private EnumFacing rcAn(EnumFacing enumFacing) {
        if (enumFacing == null) {
            return EnumFacing.EAST;
        }
        switch (enumFacing) {
            case NORTH: {
                return EnumFacing.WEST;
            }
            case EAST: {
                return EnumFacing.NORTH;
            }
            case SOUTH: {
                return EnumFacing.EAST;
            }
            case WEST: {
                return EnumFacing.SOUTH;
            }
        }
        return EnumFacing.EAST;
    }

   private int getDefenseModeIndex() {
      int var1 = (int)Math.round(this.defense.getInput());
      if (var1 < 0) {
         return 0;
      } else {
         return var1 >= DEFENSE_MODE_NAMES.length ? DEFENSE_MODE_NAMES.length - 1 : var1;
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(buildSettingAlias("Conditions", "Defense", new String[]{"only whitelisted bed"}, new String[]{"Only Whitelisted Bed"}));
   }

   private float normalizeAngleDegrees(float var1) {
      var1 = (var1 % 360.0F + 360.0F) % 360.0F;
      return var1 > 180.0F ? var1 - 360.0F : var1;
   }

   private float vmkS2(float var1, float var2) {
      float var3 = var2 - var1;

      while (var3 <= -180.0F) {
         var3 += 360.0F;
      }

      while (var3 > 180.0F) {
         var3 -= 360.0F;
      }

      return var3;
   }

   private float unwrapYawNear(float var1, float var2) {
      return var2 + (((var1 - var2 + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F);
   }

   private float[] KqZw(Vec3 var1, double var2, double var4, double var6) {
      double var8 = var2 - var1.xCoord;
      double var10 = var4 - var1.yCoord;
      double var12 = var6 - var1.zCoord;
      double var14 = Math.sqrt(var8 * var8 + var12 * var12);
      return new float[]{this.normalizeAngleDegrees((float)Math.toDegrees(Math.atan2(var12, var8)) - 90.0F), (float)Math.toDegrees(-Math.atan2(var10, var14))};
   }

   private int HFNHm(Object[][] var1, Integer var2, Integer var3) {
      int[] var4 = this.getPatternOffsets(var1, var2);
      int[] var5 = this.getPatternOffsets(var1, var3);
      if (var4[1] != var5[1]) {
         return Integer.compare(var4[1], var5[1]);
      } else {
         return var4[2] != var5[2] ? Integer.compare(var4[2], var5[2]) : Integer.compare(var4[0], var5[0]);
      }
   }

   private static int compareCandidateScores(BedDefender$1 var0, BedDefender$1 var1) {
      return Double.compare(BedDefender$1.getScore(var0), BedDefender$1.getScore(var1));
   }

   static {
      Object[][] var10000 = new Object[12][];
      Object[] var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = -1;
      var10000[0] = var10003;
      int var10002 = 1;
      var10003 = new Object[]{"end_stone", null, null, null};
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[2] = var10003;
      var10002 = 3;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = 3;
      var10000[4] = var10003;
      var10002 = 5;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 6;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 7;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 8;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10002 = 9;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 10;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 11;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      END_LONG_PATTERN = var10000;
      var10000 = new Object[35][];
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = -1;
      var10000[0] = var10003;
      var10002 = 1;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 2;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 3;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10002 = 4;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = 3;
      var10000[var10002] = var10003;
      var10002 = 5;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 6;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 1;
      var10000[7] = var10003;
      var10002 = 8;
      var10003 = new Object[]{"end_stone", null, null, null};
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10002 = 9;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[10] = var10003;
      var10003 = new Object[4];
      var10003[0] = "end_stone";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[11] = var10003;
      var10002 = 12;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -2;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 13;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -2;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -2;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[14] = var10003;
      var10002 = 15;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -1;
      var10003[2] = 1;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -1;
      var10003[2] = 1;
      var10003[3] = 1;
      var10000[16] = var10003;
      var10002 = 17;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -1;
      var10003[2] = 1;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10002 = 18;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 2;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 2;
      var10003[3] = 1;
      var10000[19] = var10003;
      var10002 = 20;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 2;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10002 = 21;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 2;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 22;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 2;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 23;
      var10003 = new Object[]{"wool", null, null, null};
      var10003[1] = 2;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10002 = 24;
      var10003 = new Object[]{"wool", null, null, null};
      var10003[1] = 1;
      var10003[2] = 1;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      var10002 = 25;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 1;
      var10003[2] = 1;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 26;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 1;
      var10003[2] = 1;
      var10003[3] = 0;
      var10000[var10002] = var10003;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = -1;
      var10000[27] = var10003;
      var10002 = 28;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = -1;
      var10000[var10002] = var10003;
      var10002 = 29;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = -1;
      var10000[var10002] = var10003;
      var10002 = 30;
      var10003 = new Object[]{"wool", null, null, null};
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = -2;
      var10000[var10002] = var10003;
      var10002 = 31;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 3;
      var10000[var10002] = var10003;
      var10002 = 32;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = 4;
      var10000[var10002] = var10003;
      var10002 = 33;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 3;
      var10000[var10002] = var10003;
      var10002 = 34;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 3;
      var10000[var10002] = var10003;
      END_WOOL_LONG_PATTERN = var10000;
      var10000 = new Object[8][];
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 0;
      var10000[0] = var10003;
      var10002 = 1;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 1;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 2;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = -1;
      var10000[var10002] = var10003;
      var10003 = new Object[]{
         "wool", -1, null, null
      };
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[3] = var10003;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 0;
      var10000[4] = var10003;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = -1;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[5] = var10003;
      var10002 = 6;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 1;
      var10003[2] = 0;
      var10003[3] = 1;
      var10000[var10002] = var10003;
      var10002 = 7;
      var10003 = new Object[4];
      var10003[0] = "wool";
      var10003[1] = 0;
      var10003[2] = 0;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      ONE_LAYER_WOOL_PATTERN = var10000;
      Object[][][] var2 = new Object[3][][];
      var2[0] = END_LONG_PATTERN;
      var2[1] = END_WOOL_LONG_PATTERN;
      var2[2] = ONE_LAYER_WOOL_PATTERN;
      DEFENSE_PATTERNS = var2;
   }
}
