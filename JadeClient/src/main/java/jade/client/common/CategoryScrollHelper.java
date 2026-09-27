// Jade recovery: original class: jade.deps.eLz.P95QdORm9O
package jade.client.common;

import jade.client.gui.Component;
import jade.client.setting.ItemListComponent;
import java.util.List;
import java.util.function.BooleanSupplier;

public final class CategoryScrollHelper {
   private CategoryScrollHelper() {
   }

   public static boolean shouldHandleScroll(boolean var0, boolean var1, float var2, float var3, BooleanSupplier var4) {
      return var0 && var1 ? Float.isNaN(var2) || Float.isNaN(var3) || !var4.getAsBoolean() : false;
   }

   public static float getScrollAmount(int var0) {
      return 20.0F * (var0 / 120.0F);
   }

   public static boolean isHoveringListComponent(List<ModuleComponent> var0, float var1, float var2) {
      for (ModuleComponent var4 : var0) {
         for (Component var6 : var4.components) {
            if (var4.Nqe && var4.isComponentVisible(var6) && isScrollAreaHovered(var6, var1, var2)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean isScrollAreaHovered(Component var0, float var1, float var2) {
      if (var0 instanceof ItemListComponent) {
         return ((ItemListComponent)var0).isMouseOverAnyPart(var1, var2);
      } else {
         return var0 instanceof PlayerListComponent ? ((PlayerListComponent)var0).isScrollListHovered(var1, var2) : var0 instanceof StringListComponent && ((StringListComponent)var0).isScrollListHovered(var1, var2);
      }
   }
}
