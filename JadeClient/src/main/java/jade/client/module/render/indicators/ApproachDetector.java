// Jade recovery: original class: jade.deps.eLz.eve6Tavu
package jade.client.module.render.indicators;

public final class ApproachDetector {
   private static final double ZJdJvm = 1.0;
   private static final double HBig = 0.25;

   private ApproachDetector() {
   }

   public static boolean isApproaching(
      double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16, double var18
   ) {
      double var20 = var12 * 0.5;
      double var22 = var2 + var20;
      double var24 = var8 + var20;
      double var26 = length3D(var14 - var0, var16 - var22, var18 - var4);
      double var28 = length3D(var14 - var6, var16 - var24, var18 - var10);
      return var26 - var28 > 1.0 ? true : Math.abs(var16 - var22) - Math.abs(var16 - var24) > 0.25;
   }

   private static double length3D(double var0, double var2, double var4) {
      return Math.sqrt(var0 * var0 + var2 * var2 + var4 * var4);
   }
}
