// Jade recovery: original class: jade.deps.eLz.UgGkLY
package jade.client.module.combat.autoaim;

public final class TargetPrediction {
   private final double YQdwM9;
   private final double y;
   private final double z;
   private final double positionUncertainty;
   private final double lNl;
   private final String profileName;
   private final String movementLabel;

   public TargetPrediction(double var1, double var3, double var5, double var7, double var9, String var11, String var12) {
      this.YQdwM9 = var1;
      this.y = var3;
      this.z = var5;
      this.positionUncertainty = var7;
      this.lNl = var9;
      this.profileName = var11;
      this.movementLabel = var12;
   }

   public double RAYFZ() {
      return this.YQdwM9;
   }

   public double fmmQ() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public double QxKt49() {
      return this.positionUncertainty;
   }

   public double getConfidence() {
      return this.lNl;
   }

   public String getProfileName() {
      return this.profileName;
   }

   public String RflLg() {
      return this.movementLabel;
   }
}
