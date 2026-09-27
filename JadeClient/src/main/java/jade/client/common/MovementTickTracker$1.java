// Jade recovery: original class: jade.deps.eLz.Qn6TB0Xfu$1
package jade.client.common;

public final class MovementTickTracker$1 {
   private final int airborneTicks;
   private final int groundedTicks;
   private final int idleTicks;
   private final boolean Qfu9;
   private final boolean wasOnBlockGrid;

   MovementTickTracker$1(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.airborneTicks = var1;
      this.groundedTicks = var2;
      this.idleTicks = var3;
      this.Qfu9 = var4;
      this.wasOnBlockGrid = var5;
   }

   public int getAirborneTicks() {
      return this.airborneTicks;
   }

   public int KynY0() {
      return this.groundedTicks;
   }

   public int getIdleTicks() {
      return this.idleTicks;
   }

   public boolean wasPreviouslyOnGround() {
      return this.Qfu9;
   }

   public boolean wasPreviouslyOnBlockGrid() {
      return this.wasOnBlockGrid;
   }
}
