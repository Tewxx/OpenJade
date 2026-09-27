// Jade recovery: original class: jade.deps.eLz.SqO4CW$2
package jade.client.module.render;

import net.minecraft.entity.item.EntityItem;

public final class ItemESP$2 {
   private EntityItem entityItem;
   private ItemESP$1 qXb;
   private String GMdy;

   ItemESP$2() {
   }

   private void populate(EntityItem var1, ItemESP$1 var2, String var3) {
      this.entityItem = var1;
      this.qXb = var2;
      this.GMdy = var3;
   }

   public static EntityItem getEntityItem(ItemESP$2 var0) {
      return var0.entityItem;
   }

   public static String getItemKey(ItemESP$2 var0) {
      return var0.GMdy;
   }

   public static ItemESP$1 ZEth(ItemESP$2 var0) {
      return var0.qXb;
   }

   public static void populateEntry(ItemESP$2 var0, EntityItem var1, ItemESP$1 var2, String var3) {
      var0.populate(var1, var2, var3);
   }
}
