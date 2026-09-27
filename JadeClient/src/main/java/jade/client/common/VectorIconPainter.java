// Jade recovery: original class: jade.deps.eLz.yOfh6tY2
package jade.client.common;

import org.lwjgl.opengl.GL11;

public final class VectorIconPainter {
   private VectorIconPainter() {
   }

   public static void drawExpandIndicator(float var0, float var1, boolean var2, int var3) {
      for (int var4 = 0; var4 < 2; var4++) {
         float var5 = var4 == 0 ? 0.8F : 0.0F;
         int var6 = var4 == 0 ? var3 & 0xFF000000 : var3;
         beginGlIconState(var6);
         if (var2) {
            GL11.glLineWidth(2.2F);
            GL11.glBegin(1);
            GL11.glVertex2f(var0 - 4.0F + var5, var1 + var5);
            GL11.glVertex2f(var0 + 4.0F + var5, var1 + var5);
            GL11.glEnd();
         } else {
            for (int var7 = -1; var7 <= 1; var7++) {
               drawGlowCircle(var0 + var7 * 5.0F + var5, var1 + var5, 1.5F, var6);
            }
         }

         GL11.glPopAttrib();
      }
   }

   private static void drawGlowCircle(float var0, float var1, float var2, int var3) {
      float var4 = (var3 >> 16 & 0xFF) / 255.0F;
      float var5 = (var3 >> 8 & 0xFF) / 255.0F;
      float var6 = (var3 & 0xFF) / 255.0F;
      float var7 = (var3 >>> 24) / 255.0F;
      GL11.glColor4f(var4, var5, var6, var7);
      GL11.glBegin(6);
      GL11.glVertex2f(var0, var1);

      for (int var8 = 0; var8 <= 32; var8++) {
         double var9 = var8 * Math.PI / 16.0;
         GL11.glVertex2f(var0 + (float)Math.cos(var9) * var2, var1 + (float)Math.sin(var9) * var2);
      }

      GL11.glEnd();
      GL11.glBegin(8);

      for (int var13 = 0; var13 <= 32; var13++) {
         double var14 = var13 * Math.PI / 16.0;
         float var11 = (float)Math.cos(var14);
         float var12 = (float)Math.sin(var14);
         GL11.glColor4f(var4, var5, var6, var7);
         GL11.glVertex2f(var0 + var11 * var2, var1 + var12 * var2);
         GL11.glColor4f(var4, var5, var6, 0.0F);
         GL11.glVertex2f(var0 + var11 * (var2 + 0.55F), var1 + var12 * (var2 + 0.55F));
      }

      GL11.glEnd();
   }

   public static void drawPowerOrPanelIcon(float var0, float var1, boolean var2, int var3) {
      for (int var4 = 0; var4 < 2; var4++) {
         float var5 = var0 + (var4 == 0 ? 1 : 0);
         float var6 = var1 + (var4 == 0 ? 1 : 0);
         beginGlIconState(var4 == 0 ? var3 & 0xFF000000 : var3);
         GL11.glLineWidth(2.2F);
         if (!var2) {
            GL11.glBegin(2);
            GL11.glVertex2f(var5 - 7.0F, var6 - 6.0F);
            GL11.glVertex2f(var5 + 7.0F, var6 - 6.0F);
            GL11.glVertex2f(var5 + 7.0F, var6 + 6.0F);
            GL11.glVertex2f(var5 - 7.0F, var6 + 6.0F);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex2f(var5 - 2.0F, var6 - 6.0F);
            GL11.glVertex2f(var5 - 2.0F, var6 + 6.0F);
            GL11.glVertex2f(var5 - 2.0F, var6 - 1.0F);
            GL11.glVertex2f(var5 + 7.0F, var6 - 1.0F);
            GL11.glEnd();
         } else {
            GL11.glBegin(3);

            for (int var7 = 0; var7 <= 64; var7++) {
               double var8 = Math.toRadians(42.0 + 276 * var7 / 64.0);
               GL11.glVertex2f(var5 + (float)Math.sin(var8) * 6.5F, var6 - (float)Math.cos(var8) * 6.5F);
            }

            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex2f(var5, var6 - 8.0F);
            GL11.glVertex2f(var5, var6 - 1.0F);
            GL11.glEnd();
         }

         GL11.glPopAttrib();
      }
   }

   public static void drawArrowIcon(float var0, float var1, int var2) {
      beginGlIconState(var2);
      GL11.glLineWidth(2.0F);
      GL11.glBegin(3);
      GL11.glVertex2f(var0 + 5.0F, var1 - 6.0F);
      GL11.glVertex2f(var0 - 1.0F, var1);
      GL11.glVertex2f(var0 + 5.0F, var1 + 6.0F);
      GL11.glEnd();
      GL11.glBegin(1);
      GL11.glVertex2f(var0 - 1.0F, var1);
      GL11.glVertex2f(var0 + 7.0F, var1);
      GL11.glEnd();
      GL11.glPopAttrib();
   }

   private static void beginGlIconState(int var0) {
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3553);
      GL11.glDisable(3008);
      GL11.glDisable(2929);
      GL11.glDisable(2884);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glShadeModel(7425);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(1.25F);
      GL11.glColor4f((var0 >> 16 & 0xFF) / 255.0F, (var0 >> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >>> 24) / 255.0F);
   }

   public static void YRSmn(float var0, float var1, boolean var2, int var3) {
      beginGlIconState(var3);
      GL11.glBegin(2);

      for (int var4 = 0; var4 <= 32; var4++) {
         float var5 = var4 / 32.0F;
         GL11.glVertex2f(var0 - 5.5F + 11.0F * var5, var1 - (float)Math.sin(Math.PI * var5) * 3.2F);
      }

      for (int var7 = 31; var7 > 0; var7--) {
         float var9 = var7 / 32.0F;
         GL11.glVertex2f(var0 - 5.5F + 11.0F * var9, var1 + (float)Math.sin(Math.PI * var9) * 3.2F);
      }

      GL11.glEnd();
      GL11.glBegin(2);

      for (int var8 = 0; var8 < 32; var8++) {
         double var10 = (Math.PI * 2) * var8 / 32.0;
         GL11.glVertex2f(var0 + (float)Math.cos(var10) * 1.4F, var1 + (float)Math.sin(var10) * 1.4F);
      }

      GL11.glEnd();
      if (var2) {
         GL11.glBegin(1);
         GL11.glVertex2f(var0 - 5.0F, var1 - 4.0F);
         GL11.glVertex2f(var0 + 5.0F, var1 + 4.0F);
         GL11.glEnd();
      }

      GL11.glPopAttrib();
   }

   public static void drawSaveIcon(float var0, float var1, int var2) {
      beginGlIconState(var2);
      GL11.glBegin(2);
      GL11.glVertex2f(var0 - 5.0F, var1 - 5.0F);
      GL11.glVertex2f(var0 + 3.0F, var1 - 5.0F);
      GL11.glVertex2f(var0 + 5.0F, var1 - 3.0F);
      GL11.glVertex2f(var0 + 5.0F, var1 + 5.0F);
      GL11.glVertex2f(var0 - 5.0F, var1 + 5.0F);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2f(var0 - 2.0F, var1 - 5.0F);
      GL11.glVertex2f(var0 - 2.0F, var1 - 1.0F);
      GL11.glVertex2f(var0 + 2.0F, var1 - 1.0F);
      GL11.glVertex2f(var0 + 2.0F, var1 - 5.0F);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2f(var0 - 2.0F, var1 + 5.0F);
      GL11.glVertex2f(var0 - 2.0F, var1 + 2.0F);
      GL11.glVertex2f(var0 + 2.0F, var1 + 2.0F);
      GL11.glVertex2f(var0 + 2.0F, var1 + 5.0F);
      GL11.glEnd();
      GL11.glPopAttrib();
   }

   public static void drawRefreshIcon(float var0, float var1, int var2) {
      beginGlIconState(var2);
      GL11.glBegin(3);

      for (int var3 = 0; var3 <= 40; var3++) {
         double var4 = Math.toRadians(45 + var3 * 7);
         GL11.glVertex2f(var0 + (float)Math.cos(var4) * 4.5F, var1 + (float)Math.sin(var4) * 4.5F);
      }

      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2f(var0 + 1.0F, var1 - 4.0F);
      GL11.glVertex2f(var0 + 5.0F, var1 - 2.0F);
      GL11.glVertex2f(var0 + 5.0F, var1 - 6.0F);
      GL11.glEnd();
      GL11.glPopAttrib();
   }
}
