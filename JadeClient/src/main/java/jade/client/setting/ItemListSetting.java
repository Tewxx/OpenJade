// Jade recovery: original class: jade.deps.eLz.sV6YzI
package jade.client.setting;

import jade.client.common.ItemMatcher;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ItemListSetting extends BlockListSetting {
   public ItemListSetting(String var1) {
      this(null, var1);
   }

   public ItemListSetting(String var1, String... var2) {
      this(null, var1, var2);
   }

   public ItemListSetting(GroupSetting var1, String var2) {
      this(var1, var2, new String[0]);
   }

   public ItemListSetting(GroupSetting var1, String var2, String... var3) {
      super(var1, var2, var3);
   }

   public List<String> getItems() {
      return this.getEntries();
   }

   public void addItem(String var1) {
      this.addEntry(var1);
   }

   public void removeItem(String var1) {
      this.removeEntry(var1);
   }

   public boolean containsItem(String var1) {
      return this.containsEntry(var1);
   }

   public boolean EMuhC6(ItemStack var1) {
      return ItemMatcher.AkEm(this.getItems(), var1);
   }
}
