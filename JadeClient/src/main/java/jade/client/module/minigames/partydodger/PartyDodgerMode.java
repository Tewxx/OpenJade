// Jade recovery: original class: jade.deps.eLz.tuawotvo
package jade.client.module.minigames.partydodger;

public enum PartyDodgerMode {
   LOBBY("Lobby"),
   SAFE_WARP("Safe Warp");

   private final String label;

   private PartyDodgerMode(String var3) {
      this.label = var3;
   }

   public static String[] labels() {
      PartyDodgerMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static PartyDodgerMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : LOBBY;
   }

   static {
      PartyDodgerMode[] var10000 = new PartyDodgerMode[2];
      var10000[0] = LOBBY;
      var10000[1] = SAFE_WARP;
   }
}
