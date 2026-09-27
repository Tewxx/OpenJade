// Jade recovery: original class: jade.deps.eLz.wpmQGvzwn$12
package jade.client.module.other;

public final class Denick$12 {
   private final String zezQq;
   private final String TBLyxI;

   Denick$12(String var1, String var2) {
      this.zezQq = var1;
      this.TBLyxI = var2;
   }

   private boolean AaIt(Denick$19 var1) {
      String var2 = (String)Denick$19.getCosmeticSelections(var1).get(this.zezQq);
      return this.isDefaultSelection() ? var2 == null || var2.trim().isEmpty() || var2.equals(this.TBLyxI) : var2 != null && var2.equals(this.TBLyxI);
   }

   private boolean isDefaultSelection() {
      return this.TBLyxI.endsWith("_none");
   }

   private String getDisplayValue() {
      return this.TBLyxI.replace('_', ' ');
   }

   public static String getCosmeticId(Denick$12 var0) {
      return var0.zezQq;
   }

   public static String GJUvu(Denick$12 var0) {
      return var0.TBLyxI;
   }

   public static boolean matchesEntry(Denick$12 var0, Denick$19 var1) {
      return var0.AaIt(var1);
   }

   public static String getDisplayText(Denick$12 var0) {
      return var0.getDisplayValue();
   }
}
