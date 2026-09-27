// Jade recovery: original class: jade.deps.eLz.pfJqnwLDwF
package jade.client.common;

import org.lwjgl.opengl.GL11;

public final class TooltipTextRenderer {
   private TooltipTextRenderer() {
   }

   public static void drawTooltipText(IFont var0, String var1, boolean var2, boolean var3, float var4, float var5, float var6, float var7) {
      GL11.glPushMatrix();
      GL11.glScaled(0.5, 0.5, 0.5);
      String var8 = BooleanSettingRowHelper.formatSettingLabel(var1, var2, var3);
      float var9 = (var4 + 4.0F) * 2.0F + var7;
      float var10 = (var5 + var6 + 4.0F) * 2.0F;
      var0.drawString(var8, var9, var10, BooleanSettingRowHelper.getToggleColor(var3), false);
      GL11.glPopMatrix();
   }
}
