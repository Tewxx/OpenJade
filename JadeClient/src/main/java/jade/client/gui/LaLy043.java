// Jade recovery: original class: jade.deps.eLz.LaLy043
package jade.client.gui;

public final class LaLy043 {
   private static final long BLINK_INTERVAL_MILLIS = 500L;
   private long GpP;
   private boolean cursorVisible;

   public boolean tHdu(long var1) {
      if (var1 - this.GpP >= 500L) {
         this.GpP = var1;
         this.cursorVisible = !this.cursorVisible;
      }

      return this.cursorVisible;
   }

   public void resetBlink(long var1) {
      this.cursorVisible = true;
      this.GpP = var1;
   }

   public boolean isCursorVisible() {
      return this.cursorVisible;
   }
}
