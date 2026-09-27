// Jade recovery: module: Arraylist (render); original class: jade.deps.eLz.MbsVIT
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
import jade.client.module.client.CommandLine;
import jade.client.module.render.arraylist.ColorTheme;
import jade.client.module.render.arraylist.ModuleSorter;
import jade.client.module.render.shared.PostProcessing;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = "HUD")
public class Arraylist extends Module implements ModuleSorter, ExternalRenderableModule {
   private static final String[] nXx = new String[]{"Vertical", "Horizontal"};
   private static final String[] WAVE_VERTICAL_DIRECTION_MODES = new String[]{"Down", "Up"};
   private static final String[] WAVE_HORIZONTAL_DIRECTION_MODES = new String[]{"Left", "Right"};
   private static final double Uq4 = 0.35;
   private static final long LvQ = 7500L;
   private static final double WAVE_PHASE_SCALE = 0.12;
   private static List<Integer> AOGM = Collections.emptyList();
   public static SliderSetting waveAxis;
   public static SliderSetting waveDirection;
   public static SliderSetting waveDirection2;
   public static SliderSetting waveSpeed;
   public static SliderSetting waveLength;
   public static FontSetting font;
   public static SliderSetting scale;
   private static SliderSetting animationSpeed;
   private static SliderSetting outline;
   private static SliderSetting backgroundRounding;
   private static SliderSetting backgroundPaddingX;
   private static SliderSetting backgroundPaddingY;
   private static SliderSetting backgroundOpacity;
   private static BooleanSetting shadow;
   private static SliderSetting shadowRadius;
   private static SliderSetting shadowOpacity;
   public static SliderSetting sort;
   private static BooleanSetting drawBackground;
   private static BooleanSetting roundAlignedSide;
   private static BooleanSetting textShadow;
   private static BooleanSetting lowercase;
   public static BooleanSetting showModuleInfo;
   private static BooleanSetting bind;
   private static BooleanSetting onlyBoundVisible;
   private static final float DEFAULT_ANCHOR_X = 5.0F;
   private static final float DEFAULT_ANCHOR_Y = 70.0F;
   public static float anchorX = 5.0F;
   public static float riwe = 70.0F;
   private static float fXccSz = 0.985F;
   private static float anchorYRatio = 0.02F;
   private static final String[] OUTLINE_MODES = new String[]{"None", "Bar", "Dash", "Full"};
   private static final String[] SORT_MODES = new String[]{"Length", "Alphabetical"};
   private static final int BACKGROUND_BASE_COLOR = new Color(
         0, 0, 0
      )
      .getRGB();
   private static final int OzW4 = 3;
   private static final float DEFAULT_SHADOW_RADIUS = 3.5F;
   private static final float str5 = 1.0F;
   private static final float MIN_VISIBLE_PROGRESS = 0.015F;
   private static final float ROW_SPACING = 12.0F;
   private static final long INFO_REFRESH_INTERVAL_NANOS = 100000000L;
   private static final Map<Module, Arraylist$3> GeV = new IdentityHashMap<>();
   private static final Map<Module, String> moduleInfoCache = new IdentityHashMap<>();
   private static long lastFrameMillis = System.currentTimeMillis();
   private boolean lastAlphabeticalSort;
   private boolean lastShowModuleInfo;
   private String VwA = "";
   private float fio = -1.0F;
   private long lastInfoRefreshNanos;
   private static final double HALF = 0.5;

   public Arraylist() {
      super("Arraylist", Category.render);
      this.registerSetting(
         waveAxis = new SliderSetting(
            "Wave axis", 0, nXx
         )
      );
      this.registerSetting(
         waveDirection = new SliderSetting(
            "Wave direction", 0, WAVE_VERTICAL_DIRECTION_MODES
         )
      );
      this.registerSetting(
         waveDirection2 = new SliderSetting(
            "Wave direction", 0, WAVE_HORIZONTAL_DIRECTION_MODES
         )
      );
      this.registerSetting(waveSpeed = new SliderSetting("Wave speed", 1.0, 0.1, 5.0, 0.1));
      this.registerSetting(waveLength = new SliderSetting("Wave length", 1.0, 0.5, 5.0, 0.1));
      this.registerSetting(font = new FontSetting("Font", "Modern"));
      this.registerSetting(scale = new SliderSetting("Scale", 1.0, 0.5, 2.0, 0.1));
      scale.visible = false;
      this.registerSetting(animationSpeed = new SliderSetting("Animation speed", 14.0, 4.0, 30.0, 0.5));
      this.registerSetting(
         outline = new SliderSetting(
            "Outline", 0, OUTLINE_MODES
         )
      );
      this.registerSetting(
         sort = new SliderSetting(
            "Sort",
            0,
            SORT_MODES,
            new String[]{"Alphabetical sort"}
         )
      );
      this.registerSetting(
         drawBackground = new BooleanSetting(
            "Draw background", false
         )
      );
      this.registerSetting(backgroundRounding = new SliderSetting("Background rounding", 3.0, 0.0, 10.0, 0.5));
      this.registerSetting(roundAlignedSide = new BooleanSetting("Round aligned side", false));
      this.registerSetting(backgroundPaddingX = new SliderSetting("Background padding X", 2.0, 0.0, 10.0, 0.5));
      this.registerSetting(backgroundPaddingY = new SliderSetting("Background padding Y", 2.0, 0.0, 10.0, 0.5));
      this.registerSetting(backgroundOpacity = new SliderSetting("Background opacity", 0.45, 0.0, 1.0, 0.05));
      this.registerSetting(
         shadow = new BooleanSetting("Shadow", false)
      );
      this.registerSetting(shadowRadius = new SliderSetting("Shadow radius", 3.5, 1.0, 10.0, 0.5));
      this.registerSetting(shadowOpacity = new SliderSetting("Shadow opacity", 1.0, 0.0, 1.0, 0.05));
      this.registerSetting(textShadow = new BooleanSetting("Text shadow", true));
      this.registerSetting(
         lowercase = new BooleanSetting(
            "Lowercase", false
         )
      );
      this.registerSetting(showModuleInfo = new BooleanSetting("Show module info", true));
      this.registerSetting(
         bind = new BooleanSetting(
            "Bind", false
         )
      );
      this.registerSetting(
         onlyBoundVisible = new BooleanSetting(
            "Only Bound Visible", false
         )
      );
      bind.visible = showModuleInfo.isToggled();
   }

   @Override
   public void guiUpdate() {
      font.setVisible(true, this);
      boolean var1 = AOGM.size() > 1;
      boolean var2 = KyZe();
      if (waveAxis != null) {
         waveAxis.setVisible(var1, this);
      }

      if (waveDirection != null) {
         waveDirection.setVisible(var1 && var2, this);
      }

      if (waveDirection2 != null) {
         waveDirection2.setVisible(var1 && !var2, this);
      }

      if (waveSpeed != null) {
         waveSpeed.setVisible(var1, this);
      }

      if (waveLength != null) {
         waveLength.setVisible(var1, this);
      }

      boolean var3 = drawBackground != null && drawBackground.isToggled();
      if (bind != null) {
         bind.setVisible(showModuleInfo != null && showModuleInfo.isToggled(), this);
      }

      if (backgroundRounding != null) {
         backgroundRounding.setVisible(var3, this);
      }

      if (roundAlignedSide != null) {
         roundAlignedSide.setVisible(var3, this);
      }

      if (backgroundPaddingX != null) {
         backgroundPaddingX.setVisible(var3, this);
      }

      if (backgroundPaddingY != null) {
         backgroundPaddingY.setVisible(var3, this);
      }

      if (backgroundOpacity != null) {
         backgroundOpacity.setVisible(var3, this);
      }

      if (shadow != null) {
         shadow.setVisible(var3, this);
      }

      boolean var4 = var3 && shadow != null && shadow.isToggled();
      if (shadowRadius != null) {
         shadowRadius.setVisible(var4, this);
      }

      if (shadowOpacity != null) {
         shadowOpacity.setVisible(var4, this);
      }
   }

   @Override
   public void onEnable() {
      this.guiUpdate();
      Jade.getModuleManager().resortModules();
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == showModuleInfo || var1 == bind || var1 == onlyBoundVisible) {
         Jade.getModuleManager().resortModules();
      }

      if (var1 == showModuleInfo || var1 == drawBackground || var1 == shadow) {
         this.guiUpdate();
      }
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      if (var1 == sort || var1 == font || var1 == scale) {
         Jade.getModuleManager().resortModules();
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         if (this.lastAlphabeticalSort != isAlphabeticalSort()) {
            this.lastAlphabeticalSort = isAlphabeticalSort();
            Jade.getModuleManager().resortModules();
         }

         if (this.lastShowModuleInfo != showModuleInfo.isToggled()) {
            this.lastShowModuleInfo = showModuleInfo.isToggled();
            Jade.getModuleManager().resortModules();
         }

         String var2 = getFontName();
         float var3 = getScaleFactor();
         if (!var2.equals(this.VwA) || Float.compare(var3, this.fio) != 0) {
            this.VwA = var2;
            this.fio = var3;
            Jade.getModuleManager().resortModules();
         }

         if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
            clampAnchor();
            this.refreshModuleInfo();
            if (Module.infoChanged) {
               Jade.getModuleManager().resortModules();
            }

            Module.infoChanged = false;
            IFont var4 = FAEgbj();
            int var5 = var4.getTextTopOffset();
            int var6 = var4.getTextBottomOffset();
            int var7 = computeBackgroundPaddingX();
            int var8 = computeBackgroundPaddingTop();
            int var9 = computeBackgroundPaddingBottom();
            int var10 = computeOutlineThickness();
            int var11 = computeRowHeight(var5, var6, var8, var9);
            long var12 = System.currentTimeMillis();
            float var14 = Math.min(0.05F, Math.max(0.0F, (float)(var12 - lastFrameMillis) / 1000.0F));
            lastFrameMillis = var12;
            List var15 = collectVisibleModules(var14, var11);
            ArrayList var16 = new ArrayList();
            double var17 = 0.0;

            try {
               for (Module var20 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var15)) {
                  Arraylist$3 var21 = GeV.get(var20);
                  if (var21 != null) {
                     float var22 = TnX7(Arraylist$3.kdvyirQ(var21));
                     if (!(var22 <= 0.015F)) {
                        String var23 = XSOn(var20);
                        int var24 = var4.getStringWidth(var23);
                        float var25 = Arraylist$3.getRowY(var21);
                        float var26 = (1.0F - var22) * 12.0F;
                        boolean var27 = isRightAligned();
                        float var28 = var27 ? anchorX + var26 : anchorX - var26;
                        float var29 = ixd1(var25, var5, var8);
                        double var30 = var28 - var7;
                        double var32 = var28 + var24 + var7;
                        double var34 = var25;
                        double var36 = var25 + var11;
                        double var38 = var30 - var10;
                        double var40 = var32 + var10;
                        double var42 = var34 - var10;
                        if (var27) {
                           var28 -= var24;
                           var30 = var28 - var7;
                           var32 = var28 + var24 + var7;
                           var38 = var30 - var10;
                           var40 = var32 + var10;
                        }

                        double var44 = (var30 + var32) * 0.5;
                        double var46 = CDp1(var17, var44);
                        int var48 = rdYl(xQec0(var46), var22);
                        var16.add(new Arraylist$4(var23, var24, var28, var29, var30, var34, var32, var36, var38, var42, var40, var48, var22));
                        if (KyZe()) {
                           var17 += MSJgFh4();
                        }
                     }
                  }
               }

               if (this.VIuQ()) {
                  this.renderToExternalBuffer(var16, var4, var10);
               } else {
                  rwqDwx(var16, var4, var10, var7, true);
               }
            } catch (Exception var49) {
               ClientUtils.sendColoredMessage("&cAn error occurred rendering Arraylist. check your logs");
               var49.printStackTrace();
            }
         }
      }
   }

   @Override
   public void onDisable() {
      ExternalRenderer.invalidateExternalFrame();
   }

   private void refreshModuleInfo() {
      long var1 = System.nanoTime();
      if (var1 - this.lastInfoRefreshNanos >= 100000000L) {
         this.lastInfoRefreshNanos = var1;

         for (Module var4 : Jade.getModuleManager().getSortedModules()) {
            moduleInfoCache.put(var4, var4.updateInfo());
         }
      }
   }

   private static String XSOn(Module var0) {
      String var1 = getModuleName(var0);
      String var2 = moduleInfoCache.get(var0);
      if (showModuleInfo != null && showModuleInfo.isToggled() && var2 != null && !var2.isEmpty()) {
         var1 = var1 + " §7" + var2;
      }

      var1 = appendKeybindText(var1, var0);
      if (lowercase != null && lowercase.isToggled()) {
         var1 = var1.toLowerCase();
      }

      return var1;
   }

   private void renderToExternalBuffer(List<Arraylist$4> var1, IFont var2, int var3) {
      ExternalRenderBuffer var4 = ExternalRenderer.getActiveRenderBuffer();
      if (var4 != null) {
         float var5 = new ScaledResolution(mc).getScaleFactor();
         int var6 = getOutlineMode();

         for (int var7 = 0; var7 < var1.size(); var7++) {
            Arraylist$4 var8 = (Arraylist$4)var1.get(var7);
            double var9 = (var7 == 0 ? Arraylist$4.NsfF(var8) : (Arraylist$4.getOuterBottom((Arraylist$4)var1.get(var7 - 1)) + Arraylist$4.NsfF(var8)) * 0.5) * var5;
            double var11 = (var7 + 1 == var1.size() ? Arraylist$4.getOuterBottom(var8) : (Arraylist$4.getOuterBottom(var8) + Arraylist$4.NsfF((Arraylist$4)var1.get(var7 + 1))) * 0.5) * var5;
            double var13 = Arraylist$4.kcAox(var8) * var5;
            double var15 = Arraylist$4.getBoundsRight(var8) * var5;
            if (isBackgroundEnabled()) {
               if (isShadowEnabled()) {
                  for (int var17 = 6; var17 > 0; var17--) {
                     float var18 = getShadowRadius() * var5 * var17 / 6.0F;
                     int var19 = Math.round(SRes738() * Arraylist$4.getFadeProgress(var8) * 255.0F / 14.0F);
                     var4.fillRoundedRect(var13 - var18, var9 - var18, var15 + var18, var11 + var18, var19 << 24, getCornerRadius() * var5 + var18);
                  }
               }

               drawRowBackground(
                  var8,
                  var7 > 0 ? (Arraylist$4)var1.get(var7 - 1) : null,
                  var7 + 1 < var1.size() ? (Arraylist$4)var1.get(var7 + 1) : null,
                  getCornerRadius(),
                  rdYl(zAzr(), Arraylist$4.getFadeProgress(var8)),
                  (float)(var9 / var5),
                  (float)(var11 / var5),
                  var4,
                  var5
               );
            }

            float var22 = var3 * var5;
            if (var6 != 1 && var6 != 2) {
               if (var6 == 3) {
                  var4.drawLine(var13, var9, var13, var11, Arraylist$4.getColor(var8), var22);
                  var4.drawLine(var15, var9, var15, var11, Arraylist$4.getColor(var8), var22);
                  if (var7 == 0) {
                     var4.drawLine(var13, var9, var15, var9, Arraylist$4.getColor(var8), var22);
                  }

                  if (var7 + 1 == var1.size()) {
                     var4.drawLine(var13, var11, var15, var11, Arraylist$4.getColor(var8), var22);
                  } else {
                     var4.drawLine(var13, var11, Arraylist$4.kcAox((Arraylist$4)var1.get(var7 + 1)) * var5, var11, Arraylist$4.getColor(var8), var22);
                     var4.drawLine(var15, var11, Arraylist$4.getBoundsRight((Arraylist$4)var1.get(var7 + 1)) * var5, var11, Arraylist$4.getColor(var8), var22);
                  }
               }
            } else {
               double var23 = isRightAligned() ? var15 : var13 - var22;
               double var20 = var6 == 2 ? Math.max((double)var5, (var11 - var9) * 0.2) : 0.0;
               var4.fillRoundedRect(var23, var9 + var20, var23 + var22, var11 - var20, Arraylist$4.getColor(var8), 0.0F);
            }

            FormattedTextRenderer.drawTextAtHeight(
               var4,
               var2,
               Arraylist$4.getFormattedText(var8),
               Arraylist$4.getTextX(var8) * var5,
               Arraylist$4.getTextY(var8) * var5,
               var2.getFontHeight() * var5,
               Arraylist$4.getColor(var8),
               textShadow.isToggled(),
               Arraylist$4.getTextWidth(var8) * var5
            );
         }
      }
   }

   public static int getMaxModuleWidth() {
      IFont var0 = FAEgbj();
      int var1 = 0;

      for (Module var3 : Jade.getModuleManager().getSortedModules()) {
         if (var3.isEnabled() && !(var3 instanceof Arraylist) && !isModuleHiddenInHud(var3, false)) {
            var1 = Math.max(var1, var0.getStringWidth(getModuleDisplayText(var3)));
         }
      }

      return var1;
   }

   private static boolean isModuleHiddenInHud(Module var0, boolean var1) {
      if (var0.isHidden()) {
         return true;
      } else {
         return onlyBoundVisible != null && onlyBoundVisible.isToggled() && var0.getKeycode() == 0 ? true : var0 == Jade.getModuleManager().getModule(CommandLine.class);
      }
   }

   private static boolean isLastVisibleModule(Module var0, boolean var1) {
      boolean var2 = false;

      for (Module var4 : Jade.getModuleManager().getSortedModules()) {
         if (!var2) {
            if (var4 == var0) {
               var2 = true;
            }
         } else if (var4.isEnabled() && !(var4 instanceof Arraylist) && !isModuleHiddenInHud(var4, var1)) {
            return false;
         }
      }

      return true;
   }

   private static List<Module> collectVisibleModules(float var0, int var1) {
      ArrayList var2 = new ArrayList();
      Set var3 = Collections.newSetFromMap(new IdentityHashMap());

      for (Module var5 : Jade.getModuleManager().getSortedModules()) {
         if (shouldShowModule(var5)) {
            var2.add(var5);
            var3.add(var5);
         }
      }

      ArrayList var10 = new ArrayList();

      for (Entry var6 : GeV.entrySet()) {
         Module var7 = (Module)var6.getKey();
         if (!var3.contains(var7) && shouldShowModule(var7) && Arraylist$3.kdvyirQ((Arraylist$3)var6.getValue()) > 0.015F) {
            var10.add(var6);
         }
      }

      var10.sort(Comparator.comparingDouble(Arraylist::getEntryRowY));

      for (Entry var14 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var10)) {
         ZNrhK(var2, (Module)var14.getKey(), (Arraylist$3)var14.getValue(), var1);
      }

      float var13 = riwe;

      for (Module var17 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var2)) {
         Arraylist$3 var8 = GeV.get(var17);
         if (var8 == null) {
            var8 = new Arraylist$3();
            GeV.put(var17, var8);
         }

         float var9 = var17.isEnabled() ? 1.0F : 0.0F;
         Arraylist$3.vYal(var8, smoothTowards(Arraylist$3.kdvyirQ(var8), var9, var0, Jhlwe()));
         Arraylist$3.NerX(var8, var13);
         var13 += computeRowAdvance(Arraylist$3.kdvyirQ(var8), var1);
      }

      Iterator var16 = GeV.entrySet().iterator();

      while (var16.hasNext()) {
         Entry var18 = (Entry)var16.next();
         if (Arraylist$3.kdvyirQ((Arraylist$3)var18.getValue()) <= 0.015F && !((Module)var18.getKey()).isEnabled()) {
            var16.remove();
         }
      }

      return var2;
   }

   private static void ZNrhK(List<Module> var0, Module var1, Arraylist$3 var2, int var3) {
      int var4 = Math.max(0, Math.round((Arraylist$3.getRowY(var2) - riwe) / Math.max(1.0F, (float)var3)));
      var0.add(Math.min(var4, var0.size()), var1);
   }

   private static boolean shouldShowModule(Module var0) {
      return var0 != null
         && var0 != Jade.getModuleManager().getModule(Arraylist.class)
         && !var0.isHidden()
         && (onlyBoundVisible == null || !onlyBoundVisible.isToggled() || var0.getKeycode() != 0)
         && !isModuleHiddenInHud(var0, false);
   }

   private static float computeRowAdvance(float var0, int var1) {
      return var1 * TnX7(var0);
   }

   private static int rdYl(int var0, float var1) {
      int var2 = var0 >>> 24;
      int var3 = Math.max(0, Math.min(255, Math.round(var2 * var1)));
      return ClientUtils.YVVZ(var0, var3);
   }

   private static float smoothTowards(float var0, float var1, float var2, float var3) {
      if (Math.abs(var1 - var0) < 1.0E-4F) {
         return var1;
      } else {
         float var4 = 1.0F - (float)Math.pow(2.0, -var3 * var2);
         return var0 + (var1 - var0) * var4;
      }
   }

   private static float easeOutCubic(float var0) {
      float var1 = Math.max(0.0F, Math.min(1.0F, var0));
      float var2 = 1.0F - var1;
      return 1.0F - var2 * var2 * var2;
   }

   private static float Jhlwe() {
      return animationSpeed == null ? 14.0F : (float)animationSpeed.getInput();
   }

   private static float TnX7(float var0) {
      float var1 = easeOutCubic(var0);
      float var2 = 0.35F;
      return var1 <= 0.35F ? 0.0F : Math.max(0.0F, Math.min(1.0F, (var1 - 0.35F) / 0.65F));
   }

   public static IFont FAEgbj() {
      return FontManager.getHudRenderer(getFontName(), getScaleFactor());
   }

   public static String getModuleName(Module var0) {
      String var1 = var0.getNameInHud();
      if (lowercase != null && lowercase.isToggled()) {
         var1 = var1.toLowerCase();
      }

      return var1;
   }

   public static String getModuleDisplayText(Module var0) {
      String var1 = getModuleName(var0);
      if (showModuleInfo != null && showModuleInfo.isToggled() && !var0.getInfo().isEmpty()) {
         var1 = var1 + " §7" + var0.getInfo();
      }

      var1 = appendKeybindText(var1, var0);
      if (lowercase != null && lowercase.isToggled()) {
         var1 = var1.toLowerCase();
      }

      return var1;
   }

   private static String appendKeybindText(String var0, Module var1) {
      return showModuleInfo != null && showModuleInfo.isToggled() && bind != null && bind.isToggled() && var1.getKeycode() != 0 ? var0 + " §7(" + KeySetting.SBJv(var1.getKeycode()) + ")" : var0;
   }

   public static int getModuleDisplayWidth(Module var0) {
      String var1 = getModuleDisplayText(var0);
      return FAEgbj().getStringWidth(var1 == null ? "" : var1);
   }

   public static String getFontName() {
      return font == null ? FontManager.getDefaultHudFontName() : font.getResolvedFontName();
   }

   public static boolean isAlphabeticalSort() {
      return sort != null && (int)sort.getInput() == 1;
   }

   @Override
   public Comparator<Module> createModuleComparator() {
      return isAlphabeticalSort() ? Comparator.comparing(Module::getNameInHud) : new Comparator<Module>() {
         public int compare(Module var1, Module var2) {
            return Arraylist.getModuleDisplayWidth(var2) - Arraylist.getModuleDisplayWidth(var1);
         }
      };
   }

   public static boolean isRightAligned() {
      ScaledResolution var0 = new ScaledResolution(mc);
      return anchorX >= var0.getScaledWidth() * 0.5F;
   }

   public static float getScaleFactor() {
      return scale == null ? 1.0F : (float)scale.getInput();
   }

   public SliderSetting getScale() {
      return scale;
   }

   public static float getAnchorXRatio() {
      clampAnchor();
      return fXccSz;
   }

   public static float IRZw() {
      clampAnchor();
      return anchorYRatio;
   }

   public static void setAnchorRatios(float var0, float var1) {
      fXccSz = var0;
      anchorYRatio = var1;
      clampAnchor();
   }

   public static void setAbsolutePosition(float var0, float var1) {
      applyAbsolutePosition(var0, var1, new ScaledResolution(mc));
   }

   public static float[] renderPreviewBounds(float var0, float var1) {
      ScaledResolution var2 = new ScaledResolution(mc);
      float var3 = anchorX;
      float var4 = riwe;
      float var5 = fXccSz;
      float var6 = anchorYRatio;
      applyAbsolutePosition(var0, var1, var2);
      IFont var7 = FAEgbj();
      int var8 = computeBackgroundPaddingTop();
      int var9 = computeBackgroundPaddingBottom();
      int var10 = computeRowHeight(var7.getTextTopOffset(), var7.getTextBottomOffset(), var8, var9);
      float var11 = var0;
      float var12 = var1;
      float var13 = var0;
      float var14 = var1 + var10;
      if (Jade.getModuleManager().getSortedModules().isEmpty()) {
         String[] var15 = new String[]{"Jade", "Example", "Arraylist"};
         float var16 = var1;

         for (String var20 : var15) {
            float var21 = var0;
            if (isRightAligned()) {
               var21 = var0 + (var7.getStringWidth(var15[1]) - var7.getStringWidth(var20));
            }

            drawModuleText(var7, var20, var21, ixd1(var16, var7.getTextTopOffset(), var8), Color.white.getRGB());
            var13 = Math.max(var13, var21 + var7.getStringWidth(var20));
            var16 += var10;
         }

         var14 = var16;
      } else {
         float var53 = var1;
         double var55 = 0.0;
         int var56 = var7.getTextTopOffset();
         int var57 = var7.getTextBottomOffset();
         int var58 = computeBackgroundPaddingX();
         int var59 = computeBackgroundPaddingTop();
         int var22 = computeBackgroundPaddingBottom();
         int var23 = computeOutlineThickness();
         int var24 = computeRowHeight(var56, var57, var59, var22);
         ArrayList var25 = new ArrayList();
         boolean var26 = false;

         for (Module var28 : Jade.getModuleManager().getSortedModules()) {
            if (var28.isEnabled() && !(var28 instanceof Arraylist) && !isModuleHiddenInHud(var28, false)) {
               String var29 = getModuleDisplayText(var28);
               int var30 = var7.getStringWidth(var29);
               float var31 = anchorX;
               float var32 = ixd1(var53, var56, var59);
               double var33 = var31 - var58;
               double var35 = var31 + var30 + var58;
               double var37 = var53;
               double var39 = var53 + var24;
               double var41 = var33 - var23;
               double var43 = var35 + var23;
               double var45 = var37 - var23;
               if (isRightAligned()) {
                  var31 -= var30;
                  var33 = var31 - var58;
                  var35 = var31 + var30 + var58;
                  var41 = var33 - var23;
                  var43 = var35 + var23;
               }

               int var47 = xQec0(CDp1(var55, (var33 + var35) * 0.5));
               Arraylist$4 var48 = new Arraylist$4(var29, var30, var31, var32, var33, var37, var35, var39, var41, var45, var43, var47, 1.0F);
               var25.add(var48);
               float var49 = EVFY(var48);
               float var50 = computeRowOuterTop(var48, var56);
               float var51 = BjeBaw(var48);
               float var52 = computeRowOuterBottom(var48, var57);
               if (!var26) {
                  var11 = var49;
                  var12 = var50;
                  var13 = var51;
                  var14 = var52;
                  var26 = true;
               } else {
                  var11 = Math.min(var11, var49);
                  var12 = Math.min(var12, var50);
                  var13 = Math.max(var13, var51);
                  var14 = Math.max(var14, var52);
               }

               if (KyZe()) {
                  var55 += MSJgFh4();
               }

               var53 += var24;
            }
         }

         if (var25.isEmpty()) {
            String[] var60 = new String[]{"Jade", "Example", "Arraylist"};
            var53 = var1;

            for (String var64 : var60) {
               drawModuleText(var7, var64, var0, ixd1(var53, var56, var59), Color.white.getRGB());
               var13 = Math.max(var13, var0 + var7.getStringWidth(var64));
               var53 += var24;
            }

            var14 = var53;
         } else {
            rwqDwx(var25, var7, var23, var58, false);
         }
      }

      anchorX = var3;
      riwe = var4;
      fXccSz = var5;
      anchorYRatio = var6;
      return new float[]{var11, var12, var13, var14};
   }

   public static void resetPosition() {
      resetAnchorToDefault(new ScaledResolution(mc));
   }

   private static void clampAnchor() {
      clampAnchorToScreen(new ScaledResolution(mc));
   }

   private static void clampAnchorToScreen(ScaledResolution var0) {
      int var1 = Math.max(1, var0.getScaledWidth());
      int var2 = Math.max(1, var0.getScaledHeight());
      if (Float.isNaN(fXccSz) || Float.isNaN(anchorYRatio)) {
         fXccSz = anchorX / var1;
         anchorYRatio = riwe / var2;
      }

      anchorX = fXccSz * var1;
      riwe = anchorYRatio * var2;
   }

   private static void applyAbsolutePosition(float var0, float var1, ScaledResolution var2) {
      anchorX = var0;
      riwe = var1;
      int var3 = Math.max(1, var2.getScaledWidth());
      int var4 = Math.max(1, var2.getScaledHeight());
      fXccSz = var0 / var3;
      anchorYRatio = var1 / var4;
   }

   private static void resetAnchorToDefault(ScaledResolution var0) {
      setAnchorRatios(0.985F, 0.02F);
   }

   private static int computeBackgroundPaddingX() {
      double var0 = backgroundPaddingX == null ? 2.0 : backgroundPaddingX.getInput();
      return Math.max(0, Math.round((float)var0 * getScaleFactor()));
   }

   private static int computeBackgroundPaddingTop() {
      int var0 = computeBackgroundPaddingY();
      return (var0 + 1) / 2;
   }

   private static int computeBackgroundPaddingBottom() {
      return computeBackgroundPaddingY() / 2;
   }

   private static int computeOutlineThickness() {
      return scaleInt(1.0F);
   }

   private static int computeRowHeight(int var0, int var1, int var2, int var3) {
      int var4 = Math.max(1, var1 - var0);
      return Math.max(1, var4 + var2 + var3);
   }

   private static float ixd1(float var0, int var1, int var2) {
      return var0 + var2 - var1;
   }

   private static int scaleInt(float var0) {
      return Math.max(1, Math.round(var0 * getScaleFactor()));
   }

   private static int computeBackgroundPaddingY() {
      double var0 = backgroundPaddingY == null ? 2.0 : backgroundPaddingY.getInput();
      return Math.max(0, Math.round((float)var0 * getScaleFactor()));
   }

   private static float getCornerRadius() {
      return backgroundRounding == null ? scaleInt(3.0F) : Math.max(0.0F, (float)backgroundRounding.getInput() * getScaleFactor());
   }

   private static int zAzr() {
      double var0 = backgroundOpacity == null ? 0.45 : backgroundOpacity.getInput();
      int var2 = Math.max(0, Math.min(255, (int)Math.round(var0 * 255.0)));
      return ClientUtils.YVVZ(BACKGROUND_BASE_COLOR, var2);
   }

   private static void rwqDwx(List<Arraylist$4> var0, IFont var1, int var2, int var3, boolean var4) {
      if (!var0.isEmpty()) {
         try {
            float[] var5 = new float[var0.size()];
            float[] var6 = new float[var0.size()];
            var5[0] = (float)Arraylist$4.NsfF((Arraylist$4)var0.get(0));

            for (int var7 = 1; var7 < var0.size(); var7++) {
               Arraylist$4 var8 = (Arraylist$4)var0.get(var7 - 1);
               Arraylist$4 var9 = (Arraylist$4)var0.get(var7);
               float var10 = (float)((Arraylist$4.getOuterBottom(var8) + Arraylist$4.NsfF(var9)) * 0.5);
               var6[var7 - 1] = var10;
               var5[var7] = var10;
            }

            var6[var0.size() - 1] = (float)Arraylist$4.getOuterBottom((Arraylist$4)var0.get(var0.size() - 1));
            int var16 = getOutlineMode();
            if (isBackgroundEnabled() && isShadowEnabled()) {
               LgKhsP(var0, var5, var6);
            }

            for (int var17 = 0; var17 < var0.size(); var17++) {
               Arraylist$4 var19 = (Arraylist$4)var0.get(var17);
               Arraylist$4 var21 = var17 > 0 ? (Arraylist$4)var0.get(var17 - 1) : null;
               Arraylist$4 var11 = var17 + 1 < var0.size() ? (Arraylist$4)var0.get(var17 + 1) : null;
               if (isBackgroundEnabled()) {
                  int var12 = var4 ? rdYl(zAzr(), Arraylist$4.getFadeProgress(var19)) : zAzr();
                  drawRowBackgroundSimple(var19, var21, var11, getCornerRadius(), var12, var5[var17], var6[var17]);
               }

               if (var16 == 1) {
                  GJHt(var19, var5[var17], var6[var17]);
               } else if (var16 == 2) {
                  drawOutlineBar(var19, var5[var17], var6[var17], var2);
               }
            }

            if (var16 == 3) {
               drawFullOutline(var0, var5, var6, var2);
            }

            for (Arraylist$4 var20 : var0) {
               drawModuleText(var1, Arraylist$4.getFormattedText(var20), Arraylist$4.getTextX(var20), Arraylist$4.getTextY(var20), Arraylist$4.getColor(var20));
            }
         } finally {
            resetGlState();
         }
      }
   }

   private static void LgKhsP(final List<Arraylist$4> var0, final float[] var1, final float[] var2) {
      PostProcessing.runWithBloomMasked(new Runnable() {
         @Override
         public void run() {
            Arraylist.STJbZe(var0, var1, var2);
         }
      }, 3, getShadowRadius(), true);
      resetGlState();
   }

   private static void drawRowShadows(List<Arraylist$4> var0, float[] var1, float[] var2) {
      for (int var3 = 0; var3 < var0.size(); var3++) {
         Arraylist$4 var4 = (Arraylist$4)var0.get(var3);
         Arraylist$4 var5 = var3 > 0 ? (Arraylist$4)var0.get(var3 - 1) : null;
         Arraylist$4 var6 = var3 + 1 < var0.size() ? (Arraylist$4)var0.get(var3 + 1) : null;
         int var7 = ClientUtils.YVVZ(-16777216, Math.round(SRes738() * Arraylist$4.getFadeProgress(var4) * 255.0F));
         drawRowBackgroundSimple(var4, var5, var6, getCornerRadius(), var7, var1[var3], var2[var3]);
      }
   }

   private static int withAlpha(int var0, int var1) {
      return ClientUtils.YVVZ(var0, Math.max(0, Math.min(255, var1)));
   }

   private static float getShadowRadius() {
      return shadowRadius == null ? 3.5F : Math.max(1.0F, (float)shadowRadius.getInput());
   }

   private static float SRes738() {
      return shadowOpacity == null ? 1.0F : Math.max(0.0F, Math.min(1.0F, (float)shadowOpacity.getInput()));
   }

   private static void drawEntryLabelWithAlpha(IFont var0, String var1, float var2, float var3, int var4, boolean var5, int var6) {
      if (!isGradientEnabled()) {
         var0.drawString(var1, var2, var3, withAlpha(var4, var6), var5);
      } else {
         var0.drawGlyphString(var1, var2, var3, (recoveredArg0, recoveredArg1, recoveredArg2, recoveredArg3) -> Arraylist.resolveGradientGlyphColorWithAlpha(var6, var2, recoveredArg0, recoveredArg1, recoveredArg2, (java.lang.Integer) recoveredArg3), var5);
      }
   }

   private static boolean isShadowEnabled() {
      return shadow != null && shadow.isToggled();
   }

   private static void resetGlState() {
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2896);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private static void drawFullOutline(List<Arraylist$4> var0, float[] var1, float[] var2, int var3) {
      if (!var0.isEmpty()) {
         float var4 = 0.0F;

         for (Arraylist$4 var6 : var0) {
            var4 += Arraylist$4.getFadeProgress(var6);
         }

         var4 = Math.max(0.0F, Math.min(1.0F, var4 / var0.size()));
         float var17 = Math.max(1.0F, (float)var3);
         float[] var18 = new float[var0.size()];
         float[] var7 = new float[var0.size()];

         for (int var8 = 0; var8 < var0.size(); var8++) {
            Arraylist$4 var9 = (Arraylist$4)var0.get(var8);
            var18[var8] = (float)Arraylist$4.kcAox(var9);
            var7[var8] = (float)Arraylist$4.getBoundsRight(var9);
         }

         GlStateManager.pushMatrix();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
         Tessellator var19 = Tessellator.getInstance();
         WorldRenderer var20 = var19.getWorldRenderer();
         var20.begin(7, DefaultVertexFormats.POSITION_COLOR);

         for (int var10 = 0; var10 < var0.size(); var10++) {
            float var11 = var1[var10];
            float var12 = var2[var10];
            emitOutlineQuad(var20, var18[var10] - var17, var11, var18[var10], var12, var4);
            emitOutlineQuad(var20, var7[var10], var11, var7[var10] + var17, var12, var4);
            if (var10 == 0) {
               emitOutlineQuad(var20, var18[var10] - var17, var11 - var17, var7[var10] + var17, var11, var4);
            }

            if (var10 == var0.size() - 1) {
               emitOutlineQuad(var20, var18[var10] - var17, var12, var7[var10] + var17, var12 + var17, var4);
            }

            if (var10 + 1 < var0.size()) {
               float var13 = var18[var10 + 1];
               float var14 = var7[var10 + 1];
               if (var13 < var18[var10]) {
                  emitOutlineQuad(var20, var13 - var17, var12, var18[var10], var12 + var17, var4);
               } else if (var13 > var18[var10]) {
                  emitOutlineQuad(var20, var18[var10] - var17, var12 - var17, var13, var12, var4);
               }

               if (var14 > var7[var10]) {
                  emitOutlineQuad(var20, var7[var10], var12, var14 + var17, var12 + var17, var4);
               } else if (var14 < var7[var10]) {
                  emitOutlineQuad(var20, var14, var12 - var17, var7[var10] + var17, var12, var4);
               }
            }
         }

         var19.draw();
         GlStateManager.shadeModel(7424);
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }
   }

   private static void emitOutlineQuad(WorldRenderer var0, float var1, float var2, float var3, float var4, float var5) {
      if (!(var3 <= var1) && !(var4 <= var2)) {
         PJgW(var0, var1, var4, var5);
         PJgW(var0, var3, var4, var5);
         PJgW(var0, var3, var2, var5);
         PJgW(var0, var1, var2, var5);
      }
   }

   private static void PJgW(WorldRenderer var0, float var1, float var2, float var3) {
      int var4 = resolveOutlineVertexColor(var1, var2, var3);
      var0.pos(var1, var2, 0.0).color((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24) / 255.0F).endVertex();
   }

   private static int resolveOutlineVertexColor(float var0, float var1, float var2) {
      double var3 = KyZe() ? var1 * (Math.abs(MSJgFh4()) / 12.0) * getWaveVerticalSign() : CDp1(0.0, var0);
      return rdYl(xQec0(var3), var2);
   }

   private static List<Arraylist$5> buildOutlinePath(List<Arraylist$4> var0, float[] var1, float[] var2, int var3) {
      ArrayList var4 = new ArrayList();
      int var5 = var0.size();
      float var6 = 0.0F;
      float var7 = isBackgroundEnabled() ? getCornerRadius() : 0.0F;
      boolean var8 = var7 > 0.0F && roundAlignedSide != null && roundAlignedSide.isToggled();
      boolean var9 = isRightAligned();
      float[] var10 = new float[var5];
      float[] var11 = new float[var5];
      float[] var12 = new float[var5];
      float[] var13 = new float[var5];

      for (int var14 = 0; var14 < var5; var14++) {
         Arraylist$4 var15 = (Arraylist$4)var0.get(var14);
         var10[var14] = (float)Arraylist$4.kcAox(var15) - var6;
         var11[var14] = (float)Arraylist$4.getBoundsRight(var15) + var6;
         var12[var14] = var1[var14] - (var14 == 0 ? var6 : 0.0F);
         var13[var14] = var2[var14] + (var14 == var5 - 1 ? var6 : 0.0F);
      }

      boolean var25 = XwzEabX(var12[0]);
      boolean var26 = !var25 && (!var9 && var8 || var9 && isWiderThanNeighbour((Arraylist$4)var0.get(0), null));
      boolean var16 = !var25 && (var9 && var8 || !var9 && isWiderThanNeighbour((Arraylist$4)var0.get(0), null));
      boolean var17 = var9 && var8 || !var9 && isWiderThanNeighbour((Arraylist$4)var0.get(var5 - 1), null);
      boolean var18 = !var9 && var8 || var9 && isWiderThanNeighbour((Arraylist$4)var0.get(var5 - 1), null);
      float var19 = clampCornerRadius(var7, var10[0], var12[0], var11[0], var13[0], var26);
      float var20 = clampCornerRadius(var7, var10[0], var12[0], var11[0], var13[0], var16);
      float var21 = clampCornerRadius(var7, var10[var5 - 1], var12[var5 - 1], var11[var5 - 1], var13[var5 - 1], var17);
      float var22 = clampCornerRadius(var7, var10[var5 - 1], var12[var5 - 1], var11[var5 - 1], var13[var5 - 1], var18);
      WYUDUT(var4, var10[0] + var19, var12[0]);
      WYUDUT(var4, var11[0] - var20, var12[0]);
      appendArcPoints(var4, var11[0] - var20, var12[0] + var20, var20, -90.0, 0.0);

      for (int var23 = 0; var23 < var5; var23++) {
         float var24 = var23 == var5 - 1 ? var21 : 0.0F;
         WYUDUT(var4, var11[var23], var13[var23] - var24);
         if (var23 + 1 < var5) {
            WYUDUT(var4, var11[var23], var13[var23]);
            WYUDUT(var4, var11[var23 + 1], var13[var23]);
         }
      }

      appendArcPoints(var4, var11[var5 - 1] - var21, var13[var5 - 1] - var21, var21, 0.0, 90.0);
      WYUDUT(var4, var10[var5 - 1] + var22, var13[var5 - 1]);
      appendArcPoints(var4, var10[var5 - 1] + var22, var13[var5 - 1] - var22, var22, 90.0, 180.0);

      for (int var27 = var5 - 1; var27 >= 0; var27--) {
         float var28 = var27 == 0 ? var19 : 0.0F;
         WYUDUT(var4, var10[var27], var12[var27] + var28);
         if (var27 > 0) {
            WYUDUT(var4, var10[var27], var12[var27]);
            WYUDUT(var4, var10[var27 - 1], var12[var27]);
         }
      }

      appendArcPoints(var4, var10[0] + var19, var12[0] + var19, var19, 180.0, 270.0);
      WYUDUT(var4, var10[0] + var19, var12[0]);
      return var4;
   }

   private static float clampCornerRadius(float var0, float var1, float var2, float var3, float var4, boolean var5) {
      return !var5 ? 0.0F : Math.max(0.0F, Math.min(var0, Math.min(var3 - var1, var4 - var2) * 0.5F));
   }

   private static void appendArcPoints(List<Arraylist$5> var0, float var1, float var2, float var3, double var4, double var6) {
      if (!(var3 <= 0.0F)) {
         int var8 = Math.max(4, Math.round(var3 * 2.0F));

         for (int var9 = 1; var9 <= var8; var9++) {
            double var10 = (double)var9 / var8;
            double var12 = Math.toRadians(var4 + (var6 - var4) * var10);
            WYUDUT(var0, var1 + (float)Math.cos(var12) * var3, var2 + (float)Math.sin(var12) * var3);
         }
      }
   }

   private static void WYUDUT(List<Arraylist$5> var0, float var1, float var2) {
      if (!var0.isEmpty()) {
         Arraylist$5 var3 = (Arraylist$5)var0.get(var0.size() - 1);
         if (Math.abs(Arraylist$5.getPointX(var3) - var1) < 0.01F && Math.abs(Arraylist$5.getPointY(var3) - var2) < 0.01F) {
            return;
         }
      }

      var0.add(new Arraylist$5(var1, var2));
   }

   private static void GJHt(Arraylist$4 var0, float var1, float var2) {
      if (isRightAligned()) {
         RenderUtils.XNRNki(Arraylist$4.getBoundsRight(var0), var1, Arraylist$4.getOuterRight(var0), var2, Arraylist$4.getColor(var0));
      } else {
         RenderUtils.XNRNki(Arraylist$4.getOuterLeft(var0), var1, Arraylist$4.kcAox(var0), var2, Arraylist$4.getColor(var0));
      }
   }

   private static void drawOutlineBar(Arraylist$4 var0, float var1, float var2, int var3) {
      double var4 = isRightAligned() ? Arraylist$4.getBoundsRight(var0) : Arraylist$4.getOuterLeft(var0);
      double var6 = isRightAligned() ? Arraylist$4.getOuterRight(var0) : Arraylist$4.kcAox(var0);
      double var8 = (var1 + var2) * 0.5;
      double var10 = Math.max(2.0, var3 * 2.5);
      drawVerticalSegment(var4, var8 - var10, var6, var8 + var10, var1, var2, Arraylist$4.getColor(var0));
   }

   private static void drawVerticalSegment(double var0, double var2, double var4, double var6, double var8, double var10, int var12) {
      double var13 = Math.max(var2, var8);
      double var15 = Math.min(var6, var10);
      if (var15 > var13) {
         RenderUtils.XNRNki(var0, var13, var4, var15, var12);
      }
   }

   private static void drawRowBackgroundSimple(Arraylist$4 var0, Arraylist$4 var1, Arraylist$4 var2, float var3, int var4, float var5, float var6) {
      drawRowBackground(var0, var1, var2, var3, var4, var5, var6, null, 1.0F);
   }

   private static void drawRowBackground(Arraylist$4 var0, Arraylist$4 var1, Arraylist$4 var2, float var3, int var4, float var5, float var6, ExternalRenderBuffer var7, float var8) {
      float var9 = (float)Arraylist$4.kcAox(var0);
      float var10 = (float)Arraylist$4.getBoundsRight(var0);
      boolean var13 = isRightAligned();
      int var14 = getOutlineMode();
      if (var14 == 1 || var14 == 2) {
         if (var13) {
            var10 = (float)Arraylist$4.getOuterRight(var0);
         } else {
            var9 = (float)Arraylist$4.getOuterLeft(var0);
         }
      }

      boolean var15 = roundAlignedSide != null && roundAlignedSide.isToggled();
      boolean var16 = isWiderThanNeighbour(var0, var1);
      boolean var17 = isWiderThanNeighbour(var0, var2);
      boolean var18 = var1 == null && XwzEabX(var5);
      if (var18) {
         var16 = false;
      }

      boolean var19 = var15 && var1 == null && !var18;
      boolean var20 = var15 && var2 == null;
      boolean var21 = var13 ? var16 : var19;
      boolean var22 = var13 ? var17 : var20;
      boolean var23 = var13 ? var19 : var16;
      boolean var24 = var13 ? var20 : var17;
      float var25 = var10 - var9;
      float var26 = var6 - var5;
      float var27 = Math.max(0.0F, Math.min(var3, Math.min(var25, var26) * 0.5F));
      if (var27 <= 0.0F) {
         if (var7 == null) {
            RenderUtils.XNRNki(var9, var5, var10, var6, var4);
         } else {
            var7.fillRoundedRect(var9 * var8, var5 * var8, var10 * var8, var6 * var8, var4, 0.0F);
         }
      } else {
         float var28 = lQgdXj(var0, var1, var27);
         float var29 = lQgdXj(var0, var2, var27);
         float var30 = var21 ? (var13 ? var28 : var27) : 0.0F;
         float var31 = var22 ? (var13 ? var29 : var27) : 0.0F;
         float var32 = var23 ? (var13 ? var27 : var28) : 0.0F;
         float var33 = var24 ? (var13 ? var27 : var29) : 0.0F;
         if (var7 != null) {
            var7.fillPerCornerRoundedRect(var9 * var8, var5 * var8, var10 * var8, var6 * var8, var4, var30 * var8, var32 * var8, var33 * var8, var31 * var8);
         } else {
            HlYh(var9, var5, var10, var6, var4, var30, var32, var33, var31, var1 != null, var2 != null);
         }
      }
   }

   private static boolean isWiderThanNeighbour(Arraylist$4 var0, Arraylist$4 var1) {
      return var1 == null ? true : Arraylist$4.getTextWidth(var0) >= Arraylist$4.getTextWidth(var1) + 0.5;
   }

   private static float lQgdXj(Arraylist$4 var0, Arraylist$4 var1, float var2) {
      if (var1 == null) {
         return var2;
      } else {
         float var3 = Math.abs(Arraylist$4.getTextWidth(var0) - Arraylist$4.getTextWidth(var1));
         float var4 = Math.max(1.0F, 2.0F * getScaleFactor());
         if (var3 >= var4) {
            return var2;
         } else {
            float var5 = Math.max(0.0F, var3 / var4);
            return var2 * (0.75F + 0.25F * var5);
         }
      }
   }

   private static boolean XwzEabX(float var0) {
      float var1 = Math.max(1.0F, getScaleFactor());
      return var0 <= var1 || riwe <= var1;
   }

   private static void HlYh(
      float var0, float var1, float var2, float var3, int var4, float var5, float var6, float var7, float var8, boolean var9, boolean var10
   ) {
      if (!(var2 <= var0) && !(var3 <= var1)) {
         float var11 = Math.max(Math.max(var5, var6), Math.max(var7, var8));
         if (var11 <= 0.0F) {
            RenderUtils.XNRNki(var0, var1, var2, var3, var4);
         } else {
            RoundedRect.drawRoundedRectPerCornerRadii(var0, var1, var2 - var0, var3 - var1, var4, var5, var6, var7, var8, var9, var10);
         }
      }
   }

   private static boolean isBackgroundEnabled() {
      return drawBackground != null && drawBackground.isToggled();
   }

   private static int getOutlineMode() {
      return outline == null ? 0 : (int)outline.getInput();
   }

   private static float EVFY(Arraylist$4 var0) {
      float var1 = Arraylist$4.getTextX(var0);
      if (isBackgroundEnabled()) {
         var1 = Math.min(var1, (float)Arraylist$4.kcAox(var0));
      }

      int var2 = getOutlineMode();
      if ((var2 == 1 || var2 == 2) && !isRightAligned()) {
         var1 = Math.min(var1, (float)Arraylist$4.getOuterLeft(var0));
      } else if (var2 == 3) {
         var1 = Math.min(var1, (float)Arraylist$4.kcAox(var0) - computeOutlineThickness());
      }

      return var1;
   }

   private static float BjeBaw(Arraylist$4 var0) {
      float var1 = Arraylist$4.getTextX(var0) + Arraylist$4.getTextWidth(var0);
      if (isBackgroundEnabled()) {
         var1 = Math.max(var1, (float)Arraylist$4.getBoundsRight(var0));
      }

      int var2 = getOutlineMode();
      if ((var2 == 1 || var2 == 2) && isRightAligned()) {
         var1 = Math.max(var1, (float)Arraylist$4.getOuterRight(var0));
      } else if (var2 == 3) {
         var1 = Math.max(var1, (float)Arraylist$4.getBoundsRight(var0) + computeOutlineThickness());
      }

      return var1;
   }

   private static float computeRowOuterTop(Arraylist$4 var0, int var1) {
      int var2 = getOutlineMode();
      if (var2 == 3) {
         return (float)Arraylist$4.NsfF(var0) - computeOutlineThickness();
      } else {
         return !isBackgroundEnabled() && var2 <= 0 ? Arraylist$4.getTextY(var0) + var1 : (float)Arraylist$4.NsfF(var0);
      }
   }

   private static float computeRowOuterBottom(Arraylist$4 var0, int var1) {
      int var2 = getOutlineMode();
      if (var2 == 3) {
         return (float)Arraylist$4.getOuterBottom(var0) + computeOutlineThickness();
      } else {
         return !isBackgroundEnabled() && var2 <= 0 ? Arraylist$4.getTextY(var0) + var1 : (float)Arraylist$4.getOuterBottom(var0);
      }
   }

   private static boolean luk3() {
      return textShadow == null || textShadow.isToggled();
   }

   private static boolean KyZe() {
      return waveAxis == null || (int)waveAxis.getInput() == 0;
   }

   private static double CDp1(double var0, double var2) {
      return KyZe() ? var0 : var2 * (0.35 / getWaveLength()) * Swy9();
   }

   private static void drawModuleText(IFont var0, String var1, float var2, float var3, int var4) {
      drawModuleTextWithShadow(var0, var1, var2, var3, var4, luk3());
   }

   private static void drawModuleTextWithShadow(IFont var0, String var1, float var2, float var3, int var4, boolean var5) {
      if (!isGradientEnabled()) {
         var0.drawString(var1, var2, var3, var4, var5);
      } else {
         var0.drawGlyphString(var1, var2, var3, (recoveredArg0, recoveredArg1, recoveredArg2, recoveredArg3) -> Arraylist.resolveGradientGlyphColor(var2, recoveredArg0, recoveredArg1, recoveredArg2, (java.lang.Integer) recoveredArg3), var5);
      }
   }

   private static boolean isGradientEnabled() {
      return AOGM.size() > 1 && !KyZe();
   }

   private static double MSJgFh4() {
      return 12.0 / getWaveLength() * getWaveVerticalSign();
   }

   private static int getWaveVerticalSign() {
      return waveDirection != null && (int)waveDirection.getInput() != 0 ? 1 : -1;
   }

   private static int Swy9() {
      return waveDirection2 != null && (int)waveDirection2.getInput() != 0 ? 1 : -1;
   }

   public static int xQec0(double var0) {
      if (AOGM.size() > 1) {
         return sampleMultiColorGradient(var0);
      } else {
         return AOGM.isEmpty() ? -12326778 : AOGM.get(0);
      }
   }

   public static void setGradientColors(List<Integer> var0) {
      AOGM = (List<Integer>)(var0 == null ? Collections.emptyList() : new ArrayList<>(var0));
   }

   public static List<Integer> getGradientColors() {
      return new ArrayList<>(AOGM);
   }

   private static int sampleMultiColorGradient(double var0) {
      int var2 = AOGM.size() - 1;
      double var3 = (double)System.currentTimeMillis() / (7500L * var2) * YUzc() + var0 * 0.12 / ((Math.PI * 2) * var2);
      var3 -= Math.floor(var3);
      double var5 = var3 * var2 * 2.0;
      double var7 = var5 <= var2 ? var5 : var2 * 2.0 - var5;
      int var9 = Math.min(var2 - 1, (int)Math.floor(var7));
      double var10 = var7 - var9;
      Color var12 = new Color(AOGM.get(var9), true);
      Color var13 = new Color(AOGM.get(var9 + 1), true);
      return ColorTheme.convert(var13, var12, var10).getRGB();
   }

   private static double YUzc() {
      return waveSpeed == null ? 1.0 : Math.max(0.1, waveSpeed.getInput());
   }

   private static double getWaveLength() {
      return waveLength == null ? 1.0 : Math.max(0.5, waveLength.getInput());
   }

   private static int resolveGradientGlyphColor(float var0, char var1, float var2, float var3, Integer var4) {
      return var4 != null ? var4 : xQec0(CDp1(0.0, var0 + var2 + var3 * 0.5F));
   }

   private static int resolveGradientGlyphColorWithAlpha(int var0, float var1, char var2, float var3, float var4, Integer var5) {
      return var5 != null ? withAlpha(var5, var0) : withAlpha(xQec0(CDp1(0.0, var1 + var3 + var4 * 0.5F)), var0);
   }

   private static double getEntryRowY(Entry var0) {
      return Arraylist$3.getRowY((Arraylist$3)var0.getValue());
   }

   public static void NgJh(ScaledResolution var0) {
      clampAnchorToScreen(var0);
   }

   public static void setAbsolutePositionForResolution(float var0, float var1, ScaledResolution var2) {
      applyAbsolutePosition(var0, var1, var2);
   }

   public static int getBackgroundPaddingTop() {
      return computeBackgroundPaddingTop();
   }

   public static int getBackgroundPaddingBottom() {
      return computeBackgroundPaddingBottom();
   }

   public static int getRowHeight(int var0, int var1, int var2, int var3) {
      return computeRowHeight(var0, var1, var2, var3);
   }

   public static float aKzlE(float var0, int var1, int var2) {
      return ixd1(var0, var1, var2);
   }

   public static void drawEntryLabel(IFont var0, String var1, float var2, float var3, int var4) {
      drawModuleText(var0, var1, var2, var3, var4);
   }

   public static int getBackgroundPaddingX() {
      return computeBackgroundPaddingX();
   }

   public static int getOutlineThickness() {
      return computeOutlineThickness();
   }

   public static boolean MZb9(Module var0, boolean var1) {
      return isModuleHiddenInHud(var0, var1);
   }

   public static double getWaveOffset(double var0, double var2) {
      return CDp1(var0, var2);
   }

   public static boolean isWaveAxisVertical() {
      return KyZe();
   }

   public static double getWaveStep() {
      return MSJgFh4();
   }

   public static void HBQY(List var0, IFont var1, int var2, int var3, boolean var4) {
      rwqDwx(var0, var1, var2, var3, var4);
   }

   public static void resetAnchor(ScaledResolution var0) {
      resetAnchorToDefault(var0);
   }

   public static void STJbZe(List var0, float[] var1, float[] var2) {
      drawRowShadows(var0, var1, var2);
   }
}
