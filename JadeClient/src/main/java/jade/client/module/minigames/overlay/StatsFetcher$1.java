// Jade recovery: original class: jade.deps.eLz.BMK6I1Py$1
package jade.client.module.minigames.overlay;

public final class StatsFetcher$1 {
   private final StatsFetcher$2 TbebpW;
   private final long cachedAtMillis;

   StatsFetcher$1(StatsFetcher$2 var1, long var2) {
      this.TbebpW = var1;
      this.cachedAtMillis = var2;
   }

   public static long getCachedAtMillis(StatsFetcher$1 var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return var0.cachedAtMillis;
         }
      }
   }

   public static StatsFetcher$2 JIPpa(StatsFetcher$1 var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return var0.TbebpW;
         }
      }
   }
}
