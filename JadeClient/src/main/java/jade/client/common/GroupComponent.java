// Jade recovery: original class: jade.deps.eLz.Muq6iFDt
package jade.client.common;

import jade.client.gui.Component;
import jade.client.module.client.Gui;
import jade.client.setting.GroupSetting;

public class GroupComponent extends Component {
   public GroupSetting groupSetting;
   private ModuleComponent moduleComponent;
   public float headerOffsetY;
   private float PVTuR;
   private float HtPl;
   public boolean zeF;
   private static final float Frd = 250.0F;
   private final EasedBooleanTransition expandAnimation;

   public GroupComponent(GroupSetting var1, ModuleComponent var2, float var3) {
      this.groupSetting = var1;
      this.moduleComponent = var2;
      this.headerOffsetY = var3;
      this.PVTuR = var2.categoryComponent.EWZxNc() + var2.categoryComponent.getPanelWidth();
      this.HtPl = var2.categoryComponent.getPanelY() + var2.HPu;
      this.zeF = var1.isExpanded();
      this.expandAnimation = new EasedBooleanTransition(this.zeF, 250L, 30L);
   }

   public float YVkqQ() {
      return this.expandAnimation.getCurrentValue(System.currentTimeMillis());
   }

   @Override
   public void renderComponent() {
      CollapsibleArrowLabel.x287(Gui.getSettingFont(), this.groupSetting.getName(), this.moduleComponent.categoryComponent.EWZxNc() + 4.0F, this.moduleComponent.categoryComponent.getPanelY() + this.headerOffsetY + 4.0F, this.YVkqQ());
   }

   @Override
   public void setY(float var1) {
      this.headerOffsetY = var1;
   }

   @Override
   public float getWidth() {
      return this.headerOffsetY;
   }

   @Override
   public void updateLayout(int var1, int var2) {
      this.PVTuR = this.moduleComponent.categoryComponent.EWZxNc();
      this.HtPl = this.moduleComponent.categoryComponent.WHmq() + this.headerOffsetY;
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      boolean var4 = var3 == 0 || var3 == 1;
      if (var4 && this.moduleComponent.Nqe && this.isOverHeader(var1, var2)) {
         this.zeF = !this.zeF;
         this.groupSetting.setExpanded(this.zeF);
         this.expandAnimation.animateTo(this.zeF, System.currentTimeMillis());
         this.moduleComponent.invalidateCategoryLayout();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void resetEditingState() {
      this.expandAnimation.snapTo(this.zeF);
   }

   public boolean isOverHeader(int var1, int var2) {
      float var3 = this.PVTuR + this.moduleComponent.categoryComponent.getPanelWidth();
      float var4 = this.HtPl + 11.0F;
      return var1 > this.PVTuR && var1 < var3 && var2 > this.HtPl && var2 < var4;
   }
}
