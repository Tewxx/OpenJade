// Jade recovery: original class: jade.deps.eLz.co8Oo6hfjh
package jade.client.module.player.blockin;

import jade.client.common.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public final class BlockSlotSelector {
   private BlockSlotSelector() {
   }

   public static int findPlaceableSlot(EntityPlayer var0, boolean var1) {
      return HotbarSlotSelector.selectSlotByScore(var1, (recoveredArg0) -> BlockSlotSelector.MuUrm5(var0, recoveredArg0), (recoveredArg0) -> BlockSlotSelector.getSlotBreakSpeed(var0, recoveredArg0));
   }

   private static boolean isPlaceableStack(ItemStack var0) {
      return var0 != null && var0.stackSize != 0 && var0.getItem() instanceof ItemBlock && getBlockFromStack(var0) != null && getBlockFromStack(var0).isFullBlock();
   }

   private static Block getBlockFromStack(ItemStack var0) {
      return ((ItemBlock)var0.getItem()).getBlock();
   }

   private static double getSlotBreakSpeed(EntityPlayer var0, int var1) {
      return BlockUtils.getBlockBreakTicks(getBlockFromStack(var0.inventory.mainInventory[var1]));
   }

   private static boolean MuUrm5(EntityPlayer var0, int var1) {
      return isPlaceableStack(var0.inventory.mainInventory[var1]);
   }
}
