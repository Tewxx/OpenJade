// Jade recovery: original class: jade.deps.eLz.hVVJYVB
package jade.client.core;

import jade.client.common.PacketDirection;
import jade.client.common.PacketListenerRegistration;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.network.Packet;

public final class PacketQueueManager {
   private final Map<PacketDirection, PacketQueue> queues = new EnumMap<>(PacketDirection.class);

   public PacketQueueManager(HandledPacketTracker var1) {
      for (PacketDirection var5 : PacketDirection.values()) {
         this.queues.put(var5, new PacketQueue(var1));
      }
   }

   public void clearQueues() {
      this.queues.values().forEach(PacketQueue::FZao);
   }

   public boolean submitPacket(Packet<?> var1, PacketDirection var2) {
      if (var1 == null && var2 == null) {
         this.queues.values().forEach(PacketQueueManager::processQueue);
         return false;
      } else if (var1 != null && var2 != null) {
         return this.queues.get(var2).processQueue(var1, var2);
      } else {
         throw new NullPointerException();
      }
   }

   public void enqueueEntry(PacketListenerRegistration var1) {
      var1.lFjiOy().forEach((recoveredArg0) -> this.enqueueForDirection(var1, recoveredArg0));
   }

   public void gfXr(PacketDirection var1, long var2) {
      this.queues.get(var1).expireTimedOutTasks(var2);
   }

   private void enqueueForDirection(PacketListenerRegistration var1, PacketDirection var2) {
      this.queues.get(var2).uuoOv(var1);
   }

   private static void processQueue(PacketQueue var0) {
      var0.processQueue(null, null);
   }
}
