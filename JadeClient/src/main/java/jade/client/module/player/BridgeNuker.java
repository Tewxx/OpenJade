// Jade recovery: module: Bridge Nuker (player); original class: jade.deps.eLz.SzhwSe
package jade.client.module.player;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.MiddleClickFriend;
import jade.client.common.RotationUtils;
import jade.client.common.PointedObjectOverrider;
import jade.client.common.Subscribe;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.other.AntiBot;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorEntityRenderer;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class BridgeNuker extends Module implements PointedObjectOverrider {
   private final SliderSetting breakSpeed;
   private final SliderSetting breakDelay;
   private final SliderSetting range;
   private final BooleanSetting targetTeammates;
   private final BooleanSetting highlightBlock;
   private final ColorSetting highlightColor;
   private BlockPos blockPos;
   private Vec3 vec3;
   private EnumFacing enumFacing;
   private boolean breakingBlock;
   private int Sp8 = -1;
   private boolean DeK1;
   private int breakDelayTicks;
   private final List<BlockPos> blockPoses = new ArrayList<>();

   public BridgeNuker() {
      super("Bridge Nuker", Category.player);
      this.registerSetting(
         this.breakSpeed = new SliderSetting(
            "Break speed", "x", 1.0, 1.0, 5.0, 0.05
         )
      );
      this.registerSetting(
         this.breakDelay = new SliderSetting(
            "Break delay", "ms", 250.0, 0.0, 250.0, 50.0
         )
      );
      this.registerSetting(
         this.range = new SliderSetting(
            "Range", " blocks", 4.5, 2.0, 6.0, 0.1
         )
      );
      this.registerSetting(
         this.targetTeammates = new BooleanSetting(
            "Target teammates", false
         )
      );
      this.registerSetting(
         this.highlightBlock = new BooleanSetting(
            "Highlight block", true
         )
      );
      this.registerSetting(
         this.highlightColor = new ColorSetting(
            "Highlight color",
            255,
            64,
            64,
            229
         )
      );
   }

   @Override
   public void guiUpdate() {
      this.highlightColor.setVisible(this.highlightBlock.isToggled(), this);
   }

   @Override
   public void pollKeybind() {
      int var1 = this.getKeycode();
      if (var1 != 0) {
         try {
            boolean var2 = var1 >= 1000 ? (var1 != 1069 && var1 != 1070 ? Mouse.isButtonDown(var1 - 1000) : MiddleClickFriend.isScrollKeyPressed(var1)) : Keyboard.isKeyDown(var1);
            if (var2 && !this.isEnabled()) {
               this.enable();
            } else if (!var2 && this.isEnabled()) {
               this.disable();
            }
         } catch (Exception var3) {
         }
      }
   }

   @Override
   public void onDisable() {
      this.stopBreaking();
      this.blockPoses.clear();
      this.breakDelayTicks = 0;
   }

   public float getBreakSpeedMultiplier() {
      float var1 = (float)this.breakSpeed.getInput();
      return var1 > 1.0F ? var1 : 1.0F;
   }

   public int getBreakDelayTicks() {
      return Math.max(0, Math.min(5, (int)(this.breakDelay.getInput() / 50.0)));
   }

   public boolean PfjH() {
      return this.isActivelyBreaking();
   }

   public boolean isActivelyBreaking() {
      return this.breakingBlock && this.isEnabled() && ClientUtils.isInWorld() && mc.currentScreen == null && this.canBreakBlocks();
   }

   @Override
   public boolean shouldOverridePointedObject() {
      return this.isEnabled() && this.breakingBlock && this.canBreakBlocks() && this.blockPos != null && this.vec3 != null && this.enumFacing != null && ClientUtils.isInWorld();
   }

   private boolean canBreakBlocks() {
      return mc.thePlayer.capabilities.allowEdit && !mc.thePlayer.capabilities.isCreativeMode && !mc.thePlayer.isSpectator();
   }

   @Override
   public void applyPointedObjectOverride(float var1) {
      if (this.shouldOverridePointedObject()) {
         if (mc.getRenderViewEntity() != null) {
            MovingObjectPosition var2 = new MovingObjectPosition(this.vec3, this.enumFacing, this.blockPos);
            mc.objectMouseOver = var2;
            mc.pointedEntity = null;
            EntityRenderer var3 = mc.entityRenderer;
            if (var3 instanceof IAccessorEntityRenderer) {
               ((IAccessorEntityRenderer)var3).setPointedEntity(null);
            }
         }
      }
   }

   public void ehIu() {
      if (!this.canBreakBlocks()) {
         if (this.breakingBlock) {
            this.stopBreaking();
         }
      } else if (this.isActivelyBreaking()) {
         int var1 = mc.gameSettings.keyBindAttack.getKeyCode();
         int var2 = mc.gameSettings.keyBindUseItem.getKeyCode();
         KeyBinding.setKeyBindState(var1, false);
         KeyBinding.setKeyBindState(var2, false);
         KeyBinding.setKeyBindState(var1, true);
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      this.ehIu();
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onRotation(RotationEvent var1) {
      if (!this.isEnabled() || !ClientUtils.isInWorld() || mc.currentScreen != null || !this.canBreakBlocks()) {
         this.stopBreaking();
      } else if (var1.suppressRotations) {
         this.stopBreaking();
      } else {
         double var2 = this.range.getInput();
         if (--this.breakDelayTicks <= 0) {
            this.breakDelayTicks = 1;
            this.collectTargetBlocks();
         }

         if (this.blockPoses.isEmpty()) {
            this.stopBreaking();
         } else {
            int var4 = this.fVluDb();
            if (var4 == -1) {
               this.stopBreaking();
            } else {
               BlockPos var5 = this.findTargetBlock(var2 * var2);
               if (var5 == null) {
                  this.stopBreaking();
               } else {
                  AxisAlignedBB var6 = BlockUtils.getSelectedBounds(var5);
                  if (var6 == null) {
                     this.stopBreaking();
                  } else {
                     Vec3 var7 = mc.thePlayer.getPositionEyes(1.0F);
                     Vec3 var8 = RotationUtils.ONLLQYf(var6, var7);
                     EnumFacing var9 = BlockUtils.getFacingTowardPoint(var5, var8);
                     Block var10 = BlockUtils.iepjdt(var5);
                     if (var10 != null) {
                        MovingObjectPosition var11 = var10.collisionRayTrace(
                           mc.theWorld,
                           var5,
                           var7,
                           var8.addVector((var8.xCoord - var7.xCoord) * 0.01, (var8.yCoord - var7.yCoord) * 0.01, (var8.zCoord - var7.zCoord) * 0.01)
                        );
                        if (var11 != null && var11.hitVec != null && var11.sideHit != null && var5.equals(var11.getBlockPos())) {
                           var8 = var11.hitVec;
                           var9 = var11.sideHit;
                        }
                     }

                     this.blockPos = var5;
                     this.vec3 = var8;
                     this.enumFacing = var9;
                     this.breakingBlock = true;
                     this.switchToHotbarSlot(var4);
                     float var14 = var1.MGzP2 != null ? var1.MGzP2 : RotationUtils.lastSentRotation[0];
                     float var12 = var1.pitch != null ? var1.pitch : RotationUtils.lastSentRotation[1];
                     float[] var13 = RotationUtils.anglesToCoordinates(this.vec3.xCoord, this.vec3.yCoord, this.vec3.zCoord, var14, var12);
                     var1.setRotation(var13[0], var13[1], 45);
                  }
               }
            }
         }
      }
   }

   private void collectTargetBlocks() {
      this.blockPoses.clear();

      for (EntityPlayer var2 : mc.theWorld.playerEntities) {
         if (var2 != mc.thePlayer
            && !var2.isDead
            && var2.deathTime == 0
            && !AntiBot.shouldHideEntity(var2)
            && (this.targetTeammates.isToggled() || !ClientUtils.isTeammate(var2))
            && !ClientUtils.isFriend(var2)) {
            BlockPos var3 = new BlockPos(var2);
            int var4 = var3.getX();
            int var5 = var3.getZ();

            for (int var6 = var3.getY() - 1; var6 >= 0; var6--) {
               BlockPos var7 = new BlockPos(var4, var6, var5);
               Block var8 = mc.theWorld.getBlockState(var7).getBlock();
               if (var8 == Blocks.wool) {
                  if (!this.blockPoses.contains(var7)) {
                     this.blockPoses.add(var7);
                  }
               } else if (var8 != Blocks.air) {
                  break;
               }
            }
         }
      }
   }

   private BlockPos findTargetBlock(double var1) {
      Vec3 var3 = mc.thePlayer.getPositionEyes(1.0F);
      BlockPos var4 = null;
      int var5 = Integer.MAX_VALUE;
      IAccessorPlayerControllerMP var6 = (IAccessorPlayerControllerMP)mc.playerController;
      BlockPos var7 = var6.getCurrentBlock();
      Iterator var8 = this.blockPoses.iterator();

      while (var8.hasNext()) {
         BlockPos var9 = (BlockPos)var8.next();
         if (mc.theWorld.getBlockState(var9).getBlock() != Blocks.wool) {
            var8.remove();
         } else {
            AxisAlignedBB var10 = BlockUtils.getSelectedBounds(var9);
            if (var10 == null) {
               var8.remove();
            } else {
               Vec3 var11 = RotationUtils.ONLLQYf(var10, var3);
               double var12 = var3.squareDistanceTo(var11);
               if (!(var12 > var1)) {
                  if (var7 != null && var7.equals(var9)) {
                     return var9;
                  }

                  if (var9.getY() < var5) {
                     var5 = var9.getY();
                     var4 = var9;
                  }
               }
            }
         }
      }

      return var4;
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.isEnabled() && this.highlightBlock.isToggled() && this.breakingBlock && this.blockPos != null && ClientUtils.isInWorld() && this.canBreakBlocks()) {
         IBlockState var2 = mc.theWorld.getBlockState(this.blockPos);
         Block var3 = var2.getBlock();
         if (var3 == null || var3 == Blocks.air) {
            ;
         }
      }
   }

   private int fVluDb() {
      for (int var1 = 0; var1 < 9; var1++) {
         ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
         if (var2 != null && var2.getItem() == Items.shears) {
            return var1;
         }
      }

      return -1;
   }

   private void switchToHotbarSlot(int var1) {
      if (this.Sp8 == -1 && var1 != mc.thePlayer.inventory.currentItem) {
         this.Sp8 = mc.thePlayer.inventory.currentItem;
      }

      if (var1 != mc.thePlayer.inventory.currentItem) {
         mc.thePlayer.inventory.currentItem = var1;
         this.DeK1 = true;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private void stopBreaking() {
      boolean var1 = this.breakingBlock;
      this.breakingBlock = false;
      if (var1 && this.Sp8 != -1 && ClientUtils.isInWorld() && mc.playerController != null) {
         mc.thePlayer.inventory.currentItem = this.Sp8;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }

      if (ClientUtils.isInWorld()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), Mouse.isButtonDown(0));
      }

      this.blockPos = null;
      this.vec3 = null;
      this.enumFacing = null;
      this.DeK1 = false;
      this.Sp8 = -1;
   }
}
