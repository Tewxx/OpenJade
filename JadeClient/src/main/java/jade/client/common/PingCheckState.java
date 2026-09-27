// Jade recovery: original class: jade.deps.eLz.ekVIa3A
package jade.client.common;

public final class PingCheckState {
   private boolean messageModePending;
   private long messageRequestTime;
   private boolean commandModePending;
   private long Spb;

   public boolean amuX() {
      return this.messageModePending ^ this.commandModePending;
   }

   public boolean TBUb() {
      return this.messageModePending;
   }

   public boolean isCommandModePending() {
      return this.commandModePending;
   }

   public void markRequestSent(boolean var1, long var2) {
      if (var1) {
         this.commandModePending = true;
         this.Spb = var2;
      } else {
         this.messageModePending = true;
         this.messageRequestTime = var2;
      }
   }

   public int getElapsedMillis(boolean var1, long var2) {
      long var4 = var1 ? this.Spb : this.messageRequestTime;
      return Math.max(0, (int)(var2 - var4) - 20);
   }

   public void clearPending(boolean var1) {
      if (var1) {
         this.commandModePending = false;
         this.Spb = 0L;
      } else {
         this.messageModePending = false;
         this.messageRequestTime = 0L;
      }
   }

   public void clearAll() {
      this.clearPending(false);
      this.clearPending(true);
   }
}
