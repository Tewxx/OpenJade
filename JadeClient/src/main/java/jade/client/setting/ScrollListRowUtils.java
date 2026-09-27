// Jade recovery: original class: jade.deps.eLz.bfDhihpML
package jade.client.setting;

public final class ScrollListRowUtils {
   private ScrollListRowUtils() {
   }

   public static int getRowIndexAt(int var0, int var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      int var8 = (int)((var1 - var4 + var5) / var6);
      if (var8 >= 0 && var8 < var7 && !(var0 <= var2) && !(var0 >= var3)) {
         float var9 = var4 - var5 + var8 * var6;
         return !(var1 < var9) && !(var1 >= var9 + var6) ? var8 : -1;
      } else {
         return -1;
      }
   }

   public static int getRowColor(int var0, int var1) {
      return var0 == var1 ? -14013892 : (var0 % 2 == 0 ? -15066582 : -14803410);
   }
}
