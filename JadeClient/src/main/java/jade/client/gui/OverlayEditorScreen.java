// Jade recovery: original class: jade.deps.eLz.OaDR5Ryor
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.module.client.Gui;
import jade.client.module.minigames.Overlay$11;
import jade.client.module.minigames.Overlay$12;
import jade.client.module.minigames.Overlay;
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

public class OverlayEditorScreen extends GuiScreen {
   private static final int WINDOW_BACKGROUND_COLOR = -1291845632;
   private static final int OUTLINE_COLOR = 620756991;
   private static final int SURFACE_COLOR = -1291319288;
   private static final int HOVER_FILL_COLOR = -804253680;
   private static final int PRIMARY_TEXT_COLOR = -789001;
   private static final int MUTED_TEXT_COLOR = -5524803;
   private static final String[] COLOR_SETTING_LABELS = new String[]{"Panel", "Header", "Text", "Name"};
   private final GuiScreen USLCzC;
   private final Overlay overlayModule;
   private boolean SCCzz;
   private final List<OverlayEditorScreen$7> sliderRows = new ArrayList<>();
   private final List<OverlayEditorScreen$5> gDg79 = new ArrayList<>();
   private final List<OverlayEditorScreen$4> optionWidgets = new ArrayList<>();
   private final List<OverlayEditorScreen$8> toggleWidgets = new ArrayList<>();
   private final List<OverlayEditorScreen$2> AYXF = new ArrayList<>();
   private final List<OverlayEditorScreen$3> Iqu6 = new ArrayList<>();
   private final List<OverlayEditorScreen$3> addColumnRows = new ArrayList<>();
   private final List<OverlayEditorScreen$3> dY2 = new ArrayList<>();
   private Overlay$12 columnLayout;
   private OverlayEditorScreen$6 xvK = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 doneButtonRect = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 saturationBrightnessRect = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 ojp = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 alphaBarRect = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$7 activeSliderRow;
   private OverlayEditorScreen$1 RVO = OverlayEditorScreen$1.NONE;
   private String draggedColumnId;
   private boolean draggingNewColumn;
   private boolean dragMoved;
   private int gW0;
   private int dragStartY;
   private boolean addColumnPopupOpen;
   private int selectedColorIndex;
   private int ag3;
   private boolean colorPopupOpen;
   private OverlayEditorScreen$6 bedwarsTabRect = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 skywarsTabRect = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 colorPopupRect = OverlayEditorScreen$6.createEmpty();
   private OverlayEditorScreen$6 colorSwatchRect = OverlayEditorScreen$6.createEmpty();
   private SliderSetting openDropdownSetting;
   private OverlayEditorScreen$6 dropdownAnchorRect = OverlayEditorScreen$6.createEmpty();
   private int SLuIts;
   private int maxScroll;
   private float TXO = Float.NaN;
   private long lastAnimationTime;

   public OverlayEditorScreen(GuiScreen var1, Overlay var2, boolean var3) {
      this.USLCzC = var1;
      this.overlayModule = var2;
      this.SCCzz = var3;
   }

   public void initGui() {
      this.buttonList.clear();
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.resetGlState();
      this.updateDragInteractions(var1, var2);
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, 2013265920);
      this.crCtiF(var1, var2);
      float[] var4 = this.overlayModule.HPmA(1.0F, this.SCCzz);
      float var5 = Math.max(260.0F, this.width - 160.0F);
      float var6 = Math.max(0.9F, Math.min(this.width < 900 ? 1.2F : 1.55F, var5 / Math.max(1.0F, var4[0])));
      float[] var7 = this.overlayModule.HPmA(var6, this.SCCzz);
      int var8 = Math.max(24, Math.round((this.width - var7[0]) / 2.0F));
      byte var9 = 42;
      this.resetGlState();

      try {
         this.columnLayout = this.overlayModule.wyJ5(var8, var9, var6, this.SCCzz);
      } catch (RuntimeException var15) {
         this.columnLayout = null;
      } catch (LinkageError var16) {
         this.columnLayout = null;
      }

      this.resetGlState();
      this.buildColumnWidgets(var1, var2);
      int var10 = this.width < 600 ? 8 : 18;
      int var11 = this.width - var10 * 2;
      int var12 = Math.min(this.computeSettingsPanelHeight(var11), Math.max(1, this.height - 48 - var10));
      this.ag3 = this.height - var12 - var10;
      this.drawSettingsPanel(var1, var2);
      if (this.openDropdownSetting != null) {
         this.ddF6(var1, var2);
      }

      if (this.colorPopupOpen) {
         this.drawColorPopup();
      }

      this.drawButtonBox(this.doneButtonRect = new OverlayEditorScreen$6(this.width - 84, 14, 66, 22), "Done", var1, var2);
      if (this.draggedColumnId != null) {
         String var13 = this.overlayModule.getColumnDisplayName(this.draggedColumnId);
         int var14 = Math.max(50, this.getTextWidth(var13) + 12);
         RoundedRect.drawRoundedRectArgb(var1 + 10, var2 + 8, var14, 17.0F, 4.0F, -804253680);
         this.qWgoR().drawString(var13, var1 + 16, var2 + 13, -789001, false);
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

   private void drawSettingsPanel(int var1, int var2) {
      this.sliderRows.clear();
      this.gDg79.clear();
      this.optionWidgets.clear();
      this.toggleWidgets.clear();
      this.AYXF.clear();
      int var3 = this.width < 600 ? 8 : 18;
      int var4 = this.width - var3 * 2;
      int var5 = this.computeSettingsPanelHeight(var4);
      OverlayEditorScreen$6 var6 = new OverlayEditorScreen$6(var3, this.ag3, var4, Math.min(var5, Math.max(1, this.height - this.ag3 - var3)));
      this.SLuIts = Math.max(0, Math.min(this.maxScroll, this.SLuIts));
      RoundedRect.drawRoundedRectArgb(OverlayEditorScreen$6.getX(var6), OverlayEditorScreen$6.getY(var6), OverlayEditorScreen$6.getWidth(var6), OverlayEditorScreen$6.getHeight(var6), 7.0F, -1291845632);
      RoundedRect.drawRoundedOutline(
         OverlayEditorScreen$6.getX(var6),
         OverlayEditorScreen$6.getY(var6),
         OverlayEditorScreen$6.getWidth(var6),
         OverlayEditorScreen$6.getHeight(var6),
         7.0F,
         0.5F,
         new Color(0, 0, 0, 0),
         new Color(620756991, true)
      );
      int var7 = OverlayEditorScreen$6.getWidth(var6) >= 720 ? 3 : (OverlayEditorScreen$6.getWidth(var6) >= 460 ? 2 : 1);
      byte var8 = 8;
      int var9 = OverlayEditorScreen$6.getX(var6) + 10;
      int var10 = OverlayEditorScreen$6.getWidth(var6) - 20;
      int var11 = (var10 - var8 * (var7 - 1)) / var7;
      int[] var12 = new int[var7];

      for (int var13 = 0; var13 < var7; var13++) {
         var12[var13] = OverlayEditorScreen$6.getY(var6) + 10 - this.SLuIts;
      }

      RenderUtils.pushScissorRect(OverlayEditorScreen$6.getX(var6), OverlayEditorScreen$6.getY(var6), OverlayEditorScreen$6.getWidth(var6), OverlayEditorScreen$6.getHeight(var6));
      BooleanSetting[] var23 = this.overlayModule.getAppearanceToggles();

      for (int var14 = 0; var14 < var23.length; var14++) {
         int var15 = this.indexOfMin(var12);
         var12[var15] = this.QojuX(var23[var14], var9 + var15 * (var11 + var8), var12[var15], var11, var1, var2);
      }

      SliderSetting[] var24 = this.overlayModule.EIAJ(this.SCCzz);

      for (int var25 = 0; var25 < var24.length; var25++) {
         int var16 = this.indexOfMin(var12);
         int var17 = var9 + var16 * (var11 + var8);
         if (var24[var25].isMode) {
            var12[var16] = this.drawModeSliderRow(var24[var25], var17, var12[var16], var11, var1, var2);
         } else {
            var12[var16] = this.YAmr8(var24[var25], var17, var12[var16], var11, var1, var2);
         }
      }

      int var26 = this.maxValue(var12) + 3;
      ColorSetting[] var27 = this.overlayModule.RmynT();
      int var28 = Math.max(1, Math.min(var27.length, var10 / 76));
      int var18 = (var10 - (var28 - 1) * 3) / var28;

      for (int var19 = 0; var19 < var27.length; var19++) {
         int var20 = var19 / var28;
         int var21 = var19 % var28;
         OverlayEditorScreen$6 var22 = new OverlayEditorScreen$6(var9 + var21 * (var18 + 3), var26 + var20 * 19, var18, 16);
         this.AYXF.add(new OverlayEditorScreen$2(var22, var19));
         RoundedRect.drawRoundedRectArgb(
            OverlayEditorScreen$6.getX(var22),
            OverlayEditorScreen$6.getY(var22),
            OverlayEditorScreen$6.getWidth(var22),
            OverlayEditorScreen$6.getHeight(var22),
            3.0F,
            var19 == this.selectedColorIndex ? -804253680 : -1291319288
         );
         RenderUtils.XNRNki(
            OverlayEditorScreen$6.getX(var22) + 4,
            OverlayEditorScreen$6.getY(var22) + 4,
            OverlayEditorScreen$6.getX(var22) + 10,
            OverlayEditorScreen$6.getY(var22) + 12,
            0xFF000000 | var27[var19].getRgb()
         );
         this.qWgoR().drawString(COLOR_SETTING_LABELS[var19], OverlayEditorScreen$6.getX(var22) + 14, OverlayEditorScreen$6.getY(var22) + 4, var19 == this.selectedColorIndex ? -789001 : -5524803, false);
      }

      RenderUtils.restoreScissorState();
      int var29 = (var27.length + var28 - 1) / var28;
      int var30 = var26 + this.SLuIts + var29 * 19 + 8;
      this.maxScroll = Math.max(0, var30 - (OverlayEditorScreen$6.getY(var6) + OverlayEditorScreen$6.getHeight(var6)));
   }

   private void crCtiF(int var1, int var2) {
      int var3 = Math.min(220, Math.max(150, this.width / 4));
      int var4 = (this.width - var3) / 2;
      this.bedwarsTabRect = new OverlayEditorScreen$6(var4, 12, var3 / 2, 21);
      this.skywarsTabRect = new OverlayEditorScreen$6(var4 + var3 / 2, 12, var3 - var3 / 2, 21);
      RenderUtils.XNRNki(var4, 12.0, var4 + var3, 33.0, -1291319288);
      if (OverlayEditorScreen$6.isInside(this.bedwarsTabRect, var1, var2)) {
         RenderUtils.XNRNki(
            OverlayEditorScreen$6.getX(this.bedwarsTabRect),
            OverlayEditorScreen$6.getY(this.bedwarsTabRect),
            OverlayEditorScreen$6.getX(this.bedwarsTabRect) + OverlayEditorScreen$6.getWidth(this.bedwarsTabRect),
            OverlayEditorScreen$6.getY(this.bedwarsTabRect) + OverlayEditorScreen$6.getHeight(this.bedwarsTabRect),
            -804253680
         );
      }

      if (OverlayEditorScreen$6.isInside(this.skywarsTabRect, var1, var2)) {
         RenderUtils.XNRNki(
            OverlayEditorScreen$6.getX(this.skywarsTabRect),
            OverlayEditorScreen$6.getY(this.skywarsTabRect),
            OverlayEditorScreen$6.getX(this.skywarsTabRect) + OverlayEditorScreen$6.getWidth(this.skywarsTabRect),
            OverlayEditorScreen$6.getY(this.skywarsTabRect) + OverlayEditorScreen$6.getHeight(this.skywarsTabRect),
            -804253680
         );
      }

      float var5 = this.SCCzz ? OverlayEditorScreen$6.getX(this.skywarsTabRect) : OverlayEditorScreen$6.getX(this.bedwarsTabRect);
      this.TXO = this.smoothTowards(var5);
      int var6 = this.SCCzz ? OverlayEditorScreen$6.getWidth(this.skywarsTabRect) : OverlayEditorScreen$6.getWidth(this.bedwarsTabRect);
      RenderUtils.XNRNki(this.TXO, 12.0, this.TXO + var6, 33.0, this.RwmKru());
      this.qWgoR()
         .drawString(
            "BedWars",
            OverlayEditorScreen$6.getX(this.bedwarsTabRect) + (OverlayEditorScreen$6.getWidth(this.bedwarsTabRect) - this.getTextWidth("BedWars")) / 2.0F,
            OverlayEditorScreen$6.getY(this.bedwarsTabRect) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(this.bedwarsTabRect)),
            !this.SCCzz ? -1 : -5524803,
            false
         );
      this.qWgoR()
         .drawString(
            "SkyWars",
            OverlayEditorScreen$6.getX(this.skywarsTabRect) + (OverlayEditorScreen$6.getWidth(this.skywarsTabRect) - this.getTextWidth("SkyWars")) / 2.0F,
            OverlayEditorScreen$6.getY(this.skywarsTabRect) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(this.skywarsTabRect)),
            this.SCCzz ? -1 : -5524803,
            false
         );
   }

   private int computeSettingsPanelHeight(int var1) {
      int var2 = var1 >= 720 ? 3 : (var1 >= 460 ? 2 : 1);
      int[] var3 = new int[var2];
      BooleanSetting[] var4 = this.overlayModule.getAppearanceToggles();

      for (BooleanSetting var8 : var4) {
         var3[this.indexOfMin(var3)] += 29;
      }

      SliderSetting[] var10 = this.overlayModule.EIAJ(this.SCCzz);

      for (SliderSetting var9 : var10) {
         var3[this.indexOfMin(var3)] += var9.isMode ? 18 : 25;
      }

      int var12 = var1 - 20;
      int var14 = Math.max(1, Math.min(this.overlayModule.RmynT().length, var12 / 76));
      int var16 = (this.overlayModule.RmynT().length + var14 - 1) / var14;
      return this.maxValue(var3) + 13 + var16 * 19 + 8;
   }

   private float smoothTowards(float var1) {
      long var2 = System.currentTimeMillis();
      if (Float.isNaN(this.TXO)) {
         this.TXO = var1;
      }

      float var4 = this.lastAnimationTime == 0L ? 1.0F : Math.min(1.0F, (float)(var2 - this.lastAnimationTime) / 120.0F);
      this.lastAnimationTime = var2;
      float var5 = 1.0F - (float)Math.pow(1.0F - var4, 3.0);
      return this.TXO + (var1 - this.TXO) * var5;
   }

   private void drawColorPopup() {
      short var1 = 152;
      byte var2 = 114;
      int var3 = Math.max(5, Math.min(this.width - var1 - 5, OverlayEditorScreen$6.getX(this.colorSwatchRect)));
      int var4 = OverlayEditorScreen$6.getY(this.colorSwatchRect) + OverlayEditorScreen$6.getHeight(this.colorSwatchRect) + 4;
      if (var4 + var2 > this.height - 5) {
         var4 = OverlayEditorScreen$6.getY(this.colorSwatchRect) - var2 - 4;
      }

      var4 = Math.max(5, Math.min(this.height - var2 - 5, var4));
      this.colorPopupRect = new OverlayEditorScreen$6(var3, var4, var1, var2);
      RoundedRect.drawRoundedRectArgb(var3, var4, var1, var2, 9.0F, -267909112);
      RoundedRect.drawRoundedOutline(var3, var4, var1, var2, 9.0F, 0.5F, new Color(0, 0, 0, 0), new Color(620756991, true));
      ColorSetting[] var5 = this.overlayModule.RmynT();
      this.CqUn(var5[Math.max(0, Math.min(var5.length - 1, this.selectedColorIndex))], var3 + 10, var4 + 10, var1 - 20, 0, 0);
   }

   private int QojuX(BooleanSetting var1, int var2, int var3, int var4, int var5, int var6) {
      OverlayEditorScreen$6 var7 = new OverlayEditorScreen$6(var2, var3, var4, 27);
      this.toggleWidgets.add(new OverlayEditorScreen$8(var7, var1));
      RoundedRect.drawRoundedRectArgb(
         OverlayEditorScreen$6.getX(var7),
         OverlayEditorScreen$6.getY(var7),
         OverlayEditorScreen$6.getWidth(var7),
         OverlayEditorScreen$6.getHeight(var7),
         4.0F,
         OverlayEditorScreen$6.isInside(var7, var5, var6) ? -804253680 : -1291319288
      );
      this.qWgoR().drawString(var1.getName(), OverlayEditorScreen$6.getX(var7) + 7, OverlayEditorScreen$6.getY(var7) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(var7)), -789001, false);
      String var8 = var1.isToggled() ? "ON" : "OFF";
      this.qWgoR()
         .drawString(
            var8,
            OverlayEditorScreen$6.getX(var7) + OverlayEditorScreen$6.getWidth(var7) - this.getTextWidth(var8) - 7,
            OverlayEditorScreen$6.getY(var7) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(var7)),
            var1.isToggled() ? this.getAccentColor() : -5524803,
            false
         );
      return var3 + 29;
   }

   private int drawModeSliderRow(SliderSetting var1, int var2, int var3, int var4, int var5, int var6) {
      OverlayEditorScreen$6 var7 = new OverlayEditorScreen$6(var2, var3, var4, 16);
      this.gDg79.add(new OverlayEditorScreen$5(var7, var1));
      RoundedRect.drawRoundedRectArgb(
         OverlayEditorScreen$6.getX(var7),
         OverlayEditorScreen$6.getY(var7),
         OverlayEditorScreen$6.getWidth(var7),
         OverlayEditorScreen$6.getHeight(var7),
         3.0F,
         OverlayEditorScreen$6.isInside(var7, var5, var6) ? -804253680 : -1291319288
      );
      this.qWgoR().drawString(var1.getName(), OverlayEditorScreen$6.getX(var7) + 6, OverlayEditorScreen$6.getY(var7) + 4, -789001, false);
      String var8 = this.szGfc(var1);
      this.qWgoR()
         .drawString(var8, OverlayEditorScreen$6.getX(var7) + OverlayEditorScreen$6.getWidth(var7) - this.getTextWidth(var8) - 6, OverlayEditorScreen$6.getY(var7) + 4, this.getAccentColor(), false);
      return var3 + 18;
   }

   private void ddF6(int var1, int var2) {
      String[] var3 = this.openDropdownSetting.getOptions();
      int var4 = Math.max(120, OverlayEditorScreen$6.getWidth(this.dropdownAnchorRect));
      int var5 = var3.length * 19 + 8;
      int var6 = Math.max(5, Math.min(this.width - var4 - 5, OverlayEditorScreen$6.getX(this.dropdownAnchorRect)));
      int var7 = OverlayEditorScreen$6.getY(this.dropdownAnchorRect) + OverlayEditorScreen$6.getHeight(this.dropdownAnchorRect) + 4;
      if (var7 + var5 > this.height - 5) {
         var7 = OverlayEditorScreen$6.getY(this.dropdownAnchorRect) - var5 - 4;
      }

      RoundedRect.drawRoundedRectArgb(var6, var7, var4, var5, 6.0F, -301463544);

      for (int var8 = 0; var8 < var3.length; var8++) {
         OverlayEditorScreen$6 var9 = new OverlayEditorScreen$6(var6 + 4, var7 + 4 + var8 * 19, var4 - 8, 17);
         this.optionWidgets.add(new OverlayEditorScreen$4(var9, this.openDropdownSetting, var8));
         if (OverlayEditorScreen$6.isInside(var9, var1, var2)) {
            RoundedRect.drawRoundedRectArgb(OverlayEditorScreen$6.getX(var9), OverlayEditorScreen$6.getY(var9), OverlayEditorScreen$6.getWidth(var9), OverlayEditorScreen$6.getHeight(var9), 3.0F, 905969663);
         }

         this.qWgoR()
            .drawString(
               var3[var8],
               OverlayEditorScreen$6.getX(var9) + 6,
               OverlayEditorScreen$6.getY(var9) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(var9)),
               var8 == (int)Math.round(this.openDropdownSetting.getInput()) ? this.getAccentColor() : -789001,
               false
            );
      }
   }

   private int YAmr8(SliderSetting var1, int var2, int var3, int var4, int var5, int var6) {
      OverlayEditorScreen$6 var7 = new OverlayEditorScreen$6(var2, var3, var4, 25);
      OverlayEditorScreen$6 var8 = new OverlayEditorScreen$6(OverlayEditorScreen$6.getX(var7) + 2, OverlayEditorScreen$6.getY(var7) + 17, OverlayEditorScreen$6.getWidth(var7) - 4, 3);
      this.sliderRows.add(new OverlayEditorScreen$7(var7, var8, var1));
      this.qWgoR().drawString(var1.getName(), OverlayEditorScreen$6.getX(var7), OverlayEditorScreen$6.getY(var7) + 1, -789001, false);
      String var9 = this.formatSliderValue(var1);
      this.qWgoR().drawString(var9, OverlayEditorScreen$6.getX(var7) + OverlayEditorScreen$6.getWidth(var7) - this.getTextWidth(var9), OverlayEditorScreen$6.getY(var7) + 1, -5524803, false);
      RoundedRect.drawRoundedRectArgb(OverlayEditorScreen$6.getX(var8), OverlayEditorScreen$6.getY(var8), OverlayEditorScreen$6.getWidth(var8), OverlayEditorScreen$6.getHeight(var8), 2.0F, 1077954388);
      int var10 = Math.round(OverlayEditorScreen$6.getWidth(var8) * this.getNormalizedValue(var1));
      RoundedRect.drawRoundedRectArgb(OverlayEditorScreen$6.getX(var8), OverlayEditorScreen$6.getY(var8), var10, OverlayEditorScreen$6.getHeight(var8), 2.0F, this.getAccentColor());
      this.SQsYnn(OverlayEditorScreen$6.getX(var8) + var10, OverlayEditorScreen$6.getY(var8) + 1.5F, 3.0F, OverlayEditorScreen$6.isInside(var7, var5, var6) ? -789001 : this.getAccentColor());
      return var3 + 25;
   }

   private void CqUn(ColorSetting var1, int var2, int var3, int var4, int var5, int var6) {
      this.saturationBrightnessRect = new OverlayEditorScreen$6(var2, var3, var4 - 18, 72);
      this.ojp = new OverlayEditorScreen$6(var2 + var4 - 8, var3, 8, 72);
      this.alphaBarRect = new OverlayEditorScreen$6(var2, var3 + 88, var4, 8);
      int var7 = Color.HSBtoRGB(var1.getHue() / 360.0F, 1.0F, 1.0F) | 0xFF000000;
      RoundedRect.drawRoundedRectArgb(
         OverlayEditorScreen$6.getX(this.saturationBrightnessRect) - 2,
         OverlayEditorScreen$6.getY(this.saturationBrightnessRect) - 2,
         OverlayEditorScreen$6.getWidth(this.saturationBrightnessRect) + 4,
         OverlayEditorScreen$6.getHeight(this.saturationBrightnessRect) + 4,
         5.0F,
         -1291319288
      );
      RenderUtils.drawHorizontalGradient(
         OverlayEditorScreen$6.getX(this.saturationBrightnessRect),
         OverlayEditorScreen$6.getY(this.saturationBrightnessRect),
         OverlayEditorScreen$6.getX(this.saturationBrightnessRect) + OverlayEditorScreen$6.getWidth(this.saturationBrightnessRect),
         OverlayEditorScreen$6.getY(this.saturationBrightnessRect) + OverlayEditorScreen$6.getHeight(this.saturationBrightnessRect),
         -1,
         var7
      );
      RenderUtils.drawVerticalGradient(
         OverlayEditorScreen$6.getX(this.saturationBrightnessRect),
         OverlayEditorScreen$6.getY(this.saturationBrightnessRect),
         OverlayEditorScreen$6.getX(this.saturationBrightnessRect) + OverlayEditorScreen$6.getWidth(this.saturationBrightnessRect),
         OverlayEditorScreen$6.getY(this.saturationBrightnessRect) + OverlayEditorScreen$6.getHeight(this.saturationBrightnessRect),
         0,
         -16777216
      );
      int var8 = OverlayEditorScreen$6.getX(this.saturationBrightnessRect) + Math.round(var1.getSaturation() * OverlayEditorScreen$6.getWidth(this.saturationBrightnessRect));
      int var9 = OverlayEditorScreen$6.getY(this.saturationBrightnessRect) + Math.round((1.0F - var1.pBf3()) * OverlayEditorScreen$6.getHeight(this.saturationBrightnessRect));
      this.drawPickerCursor(var8, var9);
      this.drawHueStrip(OverlayEditorScreen$6.getX(this.ojp), OverlayEditorScreen$6.getY(this.ojp), OverlayEditorScreen$6.getWidth(this.ojp), OverlayEditorScreen$6.getHeight(this.ojp));
      int var10 = OverlayEditorScreen$6.getY(this.ojp) + Math.round(var1.getHue() / 360.0F * OverlayEditorScreen$6.getHeight(this.ojp));
      RenderUtils.XNRNki(OverlayEditorScreen$6.getX(this.ojp) - 2, var10 - 1, OverlayEditorScreen$6.getX(this.ojp) + OverlayEditorScreen$6.getWidth(this.ojp) + 2, var10 + 2, -1);
      RenderUtils.drawHorizontalGradient(
         OverlayEditorScreen$6.getX(this.alphaBarRect),
         OverlayEditorScreen$6.getY(this.alphaBarRect),
         OverlayEditorScreen$6.getX(this.alphaBarRect) + OverlayEditorScreen$6.getWidth(this.alphaBarRect),
         OverlayEditorScreen$6.getY(this.alphaBarRect) + OverlayEditorScreen$6.getHeight(this.alphaBarRect),
         var1.getRgb(),
         var1.getArgb()
      );
      int var11 = OverlayEditorScreen$6.getX(this.alphaBarRect) + Math.round(var1.JIjrD() / 255.0F * OverlayEditorScreen$6.getWidth(this.alphaBarRect));
      RenderUtils.XNRNki(var11 - 1, OverlayEditorScreen$6.getY(this.alphaBarRect) - 2, var11 + 2, OverlayEditorScreen$6.getY(this.alphaBarRect) + OverlayEditorScreen$6.getHeight(this.alphaBarRect) + 2, -1);
   }

   private int getPreferredPanelWidth() {
      return Math.min(340, Math.max(284, this.width / 3));
   }

   private int getAccentColor() {
      return Color.getHSBColor(Gui.getAccentHue() / 360.0F, Gui.zFsde8(), Gui.getAccentBrightness()).getRGB() | 0xFF000000;
   }

   private int RwmKru() {
      Color var1 = new Color(this.getAccentColor(), true);
      return new Color(Math.round(var1.getRed() * 0.62F), Math.round(var1.getGreen() * 0.62F), Math.round(var1.getBlue() * 0.62F), 220).getRGB();
   }

   private int withAlpha(int var1, int var2) {
      return Math.max(0, Math.min(255, var2)) << 24 | var1 & 16777215;
   }

   private void buildColumnWidgets(int var1, int var2) {
      this.Iqu6.clear();
      this.dY2.clear();
      this.addColumnRows.clear();
      if (this.columnLayout == null) {
         this.xvK = OverlayEditorScreen$6.createEmpty();
      } else {
         for (int var3 = 0; var3 < this.columnLayout.entries.size(); var3++) {
            Overlay$11 var4 = this.columnLayout.entries.get(var3);
            int var5 = Math.round(var4.WVUw + 1.0F);
            int var6 = Math.max(12, Math.round(this.columnLayout.hTj66 - var5 - 2.0F));
            int var7 = Math.min(Math.max(12, var4.entryHeight - 3), var6);
            OverlayEditorScreen$6 var8 = new OverlayEditorScreen$6(Math.round(var4.posX - 2.0F), var5, Math.max(14, var4.entryWidth + 4), var7);
            OverlayEditorScreen$6 var9 = new OverlayEditorScreen$6(
               OverlayEditorScreen$6.getX(var8),
               OverlayEditorScreen$6.getY(var8),
               OverlayEditorScreen$6.getWidth(var8),
               Math.max(OverlayEditorScreen$6.getHeight(var8), Math.round(this.columnLayout.bounds[3] - OverlayEditorScreen$6.getY(var8)))
            );
            this.Iqu6.add(new OverlayEditorScreen$3(var9, var4.entryName));
            boolean var10 = OverlayEditorScreen$6.isInside(var8, var1, var2) || var4.entryName.equals(this.draggedColumnId);
            RoundedRect.drawRoundedOutline(
               OverlayEditorScreen$6.getX(var8),
               OverlayEditorScreen$6.getY(var8),
               OverlayEditorScreen$6.getWidth(var8),
               OverlayEditorScreen$6.getHeight(var8),
               3.0F,
               var10 ? 1.0F : 0.45F,
               new Color(0, 0, 0, 0),
               new Color(var10 ? this.getAccentColor() : 620756991, true)
            );
            if (this.columnLayout.statsStartY < this.columnLayout.bounds[1]) {
               RoundedRect.drawRoundedRectArgb(OverlayEditorScreen$6.getX(var8), OverlayEditorScreen$6.getY(var8), OverlayEditorScreen$6.getWidth(var8), OverlayEditorScreen$6.getHeight(var8), 3.0F, -1291319288);
               this.qWgoR()
                  .drawString(var4.displayText, OverlayEditorScreen$6.getX(var8) + 4, OverlayEditorScreen$6.getY(var8) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(var8)), -789001, false);
            }

            OverlayEditorScreen$6 var11 = new OverlayEditorScreen$6(OverlayEditorScreen$6.getX(var8) + OverlayEditorScreen$6.getWidth(var8) - 8, OverlayEditorScreen$6.getY(var8) + 2, 6, 6);
            this.dY2.add(new OverlayEditorScreen$3(var11, var4.entryName));
            RenderUtils.XNRNki(
               OverlayEditorScreen$6.getX(var11),
               OverlayEditorScreen$6.getY(var11) + 2,
               OverlayEditorScreen$6.getX(var11) + OverlayEditorScreen$6.getWidth(var11),
               OverlayEditorScreen$6.getY(var11) + 3,
               -5524803
            );
         }

         int var12 = Math.max(20, this.columnLayout.OSlc);
         int var13 = Math.round(this.columnLayout.bounds[2]) + 7;
         int var14 = Math.round(this.columnLayout.statsStartY);
         this.xvK = new OverlayEditorScreen$6(var13, var14, 58, var12);
         RoundedRect.drawRoundedRectArgb(
            OverlayEditorScreen$6.getX(this.xvK),
            OverlayEditorScreen$6.getY(this.xvK),
            OverlayEditorScreen$6.getWidth(this.xvK),
            OverlayEditorScreen$6.getHeight(this.xvK),
            5.0F,
            !OverlayEditorScreen$6.isInside(this.xvK, var1, var2) && !this.addColumnPopupOpen ? -1291319288 : -804253680
         );
         this.SQsYnn(OverlayEditorScreen$6.getX(this.xvK) + 12, OverlayEditorScreen$6.getY(this.xvK) + OverlayEditorScreen$6.getHeight(this.xvK) / 2.0F, 6.0F, this.getAccentColor());
         this.qWgoR()
            .drawString(
               "+",
               OverlayEditorScreen$6.getX(this.xvK) + 12 - this.getTextWidth("+") / 2.0F,
               OverlayEditorScreen$6.getY(this.xvK) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(this.xvK)),
               -1,
               false
            );
         this.qWgoR()
            .drawString("Add", OverlayEditorScreen$6.getX(this.xvK) + 25, OverlayEditorScreen$6.getY(this.xvK) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(this.xvK)), -789001, false);
         if (this.addColumnPopupOpen) {
            this.drawAddColumnPopup(var1, var2);
         }
      }
   }

   private void drawAddColumnPopup(int var1, int var2) {
      List var3 = this.overlayModule.getHiddenColumnNames(this.SCCzz);
      short var4 = 156;
      int var5 = Math.max(24, var3.size() * 18 + 10);
      int var6 = Math.min(this.width - var4 - 12, OverlayEditorScreen$6.getX(this.xvK));
      int var7 = Math.min(this.height - var5 - 12, OverlayEditorScreen$6.getY(this.xvK) + OverlayEditorScreen$6.getHeight(this.xvK) + 4);
      RoundedRect.drawRoundedRectArgb(var6, var7, var4, var5, 5.0F, -1291845632);

      for (int var8 = 0; var8 < var3.size(); var8++) {
         String var9 = (String)var3.get(var8);
         OverlayEditorScreen$6 var10 = new OverlayEditorScreen$6(var6 + 5, var7 + 5 + var8 * 18, var4 - 10, 15);
         this.addColumnRows.add(new OverlayEditorScreen$3(var10, var9));
         RoundedRect.drawRoundedRectArgb(
            OverlayEditorScreen$6.getX(var10),
            OverlayEditorScreen$6.getY(var10),
            OverlayEditorScreen$6.getWidth(var10),
            OverlayEditorScreen$6.getHeight(var10),
            3.0F,
            OverlayEditorScreen$6.isInside(var10, var1, var2) ? -804253680 : -1291319288
         );
         this.qWgoR().drawString(this.overlayModule.getColumnDisplayName(var9), OverlayEditorScreen$6.getX(var10) + 6, OverlayEditorScreen$6.getY(var10) + 4, -789001, false);
      }

      if (var3.isEmpty()) {
         this.qWgoR().drawString("All columns added", var6 + 8, var7 + 8, -5524803, false);
      }
   }

   private void drawButtonBox(OverlayEditorScreen$6 var1, String var2, int var3, int var4) {
      boolean var5 = OverlayEditorScreen$6.isInside(var1, var3, var4);
      RoundedRect.drawRoundedRectArgb(
         OverlayEditorScreen$6.getX(var1), OverlayEditorScreen$6.getY(var1), OverlayEditorScreen$6.getWidth(var1), OverlayEditorScreen$6.getHeight(var1), 5.0F, var5 ? this.RwmKru() : -1291319288
      );
      this.qWgoR()
         .drawString(
            var2,
            OverlayEditorScreen$6.getX(var1) + (OverlayEditorScreen$6.getWidth(var1) - this.getTextWidth(var2)) / 2.0F,
            OverlayEditorScreen$6.getY(var1) + this.getVerticalCenter(OverlayEditorScreen$6.getHeight(var1)),
            -789001,
            false
         );
   }

   private void updateDragInteractions(int var1, int var2) {
      if (this.activeSliderRow != null) {
         this.updateSliderFromMouse(this.activeSliderRow, var1);
      }

      if (this.RVO != OverlayEditorScreen$1.NONE) {
         this.updateColorFromDrag(var1, var2);
      }

      if (this.draggedColumnId != null && (Math.abs(var1 - this.gW0) > 3 || Math.abs(var2 - this.dragStartY) > 3)) {
         this.dragMoved = true;
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0 && OverlayEditorScreen$6.isInside(this.doneButtonRect, var1, var2)) {
         this.mc.displayGuiScreen(this.USLCzC);
      } else if (var3 != 0 && var3 != 1 || !OverlayEditorScreen$6.isInside(this.bedwarsTabRect, var1, var2) && !OverlayEditorScreen$6.isInside(this.skywarsTabRect, var1, var2)) {
         for (int var6 = 0; var6 < this.optionWidgets.size(); var6++) {
            OverlayEditorScreen$4 var5 = this.optionWidgets.get(var6);
            if (var3 == 0 && OverlayEditorScreen$6.isInside(OverlayEditorScreen$4.getRect(var5), var1, var2)) {
               OverlayEditorScreen$4.sYjb(var5).setValueClamped(OverlayEditorScreen$4.getOptionIndex(var5));
               this.openDropdownSetting = null;
               this.markConfigUnsaved();
               return;
            }
         }

         for (int var7 = 0; var7 < this.sliderRows.size(); var7++) {
            OverlayEditorScreen$7 var14 = this.sliderRows.get(var7);
            if (var3 == 0 && OverlayEditorScreen$6.isInside(OverlayEditorScreen$7.getRowRect(var14), var1, var2)) {
               this.activeSliderRow = var14;
               this.updateSliderFromMouse(var14, var1);
               return;
            }
         }

         if (this.colorPopupOpen && var3 == 0 && OverlayEditorScreen$6.isInside(this.saturationBrightnessRect, var1, var2)) {
            this.RVO = OverlayEditorScreen$1.SATURATION;
            this.updateColorFromDrag(var1, var2);
         } else if (this.colorPopupOpen && var3 == 0 && OverlayEditorScreen$6.isInside(this.ojp, var1, var2)) {
            this.RVO = OverlayEditorScreen$1.HUE;
            this.updateColorFromDrag(var1, var2);
         } else if (this.colorPopupOpen && var3 == 0 && OverlayEditorScreen$6.isInside(this.alphaBarRect, var1, var2)) {
            this.RVO = OverlayEditorScreen$1.ALPHA;
            this.updateColorFromDrag(var1, var2);
         } else {
            for (int var8 = 0; var8 < this.toggleWidgets.size(); var8++) {
               OverlayEditorScreen$8 var15 = this.toggleWidgets.get(var8);
               if (var3 == 0 && OverlayEditorScreen$6.isInside(OverlayEditorScreen$8.ZYgNy(var15), var1, var2)) {
                  OverlayEditorScreen$8.xrQo(var15).toggle();
                  this.markConfigUnsaved();
                  return;
               }
            }

            for (int var9 = 0; var9 < this.gDg79.size(); var9++) {
               OverlayEditorScreen$5 var16 = this.gDg79.get(var9);
               if (OverlayEditorScreen$6.isInside(OverlayEditorScreen$5.getRect(var16), var1, var2)) {
                  this.openDropdownSetting = this.openDropdownSetting == OverlayEditorScreen$5.getSliderSetting(var16) ? null : OverlayEditorScreen$5.getSliderSetting(var16);
                  this.dropdownAnchorRect = OverlayEditorScreen$5.getRect(var16);
                  this.colorPopupOpen = false;
                  this.addColumnPopupOpen = false;
                  return;
               }
            }

            for (int var10 = 0; var10 < this.AYXF.size(); var10++) {
               OverlayEditorScreen$2 var17 = this.AYXF.get(var10);
               if (var3 == 0 && OverlayEditorScreen$6.isInside(OverlayEditorScreen$2.getRect(var17), var1, var2)) {
                  this.colorPopupOpen = this.selectedColorIndex != OverlayEditorScreen$2.getColorIndex(var17) || !this.colorPopupOpen;
                  this.selectedColorIndex = OverlayEditorScreen$2.getColorIndex(var17);
                  this.colorSwatchRect = OverlayEditorScreen$2.getRect(var17);
                  this.openDropdownSetting = null;
                  return;
               }
            }

            if (!this.colorPopupOpen || !OverlayEditorScreen$6.isInside(this.colorPopupRect, var1, var2)) {
               for (int var11 = 0; var11 < this.dY2.size(); var11++) {
                  OverlayEditorScreen$3 var18 = this.dY2.get(var11);
                  if (var3 == 0 && OverlayEditorScreen$6.isInside(OverlayEditorScreen$3.Twacopj(var18), var1, var2)) {
                     this.overlayModule.disableColumn(OverlayEditorScreen$3.getColumnId(var18), this.SCCzz);
                     this.markConfigUnsaved();
                     return;
                  }
               }

               if (var3 == 0 && OverlayEditorScreen$6.isInside(this.xvK, var1, var2)) {
                  this.addColumnPopupOpen = !this.addColumnPopupOpen;
               } else {
                  for (int var12 = 0; var12 < this.addColumnRows.size(); var12++) {
                     OverlayEditorScreen$3 var19 = this.addColumnRows.get(var12);
                     if (var3 == 0 && OverlayEditorScreen$6.isInside(OverlayEditorScreen$3.Twacopj(var19), var1, var2)) {
                        this.beginColumnDrag(OverlayEditorScreen$3.getColumnId(var19), true, var1, var2);
                        return;
                     }
                  }

                  for (int var13 = 0; var13 < this.Iqu6.size(); var13++) {
                     OverlayEditorScreen$3 var20 = this.Iqu6.get(var13);
                     if (OverlayEditorScreen$6.isInside(OverlayEditorScreen$3.Twacopj(var20), var1, var2)) {
                        if (var3 == 1) {
                           this.overlayModule.disableColumn(OverlayEditorScreen$3.getColumnId(var20), this.SCCzz);
                           this.markConfigUnsaved();
                           return;
                        }

                        if (var3 == 0) {
                           this.beginColumnDrag(OverlayEditorScreen$3.getColumnId(var20), false, var1, var2);
                           return;
                        }
                     }
                  }

                  this.openDropdownSetting = null;
                  this.colorPopupOpen = false;
                  super.mouseClicked(var1, var2, var3);
               }
            }
         }
      } else {
         boolean var4 = OverlayEditorScreen$6.isInside(this.skywarsTabRect, var1, var2);
         if (var4 != this.SCCzz) {
            this.SCCzz = var4;
            this.overlayModule.SSXt(this.SCCzz);
            this.SLuIts = 0;
            this.addColumnPopupOpen = false;
            this.colorPopupOpen = false;
            this.openDropdownSetting = null;
         }
      }
   }

   private void beginColumnDrag(String var1, boolean var2, int var3, int var4) {
      this.draggedColumnId = var1;
      this.draggingNewColumn = var2;
      this.dragMoved = false;
      this.gW0 = var3;
      this.dragStartY = var4;
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      this.activeSliderRow = null;
      this.RVO = OverlayEditorScreen$1.NONE;
      if (var3 == 0 && this.draggedColumnId != null) {
         int var4 = this.fqiq(var1);
         if (this.draggingNewColumn) {
            this.overlayModule.umhzj(this.draggedColumnId, var4, this.SCCzz);
            this.addColumnPopupOpen = false;
            this.markConfigUnsaved();
         } else if (this.dragMoved) {
            this.overlayModule.moveColumnIfEnabled(this.draggedColumnId, var4, this.SCCzz);
            this.markConfigUnsaved();
         }

         this.draggedColumnId = null;
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0 && this.maxScroll > 0) {
         this.SLuIts = Math.max(0, Math.min(this.maxScroll, this.SLuIts + (var1 < 0 ? 30 : -30)));
      }
   }

   private int fqiq(int var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < this.Iqu6.size(); var3++) {
         if (this.draggingNewColumn || !OverlayEditorScreen$3.getColumnId(this.Iqu6.get(var3)).equals(this.draggedColumnId)) {
            OverlayEditorScreen$6 var4 = OverlayEditorScreen$3.Twacopj(this.Iqu6.get(var3));
            if (var1 > OverlayEditorScreen$6.getX(var4) + OverlayEditorScreen$6.getWidth(var4) / 2) {
               var2++;
            }
         }
      }

      return var2;
   }

   private void updateSliderFromMouse(OverlayEditorScreen$7 var1, int var2) {
      double var3 = this.getNormalizedX(OverlayEditorScreen$7.getTrackRect(var1), var2);
      OverlayEditorScreen$7.getSliderSetting(var1).setValueClamped(OverlayEditorScreen$7.getSliderSetting(var1).getMin() + (OverlayEditorScreen$7.getSliderSetting(var1).getMax() - OverlayEditorScreen$7.getSliderSetting(var1).getMin()) * var3);
      this.markConfigUnsaved();
   }

   private void updateColorFromDrag(int var1, int var2) {
      ColorSetting var3 = this.overlayModule.RmynT()[Math.max(0, Math.min(this.overlayModule.RmynT().length - 1, this.selectedColorIndex))];
      if (this.RVO == OverlayEditorScreen$1.SATURATION) {
         float var4 = Math.max(0.0F, Math.min(1.0F, (float)(var1 - OverlayEditorScreen$6.getX(this.saturationBrightnessRect)) / Math.max(1, OverlayEditorScreen$6.getWidth(this.saturationBrightnessRect))));
         float var5 = 1.0F - Math.max(0.0F, Math.min(1.0F, (float)(var2 - OverlayEditorScreen$6.getY(this.saturationBrightnessRect)) / Math.max(1, OverlayEditorScreen$6.getHeight(this.saturationBrightnessRect))));
         var3.TAfvrw(var3.getHue(), var4, var5);
      } else if (this.RVO == OverlayEditorScreen$1.HUE) {
         float var6 = Math.max(0.0F, Math.min(1.0F, (float)(var2 - OverlayEditorScreen$6.getY(this.ojp)) / Math.max(1, OverlayEditorScreen$6.getHeight(this.ojp)))) * 360.0F;
         var3.oAej(var6);
      } else if (this.RVO == OverlayEditorScreen$1.ALPHA) {
         var3.setAlpha(Math.round(255.0F * this.getNormalizedX(this.alphaBarRect, var1)));
      }

      this.markConfigUnsaved();
   }

   private void markConfigUnsaved() {
      if (Jade.Grq != null) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }

   private float getNormalizedX(OverlayEditorScreen$6 var1, int var2) {
      return Math.max(0.0F, Math.min(1.0F, (float)(var2 - OverlayEditorScreen$6.getX(var1)) / Math.max(1, OverlayEditorScreen$6.getWidth(var1))));
   }

   private float getNormalizedValue(SliderSetting var1) {
      return (float)((var1.getInput() - var1.getMin()) / Math.max(1.0E-4, var1.getMax() - var1.getMin()));
   }

   private String formatSliderValue(SliderSetting var1) {
      double var2 = var1.getInput();
      String var4 = Math.abs(var2 - Math.round(var2)) < 0.001 ? String.valueOf((int)Math.round(var2)) : String.format(Locale.ROOT, "%.2f", var2);
      return var4 + var1.getSuffix();
   }

   private String szGfc(SliderSetting var1) {
      String[] var2 = var1.getOptions();
      int var3 = (int)Math.max(0L, Math.min((long)(var2.length - 1), Math.round(var1.getInput())));
      return var2[var3];
   }

   private void stiy(SliderSetting var1, int var2) {
      int var3 = var1.getOptions().length;
      int var4 = (int)Math.round(var1.getInput());
      var1.setValueClamped((var4 + var2 + var3) % var3);
   }

   private IFont qWgoR() {
      return Gui.getSettingFont();
   }

   private int getTextWidth(String var1) {
      return Math.round((float)this.qWgoR().getStringWidth(var1 == null ? "" : var1));
   }

   private float getVerticalCenter(int var1) {
      return Math.max(1.0F, (var1 - this.qWgoR().getFontHeight()) / 2.0F);
   }

   private void SQsYnn(float var1, float var2, float var3, int var4) {
      RoundedRect.drawRoundedRectArgb(var1 - var3, var2 - var3, var3 * 2.0F, var3 * 2.0F, var3, var4);
   }

   private void drawCheckMark(float var1, float var2, int var3) {
      this.drawLineSegment(var1 + 1.0F, var2 + 4.2F, var1 + 3.4F, var2 + 6.4F, 1.35F, var3);
      this.drawLineSegment(var1 + 3.3F, var2 + 6.4F, var1 + 7.7F, var2 + 1.4F, 1.35F, var3);
   }

   private void drawLineSegment(float var1, float var2, float var3, float var4, float var5, int var6) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glLineWidth(var5);
      GL11.glColor4f((var6 >> 16 & 0xFF) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F, (var6 >> 24 & 0xFF) / 255.0F);
      GL11.glBegin(1);
      GL11.glVertex2f(var1, var2);
      GL11.glVertex2f(var3, var4);
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   private void drawPickerCursor(int var1, int var2) {
      RenderUtils.XNRNki(var1 - 4, var2, var1 + 5, var2 + 1, -1);
      RenderUtils.XNRNki(var1, var2 - 4, var1 + 1, var2 + 5, -1);
      RenderUtils.XNRNki(var1 - 3, var2 - 1, var1 + 4, var2, -1728053248);
      RenderUtils.XNRNki(var1 - 1, var2 - 3, var1, var2 + 4, -1728053248);
   }

   private void drawHueStrip(int var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < 6; var5++) {
         float var6 = var5 / 6.0F;
         float var7 = (var5 + 1) / 6.0F;
         int var8 = Color.HSBtoRGB(var6, 1.0F, 1.0F) | 0xFF000000;
         int var9 = Color.HSBtoRGB(var7, 1.0F, 1.0F) | 0xFF000000;
         int var10 = var2 + Math.round(var4 * var5 / 6.0F);
         int var11 = var2 + Math.round(var4 * (var5 + 1) / 6.0F);
         RenderUtils.drawVerticalGradient(var1, var10, var1 + var3, var11, var8, var9);
      }
   }

   private int indexOfMin(int[] var1) {
      int var2 = 0;

      for (int var3 = 1; var3 < var1.length; var3++) {
         if (var1[var3] < var1[var2]) {
            var2 = var3;
         }
      }

      return var2;
   }

   private int maxValue(int[] var1) {
      int var2 = var1[0];

      for (int var3 = 1; var3 < var1.length; var3++) {
         var2 = Math.max(var2, var1[var3]);
      }

      return var2;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.USLCzC);
      } else {
         super.keyTyped(var1, var2);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
