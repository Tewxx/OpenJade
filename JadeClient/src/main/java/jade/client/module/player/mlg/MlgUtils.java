// Jade recovery: original class: jade.deps.eLz.rjxgI6oRoU
package jade.client.module.player.mlg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class MlgUtils {
   private MlgUtils() {
   }

   public static boolean EReKjl(ItemStack var0, Item var1) {
      return var0 != null && var0.getItem() == var1;
   }

   public static int KMqC(InventoryPlayer var0, Item var1) {
      int var2 = InventoryPlayer.getHotbarSize();

      for (int var3 = 0; var3 < var2; var3++) {
         if (EReKjl(var0.getStackInSlot(var3), var1)) {
            return var3;
         }
      }

      return -1;
   }

   public static boolean isFallingDangerously(EntityPlayer var0) {
      return !var0.onGround && var0.fallDistance >= 3.3F;
   }
}
