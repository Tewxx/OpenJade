package jade.deps.loader107;

final class StatsLookupService$1 {
   private final HypixelPlayerStats stats;
   private final long expiresAt;

   StatsLookupService$1(HypixelPlayerStats var1, long var2) {
      this.stats = var1;
      this.expiresAt = var2;
   }

   static long getExpiresAt(StatsLookupService$1 var0) {
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
               return var0.expiresAt;
         }
      }
   }

   static HypixelPlayerStats getStats(StatsLookupService$1 var0) {
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
               return var0.stats;
         }
      }
   }
}
