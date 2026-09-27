// Jade recovery: module: Watermark (render); original class: jade.deps.eLz.CYr7ZXH
package jade.client.module.render;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.Subscribe;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.client.Rendering;
import jade.client.module.render.watermark.WatermarkStyle;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.SliderSetting;
import jade.client.setting.TextSetting;
import jade.deps.loader107.SubscriptionState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = "Watermark")
public class Watermark extends Module {
   private static final float DEFAULT_X_FRACTION = 0.018F;
   private static final float aojh = 0.035F;
   private static final int COLOR_WHITE = -1;
   private static final int fGigK = -1644308;
   private static final int COLOR_ACCENT = -1171969231;
   private static final int COLOR_BACKGROUND = -266921700;
   private static final double vx4 = 34.0;
   private static ResourceLocation resourceLocation;
   private final SliderSetting mode;
   private final SliderSetting scale;
   private final TextSetting clientName;
   private float renderX = Float.NaN;
   private float renderY = Float.NaN;
   private float offsetFractionX = Float.NaN;
   private float nlu66 = Float.NaN;

   public Watermark() {
      super("Watermark", Category.render);
      this.registerSetting(this.mode = new SliderSetting("Mode", WatermarkStyle.SIMPLE.ordinal(), WatermarkStyle.labels()));
      this.registerSetting(this.scale = new SliderSetting("Scale", 1.0, 0.5, 2.5, 0.05));
      this.scale.visible = false;
      this.registerSetting(
         this.clientName = new TextSetting("Client name", "", "Jade", 32)
      );
   }

   public SliderSetting getScale() {
      return this.scale;
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
            this.renderInGame(false);
         }
      }
   }

   public float getRenderX() {
      this.wNgl();
      return this.renderX;
   }

   public float getRenderY() {
      this.wNgl();
      return this.renderY;
   }

   public float PhZ0() {
      this.wNgl();
      return this.offsetFractionX;
   }

   public float getOffsetFractionY() {
      this.wNgl();
      return this.nlu66;
   }

   public void XkC0(float var1, float var2) {
      this.offsetFractionX = Math.max(0.0F, Math.min(1.0F, var1));
      this.nlu66 = Math.max(0.0F, Math.min(1.0F, var2));
      this.wNgl();
   }

   public void setScreenPosition(float var1, float var2) {
      this.applyScreenPosition(var1, var2, new ScaledResolution(mc));
   }

   public void resetToDefaultOffset() {
      this.XkC0(0.018F, 0.035F);
   }

   public float[] getBoundsAt(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      float var4 = this.renderX;
      float var5 = this.renderY;
      float var6 = this.offsetFractionX;
      float var7 = this.nlu66;
      this.applyScreenPosition(var1, var2, var3);
      Watermark$1 var8 = this.renderStyleAndGetBounds(true);
      this.renderX = var4;
      this.renderY = var5;
      this.offsetFractionX = var6;
      this.nlu66 = var7;
      return new float[]{Watermark$1.getLeft(var8), Watermark$1.QSPqRh(var8), Watermark$1.getRight(var8), Watermark$1.getBottom(var8)};
   }

   private void renderInGame(boolean var1) {
      this.wNgl();
      if (var1 || !Rendering.isExternalOutput()) {
         this.renderStyleAndGetBounds(var1);
      }
   }

   private void renderToExternalBuffer() {
      ExternalRenderBuffer var1 = ExternalRenderer.getActiveRenderBuffer();
      if (var1 != null) {
         float var2 = new ScaledResolution(mc).getScaleFactor();
         float var3 = this.FHZzL();
         float var4 = this.renderX * var2;
         float var5 = this.renderY * var2;
         WatermarkStyle var6 = WatermarkStyle.fromSetting(this.mode.getInput());
         if (var6 == WatermarkStyle.LOGO) {
            this.renderLogo(var1, var4, var5, 28.0F * var3 * var2);
         } else if (var6 == WatermarkStyle.BOX) {
            IFont var18 = this.getModernFont(0.86F);
            String var19 = this.resolveClientName("jaded.cc");
            String var21 = this.resolvePlayerName() + " | " + this.resolveServerName();
            float var10 = 19.0F * var3 * var2;
            float var11 = 12.0F * var3 * var2;
            float var12 = 5.0F * var3 * var2;
            float var13 = 4.0F * var3 * var2;
            float var14 = 6.0F * var3 * var2;
            float var15 = var12 + var11 + var13 + var18.getStringWidth(var19) * var2 + var12;
            float var16 = var15 + var18.getStringWidth(var21) * var2 + var12 * 2.0F;
            var1.fillRoundedRect(var4, var5, var4 + var16, var5 + var10, -266921700, 4.0F * var3 * var2);
            var1.fillPerCornerRoundedRect(var4, var5, var4 + var15, var5 + var10, -1171969231, 4.0F * var3 * var2, 0.0F, 0.0F, 4.0F * var3 * var2);
            this.renderLogo(var1, var4 + var12, var5 + (var10 - var11) / 2.0F, var11);
            float var17 = var5 + (var10 - var18.getFontHeight() * var2) / 2.0F;
            FormattedTextRenderer.drawTextAtHeight(var1, var18, var19, var4 + var12 + var11 + var13, var17, var18.getFontHeight() * var2, -1, true, var18.getStringWidth(var19) * var2);
            FormattedTextRenderer.drawTextAtHeight(var1, var18, var21, var4 + var15 + var14, var17, var18.getFontHeight() * var2, -1644308, true, var18.getStringWidth(var21) * var2);
         } else {
            IFont var7 = var6 == WatermarkStyle.OLD ? this.getModernFont(1.15F) : this.getBoldFont(0.9F);
            String var8 = this.resolveClientName(var6 == WatermarkStyle.OLD ? "Jade" : "jaded.cc");
            if (var8.isEmpty()) {
               var8 = "Jade";
            }

            if (var6 == WatermarkStyle.SIMPLE) {
               float var9 = 20.0F * var3 * var2;
               this.renderLogo(var1, var4, var5, var9);
               FormattedTextRenderer.drawTextAtHeight(
                  var1,
                  var7,
                  var8,
                  var4 + var9 + 3.0F * var3 * var2,
                  var5 + (var9 - var7.getFontHeight() * var2) / 2.0F,
                  var7.getFontHeight() * var2,
                  -1,
                  true,
                  var7.getStringWidth(var8) * var2
               );
            } else {
               String var20 = var8.substring(0, 1);
               FormattedTextRenderer.drawTextAtHeight(
                  var1,
                  var7,
                  var20,
                  var4,
                  var5 - var7.getTextTopOffset() * var2,
                  var7.getFontHeight() * var2,
                  this.getThemeColor(0.0),
                  true,
                  var7.getStringWidth(var20) * var2
               );
               if (var8.length() > 1) {
                  FormattedTextRenderer.drawTextAtHeight(
                     var1,
                     var7,
                     var8.substring(1),
                     var4 + var7.getStringWidth(var20) * var2,
                     var5 - var7.getTextTopOffset() * var2,
                     var7.getFontHeight() * var2,
                     -1,
                     true,
                     var7.getStringWidth(var8.substring(1)) * var2
                  );
               }
            }
         }
      }
   }

   private void renderLogo(ExternalRenderBuffer var1, float var2, float var3, float var4) {
      double var5 = var2 + var4 * 0.5;
      double var7 = var3 + var4 * 0.09375;
      double var9 = var2 + var4 * 0.84375;
      double var11 = var3 + var4 * 0.4375;
      double var15 = var3 + var4 * 0.90625;
      double var17 = var2 + var4 * 0.15625;
      double var21 = var3 + var4 * 0.48;
      int var23 = this.getThemeColor(0.0);
      int var24 = this.getThemeColor(34.0);
      int var25 = this.getThemeColor(17.0);
      var1.VogZb(var5, var7, var5, var21, var17, var11, ClientUtils.YVVZ(var23, 140));
      var1.VogZb(var5, var7, var9, var11, var5, var21, ClientUtils.YVVZ(var24, 140));
      var1.VogZb(var17, var11, var5, var21, var5, var15, ClientUtils.YVVZ(var23, 140));
      var1.VogZb(var5, var21, var9, var11, var5, var15, ClientUtils.YVVZ(var24, 140));
      double var26 = var2 + var4 * 0.28125;
      double var28 = var2 + var4 * 0.71875;
      double var30 = var3 + var4 * 0.59375;
      var1.VogZb(var5, var7, var26, var30, var5, var21, ClientUtils.YVVZ(var23, 64));
      var1.VogZb(var5, var7, var5, var21, var28, var30, ClientUtils.YVVZ(var24, 64));
      var1.VogZb(var26, var30, var28, var30, var5, var21, ClientUtils.YVVZ(var25, 64));
      float var32 = Math.max(1.0F, var4 * 2.2F / 64.0F);
      var1.drawLine(var5, var7, var9, var11, var24, var32);
      var1.drawLine(var9, var11, var5, var15, var24, var32);
      var1.drawLine(var5, var15, var17, var11, var23, var32);
      var1.drawLine(var17, var11, var5, var7, var23, var32);
      var1.drawLine(var5, var7, var26, var30, var23, var32);
      var1.drawLine(var26, var30, var28, var30, var25, var32);
      var1.drawLine(var28, var30, var5, var7, var24, var32);
   }

   private Watermark$1 renderStyleAndGetBounds(boolean var1) {
      WatermarkStyle var2 = WatermarkStyle.fromSetting(this.mode.getInput());
      Watermark$1 var3;
      if (var2 == WatermarkStyle.LOGO) {
         var3 = this.renderLogoStyle();
      } else if (var2 == WatermarkStyle.BOX) {
         var3 = this.renderBoxStyle(var1);
      } else if (var2 == WatermarkStyle.OLD) {
         var3 = this.renderOldStyle();
      } else {
         var3 = this.renderSimpleStyle();
      }

      return var3;
   }

   private Watermark$1 renderLogoStyle() {
      float var1 = 28.0F * this.FHZzL();
      this.QciS(this.renderX, this.renderY, var1);
      return new Watermark$1(this.renderX, this.renderY, this.renderX + var1, this.renderY + var1);
   }

   private Watermark$1 renderSimpleStyle() {
      float var1 = this.FHZzL();
      float var2 = 20.0F * var1;
      float var3 = 3.0F * var1;
      IFont var4 = this.getBoldFont(0.9F);
      String var5 = this.resolveClientName("jaded.cc");
      float var6 = this.VREaC(this.renderY, var2, var4);
      this.QciS(this.renderX, this.renderY, var2);
      var4.drawString(var5, this.renderX + var2 + var3, var6, -1, true);
      return new Watermark$1(this.renderX, this.renderY, this.renderX + var2 + var3 + var4.getStringWidth(var5), this.renderY + var2);
   }

   private Watermark$1 renderBoxStyle(boolean var1) {
      float var2 = this.FHZzL();
      IFont var3 = this.getModernFont(0.86F);
      String var4 = this.resolveClientName("jaded.cc");
      String var5 = var1 ? "opal.wtf" : this.resolvePlayerName();
      String var6 = var1 ? "hypixel.net" : this.resolveServerName();
      String var7 = var5 + " | " + var6;
      float var8 = 19.0F * var2;
      float var9 = 4.0F * var2;
      float var10 = 12.0F * var2;
      float var11 = 5.0F * var2;
      float var12 = 4.0F * var2;
      float var13 = 6.0F * var2;
      float var14 = var11 + var10 + var12 + var3.getStringWidth(var4) + var11;
      float var15 = var3.getStringWidth(var7) + var11 * 2.0F;
      float var16 = var14 + var15;
      RoundedRect.drawRoundedRectArgb(this.renderX, this.renderY, var16, var8, var9, -266921700);
      RoundedRect.drawRoundedRectWithCornerFlags(this.renderX, this.renderY, var14, var8, var9, -1171969231, true, false, false, true);
      this.QciS(this.renderX + var11, this.renderY + (var8 - var10) / 2.0F, var10);
      var3.drawString(var4, this.renderX + var11 + var10 + var12, this.VREaC(this.renderY, var8, var3), -1, true);
      var3.drawString(var7, this.renderX + var14 + var13, this.VREaC(this.renderY, var8, var3), -1644308, true);
      return new Watermark$1(this.renderX, this.renderY, this.renderX + var16, this.renderY + var8);
   }

   private Watermark$1 renderOldStyle() {
      IFont var1 = this.getModernFont(1.15F);
      String var2 = this.resolveClientName("Jade");
      if (var2.isEmpty()) {
         var2 = "Jade";
      }

      float var3 = this.renderY - var1.getTextTopOffset();
      if (var2.length() == 1) {
         var1.drawString(var2, this.renderX, var3, this.getThemeColor(0.0), true);
      } else {
         String var4 = var2.substring(0, 1);
         String var5 = var2.substring(1);
         var1.drawString(var4, this.renderX, var3, this.getThemeColor(0.0), true);
         var1.drawString(var5, this.renderX + var1.getStringWidth(var4), var3, -1, true);
      }

      float var6 = var1.getTextBottomOffset() - var1.getTextTopOffset();
      return new Watermark$1(this.renderX, this.renderY, this.renderX + var1.getStringWidth(var2), this.renderY + var6);
   }

   private void QciS(float var1, float var2, float var3) {
      ResourceLocation var4 = this.getLogoTexture();
      if (var4 == null) {
         RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var3, this.getThemeColor(0.0));
      } else {
         this.naBwkEh(var4, var1, var2, var3, this.getThemeColor(0.0), this.getThemeColor(34.0));
         GlStateManager.enableBlend();
         GL11.glBlendFunc(770, 771);
      }
   }

   private void naBwkEh(ResourceLocation var1, float var2, float var3, float var4, int var5, int var6) {
      RenderUtils.finishItemRenderState();
      mc.getTextureManager().bindTexture(var1);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      var8.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      this.xgOg(var8, var2, var3 + var4, 0.0F, 1.0F, var5);
      this.xgOg(var8, var2 + var4, var3 + var4, 1.0F, 1.0F, var6);
      this.xgOg(var8, var2 + var4, var3, 1.0F, 0.0F, var6);
      this.xgOg(var8, var2, var3, 0.0F, 0.0F, var5);
      var7.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void xgOg(WorldRenderer var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = (var6 >>> 24 & 0xFF) / 255.0F;
      float var8 = (var6 >> 16 & 0xFF) / 255.0F;
      float var9 = (var6 >> 8 & 0xFF) / 255.0F;
      float var10 = (var6 & 0xFF) / 255.0F;
      var1.pos(var2, var3, 0.0).tex(var4, var5).color(var8, var9, var10, var7).endVertex();
   }

   private ResourceLocation getLogoTexture() {
      if (resourceLocation != null) {
         return resourceLocation;
      } else {
         short var1 = 128;
         BufferedImage var2 = new BufferedImage(var1, var1, 2);
         Graphics2D var3 = var2.createGraphics();
         var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         var3.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         float var4 = var1 / 64.0F;
         AffineTransform var5 = AffineTransform.getScaleInstance(var4, var4);
         Shape var6 = var5.createTransformedShape(this.createPolygonPath(32.0F, 6.0F, 54.0F, 28.0F, 32.0F, 58.0F, 10.0F, 28.0F));
         Shape var7 = var5.createTransformedShape(this.createPolygonPath(32.0F, 6.0F, 18.0F, 38.0F, 46.0F, 38.0F));
         var3.setColor(new Color(-1929379841, true));
         var3.fill(var6);
         var3.setColor(new Color(1090519039, true));
         var3.fill(var7);
         var3.setStroke(new BasicStroke(2.2F * var4, 1, 1));
         var3.setColor(Color.WHITE);
         var3.draw(var6);
         var3.draw(var7);
         var3.dispose();
         resourceLocation = mc.getTextureManager().getDynamicTextureLocation("jade_watermark_clickgui_logo", new DynamicTexture(var2));
         return resourceLocation;
      }
   }

   private Shape createPolygonPath(float var1, float var2, float... var3) {
      java.awt.geom.Path2D.Float var4 = new java.awt.geom.Path2D.Float();
      var4.moveTo(var1, var2);

      for (byte var5 = 0; var5 + 1 < var3.length; var5 += 2) {
         var4.lineTo(var3[var5], var3[var5 + 1]);
      }

      var4.closePath();
      return var4;
   }

   private int getThemeColor(double var1) {
      return ClientUtils.YVVZ(Arraylist.xQec0(var1), 255);
   }

   private String resolveClientName(String var1) {
      String var2 = this.clientName.getValue();
      return var2 != null && !var2.trim().isEmpty() ? var2.trim() : var1;
   }

   private String resolvePlayerName() {
      String var1 = SubscriptionState.getDiscordUsername();
      if (var1 != null && !var1.trim().isEmpty()) {
         return var1.trim();
      } else {
         return mc.getSession() != null && mc.getSession().getUsername() != null && !mc.getSession().getUsername().isEmpty()
            ? mc.getSession().getUsername()
            : "player";
      }
   }

   private String resolveServerName() {
      if (mc.isSingleplayer()) {
         return "singleplayer";
      } else {
         return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP != null && !mc.getCurrentServerData().serverIP.isEmpty()
            ? mc.getCurrentServerData().serverIP
            : "main menu";
      }
   }

   private IFont getModernFont(float var1) {
      return FontManager.getHudRenderer("Modern", this.FHZzL() * var1);
   }

   private IFont getBoldFont(float var1) {
      return FontManager.getHudRenderer("Bold", this.FHZzL() * var1);
   }

   private float FHZzL() {
      return (float)this.scale.getInput();
   }

   private float VREaC(float var1, float var2, IFont var3) {
      return var1 + (var2 - (var3.getTextBottomOffset() - var3.getTextTopOffset())) / 2.0F - var3.getTextTopOffset();
   }

   private void wNgl() {
      this.resolveScreenPosition(new ScaledResolution(mc));
   }

   private void resolveScreenPosition(ScaledResolution var1) {
      int var2 = Math.max(1, var1.getScaledWidth());
      int var3 = Math.max(1, var1.getScaledHeight());
      if (Float.isNaN(this.offsetFractionX) || Float.isNaN(this.nlu66)) {
         this.offsetFractionX = 0.018F;
         this.nlu66 = 0.035F;
      }

      this.renderX = this.offsetFractionX * var2;
      this.renderY = this.nlu66 * var3;
   }

   private void applyScreenPosition(float var1, float var2, ScaledResolution var3) {
      this.renderX = var1;
      this.renderY = var2;
      this.offsetFractionX = var1 / Math.max(1.0F, (float)var3.getScaledWidth());
      this.nlu66 = var2 / Math.max(1.0F, (float)var3.getScaledHeight());
   }
}
