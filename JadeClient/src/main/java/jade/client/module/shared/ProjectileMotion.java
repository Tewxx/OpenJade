// Jade recovery: original class: jade.deps.eLz.WiOnUAW
package jade.client.module.shared;

public final class ProjectileMotion {
   public static final double ARROW_DRAG = 0.99;
   public static final double lGa = 0.05;
   public static final double FISHING_HOOK_DRAG = 0.92;
   public static final double MHEbZk = 0.04;
   public static final double sJxon = 1.5;

   private ProjectileMotion() {
   }

   public static double OYIzkT(int var0) {
      return RxIh5(var0);
   }

   public static double RxIh5(double var0) {
      double var2 = Math.max(0.0, var0) / 20.0;
      var2 = (var2 * var2 + var2 * 2.0) / 3.0;
      return Math.min(1.0, var2);
   }

   public static double getArrowSpeed(int var0) {
      return OYIzkT(var0) * 3.0;
   }

   public static double getInterpolatedArrowSpeed(double var0) {
      return RxIh5(var0) * 3.0;
   }

   public static double geometricSeriesSum(double var0, double var2) {
      return var2 <= 0.0 ? 0.0 : (1.0 - Math.pow(var0, var2)) / (1.0 - var0);
   }

   public static double kgtyaW(double var0, double var2, double var4) {
      double var6 = geometricSeriesSum(var0, var4);
      return var2 / (1.0 - var0) * (var4 - var6);
   }

   public static double[] solveLaunchVelocity(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = geometricSeriesSum(var6, var10);
      if (var12 <= 0.0) {
         return null;
      } else {
         double var14 = kgtyaW(var6, var8, var10);
         return new double[]{var0 / var12, (var2 + var14) / var12, var4 / var12};
      }
   }
}
