// Jade recovery: original class: jade.deps.eLz.ewzWEIyxX7
package jade.client.common;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import net.minecraft.network.Packet;

public final class PacketExclusions {
   private final Set<Packet<?>> outboundPackets = newPacketIdentitySet();
   private final Set<Packet<?>> jBroY = newPacketIdentitySet();

   public void excludeOutboundPacket(Packet<?> var1) {
      this.outboundPackets.add(var1);
   }

   public void VIPsu34(Packet<?> var1) {
      this.jBroY.add(var1);
   }

   public boolean consumeOutboundPacket(Packet<?> var1) {
      return this.outboundPackets.remove(var1);
   }

   public boolean consumeInboundPacket(Packet<?> var1) {
      return this.jBroY.remove(var1);
   }

   private static Set<Packet<?>> newPacketIdentitySet() {
      IdentityHashMap var0 = new IdentityHashMap();
      return Collections.newSetFromMap(Collections.synchronizedMap(var0));
   }
}
