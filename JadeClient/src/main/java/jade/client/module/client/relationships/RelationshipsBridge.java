// Jade recovery: original class: jade.deps.eLz.CfZT1EPpG
package jade.client.module.client.relationships;

import jade.client.Jade;
import jade.client.common.RelationManager;

public final class RelationshipsBridge {
   private RelationshipsBridge() {
   }

   public static boolean isRelationshipsEnabled() {
      RelationManager var0 = Jade.relationManager;
      return var0 == null || var0.isActive();
   }

   public static void PnqK(boolean var0) {
      if (Jade.relationManager != null) {
         Jade.relationManager.setActive(var0);
      }
   }

   public static void setMiddleClickFriends(boolean var0) {
      if (Jade.relationManager != null) {
         Jade.relationManager.ADaZ8(var0);
      }
   }
}
