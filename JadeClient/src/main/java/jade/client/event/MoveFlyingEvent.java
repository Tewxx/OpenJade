// Jade recovery: original class: jade.deps.eLz.ArwJ0q
package jade.client.event;

public class MoveFlyingEvent extends Event {
   private final MoveFlyingState motionState;

   public MoveFlyingEvent(float var1, float var2, float var3, float var4) {
      this.motionState = new MoveFlyingState(var1, var2, var3, var4);
   }

   public float getStrafe() {
      return this.motionState.getStrafe();
   }

   public void setStrafe(float var1) {
      this.motionState.WkiuN(var1);
   }

   public float getForward() {
      return this.motionState.KQXwp();
   }

   public void setForward(float var1) {
      this.motionState.setForward(var1);
   }

   public float getFriction() {
      return this.motionState.BgmD5();
   }

   public void setFriction(float var1) {
      this.motionState.setFriction(var1);
   }

   public float osuUy7() {
      return this.motionState.getYaw();
   }

   public void setYaw(float var1) {
      this.motionState.setYaw(var1);
   }
}
