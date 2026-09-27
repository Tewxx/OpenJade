// Jade recovery: original class: jade.deps.eLz.WendFc6$0
package jade.client.common;

import java.util.List;
import net.minecraft.item.ItemStack;

public final class ItemMatcher$0 {
   private final String EwZ;
   private final String FYz3;
   public final int Hrx;
   public final List<ItemMatcher$1> variants;
   public final String itemId;

   public ItemMatcher$0(String var1, List<ItemMatcher$1> var2, int var3, String var4, String var5) {
      this.EwZ = var5;
      this.FYz3 = var4;
      this.Hrx = var3;
      this.variants = var2;
      this.itemId = var1;
   }

   public boolean isSingleItem() {
      return this.variants.size() <= 1;
   }

   public String getDisplayNameWithCount() {
      int var1 = this.variants.size();
      return this.FYz3 + " (" + var1 + ")";
   }

   public String getDisplayName() {
      return this.FYz3;
   }

   public String getWildcardId() {
      return this.EwZ;
   }

   public ItemStack NQcs() {
      return this.variants.get(CyclicIndexSelector.getCyclicIndex(this.variants.size())).copyItemStack();
   }
}
