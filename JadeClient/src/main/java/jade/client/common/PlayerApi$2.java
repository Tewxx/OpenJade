// Jade recovery: original class: jade.deps.eLz.dd8H7bUpF$2
package jade.client.common;

import java.util.UUID;

public final class PlayerApi$2 {
   private final UUID uuid;
   private final String name;

   PlayerApi$2(UUID var1, String var2) {
      this.uuid = var1;
      this.name = var2 == null ? "" : var2;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public String getName() {
      return this.name;
   }
}
