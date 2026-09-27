// Jade recovery: original class: jade.deps.eLz.n7HXWJlFWs$3
package jade.client.module.render;

public final class Notifications$3 {
   private final String title;
   private final String Vn2;
   private final String HObV;
   private final boolean moduleEnabled;
   private final boolean hasCustomDetailText;
   private final boolean compact;
   private final long createdAt;
   private float KBIq;
   private float ldB;

   Notifications$3(String var1, boolean var2, long var3, float var5) {
      this.title = var1;
      this.Vn2 = null;
      this.HObV = var1;
      this.moduleEnabled = var2;
      this.hasCustomDetailText = false;
      this.compact = false;
      this.createdAt = var3;
      this.ldB = var5;
   }

   Notifications$3(String var1, String var2, long var3, float var5) {
      this(var1, var2, var2, true, false, var3, var5);
   }

   Notifications$3(String var1, String var2, String var3, boolean var4, boolean var5, long var6, float var8) {
      this.title = var3;
      this.Vn2 = var1;
      this.HObV = var2;
      this.moduleEnabled = var4;
      this.hasCustomDetailText = true;
      this.compact = var5;
      this.createdAt = var6;
      this.ldB = var8;
   }

   private static Notifications$3 YYPOB(String var0, long var1, float var3) {
      return new Notifications$3("Config Loaded", var0, var1, var3);
   }

   private static Notifications$3 vUwsFcb(String var0, String var1, int var2, long var3, float var5) {
      return new Notifications$3(var0 + "§r is cheating!", var1 + " x" + Math.max(1, var2), var0 + "§f flagged §c" + var1, false, true, var3, var5);
   }

   public static Notifications$3 NCpF(String var0, long var1, float var3) {
      return YYPOB(var0, var1, var3);
   }

   public static Notifications$3 createCheatAlert(String var0, String var1, int var2, long var3, float var5) {
      return vUwsFcb(var0, var1, var2, var3, var5);
   }

   public static long dL21(Notifications$3 var0) {
      return var0.createdAt;
   }

   public static float QERbH(Notifications$3 var0, float var1) {
      return var0.KBIq = var1;
   }

   public static float getVisibilityProgress(Notifications$3 var0) {
      return var0.KBIq;
   }

   public static float setPositionY(Notifications$3 var0, float var1) {
      return var0.ldB = var1;
   }

   public static float getPositionY(Notifications$3 var0) {
      return var0.ldB;
   }

   public static boolean isCompact(Notifications$3 var0) {
      return var0.compact;
   }

   public static String getTitle(Notifications$3 var0) {
      return var0.title;
   }

   public static boolean pokb5(Notifications$3 var0) {
      return var0.hasCustomDetailText;
   }

   public static String getAlertTitle(Notifications$3 var0) {
      return var0.Vn2;
   }

   public static String getStatusText(Notifications$3 var0) {
      return var0.HObV;
   }

   public static boolean isModuleEnabled(Notifications$3 var0) {
      return var0.moduleEnabled;
   }
}
