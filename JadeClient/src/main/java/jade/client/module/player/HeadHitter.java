// Jade recovery: module: Head Hitter (player); original class: jade.deps.eLz.jOKJzW
package jade.client.module.player;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.MoveInputEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo
public class HeadHitter extends Module {
   private static final EnumFacing[] Mie;
   private static final double REACH_DISTANCE = 4.5;
   private static final long TARGET_TIMEOUT_MS = 500L;
   private final KeySetting key;
   private final BooleanSetting extend;
   private final SliderSetting speed;
   private final SliderSetting distance;
   private List<BlockPos> blockPoses;
   private BlockPos currentTarget;
   private long zzr;
   private BlockPos approachPos;
   private float nox;
   private float forwardX;
   private float WvA;
   private int stepX;
   private int uvn;
   private int groundY;
   private BlockPos placementPos;
   private EnumFacing placementFacing;
   private Vec3 vec3;
   private boolean placementReady;
   private boolean qHquTd;
   private boolean hotbarSlotChanged;
   private int RFEkv = -1;
   private HeadHitter$2 EUnHs = HeadHitter$2.IDLE;

   public HeadHitter() {
      super("Head Hitter", Category.player);
      this.registerSetting(
         this.key = new KeySetting("Key", 0)
      );
      this.registerSetting(
         this.extend = new BooleanSetting(
            "Extend", false
         )
      );
      this.registerSetting(this.speed = new SliderSetting("Speed", 15.0, 1.0, 30.0, 1.0));
      this.registerSetting(this.distance = new SliderSetting("Distance", 4.0, 2.0, 8.0, 1.0));
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      if (!ClientUtils.isInWorld()) {
         this.disable();
      } else {
         this.EUnHs = HeadHitter$2.IDLE;
         this.qHquTd = this.key.isHeldDown();
         this.clearAimTarget();
         this.blockPoses = null;
         this.currentTarget = null;
         this.approachPos = null;
         this.hotbarSlotChanged = false;
         this.RFEkv = -1;
      }
   }

   @Override
   public void onDisable() {
      this.resetToIdle();
      this.qHquTd = false;
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent var1) {
      if (ClientUtils.isInWorld()) {
         if ((this.EUnHs == HeadHitter$2.HEAD_HITTER || this.EUnHs == HeadHitter$2.EXTEND_GROUND) && mc.thePlayer.onGround) {
            var1.setJumping(true);
         }
      }
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      if (ClientUtils.isInWorld() && mc.currentScreen == null) {
         switch (this.EUnHs) {
            case HEAD_HITTER:
               this.tickHeadHitter(var1);
               break;
            case EXTEND_APPROACH:
               this.tickApproach();
               break;
            case EXTEND_GROUND:
               this.MhYq(var1);
               break;
            case EXTEND_AIR:
               this.OORQ(var1);
         }
      }
   }

   private void tickHeadHitter(RotationEvent var1) {
      if (this.blockPoses != null) {
         this.blockPoses.removeIf(HeadHitter::isPositionFilled);
         if (!this.blockPoses.isEmpty()) {
            this.clearAimTarget();
            if (!this.ensureSolidBlockHeld()) {
               this.resetToIdle();
            } else {
               ItemStack var2 = mc.thePlayer.getHeldItem();
               BlockPos var3 = this.blockPoses.get(0);
               if (!var3.equals(this.currentTarget)) {
                  this.currentTarget = var3;
                  this.zzr = System.currentTimeMillis();
               }

               HeadHitter$1 var4 = this.swXu(var3, var2);
               if (var4 == null) {
                  if (!this.canPlaceAgainstAnySide(var3, var2)) {
                     this.blockPoses.remove(0);
                     this.currentTarget = null;
                  }
               } else if (System.currentTimeMillis() - this.zzr > 500L) {
                  this.blockPoses.remove(0);
                  this.currentTarget = null;
               } else {
                  float var5 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
                  float var6 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
                  float[] var7 = RotationUtils.CUcKq(var4.blockPos, var4.enumFacing);
                  float[] var8 = RotationUtils.smoothAngles(var5, var6, var7[0], var7[1], (int)this.speed.getInput());
                  MovingObjectPosition var9 = RotationUtils.traceBlockHit(4.5, var8[0], var8[1]);
                  if (var9 != null && var9.getBlockPos().equals(var4.blockPos) && var9.sideHit == var4.enumFacing) {
                     this.placementPos = var9.getBlockPos();
                     this.placementFacing = var9.sideHit;
                     this.vec3 = var9.hitVec;
                     this.placementReady = true;
                  }

                  var1.setRotation(var8[0], var8[1], 45);
               }
            }
         } else {
            if (this.key.isHeldDown() && mc.currentScreen == null && this.extend.isToggled()) {
               this.beginExtend();
            } else {
               this.resetToIdle();
            }

            this.clearAimTarget();
         }
      }
   }

   private void tickApproach() {
      if (this.approachPos == null) {
         this.selectExtendState();
      } else {
         float var1 = (this.approachPos.getX() + 0.5F - (float)mc.thePlayer.posX) * this.forwardX + (this.approachPos.getZ() + 0.5F - (float)mc.thePlayer.posZ) * this.WvA;
         if (var1 <= -1.5F) {
            this.selectExtendState();
         }
      }
   }

   private void selectExtendState() {
      this.EUnHs = mc.thePlayer.onGround ? HeadHitter$2.EXTEND_GROUND : HeadHitter$2.EXTEND_AIR;
   }

   private void MhYq(RotationEvent var1) {
      float var2 = var1.MGzP2 != null ? var1.MGzP2 : mc.thePlayer.rotationYaw;
      this.nox = var2;
      float var3 = (float)Math.toRadians(var2);
      this.forwardX = -MathHelper.sin(var3);
      this.WvA = MathHelper.cos(var3);
      this.stepX = Math.round(this.forwardX);
      this.uvn = Math.round(this.WvA);
      int var4 = MathHelper.floor_double(mc.thePlayer.posX);
      int var5 = MathHelper.floor_double(mc.thePlayer.posZ);
      int var6 = MathHelper.floor_double(mc.thePlayer.posY);

      while (var6 > 0 && BlockUtils.isReplaceableAt(new BlockPos(var4, var6 - 1, var5))) {
         var6--;
      }

      this.groundY = var6;
      if (!mc.thePlayer.onGround) {
         this.EUnHs = HeadHitter$2.EXTEND_AIR;
         this.clearAimTarget();
      }
   }

   private void OORQ(RotationEvent var1) {
      if (mc.thePlayer.onGround) {
         this.EUnHs = HeadHitter$2.EXTEND_GROUND;
         this.clearAimTarget();
         this.MhYq(var1);
      } else {
         float var2 = this.nox + 180.0F;
         float var3 = 5.0F;
         int var4 = MathHelper.floor_double(mc.thePlayer.posX);
         int var5 = MathHelper.floor_double(mc.thePlayer.posZ);
         this.clearAimTarget();
         if (!this.ensureSolidBlockHeld()) {
            this.resetToIdle();
         } else {
            ItemStack var6 = mc.thePlayer.getHeldItem();

            for (int var7 = 0; var7 <= 4; var7++) {
               int var8 = var4 - this.stepX * var7;
               int var9 = var5 - this.uvn * var7;
               BlockPos var10 = new BlockPos(var8, this.groundY + 2, var9);
               if (BlockUtils.isReplaceableAt(var10)) {
                  HeadHitter$1 var11 = this.swXu(var10, var6);
                  if (var11 != null) {
                     float[] var12 = RotationUtils.CUcKq(var11.blockPos, var11.enumFacing);
                     var2 = var12[0];
                     var3 = var12[1];
                     float var13 = Math.abs(MathHelper.wrapAngleTo180_float(var2 - RotationUtils.lastSentRotation[0]));
                     if (var13 < 20.0F) {
                        MovingObjectPosition var14 = RotationUtils.traceBlockHit(4.5, var2, var3);
                        if (var14 != null && var14.getBlockPos().equals(var11.blockPos) && var14.sideHit == var11.enumFacing) {
                           this.placementPos = var14.getBlockPos();
                           this.placementFacing = var14.sideHit;
                           this.vec3 = var14.hitVec;
                           this.placementReady = true;
                        }
                     }
                     break;
                  }
               }
            }

            var2 += (ClientUtils.getRandom().nextFloat() - 0.5F) * 2.0F;
            var3 += (ClientUtils.getRandom().nextFloat() - 0.5F) * 1.0F;
            var1.setRotation(var2, var3, 45);
         }
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (ClientUtils.isInWorld()) {
         boolean var2 = mc.currentScreen == null && this.key.isHeldDown();
         boolean var3 = var2 && !this.qHquTd;
         boolean var4 = !var2 && this.qHquTd;
         this.qHquTd = var2;
         if (var3 && this.EUnHs == HeadHitter$2.IDLE) {
            this.blockPoses = this.buildHeadHitterTargets();
            this.currentTarget = null;
            this.clearAimTarget();
            this.EUnHs = HeadHitter$2.HEAD_HITTER;
         }

         if (var4 && this.isExtendState(this.EUnHs)) {
            this.resetToIdle();
         }

         if (this.EUnHs != HeadHitter$2.IDLE && this.EUnHs != HeadHitter$2.EXTEND_APPROACH && mc.currentScreen == null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
         }

         if (this.placementReady) {
            this.placementReady = false;
            ItemStack var5 = mc.thePlayer.getHeldItem();
            if (!this.isSolidBlockItem(var5)) {
               this.resetToIdle();
            } else {
               if (this.placementPos != null
                  && this.placementFacing != null
                  && this.vec3 != null
                  && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, var5, this.placementPos, this.placementFacing, this.vec3)) {
                  mc.thePlayer.swingItem();
                  if (this.EUnHs == HeadHitter$2.HEAD_HITTER && this.blockPoses != null) {
                     BlockPos var6 = this.placementPos.offset(this.placementFacing);
                     this.blockPoses.remove(var6);
                     if (this.currentTarget != null && this.currentTarget.equals(var6)) {
                        this.currentTarget = null;
                     }
                  }
               }

               this.clearAimTarget();
            }
         }
      }
   }

   private boolean isExtendState(HeadHitter$2 var1) {
      return var1 == HeadHitter$2.EXTEND_APPROACH || var1 == HeadHitter$2.EXTEND_GROUND || var1 == HeadHitter$2.EXTEND_AIR;
   }

   private void beginExtend() {
      int var1 = MathHelper.floor_double(mc.thePlayer.posX);
      int var2 = MathHelper.floor_double(mc.thePlayer.posZ);
      int var3 = MathHelper.floor_double(mc.thePlayer.posY);

      while (var3 > 0 && BlockUtils.isReplaceableAt(new BlockPos(var1, var3 - 1, var2))) {
         var3--;
      }

      this.groundY = var3;
      this.clearAimTarget();
      if (this.approachPos != null) {
         float var4 = (this.approachPos.getX() + 0.5F - (float)mc.thePlayer.posX) * this.forwardX + (this.approachPos.getZ() + 0.5F - (float)mc.thePlayer.posZ) * this.WvA;
         if (var4 <= -1.5F) {
            this.selectExtendState();
            return;
         }
      }

      this.EUnHs = HeadHitter$2.EXTEND_APPROACH;
   }

   private List<BlockPos> buildHeadHitterTargets() {
      float var1 = mc.thePlayer.rotationYaw;
      this.nox = var1;
      float var2 = (float)Math.toRadians(var1);
      this.forwardX = -MathHelper.sin(var2);
      this.WvA = MathHelper.cos(var2);
      this.stepX = Math.round(this.forwardX);
      this.uvn = Math.round(this.WvA);
      int var3 = (int)this.distance.getInput();
      float var4 = (float)Math.toRadians(var1 + 90.0F);
      int var5 = -Math.round(MathHelper.sin(var4));
      int var6 = Math.round(MathHelper.cos(var4));
      int var7 = MathHelper.floor_double(mc.thePlayer.posX);
      int var8 = MathHelper.floor_double(mc.thePlayer.posZ);
      int var9 = MathHelper.floor_double(mc.thePlayer.posY);

      while (var9 > 0 && BlockUtils.isReplaceableAt(new BlockPos(var7, var9 - 1, var8))) {
         var9--;
      }

      double var10 = (mc.thePlayer.posX - (var7 + 0.5)) * var5 + (mc.thePlayer.posZ - (var8 + 0.5)) * var6;
      int var12 = var10 > 0.0 ? -1 : 1;
      int var13 = var5 * var12;
      int var14 = var6 * var12;
      int var15 = var7 + this.stepX * var3 + var13;
      int var16 = var8 + this.uvn * var3 + var14;
      ArrayList var17 = new ArrayList();
      var17.add(new BlockPos(var15, var9, var16));
      var17.add(new BlockPos(var15, var9 + 1, var16));
      var17.add(new BlockPos(var15, var9 + 2, var16));
      BlockPos var18 = new BlockPos(var15 - var13, var9 + 2, var16 - var14);
      this.approachPos = var18;
      var17.add(var18);
      return var17;
   }

   private HeadHitter$1 swXu(BlockPos var1, ItemStack var2) {
      if (!BlockUtils.isReplaceableAt(var1)) {
         return null;
      } else {
         Vec3 var3 = mc.thePlayer.getPositionEyes(1.0F);

         for (EnumFacing var7 : Mie) {
            BlockPos var8 = var1.offset(var7.getOpposite());
            if (!BlockUtils.isReplaceableAt(var8) && BlockUtils.canPlaceItemOnSide(var2, var8, var7)) {
               Vec3 var9 = BlockUtils.sdlDajR(var8, var7);
               if (!(var3.distanceTo(var9) > 4.5)) {
                  return new HeadHitter$1(var8, var7);
               }
            }
         }

         return null;
      }
   }

   private boolean canPlaceAgainstAnySide(BlockPos var1, ItemStack var2) {
      if (!BlockUtils.isReplaceableAt(var1)) {
         return false;
      } else {
         for (EnumFacing var6 : Mie) {
            BlockPos var7 = var1.offset(var6.getOpposite());
            if (!BlockUtils.isReplaceableAt(var7) && BlockUtils.canPlaceItemOnSide(var2, var7, var6)) {
               return true;
            }
         }

         return false;
      }
   }

   private void clearAimTarget() {
      this.placementPos = null;
      this.placementFacing = null;
      this.vec3 = null;
      this.placementReady = false;
   }

   private void resetToIdle() {
      this.EUnHs = HeadHitter$2.IDLE;
      this.blockPoses = null;
      this.currentTarget = null;
      this.approachPos = null;
      this.clearAimTarget();
      this.restoreHotbarSlot();
   }

   private boolean ensureSolidBlockHeld() {
      ItemStack var1 = mc.thePlayer.getHeldItem();
      if (this.isSolidBlockItem(var1)) {
         return true;
      } else {
         int var2 = this.DBWRo();
         if (var2 == -1) {
            return false;
         } else {
            if (this.RFEkv == -1) {
               this.RFEkv = mc.thePlayer.inventory.currentItem;
            }

            if (var2 != mc.thePlayer.inventory.currentItem) {
               this.TeXd(var2);
               this.hotbarSlotChanged = true;
            }

            return true;
         }
      }
   }

   private int DBWRo() {
      int var1 = -1;
      int var2 = -1;

      for (int var3 = 8; var3 >= 0; var3--) {
         ItemStack var4 = mc.thePlayer.inventory.getStackInSlot(var3);
         if (this.isSolidBlockItem(var4) && var4.stackSize > var2) {
            var2 = var4.stackSize;
            var1 = var3;
         }
      }

      return var1;
   }

   private boolean isSolidBlockItem(ItemStack var1) {
      return var1 != null && var1.getItem() instanceof ItemBlock && ClientUtils.isPassableBlock((ItemBlock)var1.getItem());
   }

   private void restoreHotbarSlot() {
      if (this.hotbarSlotChanged && this.RFEkv != -1 && this.RFEkv != mc.thePlayer.inventory.currentItem) {
         this.TeXd(this.RFEkv);
      }

      this.hotbarSlotChanged = false;
      this.RFEkv = -1;
   }

   private void TeXd(int var1) {
      if (var1 >= 0 && var1 != mc.thePlayer.inventory.currentItem) {
         mc.thePlayer.inventory.currentItem = var1;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private static boolean isPositionFilled(BlockPos var0) {
      return !BlockUtils.isReplaceableAt(var0);
   }

   static {
      EnumFacing[] var10000 = new EnumFacing[6];
      var10000[0] = EnumFacing.DOWN;
      var10000[1] = EnumFacing.UP;
      var10000[2] = EnumFacing.NORTH;
      var10000[3] = EnumFacing.SOUTH;
      var10000[4] = EnumFacing.EAST;
      var10000[5] = EnumFacing.WEST;
      Mie = var10000;
   }
}
