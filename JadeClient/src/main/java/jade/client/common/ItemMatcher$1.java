// Jade recovery: original class: jade.deps.eLz.WendFc6$1
package jade.client.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemMatcher$1 {
   private final ItemStack itemStack;
   public final String hvDbs;
   public final String Qwi;
   public final int nfxLs;
   public final Item item;

   public ItemMatcher$1(Item var1, int var2, String var3, String var4) {
      this(var1, var2, var3, var4, var1 != null ? new ItemStack(var1, 1, var2) : null);
   }

   public ItemMatcher$1(Item var1, int var2, String var3, String var4, ItemStack var5) {
      this.itemStack = var5 != null ? var5.copy() : null;
      this.hvDbs = var4;
      this.Qwi = var3 == null ? "" : var3;
      this.nfxLs = var2;
      this.item = var1;
   }

   public ItemStack copyItemStack() {
      return this.itemStack != null ? this.itemStack.copy() : null;
   }
}
