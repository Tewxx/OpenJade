// Jade recovery: original class: jade.deps.eLz.VAd5e3Un3
package jade.client.hook;

import jade.client.common.ClientUtils;
import net.minecraft.item.ItemStack;

public final class ItemRenderSession {
   private ItemStack itemStack;

   public ItemStack beginRenderSession(ItemStack var1) {
      this.itemStack = var1;
      return ClientUtils.resolveRenderedItemStack(var1);
   }

   public ItemStack endRenderSession() {
      ItemStack var1 = this.itemStack;
      this.itemStack = null;
      return var1;
   }
}
