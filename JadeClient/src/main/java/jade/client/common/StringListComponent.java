// Jade recovery: original class: jade.deps.eLz.JupMnb
package jade.client.common;

import jade.client.module.render.arraylist.ColorTheme;
import jade.client.setting.SettingComponent$1;
import jade.client.setting.SettingComponent;
import jade.client.setting.StringListSetting;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public class StringListComponent extends SettingComponent {
   private static final float ENTRY_LIST_TOP_OFFSET = 4.0F;
   private static final int MAX_VISIBLE_ENTRY_ROWS = 7;
   private static final float TEXT_SCALE = 0.56F;
   private static final float ENTRY_ROW_HEIGHT = 11.0F;
   private static final int taI = 6;
   private static final float EMLQj = 3.0F;
   private static final String CLOSE_ICON_TEXTURE = "/assets/jade/textures/gui/close.png";
   private final StringListSetting stringListSetting;
   private final ListScrollController scrollController = new ListScrollController(7, 12.0F);

   public StringListComponent(StringListSetting var1, ModuleComponent var2, float var3) {
      super(var2, var3, var1.getInputHint(), var1.getMaxEntryLength());
      this.stringListSetting = var1;
   }

   @Override
   public void renderComponent() {
      SettingComponent$1 var1 = this.computeSettingBounds(false);
      this.drawLabelText(var1, this.stringListSetting.getName());
      this.drawTextInputBackground(var1);
      this.renderEntryRows(var1);
   }

   @Override
   public void updateLayout(int var1, int var2) {
      super.updateLayout(var1, var2);
      this.scrollController.BtQv(var1, var2);
      this.scrollController.updateScrollRange(this.stringListSetting.getEntries().size());
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (!this.isExpandedAndVisible()) {
         return false;
      } else {
         SettingComponent$1 var4 = this.computeSettingBounds(true);
         if (var3 == 0) {
            if (this.isMouseOverTextInput(var1, var2, var4)) {
               this.setTextInputFocused(true);
               return true;
            }

            if (this.handleRowDeleteClick(var1, var2, var4)) {
               return true;
            }
         }

         this.cancelEditing();
         return false;
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe && this.isTextInputFocused()) {
         TextInputAction var3 = TextInputAction.from(var2, 1, 28, 156);
         if (var3 == TextInputAction.CANCEL) {
            this.cancelEditing();
         } else if (var3 == TextInputAction.SUBMIT) {
            this.submitEntry();
            this.setTextInputFocused(false);
         } else {
            this.getSearchTextField().keyTyped(var1, var2);
         }
      }
   }

   @Override
   public void keyPressed(int var1) {
      if (this.isExpandedAndVisible()) {
         this.scrollController.onScroll(var1, this.stringListSetting.getEntries().size(), this.isScrollListHovered(this.scrollController.DUpzmA(), this.scrollController.getDragStartY()));
      }
   }

   @Override
   public void resetEditingState() {
      super.resetEditingState();
      this.getSearchTextField().setText("");
      this.scrollController.resetScroll();
   }

   @Override
   public float getHeight() {
      int var1 = this.stringListSetting.getEntries().size();
      float var2 = var1 == 0 ? 0.0F : 4.0F + LayoutMath.clampScaled(var1, 7, 12.0F);
      return 24.0F + var2;
   }

   @Override
   public boolean ejbAn() {
      return this.stringListSetting.visible;
   }

   @Override
   public String getGroupName() {
      return this.stringListSetting.groupSetting == null ? "" : this.stringListSetting.groupSetting.getName();
   }

   public boolean isScrollListHovered(float var1, float var2) {
      return this.stringListSetting.getEntries().size() > 7 && this.isOverEntryList(var1, var2);
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      SettingComponent$1 var3 = this.computeSettingBounds(true);
      return this.isMouseOverTextInput(var1, var2, var3) || this.isOverEntryList(var1, var2);
   }

   private boolean isExpandedAndVisible() {
      return this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this);
   }

   private void cancelEditing() {
      if (this.isTextInputFocused()) {
         this.getSearchTextField().setText("");
         this.setTextInputFocused(false);
      }
   }

   private void submitEntry() {
      String var1 = this.getSearchTextField().getText();
      if (var1 != null && !var1.trim().isEmpty()) {
         if (this.stringListSetting.addEntry(var1)) {
            this.getSearchTextField().setText("");
         }

         this.moduleComponent.invalidateCategoryLayout();
         this.scrollController.updateScrollRange(this.stringListSetting.getEntries().size());
      }
   }

   private void renderEntryRows(SettingComponent$1 var1) {
      List var2 = this.stringListSetting.getEntries();
      if (!var2.isEmpty()) {
         float var3 = this.getEntryListY(var1);
         float var4 = LayoutMath.clampScaled(var2.size(), 7, 12.0F);
         float var5 = this.moduleComponent.categoryComponent.WHmq() - var1.boundsY;
         RenderUtils.pushScissorRect(var1.contentLeftX, var3 + var5, var1.contentRightX - var1.contentLeftX, var4);
         float var6 = this.scrollController.lGva();
         int var7 = LayoutMath.floorDivideToInt(var6, 12.0F);
         int var8 = LayoutMath.clampEndIndex(var7, var2.size(), 7);

         for (int var9 = var7; var9 < var8; var9++) {
            this.renderEntryRow((String)var2.get(var9), var1.contentLeftX, var1.contentRightX, LayoutMath.KDlh(var3, var6, var9, 12.0F), (var9 & 1) == 0);
         }

         RenderUtils.restoreScissorState();
      }
   }

   private void renderEntryRow(String var1, float var2, float var3, float var4, boolean var5) {
      RenderUtils.XNRNki(var2, var4, var3, var4 + 11.0F, var5 ? -15066582 : -14803410);
      drawScaledSettingText(var1, var2 + 4.0F, computeCenteredTextYAtScale(var4, 11.0F, 0.56F) + 0.56F, -3355444, 0.56F);
      ResourceLocation var6 = RenderUtils.getIconTexture("/assets/jade/textures/gui/close.png");
      if (var6 != null) {
         float var7 = var3 - 6.0F - 3.0F;
         float var8 = var4 + 2.5F;
         int var9 = ColorTheme.getGradient(ColorTheme.hiddenBind[0], ColorTheme.hiddenBind[1], 0.0);
         RenderUtils.drawIconTexture(var6, var7, var8, 6, var9);
      }
   }

   private boolean handleRowDeleteClick(float var1, float var2, SettingComponent$1 var3) {
      List var4 = this.stringListSetting.getEntries();
      float var5 = this.getEntryListY(var3);
      float var6 = this.scrollController.lGva();

      for (int var7 = 0; var7 < var4.size(); var7++) {
         float var8 = LayoutMath.KDlh(var5, var6, var7, 12.0F);
         float var9 = var3.contentRightX - 6.0F - 3.0F;
         float var10 = var8 + 2.5F;
         if (LayoutMath.isInsideBoundsInclusive(var1, var2, var9, var9 + 6.0F, var10, 6.0F)) {
            this.stringListSetting.removeEntry((String)var4.get(var7));
            this.moduleComponent.invalidateCategoryLayout();
            this.scrollController.updateScrollRange(this.stringListSetting.getEntries().size());
            return true;
         }
      }

      return false;
   }

   private boolean isOverEntryList(float var1, float var2) {
      int var3 = this.stringListSetting.getEntries().size();
      if (var3 == 0) {
         return false;
      } else {
         SettingComponent$1 var4 = this.computeSettingBounds(true);
         return LayoutMath.isInsideBoundsExclusive(var1, var2, var4.contentLeftX, var4.contentRightX, this.getEntryListY(var4), LayoutMath.clampScaled(var3, 7, 12.0F));
      }
   }

   private float getEntryListY(SettingComponent$1 var1) {
      return var1.listTopY + 4.0F;
   }
}
