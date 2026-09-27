// Jade recovery: original class: jade.deps.eLz.GPZzP07qvU
package jade.client.common;

import jade.client.Jade;
import jade.client.gui.ClickGui;
import jade.client.gui.Component;
import jade.client.module.Category;
import jade.client.module.client.Relationships;
import jade.client.module.render.ESP;

public final class RelationSyncHelper {
   private RelationSyncHelper() {
   }

   public static void KQZFyJ(RelationStore var0) {
      ClientUtils.friends.clear();
      ClientUtils.friends.addAll(var0.hdnH(RelationManager$1.FRIEND));
      ClientUtils.enemies.clear();
      ClientUtils.enemies.addAll(var0.hdnH(RelationManager$1.ENEMY));
   }

   public static void LIxB(boolean var0, boolean var1) {
      Relationships var2 = Jade.getModuleManager().getModule(Relationships.class);
      if (var2 != null) {
         var2.booleanSetting.setToggled(var1);
         if (var0 != var2.isEnabled()) {
            if (var0) {
               var2.enable();
            } else {
               var2.disable();
            }
         }
      }
   }

   public static void refreshEspEntries() {
      ESP var0 = Jade.getModuleManager().getModule(ESP.class);
      if (var0 != null) {
         var0.refreshTrackedColors();
      }
   }

   public static void refreshPlayerListComponents() {
      Relationships var0 = Jade.getModuleManager().getModule(Relationships.class);
      if (Jade.clickGui != null && ClickGui.categoryPanels != null && var0 != null) {
         ModuleComponent var1 = findRelationshipsComponent(var0);
         if (var1 != null) {
            for (Component var3 : var1.components) {
               if (var3 instanceof PlayerListComponent) {
                  ((PlayerListComponent)var3).refreshScrollState();
               }
            }

            var1.invalidateCategoryLayout();
         }
      }
   }

   private static ModuleComponent findRelationshipsComponent(Relationships var0) {
      for (CategoryComponent var2 : ClickGui.categoryPanels) {
         if (var2.category == Category.client) {
            for (ModuleComponent var4 : var2.moduleComponents) {
               if (var4.module == var0) {
                  return var4;
               }
            }
         }
      }

      return null;
   }
}
