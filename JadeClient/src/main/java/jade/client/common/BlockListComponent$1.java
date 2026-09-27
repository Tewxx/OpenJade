// Jade recovery: original class: jade.deps.eLz.il6qjk$1
package jade.client.common;

import jade.client.setting.ItemListComponent$0;

public final class BlockListComponent$1 extends ItemListComponent$0 {
   private final Integer color;

   BlockListComponent$1(NkDyzD$1 var1) {
      super(var1.blockName, var1.AjT, var1.itemStack, var1.itemStacks);
      this.color = var1.FfG53;
   }

   public static Integer getColor(BlockListComponent$1 var0) {
      return var0.color;
   }
}
