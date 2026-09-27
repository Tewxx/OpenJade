// Jade recovery: original class: jade.deps.eLz.qsXn9y$31
package jade.client.gui;

public class HudEditorScreen$31 {
   public final int left;
   public final int top;
   public final int urr;
   public final int eFnoRh;

   public HudEditorScreen$31(int var1, int var2, int var3, int var4) {
      this.left = var1;
      this.top = var2;
      this.urr = var3;
      this.eFnoRh = var4;
   }

   public boolean contains(int var1, int var2) {
      return var1 >= this.left && var1 <= this.left + this.urr && var2 >= this.top && var2 <= this.top + this.eFnoRh;
   }
}
