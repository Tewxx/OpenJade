// Jade recovery: original class: jade.deps.eLz.honFrriZh
package jade.client.event;

public class UpdateWalkingPlayerEvent extends Event {
   public double DQKUC;
   private final PlayerMotionState motionState;
   private static boolean yawOverrideRequested;
   public static boolean QhtJiq;
   public static float requestedYaw;

   public UpdateWalkingPlayerEvent(double var1, double var3, double var5, float var7, float var8, boolean var9, boolean var10, boolean var11) {
      this.DQKUC = var3;
      this.motionState = new PlayerMotionState(var1, var5, var7, var8, var9, var10, var11);
   }

   public double getX() {
      return this.motionState.getX();
   }

   public double getY() {
      return this.DQKUC;
   }

   public double getZ() {
      return this.motionState.IokwI();
   }

   public float getYaw() {
      return this.motionState.IAxBa();
   }

   public float getPitch() {
      return this.motionState.xfdyY();
   }

   public boolean isOnGround() {
      return this.motionState.isOnGround();
   }

   public void FKrUg(double var1) {
      this.motionState.setX(var1);
   }

   public void setY(double var1) {
      this.DQKUC = var1;
   }

   public void setZ(double var1) {
      this.motionState.setZ(var1);
   }

   public void setYaw(float var1) {
      this.motionState.setYaw(var1);
      RotationOverrideFlags.markYawOverride(var1);
   }

   public void ebfpOkg(float var1, float var2) {
      this.motionState.setYaw(var1);
      this.motionState.ZNOu7(var2);
      RotationOverrideFlags.markYawOverride(var1);
   }

   public void setPitch(float var1) {
      this.motionState.ZNOu7(var1);
      RotationOverrideFlags.markPitchOverride();
   }

   public void setOnGround(boolean var1) {
      this.motionState.setOnGround(var1);
   }

   public static boolean isYawOverrideRequested() {
      return yawOverrideRequested;
   }

   public static void setYawOverrideRequested(boolean var0) {
      yawOverrideRequested = var0;
   }

   public boolean isSprinting() {
      return this.motionState.TLXdAi();
   }

   public void GLd2(boolean var1) {
      this.motionState.WbsK(var1);
   }

   public boolean isSneaking() {
      return this.motionState.isSneaking();
   }

   public void LWId(boolean var1) {
      this.motionState.DTHHo(var1);
   }
}
