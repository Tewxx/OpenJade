// Jade recovery: original class: jade.deps.eLz.P637X7Z
package jade.client.module.render.itemesp;

public enum ItemEspMode {
   BOX("Box"),
   NAMETAG("Nametag");

   private final String label;

   private ItemEspMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      ItemEspMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static ItemEspMode fromSetting(double var0) {
      return (int)var0 == NAMETAG.ordinal() ? NAMETAG : BOX;
   }

   static {
      ItemEspMode[] var10000 = new ItemEspMode[2];
      var10000[0] = BOX;
      var10000[1] = NAMETAG;
   }
}
