// Jade recovery: original class: jade.deps.eLz.xoHFmOLKl
package jade.client.common;

public final class xoHFmOLKl {
   private final long Hte;
   private final String Wa8;
   private final String mode;

   public xoHFmOLKl(long var1, String var3, String var4) {
      this.Hte = var1;
      this.Wa8 = var3;
      this.mode = var4;
   }

   public long getId() {
      return this.Hte;
   }

   public String getModuleName() {
      return this.Wa8;
   }

   public String getMode() {
      return this.mode;
   }

   public boolean hasNoMode() {
      return this.mode == null;
   }

   public String getDisplayName() {
      return this.mode == null ? this.Wa8 : this.mode + " in " + this.Wa8;
   }
}
