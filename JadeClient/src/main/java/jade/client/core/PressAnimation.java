// Jade recovery: original class: jade.deps.eLz.N42f8Bg2Q
package jade.client.core;

public final class PressAnimation {
   private boolean lastState = true;
   private long lastChangeMillis;
   private int alphaValue = 255;
   private double Haa = 1.0;

   public boolean hasStateChanged(boolean var1) {
      return var1 != this.lastState;
   }

   public void updateState(boolean var1, long var2) {
      this.lastState = var1;
      this.lastChangeMillis = var2;
   }

   public int getAlpha(boolean var1, long var2) {
      long var4 = var2 - this.lastChangeMillis;
      this.alphaValue = var1 ? Math.min(255, (int)(2L * var4)) : Math.max(0, 255 - (int)(2L * var4));
      return this.alphaValue;
   }

   public double JCj0(boolean var1, long var2) {
      double var4 = var2 - this.lastChangeMillis;
      this.Haa = var1 ? Math.max(0.0, 1.0 - var4 / 20.0) : Math.min(1.0, var4 / 20.0);
      return this.Haa;
   }
}
