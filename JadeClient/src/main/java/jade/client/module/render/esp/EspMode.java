// Jade recovery: original class: jade.deps.eLz.Eu7H65
package jade.client.module.render.esp;

public enum EspMode {
   TWO_D("2D"),
   THREE_D("3D"),
   OUTLINE("Outline"),
   BOX("Box"),
   SKELETON("Skeleton");

   private final String label;

   private EspMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      EspMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static EspMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : TWO_D;
   }

   static {
      EspMode[] var10000 = new EspMode[5];
      var10000[0] = TWO_D;
      var10000[1] = THREE_D;
      var10000[2] = OUTLINE;
      var10000[3] = BOX;
      var10000[4] = SKELETON;
   }
}
