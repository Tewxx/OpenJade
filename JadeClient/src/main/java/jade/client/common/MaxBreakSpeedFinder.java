// Jade recovery: original class: jade.deps.eLz.N81puugj0
package jade.client.common;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class MaxBreakSpeedFinder {
   private MaxBreakSpeedFinder() {
   }

   public static float bPoca(Block var0, EntityPlayer var1, World var2, int var3) {
      if (var1 != null && var3 > 0) {
         int var4 = Math.min(var3, var1.inventory.getSizeInventory());
         float var5 = 0.0F;

         for (int var6 = 0; var6 < var4; var6++) {
            ItemStack var7 = var1.inventory.getStackInSlot(var6);
            float var8 = BreakSpeed.computeBreakSpeed(var0, var7, var1, var2, false, false);
            if (var8 > var5) {
               var5 = var8;
            }
         }

         return var5;
      } else {
         return 0.0F;
      }
   }
}
