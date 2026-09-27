// Jade recovery: original class: jade.deps.eLz.GpYm4wKD
package jade.client.module.render.esp;

public final class ColorComponents {
   private ColorComponents() {
   }

   public static int[] NMv0(int var0) {
      return new int[]{var0 >>> 16 & 0xFF, var0 >>> 8 & 0xFF, var0 & 0xFF, var0 >>> 24 & 0xFF};
   }

   public static float[] toFloats(int var0, int var1, int var2, int var3) {
      return new float[]{var0 / 255.0F, var1 / 255.0F, var2 / 255.0F, var3 / 255.0F};
   }
}
