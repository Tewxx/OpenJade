// Jade recovery: original class: jade.deps.eLz.EwASWs
package jade.client.common;

import java.util.function.IntSupplier;

public final class MouseScrollState {
   public static final int if0 = 1069;
   public static final int MOUSE_WHEEL_DOWN = 1070;
   private int XQyy;
   private boolean zgaSov;

   public void setScrollDelta(int var1) {
      this.XQyy = var1;
      this.zgaSov = true;
   }

   public boolean matchesScrollDirection(int var1, IntSupplier var2) {
      if (!this.zgaSov) {
         this.setScrollDelta(var2.getAsInt());
      }

      return var1 == 1069 && this.XQyy > 0 || var1 == 1070 && this.XQyy < 0;
   }

   public void QhLg() {
      this.XQyy = 0;
      this.zgaSov = false;
   }
}
