// Jade recovery: original class: jade.deps.eLz.TNfnwR
package jade.client.module.player.hidewindow;

public final class SquareBounds {
   private SquareBounds() {
   }

   public static int Yvhab2(int var0, double var1) {
      return Math.round(var0 * (float)var1);
   }

   public static float[] ZlAtiX(float var0, float var1, int var2, float var3) {
      float var4 = var2 / 2.0F;
      return new float[]{var0 - var4 - var3, var1 - var4 - var3, var0 + var4 + var3, var1 + var4 + var3};
   }

   public static boolean containsPoint(float[] var0, float var1, float var2) {
      return var1 >= var0[0] && var1 <= var0[2] && var2 >= var0[1] && var2 <= var0[3];
   }
}
