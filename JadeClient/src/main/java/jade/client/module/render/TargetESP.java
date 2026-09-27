// Jade recovery: module: Target ESP (render); original class: jade.deps.eLz.U0p4E8
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.RenderUtils$1;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.AimAssist;
import jade.client.module.render.targetesp.TargetEspMode;
import jade.client.setting.ColorSetting;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.XorShiftMultiplyConstantCipherFour;

import jade.deps.loader107.DoubleMultiplyConstantCipherFour;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = "TargetESP")
public class TargetESP extends Module implements ExternalRenderableModule {
   private static final String[] COLOR_MODE_LABELS = new String[]{"Custom", "Theme"};
   private static final int COLOR_MODE_CUSTOM = 0;
   private static final int COLOR_MODE_THEME = 1;
   private static final int RING_SEGMENTS = 64;
   private static final int uHfv = 28;
   private static final double ZVVdJ = 0.04;
   private static final float[] xsS = new float[64];
   private static final float[] SvY = new float[64];
   private final SliderSetting mode;
   private final SliderSetting colorMode;
   private final ColorSetting color;
   private final SliderSetting themeOpacity;
   private final SliderSetting thickness;
   private final double[] ringVerticalOffsets = new double[28];
   private final double[] Kr8 = new double[28];
   private final float[] ringAlphas = new float[28];
   private final double[][] projectedX = new double[28][64];
   private final double[][] projectedY = new double[28][DoubleMultiplyConstantCipherFour.decodeInt(
      -1126755234, 875515721
   )];
   private final boolean[][] visiblePoints = new boolean[28][XorShiftMultiplyConstantCipherFour.JhrTp(
      -57815805, 455683893
   )];
   private EntityPlayer entityPlayer;

   public TargetESP() {
      super("Target ESP", Category.render);
      this.registerSetting(this.mode = new SliderSetting("Mode", TargetEspMode.RING.ordinal(), TargetEspMode.labels()));
      this.registerSetting(
         this.colorMode = new SliderSetting(
            "Color mode", 0, COLOR_MODE_LABELS
         )
      );
      this.registerSetting(
         this.color = new ColorSetting(
            "Color",
            67,
            232,
            134,
            255
         )
      );
      this.registerSetting(
         this.themeOpacity = new SliderSetting(
            "Theme opacity", "%", 100.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(this.thickness = new SliderSetting("Thickness", 2.0, 1.0, 5.0, 0.1));
      this.themeOpacity.visible = false;
   }

   @Override
   public void onDisable() {
      this.entityPlayer = null;
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.getColorModeIndex() == 0;
      this.color.setVisible(var1, this);
      this.themeOpacity.setVisible(!var1, this);
      this.thickness.setVisible(true, this);
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      if (var1 == this.colorMode || var1 == this.mode) {
         this.guiUpdate();
      }
   }

   @Override
   public String getInfo() {
      return this.getTargetEspMode().getLabel();
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         AimAssist var2 = Jade.getModuleManager().getModule(AimAssist.class);
         this.entityPlayer = var2 == null ? null : var2.GHPA();
      } else {
         this.entityPlayer = null;
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      EntityPlayer var2 = this.entityPlayer;
      if (!this.isValidTarget(var2)) {
         this.entityPlayer = null;
      } else {
         TargetEspMode var3 = this.getTargetEspMode();
         if (this.VIuQ()) {
            if (var3 == TargetEspMode.BOX) {
               ESP var5 = Jade.getModuleManager().getModule(ESP.class);
               if (var5 != null) {
                  var5.renderExternalBox(var2, this.getRenderColor(), (float)this.thickness.getInput());
               }
            } else if (var3 == TargetEspMode.RING) {
               this.renderRingInWorld(var2, var1.YDn0, this.getRenderColor());
            }
         } else {
            if (var3 == TargetEspMode.RING) {
               this.qaQbf(var2, var1.YDn0, this.getRenderColor());
            } else if (var3 == TargetEspMode.BOX) {
               ESP var4 = Jade.getModuleManager().getModule(ESP.class);
               if (var4 != null) {
                  var4.renderLivingBox(var2, this.getRenderColor(), (float)this.thickness.getInput());
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void izShj(RenderWorldLastEvent var1) {
      EntityPlayer var2 = this.entityPlayer;
      if (!this.VIuQ() && this.getTargetEspMode() == TargetEspMode.OUTLINE && this.isValidTarget(var2)) {
         ESP var3 = Jade.getModuleManager().getModule(ESP.class);
         if (var3 != null) {
            var3.renderGradientOutline(var2, var1.YDn0, (float)this.thickness.getInput(), this.getThemeColors(), this.vDd3());
         }
      }
   }

   private void renderRingInWorld(EntityPlayer var1, float var2, int var3) {
      ExternalRenderBuffer var4 = ExternalRenderer.getActiveRenderBuffer();
      ScreenProjector var5 = ExternalRenderer.getActiveScreenProjector();
      if (var4 != null && var5 != null && (var3 >>> 24 & 0xFF) != 0) {
         RenderManager var6 = mc.getRenderManager();
         double var7 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2 - var6.viewerPosX;
         double var9 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2 - var6.viewerPosY;
         double var11 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2 - var6.viewerPosZ;
         AxisAlignedBB var13 = var1.getEntityBoundingBox();
         double var14 = Math.max(0.1, var13.maxY - var13.minY);
         double var16 = System.currentTimeMillis() % 1800L / 1800.0 * Math.PI * 2.0;
         float var18 = (var3 >>> 24 & 0xFF) / 255.0F;
         double var19 = var1.width * 0.5 + 0.2;

         for (int var21 = 0; var21 < 28; var21++) {
            double var22 = var16 - var21 * 0.04;
            double var24 = (Math.sin(var22) + 1.0) * 0.5 * var14;
            float var26 = 1.0F - var21 / 27.0F;
            this.ringVerticalOffsets[var21] = var24;
            this.Kr8[var21] = var19 + Math.sin(var24 / var14 * Math.PI) * 0.05;
            this.ringAlphas[var21] = var26 * var26 * var18 * 0.72F;

            for (int var27 = 0; var27 < 64; var27++) {
               this.visiblePoints[var21][var27] = var5.projectPoint(var7 + xsS[var27] * this.Kr8[var21], var9 + var24, var11 + SvY[var27] * this.Kr8[var21]);
               if (this.visiblePoints[var21][var27]) {
                  this.projectedX[var21][var27] = var5.projectedPoint[0];
                  this.projectedY[var21][var27] = var5.projectedPoint[1];
               }
            }
         }

         for (int var28 = 0; var28 < 27; var28++) {
            int var30 = ClientUtils.YVVZ(var3, Math.round((this.ringAlphas[var28] + this.ringAlphas[var28 + 1]) * 0.5F * 255.0F));

            for (int var23 = 0; var23 < 64; var23++) {
               int var32 = var23 + 1 == 64 ? 0 : var23 + 1;
               if (this.visiblePoints[var28][var23] && this.visiblePoints[var28][var32] && this.visiblePoints[var28 + 1][var23] && this.visiblePoints[var28 + 1][var32]) {
                  var4.VogZb(
                     this.projectedX[var28][var23],
                     this.projectedY[var28][var23],
                     this.projectedX[var28][var32],
                     this.projectedY[var28][var32],
                     this.projectedX[var28 + 1][var32],
                     this.projectedY[var28 + 1][var32],
                     var30
                  );
                  var4.VogZb(
                     this.projectedX[var28][var23],
                     this.projectedY[var28][var23],
                     this.projectedX[var28 + 1][var32],
                     this.projectedY[var28 + 1][var32],
                     this.projectedX[var28 + 1][var23],
                     this.projectedY[var28 + 1][var23],
                     var30
                  );
               }
            }
         }

         for (int var29 = 0; var29 < 64; var29++) {
            int var31 = var29 + 1 == 64 ? 0 : var29 + 1;
            var5.drawProjectedLine(
               var7 + xsS[var29] * this.Kr8[0],
               var9 + this.ringVerticalOffsets[0],
               var11 + SvY[var29] * this.Kr8[0],
               var7 + xsS[var31] * this.Kr8[0],
               var9 + this.ringVerticalOffsets[0],
               var11 + SvY[var31] * this.Kr8[0],
               var4,
               var3,
               (float)this.thickness.getInput()
            );
         }
      }
   }

   public boolean isBoxEspTarget(EntityLivingBase var1) {
      return this.isEnabled() && this.getTargetEspMode() == TargetEspMode.BOX && var1 != null && var1 == this.entityPlayer && this.isValidTarget(this.entityPlayer);
   }

   private boolean isValidTarget(EntityPlayer var1) {
      return var1 != null && ClientUtils.isInWorld() && var1.worldObj == mc.theWorld && var1.deathTime == 0 && !var1.isDead;
   }

   private TargetEspMode getTargetEspMode() {
      return TargetEspMode.fromSetting(this.mode.getInput());
   }

   private int getColorModeIndex() {
      return (int)this.colorMode.getInput() == 1 ? 1 : 0;
   }

   private int vDd3() {
      return this.getColorModeIndex() == 0 ? this.color.JIjrD() : Math.max(0, Math.min(255, (int)Math.round(this.themeOpacity.getInput() * 2.55)));
   }

   private int getRenderColor() {
      int var1 = this.getColorModeIndex() == 1 ? Arraylist.xQec0(0.0) : this.color.getRgb();
      return ClientUtils.YVVZ(var1, this.vDd3());
   }

   private List<Integer> getThemeColors() {
      if (this.getColorModeIndex() == 0) {
         return Collections.singletonList(this.color.getRgb());
      } else {
         List var1 = Arraylist.getGradientColors();
         return var1.isEmpty() ? Collections.singletonList(Arraylist.xQec0(0.0)) : var1;
      }
   }

   private void qaQbf(EntityPlayer var1, float var2, int var3) {
      if (RenderUtils.isEntityInView(var1) && (var3 >>> 24 & 0xFF) != 0) {
         RenderManager var4 = mc.getRenderManager();
         double var5 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2 - var4.viewerPosX;
         double var7 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2 - var4.viewerPosY;
         double var9 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2 - var4.viewerPosZ;
         double var11 = Math.max(0.1, var1.getEntityBoundingBox().maxY - var1.getEntityBoundingBox().minY);
         double var13 = var1.width * 0.5 + 0.2;
         double var15 = System.currentTimeMillis() % 1800L / 1800.0 * Math.PI * 2.0;
         float var17 = (var3 >> 16 & 0xFF) / 255.0F;
         float var18 = (var3 >> 8 & 0xFF) / 255.0F;
         float var19 = (var3 & 0xFF) / 255.0F;
         float var20 = (var3 >>> 24 & 0xFF) / 255.0F;

         for (int var21 = 0; var21 < 28; var21++) {
            double var22 = var15 - var21 * 0.04;
            double var24 = (Math.sin(var22) + 1.0) * 0.5 * var11;
            float var26 = var21 / 27.0F;
            float var27 = 1.0F - var26;
            this.ringVerticalOffsets[var21] = var24;
            this.Kr8[var21] = var13 + Math.sin(var24 / var11 * Math.PI) * 0.05;
            this.ringAlphas[var21] = var27 * var27 * var20 * 0.72F;
         }

         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         RenderUtils$1 var38 = null;

         try {
            var38 = RenderUtils.uyB6();
            GL11.glTranslated(var5, var7, var9);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glShadeModel(7425);
            GL11.glBegin(4);

            for (int var39 = 0; var39 < 27; var39++) {
               double var23 = this.ringVerticalOffsets[var39];
               double var25 = this.ringVerticalOffsets[var39 + 1];
               double var41 = this.Kr8[var39];
               double var29 = this.Kr8[var39 + 1];
               float var31 = this.ringAlphas[var39];
               float var32 = this.ringAlphas[var39 + 1];

               for (int var33 = 0; var33 < 64; var33++) {
                  int var34 = var33 + 1 == 64 ? 0 : var33 + 1;
                  this.CWWkP(var33, var23, var41, var17, var18, var19, var31);
                  this.CWWkP(var34, var23, var41, var17, var18, var19, var31);
                  this.CWWkP(var34, var25, var29, var17, var18, var19, var32);
                  this.CWWkP(var33, var23, var41, var17, var18, var19, var31);
                  this.CWWkP(var34, var25, var29, var17, var18, var19, var32);
                  this.CWWkP(var33, var25, var29, var17, var18, var19, var32);
               }
            }

            GL11.glEnd();
            GL11.glLineWidth((float)this.thickness.getInput());
            GL11.glColor4f(var17, var18, var19, var20);
            GL11.glBegin(2);

            for (int var40 = 0; var40 < 64; var40++) {
               GL11.glVertex3d(xsS[var40] * this.Kr8[0], this.ringVerticalOffsets[0], SvY[var40] * this.Kr8[0]);
            }

            GL11.glEnd();
         } finally {
            RenderUtils.restoreLightmapState(var38);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glLineWidth(1.0F);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
         }
      }
   }

   private void CWWkP(int var1, double var2, double var4, float var6, float var7, float var8, float var9) {
      GL11.glColor4f(var6, var7, var8, var9);
      GL11.glVertex3d(xsS[var1] * var4, var2, SvY[var1] * var4);
   }

   static {
      for (int var0 = 0;
         var0 < 64;
         var0++
      ) {
         double var1 = (Math.PI * 2) * var0 / 64.0;
         xsS[var0] = (float)Math.cos(var1);
         SvY[var0] = (float)Math.sin(var1);
      }
   }
}
