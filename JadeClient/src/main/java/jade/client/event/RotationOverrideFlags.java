// Jade recovery: original class: jade.deps.eLz.noXgW5y
package jade.client.event;

public final class RotationOverrideFlags {
   private RotationOverrideFlags() {
   }

   public static void markYawOverride(float var0) {
      UpdateWalkingPlayerEvent.setYawOverrideRequested(true);
      UpdateWalkingPlayerEvent.QhtJiq = true;
      UpdateWalkingPlayerEvent.requestedYaw = var0;
   }

   public static void markPitchOverride() {
      UpdateWalkingPlayerEvent.QhtJiq = true;
   }
}
