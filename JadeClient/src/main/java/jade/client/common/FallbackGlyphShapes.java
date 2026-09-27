// Jade recovery: original class: jade.deps.eLz.IMRzbf8s5
package jade.client.common;

import org.lwjgl.opengl.GL11;

public final class FallbackGlyphShapes {
   private static final int POINT_COUNT = 10;
   private static final float[] APdn1 = new float[10];
   private static final float[] STAR_POINT_SINES = new float[10];
   private static final float[] CIRCLE_POINT_COSINES = new float[10];
   private static final float[] CIRCLE_POINT_SINES = new float[10];

   private FallbackGlyphShapes() {
   }

   public static void drawCircle(float var0, float var1, float var2, int var3) {
      drawShape(var0, var1, var2, var3, true);
   }

   public static void mnxstly(float var0, float var1, float var2, int var3) {
      drawShape(var0, var1, var2, var3, false);
   }

   private static void drawShape(float var0, float var1, float var2, int var3, boolean var4) {
      if (!(var2 <= 0.0F) && var3 >>> 24 != 0) {
         GL11.glPushAttrib(24577);

         try {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(2896);
            if (var4) {
               ZKsXsm(var0, var1, var2, var3);
            } else {
               ornD37(var0, var1, var2, var3);
            }
         } finally {
            GL11.glPopAttrib();
         }
      }
   }

   private static void ZKsXsm(float var0, float var1, float var2, int var3) {
      float var4 = var0 + var2 * 0.5F;
      float var5 = var1 + var2 * 0.52F;
      float var6 = var2 * 0.46F;
      float var7 = Math.min(0.4F, Math.max(0.2F, var2 * 0.045F));
      float var8 = Math.max(0.0F, var6 - var7);
      float var9 = var6 * 0.24F;
      float var10 = var9 + var7 * 0.55F;
      applyGlColor(var3);
      GL11.glBegin(8);

      for (int var11 = 0; var11 <= 10; var11++) {
         int var12 = var11 % 10;
         GL11.glVertex2f(var4 + APdn1[var12] * var8, var5 + STAR_POINT_SINES[var12] * var8);
         GL11.glVertex2f(var4 + CIRCLE_POINT_COSINES[var12] * var10, var5 + CIRCLE_POINT_SINES[var12] * var10);
      }

      GL11.glEnd();
      int var14 = var3 & 16777215;
      GL11.glBegin(8);

      for (int var15 = 0; var15 <= 10; var15++) {
         int var13 = var15 % 10;
         applyGlColor(var3);
         GL11.glVertex2f(var4 + APdn1[var13] * var8, var5 + STAR_POINT_SINES[var13] * var8);
         applyGlColor(var14);
         GL11.glVertex2f(var4 + APdn1[var13] * var6, var5 + STAR_POINT_SINES[var13] * var6);
      }

      GL11.glEnd();
      GL11.glBegin(8);

      for (int var16 = 0; var16 <= 10; var16++) {
         int var17 = var16 % 10;
         applyGlColor(var3);
         GL11.glVertex2f(var4 + CIRCLE_POINT_COSINES[var17] * var10, var5 + CIRCLE_POINT_SINES[var17] * var10);
         applyGlColor(var14);
         GL11.glVertex2f(var4 + CIRCLE_POINT_COSINES[var17] * var9, var5 + CIRCLE_POINT_SINES[var17] * var9);
      }

      GL11.glEnd();
   }

   private static void ornD37(float var0, float var1, float var2, int var3) {
      float var4 = var0 + var2 * 0.5F;
      float var5 = var1 + var2 * 0.52F;
      float var6 = var2 * 0.46F;
      float var7 = Math.min(0.4F, Math.max(0.2F, var2 * 0.045F));
      float var8 = Math.max(0.0F, var6 - var7);
      applyGlColor(var3);
      GL11.glBegin(6);
      GL11.glVertex2f(var4, var5);

      for (int var9 = 0; var9 <= 10; var9++) {
         int var10 = var9 % 10;
         GL11.glVertex2f(var4 + APdn1[var10] * var8, var5 + STAR_POINT_SINES[var10] * var8);
      }

      GL11.glEnd();
      int var12 = var3 & 16777215;
      GL11.glBegin(8);

      for (int var13 = 0; var13 <= 10; var13++) {
         int var11 = var13 % 10;
         applyGlColor(var3);
         GL11.glVertex2f(var4 + APdn1[var11] * var8, var5 + STAR_POINT_SINES[var11] * var8);
         applyGlColor(var12);
         GL11.glVertex2f(var4 + APdn1[var11] * var6, var5 + STAR_POINT_SINES[var11] * var6);
      }

      GL11.glEnd();
   }

   private static void applyGlColor(int var0) {
      GL11.glColor4f((var0 >>> 16 & 0xFF) / 255.0F, (var0 >>> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >>> 24) / 255.0F);
   }

   static {
      for (int var0 = 0; var0 < 10; var0++) {
         double var1 = (-Math.PI / 2) + var0 * Math.PI / 5.0;
         float var3 = (var0 & 1) == 0 ? 1.0F : 0.4F;
         APdn1[var0] = (float)Math.cos(var1) * var3;
         STAR_POINT_SINES[var0] = (float)Math.sin(var1) * var3;
         CIRCLE_POINT_COSINES[var0] = (float)Math.cos(var1);
         CIRCLE_POINT_SINES[var0] = (float)Math.sin(var1);
      }
   }
}
