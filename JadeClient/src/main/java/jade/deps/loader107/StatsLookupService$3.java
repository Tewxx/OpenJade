package jade.deps.loader107;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.websocket.client.WebSocketClient;
import jade.deps.websocket.handshake.ServerHandshake;
import java.net.URI;

final class StatsLookupService$3 extends WebSocketClient {
   final StatsLookupService this$0;

   private volatile boolean authenticated;
   private volatile long connectedAt;

   StatsLookupService$3(StatsLookupService var1, URI var2) {
      super(var2);
      this.this$0 = var1;
   }

   @Override
   public void onOpen(ServerHandshake var1) {
      this.connectedAt = System.currentTimeMillis();

      try {
         JsonObject var2 = new JsonObject();
         var2.addProperty(
            "type",
            "auth"
         );
         this.send(var2.toString());
      } catch (Exception var3) {
         this.close();
      }
   }

   @Override
   public void onMessage(String var1) {

      try {
         JsonObject var2 = new JsonParser().parse(var1).getAsJsonObject();
         String var3 = var2.has("type")
            ? var2.get("type").getAsString()
            : "";
         if ("state_ok".equals(var3)) {
            this.authenticated = true;
            StatsLookupService.storeLastSocketMessage(this.this$0, null);
            return;
         }

         if ("state_fail".equals(var3)) {
            StatsLookupService.storeLastSocketMessage(
               this.this$0,
               var2.has("reason")
                  ? var2.get("reason").getAsString()
                  : "initialization_failed"
            );
            this.close();
            return;
         }
      } catch (Exception var4) {
      }

      StatsLookupService.dispatchSocketMessage(this.this$0, var1);
   }

   @Override
   public void onClose(int var1, String var2, boolean var3) {
      this.authenticated = false;
      this.connectedAt = 0L;
      StatsLookupService.dispatchSocketFailure(
         this.this$0, this, "connection_closed"
      );
      synchronized (this.this$0) {
         if (StatsLookupService.getActiveSocket(this.this$0) == this) {
            StatsLookupService.setActiveSocket(this.this$0, null);
            StatsLookupService.setNextConnectAt(this.this$0, System.currentTimeMillis() + 5000L);
         }
      }
   }

   @Override
   public void onError(Exception var1) {
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
               return;
         }
      }
   }

   static boolean isAuthenticated(StatsLookupService$3 var0) {
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
               return var0.authenticated;
         }
      }
   }

   static long getConnectedAt(StatsLookupService$3 var0) {
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
               return var0.connectedAt;
         }
      }
   }
}
