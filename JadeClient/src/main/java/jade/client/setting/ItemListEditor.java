// Jade recovery: original class: jade.deps.eLz.NfvWza
package jade.client.setting;

import jade.client.common.ItemMatcher$1;
import jade.client.common.ItemMatcher;
import jade.client.common.ModuleComponent;
import jade.client.common.RenderUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ItemListEditor extends ItemListSearchComponent<ItemListSetting> {
   private static final float ptK = 7.0F;
   private static final float COLOR_SWATCH_MARGIN = 3.0F;
   private static final float Yif = 54.0F;
   private static final float PICKER_POPUP_HEIGHT = 8.0F;
   private static final float PICKER_POPUP_MARGIN = 4.0F;
   private List<ItemListComponent$0> cachedEntries;
   private String wrUtlW;
   private boolean jOqug;
   private int ziwjm;
   private float pickerHue;
   private float pickerSaturation;
   private float pickerBrightness;

   public ItemListEditor(ItemListSetting var1, ModuleComponent var2, float var3) {
      super(var1, var2, var3);
   }

   @Override
   protected int getEntryCount() {
      return this.pxP.getItems().size();
   }

   @Override
   protected void renderEntryRowRange(SettingComponent$1 var1, float var2, int var3, int var4) {
      List var5 = this.pxP.getItems();
      if (this.cachedEntries == null || !this.Oets(var5)) {
         this.cachedEntries = new ArrayList<>();

         for (String var7 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var5)) {
            List var8 = ItemMatcher.isValidToken(var7) ? ItemMatcher.getEntries(var7) : null;
            ArrayList var9 = null;
            if (var8 != null && !var8.isEmpty()) {
               var9 = new ArrayList();

               for (ItemMatcher$1 var11 : (java.lang.Iterable<ItemMatcher$1>) (java.lang.Iterable<?>) (var8)) {
                  var9.add(var11.copyItemStack());
               }
            }

            this.cachedEntries.add(new ItemListComponent$0(var7, ItemMatcher.getDisplayName(var7), ItemMatcher.getPreviewStack(var7), var9));
            if (this.pxP instanceof ItemColorListSetting) {
               ItemColorListSetting var16 = (ItemColorListSetting)this.pxP;
               this.cachedEntries.set(this.cachedEntries.size() - 1, new ItemListEditor$1(var7, ItemMatcher.getDisplayName(var7), ItemMatcher.getPreviewStack(var7), var9, var16.getPrimaryColor(var7), var16.jaqpR(var7)));
            }
         }
      }

      for (int var12 = var3; var12 < var4; var12++) {
         ItemListComponent$0 var13 = this.cachedEntries.get(var12);
         float var14 = this.getEntryListY(var1) - var2 + var12 * 12.0F;
         int var15 = var12 % 2 == 0 ? -15066582 : -14803410;
         if (var13 instanceof ItemListEditor$1) {
            this.drawColorRow((ItemListEditor$1)var13, var1.contentLeftX, var1.contentRightX, var14, var15);
         } else {
            this.Rm47(var13.displayName, this.resolveDisplayStack(var13), var1.contentLeftX, var1.contentRightX, var14, var15, true);
         }
      }
   }

   private boolean Oets(List<String> var1) {
      if (this.cachedEntries.size() != var1.size()) {
         return false;
      } else {
         for (int var2 = 0; var2 < var1.size(); var2++) {
            if (!((String)var1.get(var2)).equals(this.cachedEntries.get(var2).DQvas)) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   protected boolean isEntryRowClicked(int var1, int var2, SettingComponent$1 var3) {
      if (this.handlePickerMouseDown(var1, var2, var3)) {
         return true;
      } else {
         float var4 = this.entryListScroll.ORMWO();
         ArrayList var5 = new ArrayList<>(this.pxP.getItems());

         for (int var6 = 0; var6 < var5.size(); var6++) {
            float var7 = this.getEntryListY(var3) - var4 + var6 * 12.0F;
            if (this.isOverRowDeleteButton(var1, var2, var7, var3.contentRightX)) {
               this.DgzS((String)var5.get(var6));
               if (((String)var5.get(var6)).equals(this.wrUtlW)) {
                  this.wrUtlW = null;
               }

               return true;
            }

            if (this.pxP instanceof ItemColorListSetting && this.Vh180(var1, var2, var7, var3.contentRightX, false)) {
               this.openColorPicker((String)var5.get(var6), false);
               return true;
            }

            if (this.pxP instanceof ItemColorListSetting && this.Vh180(var1, var2, var7, var3.contentRightX, true)) {
               this.openColorPicker((String)var5.get(var6), true);
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void updateLayout(int var1, int var2) {
      super.updateLayout(var1, var2);
      this.updateColorFromPicker(var1, var2);
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         this.ziwjm = 0;
      }
   }

   @Override
   protected void onMouseMove() {
      if (!this.Sbx97()) {
         this.EMQH();
      }
   }

   @Override
   protected void onSearchResultSelected(int var1, int var2) {
      this.wrUtlW = null;
   }

   @Override
   protected void onUnhandledClick(int var1, int var2, int var3) {
      if (!this.isInsidePickerPopup(var1, var2)) {
         this.wrUtlW = null;
      }
   }

   @Override
   protected void invalidateEntryCache() {
      this.cachedEntries = null;
   }

   private void drawColorRow(ItemListEditor$1 var1, float var2, float var3, float var4, int var5) {
      RenderUtils.XNRNki(var2, var4, var3, var4 + 12.0F - 1.0F, var5);
      this.renderStackIcon(this.resolveDisplayStack(var1), var2 + 2.0F, var4);
      this.JxYy(var1.displayName != null ? var1.displayName : "", var2 + 13.0F, var4, -3355444);
      this.drawColorSwatch(var3, var4, ItemListEditor$1.getPrimaryColor(var1), false, var1.DQvas);
      this.drawColorSwatch(var3, var4, ItemListEditor$1.getSecondaryColor(var1), true, var1.DQvas);
      this.renderRowDeleteButton(var3, var4);
   }

   private void drawColorSwatch(float var1, float var2, int var3, boolean var4, String var5) {
      float var6 = var1 - 6.0F - 3.0F;
      float var7 = var6 - 3.0F - 7.0F;
      if (!var4) {
         var7 -= 10.0F;
      }

      float var8 = var2 + 2.5F;
      int var9 = var5 != null && var5.equals(this.wrUtlW) && this.jOqug == var4 ? -1 : -12961210;
      RenderUtils.XNRNki(var7 - 1.0F, var8 - 1.0F, var7 + 7.0F + 1.0F, var8 + 7.0F + 1.0F, var9);
      RenderUtils.XNRNki(var7, var8, var7 + 7.0F, var8 + 7.0F, 0xFF000000 | var3 & 16777215);
   }

   private boolean Vh180(int var1, int var2, float var3, float var4, boolean var5) {
      float var6 = var4 - 6.0F - 3.0F;
      float var7 = var6 - 3.0F - 7.0F;
      if (!var5) {
         var7 -= 10.0F;
      }

      float var8 = var3 + 2.5F;
      return var1 >= var7 - 1.0F && var1 <= var7 + 7.0F + 1.0F && var2 >= var8 - 1.0F && var2 <= var8 + 7.0F + 1.0F;
   }

   private void openColorPicker(String var1, boolean var2) {
      this.wrUtlW = var1;
      this.jOqug = var2;
      this.ziwjm = 0;
      int var3 = this.getPickerColor();
      float[] var4 = Color.RGBtoHSB(var3 >> 16 & 0xFF, var3 >> 8 & 0xFF, var3 & 0xFF, null);
      this.pickerHue = var4[0];
      this.pickerSaturation = var4[1];
      this.pickerBrightness = var4[2];
   }

   private void EMQH() {
      if (this.pxP instanceof ItemColorListSetting && this.wrUtlW != null) {
         ItemListEditor$2 var1 = this.computePickerBounds();
         RenderUtils.XNRNki(ItemListEditor$2.getPopupLeft(var1) - 3.0F, ItemListEditor$2.okJbagD(var1) - 3.0F, ItemListEditor$2.getPopupRight(var1) + 3.0F, ItemListEditor$2.RlH9(var1) + 3.0F, -300871399);
         int var2 = Color.HSBtoRGB(this.pickerHue, 1.0F, 1.0F) | 0xFF000000;
         RenderUtils.XNRNki(ItemListEditor$2.getPopupLeft(var1), ItemListEditor$2.okJbagD(var1), ItemListEditor$2.getPopupLeft(var1) + 54.0F, ItemListEditor$2.okJbagD(var1) + 54.0F, var2);
         RenderUtils.drawHorizontalGradient(ItemListEditor$2.getPopupLeft(var1), ItemListEditor$2.okJbagD(var1), ItemListEditor$2.getPopupLeft(var1) + 54.0F, ItemListEditor$2.okJbagD(var1) + 54.0F, -1, 16777215);
         RenderUtils.drawVerticalGradient(ItemListEditor$2.getPopupLeft(var1), ItemListEditor$2.okJbagD(var1), ItemListEditor$2.getPopupLeft(var1) + 54.0F, ItemListEditor$2.okJbagD(var1) + 54.0F, 0, -16777216);
         RenderUtils.drawRectOutline(
            ItemListEditor$2.getPopupLeft(var1) - 1.0F,
            ItemListEditor$2.okJbagD(var1) - 1.0F,
            ItemListEditor$2.getPopupLeft(var1) + 54.0F + 1.0F,
            ItemListEditor$2.okJbagD(var1) + 54.0F + 1.0F,
            1.0F,
            -11908522
         );
         float var3 = ItemListEditor$2.getPopupLeft(var1) + this.pickerSaturation * 54.0F;
         float var4 = ItemListEditor$2.okJbagD(var1) + (1.0F - this.pickerBrightness) * 54.0F;
         RenderUtils.XNRNki(var3 - 2.0F, var4, var3 + 3.0F, var4 + 1.0F, -1);
         RenderUtils.XNRNki(var3, var4 - 2.0F, var3 + 1.0F, var4 + 3.0F, -1);

         for (int var5 = 0; var5 < 18; var5++) {
            float var6 = ItemListEditor$2.okJbagD(var1) + var5 * 54.0F / 18.0F;
            float var7 = ItemListEditor$2.okJbagD(var1) + (var5 + 1) * 54.0F / 18.0F;
            int var8 = Color.HSBtoRGB(var5 / 18.0F, 1.0F, 1.0F) | 0xFF000000;
            int var9 = Color.HSBtoRGB((var5 + 1) / 18.0F, 1.0F, 1.0F) | 0xFF000000;
            RenderUtils.drawVerticalGradient(ItemListEditor$2.getHueBarLeft(var1), var6, ItemListEditor$2.getHueBarRight(var1), var7, var8, var9);
         }

         RenderUtils.drawRectOutline(ItemListEditor$2.getHueBarLeft(var1) - 1.0F, ItemListEditor$2.okJbagD(var1) - 1.0F, ItemListEditor$2.getHueBarRight(var1) + 1.0F, ItemListEditor$2.RlH9(var1) + 1.0F, 1.0F, -11908522);
         float var10 = ItemListEditor$2.okJbagD(var1) + this.pickerHue * 54.0F;
         RenderUtils.XNRNki(ItemListEditor$2.getHueBarLeft(var1) - 1.0F, var10 - 1.0F, ItemListEditor$2.getHueBarRight(var1) + 1.0F, var10 + 2.0F, -1);
      }
   }

   private boolean handlePickerMouseDown(int var1, int var2, SettingComponent$1 var3) {
      if (this.pxP instanceof ItemColorListSetting && this.wrUtlW != null) {
         ItemListEditor$2 var4 = this.computePickerBounds();
         if (var1 >= ItemListEditor$2.getPopupLeft(var4) && var1 <= ItemListEditor$2.getPopupLeft(var4) + 54.0F && var2 >= ItemListEditor$2.okJbagD(var4) && var2 <= ItemListEditor$2.RlH9(var4)) {
            this.ziwjm = 1;
            this.updateColorFromPicker(var1, var2);
            return true;
         } else if (var1 >= ItemListEditor$2.getHueBarLeft(var4) && var1 <= ItemListEditor$2.getHueBarRight(var4) && var2 >= ItemListEditor$2.okJbagD(var4) && var2 <= ItemListEditor$2.RlH9(var4)) {
            this.ziwjm = 2;
            this.updateColorFromPicker(var1, var2);
            return true;
         } else {
            return this.isInsidePickerPopup(var1, var2);
         }
      } else {
         return false;
      }
   }

   private void updateColorFromPicker(int var1, int var2) {
      if (this.pxP instanceof ItemColorListSetting && this.wrUtlW != null && this.ziwjm != 0) {
         ItemListEditor$2 var3 = this.computePickerBounds();
         if (this.ziwjm == 1) {
            this.pickerSaturation = znko6((var1 - ItemListEditor$2.getPopupLeft(var3)) / 54.0F);
            this.pickerBrightness = znko6(1.0F - (var2 - ItemListEditor$2.okJbagD(var3)) / 54.0F);
         } else if (this.ziwjm == 2) {
            this.pickerHue = znko6((var2 - ItemListEditor$2.okJbagD(var3)) / 54.0F);
         }

         int var4 = 0xFF000000 | Color.HSBtoRGB(this.pickerHue, this.pickerSaturation, this.pickerBrightness) & 16777215;
         ItemColorListSetting var5 = (ItemColorListSetting)this.pxP;
         if (this.jOqug) {
            var5.setSecondaryColor(this.wrUtlW, var4);
         } else {
            var5.setPrimaryColor(this.wrUtlW, var4);
         }

         this.invalidateEntryCache();
         this.markConfigDirty();
      }
   }

   private int getPickerColor() {
      ItemColorListSetting var1 = (ItemColorListSetting)this.pxP;
      return this.jOqug ? var1.jaqpR(this.wrUtlW) : var1.getPrimaryColor(this.wrUtlW);
   }

   private boolean isInsidePickerPopup(int var1, int var2) {
      if (this.wrUtlW == null) {
         return false;
      } else {
         ItemListEditor$2 var3 = this.computePickerBounds();
         return var1 >= ItemListEditor$2.getPopupLeft(var3) - 3.0F
            && var1 <= ItemListEditor$2.getPopupRight(var3) + 3.0F
            && var2 >= ItemListEditor$2.okJbagD(var3) - 3.0F
            && var2 <= ItemListEditor$2.RlH9(var3) + 3.0F;
      }
   }

   private ItemListEditor$2 computePickerBounds() {
      SettingComponent$1 var1 = this.computeSettingBounds(true);
      float var2 = var1.contentLeftX;
      float var3 = this.getEntryListY(var1) + this.getEntryListHeight() + 4.0F;
      if (var3 + 54.0F > this.moduleComponent.categoryComponent.getPanelY() + this.moduleComponent.categoryComponent.contentHeight - 4.0F) {
         var3 = var1.contentTopY + 12.0F + 4.0F;
      }

      float var4 = var2 + 54.0F + 5.0F;
      return new ItemListEditor$2(var2, var3, var4, var4 + 8.0F);
   }

   private static float znko6(float var0) {
      return Math.max(0.0F, Math.min(1.0F, var0));
   }
}
