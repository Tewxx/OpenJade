// Jade recovery: original class: jade.deps.eLz.wJOo2uOK$0
package jade.client.module.render.blockesp;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class BlockEspParser$0 {
   public final String registryKey;
   private final Item item;
   public final String displayName;
   public final int metadata;
   public final Block block;

   public BlockEspParser$0(Block var1, int var2, String var3, String var4) {
      this(var1, var2, var3, var4, null);
   }

   public BlockEspParser$0(Block var1, int var2, String var3, String var4, Item var5) {
      this.registryKey = var4;
      this.item = var5;
      this.displayName = var3 == null ? "" : var3;
      this.metadata = var2;
      this.block = var1;
   }

   public ItemStack createItemStack() {
      return BlockItemIcons.createBlockStack(this.block, this.item, this.metadata);
   }
}
