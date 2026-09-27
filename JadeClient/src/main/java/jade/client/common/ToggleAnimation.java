// Jade recovery: original class: jade.deps.eLz.Ay1CFvZcZ
package jade.client.common;

public final class ToggleAnimation {
   private static final float nbK = 250.0F;
   private Animation animation;
   private float currentValue;
   private float VVka;
   private float targetValue;

   public float getAnimatedValue() {
      if (this.animation == null) {
         return this.currentValue;
      } else if ((float)(System.currentTimeMillis() - this.animation.startTimeMillis) >= 280.0F) {
         this.finishAnimation();
         return this.currentValue;
      } else {
         this.currentValue = this.animation.computeEasedValue(this.VVka, this.targetValue, 1);
         if (this.currentValue == this.targetValue) {
            this.finishAnimation();
         }

         return this.currentValue;
      }
   }

   public void eSrb(boolean var1) {
      this.VVka = this.getAnimatedValue();
      this.targetValue = var1 ? 1.0F : 0.0F;
      this.animation = new Animation(250.0F);
      this.animation.restart();
   }

   public void snapTo(boolean var1) {
      this.animation = null;
      this.currentValue = var1 ? 1.0F : 0.0F;
      this.VVka = this.currentValue;
      this.targetValue = this.currentValue;
   }

   private void finishAnimation() {
      this.animation = null;
      this.currentValue = this.targetValue;
      this.VVka = this.targetValue;
   }
}
