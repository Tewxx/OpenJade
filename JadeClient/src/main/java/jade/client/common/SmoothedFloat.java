// Jade recovery: original class: jade.deps.eLz.VFnfWUH
package jade.client.common;

public final class SmoothedFloat {
   private float currentValue;
   private float targetValue;
   private long transitionStartMillis;

   public SmoothedFloat(float var1) {
      this.currentValue = this.targetValue = var1;
   }

   public float smoothTowards(float var1) {
      return this.smoothTowardsAt(var1, System.currentTimeMillis());
   }

   public float smoothTowardsAt(float var1, long var2) {
      float var4 = Math.max(0.0F, Math.min(1.0F, (float)(var2 - this.transitionStartMillis) / 260.0F));
      float var5 = this.currentValue + (this.targetValue - this.currentValue) * GuiTheme.ANLBFU(var4);
      if (var1 != this.targetValue) {
         this.currentValue = var5;
         this.targetValue = var1;
         this.transitionStartMillis = var2;
      }

      return var5;
   }
}
