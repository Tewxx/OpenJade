// Jade recovery: original class: jade.deps.eLz.NkDyzD
package jade.client.common;

import jade.client.module.render.blockesp.BlockEspParser$0;
import jade.client.module.render.blockesp.BlockEspParser;
import jade.client.setting.BlockColorListSetting;
import jade.client.setting.BlockListSetting;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class NkDyzD {
   private NkDyzD() {
   }

   public static List<NkDyzD$1> buildEntries(BlockListSetting var0) {
      ArrayList var1 = new ArrayList();

      for (String var3 : var0.getEntries()) {
         List var4 = resolveItemStacks(var3);
         Integer var5 = var0 instanceof BlockColorListSetting ? ((BlockColorListSetting)var0).getBlockColor(var3) : null;
         var1.add(new NkDyzD$1(var3, BlockEspParser.getIconDisplayName(var3), BlockEspParser.getIconStack(var3), var4, var5));
      }

      return var1;
   }

   private static List<ItemStack> resolveItemStacks(String var0) {
      if (!BlockEspParser.isWildcardPattern(var0)) {
         return null;
      } else {
         List var1 = BlockEspParser.NsGry(BlockEspParser.getBaseRegistryName(var0));
         if (var1 != null && !var1.isEmpty()) {
            ArrayList var2 = new ArrayList();

            for (BlockEspParser$0 var4 : (java.lang.Iterable<BlockEspParser$0>) (java.lang.Iterable<?>) (var1)) {
               var2.add(var4.createItemStack());
            }

            return var2;
         } else {
            return null;
         }
      }
   }
}
