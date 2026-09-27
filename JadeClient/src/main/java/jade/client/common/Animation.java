// Jade recovery: original class: jade.deps.eLz.WAbWVS8
package jade.client.common;

import java.util.function.LongSupplier;

public class Animation {
   public float durationMillis;
   public long startTimeMillis;
   public float vEqbfj = Float.NaN;
   private final LongSupplier longSupplier;

   public Animation(float var1) {
      this(var1, System::currentTimeMillis);
   }

   public Animation(float var1, LongSupplier var2) {
      this.durationMillis = var1;
      this.longSupplier = var2;
   }

   public void restart() {
      this.vEqbfj = Float.NaN;
      this.startTimeMillis = this.longSupplier.getAsLong();
   }

   public float computeEasedValue(float var1, float var2, int var3) {
      if (this.vEqbfj == var2) {
         return this.vEqbfj;
      } else {
         float var4 = (float)(this.longSupplier.getAsLong() - this.startTimeMillis) / this.durationMillis;
         float var5 = var3 == 4 ? this.easeInOutQuad(var4) : Easing.easeByType(var3, var4);
         float var6 = var1 + var5 * (var2 - var1);
         boolean var7 = var2 > var1 ? var6 > var2 : var2 < var1 && var6 < var2;
         if (var7) {
            var6 = var2;
         }

         if (var6 == var2) {
            this.vEqbfj = var6;
         }

         return var6;
      }
   }

   public int computeEasedInt(int var1, int var2, int var3) {
      return Math.round(this.computeEasedValue(var1, var2, var3));
   }

   public float easeInOutQuad(float var1) {
      return Easing.quadInOut(var1);
   }
}
