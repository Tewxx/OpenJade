// Jade recovery: original class: jade.deps.eLz.tt8L60TT
package jade.client.module.combat.hitselect;

public final class LocalHurtTracker {
   private int lastHurtTime;
   private boolean knockbackActive;
   private boolean lJ3;
   private int firstHitWaitStartTick = -1;
   private boolean damagedSinceWait;

   public boolean YLCHh(int var1, boolean var2) {
      boolean var3 = var1 > this.lastHurtTime;
      if (var3) {
         this.damagedSinceWait = this.lJ3 || this.damagedSinceWait;
         this.knockbackActive = true;
      }

      if (this.knockbackActive && var2 && !var3) {
         this.knockbackActive = false;
      }

      this.lastHurtTime = var1;
      return var3;
   }

   public void startFirstHitWait(boolean var1, int var2) {
      if (!var1) {
         this.resetFirstHitWait();
      } else if (!this.lJ3) {
         this.lJ3 = true;
         this.firstHitWaitStartTick = var2;
         this.damagedSinceWait = false;
      }
   }

   public boolean isWaitingForFirstHit(double var1, boolean var3, int var4) {
      if (!(var1 <= 0.0) && var3 && this.lJ3 && !this.damagedSinceWait && this.firstHitWaitStartTick >= 0) {
         int var5 = tIrjmLtOv.millisToTicks(var1);
         return var5 > 0 && var4 - this.firstHitWaitStartTick < var5;
      } else {
         return false;
      }
   }

   public boolean isKnockbackActive(int var1) {
      return this.knockbackActive || var1 > 0;
   }

   public void resetAll() {
      this.lastHurtTime = 0;
      this.knockbackActive = false;
      this.resetFirstHitWait();
   }

   private void resetFirstHitWait() {
      this.lJ3 = false;
      this.firstHitWaitStartTick = -1;
      this.damagedSinceWait = false;
   }
}
