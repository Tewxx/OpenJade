// Jade recovery: original class: jade.deps.eLz.oMKrN7D
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.profiles.ProfileManager;
import java.util.HashMap;
import java.util.Map;

public final class CategoryModuleRefresher {
   private CategoryModuleRefresher() {
   }

   public static boolean refreshCategoryModules(CategoryComponent var0, boolean var1, boolean var2) {
      HashMap var3 = new HashMap();

      for (ModuleComponent var5 : var0.moduleComponents) {
         if (var5.module != null) {
            var3.put(var5.module.getName(), var5.Nqe);
         }
      }

      var0.moduleComponents.clear();
      var0.headerHeight = 13.0F;
      float var7 = var0.headerHeight + 3.0F;
      if (var1) {
         for (Module var6 : Jade.getModuleManager().getModulesInCategory(var0.category)) {
            pfHz(var0, var3, var6, var7);
            var7 += 16.0F;
         }
      } else if (var0.category == Category.profiles && var2) {
         pfHz(var0, var3, new ProfileManager(), var7);
         if (Jade.configManager == null) {
            return false;
         }

         for (ConfigEntry var10 : Jade.configManager.profiles) {
            var7 += 16.0F;
            pfHz(var0, var3, var10.getProfile(), var7);
         }
      }

      return true;
   }

   private static void pfHz(CategoryComponent var0, Map<String, Boolean> var1, Module var2, float var3) {
      ModuleComponent var4 = new ModuleComponent(var2, var0, var3);
      var4.setExpanded(Boolean.TRUE.equals(var1.get(var2.getName())));
      var0.moduleComponents.add(var4);
   }
}
