// Jade recovery: original class: jade.deps.eLz.GbTRNg6R
package jade.client.common;

public final class PacketThrottleState {
   private int rF9;
   private int CIl;
   private int tsle;
   private long lastPlaceTime;

   public void aiof3() {
      this.CIl = 5;
   }

   public boolean consumeAttackPulse(boolean var1) {
      if (!var1) {
         return false;
      } else if (this.CIl <= 0) {
         return false;
      } else {
         this.CIl--;
         return true;
      }
   }

   public boolean consumeAlternateTick(boolean var1) {
      if (!var1) {
         return false;
      } else {
         this.rF9++;
         if (this.rF9 < 1) {
            return true;
         } else {
            this.rF9 = 0;
            return false;
         }
      }
   }

   public PacketThrottleState$1 throttleEveryEighthCall(boolean var1, boolean var2) {
      if (var1 && ++this.tsle >= 8) {
         this.tsle = 0;
         return new PacketThrottleState$1(false, false);
      } else {
         return new PacketThrottleState$1(var1, var2);
      }
   }

   public void setPlaceTime(long var1) {
      this.lastPlaceTime = var1;
   }

   public PacketThrottleState$2 RPUpBh(long var1, long var3, boolean var5, boolean var6) {
      if (this.lastPlaceTime > 0L && var1 - this.lastPlaceTime > var3 / 3L) {
         var6 = false;
      }

      if (this.lastPlaceTime > 0L && var1 - this.lastPlaceTime > var3) {
         this.lastPlaceTime = 0L;
         var5 = false;
         var6 = false;
      }

      return new PacketThrottleState$2(var5, var6);
   }
}
