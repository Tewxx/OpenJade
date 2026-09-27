// Jade recovery: original class: jade.deps.eLz.BRdxzQbdB
package jade.client.common;

import jade.client.setting.ItemListComponent;
import jade.client.setting.SettingComponent$1;
import jade.client.setting.NameListSetting;
import java.util.List;

public class PotionListComponent extends ItemListComponent {
   private final NameListSetting potionSetting;
   private final SettingEntryListModel potionEntryModel;

   public PotionListComponent(NameListSetting var1, ModuleComponent var2, float var3) {
      super(var2, var3, "Search potion effects...");
      this.potionSetting = var1;
      this.potionEntryModel = new SettingEntryListModel(var1);
   }

   @Override
   protected String BNndC() {
      return this.potionEntryModel.OZWK();
   }

   @Override
   protected int getEntryCount() {
      return this.potionEntryModel.HdhDw();
   }

   @Override
   protected int getSearchResultCount() {
      return this.potionEntryModel.getFilteredCount();
   }

   @Override
   public boolean ejbAn() {
      return this.potionSetting.visible;
   }

   @Override
   public String getGroupName() {
      return this.potionSetting.groupSetting != null ? this.potionSetting.groupSetting.getName() : "";
   }

   @Override
   protected void XMImty(SettingComponent$1 var1, float var2, int var3, int var4, int var5) {
      for (int var6 = var3; var6 < var4; var6++) {
         PotionListRow var7 = ListRowFactory.createSearchResultRow(this.potionEntryModel, var6, var5, var1.listTopY, var2, 12.0F);
         this.renderPotionRow(var7, var1);
      }
   }

   @Override
   protected void renderEntryRowRange(SettingComponent$1 var1, float var2, int var3, int var4) {
      for (int var5 = var3; var5 < var4; var5++) {
         this.renderPotionRow(ListRowFactory.createListRow(this.potionEntryModel, var5, this.getEntryListY(var1), var2, 12.0F), var1);
      }
   }

   @Override
   protected boolean isSearchResultClicked(int var1, int var2, SettingComponent$1 var3) {
      float var4 = this.searchListScroll.ORMWO();
      int var5 = this.SIpb7() ? ListRowFactory.findRowIndexAt(var1, var2, var3.contentLeftX, var3.contentRightX, var3.listTopY, var4, 12.0F, this.potionEntryModel.getFilteredCount()) : -1;
      if (var5 < 0) {
         return false;
      } else {
         this.potionEntryModel.VqhFt(var5);
         this.resetSearch();
         return true;
      }
   }

   @Override
   protected boolean isEntryRowClicked(int var1, int var2, SettingComponent$1 var3) {
      float var4 = this.entryListScroll.ORMWO();
      List var5 = this.potionEntryModel.getAllEntryNames();

      for (int var6 = 0; var6 < var5.size(); var6++) {
         float var7 = this.getEntryListY(var3) - var4 + var6 * 12.0F;
         if (this.isOverRowDeleteButton(var1, var2, var7, var3.contentRightX)) {
            this.potionEntryModel.ZXMTz((String)var5.get(var6));
            this.refreshAfterListChange();
            return true;
         }
      }

      return false;
   }

   @Override
   protected void YrDg(String var1) {
      this.potionEntryModel.applySearchFilter(var1);
   }

   @Override
   protected boolean wm0779() {
      return false;
   }

   @Override
   protected void VroyC() {
      this.potionEntryModel.applySearchFilter(this.getSearchTextField().getText());
   }

   @Override
   protected void onEditorClosed() {
      this.potionEntryModel.clearSearchFilter();
   }

   private void resetSearch() {
      this.getSearchTextField().setText("");
      this.setTextInputFocused(false);
      this.searchListScroll.snapTo(0.0F);
      this.refreshAfterListChange();
   }

   private void renderPotionRow(PotionListRow var1, SettingComponent$1 var2) {
      this.Rm47(var1.label, var1.itemStack, var2.contentLeftX, var2.contentRightX, var1.rowY, var1.backgroundColor, var1.isStoredEntry);
   }
}
