// Jade recovery: original class: jade.deps.eLz.aVWVANRws
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.IFont;
import jade.client.common.ItemNames;
import jade.client.common.OpsecStore$0;
import jade.client.common.PlayerApi$2;
import jade.client.common.PlayerApi;
import jade.client.common.QuickBuyLayout;
import jade.client.common.RoundedRect;
import jade.client.module.client.Gui;
import jade.client.module.minigames.Opsec$3;
import jade.client.module.minigames.Opsec;
import jade.deps.loader107.HypixelPlayerStats;
import jade.deps.loader107.StatsLookupService;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public final class QuickBuyCopyScreen extends GuiScreen {
   private static final long LOOKUP_TIMEOUT_MS = 25000L;
   private static final long DguzY = 90000L;
   private static final int WINDOW_WIDTH = 336;
   private static final int hUu = 24;
   private static final int ILnI7 = 20;
   private static final int SLOT_SIZE = 28;
   private static final int PoXhx = 4;
   private static final int ICd = -15066598;
   private static final int PANEL_SHADOW_COLOR = -15658735;
   private static final int BUTTON_COLOR = -14803162;
   private static final int BUTTON_HOVER_COLOR = -14408404;
   private static final int SLOT_COLOR = -15461356;
   private static final int xbat0 = -14408404;
   private static final int OUTLINE_COLOR = 1711276032;
   private static final int Pej = 352321535;
   private static final int y75 = -986896;
   private static final int TEXT_COLOR = -6645094;
   private static final int DISABLED_TEXT_COLOR = -10197916;
   private static final int ERROR_TEXT_COLOR = -34953;
   private final GuiScreen parentScreen;
   private final Opsec opsecModule;
   private final String initialPlayerName;
   private final Opsec$3 itj;
   private TextField Uyt;
   private List<String> quickBuyLayout = Collections.emptyList();
   private List<String> sourceLayout = Collections.emptyList();
   private List<String> displayedSlots = Collections.emptyList();
   private List<ItemStack> SUVKUy = Collections.emptyList();
   private PlayerApi$2 targetPlayer;
   private QuickBuyCopyScreen$1 state = QuickBuyCopyScreen$1.IDLE;
   private String statusMessage = "";
   private String loadedPlayerName = "";
   private boolean statusIsError;
   private long deadlineMs;
   private long ceNvcI;
   private boolean fetchRequested;
   private int requestId;
   private volatile QuickBuyCopyScreen$2 qyom;
   private long openedAtMs;
   private long zoj;
   private float windowHeight;
   private float slotsRevealProgress;
   private float hmupmH = 1.0F;
   private float fetchButtonHover;
   private float mO0;
   private float applyButtonHover;
   private float dialogProgress;
   private float copyButtonHover;
   private float larpButtonHover;
   private boolean setupDialogOpen;
   private final float[] GtfZ = new float[21];
   private String rrNx8 = "";
   private QuickBuyCopyScreen$3 ignFieldBounds = QuickBuyCopyScreen$3.getEmpty();
   private QuickBuyCopyScreen$3 fetchButtonBounds = QuickBuyCopyScreen$3.getEmpty();
   private QuickBuyCopyScreen$3 nryzq = QuickBuyCopyScreen$3.getEmpty();
   private QuickBuyCopyScreen$3 oHv = QuickBuyCopyScreen$3.getEmpty();
   private QuickBuyCopyScreen$3 dialogBounds = QuickBuyCopyScreen$3.getEmpty();
   private QuickBuyCopyScreen$3 QOgI = QuickBuyCopyScreen$3.getEmpty();
   private QuickBuyCopyScreen$3 Nazh = QuickBuyCopyScreen$3.getEmpty();

   public QuickBuyCopyScreen(GuiScreen var1, Opsec var2, String var3, Opsec$3 var4) {
      this.parentScreen = var1;
      this.opsecModule = var2;
      this.initialPlayerName = var3 == null ? "" : var3;
      this.itj = var4;
      if (var4 != null && QuickBuyLayout.isValidLayout(var4.layoutItemNames)) {
         this.quickBuyLayout = var4.layoutItemNames;
         this.displayedSlots = var4.layoutItemNames;
         this.loadedPlayerName = this.initialPlayerName;
         this.statusMessage = "Review the updated layout.";
         this.state = QuickBuyCopyScreen$1.DONE;
         this.rebuildItemStacks();
      }
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      String var1 = this.Uyt == null ? this.initialPlayerName : this.Uyt.getText();
      this.Uyt = new TextField("Copy IGN", 16, 1.0F, 8.0F);
      this.Uyt.setText(var1);
      this.Uyt.setFocused(false);
      this.buttonList.clear();
      this.openedAtMs = System.currentTimeMillis();
      this.zoj = this.openedAtMs;
      this.windowHeight = this.getTargetWindowHeight();
      this.slotsRevealProgress = 0.0F;
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      super.onGuiClosed();
   }

   public void updateScreen() {
      this.Uyt.kqao7();
      StatsLookupService.getInstance().tick();
      this.okzvs();
      if (this.state == QuickBuyCopyScreen$1.PROFILE && System.currentTimeMillis() > this.deadlineMs) {
         this.requestId++;
         this.ZEeCx("Player lookup timed out.");
      } else if (this.state == QuickBuyCopyScreen$1.SOURCE_STATS || this.state == QuickBuyCopyScreen$1.CURRENT_STATS) {
         this.pollStats();
      }
   }

   private void startPlayerLookup() {
      final String var1 = this.Uyt.getText().trim();
      if (!var1.matches("[A-Za-z0-9_]{1,16}")) {
         this.reportError("Invalid IGN.");
      } else {
         final int var2 = ++this.requestId;
         this.state = QuickBuyCopyScreen$1.PROFILE;
         this.quickBuyLayout = Collections.emptyList();
         this.sourceLayout = Collections.emptyList();
         this.targetPlayer = null;
         this.qyom = null;
         this.fetchRequested = false;
         this.statusIsError = false;
         this.statusMessage = "Finding player...";
         this.deadlineMs = System.currentTimeMillis() + 25000L;
         Jade.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
               PlayerApi$2 var1x;
               try {
                  var1x = PlayerApi.lookupProfileByName(var1);
               } catch (Throwable var3) {
                  var1x = null;
               }

               QuickBuyCopyScreen.XIkL5(QuickBuyCopyScreen.this, new QuickBuyCopyScreen$2(var2, var1, var1x));
            }
         });
      }
   }

   private void okzvs() {
      QuickBuyCopyScreen$2 var1 = this.qyom;
      if (var1 != null) {
         this.qyom = null;
         if (this.requestId == QuickBuyCopyScreen$2.getRequestId(var1) && this.state == QuickBuyCopyScreen$1.PROFILE) {
            PlayerApi$2 var2 = QuickBuyCopyScreen$2.getPlayerApi(var1);
            if (var2 != null && var2.getUuid() != null) {
               this.targetPlayer = var2;
               String var3 = var2.getName().isEmpty() ? QuickBuyCopyScreen$2.getRequestedName(var1) : var2.getName();
               this.Uyt.setText(var3);
               this.loadedPlayerName = var3;
               StatsLookupService.getInstance().clearPlayer(var2.getUuid());
               this.state = QuickBuyCopyScreen$1.SOURCE_STATS;
               this.statusMessage = "Loading Quick Buy...";
               this.deadlineMs = System.currentTimeMillis() + 25000L;
               this.ceNvcI = 0L;
               this.fetchRequested = false;
            } else {
               this.ZEeCx("IGN not found.");
            }
         }
      }
   }

   private void pollStats() {
      long var1 = System.currentTimeMillis();
      UUID var3 = this.state == QuickBuyCopyScreen$1.SOURCE_STATS ? this.targetPlayer.getUuid() : this.mc.thePlayer.getUniqueID();
      HypixelPlayerStats var4 = StatsLookupService.getInstance().getCachedStats(var3);
      if (var4 != null) {
         this.handleStatsLoaded(this.state, var4);
      } else {
         String var5 = StatsLookupService.getInstance().pollFailureReason(var3);
         if (var5 != null) {
            this.fetchRequested = false;
            this.ZEeCx("Stats failed: " + formatError(var5) + ".");
         } else if (var1 > this.deadlineMs) {
            this.ZEeCx("Fetch timed out.");
         } else if (!this.fetchRequested && var1 >= this.ceNvcI) {
            this.fetchRequested = StatsLookupService.getInstance().requestManualStats(var3);
            if (this.fetchRequested) {
               this.deadlineMs = var1 + 90000L;
            }

            this.ceNvcI = var1 + 500L;
         }
      }
   }

   private void handleStatsLoaded(QuickBuyCopyScreen$1 var1, HypixelPlayerStats var2) {
      this.fetchRequested = false;
      if (var1 == QuickBuyCopyScreen$1.SOURCE_STATS) {
         this.sourceLayout = var2 == null ? Collections.emptyList() : var2.getFavorites();
         UUID var3 = this.mc.thePlayer.getUniqueID();
         if (!QuickBuyLayout.isValidLayout(this.sourceLayout) && !this.targetPlayer.getUuid().equals(var3)) {
            StatsLookupService.getInstance().clearPlayer(var3);
            this.state = QuickBuyCopyScreen$1.CURRENT_STATS;
            this.statusMessage = "Checking fallbacks...";
            this.deadlineMs = System.currentTimeMillis() + 25000L;
            this.ceNvcI = 0L;
         } else {
            this.applyLayout(this.sourceLayout);
         }
      } else if (var1 == QuickBuyCopyScreen$1.CURRENT_STATS) {
         this.applyLayout(var2 == null ? Collections.emptyList() : var2.getFavorites());
      }
   }

   private void applyLayout(List<String> var1) {
      this.quickBuyLayout = QuickBuyLayout.buildLayout(this.sourceLayout, var1, null);
      if (!QuickBuyLayout.isValidLayout(this.quickBuyLayout)) {
         this.ZEeCx("Couldn't build 21 slots.");
      } else {
         this.state = QuickBuyCopyScreen$1.DONE;
         this.statusIsError = false;
         this.statusMessage = this.sourceLayout.size() == 21 ? "Ready." : "Ready with fallbacks.";
         this.displayedSlots = Collections.unmodifiableList(new ArrayList<>(this.quickBuyLayout));
         this.loadedPlayerName = this.Uyt.getText().trim();
         this.rebuildItemStacks();
      }
   }

   private void ZEeCx(String var1) {
      this.state = QuickBuyCopyScreen$1.ERROR;
      this.fetchRequested = false;
      this.reportError(var1);
   }

   private void reportError(String var1) {
      this.statusIsError = true;
      this.statusMessage = var1 == null ? "Something went wrong." : var1;
   }

   private void rebuildItemStacks() {
      ArrayList var1 = new ArrayList(this.displayedSlots.size());

      for (String var3 : this.displayedSlots) {
         var1.add(QuickBuyItems.createItemStack(var3));
      }

      this.SUVKUy = Collections.unmodifiableList(var1);
   }

   private void UrkI() {
      String var1 = this.Uyt.getText().trim();
      if (!this.loadedPlayerName.isEmpty() && !var1.equalsIgnoreCase(this.loadedPlayerName)) {
         this.quickBuyLayout = Collections.emptyList();
         this.sourceLayout = Collections.emptyList();
         this.targetPlayer = null;
         this.state = QuickBuyCopyScreen$1.IDLE;
         this.fetchRequested = false;
         this.statusMessage = "";
         this.statusIsError = false;
         this.loadedPlayerName = "";
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         if (this.setupDialogOpen) {
            this.setupDialogOpen = false;
         } else {
            this.mc.displayGuiScreen(this.parentScreen);
         }
      } else if (!this.setupDialogOpen) {
         if (var2 == 28 && this.Uyt.isFocused()) {
            this.startPlayerLookup();
         } else {
            if (this.Uyt.isFocused() && this.Uyt.keyTyped(var1, var2)) {
               this.UrkI();
            }
         }
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         if (this.setupDialogOpen) {
            if (QuickBuyCopyScreen$3.contains(this.QOgI, var1, var2)) {
               if (!this.opsecModule.EufoLa(this.parentScreen, this.Uyt.getText().trim(), this.quickBuyLayout)) {
                  this.setupDialogOpen = false;
                  this.reportError("Setup couldn't start.");
               }
            } else if (QuickBuyCopyScreen$3.contains(this.Nazh, var1, var2)) {
               this.mc.displayGuiScreen(new LarpScreen(this, this.parentScreen, this.opsecModule, this.Uyt.getText().trim(), this.quickBuyLayout));
            } else {
               if (!QuickBuyCopyScreen$3.contains(this.dialogBounds, var1, var2)) {
                  this.setupDialogOpen = false;
               }
            }
         } else {
            float var4 = this.getOpenScale();
            int var5 = Math.round(this.width / 2.0F + (var1 - this.width / 2.0F) / var4);
            int var6 = Math.round(this.height / 2.0F + (var2 - this.height / 2.0F) / var4);
            if (QuickBuyCopyScreen$3.contains(this.ignFieldBounds, var5, var6)) {
               this.Uyt.setFocused(true);
            } else {
               this.Uyt.setFocused(false);
               if (QuickBuyCopyScreen$3.contains(this.fetchButtonBounds, var5, var6) && !this.isBusy()) {
                  this.startPlayerLookup();
               } else if (QuickBuyCopyScreen$3.contains(this.nryzq, var5, var6)) {
                  this.mc.displayGuiScreen(this.parentScreen);
               } else {
                  if (QuickBuyCopyScreen$3.contains(this.oHv, var5, var6) && this.canApplyLayout()) {
                     this.setupDialogOpen = true;
                  }
               }
            }
         }
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.updateAnimations();
      drawRect(0, 0, this.width, this.height, -1206841071);
      float var4 = this.getOpenScale();
      float var5 = this.width / 2.0F;
      float var6 = this.height / 2.0F;
      int var7 = Math.round(var5 + (var1 - var5) / var4);
      int var8 = Math.round(var6 + (var2 - var6) / var4);
      GlStateManager.pushMatrix();
      GlStateManager.translate(var5, var6, 0.0F);
      GlStateManager.scale(var4, var4, 1.0F);
      GlStateManager.translate(-var5, -var6, 0.0F);
      this.drawMainPanel(var7, var8);
      GlStateManager.popMatrix();
      if (this.dialogProgress > 0.01F) {
         this.drawSetupDialog(var1, var2);
      }

      if (!this.setupDialogOpen && !this.rrNx8.isEmpty() && var4 > 0.985F) {
         this.uvi3(this.rrNx8, var1, var2);
      }
   }

   private void drawSetupDialog(int var1, int var2) {
      GlStateManager.disableDepth();
      int var3 = Math.round(145.0F * this.dialogProgress);
      drawRect(0, 0, this.width, this.height, var3 << 24);
      int var4 = Math.min(220, this.width - 28);
      byte var5 = 94;
      int var6 = (this.width - var4) / 2;
      int var7 = (this.height - var5) / 2;
      this.dialogBounds = new QuickBuyCopyScreen$3(var6, var7, var4, var5);
      this.QOgI = new QuickBuyCopyScreen$3(var6 + 18, var7 + 55, (var4 - 46) / 2, 20);
      this.Nazh = new QuickBuyCopyScreen$3(QuickBuyCopyScreen$3.CPEz(this.QOgI) + 10, var7 + 55, QuickBuyCopyScreen$3.ZHv7(this.QOgI), 20);
      float var8 = 0.92F + easeOutCubic(this.dialogProgress) * 0.08F;
      float var9 = this.width / 2.0F;
      float var10 = this.height / 2.0F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(var9, var10, 0.0F);
      GlStateManager.scale(var8, var8, 1.0F);
      GlStateManager.translate(-var9, -var10, 0.0F);
      this.JXkr(var6, var7, var4, var5, 9.0F, withAlpha(-15066598, Math.round(255.0F * this.dialogProgress)));
      this.drawPanelOutline(var6, var7, var4, var5, 9.0F);
      IFont var11 = this.getHeaderFont();
      IFont var12 = this.getSettingFont();
      String var13 = "Choose setup";
      String var14 = "Copy normally or mimic another Quick Buy.";
      var11.drawString(var13, var6 + (var4 - var11.getStringWidth(var13)) / 2.0F, var7 + 14, -986896, false);
      var12.drawString(var14, var6 + (var4 - var12.getStringWidth(var14)) / 2.0F, var7 + 34, -6645094, false);
      this.copyButtonHover = this.approach(this.copyButtonHover, QuickBuyCopyScreen$3.contains(this.QOgI, var1, var2) ? 1.0F : 0.0F);
      this.larpButtonHover = this.approach(this.larpButtonHover, QuickBuyCopyScreen$3.contains(this.Nazh, var1, var2) ? 1.0F : 0.0F);
      this.drawActionButton("Copy", this.QOgI, this.copyButtonHover, true, false);
      this.drawActionButton("Larp", this.Nazh, this.larpButtonHover, true, true);
      GlStateManager.popMatrix();
      GlStateManager.enableDepth();
   }

   private void drawMainPanel(int var1, int var2) {
      int var3 = Math.min(336, this.width - 24);
      int var4 = Math.round(this.windowHeight);
      int var5 = (this.width - var3) / 2;
      int var6 = (this.height - var4) / 2;
      float var7 = 9.0F;
      this.JXkr(var5, var6, var3, var4, var7, -15066598);
      this.drawPanelOutline(var5, var6, var3, var4, var7);
      int var8 = var6 + var4 - 32;
      this.ignFieldBounds = new QuickBuyCopyScreen$3(var5 + 12, var6 + 12, var3 - 24, 24);
      this.nryzq = new QuickBuyCopyScreen$3(var5 + 12, var8, 60, 20);
      this.fetchButtonBounds = new QuickBuyCopyScreen$3(this.XTGGn() ? var5 + (var3 - 60) / 2 : var5 + var3 - 72, var8, 60, 20);
      this.oHv = this.XTGGn() ? new QuickBuyCopyScreen$3(var5 + var3 - 72, var8, 60, 20) : QuickBuyCopyScreen$3.getEmpty();
      this.Uyt.render(QuickBuyCopyScreen$3.yLomwC(this.ignFieldBounds), QuickBuyCopyScreen$3.BEneE(this.ignFieldBounds), QuickBuyCopyScreen$3.CPEz(this.ignFieldBounds), QuickBuyCopyScreen$3.getBottom(this.ignFieldBounds));
      this.fetchButtonHover = this.approach(this.fetchButtonHover, QuickBuyCopyScreen$3.contains(this.fetchButtonBounds, var1, var2) && !this.isBusy() ? 1.0F : 0.0F);
      this.drawActionButton("Fetch", this.fetchButtonBounds, this.fetchButtonHover, !this.isBusy(), true);
      this.rrNx8 = "";
      if (this.slotsRevealProgress > 0.01F && QuickBuyLayout.isValidLayout(this.displayedSlots)) {
         this.drawSlotGrid(var5, var6 + 45, var3, var1, var2);
      }

      if (this.isBusy()) {
         this.MbKd(var5, var6, var3);
      } else {
         this.OVEC(var5, var6, var3);
      }

      this.mO0 = this.approach(this.mO0, QuickBuyCopyScreen$3.contains(this.nryzq, var1, var2) ? 1.0F : 0.0F);
      this.drawActionButton("Back", this.nryzq, this.mO0, true, false);
      if (this.XTGGn()) {
         this.applyButtonHover = this.approach(this.applyButtonHover, QuickBuyCopyScreen$3.contains(this.oHv, var1, var2) && this.canApplyLayout() ? 1.0F : 0.0F);
         this.drawActionButton("Apply", this.oHv, this.applyButtonHover, this.canApplyLayout(), false);
      } else {
         this.applyButtonHover = this.approach(this.applyButtonHover, 0.0F);
      }
   }

   private void drawSlotGrid(int var1, int var2, int var3, int var4, int var5) {
      short var6 = 220;
      int var7 = var1 + (var3 - var6) / 2;

      for (int var8 = 0; var8 < 21; var8++) {
         int var9 = var7 + var8 % 7 * 32;
         int var10 = var2 + var8 / 7 * 32;
         QuickBuyCopyScreen$3 var11 = new QuickBuyCopyScreen$3(var9, var10, 28, 28);
         boolean var12 = QuickBuyCopyScreen$3.contains(var11, var4, var5) && this.slotsRevealProgress > 0.92F;
         this.GtfZ[var8] = this.approach(this.GtfZ[var8], var12 ? 1.0F : 0.0F);
         float var13 = clampValue(this.slotsRevealProgress * 1.3F - var8 * 0.015F, 0.0F, 1.0F);
         float var14 = easeOutCubic(var13);
         int var15 = lerpColor(-15461356, -14408404, this.GtfZ[var8]);
         this.JXkr(var9, var10, 28.0F, 28.0F, 4.0F, withAlpha(var15, Math.round(255.0F * var14)));
         RoundedRect.drawRoundedOutline(var9, var10, 28.0F, 28.0F, 4.0F, 0.18F, new Color(0, 0, 0, 0), new Color(withAlpha(352321535, Math.round(40.0F * var14)), true));
         if (var8 < this.SUVKUy.size() && var13 > 0.08F) {
            float var16 = 0.78F + 0.22F * var14;
            float var17 = var9 + 14.0F;
            float var18 = var10 + 14.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(var17, var18, 0.0F);
            GlStateManager.scale(var16, var16, 1.0F);
            GlStateManager.translate(-var17, -var18, 0.0F);
            this.drawItemStack(this.SUVKUy.get(var8), var9 + 6, var10 + 6);
            GlStateManager.popMatrix();
         }

         if (var12) {
            this.rrNx8 = ItemNames.formatSkyblockName(this.displayedSlots.get(var8));
         }
      }
   }

   private void drawItemStack(ItemStack var1, int var2, int var3) {
      if (var1 != null && this.itemRender != null) {
         GlStateManager.enableDepth();
         RenderHelper.enableGUIStandardItemLighting();
         this.itemRender.renderItemAndEffectIntoGUI(var1, var2, var3);
         this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, var1, var2, var3, null);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
      }
   }

   private void MbKd(int var1, int var2, int var3) {
      IFont var4 = this.getSettingFont();
      String var5 = this.statusMessage.isEmpty() ? "Loading..." : this.statusMessage;
      byte var6 = 14;
      int var7 = var6 + 7 + var4.getStringWidth(var5);
      int var8 = var1 + (var3 - var7) / 2;
      int var9 = var2 + 43;
      this.drawSpinner(var8 + var6 / 2.0F, var9 + var6 / 2.0F, 6.0F);
      var4.drawString(var5, var8 + var6 + 7, var9 + (var6 - var4.getFontHeight()) / 2.0F + 1.0F, -6645094, false);
   }

   private void drawSpinner(float var1, float var2, float var3) {
      int var4 = this.YVJbi();
      double var5 = System.currentTimeMillis() / 220.0;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glEnable(2848);
      GL11.glLineWidth(1.7F);

      for (int var7 = 0; var7 < 12; var7++) {
         double var8 = var5 + (Math.PI * 2) * var7 / 12.0;
         double var10 = var8 + (Math.PI / 10);
         float var12 = 0.12F + 0.88F * (var7 + 1) / 12.0F;
         GlStateManager.color((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, var12);
         GL11.glBegin(1);
         GL11.glVertex2d(var1 + Math.cos(var8) * var3, var2 + Math.sin(var8) * var3);
         GL11.glVertex2d(var1 + Math.cos(var10) * var3, var2 + Math.sin(var10) * var3);
         GL11.glEnd();
      }

      GL11.glLineWidth(1.0F);
      GL11.glDisable(2848);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
   }

   private void OVEC(int var1, int var2, int var3) {
      String var4 = this.getStatusText();
      if (!var4.isEmpty()) {
         IFont var5 = this.getSettingFont();
         String var6 = mtwiXw(var4, var3 - 28, var5);
         int var7 = !this.statusIsError && this.opsecModule.OYrRt() ? -6645094 : -34953;
         int var8 = this.XTGGn() ? var2 + 145 : var2 + 43;
         var5.drawString(var6, var1 + (var3 - var5.getStringWidth(var6)) / 2.0F, var8, var7, false);
      }
   }

   private String getStatusText() {
      if (!this.opsecModule.OYrRt()) {
         return "Please go to a Bed Wars lobby!";
      } else {
         OpsecStore$0 var1 = this.opsecModule.ovob3();
         if (var1 != null && var1.isDirty()) {
            return "Restore required.";
         } else {
            return this.statusMessage == null ? "" : this.statusMessage;
         }
      }
   }

   private void drawActionButton(String var1, QuickBuyCopyScreen$3 var2, float var3, boolean var4, boolean var5) {
      if (var2 != QuickBuyCopyScreen$3.getEmpty()) {
         int var6 = var4 ? (var5 ? lerpColor(-15577801, -15172779, var3) : lerpColor(-14803162, -14408404, var3)) : -15395301;
         this.JXkr(QuickBuyCopyScreen$3.yLomwC(var2), QuickBuyCopyScreen$3.BEneE(var2), QuickBuyCopyScreen$3.ZHv7(var2), QuickBuyCopyScreen$3.XNvGx(var2), 7.0F, var6);
         IFont var7 = this.getHeaderFont();
         int var8 = var4 ? lerpColor(-6645094, -986896, var3) : -10197916;
         var7.drawString(
            var1,
            QuickBuyCopyScreen$3.yLomwC(var2) + (QuickBuyCopyScreen$3.ZHv7(var2) - var7.getStringWidth(var1)) / 2.0F,
            QuickBuyCopyScreen$3.BEneE(var2) + (QuickBuyCopyScreen$3.XNvGx(var2) - var7.getFontHeight()) / 2.0F + 1.0F,
            var8,
            false
         );
      }
   }

   private void uvi3(String var1, int var2, int var3) {
      IFont var4 = this.getSettingFont();
      int var5 = var4.getStringWidth(var1) + 12;
      int var6 = var4.getFontHeight() + 8;
      int var7 = Math.min(this.width - var5 - 4, var2 + 9);
      int var8 = Math.min(this.height - var6 - 4, var3 + 6);
      this.JXkr(var7, var8, var5, var6, 5.0F, -233170406);
      this.drawPanelOutline(var7, var8, var5, var6, 5.0F);
      var4.drawString(var1, var7 + 6, var8 + 4, -986896, false);
   }

   private void updateAnimations() {
      long var1 = System.currentTimeMillis();
      float var3 = Math.min(0.05F, Math.max(0.001F, (float)(var1 - this.zoj) / 1000.0F));
      this.zoj = var1;
      this.hmupmH = 1.0F - (float)Math.exp(-13.0F * var3);
      this.windowHeight = this.windowHeight + (this.getTargetWindowHeight() - this.windowHeight) * this.hmupmH;
      this.slotsRevealProgress = this.approach(this.slotsRevealProgress, this.XTGGn() ? 1.0F : 0.0F);
      this.dialogProgress = this.approach(this.dialogProgress, this.setupDialogOpen ? 1.0F : 0.0F);
      if (!this.XTGGn() && this.slotsRevealProgress < 0.01F && !this.displayedSlots.isEmpty()) {
         this.displayedSlots = Collections.emptyList();
         this.SUVKUy = Collections.emptyList();
      }
   }

   private float getTargetWindowHeight() {
      if (this.XTGGn()) {
         return 206.0F;
      } else {
         return !this.isBusy() && this.getStatusText().isEmpty() ? 74.0F : 96.0F;
      }
   }

   private boolean XTGGn() {
      return !this.isBusy() && !this.xx21().isEmpty() && QuickBuyLayout.isValidLayout(this.quickBuyLayout);
   }

   private boolean canApplyLayout() {
      return this.XTGGn() && this.opsecModule.OYrRt();
   }

   private boolean isBusy() {
      return this.state == QuickBuyCopyScreen$1.PROFILE || this.state == QuickBuyCopyScreen$1.SOURCE_STATS || this.state == QuickBuyCopyScreen$1.CURRENT_STATS;
   }

   private String xx21() {
      return this.Uyt == null ? this.initialPlayerName : this.Uyt.getText().trim();
   }

   private float getOpenScale() {
      float var1 = clampValue((float)(System.currentTimeMillis() - this.openedAtMs) / 230.0F, 0.0F, 1.0F);
      return 0.94F + easeOutCubic(var1) * 0.06F;
   }

   private float approach(float var1, float var2) {
      return var1 + (var2 - var1) * this.hmupmH;
   }

   private IFont getHeaderFont() {
      return Gui.getHeaderFont();
   }

   private IFont getSettingFont() {
      return Gui.getSettingFont();
   }

   private int YVJbi() {
      return Gui.accent == null ? -15030151 : Gui.accent.getArgb() | 0xFF000000;
   }

   private void JXkr(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         RoundedRect.drawRoundedRect(var1, var2, var3, var4, var5, new Color(var6, true));
      }
   }

   private void drawPanelOutline(float var1, float var2, float var3, float var4, float var5) {
      RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.22F, new Color(0, 0, 0, 0), new Color(1711276032, true));
      RoundedRect.drawRoundedOutline(var1 + 0.5F, var2 + 0.5F, var3 - 1.0F, var4 - 1.0F, Math.max(0.0F, var5 - 0.5F), 0.22F, new Color(0, 0, 0, 0), new Color(352321535, true));
   }

   private static int withAlpha(int var0, int var1) {
      return Math.max(0, Math.min(255, var1)) << 24 | var0 & 16777215;
   }

   private static int lerpColor(int var0, int var1, float var2) {
      float var3 = clampValue(var2, 0.0F, 1.0F);
      int var4 = Math.round((var0 >>> 24 & 0xFF) + ((var1 >>> 24 & 0xFF) - (var0 >>> 24 & 0xFF)) * var3);
      int var5 = Math.round((var0 >>> 16 & 0xFF) + ((var1 >>> 16 & 0xFF) - (var0 >>> 16 & 0xFF)) * var3);
      int var6 = Math.round((var0 >>> 8 & 0xFF) + ((var1 >>> 8 & 0xFF) - (var0 >>> 8 & 0xFF)) * var3);
      int var7 = Math.round((var0 & 0xFF) + ((var1 & 0xFF) - (var0 & 0xFF)) * var3);
      return var4 << 24 | var5 << 16 | var6 << 8 | var7;
   }

   private static float easeOutCubic(float var0) {
      float var1 = 1.0F - clampValue(var0, 0.0F, 1.0F);
      return 1.0F - var1 * var1 * var1;
   }

   private static float clampValue(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private static String mtwiXw(String var0, int var1, IFont var2) {
      if (var0 != null && var2.getStringWidth(var0) > var1) {
         String var3 = "...";
         String var4 = var0;

         while (!var4.isEmpty() && var2.getStringWidth(var4 + var3) > var1) {
            var4 = var4.substring(0, var4.length() - 1);
         }

         return var4 + var3;
      } else {
         return var0 == null ? "" : var0;
      }
   }

   private static String formatError(String var0) {
      return var0 != null && !var0.isEmpty() ? var0.replace('_', ' ') : "upstream error";
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public static QuickBuyCopyScreen$2 XIkL5(QuickBuyCopyScreen var0, QuickBuyCopyScreen$2 var1) {
      return var0.qyom = var1;
   }
}
