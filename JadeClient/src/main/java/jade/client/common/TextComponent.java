// Jade recovery: original class: jade.deps.eLz.F4aP29TgTA
package jade.client.common;

import jade.client.setting.SettingComponent$1;
import jade.client.setting.SettingComponent;
import jade.client.setting.TextSetting;

public class TextComponent extends SettingComponent {
   public final TextSetting textSetting;
   private final TextSettingEditor WmJ;

   public TextComponent(TextSetting var1, ModuleComponent var2, float var3) {
      super(var2, var3, var1.getPlaceholder(), var1.xmB5());
      this.textSetting = var1;
      this.WmJ = new TextSettingEditor(var1);
      this.getSearchTextField().setText(var1.getValue());
   }

   @Override
   public void renderComponent() {
      this.WmJ.syncFieldText(this.getSearchTextField(), this.isTextInputFocused());
      this.drawTextWithBox(this.textSetting.getName());
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      boolean var4 = var3 == 0 && this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this);
      if (!var4) {
         return false;
      } else {
         boolean var5 = this.isMouseOverTextInput(var1, var2, this.computeSettingBounds(true));
         if (var5) {
            if (!this.isTextInputFocused()) {
               this.WmJ.beginEditing(this.getSearchTextField());
            }

            this.setTextInputFocused(true);
            return true;
         } else {
            if (this.isTextInputFocused()) {
               this.WmJ.Cut4(this.getSearchTextField());
               this.setTextInputFocused(false);
            }

            return false;
         }
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe && this.isTextInputFocused()) {
         this.WmJ.handleKeyTyped(this.getSearchTextField(), var1, var2);
      }
   }

   @Override
   public boolean ejbAn() {
      return this.WmJ.isVisible();
   }

   @Override
   public String getGroupName() {
      return this.WmJ.getGroupName();
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      SettingComponent$1 var3 = this.computeSettingBounds(true);
      return this.isMouseOverTextInput(var1, var2, var3);
   }
}
