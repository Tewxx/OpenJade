package jade.deps.loader107;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

final class StatsLookupService$4 {
   private final UUID playerId;
   private final Object socket;
   private final long createdAt = System.currentTimeMillis();
   private final ConcurrentLinkedQueue<StatsLookupService$2> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
   private boolean completed;
   private HypixelPlayerStats stats;
   private String failureReason;

   StatsLookupService$4(UUID var1, StatsLookupService$2 var2, Object var3) {
      this.playerId = var1;
      this.socket = var3;
      this.addCallback(var2);
   }

   private synchronized void addCallback(StatsLookupService$2 var1) {
      if (var1 != null) {
         if (this.completed) {
            if (this.failureReason == null) {
               var1.onStatsReceived(this.stats);
            } else {
               var1.onStatsFailed(this.failureReason);
            }
         } else {
            if (!this.concurrentLinkedQueue.contains(var1)) {
               this.concurrentLinkedQueue.add(var1);
            }
         }
      }
   }

   private synchronized void completeWithStats(HypixelPlayerStats var1) {
      if (!this.completed) {
         this.completed = true;
         this.stats = var1;

         StatsLookupService$2 var2;
         while ((var2 = this.concurrentLinkedQueue.poll()) != null) {
            var2.onStatsReceived(var1);
         }
      }
   }

   private synchronized void completeWithFailure(String var1) {
      if (!this.completed) {
         this.completed = true;
         this.failureReason = var1;

         StatsLookupService$2 var2;
         while ((var2 = this.concurrentLinkedQueue.poll()) != null) {
            var2.onStatsFailed(var1);
         }
      }
   }

   static void attachCallback(StatsLookupService$4 var0, StatsLookupService$2 var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               var0.addCallback(var1);
               return;
         }
      }
   }

   static UUID getPlayerId(StatsLookupService$4 var0) {
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
               return var0.playerId;
         }
      }
   }

   static void deliverStats(StatsLookupService$4 var0, HypixelPlayerStats var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               var0.completeWithStats(var1);
               return;
         }
      }
   }

   static Object getSocket(StatsLookupService$4 var0) {
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
               return var0.socket;
         }
      }
   }

   static long getCreatedAt(StatsLookupService$4 var0) {
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
               return var0.createdAt;
         }
      }
   }

   static void deliverFailure(StatsLookupService$4 var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               var0.completeWithFailure(var1);
               return;
         }
      }
   }
}
