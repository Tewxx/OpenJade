// Jade recovery: original class: jade.deps.eLz.APmfhBo
package jade.client.setting;

import jade.client.common.IFont;
import jade.client.common.ModuleComponent;
import jade.client.common.RenderUtils;
import jade.client.module.client.Gui;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class ItemSlotListComponent extends ItemListSearchComponent<ItemSlotListSetting> {
   private static final float MIN_ITEM_BUTTON_WIDTH = 9.0F;
   private static final float ROW_CONTENT_PADDING = 3.0F;
   private static final float BUTTON_LABEL_SCALE = 0.5F;
   private static final float BUTTON_ROW_GAP = 3.0F;
   private static final float LABEL_OFFSET_X = 10.0F;
   private static final float ROW_SIDE_MARGIN = 3.0F;
   private List<ItemSlotListComponent$1> cachedRows;
   private String awaitingKeyItemName;
   private String sDmlU;
   private float dragOffsetY;

   public ItemSlotListComponent(ItemSlotListSetting var1, ModuleComponent var2, float var3) {
      super(var1, var2, var3);
   }

   @Override
   protected int getEntryCount() {
      return this.pxP.getItems().size();
   }

   @Override
   public void updateLayout(int var1, int var2) {
      super.updateLayout(var1, var2);
      this.updateDragReorder();
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         this.sDmlU = null;
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe) {
         if (this.awaitingKeyItemName == null) {
            super.keyTyped(var1, var2);
         } else if (var2 == 1) {
            this.awaitingKeyItemName = null;
         } else {
            int var3 = XibawdKeFr.AsbN(var2, getHotbarKeyCodes());
            if (var3 > 0) {
               this.pxP.setSlotForItem(this.awaitingKeyItemName, var3);
               this.awaitingKeyItemName = null;
               this.invalidateEntryCache();
               this.markConfigDirty();
            }
         }
      }
   }

   @Override
   protected boolean hasPendingEdit() {
      return this.awaitingKeyItemName != null;
   }

   @Override
   protected void EaWp() {
      this.awaitingKeyItemName = null;
   }

   @Override
   protected void onSearchResultSelected(int var1, int var2) {
      this.awaitingKeyItemName = null;
   }

   @Override
   protected void CKe0(int var1, int var2) {
      this.awaitingKeyItemName = null;
   }

   @Override
   protected void onUnhandledClick(int var1, int var2, int var3) {
      this.awaitingKeyItemName = null;
   }

   @Override
   protected void renderEntryRowRange(SettingComponent$1 var1, float var2, int var3, int var4) {
      List var5 = this.GAfwG();

      for (int var6 = var3; var6 < var4; var6++) {
         ItemSlotListComponent$1 var7 = (ItemSlotListComponent$1)var5.get(var6);
         float var8 = this.getEntryListY(var1) - var2 + var6 * 12.0F;
         int var9 = ItemSlotListComponent$1.getItemKey(var7).equals(this.sDmlU) ? -14013892 : (var6 % 2 == 0 ? -15066582 : -14803410);
         this.drawItemRow(var7, var1.contentLeftX, var1.contentRightX, var8, var9);
      }
   }

   @Override
   protected boolean isEntryRowClicked(int var1, int var2, SettingComponent$1 var3) {
      int var4 = this.getRowIndexAt(var1, var2, var3);
      if (var4 < 0) {
         this.sDmlU = null;
         return false;
      } else {
         String var5 = this.pxP.getItems().get(var4);
         float var6 = this.getRowY(var3, var4);
         if (this.isOverRowDeleteButton(var1, var2, var6, var3.contentRightX)) {
            this.pxP.removeItem(var5);
            this.invalidateEntryCache();
            this.awaitingKeyItemName = null;
            this.sDmlU = null;
            this.markConfigDirty();
            this.clampEntryListScroll();
            this.updateSearchListHeight();
            this.moduleComponent.invalidateCategoryLayout();
            return true;
         } else if (this.Zysf(var1, var2, var6, var3.contentRightX, var5)) {
            this.awaitingKeyItemName = var5;
            this.sDmlU = null;
            return true;
         } else {
            this.sDmlU = var5;
            this.dragOffsetY = var2 - var6;
            if (!this.isOverSlotButton(var1, var2, var3)) {
               this.awaitingKeyItemName = null;
            }

            return true;
         }
      }
   }

   @Override
   protected void invalidateEntryCache() {
      this.cachedRows = null;
   }

   @Override
   protected void resetEditorState() {
      this.awaitingKeyItemName = null;
      this.sDmlU = null;
   }

   private List<ItemSlotListComponent$1> GAfwG() {
      List var1 = this.pxP.getItems();
      if (this.cachedRows == null || this.cachedRows.size() != var1.size()) {
         this.cachedRows = new ArrayList<>();

         for (ItemSlotListResolver$1 var3 : ItemSlotListResolver.resolveEntries(this.pxP)) {
            this.cachedRows.add(new ItemSlotListComponent$1(var3));
         }
      }

      return this.cachedRows;
   }

   private void drawItemRow(ItemSlotListComponent$1 var1, float var2, float var3, float var4, int var5) {
      RenderUtils.XNRNki(var2, var4, var3, var4 + 12.0F - 1.0F, var5);
      this.renderStackIcon(this.resolveDisplayStack(var1), var2 + 2.0F, var4);
      this.JxYy(ItemSlotListComponent$1.getDisplayName(var1) == null ? "" : ItemSlotListComponent$1.getDisplayName(var1), var2 + 13.0F, var4, -3355444);
      float var6 = var3 - 6.0F - 3.0F;
      float var7 = var6 - 3.0F;
      float var8 = var7 - this.zT13(ItemSlotListComponent$1.getItemKey(var1));
      this.drawKeyButton(var1, var8, var7, var4);
      this.renderRowDeleteButton(var3, var4);
   }

   private void drawKeyButton(ItemSlotListComponent$1 var1, float var2, float var3, float var4) {
      boolean var5 = ItemSlotListComponent$1.getItemKey(var1).equals(this.awaitingKeyItemName);
      float var6 = var4 + 2.0F;
      float var7 = var4 + 12.0F - 3.0F;
      RenderUtils.XNRNki(var2, var6, var3, var7, -15657956);
      RenderUtils.XNRNki(var2 + 1.0F, var6 + 1.0F, var3 - 1.0F, var7 - 1.0F, var5 ? -13281926 : -14399130);
      String var8 = XibawdKeFr.formatNumberOrEllipsis(var5, ItemSlotListComponent$1.getSlotIndex(var1));
      IFont var9 = Gui.getSettingFont();
      float var10 = var2 + (var3 - var2 - var9.getStringWidth(var8) * 0.5F) / 2.0F;
      float var11 = computeCenteredTextY(var6, var7 - var6) + 0.5F;
      drawSettingText(var8, var10, var11, -1511691);
   }

   private void updateDragReorder() {
      List var1 = this.pxP.getItems();
      if (this.sDmlU != null && !var1.isEmpty()) {
         SettingComponent$1 var2 = this.computeSettingBounds(true);
         float var3 = this.getEntryListY(var2);
         float var4 = this.getEntryListHeight();
         if (var4 <= 0.0F) {
            this.sDmlU = null;
         } else {
            if (var1.size() > 7 && this.lastMouseX >= var2.contentLeftX && this.lastMouseX <= var2.contentRightX) {
               float var5 = XibawdKeFr.kJxiM(this.DZt, var3, var4, 10.0F, 3.0F);
               if (var5 != 0.0F) {
                  this.entryListScroll.addToTarget(var5);
                  this.clampEntryListScroll();
               }
            }

            int var7 = var1.indexOf(this.sDmlU);
            if (var7 < 0) {
               this.sDmlU = null;
            } else {
               int var6 = XibawdKeFr.getRowIndexAtY(this.DZt - this.dragOffsetY, var3, this.entryListScroll.ORMWO(), 12.0F, var1.size());
               if (var6 != var7) {
                  this.pxP.moveItemToIndex(this.sDmlU, var6);
                  this.invalidateEntryCache();
                  this.markConfigDirty();
               }
            }
         }
      }
   }

   private int getRowIndexAt(int var1, int var2, SettingComponent$1 var3) {
      if (!this.isOverEntryList(var1, var2)) {
         return -1;
      } else {
         int var4 = (int)((var2 - this.getEntryListY(var3) + this.entryListScroll.ORMWO()) / 12.0F);
         if (var4 >= 0 && var4 < this.pxP.getItems().size()) {
            float var5 = this.getRowY(var3, var4);
            return var2 >= var5 && var2 < var5 + 12.0F ? var4 : -1;
         } else {
            return -1;
         }
      }
   }

   private float getRowY(SettingComponent$1 var1, int var2) {
      return this.getEntryListY(var1) - this.entryListScroll.ORMWO() + var2 * 12.0F;
   }

   private boolean isOverSlotButton(int var1, int var2, SettingComponent$1 var3) {
      int var4 = this.getRowIndexAt(var1, var2, var3);
      return var4 >= 0 && this.Zysf(var1, var2, this.getRowY(var3, var4), var3.contentRightX, this.pxP.getItems().get(var4));
   }

   private boolean Zysf(int var1, int var2, float var3, float var4, String var5) {
      float var6 = var4 - 6.0F - 3.0F - 3.0F;
      float var7 = var6 - this.zT13(var5);
      return XibawdKeFr.isWithinInsetBounds(var1, var2, var7, var6, var3, 12.0F);
   }

   private float zT13(String var1) {
      String var2 = XibawdKeFr.formatNumberOrEllipsis(var1 != null && var1.equals(this.awaitingKeyItemName), this.pxP.getSlotForItem(var1));
      return Math.max(9.0F, Gui.getSettingFont().getStringWidth(var2) * 0.5F + 6.0F);
   }

   private static int[] getHotbarKeyCodes() {
      Minecraft var0 = Minecraft.getMinecraft();
      int[] var1 = new int[var0.gameSettings.keyBindsHotbar.length];

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = var0.gameSettings.keyBindsHotbar[var2].getKeyCode();
      }

      return var1;
   }
}
