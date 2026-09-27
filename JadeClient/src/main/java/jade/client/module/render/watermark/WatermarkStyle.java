// Jade recovery: original class: jade.deps.eLz.F8i6c65MVL
package jade.client.module.render.watermark;

public enum WatermarkStyle {
   LOGO("Logo"),
   SIMPLE("Simple"),
   BOX("Box"),
   OLD("Old");

   private final String label;

   private WatermarkStyle(String var3) {
      this.label = var3;
   }

   public static String[] labels() {
      WatermarkStyle[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static WatermarkStyle fromSetting(double var0) {
      int var2 = (int)var0;
      return var2 >= 0 && var2 < values().length ? values()[var2] : SIMPLE;
   }

   static {
      WatermarkStyle[] var10000 = new WatermarkStyle[4];
      var10000[0] = LOGO;
      var10000[1] = SIMPLE;
      var10000[2] = BOX;
      var10000[3] = OLD;
   }
}
