// Jade recovery: original class: jade.deps.eLz.WIB1mr
package jade.client.event;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class VelocityEvent extends Event {
   public S12PacketEntityVelocity s12PacketEntityVelocity;

   public VelocityEvent(S12PacketEntityVelocity var1) {
      this.s12PacketEntityVelocity = var1;
   }
}
