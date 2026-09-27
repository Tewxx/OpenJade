// Jade recovery: original class: jade.deps.eLz.c24f4B
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.IFont;
import jade.client.common.OpsecStore$0;
import jade.client.common.PlayerApi$2;
import jade.client.common.PlayerApi;
import jade.client.common.QuickBuyLayout;
import jade.client.module.minigames.Opsec;
import jade.deps.loader107.HypixelPlayerStats;
import jade.deps.loader107.StatsLookupService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public final class LarpScreen extends ItemPreviewScreen {
   private static final long PROFILE_TIMEOUT_MILLIS = 25000L;
   private static final long STATS_TIMEOUT_MILLIS = 90000L;
   private final GuiScreen prP78;
   private final GuiScreen UDO;
   private final Opsec vqq;
   private final String targetPlayerName;
   private final List<String> localQuickBuy;
   private TextField gfx;
   private PlayerApi$2 targetPlayer;
   private List<String> targetQuickBuy = Collections.emptyList();
   private List<ItemStack> targetItemStacks = Collections.emptyList();
   private LarpScreen$1 loadState = LarpScreen$1.IDLE;
   private String statusMessage = "";
   private String loadedPlayerName = "";
   private boolean jgup;
   private long QSt;
   private long nextPollTime;
   private boolean HpM;
   private int requestId;
   private volatile LarpScreen$2 pendingResult;
   private float animatedWindowHeight;
   private float previewReveal;
   private float ina;
   private float backButtonHover;
   private float continueButtonHover;
   private ItemPreviewScreen$0 CdiC = ItemPreviewScreen$0.EMPTY;
   private ItemPreviewScreen$0 fetchButtonRect = ItemPreviewScreen$0.EMPTY;
   private ItemPreviewScreen$0 backButtonRect = ItemPreviewScreen$0.EMPTY;
   private ItemPreviewScreen$0 continueButtonRect = ItemPreviewScreen$0.EMPTY;

   public LarpScreen(GuiScreen var1, GuiScreen var2, Opsec var3, String var4, List<String> var5) {
      this.prP78 = var1;
      this.UDO = var2;
      this.vqq = var3;
      this.targetPlayerName = var4 == null ? "" : var4.trim();
      this.localQuickBuy = Collections.unmodifiableList(new ArrayList<>(var5));
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      String var1 = this.gfx == null ? "" : this.gfx.getText();
      this.gfx = new TextField("Larping as", 16, 1.0F, 8.0F);
      this.gfx.setText(var1);
      this.gfx.setFocused(false);
      this.buttonList.clear();
      this.beginFlowAnimation();
      this.animatedWindowHeight = this.getTargetHeight();
      this.previewReveal = 0.0F;
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      super.onGuiClosed();
   }

   public void updateScreen() {
      this.gfx.kqao7();
      StatsLookupService.getInstance().tick();
      this.KPugJ();
      if (this.loadState == LarpScreen$1.PROFILE && System.currentTimeMillis() > this.QSt) {
         this.requestId++;
         this.BnurjqX("Player lookup timed out. Press Retry.");
      } else if (this.loadState == LarpScreen$1.STATS) {
         this.pollStatsFetch();
      }
   }

   private void MNonF() {
      final String var1 = this.gfx.getText().trim();
      if (!var1.matches("[A-Za-z0-9_]{1,16}")) {
         this.showStatus("Invalid IGN.");
      } else {
         final int var2 = ++this.requestId;
         this.loadState = LarpScreen$1.PROFILE;
         this.targetQuickBuy = Collections.emptyList();
         this.targetItemStacks = Collections.emptyList();
         this.targetPlayer = null;
         this.pendingResult = null;
         this.HpM = false;
         this.jgup = false;
         this.statusMessage = "Finding player...";
         this.QSt = System.currentTimeMillis() + 25000L;
         Jade.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
               PlayerApi$2 var1x;
               try {
                  var1x = PlayerApi.lookupProfileByName(var1);
               } catch (Throwable var3) {
                  var1x = null;
               }

               LarpScreen.setPendingResult(LarpScreen.this, new LarpScreen$2(var2, var1, var1x));
            }
         });
      }
   }

   private void KPugJ() {
      LarpScreen$2 var1 = this.pendingResult;
      if (var1 != null) {
         this.pendingResult = null;
         if (this.requestId == LarpScreen$2.getRequestId(var1) && this.loadState == LarpScreen$1.PROFILE) {
            PlayerApi$2 var2 = LarpScreen$2.getResolvedPlayer(var1);
            if (var2 != null && var2.getUuid() != null) {
               this.targetPlayer = var2;
               String var3 = var2.getName().isEmpty() ? LarpScreen$2.getRequestedName(var1) : var2.getName();
               this.gfx.setText(var3);
               this.loadedPlayerName = var3;
               StatsLookupService.getInstance().clearPlayer(var2.getUuid());
               this.loadState = LarpScreen$1.STATS;
               this.statusMessage = "Loading exact Quick Buy...";
               this.QSt = System.currentTimeMillis() + 25000L;
               this.nextPollTime = 0L;
               this.HpM = false;
            } else {
               this.BnurjqX("IGN not found.");
            }
         }
      }
   }

   private void pollStatsFetch() {
      long var1 = System.currentTimeMillis();
      UUID var3 = this.targetPlayer == null ? null : this.targetPlayer.getUuid();
      HypixelPlayerStats var4 = StatsLookupService.getInstance().getCachedStats(var3);
      if (var4 != null) {
         this.applyStatsResult(var4);
      } else {
         String var5 = StatsLookupService.getInstance().pollFailureReason(var3);
         if (var5 != null) {
            this.HpM = false;
            this.BnurjqX("Stats failed: " + formatErrorMessage(var5) + ". Press Retry.");
         } else if (var1 > this.QSt) {
            this.BnurjqX("Fetch timed out. Press Retry.");
         } else if (var3 != null && !this.HpM && var1 >= this.nextPollTime) {
            this.HpM = StatsLookupService.getInstance().requestManualStats(var3);
            if (this.HpM) {
               this.statusMessage = "Loading exact Quick Buy...";
               this.QSt = var1 + 90000L;
            } else {
               this.statusMessage = "Connecting to stats service...";
            }

            this.nextPollTime = var1 + 500L;
         }
      }
   }

   private void applyStatsResult(HypixelPlayerStats var1) {
      this.HpM = false;
      List var2 = var1 == null ? Collections.emptyList() : var1.getFavorites();
      if (!QuickBuyLayout.isValidLayout(var2)) {
         this.BnurjqX("Player has no complete 21-item Quick Buy. Press Retry.");
      } else {
         this.targetQuickBuy = Collections.unmodifiableList(new ArrayList<>(var2));
         this.targetItemStacks = resolveItemStacks(this.targetQuickBuy);
         this.loadState = LarpScreen$1.DONE;
         this.jgup = false;
         this.statusMessage = "Exact Quick Buy ready.";
         this.loadedPlayerName = this.gfx.getText().trim();
      }
   }

   private void BnurjqX(String var1) {
      this.loadState = LarpScreen$1.ERROR;
      this.HpM = false;
      this.showStatus(var1);
   }

   private void showStatus(String var1) {
      this.jgup = true;
      this.statusMessage = var1 == null ? "Something went wrong." : var1;
   }

   private void resetLookupState() {
      String var1 = this.gfx.getText().trim();
      if (!this.loadedPlayerName.isEmpty() && !var1.equalsIgnoreCase(this.loadedPlayerName)) {
         this.requestId++;
         this.targetQuickBuy = Collections.emptyList();
         this.targetItemStacks = Collections.emptyList();
         this.targetPlayer = null;
         this.loadState = LarpScreen$1.IDLE;
         this.HpM = false;
         this.statusMessage = "";
         this.jgup = false;
         this.loadedPlayerName = "";
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.prP78);
      } else if (var2 == 28 && this.gfx.isFocused()) {
         if (!this.isLoading()) {
            this.MNonF();
         }
      } else {
         if (this.gfx.isFocused() && this.gfx.keyTyped(var1, var2)) {
            this.resetLookupState();
         }
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         float var4 = this.openScale();
         int var5 = Math.round(this.width / 2.0F + (var1 - this.width / 2.0F) / var4);
         int var6 = Math.round(this.height / 2.0F + (var2 - this.height / 2.0F) / var4);
         if (this.CdiC.contains(var5, var6)) {
            this.gfx.setFocused(true);
         } else {
            this.gfx.setFocused(false);
            if (this.fetchButtonRect.contains(var5, var6) && !this.isLoading()) {
               this.MNonF();
            } else if (this.backButtonRect.contains(var5, var6)) {
               this.mc.displayGuiScreen(this.prP78);
            } else {
               if (this.continueButtonRect.contains(var5, var6) && this.FLHaR()) {
                  this.startMapping();
               }
            }
         }
      }
   }

   private void startMapping() {
      List var1 = QuickBuyLayoutComposer.findMissingItems(this.localQuickBuy, this.targetQuickBuy);
      if (var1.isEmpty()) {
         if (!this.vqq.beginQuickBuySetup(this.UDO, this.targetPlayerName, this.localQuickBuy, this.targetQuickBuy)) {
            this.showStatus("Setup couldn't start.");
         }
      } else {
         this.mc.displayGuiScreen(new LarpMappingScreen(this, this.UDO, this.vqq, this.targetPlayerName, this.gfx.getText().trim(), this.localQuickBuy, this.targetQuickBuy));
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.updateFlowAnimation();
      this.animatedWindowHeight = this.animatedWindowHeight + (this.getTargetHeight() - this.animatedWindowHeight) * this.tjyJ;
      this.previewReveal = this.approach(this.previewReveal, this.canContinue() ? 1.0F : 0.0F);
      drawRect(0, 0, this.width, this.height, -1206841071);
      float var4 = this.openScale();
      float var5 = this.width / 2.0F;
      float var6 = this.height / 2.0F;
      int var7 = Math.round(var5 + (var1 - var5) / var4);
      int var8 = Math.round(var6 + (var2 - var6) / var4);
      GlStateManager.pushMatrix();
      GlStateManager.translate(var5, var6, 0.0F);
      GlStateManager.scale(var4, var4, 1.0F);
      GlStateManager.translate(-var5, -var6, 0.0F);
      this.drawContents(var7, var8);
      GlStateManager.popMatrix();
      if (!this.hoveredItemName.isEmpty() && var4 > 0.985F) {
         this.drawTooltip(this.hoveredItemName, var1, var2);
      }
   }

   private void drawContents(int var1, int var2) {
      int var3 = Math.min(336, this.width - 24);
      int var4 = Math.round(this.animatedWindowHeight);
      int var5 = (this.width - var3) / 2;
      int var6 = (this.height - var4) / 2;
      this.drawWindow(var5, var6, var3, var4);
      int var7 = var6 + var4 - 32;
      this.CdiC = new ItemPreviewScreen$0(var5 + 12, var6 + 12, var3 - 24, 24);
      this.backButtonRect = new ItemPreviewScreen$0(var5 + 12, var7, 60, 20);
      this.fetchButtonRect = new ItemPreviewScreen$0(this.canContinue() ? var5 + (var3 - 60) / 2 : var5 + var3 - 72, var7, 60, 20);
      this.continueButtonRect = this.canContinue() ? new ItemPreviewScreen$0(var5 + var3 - 82, var7, 70, 20) : ItemPreviewScreen$0.EMPTY;
      this.gfx.render(this.CdiC.CONop, this.CdiC.YHn95, this.CdiC.getRight(), this.CdiC.dsyw());
      if (this.previewReveal > 0.01F) {
         this.drawPreview(this.targetQuickBuy, this.targetItemStacks, var5, var6 + 45, var3, var1, var2, this.previewReveal);
      } else {
         this.hoveredItemName = "";
      }

      if (this.isLoading()) {
         this.drawLoadingIndicator(var5, var6, var3);
      } else {
         this.GKdowkt(var5, var6, var3);
      }

      this.backButtonHover = this.approach(this.backButtonHover, this.backButtonRect.contains(var1, var2) ? 1.0F : 0.0F);
      this.ina = this.approach(this.ina, this.fetchButtonRect.contains(var1, var2) && !this.isLoading() ? 1.0F : 0.0F);
      this.continueButtonHover = this.approach(this.continueButtonHover, this.continueButtonRect.contains(var1, var2) && this.FLHaR() ? 1.0F : 0.0F);
      this.drawButton("Back", this.backButtonRect, this.backButtonHover, true, false);
      this.drawButton(this.loadState == LarpScreen$1.ERROR ? "Retry" : "Fetch", this.fetchButtonRect, this.ina, !this.isLoading(), false);
      if (this.canContinue()) {
         this.drawButton("Continue", this.continueButtonRect, this.continueButtonHover, this.FLHaR(), true);
      }
   }

   private void drawLoadingIndicator(int var1, int var2, int var3) {
      IFont var4 = this.settingFont();
      String var5 = this.statusMessage.isEmpty() ? "Loading..." : this.statusMessage;
      byte var6 = 14;
      int var7 = var6 + 7 + var4.getStringWidth(var5);
      int var8 = var1 + (var3 - var7) / 2;
      int var9 = var2 + 43;
      this.drawSpinner(var8 + var6 / 2.0F, var9 + var6 / 2.0F, 6.0F);
      var4.drawString(var5, var8 + var6 + 7, var9 + (var6 - var4.getFontHeight()) / 2.0F + 1.0F, -6645094, false);
   }

   private void drawSpinner(float var1, float var2, float var3) {
      int var4 = this.accent();
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

   private void GKdowkt(int var1, int var2, int var3) {
      String var4 = this.getStatusText();
      if (!var4.isEmpty()) {
         IFont var5 = this.settingFont();
         String var6 = truncateToWidth(var4, var3 - 28, var5);
         int var7 = !this.jgup && this.vqq.OYrRt() ? -6645094 : -34953;
         int var8 = this.canContinue() ? var2 + 145 : var2 + 43;
         var5.drawString(var6, var1 + (var3 - var5.getStringWidth(var6)) / 2.0F, var8, var7, false);
      }
   }

   private String getStatusText() {
      if (!this.vqq.OYrRt()) {
         return "Please go to a Bed Wars lobby!";
      } else {
         OpsecStore$0 var1 = this.vqq.ovob3();
         if (var1 != null && var1.isDirty()) {
            return "Restore required.";
         } else {
            return this.statusMessage == null ? "" : this.statusMessage;
         }
      }
   }

   private int getTargetHeight() {
      if (this.canContinue()) {
         return 206;
      } else {
         return !this.isLoading() && this.getStatusText().isEmpty() ? 74 : 96;
      }
   }

   private boolean canContinue() {
      return !this.isLoading() && !this.getTypedName().isEmpty() && QuickBuyLayout.isValidLayout(this.targetQuickBuy);
   }

   private boolean FLHaR() {
      return this.canContinue() && this.vqq.OYrRt();
   }

   private boolean isLoading() {
      return this.loadState == LarpScreen$1.PROFILE || this.loadState == LarpScreen$1.STATS;
   }

   private String getTypedName() {
      return this.gfx == null ? "" : this.gfx.getText().trim();
   }

   private static String truncateToWidth(String var0, int var1, IFont var2) {
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

   private static String formatErrorMessage(String var0) {
      return var0 != null && !var0.isEmpty() ? var0.replace('_', ' ') : "upstream error";
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public static LarpScreen$2 setPendingResult(LarpScreen var0, LarpScreen$2 var1) {
      return var0.pendingResult = var1;
   }
}
