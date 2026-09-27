// Jade recovery: original class: jade.deps.eLz.OcJ6Qju7M
package jade.client.gui;

import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.SettingRowLayout;
import jade.client.module.client.Gui;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

public class TextField {
   private static final int CURSOR_COLOR = -3092272;
   private static final int QUoH5 = -2142596894;
   private static final int OUTLINE_COLOR = -1761607681;
   private static final int iHo = 0;
   private static final int FOCUSED_BACKGROUND_COLOR = -15395563;
   private static final int UNFOCUSED_BACKGROUND_COLOR = -15658735;
   private static final float Jww = 0.5F;
   private static int nextTextFieldId;
   private final GuiTextField guiTextField;
   private final String placeholder;
   private final float textScale;
   private final float lleVn;
   private final LaLy043 TYPeF3 = new LaLy043();

   public TextField(String var1, int var2) {
      this(var1, var2, 0.5F, 2.0F);
   }

   public TextField(String var1, int var2, float var3) {
      this(var1, var2, var3, 2.0F);
   }

   public TextField(String var1, int var2, float var3, float var4) {
      this.placeholder = var1 == null ? "" : var1;
      this.textScale = var3;
      this.lleVn = Math.max(2.0F, var4);
      this.guiTextField = new GuiTextField(nextTextFieldId++, Minecraft.getMinecraft().fontRendererObj, 0, 0, 100, 20);
      this.guiTextField.setMaxStringLength(var2);
      this.guiTextField.setEnableBackgroundDrawing(false);
      this.guiTextField.setCanLoseFocus(true);
   }

   public void render(float var1, float var2, float var3, float var4) {
      float var5 = var1 + this.lleVn;
      float var6 = Math.max(0.0F, var3 - var1 - this.lleVn * 2.0F);
      float var7 = var4 - 1.0F;
      float var8 = Math.max(0.0F, var7 - var2);
      IFont var9 = Gui.getSettingFont();
      String var10 = this.guiTextField.getText();
      int var11 = Math.max(0, Math.min(TextFieldAccess.qZn0(this.guiTextField, 0), var10.length()));
      String var12 = ScrolledTextLayout.buildVisibleText(var10, var11, var6, this.textScale, new TextField$1(var9));
      int var13 = TextFieldAccess.getCursorPosition(this.guiTextField, var10.length());
      int var14 = TextFieldAccess.getSelectionEnd(this.guiTextField, var13);
      float var16 = this.computeCenteredTextY(var2, var8, var9);
      int var17 = TextFieldAccess.getTextColor(this.guiTextField);
      float var18 = Gui.JzraV3() ? 0.0F : 6.0F;
      RenderUtils.jxyoE(var1, var2, var3, var7, var18, this.isFocused() ? -15395563 : -15658735);
      if (this.isFocused()) {
         RoundedRect.drawRoundedOutline(var1, var2, var3 - var1, var7 - var2, var18, 0.25F, new Color(0, 0, 0, 0), new Color(-1761607681, true));
      }

      if (!var12.isEmpty()) {
         this.drawSelectionHighlight(var5, var2, var7, var9, var12, var13, var14, var11);
         this.drawScaledText(var12, var5, var16, var17, var9);
      } else if (!this.guiTextField.isFocused() && !this.placeholder.isEmpty()) {
         this.drawScaledText("§7" + this.placeholder, var5, var16, 11184810, var9);
      }

      if (this.guiTextField.isFocused() && this.TYPeF3.isCursorVisible()) {
         this.drawCursor(var5, var2, var7, var9, var12, var13, var11);
      }
   }

   public void kqao7() {
      this.TYPeF3.tHdu(System.currentTimeMillis());
      this.guiTextField.updateCursorCounter();
   }

   public boolean emAy(int var1, int var2, float var3, float var4, float var5, float var6) {
      return ScrolledTextLayout.isPointInRect(var1, var2, var3, var4, var5, var6);
   }

   public boolean keyTyped(char var1, int var2) {
      return this.guiTextField.textboxKeyTyped(var1, var2);
   }

   public String getText() {
      return this.guiTextField.getText();
   }

   public void setText(String var1) {
      this.guiTextField.setText(var1 == null ? "" : var1);
   }

   public boolean isFocused() {
      return this.guiTextField.isFocused();
   }

   public void setFocused(boolean var1) {
      this.guiTextField.setFocused(var1);
      if (var1) {
         this.TYPeF3.resetBlink(System.currentTimeMillis());
      }
   }

   public void selectAll() {
      this.guiTextField.setCursorPositionEnd();
      this.guiTextField.setSelectionPos(0);
   }

   private void drawSelectionHighlight(float var1, float var2, float var3, IFont var4, String var5, int var6, int var7, int var8) {
      ScrolledTextLayout$1 var9 = ScrolledTextLayout.computeVisibleRange(var6, var7, var8, var5.length());
      if (!var9.TIsgeG()) {
         float var10 = var1 + var4.getStringWidth(var5.substring(0, var9.getStartIndex())) * this.textScale;
         float var11 = var1 + var4.getStringWidth(var5.substring(0, var9.getEndIndex())) * this.textScale;
         if (var11 > var10) {
            RenderUtils.XNRNki(var10, var2 + 1.0F, var11, var3 - 1.0F, -2142596894);
         }
      }
   }

   private void drawCursor(float var1, float var2, float var3, IFont var4, String var5, int var6, int var7) {
      int var8 = ScrolledTextLayout.ImA6(var6, var7, var5.length());
      float var9 = var1 + var4.getStringWidth(var5.substring(0, var8)) * this.textScale;
      RenderUtils.XNRNki(var9, var2 + 2.0F, var9 + 1.0F, var3 - 2.0F, -3092272);
   }

   private float computeCenteredTextY(float var1, float var2, IFont var3) {
      return SettingRowLayout.QYl2(var1, var2, this.textScale, var3.getTextTopOffset(), var3.getTextBottomOffset());
   }

   private void drawScaledText(String var1, float var2, float var3, int var4, IFont var5) {
      GL11.glPushMatrix();
      GL11.glScaled(this.textScale, this.textScale, this.textScale);
      var5.drawString(var1, var2 / this.textScale, var3 / this.textScale, var4, Gui.JzraV3());
      GL11.glPopMatrix();
   }
}
