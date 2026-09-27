// Jade recovery: original class: jade.deps.eLz.c24f4B$2
package jade.client.gui;

import jade.client.common.PlayerApi$2;

public final class LarpScreen$2 {
   private final int requestId;
   private final String eOpwZ;
   private final PlayerApi$2 resolvedPlayer;

   LarpScreen$2(int var1, String var2, PlayerApi$2 var3) {
      this.requestId = var1;
      this.eOpwZ = var2;
      this.resolvedPlayer = var3;
   }

   public static int getRequestId(LarpScreen$2 var0) {
      return var0.requestId;
   }

   public static PlayerApi$2 getResolvedPlayer(LarpScreen$2 var0) {
      return var0.resolvedPlayer;
   }

   public static String getRequestedName(LarpScreen$2 var0) {
      return var0.eOpwZ;
   }
}
