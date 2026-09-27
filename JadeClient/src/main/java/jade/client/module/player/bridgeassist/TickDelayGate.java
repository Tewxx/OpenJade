// Jade recovery: original class: jade.deps.eLz.fVrEoGCh9m
package jade.client.module.player.bridgeassist;

import java.util.function.DoubleSupplier;

public final class TickDelayGate {
   private int UhuI0 = -1;
   private int HGBeM2 = -1;
   private int ity = -1;
   private int releaseDelayTicks = -1;

   public void startJumpHold(int var1, double var2, DoubleSupplier var4) {
      this.UhuI0 = var1;
      this.HGBeM2 = Math.max(1, randomizedTickDelay(var2 / 50.0, var4));
   }

   public boolean shouldKeepSneaking(int var1, double var2, DoubleSupplier var4) {
      if (this.ity < 0 && this.UhuI0 < 0) {
         this.ity = var1;
         this.releaseDelayTicks = randomizedTickDelay((var2 - 50.0) / 50.0, var4);
      }

      return this.UhuI0 >= 0 && var1 - this.UhuI0 < this.HGBeM2 ? true : this.ity >= 0 && var1 - this.ity < this.releaseDelayTicks;
   }

   public void clearReleaseDelay() {
      this.ity = -1;
   }

   public void clearAll() {
      this.UhuI0 = -1;
      this.HGBeM2 = -1;
      this.ity = -1;
      this.releaseDelayTicks = -1;
   }

   private static int randomizedTickDelay(double var0, DoubleSupplier var2) {
      int var3 = (int)var0;
      return var3 + (var2.getAsDouble() < var0 - var3 ? 1 : 0);
   }
}
