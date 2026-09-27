// Jade recovery: original class: jade.deps.eLz.Js4GuGnc
package jade.client.module.render.freelook;

public final class KeyStateEdgeDetector {
   private boolean lastState;

   public Boolean pollChange(boolean var1) {
      if (var1 == this.lastState) {
         return null;
      } else {
         this.lastState = var1;
         return var1;
      }
   }
}
