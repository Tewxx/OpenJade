// Jade recovery: module: Key Binds (render); original class: jade.deps.eLz.gwSwNWeJq4
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.client.Gui;
import jade.client.module.client.Rendering;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;

@ModuleInfo(aliases = {"Hotkey", "Hotkeys", "Binds"})
public class KeyBinds extends Module implements ExternalRenderableModule {
   private static final String WxqUjz = "Key Binds";
   private static final String[] MODE_LABELS = new String[]{"Jade", "Basic"};
   private static final float DEFAULT_POSITION_X = 0.02F;
   private static final float PQGJry = 0.18F;
   private static final int DISABLED_TEXT_COLOR = -7761512;
   private static final float MIN_SCALE = 0.5F;
   private static final float DEFAULT_SCALE = 1.0F;
   private final SliderSetting mode;
   private final FontSetting font;
   private final SliderSetting scale;
   private final SliderSetting rounding;
   private final SliderSetting bodyOpacity;
   private final BooleanSetting hideHiddenModules;
   private final SliderSetting positionX;
   private final SliderSetting positionY;
   private float screenX;
   private float screenY;

   public KeyBinds() {
      super("Key Binds", Category.render);
      this.registerSetting(this.mode = new SliderSetting("Mode", 0, MODE_LABELS));
      this.registerSetting(this.font = new FontSetting("Font", "Modern"));
      this.registerSetting(this.scale = new SliderSetting("Scale", 1.0, 0.5, 2.0, 0.05));
      this.registerSetting(this.rounding = new SliderSetting("Rounding", 4.0, 0.0, 10.0, 0.5));
      this.registerSetting(this.bodyOpacity = new SliderSetting("Body opacity", 0.72, 0.0, 1.0, 0.05));
      this.registerSetting(
         this.hideHiddenModules = new BooleanSetting(
            "Hide hidden modules",
            true
         )
      );
      this.registerSetting(this.positionX = new SliderSetting("Position X", 0.02F, -0.25, 1.25, 0.001));
      this.registerSetting(this.positionY = new SliderSetting("Position Y", 0.18F, -0.25, 1.25, 0.001));
      this.scale.visible = false;
      this.positionX.visible = false;
      this.positionY.visible = false;
      this.guiUpdate();
   }

   @Override
   public void guiUpdate() {
      this.rounding.setVisible(this.isJadeStyle(), this);
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      if (var1 == this.mode) {
         this.guiUpdate();
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld() && mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
         this.updateScreenPosition();
         if (Rendering.isExternalOutput()) {
            this.renderToExternalBuffer(this.screenX, this.screenY);
         } else {
            this.renderKeyBindPanel(this.screenX, this.screenY, false);
         }
      }
   }

   @Override
   public void onDisable() {
      ExternalRenderer.invalidateExternalFrame();
   }

   public SliderSetting getScale() {
      return this.scale;
   }

   public float getResolvedX() {
      this.updateScreenPosition();
      return this.screenX;
   }

   public float getResolvedY() {
      this.updateScreenPosition();
      return this.screenY;
   }

   public void setNormalizedPosition(float var1, float var2) {
      this.positionX.setValueRaw(yKt1(var1, -0.25F, 1.25F));
      this.positionY.setValueRaw(yKt1(var2, -0.25F, 1.25F));
      this.updateScreenPosition();
   }

   public void resetPosition() {
      this.setNormalizedPosition(0.02F, 0.18F);
   }

   public float[] getPanelBounds(float var1, float var2) {
      return this.renderKeyBindPanel(var1, var2, true);
   }

   private float[] renderKeyBindPanel(float var1, float var2, boolean var3) {
      IFont var4 = FontManager.getHudRenderer(this.font.getResolvedFontName(), (float)this.scale.getInput());
      List var5 = this.collectBoundModules();
      if (var3 && var5.isEmpty()) {
         var5 = this.ijqcO();
      }

      float var6 = (float)this.scale.getInput();
      float var7 = 5.0F * var6;
      float var8 = var4.getFontHeight() + (this.isJadeStyle() ? 8.0F : 4.0F) * var6;
      float var9 = var4.getFontHeight() + 4.0F * var6;
      float var10 = var4.getStringWidth("Key Binds");
      float var11 = this.isJadeStyle() ? 15.0F * var6 : 0.0F;
      float var12 = Math.max(105.0F * var6, var10 + var11 + var7 * 2.0F);

      for (Module var14 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var5)) {
         float var15 = var4.getStringWidth(var14.getNameInHud()) + var4.getStringWidth(KeySetting.SBJv(var14.getKeycode())) + var7 * 3.0F;
         var12 = Math.max(var12, var15);
      }

      float var23 = Math.max(5.0F * var6, var5.size() * var9 + 3.0F * var6);
      float var24 = var8 + var23;
      float var25 = this.isJadeStyle() ? (float)this.rounding.getInput() : 0.0F;
      this.axnQ593(var1, var2, var12, var24, var8, var25);
      this.drawPanelHeader(var1, var2, var12, var8, var25, var4, var7);
      float var16 = var2 + var8 + 2.0F * var6;

      for (Module var18 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var5)) {
         String var19 = var18.getNameInHud();
         String var20 = KeySetting.SBJv(var18.getKeycode());
         float var21 = var16 + (var9 - var4.getFontHeight()) * 0.5F;
         if (var18.isEnabled()) {
            this.drawAnimatedGradientText(var4, var19, var1 + var7, var21);
         } else {
            var4.drawString(var19, var1 + var7, var21, -7761512, true);
         }

         float var22 = var1 + var12 - var7 - var4.getStringWidth(var20);
         if (var18.isEnabled()) {
            this.drawAnimatedGradientText(var4, var20, var22, var21);
         } else {
            var4.drawString(var20, var22, var21, -7761512, true);
         }

         var16 += var9;
      }

      return new float[]{var1, var2, var1 + var12, var2 + var24};
   }

   private void renderToExternalBuffer(float var1, float var2) {
      ExternalRenderBuffer var3 = ExternalRenderer.getActiveRenderBuffer();
      if (var3 != null) {
         float var4 = new ScaledResolution(mc).getScaleFactor();
         IFont var5 = FontManager.getHudRenderer(this.font.getResolvedFontName(), (float)this.scale.getInput());
         List var6 = this.collectBoundModules();
         float var7 = (float)this.scale.getInput();
         float var8 = 5.0F * var7;
         float var9 = var5.getFontHeight() + (this.isJadeStyle() ? 8.0F : 4.0F) * var7;
         float var10 = var5.getFontHeight() + 4.0F * var7;
         float var11 = var5.getStringWidth("Key Binds");
         float var12 = this.isJadeStyle() ? 15.0F * var7 : 0.0F;
         float var13 = Math.max(105.0F * var7, var11 + var12 + var8 * 2.0F);

         for (Module var15 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var6)) {
            var13 = Math.max(var13, var5.getStringWidth(var15.getNameInHud()) + var5.getStringWidth(KeySetting.SBJv(var15.getKeycode())) + var8 * 3.0F);
         }

         float var31 = Math.max(5.0F * var7, var6.size() * var10 + 3.0F * var7);
         float var32 = var9 + var31;
         float var16 = this.isJadeStyle() ? (float)this.rounding.getInput() : 0.0F;
         float var17 = var1 * var4;
         float var18 = var2 * var4;
         float var19 = var13 * var4;
         float var20 = var32 * var4;
         float var21 = var9 * var4;
         int var22 = withAlpha(-16184306, this.bodyOpacity.getInput());
         if (this.isJadeStyle()) {
            var3.fillPerCornerRoundedRect(var17, var18 + var21 - 1.0F, var17 + var19, var18 + var20, var22, 0.0F, 0.0F, var16 * var4, var16 * var4);
            var3.fillPerCornerRoundedRect(var17, var18, var17 + var19, var18 + var21 + 1.0F, -15657190, var16 * var4, var16 * var4, 0.0F, 0.0F);
            this.Nhej(var3, (var1 + var8) * var4, (var2 + (var9 - 9.0F * var7) * 0.5F) * var4, var4 * var7);
            this.crcY(
               var3,
               var5,
               "Key Binds",
               (var1 + (var13 - var5.getStringWidth("Key Binds")) * 0.5F) * var4,
               (var2 + (var9 - var5.getFontHeight()) * 0.5F) * var4,
               -986124,
               var4
            );
         } else {
            var3.fillRoundedRect(var17, var18 + var21 - 1.0F, var17 + var19, var18 + var20, var22, 0.0F);
            this.drawBasicBodyBackground(var3, var17, var18, var19, var21 + 1.0F);
            this.crcY(
               var3,
               var5,
               "Key Binds",
               (var1 + (var13 - var5.getStringWidth("Key Binds")) * 0.5F) * var4,
               (var2 + (var9 - var5.getFontHeight()) * 0.5F) * var4,
               -789002,
               var4
            );
         }

         float var23 = var2 + var9 + 2.0F * var7;

         for (Module var25 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var6)) {
            String var26 = var25.getNameInHud();
            String var27 = KeySetting.SBJv(var25.getKeycode());
            float var28 = var23 + (var10 - var5.getFontHeight()) * 0.5F;
            float var29 = var1 + var8;
            if (var25.isEnabled()) {
               this.UsW3(var3, var5, var26, var29 * var4, var28 * var4, var4);
            } else {
               this.crcY(var3, var5, var26, var29 * var4, var28 * var4, -7761512, var4);
            }

            float var30 = (var1 + var13 - var8 - var5.getStringWidth(var27)) * var4;
            if (var25.isEnabled()) {
               this.UsW3(var3, var5, var27, var30, var28 * var4, var4);
            } else {
               this.crcY(var3, var5, var27, var30, var28 * var4, -7761512, var4);
            }

            var23 += var10;
         }
      }
   }

   private void drawBasicBodyBackground(ExternalRenderBuffer var1, float var2, float var3, float var4, float var5) {
      int var6 = Math.max(1, Math.min(32, (int)Math.ceil(var4)));
      int var7 = Math.max(1, (int)Math.ceil(var5));
      int var8 = Arraylist.xQec0(-36.0);
      int var9 = Arraylist.xQec0(36.0);

      for (int var10 = 0; var10 < var7; var10++) {
         float var11 = var7 == 1 ? 0.0F : (var10 + 0.5F) / var7;
         float var12 = 0.94F - var11 * 0.44F;
         float var13 = var3 + var5 * var10 / var7;
         float var14 = var3 + var5 * (var10 + 1) / var7;
         float var15 = var10 + 1 < var7 ? var14 + 1.0F : var14;

         for (int var16 = 0; var16 < var6; var16++) {
            float var17 = var6 == 1 ? 0.0F : (float)var16 / (var6 - 1);
            int var18 = scaleRgb(lerpColor(var8, var9, var17), var12);
            float var19 = var2 + var4 * var16 / var6;
            float var20 = var2 + var4 * (var16 + 1) / var6;
            if (var16 + 1 < var6) {
               var20++;
            }

            var1.fillRoundedRect(var19, var13, var20, var15, var18, 0.0F);
         }
      }
   }

   private void Nhej(ExternalRenderBuffer var1, float var2, float var3, float var4) {
      int var5 = -986124;
      var1.fillRoundedRect(var2, var3, var2 + 10.0F * var4, var3 + 9.0F * var4, var5, 2.0F * var4);
      var1.fillRoundedRect(var2 + var4, var3 + var4, var2 + 9.0F * var4, var3 + 8.0F * var4, -15657190, 1.25F * var4);

      for (int var6 = 0; var6 < 2; var6++) {
         for (int var7 = 0; var7 < 4; var7++) {
            var1.fillRoundedRect(
               var2 + (2.0F + var7 * 1.65F) * var4,
               var3 + (2.0F + var6 * 1.8F) * var4,
               var2 + (3.0F + var7 * 1.65F) * var4,
               var3 + (3.0F + var6 * 1.8F) * var4,
               var5,
               0.0F
            );
         }
      }

      var1.fillRoundedRect(var2 + 3.0F * var4, var3 + 6.0F * var4, var2 + 7.0F * var4, var3 + 7.0F * var4, var5, 0.0F);
   }

   private void UsW3(ExternalRenderBuffer var1, IFont var2, String var3, float var4, float var5, float var6) {
      float var7 = var4;

      for (int var8 = 0; var8 < var3.length(); var8++) {
         String var9 = String.valueOf(var3.charAt(var8));
         float var10 = var2.getStringWidth(var9) * var6;
         int var11 = Arraylist.xQec0((var7 / var6 + var10 / var6 * 0.5F) * 0.42);
         FormattedTextRenderer.drawText(var1, var2, var9, var7, var5, var6, var11, true, true);
         var7 += var10;
      }
   }

   private void crcY(ExternalRenderBuffer var1, IFont var2, String var3, float var4, float var5, int var6, float var7) {
      FormattedTextRenderer.drawTextAtHeight(var1, var2, var3, var4, var5, var2.getFontHeight() * var7, var6, true, var2.getStringWidth(var3) * var7);
   }

   private void axnQ593(float var1, float var2, float var3, float var4, float var5, float var6) {
      int var7 = withAlpha(-16184306, this.bodyOpacity.getInput());
      if (this.isJadeStyle()) {
         float var8 = var2 + var5 - 0.5F;
         float var9 = var4 - var5 + 0.5F;
         if (var6 <= 0.0F) {
            RenderUtils.XNRNki(var1, var8, var1 + var3, var2 + var4, var7);
         } else {
            RoundedRect.drawRoundedRectPerCornerRadii(var1, var8, var3, var9, var7, 0.0F, 0.0F, var6, var6, true, false);
         }
      } else {
         RenderUtils.XNRNki(var1, var2 + var5, var1 + var3, var2 + var4, var7);
      }
   }

   private void drawPanelHeader(float var1, float var2, float var3, float var4, float var5, IFont var6, float var7) {
      if (this.isJadeStyle()) {
         float var10 = (float)this.scale.getInput();
         if (var5 <= 0.0F) {
            RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, -15657190);
         } else {
            RoundedRect.drawRoundedRectPerCornerRadii(var1, var2, var3, var4 + 0.5F, -15657190, var5, var5, 0.0F, 0.0F, false, true);
         }

         this.drawHotkeyIcon(var1 + var7, var2 + (var4 - 9.0F * var10) * 0.5F, var10);
         var6.drawString("Key Binds", var1 + (var3 - var6.getStringWidth("Key Binds")) * 0.5F, var2 + (var4 - var6.getFontHeight()) * 0.5F, -986124, true);
      } else {
         int var8 = Arraylist.xQec0(-36.0);
         int var9 = Arraylist.xQec0(36.0);
         this.drawBasicHeaderBackground(var1, var2, var3, var4, var8, var9);
         var6.drawString("Key Binds", var1 + (var3 - var6.getStringWidth("Key Binds")) * 0.5F, var2 + (var4 - var6.getFontHeight()) * 0.5F, -789002, true);
      }
   }

   private void drawBasicHeaderBackground(float var1, float var2, float var3, float var4, int var5, int var6) {
      RenderUtils.drawHorizontalGradient(var1, var2, var1 + var3, var2 + var4, var5, var6);
      RenderUtils.drawVerticalGradient(var1, var2, var1 + var3, var2 + var4, 251658240, Integer.MIN_VALUE);
   }

   private void drawAnimatedGradientText(IFont var1, String var2, float var3, float var4) {
      var1.drawGlyphString(var2, var3, var4, (recoveredArg0, recoveredArg1, recoveredArg2, recoveredArg3) -> KeyBinds.xDgk1(var3, recoveredArg0, recoveredArg1, recoveredArg2, (java.lang.Integer) recoveredArg3), true);
   }

   private void drawHotkeyIcon(float var1, float var2, float var3) {
      int var4 = -986124;
      RoundedRect.drawRoundedRectArgb(var1, var2, 10.0F * var3, 9.0F * var3, 2.0F * var3, var4);
      RoundedRect.drawRoundedRectArgb(var1 + var3, var2 + var3, 8.0F * var3, 7.0F * var3, 1.25F * var3, -15657190);

      for (int var5 = 0; var5 < 2; var5++) {
         for (int var6 = 0; var6 < 4; var6++) {
            RenderUtils.XNRNki(
               var1 + (2.0F + var6 * 1.65F) * var3,
               var2 + (2.0F + var5 * 1.8F) * var3,
               var1 + (3.0F + var6 * 1.65F) * var3,
               var2 + (3.0F + var5 * 1.8F) * var3,
               var4
            );
         }
      }

      RenderUtils.XNRNki(var1 + 3.0F * var3, var2 + 6.0F * var3, var1 + 7.0F * var3, var2 + 7.0F * var3, var4);
   }

   private List<Module> collectBoundModules() {
      ArrayList var1 = new ArrayList();

      for (Module var3 : Jade.getModuleManager().getModules()) {
         if (var3.getKeycode() != 0 && !(var3 instanceof Gui) && (!this.hideHiddenModules.isToggled() || !var3.isHidden())) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private List<Module> ijqcO() {
      ArrayList var1 = new ArrayList();

      for (Module var3 : Jade.getModuleManager().getModules()) {
         if (var3 != this && !(var3 instanceof Gui) && var1.size() < 3) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private boolean isJadeStyle() {
      return (int)this.mode.getInput() == 0;
   }

   private void updateScreenPosition() {
      ScaledResolution var1 = new ScaledResolution(mc);
      this.screenX = (float)this.positionX.getInput() * var1.getScaledWidth();
      this.screenY = (float)this.positionY.getInput() * var1.getScaledHeight();
   }

   private static int scaleRgb(int var0, float var1) {
      int var2 = Math.round((var0 >> 16 & 0xFF) * var1);
      int var3 = Math.round((var0 >> 8 & 0xFF) * var1);
      int var4 = Math.round((var0 & 0xFF) * var1);
      return 0xFF000000 | var2 << 16 | var3 << 8 | var4;
   }

   private static int lerpColor(int var0, int var1, float var2) {
      float var3 = yKt1(var2, 0.0F, 1.0F);
      int var4 = Math.round((var0 >> 16 & 0xFF) * (1.0F - var3) + (var1 >> 16 & 0xFF) * var3);
      int var5 = Math.round((var0 >> 8 & 0xFF) * (1.0F - var3) + (var1 >> 8 & 0xFF) * var3);
      int var6 = Math.round((var0 & 0xFF) * (1.0F - var3) + (var1 & 0xFF) * var3);
      return 0xFF000000 | var4 << 16 | var5 << 8 | var6;
   }

   private static int withAlpha(int var0, double var1) {
      int var3 = Math.max(0, Math.min(255, (int)Math.round(var1 * 255.0)));
      return var0 & 16777215 | var3 << 24;
   }

   private static float yKt1(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private static int xDgk1(float var0, char var1, float var2, float var3, Integer var4) {
      return var4 != null ? var4 : Arraylist.xQec0((var0 + var2 + var3 * 0.5F) * 0.42);
   }
}
