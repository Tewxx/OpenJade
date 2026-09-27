// Jade recovery: original class: jade.deps.eLz.i9GmZuBSM
package jade.client.module.render.progressbar;

public enum ProgressBarStyle {
   JADE("Jade"),
   TEXT("Text");

   private final String label;

   private ProgressBarStyle(String var3) {
      this.label = var3;
   }

   public static String[] labels() {
      ProgressBarStyle[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static ProgressBarStyle fromSetting(double var0) {
      return (int)var0 == TEXT.ordinal() ? TEXT : JADE;
   }

   static {
      ProgressBarStyle[] var10000 = new ProgressBarStyle[2];
      var10000[0] = JADE;
      var10000[1] = TEXT;
   }
}
