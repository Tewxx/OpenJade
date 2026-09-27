// Jade recovery: original class: jade.deps.eLz.F1yPDwD$5
package jade.client.gui;

public final class TabStatsScreen$5 {
   public final int x;
   public final int Yhf9;
   public final int width;
   public final int PWGp;

   public TabStatsScreen$5(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.Yhf9 = var2;
      this.width = var3;
      this.PWGp = var4;
   }

   public static TabStatsScreen$5 vNij() {
      return new TabStatsScreen$5(-1000, -1000, 0, 0);
   }

   public boolean gUw8(int var1, int var2) {
      return var1 >= this.x && var1 <= this.x + this.width && var2 >= this.Yhf9 && var2 <= this.Yhf9 + this.PWGp;
   }
}
