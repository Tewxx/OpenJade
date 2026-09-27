// Jade recovery: original class: jade.deps.eLz.z95B1to6J
package jade.client.module.render.targetesp;

public enum TargetEspMode {
   RING("Ring"),
   BOX("Box"),
   OUTLINE("Outline");

   private final String label;

   private TargetEspMode(String var3) {
      this.label = var3;
   }

   public String getLabel() {
      return this.label;
   }

   public static String[] labels() {
      TargetEspMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static TargetEspMode fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : RING;
   }

   static {
      TargetEspMode[] var10000 = new TargetEspMode[3];
      var10000[0] = RING;
      var10000[1] = BOX;
      var10000[2] = OUTLINE;
   }
}
