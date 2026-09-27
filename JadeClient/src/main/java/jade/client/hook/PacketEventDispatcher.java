// Jade recovery: original class: jade.deps.eLz.wvUXxQ
package jade.client.hook;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.common.PacketUtils;
import jade.client.event.PacketDispatchEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.SilentPacketSendEvent;
import jade.client.module.minigames.NukeDefend;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public final class PacketEventDispatcher {
   private PacketEventDispatcher() {
   }

   public static boolean onOutgoingPacket(Packet var0) {
      NukeDefend var1 = Jade.getModuleManager().getModule(NukeDefend.class);
      if (var1 != null && var0 instanceof C08PacketPlayerBlockPlacement && var1.MuoLy()) {
         PacketUtils.consumeSilentlySent(var0);
         return true;
      } else if (PacketUtils.consumeSilentlySent(var0)) {
         notifyNukeDefend(var1, var0);
         EventBus.post(new SilentPacketSendEvent(var0));
         return false;
      } else {
         PacketSendEvent var2 = new PacketSendEvent(var0);
         EventBus.post(var2);
         if (!var2.isCanceled()) {
            notifyNukeDefend(var1, var0);
         }

         return var2.isCanceled();
      }
   }

   public static void pvkT(Packet var0, GenericFutureListener<? extends Future<? super Void>>[] var1) {
      EventBus.post(new PacketDispatchEvent(var0));
   }

   public static boolean onIncomingPacket(Packet var0) {
      if (PacketUtils.NQazztK(var0)) {
         return false;
      } else {
         PacketReceiveEvent var1 = new PacketReceiveEvent(var0);
         EventBus.post(var1);
         return var1.isCanceled();
      }
   }

   private static void notifyNukeDefend(NukeDefend var0, Packet var1) {
      if (var0 != null && var1 instanceof C03PacketPlayer) {
         var0.clearPendingPlacement();
      }
   }
}
