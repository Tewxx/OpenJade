// Jade recovery: original class: jade.deps.eLz.V2N5WX
package jade.client.setting;

import jade.client.common.IFont;
import jade.client.common.SettingRowLayout;
import jade.client.gui.TextField;
import jade.client.module.client.Gui;
import jade.client.module.render.arraylist.ColorTheme;
import org.lwjgl.opengl.GL11;

public final class TextFieldBox {
   private final TextField textField;

   public TextFieldBox(String var1, int var2, float var3) {
      this.textField = new TextField(var1, var2, var3);
   }

   public TextField getTextField() {
      return this.textField;
   }

   public void drawGradientText(String var1, float var2, float var3, float var4, float var5) {
      drawScaledString(var1, var2, computeCenteredTextY(var3, var4, var5), ColorTheme.getGradient(ColorTheme.descriptor[0], ColorTheme.descriptor[1], 0.0), var5);
   }

   public void drawFieldBackground(float var1, float var2, float var3, float var4) {
      this.textField.render(var1, var2 + 1.0F, var3, var2 + var4 - 1.0F);
   }

   public boolean isMouseOverField(int var1, int var2, float var3, float var4, float var5, float var6) {
      return this.textField.emAy(var1, var2, var3, var4 + 1.0F, var5, var4 + var6 - 1.0F);
   }

   public void updateCursorBlink() {
      this.textField.kqao7();
   }

   public boolean isFocused() {
      return this.textField.isFocused();
   }

   public void setFocused(boolean var1) {
      this.textField.setFocused(var1);
   }

   public static float computeCenteredTextY(float var0, float var1, float var2) {
      IFont var3 = Gui.getSettingFont();
      return SettingRowLayout.QYl2(var0, var1, var2, var3.getTextTopOffset(), var3.getTextBottomOffset());
   }

   public static void drawScaledString(String var0, float var1, float var2, int var3, float var4) {
      IFont var5 = Gui.getSettingFont();
      GL11.glPushMatrix();
      GL11.glScaled(var4, var4, var4);
      var5.drawString(var0, var1 / var4, var2 / var4, var3, false);
      GL11.glPopMatrix();
   }
}
