// Jade recovery: original class: jade.deps.eLz.ONNArv0
package jade.client.setting;

import jade.client.common.ItemMatcher$0;
import jade.client.common.ItemMatcher$1;
import jade.client.common.ModuleComponent;
import java.util.List;
import net.minecraft.item.ItemStack;

public abstract class ItemListSearchComponent<T extends ItemListSetting> extends ItemListComponent {
   protected final T pxP;
   private final ItemSearchController searchController;

   protected ItemListSearchComponent(T var1, ModuleComponent var2, float var3) {
      super(var2, var3, "Search items...");
      this.pxP = (T)var1;
      this.searchController = new ItemSearchController(var1);
   }

   @Override
   protected String BNndC() {
      return this.searchController.getDisplayTitle();
   }

   @Override
   public boolean ejbAn() {
      return ItemListSettingHelper.isVisible(this.pxP);
   }

   @Override
   public String getGroupName() {
      return ItemListSettingHelper.BxyBj0(this.pxP);
   }

   @Override
   protected int getSearchResultCount() {
      return this.searchController.getRowCount();
   }

   @Override
   protected void XMImty(SettingComponent$1 var1, float var2, int var3, int var4, int var5) {
      if (this.searchController.kaagKg()) {
         String var11 = this.searchController.getOpenedMatcherLabel();

         for (int var12 = var3; var12 < var4; var12++) {
            float var13 = var1.listTopY - var2 + var12 * 12.0F;
            int var14 = ScrollListRowUtils.getRowColor(var12, var5);
            if (var12 == 0) {
               this.ewwE(var1.contentLeftX, var1.contentRightX, var13, var14, var11);
            } else if (var12 == 1) {
               this.Rm47(var11 + " (All)", this.getRotatingIconStack(), var1.contentLeftX, var1.contentRightX, var13, var14, false);
            } else {
               ItemMatcher$1 var15 = this.searchController.ywCw().get(var12 - 2);
               this.Rm47(var15.Qwi, var15.copyItemStack(), var1.contentLeftX, var1.contentRightX, var13, var14, false);
            }
         }
      } else {
         for (int var6 = var3; var6 < var4; var6++) {
            ItemMatcher$0 var7 = this.searchController.NUZQ(var6);
            float var8 = var1.listTopY - var2 + var6 * 12.0F;
            int var9 = ScrollListRowUtils.getRowColor(var6, var5);
            if (var7.isSingleItem()) {
               ItemMatcher$1 var10 = var7.variants.get(0);
               this.Rm47(var10.Qwi, var10.copyItemStack(), var1.contentLeftX, var1.contentRightX, var8, var9, false);
            } else {
               this.Rm47(var7.getDisplayNameWithCount(), var7.NQcs(), var1.contentLeftX, var1.contentRightX, var8, var9, false);
            }
         }
      }
   }

   @Override
   protected boolean isSearchResultClicked(int var1, int var2, SettingComponent$1 var3) {
      if (!this.SIpb7()) {
         return false;
      } else {
         int var4 = this.getSearchResultCount();
         if (var4 == 0) {
            return false;
         } else {
            int var5 = ScrollListRowUtils.getRowIndexAt(var1, var2, var3.contentLeftX, var3.contentRightX, var3.listTopY, this.searchListScroll.ORMWO(), 12.0F, var4);
            if (var5 < 0) {
               return false;
            } else if (this.searchController.kaagKg()) {
               if (var5 == 0) {
                  this.taD7();
                  return true;
               } else if (var5 == 1) {
                  this.pxP.addItem(this.searchController.getEntryValueAt(var5));
                  this.applySelectedEntry();
                  return true;
               } else {
                  int var7 = var5 - 2;
                  if (var7 >= 0 && var7 < this.searchController.ywCw().size()) {
                     this.pxP.addItem(this.searchController.getEntryValueAt(var5));
                     this.applySelectedEntry();
                     return true;
                  } else {
                     return true;
                  }
               }
            } else {
               ItemMatcher$0 var6 = this.searchController.NUZQ(var5);
               if (var6.isSingleItem()) {
                  this.pxP.addItem(var6.variants.get(0).hvDbs);
                  this.applySelectedEntry();
                  return true;
               } else {
                  this.searchController.openMatcher(var5);
                  this.searchListScroll.snapTo(0.0F);
                  this.updateSearchListHeight();
                  this.moduleComponent.invalidateCategoryLayout();
                  return true;
               }
            }
         }
      }
   }

   @Override
   protected void YrDg(String var1) {
      this.searchController.resetAndSearch(var1);
   }

   @Override
   protected boolean wm0779() {
      if (this.searchController.kaagKg()) {
         this.taD7();
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void VroyC() {
      this.searchController.searchIfEmpty(this.getSearchTextField().getText());
   }

   @Override
   protected void onEditorClosed() {
      this.searchController.clearSearch();
      this.resetEditorState();
   }

   protected final void DgzS(String var1) {
      ItemListSettingHelper.addEntry(this.pxP, var1);
      this.invalidateEntryCache();
      this.refreshAfterListChange();
   }

   protected final void pKv74() {
      this.invalidateEntryCache();
   }

   protected final T getItemListSetting() {
      return this.pxP;
   }

   protected final ItemMatcher$0 getSearchResult(int var1) {
      return this.searchController.NUZQ(var1);
   }

   protected final List<ItemMatcher$1> getMatcherEntries() {
      return this.searchController.ywCw();
   }

   private void taD7() {
      this.searchController.closeMatcher();
      this.searchListScroll.snapTo(0.0F);
      this.updateSearchListHeight();
      this.moduleComponent.invalidateCategoryLayout();
   }

   private void applySelectedEntry() {
      this.searchController.search(this.getSearchTextField().getText());
      this.searchController.closeMatcher();
      this.searchListScroll.snapTo(0.0F);
      this.invalidateEntryCache();
      this.refreshAfterListChange();
   }

   private ItemStack getRotatingIconStack() {
      ItemMatcher$1 var1 = this.searchController.getCycledSubEntry(System.currentTimeMillis());
      return var1 == null ? null : var1.copyItemStack();
   }

   protected void resetEditorState() {
   }

   protected abstract void invalidateEntryCache();
}
