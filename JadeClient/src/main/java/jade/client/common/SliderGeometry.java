// Jade recovery: original class: jade.deps.eLz.F4IBTBXnpC
package jade.client.common;

import jade.client.setting.SliderSetting;

public final class SliderGeometry {
   private SliderGeometry() {
   }

   public static double getHandleOffset(SliderSetting var0, float var1) {
      double var2 = var0.getInput();
      if (var2 == -1.0) {
         return 0.0;
      } else {
         double var4 = var0.getMax() - var0.getMin();
         return (var1 - 8.0F) * (var2 - var0.getMin()) / var4;
      }
   }
}
