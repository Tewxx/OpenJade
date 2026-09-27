// Jade recovery: original class: jade.deps.eLz.KYANljw3$1
package jade.client.setting;

import java.util.List;
import net.minecraft.item.ItemStack;

public final class ItemSlotListResolver$1 {
   private final String GGr;
   private final String UbA679;
   private final ItemStack itemStack;
   private final List<ItemStack> itemStacks;
   private final Integer BCOjd;

   ItemSlotListResolver$1(String var1, String var2, ItemStack var3, List<ItemStack> var4, Integer var5) {
      this.GGr = var1;
      this.UbA679 = var2;
      this.itemStack = var3;
      this.itemStacks = var4;
      this.BCOjd = var5;
   }

   public String pkPjxC() {
      return this.GGr;
   }

   public String getDisplayName() {
      return this.UbA679;
   }

   public ItemStack aks79() {
      return this.itemStack;
   }

   public List<ItemStack> Rwywl() {
      return this.itemStacks;
   }

   public Integer getSlotIndex() {
      return this.BCOjd;
   }
}
