// Jade recovery: original class: jade.deps.eLz.AdWibXxhqv
package jade.client.module.combat.hitselect;

public final class PlayerHitTracker {
   private boolean LqWw;
   private int lastServerAttackTick = -1;
   private int pendingServerAttackTick = -1;
   private int lastAttackTick = -1;
   private int attackCooldownEndTick = -1;
   private int DTkiJ;
   private int lastHurtTimeChangeTick = -1;
   private int lastHurtTime;

   public PlayerHitTracker(int var1) {
      this.DTkiJ = var1;
   }

   public void LDYAtZ() {
      this.LqWw = true;
   }

   public boolean hasTakenDamage() {
      return this.LqWw;
   }

   public void trackAttackConfirmation(int var1, int var2, int var3, int var4) {
      if (this.pendingServerAttackTick >= 0 && var2 - this.pendingServerAttackTick > var3) {
         this.pendingServerAttackTick = -1;
      }

      if (this.pendingServerAttackTick >= 0 && var1 > this.DTkiJ) {
         this.pendingServerAttackTick = -1;
         this.lastServerAttackTick = var2;
         this.lastHurtTime = var4;
         this.lastHurtTimeChangeTick = var2;
      }

      this.DTkiJ = var1;
   }

   public int computeAttackTimingFlags(boolean var1, int var2, int var3, int var4, int var5, int var6) {
      if (var1) {
         return this.lastServerAttackTick >= 0 && var2 - this.lastServerAttackTick < var4 ? var5 : 0;
      } else {
         return this.isWithinAttackCooldown(var2) && var3 > 0 && var2 - this.lastAttackTick < var3 ? var6 : 0;
      }
   }

   public boolean ngDbe(int var1, int var2, double var3) {
      if (var1 == 0) {
         this.lastHurtTime = 0;
         this.lastHurtTimeChangeTick = -1;
         return false;
      } else if (var3 <= 0.0) {
         this.lastHurtTime = var1;
         this.lastHurtTimeChangeTick = var2;
         return false;
      } else {
         if (var1 != this.lastHurtTime) {
            this.lastHurtTime = var1;
            this.lastHurtTimeChangeTick = var2;
         } else if (this.lastHurtTimeChangeTick < 0) {
            this.lastHurtTimeChangeTick = var2;
         }

         int var5 = tIrjmLtOv.millisToTicks(var3);
         return var5 > 0 && var2 - this.lastHurtTimeChangeTick < var5;
      }
   }

   public void onLocalAttack(boolean var1, int var2, int var3) {
      if (var1) {
         this.pendingServerAttackTick = var2;
         this.lastServerAttackTick = -1;
      } else if (!this.isWithinAttackCooldown(var2)) {
         this.startAttackCooldown(var2, var3);
      }
   }

   public boolean isWithinAttackCooldown(int var1) {
      return this.attackCooldownEndTick >= 0 && var1 < this.attackCooldownEndTick;
   }

   private void startAttackCooldown(int var1, int var2) {
      int var3 = Math.max(1, var2);
      this.lastAttackTick = var1;
      this.attackCooldownEndTick = var1 + var3;
   }
}
