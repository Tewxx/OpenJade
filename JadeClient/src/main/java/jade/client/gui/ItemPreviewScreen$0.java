// Jade recovery: original class: jade.deps.eLz.agotcbK2L$0
package jade.client.gui;

public final class ItemPreviewScreen$0 {
   protected static final ItemPreviewScreen$0 EMPTY = new ItemPreviewScreen$0(
      0, 0, 0, 0
   );
   protected final int CONop;
   protected final int YHn95;
   protected final int NhA;
   protected final int crXyrt;

   protected ItemPreviewScreen$0(int var1, int var2, int var3, int var4) {
      this.CONop = var1;
      this.YHn95 = var2;
      this.NhA = var3;
      this.crXyrt = var4;
   }

   protected int getRight() {
      return this.CONop + this.NhA;
   }

   protected int dsyw() {
      return this.YHn95 + this.crXyrt;
   }

   protected boolean contains(int var1, int var2) {
      return this.NhA > 0 && this.crXyrt > 0 && var1 >= this.CONop && var1 < this.getRight() && var2 >= this.YHn95 && var2 < this.dsyw();
   }
}
