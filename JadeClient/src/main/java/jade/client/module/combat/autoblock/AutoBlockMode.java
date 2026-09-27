// Jade recovery: original class: jade.deps.eLz.XyhjmffAs
package jade.client.module.combat.autoblock;

public enum AutoBlockMode {
   CUSTOM("Custom"),
   SWAP("Swap"),
   PREDICT("Predict");

   private final String label;

   private AutoBlockMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      AutoBlockMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static AutoBlockMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : CUSTOM;
   }

   static {
      AutoBlockMode[] var10000 = new AutoBlockMode[3];
      var10000[0] = CUSTOM;
      var10000[1] = SWAP;
      var10000[2] = PREDICT;
   }
}
