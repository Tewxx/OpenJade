// Jade recovery: original class: jade.deps.eLz.kNILsV
package jade.client.common;

import java.util.HashMap;
import java.util.Map;

public final class GuiAnimationRegistry {
   private SmoothedFloat openAnimation = new SmoothedFloat(1.0F);
   private final SmoothedFloat globalToggleAnimation = new SmoothedFloat(0.0F);
   private final Map<String, SmoothedFloat> gVyv7 = new HashMap<>();
   private final Map<String, SmoothedFloat> presetAnimations = new HashMap<>();

   public void resetAnimations() {
      this.openAnimation = new SmoothedFloat(0.0F);
      this.openAnimation.smoothTowards(1.0F);
      this.gVyv7.clear();
      this.presetAnimations.clear();
   }

   public float MUePi() {
      return this.openAnimation.smoothTowards(1.0F);
   }

   public float getGlobalToggleValue(boolean var1) {
      return this.globalToggleAnimation.smoothTowards(var1 ? 1.0F : 0.0F);
   }

   public float Xfa0(String var1, boolean var2) {
      SmoothedFloat var3 = this.gVyv7.get(var1);
      if (var3 == null) {
         var3 = new SmoothedFloat(0.0F);
         this.gVyv7.put(var1, var3);
      }

      return var3.smoothTowards(var2 ? 1.0F : 0.0F);
   }

   public float QHqA(String var1, boolean var2) {
      SmoothedFloat var3 = this.presetAnimations.get(var1);
      if (var3 == null) {
         var3 = new SmoothedFloat(var2 ? 1.0F : 0.0F);
         this.presetAnimations.put(var1, var3);
      }

      return var3.smoothTowards(var2 ? 1.0F : 0.0F);
   }
}
