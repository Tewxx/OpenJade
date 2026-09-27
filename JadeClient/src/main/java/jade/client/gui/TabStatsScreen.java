// Jade recovery: original class: jade.deps.eLz.F1yPDwD
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.module.client.Gui;
import jade.client.module.minigames.TabStats$4;
import jade.client.module.minigames.TabStats;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.SliderSetting;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class TabStatsScreen extends GuiScreen {
   private static final int JQm = -1291845632;
   private static final int AQe = -1291319288;
   private static final int QSaX = -804253680;
   private static final int HOVER_OVERLAY_COLOR = 905969663;
   private static final int Rfy = -986380;
   private static final int LABEL_TEXT_COLOR = -5392964;
   private final GuiScreen parentScreen;
   private final TabStats tabStatsModule;
   private boolean Hh5;
   private final List<TabStatsScreen$3> widgetRows = new ArrayList<>();
   private final List<TabStatsScreen$2> jadfwT = new ArrayList<>();
   private final List<TabStatsScreen$4> optionRows = new ArrayList<>();
   private TabStatsScreen$5 doneButtonRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 bedwarsTabRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 skywarsTabRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 addRowButtonRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 colorPopupRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 saturationBrightnessRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 YMFd = TabStatsScreen$5.vNij();
   private TabStatsScreen$5 JyHpg = TabStatsScreen$5.vNij();
   private SliderSetting UXKemI;
   private TabStatsScreen$5 activeSliderTrackRect = TabStatsScreen$5.vNij();
   private SliderSetting EIcsYj;
   private TabStatsScreen$5 dropdownAnchorRect = TabStatsScreen$5.vNij();
   private ColorSetting openColorSetting;
   private TabStatsScreen$5 colorRowRect = TabStatsScreen$5.vNij();
   private TabStatsScreen$1 activeDragMode = TabStatsScreen$1.NONE;
   private boolean ruA;
   private TabStats$4 OOZDUq;
   private int LFwD5;
   private int dragStartY;
   private int UMJO;
   private int maxScroll;
   private float tabIndicatorX = Float.NaN;
   private long xPx2;

   public TabStatsScreen(GuiScreen var1, TabStats var2, boolean var3) {
      this.parentScreen = var1;
      this.tabStatsModule = var2;
      this.Hh5 = var3;
   }

   public void initGui() {
      this.buttonList.clear();
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.resetGlState();
      if (this.UXKemI != null) {
         this.setSliderFromMouse(this.UXKemI, this.activeSliderTrackRect, var1);
      }

      if (this.activeDragMode != TabStatsScreen$1.NONE) {
         this.updateColorFromDrag(var1, var2);
      }

      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, 2013265920);
      this.drawTabHeader(var1, var2);
      this.doneButtonRect = new TabStatsScreen$5(this.width - 78, 12, 62, 21);
      this.drawLabeledButton(this.doneButtonRect, "Done", var1, var2);
      float var4 = this.width >= 1000 ? 1.65F : (this.width >= 700 ? 1.4F : 1.1F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 37.0F, 0.0F);

      try {
         this.tabStatsModule.RgHc(this.width, this.Hh5, var4);
      } catch (Throwable var13) {
      }

      GlStateManager.popMatrix();
      this.resetGlState();
      int var5 = Math.max(8, Math.min(18, this.width / 50));
      int var6 = this.width - var5 * 2;
      int var7 = this.computeContentHeight(var6);
      int var8 = Math.min(var7, Math.max(1, this.height - 48 - var5));
      int var9 = this.height - var8 - var5;
      TabStatsScreen$5 var10 = new TabStatsScreen$5(var5, var9, var6, var8);
      this.UMJO = Math.max(0, Math.min(this.maxScroll, this.UMJO));
      RoundedRect.drawRoundedRectArgb(var10.x, var10.Yhf9, var10.width, var10.PWGp, 6.0F, -1291845632);
      RoundedRect.drawRoundedOutline(var10.x, var10.Yhf9, var10.width, var10.PWGp, 6.0F, 0.5F, new Color(0, 0, 0, 0), new Color(905969663, true));
      RenderUtils.pushScissorRect(var10.x, var10.Yhf9, var10.width, var10.PWGp);
      this.VCmCge(var10, var1, var2);
      int var11 = this.layoutRowChips(var10, var1, var2);
      RenderUtils.restoreScissorState();
      this.maxScroll = Math.max(0, var11 + this.UMJO - (var10.Yhf9 + var10.PWGp) + 10);
      if (this.EIcsYj != null) {
         this.drawOptionDropdown(var1, var2);
      }

      if (this.openColorSetting != null) {
         this.drawColorPopup();
      }

      if (this.ruA) {
         this.drawAddRowPopup(var1, var2);
      }

      if (this.OOZDUq != null && this.XbrKl(var1, var2)) {
         int var12 = this.getTextWidth(this.OOZDUq.label) + 16;
         RoundedRect.drawRoundedRectArgb(var1 + 8, var2 + 7, var12, 20.0F, 4.0F, -804253680);
         this.getSettingFont().drawString(this.OOZDUq.label, var1 + 16, var2 + 7 + this.getVerticalCenter(20), -986380, false);
      }
   }

   private void drawTabHeader(int var1, int var2) {
      int var3 = Math.min(220, Math.max(150, this.width / 4));
      int var4 = (this.width - var3) / 2;
      this.bedwarsTabRect = new TabStatsScreen$5(var4, 12, var3 / 2, 21);
      this.skywarsTabRect = new TabStatsScreen$5(var4 + var3 / 2, 12, var3 - var3 / 2, 21);
      RenderUtils.XNRNki(var4, 12.0, var4 + var3, 33.0, -1291319288);
      if (this.bedwarsTabRect.gUw8(var1, var2)) {
         RenderUtils.XNRNki(this.bedwarsTabRect.x, this.bedwarsTabRect.Yhf9, this.bedwarsTabRect.x + this.bedwarsTabRect.width, this.bedwarsTabRect.Yhf9 + this.bedwarsTabRect.PWGp, -804253680);
      }

      if (this.skywarsTabRect.gUw8(var1, var2)) {
         RenderUtils.XNRNki(this.skywarsTabRect.x, this.skywarsTabRect.Yhf9, this.skywarsTabRect.x + this.skywarsTabRect.width, this.skywarsTabRect.Yhf9 + this.skywarsTabRect.PWGp, -804253680);
      }

      float var5 = this.Hh5 ? this.skywarsTabRect.x : this.bedwarsTabRect.x;
      this.tabIndicatorX = this.smoothTowards(var5);
      int var6 = this.Hh5 ? this.skywarsTabRect.width : this.bedwarsTabRect.width;
      RenderUtils.XNRNki(this.tabIndicatorX, 12.0, this.tabIndicatorX + var6, 33.0, this.getDimmedAccentColor());
      this.getSettingFont()
         .drawString(
            "BedWars",
            this.bedwarsTabRect.x + (this.bedwarsTabRect.width - this.getTextWidth("BedWars")) / 2.0F,
            this.bedwarsTabRect.Yhf9 + this.getVerticalCenter(this.bedwarsTabRect.PWGp),
            !this.Hh5 ? -1 : -5392964,
            false
         );
      this.getSettingFont()
         .drawString(
            "SkyWars",
            this.skywarsTabRect.x + (this.skywarsTabRect.width - this.getTextWidth("SkyWars")) / 2.0F,
            this.skywarsTabRect.Yhf9 + this.getVerticalCenter(this.skywarsTabRect.PWGp),
            this.Hh5 ? -1 : -5392964,
            false
         );
   }

   private int computeContentHeight(int var1) {
      int var2 = var1 >= 780 ? 4 : (var1 >= 520 ? 3 : 2);
      int var3 = (10 + var2 - 1) / var2 * 35;
      int var4 = var1 - 24;
      int var5 = 1;
      int var6 = 0;

      for (TabStats$4 var8 : this.tabStatsModule.getConfiguredColumns(this.Hh5)) {
         int var9 = Math.max(38, this.getTextWidth(var8.label) + 16) + 5;
         if (var6 > 0 && var6 + var9 > var4 - 29) {
            var5++;
            var6 = 0;
         }

         var6 += var9;
      }

      return var3 + 39 + var5 * 25 + 10;
   }

   private float smoothTowards(float var1) {
      long var2 = System.currentTimeMillis();
      if (Float.isNaN(this.tabIndicatorX)) {
         this.tabIndicatorX = var1;
      }

      float var4 = this.xPx2 == 0L ? 1.0F : Math.min(1.0F, (float)(var2 - this.xPx2) / 120.0F);
      this.xPx2 = var2;
      float var5 = 1.0F - (float)Math.pow(1.0F - var4, 3.0);
      return this.tabIndicatorX + (var1 - this.tabIndicatorX) * var5;
   }

   private void VCmCge(TabStatsScreen$5 var1, int var2, int var3) {
      this.widgetRows.clear();
      this.optionRows.clear();
      byte var4 = 10;
      int var5 = var1.width >= 780 ? 4 : (var1.width >= 520 ? 3 : 2);
      int var6 = (var1.width - 24 - var4 * (var5 - 1)) / var5;
      int var7 = var1.x + 12;
      int var8 = var1.Yhf9 + 11 - this.UMJO;
      Object[] var9 = new Object[]{
         this.tabStatsModule.getFont(),
         this.tabStatsModule.getFontSize(),
         this.tabStatsModule.getRowHeight(),
         this.tabStatsModule.getPadding(),
         this.tabStatsModule.getColumnGap(),
         this.tabStatsModule.getMaxPlayers(),
         this.tabStatsModule.getRounding(),
         this.tabStatsModule.getSortSetting(this.Hh5),
         this.tabStatsModule.getDirection(),
         this.tabStatsModule.getTextShadow()
      };

      for (int var10 = 0; var10 < var9.length; var10++) {
         int var11 = var7 + var10 % var5 * (var6 + var4);
         int var12 = var8 + var10 / var5 * 35;
         Object var13 = var9[var10];
         if (var13 instanceof SliderSetting && ((SliderSetting)var13).isMode) {
            this.drawModeSliderRow((SliderSetting)var13, new TabStatsScreen$5(var11, var12, var6, 27), var2, var3);
         } else if (var13 instanceof SliderSetting) {
            this.drawValueSliderRow((SliderSetting)var13, new TabStatsScreen$5(var11, var12, var6, 27), var2, var3);
         } else {
            this.drawToggleRow((BooleanSetting)var13, new TabStatsScreen$5(var11, var12, var6, 27), var2, var3);
         }
      }

      ColorSetting[] var15 = this.tabStatsModule.getColorSettings();
      int var16 = var8 + (var9.length + var5 - 1) / var5 * 35;
      int var17 = Math.min(150, (var1.width - 24 - var4 * (var15.length - 1)) / var15.length);

      for (int var18 = 0; var18 < var15.length; var18++) {
         TabStatsScreen$5 var14 = new TabStatsScreen$5(var7 + var18 * (var17 + var4), var16, var17, 27);
         this.widgetRows.add(new TabStatsScreen$3(var14, var15[var18]));
         this.JSLm(var14, var2, var3);
         RoundedRect.drawRoundedRectArgb(var14.x + 6, var14.Yhf9 + 6, 15.0F, 15.0F, 4.0F, var15[var18].getArgb());
         this.getSettingFont().drawString(var15[var18].getName(), var14.x + 27, var14.Yhf9 + this.getVerticalCenter(var14.PWGp), -986380, false);
      }
   }

   private int layoutRowChips(TabStatsScreen$5 var1, int var2, int var3) {
      this.jadfwT.clear();
      int var4 = var1.width >= 780 ? 4 : (var1.width >= 520 ? 3 : 2);
      int var5 = var1.x + 12;
      int var6 = var1.Yhf9 + 11 - this.UMJO + (10 + var4 - 1) / var4 * 35 + 39;
      int var7 = var1.x + var1.width - 12;

      for (TabStats$4 var9 : this.tabStatsModule.getConfiguredColumns(this.Hh5)) {
         int var10 = Math.max(38, this.getTextWidth(var9.label) + 16);
         if (var5 + var10 > var7 - 29) {
            var5 = var1.x + 12;
            var6 += 25;
         }

         TabStatsScreen$5 var11 = new TabStatsScreen$5(var5, var6, var10, 20);
         this.jadfwT.add(new TabStatsScreen$2(var11, var9));
         RoundedRect.drawRoundedRectArgb(var11.x, var11.Yhf9, var11.width, var11.PWGp, 4.0F, !var11.gUw8(var2, var3) && var9 != this.OOZDUq ? -1291319288 : -804253680);
         this.getSettingFont().drawString(var9.label, var11.x + 8, var11.Yhf9 + this.getVerticalCenter(var11.PWGp), -986380, false);
         var5 += var10 + 5;
      }

      this.addRowButtonRect = new TabStatsScreen$5(Math.min(var5, var7 - 24), var6, 24, 20);
      RoundedRect.drawRoundedRectArgb(
         this.addRowButtonRect.x, this.addRowButtonRect.Yhf9, this.addRowButtonRect.width, this.addRowButtonRect.PWGp, 4.0F, !this.addRowButtonRect.gUw8(var2, var3) && !this.ruA ? -1291319288 : -804253680
      );
      this.getSettingFont()
         .drawString(
            "+", this.addRowButtonRect.x + (this.addRowButtonRect.width - this.getTextWidth("+")) / 2.0F, this.addRowButtonRect.Yhf9 + this.getVerticalCenter(this.addRowButtonRect.PWGp), this.HnXeuL(), false
         );
      return var6 + 20;
   }

   private void drawModeSliderRow(SliderSetting var1, TabStatsScreen$5 var2, int var3, int var4) {
      this.widgetRows.add(new TabStatsScreen$3(var2, var1));
      this.JSLm(var2, var3, var4);
      this.getSettingFont().drawString(var1.getName(), var2.x + 7, var2.Yhf9 + 4, -5392964, false);
      String[] var5 = var1.getOptions();
      int var6 = Math.max(0, Math.min(var5.length - 1, (int)Math.round(var1.getInput())));
      String var7 = var5[var6];
      this.getSettingFont().drawString(var7, var2.x + var2.width - this.getTextWidth(var7) - 7, var2.Yhf9 + 14, -986380, false);
   }

   private void drawValueSliderRow(SliderSetting var1, TabStatsScreen$5 var2, int var3, int var4) {
      TabStatsScreen$5 var5 = new TabStatsScreen$5(var2.x + 5, var2.Yhf9 + 20, var2.width - 10, 3);
      this.widgetRows.add(new TabStatsScreen$3(var2, new TabStatsScreen$6(var1, var5)));
      this.getSettingFont().drawString(var1.getName(), var2.x + 1, var2.Yhf9 + 3, -5392964, false);
      String var6 = this.duWz(var1);
      this.getSettingFont().drawString(var6, var2.x + var2.width - this.getTextWidth(var6), var2.Yhf9 + 3, -986380, false);
      RoundedRect.drawRoundedRectArgb(var5.x, var5.Yhf9, var5.width, var5.PWGp, 1.5F, -803727336);
      int var7 = Math.round(var5.width * this.getNormalizedValue(var1));
      RoundedRect.drawRoundedRectArgb(var5.x, var5.Yhf9, var7, var5.PWGp, 1.5F, this.HnXeuL());
      RoundedRect.drawRoundedRectArgb(var5.x + var7 - 2, var5.Yhf9 - 2, 6.0F, 7.0F, 3.0F, var2.gUw8(var3, var4) ? -1 : this.HnXeuL());
   }

   private void drawToggleRow(BooleanSetting var1, TabStatsScreen$5 var2, int var3, int var4) {
      this.widgetRows.add(new TabStatsScreen$3(var2, var1));
      this.JSLm(var2, var3, var4);
      this.getSettingFont().drawString(var1.getName(), var2.x + 7, var2.Yhf9 + this.getVerticalCenter(var2.PWGp), -986380, false);
      String var5 = var1.isToggled() ? "ON" : "OFF";
      this.getSettingFont()
         .drawString(var5, var2.x + var2.width - this.getTextWidth(var5) - 7, var2.Yhf9 + this.getVerticalCenter(var2.PWGp), var1.isToggled() ? this.HnXeuL() : -5392964, false);
   }

   private void drawOptionDropdown(int var1, int var2) {
      String[] var3 = this.EIcsYj.getOptions();
      int var4 = Math.max(this.dropdownAnchorRect.width, 120);
      int var5 = var3.length * 19 + 8;
      int var6 = Math.max(5, Math.min(this.width - var4 - 5, this.dropdownAnchorRect.x));
      int var7 = this.dropdownAnchorRect.Yhf9 + this.dropdownAnchorRect.PWGp + 4;
      if (var7 + var5 > this.height - 5) {
         var7 = this.dropdownAnchorRect.Yhf9 - var5 - 4;
      }

      RoundedRect.drawRoundedRectArgb(var6, var7, var4, var5, 6.0F, -301463544);

      for (int var8 = 0; var8 < var3.length; var8++) {
         TabStatsScreen$5 var9 = new TabStatsScreen$5(var6 + 4, var7 + 4 + var8 * 19, var4 - 8, 17);
         this.optionRows.add(new TabStatsScreen$4(var9, this.EIcsYj, var8));
         if (var9.gUw8(var1, var2)) {
            RoundedRect.drawRoundedRectArgb(var9.x, var9.Yhf9, var9.width, var9.PWGp, 3.0F, 905969663);
         }

         this.getSettingFont()
            .drawString(
               var3[var8], var9.x + 6, var9.Yhf9 + this.getVerticalCenter(var9.PWGp), var8 == (int)Math.round(this.EIcsYj.getInput()) ? this.HnXeuL() : -986380, false
            );
      }
   }

   private void drawColorPopup() {
      short var1 = 152;
      int var2 = this.openColorSetting.supportsAlpha() ? 114 : 86;
      int var3 = Math.max(5, Math.min(this.width - var1 - 5, this.colorRowRect.x));
      int var4 = this.colorRowRect.Yhf9 + this.colorRowRect.PWGp + 4;
      if (var4 + var2 > this.height - 5) {
         var4 = this.colorRowRect.Yhf9 - var2 - 4;
      }

      var4 = Math.max(5, Math.min(this.height - var2 - 5, var4));
      this.colorPopupRect = new TabStatsScreen$5(var3, var4, var1, var2);
      RoundedRect.drawRoundedRectArgb(var3, var4, var1, var2, 9.0F, -267909112);
      RoundedRect.drawRoundedOutline(var3, var4, var1, var2, 9.0F, 0.5F, new Color(0, 0, 0, 0), new Color(905969663, true));
      this.saturationBrightnessRect = new TabStatsScreen$5(var3 + 10, var4 + 10, var1 - 40, 66);
      this.YMFd = new TabStatsScreen$5(var3 + var1 - 20, var4 + 10, 8, 66);
      int var5 = Color.HSBtoRGB(this.openColorSetting.getHue() / 360.0F, 1.0F, 1.0F) | 0xFF000000;
      RoundedRect.drawFourCornerGradientArgb(this.saturationBrightnessRect.x, this.saturationBrightnessRect.Yhf9, this.saturationBrightnessRect.width, this.saturationBrightnessRect.PWGp, 5.0F, -16777216, -1, -16777216, var5);

      for (int var6 = 0; var6 < 20; var6++) {
         int var7 = this.YMFd.Yhf9 + this.YMFd.PWGp * var6 / 20;
         int var8 = this.YMFd.Yhf9 + this.YMFd.PWGp * (var6 + 1) / 20;
         RenderUtils.drawVerticalGradient(
            this.YMFd.x,
            var7,
            this.YMFd.x + this.YMFd.width,
            var8,
            Color.HSBtoRGB(var6 / 20.0F, 1.0F, 1.0F) | 0xFF000000,
            Color.HSBtoRGB((var6 + 1) / 20.0F, 1.0F, 1.0F) | 0xFF000000
         );
      }

      int var11 = this.saturationBrightnessRect.x + Math.round(this.openColorSetting.getSaturation() * this.saturationBrightnessRect.width);
      int var12 = this.saturationBrightnessRect.Yhf9 + Math.round((1.0F - this.openColorSetting.pBf3()) * this.saturationBrightnessRect.PWGp);
      RenderUtils.XNRNki(var11 - 3, var12, var11 + 4, var12 + 1, -1);
      RenderUtils.XNRNki(var11, var12 - 3, var11 + 1, var12 + 4, -1);
      int var13 = this.YMFd.Yhf9 + Math.round(this.openColorSetting.getHue() / 360.0F * this.YMFd.PWGp);
      RenderUtils.XNRNki(this.YMFd.x - 2, var13 - 1, this.YMFd.x + this.YMFd.width + 2, var13 + 2, -1);
      if (this.openColorSetting.supportsAlpha()) {
         this.JyHpg = new TabStatsScreen$5(var3 + 10, var4 + 94, var1 - 20, 8);
         RenderUtils.drawHorizontalGradient(
            this.JyHpg.x,
            this.JyHpg.Yhf9,
            this.JyHpg.x + this.JyHpg.width,
            this.JyHpg.Yhf9 + this.JyHpg.PWGp,
            this.openColorSetting.getRgb(),
            this.openColorSetting.getArgb()
         );
         int var9 = this.JyHpg.x + Math.round(this.openColorSetting.JIjrD() / 255.0F * this.JyHpg.width);
         RenderUtils.XNRNki(var9 - 1, this.JyHpg.Yhf9 - 2, var9 + 2, this.JyHpg.Yhf9 + this.JyHpg.PWGp + 2, -1);
      } else {
         this.JyHpg = TabStatsScreen$5.vNij();
      }
   }

   private void drawAddRowPopup(int var1, int var2) {
      List var3 = this.tabStatsModule.getAvailableColumns(this.Hh5);
      short var4 = 142;
      int var5 = Math.max(28, var3.size() * 19 + 8);
      int var6 = Math.max(5, Math.min(this.width - var4 - 5, this.addRowButtonRect.x));
      int var7 = this.addRowButtonRect.Yhf9 + this.addRowButtonRect.PWGp + 4;
      if (var7 + var5 > this.height - 5) {
         var7 = this.addRowButtonRect.Yhf9 - var5 - 4;
      }

      RoundedRect.drawRoundedRectArgb(var6, var7, var4, var5, 6.0F, -301463544);

      for (int var8 = 0; var8 < var3.size(); var8++) {
         TabStatsScreen$5 var9 = new TabStatsScreen$5(var6 + 4, var7 + 4 + var8 * 19, var4 - 8, 17);
         this.widgetRows.add(new TabStatsScreen$3(var9, new TabStatsScreen$0((TabStats$4)var3.get(var8))));
         if (var9.gUw8(var1, var2)) {
            RoundedRect.drawRoundedRectArgb(var9.x, var9.Yhf9, var9.width, var9.PWGp, 3.0F, 905969663);
         }

         this.getSettingFont().drawString(((TabStats$4)var3.get(var8)).label, var9.x + 6, var9.Yhf9 + this.getVerticalCenter(var9.PWGp), -986380, false);
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (this.doneButtonRect.gUw8(var1, var2)) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else if ((var3 == 0 || var3 == 1) && (this.bedwarsTabRect.gUw8(var1, var2) || this.skywarsTabRect.gUw8(var1, var2))) {
         boolean var9 = this.skywarsTabRect.gUw8(var1, var2);
         if (var9 != this.Hh5) {
            this.Hh5 = var9;
            this.UMJO = 0;
            this.closePopups();
         }
      } else {
         for (TabStatsScreen$4 var5 : this.optionRows) {
            if (var5.rect.gUw8(var1, var2)) {
               var5.sliderSetting.setValueClamped(var5.optionIndex);
               this.EIcsYj = null;
               this.markConfigUnsaved();
               return;
            }
         }

         if (this.openColorSetting != null) {
            if (this.saturationBrightnessRect.gUw8(var1, var2)) {
               this.activeDragMode = TabStatsScreen$1.SAT_BRIGHT;
               this.updateColorFromDrag(var1, var2);
               return;
            }

            if (this.YMFd.gUw8(var1, var2)) {
               this.activeDragMode = TabStatsScreen$1.HUE;
               this.updateColorFromDrag(var1, var2);
               return;
            }

            if (this.JyHpg.gUw8(var1, var2)) {
               this.activeDragMode = TabStatsScreen$1.ALPHA;
               this.updateColorFromDrag(var1, var2);
               return;
            }

            if (this.colorPopupRect.gUw8(var1, var2)) {
               return;
            }
         }

         if (this.addRowButtonRect.gUw8(var1, var2)) {
            this.ruA = !this.ruA;
            this.EIcsYj = null;
            this.openColorSetting = null;
         } else {
            for (TabStatsScreen$2 var10 : this.jadfwT) {
               if (var10.wHe.gUw8(var1, var2)) {
                  if (var3 == 1) {
                     this.removeRow(var10.rowDefinition);
                     this.markConfigUnsaved();
                     return;
                  }

                  this.OOZDUq = var10.rowDefinition;
                  this.LFwD5 = var1;
                  this.dragStartY = var2;
                  return;
               }
            }

            for (TabStatsScreen$3 var11 : this.widgetRows) {
               if (var11.rect.gUw8(var1, var2)) {
                  if (var11.Kuw instanceof TabStatsScreen$6) {
                     TabStatsScreen$6 var6 = (TabStatsScreen$6)var11.Kuw;
                     this.UXKemI = var6.sliderSetting;
                     this.activeSliderTrackRect = var6.trackRect;
                     this.setSliderFromMouse(var6.sliderSetting, var6.trackRect, var1);
                  } else if (var11.Kuw instanceof SliderSetting) {
                     SliderSetting var12 = (SliderSetting)var11.Kuw;
                     this.EIcsYj = this.EIcsYj == var12 ? null : var12;
                     this.dropdownAnchorRect = var11.rect;
                     this.openColorSetting = null;
                     this.ruA = false;
                  } else if (var11.Kuw instanceof BooleanSetting) {
                     ((BooleanSetting)var11.Kuw).toggle();
                     this.markConfigUnsaved();
                  } else if (var11.Kuw instanceof ColorSetting) {
                     ColorSetting var13 = (ColorSetting)var11.Kuw;
                     this.openColorSetting = this.openColorSetting == var13 ? null : var13;
                     this.colorRowRect = var11.rect;
                     this.EIcsYj = null;
                     this.ruA = false;
                  } else if (var11.Kuw instanceof TabStatsScreen$0) {
                     ArrayList var14 = new ArrayList<>(this.tabStatsModule.getConfiguredColumns(this.Hh5));
                     var14.add(((TabStatsScreen$0)var11.Kuw).rowDefinition);
                     this.tabStatsModule.setColumnOrder(this.Hh5, var14);
                     this.markConfigUnsaved();
                     this.ruA = false;
                  }

                  return;
               }
            }

            this.closePopups();
            super.mouseClicked(var1, var2, var3);
         }
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      this.UXKemI = null;
      this.activeDragMode = TabStatsScreen$1.NONE;
      if (var3 == 0 && this.OOZDUq != null) {
         if (this.XbrKl(var1, var2)) {
            this.reorderRows(var1, var2);
         }

         this.OOZDUq = null;
         this.markConfigUnsaved();
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0 && this.maxScroll > 0) {
         this.UMJO = Math.max(0, Math.min(this.maxScroll, this.UMJO + (var1 < 0 ? 30 : -30)));
      }
   }

   private void reorderRows(int var1, int var2) {
      ArrayList var3 = new ArrayList<>(this.tabStatsModule.getConfiguredColumns(this.Hh5));
      var3.remove(this.OOZDUq);
      int var4 = 0;

      for (TabStatsScreen$2 var6 : this.jadfwT) {
         if (var6.rowDefinition != this.OOZDUq) {
            int var7 = var6.wHe.Yhf9 + var6.wHe.PWGp / 2;
            if (var2 > var7 || Math.abs(var2 - var7) <= var6.wHe.PWGp && var1 > var6.wHe.x + var6.wHe.width / 2) {
               var4++;
            }
         }
      }

      var3.add(Math.max(0, Math.min(var3.size(), var4)), this.OOZDUq);
      this.tabStatsModule.setColumnOrder(this.Hh5, var3);
   }

   private void removeRow(TabStats$4 var1) {
      ArrayList var2 = new ArrayList<>(this.tabStatsModule.getConfiguredColumns(this.Hh5));
      if (var2.size() > 1) {
         var2.remove(var1);
         this.tabStatsModule.setColumnOrder(this.Hh5, var2);
      }
   }

   private void setSliderFromMouse(SliderSetting var1, TabStatsScreen$5 var2, int var3) {
      double var4 = Math.max(0.0, Math.min(1.0, (double)(var3 - var2.x) / Math.max(1, var2.width)));
      var1.setValueClamped(var1.getMin() + (var1.getMax() - var1.getMin()) * var4);
      this.markConfigUnsaved();
   }

   private void updateColorFromDrag(int var1, int var2) {
      if (this.openColorSetting != null) {
         if (this.activeDragMode == TabStatsScreen$1.SAT_BRIGHT) {
            this.openColorSetting
               .TAfvrw(
                  this.openColorSetting.getHue(),
                  this.clamp01((float)(var1 - this.saturationBrightnessRect.x) / this.saturationBrightnessRect.width),
                  1.0F - this.clamp01((float)(var2 - this.saturationBrightnessRect.Yhf9) / this.saturationBrightnessRect.PWGp)
               );
         } else if (this.activeDragMode == TabStatsScreen$1.HUE) {
            this.openColorSetting.oAej(this.clamp01((float)(var2 - this.YMFd.Yhf9) / this.YMFd.PWGp) * 360.0F);
         } else if (this.activeDragMode == TabStatsScreen$1.ALPHA) {
            this.openColorSetting.setAlpha(Math.round(this.clamp01((float)(var1 - this.JyHpg.x) / this.JyHpg.width) * 255.0F));
         }

         this.markConfigUnsaved();
      }
   }

   private void JSLm(TabStatsScreen$5 var1, int var2, int var3) {
      RoundedRect.drawRoundedRectArgb(var1.x, var1.Yhf9, var1.width, var1.PWGp, 4.0F, var1.gUw8(var2, var3) ? -804253680 : -1291319288);
   }

   private void drawLabeledButton(TabStatsScreen$5 var1, String var2, int var3, int var4) {
      boolean var5 = var1.gUw8(var3, var4);
      RoundedRect.drawRoundedRectArgb(var1.x, var1.Yhf9, var1.width, var1.PWGp, 4.0F, var5 ? this.getDimmedAccentColor() : -1291319288);
      this.getSettingFont().drawString(var2, var1.x + (var1.width - this.getTextWidth(var2)) / 2.0F, var1.Yhf9 + this.getVerticalCenter(var1.PWGp), -986380, false);
   }

   private void closePopups() {
      this.EIcsYj = null;
      this.openColorSetting = null;
      this.ruA = false;
   }

   private boolean XbrKl(int var1, int var2) {
      return Math.abs(var1 - this.LFwD5) > 3 || Math.abs(var2 - this.dragStartY) > 3;
   }

   private float getNormalizedValue(SliderSetting var1) {
      return (float)((var1.getInput() - var1.getMin()) / Math.max(1.0E-4, var1.getMax() - var1.getMin()));
   }

   private float clamp01(float var1) {
      return Math.max(0.0F, Math.min(1.0F, var1));
   }

   private String duWz(SliderSetting var1) {
      double var2 = var1.getInput();
      return (Math.abs(var2 - Math.round(var2)) < 0.001 ? Integer.toString((int)Math.round(var2)) : String.format(Locale.ROOT, "%.2f", var2)) + var1.getSuffix();
   }

   private IFont getSettingFont() {
      return Gui.getSettingFont();
   }

   private int getTextWidth(String var1) {
      return this.getSettingFont().getStringWidth(var1 == null ? "" : var1);
   }

   private float getVerticalCenter(int var1) {
      return Math.max(2.0F, (var1 - this.getSettingFont().getFontHeight()) / 2.0F);
   }

   private int HnXeuL() {
      return Color.getHSBColor(Gui.getAccentHue() / 360.0F, Gui.zFsde8(), Gui.getAccentBrightness()).getRGB() | 0xFF000000;
   }

   private int getDimmedAccentColor() {
      Color var1 = new Color(this.HnXeuL(), true);
      return new Color(Math.round(var1.getRed() * 0.62F), Math.round(var1.getGreen() * 0.62F), Math.round(var1.getBlue() * 0.62F), 220).getRGB();
   }

   private void markConfigUnsaved() {
      if (Jade.Grq != null) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }

   private void resetGlState() {
      GL11.glDisable(3089);
      GlStateManager.disableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.enableTexture2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else {
         super.keyTyped(var1, var2);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
