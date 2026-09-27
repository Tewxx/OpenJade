// Jade recovery: original class: jade.deps.eLz.XibawdKeFr
package jade.client.setting;

public final class XibawdKeFr {
   private XibawdKeFr() {
   }

   public static int AsbN(int var0, int[] var1) {
      if (var1 == null) {
         return -1;
      } else {
         for (int var2 = 0; var2 < var1.length; var2++) {
            if (var1[var2] == var0) {
               return var2 + 1;
            }
         }

         return -1;
      }
   }

   public static String formatNumberOrEllipsis(boolean var0, Integer var1) {
      return var0 ? "..." : Integer.toString(var1 == null ? 1 : var1);
   }

   public static float kJxiM(float var0, float var1, float var2, float var3, float var4) {
      if (var0 < var1 + var3) {
         return var4;
      } else {
         return var0 > var1 + var2 - var3 ? -var4 : 0.0F;
      }
   }

   public static int getRowIndexAtY(float var0, float var1, float var2, float var3, int var4) {
      if (var4 > 0 && !(var3 <= 0.0F)) {
         float var5 = var0 + var3 / 2.0F;
         int var6 = (int)Math.floor((var5 - var1 + var2) / var3);
         return Math.max(0, Math.min(var6, var4 - 1));
      } else {
         return -1;
      }
   }

   public static boolean isWithinInsetBounds(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var3 && var1 >= var4 + 1.0F && var1 <= var4 + var5 - 1.0F;
   }
}
