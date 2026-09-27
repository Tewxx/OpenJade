// Jade recovery: original class: jade.deps.eLz.NgQmsWa1Op$2
package jade.client.module.render;

public final class ProgressBar$2 {
   private String VzDk;
   private float OEu;
   private float akr03 = 1.0F;

   ProgressBar$2(String var1, float var2) {
      this.VzDk = var1;
      this.OEu = var2;
   }

   public static float XMkTz(ProgressBar$2 var0, float var1) {
      return var0.akr03 = var1;
   }

   public static float getVisibility(ProgressBar$2 var0) {
      return var0.akr03;
   }

   public static String setLabel(ProgressBar$2 var0, String var1) {
      return var0.VzDk = var1;
   }

   public static float setProgress(ProgressBar$2 var0, float var1) {
      return var0.OEu = var1;
   }

   public static float getProgress(ProgressBar$2 var0) {
      return var0.OEu;
   }

   public static String getLabel(ProgressBar$2 var0) {
      return var0.VzDk;
   }
}
