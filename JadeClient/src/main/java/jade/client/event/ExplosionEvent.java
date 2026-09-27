// Jade recovery: original class: jade.deps.eLz.DRNWbFm0
package jade.client.event;

import net.minecraft.network.play.server.S27PacketExplosion;

public class ExplosionEvent extends Event {
   public S27PacketExplosion s27PacketExplosion;

   public ExplosionEvent(S27PacketExplosion var1) {
      this.s27PacketExplosion = var1;
   }
}
