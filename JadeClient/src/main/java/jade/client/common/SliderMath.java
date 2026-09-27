// Jade recovery: original class: jade.deps.eLz.JHXQ6K
package jade.client.common;

public final class SliderMath {
   private SliderMath() {
   }

   private static int iaU06(int var0) {
      return Math.min(7, Math.max(0, var0 - 3));
   }

   private static int getSliderTravel(int var0) {
      return Math.max(0, var0 - 3 - iaU06(var0));
   }

   public static int fractionToSliderOffset(int var0, double var1) {
      return iaU06(var0) + (int)Math.round(getSliderTravel(var0) * Math.max(0.0, Math.min(1.0, var1)));
   }

   public static double sliderOffsetToFraction(int var0, double var1) {
      return Math.max(0.0, Math.min(1.0, (var1 - iaU06(var0)) / Math.max(1, getSliderTravel(var0))));
   }
}
