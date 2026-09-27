// Jade recovery: original class: jade.deps.eLz.wJOo2uOK$1
package jade.client.module.render.blockesp;

import jade.client.common.CyclicIndexSelector;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class BlockEspParser$1 {
   public final int matchCount;
   public final List<BlockEspParser$0> nh7;
   public final String baseRegistryName;

   public BlockEspParser$1(String var1, List<BlockEspParser$0> var2, int var3) {
      this.matchCount = var3;
      this.nh7 = var2;
      this.baseRegistryName = var1;
   }

   public boolean isSingleMatch() {
      return this.nh7.size() <= 1;
   }

   public String buildGroupLabel() {
      BlockEspParser$0 var1 = this.nh7.get(0);
      return var1.displayName + " (" + this.nh7.size() + ")";
   }

   public ItemStack getRepresentativeStack() {
      return this.nh7.get(CyclicIndexSelector.getCyclicIndex(this.nh7.size())).createItemStack();
   }
}
