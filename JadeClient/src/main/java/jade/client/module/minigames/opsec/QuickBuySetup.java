// Jade recovery: original class: jade.deps.eLz.fGXd54Wf
package jade.client.module.minigames.opsec;

import jade.client.common.ClientUtils;
import jade.client.common.ItemNames;
import jade.client.common.OpsecStore;
import jade.client.common.QuickBuyLayout;
import jade.client.module.minigames.Opsec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class QuickBuySetup {
   private static final long BASE_TIMEOUT_MILLIS = 8000L;
   private static final long jcK = 1000L;
   private static final long UUS = 750L;
   private static final long zUh = 5000L;
   private static final long MAX_LATENCY_MILLIS = 5000L;
   private static final long STn = 175L;
   private final Opsec opsec;
   private final OpsecStore opsecStore;
   private final Minecraft mc = Minecraft.getMinecraft();
   private final Map<String, Integer> cataloguePageByItem = new LinkedHashMap<>();
   private QuickBuySetup$1 IDF = QuickBuySetup$1.IDLE;
   private GuiScreen guiScreen;
   private String targetPlayerName = "";
   private List<String> dM5 = Collections.emptyList();
   private List<String> previewLayout = Collections.emptyList();
   private List<String> WmH = Collections.emptyList();
   private List<String> targetLayout = Collections.emptyList();
   private List<String> originalLayout = Collections.emptyList();
   private List<Integer> quickBuyGridSlots = Collections.emptyList();
   private long Lehiv;
   private long nextClickAllowedTime;
   private boolean AXzhuc;
   private long iTa;
   private int clickedWindowId;
   private String clickedContainerSignature = "";
   private int cataloguePage;
   private int currentSlotIndex;
   private int cFqyF;
   private boolean layoutBeingCreated;
   private boolean UZTwBo;
   private boolean reopenScreenAfterRestore;
   private boolean larpMode;
   private String restoreMessage = "";

   public QuickBuySetup(Opsec var1, OpsecStore var2) {
      this.opsec = var1;
      this.opsecStore = var2;
   }

   public void Ils20(GuiScreen var1, String var2, List<String> var3) {
      this.beginSetup(var1, var2, var3, Collections.emptyList(), false);
   }

   public void mkQi(GuiScreen var1, String var2, List<String> var3, List<String> var4) {
      this.beginSetup(var1, var2, var3, var4, true);
   }

   private void beginSetup(GuiScreen var1, String var2, List<String> var3, List<String> var4, boolean var5) {
      if (!this.isSetupActive()) {
         this.guiScreen = var1;
         this.targetPlayerName = var2 == null ? "" : var2.trim();
         this.dM5 = immutableItemList(var3);
         this.previewLayout = immutableItemList(var3);
         this.WmH = immutableItemList(var4);
         this.targetLayout = Collections.emptyList();
         this.originalLayout = Collections.emptyList();
         this.quickBuyGridSlots = Collections.emptyList();
         this.cataloguePageByItem.clear();
         this.cataloguePage = 0;
         this.currentSlotIndex = 0;
         this.layoutBeingCreated = false;
         this.UZTwBo = false;
         this.reopenScreenAfterRestore = false;
         this.larpMode = var5;
         this.restoreMessage = "";
         this.mc.displayGuiScreen(null);
         this.mc.thePlayer.sendChatMessage("/settings");
         this.enterState(QuickBuySetup$1.WAIT_SETTINGS);
      }
   }

   public void beginRestoreSetup(GuiScreen var1, String var2, List<String> var3, List<String> var4) {
      if (!this.isSetupActive()) {
         this.guiScreen = var1;
         this.targetPlayerName = var2 == null ? "" : var2.trim();
         this.dM5 = immutableItemList(var3);
         this.previewLayout = immutableItemList(var3);
         this.WmH = Collections.emptyList();
         this.targetLayout = immutableItemList(var4);
         this.originalLayout = immutableItemList(var4);
         this.quickBuyGridSlots = Collections.emptyList();
         this.cataloguePageByItem.clear();
         this.cataloguePage = 0;
         this.currentSlotIndex = 0;
         this.layoutBeingCreated = true;
         this.UZTwBo = true;
         this.reopenScreenAfterRestore = true;
         this.larpMode = false;
         this.restoreMessage = "Interrupted Quick Buy restored. Review the preview and press Apply again.";
         this.mc.displayGuiScreen(null);
         this.mc.thePlayer.sendChatMessage("/settings");
         this.enterState(QuickBuySetup$1.WAIT_SETTINGS);
      }
   }

   public void tickSetup() {
      if (this.isSetupActive()) {
         long var1 = System.currentTimeMillis();
         if (!ClientUtils.isInWorld()) {
            this.failSetup("Setup interrupted by disconnect; recovery is still required.");
         } else if (var1 > this.Lehiv) {
            this.handleFailure("Timed out while " + QuickBuySetup$1.access$000(this.IDF) + ".");
         } else {
            ContainerChest var3 = this.getOpenChestContainer();
            if (this.AXzhuc) {
               if (var3 == null) {
                  return;
               }

               String var4 = this.uagrk(var3);
               boolean var5 = var3.windowId != this.clickedWindowId || !var4.equals(this.clickedContainerSignature);
               boolean var6 = this.IDF == QuickBuySetup$1.WAIT_PREFLIGHT_GRID || this.IDF == QuickBuySetup$1.WAIT_FINAL_GRID;
               if (!QPc3(var5, var6, var1, this.iTa)) {
                  return;
               }

               this.AXzhuc = false;
            }

            switch (this.IDF) {
               case WAIT_SETTINGS:
                  this.clickMenuEntry(var3, "Bed Wars Settings", Item.getItemFromBlock(Blocks.bed), QuickBuySetup$1.WAIT_BEDWARS_SETTINGS);
                  break;
               case WAIT_BEDWARS_SETTINGS:
                  this.clickMenuEntry(var3, "Edit Quick Buy", Items.nether_star, QuickBuySetup$1.WAIT_INITIAL_GRID);
                  break;
               case WAIT_INITIAL_GRID:
                  this.handleInitialGrid(var3);
                  break;
               case WAIT_PREFLIGHT_CATALOGUE:
                  this.handlePreflightCatalogue(var3);
                  break;
               case CRAWL_CATALOGUE:
                  this.handleCatalogueCrawl(var3);
                  break;
               case WAIT_PREFLIGHT_GRID:
                  this.handlePreflightGrid(var3);
                  break;
               case PLACE_AT_GRID:
                  this.SExCryN(var3);
                  break;
               case WAIT_PLACE_CATALOGUE:
                  this.handlePlaceCatalogue(var3);
                  break;
               case NAVIGATE_CATALOGUE:
                  this.handleCatalogueNavigation(var3);
                  break;
               case WAIT_PLACED_GRID:
                  this.uafiM(var3);
                  break;
               case WAIT_FINAL_CATALOGUE:
                  this.handleFinalCatalogue(var3);
                  break;
               case WAIT_FINAL_GRID:
                  this.handleFinalGrid(var3);
            }
         }
      }
   }

   public boolean isSetupActive() {
      return this.IDF != QuickBuySetup$1.IDLE;
   }

   public String getProgressText() {
      int var1 = Math.min(this.currentSlotIndex + 1, 21);
      if (this.UZTwBo) {
         return "Restoring... (" + var1 + "/" + 21 + ")";
      } else {
         return this.layoutBeingCreated ? "Setting up... (" + var1 + "/" + 21 + ")" : "Finding Quick Buy...";
      }
   }

   public void cancelSetup() {
      if (this.isSetupActive()) {
         QuickBuyRecoveryPlanner$0 var1 = QuickBuyRecoveryPlanner.decideCancelAction(this.layoutBeingCreated, this.UZTwBo);
         if (var1 == QuickBuyRecoveryPlanner$0.RESTORE) {
            this.qfyaX9("Setup cancelled; the original Quick Buy was restored.");
         } else if (var1 == QuickBuyRecoveryPlanner$0.STOP) {
            this.resetToIdle();
            this.Oljt();
            this.opsec.sendFailureMessage("Setup cancelled before any changes were made.");
         }
      }
   }

   public void yflom9() {
      if (this.isSetupActive()) {
         this.failSetup(
            this.layoutBeingCreated
               ? "Setup stopped because the module was disabled; recovery remains pending."
               : "Setup cancelled before any server-side changes were made."
         );
      }
   }

   private void handleInitialGrid(ContainerChest var1) {
      List var2 = QuickBuySlotReader.findQuickBuyGridSlots(var1);
      if (var2.size() == 21) {
         this.quickBuyGridSlots = immutableSlotList(var2);
         List var3 = QuickBuySlotReader.readItemNamesAt(var1, this.quickBuyGridSlots);
         if (var3.size() == 21) {
            if (this.UZTwBo && !this.cataloguePageByItem.isEmpty()) {
               this.targetLayout = this.originalLayout;
               this.currentSlotIndex = 0;
               this.enterState(QuickBuySetup$1.PLACE_AT_GRID);
            } else {
               if (!this.UZTwBo) {
                  this.originalLayout = immutableItemList(var3);
               }

               if (this.clickSlot(var1, this.quickBuyGridSlots.get(0), 0)) {
                  this.enterState(QuickBuySetup$1.WAIT_PREFLIGHT_CATALOGUE);
               }
            }
         }
      }
   }

   private void handlePreflightCatalogue(ContainerChest var1) {
      if (this.VkEa(var1)) {
         int var2 = this.findSlotByLore(var1, "right-click for first page");
         this.cataloguePageByItem.clear();
         this.cataloguePage = 0;
         if (var2 >= 0) {
            if (this.clickSlot(var1, var2, 1)) {
               this.enterState(QuickBuySetup$1.CRAWL_CATALOGUE);
            }
         } else {
            this.enterState(QuickBuySetup$1.CRAWL_CATALOGUE);
         }
      }
   }

   private void handleCatalogueCrawl(ContainerChest var1) {
      if (this.VkEa(var1)) {
         int var2 = this.findSlotByName(var1, "Go Back", null);
         if (var2 >= 0) {
            this.indexCataloguePage(var1, this.cataloguePage);
            int var3 = this.findSlotByLore(var1, "left-click for next page");
            if (var3 >= 0) {
               boolean var4 = this.clickSlot(var1, var3, 0);
               this.cataloguePage = QuickBuyCatalogue.incrementPageIf(this.cataloguePage, var4);
               if (var4) {
                  this.enterState(QuickBuySetup$1.CRAWL_CATALOGUE);
               }
            } else {
               if (this.clickSlot(var1, var2, 0)) {
                  this.enterState(QuickBuySetup$1.WAIT_PREFLIGHT_GRID);
               }
            }
         }
      }
   }

   private void handlePreflightGrid(ContainerChest var1) {
      List var2 = QuickBuySlotReader.findQuickBuyGridSlots(var1);
      if (var2.size() != 21) {
         this.TRp2(var1);
      } else {
         this.quickBuyGridSlots = immutableSlotList(var2);
         List var3 = QuickBuySlotReader.readItemNamesAt(var1, this.quickBuyGridSlots);
         if (this.UZTwBo) {
            this.targetLayout = this.originalLayout;
            this.currentSlotIndex = 0;
            this.enterState(QuickBuySetup$1.PLACE_AT_GRID);
         } else {
            LinkedHashSet var4 = new LinkedHashSet<>(this.cataloguePageByItem.keySet());
            List var5 = QuickBuyLayout.buildLayout(this.dM5, var3, var4);
            if (!QuickBuyLayout.isValidLayout(var5)) {
               this.handleFailure("The live editor did not contain 21 usable unique Quick Buy items.");
            } else if (!var5.equals(this.dM5)) {
               this.resetToIdle();
               this.opsec.openQuickBuyCopyWithMessage(this.guiScreen, this.targetPlayerName, var5, "Live editor fallbacks changed the preview. Review and press Apply again.");
            } else {
               this.previewLayout = immutableItemList(var5);
               if (this.larpMode) {
                  if (!QuickBuyLayout.ywZww(this.previewLayout, this.WmH)) {
                     this.handleFailure("The Larp Quick Buy no longer matches the desired item set.");
                     return;
                  }

                  this.targetLayout = this.WmH;
               } else {
                  this.targetLayout = immutableItemList(QuickBuyLayout.shuffleLayout(this.previewLayout));
               }

               UUID var6 = this.getPlayerUuid();
               if (!this.opsecStore.tBxg(var6, this.originalLayout)) {
                  this.handleFailure("Could not persist the recovery snapshot; no server-side changes were made.");
               } else {
                  this.layoutBeingCreated = true;
                  this.currentSlotIndex = 0;
                  this.enterState(QuickBuySetup$1.PLACE_AT_GRID);
               }
            }
         }
      }
   }

   private void SExCryN(ContainerChest var1) {
      List var2 = QuickBuySlotReader.findQuickBuyGridSlots(var1);
      if (var2.size() == 21) {
         this.quickBuyGridSlots = immutableSlotList(var2);
         List var3 = QuickBuySlotReader.readItemNamesAt(var1, this.quickBuyGridSlots);
         if (var3.size() == 21) {
            while (this.currentSlotIndex < 21 && this.targetLayout.get(this.currentSlotIndex).equals(var3.get(this.currentSlotIndex))) {
               this.currentSlotIndex++;
            }

            if (this.currentSlotIndex >= 21) {
               if (this.clickSlot(var1, this.quickBuyGridSlots.get(0), 0)) {
                  this.enterState(QuickBuySetup$1.WAIT_FINAL_CATALOGUE);
               }
            } else {
               String var4 = this.targetLayout.get(this.currentSlotIndex);
               int var5 = this.quickBuyGridSlots.get(this.currentSlotIndex);
               if (var4.isEmpty()) {
                  if (this.clickSlot(var1, var5, 1)) {
                     this.enterState(QuickBuySetup$1.WAIT_PLACED_GRID);
                  }
               } else if (!this.cataloguePageByItem.containsKey(var4)) {
                  this.handleFailure("The editor catalogue no longer contains " + var4 + ".");
               } else {
                  if (this.clickSlot(var1, var5, 0)) {
                     this.enterState(QuickBuySetup$1.WAIT_PLACE_CATALOGUE);
                  }
               }
            }
         }
      }
   }

   private void handlePlaceCatalogue(ContainerChest var1) {
      if (this.VkEa(var1)) {
         String var2 = this.targetLayout.get(this.currentSlotIndex);
         this.cFqyF = this.cataloguePageByItem.get(var2);
         int var3 = this.findSlotByLore(var1, "right-click for first page");
         if (var3 >= 0) {
            if (this.clickSlot(var1, var3, 1)) {
               this.enterState(QuickBuySetup$1.NAVIGATE_CATALOGUE);
            }
         } else {
            this.enterState(QuickBuySetup$1.NAVIGATE_CATALOGUE);
         }
      }
   }

   private void handleCatalogueNavigation(ContainerChest var1) {
      if (this.VkEa(var1)) {
         if (this.findSlotByName(var1, "Go Back", null) >= 0) {
            if (this.cFqyF > 0) {
               int var4 = this.findSlotByLore(var1, "left-click for next page");
               if (var4 >= 0) {
                  boolean var3 = this.clickSlot(var1, var4, 0);
                  this.cFqyF = QuickBuyCatalogue.decrementPageIf(this.cFqyF, var3);
                  if (var3) {
                     this.enterState(QuickBuySetup$1.NAVIGATE_CATALOGUE);
                  }
               }
            } else {
               int var2 = this.findCatalogueSlot(var1, this.targetLayout.get(this.currentSlotIndex));
               if (var2 >= 0) {
                  if (this.clickSlot(var1, var2, 0)) {
                     this.enterState(QuickBuySetup$1.WAIT_PLACED_GRID);
                  }
               }
            }
         }
      }
   }

   private void uafiM(ContainerChest var1) {
      List var2 = QuickBuySlotReader.findQuickBuyGridSlots(var1);
      if (var2.size() == 21) {
         this.quickBuyGridSlots = immutableSlotList(var2);
         List var3 = QuickBuySlotReader.readItemNamesAt(var1, this.quickBuyGridSlots);
         if (var3.size() == 21) {
            if (!QuickBuyGridUtils.isSlotItemMatchingName(var3, this.currentSlotIndex, this.targetLayout.get(this.currentSlotIndex))) {
               this.handleFailure("Hypixel did not confirm the replacement for Quick Buy position " + (this.currentSlotIndex + 1) + ".");
            } else {
               this.currentSlotIndex++;
               this.enterState(QuickBuySetup$1.PLACE_AT_GRID);
            }
         }
      }
   }

   private void handleFinalCatalogue(ContainerChest var1) {
      if (this.VkEa(var1)) {
         int var2 = this.findSlotByName(var1, "Go Back", null);
         if (var2 >= 0) {
            if (this.clickSlot(var1, var2, 0)) {
               this.enterState(QuickBuySetup$1.WAIT_FINAL_GRID);
            }
         }
      }
   }

   private void handleFinalGrid(ContainerChest var1) {
      List var2 = QuickBuySlotReader.findQuickBuyGridSlots(var1);
      if (var2.size() != 21) {
         this.TRp2(var1);
      } else {
         this.quickBuyGridSlots = immutableSlotList(var2);
         this.verifyFinalLayout(QuickBuySlotReader.readItemNamesAt(var1, this.quickBuyGridSlots));
      }
   }

   private void TRp2(ContainerChest var1) {
      if (var1 != null) {
         if (this.VkEa(var1)) {
            int var4 = this.findSlotByName(var1, "Go Back", null);
            if (var4 >= 0) {
               this.clickSlot(var1, var4, 0);
            }
         } else {
            int var2 = this.findSlotByName(var1, "Edit Quick Buy", Items.nether_star);
            if (var2 >= 0) {
               this.clickSlot(var1, var2, 0);
            } else {
               int var3 = this.findSlotByName(var1, "Bed Wars Settings", Item.getItemFromBlock(Blocks.bed));
               if (var3 >= 0) {
                  this.clickSlot(var1, var3, 0);
               }
            }
         }
      }
   }

   private void verifyFinalLayout(List<String> var1) {
      if (!var1.equals(this.targetLayout)) {
         this.handleFailure("Final Quick Buy verification did not match the requested layout.");
      } else {
         UUID var2 = this.getPlayerUuid();
         if (this.UZTwBo) {
            if (!this.opsecStore.jeDf6(var2)) {
               this.failSetup("Quick Buy was restored, but its recovery marker could not be cleared.");
            } else {
               String var8 = this.restoreMessage.isEmpty() ? "Setup cancelled and restored." : this.restoreMessage;
               boolean var4 = this.reopenScreenAfterRestore;
               GuiScreen var5 = this.guiScreen;
               String var6 = this.targetPlayerName;
               List var7 = this.dM5;
               this.resetToIdle();
               if (var4) {
                  this.opsec.openQuickBuyCopyWithMessage(var5, var6, var7, var8);
               } else {
                  this.Oljt();
                  this.opsec.sendFailureMessage(var8);
               }
            }
         } else {
            boolean var3 = this.larpMode ? QuickBuyLayout.ywZww(this.previewLayout, this.targetLayout) : QuickBuyLayout.isDerangement(this.previewLayout, this.targetLayout);
            if (!var3) {
               this.handleFailure(
                  this.larpMode ? "Generated Larp Quick Buy did not preserve the desired item set." : "Generated Quick Buy was not a complete derangement."
               );
            } else if (!this.opsecStore.markAccountVerified(var2, this.targetPlayerName, this.previewLayout, this.targetLayout)) {
               this.failSetup("Quick Buy was changed, but verified state could not be saved; recovery remains pending.");
            } else {
               this.resetToIdle();
               this.Oljt();
               this.opsec.onSetupVerified();
            }
         }
      }
   }

   private void qfyaX9(String var1) {
      if (!this.UZTwBo && this.originalLayout.size() == 21) {
         this.UZTwBo = true;
         this.restoreMessage = var1;
         this.targetLayout = this.originalLayout;
         this.currentSlotIndex = 0;
         this.layoutBeingCreated = true;
         this.AXzhuc = false;
         this.Oljt();
         if (ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 0) {
            this.mc.thePlayer.sendChatMessage("/settings");
            this.enterState(QuickBuySetup$1.WAIT_SETTINGS);
         } else {
            this.failSetup("Could not restore now; recovery remains pending for the next Setup.");
         }
      } else {
         this.failSetup(var1);
      }
   }

   private void handleFailure(String var1) {
      QuickBuyRecoveryPlanner$0 var2 = QuickBuyRecoveryPlanner.decideFailureAction(this.layoutBeingCreated, this.UZTwBo, ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 0);
      if (var2 == QuickBuyRecoveryPlanner$0.KEEP_RECOVERY) {
         this.failSetup((this.UZTwBo ? "Rollback failed: " : "") + var1 + " Recovery remains pending.");
      } else if (var2 == QuickBuyRecoveryPlanner$0.RESTORE) {
         this.qfyaX9(var1 + " The original layout was restored.");
      } else {
         this.resetToIdle();
         this.Oljt();
         this.opsec.sendFailureMessage(var1);
      }
   }

   private void failSetup(String var1) {
      this.resetToIdle();
      this.Oljt();
      this.opsec.sendFailureMessage(var1);
   }

   private void clickMenuEntry(ContainerChest var1, String var2, Item var3, QuickBuySetup$1 var4) {
      int var5 = this.findSlotByName(var1, var2, var3);
      if (var5 >= 0 && this.clickSlot(var1, var5, 0)) {
         this.enterState(var4);
      }
   }

   private int findSlotByName(ContainerChest var1, String var2, Item var3) {
      if (var1 == null) {
         return -1;
      } else {
         int var4 = var1.getLowerChestInventory().getSizeInventory();

         for (int var5 = 0; var5 < var4; var5++) {
            ItemStack var6 = var1.getLowerChestInventory().getStackInSlot(var5);
            if (var6 != null && QuickBuyGridUtils.BebK(ItemNames.getCleanDisplayName(var6), var3 == null || var6.getItem() == var3, var2)) {
               return var5;
            }
         }

         return -1;
      }
   }

   private int findSlotByLore(ContainerChest var1, String var2) {
      if (var1 == null) {
         return -1;
      } else {
         int var3 = var1.getLowerChestInventory().getSizeInventory();

         for (int var4 = 0; var4 < var3; var4++) {
            if (ItemNames.tooltipContains(var1.getLowerChestInventory().getStackInSlot(var4), var2)) {
               return var4;
            }
         }

         return -1;
      }
   }

   private int findCatalogueSlot(ContainerChest var1, String var2) {
      if (var1 == null) {
         return -1;
      } else {
         int var3 = var1.getLowerChestInventory().getSizeInventory();

         for (int var4 = 0; var4 < var3; var4++) {
            ItemStack var5 = var1.getLowerChestInventory().getStackInSlot(var4);
            if (ItemNames.tooltipContains(var5, "click to add this item to your quick") && var2.equals(ItemNames.getSkyblockId(var5))) {
               return var4;
            }
         }

         return -1;
      }
   }

   private void indexCataloguePage(ContainerChest var1, int var2) {
      int var3 = var1.getLowerChestInventory().getSizeInventory();
      ArrayList var4 = new ArrayList();

      for (int var5 = 0; var5 < var3; var5++) {
         ItemStack var6 = var1.getLowerChestInventory().getStackInSlot(var5);
         if (ItemNames.tooltipContains(var6, "click to add this item to your quick")) {
            String var7 = ItemNames.getSkyblockId(var6);
            if (!var7.isEmpty()) {
               var4.add(var7);
            }
         }
      }

      QuickBuyCatalogue.XQxM(this.cataloguePageByItem, var4, var2);
   }

   private boolean VkEa(ContainerChest var1) {
      if (var1 == null) {
         return false;
      } else {
         int var2 = var1.getLowerChestInventory().getSizeInventory();

         for (int var3 = 0; var3 < var2; var3++) {
            if (ItemNames.tooltipContains(var1.getLowerChestInventory().getStackInSlot(var3), "click to add this item to your quick")) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean clickSlot(ContainerChest var1, int var2, int var3) {
      long var4 = System.currentTimeMillis();
      if (var1 != null && var4 >= this.nextClickAllowedTime && var2 >= 0 && var2 < var1.getLowerChestInventory().getSizeInventory()) {
         this.clickedWindowId = var1.windowId;
         this.clickedContainerSignature = this.uagrk(var1);
         this.AXzhuc = true;
         this.iTa = var4 + getClickConfirmWaitMillis(this.opsec.getOwnPing(), this.opsec.KZsT());
         this.mc.playerController.windowClick(var1.windowId, var2, var3, 0, this.mc.thePlayer);
         this.nextClickAllowedTime = var4 + 175L;
         return true;
      } else {
         return false;
      }
   }

   public static boolean QPc3(boolean var0, boolean var1, long var2, long var4) {
      return var0 || var1 && var2 >= var4;
   }

   public static long getClickConfirmWaitMillis(long var0, long var2) {
      long var4 = aMew(var0, var2);
      return Math.max(1000L, var4 + 750L);
   }

   public static long getStateTimeoutMillis(long var0, long var2) {
      return 8000L + aMew(var0, var2) * 4L;
   }

   private static long aMew(long var0, long var2) {
      long var4 = Math.max(0L, Math.min(5000L, var0));
      long var6 = Math.max(0L, Math.min(5000L, var2));
      return var4 + var6;
   }

   private ContainerChest getOpenChestContainer() {
      return this.mc.currentScreen instanceof GuiChest && this.mc.thePlayer.openContainer instanceof ContainerChest
         ? (ContainerChest)this.mc.thePlayer.openContainer
         : null;
   }

   private String uagrk(ContainerChest var1) {
      if (var1 == null) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1.windowId).append('|');
         int var3 = var1.getLowerChestInventory().getSizeInventory();

         for (int var4 = 0; var4 < var3; var4++) {
            ItemStack var5 = var1.getLowerChestInventory().getStackInSlot(var4);
            var2.append(ItemNames.getSkyblockId(var5)).append(':').append(ItemNames.getCleanDisplayName(var5)).append(';');
         }

         return var2.toString();
      }
   }

   private UUID getPlayerUuid() {
      return ClientUtils.isInWorld() ? this.mc.thePlayer.getUniqueID() : null;
   }

   private void Oljt() {
      if (ClientUtils.isInWorld() && this.mc.thePlayer.openContainer != this.mc.thePlayer.inventoryContainer) {
         this.mc.thePlayer.closeScreen();
      } else {
         this.mc.displayGuiScreen(null);
      }
   }

   private void enterState(QuickBuySetup$1 var1) {
      this.IDF = var1;
      this.Lehiv = System.currentTimeMillis() + getStateTimeoutMillis(this.opsec.getOwnPing(), this.opsec.KZsT());
   }

   private void resetToIdle() {
      this.IDF = QuickBuySetup$1.IDLE;
      this.AXzhuc = false;
      this.iTa = 0L;
      this.nextClickAllowedTime = 0L;
      this.cataloguePageByItem.clear();
      this.layoutBeingCreated = false;
      this.UZTwBo = false;
      this.reopenScreenAfterRestore = false;
      this.larpMode = false;
      this.WmH = Collections.emptyList();
   }

   private static List<String> immutableItemList(List<String> var0) {
      return var0 == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(var0));
   }

   private static List<Integer> immutableSlotList(List<Integer> var0) {
      return var0 == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(var0));
   }
}
