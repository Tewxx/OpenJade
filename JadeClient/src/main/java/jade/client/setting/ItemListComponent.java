// Jade recovery: original class: jade.deps.eLz.sN69AO
package jade.client.setting;

import jade.client.Jade;
import jade.client.common.ModuleComponent;
import jade.client.common.RenderUtils;
import jade.client.gui.AnimatedFloat;
import jade.client.module.render.arraylist.ColorTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ItemListComponent extends SettingComponent {
   private static final String CLOSE_ICON_TEXTURE = "/assets/jade/textures/gui/close.png";
   private static final String BACK_ARROW_TEXTURE = "/assets/jade/textures/gui/arrow_left.png";
   protected static final float MAX_POPUP_WIDTH = 250.0F;
   protected static final int MAX_VISIBLE_ENTRY_ROWS = 7;
   protected static final int MAX_VISIBLE_SEARCH_ROWS = 7;
   protected static final int IyvcvK = 6;
   protected static final float POPUP_PADDING = 3.0F;
   protected static final float LIST_ROW_GAP = 4.0F;
   protected static final float ROW_LABEL_FONT_SCALE = 0.56F;
   protected static final float POPUP_LABEL_FONT_SCALE = 0.56F;
   protected static final float ENTRY_ROW_HEIGHT = 11.0F;
   private static final float UUzz = 8.0F;
   private static final float POPUP_HEADER_HEIGHT = 18.0F;
   private static final float ENTRY_LIST_TOP_OFFSET = 14.0F;
   private static final int MXHwr = 12;
   protected final AnimatedFloat searchListScroll = new AnimatedFloat(200L);
   protected final AnimatedFloat entryListScroll = new AnimatedFloat(200L);
   protected float lastMouseX;
   protected float DZt;
   private final SmoothFloatAnimator searchListHeightAnim = new SmoothFloatAnimator();
   private boolean OvR;

   protected ItemListComponent(ModuleComponent var1, float var2, String var3) {
      super(var1, var2, var3, 128);
   }

   @Override
   public void renderComponent() {
      SettingComponent$1 var1 = this.computeSettingBounds(false);
      this.drawLabelText(var1, this.BNndC());
      this.renderAddRow(var1);
      this.Iaqs(var1);
   }

   @Override
   public void updateLayout(int var1, int var2) {
      super.updateLayout(var1, var2);
      this.lastMouseX = var1;
      this.DZt = var2;
      this.updateSearchListHeight();
      if (this.OvR) {
         this.renderPopup();
      }

      this.onMouseMove();
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         SettingComponent$1 var4 = this.computeSettingBounds(true);
         if (this.OvR) {
            return this.handlePopupClick(var1, var2, var3);
         } else if (var3 == 0 && this.isOverAddRow(var1, var2, var4)) {
            this.openEditor();
            return true;
         } else if (var3 == 0 && this.isEntryRowClicked(var1, var2, var4)) {
            this.onEntryRowClicked(var1, var2);
            return true;
         } else {
            if (this.SIpb7()) {
               this.clearSearchFocus();
            }

            this.onUnhandledClick(var1, var2, var3);
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean handlePopupClick(int var1, int var2, int var3) {
      SettingComponent$1 var4 = this.computeContentBounds();
      if (var3 == 0 && this.isOverPopupCloseButton(var1, var2)) {
         this.closeEditor();
         return true;
      } else if (var3 == 0 && this.isSearchResultClicked(var1, var2, var4)) {
         this.onSearchResultSelected(var1, var2);
         return true;
      } else if (this.isMouseOverTextInput(var1, var2, var4)) {
         this.setTextInputFocused(true);
         this.VroyC();
         return true;
      } else {
         SettingPopupLayout$1 var5 = this.computePopupBounds();
         if (!SettingPopupLayout.isPointInBounds(var1, var2, var5.panelLeft, var5.panelRight, var5.panelTop, var5.panelBottom)) {
            this.closeEditor();
         }

         return true;
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe) {
         if (this.OvR && var2 == 1) {
            if (!this.SIpb7() || !this.wm0779()) {
               this.closeEditor();
            }
         } else if (this.OvR) {
            if (var2 != 28 && var2 != 156 || !this.SIpb7()) {
               if (this.getSearchTextField().keyTyped(var1, var2)) {
                  this.YrDg(this.getSearchTextField().getText());
                  this.searchListScroll.snapTo(0.0F);
               }
            }
         }
      }
   }

   @Override
   public void keyPressed(int var1) {
      if (this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         float var2 = 20.0F * (var1 / 120.0F);
         if (this.OvR && this.isOverSearchResults(this.lastMouseX, this.DZt)) {
            if (var2 != 0.0F) {
               this.searchListScroll.addToTarget(-var2);
            }

            this.clampSearchListScroll();
         } else if (this.TQAe() && this.getEntryCount() > 7) {
            if (var2 != 0.0F) {
               this.entryListScroll.addToTarget(-var2);
            }

            this.clampEntryListScroll();
         }
      }
   }

   @Override
   public void resetEditingState() {
      super.resetEditingState();
      this.getSearchTextField().setText("");
      this.onEditorClosed();
      this.OvR = false;
      this.searchListScroll.snapTo(0.0F);
      this.entryListScroll.snapTo(0.0F);
      this.searchListHeightAnim.resetAnimation();
   }

   @Override
   public float getHeight() {
      return this.getComponentHeight();
   }

   public boolean SIpb7() {
      return this.isTextInputFocused();
   }

   public void clearSearchFocus() {
      this.setTextInputFocused(false);
      this.updateSearchListHeight();
   }

   @Override
   public boolean isFocused() {
      return this.SIpb7() || this.hasPendingEdit();
   }

   @Override
   public void unfocus() {
      this.EaWp();
      this.clearSearchFocus();
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      SettingComponent$1 var3 = this.computeSettingBounds(true);
      SettingPopupLayout$1 var4 = this.computePopupBounds();
      return this.isOverAddRow(var1, var2, var3) || this.OvR && SettingPopupLayout.isPointInBounds(var1, var2, var4.panelLeft, var4.panelRight, var4.panelTop, var4.panelBottom) || this.isOverEntryList(var1, var2);
   }

   public boolean isMouseOverAnyPart(float var1, float var2) {
      if (this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         return this.OvR && this.isOverSearchResults(var1, var2) && this.getSearchResultCount() > 12 ? true : this.isOverEntryList(var1, var2) && this.getEntryCount() > 7;
      } else {
         return false;
      }
   }

   public float getComponentHeight() {
      int var1 = this.getEntryCount();
      float var2 = var1 == 0 ? 0.0F : 4.0F + SettingPopupLayout.computeClampedListHeight(var1, 7, 12.0F);
      return 24.0F + var2;
   }

   protected final float getSearchListHeight() {
      return this.searchListHeightAnim.getAnimatedValue();
   }

   protected final float getEntryListY(SettingComponent$1 var1) {
      return var1.listTopY + 4.0F;
   }

   protected final float getEntryListHeight() {
      return SettingPopupLayout.computeClampedListHeight(this.getEntryCount(), 7, 12.0F);
   }

   protected final void clampSearchListScroll() {
      int var1 = this.OvR ? 12 : 7;
      this.searchListScroll.clampTarget(0.0F, SettingPopupLayout.computeMaxScroll(this.getSearchResultCount(), var1, 12.0F));
   }

   protected final void clampEntryListScroll() {
      this.entryListScroll.clampTarget(0.0F, SettingPopupLayout.computeMaxScroll(this.getEntryCount(), 7, 12.0F));
   }

   protected final void updateSearchListHeight() {
      float var1 = !this.OvR && this.SIpb7() && this.getSearchResultCount() > 0 ? SettingPopupLayout.computeClampedListHeight(this.getSearchResultCount(), 7, 12.0F) : 0.0F;
      this.searchListHeightAnim.animateTo(var1);
   }

   protected final void refreshAfterListChange() {
      this.markConfigDirty();
      if (this.OvR) {
         this.setTextInputFocused(true);
      }

      this.updateSearchListHeight();
      this.moduleComponent.invalidateCategoryLayout();
   }

   protected final void markConfigDirty() {
      if (Jade.Grq != null) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }

   protected final ItemStack resolveDisplayStack(ItemListComponent$0 var1) {
      if (var1 == null) {
         return null;
      } else {
         return var1.itemStacks != null && !var1.itemStacks.isEmpty() ? var1.itemStacks.get((int)(System.currentTimeMillis() / 1000L % var1.itemStacks.size())) : var1.itemStack;
      }
   }

   protected final boolean Sbx97() {
      return this.OvR;
   }

   protected final void ewwE(float var1, float var2, float var3, int var4, String var5) {
      RenderUtils.XNRNki(var1, var3, var2, var3 + 12.0F - 1.0F, var4);
      ResourceLocation var6 = RenderUtils.getIconTexture("/assets/jade/textures/gui/arrow_left.png");
      if (var6 != null) {
         float var7 = var3 + 2.5F;
         RenderUtils.drawIconTexture(var6, var1 + 2.0F, var7, 6, -1);
      }

      this.JxYy(var5 == null ? "Back" : var5, var1 + 13.0F, var3, -3355444);
   }

   protected final void Rm47(String var1, ItemStack var2, float var3, float var4, float var5, int var6, boolean var7) {
      RenderUtils.XNRNki(var3, var5, var4, var5 + 12.0F - 1.0F, var6);
      this.renderStackIcon(var2, var3 + 2.0F, var5);
      this.JxYy(var1 == null ? "" : var1, var3 + 13.0F, var5, -3355444);
      if (var7) {
         this.renderRowDeleteButton(var4, var5);
      }
   }

   protected final void JxYy(String var1, float var2, float var3, int var4) {
      float var5 = computeCenteredTextYAtScale(var3, 11.0F, 0.56F) + 0.56F;
      drawScaledSettingText(var1, var2, var5, var4, 0.56F);
   }

   protected final void renderRowDeleteButton(float var1, float var2) {
      ResourceLocation var3 = RenderUtils.getIconTexture("/assets/jade/textures/gui/close.png");
      if (var3 != null) {
         float var4 = var1 - 6.0F - 3.0F;
         float var5 = var2 + 2.5F;
         RenderUtils.drawIconTexture(var3, var4, var5, 6, ColorTheme.getGradient(ColorTheme.hiddenBind[0], ColorTheme.hiddenBind[1], 0.0));
      }
   }

   protected final boolean isOverRowDeleteButton(float var1, float var2, float var3, float var4) {
      float var5 = var4 - 6.0F - 3.0F;
      float var6 = var3 + 3.0F;
      return SettingPopupLayout.isPointInBounds(var1, var2, var5, var5 + 6.0F, var6, var6 + 6.0F);
   }

   protected final void renderStackIcon(ItemStack var1, float var2, float var3) {
      if (var1 != null) {
         RenderItem var4 = Minecraft.getMinecraft().getRenderItem();
         double var5 = 0.55;
         float var7 = var3 + (11.0F - (float)(16.0 * var5)) / 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.scale(var5, var5, var5);
         GlStateManager.translate(var2 / var5, var7 / var5, 0.0);
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.disableBlend();
         var4.renderItemAndEffectIntoGUI(var1, 0, 0);
         GlStateManager.enableBlend();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.popMatrix();
      }
   }

   protected final boolean isOverSearchList(float var1, float var2) {
      if (this.OvR) {
         return this.isOverSearchResults(var1, var2);
      } else {
         float var3 = this.searchListHeightAnim.getAnimatedValue();
         if (this.getSearchResultCount() != 0 && !(var3 <= 0.0F)) {
            SettingComponent$1 var4 = this.computeSettingBounds(true);
            return SettingPopupLayout.isPointInListArea(var1, var2, var4.contentLeftX, var4.contentRightX, var4.listTopY, var3);
         } else {
            return false;
         }
      }
   }

   protected final boolean isOverEntryList(float var1, float var2) {
      if (this.getEntryCount() == 0) {
         return false;
      } else {
         SettingComponent$1 var3 = this.computeSettingBounds(true);
         return SettingPopupLayout.isPointInListArea(var1, var2, var3.contentLeftX, var3.contentRightX, this.getEntryListY(var3), this.getEntryListHeight());
      }
   }

   protected final boolean isSearchListHovered() {
      return this.isOverSearchList(this.lastMouseX, this.DZt);
   }

   protected final boolean TQAe() {
      return this.isOverEntryList(this.lastMouseX, this.DZt);
   }

   private void renderAddRow(SettingComponent$1 var1) {
      float var2 = var1.contentTopY + 1.0F;
      float var3 = var1.contentTopY + 12.0F - 1.0F;
      int var4 = this.isOverAddRow(this.lastMouseX, this.DZt, var1) ? -14342858 : -15132379;
      RenderUtils.XNRNki(var1.contentLeftX, var2, var1.contentRightX, var3, var4);
      RenderUtils.drawRectOutline(var1.contentLeftX, var2, var1.contentRightX, var3, 1.0F, -13619134);
      drawSettingText("+ Add", var1.contentLeftX + 6.0F, computeCenteredTextY(var2, var3 - var2), -2236963);
   }

   private boolean isOverAddRow(float var1, float var2, SettingComponent$1 var3) {
      return SettingPopupLayout.isPointInBounds(var1, var2, var3.contentLeftX, var3.contentRightX, var3.contentTopY + 1.0F, var3.contentTopY + 12.0F - 1.0F);
   }

   private void openEditor() {
      this.OvR = true;
      this.setTextInputFocused(true);
      if (!this.getSearchTextField().getText().isEmpty()) {
         this.YrDg(this.getSearchTextField().getText());
      }

      this.searchListScroll.snapTo(0.0F);
   }

   private void closeEditor() {
      this.OvR = false;
      this.setTextInputFocused(false);
      this.getSearchTextField().setText("");
      this.onEditorClosed();
      this.searchListScroll.snapTo(0.0F);
   }

   private void renderPopup() {
      SettingComponent$1 var1 = this.computeContentBounds();
      SettingPopupLayout$1 var2 = this.computePopupBounds();
      RenderUtils.XNRNki(var2.panelLeft - 2.0F, var2.panelTop - 2.0F, var2.panelRight + 2.0F, var2.panelBottom + 2.0F, -1442840576);
      RenderUtils.XNRNki(var2.panelLeft, var2.panelTop, var2.panelRight, var2.panelBottom, -267185379);
      RenderUtils.drawRectOutline(var2.panelLeft, var2.panelTop, var2.panelRight, var2.panelBottom, 1.0F, -13355960);
      drawSettingText("Add " + this.BNndC(), var2.panelLeft + 8.0F, computeCenteredTextY(var2.panelTop, 18.0F), -1579026);
      this.renderRowDeleteButton(var2.panelRight - 3.0F, var2.panelTop + 3.0F);
      this.drawTextInputBackground(var1);
      this.renderSearchResults(var1);
   }

   private void renderSearchResults(SettingComponent$1 var1) {
      int var2 = this.getSearchResultCount();
      if (var2 == 0) {
         String var7 = this.getSearchTextField().getText().trim().isEmpty() ? "Start typing to search" : "No matches";
         drawSettingText(var7, var1.contentLeftX + 4.0F, var1.listTopY + 5.0F, -7500392);
      } else {
         float var3 = SettingPopupLayout.computeClampedListHeight(var2, 12, 12.0F);
         RenderUtils.pushScissorRect(var1.contentLeftX, var1.listTopY, var1.contentRightX - var1.contentLeftX, var3);
         float var4 = this.searchListScroll.ORMWO();
         SettingPopupLayout$2 var5 = SettingPopupLayout.computeVisibleRowRange(var4, 12.0F, var2, 12);
         int var6 = SettingPopupLayout.getRowIndexAtPoint(this.lastMouseX, this.DZt, var1.contentLeftX, var1.contentRightX, var1.listTopY, var3, var4, 12.0F, var2);
         this.XMImty(var1, var4, var5.firstVisibleRow, var5.lastVisibleRowExclusive, var6);
         RenderUtils.restoreScissorState();
      }
   }

   private boolean isOverPopupCloseButton(float var1, float var2) {
      SettingPopupLayout$1 var3 = this.computePopupBounds();
      float var4 = var3.panelRight - 3.0F - 6.0F - 3.0F;
      float var5 = var3.panelTop + 6.0F;
      return SettingPopupLayout.isPointInBounds(var1, var2, var4, var4 + 6.0F, var5, var5 + 6.0F);
   }

   private boolean isOverSearchResults(float var1, float var2) {
      int var3 = this.getSearchResultCount();
      if (var3 == 0) {
         return false;
      } else {
         SettingComponent$1 var4 = this.computeContentBounds();
         return SettingPopupLayout.isPointInListArea(var1, var2, var4.contentLeftX, var4.contentRightX, var4.listTopY, SettingPopupLayout.computeClampedListHeight(var3, 12, 12.0F));
      }
   }

   private SettingComponent$1 computeContentBounds() {
      SettingPopupLayout$1 var1 = this.computePopupBounds();
      SettingComponent$1 var2 = new SettingComponent$1();
      var2.boundsX = var1.panelLeft;
      var2.boundsY = var1.panelTop;
      var2.boundsWidth = var1.panelRight - var1.panelLeft;
      var2.contentLeftX = var1.panelLeft + 8.0F;
      var2.contentRightX = var1.panelRight - 8.0F;
      var2.contentTopY = var1.panelTop + 18.0F;
      var2.listTopY = var2.contentTopY + 14.0F + 4.0F;
      return var2;
   }

   private SettingPopupLayout$1 computePopupBounds() {
      return SettingPopupLayout.computePopupRect(this.moduleComponent.categoryComponent.EWZxNc(), this.moduleComponent.categoryComponent.getPanelY(), this.moduleComponent.categoryComponent.getPanelWidth(), this.moduleComponent.categoryComponent.contentHeight);
   }

   private void Iaqs(SettingComponent$1 var1) {
      int var2 = this.getEntryCount();
      if (var2 != 0) {
         float var3 = this.getEntryListY(var1);
         float var4 = this.getEntryListHeight();
         float var5 = this.moduleComponent.categoryComponent.animatedPanelY - var1.boundsY;
         RenderUtils.pushScissorRect(var1.contentLeftX, var3 + var5, var1.contentRightX - var1.contentLeftX, var4);
         float var6 = this.entryListScroll.ORMWO();
         SettingPopupLayout$2 var7 = SettingPopupLayout.computeVisibleRowRange(var6, 12.0F, var2, 7);
         this.renderEntryRowRange(var1, var6, var7.firstVisibleRow, var7.lastVisibleRowExclusive);
         RenderUtils.restoreScissorState();
      }
   }

   protected boolean hasPendingEdit() {
      return false;
   }

   protected void EaWp() {
   }

   protected void onMouseMove() {
   }

   protected void onSearchResultSelected(int var1, int var2) {
   }

   protected void onEntryRowClicked(int var1, int var2) {
   }

   protected void CKe0(int var1, int var2) {
   }

   protected void onUnhandledClick(int var1, int var2, int var3) {
   }

   protected abstract String BNndC();

   protected abstract int getEntryCount();

   protected abstract int getSearchResultCount();

   protected abstract void XMImty(SettingComponent$1 var1, float var2, int var3, int var4, int var5);

   protected abstract void renderEntryRowRange(SettingComponent$1 var1, float var2, int var3, int var4);

   protected abstract boolean isSearchResultClicked(int var1, int var2, SettingComponent$1 var3);

   protected abstract boolean isEntryRowClicked(int var1, int var2, SettingComponent$1 var3);

   protected abstract void YrDg(String var1);

   protected abstract boolean wm0779();

   protected abstract void VroyC();

   protected abstract void onEditorClosed();
}
