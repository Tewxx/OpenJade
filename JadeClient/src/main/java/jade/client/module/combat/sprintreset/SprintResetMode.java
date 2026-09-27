// Jade recovery: original class: jade.deps.eLz.FQObYW67F
package jade.client.module.combat.sprintreset;

public enum SprintResetMode {
   W_TAP("W-Tap"),
   NO_STOP("No Stop"),
   DYNAMIC("Dynamic");

   private final String label;

   private SprintResetMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      SprintResetMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static SprintResetMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : W_TAP;
   }

   static {
      SprintResetMode[] var10000 = new SprintResetMode[3];
      var10000[0] = W_TAP;
      var10000[1] = NO_STOP;
      var10000[2] = DYNAMIC;
   }
}
