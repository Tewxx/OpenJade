// Jade recovery: original class: jade.deps.eLz.mfVOUAAl7
package jade.client.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public final class mfVOUAAl7 {
   private final Map<Integer, ItemStack> lukJm = new HashMap<>();
   private final IntFunction<ItemStack> intFunction;

   public mfVOUAAl7() {
      this(mfVOUAAl7::createPotionStack);
   }

   public mfVOUAAl7(IntFunction<ItemStack> var1) {
      this.intFunction = var1;
   }

   public ItemStack getCachedStack(int var1) {
      if (!this.lukJm.containsKey(var1)) {
         this.lukJm.put(var1, this.intFunction.apply(var1));
      }

      ItemStack var2 = this.lukJm.get(var1);
      return var2 == null ? null : var2.copy();
   }

   private static ItemStack createPotionStack(int var0) {
      return Items.potionitem instanceof ItemPotion ? new ItemStack(Items.potionitem, 1, PotionUtils.findBestPotionMetadata(Items.potionitem, var0)) : null;
   }
}
