// Jade recovery: module: Auto Chest (minigames); original class: jade.deps.eLz.YQFvdZG
package jade.client.module.minigames;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.PreUpdateEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@ModuleInfo
public class AutoChest extends Module {
   private final BooleanSetting blocks;
   private final BooleanSetting fireballs;
   private final BooleanSetting swords;
   private final BooleanSetting emeralds;
   private final BooleanSetting diamonds;
   private final BooleanSetting iron;
   private final BooleanSetting gold;
   private final BooleanSetting other;
   private final MultiSelectSetting multiSelectSetting;
   private final SliderSetting speed;
   private final BooleanSetting closeWhenDone;
   private AutoChest$1 RDa;
   private ContainerChest containerChest;
   private long nextClickAtMillis;

   public AutoChest() {
      super("Auto Chest", Category.minigames);
      this.registerSetting(new DescriptionSetting("Scroll up to deposit and down to steal."));
      this.registerSetting(
         this.speed = new SliderSetting(
            "Speed", "ms", 100.0, 0.0, 500.0, 50.0
         )
      );
      this.registerSetting(
         this.closeWhenDone = new BooleanSetting(
            "Close When Done", false
         )
      );
      String var10004 = "Items";
      BooleanSetting[] var10005 = new BooleanSetting[]{
         this.blocks = new BooleanSetting("Blocks", true), null, null, null, null, null, null, null
      };
      var10005[1] = this.fireballs = new BooleanSetting(
         "Fireballs", true
      );
      var10005[2] = this.swords = new BooleanSetting(
         "Swords", true
      );
      var10005[3] = this.emeralds = new BooleanSetting(
         "Emeralds", true
      );
      var10005[4] = this.diamonds = new BooleanSetting(
         "Diamonds", true
      );
      var10005[5] = this.iron = new BooleanSetting(
         "Iron", true
      );
      var10005[6] = this.gold = new BooleanSetting(
         "Gold", true
      );
      var10005[7] = this.other = new BooleanSetting(
         "Other", true
      );
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.registerHiddenSetting(this.blocks);
      this.registerHiddenSetting(this.fireballs);
      this.registerHiddenSetting(this.swords);
      this.registerHiddenSetting(this.emeralds);
      this.registerHiddenSetting(this.diamonds);
      this.registerHiddenSetting(this.iron);
      this.registerHiddenSetting(this.gold);
      this.registerHiddenSetting(this.other);
      this.initialized = true;
   }

   private void registerHiddenSetting(BooleanSetting var1) {
      var1.visible = false;
      this.registerSetting(var1);
   }

   public void handleScrollTransfer(int var1) {
      if (var1 != 0 && isLootChestOpen()) {
         this.RDa = var1 > 0 ? AutoChest$1.DEPOSIT : AutoChest$1.STEAL;
         this.containerChest = (ContainerChest)mc.thePlayer.openContainer;
         this.nextClickAtMillis = 0L;
      }
   }

   @Override
   public void onEnable() {
      this.resetTransferState();
   }

   @Override
   public void onDisable() {
      this.resetTransferState();
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (this.RDa != null) {
         if (isLootChestOpen() && mc.thePlayer.openContainer == this.containerChest) {
            long var2 = System.currentTimeMillis();
            if (var2 >= this.nextClickAtMillis) {
               int var4 = this.findSlotToTransfer(this.containerChest, this.RDa);
               if (var4 >= 0) {
                  mc.playerController.windowClick(this.containerChest.windowId, var4, 0, 1, mc.thePlayer);
                  this.nextClickAtMillis = var2 + Math.max(0L, Math.round(this.speed.getInput()));
               } else {
                  boolean var5 = this.closeWhenDone.isToggled() && this.isTransferComplete(this.containerChest, this.RDa);
                  this.resetTransferState();
                  if (var5) {
                     mc.thePlayer.closeScreen();
                  }
               }
            }
         } else {
            this.resetTransferState();
         }
      }
   }

   private boolean isTransferComplete(ContainerChest var1, AutoChest$1 var2) {
      int var3 = var1.getLowerChestInventory().getSizeInventory();
      int var4 = var2 == AutoChest$1.STEAL ? 0 : var3;
      int var5 = var2 == AutoChest$1.STEAL ? var3 : var1.inventorySlots.size();

      for (int var6 = var4; var6 < var5; var6++) {
         Slot var7 = var1.getSlot(var6);
         if (var7 != null && var7.getHasStack() && this.r343(var7.getStack())) {
            return false;
         }
      }

      return true;
   }

   private int findSlotToTransfer(final ContainerChest var1, AutoChest$1 var2) {
      IInventory var3 = var1.getLowerChestInventory();
      int var4 = var3.getSizeInventory();
      int var5 = var2 == AutoChest$1.STEAL ? 0 : var4;
      int var6 = var2 == AutoChest$1.STEAL ? var4 : var1.inventorySlots.size();
      ArrayList var7 = new ArrayList();

      for (int var8 = var5; var8 < var6; var8++) {
         Slot var9 = var1.getSlot(var8);
         if (var9 != null && var9.getHasStack()) {
            ItemStack var10 = var9.getStack();
            if (this.r343(var10) && (var2 != AutoChest$1.STEAL || canFitInInventory(var10)) && (var2 != AutoChest$1.DEPOSIT || MAbl(var3, var10))) {
               var7.add(var8);
            }
         }
      }

      Collections.sort(var7, new Comparator<Integer>() {
         public int compare(Integer var1x, Integer var2x) {
            int var3x = AutoChest.WrTtu(var1.getSlot(var1x).getStack());
            int var4x = AutoChest.WrTtu(var1.getSlot(var2x).getStack());
            return var3x != var4x ? Integer.compare(var3x, var4x) : Integer.compare(var1x, var2x);
         }
      });
      return var7.isEmpty() ? -1 : (Integer)var7.get(0);
   }

   private boolean r343(ItemStack var1) {
      if (var1 != null && !WPsV(var1)) {
         Item var2 = var1.getItem();
         if (var2 == Items.emerald) {
            return this.emeralds.isToggled();
         } else if (var2 == Items.diamond) {
            return this.diamonds.isToggled();
         } else if (var2 == Items.gold_ingot) {
            return this.gold.isToggled();
         } else if (var2 == Items.iron_ingot) {
            return this.iron.isToggled();
         } else if (var2 == Items.fire_charge) {
            return this.fireballs.isToggled();
         } else if (var2 instanceof ItemSword) {
            return this.swords.isToggled();
         } else {
            return var2 instanceof ItemBlock ? this.blocks.isToggled() : this.other.isToggled();
         }
      } else {
         return false;
      }
   }

   private static boolean WPsV(ItemStack var0) {
      Item var1 = var0.getItem();
      return var1 == Items.wooden_sword || var1 == Items.compass || var1 instanceof ItemShears || var1 instanceof ItemPickaxe || var1 instanceof ItemAxe;
   }

   private static int getItemPriority(ItemStack var0) {
      if (var0 == null) {
         return 6;
      } else {
         Item var1 = var0.getItem();
         if (var1 == Items.emerald) {
            return 0;
         } else if (var1 == Items.diamond) {
            return 1;
         } else if (var1 == Items.gold_ingot) {
            return 3;
         } else if (var1 == Items.iron_ingot) {
            return 4;
         } else {
            return var1 instanceof ItemBlock ? 5 : 2;
         }
      }
   }

   private static boolean canFitInInventory(ItemStack var0) {
      if (mc.thePlayer.inventory.getFirstEmptyStack() != -1) {
         return true;
      } else {
         for (ItemStack var4 : mc.thePlayer.inventory.mainInventory) {
            if (DiwG2(var4, var0) && var4.stackSize < var4.getMaxStackSize()) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean MAbl(IInventory var0, ItemStack var1) {
      for (int var2 = 0; var2 < var0.getSizeInventory(); var2++) {
         ItemStack var3 = var0.getStackInSlot(var2);
         if (var3 == null) {
            return true;
         }

         if (DiwG2(var3, var1) && var3.stackSize < var3.getMaxStackSize()) {
            return true;
         }
      }

      return false;
   }

   private static boolean DiwG2(ItemStack var0, ItemStack var1) {
      if (var0 == null || var1 == null || var0.getItem() != var1.getItem()) {
         return false;
      } else {
         return var0.getHasSubtypes() && var0.getMetadata() != var1.getMetadata() ? false : ItemStack.areItemStackTagsEqual(var0, var1);
      }
   }

   private static boolean isLootChestOpen() {
      if (ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2 && mc.currentScreen instanceof GuiChest && mc.thePlayer.openContainer instanceof ContainerChest) {
         IInventory var0 = ((ContainerChest)mc.thePlayer.openContainer).getLowerChestInventory();
         return luKt6(var0);
      } else {
         return false;
      }
   }

   private static boolean luKt6(IInventory var0) {
      if (var0 != null && var0.hasCustomName()) {
         String var1 = var0.getDisplayName() == null ? "" : var0.getDisplayName().getUnformattedText();
         return var1 == null || var1.trim().isEmpty();
      } else {
         return true;
      }
   }

   private void resetTransferState() {
      this.RDa = null;
      this.containerChest = null;
      this.nextClickAtMillis = 0L;
   }

   @Override
   public String getInfo() {
      return (int)Math.round(this.speed.getInput()) + "ms";
   }

   public static int WrTtu(ItemStack var0) {
      return getItemPriority(var0);
   }
}
