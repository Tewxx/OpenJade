// Jade recovery: original class: jade.deps.eLz.Bjd89v
package jade.client.core;

import jade.client.common.PacketDirection;
import jade.client.common.PacketListenerRegistration;
import net.minecraft.network.Packet;

public final class DelayedPacketEntry extends PacketQueueEntry {
   private final Packet<?> packet;
   private final PacketDirection packetDirection;
   private final long UpNmu;

   public DelayedPacketEntry(Packet<?> var1, PacketDirection var2) {
      this.packet = var1;
      this.packetDirection = var2;
      this.UpNmu = System.currentTimeMillis();
   }

   public long getTimestamp() {
      return this.UpNmu;
   }

   @Override
   public boolean isExpired(long var1) {
      return this.UpNmu <= var1;
   }

   @Override
   public PacketListenerRegistration nok9(PacketListenerRegistration var1, HandledPacketTracker var2) {
      this.GuVw(var2);
      return var1;
   }

   public void GuVw(HandledPacketTracker var1) {
      if (PacketDirection.OUTBOUND == this.packetDirection) {
         var1.markHandled(this.packet);
      }

      this.packetDirection.passThroughChannel(this.packet);
   }
}
