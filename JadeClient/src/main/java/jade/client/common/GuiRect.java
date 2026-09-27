// Jade recovery: original class: jade.deps.eLz.MZAdvPER3
package jade.client.common;

public final class GuiRect {
   public final int x;
   public final int ufe;
   public final int busF;
   public final int HfS;

   public GuiRect(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.ufe = var2;
      this.busF = var3;
      this.HfS = var4;
   }

   public boolean contains(int var1, int var2) {
      return var1 >= this.x && var1 < this.x + this.busF && var2 >= this.ufe && var2 < this.ufe + this.HfS;
   }

   public int getCenterX() {
      return this.x + this.busF / 2;
   }

   public int getCenterY() {
      return this.ufe + this.HfS / 2;
   }
}
