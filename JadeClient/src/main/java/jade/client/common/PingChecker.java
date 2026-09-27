// Jade recovery: original class: jade.deps.eLz.FN861gxGC
package jade.client.common;

import jade.client.event.ChatReceivedEvent;

public class PingChecker {
   private static final PingCheckState pingCheckState = new PingCheckState();

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (pingCheckState.amuX() && ClientUtils.isInWorld()) {
         String var2 = ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText());
         if (var2.startsWith("Unknown")) {
            var1.setCanceled(true);
            this.reportPingResult(pingCheckState.isCommandModePending(), System.currentTimeMillis());
            pingCheckState.clearAll();
         }
      }
   }

   public static void startPingCheck(boolean var0) {
      if (var0) {
         ClientUtils.sendJadeMessage("Jade", "&7checking ping...");
      } else {
         CommandLineBridge.appendConsoleLine("§3Checking...", 1);
      }

      if (pingCheckState.TBUb()) {
         if (var0) {
            ClientUtils.sendJadeMessage("Jade", "&7please wait before checking again.");
         } else {
            CommandLineBridge.appendConsoleLine("§cPlease wait.", 0);
         }
      } else {
         ClientUtils.mc.thePlayer.sendChatMessage("/...");
         pingCheckState.markRequestSent(var0, System.currentTimeMillis());
      }
   }

   private void reportPingResult(boolean var1, long var2) {
      int var4 = pingCheckState.getElapsedMillis(var1, var2);
      if (var1) {
         ClientUtils.sendJadeMessage("Jade", "&7your ping: &b" + var4 + "&7ms.");
      } else {
         CommandLineBridge.appendConsoleLine("Your ping: " + var4 + "ms", 0);
      }

      pingCheckState.clearPending(var1);
   }

   public static void cancelPingCheck(boolean var0) {
      pingCheckState.clearPending(var0);
   }
}
