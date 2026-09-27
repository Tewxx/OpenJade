// Jade recovery: original class: jade.deps.eLz.k2mbCHBtu
package jade.client.common;

import java.util.Set;

public final class PacketHandlerBinding {
   private final Set<PacketDirection> packetDirections;
   private final PacketReleaseGate kGq2;

   public PacketHandlerBinding(Set<PacketDirection> var1, PacketReleaseGate var2) {
      this.packetDirections = var1;
      this.kGq2 = var2;
   }

   public Set<PacketDirection> getDirections() {
      return this.packetDirections;
   }

   public PacketReleaseGate PdeC() {
      return this.kGq2;
   }
}
