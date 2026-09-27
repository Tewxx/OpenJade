package jade.deps.loader107;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.websocket.enums.ReadyState;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;

public final class PlayerTrackerClient {
   private static final String TRACKER_ENDPOINT_URL = null;
   private static volatile PlayerTrackerClient instance = new PlayerTrackerClient();
   private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
   private final Map<String, PlayerTrackerClient$1> trackedPlayers = new ConcurrentHashMap<>();
   private volatile PlayerTrackerClient$2 trackerSocket;
   private volatile long nextReconnectAtMillis;

   private PlayerTrackerClient() {
      this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
         @Override
         public void run() {
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
                     PlayerTrackerClient.this.ensureConnected();
                     return;
               }
            }
         }
      }, 5L, 5L, TimeUnit.SECONDS);
   }

   public static PlayerTrackerClient getInstance() {
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
               return instance;
         }
      }
   }

   static synchronized void resetInstance() {
      instance.shutdown();
      instance = new PlayerTrackerClient();
   }

   public boolean isTracked(String var1) {
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
               return var1 != null && this.trackedPlayers.containsKey(normalizeUuid(var1));
         }
      }
   }

   public void trackPlayer(String var1, String var2) {
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
               if (var1 != null && !var1.trim().isEmpty()) {
                  String var3 = normalizeUuid(var1);
                  this.trackedPlayers.put(var3, new PlayerTrackerClient$1(var3, var2));
                  this.connectIfDue(System.currentTimeMillis());
                  PlayerTrackerClient$2 var4 = this.trackerSocket;
                  if (var4 != null && var4.isOpen() && PlayerTrackerClient$2.isAuthenticated(var4)) {
                     this.sendTrackMessage(var4, var3, var2);
                  }

                  return;
               } else {
                  return;
               }
         }
      }
   }

   public void untrackPlayer(String var1) {
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
               if (var1 != null && !var1.trim().isEmpty()) {
                  String var2 = normalizeUuid(var1);
                  this.trackedPlayers.remove(var2);
                  PlayerTrackerClient$2 var3 = this.trackerSocket;
                  if (var3 != null && var3.isOpen() && PlayerTrackerClient$2.isAuthenticated(var3)) {
                     JsonObject var4 = new JsonObject();
                     var4.addProperty(
                        "type",
                        "untrack"
                     );
                     var4.addProperty("uuid", var2);
                     var3.send(var4.toString());
                  }

                  if (this.trackedPlayers.isEmpty()) {
                     this.closeSocket();
                  }

                  return;
               } else {
                  return;
               }
         }
      }
   }

   public void ensureConnected() {
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
               if (!this.trackedPlayers.isEmpty()) {
                  this.connectIfDue(System.currentTimeMillis());
               }

               return;
         }
      }
   }

   private void closeSocket() {
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
               PlayerTrackerClient$2 var1 = this.trackerSocket;
               this.trackerSocket = null;
               if (var1 != null) {
                  var1.close();
               }

               return;
         }
      }
   }

   public void shutdown() {
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
               this.trackedPlayers.clear();
               this.closeSocket();
               this.scheduledExecutorService.shutdownNow();
               return;
         }
      }
   }

   private synchronized void connectIfDue(long var1) {
      PlayerTrackerClient$2 var3 = this.trackerSocket;
      if (var3 == null || !var3.isOpen() && var3.getReadyState() != ReadyState.NOT_YET_CONNECTED) {
         if (var1 >= this.nextReconnectAtMillis) {
            try {
               PlayerTrackerClient$2 var4 = new PlayerTrackerClient$2(
                  this, new URI("wss://redacted/tracker")
               );
               SSLSocketFactory var5 = TlsSockets.getPlatformSslSocketFactory();
               if (var5 != null) {
                  var4.setSocketFactory(var5);
               }

               this.trackerSocket = var4;
               this.nextReconnectAtMillis = var1 + 5000L;
               var4.connect();
            } catch (Throwable var6) {
               this.nextReconnectAtMillis = var1 + 15000L;
            }
         }
      }
   }

   private void sendTrackRequestsForAll(PlayerTrackerClient$2 var1) {
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

               for (PlayerTrackerClient$1 var3 : this.trackedPlayers.values()) {
                  this.sendTrackMessage(
                     var1,
                     PlayerTrackerClient$1.getUuid(var3),
                     PlayerTrackerClient$1.getUsername(var3)
                  );
               }

               return;
         }
      }
   }

   private void sendTrackMessage(PlayerTrackerClient$2 var1, String var2, String var3) {
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
               JsonObject var4 = new JsonObject();
               var4.addProperty(
                  "type",
                  "track"
               );
               var4.addProperty("uuid", normalizeUuid(var2));
               var4.addProperty("username", var3 == null ? "" : var3);
               var1.send(var4.toString());
               return;
         }
      }
   }

   private void handleServerMessage(String var1) {

      try {
         JsonObject var2 = new JsonParser().parse(var1).getAsJsonObject();
         String var3 = readStringField(var2, "type");
         if (!"playerJoined".equals(var3)
            && !"playerLeft".equals(var3)) {
            return;
         }

         String var4 = normalizeUuid(
            readStringField(var2, "uuid")
         );
         PlayerTrackerClient$1 var5 = this.trackedPlayers.get(var4);
         String var6 = readStringField(var2, "username");
         if (var6.isEmpty() && var5 != null) {
            var6 = PlayerTrackerClient$1.getUsername(var5);
         }

         if (var6 == null || var6.isEmpty()) {
            var6 = "Player";
         }

         String var7 = readStringField(var2, "lobby");
         if (var7.isEmpty()) {
            var7 = readStringField(var2, "lobbyId");
         }

         if (var7.isEmpty()) {
            var7 = readStringField(var2, "server");
         }

         if (var7.isEmpty()) {
            var7 = "unknown";
         }

         boolean var8 = "playerJoined".equals(var3);
         IrcMessageBus.publishNotice(
            (
                  var8
                     ? "&a"
                     : "&c"
               )
               + var6
               + (
                  var8
                     ? " &7joined lobby &b"
                     : " &7left lobby &b"
               )
               + var7
               + "&7!"
         );
      } catch (Exception var9) {
      }
   }

   private static String normalizeUuid(String var0) {
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
               return var0 == null ? "" : var0.replace("-", "").toLowerCase();
         }
      }
   }

   private static String readStringField(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsString();
         } catch (Exception var3) {
         }
      }

      return "";
   }

   static String readJsonString(JsonObject var0, String var1) {
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
               return readStringField(var0, var1);
         }
      }
   }

   static void onSocketOpen(PlayerTrackerClient var0, PlayerTrackerClient$2 var1) {
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
               var0.sendTrackRequestsForAll(var1);
               return;
         }
      }
   }

   static void onSocketMessage(PlayerTrackerClient var0, String var1) {
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
               var0.handleServerMessage(var1);
               return;
         }
      }
   }

   static long setNextReconnectAt(PlayerTrackerClient var0, long var1) {
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
               return var0.nextReconnectAtMillis = var1;
         }
      }
   }

   static {
   }
}
