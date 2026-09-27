// Jade recovery: original class: jade.deps.eLz.i8gQqb
package jade.client.common;

public final class EdgeTrigger {
   private boolean triggered;

   public boolean TLhG(boolean var1) {
      if (!var1) {
         this.triggered = false;
         return false;
      } else if (this.triggered) {
         return false;
      } else {
         this.triggered = true;
         return true;
      }
   }
}
