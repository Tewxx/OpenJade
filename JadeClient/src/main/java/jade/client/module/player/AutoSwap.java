// Jade recovery: module: Auto Swap (player); original class: jade.deps.eLz.ho1WMTCBL
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.PacketSendEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.autoswap.SwapStateTracker;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

@ModuleInfo
public class AutoSwap extends Module {
   private final SliderSetting swapAt;
   private final BooleanSetting useBlockWhitelist;
   private final BlockListSetting whitelistedBlocks;
   private final BooleanSetting blockBlacklist;
   private final BlockListSetting blacklistedBlocks;
   private final MultiSelectSetting multiSelectSetting;
   private final BooleanSetting lookingDown;
   private final BooleanSetting movingBackwards;
   private final SwapStateTracker Sxek = new SwapStateTracker();

   public AutoSwap() {
      super("Auto Swap", Category.player);
      this.registerSetting(
         this.swapAt = new SliderSetting(
            "Swap at", " block", 1.0, 1.0, 64.0, 1.0
         )
      );
      this.registerSetting(
         this.useBlockWhitelist = new BooleanSetting(
            "Use block whitelist", false
         )
      );
      this.registerSetting(this.whitelistedBlocks = new BlockListSetting("Whitelisted blocks", new String[]{"Blocks", "Blocks.Block whitelist", "Blocks.Whitelisted blocks"}));
      this.lookingDown = new BooleanSetting("Looking down", false);
      this.movingBackwards = new BooleanSetting(
         "Moving backwards", false
      );
      String var10004 = "Conditionals";
      BooleanSetting[] var10005 = new BooleanSetting[2];
      var10005[0] = this.lookingDown;
      var10005[1] = this.movingBackwards;
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.lookingDown.visible = false;
      this.movingBackwards.visible = false;
      this.registerSetting(this.lookingDown);
      this.registerSetting(this.movingBackwards);
      this.whitelistedBlocks.visible = false;
      this.registerSetting(
         this.blockBlacklist = new BooleanSetting(
            "Block blacklist", false
         )
      );
      this.registerSetting(this.blacklistedBlocks = new BlockListSetting("Blacklisted blocks"));
      this.blacklistedBlocks.visible = false;
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      this.whitelistedBlocks.setVisible(this.useBlockWhitelist.isToggled(), this);
      this.blacklistedBlocks.setVisible(this.blockBlacklist.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.SiIn();
   }

   @Override
   public void onDisable() {
      this.SiIn();
   }

   @Subscribe
   public void onPacketSend(PacketSendEvent var1) {
      if (ClientUtils.isInWorld() && var1.ys98() instanceof C08PacketPlayerBlockPlacement) {
         C08PacketPlayerBlockPlacement var2 = (C08PacketPlayerBlockPlacement)var1.ys98();
         if (var2.getPlacedBlockDirection() != 255) {
            ItemStack var3 = var2.getStack();
            if (var3 != null && var3.getItem() instanceof ItemBlock) {
               this.Sxek.recordPlacement(var3, mc.thePlayer.inventory.currentItem, this.TzZp(var3));
            }
         }
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (this.Sxek.consumePendingSwap()) {
         ItemStack var2 = this.Sxek.getPlacedStack();
         int var3 = this.Sxek.getPlacedSlot();
         if (ClientUtils.isInWorld() && var2 != null && var3 != -1 && this.zdte5()) {
            if (this.TzZp(var2)) {
               ItemStack var4 = mc.thePlayer.inventory.getStackInSlot(var3);
               if (var4 == null || var4.stackSize != 1) {
                  if (var4 == null || var4.stackSize <= this.getSwapStackLimit()) {
                     this.swapToBestBlock();
                  }
               }
            }
         }
      }
   }

   private boolean zdte5() {
      return this.lookingDown.isToggled() && mc.thePlayer.rotationPitch < 70.0F ? false : !this.movingBackwards.isToggled() || ClientUtils.xusXfhC(mc.gameSettings.keyBindBack);
   }

   private boolean isAllowedBlock(ItemStack var1) {
      return (!this.blockBlacklist.isToggled() || !this.matchesBlockList(var1, this.blacklistedBlocks)) && (!this.useBlockWhitelist.isToggled() || this.matchesBlockList(var1, this.whitelistedBlocks));
   }

   private boolean matchesBlockList(ItemStack var1, BlockListSetting var2) {
      if (var1 != null && var1.getItem() instanceof ItemBlock) {
         Block var3 = ((ItemBlock)var1.getItem()).getBlock();
         Object var4 = Block.blockRegistry.getNameForObject(var3);
         if (var3 != null && var4 != null) {
            String var5 = var4.toString();
            int var6 = var1.getMetadata();
            String var7 = var6 != 0 ? var5 + ":" + var6 : var5;
            return var2.containsEntry(var7) || var2.containsEntry(var5);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean TzZp(ItemStack var1) {
      if (var1 != null && var1.getItem() instanceof ItemBlock) {
         Block var2 = ((ItemBlock)var1.getItem()).getBlock();
         return var2 != null && var2.isFullBlock() && this.isAllowedBlock(var1);
      } else {
         return false;
      }
   }

   private void swapToBestBlock() {
      long var1 = System.currentTimeMillis();
      int var3 = -1;

      for (int var4 = 8; var4 >= 0; var4--) {
         if (this.Sxek.canSwapToSlot(var4, var1)) {
            ItemStack var5 = mc.thePlayer.inventory.getStackInSlot(var4);
            if (this.TzZp(var5)) {
               if (((ItemBlock)var5.getItem()).getBlock() == Blocks.wool) {
                  var3 = var4;
                  break;
               }

               if (var3 == -1) {
                  var3 = var4;
               }
            }
         }
      }

      if (var3 != -1) {
         this.selectHotbarSlot(var3);
         this.Sxek.wfTy1(var3, var1);
      }
   }

   private int getSwapStackLimit() {
      return Math.max(0, (int)Math.round(this.swapAt.getInput()));
   }

   private void selectHotbarSlot(int var1) {
      if (var1 != -1 && var1 != mc.thePlayer.inventory.currentItem) {
         mc.thePlayer.inventory.currentItem = var1;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private void SiIn() {
      this.Sxek.clear();
   }
}
