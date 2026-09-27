// Jade recovery: original class: jade.deps.eLz.Kmj1I5JDU
package jade.client.module.minigames.opsec;

public final class QuickBuyEditCooldown {
   public static final long COOLDOWN_MILLIS = 3000L;
   private int GJqH = -2147483648;
   private long EUV;
   private boolean editObserved;

   public void startCooldown(int var1, long var2) {
      this.GJqH = var1;
      this.EUV = var2 + 3000L;
      this.editObserved = false;
   }

   public boolean JcOu(int var1, long var2) {
      return this.GJqH == var1 && var2 <= this.EUV;
   }

   public void markEditObserved(int var1, long var2) {
      if (this.JcOu(var1, var2)) {
         this.editObserved = true;
      }
   }

   public boolean FvuiK(int var1, long var2) {
      return this.JcOu(var1, var2) && this.editObserved;
   }

   public void clearAfterSync() {
      this.reset();
   }

   public void reset() {
      this.GJqH = Integer.MIN_VALUE;
      this.EUV = 0L;
      this.editObserved = false;
   }
}
