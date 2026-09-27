// Jade recovery: original class: jade.deps.eLz.ubxkXeRY
package jade.client.gui;

import jade.client.common.IFont;
import jade.client.common.ItemNames;
import jade.client.common.RoundedRect;
import jade.client.module.minigames.Opsec;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public final class LarpMappingScreen extends ItemPreviewScreen {
   private static final int PAGE_SIZE = 7;
   private final GuiScreen hfaUt;
   private final GuiScreen Ifd;
   private final Opsec uctJ;
   private final String targetPlayerName;
   private final String larpDisplayName;
   private final List<String> localQuickBuy;
   private final List<String> larpSlotItemIds;
   private final List<String> missingItems;
   private final List<String> replacementCandidates;
   private final Map<String, String> NXSy = new LinkedHashMap<>();
   private List<String> UdU;
   private List<ItemStack> nuL;
   private String selectedMissingItem = "";
   private String TIjnud = "Select a missing item, then choose a highlighted Larp slot.";
   private boolean hasError;
   private int lT46;
   private float openProgress;
   private float backButtonHover;
   private float AQx;
   private float LYPl7;
   private float nextButtonHover;
   private final float[] slotHoverAnimations = new float[7];
   private final ItemPreviewScreen$0[] missingItemBounds = new ItemPreviewScreen$0[7];
   private final ItemPreviewScreen$0[] larpSlotBounds = new ItemPreviewScreen$0[21];
   private ItemPreviewScreen$0 Vtk = ItemPreviewScreen$0.EMPTY;
   private ItemPreviewScreen$0 ruaJq = ItemPreviewScreen$0.EMPTY;
   private ItemPreviewScreen$0 previousPageButton = ItemPreviewScreen$0.EMPTY;
   private ItemPreviewScreen$0 nextPageButton = ItemPreviewScreen$0.EMPTY;

   public LarpMappingScreen(GuiScreen var1, GuiScreen var2, Opsec var3, String var4, String var5, List<String> var6, List<String> var7) {
      this.hfaUt = var1;
      this.Ifd = var2;
      this.uctJ = var3;
      this.targetPlayerName = var4 == null ? "" : var4.trim();
      this.larpDisplayName = var5 == null ? "" : var5.trim();
      this.localQuickBuy = ytlk(var6);
      this.larpSlotItemIds = ytlk(var7);
      this.missingItems = QuickBuyLayoutComposer.findMissingItems(this.localQuickBuy, this.larpSlotItemIds);
      this.replacementCandidates = QuickBuyLayoutComposer.findExtraItems(this.localQuickBuy, this.larpSlotItemIds);
      this.UdU = this.larpSlotItemIds;
      this.nuL = resolveItemStacks(this.UdU);

      for (int var8 = 0; var8 < this.missingItemBounds.length; var8++) {
         this.missingItemBounds[var8] = ItemPreviewScreen$0.EMPTY;
      }

      for (int var9 = 0; var9 < this.larpSlotBounds.length; var9++) {
         this.larpSlotBounds[var9] = ItemPreviewScreen$0.EMPTY;
      }
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(false);
      this.buttonList.clear();
      this.beginFlowAnimation();
      this.openProgress = 0.0F;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.hfaUt);
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         float var4 = this.openScale();
         int var5 = Math.round(this.width / 2.0F + (var1 - this.width / 2.0F) / var4);
         int var6 = Math.round(this.height / 2.0F + (var2 - this.height / 2.0F) / var4);
         if (this.Vtk.contains(var5, var6)) {
            this.mc.displayGuiScreen(this.hfaUt);
         } else if (this.previousPageButton.contains(var5, var6) && this.lT46 > 0) {
            this.lT46--;
         } else if (this.nextPageButton.contains(var5, var6) && this.lT46 + 1 < this.getPageCount()) {
            this.lT46++;
         } else {
            for (int var7 = 0; var7 < this.missingItemBounds.length; var7++) {
               int var8 = this.lT46 * 7 + var7;
               if (var8 < this.missingItems.size() && this.missingItemBounds[var7].contains(var5, var6)) {
                  this.selectedMissingItem = this.missingItems.get(var8);
                  this.hasError = false;
                  this.TIjnud = "Now choose the highlighted Larp item to replace.";
                  return;
               }
            }

            if (!this.selectedMissingItem.isEmpty()) {
               for (int var10 = 0; var10 < this.larpSlotBounds.length; var10++) {
                  if (this.larpSlotBounds[var10].contains(var5, var6)) {
                     String var12 = this.larpSlotItemIds.get(var10);
                     if (this.replacementCandidates.contains(var12) && !this.isReplacementTaken(var12, this.selectedMissingItem)) {
                        this.NXSy.put(this.selectedMissingItem, var12);
                        this.selectedMissingItem = "";
                        this.rebuildPreviewItems();
                        this.hasError = false;
                        this.TIjnud = this.NXSy.size() == this.missingItems.size() ? "All missing items mapped. Review and continue." : "Select the next missing item.";
                        return;
                     }

                     return;
                  }
               }
            }

            if (this.ruaJq.contains(var5, var6) && this.vPx5()) {
               try {
                  List var11 = QuickBuyLayoutComposer.composeLayoutWithReplacements(this.localQuickBuy, this.larpSlotItemIds, this.NXSy);
                  if (!this.uctJ.beginQuickBuySetup(this.Ifd, this.targetPlayerName, this.localQuickBuy, var11)) {
                     this.showError("Setup couldn't start.");
                  }
               } catch (IllegalArgumentException var9) {
                  this.showError("Replacement mapping is incomplete.");
               }
            }
         }
      }
   }

   private void rebuildPreviewItems() {
      ArrayList var1 = new ArrayList<>(this.larpSlotItemIds);

      for (Entry var3 : this.NXSy.entrySet()) {
         int var4 = var1.indexOf(var3.getValue());
         if (var4 >= 0) {
            var1.set(var4, var3.getKey());
         }
      }

      this.UdU = Collections.unmodifiableList(var1);
      this.nuL = resolveItemStacks(this.UdU);
   }

   private boolean isReplacementTaken(String var1, String var2) {
      for (Entry var4 : this.NXSy.entrySet()) {
         if (!((String)var4.getKey()).equals(var2) && ((String)var4.getValue()).equals(var1)) {
            return true;
         }
      }

      return false;
   }

   private void showError(String var1) {
      this.hasError = true;
      this.TIjnud = var1;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.updateFlowAnimation();
      this.openProgress = this.approach(this.openProgress, 1.0F);
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
      this.epfSh2(var7, var8);
      GlStateManager.popMatrix();
      if (!this.hoveredItemName.isEmpty() && var4 > 0.985F) {
         this.drawTooltip(this.hoveredItemName, var1, var2);
      }
   }

   private void epfSh2(int var1, int var2) {
      int var3 = Math.min(336, this.width - 24);
      int var4 = Math.min(224, this.height - 18);
      int var5 = (this.width - var3) / 2;
      int var6 = (this.height - var4) / 2;
      this.drawWindow(var5, var6, var3, var4);
      IFont var7 = this.headerFont();
      IFont var8 = this.settingFont();
      String var9 = "Larping as " + this.larpDisplayName;
      var7.drawString(var9, var5 + (var3 - var7.getStringWidth(var9)) / 2.0F, var6 + 10, -986896, false);
      String var10 = "Missing items " + (this.lT46 * 7 + 1) + "-" + Math.min(this.missingItems.size(), (this.lT46 + 1) * 7) + " of " + this.missingItems.size();
      var8.drawString(var10, var5 + (var3 - var8.getStringWidth(var10)) / 2.0F, var6 + 27, -6645094, false);
      short var11 = 220;
      int var12 = var5 + (var3 - var11) / 2;
      int var13 = var6 + 39;
      this.hoveredItemName = "";

      for (int var14 = 0; var14 < 7; var14++) {
         int var15 = this.lT46 * 7 + var14;
         if (var15 >= this.missingItems.size()) {
            this.missingItemBounds[var14] = ItemPreviewScreen$0.EMPTY;
         } else {
            String var16 = this.missingItems.get(var15);
            int var17 = var12 + var14 * 32;
            ItemPreviewScreen$0 var18 = new ItemPreviewScreen$0(var17, var13, 28, 28);
            this.missingItemBounds[var14] = var18;
            boolean var19 = var18.contains(var1, var2);
            this.slotHoverAnimations[var14] = this.approach(this.slotHoverAnimations[var14], var19 ? 1.0F : 0.0F);
            boolean var20 = var16.equals(this.selectedMissingItem);
            boolean var21 = this.NXSy.containsKey(var16);
            int var22 = var20 ? -15181246 : (var21 ? -14666450 : lerpColor(-15461356, -14408404, this.slotHoverAnimations[var14]));
            this.round(var17, var13, 28.0F, 28.0F, 4.0F, var22);
            int var23 = var20 ? this.accent() : (var21 ? -2008430197 : 352321535);
            RoundedRect.drawRoundedOutline(var17, var13, 28.0F, 28.0F, 4.0F, var20 ? 0.7F : 0.22F, new Color(0, 0, 0, 0), new Color(var23, true));
            this.drawItem(QuickBuyItems.createItemStack(var16), var17 + 6, var13 + 6);
            if (var19) {
               this.hoveredItemName = ItemNames.formatSkyblockName(var16);
            }
         }
      }

      this.previousPageButton = new ItemPreviewScreen$0(var5 + 8, var13 + 4, 20, 20);
      this.nextPageButton = new ItemPreviewScreen$0(var5 + var3 - 28, var13 + 4, 20, 20);
      this.LYPl7 = this.approach(this.LYPl7, this.previousPageButton.contains(var1, var2) && this.lT46 > 0 ? 1.0F : 0.0F);
      this.nextButtonHover = this.approach(this.nextButtonHover, this.nextPageButton.contains(var1, var2) && this.lT46 + 1 < this.getPageCount() ? 1.0F : 0.0F);
      this.drawButton("<", this.previousPageButton, this.LYPl7, this.lT46 > 0, false);
      this.drawButton(">", this.nextPageButton, this.nextButtonHover, this.lT46 + 1 < this.getPageCount(), false);
      int var24 = var6 + 78;
      String var25 = this.hoveredItemName;
      this.drawPreview(this.UdU, this.nuL, var5, var24, var3, var1, var2, this.openProgress);
      if (!var25.isEmpty()) {
         this.hoveredItemName = var25;
      }

      this.vnMg(var5, var24, var3, var1, var2);
      String var26 = this.TIjnud;
      int var27 = var3 - 28;

      while (!var26.isEmpty() && var8.getStringWidth(var26) > var27) {
         var26 = var26.substring(0, var26.length() - 1);
      }

      int var28 = !this.hasError && this.uctJ.OYrRt() ? -6645094 : -34953;
      if (!this.uctJ.OYrRt()) {
         var26 = "Please go to a Bed Wars lobby!";
      }

      var8.drawString(var26, var5 + (var3 - var8.getStringWidth(var26)) / 2.0F, var6 + var4 - 47, var28, false);
      int var29 = var6 + var4 - 30;
      this.Vtk = new ItemPreviewScreen$0(var5 + 12, var29, 60, 20);
      this.ruaJq = new ItemPreviewScreen$0(var5 + var3 - 82, var29, 70, 20);
      this.backButtonHover = this.approach(this.backButtonHover, this.Vtk.contains(var1, var2) ? 1.0F : 0.0F);
      this.AQx = this.approach(this.AQx, this.ruaJq.contains(var1, var2) && this.vPx5() ? 1.0F : 0.0F);
      this.drawButton("Back", this.Vtk, this.backButtonHover, true, false);
      this.drawButton("Continue", this.ruaJq, this.AQx, this.vPx5(), true);
   }

   private void vnMg(int var1, int var2, int var3, int var4, int var5) {
      short var6 = 220;
      int var7 = var1 + (var3 - var6) / 2;

      for (int var8 = 0; var8 < 21; var8++) {
         int var9 = var7 + var8 % 7 * 32;
         int var10 = var2 + var8 / 7 * 32;
         ItemPreviewScreen$0 var11 = new ItemPreviewScreen$0(var9, var10, 28, 28);
         this.larpSlotBounds[var8] = var11;
         String var12 = this.larpSlotItemIds.get(var8);
         if (this.replacementCandidates.contains(var12)) {
            String var13 = this.getMissingItemForSlot(var12);
            boolean var14 = !var13.isEmpty() && !var13.equals(this.selectedMissingItem);
            boolean var15 = !this.selectedMissingItem.isEmpty() && !var14;
            boolean var16 = var15 && var11.contains(var4, var5);
            int var17 = var16 ? -9445206 : (var15 ? this.accent() : (var13.isEmpty() ? 1719105399 : -2006949792));
            float var18 = var16 ? 0.9F : (var15 ? 0.55F : 0.3F);
            RoundedRect.drawRoundedOutline(var9 + 0.5F, var10 + 0.5F, 27.0F, 27.0F, 3.5F, var18, new Color(0, 0, 0, 0), new Color(var17, true));
         }
      }
   }

   private String getMissingItemForSlot(String var1) {
      for (Entry var3 : this.NXSy.entrySet()) {
         if (((String)var3.getValue()).equals(var1)) {
            return (String)var3.getKey();
         }
      }

      return "";
   }

   private int getPageCount() {
      return Math.max(1, (this.missingItems.size() + 7 - 1) / 7);
   }

   private boolean vPx5() {
      return this.NXSy.size() == this.missingItems.size() && this.uctJ.OYrRt();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private static List<String> ytlk(List<String> var0) {
      return Collections.unmodifiableList(new ArrayList<>(var0));
   }
}
