// Jade recovery: original class: jade.deps.eLz.N8bD3y9O
package jade.client.common;

import jade.client.Jade;
import jade.client.gui.Component;
import jade.client.module.client.Gui;
import jade.client.setting.CategoryListSetting;
import org.lwjgl.opengl.GL11;

public class CategoryListComponent extends Component {
   private static final float ROW_HEIGHT = 12.0F;
   private static final int POPUP_COLUMN_COUNT = 2;
   private static final float POPUP_ROW_HEIGHT = 11.0F;
   private static final int Mrl = CategoryListSetting.keM.length + 1;
   private static final int POPUP_ROW_COUNT = (Mrl + 2 - 1) / 2;
   private static final float VXaXc = 2.0F;
   private static final int CLEAR_ENTRY_INDEX = CategoryListSetting.keM.length;
   private static final int ROW_BACKGROUND_COLOR = -15066588;
   private static final int ROW_HOVER_COLOR = -14013896;
   private static final int POPUP_BORDER_COLOR = -12961210;
   private static final int mPh = -13882312;
   private static final int RtkW = -12829622;
   private static final int PRIMARY_TEXT_COLOR = -1;
   private static final int SECONDARY_TEXT_COLOR = -2039584;
   private static final int KRjN = -7829368;
   private static final int CLEAR_ACTION_COLOR = -36752;
   private final CategoryListSetting categoryListSetting;
   private final ModuleComponent moduleComponent;
   private float offsetY;
   private int expandedSlot = -1;
   private int cursorX;
   private int QTn;

   public CategoryListComponent(CategoryListSetting var1, ModuleComponent var2, float var3) {
      this.categoryListSetting = var1;
      this.moduleComponent = var2;
      this.offsetY = var3;
   }

   @Override
   public void renderComponent() {
      float var1 = this.moduleComponent.categoryComponent.EWZxNc();
      float var2 = this.moduleComponent.categoryComponent.getPanelY() + this.offsetY;
      float var3 = this.moduleComponent.categoryComponent.getPanelWidth();
      IFont var4 = Gui.getSettingFont();

      for (int var5 = 0; var5 < 9; var5++) {
         float var6 = var2 + this.getSlotOffsetY(var5);
         float var7 = var6 + 12.0F - 1.0F;
         boolean var8 = this.isOverSlot(this.cursorX, this.QTn, var5);
         RenderUtils.XNRNki(var1 + 2.0F, var6, var1 + var3 - 2.0F, var7, var8 ? -14013896 : -15066588);
         float var9 = var1 + var3 * 0.45F;
         float var10 = var1 + var3 - 4.0F;
         int var11 = this.expandedSlot == var5 ? -12829622 : -13882312;
         RenderUtils.jxyoE(var9, var6 + 1.5F, var10, var7 - 0.5F, 2.0F, var11);
         GL11.glPushMatrix();
         GL11.glScaled(0.5, 0.5, 0.5);
         var4.drawString("Slot " + (var5 + 1), (var1 + 6.0F) * 2.0F, (var6 + 2.5F) * 2.0F, -1, true);
         String var12 = this.categoryListSetting.scXepg(var5);
         int var13 = this.categoryListSetting.isSlotEmpty(var5) ? -7829368 : -2039584;
         float var14 = (var9 + 4.0F) * 2.0F;
         float var15 = (var6 + 2.5F) * 2.0F;
         var4.drawString(wBbs(var4, var12, (int)((var10 - var9 - 6.0F) * 2.0F)), var14, var15, var13, true);
         GL11.glPopMatrix();
         if (this.expandedSlot == var5) {
            this.renderPopup(var1, var2, var3, var5, var4);
         }
      }
   }

   private void renderPopup(float var1, float var2, float var3, int var4, IFont var5) {
      float var6 = var2 + this.getSlotOffsetY(var4) + 12.0F + 2.0F;
      float var7 = POPUP_ROW_COUNT * 11.0F;
      RenderUtils.XNRNki(var1 + 4.0F, var6, var1 + var3 - 4.0F, var6 + var7, -15395554);
      RenderUtils.drawRectOutline(var1 + 4.0F, var6, var1 + var3 - 4.0F, var6 + var7, 0.5F, -12961210);
      float var8 = (var3 - 12.0F) / 2.0F;

      for (int var9 = 0; var9 < Mrl; var9++) {
         int var10 = var9 % 2;
         int var11 = var9 / 2;
         float var12 = var1 + 6.0F + var10 * var8;
         float var13 = var12 + var8 - 2.0F;
         float var14 = var6 + var11 * 11.0F + 0.5F;
         float var15 = var14 + 11.0F - 1.0F;
         boolean var16 = this.cursorX >= var12
            && this.cursorX <= var13
            && this.QTn >= this.moduleComponent.categoryComponent.WHmq() + (var14 - var2)
            && this.QTn <= this.moduleComponent.categoryComponent.WHmq() + (var15 - var2);
         boolean var17 = var9 != CLEAR_ENTRY_INDEX && this.categoryListSetting.getCategoriesForSlot(var4).contains(CategoryListSetting.keM[var9]);
         int var18;
         if (var9 == CLEAR_ENTRY_INDEX) {
            var18 = var16 ? -12967898 : -14411748;
         } else if (var17) {
            var18 = var16 ? -14005718 : -14796258;
         } else {
            var18 = var16 ? -13290168 : -14671828;
         }

         RenderUtils.jxyoE(var12, var14, var13, var15, 2.0F, var18);
         String var19 = var9 == CLEAR_ENTRY_INDEX ? "Clear" : CategoryListSetting.JQZs[var9];
         if (var17) {
            var19 = "✓ " + var19;
         }

         int var20 = var9 == CLEAR_ENTRY_INDEX ? -36752 : (var17 ? -8323200 : -1);
         GL11.glPushMatrix();
         GL11.glScaled(0.5, 0.5, 0.5);
         var5.drawString(var19, (var12 + 3.0F) * 2.0F, (var14 + 2.5F) * 2.0F, var20, true);
         GL11.glPopMatrix();
      }
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.cursorX = var1;
      this.QTn = var2;
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         if (this.expandedSlot >= 0) {
            int var4 = this.getPopupEntryAt(var1, var2, this.expandedSlot);
            if (var4 >= 0) {
               if (var3 == 0) {
                  if (var4 == CLEAR_ENTRY_INDEX) {
                     this.categoryListSetting.clearSlot(this.expandedSlot);
                  } else {
                     String var5 = CategoryListSetting.keM[var4];
                     if (this.categoryListSetting.getCategoriesForSlot(this.expandedSlot).contains(var5)) {
                        this.categoryListSetting.removeCategoryFromSlot(this.expandedSlot, var5);
                     } else {
                        this.categoryListSetting.addCategoryToSlot(this.expandedSlot, var5);
                     }
                  }

                  this.markConfigDirty();
               }

               return true;
            }
         }

         for (int var6 = 0; var6 < 9; var6++) {
            if (this.isOverSlot(var1, var2, var6)) {
               if (var3 == 0) {
                  this.expandedSlot = this.expandedSlot == var6 ? -1 : var6;
                  this.moduleComponent.invalidateCategoryLayout();
               } else if (var3 == 1) {
                  this.categoryListSetting.removeLastCategory(var6);
                  this.markConfigDirty();
               }

               return true;
            }
         }

         if (this.expandedSlot >= 0) {
            this.expandedSlot = -1;
            this.moduleComponent.invalidateCategoryLayout();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void resetEditingState() {
      this.expandedSlot = -1;
   }

   @Override
   public float getHeight() {
      float var1 = 108.0F;
      if (this.expandedSlot >= 0) {
         var1 += this.getPopupHeight();
      }

      return var1;
   }

   @Override
   public float getWidth() {
      return this.offsetY;
   }

   @Override
   public void setY(float var1) {
      this.offsetY = var1;
   }

   @Override
   public boolean ejbAn() {
      return this.categoryListSetting.visible;
   }

   private float getPopupHeight() {
      return POPUP_ROW_COUNT * 11.0F + 4.0F;
   }

   private float getSlotOffsetY(int var1) {
      float var2 = var1 * 12.0F;
      if (this.expandedSlot >= 0 && var1 > this.expandedSlot) {
         var2 += this.getPopupHeight();
      }

      return var2;
   }

   private boolean isOverSlot(int var1, int var2, int var3) {
      float var4 = this.moduleComponent.categoryComponent.EWZxNc();
      float var5 = this.moduleComponent.categoryComponent.getPanelWidth();
      float var6 = this.moduleComponent.categoryComponent.WHmq() + this.offsetY + this.getSlotOffsetY(var3);
      return var1 >= var4 + 2.0F && var1 <= var4 + var5 - 2.0F && var2 >= var6 && var2 <= var6 + 12.0F - 1.0F;
   }

   private int getPopupEntryAt(int var1, int var2, int var3) {
      float var4 = this.moduleComponent.categoryComponent.EWZxNc();
      float var5 = this.moduleComponent.categoryComponent.getPanelWidth();
      float var6 = this.moduleComponent.categoryComponent.WHmq() + this.offsetY + this.getSlotOffsetY(var3) + 12.0F + 2.0F;
      float var7 = (var5 - 12.0F) / 2.0F;

      for (int var8 = 0; var8 < Mrl; var8++) {
         int var9 = var8 % 2;
         int var10 = var8 / 2;
         float var11 = var4 + 6.0F + var9 * var7;
         float var12 = var11 + var7 - 2.0F;
         float var13 = var6 + var10 * 11.0F + 0.5F;
         float var14 = var13 + 11.0F - 1.0F;
         if (var1 >= var11 && var1 <= var12 && var2 >= var13 && var2 <= var14) {
            return var8;
         }
      }

      return -1;
   }

   private static String wBbs(IFont var0, String var1, int var2) {
      if (var1 != null && !var1.isEmpty()) {
         if (var0.getStringWidth(var1) <= var2) {
            return var1;
         } else {
            String var3 = "...";
            int var4 = var0.getStringWidth(var3);
            StringBuilder var5 = new StringBuilder();

            for (int var6 = 0; var6 < var1.length(); var6++) {
               var5.append(var1.charAt(var6));
               if (var0.getStringWidth(var5.toString()) + var4 > var2) {
                  var5.deleteCharAt(var5.length() - 1);
                  var5.append(var3);
                  return var5.toString();
               }
            }

            return var1;
         }
      } else {
         return "";
      }
   }

   private void markConfigDirty() {
      if (Jade.Grq != null && !this.moduleComponent.module.skipSettingsPersistence) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }
}
