// Jade recovery: original class: jade.deps.eLz.Bz6LtyS$13
package jade.client.module.minigames;

import jade.client.common.IFont;

public final class Overlay$13 {
   private final IFont iFont;
   private final int[] columnWidths;
   private final int width;
   private final int llj;
   private final int RakS3;
   private final int usdH;
   private final int paddingX;
   private final int paddingY;
   private final int JPi;

   Overlay$13(IFont var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this.iFont = var1;
      this.columnWidths = var2;
      this.width = var3;
      this.llj = var4;
      this.RakS3 = var5;
      this.usdH = var6;
      this.paddingX = var7;
      this.paddingY = var8;
      this.JPi = var9;
   }

   public static int getWidth(Overlay$13 var0) {
      return var0.width;
   }

   public static int getHeight(Overlay$13 var0) {
      return var0.llj;
   }

   public static int getHeaderHeight(Overlay$13 var0) {
      return var0.RakS3;
   }

   public static int getPaddingY(Overlay$13 var0) {
      return var0.paddingY;
   }

   public static IFont getFont(Overlay$13 var0) {
      return var0.iFont;
   }

   public static int getPaddingX(Overlay$13 var0) {
      return var0.paddingX;
   }

   public static int[] JxeeY(Overlay$13 var0) {
      return var0.columnWidths;
   }

   public static int getColumnGap(Overlay$13 var0) {
      return var0.JPi;
   }

   public static int getRowHeight(Overlay$13 var0) {
      return var0.usdH;
   }
}
