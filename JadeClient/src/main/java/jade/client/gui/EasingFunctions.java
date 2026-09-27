// Jade recovery: original class: jade.deps.eLz.r2jw38FgW
package jade.client.gui;

public final class EasingFunctions {
   private EasingFunctions() {
   }

   public static float easeOutExpo(float var0) {
      if (var0 <= 0.0F) {
         return 0.0F;
      } else {
         return var0 >= 1.0F ? 1.0F : 1.0F - (float)Math.pow(2.0, -10.0 * var0);
      }
   }
}
