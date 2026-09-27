// Jade recovery: original class: jade.deps.eLz.NTFhDra42n
package jade.client.event;

import net.minecraft.item.ItemStack;

public class UseItemEvent extends Event {
   public ItemStack itemStack;

   public UseItemEvent(ItemStack var1) {
      this.itemStack = var1;
   }
}
