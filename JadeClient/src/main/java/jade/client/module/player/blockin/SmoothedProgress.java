// Jade recovery: original class: jade.deps.eLz.xLRU0EyYVx
package jade.client.module.player.blockin;

public final class SmoothedProgress {
   private float targetValue;
   private float lastTargetValue = -1.0F;
   private float XXt;
   private float transitionStartValue;
   private float targetFraction;
   private long transitionStartMillis;

   public void reset() {
      this.targetValue = 0.0F;
      this.lastTargetValue = -1.0F;
      this.XXt = 0.0F;
   }

   public void cilm() {
      this.XXt = 0.0F;
   }

   public void resetTarget() {
      this.targetValue = 0.0F;
   }

   public boolean hasTarget() {
      return this.targetValue > 0.0F;
   }

   public void updateTarget(float var1, long var2) {
      this.targetValue = var1;
      if (this.targetValue != this.lastTargetValue) {
         this.transitionStartValue = this.XXt;
         this.targetFraction = Math.max(0.0F, Math.min(1.0F, this.targetValue / 9.0F));
         this.transitionStartMillis = var2;
         this.lastTargetValue = this.targetValue;
      }
   }

   public float getSmoothedValue(long var1) {
      if (this.targetValue <= 0.0F) {
         this.XXt = 0.0F;
      } else if (var1 - this.transitionStartMillis >= 50L) {
         this.XXt = this.targetFraction;
      } else {
         float var3 = (float)(var1 - this.transitionStartMillis) / 50.0F;
         float var4 = var3 < 0.5F ? 2.0F * var3 * var3 : -1.0F + (4.0F - 2.0F * var3) * var3;
         this.XXt = this.transitionStartValue + (this.targetFraction - this.transitionStartValue) * var4;
      }

      return Math.max(0.0F, Math.min(1.0F, this.XXt));
   }
}
