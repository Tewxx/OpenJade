// Jade recovery: original class: jade.deps.eLz.rbJ21kftcC
package jade.client.module.combat.velocity;

import jade.client.common.PacketReleaseGate;

public final class TimeoutCondition extends PacketReleaseGate {
   private final long JDl;

   public TimeoutCondition(long var1) {
      this.JDl = System.currentTimeMillis() + Math.max(1L, var1);
   }

   @Override
   protected boolean shouldRelease() {
      return System.currentTimeMillis() >= this.JDl;
   }
}
