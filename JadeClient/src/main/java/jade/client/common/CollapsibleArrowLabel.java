// Jade recovery: original class: jade.deps.eLz.IdgfPTzD
package jade.client.common;

import org.lwjgl.opengl.GL11;

public final class CollapsibleArrowLabel {
   private CollapsibleArrowLabel() {
   }

   public static void x287(IFont var0, String var1, float var2, float var3, float var4) {
      GL11.glPushMatrix();
      GL11.glScaled(0.5, 0.5, 0.5);
      float var5 = var2 * 2.0F + 1.0F;
      float var6 = var3 * 2.0F;
      if (var4 <= 0.0F) {
         drawLabelText(var0, "[>]  " + var1, var5, var6);
      } else {
         yqaYib(var0, var1, var5, var6, var4);
      }

      GL11.glPopMatrix();
   }

   private static void yqaYib(IFont var0, String var1, float var2, float var3, float var4) {
      drawLabelText(var0, "[", var2, var3);
      int var5 = var0.getStringWidth("[");
      int var6 = var0.getStringWidth(">");
      GL11.glPushMatrix();
      GL11.glTranslatef(var2 + var5 - 2.0F + var6 / 2.0F, var3 + var0.getFontHeight() / 2.0F, 0.0F);
      GL11.glRotatef(90.0F * var4, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-var6 / 2.0F, -var0.getFontHeight() / 2.0F, 0.0F);
      drawLabelText(var0, ">", 0.0F, 0.0F);
      GL11.glPopMatrix();
      drawLabelText(var0, "]  " + var1, var2 + var5 + var6, var3);
   }

   private static void drawLabelText(IFont var0, String var1, float var2, float var3) {
      var0.drawString(var1, var2, var3, -1, false);
   }
}
