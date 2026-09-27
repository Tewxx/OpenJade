// Jade recovery: module: Stealer (player); original class: jade.deps.eLz.KtDQLp
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.SkywarsGameState;
import jade.client.common.Subscribe;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.SliderSetting;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldSettings.GameType;

@ModuleInfo
public class Stealer extends Module {
   private final BooleanSetting nameCheck;
   private final SliderSetting delayTicks;
   private final BooleanSetting randomize;
   private final BooleanSetting autoClose;
   private final BooleanSetting skipBadItems;
   private final BooleanSetting onlyInSkywars;
   private int clickDelayTicks;
   private boolean stealSessionStarted;
   private boolean OQvO;

   public Stealer() {
      super("Stealer", Category.player);
      this.registerSetting(
         this.nameCheck = new BooleanSetting(
            "Name Check", true
         )
      );
      this.registerSetting(this.delayTicks = new SliderSetting("Delay Ticks", 2.0, 0.0, 20.0, 1.0));
      this.registerSetting(this.randomize = new BooleanSetting("Randomize", false));
      this.registerSetting(
         this.autoClose = new BooleanSetting(
            "Auto Close", false
         )
      );
      this.registerSetting(this.skipBadItems = new BooleanSetting("Skip bad items", true));
      this.registerSetting(
         this.onlyInSkywars = new BooleanSetting(
            "Only in Skywars", true
         )
      );
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.resetStealerState();
   }

   @Override
   public void onDisable() {
      this.resetStealerState();
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.resetStealerState();
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (!ClientUtils.isInWorld() || !(mc.currentScreen instanceof GuiChest) || !(mc.thePlayer.openContainer instanceof ContainerChest)) {
         this.resetStealerState();
      } else if (!this.isGameModeAllowed()) {
         this.resetStealerState();
      } else {
         ContainerChest var2 = (ContainerChest)mc.thePlayer.openContainer;
         IInventory var3 = var2.getLowerChestInventory();
         if (this.nameCheck.isToggled() && !isUnnamedChest(var3)) {
            this.resetStealerState();
         } else if (!isSurvivalMode()) {
            this.OQvO = false;
         } else {
            if (!this.stealSessionStarted) {
               this.stealSessionStarted = true;
               this.clickDelayTicks = 0;
            }

            if (this.clickDelayTicks > 0) {
               this.clickDelayTicks--;
            } else {
               int var4 = this.findNextStealSlot(var2, var3);
               if (var4 < 0) {
                  this.OQvO = false;
                  this.AGfvX();
               } else {
                  this.OQvO = true;
                  mc.playerController.windowClick(var2.windowId, var4, 0, 1, mc.thePlayer);
                  this.clickDelayTicks = this.XKhj5();
               }
            }
         }
      }
   }

   private void resetStealerState() {
      this.clickDelayTicks = 0;
      this.stealSessionStarted = false;
      this.OQvO = false;
   }

   public boolean isStealingActive() {
      return this.isEnabled() && this.OQvO;
   }

   private boolean isGameModeAllowed() {
      return !this.onlyInSkywars.isToggled() || SkywarsGameState.isFightInProgress();
   }

   private int findNextStealSlot(ContainerChest var1, IInventory var2) {
      for (int var3 = 0; var3 < var2.getSizeInventory(); var3++) {
         if (var1.getSlot(var3).getHasStack()) {
            ItemStack var4 = var1.getSlot(var3).getStack();
            if (this.hasInventoryRoom(var4) && this.MLzz(var4)) {
               return var3;
            }
         }
      }

      return -1;
   }

   private boolean MLzz(ItemStack var1) {
      if (this.isUselessItem(var1) && this.xOhkcd5(var1)) {
         return false;
      } else if (!this.skipBadItems.isToggled()) {
         return true;
      } else {
         return var1 != null && var1.getItem() instanceof ItemArmor ? this.isArmorUpgrade(var1) : InventoryManager.isWantedItem(var1);
      }
   }

   private boolean isUselessItem(ItemStack var1) {
      return var1 != null && (var1.getItem() == Items.fishing_rod || var1.getItem() == Items.water_bucket || var1.getItem() == Items.lava_bucket);
   }

   private boolean xOhkcd5(ItemStack var1) {
      for (int var2 = 0; var2 < mc.thePlayer.inventory.mainInventory.length; var2++) {
         ItemStack var3 = mc.thePlayer.inventory.mainInventory[var2];
         if (var3 != null && var3.getItem() == var1.getItem()) {
            return true;
         }
      }

      return false;
   }

   private boolean isArmorUpgrade(ItemStack var1) {
      int var2 = InventoryManager.getArmorType(var1);
      if (var2 < 0) {
         return false;
      } else {
         double var3 = InventoryManager.getItemArmorScore(var1);
         double var5 = InventoryManager.getWornArmorScore(var2, true);

         for (int var7 = 0; var7 < mc.thePlayer.inventory.mainInventory.length; var7++) {
            ItemStack var8 = mc.thePlayer.inventory.mainInventory[var7];
            if (InventoryManager.getArmorType(var8) == var2) {
               var5 = Math.max(var5, InventoryManager.getItemArmorScore(var8));
            }
         }

         return var3 > var5 + 1.0E-6;
      }
   }

   private boolean hasInventoryRoom(ItemStack var1) {
      if (var1 == null) {
         return false;
      } else if (mc.thePlayer.inventory.getFirstEmptyStack() != -1) {
         return true;
      } else {
         for (int var2 = 0; var2 < mc.thePlayer.inventory.mainInventory.length; var2++) {
            ItemStack var3 = mc.thePlayer.inventory.mainInventory[var2];
            if (uj28(var3, var1) && var3.stackSize < var3.getMaxStackSize()) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean uj28(ItemStack var0, ItemStack var1) {
      if (var0 == null || var1 == null || var0.getItem() != var1.getItem()) {
         return false;
      } else {
         return var0.getHasSubtypes() && var0.getMetadata() != var1.getMetadata() ? false : ItemStack.areItemStackTagsEqual(var0, var1);
      }
   }

   private void AGfvX() {
      if (this.autoClose.isToggled()) {
         mc.thePlayer.closeScreen();
      }
   }

   private int XKhj5() {
      int var1 = Math.max(0, (int)Math.round(this.delayTicks.getInput()));
      if (this.randomize.isToggled()) {
         var1 += 1 + (int)(Math.random() * 2.0);
      }

      return var1;
   }

   private static boolean isUnnamedChest(IInventory var0) {
      if (var0 != null && var0.hasCustomName()) {
         String var1 = var0.getDisplayName() != null ? var0.getDisplayName().getUnformattedText() : "";
         return var1 == null || var1.trim().isEmpty();
      } else {
         return true;
      }
   }

   private static boolean isSurvivalMode() {
      GameType var0 = mc.playerController.getCurrentGameType();
      return var0 == GameType.SURVIVAL || var0 == GameType.ADVENTURE;
   }
}
