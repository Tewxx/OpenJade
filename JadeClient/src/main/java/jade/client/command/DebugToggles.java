// Jade recovery: original class: jade.deps.eLz.nSEQ9Pd0
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ConnectionStatsOverlay;

public final class DebugToggles {
   private DebugToggles() {
   }

   public static boolean toggleProfiling() {
      Jade.profilingEnabled = !Jade.profilingEnabled;
      return Jade.profilingEnabled;
   }

   public static boolean toggleMixinDebug() {
      ConnectionStatsOverlay.mixinDebug = !ConnectionStatsOverlay.mixinDebug;
      return ConnectionStatsOverlay.mixinDebug;
   }

   public static boolean toggleBackgroundDebug() {
      ConnectionStatsOverlay.backgroundDebug = !ConnectionStatsOverlay.backgroundDebug;
      return ConnectionStatsOverlay.backgroundDebug;
   }
}
