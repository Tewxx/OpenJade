// Jade recovery: original class: jade.deps.eLz.iqfjC3trqM
package jade.client.module.minigames.opsec;

public final class QuickBuyRecoveryPlanner {
   private QuickBuyRecoveryPlanner() {
   }

   public static QuickBuyRecoveryPlanner$0 decideFailureAction(boolean var0, boolean var1, boolean var2) {
      if (!var1 && (!var0 || var2)) {
         return var0 ? QuickBuyRecoveryPlanner$0.RESTORE : QuickBuyRecoveryPlanner$0.STOP;
      } else {
         return QuickBuyRecoveryPlanner$0.KEEP_RECOVERY;
      }
   }

   public static QuickBuyRecoveryPlanner$0 decideCancelAction(boolean var0, boolean var1) {
      if (var1) {
         return QuickBuyRecoveryPlanner$0.KEEP_RECOVERY;
      } else {
         return var0 ? QuickBuyRecoveryPlanner$0.RESTORE : QuickBuyRecoveryPlanner$0.STOP;
      }
   }
}
