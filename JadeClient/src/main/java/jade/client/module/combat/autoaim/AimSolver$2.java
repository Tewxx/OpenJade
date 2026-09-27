// Jade recovery: original class: jade.deps.eLz.gx7O5zKt$2
package jade.client.module.combat.autoaim;

public enum AimSolver$2 {
   BOW(0.99, 0.05, 0.3, 70),
   FISHING_ROD(0.92, 0.04, 0.3, 35);

   public final double drag;
   public final double gravity;
   public final double hitExpansion;
   public final int maxTicks;

   AimSolver$2(double var3, double var5, double var7, int var9) {
      this.drag = var3;
      this.gravity = var5;
      this.hitExpansion = var7;
      this.maxTicks = var9;
   }

   static {
      AimSolver$2[] var10000 = new AimSolver$2[2];
      var10000[0] = BOW;
      var10000[1] = FISHING_ROD;
   }
}
