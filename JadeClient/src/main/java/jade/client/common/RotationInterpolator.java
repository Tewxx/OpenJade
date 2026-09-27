// Jade recovery: original class: jade.deps.eLz.W8VXEyl
package jade.client.common;

public final class RotationInterpolator {
   private RotationInterpolator() {
   }

   public static float tfpXj(float var0, float var1) {
      float var2 = ((var0 - var1 + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F;
      return var1 + var2;
   }

   public static float[] stepRotation(float var0, float var1, float var2, float var3, float var4) {
      float var5 = Math.max(1.0F, var4);
      float var6 = ((var2 - var0 + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F;
      return new float[]{var0 + var6 / var5, var1 + (var3 - var1) / var5};
   }
}
