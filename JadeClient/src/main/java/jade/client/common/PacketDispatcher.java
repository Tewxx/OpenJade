// Jade recovery: original class: jade.deps.eLz.iJ3mZiBqn
package jade.client.common;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;

public final class PacketDispatcher {
   private PacketDispatcher() {
   }

   public static void dispatchPacket(NetHandlerPlayClient var0, EnumPacketDirection var1, Packet<?> var2) {
      if (var0 != null) {
         boolean var3 = var1 == EnumPacketDirection.CLIENTBOUND;
         if (EnumConnectionState.PLAY.getPacketId(var1, var2) == null) {
            ClientUtils.sendDebugMessage(
               "dropped stale "
                  + (var3 ? "inbound" : "outbound")
                  + " packet not registered in PLAY/"
                  + (var3 ? "CLIENTBOUND" : "SERVERBOUND")
                  + ": "
                  + var2.getClass().getSimpleName()
            );
         } else if (!var3) {
            var0.addToSendQueue(var2);
         } else {
            handleInboundPacket(var0, var2);
         }
      }
   }

   private static void handleInboundPacket(NetHandlerPlayClient var0, Packet<?> var1) {
      try {
         ((Packet)var1).processPacket(var0);
      } catch (ThreadQuickExitException var3) {
      } catch (Exception var4) {
         ClientUtils.sendDebugMessage("error while handling packet: " + var1.getClass().getSimpleName());
      }
   }
}
