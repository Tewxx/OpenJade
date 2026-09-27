// Jade recovery: original class: jade.deps.eLz.v221s0rF
package jade.client.common;

public final class FloatTransition {
   private static final long DURATION_MILLIS = 250L;
   private static final long TIMEOUT_MILLIS = 280L;
   private float currentValue;
   private float startValue;
   private float targetValue;
   private long startTimeMillis;
   private boolean animating;

   public FloatTransition(float var1) {
      this.setImmediate(var1);
   }

   public void startTransition(float var1, float var2, long var3) {
      this.currentValue = var1;
      this.startValue = var1;
      this.targetValue = var2;
      this.startTimeMillis = var3;
      this.animating = true;
   }

   public void updateTransition(long var1) {
      if (this.animating) {
         if (var1 - this.startTimeMillis >= 280L) {
            this.completeTransition();
         } else {
            float var3 = (float)(var1 - this.startTimeMillis) / 250.0F;
            float var4 = this.startValue + Easing.easeByType(1, var3) * (this.targetValue - this.startValue);
            this.currentValue = this.targetValue > this.startValue ? Math.min(var4, this.targetValue) : Math.max(var4, this.targetValue);
            if (this.currentValue == this.targetValue) {
               this.completeTransition();
            }
         }
      }
   }

   public void setImmediate(float var1) {
      this.currentValue = var1;
      this.startValue = var1;
      this.targetValue = var1;
      this.animating = false;
   }

   public float getValue() {
      return this.currentValue;
   }

   public float KADbe() {
      return this.targetValue;
   }

   public boolean isAnimating() {
      return this.animating;
   }

   private void completeTransition() {
      this.currentValue = this.targetValue;
      this.startValue = this.targetValue;
      this.animating = false;
   }
}
