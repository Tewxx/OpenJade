// Jade recovery: original class: jade.deps.eLz.fvhUwP
package jade.client.module.movement.noslow;

public enum NoSlowMode {
   NONE("None"),
   VANILLA("Vanilla"),
   SPRINT("Sprint");

   private final String label;

   private NoSlowMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      NoSlowMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static NoSlowMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : NONE;
   }

   static {
      NoSlowMode[] var10000 = new NoSlowMode[3];
      var10000[0] = NONE;
      var10000[1] = VANILLA;
      var10000[2] = SPRINT;
   }
}
