// Jade recovery: original class: jade.deps.eLz.qbx0ewn
package jade.client.setting;

import java.util.List;

public class GroupSetting extends LabelSetting {
   private boolean hzs;

   public GroupSetting(String var1) {
      super(var1);
   }

   public boolean isExpanded() {
      return this.hzs;
   }

   public void setExpanded(boolean var1) {
      this.hzs = var1;
   }

   public List<Setting> getSettings() {
      return null;
   }

   public void addSetting(Setting var1) {
      throw new NullPointerException();
   }

   public void removeSetting(Setting var1) {
      throw new NullPointerException();
   }
}
