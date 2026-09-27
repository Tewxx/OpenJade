// Jade recovery: original class: jade.deps.eLz.DqGzEkR1m
package jade.client.common;

public final class RepeatCounter {
   private int rHi;

   public int nextRepeatCount(int var1, int var2) {
      int var3 = var1 == this.rHi ? var2 + 1 : 0;
      this.rHi = var1;
      return var3;
   }
}
