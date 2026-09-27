// Jade recovery: module: Clutch Assist (player); original class: jade.deps.eLz.hFekCFn
package jade.client.module.player;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.MouseEvent;
import jade.client.event.RotationEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorMinecraft;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class ClutchAssist extends Module {
   private final SliderSetting randomisation;
   private final SliderSetting fov;
   private final SliderSetting maxRotationSpeed;
   private final SliderSetting maxClutchLength;
   private final SliderSetting minimumFall;
   private final BooleanSetting ladder;
   private final BooleanSetting swapSlot;
   private final BooleanSetting holdingBlocks;
   private final BooleanSetting damaged;
   private final BooleanSetting inAir;
   private final BooleanSetting holdingRightClick;
   private ClutchAssist$1 YTbl;
   private boolean Wt9;
   private int TOmir;
   private int XmrX;
   private int akbwsn;
   private boolean damagedInAir;
   private boolean wasInAir;
   private BlockPos blockPos;
   public AtomicInteger previousHotbarSlot = new AtomicInteger(-1);
   private int rememberedBlockSlot = -1;
   private static final double MAX_CLUTCH_REACH = 4.5;
   private static final int HORIZONTAL_SEARCH_MARGIN = 2;
   private static final int VERTICAL_SEARCH_DEPTH = 5;
   private static final int MOTION_PREDICTION_TICKS = 4;
   private static final double[] RuM;
   private static final EnumFacing[] HORIZONTAL_FACINGS;

   public ClutchAssist() {
      super("Clutch Assist", Category.player);
      this.registerSetting(
         this.randomisation = new SliderSetting(
            "Randomisation", "%", 0.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(
         this.fov = new SliderSetting(
            "FOV", "\u00b0", 180.0, 1.0, 180.0, 1.0
         )
      );
      this.registerSetting(
         this.maxRotationSpeed = new SliderSetting(
            "Max Rotation Speed",
            "\u00b0",
            180.0,
            1.0,
            180.0,
            1.0
         )
      );
      this.registerSetting(this.maxClutchLength = new SliderSetting("Max clutch length", "", 10.0, 1.0, 50.0, 1.0));
      this.registerSetting(this.minimumFall = new SliderSetting("Minimum Fall", 4.0, 1.0, 20.0, 1.0, new String[]{"Trigger distance", "Trigger Distance"}));
      this.minimumFall.setSuffix("blocks");
      this.registerSetting(
         this.ladder = new BooleanSetting(
            "Ladder", false
         )
      );
      this.registerSetting(this.swapSlot = new BooleanSetting("Swap slot", true));
      this.registerSetting(
         this.holdingBlocks = new BooleanSetting(
            "Holding blocks", true
         )
      );
      this.registerSetting(
         this.damaged = new BooleanSetting(
            "Damaged", false
         )
      );
      this.registerSetting(
         this.inAir = new BooleanSetting(
            "In air", true
         )
      );
      this.registerSetting(
         this.holdingRightClick = new BooleanSetting(
            "Holding right click", false
         )
      );
   }

   @Override
   public void guiUpdate() {
      this.holdingBlocks.setVisible(!this.swapSlot.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.resetState();
   }

   @Override
   public void onDisable() {
      if (mc.thePlayer != null && this.previousHotbarSlot.get() != -1) {
         this.setHotbarSlot(this.previousHotbarSlot.get());
      }

      this.resetState();
   }

   private void resetState() {
      this.previousHotbarSlot.set(-1);
      this.rememberedBlockSlot = -1;
      this.TOmir = 0;
      this.damagedInAir = false;
      this.wasInAir = false;
      this.blockPos = null;
      this.YTbl = null;
      this.Wt9 = false;
      this.XmrX = 0;
      this.akbwsn = -1;
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onRotation(RotationEvent var1) {
      if (ClientUtils.isInWorld()) {
         this.YTbl = null;
         float var2 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
         float var3 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
         if (this.canStartClutch()) {
            ClutchAssist$1 var4 = this.countPlaceableBlocks() > 0 ? this.RHBJsc(var2, var3) : null;
            if (var4 == null && this.ladder.isToggled()) {
               var4 = this.hS87(var2, var3);
            }

            if (var4 != null) {
               this.YTbl = this.limitRotationStep(var4, var2, var3);
               this.Wt9 = true;
               var1.setRotation(this.YTbl.usiU, this.YTbl.targetPitch, 50);
               return;
            }
         }

         this.Wt9 = false;
      }
   }

   @Subscribe
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (mc.thePlayer.hurtTime > 0 && !mc.thePlayer.onGround) {
            this.damagedInAir = true;
         }

         if (mc.thePlayer.onGround) {
            if (this.wasInAir) {
               this.TOmir = 0;
               this.damagedInAir = false;
               this.Wt9 = false;
            }

            this.wasInAir = false;
         } else {
            this.wasInAir = true;
         }

         if (!this.Wt9 && this.previousHotbarSlot.get() != -1) {
            this.setHotbarSlot(this.previousHotbarSlot.get());
            this.previousHotbarSlot.set(-1);
            this.rememberedBlockSlot = -1;
         }

         if (this.YTbl != null) {
            if (this.isItemUseBlocked()) {
               this.YTbl = null;
               this.Wt9 = false;
            } else {
               var1.ebfpOkg(this.YTbl.usiU, this.YTbl.targetPitch);
               BlockPos var2 = this.YTbl.vj4.offset(this.YTbl.enumFacing);
               if (!this.isReplaceableAt(var2) || !this.YTbl.ladderClutch && this.KINXj(var2) || !this.isSolidSupportAt(this.YTbl.vj4)) {
                  this.YTbl = null;
               } else if (!this.YTbl.stillAimedAt) {
                  this.YTbl = null;
               } else if (this.YTbl.ladderClutch ? this.equipLadder() : this.hasPlaceableBlock() && this.ANhh()) {
                  double var3 = this.getClutchReach();
                  MovingObjectPosition var5 = RotationUtils.traceBlockThroughUncollidable(var3, this.YTbl.usiU, this.YTbl.targetPitch);
                  Vec3 var6 = this.YTbl.vec3;
                  if (this.QUaF(this.YTbl, var5)) {
                     var6 = var5.hitVec;
                  } else if (!this.isWithinReach(var6, var3)) {
                     this.YTbl = null;
                     return;
                  }

                  if (this.TaAt()) {
                     this.stopMining();
                     this.YTbl = null;
                  } else {
                     this.stopMining();
                     ((IAccessorMinecraft)mc).setRightClickDelayTimer(0);
                     if (this.placeBlockAt(this.YTbl.vj4, this.YTbl.enumFacing, var6)) {
                        this.TOmir++;
                        this.blockPos = this.YTbl.vj4.offset(this.YTbl.enumFacing);
                        ((IAccessorMinecraft)mc).setRightClickDelayTimer(0);
                     }

                     this.YTbl = null;
                  }
               } else {
                  this.YTbl = null;
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onMouse(MouseEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (!mc.thePlayer.onGround && this.isActivelyClutching() && var1.button == 0) {
            var1.setCanceled(true);
         }
      }
   }

   @Subscribe
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (this.swapSlot.isToggled() && this.isActivelyClutching()) {
         this.previousHotbarSlot.set(var1.slot);
         var1.setCanceled(true);
      }
   }

   private boolean canStartClutch() {
      if (this.isItemUseBlocked()) {
         return false;
      } else if (this.inAir.isToggled() && mc.thePlayer.onGround) {
         return false;
      } else if (this.holdingRightClick.isToggled() && !Mouse.isButtonDown(1)) {
         return false;
      } else if (this.damaged.isToggled() && !this.damagedInAir) {
         return false;
      } else {
         if (!this.swapSlot.isToggled() && this.holdingBlocks.isToggled()) {
            ItemStack var1 = mc.thePlayer.getHeldItem();
            if (!this.isPlaceableBlockStack(var1)) {
               return false;
            }
         }

         if (this.TOmir >= (int)this.maxClutchLength.getInput()) {
            return false;
         } else if (this.countPlaceableBlocks() > 0 || this.ladder.isToggled() && this.findLadderSlot() != -1) {
            if (mc.thePlayer.motionY >= 0.0) {
               return false;
            } else if (this.Cdqi()) {
               return false;
            } else {
               int var3 = this.getFallDistanceToGround();
               int var2 = (int)this.minimumFall.getInput();
               return var3 == -1 || var3 >= var2;
            }
         } else {
            return false;
         }
      }
   }

   private int getFallDistanceToGround() {
      int var1 = MathHelper.floor_double(mc.thePlayer.posX);
      int var2 = MathHelper.floor_double(mc.thePlayer.posZ);
      int var3 = MathHelper.floor_double(mc.thePlayer.posY) - 1;

      for (int var4 = var3; var4 >= 0; var4--) {
         Block var5 = mc.theWorld.getBlockState(new BlockPos(var1, var4, var2)).getBlock();
         if (var5.getMaterial() != Material.air && !(var5 instanceof BlockLiquid)) {
            return var3 - var4;
         }
      }

      return -1;
   }

   private ClutchAssist$1 RHBJsc(float var1, float var2) {
      AxisAlignedBB var3 = mc.thePlayer.getEntityBoundingBox();
      double var4 = mc.thePlayer.posX + mc.thePlayer.motionX * 4.0;
      double var6 = mc.thePlayer.posY + mc.thePlayer.motionY * 4.0;
      double var8 = mc.thePlayer.posZ + mc.thePlayer.motionZ * 4.0;
      int var10 = MathHelper.floor_double(Math.min(var3.minX, var3.minX + mc.thePlayer.motionX * 4.0)) - 2;
      int var11 = MathHelper.floor_double(Math.max(var3.maxX, var3.maxX + mc.thePlayer.motionX * 4.0)) + 2;
      int var12 = MathHelper.floor_double(Math.min(var3.minZ, var3.minZ + mc.thePlayer.motionZ * 4.0)) - 2;
      int var13 = MathHelper.floor_double(Math.max(var3.maxZ, var3.maxZ + mc.thePlayer.motionZ * 4.0)) + 2;
      int var14 = MathHelper.floor_double(mc.thePlayer.posY);
      int var15 = Math.max(0, MathHelper.floor_double(Math.min(mc.thePlayer.posY, var6)) - 5);
      float var16 = (float)(this.randomisation.getInput() / 100.0 * 5.0);
      ClutchAssist$1 var17 = null;
      boolean var18 = false;
      double var19 = Double.MAX_VALUE;

      for (int var21 = var14; var21 >= var15; var21--) {
         for (int var22 = var10; var22 <= var11; var22++) {
            for (int var23 = var12; var23 <= var13; var23++) {
               BlockPos var24 = new BlockPos(var22, var21, var23);
               if (this.isReplaceableAt(var24) && !this.KINXj(var24)) {
                  boolean var25 = this.CTNt(var24);

                  for (EnumFacing var29 : EnumFacing.values()) {
                     BlockPos var30 = var24.offset(var29);
                     if (this.isSolidSupportAt(var30) && !(mc.theWorld.getBlockState(var30).getBlock() instanceof BlockLiquid) && var30.getY() < var14 + 2) {
                        EnumFacing var31 = var29.getOpposite();
                        if (var31 != EnumFacing.DOWN) {
                           boolean var32 = this.isSidePlacementValid(var24, var30, var31);
                           if (var32 && (var31 == EnumFacing.UP || this.isReplaceableAt(var30.up()))) {
                              for (double var36 : RuM) {
                                 for (double var41 : RuM) {
                                    Vec3 var43 = this.getPointOnFace(var30, var31, var36, var41);
                                    float[] var44 = this.computeAnglesToPoint(var43, var1, var2);
                                    if (var44 != null) {
                                       float var45 = MathHelper.wrapAngleTo180_float(var44[0] - var1);
                                       float var46 = var44[1] - var2;
                                       if (!(Math.sqrt(var45 * var45 + var46 * var46) > (float)this.fov.getInput())) {
                                          MovingObjectPosition var47 = RotationUtils.traceBlockHit(this.getClutchReach(), var44[0], var44[1]);
                                          if (var47 != null && var47.getBlockPos().equals(var30) && var47.sideHit == var31) {
                                             double var48 = var24.getX() + 0.5 - var4;
                                             double var50 = var24.getY() + 0.5 - var6;
                                             double var52 = var24.getZ() + 0.5 - var8;
                                             double var54 = Math.sqrt(var48 * var48 + var52 * var52) * 5.0
                                                + Math.abs(var50) * 1.5
                                                + Math.abs(var45) * 0.03
                                                + Math.abs(var46) * 0.03;
                                             if (!var25) {
                                                var54 += 30.0;
                                             }

                                             if (var25 && !var18 || var25 == var18 && var54 < var19) {
                                                var18 = var25;
                                                var19 = var54;
                                                var17 = new ClutchAssist$1(var30, var31, var43, var44[0], var44[1], true, false);
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
            }
         }
      }

      if (var17 != null && var16 > 0.0F) {
         float var56 = var17.usiU + (float)(Math.random() * 2.0 - 1.0) * var16;
         float var57 = MathHelper.clamp_float(var17.targetPitch + (float)(Math.random() * 2.0 - 1.0) * var16, -90.0F, 90.0F);
         MovingObjectPosition var58 = RotationUtils.traceBlockHit(this.getClutchReach(), var56, var57);
         if (var58 != null && var58.getBlockPos().equals(var17.vj4) && var58.sideHit == var17.enumFacing) {
            var17 = new ClutchAssist$1(var17.vj4, var17.enumFacing, var58.hitVec, var56, var57, true, var17.ladderClutch);
         }
      }

      return var17;
   }

   private ClutchAssist$1 hS87(float var1, float var2) {
      if (this.findLadderSlot() == -1) {
         return null;
      } else {
         ClutchAssist$1 var3 = null;
         double var4 = Double.MAX_VALUE;
         double var6 = mc.thePlayer.posY;
         double var8 = mc.thePlayer.motionY;

         for (int var10 = 1; var10 <= 12; var10++) {
            double var11 = mc.thePlayer.posX + mc.thePlayer.motionX * var10;
            double var13 = mc.thePlayer.posZ + mc.thePlayer.motionZ * var10;
            var6 += var8;
            var8 = (var8 - 0.08) * 0.98;
            BlockPos var15 = new BlockPos(MathHelper.floor_double(var11), MathHelper.floor_double(var6 + 0.2), MathHelper.floor_double(var13));
            if (this.isReplaceableAt(var15) && var15.getY() < MathHelper.floor_double(mc.thePlayer.posY + mc.thePlayer.getEyeHeight())) {
               for (EnumFacing var19 : HORIZONTAL_FACINGS) {
                  BlockPos var20 = var15.offset(var19.getOpposite());
                  if (this.isSolidSupportAt(var20)) {
                     for (double var24 : RuM) {
                        for (double var29 : RuM) {
                           Vec3 var31 = this.getPointOnFace(var20, var19, var24, var29);
                           float[] var32 = this.computeAnglesToPoint(var31, var1, var2);
                           if (var32 != null) {
                              MovingObjectPosition var33 = RotationUtils.traceBlockHit(this.getClutchReach(), var32[0], var32[1]);
                              if (var33 != null && var33.getBlockPos().equals(var20) && var33.sideHit == var19) {
                                 double var34 = var15.getX() + 0.5 - var11;
                                 double var36 = var15.getZ() + 0.5 - var13;
                                 double var38 = var10 * 2.0
                                    + Math.sqrt(var34 * var34 + var36 * var36)
                                    + Math.abs(MathHelper.wrapAngleTo180_float(var32[0] - var1)) * 0.03;
                                 if (var38 < var4) {
                                    var4 = var38;
                                    var3 = new ClutchAssist$1(var20, var19, var31, var32[0], var32[1], true, true);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return var3;
      }
   }

   private ClutchAssist$1 limitRotationStep(ClutchAssist$1 var1, float var2, float var3) {
      float var4 = MathHelper.wrapAngleTo180_float(var1.usiU - var2);
      float var5 = var1.targetPitch - var3;
      float var6 = MathHelper.sqrt_float(var4 * var4 + var5 * var5);
      float var7 = (float)this.maxRotationSpeed.getInput();
      if (!(var6 <= var7) && !(var6 < 0.001F)) {
         float var8 = var7 / var6;
         float var9 = var2 + var4 * var8;
         float var10 = MathHelper.clamp_float(var3 + var5 * var8, -90.0F, 90.0F);
         MovingObjectPosition var11 = RotationUtils.traceBlockHit(this.getClutchReach(), var9, var10);
         boolean var12 = var11 != null && var11.getBlockPos().equals(var1.vj4) && var11.sideHit == var1.enumFacing;
         Vec3 var13 = var12 ? var11.hitVec : var1.vec3;
         return new ClutchAssist$1(var1.vj4, var1.enumFacing, var13, var9, var10, var12, var1.ladderClutch);
      } else {
         return var1;
      }
   }

   private boolean placeBlockAt(BlockPos var1, EnumFacing var2, Vec3 var3) {
      ItemStack var4 = mc.thePlayer.getHeldItem();
      if (!this.isPlaceableBlockStack(var4)) {
         return false;
      } else {
         boolean var5 = mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, var4, var1, var2, var3);
         if (var5) {
            mc.thePlayer.swingItem();
         }

         return var5;
      }
   }

   private boolean isActivelyClutching() {
      return this.Wt9 || this.YTbl != null;
   }

   private boolean isItemUseBlocked() {
      this.updateItemUseCooldown();
      return mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking() || this.XmrX > 0;
   }

   private void updateItemUseCooldown() {
      if (mc.thePlayer != null) {
         int var1 = mc.thePlayer.ticksExisted;
         if (this.akbwsn != var1) {
            this.akbwsn = var1;
            if (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) {
               this.XmrX = 3;
            } else if (this.XmrX > 0) {
               this.XmrX--;
            }
         }
      }
   }

   private boolean TaAt() {
      if (mc.playerController == null) {
         return false;
      } else {
         IAccessorPlayerControllerMP var1 = (IAccessorPlayerControllerMP)mc.playerController;
         return ClientUtils.xusXfhC(mc.gameSettings.keyBindAttack) || var1.getIsHittingBlock() || var1.getCurBlockDamageMP() > 0.0F;
      }
   }

   private boolean mpiTw(BlockPos var1) {
      return mc.theWorld.getBlockState(var1).getBlock().getMaterial() == Material.air;
   }

   private boolean isReplaceableAt(BlockPos var1) {
      Block var2 = BlockUtils.iepjdt(var1);
      return var2 != null && !(var2 instanceof BlockLiquid) && (this.mpiTw(var1) || BlockUtils.isReplaceableAt(var1));
   }

   private boolean isPlaceableBlockStack(ItemStack var1) {
      if (var1 != null && var1.getItem() instanceof ItemBlock) {
         ItemBlock var2 = (ItemBlock)var1.getItem();
         return var2.getBlock() == Blocks.ladder || ClientUtils.isPassableBlock(var2);
      } else {
         return false;
      }
   }

   private boolean KINXj(BlockPos var1) {
      AxisAlignedBB var2 = mc.thePlayer.getEntityBoundingBox();
      if (var1.getY() >= MathHelper.floor_double(var2.minY)) {
         return true;
      } else {
         AxisAlignedBB var3 = new AxisAlignedBB(var1.getX(), var1.getY(), var1.getZ(), var1.getX() + 1.0, var1.getY() + 1.0, var1.getZ() + 1.0);
         return this.boundsIntersect(var2, var3);
      }
   }

   private boolean boundsIntersect(AxisAlignedBB var1, AxisAlignedBB var2) {
      return var1.maxX > var2.minX && var1.minX < var2.maxX && var1.maxY > var2.minY && var1.minY < var2.maxY && var1.maxZ > var2.minZ && var1.minZ < var2.maxZ;
   }

   private boolean isSolidSupportAt(BlockPos var1) {
      Block var2 = BlockUtils.iepjdt(var1);
      return !BlockUtils.isReplaceableAt(var1) && !BlockUtils.isInteractiveBlock(var2) && !(var2 instanceof BlockLiquid);
   }

   private boolean isSidePlacementValid(BlockPos var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.UP || var1.getY() == var2.getY();
   }

   private boolean CTNt(BlockPos var1) {
      int var2 = this.predictTicksUntilHeight(var1.getY() + 1.0);
      if (var2 < 0) {
         return false;
      } else {
         double var3 = mc.thePlayer.posX + mc.thePlayer.motionX * var2;
         double var5 = mc.thePlayer.posZ + mc.thePlayer.motionZ * var2;
         return Math.abs(var3 - (var1.getX() + 0.5)) <= 1.45 && Math.abs(var5 - (var1.getZ() + 0.5)) <= 1.45;
      }
   }

   private boolean Cdqi() {
      double var1 = mc.thePlayer.posY;
      double var3 = mc.thePlayer.motionY;

      for (int var5 = 1; var5 <= 8; var5++) {
         double var6 = mc.thePlayer.posX + mc.thePlayer.motionX * var5;
         double var8 = mc.thePlayer.posZ + mc.thePlayer.motionZ * var5;
         var1 += var3;
         var3 = (var3 - 0.08) * 0.98;
         BlockPos var10 = new BlockPos(MathHelper.floor_double(var6), MathHelper.floor_double(var1) - 1, MathHelper.floor_double(var8));
         if (this.isLandingBlockSolid(var10)) {
            return true;
         }
      }

      return false;
   }

   private boolean isLandingBlockSolid(BlockPos var1) {
      if (this.blockPos != null && this.blockPos.equals(var1)) {
         return true;
      } else {
         Block var2 = BlockUtils.iepjdt(var1);
         return var2 != null && var2.getMaterial() != Material.air && !(var2 instanceof BlockLiquid) && !BlockUtils.isReplaceableAt(var1);
      }
   }

   private boolean isTrajectoryNearBlockCenter(BlockPos var1) {
      int var2 = this.predictTicksUntilHeight(var1.getY() + 1.7);
      if (var2 < 0) {
         var2 = 4;
      }

      double var3 = mc.thePlayer.posX + mc.thePlayer.motionX * var2;
      double var5 = mc.thePlayer.posZ + mc.thePlayer.motionZ * var2;
      return Math.abs(var3 - (var1.getX() + 0.5)) <= 0.95 && Math.abs(var5 - (var1.getZ() + 0.5)) <= 0.95;
   }

   private int predictTicksUntilHeight(double var1) {
      double var3 = mc.thePlayer.posY;
      double var5 = mc.thePlayer.motionY;

      for (int var7 = 1; var7 <= 12; var7++) {
         var3 += var5;
         var5 = (var5 - 0.08) * 0.98;
         if (var3 <= var1 + 0.05) {
            return var7;
         }
      }

      return -1;
   }

   private boolean hasPlaceableBlock() {
      if (this.swapSlot.isToggled() && this.findBlockSlot() != -1) {
         return true;
      } else {
         ItemStack var1 = mc.thePlayer.getHeldItem();
         return var1 != null && var1.getItem() instanceof ItemBlock && ClientUtils.isPassableBlock((ItemBlock)var1.getItem());
      }
   }

   private boolean ANhh() {
      int var1 = this.findBlockSlot();
      if (var1 == -1) {
         return false;
      } else {
         if (this.rememberedBlockSlot == -1) {
            this.rememberedBlockSlot = var1;
         }

         if (this.previousHotbarSlot.get() == -1) {
            this.previousHotbarSlot.set(mc.thePlayer.inventory.currentItem);
         }

         if (this.swapSlot.isToggled()) {
            this.setHotbarSlot(var1);
         }

         ItemStack var2 = mc.thePlayer.getHeldItem();
         if (var2 != null && var2.getItem() instanceof ItemBlock && ClientUtils.isPassableBlock((ItemBlock)var2.getItem())) {
            return true;
         } else {
            this.rememberedBlockSlot = -1;
            return false;
         }
      }
   }

   private boolean equipLadder() {
      int var1 = this.findLadderSlot();
      if (var1 == -1) {
         return false;
      } else {
         if (this.previousHotbarSlot.get() == -1) {
            this.previousHotbarSlot.set(mc.thePlayer.inventory.currentItem);
         }

         this.setHotbarSlot(var1);
         ItemStack var2 = mc.thePlayer.getHeldItem();
         return var2 != null && var2.getItem() instanceof ItemBlock && ((ItemBlock)var2.getItem()).getBlock() == Blocks.ladder;
      }
   }

   private int findBlockSlot() {
      int var1 = -1;
      int var2 = -1;

      for (int var3 = 0; var3 < 9; var3++) {
         ItemStack var4 = mc.thePlayer.inventory.mainInventory[var3];
         if (var4 != null && var4.getItem() instanceof ItemBlock && ClientUtils.isPassableBlock((ItemBlock)var4.getItem()) && var4.stackSize > var2) {
            var2 = var4.stackSize;
            var1 = var3;
         }
      }

      return var1;
   }

   private int findLadderSlot() {
      int var1 = -1;
      int var2 = -1;

      for (int var3 = 0; var3 < 9; var3++) {
         ItemStack var4 = mc.thePlayer.inventory.mainInventory[var3];
         if (var4 != null && var4.getItem() instanceof ItemBlock && ((ItemBlock)var4.getItem()).getBlock() == Blocks.ladder && var4.stackSize > var2) {
            var2 = var4.stackSize;
            var1 = var3;
         }
      }

      return var1;
   }

   private void setHotbarSlot(int var1) {
      if (var1 >= 0 && var1 <= 8 && mc.thePlayer.inventory.currentItem != var1) {
         mc.thePlayer.inventory.currentItem = var1;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private double getClutchReach() {
      return Math.min(4.5, (double)mc.playerController.getBlockReachDistance());
   }

   private boolean isWithinReach(Vec3 var1, double var2) {
      return var1 != null && mc.thePlayer.getPositionEyes(1.0F).distanceTo(var1) <= var2 + 0.01;
   }

   private boolean QUaF(ClutchAssist$1 var1, MovingObjectPosition var2) {
      return var1 != null && var2 != null && var2.typeOfHit == MovingObjectType.BLOCK && var1.vj4.equals(var2.getBlockPos()) && var1.enumFacing == var2.sideHit;
   }

   private void stopMining() {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
      IAccessorPlayerControllerMP var1 = (IAccessorPlayerControllerMP)mc.playerController;
      var1.setIsHittingBlock(false);
      var1.setCurBlockDamageMP(0.0F);
      var1.setBlockHitDelay(0);
   }

   private float[] computeAnglesToPoint(Vec3 var1, float var2, float var3) {
      Vec3 var4 = mc.thePlayer.getPositionEyes(1.0F);
      double var5 = var1.xCoord - var4.xCoord;
      double var7 = var1.yCoord - var4.yCoord;
      double var9 = var1.zCoord - var4.zCoord;
      if (var5 * var5 + var7 * var7 + var9 * var9 > this.getClutchReach() * this.getClutchReach()) {
         return null;
      } else {
         double var11 = var5 * var5 + var9 * var9;
         float var13 = var11 < 1.0E-8
            ? var2
            : var2 + MathHelper.wrapAngleTo180_float((float)(Math.atan2(var9, var5) * (float) (180.0 / Math.PI)) - 90.0F - var2);
         float var14 = MathHelper.clamp_float((float)(-(Math.atan2(var7, Math.sqrt(var11)) * (float) (180.0 / Math.PI))), -90.0F, 90.0F);
         return RotationUtils.NSsr(var13, var14, var2, var3);
      }
   }

    private Vec3 getPointOnFace(BlockPos blockPos, EnumFacing enumFacing, double d, double d2) {
        double d3 = (double)blockPos.getX() + 0.5 + (double)enumFacing.getFrontOffsetX() * 0.5;
        double d4 = (double)blockPos.getY() + 0.5 + (double)enumFacing.getFrontOffsetY() * 0.5;
        double d5 = (double)blockPos.getZ() + 0.5 + (double)enumFacing.getFrontOffsetZ() * 0.5;
        switch (enumFacing) {
            case UP: 
            case DOWN: {
                d3 = (double)blockPos.getX() + d;
                d5 = (double)blockPos.getZ() + d2;
                break;
            }
            case EAST: 
            case WEST: {
                d4 = (double)blockPos.getY() + d;
                d5 = (double)blockPos.getZ() + d2;
                break;
            }
            case NORTH: 
            case SOUTH: {
                d3 = (double)blockPos.getX() + d;
                d4 = (double)blockPos.getY() + d2;
                break;
            }
        }
        return new Vec3(d3, d4, d5);
    }

   public int countPlaceableBlocks() {
      int var1 = 0;

      for (int var2 = 0; var2 < 9; var2++) {
         ItemStack var3 = mc.thePlayer.inventory.mainInventory[var2];
         if (var3 != null && var3.getItem() instanceof ItemBlock && ClientUtils.isPassableBlock((ItemBlock)var3.getItem())) {
            var1 += var3.stackSize;
         }
      }

      return var1;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Action", "Delay", new String[]{"ladder", "swap slot"}, new String[]{"Use ladder", "Swap slot"}),
         buildSettingAlias(
            "Conditions",
            "Action",
            new String[]{"holding blocks", "damaged", "in air", "holding right click"},
            new String[]{"Holding blocks", "Damaged", "In air", "Right mouse held"}
         )
      );
   }

   static {
      double[] var10000 = new double[3];
      var10000[0] = 0.5;
      var10000[1] = 0.35;
      var10000[2] = 0.65;
      RuM = var10000;
      EnumFacing[] var0 = new EnumFacing[]{EnumFacing.NORTH, null, null, null};
      var0[1] = EnumFacing.SOUTH;
      var0[2] = EnumFacing.WEST;
      var0[3] = EnumFacing.EAST;
      HORIZONTAL_FACINGS = var0;
   }
}
