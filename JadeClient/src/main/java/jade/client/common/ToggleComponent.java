// Jade recovery: original class: jade.deps.eLz.SV9ZlG5
package jade.client.common;

import jade.client.gui.Component;
import jade.client.module.Module;
import jade.client.module.client.Gui;
import jade.client.setting.BooleanSetting;

public class ToggleComponent extends Component {
   public BooleanSetting booleanSetting;
   public float x;
   public float offsetY;
   public float labelOffsetX;
   private float y;
   private final ModuleComponent moduleComponent;
   private final Module module;

   public ToggleComponent(Module var1, BooleanSetting var2, ModuleComponent var3, float var4) {
      this.module = var1;
      this.moduleComponent = var3;
      this.booleanSetting = var2;
      this.offsetY = var4;
      this.setPosition(var3.categoryComponent.EWZxNc() + var3.categoryComponent.getPanelWidth(), var3.categoryComponent.getPanelY() + var3.HPu);
   }

   @Override
   public boolean ejbAn() {
      return this.booleanSetting.visible;
   }

   public boolean isHovered(int var1, int var2) {
      return BooleanSettingRowHelper.isRowHovered(var1, var2, 0, this.x, this.y, this.moduleComponent.categoryComponent.getPanelWidth(), true, true);
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      float var4 = this.moduleComponent.categoryComponent.getPanelWidth();
      boolean var5 = this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this);
      if (BooleanSettingRowHelper.isRowHovered(var1, var2, var3, this.x, this.y, var4, var5, var5)) {
         BooleanSettingToggle.toggleBooleanSetting(this.module, this.booleanSetting);
      }

      return false;
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.setPosition(this.moduleComponent.categoryComponent.EWZxNc(), this.moduleComponent.categoryComponent.WHmq() + this.offsetY);
   }

   @Override
   public void renderComponent() {
      TooltipTextRenderer.drawTooltipText(
         Gui.getSettingFont(),
         this.booleanSetting.getName(),
         this.booleanSetting.isButton,
         this.booleanSetting.isToggled(),
         this.moduleComponent.categoryComponent.EWZxNc(),
         this.moduleComponent.categoryComponent.getPanelY(),
         this.offsetY,
         this.labelOffsetX
      );
   }

   @Override
   public void setY(float var1) {
      this.offsetY = var1;
   }

   @Override
   public float getWidth() {
      return this.offsetY;
   }

   private void setPosition(float var1, float var2) {
      this.x = var1;
      this.y = var2;
   }
}
