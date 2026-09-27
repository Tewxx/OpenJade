// Jade recovery: original class: jade.deps.eLz.gieRYmjnz$1
package jade.client.module.combat.autoblock;

public final class gieRYmjnz$1 {
   private final double approachDistance;
   private final double closingDistancePerTick;
   private final boolean targetMovingAway;

   gieRYmjnz$1(double var1, double var3, boolean var5) {
      this.approachDistance = var1;
      this.closingDistancePerTick = var3;
      this.targetMovingAway = var5;
   }

   public static double getApproachDistance(gieRYmjnz$1 var0) {
      return var0.approachDistance;
   }

   public static double VGdeC(gieRYmjnz$1 var0) {
      return var0.closingDistancePerTick;
   }

   public static boolean isTargetMovingAway(gieRYmjnz$1 var0) {
      return var0.targetMovingAway;
   }
}
