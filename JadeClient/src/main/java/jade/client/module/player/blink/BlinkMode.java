// Jade recovery: original class: jade.deps.eLz.vagkR0h8tU
package jade.client.module.player.blink;

import jade.client.common.PacketDirection;
import java.util.Set;

public final class BlinkMode {
   public static final int Kfq = 0;
   public static final int MODE_OUTBOUND = 1;
   public static final int MODE_BOTH = 2;

   private BlinkMode() {
   }

   public static Set<PacketDirection> getPacketDirections(int var0) {
      if (var0 == 0) {
         return PacketDirection.ONLY_INBOUND;
      } else {
         return var0 == 2 ? PacketDirection.BIDIRECTIONAL : PacketDirection.ONLY_OUTBOUND;
      }
   }

   public static boolean vTkoy(int var0) {
      return var0 == 0 || var0 == 2;
   }

   public static boolean isOutbound(int var0) {
      return var0 == 1 || var0 == 2;
   }
}
