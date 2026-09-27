// Jade recovery: original class: jade.deps.eLz.gESvYYue
package jade.client.module.minigames.overlay;

import jade.client.common.ExternalRenderBuffer;

public final class StarGlyphRenderer {
   private static final int VEsc0 = 10;
   private static final float[] aAl = new float[10];
   private static final float[] STAR_SIN_TABLE = new float[10];

   private StarGlyphRenderer() {
   }

   public static void egNdi(ExternalRenderBuffer var0, double var1, double var3, float var5, int var6, boolean var7) {
      if (var0 != null && !(var5 <= 0.0F) && var6 >>> 24 != 0) {
         double var8 = var1 + var5 * 0.5;
         double var10 = var3 + var5 * 0.52;
         double var12 = var5 * 0.46;
         double var14 = var7 ? 0.0 : var12 * 0.24;

         for (int var16 = 0; var16 < 10; var16++) {
            int var17 = (var16 + 1) % 10;
            double var18 = var8 + aAl[var16] * var12;
            double var20 = var10 + STAR_SIN_TABLE[var16] * var12;
            double var22 = var8 + aAl[var17] * var12;
            double var24 = var10 + STAR_SIN_TABLE[var17] * var12;
            if (var7) {
               var0.VogZb(var8, var10, var18, var20, var22, var24, var6);
            } else {
               double var26 = var8 + aAl[var16] * var14;
               double var28 = var10 + STAR_SIN_TABLE[var16] * var14;
               double var30 = var8 + aAl[var17] * var14;
               double var32 = var10 + STAR_SIN_TABLE[var17] * var14;
               var0.VogZb(var18, var20, var22, var24, var30, var32, var6);
               var0.VogZb(var18, var20, var30, var32, var26, var28, var6);
            }
         }
      }
   }

   static {
      for (int var0 = 0; var0 < 10; var0++) {
         double var1 = (-Math.PI / 2) + var0 * Math.PI / 5.0;
         float var3 = (var0 & 1) == 0 ? 1.0F : 0.4F;
         aAl[var0] = (float)Math.cos(var1) * var3;
         STAR_SIN_TABLE[var0] = (float)Math.sin(var1) * var3;
      }
   }
}
