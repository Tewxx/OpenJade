// Jade recovery: original class: jade.deps.eLz.iogd7ZD
package jade.client.module.player.mlg;

public final class MlgState {
   private static final long xMvovV = 500L;
   private static final long PICKUP_DELAY_MS = 150L;
   private long lastPlaceTime;
   private boolean pickUpPending;
   private int Hlw = -1;

   public boolean isReadyToRefill(long var1) {
      return AOGy(this.lastPlaceTime, var1) >= 500L;
   }

   public boolean isRecentlyPlaced(long var1) {
      return AOGy(this.lastPlaceTime, var1) < 500L;
   }

   public void onWaterPlaced(long var1, boolean var3) {
      this.lastPlaceTime = var1;
      this.pickUpPending = var3;
   }

   public boolean shouldPickUpWater(long var1) {
      return this.pickUpPending && AOGy(this.lastPlaceTime, var1) > 150L;
   }

   public void TcFaeva() {
      this.pickUpPending = false;
   }

   public void NOMpoo(int var1) {
      this.Hlw = var1;
   }

   public int consumeSavedSlot() {
      int var1 = this.Hlw;
      this.Hlw = -1;
      return var1;
   }

   public void reset() {
      this.lastPlaceTime = 0L;
      this.pickUpPending = false;
   }

   private static long AOGy(long var0, long var2) {
      return Math.abs(var2 - var0);
   }
}
