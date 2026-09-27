// Jade recovery: module: Progress Bar (render); original class: jade.deps.eLz.NgQmsWa1Op
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
import jade.client.module.minigames.BedDefender;
import jade.client.module.movement.Stasis;
import jade.client.module.player.BedNuker;
import jade.client.module.player.Blink;
import jade.client.module.player.BlockIn;
import jade.client.module.player.Buffer;
import jade.client.module.player.FakeLag;
import jade.client.module.render.progressbar.ProgressBarStyle;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

@ModuleInfo
public class ProgressBar extends Module implements ExternalRenderableModule {
   private static final float PCY = 0.5F;
   private static final float exTi = 0.56F;
   private static final int LABEL_COLOR = -855310;
   private static final int BAR_INNER_COLOR = -14408665;
   private static final int BAR_TRACK_COLOR = -15658732;
   private static final float MIN_VISIBILITY = 0.015F;
   private static final float zwB = 148.0F;
   private static final float MIN_ROW_WIDTH = 126.0F;
   private static final float TEXT_STYLE_LABEL_GAP = 4.0F;
   private static final float BAR_TOP_GAP = 2.0F;
   private static final float RjvTp = 7.0F;
   private static final float uDygN = 7.0F;
   private static final float BAR_FONT_SCALE = 0.84F;
   private static final float BAR_HEIGHT = 4.0F;
   private static final float TEXT_ROW_SPACING = 3.0F;
   private static final float OYP = 14.0F;
   private static final float PROGRESS_ANIMATION_SPEED = 18.0F;
   private final SliderSetting theme;
   private final FontSetting font;
   private final SliderSetting fontScale;
   private final SliderSetting jadeRoundness;
   private final GroupSetting modulesGroup;
   private final BooleanSetting bedDefender;
   private final BooleanSetting blockIn;
   private final BooleanSetting buffer;
   private final BooleanSetting blink;
   private final BooleanSetting fakeLag;
   private final BooleanSetting stasis;
   private final BooleanSetting bedNuker;
   private float screenX = Float.NaN;
   private float screenY = Float.NaN;
   private float Wcn = Float.NaN;
   private float normalizedY = Float.NaN;
   private long SYq = System.currentTimeMillis();
   private final Map<ProgressBarSource, ProgressBar$2> barStates = new IdentityHashMap<>();

   public ProgressBar() {
      super("Progress Bar", Category.render);
      this.registerSetting(this.theme = new SliderSetting("Theme", ProgressBarStyle.JADE.ordinal(), ProgressBarStyle.labels()));
      this.registerSetting(this.font = new FontSetting("Font", "Modern"));
      this.registerSetting(this.fontScale = new SliderSetting("Font scale", 1.0, 0.5, 2.0, 0.1));
      this.fontScale.visible = false;
      this.registerSetting(this.jadeRoundness = new SliderSetting("Jade roundness", 2.0, 0.0, 8.0, 0.5));
      this.registerSetting(this.modulesGroup = new GroupSetting("Modules"));
      this.registerSetting(
         this.bedDefender = new BooleanSetting(
            this.modulesGroup, "Bed Defender", true
         )
      );
      this.registerSetting(
         this.blockIn = new BooleanSetting(
            this.modulesGroup, "Block In", true
         )
      );
      this.registerSetting(
         this.buffer = new BooleanSetting(
            this.modulesGroup, "Buffer", true
         )
      );
      this.registerSetting(
         this.blink = new BooleanSetting(
            this.modulesGroup, "Blink", true
         )
      );
      this.registerSetting(
         this.fakeLag = new BooleanSetting(
            this.modulesGroup, "Fake Lag", true
         )
      );
      this.registerSetting(this.stasis = new BooleanSetting(this.modulesGroup, "Stasis", true));
      this.registerSetting(
         this.bedNuker = new BooleanSetting(
            this.modulesGroup, "Bed Nuker", true
         )
      );
   }

   public SliderSetting getFontScale() {
      return this.fontScale;
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
            this.updateAndRender(false);
         }
      }
   }

   public float getScreenX() {
      this.updateResolvedPosition();
      return this.screenX;
   }

   public float getScreenY() {
      this.updateResolvedPosition();
      return this.screenY;
   }

   public float luFsg3() {
      this.updateResolvedPosition();
      return this.Wcn;
   }

   public float IPLfz() {
      this.updateResolvedPosition();
      return this.normalizedY;
   }

   public void setNormalizedPosition(float var1, float var2) {
      this.Wcn = Math.max(0.0F, Math.min(1.0F, var1));
      this.normalizedY = Math.max(0.0F, Math.min(1.0F, var2));
      this.updateResolvedPosition();
   }

   public void resetPosition() {
      this.Wcn = 0.5F;
      this.normalizedY = 0.56F;
      this.updateResolvedPosition();
   }

   public float[] getBoundsAt(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      float var4 = this.screenX;
      float var5 = this.screenY;
      float var6 = this.Wcn;
      float var7 = this.normalizedY;
      this.setScreenPosition(var1, var2, var3);
      ArrayList var8 = new ArrayList();
      var8.add(new ProgressBar$3("Bed Defender", 0.72F, 1.0F));
      var8.add(new ProgressBar$3("Buffer 84t", 0.28F, 1.0F));
      ProgressBar$1 var9 = this.renderScaled(var8);
      this.screenX = var4;
      this.screenY = var5;
      this.Wcn = var6;
      this.normalizedY = var7;
      return new float[]{ProgressBar$1.rb07(var9), ProgressBar$1.getTop(var9), ProgressBar$1.getRight(var9), ProgressBar$1.QwtC3(var9)};
   }

   private void updateAndRender(boolean var1) {
      this.updateResolvedPosition();
      List<ProgressBarSource> var2 = var1 ? new ArrayList<ProgressBarSource>() : this.collectActiveBars();
      long var3 = System.currentTimeMillis();
      float var5 = Math.min(0.05F, Math.max(0.0F, (float)(var3 - this.SYq) / 1000.0F));
      this.SYq = var3;

      for (ProgressBarSource var7 : var2) {
         ProgressBar$2 var8 = this.barStates.get(var7);
         if (var8 == null) {
            var8 = new ProgressBar$2(var7.getProgressLabel(), clamp01(var7.getProgressFraction()));
            this.barStates.put(var7, var8);
         }
      }

      Iterator var13 = this.barStates.entrySet().iterator();
      ArrayList var14 = new ArrayList();

      while (var13.hasNext()) {
         Entry var16 = (Entry)var13.next();
         ProgressBarSource var9 = (ProgressBarSource)var16.getKey();
         ProgressBar$2 var10 = (ProgressBar$2)var16.getValue();
         boolean var11 = var2.contains(var9);
         float var12 = var11 ? 1.0F : 0.0F;
         ProgressBar$2.XMkTz(var10, WBeb(ProgressBar$2.getVisibility(var10), var12, var5, 14.0F));
         if (var11) {
            ProgressBar$2.setLabel(var10, var9.getProgressLabel());
            ProgressBar$2.setProgress(var10, WBeb(ProgressBar$2.getProgress(var10), clamp01(var9.getProgressFraction()), var5, 18.0F));
         }

         if (ProgressBar$2.getVisibility(var10) <= 0.015F && !var11) {
            var13.remove();
         } else if (ProgressBar$2.getVisibility(var10) > 0.015F) {
            if (ProgressBar$2.getProgress(var10) > 0.995F && (!var11 || clamp01(var9.getProgressFraction()) >= 0.999F)) {
               ProgressBar$2.setProgress(var10, 1.0F);
            }

            var14.add(new ProgressBar$3(ProgressBar$2.getLabel(var10), ProgressBar$2.getProgress(var10), KuAa(ProgressBar$2.getVisibility(var10))));
         }
      }

      if (!var14.isEmpty()) {
         if (this.VIuQ()) {
            this.renderToExternalBuffer(var14);
         } else {
            this.renderScaled(var14);
         }
      }
   }

   private void renderToExternalBuffer(List<ProgressBar$3> var1) {
      ExternalRenderBuffer var2 = ExternalRenderer.getActiveRenderBuffer();
      if (var2 != null) {
         float var3 = new ScaledResolution(mc).getScaleFactor();
         float var4 = (float)Math.max(0.5, Math.min(2.0, this.fontScale.getInput()));
         float var5 = var3 * var4;
         float var6 = this.screenX * var3;
         float var7 = this.screenY * var3;
         if (ProgressBarStyle.fromSetting(this.theme.getInput()) == ProgressBarStyle.TEXT) {
            this.renderTextStyleExternal(var2, var1, var6, var7, var5);
         } else {
            this.uMnw(var2, var1, var6, var7, var5);
         }
      }
   }

   private void uMnw(ExternalRenderBuffer var1, List<ProgressBar$3> var2, float var3, float var4, float var5) {
      IFont var6 = this.getJadeStyleFont();
      IFont var7 = this.getJadeStyleBoldFont();
      float var8 = var6.getTextBottomOffset() - var6.getTextTopOffset();
      float var9 = var7.getTextBottomOffset() - var7.getTextTopOffset();
      float var10 = var8 + 2.0F + 4.0F;
      float var11 = 148.0F;
      int var12 = getPercentTextWidth(var7);

      for (ProgressBar$3 var14 : var2) {
         var11 = Math.max(var11, Math.max(126.0F, (float)var6.getStringWidth(ProgressBar$3.getLabel(var14))) + 7.0F + var12);
      }

      float var26 = var3 - var11 * var5 / 2.0F;
      float var27 = var4;
      float var15 = Math.min(2.0F, Math.max(0.0F, (float)this.jadeRoundness.getInput()));

      for (int var16 = 0; var16 < var2.size(); var16++) {
         ProgressBar$3 var17 = (ProgressBar$3)var2.get(var16);
         int var18 = Math.round(255.0F * ProgressBar$3.getAlpha(var17));
         int var19 = ClientUtils.YVVZ(Arraylist.xQec0(var16 * 58.0), var18);
         String var20 = fvboe(ProgressBar$3.getProgress(var17));
         float var21 = Math.max(1.0F, var11 - var12 - 7.0F);
         String var22 = truncateToWidth(var6, ProgressBar$3.getLabel(var17), var21);
         FormattedTextRenderer.drawTextAtHeight(
            var1,
            var6,
            var22,
            var26,
            var27 - var6.getTextTopOffset() * var5,
            var6.getFontHeight() * var5,
            ClientUtils.YVVZ(-855310, var18),
            true,
            var6.getStringWidth(var22) * var5
         );
         float var23 = var27 + (var8 + 2.0F) * var5;
         this.drawJadeBarExternal(var1, var26, var23, var21 * var5, 4.0F * var5, var15 * var5, ProgressBar$3.getProgress(var17), var19, ProgressBar$3.getAlpha(var17));
         float var24 = var26 + (var21 + 7.0F + var12 - var7.getStringWidth(var20)) * var5;
         float var25 = var23 + (2.0F - var9 / 2.0F - var7.getTextTopOffset()) * var5;
         FormattedTextRenderer.drawTextAtHeight(var1, var7, var20, var24, var25, var7.getFontHeight() * var5, var19, true, var7.getStringWidth(var20) * var5);
         var27 += (var10 + 7.0F) * var5;
      }
   }

   private void renderTextStyleExternal(ExternalRenderBuffer var1, List<ProgressBar$3> var2, float var3, float var4, float var5) {
      IFont var6 = this.getTextStyleFont();
      IFont var7 = this.getTextStyleBoldFont();
      float var8 = 0.0F;
      float var9 = var6.getTextBottomOffset() - var6.getTextTopOffset();
      int var10 = getPercentTextWidth(var7);

      for (ProgressBar$3 var12 : var2) {
         var8 = Math.max(var8, var6.getStringWidth(ProgressBar$3.getLabel(var12)) + 4.0F + var10);
      }

      float var18 = var3 - var8 * var5 / 2.0F;
      float var19 = var4;

      for (int var13 = 0; var13 < var2.size(); var13++) {
         ProgressBar$3 var14 = (ProgressBar$3)var2.get(var13);
         int var15 = Math.round(255.0F * ProgressBar$3.getAlpha(var14));
         String var16 = fvboe(ProgressBar$3.getProgress(var14));
         FormattedTextRenderer.drawTextAtHeight(
            var1,
            var6,
            ProgressBar$3.getLabel(var14),
            var18,
            var19 - var6.getTextTopOffset() * var5,
            var6.getFontHeight() * var5,
            ClientUtils.YVVZ(-855310, var15),
            true,
            var6.getStringWidth(ProgressBar$3.getLabel(var14)) * var5
         );
         float var17 = var18 + (var6.getStringWidth(ProgressBar$3.getLabel(var14)) + 4.0F + var10 - var7.getStringWidth(var16)) * var5;
         FormattedTextRenderer.drawTextAtHeight(
            var1,
            var7,
            var16,
            var17,
            var19 - var7.getTextTopOffset() * var5,
            var7.getFontHeight() * var5,
            ClientUtils.YVVZ(Arraylist.xQec0(var13 * 58.0), var15),
            true,
            var7.getStringWidth(var16) * var5
         );
         var19 += (var9 + 3.0F) * var5;
      }
   }

   private void drawJadeBarExternal(ExternalRenderBuffer var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8, float var9) {
      int var10 = ClientUtils.YVVZ(-15658732, Math.round(255.0F * var9));
      int var11 = ClientUtils.YVVZ(-14408665, Math.round(255.0F * var9));
      var1.fillRoundedRect(var2, var3, var2 + var4, var3 + var5, var10, var6);
      float var12 = Math.max(1.0F, (float)new ScaledResolution(mc).getScaleFactor());
      var1.fillRoundedRect(var2 + var12, var3 + var12, var2 + var4 - var12, var3 + var5 - var12, var11, Math.max(0.0F, var6 - var12));
      float var13 = Math.max(0.0F, (var4 - var12 * 2.0F) * clamp01(var7));
      if (var13 > 0.5F) {
         var1.fillRoundedRect(var2 + var12, var3 + var12, var2 + var12 + var13, var3 + var5 - var12, var8, Math.min(Math.max(0.0F, var6 - var12), var13 / 2.0F));
      }
   }

   private List<ProgressBarSource> collectActiveBars() {
      ArrayList var1 = new ArrayList();
      ProgressBarSource var2 = this.getBarModule(BedDefender.class);
      this.addBarIfEnabled(var1, this.bedDefender, var2);
      var2 = this.getBarModule(BlockIn.class);
      this.addBarIfEnabled(var1, this.blockIn, var2);
      var2 = this.getBarModule(Buffer.class);
      this.addBarIfEnabled(var1, this.buffer, var2);
      var2 = this.getBarModule(Blink.class);
      this.addBarIfEnabled(var1, this.blink, var2);
      var2 = this.getBarModule(FakeLag.class);
      this.addBarIfEnabled(var1, this.fakeLag, var2);
      var2 = this.getBarModule(Stasis.class);
      this.addBarIfEnabled(var1, this.stasis, var2);
      var2 = this.getBarModule(BedNuker.class);
      this.addBarIfEnabled(var1, this.bedNuker, var2);
      return var1;
   }

   private void addBarIfEnabled(List<ProgressBarSource> var1, BooleanSetting var2, ProgressBarSource var3) {
      if (var2.isToggled() && this.Tb25(var3)) {
         var1.add(var3);
      }
   }

   private ProgressBarSource getBarModule(Class<? extends Module> var1) {
      Module var2 = Jade.getModuleManager().getModule(var1);
      return var2 instanceof ProgressBarSource ? (ProgressBarSource)var2 : null;
   }

   private boolean Tb25(ProgressBarSource var1) {
      return var1 != null && var1.isProgressActive();
   }

   private ProgressBar$1 renderScaled(List<ProgressBar$3> var1) {
      float var2 = (float)Math.max(0.5, Math.min(2.0, this.fontScale.getInput()));
      float var3 = this.screenX;
      float var4 = this.screenY;
      this.screenX = 0.0F;
      this.screenY = 0.0F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(var3, var4, 0.0F);
      GlStateManager.scale(var2, var2, 1.0F);

      ProgressBar$1 var5;
      try {
         var5 = ProgressBarStyle.fromSetting(this.theme.getInput()) == ProgressBarStyle.TEXT ? this.QLCyn(var1) : this.rDff1(var1);
      } finally {
         GlStateManager.popMatrix();
         this.screenX = var3;
         this.screenY = var4;
      }

      return new ProgressBar$1(
         var3 + ProgressBar$1.rb07(var5) * var2,
         var4 + ProgressBar$1.getTop(var5) * var2,
         var3 + ProgressBar$1.getRight(var5) * var2,
         var4 + ProgressBar$1.QwtC3(var5) * var2
      );
   }

   private ProgressBar$1 rDff1(List<ProgressBar$3> var1) {
      IFont var2 = this.getJadeStyleFont();
      IFont var3 = this.getJadeStyleBoldFont();
      float var4 = var2.getTextBottomOffset() - var2.getTextTopOffset();
      float var5 = var3.getTextBottomOffset() - var3.getTextTopOffset();
      float var6 = var4 + 2.0F + 4.0F;
      float var7 = 148.0F;
      int var8 = getPercentTextWidth(var3);

      for (ProgressBar$3 var10 : var1) {
         var7 = Math.max(var7, Math.max(126.0F, (float)var2.getStringWidth(ProgressBar$3.getLabel(var10))) + 7.0F + var8);
      }

      float var23 = this.screenX - var7 / 2.0F;
      float var24 = this.screenY;
      float var11 = var24;
      float var12 = Math.min(2.0F, Math.max(0.0F, (float)this.jadeRoundness.getInput()));

      for (int var13 = 0; var13 < var1.size(); var13++) {
         ProgressBar$3 var14 = (ProgressBar$3)var1.get(var13);
         int var15 = Math.round(255.0F * ProgressBar$3.getAlpha(var14));
         int var16 = ClientUtils.YVVZ(Arraylist.xQec0(var13 * 58.0), var15);
         int var17 = ClientUtils.YVVZ(-855310, var15);
         String var18 = fvboe(ProgressBar$3.getProgress(var14));
         float var19 = Math.max(1.0F, var7 - var8 - 7.0F);
         float var20 = var11 - var2.getTextTopOffset();
         String var21 = truncateToWidth(var2, ProgressBar$3.getLabel(var14), var19);
         var2.drawString(var21, var23, var20, var17, true);
         float var22 = var11 + var4 + 2.0F;
         this.DPKWu(var23, var22, var19, 4.0F, var12, ProgressBar$3.getProgress(var14), var16, ProgressBar$3.getAlpha(var14));
         var3.drawString(var18, var23 + var19 + 7.0F + var8 - var3.getStringWidth(var18), var22 + 2.0F - var5 / 2.0F - var3.getTextTopOffset(), var16, true);
         var11 += var6 + 7.0F;
      }

      return new ProgressBar$1(var23, var24, var23 + var7, var11 - 7.0F);
   }

   private ProgressBar$1 QLCyn(List<ProgressBar$3> var1) {
      IFont var2 = this.getTextStyleFont();
      IFont var3 = this.getTextStyleBoldFont();
      float var4 = 0.0F;
      float var5 = var2.getTextBottomOffset() - var2.getTextTopOffset();
      int var6 = getPercentTextWidth(var3);

      for (ProgressBar$3 var8 : var1) {
         var4 = Math.max(var4, var2.getStringWidth(ProgressBar$3.getLabel(var8)) + 4.0F + var6);
      }

      float var15 = this.screenX - var4 / 2.0F;
      float var16 = this.screenY;

      for (int var9 = 0; var9 < var1.size(); var9++) {
         ProgressBar$3 var10 = (ProgressBar$3)var1.get(var9);
         String var11 = fvboe(ProgressBar$3.getProgress(var10));
         int var12 = Math.round(255.0F * ProgressBar$3.getAlpha(var10));
         int var13 = var2.getStringWidth(ProgressBar$3.getLabel(var10));
         float var14 = var16 - var2.getTextTopOffset();
         var2.drawString(ProgressBar$3.getLabel(var10), var15, var14, ClientUtils.YVVZ(-855310, var12), true);
         var3.drawString(
            var11,
            var15 + var13 + 4.0F + var6 - var3.getStringWidth(var11),
            var16 - var3.getTextTopOffset(),
            ClientUtils.YVVZ(Arraylist.xQec0(var9 * 58.0), var12),
            true
         );
         var16 += var5 + 3.0F;
      }

      return new ProgressBar$1(var15, this.screenY, var15 + var4, var16 - 3.0F);
   }

   private void DPKWu(float var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8) {
      int var9 = Math.round(255.0F * var8);
      int var10 = ClientUtils.YVVZ(-15658732, var9);
      if (var5 < 1.0F) {
         RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, var10);
      } else {
         RoundedRect.drawRoundedRectArgb(var1, var2, var3, var4, var5, var10);
      }

      float var11 = var1 + 1.0F;
      float var12 = var2 + 1.0F;
      float var13 = Math.max(0.0F, var3 - 2.0F);
      float var14 = Math.max(0.0F, var4 - 2.0F);
      float var15 = Math.max(0.0F, var5 - 1.0F);
      this.drawBarTrack(var11, var12, var13, var14, var15, var8);
      float var16 = var13 * clamp01(var6);
      if (var16 > 0.5F) {
         this.drawRoundedFill(var11, var12, var16, var14, Math.min(var15, var16 / 2.0F), var7);
      }
   }

   private void drawBarTrack(float var1, float var2, float var3, float var4, float var5, float var6) {
      int var7 = Math.round(255.0F * var6);
      int var8 = ClientUtils.YVVZ(-14408665, var7);
      if (var5 < 1.0F) {
         RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, var8);
      } else {
         RoundedRect.drawRoundedRectArgb(var1, var2, var3, var4, var5, var8);
      }
   }

   private void drawRoundedFill(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (var5 < 1.0F) {
         RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, var6);
      } else {
         RoundedRect.drawRoundedRectArgb(var1, var2, var3, var4, var5, var6);
      }
   }

   private IFont getTextStyleFont() {
      return FontManager.getHudRenderer(this.getFontFamily(), 1.0F);
   }

   private IFont getTextStyleBoldFont() {
      return FontManager.getHudRenderer(FontManager.getBoldFamily(this.getFontFamily()), 1.0F);
   }

   private IFont getJadeStyleFont() {
      return FontManager.getHudRenderer(this.getFontFamily(), 0.84F);
   }

   private IFont getJadeStyleBoldFont() {
      return FontManager.getHudRenderer(FontManager.getBoldFamily(this.getFontFamily()), 0.84F);
   }

   private String getFontFamily() {
      return this.font.getResolvedFontName();
   }

   private void updateResolvedPosition() {
      this.dBlpX(new ScaledResolution(mc));
   }

   private void dBlpX(ScaledResolution var1) {
      int var2 = Math.max(1, var1.getScaledWidth());
      int var3 = Math.max(1, var1.getScaledHeight());
      if (Float.isNaN(this.Wcn) || Float.isNaN(this.normalizedY)) {
         this.Wcn = 0.5F;
         this.normalizedY = 0.56F;
      }

      this.screenX = this.Wcn * var2;
      this.screenY = this.normalizedY * var3;
   }

   private void setScreenPosition(float var1, float var2, ScaledResolution var3) {
      this.screenX = var1;
      this.screenY = var2;
      this.Wcn = var1 / Math.max(1.0F, (float)var3.getScaledWidth());
      this.normalizedY = var2 / Math.max(1.0F, (float)var3.getScaledHeight());
   }

   private static float WBeb(float var0, float var1, float var2, float var3) {
      if (Math.abs(var1 - var0) < 1.0E-4F) {
         return var1;
      } else {
         float var4 = 1.0F - (float)Math.pow(2.0, -var3 * var2);
         return var0 + (var1 - var0) * var4;
      }
   }

   private static float KuAa(float var0) {
      float var1 = clamp01(var0);
      float var2 = 1.0F - var1;
      return 1.0F - var2 * var2 * var2;
   }

   private static float clamp01(float var0) {
      return var0 < 0.0F ? 0.0F : (var0 > 1.0F ? 1.0F : var0);
   }

   private static String fvboe(float var0) {
      return Math.round(clamp01(var0) * 100.0F) + "%";
   }

   private static int getPercentTextWidth(IFont var0) {
      return var0.getStringWidth("100%");
   }

   private static String truncateToWidth(IFont var0, String var1, float var2) {
      if (var0.getStringWidth(var1) <= var2) {
         return var1;
      } else {
         String var3 = "...";
         float var4 = var0.getStringWidth(var3);
         if (var4 >= var2) {
            return "";
         } else {
            StringBuilder var5 = new StringBuilder(var1);

            while (var5.length() > 0 && var0.getStringWidth(var5.toString()) + var4 > var2) {
               var5.setLength(var5.length() - 1);
            }

            return var5.toString() + var3;
         }
      }
   }
}
