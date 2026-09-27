// Jade recovery: original class: jade.deps.eLz.qwnjQmlG
package jade.client.event;

public final class MoveInputState {
   private float moveForward;
   private float moveStrafe;
   private boolean jump;
   private boolean YxkmI;
   private double slowdownFactor;

   public MoveInputState(float var1, float var2, boolean var3, boolean var4, double var5) {
      this.moveForward = var1;
      this.moveStrafe = var2;
      this.jump = var3;
      this.YxkmI = var4;
      this.slowdownFactor = var5;
   }

   public float getMoveForward() {
      return this.moveForward;
   }

   public void setMoveForward(float var1) {
      this.moveForward = var1;
   }

   public float goei() {
      return this.moveStrafe;
   }

   public void setMoveStrafe(float var1) {
      this.moveStrafe = var1;
   }

   public boolean isJumping() {
      return this.jump;
   }

   public void setJump(boolean var1) {
      this.jump = var1;
   }

   public boolean dLzmStk() {
      return this.YxkmI;
   }

   public void setSneak(boolean var1) {
      this.YxkmI = var1;
   }

   public double foxnBg() {
      return this.slowdownFactor;
   }

   public void AKGBku(double var1) {
      this.slowdownFactor = var1;
   }
}
