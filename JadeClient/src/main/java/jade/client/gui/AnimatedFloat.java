// Jade recovery: original class: jade.deps.eLz.l3rt87Pw
package jade.client.gui;

import java.util.function.LongSupplier;

public class AnimatedFloat {
   private float jnM6;
   private float RBCLj;
   private long transitionStartTimeMillis;
   private final long animationDurationMs;
   private final LongSupplier longSupplier;

   public AnimatedFloat(long var1) {
      this(var1, System::currentTimeMillis);
   }

   public AnimatedFloat(long var1, LongSupplier var3) {
      this.animationDurationMs = var1;
      this.longSupplier = var3;
   }

   public void snapTo(float var1) {
      this.jnM6 = var1;
      this.RBCLj = var1;
      this.transitionStartTimeMillis = 0L;
   }

   public void setTargetValue(float var1) {
      this.beginTransitionTo(var1);
   }

   public void addToTarget(float var1) {
      this.beginTransitionTo(this.RBCLj + var1);
   }

   public void clampTarget(float var1, float var2) {
      this.RBCLj = Math.max(var1, Math.min(var2, this.RBCLj));
   }

   public float ORMWO() {
      if (this.transitionStartTimeMillis == 0L) {
         return this.RBCLj;
      } else {
         long var1 = this.longSupplier.getAsLong() - this.transitionStartTimeMillis;
         if (var1 >= this.animationDurationMs) {
            this.transitionStartTimeMillis = 0L;
            this.jnM6 = this.RBCLj;
            return this.RBCLj;
         } else {
            float var3 = (float)var1 / (float)this.animationDurationMs;
            return this.jnM6 + (this.RBCLj - this.jnM6) * EasingFunctions.easeOutExpo(var3);
         }
      }
   }

   public boolean isAnimating() {
      return this.transitionStartTimeMillis != 0L && this.longSupplier.getAsLong() - this.transitionStartTimeMillis < this.animationDurationMs;
   }

   public float getTargetValue() {
      return this.RBCLj;
   }

   private void beginTransitionTo(float var1) {
      this.jnM6 = this.ORMWO();
      this.RBCLj = var1;
      this.transitionStartTimeMillis = this.longSupplier.getAsLong();
   }
}
