// Jade recovery: original class: jade.deps.eLz.NfvWza$1
package jade.client.setting;

import java.util.List;
import net.minecraft.item.ItemStack;

public final class ItemListEditor$1 extends ItemListComponent$0 {
   private final int primaryColor;
   private final int secondaryColor;

   ItemListEditor$1(String var1, String var2, ItemStack var3, List<ItemStack> var4, int var5, int var6) {
      super(var1, var2, var3, var4);
      this.primaryColor = var5;
      this.secondaryColor = var6;
   }

   public static int getPrimaryColor(ItemListEditor$1 var0) {
      return var0.primaryColor;
   }

   public static int getSecondaryColor(ItemListEditor$1 var0) {
      return var0.secondaryColor;
   }
}
