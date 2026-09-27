// Jade recovery: original class: jade.deps.eLz.eZOm78n
package jade.client.event;

import jade.client.common.EventPhase;

public class RenderTickEvent extends Event {
   public final EventPhase eventPhase;
   public final float partialTicks;

   public RenderTickEvent(EventPhase var1, float var2) {
      this.eventPhase = var1;
      this.partialTicks = var2;
   }
}
