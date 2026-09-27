// Jade recovery: original class: jade.deps.eLz.wqc7dwoz
package jade.client.setting;

public final class SettingPopupLayout {
   private SettingPopupLayout() {
   }

   public static SettingPopupLayout$1 computePopupRect(float var0, float var1, float var2, float var3) {
      float var4 = Math.max(160.0F, var3);
      float var5 = Math.min(208.0F, Math.max(152.0F, var4 - 18.0F));
      float var6 = var1 + Math.max(8.0F, (var4 - var5) / 2.0F);
      return new SettingPopupLayout$1(var0 + 6.0F, var6, var0 + var2 - 6.0F, var6 + var5);
   }

   public static SettingPopupLayout$2 computeVisibleRowRange(float var0, float var1, int var2, int var3) {
      int var4 = (int)(var0 / var1);
      return new SettingPopupLayout$2(var4, Math.min(var4 + var3 + 1, var2));
   }

   public static float computeClampedListHeight(int var0, int var1, float var2) {
      return Math.min(Math.max(var0, 0), var1) * var2;
   }

   public static float computeMaxScroll(int var0, int var1, float var2) {
      return Math.max(0, var0 - var1) * var2;
   }

   public static boolean isPointInListArea(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var3 && var1 >= var4 && var1 < var4 + var5;
   }

   public static boolean isPointInBounds(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var3 && var1 >= var4 && var1 <= var5;
   }

   public static int getRowIndexAtPoint(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8) {
      if (!isPointInListArea(var0, var1, var2, var3, var4, var5)) {
         return -1;
      } else {
         int var9 = (int)((var1 - var4 + var6) / var7);
         return var9 >= 0 && var9 < var8 ? var9 : -1;
      }
   }
}
