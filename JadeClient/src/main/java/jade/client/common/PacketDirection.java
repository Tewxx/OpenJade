// Jade recovery: original class: jade.deps.eLz.ZuidpX
package jade.client.common;

import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;

public enum PacketDirection implements IMinecraft {
   INBOUND(EnumPacketDirection.CLIENTBOUND),
   OUTBOUND(EnumPacketDirection.SERVERBOUND);

   public static final Set<PacketDirection> ONLY_INBOUND = EnumSet.of(INBOUND);
   public static final Set<PacketDirection> ONLY_OUTBOUND = EnumSet.of(OUTBOUND);
   public static final Set<PacketDirection> BIDIRECTIONAL = EnumSet.allOf(PacketDirection.class);
   private final EnumPacketDirection destination;

   private PacketDirection(EnumPacketDirection var3) {
      this.destination = var3;
   }

   public void passThroughChannel(Packet<?> var1) {
      PacketDispatcher.dispatchPacket(mc.getNetHandler(), this.destination, var1);
   }
}
