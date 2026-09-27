// Jade recovery: original class: jade.deps.eLz.Z1GcHZqmh
package jade.client.module.player.bridgeassist;

public final class PlacementHighlightTimer {
   private static final long FADE_IN_DURATION_MS = 120L;
   private static final long HOLD_DURATION_MS = 1000L;
   private static final long FADE_OUT_DURATION_MS = 300L;
   private long aaD;
   private long lastPlacementTime;

   public void onPlacement(long var1) {
      if (!this.isRecentPlacement(var1)) {
         this.aaD = var1;
      }

      this.lastPlacementTime = var1;
   }

   public boolean isRecentPlacement(long var1) {
      return this.lastPlacementTime > 0L && var1 - this.lastPlacementTime < 1300L;
   }

   public float getHighlightAlpha(long var1) {
      if (this.aaD > 0L && this.lastPlacementTime > 0L && var1 >= this.aaD) {
         long var3 = var1 - this.aaD;
         long var5 = var1 - this.lastPlacementTime;
         if (var3 < 120L) {
            return (float)var3 / 120.0F;
         } else if (var5 <= 1000L) {
            return 1.0F;
         } else if (var5 < 1300L) {
            return 1.0F - (float)(var5 - 1000L) / 300.0F;
         } else {
            this.DkfRq();
            return 0.0F;
         }
      } else {
         return 0.0F;
      }
   }

   public void DkfRq() {
      this.aaD = 0L;
      this.lastPlacementTime = 0L;
   }
}
