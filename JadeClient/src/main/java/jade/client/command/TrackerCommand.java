// Jade recovery: original class: jade.deps.eLz.ISJ9Xs
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.PlayerApi$2;
import jade.client.common.PlayerApi;
import jade.client.common.CommandInput;
import jade.deps.loader107.PlayerTrackerClient;
import java.util.Locale;
import java.util.UUID;

public class TrackerCommand extends Command {
   public TrackerCommand() {
      super("tracker", "track");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 1) {
         this.ahGlioN();
      } else {
         final String var2 = var1.getArgument(0);
         this.sendChatMessage("&7checking tracker for &f" + var2 + "&7...");
         Jade.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
               final PlayerApi$2 recoveredPlayer = PlayerApi.lookupProfileByName(var2);
               ClientUtils.mc.addScheduledTask(new Runnable() {
                  @Override
                  public void run() {
                     if (recoveredPlayer != null && recoveredPlayer.getUuid() != null) {
                        String var1x = TrackerCommand.formatPlayerId(recoveredPlayer.getUuid());
                        String var2x = recoveredPlayer.getName().isEmpty() ? var2 : recoveredPlayer.getName();
                        PlayerTrackerClient var3 = PlayerTrackerClient.getInstance();
                        if (var3.isTracked(var1x)) {
                           var3.untrackPlayer(var1x);
                           ClientUtils.sendJadeMessage("Jade", "&7no longer tracking &f" + var2x + "&7.");
                        } else {
                           var3.trackPlayer(var1x, var2x);
                           ClientUtils.sendJadeMessage("Jade", "&7tracking &f" + var2x + "&7.");
                        }
                     } else {
                        ClientUtils.sendJadeMessage("Jade", "&7could not find player &f" + var2 + "&7.");
                     }
                  }
               });
            }
         });
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Tracker: &b" + this.withCommandPrefix("tracker") + " <username>");
   }

   private static String Bp16(UUID var0) {
      return var0.toString().replace("-", "").toLowerCase(Locale.ROOT);
   }

   public static String formatPlayerId(UUID var0) {
      return Bp16(var0);
   }
}
