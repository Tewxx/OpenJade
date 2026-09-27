// Jade recovery: original class: jade.deps.eLz.K82grhdir
package jade.client.module.shared;

import jade.client.common.PacketReleaseGate;
import jade.client.module.Module;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DisabledOrNestedCondition extends PacketReleaseGate {
   private final Idctho everDisabledTracker;
   @Nullable
   private final PacketReleaseGate j53;

   public DisabledOrNestedCondition(@NotNull Module var1, @Nullable PacketReleaseGate var2) {
      this.everDisabledTracker = new Idctho(var1);
      this.j53 = var2;
   }

   public DisabledOrNestedCondition(@NotNull Module var1) {
      this(var1, null);
   }

   @Override
   protected boolean shouldRelease() {
      return this.everDisabledTracker.hasEverBeenDisabled() || this.j53 != null && this.j53.isOpen();
   }
}
