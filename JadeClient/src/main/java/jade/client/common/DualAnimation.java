// Jade recovery: original class: jade.deps.eLz.b76uirL
package jade.client.common;

import java.util.function.Supplier;

public final class DualAnimation {
   private final Supplier<Animation> supplier;
   private Animation animation;
   private float currentValue;
   private float bM9;
   private float primaryDuration;
   private float secondaryDuration;

   public DualAnimation(float var1) {
      this(var1, DualAnimation::yajW);
   }

   public DualAnimation(float var1, Supplier<Animation> var2) {
      this.supplier = var2;
      this.currentValue = this.primaryDuration = var1;
   }

   public Animation retarget(float var1, float var2) {
      this.primaryDuration = var1;
      this.secondaryDuration = var2;
      Animation var3 = this.supplier.get();
      var3.restart();
      this.animation = this.supplier.get();
      this.animation.restart();
      return var3;
   }

   public static Animation expireIfStale(Animation var0, long var1) {
      return var0 != null && var1 - var0.startTimeMillis >= 280L ? null : var0;
   }

   public void tickAnimations(long var1) {
      this.animation = expireIfStale(this.animation, var1);
   }

   public boolean TXCvi1() {
      return this.animation != null;
   }

   public float getCurrentValue() {
      return this.currentValue;
   }

   public void WUjb(float var1) {
      this.currentValue = var1;
   }

   public void clearAnimation() {
      this.animation = null;
   }

   public float getSecondaryValue(float var1) {
      return this.animation == null ? var1 : this.bM9;
   }

   public float xhIe2(Animation var1, boolean var2, float var3, float var4, float var5) {
      float var6 = var2 ? var3 : var4;
      float var7 = var1 == null ? var3 : var1.computeEasedValue(this.primaryDuration, var6, 1);
      if (var1 != null && (var2 ? var7 > var6 : var7 < var6)) {
         var7 = var6;
      }

      this.currentValue = var7;
      this.bM9 = this.animation == null ? var5 : this.animation.computeEasedValue(this.secondaryDuration, var5, 1);
      return var7;
   }

   private static Animation yajW() {
      return new Animation(250.0F);
   }
}
