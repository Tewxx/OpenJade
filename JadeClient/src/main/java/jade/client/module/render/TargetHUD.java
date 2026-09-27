// Jade recovery: module: Target HUD (render); original class: jade.deps.eLz.Wq6GDygnI4
package jade.client.module.render;

import jade.client.common.Animation;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.ExternalSkinTextures;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.AttackEntityEvent;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.shared.PostProcessing;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = "TargetHUD")
public class TargetHUD extends Module implements ExternalRenderableModule {
   private final SliderSetting backgroundOpacity;
   private final SliderSetting rounding;
   private final FontSetting font;
   private final SliderSetting fontScale;
   private final MultiSelectSetting multiSelectSetting;
   private final BooleanSetting textShadow;
   private final BooleanSetting outerShadow;
   private final BooleanSetting healthDifference;
   private final SliderSetting shadowRadius;
   private final SliderSetting shadowOpacity;
   private Animation fadeInAnimation;
   private Animation Qqd;
   private EntityLivingBase rwEih;
   private long QtL;
   private double healthFraction;
   private float animatedHealthX = -1.0F;
   private boolean PmiS;
   public EntityLivingBase zL64;
   public int Vilxx = 70;
   public int offsetY = 30;
   private static final float PADDING = 5.0F;
   private static final float amg = 5.0F;
   private static final float TGa936 = 20.0F;
   private static final float OFH = 5.0F;
   private static final int Nkp = 3;
   private static final float DEFAULT_SHADOW_RADIUS = 3.5F;
   private static final float DEFAULT_SHADOW_OPACITY = 1.0F;

   public TargetHUD() {
      super("Target HUD", Category.render);
      this.registerSetting(new DescriptionSetting("Shows on the player you attack."));
      this.registerSetting(this.backgroundOpacity = new SliderSetting("Background Opacity", 85.0, 10.0, 100.0, 1.0));
      this.registerSetting(this.rounding = new SliderSetting("Rounding", 6.0, 0.0, 20.0, 1.0));
      this.registerSetting(this.font = new FontSetting("Font", "Bold"));
      this.registerSetting(this.fontScale = new SliderSetting("Font scale", 1.0, 0.5, 2.0, 0.1));
      this.fontScale.visible = false;
      this.textShadow = new BooleanSetting("Text Shadow", true);
      this.outerShadow = new BooleanSetting("Outer Shadow", false);
      this.healthDifference = new BooleanSetting(
         "Health Difference", false
      );
      String var10004 = "Options";
      BooleanSetting[] var10005 = new BooleanSetting[3];
      var10005[0] = this.textShadow;
      var10005[1] = this.outerShadow;
      var10005[2] = this.healthDifference;
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.textShadow.visible = false;
      this.outerShadow.visible = false;
      this.healthDifference.visible = false;
      this.registerSetting(this.textShadow);
      this.registerSetting(this.outerShadow);
      this.registerSetting(this.healthDifference);
      this.registerSetting(this.shadowRadius = new SliderSetting("Shadow Radius", 3.5, 1.0, 10.0, 0.5));
      this.registerSetting(this.shadowOpacity = new SliderSetting("Shadow Opacity", 1.0, 0.0, 1.0, 0.05));
      this.shadowRadius.visible = false;
      this.shadowOpacity.visible = false;
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.outerShadow.isToggled();
      this.shadowRadius.setVisible(var1, this);
      this.shadowOpacity.setVisible(var1, this);
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.outerShadow) {
         this.guiUpdate();
      }
   }

   public SliderSetting getFontScale() {
      return this.fontScale;
   }

   @Override
   public void onDisable() {
      this.clearTargetState();
   }

   @Subscribe
   public void onAttackEntity(AttackEntityEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (var1.entity instanceof EntityLivingBase) {
            this.rwEih = (EntityLivingBase)var1.entity;
            this.zL64 = this.rwEih;
            this.QtL = System.currentTimeMillis();
            this.fadeInAnimation = null;
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.clearTargetState();
      } else if (var1.eventPhase == EventPhase.END) {
         if (mc.currentScreen != null) {
            this.clearTargetState();
         } else if (this.rwEih != null) {
            if (System.currentTimeMillis() - this.QtL >= 400L && this.fadeInAnimation == null) {
               (this.fadeInAnimation = new Animation(400.0F)).restart();
            }

            double var2 = this.rwEih.getHealth() / this.rwEih.getMaxHealth();
            if (this.rwEih.isDead) {
               var2 = 0.0;
            }

            if (var2 != this.healthFraction) {
               (this.Qqd = new Animation(500.0F)).restart();
            }

            this.healthFraction = var2;
            this.renderTargetHudPanel(this.fadeInAnimation, this.rwEih, var2);
         }
      }
   }

   private void renderTargetHudPanel(Animation var1, EntityLivingBase var2, double var3) {
      int var5 = var1 == null ? 255 : 255 - var1.computeEasedInt(0, 255, 1);
      if (var5 <= 0) {
         this.rwEih = null;
         this.zL64 = null;
         this.Qqd = null;
         this.animatedHealthX = -1.0F;
      } else {
         int var6 = (int)(this.backgroundOpacity.getInput() / 100.0 * var5);
         ScaledResolution var8 = new ScaledResolution(mc);
         String var9 = this.font.getResolvedFontName();
         IFont var10 = FontManager.getHudRenderer(var9, (float)this.fontScale.getInput());
         float var11 = (float)this.rounding.getInput();
         String var12 = var2.getDisplayName().getFormattedText();
         float var13 = var2.isDead ? 0.0F : var2.getHealth();
         String var14 = String.valueOf(Math.round(var13));
         float var15 = mc.thePlayer != null && !mc.thePlayer.isDead ? mc.thePlayer.getHealth() : 0.0F;
         float var16 = var15 - var13;
         String var17 = SVnQ(var16);
         TargetHUD$2 var18 = this.computeLayoutMetrics(var10, var12, var14, var17);
         float var19 = var8.getScaledWidth() / 2.0F - TargetHUD$2.getWidth(var18) / 2.0F + this.Vilxx;
         float var20 = var8.getScaledHeight() / 2.0F + 15.0F + this.offsetY;
         if (this.VIuQ() && !this.PmiS) {
            this.renderHudToBuffer(var2, var3, var16, var14, var17, var12, var10, var18, var19, var20, var5, var6, var5);
         } else {
            if (this.outerShadow.isToggled()) {
               this.RqE3(var19, var20, TargetHUD$2.getWidth(var18), TargetHUD$2.getHeight(var18), var11 * TargetHUD$2.getScale(var18), var5);
            }

            Color var21 = new Color(12, 12, 12, var6);
            RoundedRect.aedh(var19, var20, TargetHUD$2.getWidth(var18), TargetHUD$2.getHeight(var18), var11 * TargetHUD$2.getScale(var18), true, var21);
            float var22 = var19 + TargetHUD$2.bCboH(var18);
            float var23 = var20 + (TargetHUD$2.getHeight(var18) - TargetHUD$2.getHeadSize(var18)) / 2.0F;
            this.drawPlayerFace(var2, var22, var23, TargetHUD$2.getHeadSize(var18), var5);
            float var24 = var22 + TargetHUD$2.getHeadSize(var18) + TargetHUD$2.rL02(var18);
            float var25 = var20 + TargetHUD$2.getHeight(var18) - TargetHUD$2.bCboH(var18) - TargetHUD$2.getBarHeight(var18);
            float var26 = var20 + TargetHUD$2.bCboH(var18) - var10.getTextTopOffset();
            int var27 = new Color(220, 220, 220).getRGB() & 16777215 | ClientUtils.clampColorComponent(var5 + 15) << 24;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            var10.drawString(var12, var24, var26, var27, this.textShadow.isToggled());
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            float var29 = var19 + TargetHUD$2.getWidth(var18) - TargetHUD$2.bCboH(var18) - TargetHUD$2.getHealthTextWidth(var18);
            float var30 = var29 - var24;
            float var32 = var24 + var30 * (float)var3;
            if (this.animatedHealthX < 0.0F) {
               this.animatedHealthX = var32;
            }

            if (Math.abs(var32 - this.animatedHealthX) > 0.1F && this.Qqd != null) {
               float var33 = this.animatedHealthX - var32;
               if (var33 > 0.0F) {
                  this.animatedHealthX = this.animatedHealthX - this.Qqd.computeEasedValue(0.0F, var33, 4);
               } else {
                  this.animatedHealthX = this.Qqd.computeEasedValue(this.animatedHealthX, var32, 4);
               }
            } else {
               this.animatedHealthX = var32;
            }

            this.animatedHealthX = Math.max(var24, Math.min(var29, this.animatedHealthX));
            int var42 = Arraylist.xQec0(var24 * 0.25);
            int var34 = Arraylist.xQec0(var29 * 0.25 + 35.0);
            int var35 = ClientUtils.YVVZ(var42, var5);
            int var36 = ClientUtils.YVVZ(var34, var5);
            float var37 = Math.min((float)this.rounding.getInput() * TargetHUD$2.getScale(var18), TargetHUD$2.getBarHeight(var18) / 2.0F);
            RenderUtils.jxyoE(var24, var25, var29, var25 + TargetHUD$2.getBarHeight(var18), var37, ClientUtils.YVVZ(Color.black.getRGB(), Math.min(var5, 90)));
            if (this.animatedHealthX > var24) {
               RenderUtils.drawRoundedGradientRect(var24, var25, this.animatedHealthX, var25 + TargetHUD$2.getBarHeight(var18), var37, var35, var35, var36, var36);
            }

            float var38 = var29 + TargetHUD$2.getBarGap(var18);
            float var39 = var25 + (TargetHUD$2.getBarHeight(var18) - var10.getFontHeight()) / 2.0F;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            var10.drawString(var14, var38, var39, var27, this.textShadow.isToggled());
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            if (this.healthDifference.isToggled()) {
               int var40 = var16 > 0.05F
                  ? new Color(76, 215, 120).getRGB()
                  : (var16 < -0.05F ? new Color(239, 83, 80).getRGB() : new Color(180, 180, 180).getRGB());
               int var41 = ClientUtils.YVVZ(var40, var5);
               GL11.glPushMatrix();
               GL11.glEnable(3042);
               var10.drawString(var17, var38, var26, var41, this.textShadow.isToggled());
               GL11.glDisable(3042);
               GL11.glPopMatrix();
            }
         }
      }
   }

   private void renderHudToBuffer(
      EntityLivingBase var1,
      double var2,
      float var4,
      String var5,
      String var6,
      String var7,
      IFont var8,
      TargetHUD$2 var9,
      float var10,
      float var11,
      int var12,
      int var13,
      int var14
   ) {
      ExternalRenderBuffer var15 = ExternalRenderer.getActiveRenderBuffer();
      if (var15 != null) {
         float var16 = new ScaledResolution(mc).getScaleFactor();
         float var17 = var10 * var16;
         float var18 = var11 * var16;
         float var19 = TargetHUD$2.getWidth(var9) * var16;
         float var20 = TargetHUD$2.getHeight(var9) * var16;
         float var21 = (float)this.rounding.getInput() * TargetHUD$2.getScale(var9) * var16;
         if (this.outerShadow.isToggled()) {
            for (int var22 = 5; var22 > 0; var22--) {
               float var23 = (float)this.shadowRadius.getInput() * var16 * var22 / 5.0F;
               int var24 = Math.round((float)this.shadowOpacity.getInput() * var12 / 12.0F);
               var15.fillRoundedRect(var17 - var23, var18 - var23, var17 + var19 + var23, var18 + var20 + var23, var24 << 24, var21 + var23);
            }
         }

         var15.fillRoundedRect(var17, var18, var17 + var19, var18 + var20, var13 << 24 | 789516, var21);
         float var38 = var17 + TargetHUD$2.bCboH(var9) * var16;
         float var39 = var18 + (TargetHUD$2.getHeight(var9) - TargetHUD$2.getHeadSize(var9)) * var16 / 2.0F;
         float var40 = TargetHUD$2.getHeadSize(var9) * var16;
         if (var1 instanceof AbstractClientPlayer) {
            ExternalSkinTextures.drawPlayerHead(var15, (AbstractClientPlayer)var1, var38, var39, var40, var14 / 255.0F);
         }

         float var25 = var10 + TargetHUD$2.bCboH(var9) + TargetHUD$2.getHeadSize(var9) + TargetHUD$2.rL02(var9);
         float var26 = var25 * var16;
         float var27 = var18 + TargetHUD$2.bCboH(var9) * var16 - var8.getTextTopOffset() * var16;
         int var28 = ClientUtils.YVVZ(new Color(220, 220, 220).getRGB(), ClientUtils.clampColorComponent(var14 + 15));
         FormattedTextRenderer.drawTextAtHeight(var15, var8, var7, var26, var27, var8.getFontHeight() * var16, var28, this.textShadow.isToggled(), var8.getStringWidth(var7) * var16);
         float var29 = var18 + (TargetHUD$2.getHeight(var9) - TargetHUD$2.bCboH(var9) - TargetHUD$2.getBarHeight(var9)) * var16;
         float var30 = var10 + TargetHUD$2.getWidth(var9) - TargetHUD$2.bCboH(var9) - TargetHUD$2.getHealthTextWidth(var9);
         float var31 = var25 + (var30 - var25) * (float)var2;
         if (this.animatedHealthX < 0.0F) {
            this.animatedHealthX = var31;
         }

         if (Math.abs(var31 - this.animatedHealthX) > 0.1F && this.Qqd != null) {
            float var32 = this.animatedHealthX - var31;
            this.animatedHealthX = var32 > 0.0F ? this.animatedHealthX - this.Qqd.computeEasedValue(0.0F, var32, 4) : this.Qqd.computeEasedValue(this.animatedHealthX, var31, 4);
         } else {
            this.animatedHealthX = var31;
         }

         this.animatedHealthX = Math.max(var25, Math.min(var30, this.animatedHealthX));
         float var41 = var30 * var16;
         float var33 = this.animatedHealthX * var16;
         float var34 = Math.min(var21, TargetHUD$2.getBarHeight(var9) * var16 / 2.0F);
         var15.fillRoundedRect(var26, var29, var41, var29 + TargetHUD$2.getBarHeight(var9) * var16, ClientUtils.YVVZ(Color.black.getRGB(), Math.min(var14, 90)), var34);
         if (var33 > var26) {
            int var35 = ClientUtils.YVVZ(Arraylist.xQec0(var25 * 0.25), var14);
            int var36 = ClientUtils.YVVZ(Arraylist.xQec0(var30 * 0.25 + 35.0), var14);
            var15.fillRoundedRect(var26, var29, var33, var29 + TargetHUD$2.getBarHeight(var9) * var16, lerpArgb(var35, var36, 0.5F), var34);
         }

         float var42 = var41 + TargetHUD$2.getBarGap(var9) * var16;
         float var43 = var29 + (TargetHUD$2.getBarHeight(var9) - var8.getFontHeight()) * var16 / 2.0F;
         FormattedTextRenderer.drawTextAtHeight(var15, var8, var5, var42, var43, var8.getFontHeight() * var16, var28, this.textShadow.isToggled(), var8.getStringWidth(var5) * var16);
         if (this.healthDifference.isToggled()) {
            int var37 = var4 > 0.05F ? new Color(76, 215, 120).getRGB() : (var4 < -0.05F ? new Color(239, 83, 80).getRGB() : new Color(180, 180, 180).getRGB());
            FormattedTextRenderer.drawTextAtHeight(
               var15, var8, var6, var42, var27, var8.getFontHeight() * var16, ClientUtils.YVVZ(var37, var14), this.textShadow.isToggled(), var8.getStringWidth(var6) * var16
            );
         }
      }
   }

   public float[] getEditorBounds(int var1, int var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      int var4 = this.Vilxx;
      int var5 = this.offsetY;
      EntityPlayerSP var6 = mc.thePlayer;
      if (var6 == null) {
         this.Vilxx = var4;
         this.offsetY = var5;
         float var19 = Math.max(0.5F, (float)this.fontScale.getInput());
         return new float[]{var1, var2, var1 + 122.0F * var19, var2 + 30.0F * var19};
      } else {
         double var7 = var6.getHealth() / var6.getMaxHealth();
         String var9 = this.font.getResolvedFontName();
         IFont var10 = FontManager.getHudRenderer(var9, (float)this.fontScale.getInput());
         String var11 = var6.getDisplayName().getFormattedText();
         String var12 = String.valueOf(Math.round(var6.getHealth()));
         String var13 = SVnQ(0.0F);
         TargetHUD$2 var14 = this.computeLayoutMetrics(var10, var11, var12, var13);
         this.Vilxx = Math.round(var1 - (var3.getScaledWidth() / 2.0F - TargetHUD$2.getWidth(var14) / 2.0F));
         this.offsetY = Math.round(var2 - (var3.getScaledHeight() / 2.0F + 15.0F));
         EntityLivingBase var15 = this.rwEih;
         this.rwEih = var6;
         this.PmiS = true;

         try {
            this.renderTargetHudPanel(null, var6, var7);
         } finally {
            this.PmiS = false;
            this.rwEih = var15;
         }

         this.Vilxx = var4;
         this.offsetY = var5;
         return new float[]{var1, var2, var1 + TargetHUD$2.getWidth(var14), var2 + TargetHUD$2.getHeight(var14)};
      }
   }

   public float getHudWidth() {
      EntityPlayerSP var1 = mc.thePlayer;
      if (var1 == null) {
         return 122.0F * Math.max(0.5F, (float)this.fontScale.getInput());
      } else {
         String var2 = this.font.getResolvedFontName();
         IFont var3 = FontManager.getHudRenderer(var2, (float)this.fontScale.getInput());
         String var4 = var1.getDisplayName().getFormattedText();
         String var5 = String.valueOf(Math.round(var1.getHealth()));
         return TargetHUD$2.getWidth(this.computeLayoutMetrics(var3, var4, var5, SVnQ(0.0F)));
      }
   }

   private TargetHUD$2 computeLayoutMetrics(IFont var1, String var2, String var3, String var4) {
      float var5 = Math.max(0.5F, (float)this.fontScale.getInput());
      float var6 = 5.0F * var5;
      float var7 = 5.0F * var5;
      float var8 = 20.0F * var5;
      float var9 = 5.0F * var5;
      float var10 = 3.0F * var5;
      float var11 = var1.getStringWidth(var3);
      float var12 = var1.getStringWidth(var2);
      if (this.healthDifference.isToggled()) {
         var11 = Math.max(var11, (float)var1.getStringWidth(var4));
         var12 += var10 + var11 + 1.0F * var5;
      }

      float var13 = var11 + var10 + 1.0F * var5;
      float var14 = 40.0F * var5 + var13;
      float var15 = Math.max(Math.max(var12, var14), 90.0F * var5);
      float var16 = var1.getFontHeight() + 4.0F * var5 + var9;
      float var17 = var6 + var8 + var7 + var15 + var6;
      float var18 = Math.max(var8, var16) + var6 * 2.0F;
      return new TargetHUD$2(var5, var6, var7, var8, var9, var10, var13, var17, var18);
   }

   private static String SVnQ(float var0) {
      if (Math.abs(var0) < 0.05F) {
         return "0";
      } else {
         float var1 = Math.abs(var0);
         long var2 = Math.round(var1);
         String var4 = Math.abs(var1 - (float)var2) < 0.05F ? String.valueOf(var2) : String.format(Locale.ROOT, "%.1f", var1);
         return (var0 > 0.0F ? "+" : "-") + var4;
      }
   }

   private static int lerpArgb(int var0, int var1, float var2) {
      var2 = Math.max(0.0F, Math.min(1.0F, var2));
      int var3 = Math.round((var0 >>> 24) + ((var1 >>> 24) - (var0 >>> 24)) * var2);
      int var4 = Math.round((var0 >> 16 & 0xFF) + ((var1 >> 16 & 0xFF) - (var0 >> 16 & 0xFF)) * var2);
      int var5 = Math.round((var0 >> 8 & 0xFF) + ((var1 >> 8 & 0xFF) - (var0 >> 8 & 0xFF)) * var2);
      int var6 = Math.round((var0 & 0xFF) + ((var1 & 0xFF) - (var0 & 0xFF)) * var2);
      return var3 << 24 | var4 << 16 | var5 << 8 | var6;
   }

   private void RqE3(final float var1, final float var2, final float var3, final float var4, final float var5, int var6) {
      final int var7 = Math.round((float)this.shadowOpacity.getInput() * var6);
      if (var7 > 0) {
         PostProcessing.runWithBloomMasked(new Runnable() {
            @Override
            public void run() {
               RoundedRect.aedh(var1, var2, var3, var4, var5, true, new Color(0, 0, 0, var7));
            }
         }, 3, Math.max(1.0F, (float)this.shadowRadius.getInput()), true);
         pVlg();
      }
   }

   private static void pVlg() {
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void drawPlayerFace(EntityLivingBase var1, float var2, float var3, float var4, int var5) {
      if (var1 instanceof AbstractClientPlayer) {
         ResourceLocation var6 = ((AbstractClientPlayer)var1).getLocationSkin();
         if (var6 != null) {
            mc.getTextureManager().bindTexture(var6);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, var5 / 255.0F);
            VYjD(var2, var3, var4, var4, 0.125F, 0.125F, 0.25F, 0.25F);
            VYjD(var2, var3, var4, var4, 0.625F, 0.125F, 0.75F, 0.25F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
         }
      }
   }

   private static void VYjD(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(var4, var5);
      GL11.glVertex2f(var0, var1);
      GL11.glTexCoord2f(var4, var7);
      GL11.glVertex2f(var0, var1 + var3);
      GL11.glTexCoord2f(var6, var7);
      GL11.glVertex2f(var0 + var2, var1 + var3);
      GL11.glTexCoord2f(var6, var5);
      GL11.glVertex2f(var0 + var2, var1);
      GL11.glEnd();
   }

   private void clearTargetState() {
      this.fadeInAnimation = null;
      this.rwEih = null;
      this.Qqd = null;
      this.zL64 = null;
      this.animatedHealthX = -1.0F;
   }

   public static double setHealthFraction(TargetHUD var0, double var1) {
      return var0.healthFraction = var1;
   }

   public static FontSetting getFontSetting(TargetHUD var0) {
      return var0.font;
   }

   public static SliderSetting getFontScaleSetting(TargetHUD var0) {
      return var0.fontScale;
   }

   public static String formatHealthDifference(float var0) {
      return SVnQ(var0);
   }

   public static TargetHUD$2 computeHudLayout(TargetHUD var0, IFont var1, String var2, String var3, String var4) {
      return var0.computeLayoutMetrics(var1, var2, var3, var4);
   }

   public static float YOciJ(TargetHUD var0) {
      return var0.animatedHealthX;
   }

   public static float QayZ7(TargetHUD var0, float var1) {
      return var0.animatedHealthX = var1;
   }

   public static EntityLivingBase pasKgv(TargetHUD var0) {
      return var0.rwEih;
   }

   public static EntityLivingBase setTargetEntity(TargetHUD var0, EntityLivingBase var1) {
      return var0.rwEih = var1;
   }

   public static void renderTargetHud(TargetHUD var0, Animation var1, EntityLivingBase var2, double var3) {
      var0.renderTargetHudPanel(var1, var2, var3);
   }
}
