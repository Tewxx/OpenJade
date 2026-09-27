// Jade recovery: original class: jade.deps.eLz.JNSEj3wg
package jade.client.common;

public final class HsbColor {
   private static final float MIN_COMPONENT_VALUE = 0.001F;
   private static final float COMPONENT_EPSILON = 0.001F;
   private float hue;
   private float saturation;
   private float brightness;

   public void MHxl8(float var1, float var2, float var3) {
      this.brightness = var3;
      if (!(var3 < 0.001F)) {
         this.saturation = var2;
         if (var2 >= 0.001F) {
            this.hue = var1;
         }
      }
   }

   public void HHMk(float var1, float var2) {
      this.saturation = epIr(var1);
      this.brightness = epIr(var2);
   }

   public void setHue(float var1) {
      this.hue = clamp(var1, 0.0F, 360.0F);
   }

   public float ecjE() {
      return this.hue;
   }

   public float getSaturation() {
      return this.saturation;
   }

   public float getBrightness() {
      return this.brightness;
   }

   public static int normalizedToByte(float var0) {
      return (int)(epIr(var0) * 255.0F);
   }

   private static float epIr(float var0) {
      return clamp(var0, 0.0F, 1.0F);
   }

   private static float clamp(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }
}
