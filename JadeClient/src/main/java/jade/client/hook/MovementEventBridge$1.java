// Jade recovery: original class: jade.deps.eLz.kzqzwW$1
package jade.client.hook;

public final class MovementEventBridge$1 {
   public final float jlX;
   public final float updateDistanceResult;
   public final float forcedRotationYawHead;

   MovementEventBridge$1(float var1, float var2, float var3) {
      this.jlX = var1;
      this.updateDistanceResult = var2;
      this.forcedRotationYawHead = var3;
   }

   public boolean hasForcedYaw() {
      return !Float.isNaN(this.forcedRotationYawHead);
   }
}
