// Jade recovery: original class: jade.deps.eLz.RWFbTX
package jade.client.gui;

import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.ProxyManager;
import jade.client.common.RoundedRect;
import jade.client.module.client.Gui;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class ProxyManagerScreen extends GuiScreen {
   private static final int WINDOW_WIDTH = 244;
   private static final int pHgbDw = 18;
   private static final int tOt = 6;
   private static final int FIELD_HEIGHT = 13;
   private static final int BUTTON_HEIGHT = 13;
   private static final int ksrV = 190;
   private static final int WINDOW_BACKGROUND_COLOR = -15066598;
   private static final int HEADER_COLOR = -15658735;
   private static final int ndhOrc = -16119285;
   private static final int SEPARATOR_COLOR = 352321535;
   private static final int ZsS = -986896;
   private static final int IDLE_TEXT_COLOR = -6645094;
   private static final int MUTED_TEXT_COLOR = -9803158;
   private static final int BUTTON_IDLE_COLOR = -15658735;
   private static final int jSbcEx = -12972016;
   private final GuiScreen Bal;
   private final ProxySettings originalSettings;
   private GuiTextField addressField;
   private GuiTextField bjNkt;
   private GuiTextField hxgC4;
   private GuiTextField passwordField;
   private ProxySettings$0 proxyType;
   private String errorMessage = "";
   private int windowX;
   private int windowY;

   public ProxyManagerScreen(GuiScreen var1) {
      this.Bal = var1;
      this.originalSettings = ProxyManager.rfB4();
      this.proxyType = this.originalSettings.getProxyType();
   }

   private IFont getHeaderFont() {
      return FontManager.getClickGuiHeaderRenderer(Gui.ISjhxoi());
   }

   private IFont getSettingFont() {
      return FontManager.getClickGuiSettingRenderer(Gui.ISjhxoi());
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.ROpzNc();
      this.initFields();
   }

   private void ROpzNc() {
      this.windowX = (this.width - 244) / 2;
      this.windowY = (this.height - 190) / 2;
   }

   private void initFields() {
      ProxySettings var1 = ProxyManager.rfB4();
      this.proxyType = var1.getProxyType();
      this.addressField = this.createField(1, this.windowY + 91);
      this.addressField.setText(var1.gtItl());
      this.addressField.setFocused(true);
      this.bjNkt = this.createField(2, this.windowY + 136);
      this.bjNkt.setText(var1.getUserId());
      this.hxgC4 = this.createField(3, this.windowY + 136);
      this.hxgC4.setText(var1.getUsername());
      this.passwordField = this.createField(4, this.windowY + 136);
      this.passwordField.setText(var1.getPassword());
   }

   private GuiTextField createField(int var1, int var2) {
      GuiTextField var3 = new GuiTextField(var1, this.mc.fontRendererObj, this.windowX + 6 + 3, var2 + 1, 226, 11);
      var3.setMaxStringLength(255);
      var3.setEnableBackgroundDrawing(false);
      var3.setTextColor(-986896);
      var3.setDisabledTextColour(-9803158);
      return var3;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.ROpzNc();
      this.repositionFields();
      if (this.Bal != null) {
         this.Bal.drawScreen(var1, var2, var3);
      } else {
         this.drawDefaultBackground();
      }

      drawRect(0, 0, this.width, this.height, 1711276032);
      float var4 = this.scaledWindowRadius();
      RoundedRect.drawRoundedRectArgb(this.windowX, this.windowY, 244.0F, 190.0F, var4, -15066598);
      RoundedRect.drawRoundedRectArgb(this.windowX, this.windowY, 244.0F, 18.0F, var4, -15658735);
      if (var4 > 0.0F) {
         drawRect(this.windowX, this.windowY + (int)var4, this.windowX + 244, this.windowY + 18, -15658735);
      }

      IFont var5 = this.getHeaderFont();
      this.jBjxNv();
      var5.drawString("Proxy Manager", this.windowX + 6, this.windowY + (18 - var5.getFontHeight()) / 2.0F, -986896);
      int var6 = this.windowX + 244 - 14;
      int var7 = this.windowY + 5;
      boolean var8 = this.isPointInRect(var1, var2, var6, var7, 10, 10);
      this.jBjxNv();
      this.getSettingFont().drawString("×", var6 + 1, var7 + 1, var8 ? -986896 : -6645094);
      this.drawCurrentProxyRow();
      drawRect(this.windowX, this.windowY + 18 + 18, this.windowX + 244, this.windowY + 18 + 19, 352321535);
      this.IOLkBpi(var1, var2);
      this.drawConnectionSection(var1, var2);
      this.drawActionButtons(var1, var2);
   }

   private void drawCurrentProxyRow() {
      IFont var1 = this.getSettingFont();
      int var2 = this.windowY + 18;
      String var3 = "Current:";
      float var4 = var1.getStringWidth(var3);
      this.jBjxNv();
      var1.drawString(var3, this.windowX + 6, var2 + (18 - var1.getFontHeight()) / 2.0F, -6645094);
      ProxySettings var5 = ProxyManager.rfB4();
      String var6 = var5.XAiikGo() ? var5.getProxyType().name() + " " + var5.getHost() + ":" + var5.getPort() : "Disabled";
      var1.drawString(
         this.truncateToWidth(var6, 232 - (int)var4 - 5, var1),
         this.windowX + 6 + var4 + 4.0F,
         var2 + (18 - var1.getFontHeight()) / 2.0F,
         var5.XAiikGo() ? this.XvfAm() : -9803158
      );
   }

   private void IOLkBpi(int var1, int var2) {
      IFont var3 = this.getSettingFont();
      this.jBjxNv();
      var3.drawString("Proxy Type", this.windowX + 6, this.windowY + 44, -6645094);
      byte var4 = 4;
      int var5 = (232 - var4) / 2;
      int var6 = this.windowX + 6;
      int var7 = var6 + var5 + var4;
      int var8 = this.windowY + 55;
      this.ElWp("SOCKS4", var6, var8, var5, 13, this.proxyType == ProxySettings$0.SOCKS4, this.isPointInRect(var1, var2, var6, var8, var5, 13));
      this.ElWp("SOCKS5", var7, var8, var5, 13, this.proxyType == ProxySettings$0.SOCKS5, this.isPointInRect(var1, var2, var7, var8, var5, 13));
   }

   private void drawConnectionSection(int var1, int var2) {
      IFont var3 = this.getSettingFont();
      this.jBjxNv();
      var3.drawString("IP:PORT", this.windowX + 6, this.windowY + 80, -6645094);
      this.drawFieldBackground(this.windowX + 6, this.windowY + 91, 232);
      this.drawTextField(this.addressField, "none", this.windowX + 6, this.windowY + 91, 232);
      if (this.proxyType == ProxySettings$0.SOCKS4) {
         this.jBjxNv();
         var3.drawString("User ID", this.windowX + 6, this.windowY + 125, -6645094);
         this.drawFieldBackground(this.windowX + 6, this.windowY + 136, 232);
         this.drawTextField(this.bjNkt, "optional", this.windowX + 6, this.windowY + 136, 232);
      } else {
         byte var4 = 4;
         int var5 = (232 - var4) / 2;
         int var6 = this.windowX + 6 + var5 + var4;
         this.jBjxNv();
         var3.drawString("Username", this.windowX + 6, this.windowY + 125, -6645094);
         this.drawFieldBackground(this.windowX + 6, this.windowY + 136, var5);
         this.drawTextField(this.hxgC4, "optional", this.windowX + 6, this.windowY + 136, var5);
         this.jBjxNv();
         var3.drawString("Password", var6, this.windowY + 125, -6645094);
         this.drawFieldBackground(var6, this.windowY + 136, var5);
         this.drawTextField(this.passwordField, "", var6, this.windowY + 136, var5);
      }

      if (this.errorMessage.length() > 0) {
         this.jBjxNv();
         var3.drawString(this.errorMessage, this.windowX + 6, this.windowY + 156, -34953);
      }
   }

   private void drawTextField(GuiTextField var1, String var2, int var3, int var4, int var5) {
      IFont var6 = this.getSettingFont();
      String var7 = var1.getText();
      boolean var8 = var1.isFocused();
      if (var7.isEmpty() && !var8) {
         float var17 = var6.getStringWidth(var2);
         float var18 = var4 + (13 - var6.getFontHeight()) / 2.0F;
         this.jBjxNv();
         var6.drawString(var2, var3 + (var5 - var17) / 2.0F, var18, -9803158);
      } else {
         float var9 = var6.getStringWidth(var7);
         float var10 = var4 + (13 - var6.getFontHeight()) / 2.0F;
         float var11 = var3 + 3.0F;
         float var12 = var3 + var5 - 3.0F;
         float var13 = var9 > var12 - var11 ? var11 : var3 + (var5 - var9) / 2.0F;
         this.jBjxNv();
         var6.drawString(var7, var13, var10, var8 ? -986896 : -6645094);
         if (var8 && System.currentTimeMillis() / 500L % 2L == 0L) {
            int var14 = Math.max(0, Math.min(var7.length(), var1.getCursorPosition()));
            float var15 = var13 + var6.getStringWidth(var7.substring(0, var14));
            int var16 = Math.max(var6.getFontHeight(), 8);
            drawRect((int)var15, (int)var10, (int)var15 + 1, (int)var10 + var16, -1);
         }
      }
   }

   private void drawActionButtons(int var1, int var2) {
      drawRect(this.windowX, this.windowY + 190 - 25, this.windowX + 244, this.windowY + 190 - 24, 352321535);
      byte var3 = 4;
      int var4 = (232 - var3) / 2;
      int var5 = this.windowY + 190 - 19;
      int var6 = this.windowX + 6;
      int var7 = var6 + var4 + var3;
      this.GOfaO4("Apply", var6, var5, var4, 13, this.isPointInRect(var1, var2, var6, var5, var4, 13), false);
      this.GOfaO4("Cancel", var7, var5, var4, 13, this.isPointInRect(var1, var2, var7, var5, var4, 13), true);
   }

   private void drawFieldBackground(int var1, int var2, int var3) {
      RoundedRect.drawRoundedRectArgb(var1, var2, var3, 13.0F, this.scaledCornerRadius(13.0F), -16119285);
   }

   private void ElWp(String var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      int var8 = var6 ? this.accentWithAlpha(150) : (var7 ? this.accentWithAlpha(80) : -15658735);
      RoundedRect.drawRoundedRectArgb(var2, var3, var4, var5, this.scaledCornerRadius(var5), var8);
      IFont var9 = this.getSettingFont();
      this.jBjxNv();
      var9.drawString(var1, var2 + (var4 - var9.getStringWidth(var1)) / 2.0F, var3 + (var5 - var9.getFontHeight()) / 2.0F, !var6 && !var7 ? -6645094 : -986896);
   }

   private void GOfaO4(String var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      RoundedRect.drawRoundedRectArgb(var2, var3, var4, var5, this.scaledCornerRadius(var5), var6 ? (var7 ? -12972016 : this.accentWithAlpha(170)) : -15658735);
      IFont var8 = this.getSettingFont();
      this.jBjxNv();
      var8.drawString(var1, var2 + (var4 - var8.getStringWidth(var1)) / 2.0F, var3 + (var5 - var8.getFontHeight()) / 2.0F, var6 ? -986896 : -6645094);
   }

   private void jBjxNv() {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.disableLighting();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         int var4 = this.windowX + 244 - 14;
         int var5 = this.windowY + 5;
         if (this.isPointInRect(var1, var2, var4, var5, 10, 10)) {
            this.closeScreen(false);
         } else {
            byte var6 = 4;
            int var7 = (232 - var6) / 2;
            int var8 = this.windowY + 55;
            int var9 = this.windowX + 6;
            int var10 = var9 + var7 + var6;
            if (this.isPointInRect(var1, var2, var9, var8, var7, 13)) {
               this.proxyType = ProxySettings$0.SOCKS4;
               this.errorMessage = "";
               this.jLpvafP();
            } else if (this.isPointInRect(var1, var2, var10, var8, var7, 13)) {
               this.proxyType = ProxySettings$0.SOCKS5;
               this.errorMessage = "";
               this.jLpvafP();
            } else {
               this.addressField.mouseClicked(var1, var2, var3);
               if (this.proxyType == ProxySettings$0.SOCKS4) {
                  this.bjNkt.mouseClicked(var1, var2, var3);
               } else {
                  this.hxgC4.mouseClicked(var1, var2, var3);
                  this.passwordField.mouseClicked(var1, var2, var3);
               }

               int var11 = this.windowY + 190 - 19;
               if (this.isPointInRect(var1, var2, var9, var11, var7, 13)) {
                  this.applySettings();
               } else if (this.isPointInRect(var1, var2, var10, var11, var7, 13)) {
                  this.closeScreen(true);
               }
            }
         }
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.closeScreen(false);
      } else if (var2 == 28) {
         this.applySettings();
      } else {
         this.addressField.textboxKeyTyped(var1, var2);
         if (this.proxyType == ProxySettings$0.SOCKS4) {
            this.bjNkt.textboxKeyTyped(var1, var2);
         } else {
            this.hxgC4.textboxKeyTyped(var1, var2);
            this.passwordField.textboxKeyTyped(var1, var2);
         }

         this.errorMessage = "";
      }
   }

   public void updateScreen() {
      this.addressField.updateCursorCounter();
      this.bjNkt.updateCursorCounter();
      this.hxgC4.updateCursorCounter();
      this.passwordField.updateCursorCounter();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private void applySettings() {
      ProxySettings var1 = this.buildSettingsFromFields();
      if (var1 != null) {
         ProxyManager.KOvZb(var1, true);
         this.closeScreen(false);
      }
   }

   private void closeScreen(boolean var1) {
      if (var1) {
         ProxyManager.KOvZb(this.originalSettings, false);
      }

      this.mc.displayGuiScreen(this.Bal);
   }

   private ProxySettings buildSettingsFromFields() {
      String var1 = this.addressField.getText().trim();
      if (var1.length() != 0 && !"none".equalsIgnoreCase(var1)) {
         int var2 = var1.lastIndexOf(58);
         if (var2 > 0 && var2 != var1.length() - 1) {
            String var3 = var1.substring(0, var2).trim();

            int var4;
            try {
               var4 = Integer.parseInt(var1.substring(var2 + 1).trim());
            } catch (NumberFormatException var6) {
               this.errorMessage = "Invalid port";
               this.addressField.setFocused(true);
               return null;
            }

            if (var3.length() != 0 && var4 >= 1 && var4 <= 65535) {
               return new ProxySettings(true, this.proxyType, var3, var4, this.bjNkt.getText(), this.hxgC4.getText(), this.passwordField.getText());
            } else {
               this.errorMessage = "Invalid IP:PORT";
               this.addressField.setFocused(true);
               return null;
            }
         } else {
            this.errorMessage = "Invalid IP:PORT";
            this.addressField.setFocused(true);
            return null;
         }
      } else {
         return ProxySettings.createDisabledSettings(this.proxyType, this.bjNkt.getText(), this.hxgC4.getText(), this.passwordField.getText());
      }
   }

   private void repositionFields() {
      this.addressField.xPosition = this.windowX + 6 + 3;
      this.addressField.yPosition = this.windowY + 91 + 1;
      jade.build.TextFieldAccess.setWidth(this.addressField, 226);
      this.bjNkt.xPosition = this.windowX + 6 + 3;
      this.bjNkt.yPosition = this.windowY + 136 + 1;
      jade.build.TextFieldAccess.setWidth(this.bjNkt, 226);
      byte var1 = 4;
      int var2 = (232 - var1) / 2;
      int var3 = this.windowX + 6 + var2 + var1;
      this.hxgC4.xPosition = this.windowX + 6 + 3;
      this.hxgC4.yPosition = this.windowY + 136 + 1;
      jade.build.TextFieldAccess.setWidth(this.hxgC4, var2 - 6);
      this.passwordField.xPosition = var3 + 3;
      this.passwordField.yPosition = this.windowY + 136 + 1;
      jade.build.TextFieldAccess.setWidth(this.passwordField, var2 - 6);
   }

   private void jLpvafP() {
      this.bjNkt.setFocused(false);
      this.hxgC4.setFocused(false);
      this.passwordField.setFocused(false);
      if (this.proxyType == ProxySettings$0.SOCKS4) {
         this.bjNkt.setFocused(true);
      } else {
         this.hxgC4.setFocused(true);
      }
   }

   private boolean isPointInRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      return var1 >= var3 && var1 < var3 + var5 && var2 >= var4 && var2 < var4 + var6;
   }

   private String truncateToWidth(String var1, int var2, IFont var3) {
      if (var3.getStringWidth(var1) <= var2) {
         return var1;
      } else {
         while (var1.length() > 1 && var3.getStringWidth(var1 + "..") > var2) {
            var1 = var1.substring(0, var1.length() - 1);
         }

         return var1 + "..";
      }
   }

   private float scaledWindowRadius() {
      float var1 = Math.min(244.0F, 34.0F);
      float var2 = Math.max(0.0F, var1 / 2.0F - 0.5F);
      return var2 * 0.625F * Math.min(1.0F, Math.max(0.0F, Gui.getRoundingPercent()) / 100.0F);
   }

   private float scaledCornerRadius(float var1) {
      float var2 = Math.max(0.0F, var1 / 2.0F - 0.5F);
      return var2 * 0.625F * Math.min(1.0F, Math.max(0.0F, Gui.getRoundingPercent()) / 100.0F);
   }

   private int XvfAm() {
      return Color.getHSBColor(Gui.getAccentHue() / 360.0F, Gui.zFsde8(), Gui.getAccentBrightness()).getRGB() | 0xFF000000;
   }

   private int accentWithAlpha(int var1) {
      Color var2 = Color.getHSBColor(Gui.getAccentHue() / 360.0F, Gui.zFsde8(), Gui.getAccentBrightness());
      return Math.max(0, Math.min(255, var1)) << 24 | var2.getRGB() & 16777215;
   }
}
