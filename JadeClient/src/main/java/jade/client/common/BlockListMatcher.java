// Jade recovery: original class: jade.deps.eLz.sISGsz
package jade.client.common;

import jade.client.setting.BlockListSetting;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public final class BlockListMatcher {
   private final BlockListSetting blockListSetting;
   private int cachedListHash;
   private ItemIdMatcher blockMatcher;

   public BlockListMatcher(BlockListSetting var1) {
      this.blockListSetting = var1;
      this.rebuildMatcher();
   }

   public void refreshIfChanged() {
      if (this.blockListSetting.getEntries().hashCode() != this.cachedListHash) {
         this.rebuildMatcher();
      }
   }

   public boolean hasAnyBlocks() {
      return !this.blockListSetting.getEntries().isEmpty();
   }

   public boolean POTheU(IBlockState var1) {
      if (var1 == null) {
         return false;
      } else {
         Block var2 = var1.getBlock();
         Object var3 = var2 == null ? null : Block.blockRegistry.getNameForObject(var2);
         return var3 != null && this.blockMatcher.matches(var3.toString(), var2.getMetaFromState(var1));
      }
   }

   private void rebuildMatcher() {
      List var1 = this.blockListSetting.getEntries();
      this.cachedListHash = var1.hashCode();
      this.blockMatcher = ItemIdMatcher.EYgroY8(var1);
   }
}
