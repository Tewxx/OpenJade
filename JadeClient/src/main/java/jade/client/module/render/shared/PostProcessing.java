// Jade recovery: original class: jade.deps.eLz.Cb9P5X6xKM
package jade.client.module.render.shared;

public final class PostProcessing {
   private static final BloomBlurFramebuffers SKOiH9 = new BloomBlurFramebuffers();

   private PostProcessing() {
   }

   public static void beginBlurCapture() {
      SKOiH9.refreshBlurBuffer();
   }

   public static void beginBloomCapture() {
      SKOiH9.refreshBloomBuffer();
   }

   public static void FLZaJw(int var0, float var1) {
      SKOiH9.applyKawaseBlur(var0, var1);
   }

   public static void VacO7(int var0, float var1) {
      SKOiH9.bsopeuR(var0, var1, true);
   }

   public static void runWithBloom(Runnable var0, int var1, float var2) {
      runWithBloomMasked(var0, var1, var2, true);
   }

   public static void runWithBloomMasked(Runnable var0, int var1, float var2, boolean var3) {
      SKOiH9.captureSceneForBloom(var0, var1, var2, var3);
   }
}
