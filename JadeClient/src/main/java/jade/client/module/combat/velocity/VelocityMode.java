// Jade recovery: original class: jade.deps.eLz.BYW197
package jade.client.module.combat.velocity;

public enum VelocityMode {
   JUMP_RESET("Jump Reset"),
   DELAY("Delay"),
   HARD_RESET("Hard Reset");

   private final String label;

   private VelocityMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      VelocityMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static VelocityMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : JUMP_RESET;
   }

   static {
      VelocityMode[] var10000 = new VelocityMode[3];
      var10000[0] = JUMP_RESET;
      var10000[1] = DELAY;
      var10000[2] = HARD_RESET;
   }
}
