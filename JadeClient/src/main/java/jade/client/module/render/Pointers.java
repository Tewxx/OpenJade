// Jade recovery: module: Pointers (render); original class: jade.deps.eLz.A2c8GVQvn
package jade.client.module.render;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.RenderUtils$2;
import jade.client.common.RenderUtils;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderTickEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.other.AntiBot;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = "Arrows")
public class Pointers extends Module implements ExternalRenderableModule {
   private static final double EYaU = 24.0;
   private static final double EIjVfx = 16.0;
   public static final double iXdrN4 = 0.78;
   private static final float VISIBILITY_FADE_IN_RATE = 0.18F;
   private static final float VISIBILITY_FADE_OUT_RATE = 0.14F;
   private static final float ARROW_GROW_RATE = 4.2F;
   private static final float ARROW_SHRINK_RATE = 3.2F;
   private static final double[][] ARROW_MESH = buildArrowMesh();
   private ColorSetting color;
   private BooleanSetting teamColour;
   private BooleanSetting hideTeammates;
   private BooleanSetting onlyInGame;
   private SliderSetting range;
   private SliderSetting maxScreenDistance;
   private BooleanSetting renderOffscreen;
   private final Map<UUID, Pointers$1> trackedPlayers = new HashMap<>();
   private RenderUtils$2 screenProjector;
   private final double[] omkFhw = new double[3];
   private long ylbzsu = System.nanoTime();

   public Pointers() {
      super("Pointers", Category.render);
      this.registerSetting(
         this.color = new ColorSetting(
            "Color",
            255,
            255,
            255
         )
      );
      this.registerSetting(
         this.teamColour = new BooleanSetting(
            "Team Colour",
            true,
            new String[]{"Team color"}
         )
      );
      this.registerSetting(
         this.hideTeammates = new BooleanSetting(
            "Hide Teammates",
            true,
            new String[]{"Hide teammates"}
         )
      );
      this.registerSetting(
         this.onlyInGame = new BooleanSetting(
            "Only in-game", false
         )
      );
      this.registerSetting(
         this.range = new SliderSetting(
            "Range", "m", 80.0, 10.0, 200.0, 5.0
         )
      );
      this.registerSetting(
         this.maxScreenDistance = new SliderSetting(
            "Max screen distance",
            "px",
            120.0,
            30.0,
            240.0,
            5.0
         )
      );
      this.registerSetting(
         this.renderOffscreen = new BooleanSetting(
            "Render offscreen",
            false,
            new String[]{"Render only offscreen"}
         )
      );
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         this.updateTrackedPlayers();
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.screenProjector = null;
      } else {
         ScaledResolution var2 = new ScaledResolution(mc);
         this.screenProjector = RenderUtils.captureProjectionMatrices(this.screenProjector, var2.getScaleFactor());
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (mc.currentScreen == null && ClientUtils.isInWorld() && this.screenProjector != null) {
            long var2 = System.nanoTime();
            float var4 = Math.min(0.05F, Math.max(0.0F, (float)(var2 - this.ylbzsu) / 1.0E9F));
            this.ylbzsu = var2;
            ScaledResolution var5 = new ScaledResolution(mc);
            double var6 = var5.getScaledWidth() / 2.0;
            double var8 = var5.getScaledHeight() / 2.0;
            double var10 = Math.min(var5.getScaledWidth(), var5.getScaledHeight()) / 2.0 - 16.0;

            for (Pointers$1 var13 : this.trackedPlayers.values()) {
               if (Pointers$1.getTrackedPlayer(var13) != null && Pointers$1.getVisibility(var13) > 0.01F) {
                  this.updateAndRenderPointer(var13, var1.partialTicks, var4, var5.getScaledWidth(), var5.getScaledHeight(), var6, var8, var10);
               }
            }
         }
      }
   }

   private void updateTrackedPlayers() {
      if (ClientUtils.isInWorld() && mc.theWorld != null) {
         for (Pointers$1 var2 : this.trackedPlayers.values()) {
            Pointers$1.setTracked(var2, false);
         }

         if (this.onlyInGame.isToggled() && ClientUtils.getBedWarsBoardType() != 2) {
            this.updateVisibility();
         } else {
            double var8 = this.range.getInput();

            for (EntityPlayer var4 : mc.theWorld.playerEntities) {
               if (var4 != null
                  && var4 != mc.thePlayer
                  && !AntiBot.shouldHideEntity(var4)
                  && (!ClientUtils.isTeammate(var4) || !this.hideTeammates.isToggled())
                  && !(mc.thePlayer.getDistanceToEntity(var4) > var8)) {
                  int var5 = this.color.getArgb();
                  if (this.teamColour.isToggled()) {
                     int var6 = ClientUtils.oCoqd(var4);
                     if (var6 != -1) {
                        var5 = ClientUtils.YVVZ(var6, 255);
                     }
                  }

                  UUID var9 = var4.getUniqueID();
                  Pointers$1 var7 = this.trackedPlayers.get(var9);
                  if (var7 == null) {
                     var7 = new Pointers$1();
                     this.trackedPlayers.put(var9, var7);
                  }

                  Pointers$1.nlag(var7, var4, var5, true);
               }
            }

            this.updateVisibility();
         }
      } else {
         this.trackedPlayers.clear();
      }
   }

   private void updateVisibility() {
      Iterator var1 = this.trackedPlayers.entrySet().iterator();

      while (var1.hasNext()) {
         Pointers$1 var2 = (Pointers$1)((Entry)var1.next()).getValue();
         if (Pointers$1.isTracked(var2)) {
            Pointers$1.setVisibility(var2, Math.min(1.0F, Pointers$1.getVisibility(var2) + 0.18F));
         } else {
            Pointers$1.setVisibility(var2, Math.max(0.0F, Pointers$1.getVisibility(var2) - 0.14F));
            if (Pointers$1.getVisibility(var2) <= 0.0F) {
               var1.remove();
            }
         }
      }
   }

   private void updateAndRenderPointer(Pointers$1 var1, float var2, float var3, int var4, int var5, double var6, double var8, double var10) {
      EntityPlayer var12 = Pointers$1.getTrackedPlayer(var1);
      double var13 = var12.lastTickPosX + (var12.posX - var12.lastTickPosX) * var2 - mc.getRenderManager().viewerPosX;
      double var15 = var12.lastTickPosY + (var12.posY - var12.lastTickPosY) * var2 - mc.getRenderManager().viewerPosY + var12.height / 2.0;
      double var17 = var12.lastTickPosZ + (var12.posZ - var12.lastTickPosZ) * var2 - mc.getRenderManager().viewerPosZ;
      if (!RenderUtils.projectToScreen(this.screenProjector, var13, var15, var17, this.omkFhw)) {
         this.WDtI0(var1, false, var3);
         this.mfvDg(var1);
      } else {
         double var19 = this.omkFhw[0] - var6;
         double var21 = this.omkFhw[1] - var8;
         boolean var23 = this.omkFhw[2] < 1.0003684;
         boolean var24 = var23 && this.omkFhw[0] >= 0.0 && this.omkFhw[0] <= var4 && this.omkFhw[1] >= 0.0 && this.omkFhw[1] <= var5;
         boolean var25 = this.renderOffscreen.isToggled() ? !var24 : var23;
         this.WDtI0(var1, var25, var3);
         float var26 = Pointers$1.getVisibility(var1) * Pointers$1.getArrowScale(var1);
         if (!var23) {
            if (!this.renderOffscreen.isToggled()) {
               this.mfvDg(var1);
               return;
            }

            var19 *= -1.0;
            var21 *= -1.0;
         }

         double var27 = Math.hypot(var19, var21);
         if (!(var27 < 0.001)) {
            double var29 = Math.max(24.0, Math.min(this.maxScreenDistance.getInput(), var10));
            double var31 = Math.min(1.0, Math.max(0.0, mc.thePlayer.getDistanceToEntity(var12) / this.range.getInput()));
            double var33 = 24.0 + (var29 - 24.0) * var31;
            double var35 = var6 + var19 / var27 * var33;
            double var37 = var8 + var21 / var27 * var33;
            double var39 = Math.atan2(var21, var19) * (float) (180.0 / Math.PI) + 90.0;
            Pointers$1.rKaqs(var1, var35, var37, var39);
            if (!(var26 <= 0.01F)) {
               this.renderArrow(var35, var37, var39, Pointers$1.dHqaD(var1), var26);
            }
         }
      }
   }

   private void mfvDg(Pointers$1 var1) {
      float var2 = Pointers$1.getVisibility(var1) * Pointers$1.getArrowScale(var1);
      if (Pointers$1.PpU5(var1) && !(var2 <= 0.01F)) {
         this.renderArrow(Pointers$1.getScreenX(var1), Pointers$1.getScreenY(var1), Pointers$1.iBjoKj(var1), Pointers$1.dHqaD(var1), var2);
      }
   }

   private void renderArrow(double var1, double var3, double var5, int var7, float var8) {
      if (this.VIuQ()) {
         this.renderArrowToExternalBuffer(var1, var3, var5, var7, var8);
      } else {
         GlStateManager.pushMatrix();
         GlStateManager.translate(var1, var3, 0.0);
         GlStateManager.rotate((float)var5, 0.0F, 0.0F, 1.0F);
         drawArrowImmediate(var7, var8);
         GlStateManager.popMatrix();
      }
   }

   private void renderArrowToExternalBuffer(double var1, double var3, double var5, int var7, float var8) {
      ExternalRenderBuffer var9 = ExternalRenderer.getActiveRenderBuffer();
      if (var9 != null) {
         float var10 = new ScaledResolution(mc).getScaleFactor();
         double var11 = Math.toRadians(var5);
         double var13 = Math.sin(var11);
         double var15 = Math.cos(var11);
         double var17 = var1 * var10;
         double var19 = var3 * var10;
         int var21 = ClientUtils.YVVZ(var7, Math.round((var7 >>> 24 & 0xFF) * var8));
         double[][] var22 = new double[ARROW_MESH.length][2];

         for (int var23 = 0; var23 < ARROW_MESH.length; var23++) {
            double var24 = ARROW_MESH[var23][0] * var10;
            double var26 = ARROW_MESH[var23][1] * var10;
            var22[var23][0] = var17 + var24 * var15 - var26 * var13;
            var22[var23][1] = var19 + var24 * var13 + var26 * var15;
         }

         for (int var28 = 1; var28 + 1 < var22.length; var28++) {
            var9.VogZb(var22[0][0], var22[0][1], var22[var28][0], var22[var28][1], var22[var28 + 1][0], var22[var28 + 1][1], var21);
         }
      }
   }

   private void WDtI0(Pointers$1 var1, boolean var2, float var3) {
      if (var2) {
         Pointers$1.setArrowScale(var1, Math.min(1.0F, Pointers$1.getArrowScale(var1) + 4.2F * var3));
      } else {
         Pointers$1.setArrowScale(var1, Math.max(0.0F, Pointers$1.getArrowScale(var1) - 3.2F * var3));
      }
   }

   public static void drawArrowImmediate(int var0, float var1) {
      float var2 = (var0 >> 16 & 0xFF) / 255.0F;
      float var3 = (var0 >> 8 & 0xFF) / 255.0F;
      float var4 = (var0 & 0xFF) / 255.0F;
      float var5 = (var0 >> 24 & 0xFF) / 255.0F * var1;
      GL11.glPushAttrib(1048575);

      try {
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDisable(2884);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         float var6 = brighten(var2);
         float var7 = brighten(var3);
         float var8 = brighten(var4);
         GL11.glBegin(6);
         GL11.glColor4f(var6, var7, var8, var5);
         GL11.glVertex2d(0.0, 1.2);
         GL11.glColor4f(var2, var3, var4, var5);

         for (double[] var12 : ARROW_MESH) {
            GL11.glVertex2d(var12[0], var12[1]);
         }

         double[] var17 = ARROW_MESH[0];
         GL11.glVertex2d(var17[0], var17[1]);
         GL11.glEnd();
         GL11.glLineWidth(1.6F);
         GL11.glColor4f(Liaal7(var2), Liaal7(var3), Liaal7(var4), Math.min(1.0F, var5 + 0.1F));
         GL11.glBegin(2);

         for (double[] var13 : ARROW_MESH) {
            GL11.glVertex2d(var13[0], var13[1]);
         }

         GL11.glEnd();
      } finally {
         GL11.glPopAttrib();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private static float brighten(float var0) {
      return Math.min(1.0F, var0 + (1.0F - var0) * 0.42F);
   }

   private static float Liaal7(float var0) {
      return Math.max(0.0F, var0 * 0.42F);
   }

   private static double[][] buildArrowMesh() {
      double[][] var0 = new double[][]{{0.0, -7.0200000000000005}, {5.46, 5.07}, {-5.46, 5.07}};
      ArrayList var1 = new ArrayList();
      double var2 = 0.18;
      byte var4 = 5;

      for (int var5 = 0; var5 < var0.length; var5++) {
         double[] var6 = var0[(var5 + var0.length - 1) % var0.length];
         double[] var7 = var0[var5];
         double[] var8 = var0[(var5 + 1) % var0.length];
         double var9 = var7[0] + (var6[0] - var7[0]) * var2;
         double var11 = var7[1] + (var6[1] - var7[1]) * var2;
         double var13 = var7[0] + (var8[0] - var7[0]) * var2;
         double var15 = var7[1] + (var8[1] - var7[1]) * var2;

         for (int var17 = 0; var17 <= var4; var17++) {
            double var18 = (double)var17 / var4;
            double var20 = 1.0 - var18;
            double var22 = var20 * var20 * var9 + 2.0 * var20 * var18 * var7[0] + var18 * var18 * var13;
            double var24 = var20 * var20 * var11 + 2.0 * var20 * var18 * var7[1] + var18 * var18 * var15;
            var1.add(new double[]{var22, var24});
         }
      }

      return (double[][])var1.toArray(new double[var1.size()][]);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Target", "Width", new String[]{"hide teammates", "only in-game"}, new String[]{"Hide teammates", "In game only"}),
         buildSettingAlias(
            "Visuals",
            "Target",
            new String[]{"team color", "off-screen only", "render offscreen"},
            new String[]{"Team color", "Off-screen only", "Render off-screen"}
         )
      );
   }
}
