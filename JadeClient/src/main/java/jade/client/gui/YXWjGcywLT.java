// Jade recovery: original class: jade.deps.eLz.YXWjGcywLT
package jade.client.gui;

public final class YXWjGcywLT {
   private YXWjGcywLT() {
   }

   public static boolean IGGd(float var0, float var1, float var2, float var3, float var4) {
      return var0 > var2 && var0 < var2 + var4 && var1 > var3 - 1.0F && var1 < var3 + 12.0F;
   }

   public static boolean isPointInRect(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 < var2 + var4 && var1 >= var3 && var1 < var3 + var5;
   }

   public static float textCenterX(float var0, float var1) {
      return var0 + 4.0F + var1 * 0.5F;
   }

   public static float textBaselineY(float var0, float var1, boolean var2) {
      return var0 + var1 + (var2 ? 3.0F : 4.0F);
   }

   public static int LyqqeVh(float var0) {
      return Math.max(6, Math.round(var0) - 1);
   }

   public static float rightAlignedX(float var0, float var1, int var2, int var3) {
      return var0 + var1 - var2 - var3;
   }

   public static float centeredY(float var0, float var1, int var2) {
      return var0 + (var1 - var2) / 2.0F;
   }
}
