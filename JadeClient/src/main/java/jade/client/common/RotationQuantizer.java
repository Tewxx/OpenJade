// Jade recovery: original class: jade.deps.eLz.BIB89U
package jade.client.common;

public final class RotationQuantizer {
   private RotationQuantizer() {
   }

   public static float[] quantizeRotation(float var0, float var1, float var2, float var3, float var4) {
      float var5 = RotationInterpolator.tfpXj(var0, var2);
      return new float[]{AngleUtils.quantizeAngle(var5, var2, var4), AngleUtils.clampPitch(AngleUtils.quantizeAngle(var1, var3, var4))};
   }
}
