// Jade recovery: original class: jade.deps.eLz.aM7rwOs
package jade.client.common;

import java.util.concurrent.atomic.AtomicBoolean;

public final class OneWayFlag {
   private final AtomicBoolean puT = new AtomicBoolean();

   public boolean isSet() {
      return this.puT.get();
   }

   public void DzqT() {
      this.puT.set(true);
   }
}
