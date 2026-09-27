package jade.deps.loader107;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.websocket.enums.ReadyState;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLSocketFactory;

public final class StatsLookupService {
   private static final String STATS_SOCKET_URL = null;
   private static final long CACHE_TTL_MILLIS = 900000L;
   private static final long RETRY_AFTER_MILLIS = 12000L;
   private static final long REQUEST_TIMEOUT_MILLIS = 90000L;
   private static final long SOCKET_REFRESH_MILLIS = 10000L;
   private static final StatsLookupService INSTANCE = new StatsLookupService();
   private final Map<UUID, StatsLookupService$1> statsCache = new ConcurrentHashMap<>();
   private final Map<UUID, String> failureReasons = new ConcurrentHashMap<>();
   private final Map<Integer, StatsLookupService$4> pendingRequests = new ConcurrentHashMap<>();
   private final Map<UUID, Long> requestCooldowns = new ConcurrentHashMap<>();
   private final Set<UUID> inFlightPlayers = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final LinkedList<Long> linkedList = new LinkedList<>();
   private final AtomicInteger requestIdCounter = new AtomicInteger(1);
   private volatile StatsLookupService$3 socket;
   private volatile long nextConnectAt;
   private volatile String lastSocketMessage;

   private StatsLookupService() {
   }

   public static StatsLookupService getInstance() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return INSTANCE;
         }
      }
   }

   public HypixelPlayerStats getCachedStats(UUID var1) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               StatsLookupService$1 var2 = this.statsCache.get(var1);
               return var2 != null && StatsLookupService$1.getExpiresAt(var2) > System.currentTimeMillis()
                  ? StatsLookupService$1.getStats(var2)
                  : null;
         }
      }
   }

   public void clearPlayer(UUID var1) {
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
               if (var1 == null) {
                  return;
               } else {
                  this.statsCache.remove(var1);
                  this.failureReasons.remove(var1);
                  this.requestCooldowns.remove(var1);
                  return;
               }
         }
      }
   }

   public boolean requestManualStats(UUID var1) {
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
               return this.resolveStats(var1, StatsLookupService$5.MANUAL, null);
         }
      }
   }

   public String pollFailureReason(UUID var1) {
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
               return var1 == null ? null : this.failureReasons.remove(var1);
         }
      }
   }

   public void requestStatsForGame(UUID var1) {
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
               this.resolveStats(var1, StatsLookupService$5.GAME, null);
               return;
         }
      }
   }

   public boolean requestStats(UUID var1, StatsLookupService$5 var2, StatsLookupService$2 var3) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               HypixelPlayerStats var4 = this.getCachedStats(var1);
               if (var4 != null) {
                  if (var3 != null) {
                     var3.onStatsReceived(var4);
                  }

                  return true;
               } else {
                  return this.resolveStats(var1, var2 == null ? StatsLookupService$5.GAME : var2, var3);
               }
         }
      }
   }

   private boolean resolveStats(UUID var1, StatsLookupService$5 var2, StatsLookupService$2 var3) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               if (var1 == null) {
                  return false;
               } else {
                  this.expireTimedOutRequests(System.currentTimeMillis());
                  if (this.getCachedStats(var1) != null) {
                     if (var3 != null) {
                        var3.onStatsReceived(this.getCachedStats(var1));
                     }

                     return true;
                  } else {
                     StatsLookupService$4 var4 = this.findPendingRequest(var1);
                     if (var4 != null) {
                        StatsLookupService$4.attachCallback(var4, var3);
                        return true;
                     } else {
                        this.inFlightPlayers.remove(var1);
                        return this.sendStatsRequest(var1, var2, var3);
                     }
                  }
               }
         }
      }
   }

   private boolean sendStatsRequest(UUID var1, StatsLookupService$5 var2, StatsLookupService$2 var3) {
      byte var10 = 0;

      while (true) {
         switch (var10) {
            case 0:

               var10 = 1;
               break;
            case 1:
               var10 = 2;
               break;
            default:
               if (var1 == null) {
                  return false;
               } else {
                  long var4 = System.currentTimeMillis();
                  Long var6 = this.requestCooldowns.get(var1);
                  if (var6 != null && var4 - var6 < 12000L) {
                     return false;
                  } else {
                     this.ensureSocketConnected(var4);
                     StatsLookupService$3 var7 = this.socket;
                     if (var7 == null || !var7.isOpen() || !StatsLookupService$3.isAuthenticated(var7)) {
                        String var11 = this.lastSocketMessage;
                        if (var11 != null) {
                           this.failureReasons.put(var1, var11);
                        }

                        return false;
                     } else if (!this.allowRequest(var4)) {
                        this.failureReasons
                           .put(var1, "rate_limited");
                        if (var3 != null) {
                           var3.onStatsFailed("rate_limited");
                        }

                        return false;
                     } else {
                        int var8 = this.requestIdCounter.getAndIncrement();
                        this.pendingRequests.put(var8, new StatsLookupService$4(var1, var3, var7));
                        this.inFlightPlayers.add(var1);
                        this.failureReasons.remove(var1);
                        this.requestCooldowns.put(var1, var4);
                        JsonObject var9 = new JsonObject();
                        var9.addProperty(
                           "type",
                           "stats"
                        );
                        var9.addProperty("request_id", var8);
                        var9.addProperty("uuid", var1.toString());
                        var9.addProperty("source", var2.name());
                        var7.send(var9.toString());
                        return true;
                     }
                  }
               }
         }
      }
   }

   public void tick() {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               long var1 = System.currentTimeMillis();
               this.expireTimedOutRequests(var1);
               this.ensureSocketConnected(var1);
               return;
         }
      }
   }

   public void clearPlayerCaches() {
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
               this.statsCache.clear();
               this.failureReasons.clear();
               this.requestCooldowns.clear();
               return;
         }
      }
   }

   public void disconnect() {
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
               StatsLookupService$3 var1 = this.socket;
               this.socket = null;
               if (var1 != null) {
                  var1.close();
               }

               this.failAllPendingRequests("connection_closed");
               return;
         }
      }
   }

   public void reset() {
      this.disconnect();
      this.clearPlayerCaches();
      this.pendingRequests.clear();
      this.inFlightPlayers.clear();
      synchronized (this.linkedList) {
         this.linkedList.clear();
      }

      this.requestIdCounter.set(1);
      this.nextConnectAt = 0L;
      this.lastSocketMessage = null;
   }

   private synchronized void ensureSocketConnected(long var1) {
      StatsLookupService$3 var3 = this.socket;
      if (var3 != null && var3.isOpen()) {
         if (StatsLookupService$3.isAuthenticated(var3)
            || StatsLookupService$3.getConnectedAt(var3) <= 0L
            || var1 - StatsLookupService$3.getConnectedAt(var3) <= 10000L) {
            return;
         }

         this.socket = null;
         var3.close();
         this.nextConnectAt = 0L;
      } else if (var3 != null && var3.getReadyState() == ReadyState.NOT_YET_CONNECTED) {
         return;
      }

      if (var1 >= this.nextConnectAt) {
         try {
            StatsLookupService$3 var4 = new StatsLookupService$3(
               this, new URI("wss://redacted/overlay")
            );
            SSLSocketFactory var5 = TlsSockets.getPlatformSslSocketFactory();
            if (var5 != null) {
               var4.setSocketFactory(var5);
            }

            this.socket = var4;
            this.nextConnectAt = var1 + 5000L;
            this.lastSocketMessage = null;
            var4.connect();
         } catch (Throwable var6) {
            this.nextConnectAt = var1 + 15000L;
         }
      }
   }

   private void handleSocketMessage(String var1) {

      try {
         JsonObject var2 = new JsonParser().parse(var1).getAsJsonObject();
         String var3 = var2.has("type")
            ? var2.get("type").getAsString()
            : "";
         if ("stats_result".equals(var3)) {
            this.handleStatsResponse(var2, true);
         } else if ("stats_error".equals(var3)) {
            this.handleStatsResponse(var2, false);
         }
      } catch (Exception var4) {
      }
   }

   private void handleStatsResponse(JsonObject var1, boolean var2) {
      int var3 = var1.has("request_id")
         ? var1.get("request_id").getAsInt()
         : 0;
      StatsLookupService$4 var4 = this.pendingRequests.get(var3);
      if (var4 != null) {
         HypixelPlayerStats var5 = null;
         if (var2) {
            try {
               var5 = HypixelPlayerStats.fromJson(var1);
            } catch (Throwable var8) {
               if (this.pendingRequests.remove(var3, var4)) {
                  this.inFlightPlayers.remove(StatsLookupService$4.getPlayerId(var4));
                  this.completeRequestWithFailure(var4, "invalid_response");
               }

               return;
            }
         }

         if (this.pendingRequests.remove(var3, var4)) {
            UUID var6 = StatsLookupService$4.getPlayerId(var4);
            this.inFlightPlayers.remove(var6);
            if (var2) {
               this.failureReasons.remove(var6);
               this.statsCache.put(var6, new StatsLookupService$1(var5, System.currentTimeMillis() + 900000L));
               StatsLookupService$4.deliverStats(var4, var5);
            } else {
               String var7 = var1.has("reason")
                  ? var1.get("reason").getAsString()
                  : "upstream_error";
               this.completeRequestWithFailure(var4, var7);
            }
         }
      }
   }

   private void failRequestsForSocket(Object var1, String var2) {
      byte var6 = 0;

      while (true) {
         switch (var6) {
            case 0:

               var6 = 1;
               break;
            case 1:
               var6 = 2;
               break;
            default:

               for (Entry var4 : this.pendingRequests.entrySet()) {
                  StatsLookupService$4 var5 = (StatsLookupService$4)var4.getValue();
                  if (StatsLookupService$4.getSocket(var5) == var1
                     && this.pendingRequests.remove(var4.getKey(), var5)) {
                     this.inFlightPlayers.remove(StatsLookupService$4.getPlayerId(var5));
                     this.completeRequestWithFailure(var5, var2);
                  }
               }

               return;
         }
      }
   }

   private void failAllPendingRequests(String var1) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:

               for (Entry var3 : this.pendingRequests.entrySet()) {
                  StatsLookupService$4 var4 = (StatsLookupService$4)var3.getValue();
                  if (this.pendingRequests.remove(var3.getKey(), var4)) {
                     this.inFlightPlayers.remove(StatsLookupService$4.getPlayerId(var4));
                     this.completeRequestWithFailure(var4, var1);
                  }
               }

               return;
         }
      }
   }

   private StatsLookupService$4 findPendingRequest(UUID var1) {
      byte var4 = 0;

      while (true) {
         switch (var4) {
            case 0:

               var4 = 1;
               break;
            case 1:
               var4 = 2;
               break;
            default:

               for (StatsLookupService$4 var3 : this.pendingRequests.values()) {
                  if (StatsLookupService$4.getPlayerId(var3).equals(var1)) {
                     return var3;
                  }
               }

               return null;
         }
      }
   }

   private void expireTimedOutRequests(long var1) {
      byte var6 = 0;

      while (true) {
         switch (var6) {
            case 0:

               var6 = 1;
               break;
            case 1:
               var6 = 2;
               break;
            default:

               for (Entry var4 : this.pendingRequests.entrySet()) {
                  StatsLookupService$4 var5 = (StatsLookupService$4)var4.getValue();
                  if (var1 - StatsLookupService$4.getCreatedAt(var5) > 90000L
                     && this.pendingRequests.remove(var4.getKey(), var5)) {
                     this.inFlightPlayers.remove(StatsLookupService$4.getPlayerId(var5));
                     this.requestCooldowns.remove(StatsLookupService$4.getPlayerId(var5));
                     this.completeRequestWithFailure(var5, "request_timeout");
                  }
               }

               return;
         }
      }
   }

   private void completeRequestWithFailure(StatsLookupService$4 var1, String var2) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               this.failureReasons
                  .put(
                     StatsLookupService$4.getPlayerId(var1),
                     var2 == null ? "upstream_error" : var2
                  );
               StatsLookupService$4.deliverFailure(var1, var2);
               return;
         }
      }
   }

   private synchronized boolean allowRequest(long var1) {
      long var3 = var1 - 60000L;

      while (!this.linkedList.isEmpty() && this.linkedList.getFirst() <= var3) {
         this.linkedList.removeFirst();
      }

      if (this.linkedList.size() >= 50) {
         return false;
      } else {
         this.linkedList.addLast(var1);
         return true;
      }
   }

   static String storeLastSocketMessage(StatsLookupService var0, String var1) {
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
               return var0.lastSocketMessage = var1;
         }
      }
   }

   static void dispatchSocketMessage(StatsLookupService var0, String var1) {
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
               var0.handleSocketMessage(var1);
               return;
         }
      }
   }

   static void dispatchSocketFailure(StatsLookupService var0, Object var1, String var2) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               var0.failRequestsForSocket(var1, var2);
               return;
         }
      }
   }

   static StatsLookupService$3 getActiveSocket(StatsLookupService var0) {
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

   static StatsLookupService$3 setActiveSocket(
      StatsLookupService var0, StatsLookupService$3 var1
   ) {
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
               return var0.socket = var1;
         }
      }
   }

   static long setNextConnectAt(StatsLookupService var0, long var1) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               return var0.nextConnectAt = var1;
         }
      }
   }

   static {
   }
}
