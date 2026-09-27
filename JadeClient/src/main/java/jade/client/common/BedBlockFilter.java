// Jade recovery: original class: jade.deps.eLz.hBVDtZ
package jade.client.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public final class BedBlockFilter implements BlockFilter {
   @Override
   public boolean matches(IBlockState var1) {
      return BedUtils.isBed(var1);
   }

   @Override
   public boolean matchesAt(BlockPos var1, IBlockState var2) {
      return BedUtils.isBedFoot(var2);
   }
}
