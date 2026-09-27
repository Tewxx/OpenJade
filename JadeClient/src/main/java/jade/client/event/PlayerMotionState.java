// Jade recovery: original class: jade.deps.eLz.q1zQdwneHT
package jade.client.event;

public final class PlayerMotionState {
   private double x;
   private double CxAd;
   private float yaw;
   private float jQc96;
   private boolean ojLy;
   private boolean pb3;
   private boolean sneaking;

   public PlayerMotionState(double var1, double var3, float var5, float var6, boolean var7, boolean var8, boolean var9) {
      this.x = var1;
      this.CxAd = var3;
      this.yaw = var5;
      this.jQc96 = var6;
      this.ojLy = var7;
      this.pb3 = var8;
      this.sneaking = var9;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public double IokwI() {
      return this.CxAd;
   }

   public void setZ(double var1) {
      this.CxAd = var1;
   }

   public float IAxBa() {
      return this.yaw;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public float xfdyY() {
      return this.jQc96;
   }

   public void ZNOu7(float var1) {
      this.jQc96 = var1;
   }

   public boolean isOnGround() {
      return this.ojLy;
   }

   public void setOnGround(boolean var1) {
      this.ojLy = var1;
   }

   public boolean TLXdAi() {
      return this.pb3;
   }

   public void WbsK(boolean var1) {
      this.pb3 = var1;
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   public void DTHHo(boolean var1) {
      this.sneaking = var1;
   }
}
