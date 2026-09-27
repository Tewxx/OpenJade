// Jade recovery: original class: jade.deps.eLz.t5TVwPF
package jade.client.module.player.ghosthand;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public final class GhostHandFilters {
   private GhostHandFilters() {
   }

   public static boolean YWMt(boolean var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      return var0 && var1 && var2 && (!var3 || !var4) && var5 && var6;
   }

   public static boolean zLd0782(boolean var0, boolean var1, boolean var2, boolean var3, boolean var4) {
      return var0 || var1 && var3 || var2 && var4;
   }

   public static boolean isCategoryEnabled(GhostHandFilters$2 var0, boolean var1, boolean var2, boolean var3, boolean var4) {
      switch (var0) {
         case NON_PLAYER:
            return var1;
         case BOT:
            return var2;
         case FRIENDLY:
            return var3;
         default:
            return var4;
      }
   }

   public static GhostHandFilters$1 wsedf(ItemStack var0) {
      if (var0 == null) {
         return GhostHandFilters$1.FISTS;
      } else {
         Item var1 = var0.getItem();
         if (var1 instanceof ItemSword) {
            return GhostHandFilters$1.SWORD;
         } else if (var1 instanceof ItemTool || var1 instanceof ItemHoe || var1 instanceof ItemShears) {
            return GhostHandFilters$1.TOOL;
         } else if (var1 instanceof ItemBucket) {
            return GhostHandFilters$1.BUCKET;
         } else if (var1 instanceof ItemFlintAndSteel) {
            return GhostHandFilters$1.FLINT_STEEL;
         } else {
            return var1 instanceof ItemBlock && ((ItemBlock)var1).getBlock() == Blocks.web ? GhostHandFilters$1.COBWEB : GhostHandFilters$1.OTHER;
         }
      }
   }
}
