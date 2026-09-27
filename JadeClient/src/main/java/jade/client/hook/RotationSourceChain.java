// Jade recovery: original class: jade.deps.eLz.CbykZMn9
package jade.client.hook;

import jade.client.common.PointedObjectOverrider;
import java.util.function.Supplier;

public final class RotationSourceChain {
   private final Supplier<? extends PointedObjectOverrider>[] rotationSources;

   @SafeVarargs
   public RotationSourceChain(Supplier<? extends PointedObjectOverrider>... var1) {
      this.rotationSources = (Supplier<? extends PointedObjectOverrider>[])var1.clone();
   }

   public void updateActiveRotationSource(float var1) {
      for (Supplier var5 : this.rotationSources) {
         PointedObjectOverrider var6 = (PointedObjectOverrider)var5.get();
         if (var6 != null && var6.shouldOverridePointedObject()) {
            var6.applyPointedObjectOverride(var1);
            return;
         }
      }
   }

   public static boolean areAnglesUsable(Float var0, Float var1) {
      return var0 != null && !var0.isNaN() && var1 != null && !var1.isNaN();
   }
}
