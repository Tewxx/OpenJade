// Jade recovery: original class: jade.deps.eLz.gYCZR1z
package jade.client.gui;

import jade.client.common.IFont;
import jade.client.common.ModuleComponent;
import jade.client.common.RenderUtils;
import jade.client.module.Category;
import jade.client.module.client.Gui;
import jade.client.module.render.arraylist.ColorTheme;
import jade.client.setting.KeySetting;
import org.lwjgl.opengl.GL11;

public final class BindLabelRenderer {
   private static final String iLqu = "/assets/jade/textures/gui/eye.png";
   private static final String EYE_OFF_TEXTURE_PATH = "/assets/jade/textures/gui/eye_off.png";

   private BindLabelRenderer() {
   }

   public static void drawBindLabel(ModuleComponent var0, KeySetting var1, boolean var2, float var3, float var4) {
      IFont var5 = Gui.getSettingFont();
      String var6 = KeybindText.getBindDisplayText(var0.module, var1, var2);
      float var7 = (var0.categoryComponent.EWZxNc() + 4.0F) * 2.0F + var4;
      float var8 = (var0.categoryComponent.getPanelY() + var3 + (var1 == null ? 3.0F : 4.0F)) * 2.0F;
      GL11.glPushMatrix();
      GL11.glScaled(0.5, 0.5, 0.5);
      var5.drawString(var6, var7, var8, ColorTheme.getGradient(ColorTheme.descriptor[0], ColorTheme.descriptor[1], 0.0), true);
      GL11.glPopMatrix();
      if (var1 == null && var0.module.getCategory() != Category.profiles) {
         int var9 = YXWjGcywLT.LyqqeVh(var5.getFontHeight() * 0.5F);
         float var10 = YXWjGcywLT.rightAlignedX(var0.categoryComponent.EWZxNc(), var0.categoryComponent.getPanelWidth(), var9, 2);
         float var11 = YXWjGcywLT.textBaselineY(var0.categoryComponent.getPanelY(), var3, true);
         float var12 = YXWjGcywLT.centeredY(var11, var5.getFontHeight() * 0.5F, var9);
         boolean var13 = var0.module.isHidden();
         int var14 = var13
            ? ColorTheme.getGradient(ColorTheme.hiddenBind[0], ColorTheme.hiddenBind[1], 0.0)
            : ColorTheme.getGradient(ColorTheme.descriptor[0], ColorTheme.descriptor[1], 0.0);
         RenderUtils.drawIconTexture(RenderUtils.getIconTexture(var13 ? "/assets/jade/textures/gui/eye_off.png" : "/assets/jade/textures/gui/eye.png"), var10, var12, var9, var14);
      }
   }
}
