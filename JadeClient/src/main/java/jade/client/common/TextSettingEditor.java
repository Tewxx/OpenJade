// Jade recovery: original class: jade.deps.eLz.mx143Et2
package jade.client.common;

import jade.client.gui.TextField;
import jade.client.setting.TextSetting;

public final class TextSettingEditor {
   private final TextSetting textSetting;
   private final PendingValueSlot ZMfin5 = new PendingValueSlot();

   public TextSettingEditor(TextSetting var1) {
      this.textSetting = var1;
   }

   public boolean isVisible() {
      return this.textSetting.visible;
   }

   public String getGroupName() {
      return this.textSetting.groupSetting == null ? "" : this.textSetting.groupSetting.getName();
   }

   public void syncFieldText(TextField var1, boolean var2) {
      if (!var2) {
         if (this.ZMfin5.qcsE1()) {
            this.Cut4(var1);
         }

         String var3 = this.textSetting.getValue();
         if (!var1.getText().equals(var3)) {
            var1.setText(var3);
         }
      }
   }

   public void beginEditing(TextField var1) {
      this.ZMfin5.VHAC(this.textSetting.getValue());
      var1.setFocused(true);
   }

   public void Cut4(TextField var1) {
      String var2 = this.ZMfin5.consumePendingValue();
      if (var2 != null) {
         var1.setText(var2);
         this.textSetting.setValue(var2);
      }
   }

   public void handleKeyTyped(TextField var1, char var2, int var3) {
      if (var3 == 1) {
         this.Cut4(var1);
         var1.setFocused(false);
      } else if (var3 != 28 && var3 != 156) {
         if (var1.keyTyped(var2, var3)) {
            this.textSetting.setValue(var1.getText());
         }
      } else {
         this.textSetting.runChangeCallback();
         this.ZMfin5.clearPending();
         var1.setText(this.textSetting.getValue());
         var1.setFocused(false);
      }
   }
}
