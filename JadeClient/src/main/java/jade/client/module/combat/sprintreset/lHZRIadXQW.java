// Jade recovery: original class: jade.deps.eLz.lHZRIadXQW
package jade.client.module.combat.sprintreset;

public final class lHZRIadXQW {
   private long yiagT;
   private long sprintResumeTime;
   private boolean BCx8;
   private boolean wasSprinting;

   public void reset() {
      this.yiagT = 0L;
      this.sprintResumeTime = 0L;
      this.BCx8 = false;
      this.wasSprinting = false;
   }

   public void onSprintResumed(long var1, boolean var3) {
      if (this.BCx8 && var3 && !this.wasSprinting) {
         this.sprintResumeTime = var1;
         this.BCx8 = false;
      }
   }

   public void IHUk(boolean var1) {
      this.wasSprinting = var1;
   }

   public boolean hasResetDelayElapsed(long var1) {
      if (this.yiagT > 0L && var1 >= this.yiagT) {
         this.yiagT = 0L;
         return true;
      } else {
         return false;
      }
   }

   public void beginResetCycle(long var1, boolean var3) {
      this.BCx8 = var3;
      if (!var3) {
         this.sprintResumeTime = var1;
      }
   }

   public boolean dgN7(long var1, long var3) {
      return this.yiagT <= 0L && (this.sprintResumeTime <= 0L || var1 - this.sprintResumeTime >= var3);
   }

   public void scheduleResetAt(long var1) {
      this.yiagT = var1;
   }
}
