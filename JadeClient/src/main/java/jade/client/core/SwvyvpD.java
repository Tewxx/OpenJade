// Jade recovery: original class: jade.deps.eLz.SwvyvpD
package jade.client.core;

import jade.client.Jade;
import jade.client.common.BlockScanner;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.Subscribe;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.render.BedPlates;
import jade.client.module.render.BlockESP;
import net.minecraft.client.Minecraft;

public final class SwvyvpD {
   private static final Minecraft mc = Minecraft.getMinecraft();

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      BlockScanner.getInstance().onPacketReceive(var1);
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         BlockScanner var2 = BlockScanner.getInstance();
         if (var2.isScanningActive()) {
            int var3 = 0;
            if (Jade.getModuleManager().getModule(BlockESP.class) != null) {
               var3 = Math.max(var3, Jade.getModuleManager().getModule(BlockESP.class).getSearchSpeed());
            }

            if (Jade.getModuleManager().getModule(BedPlates.class) != null) {
               var3 = Math.max(var3, Jade.getModuleManager().getModule(BedPlates.class).getScanPriority());
            }

            if (var3 > 0) {
               var2.KOKz0(var3);
            }
         }
      }
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         BlockScanner var2 = BlockScanner.getInstance();
         if (var2.isScanningActive()) {
            var2.clearAll();
            var2.queueLoadedChunks();
         }
      }
   }
}
