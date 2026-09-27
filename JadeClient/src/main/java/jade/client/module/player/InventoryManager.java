// Jade recovery: module: Inventory Manager (player); original class: jade.deps.eLz.G70DnWvZT
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.ItemMatcher;
import jade.client.common.SkywarsGameState;
import jade.client.common.Subscribe;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.inventorymanager.TransferSourceSlot;
import jade.client.module.player.inventorymanager.ArmorScorer;
import jade.client.module.player.inventorymanager.InventorySnapshot;
import jade.client.module.player.inventorymanager.InventorySorter;
import jade.client.module.player.inventorymanager.ItemStackMatcher;
import jade.client.module.player.inventorymanager.StackMergePlan;
import jade.client.module.player.inventorymanager.StackUtils;
import jade.client.module.player.inventorymanager.CursorRecoveryPlan;
import jade.client.module.player.inventorymanager.wtKNsWqQg;
import jade.client.setting.BooleanSetting;
import jade.client.setting.CategoryListSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

@ModuleInfo(aliases = {"Inventory", "InvManager", "Inv Manager"})
public class InventoryManager extends Module {
   private static final int WWb = InventoryPlayer.getHotbarSize();
   private static final double SCORE_EPSILON = 1.0E-6;
   private static final String[] sbw = new String[]{
         "@category:sword",
         "@category:bow",
         "@category:fishing_rod",
         "@category:stick",
         "@category:axe",
         "@category:pickaxe",
         "@category:shears"
      };
   private static final Comparator<InventoryManager$3> comparator = wtKNsWqQg::compareForSorting;
   private final SliderSetting delayTicks;
   private final BooleanSetting randomize;
   private final BooleanSetting disableWhenDone;
   private final BooleanSetting durabilityCheck;
   private final BooleanSetting dropBadItems;
   private final BooleanSetting onlyInSkywars;
   private final BooleanSetting autoArmor;
   private final BooleanSetting stackBlocks;
   private final BooleanSetting reorder;
   private final CategoryListSetting hotbarSlots;
   private InventoryManager$3 currentPlan;
   private int savedSlot = -1;
   private int GEH;
   private int postponeTicks;
   private boolean stateInitialized;
   private InventoryManager$6 runState = InventoryManager$6.ACTIVE;
   private static final InventoryManager$9 VTnIh = InventoryManager::noOpAfterAction;

   public InventoryManager() {
      super("Inventory Manager", Category.player);
      this.registerSetting(this.delayTicks = new SliderSetting("Delay Ticks", 2.0, 0.0, 20.0, 1.0, new String[]{"Speed", "Target CPS"}));
      this.registerSetting(
         this.randomize = new BooleanSetting(
            "Randomize", false
         )
      );
      this.registerSetting(this.disableWhenDone = new BooleanSetting("Disable when done", false, new String[]{"Disable when complete"}));
      this.registerSetting(
         this.durabilityCheck = new BooleanSetting(
            "Durability check", true
         )
      );
      this.registerSetting(this.dropBadItems = new BooleanSetting("Drop bad items", false));
      this.registerSetting(
         this.onlyInSkywars = new BooleanSetting(
            "Only in Skywars", true
         )
      );
      this.registerSetting(
         this.autoArmor = new BooleanSetting(
            "Auto Armor",
            false,
            new String[]{"Equip armor"}
         )
      );
      this.registerSetting(
         this.stackBlocks = new BooleanSetting(
            "Stack Blocks",
            false,
            new String[]{"Combine matching block stacks"}
         )
      );
      this.registerSetting(
         this.reorder = new BooleanSetting(
            "Reorder",
            false,
            new String[]{"Inventory"}
         )
      );
      this.registerSetting(this.hotbarSlots = new CategoryListSetting("Hotbar slots"));
      this.hotbarSlots.visible = false;
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      this.hotbarSlots.setVisible(this.reorder.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.resetRun();
   }

   @Override
   public void onDisable() {
      if (this.KykEp() && this.isWorkPending()) {
         this.saobza8(InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory), false);
      }

      this.resetRun();
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.resetRun();
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.reorder) {
         this.hotbarSlots.setVisible(this.reorder.isToggled(), this);
      } else if (var1 == this.stackBlocks) {
         if (this.stackBlocks.isToggled() && this.runState == InventoryManager$6.COMPLETE_LATCHED) {
            this.runState = InventoryManager$6.ACTIVE;
         }
      } else if (var1 == this.disableWhenDone && this.KykEp()) {
         if (!this.disableWhenDone.isToggled()) {
            if (this.runState == InventoryManager$6.COMPLETE_LATCHED) {
               this.runState = InventoryManager$6.ACTIVE;
            }
         } else {
            if (this.currentPlan == null && this.savedSlot < 0) {
               InventorySnapshot var2 = InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory);
               InventoryManager$8 var3 = InventoryManager$8.createContext(var2, this.HkiS(var2));
               if (this.createNextPlan(var2, var3) == null) {
                  this.runState = InventoryManager$6.COMPLETE_LATCHED;
               }
            }
         }
      }
   }

   @Subscribe
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (this.isExecutingPlan()) {
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.LMAaf();
      } else if (!this.isGameAllowed()) {
         this.LMAaf();
      } else if (!this.KykEp()) {
         this.LMAaf();
      } else {
         InventorySnapshot var2 = InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory);
         if (!this.stateInitialized) {
            this.FXNLq(var2);
         }

         if (this.QnjU2()) {
            if (this.runState != InventoryManager$6.COMPLETE_LATCHED) {
               int var3 = this.consumeActionBudget();
               if (var3 > 0) {
                  while (var3 > 0) {
                     InventoryManager$7[] var4 = this.HkiS(var2);
                     InventoryManager$8 var5 = InventoryManager$8.createContext(var2, var4);
                     if (this.currentPlan == null) {
                        if (var2.itemStack != null) {
                           this.currentPlan = this.planCursorRecovery(var5, InventoryManager$5.RECOVER_CURSOR, false);
                           if (this.currentPlan == null) {
                              this.runState = InventoryManager$6.ACTIVE;
                              return;
                           }

                           this.runState = InventoryManager$6.EXECUTING;
                        } else {
                           this.savedSlot = -1;
                           if (this.autoArmor.isToggled()) {
                              this.currentPlan = this.planArmorEquip(var2);
                           }

                           if (this.currentPlan == null) {
                              this.currentPlan = this.dIbiA(var5);
                           }

                           if (this.currentPlan == null) {
                              this.currentPlan = this.planDropBadItem(var5);
                           }

                           if (this.currentPlan == null) {
                              this.runState = this.disableWhenDone.isToggled() ? InventoryManager$6.COMPLETE_LATCHED : InventoryManager$6.ACTIVE;
                              return;
                           }

                           this.runState = InventoryManager$6.EXECUTING;
                        }
                     }

                     if (!this.executeCurrentStep(var5)) {
                        return;
                     }

                     var3--;
                     var2 = InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory);
                  }
               }
            }
         }
      }
   }

   public boolean isExecutingPlan() {
      return this.isEnabled() && this.isGameAllowed() && this.KykEp() && this.runState == InventoryManager$6.EXECUTING && this.currentPlan != null;
   }

   public void recoverCursorOnScreenClose(String var1) {
      if (this.isEnabled() && ClientUtils.isInWorld() && this.KykEp() && this.isWorkPending()) {
         InventorySnapshot var2 = InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory);
         if (var2.itemStack != null) {
            this.saobza8(var2, true);
         }
      }
   }

   private void resetRun() {
      this.currentPlan = null;
      this.savedSlot = -1;
      this.GEH = 0;
      this.postponeTicks = 0;
      this.stateInitialized = false;
      this.runState = InventoryManager$6.ACTIVE;
   }

   private void FXNLq(InventorySnapshot var1) {
      this.stateInitialized = true;
      this.currentPlan = null;
      this.savedSlot = -1;
      this.GEH = 0;
      this.postponeTicks = 1;
      this.runState = InventoryManager$6.ACTIVE;
      if (this.disableWhenDone.isToggled()) {
         InventoryManager$8 var2 = InventoryManager$8.createContext(var1, this.HkiS(var1));
         if (this.createNextPlan(var1, var2) == null) {
            this.runState = InventoryManager$6.COMPLETE_LATCHED;
         }
      }
   }

   private void LMAaf() {
      this.stateInitialized = false;
      this.currentPlan = null;
      this.savedSlot = -1;
      this.GEH = 0;
      this.postponeTicks = 0;
      this.runState = InventoryManager$6.ACTIVE;
   }

   private boolean QnjU2() {
      if (mc.thePlayer.isSprinting() || mc.thePlayer.isSneaking()) {
         mc.thePlayer.setSprinting(false);
         this.postponeTicks = 1;
         return false;
      } else if (this.postponeTicks > 0) {
         this.postponeTicks--;
         return false;
      } else {
         return true;
      }
   }

   private boolean isGameAllowed() {
      return !this.onlyInSkywars.isToggled() || SkywarsGameState.isFightInProgress();
   }

   private boolean isWorkPending() {
      return this.currentPlan != null || this.savedSlot >= 0;
   }

   private int consumeActionBudget() {
      if (this.GEH > 0) {
         this.GEH--;
         return 0;
      } else {
         return 1;
      }
   }

   private int computeActionDelay() {
      int var1 = Math.max(0, (int)Math.round(this.delayTicks.getInput()));
      if (this.randomize.isToggled()) {
         var1 += 1 + (int)(Math.random() * 2.0);
      }

      return var1;
   }

   private boolean executeCurrentStep(InventoryManager$8 var1) {
      if (this.currentPlan == null) {
         return false;
      } else {
         InventoryManager$4 var2 = this.currentPlan.currentStep();
         if (var2 == null) {
            this.currentPlan = null;
            this.runState = InventoryManager$6.ACTIVE;
            return false;
         } else if (!var2.hEbmQ.TWOy(var1, this)) {
            this.currentPlan = null;
            this.runState = InventoryManager$6.ACTIVE;
            if (var1.inventorySnapshot.itemStack != null && this.savedSlot >= 0) {
               InventoryManager$3 var3 = this.planCursorRecovery(var1, InventoryManager$5.RECOVER_CURSOR, false);
               if (var3 != null) {
                  this.currentPlan = var3;
                  this.runState = InventoryManager$6.EXECUTING;
                  return this.executeCurrentStep(var1);
               }
            }

            if (var1.inventorySnapshot.itemStack == null) {
               this.savedSlot = -1;
            }

            return false;
         } else {
            var2.JdeNqd.applyToModule(this);
            this.knIu(var2.hxa6, var2.clickButton, var2.clickMode);
            this.GEH = this.computeActionDelay();
            var2.postClickAction.applyToModule(this);
            this.currentPlan.xxyGo2();
            if (this.currentPlan.IjSz()) {
               this.currentPlan = null;
               if (this.runState != InventoryManager$6.COMPLETE_LATCHED) {
                  this.runState = InventoryManager$6.ACTIVE;
               }
            }

            return true;
         }
      }
   }

   private InventoryManager$3 planHotbarPlacement(InventoryManager$8 var1) {
      InventoryManager$3 var2 = null;

      for (InventoryManager$7 var6 : var1.desiredItems) {
         if (var6 != null) {
            ItemStack var7 = var1.inventorySnapshot.getStackInSlot(var6.BHm1);
            boolean var8 = this.isSlotItemAlreadyBest(var1, var6, var7);
            if (!var8) {
               InventoryManager$3 var9 = this.PqB9(var1, var6);
               if (var9 != null) {
                  var2 = pickBetterPlan(var2, var9);
               }
            }
         }
      }

      return var2;
   }

   private InventoryManager$3 dIbiA(InventoryManager$8 var1) {
      InventoryManager$3 var2 = this.planHotbarPlacement(var1);
      if (var2 == null) {
         return this.planBlockStacking(var1, null);
      } else {
         InventoryManager$7 var3 = var2.hotbarSlot >= 0 && var2.hotbarSlot < var1.desiredItems.length ? var1.desiredItems[var2.hotbarSlot] : null;
         if (var3 == null) {
            return var2;
         } else {
            InventoryManager$3 var4 = this.planBlockStacking(var1, var3.categoryPattern);
            ItemStack var5 = var1.inventorySnapshot.getStackInSlot(var2.itemSlot);
            double var6 = var4 != null && var4.itemStack != null ? this.NtTzm(var3.categoryPattern, var4.itemStack) : Double.NEGATIVE_INFINITY;
            double var8 = this.NtTzm(var3.categoryPattern, var5);
            boolean var10 = var4 != null && var4.itemStack != null && var4.targetCount > var2.targetCount && var6 + 1.0E-6 >= var8;
            return var10 ? var4 : var2;
         }
      }
   }

   private InventoryManager$3 createNextPlan(InventorySnapshot var1, InventoryManager$8 var2) {
      if (this.autoArmor.isToggled()) {
         InventoryManager$3 var3 = this.planArmorEquip(var1);
         if (var3 != null) {
            return var3;
         }
      }

      InventoryManager$3 var4 = this.dIbiA(var2);
      return var4 != null ? var4 : this.planDropBadItem(var2);
   }

   private InventoryManager$3 PqB9(InventoryManager$8 var1, InventoryManager$7 var2) {
      InventoryManager$2 var3 = this.findBestMatchingStack(var1, var2, var2.BHm1);
      if (var3 == null) {
         return null;
      } else {
         int var4 = var3.slot;
         ArrayList var6 = new ArrayList(1);
         var6.add(new InventoryManager$4(toContainerSlot(var4), var2.BHm1, 2, (recoveredArg0, recoveredArg1) -> InventoryManager.Rdk9(var2, var4, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
         return new InventoryManager$3(InventoryManager$5.PLACE_TO_HOTBAR, 1, var2.BHm1, var2.preferenceIndex, var3.stackSize, true, 0, var4, var6);
      }
   }

   private InventoryManager$3 planMergeToTarget(InventoryManager$8 var1, InventoryManager$7 var2, ItemStack var3) {
      StackMergePlan var4 = InventorySorter.planStackMerge(copyInventoryStacks(var1.inventorySnapshot), this.getLockedHotbarSlots(var1), var2.BHm1, var3);
      if (var4 == null) {
         return null;
      } else {
         int var5 = var2.BHm1;
         String var6 = var2.categoryPattern;
         ArrayList var7 = new ArrayList(var4.getCost());

         for (TransferSourceSlot var9 : var4.getMergeMoves()) {
            int var10 = var9.getSourceSlot();
            if (var9.isFromMainInventory()) {
               var7.add(new InventoryManager$4(toContainerSlot(var10), 0, 1, (recoveredArg0, recoveredArg1) -> InventoryManager.canPullInventoryStackIntoTarget(var10, var5, var6, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
            } else {
               var7.add(new InventoryManager$4(toContainerSlot(var10), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canMergeHotbarStackIntoTarget(var10, var5, var6, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), (recoveredArg0) -> InventoryManager.saveMergeSourceSlot(var10, (jade.client.module.player.InventoryManager) recoveredArg0), VTnIh));
               var7.add(new InventoryManager$4(toContainerSlot(var5), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canMergeCursorIntoTargetStack(var5, var6, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, var9.iDurqQ() ? VTnIh : InventoryManager::clearSavedSlotAfterMerge));
               if (var9.iDurqQ()) {
                  var7.add(new InventoryManager$4(toContainerSlot(var10), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canRecoverCursorIntoHotbarSlot(var10, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, InventoryManager::clearSavedSlotAfterPartialMerge));
               }
            }
         }

         return new InventoryManager$3(
            InventoryManager$5.MERGE_TO_TARGET, var4.getCost(), var2.BHm1, var2.preferenceIndex, var4.YaGr(), var4.YaGr() >= var3.getMaxStackSize(), 0, var4.getMergeTargetSlot(), var7
         );
      }
   }

   private InventoryManager$3 planPickupAll(InventoryManager$8 var1, InventoryManager$7 var2, ItemStack var3) {
      int var4 = this.countResultingStackSize(var1, var2.BHm1, var3);
      if (var4 <= var3.stackSize) {
         return null;
      } else {
         int var5 = var2.BHm1;
         String var6 = var2.categoryPattern;
         ArrayList var7 = new ArrayList(3);
         var7.add(new InventoryManager$4(toContainerSlot(var5), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canPickUpTargetStack(var5, var6, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), (recoveredArg0) -> InventoryManager.saveSourceSlot(var5, (jade.client.module.player.InventoryManager) recoveredArg0), VTnIh));
         var7.add(new InventoryManager$4(toContainerSlot(var5), 0, 6, (recoveredArg0, recoveredArg1) -> InventoryManager.canPickupAllIntoEmptySlot(var5, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
         var7.add(new InventoryManager$4(toContainerSlot(var5), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canRecoverCursorIntoEmptySlot(var5, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, InventoryManager::clearSavedSlotAfterPickupAll));
         return new InventoryManager$3(InventoryManager$5.PICKUP_ALL_TO_TARGET, 3, var2.BHm1, var2.preferenceIndex, var4, var4 >= var3.getMaxStackSize(), 0, var2.BHm1, var7);
      }
   }

   private InventoryManager$3 planPickupAllToOtherStack(InventoryManager$8 var1, InventoryManager$7 var2, ItemStack var3) {
      InventoryManager$2 var4 = null;

      for (int var5 = 0; var5 < 36; var5++) {
         if (var5 != var2.BHm1) {
            ItemStack var6 = var1.inventorySnapshot.getStackInSlot(var5);
            if (ItemMatcher.matches(var2.categoryPattern, var6) && (var5 >= WWb || !this.isHotbarSlotLocked(var1, var5))) {
               int var7 = this.countResultingStackSize(var1, var5, var6);
               if (var7 > var6.stackSize) {
                  InventoryManager$2 var8 = new InventoryManager$2(var5, this.NtTzm(var2.categoryPattern, var6), var5 < WWb ? 1 : 0, var7);
                  if (var4 == null || var8.isBetterThan(var4)) {
                     var4 = var8;
                  }
               }
            }
         }
      }

      if (var4 == null) {
         return null;
      } else {
         int var11 = var4.slot;
         int var12 = var2.BHm1;
         String var13 = var2.categoryPattern;
         boolean var14 = var3 != null && !ItemMatcher.matches(var13, var3);
         ArrayList var9 = new ArrayList(var14 ? 4 : 3);
         var9.add(new InventoryManager$4(toContainerSlot(var11), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canPickUpSlotIntoEmptyCursor(var13, var11, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), (recoveredArg0) -> InventoryManager.aoIt(var11, (jade.client.module.player.InventoryManager) recoveredArg0), VTnIh));
         var9.add(new InventoryManager$4(toContainerSlot(var11), 0, 6, (recoveredArg0, recoveredArg1) -> InventoryManager.canPickUpAllFromSlot(var13, var11, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
         var9.add(new InventoryManager$4(toContainerSlot(var12), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canPlaceCursorBackIntoTarget(var13, var12, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, var14 ? VTnIh : InventoryManager::clearSavedSlotAfterTransfer));
         if (var14) {
            var9.add(new InventoryManager$4(toContainerSlot(var11), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canRecoverCursorIntoSourceSlot(var11, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, InventoryManager::clearSavedSlotAfterRecovery));
         }

         return new InventoryManager$3(
            InventoryManager$5.PICKUP_ALL_TO_TARGET,
            var9.size(),
            var2.BHm1,
            var2.preferenceIndex,
            var4.stackSize,
            var4.stackSize >= var1.inventorySnapshot.getStackInSlot(var11).getMaxStackSize(),
            0,
            var11,
            var9
         );
      }
   }

   private InventoryManager$3 planBlockStacking(InventoryManager$8 var1, String var2) {
      if (this.stackBlocks.isToggled() && var1.inventorySnapshot.itemStack == null) {
         InventoryManager$3 var3 = null;

         for (int var4 = 0; var4 < 36; var4++) {
            ItemStack var5 = var1.inventorySnapshot.getStackInSlot(var4);
            if (this.isStackableBlockCandidate(var1, var4, var5, var2)) {
               InventoryManager$1 var6 = this.simulateSlotStacking(var1, var4);
               if (isStackingProfitable(var6)) {
                  var3 = this.KNLn1(var3, this.planSingleSourceStacking(var1, var4, var6), var2);
               }
            }
         }

         for (int var10 = WWb; var10 < 36; var10++) {
            ItemStack var12 = var1.inventorySnapshot.getStackInSlot(var10);
            if (this.isStackableBlockCandidate(var1, var10, var12, var2)) {
               InventoryManager$1 var14 = this.simulateSlotStacking(var1, var10);
               if (isWholeStackRelocation(var14, true)) {
                  InventoryManager$8 var7 = InventoryManager$8.createContext(var14.resultSnapshot, var1.desiredItems);

                  for (int var8 = WWb; var8 < 36; var8++) {
                     if (var8 != var10 && isSameStackType(var12, var1.inventorySnapshot.getStackInSlot(var8))) {
                        InventoryManager$1 var9 = this.simulateSlotStacking(var7, var8);
                        if (isStackingAdvantageous(var9)) {
                           var3 = this.KNLn1(var3, this.planTwoSourceStacking(var1, var10, var14, var8, var9), var2);
                        }
                     }
                  }
               }
            }
         }

         for (int var11 = 0; var11 < WWb; var11++) {
            ItemStack var13 = var1.inventorySnapshot.getStackInSlot(var11);
            if (this.isStackableBlockCandidate(var1, var11, var13, var2)) {
               InventoryManager$1 var15 = this.simulateSlotStacking(var1, var11);
               if (isWholeStackRelocation(var15, false)) {
                  InventoryManager$8 var16 = InventoryManager$8.createContext(var15.resultSnapshot, var1.desiredItems);
                  int var17 = var15.Ichy;
                  InventoryManager$1 var18 = this.simulateSlotStacking(var16, var17);
                  if (isStackingAdvantageous(var18)) {
                     var3 = this.KNLn1(var3, this.planTwoSourceStacking(var1, var11, var15, var17, var18), var2);
                  }
               }
            }
         }

         return var3;
      } else {
         return null;
      }
   }

   private boolean isStackableBlockCandidate(InventoryManager$8 var1, int var2, ItemStack var3, String var4) {
      return var3 != null && var3.getItem() instanceof ItemBlock && (var4 == null || ItemMatcher.matches(var4, var3)) && (var2 >= WWb || !this.isHotbarSlotLocked(var1, var2));
   }

   private static boolean isStackingProfitable(InventoryManager$1 var0) {
      return var0 != null && var0.Eufw && var0.MBa > 0 && var0.improvesStacking;
   }

   private static boolean isWholeStackRelocation(InventoryManager$1 var0, boolean var1) {
      return var0 != null && var0.Eufw && var0.MBa == 0 && var0.sourceRemainder == 0 && var0.Ichy >= 0 && var0.Ichy < WWb == var1;
   }

   private static boolean isStackingAdvantageous(InventoryManager$1 var0) {
      return var0 != null && var0.Eufw && var0.MBa > 0 && var0.improvesStacking;
   }

   private InventoryManager$3 planSingleSourceStacking(InventoryManager$8 var1, int var2, InventoryManager$1 var3) {
      InventorySnapshot var5 = var1.inventorySnapshot;
      InventorySnapshot var6 = var3.resultSnapshot;
      ArrayList var7 = new ArrayList(1);
      var7.add(new InventoryManager$4(toContainerSlot(var2), 0, 1, (recoveredArg0, recoveredArg1) -> InventoryManager.canApplyStackStep(var5, var2, var6, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
      return this.buildStackingPlan(var1, var3, var2, var7);
   }

   private InventoryManager$3 planTwoSourceStacking(InventoryManager$8 var1, int var2, InventoryManager$1 var3, int var4, InventoryManager$1 var5) {
      InventorySnapshot var8 = var1.inventorySnapshot;
      InventorySnapshot var9 = var3.resultSnapshot;
      InventorySnapshot var10 = var5.resultSnapshot;
      ArrayList var11 = new ArrayList(2);
      var11.add(new InventoryManager$4(toContainerSlot(var2), 0, 1, (recoveredArg0, recoveredArg1) -> InventoryManager.canApplyStagedStackStep(var8, var2, var9, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
      var11.add(new InventoryManager$4(toContainerSlot(var4), 0, 1, (recoveredArg0, recoveredArg1) -> InventoryManager.tkedf(var9, var4, var10, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
      return this.buildStackingPlan(var1, var5, var2, var11);
   }

   private InventoryManager$3 buildStackingPlan(InventoryManager$8 var1, InventoryManager$1 var2, int var3, List<InventoryManager$4> var4) {
      int var5 = var2.LUtvX < WWb ? var2.LUtvX : Integer.MAX_VALUE;
      int var6 = var5 < WWb && var1.desiredItems[var5] != null ? var1.desiredItems[var5].preferenceIndex : Integer.MAX_VALUE;
      ItemStack var7 = var2.resultSnapshot.getStackInSlot(var2.LUtvX);
      int var8 = this.countMergeableItems(var1, var7, var2.LUtvX);
      ItemStack var9 = var7 != null ? var7.copy() : null;
      if (var9 != null) {
         var9.stackSize = var8;
      }

      return new InventoryManager$3(InventoryManager$5.STACK_BLOCKS, var4.size(), var5, var6, var8, var7 != null && var8 >= var7.getMaxStackSize(), 0, var3, var4, var9);
   }

   private int countMergeableItems(InventoryManager$8 var1, ItemStack var2, int var3) {
      if (var2 == null) {
         return 0;
      } else {
         int var4 = 0;

         for (int var5 = 0; var5 < 36 && var4 < var2.getMaxStackSize(); var5++) {
            if (var5 >= WWb || var5 == var3 || !this.isHotbarSlotLocked(var1, var5)) {
               ItemStack var6 = var1.inventorySnapshot.getStackInSlot(var5);
               if (isSameStackType(var6, var2)) {
                  var4 = Math.min(var2.getMaxStackSize(), var4 + var6.stackSize);
               }
            }
         }

         return var4;
      }
   }

   private InventoryManager$3 KNLn1(InventoryManager$3 var1, InventoryManager$3 var2, String var3) {
      if (var2 == null) {
         return var1;
      } else {
         if (var1 != null && var3 != null) {
            double var4 = this.NtTzm(var3, var1.itemStack);
            double var6 = this.NtTzm(var3, var2.itemStack);
            if (Math.abs(var6 - var4) > 1.0E-6) {
               return var6 > var4 ? var2 : var1;
            }
         }

         if (var1 != null && var2.targetCount == var1.targetCount) {
            if (var2.stepCost != var1.stepCost) {
               return var2.stepCost < var1.stepCost ? var2 : var1;
            } else if (var2.tbn != var1.tbn) {
               return var2.tbn < var1.tbn ? var2 : var1;
            } else if (var2.hotbarSlot != var1.hotbarSlot) {
               return var2.hotbarSlot < var1.hotbarSlot ? var2 : var1;
            } else {
               return var2.itemSlot < var1.itemSlot ? var2 : var1;
            }
         } else {
            return var1 != null && var2.targetCount <= var1.targetCount ? var1 : var2;
         }
      }
   }

   private InventoryManager$3 planDropBadItem(InventoryManager$8 var1) {
      if (!this.dropBadItems.isToggled()) {
         return null;
      } else {
         for (int var2 = 0; var2 < 36; var2++) {
            if (this.isBadItemSlot(var1, var2) && (var2 >= WWb || !this.isHotbarSlotLocked(var1, var2))) {
               final int recoveredSlot = var2;
               ArrayList var4 = new ArrayList(1);
               var4.add(new InventoryManager$4(toContainerSlot(var2), 1, 4, (recoveredArg0, recoveredArg1) -> InventoryManager.canDropBadItem(recoveredSlot, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
               return new InventoryManager$3(InventoryManager$5.DROP_BAD_ITEM, 1, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, false, 0, var2, var4);
            }
         }

         return null;
      }
   }

   private InventoryManager$3 planCursorRecovery(InventoryManager$8 var1, InventoryManager$5 var2, boolean var3) {
      CursorRecoveryPlan var4 = StackUtils.planInsertion(copyInventoryStacks(var1.inventorySnapshot), var1.inventorySnapshot.itemStack, this.savedSlot, var3);
      if (var4 == null) {
         return null;
      } else {
         List var5 = var4.YVjp();
         ArrayList var6 = new ArrayList(var5.size() + (var4.hasLeftoverItems() ? 1 : 0));

         for (int var7 = 0; var7 < var5.size(); var7++) {
            int var8 = (Integer)var5.get(var7);
            boolean var9 = !var4.hasLeftoverItems() && var7 == var5.size() - 1;
            var6.add(new InventoryManager$4(toContainerSlot(var8), 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.agPl(var8, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), (recoveredArg0) -> InventoryManager.saveCursorSlot(var8, (jade.client.module.player.InventoryManager) recoveredArg0), var9 ? InventoryManager::omtegd : VTnIh));
         }

         if (var4.hasLeftoverItems()) {
            var6.add(new InventoryManager$4(-999, 0, 0, InventoryManager::GdOv, VTnIh, InventoryManager::BDjV));
         }

         return new InventoryManager$3(
            var2, var6.size(), Integer.MAX_VALUE, Integer.MAX_VALUE, 0, false, 0, var5.isEmpty() ? Integer.MAX_VALUE : (Integer)var5.get(0), var6
         );
      }
   }

   private int countResultingStackSize(InventoryManager$8 var1, int var2, ItemStack var3) {
      if (!hasStackSpace(var3)) {
         return 0;
      } else {
         int var4 = var3.stackSize;

         for (int var5 = 0; var5 < 36 && var4 < var3.getMaxStackSize(); var5++) {
            if (var5 != var2) {
               ItemStack var6 = var1.inventorySnapshot.getStackInSlot(var5);
               if (isSameItemIgnoringCount(var6, var3)) {
                  if (var5 < WWb && this.isHotbarSlotLocked(var1, var5)) {
                     return 0;
                  }

                  var4 = Math.min(var3.getMaxStackSize(), var4 + var6.stackSize);
               }
            }
         }

         return var4;
      }
   }

   private InventoryManager$1 simulateSlotStacking(InventoryManager$8 var1, int var2) {
      if (var2 >= 0 && var2 < 36 && var1.inventorySnapshot.itemStack == null) {
         ItemStack var3 = var1.inventorySnapshot.getStackInSlot(var2);
         if (var3 != null && var3.getItem() instanceof ItemBlock && var3.isStackable() && (var2 >= WWb || !this.isHotbarSlotLocked(var1, var2))) {
            ItemStack[] var4 = copyInventoryStacks(var1.inventorySnapshot);
            ItemStack var5 = var4[var2];
            int var6 = var2 < WWb ? WWb : 0;
            int var7 = var2 < WWb ? 36 : WWb;
            int var8 = 0;
            int var9 = -1;
            int var10 = 0;

            for (int var11 = var6; var11 < var7 && var5.stackSize > 0; var11++) {
               ItemStack var12 = var4[var11];
               if (isSameStackType(var12, var5) && hasStackSpace(var12)) {
                  int var13 = Math.min(var5.stackSize, var12.getMaxStackSize() - var12.stackSize);
                  var12.stackSize += var13;
                  var5.stackSize -= var13;
                  var8 += var13;
                  if (var12.stackSize > var10) {
                     var9 = var11;
                     var10 = var12.stackSize;
                  }
               }
            }

            int var18 = -1;
            boolean var19 = true;
            if (var5.stackSize > 0) {
               for (int var20 = var6; var20 < var7; var20++) {
                  if (var4[var20] == null) {
                     var18 = var20;
                     if (var20 < WWb && !this.canPlaceInHotbarSlot(var1, var20, var5)) {
                        var19 = false;
                     }

                     var4[var20] = var5.copy();
                     var5.stackSize = 0;
                     break;
                  }
               }
            }

            int var21 = var5.stackSize;
            if (var21 <= 0) {
               var4[var2] = null;
            }

            InventorySnapshot var14 = new InventorySnapshot(var4, null);
            InventoryManager$0 var15 = countStackStats(var1.inventorySnapshot, var3);
            InventoryManager$0 var16 = countStackStats(var14, var3);
            boolean var17 = var16.matchingStacks < var15.matchingStacks || var16.fullStacks > var15.fullStacks;
            return new InventoryManager$1(var14, var19, var17, var8, var21, var18, var10, var9);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static InventoryManager$0 countStackStats(InventorySnapshot var0, ItemStack var1) {
      int var2 = 0;
      int var3 = 0;

      for (int var4 = 0; var4 < 36; var4++) {
         ItemStack var5 = var0.getStackInSlot(var4);
         if (isSameStackType(var5, var1)) {
            var2++;
            if (var5.stackSize >= var5.getMaxStackSize()) {
               var3++;
            }
         }
      }

      return new InventoryManager$0(var2, var3);
   }

   private boolean canPlaceInHotbarSlot(InventoryManager$8 var1, int var2, ItemStack var3) {
      InventoryManager$7 var4 = var1.desiredItems[var2];
      if (var4 != null) {
         return ItemMatcher.matches(var4.categoryPattern, var3);
      } else if (!this.reorder.isToggled()) {
         return true;
      } else {
         List var5 = this.hotbarSlots.getCategoriesForSlot(var2);
         if (var5.isEmpty()) {
            return true;
         } else {
            for (String var7 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var5)) {
               if (ItemMatcher.matches(var7, var3)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private boolean rCyoM8(InventoryManager$8 var1, int var2, InventorySnapshot var3) {
      InventoryManager$1 var4 = this.simulateSlotStacking(var1, var2);
      return var4 != null && var4.Eufw && areSnapshotsEqual(var4.resultSnapshot, var3);
   }

   private static ItemStack[] copyInventoryStacks(InventorySnapshot var0) {
      ItemStack[] var1 = new ItemStack[36];

      for (int var2 = 0; var2 < 36; var2++) {
         ItemStack var3 = var0.getStackInSlot(var2);
         var1[var2] = var3 != null ? var3.copy() : null;
      }

      return var1;
   }

   private static boolean areSnapshotsEqual(InventorySnapshot var0, InventorySnapshot var1) {
      if (!areStacksIdentical(var0.itemStack, var1.itemStack)) {
         return false;
      } else {
         for (int var2 = 0; var2 < 36; var2++) {
            if (!areStacksIdentical(var0.getStackInSlot(var2), var1.getStackInSlot(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean areStacksIdentical(ItemStack var0, ItemStack var1) {
      return ItemStackMatcher.qjpA(var0, var1);
   }

   private void saobza8(InventorySnapshot var1, boolean var2) {
      if (mc.thePlayer.openContainer instanceof ContainerPlayer && var1.itemStack != null) {
         int var3 = this.savedSlot;

         for (int var4 = 38; var1.itemStack != null && var4-- > 0; var1 = InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory)) {
            int var5 = StackUtils.findInsertSlot(copyInventoryStacks(var1), var1.itemStack, var3);
            if (var5 < 0) {
               break;
            }

            this.savedSlot = var5;
            this.knIu(toContainerSlot(var5), 0, 0);
            var3 = -1;
         }

         if (var1.itemStack != null && var2) {
            this.knIu(-999, 0, 0);
            var1 = InventorySnapshot.captureFromPlayer(mc.thePlayer.inventory);
         }

         this.currentPlan = null;
         if (var1.itemStack == null || var2) {
            this.savedSlot = -1;
         }

         if (this.runState != InventoryManager$6.COMPLETE_LATCHED) {
            this.runState = InventoryManager$6.ACTIVE;
         }
      } else {
         this.currentPlan = null;
         if (var2) {
            this.savedSlot = -1;
         }

         if (this.runState != InventoryManager$6.COMPLETE_LATCHED) {
            this.runState = InventoryManager$6.ACTIVE;
         }
      }
   }

   private InventoryManager$7[] HkiS(InventorySnapshot var1) {
      InventoryManager$7[] var2 = new InventoryManager$7[WWb];
      if (!this.reorder.isToggled()) {
         return var2;
      } else {
         int var3 = 0;

         for (int var4 = 0; var4 < WWb; var4++) {
            List var5 = this.hotbarSlots.getCategoriesForSlot(var4);
            String var6 = this.selectSlotCategory(var1, var5);
            if (var6 == null) {
               var3 += var5.size();
            } else {
               int var7 = var5.indexOf(var6);
               var2[var4] = new InventoryManager$7(var4, var6, var3 + var7);
               var3 += var7 + 1;
            }
         }

         return var2;
      }
   }

   private String selectSlotCategory(InventorySnapshot var1, List<String> var2) {
      String var3 = null;

      for (String var5 : var2) {
         if (this.hasMatchingItem(var1, var5)) {
            var3 = var5;
            break;
         }
      }

      if (var3 != null && var2.contains("@category:snowball") && var2.contains("@category:egg")) {
         int var6 = this.getLargestCategoryStackSize(var1, "@category:snowball");
         int var7 = this.getLargestCategoryStackSize(var1, "@category:egg");
         if (var7 > var6) {
            return "@category:egg";
         } else {
            return var6 > var7 ? "@category:snowball" : var3;
         }
      } else {
         return var3;
      }
   }

   private boolean hasMatchingItem(InventorySnapshot var1, String var2) {
      return this.getLargestCategoryStackSize(var1, var2) > 0;
   }

   private int getLargestCategoryStackSize(InventorySnapshot var1, String var2) {
      int var3 = 0;

      for (int var4 = 0; var4 < 36; var4++) {
         ItemStack var5 = var1.getStackInSlot(var4);
         if (ItemMatcher.matches(var2, var5) && var5.stackSize > var3) {
            var3 = var5.stackSize;
         }
      }

      return var3;
   }

   private boolean isHotbarSlotLocked(InventoryManager$8 var1, int var2) {
      if (var2 >= 0 && var2 < WWb) {
         InventoryManager$7 var3 = var1.desiredItems[var2];
         return var3 != null && ItemMatcher.matches(var3.categoryPattern, var1.inventorySnapshot.getStackInSlot(var2));
      } else {
         return false;
      }
   }

   private boolean[] getLockedHotbarSlots(InventoryManager$8 var1) {
      boolean[] var2 = new boolean[WWb];

      for (int var3 = 0; var3 < var2.length; var3++) {
         var2[var3] = this.isHotbarSlotLocked(var1, var3);
      }

      return var2;
   }

   private boolean isWantedForHotbar(ItemStack var1) {
      if (var1 != null && this.reorder.isToggled()) {
         for (int var2 = 0; var2 < 9; var2++) {
            for (String var4 : this.hotbarSlots.getCategoriesForSlot(var2)) {
               if (ItemMatcher.matches(var4, var1)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private double NtTzm(String var1, ItemStack var2) {
      return ItemMatcher.computeMatchScore(var1, var2, true, this.durabilityCheck.isToggled());
   }

   private boolean isBadItemSlot(InventoryManager$8 var1, int var2) {
      ItemStack var3 = var1.inventorySnapshot.getStackInSlot(var2);
      if (var3 == null) {
         return false;
      } else if (var3.getItem() instanceof ItemArmor) {
         return !this.isBestArmorForSlot(var1.inventorySnapshot, var2);
      } else {
         String var4 = this.matchWeaponCategory(var3);
         if (var4 != null) {
            return !this.isBestCategoryItem(var1, var2, var4);
         } else {
            return this.isWantedForHotbar(var3) ? false : !isWantedItem(var3);
         }
      }
   }

   private String matchWeaponCategory(ItemStack var1) {
      for (String var5 : sbw) {
         if (ItemMatcher.matches(var5, var1)) {
            return var5;
         }
      }

      return null;
   }

   private boolean isBestCategoryItem(InventoryManager$8 var1, int var2, String var3) {
      InventorySnapshot var4 = var1.inventorySnapshot;
      ItemStack var5 = var4.getStackInSlot(var2);
      if (!ItemMatcher.matches(var3, var5)) {
         return false;
      } else {
         double var6 = this.NtTzm(var3, var5);
         boolean var8 = var2 < WWb && this.isHotbarSlotLocked(var1, var2);

         for (int var9 = 0; var9 < 36; var9++) {
            if (var9 != var2) {
               ItemStack var10 = var4.getStackInSlot(var9);
               if (ItemMatcher.matches(var3, var10)) {
                  double var11 = this.NtTzm(var3, var10);
                  if (var11 > var6 + 1.0E-6) {
                     return false;
                  }

                  if (Math.abs(var11 - var6) <= 1.0E-6) {
                     boolean var13 = var9 < WWb && this.isHotbarSlotLocked(var1, var9);
                     if (var13 != var8) {
                        if (var13) {
                           return false;
                        }
                     } else if (var9 < var2) {
                        return false;
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean isBestArmorForSlot(InventorySnapshot var1, int var2) {
      ItemStack var3 = var1.getStackInSlot(var2);
      int var4 = getArmorType(var3);
      if (var4 < 0) {
         return false;
      } else {
         ItemStack var5 = mc.thePlayer != null && mc.thePlayer.inventory != null ? mc.thePlayer.inventory.armorInventory[3 - var4] : null;
         return ArmorScorer.findBestArmorSlot(copyInventoryStacks(var1), var5, var4, this.durabilityCheck.isToggled()) == var2;
      }
   }

   public static boolean isWantedItem(ItemStack var0) {
      return ArmorScorer.isProtectedItem(var0);
   }

   public static int getArmorType(ItemStack var0) {
      return ArmorScorer.getArmorType(var0);
   }

   public static double getItemArmorScore(ItemStack var0) {
      int var1 = getArmorType(var0);
      return var1 < 0 ? 0.0 : ArmorScorer.scoreArmor(var0, var1, true);
   }

   public static double getWornArmorScore(int var0, boolean var1) {
      return mc != null && mc.thePlayer != null && mc.thePlayer.inventory != null && var0 >= 0 && var0 < 4
         ? ArmorScorer.scoreArmor(mc.thePlayer.inventory.armorInventory[3 - var0], var0, var1)
         : 0.0;
   }

   private boolean isSlotItemAlreadyBest(InventoryManager$8 var1, InventoryManager$7 var2, ItemStack var3) {
      if (!ItemMatcher.matches(var2.categoryPattern, var3)) {
         return false;
      } else {
         InventoryManager$2 var4 = this.findBestMatchingStack(var1, var2, var2.BHm1);
         if (var4 == null) {
            return true;
         } else {
            double var5 = this.NtTzm(var2.categoryPattern, var3);
            return var4.KUqy6 <= var5 + 1.0E-6;
         }
      }
   }

   private InventoryManager$2 findBestMatchingStack(InventoryManager$8 var1, InventoryManager$7 var2, int var3) {
      InventoryManager$2 var4 = null;

      for (int var5 = 0; var5 < 36; var5++) {
         if (var5 != var3) {
            ItemStack var6 = var1.inventorySnapshot.getStackInSlot(var5);
            if (ItemMatcher.matches(var2.categoryPattern, var6) && (var5 >= WWb || !this.isHotbarSlotLocked(var1, var5))) {
               InventoryManager$2 var7 = new InventoryManager$2(var5, this.NtTzm(var2.categoryPattern, var6), var5 < WWb ? 1 : 0, var6 != null ? var6.stackSize : 0);
               if (var4 == null || var7.isBetterThan(var4)) {
                  var4 = var7;
               }
            }
         }
      }

      return var4;
   }

   private static InventoryManager$3 pickBetterPlan(InventoryManager$3 var0, InventoryManager$3 var1) {
      if (var1 == null) {
         return var0;
      } else {
         return var0 != null && comparator.compare(var1, var0) >= 0 ? var0 : var1;
      }
   }

   private void knIu(int var1, int var2, int var3) {
      mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, var1, var2, var3, mc.thePlayer);
   }

   private boolean KykEp() {
      return ClientUtils.isInWorld() && mc.currentScreen instanceof GuiInventory && mc.thePlayer.openContainer instanceof ContainerPlayer && ClientUtils.YDGRCC();
   }

   private static boolean tmLx4(InventorySnapshot var0, int var1, ItemStack var2, ItemStack var3) {
      return ItemStackMatcher.canMergeIntoHotbar(copyInventoryStacks(var0), var1, var2, var3);
   }

   private static boolean canPlaceCursorInSlot(InventorySnapshot var0, int var1) {
      ItemStack var2 = var0.getStackInSlot(var1);
      return var2 == null || canDropCursorIntoSlot(var0, var1);
   }

   private static boolean canDropCursorIntoSlot(InventorySnapshot var0, int var1) {
      ItemStack var2 = var0.getStackInSlot(var1);
      ItemStack var3 = var0.itemStack;
      return isSameItemIgnoringCount(var2, var3) && hasStackSpace(var2);
   }

   private static boolean isSameItemIgnoringCount(ItemStack var0, ItemStack var1) {
      return ItemStackMatcher.isSameItemIgnoringCount(var0, var1);
   }

   private static boolean isSameStackType(ItemStack var0, ItemStack var1) {
      return ItemStackMatcher.isSameBlockItem(var0, var1);
   }

   private static boolean hasStackSpace(ItemStack var0) {
      return ItemStackMatcher.FBbY(var0);
   }

   private static int XLVykfb(ItemStack var0) {
      return ItemStackMatcher.getRemainingSpace(var0);
   }

   private static int toContainerSlot(int var0) {
      return ItemStackMatcher.toContainerSlot(var0);
   }

   private InventoryManager$3 planArmorEquip(InventorySnapshot var1) {
      if (mc.thePlayer != null && mc.thePlayer.inventory != null) {
         for (int var2 = 0; var2 < 4; var2++) {
            ItemStack var3 = mc.thePlayer.inventory.armorInventory[3 - var2];
            int var4 = this.findArmorUpgradeSlot(var1, var2, var3);
            if (var4 >= 0) {
               int var5 = toContainerSlot(var4);
               int var6 = 5 + var2;
               final int recoveredArmor = var2;
               if (var5 >= 0) {
                  ArrayList var9 = new ArrayList(3);
                  if (var3 == null) {
                     var9.add(new InventoryManager$4(var5, 0, 1, (recoveredArg0, recoveredArg1) -> InventoryManager.buro(recoveredArmor, var4, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, VTnIh));
                  } else {
                     var9.add(new InventoryManager$4(var6, 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canUnequipArmor(var4, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), (recoveredArg0) -> InventoryManager.saveArmorSlotForUnequip(recoveredArmor, (jade.client.module.player.InventoryManager) recoveredArg0), VTnIh));
                     var9.add(new InventoryManager$4(var5, 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.canEquipArmorFromSlot(recoveredArmor, var4, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), (recoveredArg0) -> InventoryManager.saveArmorSlotForEquip(recoveredArmor, (jade.client.module.player.InventoryManager) recoveredArg0), VTnIh));
                     var9.add(new InventoryManager$4(var6, 0, 0, (recoveredArg0, recoveredArg1) -> InventoryManager.QKCre(var4, (jade.client.module.player.InventoryManager$8) recoveredArg0, (jade.client.module.player.InventoryManager) recoveredArg1), VTnIh, InventoryManager::clearSavedSlotAfterArmorSwap));
                  }

                  return new InventoryManager$3(InventoryManager$5.EQUIP_ARMOR, var9.size(), -1, Integer.MAX_VALUE, 0, true, 0, var4, var9);
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private int findArmorUpgradeSlot(InventorySnapshot var1, int var2, ItemStack var3) {
      return ArmorScorer.wnQdxGw(copyInventoryStacks(var1), var3, var2, this.durabilityCheck.isToggled());
   }

   private static boolean RuyJ(ItemStack var0, int var1) {
      return ArmorScorer.isArmorOfType(var0, var1);
   }

   private static double computeArmorScoreWithDurability(ItemStack var0, int var1) {
      return computeArmorScore(var0, var1, true);
   }

   private static double computeArmorScore(ItemStack var0, int var1, boolean var2) {
      return ArmorScorer.scoreArmor(var0, var1, var2);
   }

   private static void noOpAfterAction(InventoryManager var0) {
   }

   private static void clearSavedSlotAfterArmorSwap(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean QKCre(int var0, InventoryManager$8 var1, InventoryManager var2) {
      ItemStack var3 = var1.inventorySnapshot.itemStack;
      return var3 != null && RuyJ(var3, var0);
   }

   private static void saveArmorSlotForEquip(int var0, InventoryManager var1) {
      var1.savedSlot = var0;
   }

   private static boolean canEquipArmorFromSlot(int var0, int var1, InventoryManager$8 var2, InventoryManager var3) {
      ItemStack var4 = var2.inventorySnapshot.getStackInSlot(var0);
      return var4 != null && RuyJ(var4, var1);
   }

   private static void saveArmorSlotForUnequip(int var0, InventoryManager var1) {
      var1.savedSlot = var0;
   }

   private static boolean canUnequipArmor(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack == null && mc.thePlayer.inventory.armorInventory[3 - var0] != null;
   }

   private static boolean buro(int var0, int var1, InventoryManager$8 var2, InventoryManager var3) {
      ItemStack var4 = var2.inventorySnapshot.getStackInSlot(var0);
      return var4 != null && RuyJ(var4, var1) && mc.thePlayer.inventory.armorInventory[3 - var1] == null;
   }

   private static void BDjV(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean GdOv(InventoryManager$8 var0, InventoryManager var1) {
      return var0.inventorySnapshot.itemStack != null;
   }

   private static void omtegd(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static void saveCursorSlot(int var0, InventoryManager var1) {
      var1.savedSlot = var0;
   }

   private static boolean agPl(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack != null && canPlaceCursorInSlot(var1.inventorySnapshot, var0);
   }

   private static boolean canDropBadItem(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack == null && var2.isBadItemSlot(var1, var0) && (var0 >= WWb || !var2.isHotbarSlotLocked(var1, var0));
   }

   private static boolean tkedf(InventorySnapshot var0, int var1, InventorySnapshot var2, InventoryManager$8 var3, InventoryManager var4) {
      return areSnapshotsEqual(var3.inventorySnapshot, var0) && var4.rCyoM8(var3, var1, var2);
   }

   private static boolean canApplyStagedStackStep(InventorySnapshot var0, int var1, InventorySnapshot var2, InventoryManager$8 var3, InventoryManager var4) {
      return areSnapshotsEqual(var3.inventorySnapshot, var0) && var4.rCyoM8(var3, var1, var2);
   }

   private static boolean canApplyStackStep(InventorySnapshot var0, int var1, InventorySnapshot var2, InventoryManager$8 var3, InventoryManager var4) {
      return areSnapshotsEqual(var3.inventorySnapshot, var0) && var4.rCyoM8(var3, var1, var2);
   }

   private static void clearSavedSlotAfterRecovery(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean canRecoverCursorIntoSourceSlot(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack != null && canPlaceCursorInSlot(var1.inventorySnapshot, var0);
   }

   private static void clearSavedSlotAfterTransfer(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean canPlaceCursorBackIntoTarget(String var0, int var1, InventoryManager$8 var2, InventoryManager var3) {
      if (var2.inventorySnapshot.itemStack != null && ItemMatcher.matches(var0, var2.inventorySnapshot.itemStack)) {
         ItemStack var4 = var2.inventorySnapshot.getStackInSlot(var1);
         return var4 == null || !ItemMatcher.matches(var0, var4) || isSameItemIgnoringCount(var2.inventorySnapshot.itemStack, var4) && hasStackSpace(var4);
      } else {
         return false;
      }
   }

   private static boolean canPickUpAllFromSlot(String var0, int var1, InventoryManager$8 var2, InventoryManager var3) {
      return var2.inventorySnapshot.itemStack != null
         && ItemMatcher.matches(var0, var2.inventorySnapshot.itemStack)
         && var2.inventorySnapshot.getStackInSlot(var1) == null
         && var3.countResultingStackSize(var2, var1, var2.inventorySnapshot.itemStack) > var2.inventorySnapshot.itemStack.stackSize;
   }

   private static void aoIt(int var0, InventoryManager var1) {
      var1.savedSlot = var0;
   }

   private static boolean canPickUpSlotIntoEmptyCursor(String var0, int var1, InventoryManager$8 var2, InventoryManager var3) {
      return var2.inventorySnapshot.itemStack == null && ItemMatcher.matches(var0, var2.inventorySnapshot.getStackInSlot(var1)) && (var1 >= WWb || !var3.isHotbarSlotLocked(var2, var1));
   }

   private static void clearSavedSlotAfterPickupAll(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean canRecoverCursorIntoEmptySlot(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack != null && canPlaceCursorInSlot(var1.inventorySnapshot, var0);
   }

   private static boolean canPickupAllIntoEmptySlot(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack != null && var1.inventorySnapshot.getStackInSlot(var0) == null && var2.countResultingStackSize(var1, var0, var1.inventorySnapshot.itemStack) > var1.inventorySnapshot.itemStack.stackSize;
   }

   private static void saveSourceSlot(int var0, InventoryManager var1) {
      var1.savedSlot = var0;
   }

   private static boolean canPickUpTargetStack(int var0, String var1, InventoryManager$8 var2, InventoryManager var3) {
      ItemStack var4 = var2.inventorySnapshot.getStackInSlot(var0);
      return var2.inventorySnapshot.itemStack == null && ItemMatcher.matches(var1, var4) && hasStackSpace(var4) && var3.countResultingStackSize(var2, var0, var4) > var4.stackSize;
   }

   private static void clearSavedSlotAfterPartialMerge(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean canRecoverCursorIntoHotbarSlot(int var0, InventoryManager$8 var1, InventoryManager var2) {
      return var1.inventorySnapshot.itemStack != null && canPlaceCursorInSlot(var1.inventorySnapshot, var0);
   }

   private static void clearSavedSlotAfterMerge(InventoryManager var0) {
      var0.savedSlot = -1;
   }

   private static boolean canMergeCursorIntoTargetStack(int var0, String var1, InventoryManager$8 var2, InventoryManager var3) {
      ItemStack var4 = var2.inventorySnapshot.getStackInSlot(var0);
      return var2.inventorySnapshot.itemStack != null && ItemMatcher.matches(var1, var4) && isSameItemIgnoringCount(var2.inventorySnapshot.itemStack, var4) && hasStackSpace(var4);
   }

   private static void saveMergeSourceSlot(int var0, InventoryManager var1) {
      var1.savedSlot = var0;
   }

   private static boolean canMergeHotbarStackIntoTarget(int var0, int var1, String var2, InventoryManager$8 var3, InventoryManager var4) {
      ItemStack var5 = var3.inventorySnapshot.getStackInSlot(var0);
      ItemStack var6 = var3.inventorySnapshot.getStackInSlot(var1);
      return var3.inventorySnapshot.itemStack == null && ItemMatcher.matches(var2, var6) && isSameItemIgnoringCount(var5, var6) && hasStackSpace(var6) && !var4.isHotbarSlotLocked(var3, var0);
   }

   private static boolean canPullInventoryStackIntoTarget(int var0, int var1, String var2, InventoryManager$8 var3, InventoryManager var4) {
      ItemStack var5 = var3.inventorySnapshot.getStackInSlot(var0);
      ItemStack var6 = var3.inventorySnapshot.getStackInSlot(var1);
      return var3.inventorySnapshot.itemStack == null && ItemMatcher.matches(var2, var6) && isSameItemIgnoringCount(var5, var6) && hasStackSpace(var6) && tmLx4(var3.inventorySnapshot, var1, var5, var6);
   }

   private static boolean Rdk9(InventoryManager$7 var0, int var1, InventoryManager$8 var2, InventoryManager var3) {
      return var2.inventorySnapshot.itemStack == null && ItemMatcher.matches(var0.categoryPattern, var2.inventorySnapshot.getStackInSlot(var1)) && (var1 >= WWb || !var3.isHotbarSlotLocked(var2, var1));
   }
}
