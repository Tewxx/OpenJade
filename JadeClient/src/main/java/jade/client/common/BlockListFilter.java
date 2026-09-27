// Jade recovery: original class: jade.deps.eLz.CUhqPPR3Yh
package jade.client.common;

import jade.client.setting.BlockListSetting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public final class BlockListFilter implements BlockFilter {
   private final BlockListMatcher cc7;

   public BlockListFilter(BlockListSetting var1) {
      this.cc7 = new BlockListMatcher(var1);
   }

   @Override
   public void zykH() {
      this.cc7.refreshIfChanged();
   }

   @Override
   public boolean isActive() {
      return this.cc7.hasAnyBlocks();
   }

   @Override
   public boolean matches(IBlockState var1) {
      return this.cc7.POTheU(var1);
   }

   @Override
   public boolean matchesAt(BlockPos var1, IBlockState var2) {
      return this.cc7.POTheU(var2);
   }
}
