// Jade recovery: original class: jade.deps.eLz.ZQjV96ZQ
package jade.client.common;

public final class LayoutMath {
   private LayoutMath() {
   }

   public static float clampScaled(int var0, int var1, float var2) {
      return Math.min(Math.max(var0, 0), var1) * var2;
   }

   public static float overflowScaled(int var0, int var1, float var2) {
      return Math.max(0, var0 - var1) * var2;
   }

   public static int floorDivideToInt(float var0, float var1) {
      return (int)(var0 / var1);
   }

   public static int clampEndIndex(int var0, int var1, int var2) {
      return Math.min(var0 + var2 + 1, var1);
   }

   public static float KDlh(float var0, float var1, int var2, float var3) {
      return var0 - var1 + var2 * var3;
   }

   public static boolean isInsideBoundsExclusive(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var3 && var1 >= var4 && var1 < var4 + var5;
   }

   public static boolean isInsideBoundsInclusive(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var3 && var1 >= var4 && var1 <= var4 + var5;
   }
}
