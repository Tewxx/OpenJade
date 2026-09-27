// Jade recovery: original class: jade.deps.eLz.VCcDEORc
package jade.client.common;

import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;

public final class BedUtils {
   private BedUtils() {
   }

   public static boolean isBed(IBlockState var0) {
      return var0 != null && var0.getBlock() instanceof BlockBed;
   }

   public static boolean isBedFoot(IBlockState var0) {
      return isBed(var0) && var0.getValue(BlockBed.PART) == EnumPartType.FOOT;
   }
}
