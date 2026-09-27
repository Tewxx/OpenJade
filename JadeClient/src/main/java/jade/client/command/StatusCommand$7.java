// Jade recovery: original class: jade.deps.eLz.EjTaZDS1W$7
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class StatusCommand$7 implements Runnable {
   final StatusCommand this$0;

   private final String playerUuid;
   private final String LyaMbq;
   private volatile ScheduledFuture<?> scheduledFuture;
   private volatile StatusCommand$4 lastStatusSnapshot;
   private volatile StatusCommand$3 lastGameSnapshot;
   private volatile boolean DNbd;

   StatusCommand$7(StatusCommand var1, String var2, String var3) {
      this.this$0 = var1;
      this.playerUuid = var2;
      this.LyaMbq = var3;
   }

   private void schedulePolling() {
      this.scheduledFuture = Jade.getScheduler().scheduleAtFixedRate(this, 0L, 4L, TimeUnit.SECONDS);
   }

   private void cancelScheduledFuture() {
      ScheduledFuture var1 = this.scheduledFuture;
      if (var1 != null) {
         var1.cancel(false);
      }
   }

   @Override
   public void run() {
      try {
         this.DWVQh();
      } catch (StatusCommand$2 var2) {
         this.cancelScheduledFuture();
         StatusCommand.getActivePollers(this.this$0).remove(this.playerUuid);
         this.sendPollingStoppedMessage("&cstatus polling stopped for &f" + this.LyaMbq + "&c: " + var2.getMessage());
      } catch (Exception var3) {
      }
   }

   private void DWVQh() throws Exception {
      String var1 = StatusCommand.ewEe(this.this$0);
      if (var1 != null && !var1.trim().isEmpty()) {
         StatusCommand$4 var2 = StatusCommand$4.tIa58(StatusCommand.fetchApiJson(String.format("https://api.hypixel.net/v2/status?uuid=%s", this.playerUuid), var1));
         StatusCommand$3 var3 = StatusCommand$3.GjLg(StatusCommand.fetchApiJson(String.format("https://api.hypixel.net/v2/recentgames?uuid=%s", this.playerUuid), var1));
         if (StatusCommand.getActivePollers(this.this$0).containsKey(this.playerUuid)) {
            if (this.DNbd) {
               if (var2 != null && !var2.equals(this.lastStatusSnapshot)) {
                  this.zsNfnC("&f" + this.LyaMbq + " &7is now in " + StatusCommand$4.getStatusLine(var2));
               }

               if (var3 != null && this.lastGameSnapshot != null) {
                  if (StatusCommand$3.getLatestGameTime(var3) != StatusCommand$3.getLatestGameTime(this.lastGameSnapshot)) {
                     this.zsNfnC("&f" + this.LyaMbq + "&7's game has started!");
                  } else if (StatusCommand$3.isGameEnded(var3) && !StatusCommand$3.isGameEnded(this.lastGameSnapshot)) {
                     this.zsNfnC("&f" + this.LyaMbq + "&7's game has ended!");
                  }
               }
            }

            this.lastStatusSnapshot = var2;
            this.lastGameSnapshot = var3;
            this.DNbd = true;
         }
      } else {
         throw new StatusCommand$2("missing API key");
      }
   }

   private void zsNfnC(final String var1) {
      ClientUtils.mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            if (StatusCommand.getActivePollers(StatusCommand$7.this.this$0).containsKey(StatusCommand$7.getPlayerUuid(StatusCommand$7.this))) {
               ClientUtils.sendJadeMessage("Jade", var1);
            }
         }
      });
   }

   private void sendPollingStoppedMessage(final String var1) {
      ClientUtils.mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            ClientUtils.sendJadeMessage("Jade", var1);
         }
      });
   }

   public static void cancelPolling(StatusCommand$7 var0) {
      var0.cancelScheduledFuture();
   }

   public static String FLoRyw(StatusCommand$7 var0) {
      return var0.LyaMbq;
   }

   public static void bmt2(StatusCommand$7 var0) {
      var0.schedulePolling();
   }

   public static String getPlayerUuid(StatusCommand$7 var0) {
      return var0.playerUuid;
   }
}
