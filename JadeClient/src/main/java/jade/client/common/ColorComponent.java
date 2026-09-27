// Jade recovery: original class: jade.deps.eLz.tAytRO
package jade.client.common;

import jade.client.Jade;
import jade.client.gui.Component;
import jade.client.gui.TextField;
import jade.client.gui.FocusableTextInput;
import jade.client.module.client.Gui;
import jade.client.setting.ColorSetting;
import org.lwjgl.opengl.GL11;

public class ColorComponent extends Component implements FocusableTextInput {
   public ColorSetting colorSetting;
   private ModuleComponent moduleComponent;
   public float offsetY;
   public float componentX;
   private float componentY;
   public float previewWidth;
   public boolean jvJ;
   private int dragMode;
   private final HsbColor Lge = new HsbColor();
   private final ToggleAnimation expandAnimation = new ToggleAnimation();
   private static final float ROW_HEIGHT = 12.0F;
   private static final float GSJsq = 50.0F;
   private static final float SWATCH_SIZE = 10.0F;
   private static final float vrb = 4.0F;
   private static final float JXBw3 = 0.001F;
   private static final float MIN_PICKER_STEP = 0.001F;
   private static final float bzgZ6 = 10.0F;
   private static final float FIELD_PADDING = 4.0F;
   private static final float SMALL_PADDING = 2.0F;
   private static final float OUTLINE_WIDTH = 2.0F;
   private static final int MAX_INPUT_LENGTH = 20;
   private static final float SWATCH_RADIUS = 5.0F;
   private static final float Dsq = 0.5F;
   private static final float MIN_HEX_FIELD_WIDTH = 52.0F;
   private final TextField textField;
   private String previousText;
   private float previewLeft;
   private float previewTop;
   private float previewRight;
   private float previewBottom;
   private float hexFieldLeft;
   private float hexFieldTop;
   private float hexFieldRight;
   private float x1367;
   private float hexFieldHitTop;
   private float PloQ;

   public ColorComponent(ColorSetting var1, ModuleComponent var2, float var3) {
      this.colorSetting = var1;
      this.moduleComponent = var2;
      this.offsetY = var3;
      this.textField = new TextField("", 10, 0.5F);
   }

   public float getExpandedHeight() {
      return 66.0F;
   }

   public float hDqm53() {
      return this.expandAnimation.getAnimatedValue();
   }

   @Override
   public void renderComponent() {
      float var1 = this.moduleComponent.categoryComponent.EWZxNc();
      float var2 = this.moduleComponent.categoryComponent.getPanelY();
      float var3 = this.moduleComponent.categoryComponent.getPanelWidth();
      float var4 = var1 + 4.0F + this.previewWidth / 2.0F;
      float var5 = var2 + this.offsetY + 3.0F;
      ColorPickerRenderer.BMJr(var4, var5, 5.0F, this.colorSetting.getArgb(), this.colorSetting.supportsAlpha());
      IFont var6 = Gui.getSettingFont();
      String var7 = this.getHexColorText();
      this.layoutHexField(var1, var2, var3, var7, var6);
      GL11.glPushMatrix();
      GL11.glScaled(0.5, 0.5, 0.5);
      float var8 = var6.getStringWidth("[+]  ");
      var6.drawString(this.colorSetting.getName(), (var1 + 4.0F) * 2.0F + this.previewWidth + var8, (var2 + this.offsetY + 4.0F) * 2.0F, -1, true);
      if (!this.isFocused()) {
         var6.drawString(var7, this.previewLeft / 0.5F, (var2 + this.offsetY + 4.0F) * 2.0F, -2697514, true);
      }

      GL11.glPopMatrix();
      if (this.isFocused()) {
         this.textField.render(this.hexFieldLeft, this.hexFieldTop, this.hexFieldRight, this.x1367);
      }

      float var9 = this.hDqm53();
      if (!(var9 <= 0.0F)) {
         float var10 = this.moduleComponent.categoryComponent.animatedPanelY - var2;
         float var11 = var2 + this.offsetY + 12.0F + var10;
         float var12 = (this.getExpandedHeight() - 12.0F) * var9;
         RenderUtils.pushScissorRect(var1, var11, var3, var12);
         ColorPickerRenderer.drawColorPicker(ColorPickerLayout.create(var1, var2 + this.offsetY, this.previewWidth), this.colorSetting, this.Lge, this.dragMode != 0);
         RenderUtils.restoreScissorState();
      }
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.componentY = this.moduleComponent.categoryComponent.WHmq() + this.offsetY;
      this.componentX = this.moduleComponent.categoryComponent.EWZxNc();
      this.textField.kqao7();
      if (this.dragMode != 0 && !(this.hDqm53() < 1.0F)) {
         ColorPickerLayout var3 = ColorPickerLayout.create(this.componentX, this.componentY, this.previewWidth);
         if (this.dragMode == 1) {
            this.Lge.HHMk(var3.getSaturationAt(var1), var3.getBrightnessAt(var2));
            this.colorSetting.TAfvrw(this.Lge.ecjE(), this.Lge.getSaturation(), this.Lge.getBrightness());
            this.hRvykW();
         } else if (this.dragMode == 2) {
            this.Lge.setHue(var3.getHueAt(var2));
            this.colorSetting.TAfvrw(this.Lge.ecjE(), this.Lge.getSaturation(), this.Lge.getBrightness());
            this.hRvykW();
         } else if (this.dragMode == 3 && this.colorSetting.supportsAlpha()) {
            this.colorSetting.setAlpha(HsbColor.normalizedToByte(var3.getVerticalFraction(var2)));
            this.hRvykW();
         }
      }
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         float var4 = this.moduleComponent.categoryComponent.getPanelWidth();
         if (var1 > this.componentX && var1 < this.componentX + var4 && var2 > this.componentY && var2 < this.componentY + 12.0F) {
            if (var3 == 0 && this.isOverPreview(var1, var2)) {
               this.beginHexEditing();
               return true;
            }

            if (var3 == 0 || var3 == 1) {
               this.unfocus();
               this.jvJ = !this.jvJ;
               this.expandAnimation.eSrb(this.jvJ);
               this.moduleComponent.invalidateCategoryLayout();
               return true;
            }
         }

         if (var3 != 0) {
            return false;
         } else if (this.hDqm53() < 1.0F) {
            return false;
         } else {
            ColorPickerLayout var5 = ColorPickerLayout.create(this.componentX, this.componentY, this.previewWidth);
            if (var5.isInsideColorSquare(var1, var2)) {
               this.syncPickerFromSetting();
               this.dragMode = 1;
               return false;
            } else if (var5.jlkyC(var1, var2)) {
               this.syncPickerFromSetting();
               this.dragMode = 2;
               return false;
            } else if (this.colorSetting.supportsAlpha() && var5.isInsideAlphaBar(var1, var2)) {
               this.syncPickerFromSetting();
               this.dragMode = 3;
               return false;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      this.dragMode = 0;
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe && this.isFocused()) {
         if (var2 == 1) {
            this.cancelHexEditing();
         } else if (var2 != 28 && var2 != 156) {
            this.textField.keyTyped(var1, var2);
         } else {
            this.commitHexInput();
         }
      }
   }

   @Override
   public void resetEditingState() {
      this.dragMode = 0;
      this.unfocus();
      this.expandAnimation.snapTo(this.jvJ);
   }

   @Override
   public void setY(float var1) {
      this.offsetY = var1;
   }

   @Override
   public float getWidth() {
      return this.offsetY;
   }

   @Override
   public boolean ejbAn() {
      return this.colorSetting.visible;
   }

   public void setExpandedImmediate(boolean var1) {
      this.jvJ = var1;
      this.expandAnimation.snapTo(var1);
   }

   private void syncPickerFromSetting() {
      this.Lge.MHxl8(this.colorSetting.getHue(), this.colorSetting.getSaturation(), this.colorSetting.pBf3());
   }

   private void hRvykW() {
      if (Jade.Grq != null) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }

   private void layoutHexField(float var1, float var2, float var3, String var4, IFont var5) {
      float var6 = var5.getStringWidth(var4) * 0.5F;
      float var7 = var1 + var3 - 4.0F;
      this.previewLeft = var7 - var6 - 1.0F;
      this.previewTop = var2 + this.offsetY + 2.0F;
      this.previewRight = var7 + 1.0F;
      this.previewBottom = var2 + this.offsetY + 12.0F - 1.0F;
      this.hexFieldRight = var7 + 1.0F;
      this.hexFieldLeft = Math.max(var1 + 12.0F + this.previewWidth / 2.0F, this.hexFieldRight - Math.max(52.0F, var6 + 10.0F));
      this.hexFieldTop = this.previewTop - 1.0F;
      this.x1367 = this.previewBottom + 1.0F;
      float var8 = this.moduleComponent.categoryComponent.WHmq() - this.moduleComponent.categoryComponent.getPanelY();
      this.previewTop += var8;
      this.previewBottom += var8;
      this.hexFieldHitTop = this.hexFieldTop + var8;
      this.PloQ = this.x1367 + var8;
   }

   private boolean isOverPreview(int var1, int var2) {
      return var1 >= this.previewLeft && var1 <= this.previewRight && var2 >= this.previewTop && var2 <= this.previewBottom;
   }

   private void beginHexEditing() {
      this.dragMode = 0;
      this.previousText = this.getHexColorText();
      this.textField.setText(this.previousText);
      this.textField.setFocused(true);
      this.textField.selectAll();
   }

   private void commitHexInput() {
      int[] var1 = HexColors.parseHexColor(this.textField.getText());
      if (var1 == null) {
         this.cancelHexEditing();
      } else {
         if (this.colorSetting.supportsAlpha() && var1.length >= 4) {
            this.colorSetting.setRgba(var1[0], var1[1], var1[2], var1[3]);
         } else {
            this.colorSetting.setRgb(var1[0], var1[1], var1[2]);
         }

         this.syncPickerFromSetting();
         this.hRvykW();
         this.previousText = null;
         this.textField.setFocused(false);
         this.textField.setText(this.getHexColorText());
      }
   }

   private void cancelHexEditing() {
      if (this.previousText != null) {
         this.textField.setText(this.previousText);
      } else {
         this.textField.setText(this.getHexColorText());
      }

      this.previousText = null;
      this.textField.setFocused(false);
   }

   private String getHexColorText() {
      return HexColors.formatHexColor(this.colorSetting.getRed(), this.colorSetting.getGreen(), this.colorSetting.getBlue(), this.colorSetting.JIjrD(), this.colorSetting.supportsAlpha());
   }

   @Override
   public boolean isFocused() {
      return this.textField.isFocused();
   }

   @Override
   public void unfocus() {
      this.cancelHexEditing();
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      return var1 >= this.hexFieldLeft && var1 <= this.hexFieldRight && var2 >= this.hexFieldHitTop && var2 <= this.PloQ;
   }
}
