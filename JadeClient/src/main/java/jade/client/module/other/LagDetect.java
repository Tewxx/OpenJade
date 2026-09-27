// Jade recovery: module: Lag Detect (other); original class: jade.deps.eLz.SX7ueyGvIn
package jade.client.module.other;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.XorShiftMultiplyConstantCipherTwo;
import jade.deps.loader107.MurmurFinalizerConstantCipherThree;
import jade.deps.loader107.XorShiftMultiplyConstantCipherEight;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D.Float;
import java.awt.image.BufferedImage;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(aliases = "Latency Alerts")
public class LagDetect extends Module implements ExternalRenderableModule {
   private static final int ICON_TEXTURE_SIZE = 256;
   private static final int WaE = -45229;
   private static final int ALERT_TEXT_COLOR = -41374;
   private static final int htlX = -1610612736;
   private static final float DEFAULT_FONT_SCALE = 1.0F;
   private static final float DEFAULT_X_FRACTION = 0.5F;
   private static final float DEFAULT_Y_FRACTION = 0.08F;
   private static final long GyhR = 100L;
   private SliderSetting highLatency;
   private FontSetting font;
   private SliderSetting fontScale;
   private BooleanSetting ignoreLimbo;
   private static float ANRCZ = 0.5F;
   private static float positionYFraction = 0.08F;
   private static float alertX;
   private static float alertY;
   private static ResourceLocation resourceLocation;
   private static boolean AzjytO = true;
   private long lastPacketTimeMillis = MurmurFinalizerConstantCipherThree.decodeLong(5395950082397092512L, 1551538233);
   private long jfQ = XorShiftMultiplyConstantCipherEight.decodeLong(794638199330197277L, -1570087729);
   private long stallStartMillis = 0L;
   private long Znb = 0L;
   private long lastDurationRefreshMillis = XorShiftMultiplyConstantCipherTwo.zybiziv(-887053621636435356L, -962220247);

   public LagDetect() {
      super("Lag Detect", Category.other);
      this.registerSetting(new DescriptionSetting("Detects packet loss."));
      this.registerSetting(
         this.highLatency = new SliderSetting(
            "High latency", " second", 0.5, 0.1, 5.0, 0.1
         )
      );
      this.registerSetting(this.font = new FontSetting("Font", "Modern"));
      this.registerSetting(this.fontScale = new SliderSetting("Font scale", 1.0, 0.5, 2.0, 0.1));
      this.fontScale.visible = false;
      this.registerSetting(
         this.ignoreLimbo = new BooleanSetting(
            "Ignore limbo", true
         )
      );
      this.initialized = true;
   }

   public SliderSetting getFontScale() {
      return this.fontScale;
   }

   @Override
   public void onDisable() {
      this.lastPacketTimeMillis = 0L;
      this.resetLagState();
      ExternalRenderer.invalidateExternalFrame();
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      long var2 = System.currentTimeMillis();
      if (this.jfQ > 0L && var2 - this.lastPacketTimeMillis < this.QqQn()) {
         this.stallStartMillis = this.stallStartMillis == 0L ? var2 : this.stallStartMillis;
      } else {
         this.stallStartMillis = 0L;
      }

      this.lastPacketTimeMillis = System.currentTimeMillis();
   }

   @Override
   public void onUpdate() {
      if (!ClientUtils.isInWorld()) {
         this.lastPacketTimeMillis = System.currentTimeMillis();
         this.resetLagState();
      } else {
         if (mc.isSingleplayer() || this.ignoreLimbo.isToggled() && this.isInLimbo()) {
            this.lastPacketTimeMillis = System.currentTimeMillis();
            this.resetLagState();
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && !mc.gameSettings.showDebugInfo && ClientUtils.isInWorld()) {
         if (!mc.isSingleplayer() && (!this.ignoreLimbo.isToggled() || !this.isInLimbo())) {
            long var2 = System.currentTimeMillis();
            long var4 = var2 - this.lastPacketTimeMillis;
            long var6 = this.QqQn();
            if (var4 >= var6 && this.jfQ == 0L) {
               this.jfQ = this.lastPacketTimeMillis;
               this.stallStartMillis = 0L;
            }

            if (this.stallStartMillis > 0L && var2 - this.stallStartMillis >= var6) {
               this.resetLagState();
            }

            if (this.jfQ != 0L) {
               long var8 = var2 - this.jfQ;
               if (this.lastDurationRefreshMillis == 0L || var2 - this.lastDurationRefreshMillis >= 100L) {
                  this.Znb = roundUpToDisplayStep(var8);
                  this.lastDurationRefreshMillis = var2;
               }

               ScaledResolution var10 = new ScaledResolution(mc);
               GKEA(var10);
               long var11 = this.Znb > 0L ? this.Znb : roundUpToDisplayStep(var8);
               if (this.VIuQ()) {
                  this.hciD(var10, alertX, alertY, var11, AzjytO);
               } else {
                  this.drawAlert(alertX, alertY, var11, AzjytO);
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.lastPacketTimeMillis = System.currentTimeMillis();
      this.resetLagState();
   }

   @Override
   public String getInfo() {
      return (int)Math.round(this.highLatency.getInput() * 1000.0) + "ms";
   }

   private IFont getHudFont() {
      return FontManager.getHudRenderer(this.font.getResolvedFontName(), 1.0F);
   }

   private long QqQn() {
      return (long)(this.highLatency.getInput() * 1000.0);
   }

   private void resetLagState() {
      this.jfQ = 0L;
      this.stallStartMillis = 0L;
      this.Znb = 0L;
      this.lastDurationRefreshMillis = 0L;
   }

   private static long roundUpToDisplayStep(long var0) {
      return var0 <= 0L ? 0L : (var0 + 100L - 1L) / 100L * 100L;
   }

   public float[] drawAlertAt(float var1, float var2) {
      EVsgL(var1, var2);
      return this.drawAlert(var1, var2, 500L, false);
   }

   private float[] drawAlert(float var1, float var2, long var3, boolean var5) {
      IFont var6 = this.getHudFont();
      float var7 = (float)Math.max(0.5, Math.min(2.0, this.fontScale.getInput()));
      String var8 = "Lag Detected! (" + var3 + "ms)";
      float var9 = var6.getFontHeight();
      float var10 = Math.max(15.0F, var9 + 4.0F);
      float var11 = var2 + (var9 - var10) / 2.0F + 1.0F;
      float var12 = var2 + (var10 - var9) / 2.0F;
      float var13 = Math.max(6.0F, var10 * 0.32F);
      float var14 = var10 + var13 + var6.getStringWidth(var8);
      float var15 = Math.max(var10, var9);
      float var16 = var5 ? var1 - var14 * var7 / 2.0F : var1;
      GlStateManager.pushMatrix();
      GlStateManager.translate(var16, var2, 0.0F);
      GlStateManager.scale(var7, var7, 1.0F);
      drawAlarmIcon(0.0F, var11 - var2, var10, -45229);
      var6.drawString(var8, var10 + var13, var12 - var2, -41374, true);
      GlStateManager.popMatrix();
      return new float[]{var16, var2, var16 + var14 * var7, var2 + var15 * var7};
   }

   private void hciD(ScaledResolution var1, float var2, float var3, long var4, boolean var6) {
      ExternalRenderBuffer var7 = ExternalRenderer.getActiveRenderBuffer();
      if (var7 != null) {
         IFont var8 = this.getHudFont();
         float var9 = (float)Math.max(0.5, Math.min(2.0, this.fontScale.getInput()));
         float var10 = var1.getScaleFactor();
         float var11 = var9 * var10;
         String var12 = "Lag Detected! (" + var4 + "ms)";
         float var13 = var8.getFontHeight();
         float var14 = Math.max(15.0F, var13 + 4.0F);
         float var15 = Math.max(6.0F, var14 * 0.32F);
         float var16 = var14 + var15 + var8.getStringWidth(var12);
         float var17 = (var6 ? var2 - var16 * var9 / 2.0F : var2) * var10;
         float var18 = var3 * var10;
         float var19 = var18 + ((var13 - var14) / 2.0F + 1.0F) * var11;
         float var20 = var18 + (var14 - var13) / 2.0F * var11;
         drawBoxWithShadow(var7, var17, var19, var14 * var11, 1.0F * var11);
         FormattedTextRenderer.drawTextAtHeight(var7, var8, var12, var17 + (var14 + var15) * var11, var20, var13 * var11, -41374, true, var8.getStringWidth(var12) * var11);
      }
   }

   private static void drawBoxWithShadow(ExternalRenderBuffer var0, float var1, float var2, float var3, float var4) {
      drawExternalAlertIcon(var0, var1 + var4, var2 + var4, var3, -1610612736);
      drawExternalAlertIcon(var0, var1, var2, var3, -45229);
   }

   private static void drawExternalAlertIcon(ExternalRenderBuffer var0, float var1, float var2, float var3, int var4) {
      float var5 = var3 * 0.035F;
      float var6 = var3 - var5 * 2.0F;
      float var7 = Math.max(1.0F, var3 * 0.09F);
      float var8 = var6 / 2.0F;
      var0.strokeRoundedRect(var1 + var5, var2 + var5, var1 + var5 + var6, var2 + var5 + var6, var4, var8, var7);
      float var9 = var3 * 0.09F;
      float var10 = var1 + (var3 - var9) / 2.0F;
      float var11 = var2 + var3 * 0.22F;
      float var12 = var2 + var3 * 0.56F;
      var0.fillRoundedRect(var10, var11, var10 + var9, var12, var4, var9 * 0.35F);
      float var13 = var3 * 0.12F;
      float var14 = var1 + (var3 - var13) / 2.0F;
      float var15 = var2 + var3 * 0.69F;
      var0.fillRoundedRect(var14, var15, var14 + var13, var15 + var13, var4, var13 / 2.0F);
   }

   public static void EVsgL(float var0, float var1) {
      setAlertPosition(var0, var1, new ScaledResolution(mc));
   }

   public static void setPositionFractions(float var0, float var1) {
      ANRCZ = Math.max(0.0F, Math.min(1.0F, var0));
      positionYFraction = Math.max(0.0F, Math.min(1.0F, var1));
      AzjytO = false;
      GKEA(new ScaledResolution(mc));
   }

   public static float getAlertX() {
      GKEA(new ScaledResolution(mc));
      return alertX;
   }

   public static float unh25() {
      GKEA(new ScaledResolution(mc));
      return alertY;
   }

   public static float getPositionXFraction() {
      return ANRCZ;
   }

   public static float getPositionYFraction() {
      return positionYFraction;
   }

   public static void resetPosition() {
      ANRCZ = 0.5F;
      positionYFraction = 0.08F;
      AzjytO = true;
      GKEA(new ScaledResolution(mc));
   }

   private static void GKEA(ScaledResolution var0) {
      alertX = ANRCZ * var0.getScaledWidth();
      alertY = positionYFraction * var0.getScaledHeight();
   }

   private static void setAlertPosition(float var0, float var1, ScaledResolution var2) {
      alertX = var0;
      alertY = var1;
      ANRCZ = var0 / Math.max(1.0F, (float)var2.getScaledWidth());
      positionYFraction = var1 / Math.max(1.0F, (float)var2.getScaledHeight());
      AzjytO = false;
   }

   private static void drawAlarmIcon(float var0, float var1, float var2, int var3) {
      RenderUtils.drawIconTexture(getAlarmTexture(), var0 + 1.0F, var1 + 1.0F, Math.round(var2), -1610612736);
      RenderUtils.drawIconTexture(getAlarmTexture(), var0, var1, Math.round(var2), var3);
   }

   private static ResourceLocation getAlarmTexture() {
      if (resourceLocation != null) {
         return resourceLocation;
      } else {
         BufferedImage var0 = new BufferedImage(256, 256, 2);
         Graphics2D var1 = var0.createGraphics();
         var1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         var1.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         float var2 = 8.0F;
         float var3 = 256.0F - var2 * 2.0F;
         float var6 = 128.0F;
         var1.setColor(Color.WHITE);
         var1.fill(new Float(var2, var2, var3, var3));
         var1.setComposite(AlphaComposite.Clear);
         float var7 = var3 * 0.1F;
         var1.fill(new Float(var2 + var7, var2 + var7, var3 - var7 * 2.0F, var3 - var7 * 2.0F));
         var1.setComposite(AlphaComposite.SrcOver);
         var1.setColor(Color.WHITE);
         float var8 = var3 * 0.1F;
         float var9 = var6 - var8 / 2.0F;
         var1.fill(new java.awt.geom.RoundRectangle2D.Float(var9, var2 + var3 * 0.2F, var8, var3 * 0.37F, var8 * 0.35F, var8 * 0.35F));
         float var10 = var3 * 0.13F;
         var1.fill(new Float(var6 - var10 / 2.0F, var2 + var3 * 0.68F, var10, var10));
         var1.dispose();
         resourceLocation = mc.getTextureManager().getDynamicTextureLocation("jade_lag_detect_alarm", new DynamicTexture(var0));
         return resourceLocation;
      }
   }

   public boolean isInLimbo() {
      if (!ClientUtils.isInWorld()) {
         return false;
      } else {
         List var1 = ClientUtils.PxSw4();
         return var1.isEmpty() ? mc.theWorld.provider.getDimensionName().equals("The End") : false;
      }
   }
}
