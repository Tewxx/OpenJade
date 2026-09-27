// Jade recovery: original class: jade.deps.eLz.oQT2TPlof
package jade.client.setting;

import jade.client.common.Animation;

public final class SmoothFloatAnimator {
   private static final float DiMn = 250.0F;
   private Animation animation;
   private float YPxva;
   private float startValue;
   private float targetValue;

   public float getAnimatedValue() {
      if (this.animation == null) {
         return this.YPxva;
      } else if ((float)(System.currentTimeMillis() - this.animation.startTimeMillis) >= 280.0F) {
         return this.BFje();
      } else {
         this.YPxva = this.animation.computeEasedValue(this.startValue, this.targetValue, 1);
         return this.YPxva == this.targetValue ? this.BFje() : this.YPxva;
      }
   }

   public void animateTo(float var1) {
      if (var1 != this.targetValue) {
         this.startValue = this.getAnimatedValue();
         this.targetValue = var1;
         this.animation = new Animation(250.0F);
         this.animation.restart();
      }
   }

   public void resetAnimation() {
      this.animation = null;
      this.YPxva = this.startValue = this.targetValue = 0.0F;
   }

   private float BFje() {
      this.animation = null;
      this.YPxva = this.startValue = this.targetValue;
      return this.YPxva;
   }
}
