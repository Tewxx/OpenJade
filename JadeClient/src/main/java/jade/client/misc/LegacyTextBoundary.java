// Jade recovery: original class: jade.deps.eLz.vqEPWX
package jade.client.misc;

public final class LegacyTextBoundary {
   private final char[] Jpd;
   private final char[] TNh;

   public LegacyTextBoundary(String var1) {
      int var2 = var1.length();
      this.Jpd = new char[var2 + 1];
      this.TNh = new char[var2 + 1];

      for (int var3 = 1; var3 <= var2; var3++) {
         if (var3 >= 2 && var1.charAt(var3 - 2) == 167) {
            this.Jpd[var3] = this.Jpd[var3 - 2];
         } else if (var1.charAt(var3 - 1) == 167) {
            this.Jpd[var3] = this.Jpd[var3 - 1];
         } else {
            this.Jpd[var3] = var1.charAt(var3 - 1);
         }
      }

      for (int var4 = var2 - 1; var4 >= 0; var4--) {
         this.TNh[var4] = var4 + 1 < var2 && var1.charAt(var4) == 167 ? this.TNh[var4 + 2] : var1.charAt(var4);
      }
   }

   public boolean gfN5(int var1, int var2) {
      return isNonWordCharacter(this.Jpd[var1]) && isNonWordCharacter(this.TNh[var2]);
   }

   private static boolean isNonWordCharacter(char var0) {
      return var0 != '_' && !Character.isLetterOrDigit(var0);
   }
}
