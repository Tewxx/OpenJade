// Jade recovery: original class: jade.deps.eLz.zoKba05
package jade.client.gui;

public final class KeyBindState {
   private final int defaultKey;
   private boolean tbC;
   private int nHgh;

   public KeyBindState(int var1) {
      this.defaultKey = var1;
      this.nHgh = var1;
   }

   public boolean isListening() {
      return this.tbC;
   }

   public void setListening(boolean var1) {
      if (this.tbC != var1) {
         this.tbC = var1;
         this.nHgh = this.defaultKey;
      }
   }

   public boolean gduzp() {
      this.tbC = !this.tbC;
      this.nHgh = this.defaultKey;
      return this.tbC;
   }

   public void setCapturedKey(int var1) {
      this.nHgh = var1;
   }

   public boolean matchesCapturedKey(int var1) {
      return this.tbC && this.nHgh == var1;
   }

   public void NcyUqh() {
      this.tbC = false;
      this.nHgh = this.defaultKey;
   }

   public static int toScrollKeycode(int var0) {
      return var0 + 1000;
   }

   public static int scrollDeltaToKeycode(int var0) {
      return var0 > 0 ? 1069 : 1070;
   }

   public static String getScrollKeyName(int var0) {
      if (var0 == 1069) {
         return "MScrollUp";
      } else {
         return var0 == 1070 ? "MScrollDown" : "&cERROR";
      }
   }
}
