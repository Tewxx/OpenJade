// Jade recovery: original class: jade.deps.eLz.N1vLKkf$1
package jade.client.common;

public final class RenderUtils$1 {
   private final boolean lightingEnabled;
   private final boolean b23;
   private final float lightmapU;
   private final float ZGt;

   RenderUtils$1(boolean var1, boolean var2, float var3, float var4) {
      this.lightingEnabled = var1;
      this.b23 = var2;
      this.lightmapU = var3;
      this.ZGt = var4;
   }

   public static float getLightmapU(RenderUtils$1 var0) {
      return var0.lightmapU;
   }

   public static float getLightmapV(RenderUtils$1 var0) {
      return var0.ZGt;
   }

   public static boolean bGlc(RenderUtils$1 var0) {
      return var0.b23;
   }

   public static boolean isLightingEnabled(RenderUtils$1 var0) {
      return var0.lightingEnabled;
   }
}
