// Jade recovery: original class: jade.deps.eLz.InFWREB
package jade.client.module.minigames.autowho;

public final class DelayedStateTrigger {
   private static final int QJWms = Integer.MIN_VALUE;
   private int caAv = Integer.MIN_VALUE;
   private long fireAtMillis;

   public void reset() {
      this.caAv = Integer.MIN_VALUE;
      this.fireAtMillis = 0L;
   }

   public boolean shouldFire(int var1, long var2, long var4) {
      boolean var6 = var1 == 2 && this.caAv != 2;
      this.caAv = var1;
      if (var6) {
         this.fireAtMillis = var2 + var4;
      }

      if (this.fireAtMillis != 0L && var2 >= this.fireAtMillis) {
         this.fireAtMillis = 0L;
         return true;
      } else {
         return false;
      }
   }
}
