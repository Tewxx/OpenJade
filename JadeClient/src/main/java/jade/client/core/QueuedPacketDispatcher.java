// Jade recovery: original class: jade.deps.eLz.vt7kjqd1Bm
package jade.client.core;

import jade.client.common.EventPriority;
import jade.client.common.PacketDirection;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.Event;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.TickStartEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.Vec3;

public final class QueuedPacketDispatcher extends HandledPacketTracker {
   private final PacketQueueManager JQwj = new PacketQueueManager(this);
   private final SentPacketTracker sentPacketTracker = new SentPacketTracker();
   private volatile long generation;

   public void JUlwlNu(PacketListenerRegistration var1) {
      this.JQwj.enqueueEntry(var1);
   }

   public void Cvuz(PacketDirection var1, long var2) {
      this.JQwj.gfXr(var1, var2);
   }

   public Vec3 LBPYfxV() {
      return this.sentPacketTracker.KoeN();
   }

   public long weqo1() {
      return this.generation;
   }

   public synchronized void FfRco() {
      this.JQwj.clearQueues();
      this.sentPacketTracker.PWbx34();
      this.generation++;
   }

   private boolean canDispatchPackets() {
      if (Minecraft.getMinecraft().getNetHandler() != null) {
         return true;
      } else {
         this.FfRco();
         return false;
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (this.canDispatchPackets()) {
         this.dispatchPacket(var1.ys98(), var1, PacketDirection.OUTBOUND);
      }
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.canDispatchPackets()) {
         this.dispatchPacket(var1.ys98(), var1, PacketDirection.INBOUND);
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (this.canDispatchPackets()) {
         this.JQwj.submitPacket(null, null);
      }
   }

   @Override
   public void markHandled(Packet<?> var1) {
      this.sentPacketTracker.track(var1);
   }

   private void dispatchPacket(Packet<?> var1, Event var2, PacketDirection var3) {
      boolean var4 = var3 == PacketDirection.OUTBOUND;
      EnumPacketDirection var5 = var4 ? EnumPacketDirection.SERVERBOUND : EnumPacketDirection.CLIENTBOUND;
      if (EnumConnectionState.PLAY.getPacketId(var5, var1) != null) {
         boolean var6 = this.sentPacketTracker.untrack(var1);
         if (!var2.isCanceled()) {
            if (var6) {
               if (var4) {
                  this.sentPacketTracker.recordMovement(var1);
               }
            } else if (!var4 || !(var1 instanceof C01PacketChatMessage)) {
               if (this.JQwj.submitPacket(var1, var3)) {
                  var2.setCanceled(true);
               } else if (var4) {
                  this.sentPacketTracker.recordMovement(var1);
               }
            }
         }
      }
   }
}
