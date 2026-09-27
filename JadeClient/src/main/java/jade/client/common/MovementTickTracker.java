// Jade recovery: original class: jade.deps.eLz.Qn6TB0Xfu
package jade.client.common;

public final class MovementTickTracker {
   private boolean wasOnGround;
   private boolean wasOnBlockBoundary;

   public MovementTickTracker$1 wWmd(boolean var1, boolean var2, boolean var3, int var4, int var5, int var6) {
      boolean var7 = this.wasOnGround;
      boolean var8 = this.wasOnBlockBoundary;
      this.wasOnGround = var1;
      this.wasOnBlockBoundary = var3;
      return new MovementTickTracker$1(var1 ? 0 : var4 + 1, var1 ? var5 + 1 : 0, var2 ? 0 : var6 + 1, var7, var8);
   }
}
