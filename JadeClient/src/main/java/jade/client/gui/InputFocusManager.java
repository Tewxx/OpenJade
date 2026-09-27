// Jade recovery: original class: jade.deps.eLz.t0u6HgN
package jade.client.gui;

import jade.client.common.CategoryComponent;
import jade.client.common.ModuleComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiTextField;

public final class InputFocusManager {
   private InputFocusManager() {
   }

   public static FocusableTextInput findFocusedInput(List<CategoryComponent> var0) {
      FocusableTextInput var1 = null;

      for (Component var3 : collectAllComponents(var0)) {
         if (var3 instanceof FocusableTextInput) {
            FocusableTextInput var4 = (FocusableTextInput)var3;
            if (var4.isFocused()) {
               if (var1 == null) {
                  var1 = var4;
               } else {
                  var4.unfocus();
               }
            }
         }
      }

      return var1;
   }

   public static void unfocusOthers(List<CategoryComponent> var0, int var1, int var2) {
      FocusableTextInput var3 = findFocusedInputAt(var0, var1, var2);

      for (Component var5 : collectAllComponents(var0)) {
         if (var5 instanceof FocusableTextInput) {
            FocusableTextInput var6 = (FocusableTextInput)var5;
            if (var6 != var3 && var6.isFocused()) {
               var6.unfocus();
            }
         }
      }
   }

   public static boolean CXaRpt1(List<CategoryComponent> var0) {
      for (Component var2 : collectAllComponents(var0)) {
         if (var2 instanceof BindComponent && ((BindComponent)var2).listening) {
            return true;
         }

         if (var2 instanceof FocusableTextInput && ((FocusableTextInput)var2).isFocused()) {
            return true;
         }
      }

      return false;
   }

   public static boolean isAnyInputFocused(List<CategoryComponent> var0, GuiTextField var1) {
      if (var1 != null && var1.isFocused()) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         for (Component var3 : collectAllComponents(var0)) {
            if (var3 instanceof FocusableTextInput && ((FocusableTextInput)var3).isFocused()) {
               return true;
            }
         }

         return false;
      }
   }

   private static FocusableTextInput findFocusedInputAt(List<CategoryComponent> var0, int var1, int var2) {
      ArrayList var3 = new ArrayList(var0);
      Collections.sort(var3, InputFocusManager::compareByReverseInsertionOrder);

      for (CategoryComponent var5 : (java.lang.Iterable<CategoryComponent>) (java.lang.Iterable<?>) (var3)) {
         if (var5.isExpanded() && var5.DHEXX(var1, var2)) {
            for (ModuleComponent var7 : var5.getModuleComponents()) {
               for (Component var9 : var7.components) {
                  if (var9 instanceof FocusableTextInput) {
                     FocusableTextInput var10 = (FocusableTextInput)var9;
                     if (var10.isFocused() && var10.isMouseOver(var1, var2)) {
                        return var10;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static List<Component> collectAllComponents(List<CategoryComponent> var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         return var1;
      } else {
         for (CategoryComponent var3 : var0) {
            for (ModuleComponent var5 : var3.getModuleComponents()) {
               var1.addAll(var5.components);
            }
         }

         return var1;
      }
   }

   private static int compareByReverseInsertionOrder(CategoryComponent var0, CategoryComponent var1) {
      return Long.compare(var1.Cs2, var0.Cs2);
   }
}
