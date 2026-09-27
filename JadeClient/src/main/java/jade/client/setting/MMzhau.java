// Jade recovery: original class: jade.deps.eLz.MMzhau
package jade.client.setting;

import jade.client.common.CategoryComponent;
import jade.client.common.ModuleComponent;
import jade.client.gui.ClickGui;
import jade.client.module.Module;

public final class MMzhau {
   private MMzhau() {
   }

   public static void JZYRBC(Module var0) {
      if (ClickGui.categoryPanels != null) {
         for (CategoryComponent var2 : ClickGui.categoryPanels) {
            if (var2.category == var0.getCategory()) {
               ModuleComponent var3 = var2.moduleComponents.stream().filter((recoveredArg0) -> MMzhau.matchesModule(var0, (jade.client.common.ModuleComponent) recoveredArg0)).findFirst().orElse(null);
               if (var3 != null) {
                  var3.rebuildSettingComponents();
               }
            }
         }
      }
   }

   private static boolean matchesModule(Module var0, ModuleComponent var1) {
      return var1.module.getName().equals(var0.getName());
   }
}
