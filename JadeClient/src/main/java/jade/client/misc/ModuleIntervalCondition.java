// Jade recovery: original class: jade.deps.eLz.km8X5lH
package jade.client.misc;

import jade.client.common.PacketReleaseGate;
import jade.client.module.Module;
import org.jetbrains.annotations.NotNull;

public final class ModuleIntervalCondition extends PacketReleaseGate {
   @NotNull
   private final Module module;
   private final long intervalMillis;
   private long nextReleaseAtMillis = 0L;

   public ModuleIntervalCondition(@NotNull Module var1, long var2) {
      this.module = var1;
      this.intervalMillis = Math.max(1L, var2);
   }

   @Override
   protected boolean shouldRelease() {
      if (!this.module.isEnabled()) {
         return true;
      } else {
         long var1 = System.currentTimeMillis();
         if (var1 >= this.nextReleaseAtMillis) {
            this.nextReleaseAtMillis = var1 + this.intervalMillis;
            return true;
         } else {
            return false;
         }
      }
   }
}
