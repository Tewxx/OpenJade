// Jade recovery: original class: jade.deps.eLz.il6qjk
package jade.client.common;

import jade.client.module.render.blockesp.BlockEspParser$0;
import jade.client.module.render.blockesp.BlockEspParser$1;
import jade.client.setting.BlockColorListSetting;
import jade.client.setting.BlockListSetting;
import jade.client.setting.ItemListComponent;
import jade.client.setting.SettingComponent$1;
import jade.client.setting.ScrollListRowUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class BlockListComponent extends ItemListComponent {
   private static final float SWATCH_SIZE = 7.0F;
   private static final float SWATCH_MARGIN = 3.0F;
   private static final float SWATCH_PADDING = 4.0F;
   public final BlockListSetting blockListSetting;
   private final BlockListSearchModel listModel;
   private final HsbColorPickerState colorPicker = new HsbColorPickerState();
   private List<BlockListComponent$1> dSk4;

   public BlockListComponent(BlockListSetting var1, ModuleComponent var2, float var3) {
      super(var2, var3, "Search blocks...");
      this.blockListSetting = var1;
      this.listModel = new BlockListSearchModel(var1);
   }

   @Override
   protected String BNndC() {
      return this.listModel.cuWb2();
   }

   @Override
   protected int getEntryCount() {
      return this.listModel.getSelectedCount();
   }

   @Override
   protected int getSearchResultCount() {
      return this.listModel.getRowCount();
   }

   @Override
   public boolean ejbAn() {
      return this.blockListSetting.visible;
   }

   @Override
   public String getGroupName() {
      return this.blockListSetting.groupSetting == null ? "" : this.blockListSetting.groupSetting.getName();
   }

   @Override
   protected void XMImty(SettingComponent$1 var1, float var2, int var3, int var4, int var5) {
      if (this.listModel.isGroupExpanded()) {
         String var11 = this.listModel.getGroupLabel();

         for (int var12 = var3; var12 < var4; var12++) {
            float var13 = var1.listTopY - var2 + var12 * 12.0F;
            int var14 = ScrollListRowUtils.getRowColor(var12, var5);
            if (var12 == 0) {
               this.ewwE(var1.contentLeftX, var1.contentRightX, var13, var14, var11);
            } else if (var12 == 1) {
               this.Rm47(var11 + " (All)", this.Acmt8(), var1.contentLeftX, var1.contentRightX, var13, var14, false);
            } else {
               BlockEspParser$0 var15 = this.listModel.m249().get(var12 - 2);
               this.Rm47(var15.displayName, var15.createItemStack(), var1.contentLeftX, var1.contentRightX, var13, var14, false);
            }
         }
      } else {
         for (int var6 = var3; var6 < var4; var6++) {
            BlockEspParser$1 var7 = this.listModel.getSearchResult(var6);
            float var8 = var1.listTopY - var2 + var6 * 12.0F;
            int var9 = ScrollListRowUtils.getRowColor(var6, var5);
            if (var7.isSingleMatch()) {
               BlockEspParser$0 var10 = var7.nh7.get(0);
               this.Rm47(var10.displayName, var10.createItemStack(), var1.contentLeftX, var1.contentRightX, var8, var9, false);
            } else {
               this.Rm47(var7.buildGroupLabel(), var7.getRepresentativeStack(), var1.contentLeftX, var1.contentRightX, var8, var9, false);
            }
         }
      }
   }

   @Override
   protected void renderEntryRowRange(SettingComponent$1 var1, float var2, int var3, int var4) {
      List var5 = this.getRows();

      for (int var6 = var3; var6 < var4; var6++) {
         BlockListComponent$1 var7 = (BlockListComponent$1)var5.get(var6);
         float var8 = this.getEntryListY(var1) - var2 + var6 * 12.0F;
         int var9 = var6 % 2 == 0 ? -15066582 : -14803410;
         if (BlockListComponent$1.getColor(var7) == null) {
            this.Rm47(var7.displayName, this.resolveDisplayStack(var7), var1.contentLeftX, var1.contentRightX, var8, var9, true);
         } else {
            this.renderColorRow(var7, var1.contentLeftX, var1.contentRightX, var8, var9);
         }
      }
   }

   @Override
   protected boolean isSearchResultClicked(int var1, int var2, SettingComponent$1 var3) {
      if (!this.SIpb7()) {
         return false;
      } else {
         int var4 = ScrollListRowUtils.getRowIndexAt(var1, var2, var3.contentLeftX, var3.contentRightX, var3.listTopY, this.searchListScroll.ORMWO(), 12.0F, this.listModel.getRowCount());
         if (var4 < 0) {
            return false;
         } else if (this.listModel.isGroupExpanded() && var4 == 0) {
            this.collapseSearchGroup();
            return true;
         } else if (!this.listModel.isGroupExpanded() && !this.listModel.getSearchResult(var4).isSingleMatch()) {
            this.listModel.HklNdz(var4);
            this.searchListScroll.snapTo(0.0F);
            this.updateSearchListHeight();
            this.moduleComponent.invalidateCategoryLayout();
            return true;
         } else {
            this.blockListSetting.addEntry(this.listModel.RPCwOkb(var4));
            this.reloadBlockList();
            return true;
         }
      }
   }

   @Override
   protected boolean isEntryRowClicked(int var1, int var2, SettingComponent$1 var3) {
      if (this.handleColorPickerClick(var1, var2)) {
         return true;
      } else {
         float var4 = this.entryListScroll.ORMWO();
         ArrayList var5 = new ArrayList<>(this.blockListSetting.getEntries());

         for (int var6 = 0; var6 < var5.size(); var6++) {
            String var7 = (String)var5.get(var6);
            float var8 = this.getEntryListY(var3) - var4 + var6 * 12.0F;
            if (this.isOverRowDeleteButton(var1, var2, var8, var3.contentRightX)) {
               this.blockListSetting.removeEntry(var7);
               if (this.colorPicker.matchesTargetName(var7)) {
                  this.colorPicker.kbAd();
               }

               this.dSk4 = null;
               this.refreshAfterListChange();
               return true;
            }

            if (this.jLaw() && this.isOverColorSwatch(var1, var2, var8, var3.contentRightX)) {
               this.colorPicker.whL6(var7, this.BBqoBi().getBlockColor(var7));
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void updateLayout(int var1, int var2) {
      super.updateLayout(var1, var2);
      this.uMxaN(var1, var2);
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         this.colorPicker.clearDragMode();
      }
   }

   @Override
   protected void onMouseMove() {
      if (!this.Sbx97()) {
         this.KHzna();
      }
   }

   @Override
   protected void onSearchResultSelected(int var1, int var2) {
      this.colorPicker.kbAd();
   }

   @Override
   protected void onUnhandledClick(int var1, int var2, int var3) {
      if (!this.isOverColorPopup(var1, var2)) {
         this.colorPicker.kbAd();
      }
   }

   @Override
   protected void YrDg(String var1) {
      this.listModel.search(var1);
   }

   @Override
   protected boolean wm0779() {
      if (!this.listModel.isGroupExpanded()) {
         return false;
      } else {
         this.collapseSearchGroup();
         return true;
      }
   }

   @Override
   protected void VroyC() {
      this.listModel.searchIfEmpty(this.getSearchTextField().getText());
   }

   @Override
   protected void onEditorClosed() {
      this.listModel.fVru();
      this.dSk4 = null;
      this.colorPicker.kbAd();
   }

   private List<BlockListComponent$1> getRows() {
      if (this.dSk4 == null || this.dSk4.size() != this.blockListSetting.getEntries().size()) {
         this.dSk4 = new ArrayList<>();

         for (NkDyzD$1 var2 : NkDyzD.buildEntries(this.blockListSetting)) {
            this.dSk4.add(new BlockListComponent$1(var2));
         }
      }

      return this.dSk4;
   }

   private void reloadBlockList() {
      this.listModel.refreshSearch(this.getSearchTextField().getText());
      this.listModel.collapseGroup();
      this.searchListScroll.snapTo(0.0F);
      this.dSk4 = null;
      this.refreshAfterListChange();
   }

   private void collapseSearchGroup() {
      this.listModel.collapseGroup();
      this.searchListScroll.snapTo(0.0F);
      this.updateSearchListHeight();
      this.moduleComponent.invalidateCategoryLayout();
   }

   private ItemStack Acmt8() {
      BlockEspParser$0 var1 = this.listModel.amyrcW(System.currentTimeMillis());
      return var1 == null ? null : var1.createItemStack();
   }

   private void renderColorRow(BlockListComponent$1 var1, float var2, float var3, float var4, int var5) {
      RenderUtils.XNRNki(var2, var4, var3, var4 + 12.0F - 1.0F, var5);
      this.renderStackIcon(this.resolveDisplayStack(var1), var2 + 2.0F, var4);
      this.JxYy(var1.displayName == null ? "" : var1.displayName, var2 + 13.0F, var4, -3355444);
      this.renderColorSwatch(var3, var4, BlockListComponent$1.getColor(var1), var1.DQvas);
      this.renderRowDeleteButton(var3, var4);
   }

   private void renderColorSwatch(float var1, float var2, int var3, String var4) {
      float var5 = var1 - 6.0F - 3.0F - 3.0F - 7.0F;
      float var6 = var2 + 2.5F;
      int var7 = this.colorPicker.matchesTargetName(var4) ? -1 : -12961210;
      RenderUtils.XNRNki(var5 - 1.0F, var6 - 1.0F, var5 + 7.0F + 1.0F, var6 + 7.0F + 1.0F, var7);
      RenderUtils.XNRNki(var5, var6, var5 + 7.0F, var6 + 7.0F, 0xFF000000 | var3 & 16777215);
   }

   private boolean isOverColorSwatch(float var1, float var2, float var3, float var4) {
      float var5 = var4 - 6.0F - 3.0F - 3.0F - 7.0F;
      float var6 = var3 + 2.5F;
      return var1 >= var5 - 1.0F && var1 <= var5 + 7.0F + 1.0F && var2 >= var6 - 1.0F && var2 <= var6 + 7.0F + 1.0F;
   }

   private void KHzna() {
      if (this.jLaw() && this.colorPicker.hasTarget()) {
         ItemGridLayout$1 var1 = this.getColorPickerBounds();
         RenderUtils.XNRNki(var1.squareLeft - 3.0F, var1.top - 3.0F, var1.squareRight + 3.0F, var1.bottom + 3.0F, -300871399);
         int var2 = Color.HSBtoRGB(this.colorPicker.getHue(), 1.0F, 1.0F) | 0xFF000000;
         RenderUtils.XNRNki(var1.squareLeft, var1.top, var1.squareLeft + 54.0F, var1.bottom, var2);
         RenderUtils.drawHorizontalGradient(var1.squareLeft, var1.top, var1.squareLeft + 54.0F, var1.bottom, -1, 16777215);
         RenderUtils.drawVerticalGradient(var1.squareLeft, var1.top, var1.squareLeft + 54.0F, var1.bottom, 0, -16777216);
         RenderUtils.drawRectOutline(var1.squareLeft - 1.0F, var1.top - 1.0F, var1.squareLeft + 54.0F + 1.0F, var1.bottom + 1.0F, 1.0F, -11908522);
         float var3 = var1.squareLeft + this.colorPicker.NJgI() * 54.0F;
         float var4 = var1.top + (1.0F - this.colorPicker.getBrightness()) * 54.0F;
         RenderUtils.XNRNki(var3 - 2.0F, var4, var3 + 3.0F, var4 + 1.0F, -1);
         RenderUtils.XNRNki(var3, var4 - 2.0F, var3 + 1.0F, var4 + 3.0F, -1);

         for (int var5 = 0; var5 < 18; var5++) {
            float var6 = var1.top + var5 * 54.0F / 18.0F;
            float var7 = var1.top + (var5 + 1) * 54.0F / 18.0F;
            RenderUtils.drawVerticalGradient(
               var1.hueStripLeft,
               var6,
               var1.Jfr,
               var7,
               Color.HSBtoRGB(var5 / 18.0F, 1.0F, 1.0F) | 0xFF000000,
               Color.HSBtoRGB((var5 + 1) / 18.0F, 1.0F, 1.0F) | 0xFF000000
            );
         }

         RenderUtils.drawRectOutline(var1.hueStripLeft - 1.0F, var1.top - 1.0F, var1.Jfr + 1.0F, var1.bottom + 1.0F, 1.0F, -11908522);
         float var8 = var1.top + this.colorPicker.getHue() * 54.0F;
         RenderUtils.XNRNki(var1.hueStripLeft - 1.0F, var8 - 1.0F, var1.Jfr + 1.0F, var8 + 2.0F, -1);
      }
   }

   private boolean handleColorPickerClick(int var1, int var2) {
      if (this.jLaw() && this.colorPicker.hasTarget()) {
         ItemGridLayout$1 var3 = this.getColorPickerBounds();
         if (var3.isOverSaturationSquare(var1, var2)) {
            this.colorPicker.setDragMode(1);
            this.uMxaN(var1, var2);
            return true;
         } else if (var3.isOverHueStrip(var1, var2)) {
            this.colorPicker.setDragMode(2);
            this.uMxaN(var1, var2);
            return true;
         } else {
            return var3.isOverColorPicker(var1, var2);
         }
      } else {
         return false;
      }
   }

   private void uMxaN(int var1, int var2) {
      if (this.jLaw() && this.colorPicker.hasTarget() && this.colorPicker.getDragMode() != 0) {
         ItemGridLayout$1 var3 = this.getColorPickerBounds();
         if (this.colorPicker.getDragMode() == 1) {
            this.colorPicker.setSaturationAndBrightness(var3.getSaturationFromX(var1), var3.getBrightnessFromY(var2));
         } else {
            this.colorPicker.setHue(var3.getHueFromY(var2));
         }

         this.BBqoBi().setBlockColor(this.colorPicker.getTargetName(), this.colorPicker.getPackedColor());
         this.dSk4 = null;
         this.markConfigDirty();
      }
   }

   private boolean isOverColorPopup(int var1, int var2) {
      return this.colorPicker.hasTarget() && this.getColorPickerBounds().isOverColorPicker(var1, var2);
   }

   private ItemGridLayout$1 getColorPickerBounds() {
      SettingComponent$1 var1 = this.computeSettingBounds(true);
      float var2 = this.getEntryListY(var1) + this.getEntryListHeight() + 4.0F;
      float var3 = var1.contentTopY + 12.0F + 4.0F;
      float var4 = this.moduleComponent.categoryComponent.getPanelY() + this.moduleComponent.categoryComponent.contentHeight;
      return ItemGridLayout.quZs(var1.contentLeftX, var2, var3, var4);
   }

   private boolean jLaw() {
      return this.blockListSetting instanceof BlockColorListSetting;
   }

   private BlockColorListSetting BBqoBi() {
      return (BlockColorListSetting)this.blockListSetting;
   }
}
