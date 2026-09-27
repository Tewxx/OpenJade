// Jade recovery: module: Opsec (minigames); original class: jade.deps.eLz.NbwpOt8
package jade.client.module.minigames;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.PacketReleaseGate;
import jade.client.common.OpsecStore$0;
import jade.client.common.OpsecStore;
import jade.client.common.PacketDirection;
import jade.client.common.QuickBuyLayout;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.TickEndEvent;
import jade.client.gui.QuickBuyCopyScreen;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.opsec.QuickBuyEditCooldown;
import jade.client.module.minigames.opsec.QuickBuySetup;
import jade.client.module.minigames.opsec.QuickBuySlotReader;
import jade.client.module.minigames.opsec.WDHhrTSdi;
import jade.client.module.minigames.opsec.oeqxjyc2;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.HypixelPlayerStats;
import jade.deps.loader107.StatsLookupService;
import jade.deps.loader107.AdditiveMixConstantCipherTwo;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

@ModuleInfo
public final class Opsec extends Module {
   private final BooleanSetting antiQuickbuyIdentify;
   private final BooleanSetting antiPingIdentify;
   private final SliderSetting pingBoost;
   private final OpsecStore opsecStore = new OpsecStore();
   private final QuickBuySetup quickBuySetup = new QuickBuySetup(this, this.opsecStore);
   private final QuickBuyEditCooldown editCooldown = new QuickBuyEditCooldown();
   private PacketListenerRegistration GNCBpp;
   private long zfC = AdditiveMixConstantCipherTwo.decodeLong(-7009601930906264321L, 516477743);
   private boolean inBedwarsLobby;
   private Opsec$4 cachedQuickBuySnapshot;
   private int XkYyfU = Integer.MIN_VALUE;
   private int lastWindowId = Integer.MIN_VALUE;
   private long windowOpenedAtMillis;

   public Opsec() {
      super("Opsec", Category.minigames);
      this.registerSetting(new DescriptionSetting("Hide identifying server-side Bed Wars layouts."));
      this.registerSetting(this.antiQuickbuyIdentify = new BooleanSetting("Anti Quickbuy Identify", true));
      this.registerSetting(
         this.antiPingIdentify = new BooleanSetting(
            "Anti Ping Identify", false
         )
      );
      this.pingBoost = new SliderSetting(
         "Ping Boost", "ms", 100.0, 50.0, 1000.0, 50.0
      );
      this.pingBoost.visible = false;
      this.registerSetting(this.pingBoost);
      this.registerSetting(new BooleanSetting("Setup", new Runnable() {
         @Override
         public void run() {
            Opsec.openQuickBuyCopyScreen(Opsec.this, Opsec.getMc().currentScreen, null, null);
         }
      }).setButtonText("SETUP"));
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.updateAntiPingGate();
   }

   @Override
   public void onDisable() {
      this.inBedwarsLobby = false;
      this.ojyUjf();
      this.cachedQuickBuySnapshot = null;
      this.editCooldown.reset();
      if (this.quickBuySetup.isSetupActive()) {
         this.quickBuySetup.yflom9();
      }
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.antiPingIdentify) {
         this.pingBoost.setVisible(this.antiPingIdentify.isToggled(), this);
         this.updateAntiPingGate();
      }

      if (var1 == this.antiQuickbuyIdentify && !this.antiQuickbuyIdentify.isToggled()) {
         this.cachedQuickBuySnapshot = null;
         this.editCooldown.reset();
         if (this.quickBuySetup.isSetupActive()) {
            this.quickBuySetup.yflom9();
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         this.quickBuySetup.tickSetup();
         this.updateAntiPingGate();
         if (this.GNCBpp != null) {
            Jade.nbT.Cvuz(PacketDirection.INBOUND, (long)this.pingBoost.getInput());
            Jade.nbT.Cvuz(PacketDirection.OUTBOUND, (long)this.pingBoost.getInput());
         }
      }
   }

   @Override
   public void guiUpdate() {
      this.pingBoost.setVisible(this.antiPingIdentify.isToggled(), this);
   }

   private void updateAntiPingGate() {
      if (this.isEnabled() && this.antiPingIdentify.isToggled()) {
         if (this.GNCBpp != null && Jade.nbT != null && this.zfC != Jade.nbT.weqo1()) {
            this.ojyUjf();
         }

         Opsec$2 var1 = this.detectGameState();
         this.inBedwarsLobby = var1 == Opsec$2.BEDWARS_LOBBY;
         if (!this.inBedwarsLobby || mc.getNetHandler() == null || Jade.nbT == null) {
            this.ojyUjf();
         } else if (this.GNCBpp == null) {
            this.GNCBpp = new PacketListenerRegistration(PacketDirection.BIDIRECTIONAL, new PacketReleaseGate() {
               @Override
               protected boolean shouldRelease() {
                  return !Opsec.this.isEnabled() || !Opsec.getAntiPingIdentifySetting(Opsec.this).isToggled() || !Opsec.isInBedwarsLobby(Opsec.this);
               }
            });
            Jade.nbT.JUlwlNu(this.GNCBpp);
            this.zfC = Jade.nbT.weqo1();
         }
      } else {
         this.inBedwarsLobby = false;
         this.ojyUjf();
      }
   }

   private void ojyUjf() {
      if (this.GNCBpp != null) {
         this.GNCBpp.getPacketHandler().forceOpen();
         this.GNCBpp = null;
         this.zfC = -1L;
      }
   }

   public long KZsT() {
      return this.GNCBpp != null ? Math.max(0L, (long)this.pingBoost.getInput()) : 0L;
   }

   public long getOwnPing() {
      if (ClientUtils.isInWorld() && mc.getNetHandler() != null) {
         NetworkPlayerInfo var1 = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
         return var1 == null ? 0L : Math.max(0L, (long)var1.getResponseTime());
      } else {
         return 0L;
      }
   }

   private Opsec$2 detectGameState() {
      if (!ClientUtils.isInWorld()) {
         return Opsec$2.UNKNOWN;
      } else if (ClientUtils.isOnHypixel() && !ClientUtils.mdeQ()) {
         int var1 = ClientUtils.getBedWarsBoardType();
         if (var1 == 0) {
            return Opsec$2.BEDWARS_LOBBY;
         } else if (var1 != 1 && var1 != 2) {
            Scoreboard var2 = mc.theWorld.getScoreboard();
            if (var2 == null) {
               return Opsec$2.UNKNOWN;
            } else {
               ScoreObjective var3 = var2.getObjectiveInDisplaySlot(1);
               if (var3 == null) {
                  return Opsec$2.UNKNOWN;
               } else {
                  String var4 = ClientUtils.zaUnpz(var3.getDisplayName());
                  return var4.contains("BED WARS") ? Opsec$2.UNKNOWN : Opsec$2.NOT_BEDWARS_LOBBY;
               }
            }
         } else {
            return Opsec$2.NOT_BEDWARS_LOBBY;
         }
      } else {
         return Opsec$2.NOT_BEDWARS_LOBBY;
      }
   }

   public ItemStack Bxn6(Slot var1, ItemStack var2) {
      Opsec$4 var3 = this.XhWufg();
      if (var3 != null && var1 != null) {
         int var4 = Opsec$4.getLiveGridSlots(var3).indexOf(var1.slotNumber);
         if (var4 < 0) {
            return var2;
         } else {
            ItemStack var5 = (ItemStack)Opsec$4.CrXz(var3).get(var4);
            return var5 == null ? var2 : var5;
         }
      } else {
         return var2;
      }
   }

   public boolean axClaj(Slot var1, int var2, int var3, int var4) {
      Opsec$4 var5 = this.XhWufg();
      if (var5 != null && var1 != null) {
         int var6 = Opsec$4.getLiveGridSlots(var5).indexOf(var2);
         if (var6 < 0) {
            return false;
         } else {
            int var7 = WDHhrTSdi.normalizeClickMode(var4);
            if (!WDHhrTSdi.isAllowedEditClickMode(var4)) {
               this.sendWindowMessageOnce("&cYou cannot edit whilst Opsec is enabled!");
               return true;
            } else {
               int var8 = QuickBuyLayout.indexOfItem(Opsec$4.getLiveItemNames(var5), (String)Opsec$4.mhCt(var5).get(var6));
               if (var8 < 0) {
                  return true;
               } else {
                  int var9 = (Integer)Opsec$4.getLiveGridSlots(var5).get(var8);
                  Slot var10 = Opsec$4.getContainerChest(var5).getSlot(var9);
                  this.editCooldown.startCooldown(Opsec$4.getWindowId(var5), System.currentTimeMillis());
                  if (Jade.getModuleManager().getModule(BedwarsUtils.class) != null && Jade.getModuleManager().getModule(BedwarsUtils.class).handleQuickShopSlotClick(var10, var9, var3, var7)) {
                     return true;
                  } else {
                     mc.playerController.windowClick(Opsec$4.getContainerChest(var5).windowId, var9, var3, var7, mc.thePlayer);
                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public boolean isQuickBuySetupActive() {
      return this.quickBuySetup.isSetupActive();
   }

   public boolean RwheD27(int var1) {
      if (this.quickBuySetup.isSetupActive() && var1 == 1) {
         this.quickBuySetup.cancelSetup();
         return true;
      } else {
         return false;
      }
   }

   public void drawQuickBuySetupText() {
      if (this.quickBuySetup.isSetupActive()) {
         String var1 = this.quickBuySetup.getProgressText();
         int var2 = mc.fontRendererObj.getStringWidth(var1);
         int var3 = (new ScaledResolution(mc).getScaledWidth() - var2) / 2;
         mc.fontRendererObj.drawStringWithShadow(var1, var3, 8.0F, -1);
      }
   }

   public OpsecStore$0 ovob3() {
      return this.opsecStore.getAccount(this.BCqjV());
   }

   public boolean EufoLa(GuiScreen var1, String var2, List<String> var3) {
      return this.startQuickBuySetup(var1, var2, var3, null, false);
   }

   public boolean beginQuickBuySetup(GuiScreen var1, String var2, List<String> var3, List<String> var4) {
      return !QuickBuyLayout.ywZww(var3, var4) ? false : this.startQuickBuySetup(var1, var2, var3, var4, true);
   }

   private boolean startQuickBuySetup(GuiScreen var1, String var2, List<String> var3, List<String> var4, boolean var5) {
      if (this.OYrRt() && QuickBuyLayout.isValidLayout(var3)) {
         this.cachedQuickBuySnapshot = null;
         this.editCooldown.reset();
         OpsecStore$0 var6 = this.opsecStore.getAccount(this.BCqjV());
         if (var6 != null && var6.isDirty()) {
            if (var6.getRecoveryLayout().size() != 21) {
               this.sendFailureMessage("Interrupted setup has no valid recovery snapshot.");
               return false;
            } else {
               this.quickBuySetup.beginRestoreSetup(var1, var2, var3, var6.getRecoveryLayout());
               return true;
            }
         } else {
            if (var5) {
               this.quickBuySetup.mkQi(var1, var2, var3, var4);
            } else {
               this.quickBuySetup.Ils20(var1, var2, var3);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public void openQuickBuyCopyWithMessage(GuiScreen var1, String var2, List<String> var3, String var4) {
      if (ClientUtils.isInWorld() && mc.thePlayer.openContainer != mc.thePlayer.inventoryContainer) {
         mc.thePlayer.closeScreen();
      }

      this.EDe6145(var1, var2, new Opsec$3(var3, var4));
   }

   public void onSetupVerified() {
      this.cachedQuickBuySnapshot = null;
      this.editCooldown.reset();
      ClientUtils.sendJadeMessage("Jade", "&aAnti Quickbuy Identify setup verified.");
   }

   public void sendFailureMessage(String var1) {
      this.cachedQuickBuySnapshot = null;
      this.editCooldown.reset();
      ClientUtils.sendJadeMessage("Jade", "&c" + var1);
   }

   public boolean OYrRt() {
      return ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 0 && !this.quickBuySetup.isSetupActive() && mc.thePlayer.openContainer == mc.thePlayer.inventoryContainer;
   }

   private void EDe6145(GuiScreen var1, String var2, Opsec$3 var3) {
      mc.displayGuiScreen(new QuickBuyCopyScreen(var1, this, var2, var3));
   }

   private UUID BCqjV() {
      return ClientUtils.isInWorld()
         ? mc.thePlayer.getUniqueID()
         : (mc.getSession() != null && mc.getSession().getProfile() != null ? mc.getSession().getProfile().getId() : null);
   }

   private Opsec$4 XhWufg() {
      if (this.isEnabled()
         && this.antiQuickbuyIdentify.isToggled()
         && !this.quickBuySetup.isSetupActive()
         && ClientUtils.isInWorld()
         && ClientUtils.getBedWarsBoardType() == 2
         && mc.currentScreen instanceof GuiChest
         && mc.thePlayer.openContainer instanceof ContainerChest) {
         ContainerChest var1 = (ContainerChest)mc.thePlayer.openContainer;
         String var2 = ClientUtils.AOAtn(var1.getLowerChestInventory().getDisplayName().getUnformattedText()).trim();
         if (!"Quick Buy".equals(var2)) {
            this.cachedQuickBuySnapshot = null;
            this.editCooldown.reset();
            return null;
         } else {
            if (this.lastWindowId != var1.windowId) {
               this.lastWindowId = var1.windowId;
               this.windowOpenedAtMillis = System.currentTimeMillis();
               this.XkYyfU = Integer.MIN_VALUE;
               this.editCooldown.reset();
            }

            if (this.cachedQuickBuySnapshot != null && Opsec$4.getContainerChest(this.cachedQuickBuySnapshot) == var1 && Opsec$4.getWindowId(this.cachedQuickBuySnapshot) == var1.windowId) {
               long var3 = System.currentTimeMillis();
               if (var3 - Opsec$4.VbPu(this.cachedQuickBuySnapshot) < 50L) {
                  return this.cachedQuickBuySnapshot;
               }

               Opsec$4.setSnapshotTimeMillis(this.cachedQuickBuySnapshot, var3);
               List var5 = QuickBuySlotReader.readLayoutItemNames(var1, Opsec$4.getLiveGridSlots(this.cachedQuickBuySnapshot), Opsec$4.getLiveItemNames(this.cachedQuickBuySnapshot));
               if (var5.equals(Opsec$4.getLiveItemNames(this.cachedQuickBuySnapshot))) {
                  Opsec$4.refreshItemStacks(this.cachedQuickBuySnapshot);
                  if (this.editCooldown.FvuiK(var1.windowId, var3)) {
                     this.editCooldown.clearAfterSync();
                  }

                  return this.cachedQuickBuySnapshot;
               }

               if (this.editCooldown.JcOu(var1.windowId, var3)) {
                  this.editCooldown.markEditObserved(var1.windowId, var3);
                  return this.cachedQuickBuySnapshot;
               }
            }

            UUID var7 = this.BCqjV();
            OpsecStore$0 var4 = this.opsecStore.getAccount(var7);
            if (var4 == null) {
               this.cachedQuickBuySnapshot = null;
               if (this.hasValidStoredLayout(var7)) {
                  this.ejhu(var1.windowId, "Setup is missing.");
               }

               return null;
            } else if (!var4.isDirty() && QuickBuyLayout.isValidLayout(var4.gsyNw()) && QuickBuyLayout.isValidLayout(var4.wksF())) {
               List var8 = QuickBuySlotReader.computeGridSlotIndices(var1);
               List var6 = QuickBuySlotReader.readLayoutItemNames(var1, var8, var4.wksF());
               if (var8.size() != 21) {
                  this.cachedQuickBuySnapshot = null;
                  this.AXLa(var1.windowId, "The recorded 7x3 live Quick Buy grid is unavailable.", var7);
                  return null;
               } else if (!var6.equals(var4.wksF())) {
                  this.cachedQuickBuySnapshot = null;
                  this.AXLa(var1.windowId, "The live shop items do not match the verified server-side layout.", var7);
                  return null;
               } else if (!QuickBuyLayout.ywZww(var4.gsyNw(), var6)) {
                  this.cachedQuickBuySnapshot = null;
                  this.AXLa(var1.windowId, "The verified layout does not preserve the desired item set.", var7);
                  return null;
               } else {
                  this.cachedQuickBuySnapshot = new Opsec$4(var1, var8, var4.gsyNw(), var6);
                  this.editCooldown.clearAfterSync();
                  return this.cachedQuickBuySnapshot;
               }
            } else {
               this.cachedQuickBuySnapshot = null;
               this.warnIfSetupExists(var1.windowId, "Setup is interrupted or invalid.", var7);
               return null;
            }
         }
      } else {
         this.cachedQuickBuySnapshot = null;
         this.editCooldown.reset();
         return null;
      }
   }

   private boolean hasValidStoredLayout(UUID var1) {
      HypixelPlayerStats var2 = StatsLookupService.getInstance().getCachedStats(var1);
      if (var2 == null) {
         StatsLookupService.getInstance().requestStatsForGame(var1);
         return false;
      } else {
         return oeqxjyc2.isUsableLayout(var2.getFavorites());
      }
   }

   private void warnIfSetupExists(int var1, String var2, UUID var3) {
      if (this.hasValidStoredLayout(var3)) {
         this.ejhu(var1, var2);
      }
   }

   private void AXLa(int var1, String var2, UUID var3) {
      if (System.currentTimeMillis() - this.windowOpenedAtMillis >= 500L) {
         this.warnIfSetupExists(var1, var2, var3);
      }
   }

   private void ejhu(int var1, String var2) {
      if (this.XkYyfU != var1) {
         this.XkYyfU = var1;
         ClientUtils.sendJadeMessage("Jade", "&cAnti Quickbuy Identify inactive: &7" + var2);
      }
   }

   private void sendWindowMessageOnce(String var1) {
      int var2 = ClientUtils.isInWorld() && mc.thePlayer.openContainer != null ? mc.thePlayer.openContainer.windowId : Integer.MIN_VALUE;
      if (this.XkYyfU != var2) {
         this.XkYyfU = var2;
         ClientUtils.sendJadeMessage("Jade", var1);
      }
   }

   public static Minecraft getMc() {
      return mc;
   }

   public static void openQuickBuyCopyScreen(Opsec var0, GuiScreen var1, String var2, Opsec$3 var3) {
      var0.EDe6145(var1, var2, var3);
   }

   public static BooleanSetting getAntiPingIdentifySetting(Opsec var0) {
      return var0.antiPingIdentify;
   }

   public static boolean isInBedwarsLobby(Opsec var0) {
      return var0.inBedwarsLobby;
   }
}
