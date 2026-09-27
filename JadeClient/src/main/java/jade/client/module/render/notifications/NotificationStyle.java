// Jade recovery: original class: jade.deps.eLz.OdZDIK
package jade.client.module.render.notifications;

public enum NotificationStyle {
   CLASSIC("Classic"),
   MODERN("Modern");

   private final String label;

   private NotificationStyle(String var3) {
      this.label = var3;
   }

   public static String[] labels() {
      NotificationStyle[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static NotificationStyle fromSetting(double var0) {
      return (int)var0 == MODERN.ordinal() ? MODERN : CLASSIC;
   }

   static {
      NotificationStyle[] var10000 = new NotificationStyle[2];
      var10000[0] = CLASSIC;
      var10000[1] = MODERN;
   }
}
