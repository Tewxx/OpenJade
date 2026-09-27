// Jade recovery: original class: jade.deps.eLz.aVWVANRws$3
package jade.client.gui;

public final class QuickBuyCopyScreen$3 {
   private static final QuickBuyCopyScreen$3 EMPTY = new QuickBuyCopyScreen$3(
      0,
      0,
      0,
      0
   );
   private final int x;
   private final int y;
   private final int rrykl7;
   private final int JLIyto;

   QuickBuyCopyScreen$3(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.y = var2;
      this.rrykl7 = var3;
      this.JLIyto = var4;
   }

   private int JTgH() {
      return this.x + this.rrykl7;
   }

   private int bottom() {
      return this.y + this.JLIyto;
   }

   private boolean IFeL(int var1, int var2) {
      return this.rrykl7 > 0 && this.JLIyto > 0 && var1 >= this.x && var1 < this.JTgH() && var2 >= this.y && var2 < this.bottom();
   }

   public static QuickBuyCopyScreen$3 getEmpty() {
      return EMPTY;
   }

   public static boolean contains(QuickBuyCopyScreen$3 var0, int var1, int var2) {
      return var0.IFeL(var1, var2);
   }

   public static int CPEz(QuickBuyCopyScreen$3 var0) {
      return var0.JTgH();
   }

   public static int ZHv7(QuickBuyCopyScreen$3 var0) {
      return var0.rrykl7;
   }

   public static int yLomwC(QuickBuyCopyScreen$3 var0) {
      return var0.x;
   }

   public static int BEneE(QuickBuyCopyScreen$3 var0) {
      return var0.y;
   }

   public static int getBottom(QuickBuyCopyScreen$3 var0) {
      return var0.bottom();
   }

   public static int XNvGx(QuickBuyCopyScreen$3 var0) {
      return var0.JLIyto;
   }
}
