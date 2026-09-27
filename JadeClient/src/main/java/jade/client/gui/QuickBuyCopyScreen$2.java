// Jade recovery: original class: jade.deps.eLz.aVWVANRws$2
package jade.client.gui;

import jade.client.common.PlayerApi$2;

public final class QuickBuyCopyScreen$2 {
   private final int requestId;
   private final String requestedName;
   private final PlayerApi$2 DQuVbh;

   QuickBuyCopyScreen$2(int var1, String var2, PlayerApi$2 var3) {
      this.requestId = var1;
      this.requestedName = var2;
      this.DQuVbh = var3;
   }

   public static int getRequestId(QuickBuyCopyScreen$2 var0) {
      return var0.requestId;
   }

   public static PlayerApi$2 getPlayerApi(QuickBuyCopyScreen$2 var0) {
      return var0.DQuVbh;
   }

   public static String getRequestedName(QuickBuyCopyScreen$2 var0) {
      return var0.requestedName;
   }
}
