// Jade recovery: original class: jade.deps.eLz.RTVFjM6e
package jade.client.event;

import net.minecraft.network.Packet;

public abstract class PacketEvent extends Event {
   private final Packet<?> packet;

   protected PacketEvent(Packet<?> var1) {
      this.packet = var1;
   }

   public Packet<?> ys98() {
      return this.packet;
   }
}
