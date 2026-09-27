// Jade recovery: original class: jade.deps.eLz.A8fRETF5
package jade.client.module.player.bridgeassist;

public enum SneakMode {
   SNEAK_INPUT(
      "Sneak (Input)",
      true,
      true
   ),
   SNEAK_PACKET(
      "Sneak (Packet)", true, false
   ),
   PRE_PLACE("Pre Place", false, false);

   private final String label;
   private final boolean sneakMode;
   private final boolean inputMode;

   private SneakMode(String var3, boolean var4, boolean var5) {
      this.label = var3;
      this.sneakMode = var4;
      this.inputMode = var5;
   }

   public boolean isSneakMode() {
      return this.sneakMode;
   }

   public boolean isInputMode() {
      return this.inputMode;
   }

   public static String[] labels() {
      SneakMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static SneakMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : SNEAK_INPUT;
   }

   static {
      SneakMode[] var10000 = new SneakMode[3];
      var10000[0] = SNEAK_INPUT;
      var10000[1] = SNEAK_PACKET;
      var10000[2] = PRE_PLACE;
   }
}
