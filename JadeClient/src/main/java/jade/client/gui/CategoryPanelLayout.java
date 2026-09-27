// Jade recovery: original class: jade.deps.eLz.v9s4PtmE
package jade.client.gui;

import jade.client.common.CategoryComponent;
import jade.client.module.Category;
import java.util.List;

public final class CategoryPanelLayout {
   private CategoryPanelLayout() {
   }

   public static void jjdWycA(List<CategoryComponent> var0, int var1, int var2, boolean var3) {
      for (CategoryComponent var5 : var0) {
         var5.setHoverOrigins(var1, var2);
      }

      if (var3) {
         for (CategoryComponent var8 : var0) {
            var8.feedOnc();
         }
      }

      for (CategoryComponent var9 : var0) {
         if (var9.category == Category.profiles) {
            var9.close(true);
         } else {
            var9.open();
         }
      }
   }

   public static void relayoutPanels(List<CategoryComponent> var0, int var1, int var2) {
      for (CategoryComponent var4 : var0) {
         var4.setHoverOrigins(var1, var2);
         var4.feedOnc();
      }
   }
}
