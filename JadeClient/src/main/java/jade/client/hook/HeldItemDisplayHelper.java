// Jade recovery: original class: jade.deps.eLz.tnKsKs0o
package jade.client.hook;

import jade.client.common.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public final class HeldItemDisplayHelper {
   private HeldItemDisplayHelper() {
   }

   public static ItemStack MAUYF(InventoryPlayer var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      ItemStack var2 = var0.getCurrentItem();
      return var1.gameSettings.thirdPersonView == 0 && var0.player == var1.thePlayer ? ClientUtils.resolveRenderedItemStack(var2) : var2;
   }

   public static int resolveItemInUseCount(AbstractClientPlayer var0) {
      int var1 = var0.getItemInUseCount();
      if (var1 > 0) {
         return var1;
      } else {
         Minecraft var2 = Minecraft.getMinecraft();
         ItemStack var3 = var0.inventory.getCurrentItem();
         ItemStack var4 = var2.gameSettings.thirdPersonView == 0 ? ClientUtils.resolveRenderedItemStack(var3) : var3;
         return ItemUseHelper.shouldForceItemUse(var0, var4) ? 1 : 0;
      }
   }
}
