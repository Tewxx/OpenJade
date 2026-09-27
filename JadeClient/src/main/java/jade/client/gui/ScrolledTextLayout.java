// Jade recovery: original class: jade.deps.eLz.i3Q2pBR
package jade.client.gui;

public final class ScrolledTextLayout {
   private ScrolledTextLayout() {
   }

   public static String buildVisibleText(String var0, int var1, float var2, float var3, ScrolledTextLayout$2 var4) {
      if (var0 != null && !var0.isEmpty() && !(var2 <= 0.0F)) {
         int var5 = clamp(var1, 0, var0.length());
         float var6 = var2 / var3;
         StringBuilder var7 = new StringBuilder();

         for (int var8 = var5; var8 < var0.length(); var8++) {
            var7.append(var0.charAt(var8));
            if (var4.getTextWidth(var7.toString()) > var6) {
               var7.setLength(var7.length() - 1);
               break;
            }
         }

         return var7.toString();
      } else {
         return "";
      }
   }

   public static ScrolledTextLayout$1 computeVisibleRange(int var0, int var1, int var2, int var3) {
      int var4 = var2 + var3;
      int var5 = Math.min(var0, var1);
      int var6 = Math.max(var0, var1);
      return var5 != var6 && var6 > var2 && var5 < var4 ? new ScrolledTextLayout$1(Math.max(0, var5 - var2), Math.min(var3, var6 - var2)) : new ScrolledTextLayout$1(0, 0);
   }

   public static int ImA6(int var0, int var1, int var2) {
      return clamp(var0 - var1, 0, var2);
   }

   public static boolean isPointInRect(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var4 && var1 >= var3 && var1 <= var5;
   }

   private static int clamp(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var0, var2));
   }
}
