// Jade recovery: original class: jade.deps.eLz.X3coXPyU5
package jade.client.common;

import jade.client.gui.Component;
import jade.client.module.Module;
import jade.client.module.profiles.ProfileManager;
import java.util.ArrayList;

public class ModuleComponent extends Component {
   public Module module;
   public CategoryComponent categoryComponent;
   public float HPu;
   public ArrayList<Component> components;
   public boolean Nqe;
   private final HoverHighlightAnimation nms = new HoverHighlightAnimation();
   private final FloatTransition heightTransition;
   private final boolean isProfileManagerModule;
   private final ComponentLayout componentLayout = new ComponentLayout();
   private final ScissorStack scissorStack = new ScissorStack();

   public ModuleComponent(Module var1, CategoryComponent var2, float var3) {
      this.module = var1;
      this.categoryComponent = var2;
      this.HPu = var3;
      this.components = new ArrayList<>();
      this.isProfileManagerModule = var1 instanceof ProfileManager;
      this.Nqe = this.isProfileManagerModule;
      float var4 = this.getHeaderHeight();
      this.heightTransition = new FloatTransition(var4);
      this.IPgC7();
   }

   private void IPgC7() {
      this.components = SettingComponentFactory.ExtHpb(this.module, this, this.HPu + this.getSettingsOffsetY(), !this.isProfileManagerModule);
      this.refreshComponentLayout();
   }

   public void rebuildSettingComponents() {
      boolean var1 = this.Nqe;
      SettingComponentFactory$2 var2 = SettingComponentFactory.KSMlIkm(this.components);
      this.IPgC7();
      SettingComponentFactory.WTEB(this.components, var2);
      this.setExpanded(var1);
      this.invalidateCategoryLayout();
   }

   public void setExpanded(boolean var1) {
      this.Nqe = this.isProfileManagerModule || var1;
      this.heightTransition.setImmediate(this.Nqe ? this.computeContentHeight() : this.getHeaderHeight());
   }

   public void updateHeightAnimation() {
      this.heightTransition.updateTransition(System.currentTimeMillis());
   }

   @Override
   public void setY(float var1) {
      this.HPu = var1;
      this.componentLayout.nd404(this.components, this.HPu + this.getHeaderHeight());
   }

   @Override
   public void renderComponent() {
      ModuleEntryHelper.Lyxy7(this, this.nms, this.nigv());
      ModuleListRenderer.eZiz(this.components, this.componentLayout, this.categoryComponent, this.scissorStack, this.heightTransition, this.HPu, this.Nqe);
   }

   @Override
   public float getHeight() {
      if (this.heightTransition.isAnimating()) {
         return this.heightTransition.getValue();
      } else {
         return !this.Nqe ? this.getHeaderHeight() : this.computeContentHeight();
      }
   }

   @Override
   public int getHeightRounded() {
      return Math.round(this.getHeight());
   }

   public void refreshSliderValues() {
      for (Component var2 : this.components) {
         if (var2 instanceof SliderComponent) {
            ((SliderComponent)var2).qeTtmy();
         }
      }
   }

   public float getExpandedContentHeight() {
      return !this.Nqe && (!this.heightTransition.isAnimating() || !(this.heightTransition.KADbe() > this.getHeaderHeight())) ? this.getHeight() : this.componentLayout.computeTotalHeight(this.components, this.getHeaderHeight(), true);
   }

   @Override
   public void updateLayout(int var1, int var2) {
      for (Component var4 : this.components) {
         var4.updateLayout(var1, var2);
      }

      this.nms.JMHl(this.JoOhdD(var1, var2) && this.categoryComponent.expanded, System.currentTimeMillis());
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (ClickGuiRowHelper.ETk4(this.JoOhdD(var1, var2), var3, this.module::canBeEnabled, this::toggleModule, this::Spu93)) {
         return true;
      } else {
         for (Component var5 : this.components) {
            if (var5.mouseClicked(var1, var2, var3)) {
               return true;
            }
         }

         return false;
      }
   }

   private void Spu93() {
      float var1 = this.getHeight();
      this.Nqe = !this.Nqe;
      this.heightTransition.startTransition(var1, this.Nqe ? this.computeContentHeight() : this.getHeaderHeight(), System.currentTimeMillis());
   }

   @Override
   public void mouseReleased(int var1, int var2, int var3) {
      for (Component var5 : this.components) {
         var5.mouseReleased(var1, var2, var3);
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      for (Component var4 : this.components) {
         var4.keyTyped(var1, var2);
      }
   }

   @Override
   public void keyPressed(int var1) {
      for (Component var3 : this.components) {
         var3.keyPressed(var1);
      }
   }

   @Override
   public void resetEditingState() {
      for (Component var2 : this.components) {
         var2.resetEditingState();
      }

      this.nms.vBj5();
      this.heightTransition.setImmediate(this.Nqe ? this.computeContentHeight() : this.getHeaderHeight());
   }

   public boolean JoOhdD(int var1, int var2) {
      return this.nigv() && ClickGuiRowHelper.isMouseOverModuleRow(var1, var2, this.categoryComponent.EWZxNc(), this.categoryComponent.getPanelWidth(), this.categoryComponent.WHmq(), this.HPu);
   }

   public void invalidateCategoryLayout() {
      this.categoryComponent.recalculateLayout();
   }

   public boolean isComponentVisible(Component var1) {
      return this.componentLayout.isComponentVisible(var1);
   }

   private void refreshComponentLayout() {
      this.componentLayout.rebuildGroupMap(this.components);
   }

   private float computeContentHeight() {
      return this.componentLayout.computeTotalHeight(this.components, this.getHeaderHeight(), false);
   }

   private boolean nigv() {
      return !this.isProfileManagerModule;
   }

   private float getHeaderHeight() {
      return this.nigv() ? 16.0F : 0.0F;
   }

   private float getSettingsOffsetY() {
      return this.nigv() ? 12.0F : 0.0F;
   }

   private void toggleModule() {
      ModuleEntryHelper.toggleModule(this.module);
   }
}
