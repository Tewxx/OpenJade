// Jade recovery: module: ESP (render); original class: jade.deps.eLz.FiuflJ
package jade.client.module.render;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.RenderUtils$1;
import jade.client.common.RenderUtils$2;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderPlayerPostEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.SkywarsUtils;
import jade.client.module.other.AntiBot;
import jade.client.module.player.Freecam;
import jade.client.module.render.esp.EspMode;
import jade.client.module.render.esp.GlowShader;
import jade.client.module.render.esp.GradientOutlineShader;
import jade.client.module.render.esp.TintShader;
import jade.client.module.render.esp.PlayerModelBoxRenderer$2;
import jade.client.module.render.esp.PlayerModelBoxRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import jade.inject.RuntimeAccess;
import jade.mixin.impl.accessor.IAccessorEntityRenderer;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import java.awt.Color;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

@ModuleInfo(aliases = {"PlayerESP", "MobESP"})
public class ESP extends Module implements ExternalRenderableModule {
   private final DescriptionSetting description;
   private static final String[] sbMf = EspMode.labels();
   private static final String[] TWO_D_MODE_LABELS = new String[]{"Corners", "Full"};
   private static final String[] yXk = new String[]{"Health", "Hud"};
   private static final int MPIhHa = EspMode.TWO_D.ordinal();
   private static final int MODE_THREE_D = EspMode.THREE_D.ordinal();
   private static final int MODE_OUTLINE = EspMode.OUTLINE.ordinal();
   private static final int Yjr = EspMode.BOX.ordinal();
   private static final int MODE_SKELETON = EspMode.SKELETON.ordinal();
   private static final int bry = 0;
   private static final int TWO_D_MODE_FULL = 1;
   private static final int HEALTH_BAR_MODE_HEALTH = 0;
   private static final int jzooL = 1;
   private static final float zfRg = (float) (180.0 / Math.PI);
   private static final double RKyo = 0.1;
   private static final double ESP_BOX_SIDE_MARGIN = 0.1;
   private static final double ESP_BOX_TOP_MARGIN = 0.2;
   public static boolean overlayPassActive = false;
   public SliderSetting mode;
   public SliderSetting render2dStyle;
   public SliderSetting thickness;
   public SliderSetting opacity;
   public ColorSetting defaultColour2;
   public ColorSetting defaultColour;
   public ColorSetting friendColour;
   public ColorSetting enemyColour;
   public BooleanSetting teamColor;
   public BooleanSetting healthBar;
   public SliderSetting healthBarColor;
   public BooleanSetting render2dOutline;
   public BooleanSetting healthBarOutline;
   public BooleanSetting mobs;
   public BooleanSetting onlyBedwarsMobs;
   public SliderSetting mobMode;
   public ColorSetting mobColor;
   private final SliderSetting maxDistance;
   private final List<ESP$1> trackedEntities = new ArrayList<>();
   private final List<ESP$1> ONU = new ArrayList<>();
   private final Map<UUID, PlayerModelBoxRenderer$2> playerModelRotations = new HashMap<>();
   private final double[] Dty7 = new double[3];
   private RenderUtils$2 projectionCache;
   private int trackedCount;
   private int projectedCount;
   private Framebuffer BTItf;
   private Framebuffer outlineFramebuffer;
   private GlowShader glowShader;
   private GradientOutlineShader gradientOutlineShader;
   private TintShader tintShader;
   private boolean doAl;
   private Class<?> dyNo;
   private Method method;
   private final IntBuffer intBuffer = BufferUtils.createIntBuffer(4);
   private final int[] scissorRect = new int[4];

   public ESP() {
      super("ESP", Category.render, 0);
      this.registerSetting(
         this.description = new DescriptionSetting(
            "External: effect modes use overlay-safe projected equivalents; keep injector open."
         )
      );
      this.description.visible = false;
      this.registerSetting(this.mode = new SliderSetting("Mode", MPIhHa, sbMf));
      this.registerSetting(this.render2dStyle = new SliderSetting("2D Mode", 1, TWO_D_MODE_LABELS));
      this.registerSetting(this.thickness = new SliderSetting("Thickness", 1.0, 1.0, 5.0, 0.1));
      this.registerSetting(
         this.opacity = new SliderSetting(
            "Opacity", "%", 100.0, 0.0, 100.0, 1.0
         )
      );
      this.opacity.visible = false;
      this.registerSetting(
         this.defaultColour2 = this.defaultColour = new ColorSetting(
            "Default colour",
            0,
            255,
            0,
            255
         )
      );
      this.registerSetting(
         this.friendColour = new ColorSetting(
            "Friend colour",
            0,
            255,
            0,
            255
         )
      );
      this.registerSetting(
         this.enemyColour = new ColorSetting(
            "Enemy colour",
            255,
            96,
            96,
            255
         )
      );
      this.registerSetting(
         this.teamColor = new BooleanSetting(
            "Team color", false
         )
      );
      this.registerSetting(
         this.healthBar = new BooleanSetting(
            "Health bar", true
         )
      );
      this.registerSetting(this.healthBarColor = new SliderSetting("Health Bar Color", 0, yXk));
      this.registerSetting(this.render2dOutline = new BooleanSetting("2D outline", false));
      this.registerSetting(this.healthBarOutline = new BooleanSetting("Health Bar Outline", false));
      this.registerSetting(
         this.mobs = new BooleanSetting(
            "Mobs", true
         )
      );
      this.registerSetting(this.onlyBedwarsMobs = new BooleanSetting("Only Bedwars Mobs", false));
      this.registerSetting(this.mobMode = new SliderSetting("Mob Mode", MPIhHa, sbMf));
      this.registerSetting(
         this.mobColor = new ColorSetting(
            "Mob Color", 255, 96, 96, 255
         )
      );
      this.registerSetting(this.maxDistance = new SliderSetting("Max distance", 128.0, 32.0, 256.0, 8.0));
   }

   @Override
   public void onDisable() {
      ExternalRenderer.invalidateExternalFrame();
      this.clearEntityCaches();
      this.playerModelRotations.clear();
   }

   @Override
   public void guiUpdate() {
      this.description.setVisible(this.VIuQ(), this);
      int var1 = this.Vkr8();
      boolean var2 = var1 == MPIhHa;
      this.thickness.setVisible(var1 == MPIhHa || var1 == MODE_THREE_D || var1 == MODE_OUTLINE || var1 == MODE_SKELETON, this);
      this.render2dStyle.setVisible(var2, this);
      this.render2dOutline.setVisible(var2, this);
      this.healthBarColor.setVisible(this.healthBar.isToggled(), this);
      this.healthBarOutline.setVisible(this.healthBar.isToggled(), this);
      this.onlyBedwarsMobs.setVisible(this.mobs.isToggled(), this);
      this.mobMode.setVisible(this.mobs.isToggled(), this);
      this.mobColor.setVisible(this.mobs.isToggled(), this);
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      if (var1 == this.mode || var1 == this.mobMode) {
         this.guiUpdate();
      }
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.healthBar || var1 == this.mobs) {
         this.guiUpdate();
      }
   }

   @Override
   public String getInfo() {
      return EspMode.fromSetting(this.Vkr8()).getLabel();
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (ClientUtils.isInWorld() && mc.theWorld != null) {
            this.eimw();
         } else {
            this.clearEntityCaches();
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      this.projectedCount = 0;
      if (!this.VIuQ() && ClientUtils.isInWorld() && this.trackedCount != 0) {
         for (int var2 = 0; var2 < this.trackedCount; var2++) {
            ESP$1 var3 = this.trackedEntities.get(var2);
            EntityLivingBase var4 = ESP$1.ZGuBgtI(var3);
            if (var4 != null && RenderUtils.isEntityInView(var4)) {
               ESP$1.setColor(var3, this.KsqX(var4));
               if (!this.isTargetEspEntity(var4)) {
                  boolean var5 = this.isSkywarsTarget(var4);
                  if (ESP$1.getMode(var3) == MODE_THREE_D) {
                     if (var5) {
                        if (this.healthBar.isToggled()) {
                           this.drawWorldHealthBar(var4, this.getInterpolatedBounds(var4, var1.YDn0), (float)this.thickness.getInput() * 1.5F);
                        }
                     } else {
                        this.render3dBox(var4, ESP$1.KGTuj(var3));
                     }
                  } else if (ESP$1.getMode(var3) == Yjr) {
                     if (var5) {
                        if (this.healthBar.isToggled()) {
                           this.drawWorldHealthBar(var4, this.getInterpolatedBounds(var4, var1.YDn0), 3.0F);
                        }
                     } else {
                        this.renderBox(var4, ESP$1.KGTuj(var3));
                     }
                  } else if (ESP$1.getMode(var3) == MODE_SKELETON) {
                     if (var4 instanceof EntityPlayer) {
                        PlayerModelBoxRenderer$2 var6 = this.playerModelRotations.get(var4.getUniqueID());
                        if (var6 != null) {
                           this.renderPlayerSkeleton((EntityPlayer)var4, var6, ESP$1.KGTuj(var3), var1.YDn0);
                        }
                     }

                     if (this.healthBar.isToggled()) {
                        this.drawWorldHealthBar(var4, this.getInterpolatedBounds(var4, var1.YDn0), (float)this.thickness.getInput() * 1.5F);
                     }
                  }

                  if (ESP$1.getMode(var3) == MPIhHa || ESP$1.getMode(var3) == MODE_OUTLINE) {
                     this.addProjectedEntry(var3);
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onRenderPlayerPost(RenderPlayerPostEvent var1) {
      if (var1 != null && var1.entityPlayer != null && var1.modelBiped != null) {
         PlayerModelBoxRenderer$2 var2 = this.playerModelRotations.get(var1.entityPlayer.getUniqueID());
         if (var2 == null) {
            var2 = new PlayerModelBoxRenderer$2();
            this.playerModelRotations.put(var1.entityPlayer.getUniqueID(), var2);
         }

         var2.captureModelRotations(var1.modelBiped);
      }
   }

   @Subscribe(priority = EventPriority.LOW)
   public void onRenderWorldLastLate(RenderWorldLastEvent var1) {
      if (!this.VIuQ() && ClientUtils.isInWorld() && this.projectedCount != 0) {
         if (this.hasProjectedMode(MODE_OUTLINE)) {
            this.renderGlowPass(var1.YDn0);
         }

         if (this.hasProjectedMode(MPIhHa) || this.hasProjectedMode(MODE_OUTLINE) && this.healthBar.isToggled()) {
            this.render2dPass(var1.YDn0);
         }
      }
   }

   private boolean hasProjectedMode(int var1) {
      for (int var2 = 0; var2 < this.projectedCount; var2++) {
         if (ESP$1.getMode(this.ONU.get(var2)) == var1) {
            return true;
         }
      }

      return false;
   }

   public boolean isHealthBarOverlayEnabled() {
      return false;
   }

   public void KbEan(ExternalRenderBuffer var1, ScreenProjector var2, float var3) {
      RenderManager var4 = mc.getRenderManager();
      if (var4 != null) {
         float var5 = new ScaledResolution(mc).getScaleFactor();
         float var6 = (float)this.thickness.getInput();

         for (int var7 = 0; var7 < this.trackedCount; var7++) {
            ESP$1 var8 = this.trackedEntities.get(var7);
            EntityLivingBase var9 = ESP$1.ZGuBgtI(var8);
            if (var9 != null && !var9.isDead && mc.theWorld.loadedEntityList.contains(var9)) {
               ESP$1.setColor(var8, this.KsqX(var9));
               if (ESP$1.getMode(var8) != MODE_OUTLINE && !this.isTargetEspEntity(var9)) {
                  double var10 = var9.lastTickPosX + (var9.posX - var9.lastTickPosX) * var3 - var4.viewerPosX;
                  double var12 = var9.lastTickPosY + (var9.posY - var9.lastTickPosY) * var3 - var4.viewerPosY;
                  double var14 = var9.lastTickPosZ + (var9.posZ - var9.lastTickPosZ) * var3 - var4.viewerPosZ;
                  double[] var16 = this.zlFv6(var9, var3, var4);
                  if (var2.drawProjectedBox(
                     var16[0], var16[1], var16[2], var16[3], var16[4], var16[5], var1, ESP$1.KGTuj(var8), var6, ESP$1.getMode(var8) == MODE_THREE_D
                  )) {
                     double[] var17 = var2.screenBounds;
                     boolean var18 = this.isSkywarsTarget(var9);
                     if (ESP$1.getMode(var8) == MPIhHa && !var18) {
                        if (this.render2dOutline.isToggled()) {
                           this.CYbu(var1, var17, -16777216, var6 + 2.0F);
                        }

                        this.CYbu(var1, var17, ESP$1.KGTuj(var8), var6);
                     } else if (ESP$1.getMode(var8) == Yjr && !var18) {
                        this.drawExternalBox(var1, var2, var16, ESP$1.KGTuj(var8), var6);
                     } else if (ESP$1.getMode(var8) == MODE_SKELETON && var9 instanceof EntityPlayer && !var18) {
                        PlayerModelBoxRenderer.drawSkeletonLines(
                           var1,
                           var2,
                           (EntityPlayer)var9,
                           this.playerModelRotations.get(var9.getUniqueID()),
                           var3,
                           var10,
                           var12,
                           var14,
                           ESP$1.KGTuj(var8),
                           Math.max(1.0F, var6)
                        );
                     }

                     if (this.healthBar.isToggled()) {
                        double var19 = var17[0] - 3.0 * var5;
                        double var21 = var17[3];
                        float var23 = Math.max(0.0F, Math.min(1.0F, var9.getHealth() / Math.max(1.0F, var9.getMaxHealth())));
                        double var24 = var21 - (var21 - var17[1]) * var23;
                        float var26 = ESP$1.getMode(var8) == Yjr
                           ? 3.0F
                           : (ESP$1.getMode(var8) != MPIhHa && ESP$1.getMode(var8) != MODE_OUTLINE ? Math.max(1.0F, var6 * 1.5F) : 2.0F);
                        if (this.healthBarOutline.isToggled()) {
                           var1.drawLine(var19, var21, var19, var17[1], -16777216, var26 + 2.0F);
                        }

                        if (this.xmE0() == 1) {
                           int var27 = ClientUtils.YVVZ(Arraylist.xQec0(var17[1] * 0.75), 255);
                           int var28 = this.scaleRgb(var27, 0.45F);
                           byte var29 = 10;

                           for (int var30 = 0; var30 < var29; var30++) {
                              double var31 = (double)var30 / var29;
                              double var33 = (double)(var30 + 1) / var29;
                              var1.drawLine(
                                 var19,
                                 var21 - (var21 - var24) * var31,
                                 var19,
                                 var21 - (var21 - var24) * var33,
                                 this.lerpColor(var28, var27, (float)((var31 + var33) * 0.5)),
                                 var26
                              );
                           }
                        } else {
                           var1.drawLine(var19, var21, var19, var24, this.getHealthColor(var23) | 0xFF000000, var26);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void renderExternalBox(Entity var1, int var2, float var3) {
      ExternalRenderBuffer var4 = ExternalRenderer.getActiveRenderBuffer();
      ScreenProjector var5 = ExternalRenderer.getActiveScreenProjector();
      RenderManager var6 = mc.getRenderManager();
      if (var4 != null && var5 != null && var6 != null && var1 != null) {
         float var7 = ((IAccessorMinecraft)mc).getTimer().renderPartialTicks;
         double[] var8 = this.zlFv6(var1, var7, var6);
         if (var5.drawProjectedBox(var8[0], var8[1], var8[2], var8[3], var8[4], var8[5], var4, var2, 0.0F, false)) {
            this.drawExternalBox(var4, var5, var8, var2, var3);
         }
      }
   }

   private void drawExternalBox(ExternalRenderBuffer var1, ScreenProjector var2, double[] var3, int var4, float var5) {
      var2.HIQRn(var1, ClientUtils.YVVZ(var4, Math.round((var4 >>> 24 & 0xFF) * 0.3F)));
      var2.drawProjectedBox(var3[0], var3[1], var3[2], var3[3], var3[4], var3[5], var1, var4, Math.max(1.0F, var5), true);
   }

   private double[] zlFv6(Entity var1, float var2, RenderManager var3) {
      double var4 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2 - var3.viewerPosX;
      double var6 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2 - var3.viewerPosY;
      double var8 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2 - var3.viewerPosZ;
      AxisAlignedBB var10 = var1.getEntityBoundingBox();
      return new double[]{
         var4 + var10.minX - var1.posX - 0.1,
         var6 + var10.minY - var1.posY - 0.1,
         var8 + var10.minZ - var1.posZ - 0.1,
         var4 + var10.maxX - var1.posX + 0.1,
         var6 + var10.maxY - var1.posY + 0.2,
         var8 + var10.maxZ - var1.posZ + 0.1
      };
   }

   private void CYbu(ExternalRenderBuffer var1, double[] var2, int var3, float var4) {
      double var5 = var2[0];
      double var7 = var2[1];
      double var9 = var2[2];
      double var11 = var2[3];
      double var13 = (var9 - var5) / 4.0;
      double var15 = (var11 - var7) / 4.0;
      if (this.qsxo() == 0) {
         var1.drawLine(var5, var7, var5 + var13, var7, var3, var4);
         var1.drawLine(var9 - var13, var7, var9, var7, var3, var4);
         var1.drawLine(var5, var11, var5 + var13, var11, var3, var4);
         var1.drawLine(var9 - var13, var11, var9, var11, var3, var4);
         var1.drawLine(var5, var7, var5, var7 + var15, var3, var4);
         var1.drawLine(var9, var7, var9, var7 + var15, var3, var4);
         var1.drawLine(var5, var11 - var15, var5, var11, var3, var4);
         var1.drawLine(var9, var11 - var15, var9, var11, var3, var4);
      } else {
         var1.drawLine(var5, var7, var9, var7, var3, var4);
         var1.drawLine(var9, var7, var9, var11, var3, var4);
         var1.drawLine(var9, var11, var5, var11, var3, var4);
         var1.drawLine(var5, var11, var5, var7, var3, var4);
      }
   }

   private void clearEntityCaches() {
      this.trackedCount = 0;
      this.projectedCount = 0;
   }

   private void eimw() {
      this.trackedCount = 0;
      this.projectedCount = 0;
      double var1 = this.maxDistance.getInput() * this.maxDistance.getInput();

      for (Entity var4 : mc.theWorld.loadedEntityList) {
         if (var4 instanceof EntityLivingBase && var4 != mc.thePlayer) {
            EntityLivingBase var5 = (EntityLivingBase)var4;
            if (this.shouldRenderEntity(var5, var1)) {
               this.krHg(var5, this.KsqX(var5), this.getModeForEntity(var5));
            }
         }
      }
   }

   public void refreshTrackedColors() {
      for (int var1 = 0; var1 < this.trackedCount; var1++) {
         ESP$1 var2 = this.trackedEntities.get(var1);
         if (ESP$1.ZGuBgtI(var2) instanceof EntityPlayer) {
            ESP$1.setColor(var2, this.KsqX(ESP$1.ZGuBgtI(var2)));
         }
      }
   }

   private int KsqX(EntityLivingBase var1) {
      if (!(var1 instanceof EntityPlayer)) {
         return this.mobColor.getArgb();
      } else {
         EntityPlayer var2 = (EntityPlayer)var1;
         if (ClientUtils.isEnemy(var2)) {
            return this.enemyColour.getArgb();
         } else if (ClientUtils.isFriend(var2)) {
            return this.friendColour.getArgb();
         } else {
            if (this.teamColor.isToggled()) {
               int var3 = this.getTeamColor(var1);
               if (var3 != -1) {
                  return ClientUtils.YVVZ(var3, this.defaultColour.JIjrD());
               }
            }

            return this.defaultColour.getArgb();
         }
      }
   }

   private int getModeForEntity(EntityLivingBase var1) {
      return !(var1 instanceof EntityPlayer) ? this.ASWk((int)this.mobMode.getInput()) : this.Vkr8();
   }

   private int getTeamColor(EntityLivingBase var1) {
      int var2 = ClientUtils.oCoqd(var1);
      if (var2 != -1) {
         return var2 & 16777215;
      } else {
         if (var1 instanceof EntityPlayer) {
            ScorePlayerTeam var3 = (ScorePlayerTeam)var1.getTeam();
            if (var3 != null) {
               int var4 = this.parseColorCode(var3.getColorPrefix());
               if (var4 != -1) {
                  return var4;
               }
            }
         }

         return this.parseColorCode(var1.getDisplayName() == null ? null : var1.getDisplayName().getFormattedText());
      }
   }

   private int parseColorCode(String var1) {
      String var2 = ClientUtils.getFirstColorCode(var1);
      if (var2.length() < 2) {
         return -1;
      } else {
         int var3 = mc.fontRendererObj.getColorCode(Character.toLowerCase(var2.charAt(1)));
         return var3 >= 0 ? var3 & 16777215 : -1;
      }
   }

   private boolean shouldRenderEntity(EntityLivingBase var1, double var2) {
      if (var1 == null || var1.deathTime != 0 || var1.isInvisible()) {
         return false;
      } else if (var1 instanceof EntityArmorStand) {
         return false;
      } else {
         if (var1 instanceof EntityPlayer) {
            Object var4 = Freecam.cameraEntity == null ? mc.thePlayer : Freecam.cameraEntity;
            if (var1 == var4 || AntiBot.shouldHideEntity(var1)) {
               return false;
            }
         } else if (!this.mobs.isToggled() || this.onlyBedwarsMobs.isToggled() && !this.isBedwarsMob(var1)) {
            return false;
         }

         return RenderUtils.HVp0(var1, var2);
      }
   }

   private boolean isSkywarsTarget(EntityLivingBase var1) {
      return Jade.getModuleManager().getModule(SkywarsUtils.class) != null && Jade.getModuleManager().getModule(SkywarsUtils.class).isStrengthHighlighted(var1);
   }

   private boolean isTargetEspEntity(EntityLivingBase var1) {
      TargetESP var2 = Jade.getModuleManager().getModule(TargetESP.class);
      return var2 != null && var2.isBoxEspTarget(var1);
   }

   private boolean isBedwarsMob(EntityLivingBase var1) {
      return var1 instanceof EntityIronGolem || var1 instanceof EntitySilverfish;
   }

   private void krHg(EntityLivingBase var1, int var2, int var3) {
      if (this.trackedCount >= this.trackedEntities.size()) {
         this.trackedEntities.add(new ESP$1());
      }

      ESP$1 var4 = this.trackedEntities.get(this.trackedCount++);
      ESP$1.initializeEntry(var4, var1, var2, var3);
   }

   private void addProjectedEntry(ESP$1 var1) {
      if (this.projectedCount >= this.ONU.size()) {
         this.ONU.add(var1);
      } else {
         this.ONU.set(this.projectedCount, var1);
      }

      this.projectedCount++;
   }

   private int Vkr8() {
      return EspMode.fromSetting(this.mode.getInput()).ordinal();
   }

   private int ASWk(int var1) {
      return var1 >= 0 && var1 < sbMf.length ? var1 : MPIhHa;
   }

   private int qsxo() {
      int var1 = (int)this.render2dStyle.getInput();
      return var1 >= 0 && var1 < TWO_D_MODE_LABELS.length ? var1 : 1;
   }

   private int xmE0() {
      int var1 = (int)this.healthBarColor.getInput();
      return var1 >= 0 && var1 < yXk.length ? var1 : 0;
   }

   public void applyOpacityToColors() {
      int var1 = Math.max(0, Math.min(255, (int)Math.round(this.opacity.getInput() * 2.55)));
      this.defaultColour.setAlpha(var1);
      this.friendColour.setAlpha(var1);
      this.enemyColour.setAlpha(var1);
      this.mobColor.setAlpha(var1);
   }

   private AxisAlignedBB getInterpolatedBounds(Entity var1, float var2) {
      RenderManager var3 = mc.getRenderManager();
      double var4 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2 - var3.viewerPosX;
      double var6 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2 - var3.viewerPosY;
      double var8 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2 - var3.viewerPosZ;
      AxisAlignedBB var10 = this.getProjectionBounds(var1);
      return new AxisAlignedBB(
         var10.minX - var1.posX + var4,
         var10.minY - var1.posY + var6,
         var10.minZ - var1.posZ + var8,
         var10.maxX - var1.posX + var4,
         var10.maxY - var1.posY + var6,
         var10.maxZ - var1.posZ + var8
      );
   }

   private AxisAlignedBB getProjectionBounds(Entity var1) {
      AxisAlignedBB var2 = var1.getEntityBoundingBox();
      return new AxisAlignedBB(var2.minX - 0.1, var2.minY - 0.1, var2.minZ - 0.1, var2.maxX + 0.1, var2.maxY + 0.2, var2.maxZ + 0.1);
   }

   public void renderBoxAndOutline(EntityLivingBase var1, int var2) {
      try {
         this.renderBoxInternal(var1, var2, false, 0.25F);
      } finally {
         RenderUtils.resetGlPipelineState();
      }

      try {
         this.XNSKu(var1, var2, false);
      } finally {
         RenderUtils.resetGlPipelineState();
      }
   }

   public void renderLivingBoxDefault(EntityLivingBase var1, int var2) {
      this.renderLivingBox(var1, var2, (float)this.thickness.getInput());
   }

   public void renderLivingBox(EntityLivingBase var1, int var2, float var3) {
      this.renderEntityBox(var1, var2, var3);
   }

   public void renderEntityBox(Entity var1, int var2, float var3) {
      float var4 = (var2 >> 24 & 0xFF) / 255.0F;
      this.MUvgpn(var1, var2, false, 0.3F * var4, var3);
   }

   private void render3dBox(EntityLivingBase var1, int var2) {
      this.XNSKu(var1, var2, true);
   }

   private void XNSKu(EntityLivingBase var1, int var2, boolean var3) {
      float var4 = ((IAccessorMinecraft)mc).getTimer().renderPartialTicks;
      AxisAlignedBB var5 = this.getInterpolatedBounds(var1, var4);
      float var6 = (var2 >> 24 & 0xFF) / 255.0F;
      float var7 = (var2 >> 16 & 0xFF) / 255.0F;
      float var8 = (var2 >> 8 & 0xFF) / 255.0F;
      float var9 = (var2 & 0xFF) / 255.0F;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      RenderUtils$1 var10 = null;

      try {
         var10 = RenderUtils.uyB6();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glEnable(2848);
         GL11.glLineWidth((float)this.thickness.getInput());
         GL11.glColor4f(var7, var8, var9, var6);
         RenderUtils.drawBoxOutline(var5);
         if (var3 && this.healthBar.isToggled()) {
            this.renderWorldHealthBar(var1, var5, (float)this.thickness.getInput() * 1.5F);
         }
      } finally {
         RenderUtils.restoreLightmapState(var10);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glLineWidth(1.0F);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }
   }

   private void renderBox(EntityLivingBase var1, int var2) {
      float var3 = (var2 >> 24 & 0xFF) / 255.0F;
      this.renderBoxInternal(var1, var2, true, 0.3F * var3);
   }

   private void renderBoxInternal(EntityLivingBase var1, int var2, boolean var3, float var4) {
      this.MUvgpn(var1, var2, var3, var4, (float)this.thickness.getInput());
   }

   private void MUvgpn(Entity var1, int var2, boolean var3, float var4, float var5) {
      float var6 = ((IAccessorMinecraft)mc).getTimer().renderPartialTicks;
      AxisAlignedBB var7 = this.getInterpolatedBounds(var1, var6);
      float var8 = (var2 >> 16 & 0xFF) / 255.0F;
      float var9 = (var2 >> 8 & 0xFF) / 255.0F;
      float var10 = (var2 & 0xFF) / 255.0F;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      RenderUtils$1 var11 = null;

      try {
         var11 = RenderUtils.uyB6();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         RenderUtils.drawFilledAabb(var7, var8, var9, var10, var4);
         GL11.glEnable(2848);
         GL11.glLineWidth(Math.max(1.0F, var5));
         GL11.glColor4f(var8, var9, var10, (var2 >>> 24 & 0xFF) / 255.0F);
         RenderUtils.drawBoxOutline(var7);
         if (var3 && this.healthBar.isToggled() && var1 instanceof EntityLivingBase) {
            this.renderWorldHealthBar((EntityLivingBase)var1, var7, 3.0F);
         }
      } finally {
         RenderUtils.restoreLightmapState(var11);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glLineWidth(1.0F);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }
   }

   private void renderWorldHealthBar(EntityLivingBase var1, AxisAlignedBB var2, float var3) {
      float var4 = Math.max(1.0F, var1.getMaxHealth());
      float var5 = Math.max(0.0F, Math.min(1.0F, var1.getHealth() / var4));
      double var6 = (var2.minX + var2.maxX) * 0.5;
      double var8 = (var2.minZ + var2.maxZ) * 0.5;
      double var10 = -var6;
      double var12 = -var8;
      double var14 = Math.sqrt(var10 * var10 + var12 * var12);
      if (var14 < 1.0E-4) {
         float var16 = (float)Math.toRadians(mc.getRenderManager().playerViewY);
         var10 = -Math.sin(var16);
         var12 = Math.cos(var16);
      } else {
         var10 /= var14;
         var12 /= var14;
      }

      double var44 = -var12;
      double var20 = (var2.maxX - var2.minX) * 0.5;
      double var22 = (var2.maxZ - var2.minZ) * 0.5;
      double var24 = Math.abs(var10) * var20 + Math.abs(var12) * var22;
      double var26 = Math.abs(var44) * var20 + Math.abs(var10) * var22;
      double var28 = 0.06;
      double var30 = var6 + var10 * (var24 + var28) + var44 * (var26 + var28);
      double var32 = var8 + var12 * (var24 + var28) + var10 * (var26 + var28);
      double var34 = var2.minY;
      double var36 = var2.minY + (var2.maxY - var2.minY) * var5;
      if (this.healthBarOutline.isToggled()) {
         GL11.glLineWidth(var3 + 2.0F);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
         GL11.glBegin(1);
         GL11.glVertex3d(var30, var34, var32);
         GL11.glVertex3d(var30, var2.maxY, var32);
         GL11.glEnd();
      }

      if (this.xmE0() == 1) {
         this.renderGradientHealthBar(var30, var34, var32, var36, var3);
      } else {
         int var38 = this.getHealthColor(var5);
         float var39 = (var38 >> 16 & 0xFF) / 255.0F;
         float var40 = (var38 >> 8 & 0xFF) / 255.0F;
         float var41 = (var38 & 0xFF) / 255.0F;
         GL11.glLineWidth(var3);
         GL11.glColor4f(var39, var40, var41, 1.0F);
         GL11.glBegin(1);
         GL11.glVertex3d(var30, var34, var32);
         GL11.glVertex3d(var30, var36, var32);
         GL11.glEnd();
      }
   }

   private void drawWorldHealthBar(EntityLivingBase var1, AxisAlignedBB var2, float var3) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      RenderUtils$1 var4 = null;

      try {
         var4 = RenderUtils.uyB6();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glEnable(2848);
         this.renderWorldHealthBar(var1, var2, var3);
      } finally {
         RenderUtils.restoreLightmapState(var4);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glLineWidth(1.0F);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }
   }

   private void renderGradientHealthBar(double var1, double var3, double var5, double var7, float var9) {
      if (!(var7 <= var3)) {
         int var10 = ClientUtils.YVVZ(Arraylist.xQec0(var3 * 38.0 + var5 * 18.0), 255);
         int var11 = this.scaleRgb(var10, 0.45F);
         byte var12 = 10;
         GL11.glLineWidth(var9);
         GL11.glBegin(1);

         for (int var13 = 0; var13 < var12; var13++) {
            double var14 = (double)var13 / var12;
            double var16 = (double)(var13 + 1) / var12;
            double var18 = var3 + (var7 - var3) * var14;
            double var20 = var3 + (var7 - var3) * var16;
            int var22 = this.lerpColor(var11, var10, (float)((var14 + var16) * 0.5));
            this.SFQre(var22);
            GL11.glVertex3d(var1, var18, var5);
            GL11.glVertex3d(var1, var20, var5);
         }

         GL11.glEnd();
      }
   }

   private void renderPlayerSkeleton(EntityPlayer var1, PlayerModelBoxRenderer$2 var2, int var3, float var4) {
      RenderManager var5 = mc.getRenderManager();
      double var6 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var4 - var5.viewerPosX;
      double var8 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var4 - var5.viewerPosY;
      double var10 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var4 - var5.viewerPosZ;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);

      try {
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glEnable(2848);
         GL11.glLineWidth(Math.max(1.0F, (float)this.thickness.getInput()));
         this.SFQre(var3);
         GL11.glTranslated(var6, var8, var10);
         boolean var12 = var1.isSneaking();
         float var13 = var12 ? 0.6F : 0.75F;
         double var14 = var12 ? -0.2 : 0.0;
         GL11.glRotatef(var1.renderYawOffset, 0.0F, -1.0F, 0.0F);
         GL11.glTranslated(-0.15, var13, var14);
         this.applyLimbRotationGl(var2.rightLegRotX, var2.RGtc, var2.rightLegRotZ);
         this.amntry(0.0, 0.0, 0.0, 0.0, -var13, 0.0);
         this.FHs7(var2.rightLegRotX, var2.RGtc, var2.rightLegRotZ);
         GL11.glTranslated(0.3, 0.0, 0.0);
         this.applyLimbRotationGl(var2.gKyq, var2.leftLegRotY, var2.leftLegRotZ);
         this.amntry(0.0, 0.0, 0.0, 0.0, -var13, 0.0);
         this.FHs7(var2.gKyq, var2.leftLegRotY, var2.leftLegRotZ);
         GL11.glTranslated(-0.15, 0.0, 0.0);
         this.amntry(0.15, 0.0, 0.0, -0.15, 0.0, 0.0);
         if (var12) {
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
         }

         this.amntry(0.0, 0.0, 0.0, 0.0, 0.65, 0.0);
         GL11.glTranslated(0.0, 0.65, 0.0);
         this.amntry(0.35, 0.0, 0.0, -0.35, 0.0, 0.0);
         GL11.glTranslated(-0.35, 0.0, 0.0);
         this.applyLimbRotationGl(var2.rightArmRotX, var2.PYK, var2.rightArmRotZ);
         this.amntry(0.0, 0.0, 0.0, 0.0, -0.6, 0.0);
         this.FHs7(var2.rightArmRotX, var2.PYK, var2.rightArmRotZ);
         GL11.glTranslated(0.7, 0.0, 0.0);
         this.applyLimbRotationGl(var2.ULu, var2.leftArmRotY, var2.fwB);
         this.amntry(0.0, 0.0, 0.0, 0.0, -0.6, 0.0);
         this.FHs7(var2.ULu, var2.leftArmRotY, var2.fwB);
         GL11.glTranslated(-0.35, 0.0, 0.0);
         GL11.glRotatef(-var1.renderYawOffset, 0.0F, -1.0F, 0.0F);
         GL11.glRotated(var1.prevRotationYawHead + (var1.rotationYawHead - var1.prevRotationYawHead) * var4, 0.0, -1.0, 0.0);
         GL11.glRotated(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var4, 1.0, 0.0, 0.0);
         this.amntry(0.0, 0.0, 0.0, 0.0, 0.4, 0.0);
      } finally {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glLineWidth(1.0F);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }
   }

   private void applyLimbRotationGl(float var1, float var2, float var3) {
      GL11.glRotatef(var1 * (float) (180.0 / Math.PI), 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-var2 * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-var3 * (float) (180.0 / Math.PI), 0.0F, 0.0F, 1.0F);
   }

   private void FHs7(float var1, float var2, float var3) {
      GL11.glRotatef(var3 * (float) (180.0 / Math.PI), 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(var2 * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-var1 * (float) (180.0 / Math.PI), 1.0F, 0.0F, 0.0F);
   }

   private void amntry(double var1, double var3, double var5, double var7, double var9, double var11) {
      GL11.glBegin(1);
      GL11.glVertex3d(var1, var3, var5);
      GL11.glVertex3d(var7, var9, var11);
      GL11.glEnd();
   }

   private void render2dPass(float var1) {
      RenderManager var2 = mc.getRenderManager();
      if (var2 != null) {
         int var3 = this.pushProjectionMatrices();

         try {
            GL11.glPushAttrib(1048575);

            try {
               ScaledResolution var4 = new ScaledResolution(mc);
               this.setupCameraTransform(var1);
               this.projectionCache = RenderUtils.captureProjectionMatrices(this.projectionCache, var4.getScaleFactor());
               mc.entityRenderer.setupOverlayRendering();
               int var5 = var4.getScaledWidth();
               int var6 = var4.getScaledHeight();

               for (int var7 = 0; var7 < this.projectedCount; var7++) {
                  ESP$1 var8 = this.ONU.get(var7);

                  try {
                     if (this.projectEntityBounds(var8, var2, var5, var6, var1)) {
                        this.ROcO(var8, ESP$1.getMode(var8) == MPIhHa && !this.isSkywarsTarget(ESP$1.ZGuBgtI(var8)));
                     }
                  } catch (RuntimeException var18) {
                     ESP$1.JzgO42(var8, false);
                  }
               }
            } finally {
               GL11.glPopAttrib();
            }
         } finally {
            this.popProjectionMatrices(var3);
         }
      }
   }

   private boolean projectEntityBounds(ESP$1 var1, RenderManager var2, int var3, int var4, float var5) {
      EntityLivingBase var6 = ESP$1.ZGuBgtI(var1);
      double var7 = var6.lastTickPosX + (var6.posX - var6.lastTickPosX) * var5 - var2.viewerPosX;
      double var9 = var6.lastTickPosY + (var6.posY - var6.lastTickPosY) * var5 - var2.viewerPosY;
      double var11 = var6.lastTickPosZ + (var6.posZ - var6.lastTickPosZ) * var5 - var2.viewerPosZ;
      AxisAlignedBB var13 = this.getProjectionBounds(var6);
      double var14 = var13.minX - var6.posX + var7;
      double var16 = var13.minY - var6.posY + var9;
      double var18 = var13.minZ - var6.posZ + var11;
      double var20 = var13.maxX - var6.posX + var7;
      double var22 = var13.maxY - var6.posY + var9;
      double var24 = var13.maxZ - var6.posZ + var11;
      double var26 = Double.POSITIVE_INFINITY;
      double var28 = Double.POSITIVE_INFINITY;
      double var30 = Double.NEGATIVE_INFINITY;
      double var32 = Double.NEGATIVE_INFINITY;
      boolean var34 = false;

      for (int var35 = 0; var35 < 8; var35++) {
         double var36 = (var35 & 1) == 0 ? var14 : var20;
         double var38 = (var35 & 2) == 0 ? var16 : var22;
         double var40 = (var35 & 4) == 0 ? var18 : var24;
         if (RenderUtils.projectToScreen(this.projectionCache, var36, var38, var40, this.Dty7)) {
            double var42 = this.Dty7[2];
            if (!(var42 >= 1.0003684) && !(var42 <= 0.0)) {
               var34 = true;
               double var44 = this.Dty7[0];
               double var46 = this.Dty7[1];
               if (var44 < var26) {
                  var26 = var44;
               }

               if (var46 < var28) {
                  var28 = var46;
               }

               if (var44 > var30) {
                  var30 = var44;
               }

               if (var46 > var32) {
                  var32 = var46;
               }
            }
         }
      }

      if (!var34) {
         ESP$1.JzgO42(var1, false);
         return false;
      } else {
         ESP$1.setLeftX(var1, Math.max(0.0, var26));
         ESP$1.setTopY(var1, Math.max(0.0, var28));
         ESP$1.setRightX(var1, Math.min((double)var3, var30));
         ESP$1.setBottomY(var1, Math.min((double)var4, var32));
         ESP$1.JzgO42(var1, ESP$1.getRightX(var1) > ESP$1.getLeftX(var1) && ESP$1.getBottomY(var1) > ESP$1.getTopY(var1));
         return ESP$1.isOnScreen(var1);
      }
   }

   private void ROcO(ESP$1 var1, boolean var2) {
      float var3 = (ESP$1.KGTuj(var1) >> 16 & 0xFF) / 255.0F;
      float var4 = (ESP$1.KGTuj(var1) >> 8 & 0xFF) / 255.0F;
      float var5 = (ESP$1.KGTuj(var1) & 0xFF) / 255.0F;
      float var6 = (ESP$1.KGTuj(var1) >> 24 & 0xFF) / 255.0F;
      GL11.glPushMatrix();

      try {
         GL11.glPushAttrib(1048575);

         try {
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            if (var2) {
               this.draw2dBox(var1, var3, var4, var5, var6);
            }

            if (this.healthBar.isToggled()) {
               this.render2dHealthBar(var1);
            }
         } finally {
            GL11.glPopAttrib();
         }
      } finally {
         GL11.glPopMatrix();
      }
   }

   private void draw2dBox(ESP$1 var1, float var2, float var3, float var4, float var5) {
      float var6 = (float)this.thickness.getInput();
      boolean var7 = this.qsxo() == 0;
      if (this.render2dOutline.isToggled()) {
         GL11.glLineWidth(var6 + 2.0F);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, var5);
         if (var7) {
            this.ddgim(var1);
         } else {
            this.TpQiknJ(var1);
         }
      }

      GL11.glLineWidth(var6);
      GL11.glColor4f(var2, var3, var4, var5);
      if (var7) {
         this.ddgim(var1);
      } else {
         this.TpQiknJ(var1);
      }
   }

   private void TpQiknJ(ESP$1 var1) {
      GL11.glBegin(2);
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getBottomY(var1));
      GL11.glEnd();
   }

   private void ddgim(ESP$1 var1) {
      double var2 = ESP$1.getRightX(var1) - ESP$1.getLeftX(var1);
      double var4 = ESP$1.getBottomY(var1) - ESP$1.getTopY(var1);
      double var6 = Math.min(Math.min(var2, var4) * 0.28, 18.0);
      GL11.glBegin(1);
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1) + var6, ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getTopY(var1) + var6);
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1) - var6, ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getTopY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getTopY(var1) + var6);
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1) + var6, ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getLeftX(var1), ESP$1.getBottomY(var1) - var6);
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1) - var6, ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getBottomY(var1));
      GL11.glVertex2d(ESP$1.getRightX(var1), ESP$1.getBottomY(var1) - var6);
      GL11.glEnd();
   }

   private void render2dHealthBar(ESP$1 var1) {
      EntityLivingBase var2 = ESP$1.ZGuBgtI(var1);
      float var3 = Math.max(1.0F, var2.getMaxHealth());
      float var4 = Math.max(0.0F, Math.min(1.0F, var2.getHealth() / var3));
      double var5 = ESP$1.getBottomY(var1) - (ESP$1.getBottomY(var1) - ESP$1.getTopY(var1)) * var4;
      double var7 = ESP$1.getLeftX(var1) - 3.0;
      if (this.xmE0() == 1) {
         int var13 = ClientUtils.YVVZ(Arraylist.xQec0(ESP$1.getTopY(var1) * 0.75), 255);
         int var14 = this.scaleRgb(var13, 0.45F);
         if (this.healthBarOutline.isToggled()) {
            this.aOmwawA(var7 - 1.5, ESP$1.getTopY(var1), var7 + 1.5, ESP$1.getBottomY(var1), -16777216);
         }

         RenderUtils.drawVerticalGradient((float)(var7 - 1.0), (float)var5, (float)(var7 + 1.0), (float)ESP$1.getBottomY(var1), var13, var14);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
      } else {
         int var9 = this.getHealthColor(var4);
         float var10 = (var9 >> 16 & 0xFF) / 255.0F;
         float var11 = (var9 >> 8 & 0xFF) / 255.0F;
         float var12 = (var9 & 0xFF) / 255.0F;
         if (this.healthBarOutline.isToggled()) {
            GL11.glLineWidth(4.0F);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glBegin(1);
            GL11.glVertex2d(var7, ESP$1.getBottomY(var1));
            GL11.glVertex2d(var7, ESP$1.getTopY(var1));
            GL11.glEnd();
         }

         GL11.glLineWidth(2.0F);
         GL11.glColor4f(var10, var11, var12, 1.0F);
         GL11.glBegin(1);
         GL11.glVertex2d(var7, ESP$1.getBottomY(var1));
         GL11.glVertex2d(var7, var5);
         GL11.glEnd();
      }
   }

   private void aOmwawA(double var1, double var3, double var5, double var7, int var9) {
      this.SFQre(var9);
      GL11.glBegin(7);
      GL11.glVertex2d(var1, var7);
      GL11.glVertex2d(var5, var7);
      GL11.glVertex2d(var5, var3);
      GL11.glVertex2d(var1, var3);
      GL11.glEnd();
   }

   private int getHealthColor(float var1) {
      return var1 < 0.3F ? Color.RED.getRGB() : (var1 < 0.5F ? Color.ORANGE.getRGB() : (var1 < 0.7F ? Color.YELLOW.getRGB() : Color.GREEN.getRGB()));
   }

   private int scaleRgb(int var1, float var2) {
      var2 = Math.max(0.0F, Math.min(1.0F, var2));
      int var3 = var1 >> 24 & 0xFF;
      int var4 = Math.round((var1 >> 16 & 0xFF) * var2);
      int var5 = Math.round((var1 >> 8 & 0xFF) * var2);
      int var6 = Math.round((var1 & 0xFF) * var2);
      return var3 << 24 | var4 << 16 | var5 << 8 | var6;
   }

   private int lerpColor(int var1, int var2, float var3) {
      var3 = Math.max(0.0F, Math.min(1.0F, var3));
      int var4 = Math.round((var1 >> 24 & 0xFF) + ((var2 >> 24 & 0xFF) - (var1 >> 24 & 0xFF)) * var3);
      int var5 = Math.round((var1 >> 16 & 0xFF) + ((var2 >> 16 & 0xFF) - (var1 >> 16 & 0xFF)) * var3);
      int var6 = Math.round((var1 >> 8 & 0xFF) + ((var2 >> 8 & 0xFF) - (var1 >> 8 & 0xFF)) * var3);
      int var7 = Math.round((var1 & 0xFF) + ((var2 & 0xFF) - (var1 & 0xFF)) * var3);
      return var4 << 24 | var5 << 16 | var6 << 8 | var7;
   }

   private void SFQre(int var1) {
      GL11.glColor4f((var1 >> 16 & 0xFF) / 255.0F, (var1 >> 8 & 0xFF) / 255.0F, (var1 & 0xFF) / 255.0F, (var1 >> 24 & 0xFF) / 255.0F);
   }

   private void renderGlowPass(float var1) {
      if (this.IjOq() && this.projectedCount != 0) {
         this.BTItf = RenderUtils.resizeFramebuffer(this.BTItf, true);
         if (this.BTItf != null) {
            boolean var2 = mc.gameSettings.entityShadows;
            int var3 = GL11.glGetInteger(35725);
            int var4 = this.pushProjectionMatrices();
            GL11.glPushAttrib(1048575);

            try {
               mc.gameSettings.entityShadows = false;
               this.BTItf.bindFramebuffer(true);
               this.BTItf.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
               this.BTItf.framebufferClear();
               this.BTItf.bindFramebuffer(true);
               this.setupCameraTransform(var1);
               boolean var5 = false;
               RenderUtils$1 var6 = RenderUtils.uyB6();
               overlayPassActive = true;
               this.tintShader.bindShader();

               try {
                  for (int var7 = 0; var7 < this.projectedCount; var7++) {
                     ESP$1 var8 = this.ONU.get(var7);
                     EntityLivingBase var9 = ESP$1.ZGuBgtI(var8);
                     if (var9 != null && !this.isSkywarsTarget(var9) && RenderUtils.isEntityInView(var9)) {
                        int var10 = ESP$1.KGTuj(var8);
                        this.tintShader.BOral1(var10 >> 16 & 0xFF, var10 >> 8 & 0xFF, var10 & 0xFF, var10 >> 24 & 0xFF);
                        mc.getRenderManager().renderEntityStatic(var9, var1, true);
                        var5 = true;
                     }
                  }
               } finally {
                  this.tintShader.WutN18();
                  overlayPassActive = false;
                  RenderUtils.restoreLightmapState(var6);
               }

               if (var5) {
                  mc.getFramebuffer().bindFramebuffer(true);
                  RenderUtils$1 var35 = RenderUtils.uyB6();

                  try {
                     mc.entityRenderer.setupOverlayRendering();
                     GL11.glDisable(2929);
                     GL11.glDepthMask(false);
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     GL11.glEnable(3553);
                     this.glowShader.setKernelSize((float)this.thickness.getInput());

                     try {
                        this.glowShader.bindShader();
                        RenderUtils.drawFramebufferTexture(this.BTItf);
                     } finally {
                        this.glowShader.WutN18();
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                     }
                  } finally {
                     RenderUtils.restoreLightmapState(var35);
                  }
               }
            } finally {
               overlayPassActive = false;
               mc.gameSettings.entityShadows = var2;
               this.BTItf.framebufferClear();
               mc.getFramebuffer().bindFramebuffer(true);
               GL11.glPopAttrib();
               RenderUtils.resetGlPipelineState();
               GL20.glUseProgram(var3);
               this.popProjectionMatrices(var4);
            }
         }
      }
   }

   public void renderGradientOutline(EntityLivingBase var1, float var2, float var3, List<Integer> var4, int var5) {
      if (var1 != null && var5 > 0 && RenderUtils.isEntityInView(var1) && this.HVppo() && this.computeScissorRect(var1, var2, var3)) {
         this.outlineFramebuffer = RenderUtils.resizeFramebuffer(this.outlineFramebuffer, false);
         if (this.outlineFramebuffer != null) {
            boolean var6 = mc.gameSettings.entityShadows;
            int var7 = GL11.glGetInteger(35725);
            int var8 = GL11.glGetInteger(36006);
            ((Buffer)this.intBuffer).clear();
            GL11.glGetInteger(2978, this.intBuffer);
            int var9 = this.intBuffer.get(0);
            int var10 = this.intBuffer.get(1);
            int var11 = this.intBuffer.get(2);
            int var12 = this.intBuffer.get(3);
            int var13 = this.pushProjectionMatrices();
            GL11.glPushAttrib(1048575);

            try {
               mc.gameSettings.entityShadows = false;
               this.outlineFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
               this.outlineFramebuffer.framebufferClear();
               this.outlineFramebuffer.bindFramebuffer(true);
               this.setupCameraTransform(var2);
               RenderUtils$1 var14 = RenderUtils.uyB6();
               overlayPassActive = true;
               this.tintShader.bindShader();

               try {
                  this.tintShader.BOral1(255, 255, 255, 255);
                  mc.getRenderManager().renderEntityStatic(var1, var2, true);
               } finally {
                  this.tintShader.WutN18();
                  overlayPassActive = false;
                  RenderUtils.restoreLightmapState(var14);
               }

               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var8);
               GL11.glViewport(var9, var10, var11, var12);
               RenderUtils$1 var15 = RenderUtils.uyB6();

               try {
                  mc.entityRenderer.setupOverlayRendering();
                  GL11.glDisable(2929);
                  GL11.glDepthMask(false);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  GL11.glEnable(3553);
                  GL11.glEnable(3089);
                  GL11.glScissor(this.scissorRect[0], this.scissorRect[1], this.scissorRect[2], this.scissorRect[3]);
                  this.gradientOutlineShader.ETNK(var3, var4, var5);

                  try {
                     this.gradientOutlineShader.bindShader();
                     RenderUtils.drawFramebufferTexture(this.outlineFramebuffer);
                  } finally {
                     this.gradientOutlineShader.WutN18();
                     GL11.glDepthMask(true);
                     GL11.glEnable(2929);
                  }
               } finally {
                  RenderUtils.restoreLightmapState(var15);
               }
            } finally {
               overlayPassActive = false;
               mc.gameSettings.entityShadows = var6;
               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var8);
               GL11.glViewport(var9, var10, var11, var12);
               GL11.glPopAttrib();
               RenderUtils.resetGlPipelineState();
               GL20.glUseProgram(var7);
               this.popProjectionMatrices(var13);
            }
         }
      }
   }

   private boolean computeScissorRect(Entity var1, float var2, float var3) {
      this.projectionCache = RenderUtils.captureProjectionMatrices(this.projectionCache, 1);
      AxisAlignedBB var4 = this.getInterpolatedBounds(var1, var2);
      double var5 = Double.POSITIVE_INFINITY;
      double var7 = Double.POSITIVE_INFINITY;
      double var9 = Double.NEGATIVE_INFINITY;
      double var11 = Double.NEGATIVE_INFINITY;
      boolean var13 = false;

      for (int var14 = 0; var14 < 8; var14++) {
         double var15 = (var14 & 1) == 0 ? var4.minX : var4.maxX;
         double var17 = (var14 & 2) == 0 ? var4.minY : var4.maxY;
         double var19 = (var14 & 4) == 0 ? var4.minZ : var4.maxZ;
         if (RenderUtils.projectToScreen(this.projectionCache, var15, var17, var19, this.Dty7) && !(this.Dty7[2] <= 0.0) && !(this.Dty7[2] >= 1.0)) {
            var13 = true;
            var5 = Math.min(var5, this.Dty7[0]);
            var7 = Math.min(var7, this.Dty7[1]);
            var9 = Math.max(var9, this.Dty7[0]);
            var11 = Math.max(var11, this.Dty7[1]);
         }
      }

      if (!var13) {
         return false;
      } else {
         int var21 = Math.max(3, (int)Math.ceil(var3) + 2);
         int var22 = Math.max(0, (int)Math.floor(var5) - var21);
         int var16 = Math.min(mc.displayWidth, (int)Math.ceil(var9) + var21);
         int var23 = Math.max(0, (int)Math.floor(var7) - var21);
         int var18 = Math.min(mc.displayHeight, (int)Math.ceil(var11) + var21);
         if (var16 > var22 && var18 > var23) {
            this.scissorRect[0] = var22;
            this.scissorRect[1] = mc.displayHeight - var18;
            this.scissorRect[2] = var16 - var22;
            this.scissorRect[3] = var18 - var23;
            return true;
         } else {
            return false;
         }
      }
   }

   private int pushProjectionMatrices() {
      int var1 = GL11.glGetInteger(2976);
      GL11.glMatrixMode(5889);
      GL11.glPushMatrix();
      GL11.glMatrixMode(5888);
      GL11.glPushMatrix();
      GL11.glMatrixMode(var1);
      return var1;
   }

   private void popProjectionMatrices(int var1) {
      GL11.glMatrixMode(5888);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5889);
      GL11.glPopMatrix();
      GL11.glMatrixMode(var1);
   }

   private boolean IjOq() {
      if (!this.doAl) {
         this.doAl = true;

         try {
            this.glowShader = new GlowShader();
            this.tintShader = new TintShader();
         } catch (Throwable var2) {
            this.glowShader = null;
            this.tintShader = null;
         }
      }

      return this.glowShader != null && this.tintShader != null && this.glowShader.hasValidProgram() && this.tintShader.hasValidProgram();
   }

   private boolean HVppo() {
      if (!this.IjOq()) {
         return false;
      } else {
         if (this.gradientOutlineShader == null) {
            try {
               this.gradientOutlineShader = new GradientOutlineShader();
            } catch (Throwable var2) {
               this.gradientOutlineShader = null;
            }
         }

         return this.gradientOutlineShader != null && this.gradientOutlineShader.hasValidProgram();
      }
   }

   private void setupCameraTransform(float var1) {
      EntityRenderer var2 = mc.entityRenderer;
      if (var2 != null) {
         try {
            if (var2 instanceof IAccessorEntityRenderer) {
               ((IAccessorEntityRenderer)var2).callSetupCameraTransform(var1, 0);
               return;
            }

            Class var3 = var2.getClass();
            Method var4 = this.method;
            if (var4 == null || this.dyNo != var3) {
               var4 = RuntimeAccess.resolveMappedMethod(var3, new Class[]{float.class, int.class}, "setupCameraTransform", "func_78479_a");
               this.dyNo = var3;
               this.method = var4;
            }

            if (var4 == null) {
               return;
            }

            var4.invoke(var2, var1, 0);
         } catch (Throwable var5) {
            this.method = null;
         }
      }
   }
}
