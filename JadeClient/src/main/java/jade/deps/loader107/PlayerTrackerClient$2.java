package jade.deps.loader107;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.websocket.client.WebSocketClient;
import jade.deps.websocket.handshake.ServerHandshake;
import java.net.URI;

final class PlayerTrackerClient$2 extends WebSocketClient {
   final PlayerTrackerClient this$0;

   private volatile boolean authenticated;

   PlayerTrackerClient$2(PlayerTrackerClient var1, URI var2) {
      super(var2);
      this.this$0 = var1;
   }

   @Override
   public void onOpen(ServerHandshake var1) {

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
         String var3 = PlayerTrackerClient.readJsonString(
            var2, "type"
         );
         if ("state_ok".equals(var3)) {
            this.authenticated = true;
            PlayerTrackerClient.onSocketOpen(this.this$0, this);
            return;
         }

         if ("state_fail".equals(var3)) {
            this.close();
            return;
         }
      } catch (Exception var4) {
      }

      PlayerTrackerClient.onSocketMessage(this.this$0, var1);
   }

   @Override
   public void onClose(int var1, String var2, boolean var3) {
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
               this.authenticated = false;
               PlayerTrackerClient.setNextReconnectAt(this.this$0, System.currentTimeMillis() + 5000L);
               return;
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

   static boolean isAuthenticated(PlayerTrackerClient$2 var0) {
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
}
