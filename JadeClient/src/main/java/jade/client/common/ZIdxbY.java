// Jade recovery: original class: jade.deps.eLz.ZIdxbY
package jade.client.common;

import org.lwjgl.opengl.GL11;

public final class ZIdxbY {
   private ZIdxbY() {
   }

   public static void drawGlowLine(float var0, float var1, float var2, int var3) {
      if (!(var2 <= 0.0F)) {
         GL11.glPushAttrib(24897);

         try {
            GL11.glDisable(3553);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glShadeModel(7425);
            float var4 = (var3 >> 16 & 0xFF) / 255.0F;
            float var5 = (var3 >> 8 & 0xFF) / 255.0F;
            float var6 = (var3 & 0xFF) / 255.0F;
            float var7 = (var3 >>> 24) / 255.0F;

            for (byte var8 = -1; var8 <= 1; var8 += 2) {
               GL11.glBegin(8);

               for (int var9 = 0; var9 <= 32; var9++) {
                  float var10 = var9 / 32.0F;
                  float var11 = 1.0F - Math.abs(2.0F * var10 - 1.0F);
                  GL11.glColor4f(var4, var5, var6, var7 * var11);
                  GL11.glVertex2f(var0 + var2 * var10, var1);
                  GL11.glColor4f(var4, var5, var6, 0.0F);
                  GL11.glVertex2f(var0 + var2 * var10, var1 + var8 * 0.65F * var11);
               }

               GL11.glEnd();
            }
         } finally {
            GL11.glPopAttrib();
         }
      }
   }
}
