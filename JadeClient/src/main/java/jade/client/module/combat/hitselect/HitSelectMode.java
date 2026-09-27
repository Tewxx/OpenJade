// Jade recovery: original class: jade.deps.eLz.Wtp1Xq
package jade.client.module.combat.hitselect;

public enum HitSelectMode {
   BURST("Burst"),
   CRITICALS("Criticals");

   private final String label;

   private HitSelectMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      HitSelectMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static HitSelectMode fromSetting(double var0) {
      return (int)var0 == CRITICALS.ordinal() ? CRITICALS : BURST;
   }

   static {
      HitSelectMode[] var10000 = new HitSelectMode[2];
      var10000[0] = BURST;
      var10000[1] = CRITICALS;
   }
}
