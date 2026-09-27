// Jade recovery: original class: jade.deps.eLz.Idctho
package jade.client.module.shared;

import jade.client.module.Module;

public final class Idctho {
   private final Module module;
   private boolean everDisabled;

   public Idctho(Module var1) {
      this.module = var1;
      this.everDisabled = !var1.isEnabled();
   }

   public boolean hasEverBeenDisabled() {
      this.everDisabled = this.everDisabled | !this.module.isEnabled();
      return this.everDisabled;
   }
}
