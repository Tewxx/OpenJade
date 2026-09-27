// Jade recovery: module: Indicators (render); original class: jade.deps.eLz.cwGL0r
package jade.client.module.render;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.ExternalItemTextures;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils$2;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderTickEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.indicators.IndicatorUtils;
import jade.client.module.render.indicators.IndicatorSettings;
import jade.client.module.render.indicators.ProjectileIcons;
import jade.client.module.render.indicators.ApproachDetector;
import jade.client.module.shared.FormattedTextRenderer;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo
public class Indicators extends Module implements ExternalRenderableModule {
   private final IndicatorSettings indicatorSettings;
   private static final int UPDATE_INTERVAL_TICKS = 5;
   private static final int MAX_SIMULATION_STEPS = 200;
   private static final double FADE_WINDOW_SECONDS = 0.2;
   private int mIr;
   private final Map<Entity, Vec3> previousPositions = new HashMap<>();
   private final Set<Entity> entitys = new HashSet<>();
   private RenderUtils$2 gluProjector;
   private final double[] projectedCoords = new double[3];

   public Indicators() {
      super("Indicators", Category.render);
      this.indicatorSettings = new IndicatorSettings(this);
   }

   @Override
   public void onDisable() {
      ExternalRenderer.invalidateExternalFrame();
      this.previousPositions.clear();
      this.entitys.clear();
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (!this.VIuQ()) {
         if (ClientUtils.isInWorld()) {
            ScaledResolution var2 = new ScaledResolution(mc);
            this.gluProjector = RenderUtils.captureProjectionMatrices(this.gluProjector, var2.getScaleFactor());
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         this.mIr++;
         if (this.mIr % 5 == 0) {
            HashSet var2 = new HashSet();
            this.entitys.clear();
            double var3 = mc.thePlayer.posX;
            double var5 = mc.thePlayer.posY;
            double var7 = mc.thePlayer.posZ;

            for (Entity var10 : mc.theWorld.loadedEntityList) {
               if (var10 != null && var10 != mc.thePlayer) {
                  ItemStack var11 = this.getProjectileIcon(var10);
                  if (var11 != null && this.okjq(var10)) {
                     var2.add(var10);
                     Vec3 var12 = this.previousPositions.get(var10);
                     if (this.indicatorSettings.onlyWhenApproaching.isToggled()) {
                        if (var12 == null) {
                           this.previousPositions.put(var10, new Vec3(var10.posX, var10.posY, var10.posZ));
                           continue;
                        }

                        if (!this.isApproachingPlayer(var10, var12, var3, var5 + mc.thePlayer.height / 2.0, var7)) {
                           this.previousPositions.put(var10, new Vec3(var10.posX, var10.posY, var10.posZ));
                           continue;
                        }
                     }

                     this.entitys.add(var10);
                     this.previousPositions.put(var10, new Vec3(var10.posX, var10.posY, var10.posZ));
                  }
               }
            }

            this.previousPositions.keySet().retainAll(var2);
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (mc.currentScreen == null && ClientUtils.isInWorld()) {
            try {
               for (Entity var3 : this.entitys) {
                  ItemStack var4 = this.getProjectileIcon(var3);
                  if (var4 != null) {
                     if (this.VIuQ()) {
                        this.renderWorldIndicator(var3, var4, var1.partialTicks);
                     } else {
                        this.renderScreenIndicator(var3, var4, var1.partialTicks);
                     }
                  }
               }
            } catch (Exception var5) {
            }
         }
      }
   }

   @Override
   public void guiUpdate() {
      this.indicatorSettings.font.setVisible(true, this);
   }

   private void renderWorldIndicator(Entity var1, ItemStack var2, float var3) {
      if (!var1.isDead && this.okjq(var1) && this.shouldRenderIndicator(var1, var2) && (!this.indicatorSettings.renderOnlyOffscreen.isToggled() || !RenderUtils.isEntityInView(var1))) {
         ExternalRenderBuffer var4 = ExternalRenderer.getActiveRenderBuffer();
         ScreenProjector var5 = ExternalRenderer.getActiveScreenProjector();
         if (var4 != null && var5 != null) {
            double var6 = var1 instanceof EntityFireball ? this.computeFireballImpactTime((EntityFireball)var1, var3) : -1.0;
            int var8 = var1 instanceof EntityFireball ? this.getFadeAlpha(var6) : 255;
            if (var8 > 0) {
               double var9 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3 - mc.getRenderManager().viewerPosX;
               double var11 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var3 - mc.getRenderManager().viewerPosY + var1.height / 2.0F;
               double var13 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3 - mc.getRenderManager().viewerPosZ;
               float var15 = new ScaledResolution(mc).getScaleFactor();
               double var16 = mc.displayWidth / 2.0;
               double var18 = mc.displayHeight / 2.0;
               double var20 = (this.indicatorSettings.radius.getInput() + (this.indicatorSettings.renderItem.isToggled() ? 20 : 0)) * var15;
               if (!var5.projectPoint(var9, var11, var13) || !(Math.hypot(var5.projectedPoint[0] - var16, var5.projectedPoint[1] - var18) < var20 + 15.0F * var15)) {
                  var5.projectOffscreenDirection(var9, var11, var13);
                  double var22 = Math.hypot(var5.projectedPoint[0], var5.projectedPoint[1]);
                  double var24 = var5.projectedPoint[0] / var22;
                  double var26 = var5.projectedPoint[1] / var22;
                  double var28 = var16 + var20 * var24;
                  double var30 = var18 + var20 * var26;
                  int var32 = ClientUtils.YVVZ(this.indicatorSettings.itemColors.isToggled() ? this.getProjectileColor(var2).getRGB() : -1, var8);
                  var4.VogZb(
                     var28 + var26 * 5.0 * var15,
                     var30 - var24 * 5.0 * var15,
                     var28 - var26 * 5.0 * var15,
                     var30 + var24 * 5.0 * var15,
                     var28 + var24 * 9.0 * var15,
                     var30 + var26 * 9.0 * var15,
                     var32
                  );
                  if (this.indicatorSettings.renderItem.isToggled()) {
                     double var33 = var16 + (var20 - 20.0F * var15) * var24;
                     double var35 = var18 + (var20 - 20.0F * var15) * var26;
                     float var37 = var2.getItem() == Items.arrow ? (float)(Math.toDegrees(Math.atan2(var26, var24)) + 45.0) : 0.0F;
                     ExternalItemTextures.drawItemTexture(var4, var2, var33 - 8.0F * var15, var35 - 8.0F * var15, 16.0F * var15, var8 / 255.0F, var37);
                  }

                  int var38 = ClientUtils.YVVZ(-1, var8);
                  if (this.indicatorSettings.renderDistance.isToggled()) {
                     FormattedTextRenderer.drawMultiLineLabel(
                        var4,
                        this.getDistanceFont(),
                        var16 + (var20 - (this.indicatorSettings.renderItem.isToggled() ? 36 : 13) * var15) * var24,
                        var18 + (var20 - (this.indicatorSettings.renderItem.isToggled() ? 36 : 13) * var15) * var26 + 4.0F * var15,
                        7.2F * var15,
                        1.0F,
                        0.0F,
                        0.0F,
                        var38,
                        0,
                        4,
                        (int)mc.thePlayer.getDistanceToEntity(var1) + "m",
                        "",
                        ""
                     );
                  }

                  String var34 = this.getImpactTimeText(var6);
                  if (var1 instanceof EntityFireball && var34 != null) {
                     FormattedTextRenderer.drawMultiLineLabel(
                        var4,
                        this.getImpactTimeFont(),
                        var16 + (var20 + 13.0F * var15) * var24,
                        var18 + (var20 + 13.0F * var15) * var26 + 4.0F * var15,
                        7.2F * var15,
                        1.0F,
                        0.0F,
                        0.0F,
                        var38,
                        0,
                        4,
                        var34,
                        "",
                        ""
                     );
                  }
               }
            }
         }
      }
   }

   private ItemStack getProjectileIcon(Entity var1) {
      return var1 == null ? null : ProjectileIcons.getProjectileIcon(var1);
   }

   private boolean okjq(Entity var1) {
      return ProjectileIcons.shouldRenderProjectile(var1, this.indicatorSettings);
   }

   private void renderScreenIndicator(Entity var1, ItemStack var2, float var3) {
      if (this.okjq(var1)) {
         if (this.shouldRenderIndicator(var1, var2)) {
            if (!this.indicatorSettings.renderOnlyOffscreen.isToggled() || !RenderUtils.isEntityInView(var1)) {
               double var4 = var1 instanceof EntityFireball ? this.computeFireballImpactTime((EntityFireball)var1, var3) : -1.0;
               int var6 = var1 instanceof EntityFireball ? this.getFadeAlpha(var4) : 255;
               if (var6 > 0) {
                  Color var7 = this.getProjectileColor(var2);
                  int var8 = this.indicatorSettings.itemColors.isToggled() ? var7.getRGB() : -1;
                  if (this.gluProjector != null) {
                     double var9 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3 - mc.getRenderManager().viewerPosX;
                     double var11 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var3 - mc.getRenderManager().viewerPosY + var1.height / 2.0F;
                     double var13 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3 - mc.getRenderManager().viewerPosZ;
                     if (RenderUtils.projectToScreen(this.gluProjector, var9, var11, var13, this.projectedCoords)) {
                        ScaledResolution var15 = new ScaledResolution(mc);
                        double var16 = this.projectedCoords[0] - var15.getScaledWidth() / 2.0;
                        double var18 = this.projectedCoords[1] - var15.getScaledHeight() / 2.0;
                        boolean var20 = this.projectedCoords[2] < 1.0003684;
                        if (!var20) {
                           var16 *= -1.0;
                           var18 *= -1.0;
                        }

                        double var21 = Math.atan2(var16, var18);
                        double var23 = Math.atan2(var18, var16) * (float) (180.0 / Math.PI) + 90.0;
                        double var25 = Math.hypot(var16, var18);
                        double var27 = this.indicatorSettings.radius.getInput();
                        if (this.indicatorSettings.renderItem.isToggled()) {
                           var27 += 20.0;
                        }

                        if (!var20 || !(var25 < var27 + 15.0)) {
                           double var29 = var15.getScaledWidth() / 2.0;
                           double var31 = var15.getScaledHeight() / 2.0;
                           double var33 = Math.sin(var21);
                           double var35 = Math.cos(var21);
                           double var37 = var29 + var27 * var33;
                           double var39 = var31 + var27 * var35;
                           GlStateManager.pushMatrix();
                           GlStateManager.translate(var37, var39, 0.0);
                           GlStateManager.rotate((float)var23, 0.0F, 0.0F, 1.0F);
                           GlStateManager.scale(1.0F, 1.0F, 1.0F);
                           Pointers.drawArrowImmediate(ClientUtils.YVVZ(var8, var6), 1.0F);
                           GlStateManager.popMatrix();
                           if (this.indicatorSettings.renderItem.isToggled() && var2 != null) {
                              GlStateManager.pushMatrix();
                              GlStateManager.enableBlend();
                              GlStateManager.color(1.0F, 1.0F, 1.0F, var6 / 255.0F);
                              if (var2.getItem() == Items.arrow) {
                                 var37 = var29 + (var27 - 18.0) * var33;
                                 var39 = var31 + (var27 - 18.0) * var35;
                                 GlStateManager.translate(var37, var39, 0.0);
                                 GlStateManager.scale(1.0F, 1.0F, 1.0F);
                                 GlStateManager.rotate((float)var23 - 45.0F, 0.0F, 0.0F, 1.0F);
                                 mc.getRenderItem().renderItemIntoGUI(var2, -12, -4);
                              } else {
                                 var37 = var29 + (var27 - 20.0) * var33;
                                 var39 = var31 + (var27 - 20.0) * var35;
                                 GlStateManager.translate(var37, var39, 0.0);
                                 GlStateManager.scale(1.0F, 1.0F, 1.0F);
                                 mc.getRenderItem().renderItemIntoGUI(var2, -8, -9);
                              }

                              GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                              GlStateManager.popMatrix();
                           }

                           double var41 = this.indicatorSettings.renderItem.isToggled() && var2 != null ? 36.0 : 13.0;
                           var37 = var29 + (var27 - var41) * var33;
                           var39 = var31 + (var27 - var41) * var35;
                           if (this.indicatorSettings.renderDistance.isToggled()) {
                              this.drawScaledCenteredText(this.getDistanceFont(), (int)mc.thePlayer.getDistanceToEntity(var1) + "m", var37, var39, ClientUtils.YVVZ(-1, var6));
                           }

                           if (var1 instanceof EntityFireball) {
                              String var43 = this.getImpactTimeText(var4);
                              if (var43 != null) {
                                 var37 = var29 + (var27 + 13.0) * var33;
                                 var39 = var31 + (var27 + 13.0) * var35;
                                 this.drawScaledCenteredText(this.getImpactTimeFont(), var43, var37, var39, ClientUtils.YVVZ(-1, var6));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void drawScaledCenteredText(IFont var1, String var2, double var3, double var5, int var7) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(var3, var5, 0.0);
      GlStateManager.scale(0.8, 0.8, 0.8);
      var1.drawString(var2, -var1.getStringWidth(var2) / 2.0F, -4.0F, var7, true);
      GlStateManager.popMatrix();
   }

   private String getImpactTimeText(double var1) {
      return var1 < 0.0 ? null : this.formatImpactTime(var1);
   }

   private int getFadeAlpha(double var1) {
      return !(var1 < 0.0) && !(var1 >= 0.2) ? IndicatorUtils.computeFadeAlpha(var1, 0.2) : 255;
   }

   private double computeFireballImpactTime(EntityFireball var1, float var2) {
      if (mc.thePlayer != null && mc.theWorld != null) {
         double var3 = var1.motionX;
         double var5 = var1.motionY;
         double var7 = var1.motionZ;
         double var9 = var3 * var3 + var5 * var5 + var7 * var7;
         if (var9 <= 1.0E-7) {
            var3 = var1.accelerationX;
            var5 = var1.accelerationY;
            var7 = var1.accelerationZ;
            var9 = var3 * var3 + var5 * var5 + var7 * var7;
         }

         if (var9 <= 1.0E-7) {
            return -1.0;
         } else {
            double var11 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2;
            double var13 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2;
            double var15 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2;
            AxisAlignedBB var17 = this.HqLz(mc.thePlayer.getEntityBoundingBox(), var1);

            for (int var18 = 0; var18 < 200; var18++) {
               Vec3 var19 = new Vec3(var11, var13, var15);
               Vec3 var20 = new Vec3(var11 + var3, var13 + var5, var15 + var7);
               double var21 = this.computeHitFraction(var19, var20, var17);
               if (var21 >= 0.0) {
                  return (var18 + var21) / 20.0;
               }

               var11 += var3;
               var13 += var5;
               var15 += var7;
            }

            return -1.0;
         }
      } else {
         return -1.0;
      }
   }

   private double computeHitFraction(Vec3 var1, Vec3 var2, AxisAlignedBB var3) {
      double var4 = Double.MAX_VALUE;
      MovingObjectPosition var6 = var3.calculateIntercept(var1, var2);
      if (var6 == null && var3.isVecInside(var1)) {
         return 0.0;
      } else {
         if (var6 != null) {
            var4 = var1.squareDistanceTo(var6.hitVec);
         }

         MovingObjectPosition var7 = mc.theWorld.rayTraceBlocks(var1, var2, false, true, false);
         if (var7 != null) {
            double var8 = var1.squareDistanceTo(var7.hitVec);
            if (var8 < var4) {
               var4 = var8;
            }
         }

         if (var4 == Double.MAX_VALUE) {
            return -1.0;
         } else {
            double var10 = var1.squareDistanceTo(var2);
            return var10 <= 1.0E-7 ? 0.0 : Math.min(1.0, Math.sqrt(var4 / var10));
         }
      }
   }

   private AxisAlignedBB HqLz(AxisAlignedBB var1, EntityFireball var2) {
      double var3 = var2.width * 0.5;
      double var5 = var2.height;
      double var7 = 0.3;
      return new AxisAlignedBB(
         var1.minX - var3 - var7, var1.minY - var5 - var7, var1.minZ - var3 - var7, var1.maxX + var3 + var7, var1.maxY + var7, var1.maxZ + var3 + var7
      );
   }

   private String formatImpactTime(double var1) {
      return IndicatorUtils.formatSeconds(var1);
   }

   private Color getProjectileColor(ItemStack var1) {
      if (var1 == null) {
         return Color.WHITE;
      } else if (var1.getItem() == Items.ender_pearl) {
         return new Color(210, 0, 255);
      } else if (var1.getItem() == Items.fire_charge) {
         return new Color(255, 150, 0);
      } else {
         return var1.getItem() == Items.egg ? new Color(255, 238, 154) : Color.WHITE;
      }
   }

   private boolean isApproachingPlayer(Entity var1, Vec3 var2, double var3, double var5, double var7) {
      return ApproachDetector.isApproaching(var2.xCoord, var2.yCoord, var2.zCoord, var1.posX, var1.posY, var1.posZ, var1.height, var3, var5, var7);
   }

   private boolean shouldRenderIndicator(Entity var1, ItemStack var2) {
      return true;
   }

   private String getSelectedFontName() {
      return this.indicatorSettings.font == null ? FontManager.getDefaultHudFontName() : this.indicatorSettings.font.getResolvedFontName();
   }

   private IFont getDistanceFont() {
      return FontManager.getNametagRenderer(this.getSelectedFontName());
   }

   private IFont getImpactTimeFont() {
      return FontManager.getHudRenderer(this.getSelectedFontName(), 0.75F);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Items",
            "Scale",
            new String[]{"render arrows", "render ender pearls", "render fireballs", "render eggs", "render snowballs"},
            new String[]{"Arrows", "Ender pearls", "Fireballs", "Eggs", "Snowballs"}
         ),
         buildSettingAlias("Visuals", "Items", new String[]{"item colors", "render item", "render distance"}, new String[]{"Item colors", "Item icon", "Distance"}),
         buildSettingAlias("Conditions", "Visuals", new String[]{"only when approaching", "render only offscreen"}, new String[]{"Approaching", "Off-screen only"})
      );
   }
}
