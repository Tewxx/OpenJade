// Jade recovery: original class: jade.deps.eLz.qLDpSJ
package jade.client.gui;

import jade.client.common.CategoryComponent;
import jade.client.common.ModuleComponent;
import java.util.List;

public final class CategoryPanelMouseHandler {
   private CategoryPanelMouseHandler() {
   }

   public static void handleMouseClick(List<CategoryComponent> var0, int var1, int var2, int var3) {
      CategoryComponent var4 = FCPs(CategoryPanels.sortByReverseInsertionOrder(var0), var1, var2);
      if (var4 != null) {
         var4.bGdbO();
      }

      if (var3 == 0) {
         beginPanelDrag(var0, var4, var1, var2);
      }

      if (var3 == 1 && var4 != null && var4.isMouseOverPanel(var1, var2)) {
         var4.zDmaqhA(!var4.isExpanded());
      }

      if (var4 != null && var4.isExpanded() && !var4.getModuleComponents().isEmpty() && !var4.isMouseOverPanel(var1, var2)) {
         for (ModuleComponent var6 : var4.getModuleComponents()) {
            if (var6.mouseClicked(var1, var2, var3)) {
               return;
            }
         }
      }
   }

   public static void handleMouseRelease(List<CategoryComponent> var0, int var1, int var2, int var3) {
      if (var3 == 0) {
         for (CategoryComponent var5 : var0) {
            var5.setDragging(false);
            if (var5.isExpanded() && !var5.getModuleComponents().isEmpty()) {
               for (Component var7 : var5.getModuleComponents()) {
                  var7.mouseReleased(var1, var2, var3);
               }
            }
         }
      }
   }

   private static CategoryComponent FCPs(List<CategoryComponent> var0, int var1, int var2) {
      for (CategoryComponent var4 : var0) {
         if (var4.DHEXX(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   private static void beginPanelDrag(List<CategoryComponent> var0, CategoryComponent var1, int var2, int var3) {
      for (CategoryComponent var5 : var0) {
         var5.setDragging(false);
      }

      if (var1 != null && var1.isOverHeader(var2, var3)) {
         var1.setDragging(true);
         var1.dragOffsetX = var2 - var1.EWZxNc();
         var1.dragOffsetY = var3 - var1.getPanelY();
         var1.dragging = true;
      }
   }
}
