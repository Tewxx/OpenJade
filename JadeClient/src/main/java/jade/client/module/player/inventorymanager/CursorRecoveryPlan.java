// Jade recovery: original class: jade.deps.eLz.pwg2ILa
package jade.client.module.player.inventorymanager;

import java.util.Collections;
import java.util.List;

public final class CursorRecoveryPlan {
   private final List<Integer> slots;
   private final boolean JabquQ;

   public CursorRecoveryPlan(List<Integer> var1, boolean var2) {
      this.slots = Collections.unmodifiableList(var1);
      this.JabquQ = var2;
   }

   public List<Integer> YVjp() {
      return this.slots;
   }

   public boolean hasLeftoverItems() {
      return this.JabquQ;
   }
}
