// Jade recovery: original class: jade.deps.eLz.HYkckWEdyc
package jade.client.common;

import jade.client.Jade;
import jade.client.gui.Component;
import jade.client.gui.TextField;
import jade.client.gui.FocusableTextInput;
import jade.client.module.client.Gui;
import jade.client.module.render.Arraylist;
import jade.client.setting.FontSetting;
import jade.client.setting.SliderSetting;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class SliderComponent extends Component implements FocusableTextInput {
   public SliderSetting sliderSetting;
   private ModuleComponent moduleComponent;
   public float layoutOffset;
   public float rowOriginX;
   private float rowOriginY;
   public boolean draggingHandle = false;
   private double vh0;
   public float indentX;
   private final SmoothedValue NDXxuf;
   private static final double SLIDER_SMOOTHING = 0.6;
   private static final float TRACK_THICKNESS = 0.5F;
   private static final float MIN_TEXT_FIELD_WIDTH = 28.0F;
   private final TextField textField;
   private double CIAdX;
   private boolean textEditing;
   private float valueHoverLeft;
   private float valueHoverTop;
   private float valueHoverRight;
   private float valueHoverBottom;
   private float editFieldLeft;
   private float editFieldTop;
   private float editFieldRight;
   private float editFieldBottom;
   private float scrolledFieldTop;
   private float scrolledFieldBottom;

   public SliderComponent(SliderSetting var1, ModuleComponent var2, float var3) {
      this.sliderSetting = var1;
      this.moduleComponent = var2;
      this.layoutOffset = var3;
      this.textField = new TextField("", 64, 0.5F);
      double var4 = var1.getInput() == -1.0 && var1.allowsDisabled ? -1.0 : var1.getInput();
      this.NDXxuf = new SmoothedValue(var4);
      this.vh0 = SliderGeometry.getHandleOffset(var1, var2.categoryComponent.getPanelWidth());
   }

   @Override
   public void renderComponent() {
      if (!this.draggingHandle && this.sliderSetting instanceof FontSetting) {
         this.qeTtmy();
      }

      RenderUtils.jxyoE(
         this.moduleComponent.categoryComponent.EWZxNc() + 4.0F + this.indentX / 2.0F,
         this.moduleComponent.categoryComponent.getPanelY() + this.layoutOffset + 11.0F,
         this.moduleComponent.categoryComponent.EWZxNc() + 4.0F + this.moduleComponent.categoryComponent.getPanelWidth() - 8.0F,
         this.moduleComponent.categoryComponent.getPanelY() + this.layoutOffset + 15.0F,
         4.0F,
         -12302777
      );
      float var1 = this.moduleComponent.categoryComponent.EWZxNc() + 4.0F + this.indentX / 2.0F;
      float var2 = (float)(var1 + this.vh0);
      if (var2 - var1 > 84.0F) {
         var2 = var1 + 84.0F;
      }

      RenderUtils.jxyoE(
         var1,
         this.moduleComponent.categoryComponent.getPanelY() + this.layoutOffset + 11.0F,
         var2,
         this.moduleComponent.categoryComponent.getPanelY() + this.layoutOffset + 15.0F,
         4.0F,
         Color.getHSBColor((float)(System.currentTimeMillis() % 11000L) / 11000.0F, 0.75F, 0.9F).getRGB()
      );
      GL11.glPushMatrix();
      GL11.glScaled(0.5, 0.5, 0.5);
      double var3 = this.getRenderedValue();
      String var5 = DegreeSymbols.applyDegreeSymbol(this.sliderSetting.getSuffix());
      String var6;
      String var7;
      if (var3 == -1.0 && this.sliderSetting.allowsDisabled) {
         var6 = "§cDisabled";
         var7 = "Disabled";
         var5 = "";
      } else {
         if (var3 != 1.0 && (var5.equals(" second") || var5.equals(" block") || var5.equals(" tick"))) {
            var5 = var5 + "s";
         }

         if (this.sliderSetting.isMode) {
            int var8 = (int)Math.round(var3);
            var8 = Math.max(0, Math.min(var8, this.sliderSetting.getOptions().length - 1));
            var6 = this.sliderSetting.getOptions()[var8];
            var7 = var6;
         } else {
            var6 = ClientUtils.formatNumberAsString(var3);
            var7 = var6;
         }
      }

      float var11 = (this.moduleComponent.categoryComponent.EWZxNc() + 4.0F) * 2.0F + this.indentX;
      float var9 = (this.moduleComponent.categoryComponent.getPanelY() + this.layoutOffset + 3.0F) * 2.0F;
      this.computeTextLayout(var11, var9, var6, var7, var5);
      if (this.qnmZ()) {
         this.LhwU(var11, var9, var6, var5);
      } else {
         Gui.getSettingFont().drawString(this.sliderSetting.getName() + ": " + (this.sliderSetting.isMode ? "§e" : "§b") + var6 + var5, var11, var9, -1, true);
      }

      GL11.glPopMatrix();
      if (this.isFocused()) {
         this.textField.render(this.editFieldLeft, this.editFieldTop, this.editFieldRight, this.editFieldBottom);
      }
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.rowOriginY = this.moduleComponent.categoryComponent.WHmq() + this.layoutOffset;
      this.rowOriginX = this.moduleComponent.categoryComponent.EWZxNc();
      this.textField.kqao7();
      if (this.draggingHandle) {
         double var3 = this.moduleComponent.categoryComponent.getPanelWidth() - 8.0F;
         double var5 = this.NDXxuf.updateTargetFromPosition(var1 - this.rowOriginX, var3, this.sliderSetting.getMin(), this.sliderSetting.getMax(), this.sliderSetting.allowsDisabled, 0.6);
         if (!this.isGuiScaleSlider()) {
            if (var5 == -1.0) {
               this.sliderSetting.setValueRaw(-1.0);
            } else {
               this.sliderSetting.setValueClamped(var5);
            }
         }

         this.vh0 = this.NDXxuf.UphyY(var3, this.sliderSetting.getMin(), this.sliderSetting.getMax());
         if (this.sliderSetting.getInput() != this.sliderSetting.getMin()
            && Jade.getModuleManager().getModule(Arraylist.class) != null
            && Jade.getModuleManager().getModule(Arraylist.class).isEnabled()
            && !Jade.getModuleManager().getSortedModules().isEmpty()) {
            Jade.getModuleManager().resortModules();
         }

         if (Jade.Grq != null) {
            Jade.Grq.getProfile().unmodified = false;
         }
      }
   }

   public void qeTtmy() {
      double var1 = this.sliderSetting.getInput() == -1.0 && this.sliderSetting.allowsDisabled ? -1.0 : this.sliderSetting.getInput();
      this.NDXxuf.resetValue(var1);
      this.vh0 = SliderGeometry.getHandleOffset(this.sliderSetting, this.moduleComponent.categoryComponent.getPanelWidth());
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
         if ((var3 != 0 || !this.fiza(var1, var2)) && (var3 != 2 || !this.isOverLeftHalf(var1, var2) && !this.isOverRightHalf(var1, var2))) {
            if ((this.isOverLeftHalf(var1, var2) || this.isOverRightHalf(var1, var2)) && var3 == 0 && this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this)) {
               this.unfocus();
               this.draggingHandle = true;
            }

            return false;
         } else {
            this.beginTextEdit();
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      boolean var4 = this.draggingHandle;
      this.draggingHandle = false;
      if (var3 == 0 && var4 && this.isGuiScaleSlider()) {
         if (this.NDXxuf.getTargetValue() == -1.0) {
            this.sliderSetting.setValueRaw(-1.0);
         } else {
            this.sliderSetting.setValueClamped(this.NDXxuf.getTargetValue());
         }

         this.qeTtmy();
         Jade.clickGui.requestScaleRefresh();
      }
   }

   public boolean isOverLeftHalf(int var1, int var2) {
      return SplitRowHitTest.FBin(var1, var2, this.rowOriginX, this.rowOriginY, this.moduleComponent.categoryComponent.getPanelWidth());
   }

   public boolean isOverRightHalf(int var1, int var2) {
      return SplitRowHitTest.BCwl(var1, var2, this.rowOriginX, this.rowOriginY, this.moduleComponent.categoryComponent.getPanelWidth());
   }

   @Override
   public void resetEditingState() {
      this.draggingHandle = false;
      this.unfocus();
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe && this.isFocused()) {
         if (var2 == 1) {
            this.cancelTextEdit();
         } else if (var2 != 28 && var2 != 156) {
            this.textField.keyTyped(var1, var2);
         } else {
            this.Una7();
         }
      }
   }

   @Override
   public void setY(float var1) {
      this.layoutOffset = var1;
   }

   private boolean qnmZ() {
      return this.sliderSetting instanceof FontSetting;
   }

   private boolean isGuiScaleSlider() {
      return this.moduleComponent.module instanceof Gui && this.sliderSetting == Gui.guiScale;
   }

   private double getRenderedValue() {
      return this.isGuiScaleSlider() && this.draggingHandle ? this.NDXxuf.getTargetValue() : this.sliderSetting.getInput();
   }

   private void LhwU(float var1, float var2, String var3, String var4) {
      String var5 = this.sliderSetting.getName() + ": ";
      Minecraft var6 = Minecraft.getMinecraft();
      var6.fontRendererObj.drawStringWithShadow(var5, var1, var2, -1);
      IFont var7 = FontManager.getClickGuiSettingRenderer(var3);
      float var8 = var1 + var6.fontRendererObj.getStringWidth(var5);
      float var9 = var2 - (var7.getFontHeight() - var6.fontRendererObj.FONT_HEIGHT) / 2.0F;
      var7.drawString(var3 + var4, var8, var9, 16777215, true);
   }

   private void computeTextLayout(float var1, float var2, String var3, String var4, String var5) {
      IFont var6 = Gui.getSettingFont();
      String var7 = this.sliderSetting.getName() + ": ";
      float var8 = var1 * 0.5F;
      float var9 = var2 * 0.5F;
      float var10 = var6.getStringWidth(var7) * 0.5F;
      float var11 = var6.getStringWidth(var4) * 0.5F;
      float var12 = var6.getStringWidth(var5) * 0.5F;
      this.valueHoverLeft = var8 + var10 - 1.0F;
      this.valueHoverTop = var9 - 1.0F;
      this.valueHoverRight = this.valueHoverLeft + Math.max(8.0F, var11) + 2.0F;
      this.valueHoverBottom = var9 + 8.0F;
      this.editFieldLeft = this.valueHoverLeft;
      this.editFieldTop = this.valueHoverTop;
      this.editFieldRight = Math.min(this.moduleComponent.categoryComponent.EWZxNc() + this.moduleComponent.categoryComponent.getPanelWidth() - 4.0F, this.editFieldLeft + Math.max(28.0F, var11 + var12 + 12.0F));
      this.editFieldBottom = this.valueHoverBottom + 1.0F;
      float var13 = this.moduleComponent.categoryComponent.WHmq() - this.moduleComponent.categoryComponent.getPanelY();
      this.valueHoverTop += var13;
      this.valueHoverBottom += var13;
      this.scrolledFieldTop = this.editFieldTop + var13;
      this.scrolledFieldBottom = this.editFieldBottom + var13;
   }

   private boolean fiza(int var1, int var2) {
      return var1 >= this.valueHoverLeft && var1 <= this.valueHoverRight && var2 >= this.valueHoverTop && var2 <= this.valueHoverBottom;
   }

   private void beginTextEdit() {
      this.draggingHandle = false;
      this.CIAdX = this.sliderSetting.getInput();
      this.textEditing = true;
      this.textField.setText(this.IOoNk());
      this.textField.setFocused(true);
      this.textField.selectAll();
   }

   private String IOoNk() {
      double var1 = this.sliderSetting.getInput();
      if (var1 == -1.0 && this.sliderSetting.allowsDisabled) {
         return "Disabled";
      } else if (this.sliderSetting.isMode) {
         int var3 = (int)Math.round(var1);
         var3 = Math.max(0, Math.min(var3, this.sliderSetting.getOptions().length - 1));
         return this.sliderSetting.getOptions()[var3];
      } else {
         return ClientUtils.formatNumberAsString(var1);
      }
   }

   private void Una7() {
      Double var1 = this.UbM0(this.textField.getText());
      if (var1 == null) {
         this.cancelTextEdit();
      } else {
         if (var1 == -1.0 && this.sliderSetting.allowsDisabled) {
            this.sliderSetting.setValueRaw(-1.0);
         } else {
            this.sliderSetting.setValueClamped(var1);
         }

         if (this.sliderSetting.getInput() != this.sliderSetting.getMin()
            && Jade.getModuleManager().getModule(Arraylist.class) != null
            && Jade.getModuleManager().getModule(Arraylist.class).isEnabled()
            && !Jade.getModuleManager().getSortedModules().isEmpty()) {
            Jade.getModuleManager().resortModules();
         }

         if (Jade.Grq != null) {
            Jade.Grq.getProfile().unmodified = false;
         }

         this.textEditing = false;
         this.textField.setFocused(false);
         this.qeTtmy();
         if (this.isGuiScaleSlider()) {
            Jade.clickGui.requestScaleRefresh();
         }
      }
   }

   private Double UbM0(String var1) {
      return SliderValueParser.parseSliderValue(var1, this.sliderSetting.allowsDisabled, this.sliderSetting.isMode, this.sliderSetting.getOptions(), this.sliderSetting.getMin(), this.sliderSetting.getMax());
   }

   private void cancelTextEdit() {
      if (this.textEditing) {
         if (this.CIAdX == -1.0 && this.sliderSetting.allowsDisabled) {
            this.sliderSetting.setValueRaw(-1.0);
         } else {
            this.sliderSetting.setValueClamped(this.CIAdX);
         }
      }

      this.textEditing = false;
      this.textField.setFocused(false);
      this.textField.setText(this.IOoNk());
      this.qeTtmy();
   }

   @Override
   public boolean isFocused() {
      return this.textField.isFocused();
   }

   @Override
   public void unfocus() {
      this.cancelTextEdit();
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      return var1 >= this.editFieldLeft && var1 <= this.editFieldRight && var2 >= this.scrolledFieldTop && var2 <= this.scrolledFieldBottom || this.isOverLeftHalf(var1, var2) || this.isOverRightHalf(var1, var2);
   }

   @Override
   public float getWidth() {
      return this.getLayoutOffset();
   }

   private float getLayoutOffset() {
      return this.layoutOffset;
   }

   @Override
   public boolean ejbAn() {
      return this.sliderSetting.visible;
   }
}
