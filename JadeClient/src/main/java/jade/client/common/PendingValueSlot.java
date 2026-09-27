// Jade recovery: original class: jade.deps.eLz.YIELb52TIH
package jade.client.common;

public final class PendingValueSlot {
   private String pendingValue;

   public void VHAC(String var1) {
      if (this.pendingValue == null) {
         this.pendingValue = var1;
      }
   }

   public boolean qcsE1() {
      return this.pendingValue != null;
   }

   public String consumePendingValue() {
      String var1 = this.pendingValue;
      this.pendingValue = null;
      return var1;
   }

   public void clearPending() {
      this.pendingValue = null;
   }
}
