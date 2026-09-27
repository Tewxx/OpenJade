// Jade recovery: original class: jade.deps.eLz.acSzVn6F
package jade.client.setting;

import jade.client.common.ModuleComponent;
import jade.client.common.SettingRowLayout;
import jade.client.gui.Component;
import jade.client.gui.TextField;
import jade.client.gui.FocusableTextInput;

public abstract class SettingComponent extends Component implements FocusableTextInput {
   protected static final int MAX_TEXT_LENGTH = 128;
   protected static final float TEXT_SCALE = 0.5F;
   protected static final float ROW_HEIGHT = 12.0F;
   public float groupIndent;
   public float positionY;
   protected final ModuleComponent moduleComponent;
   private final TextFieldBox searchField;

   protected SettingComponent(ModuleComponent var1, float var2, String var3, int var4) {
      this.moduleComponent = var1;
      this.positionY = var2;
      this.searchField = new TextFieldBox(var3, var4, 0.5F);
   }

   public abstract String getGroupName();

   public void setGroupIndent(float var1) {
      this.groupIndent = var1;
   }

   @Override
   public void resetEditingState() {
      this.searchField.setFocused(false);
   }

   @Override
   public void unfocus() {
      this.searchField.setFocused(false);
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      SettingComponent$1 var3 = this.computeSettingBounds(true);
      return this.isMouseOverTextInput(var1, var2, var3);
   }

   @Override
   public boolean isFocused() {
      return this.searchField.isFocused();
   }

   @Override
   public float getHeight() {
      return 24.0F;
   }

   @Override
   public float getWidth() {
      return this.positionY;
   }

   @Override
   public void setY(float var1) {
      this.positionY = var1;
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.searchField.updateCursorBlink();
   }

   protected final boolean isTextInputFocused() {
      return this.searchField.isFocused();
   }

   protected final void setTextInputFocused(boolean var1) {
      this.searchField.setFocused(var1);
   }

   protected final float computeTextInputBottom(SettingComponent$1 var1) {
      return var1.contentTopY + 12.0F - 1.0F;
   }

   protected final float computeTextInputTop(SettingComponent$1 var1) {
      return var1.contentTopY + 1.0F;
   }

   protected final boolean isMouseOverTextInput(int var1, int var2, SettingComponent$1 var3) {
      return this.searchField.isMouseOverField(var1, var2, var3.contentLeftX, var3.contentTopY, var3.contentRightX, 12.0F);
   }

   protected final void drawTextInputBackground(SettingComponent$1 var1) {
      this.searchField.drawFieldBackground(var1.contentLeftX, var1.contentTopY, var1.contentRightX, 12.0F);
   }

   protected final void drawLabelText(SettingComponent$1 var1, String var2) {
      this.searchField.drawGradientText(var2, var1.contentLeftX, var1.boundsY + this.positionY, 12.0F, 0.5F);
   }

   protected final void drawTextWithBox(String var1) {
      SettingComponent$1 var2 = this.computeSettingBounds(false);
      this.drawLabelText(var2, var1);
      this.drawTextInputBackground(var2);
   }

   protected final SettingComponent$1 computeSettingBounds(boolean var1) {
      float var2 = var1 ? this.moduleComponent.categoryComponent.WHmq() : this.moduleComponent.categoryComponent.getPanelY();
      SettingRowLayout var3 = SettingRowLayout.create(this.moduleComponent.categoryComponent.EWZxNc(), var2, this.moduleComponent.categoryComponent.getPanelWidth(), this.positionY, this.groupIndent, 12.0F);
      return new SettingComponent$1(var3);
   }

   protected final TextField getSearchTextField() {
      return this.searchField.getTextField();
   }

   protected static void drawScaledSettingText(String var0, float var1, float var2, int var3, float var4) {
      TextFieldBox.drawScaledString(var0, var1, var2, var3, var4);
   }

   protected static void drawSettingText(String var0, float var1, float var2, int var3) {
      drawScaledSettingText(var0, var1, var2, var3, 0.5F);
   }

   protected static float computeCenteredTextYAtScale(float var0, float var1, float var2) {
      return TextFieldBox.computeCenteredTextY(var0, var1, var2);
   }

   protected static float computeCenteredTextY(float var0, float var1) {
      return computeCenteredTextYAtScale(var0, var1, 0.5F);
   }
}
