// Jade recovery: original class: jade.deps.eLz.TKis56uoZb
package jade.client.setting;

import java.util.List;

public class NameListSetting extends BlockListSetting {
   public NameListSetting(String var1) {
      this(null, var1);
   }

   public NameListSetting(String var1, String... var2) {
      this(null, var1, var2);
   }

   public NameListSetting(GroupSetting var1, String var2) {
      this(var1, var2, new String[0]);
   }

   public NameListSetting(GroupSetting var1, String var2, String... var3) {
      super(var1, var2, var3);
   }

   public List<String> getEntryList() {
      return this.getEntries();
   }

   public void appendEntry(String var1) {
      this.addEntry(var1);
   }

   public void WAAq(String var1) {
      this.removeEntry(var1);
   }

   public boolean hasEntry(String var1) {
      return this.containsEntry(var1);
   }
}
