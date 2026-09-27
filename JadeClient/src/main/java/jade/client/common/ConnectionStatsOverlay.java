// Jade recovery: original class: jade.deps.eLz.h83OKUMgqm
package jade.client.common;

import jade.client.Jade;
import jade.client.event.RenderTickEvent;

public class ConnectionStatsOverlay implements IMinecraft {
   public static boolean mixinDebug;
   public static boolean backgroundDebug;

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      boolean var2 = ConditionGuard.WARug(Jade.profilingEnabled, var1.eventPhase == EventPhase.END, ClientUtils.isInWorld(), mc.currentScreen == null);
      if (var2) {
         RenderUtils.drawDebugStatsOverlay(true, true);
      }
   }

   public static void CBlo(Object var0, String var1) {
      if (mixinDebug) {
         ClientUtils.sendColoredMessage(DebugMessageFormatter.ftSq(var0.getClass(), var1));
      }
   }
}
