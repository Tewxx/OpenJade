// Jade recovery: original class: jade.deps.eLz.LXEpIDt3l
package jade.client.module.combat.keepsprint;

public final class KeepSprintState {
   private static final int zUzu = 0;
   private static final int ryjEi = 1;
   private static final int EZQ = 2;
   private int state;
   private int stateTicks;
   private boolean processedThisTick;

   public void clearTickFlag() {
      this.processedThisTick = false;
   }

   public KeepSprintState$0 onAttack(boolean var1, boolean var2) {
      if (this.processedThisTick) {
         return KeepSprintState$0.NONE;
      } else {
         KeepSprintState$0 var3 = KeepSprintState$0.NONE;
         if (var1) {
            if (this.state == 0) {
               if (var2) {
                  this.state = 1;
                  var3 = KeepSprintState$0.CANCEL_ATTACK;
               } else {
                  this.state = 2;
               }

               this.stateTicks = 0;
            } else if (this.state == 1) {
               this.state = 2;
               this.stateTicks = 0;
               var3 = KeepSprintState$0.STOP_SPRINT;
            }
         }

         this.processedThisTick = true;
         return var3;
      }
   }

   public KeepSprintState$0 advanceState() {
      if (this.stateTicks > 5) {
         this.YWGs();
      }

      if (this.state == 1) {
         this.stateTicks++;
         return KeepSprintState$0.STOP_SPRINT;
      } else if (this.state == 2) {
         this.YWGs();
         return KeepSprintState$0.RESTORE_SPRINT;
      } else {
         return KeepSprintState$0.NONE;
      }
   }

   public KeepSprintState$0 getCurrentSprintAction() {
      if (this.state == 1) {
         return KeepSprintState$0.STOP_SPRINT;
      } else {
         return this.state == 2 ? KeepSprintState$0.RESTORE_SPRINT : KeepSprintState$0.NONE;
      }
   }

   public boolean isAwaitingRestore() {
      return this.state == 2;
   }

   public int FKAhqUw() {
      return this.state;
   }

   public int getStateTicks() {
      return this.stateTicks;
   }

   public void YWGs() {
      this.state = 0;
      this.stateTicks = 0;
      this.processedThisTick = false;
   }
}
