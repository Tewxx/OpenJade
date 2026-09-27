// Jade recovery: original class: jade.deps.eLz.oKaHYZy4
package jade.client.common;

import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class PacketListenerRegistration {
   private final PacketHandlerBinding registration;

   public PacketListenerRegistration(@NotNull Set<PacketDirection> var1, @NotNull PacketReleaseGate var2) {
      this.registration = new PacketHandlerBinding(var1, var2);
   }

   @NotNull
   public Set<PacketDirection> lFjiOy() {
      return this.registration.getDirections();
   }

   @NotNull
   public PacketReleaseGate getPacketHandler() {
      return this.registration.PdeC();
   }
}
