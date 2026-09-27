// Jade recovery: original class: jade.deps.eLz.QSYz19rC
package jade.client.common;

public final class EasedBooleanTransition {
   private final long gNddR;
   private final long delayMillis;
   private float Etu;
   private float startValue;
   private float BYK;
   private long animationStartTime;
   private boolean animating;

   public EasedBooleanTransition(boolean var1, long var2, long var4) {
      this.gNddR = var2;
      this.delayMillis = var4;
      this.snapTo(var1);
   }

   public float getCurrentValue(long var1) {
      if (!this.animating) {
         return this.Etu;
      } else {
         long var3 = var1 - this.animationStartTime;
         if (var3 >= this.gNddR + this.delayMillis) {
            this.finishAnimation();
            return this.Etu;
         } else {
            float var5 = (float)var3 / (float)this.gNddR;
            this.Etu = this.startValue + Easing.easeByType(1, var5) * (this.BYK - this.startValue);
            if (this.BYK >= this.startValue && this.Etu >= this.BYK || this.BYK < this.startValue && this.Etu <= this.BYK) {
               this.finishAnimation();
            }

            return this.Etu;
         }
      }
   }

   public void animateTo(boolean var1, long var2) {
      this.startValue = this.getCurrentValue(var2);
      this.BYK = var1 ? 1.0F : 0.0F;
      this.animationStartTime = var2;
      this.animating = true;
   }

   public void snapTo(boolean var1) {
      this.Etu = var1 ? 1.0F : 0.0F;
      this.startValue = this.Etu;
      this.BYK = this.Etu;
      this.animating = false;
   }

   private void finishAnimation() {
      this.Etu = this.BYK;
      this.startValue = this.BYK;
      this.animating = false;
   }
}
