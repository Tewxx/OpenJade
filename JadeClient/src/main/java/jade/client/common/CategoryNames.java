// Jade recovery: original class: jade.deps.eLz.yMpO4zb
package jade.client.common;

import jade.client.module.Category;

public final class CategoryNames {
   private CategoryNames() {
   }

   public static Category[] getOrderedCategories() {
      return new Category[]{
         Category.combat,
         Category.player,
         Category.movement,
         Category.render,
         Category.minigames,
         Category.other,
         Category.friends,
         Category.profiles,
         Category.themes
      };
   }

   public static String getDisplayName(Category var0) {
      switch (var0) {
         case movement:
            return "Move";
         case render:
            return "Visuals";
         case other:
            return "Misc";
         case friends:
            return "Friends";
         case profiles:
            return "Configs";
         case themes:
            return "Themes";
         case minigames:
            return "Minigames";
         case client:
            return "Settings";
         case player:
            return "Player";
         case combat:
         default:
            return "Combat";
      }
   }
}
