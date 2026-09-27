// Jade recovery: original class: jade.deps.eLz.DLzwzOYvZI
package jade.client.common;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

public final class PlaceUtils {
   private PlaceUtils() {
   }

   public static boolean IBYK(Minecraft var0, MovingObjectPosition var1) {
      if (var1 == null || var1.typeOfHit != MovingObjectType.BLOCK || var1.getBlockPos() == null) {
         return false;
      } else {
         return var0.thePlayer.isSneaking() && var0.thePlayer.getHeldItem() != null ? false : BlockTypes.DSLVc(WorldUtils.getBlock(var0.theWorld, var1.getBlockPos()));
      }
   }

   public static boolean isBlockReplaceable(Minecraft var0, BlockPos var1) {
      return !ClientUtils.isInWorld() ? true : WorldUtils.getBlock(var0.theWorld, var1).isReplaceable(var0.theWorld, var1);
   }

   public static boolean canPlaceItemOnSide(Minecraft var0, ItemStack var1, BlockPos var2, EnumFacing var3) {
      return var1 != null && var1.getItem() instanceof ItemBlock
         ? ((ItemBlock)var1.getItem()).canPlaceBlockOnSide(var0.theWorld, var2, var3, var0.thePlayer, var1)
         : false;
   }
}
