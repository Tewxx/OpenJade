// Jade recovery: original class: jade.deps.eLz.mjZzlh
package jade.client.module.render.blockesp;

import jade.client.common.VCvk14;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class BlockItemIcons {
   private final Map<Block, Item> blockItemOverrides = new HashMap<>();

   public BlockItemIcons() {
      this.blockItemOverrides.put(Blocks.bed, Items.bed);
   }

   public Item PBYJHOb(Block var1) {
      return this.blockItemOverrides.get(var1);
   }

   public static ItemStack createBlockStack(Block var0, Item var1, int var2) {
      return var1 == null ? new ItemStack(var0, 1, var2) : new ItemStack(var1, 1, var2);
   }

   public ItemStack SxiT(String var1) {
      boolean var2 = VCvk14.hasWildcardSuffix(var1);
      String var3 = VCvk14.normalizeBlockId(var2 ? VCvk14.stripWildcardSuffix(var1) : var1);
      if (var3 == null) {
         return null;
      } else {
         Block var4;
         try {
            var4 = (Block)Block.blockRegistry.getObject(new ResourceLocation(var3));
         } catch (Exception var6) {
            return null;
         }

         return var4 == null ? null : createBlockStack(var4, this.PBYJHOb(var4), var2 ? 0 : VCvk14.DPdat(var1));
      }
   }

   public String ublf(String var1) {
      boolean var2 = VCvk14.hasWildcardSuffix(var1);
      ItemStack var3 = this.SxiT(var2 ? VCvk14.stripWildcardSuffix(var1) : var1);
      return var3 == null ? var1 : var3.getDisplayName() + (var2 ? " (All)" : "");
   }
}
