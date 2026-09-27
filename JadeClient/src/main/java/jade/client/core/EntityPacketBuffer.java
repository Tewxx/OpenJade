// Jade recovery: original class: jade.deps.eLz.qSNh8e4
package jade.client.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import net.minecraft.network.Packet;

public final class EntityPacketBuffer {
   private final Queue<Packet<?>> entityPacketQueue = new ConcurrentLinkedQueue<>();
   private final Queue<Packet<?>> fyK = new ConcurrentLinkedQueue<>();

   public void addPacket(Packet<?> var1) {
      (EntityPacketFilter.isEntityPacket(var1) ? this.entityPacketQueue : this.fyK).add(var1);
   }

   public void SvivXg(Consumer<Packet<?>> var1) {
      KkbK(this.entityPacketQueue, var1);
   }

   public void lPreU(Consumer<Packet<?>> var1) {
      this.SvivXg(var1);
      KkbK(this.fyK, var1);
   }

   public void CADWz6() {
      this.entityPacketQueue.clear();
      this.fyK.clear();
   }

   private static void KkbK(Queue<Packet<?>> var0, Consumer<Packet<?>> var1) {
      Packet var2;
      while ((var2 = (Packet)var0.poll()) != null) {
         var1.accept(var2);
      }
   }
}
