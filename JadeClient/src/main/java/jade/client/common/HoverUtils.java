// Jade recovery: original class: jade.deps.eLz.IsfXJKoR
package jade.client.common;

public final class HoverUtils {
   private HoverUtils() {
   }

   public static float nrje(float var0, float var1, float var2) {
      return Math.min(Math.max(var0, 2.0F), var1 - var2 - 4.0F);
   }

   public static float clampVerticalPosition(float var0, float var1, float var2, boolean var3) {
      float var4 = var1 - var2 - 5.0F;
      if (var3) {
         var4 = (int)var4;
      }

      return Math.min(Math.max(var0, 1.0F), var4);
   }

   public static boolean isPointInPaddedRect(int var0, int var1, float var2, float var3, float var4, float var5) {
      return isPointInBounds(var0, var1, var2, var2 + var4, var3 + 2.0F, var3 + var5 + 1.0F);
   }

   public static boolean isPointInTallPaddedRect(int var0, int var1, float var2, float var3, float var4, float var5, float var6) {
      return isPointInBounds(var0, var1, var2 - 2.0F, var2 + var4 + 2.0F, var3 + 2.0F, var3 + var5 + var6 + 1.0F);
   }

   public static boolean isPointInRect(int var0, int var1, float var2, float var3, float var4, float var5) {
      return isPointInBounds(var0, var1, var2, var2 + var4, var3, var3 + var5);
   }

   public static boolean isPointInWideRect(int var0, int var1, float var2, float var3, float var4, float var5) {
      return isPointInBounds(var0, var1, var2 - 2.0F, var2 + var4 + 2.0F, var3, var5);
   }

   private static boolean isPointInBounds(int var0, int var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var3 && var1 >= var4 && var1 <= var5;
   }
}
