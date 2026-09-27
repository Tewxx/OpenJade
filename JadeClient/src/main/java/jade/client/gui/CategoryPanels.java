// Jade recovery: original class: jade.deps.eLz.gzRud90
package jade.client.gui;

import jade.client.common.CategoryComponent;
import jade.client.common.ModuleComponent;
import jade.client.module.Category;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;

public final class CategoryPanels {
   private CategoryPanels() {
   }

   public static ArrayList<CategoryComponent> TXIYOn() {
      ArrayList var0 = new ArrayList();
      int var1 = 5;

      for (Category var5 : Category.values()) {
         CategoryComponent var6 = new CategoryComponent(var5);
         var6.setPanelY(var1, false);
         var0.add(var6);
         var1 += 20;
      }

      return var0;
   }

   public static List<CategoryComponent> sortByInsertionOrder(List<CategoryComponent> var0) {
      ArrayList var1 = new ArrayList(var0);
      Collections.sort(var1, Comparator.comparingLong(CategoryPanels::getInsertionOrder));
      return var1;
   }

   public static List<CategoryComponent> sortByReverseInsertionOrder(List<CategoryComponent> var0) {
      ArrayList var1 = new ArrayList(var0);
      Collections.sort(var1, CategoryPanels::compareByReverseInsertionOrder);
      return var1;
   }

   public static CategoryComponent findHoveredPanel(List<CategoryComponent> var0, int var1, int var2) {
      for (int var3 = var0.size() - 1; var3 >= 0; var3--) {
         CategoryComponent var4 = (CategoryComponent)var0.get(var3);
         if (var4.DHEXX(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   public static void drawPanels(List<CategoryComponent> var0, FontRenderer var1, int var2, int var3) {
      List var4 = sortByInsertionOrder(var0);
      CategoryComponent var5 = findHoveredPanel(var4, var2, var3);

      for (CategoryComponent var7 : (java.lang.Iterable<CategoryComponent>) (java.lang.Iterable<?>) (var4)) {
         var7.render(var1);
         var7.updateDrag(var2, var3, var7 == var5);

         for (Component var9 : var7.getModuleComponents()) {
            var9.updateLayout(var2, var3);
         }
      }
   }

   public static void dispatchKeyTyped(List<CategoryComponent> var0, char var1, int var2) {
      for (CategoryComponent var4 : var0) {
         if (var4.isExpanded() && !var4.getModuleComponents().isEmpty()) {
            for (Component var6 : var4.getModuleComponents()) {
               var6.keyTyped(var1, var2);
            }
         }
      }
   }

   public static void assignModifierKey(List<CategoryComponent> var0, int var1) {
      for (CategoryComponent var3 : var0) {
         for (ModuleComponent var5 : var3.getModuleComponents()) {
            for (Component var7 : var5.components) {
               if (var7 instanceof BindComponent) {
                  ((BindComponent)var7).assignScrollKey(var1);
               }
            }
         }
      }
   }

   public static void refreshSliders(List<CategoryComponent> var0) {
      for (CategoryComponent var2 : var0) {
         for (ModuleComponent var4 : var2.getModuleComponents()) {
            var4.refreshSliderValues();
         }
      }
   }

   public static void resetAllPanels(List<CategoryComponent> var0) {
      for (CategoryComponent var2 : var0) {
         var2.dragging = false;
         var2.mlvAc();

         for (Component var4 : var2.getModuleComponents()) {
            var4.resetEditingState();
         }
      }
   }

   private static int compareByReverseInsertionOrder(CategoryComponent var0, CategoryComponent var1) {
      return Long.compare(var1.Cs2, var0.Cs2);
   }

   private static long getInsertionOrder(CategoryComponent var0) {
      return var0.Cs2;
   }
}
