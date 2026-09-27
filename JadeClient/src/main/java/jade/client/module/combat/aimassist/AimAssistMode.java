// Jade recovery: original class: jade.deps.eLz.XEeMAC6UJh
package jade.client.module.combat.aimassist;

public enum AimAssistMode {
   NORMAL("Normal"),
   SILENT("Silent"),
   LOCK_ON("Lock-On");

   private final String label;

   private AimAssistMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      AimAssistMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static AimAssistMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : NORMAL;
   }

   static {
      AimAssistMode[] var10000 = new AimAssistMode[3];
      var10000[0] = NORMAL;
      var10000[1] = SILENT;
      var10000[2] = LOCK_ON;
   }
}
