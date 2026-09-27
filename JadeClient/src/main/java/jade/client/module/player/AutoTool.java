// Jade recovery: module: Auto Tool (player); original class: jade.deps.eLz.sk8uTNP6W
package jade.client.module.player;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.autotool.ToolSlotController;
import jade.client.module.player.autotool.BlockNameMatcher;
import jade.client.module.player.autotool.TimedValueTracker;
import jade.client.module.player.autotool.AutoToolSettings;
import jade.client.setting.BlockListSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class AutoTool extends Module {
   private final AutoToolSettings PbI;
   private final ToolSlotController slotController = new ToolSlotController();
   private final TimedValueTracker<BlockPos> hoverTracker = new TimedValueTracker<>();
   public int previousSlot = -1;

   public AutoTool() {
      super("Auto Tool", Category.player);
      this.PbI = new AutoToolSettings(this);
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      this.PbI.holdDelay.setVisible(this.PbI.requireLeftMouse.isToggled(), this);
      this.PbI.heldItems.setVisible(this.PbI.heldItemBlacklist.isToggled(), this);
      this.PbI.whitelistedBlocks.setVisible(this.PbI.blockWhitelist.isToggled(), this);
      this.PbI.blacklistedBlocks.setVisible(this.PbI.blockBlacklist.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.resetTracking();
   }

   @Override
   public void onDisable() {
      this.resetTracking();
   }

   @Subscribe
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (this.slotController.isActive()) {
         this.previousSlot = this.slotController.nextSlotForScroll(mc.thePlayer.inventory.currentItem, var1.scrollDirection);
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (this.slotController.isActive()) {
         this.previousSlot = var1.slot;
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.resetTracking();
      } else {
         long var2 = System.currentTimeMillis();
         boolean var4 = Mouse.isButtonDown(0);
         this.hoverTracker.updateHoldStart(var4, var2);
         if (mc.inGameHasFocus && mc.currentScreen == null && !mc.thePlayer.isDead && mc.thePlayer.capabilities.allowEdit) {
            MovingObjectPosition var5 = RotationUtils.PUiOcj(
               mc.playerController.getBlockReachDistance(), mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch
            );
            BlockPos var6 = var5 != null && var5.typeOfHit == MovingObjectType.BLOCK ? var5.getBlockPos() : null;
            this.hoverTracker.updateValue(var6, var2);
            if (var6 == null) {
               this.restorePreviousTool();
            } else if (this.PbI.onlyWhileCrouching.isToggled() && !mc.thePlayer.isSneaking()) {
               this.restorePreviousTool();
            } else {
               if (this.PbI.requireLeftMouse.isToggled()) {
                  if (!var4) {
                     this.restorePreviousTool();
                     return;
                  }

                  if (!this.hoverTracker.hasHoldElapsed(this.PbI.holdDelay.getInput(), var2)) {
                     this.restorePreviousTool();
                     return;
                  }
               }

               if (!this.hoverTracker.hasValueElapsed(this.PbI.hoverDelay.getInput(), var2, this.slotController.isActiveAndAttacking(var4))) {
                  this.restorePreviousTool();
               } else if (this.xPo1()) {
                  this.restorePreviousTool();
               } else if (this.zSmtKfo(var6)) {
                  this.restorePreviousTool();
               } else if (this.PbI.blockWhitelist.isToggled() && !this.isBlockWhitelisted(var6)) {
                  this.restorePreviousTool();
               } else {
                  int var7 = ClientUtils.AHvb(BlockUtils.iepjdt(var6));
                  if (var7 != -1) {
                     this.previousSlot = this.slotController.VORfA(this.previousSlot, mc.thePlayer.inventory.currentItem, var7);
                     this.switchToToolSlot(var7);
                  }
               }
            }
         } else {
            this.resetTracking();
         }
      }
   }

   private boolean xPo1() {
      boolean var1 = ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem) || mc.thePlayer.isUsingItem();
      return this.PbI.heldItemBlacklist.isToggled() && this.PbI.heldItems.EMuhC6(mc.thePlayer.getHeldItem()) ? true : var1;
   }

   private boolean zSmtKfo(BlockPos var1) {
      return !this.PbI.blockBlacklist.isToggled() ? false : this.GmJq(var1, this.PbI.blacklistedBlocks);
   }

   private boolean isBlockWhitelisted(BlockPos var1) {
      return this.PbI.whitelistedBlocks.getEntries().isEmpty() ? false : this.GmJq(var1, this.PbI.whitelistedBlocks);
   }

   private boolean GmJq(BlockPos var1, BlockListSetting var2) {
      IBlockState var3 = BlockUtils.getBlockState(var1);
      Block var4 = var3.getBlock();
      return var4 != null && Block.blockRegistry.getNameForObject(var4) != null
         ? BlockNameMatcher.matchesNameOrMeta(((ResourceLocation)Block.blockRegistry.getNameForObject(var4)).toString(), var4.getMetaFromState(var3), var2::containsEntry)
         : false;
   }

   private void resetTracking() {
      this.hoverTracker.reset();
      this.restorePreviousTool();
   }

   private void restorePreviousTool() {
      if (this.previousSlot != -1 && this.PbI.switchBackWhenDone.isToggled()) {
         this.switchToToolSlot(this.previousSlot);
      }

      this.previousSlot = -1;
      this.slotController.MhLm6();
   }

   private void switchToToolSlot(int var1) {
      if (this.slotController.needsSlotSwitch(mc.thePlayer.inventory.currentItem, var1)) {
         mc.thePlayer.inventory.currentItem = var1;
         this.slotController.markActive();
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Conditions", "Hover delay", new String[]{"only while crouching", "require left mouse"}, new String[]{"Crouching", "Left mouse held"}),
         buildSettingAlias("Swap", "Conditions", new String[]{"switch back when done"}, new String[]{"Switch back"}),
         buildSettingAlias(
            "Filters",
            "Swap",
            new String[]{"held item blacklist", "block whitelist", "block blacklist"},
            new String[]{"Held item blacklist", "Block whitelist", "Block blacklist"}
         )
      );
   }
}
