// Jade recovery: original class: jade.deps.eLz.uxmnBlj
package jade.client.event;

public class MoveInputEvent extends Event {
   private final MoveInputState moveInputState;

   public MoveInputEvent(float var1, float var2, boolean var3, boolean var4, double var5) {
      this.moveInputState = new MoveInputState(var1, var2, var3, var4, var5);
   }

   public float getMoveForward() {
      return this.moveInputState.getMoveForward();
   }

   public void setMoveForward(float var1) {
      this.moveInputState.setMoveForward(var1);
   }

   public float AfWugH() {
      return this.moveInputState.goei();
   }

   public void setMoveStrafe(float var1) {
      this.moveInputState.setMoveStrafe(var1);
   }

   public boolean isJumping() {
      return this.moveInputState.isJumping();
   }

   public void setJumping(boolean var1) {
      this.moveInputState.setJump(var1);
   }

   public boolean isSneaking() {
      return this.moveInputState.dLzmStk();
   }

   public void setSneaking(boolean var1) {
      this.moveInputState.setSneak(var1);
   }

   public double BFAN() {
      return this.moveInputState.foxnBg();
   }

   public void KOncH(double var1) {
      this.moveInputState.AKGBku(var1);
   }
}
