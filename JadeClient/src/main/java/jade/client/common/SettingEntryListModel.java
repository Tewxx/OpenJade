// Jade recovery: original class: jade.deps.eLz.ciEJUJLt
package jade.client.common;

import jade.client.setting.NameListSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SettingEntryListModel {
   private final NameListSetting sourceSetting;
   private List<PotionLookup$0> filteredEntries = Collections.emptyList();

   public SettingEntryListModel(NameListSetting var1) {
      this.sourceSetting = var1;
   }

   public String OZWK() {
      int var1 = this.sourceSetting.getEntryList().size();
      return var1 == 0 ? this.sourceSetting.getName() : this.sourceSetting.getName() + " (" + var1 + ")";
   }

   public int HdhDw() {
      return this.sourceSetting.getEntryList().size();
   }

   public int getFilteredCount() {
      return this.filteredEntries.size();
   }

   public PotionLookup$0 Laqjix(int var1) {
      return this.filteredEntries.get(var1);
   }

   public String getEntryName(int var1) {
      return this.sourceSetting.getEntryList().get(var1);
   }

   public List<String> getAllEntryNames() {
      return new ArrayList<>(this.sourceSetting.getEntryList());
   }

   public void applySearchFilter(String var1) {
      this.filteredEntries = PotionLookup.searchPotions(var1, this.sourceSetting);
   }

   public void VqhFt(int var1) {
      this.sourceSetting.appendEntry(this.Laqjix(var1).registryName);
      this.clearSearchFilter();
   }

   public void ZXMTz(String var1) {
      this.sourceSetting.WAAq(var1);
   }

   public void clearSearchFilter() {
      this.filteredEntries = Collections.emptyList();
   }
}
