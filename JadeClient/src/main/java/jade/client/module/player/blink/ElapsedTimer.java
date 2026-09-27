// Jade recovery: original class: jade.deps.eLz.KnG62lipF
package jade.client.module.player.blink;

public final class ElapsedTimer {
   private long startTime;

   public void startAt(long var1) {
      this.startTime = var1;
   }

   public boolean hasElapsed(long var1, long var3) {
      return var1 - this.startTime >= var3;
   }

   public float getProgressFraction(long var1, long var3) {
      long var5 = Math.max(1L, var3);
      float var7 = (float)(var1 - this.startTime) / (float)var5;
      return Math.max(0.0F, Math.min(1.0F, var7));
   }
}
