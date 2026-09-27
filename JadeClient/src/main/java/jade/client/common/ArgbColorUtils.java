// Jade recovery: original class: jade.deps.eLz.jj0KUXSViC
package jade.client.common;

public final class ArgbColorUtils {
   private ArgbColorUtils() {
   }

   public static float CWWp(int var0) {
      return SDtD(var0, 16);
   }

   public static float xzId(int var0) {
      return SDtD(var0, 8);
   }

   public static float getBlue(int var0) {
      return SDtD(var0, 0);
   }

   public static float getAlpha(int var0) {
      return SDtD(var0, 24);
   }

   public static float[] toRgbaArray(int var0) {
      return new float[]{CWWp(var0), xzId(var0), getBlue(var0), getAlpha(var0)};
   }

   private static float SDtD(int var0, int var1) {
      return (var0 >>> var1 & 0xFF) / 255.0F;
   }
}
