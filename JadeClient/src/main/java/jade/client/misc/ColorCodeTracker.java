// Jade recovery: original class: jade.deps.eLz.Xg3Z2L8lQ
package jade.client.misc;

public final class ColorCodeTracker {
   private static final String LQrM2 = "0123456789abcdef";
   private static final String vsqCk9 = "klmno";
   private int QMUZ = -1;
   private int activeStyleMask;

   public void parseCode(char var1) {
      char var2 = Character.toLowerCase(var1);
      int var3 = "0123456789abcdef".indexOf(var2);
      if (var3 < 0 && var2 != 'r') {
         int var4 = "klmno".indexOf(var2);
         if (var4 >= 0) {
            this.activeStyleMask |= 1 << var4;
         }
      } else {
         this.QMUZ = var3;
         this.activeStyleMask = 0;
      }
   }

   public void appendActiveCodes(StringBuilder var1) {
      if (this.QMUZ >= 0) {
         var1.append('§').append("0123456789abcdef".charAt(this.QMUZ));
      }

      for (int var2 = 0; var2 < "klmno".length(); var2++) {
         if ((this.activeStyleMask & 1 << var2) != 0) {
            var1.append('§').append("klmno".charAt(var2));
         }
      }
   }
}
