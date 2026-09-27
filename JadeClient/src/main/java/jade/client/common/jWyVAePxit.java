// Jade recovery: original class: jade.deps.eLz.jWyVAePxit
package jade.client.common;

import jade.mixin.impl.accessor.IAccessorItemFood;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public final class jWyVAePxit {
   private jWyVAePxit() {
   }

   public static boolean isHoldingItemType(EntityLivingBase var0, Class<? extends Item> var1) {
      return var0.getHeldItem() != null && var1.isInstance(var0.getHeldItem().getItem());
   }

   public static boolean Osqz7(ItemStack var0, EntityPlayer var1) {
      if (!(var0.getItem() instanceof ItemFood)) {
         return true;
      } else {
         return var1.getFoodStats().getFoodLevel() != 20 ? true : ((IAccessorItemFood)((ItemFood)var0.getItem())).getAlwaysEdible();
      }
   }
}
