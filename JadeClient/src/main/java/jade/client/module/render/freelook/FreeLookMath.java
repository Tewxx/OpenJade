// Jade recovery: original class: jade.deps.eLz.ajC3CtS1
package jade.client.module.render.freelook;

public final class FreeLookMath {
   private FreeLookMath() {
   }

   public static FreeLookMath$1 computeAngles(float var0, float var1, int var2, int var3, float var4) {
      float var5 = var4 * 0.6F + 0.2F;
      float var6 = var5 * var5 * var5 * 8.0F;
      float var7 = var0 + var2 * var6 * 0.15F;
      float var8 = var1 - var3 * var6 * 0.15F;
      if (var8 < -90.0F) {
         var8 = -90.0F;
      }

      if (var8 > 90.0F) {
         var8 = 90.0F;
      }

      return new FreeLookMath$1(var7, var8);
   }

   public static int clampPerspective(int var0) {
      return var0 < 0 ? 0 : Math.min(var0, 2);
   }

   public static boolean needsFovRestore(boolean var0, float var1, float var2, boolean var3) {
      return var0 || var1 == var2 || var3;
   }
}
