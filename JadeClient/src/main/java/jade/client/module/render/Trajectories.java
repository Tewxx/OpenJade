// Jade recovery: module: Trajectories (render); original class: jade.deps.eLz.kKQiK6l
package jade.client.module.render;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils$1;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.other.AntiBot;
import jade.client.module.render.trajectories.ProjectilePhysics;
import jade.client.module.render.trajectories.ProjectileType;
import jade.client.module.render.trajectories.TrajectoryMath;
import jade.client.module.render.trajectories.FluidState;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.module.shared.ProjectileMotion;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class Trajectories extends Module implements ExternalRenderableModule {
   private static final boolean GL_DEBUG_ENABLED = ((!Boolean.getBoolean("jade.debugTrajectoriesGl")
         && !Boolean.getBoolean("jade.debugRenderGl")
      ? 0
      : 1) != 0);
   private static final int OXhL = 0;
   private static final int aXo8 = 1;
   private static final int IMPACT_TYPE_WALL = 2;
   private static final int IMPACT_TYPE_GROUND = 3;
   private SliderSetting thickness;
   private GroupSetting itemsGroup;
   private BooleanSetting renderBows;
   private BooleanSetting onlyCharged;
   private BooleanSetting renderFireballs;
   private BooleanSetting projectedFireball;
   private BooleanSetting renderEnderPearls;
   private BooleanSetting renderFishingRods;
   private BooleanSetting renderEggs;
   private BooleanSetting renderSnowballs;
   private ColorSetting trail;
   private ColorSetting fireballColor;
   private ColorSetting projectedFireballColor;
   private ColorSetting entityLanding;
   private ColorSetting wallLanding;
   private ColorSetting groundLanding;
   private BooleanSetting landingPoint;
   private BooleanSetting collisionTimer;
   private BooleanSetting highlight;
   private final Map<EntityFireball, Trajectories$3> Ms14 = new HashMap<>();
   private final Map<EntityEnderPearl, Trajectories$7> SZxL = new HashMap<>();
   private final List<Trajectories$5> ysP7 = new ArrayList<>();
   private final Trajectories$6 XJxtN = new Trajectories$6();
   private static final double Fqza = 0.6;
   private static final double THROWABLE_WATER_DRAG = 0.8;
   private static final double WATER_FLOW_ACCELERATION = 0.014;
   private static final double WATER_SCAN_EXPAND_Y = -0.4F;
   private static final double COLLISION_SHRINK = 0.001;
   private static final double LAVA_SCAN_SHRINK = -0.1F;
   private static final double FIREBALL_WATER_SCAN_EXPAND_Y = -0.4F;
   private static final int WATER_SAMPLE_COUNT = 5;
   private static final double BUOYANCY_ACCELERATION = 0.04F;
   private static final double WATER_VERTICAL_DAMPING = 0.8;
   private static final double FISHING_HOOK_WATER_DRAG = 0.9;
   private static final double jWb = 0.5;
   private static final double Fvg = 1.0;
   private static final double Wuvm = 1.0E-7;
   private static final double ENTITY_BOX_EXPANSION = 0.3;
   private static final double LANDING_SMOOTH_THRESHOLD = 0.0144;
   private static final double LANDING_SMOOTH_FACTOR = 0.35;
   private static final int uckt8 = 4;
   private static final double fhq = 4.0;
   private static final double PDGx = 0.45;
   private static final double pHi = 0.09;
   private static final double FIRST_PERSON_TRAIL_OFFSET = 0.5;
   private static final double HIGHLIGHT_MERGE_DISTANCE_SQ = 2.25;
   private static final double MAX_HIGHLIGHT_BOX_SIZE = 3.25;
   private static final int jApyuu = 3;
   private static final double Rtw = 2.75;
   private static final int nZyhl = 300;
   private static final int Zu6 = 200;
   private static final int THROWER_GRACE_TICKS = 5;
   private static final double LANDING_RESET_DISTANCE_SQ = 6.25;
   private static final double AIM_VECTOR_LENGTH = 80.0;
   private static final float BXu = 1.0F;
   private static final float DEFAULT_HIGHLIGHT_ALPHA = 1.0F;
   private static final float MIN_PROJECTED_LINE_THICKNESS = 4.0F;
   private static final int nin = 25;
   private static final double TRAIL_VIEW_DISTANCE_LIMIT = 1.0;
   private static final float sww = 0.95F;
   private static final float TRAIL_FADE_ALPHA = 0.8F;
   private static final int HIT_TYPE_NONE = 0;
   private static final int czs = 1;
   private static final int HIT_TYPE_WALL = 2;
   private static final int HIT_TYPE_GROUND = 3;
   private static final int[] ITEM_COLORS;
   private static final float BED_BUG_SNOWBALL_VELOCITY_SCALE = 1.75F;

   public Trajectories() {
      super("Trajectories", Category.render);
      this.registerSetting(this.itemsGroup = new GroupSetting("Items"));
      this.registerSetting(
         this.renderBows = new BooleanSetting(
            this.itemsGroup,
            "Render bows",
            true
         )
      );
      this.registerSetting(
         this.onlyCharged = new BooleanSetting(
            this.itemsGroup, "Only Charged", false
         )
      );
      this.registerSetting(this.renderFireballs = new BooleanSetting(this.itemsGroup, "Render fireballs", true));
      this.registerSetting(
         this.projectedFireball = new BooleanSetting(
            this.itemsGroup,
            "Projected Fireball",
            true
         )
      );
      this.registerSetting(this.renderEnderPearls = new BooleanSetting(this.itemsGroup, "Render ender pearls", true));
      this.registerSetting(this.renderFishingRods = new BooleanSetting(this.itemsGroup, "Render fishing rods", true));
      this.registerSetting(this.renderEggs = new BooleanSetting(this.itemsGroup, "Render eggs", true));
      this.registerSetting(
         this.renderSnowballs = new BooleanSetting(
            this.itemsGroup,
            "Render snowballs",
            true
         )
      );
      this.registerSetting(this.thickness = new SliderSetting("Thickness", 2.0, 1.0, 5.0, 0.1, new String[]{"Line thickness"}));
      this.registerSetting(
         this.trail = new ColorSetting(
            "Trail",
            170,
            0,
            255,
            255
         )
      );
      this.registerSetting(
         this.fireballColor = new ColorSetting(
            "Fireball Color",
            255,
            150,
            0,
            255
         )
      );
      this.registerSetting(
         this.projectedFireballColor = new ColorSetting(
            "Projected Fireball Color",
            255,
            0,
            0,
            153
         )
      );
      this.registerSetting(
         this.entityLanding = new ColorSetting(
            "Entity Landing",
            255,
            50,
            50,
            255
         )
      );
      this.registerSetting(
         this.wallLanding = new ColorSetting(
            "Wall Landing",
            50,
            255,
            50,
            255
         )
      );
      this.registerSetting(
         this.groundLanding = new ColorSetting(
            "Ground Landing",
            85,
            255,
            255,
            255
         )
      );
      this.registerSetting(
         this.landingPoint = new BooleanSetting(
            "Landing Point",
            true,
            new String[]{"Show landing"}
         )
      );
      this.registerSetting(
         this.collisionTimer = new BooleanSetting(
            "Collision Timer", true
         )
      );
      this.registerSetting(
         this.highlight = new BooleanSetting(
            "Highlight", true
         )
      );
   }

   private float computeBowCharge(float var1) {
      int var2 = mc.thePlayer.getItemInUseCount();
      float var3 = 72000 - var2 + var1;
      return (float)ProjectileMotion.getInterpolatedArrowSpeed(var3);
   }

   private ProjectilePhysics NVUX(Item var1, EntityPlayer var2, float var3) {
      if (var1 == Items.bow) {
         float var4 = this.computeBowCharge(var3);
         return new ProjectilePhysics(ProjectileType.ARROW, 0.05, 0.99, 0.6, 0.5, 0.5, 0.5, var4, false, true);
      } else if (var1 == Items.ender_pearl) {
         return new ProjectilePhysics(ProjectileType.THROWABLE, 0.03, 0.99, 0.8, 0.25, 0.25, 0.25, 1.5, true, false);
      } else if (var1 == Items.snowball || var1 == Items.egg) {
         return new ProjectilePhysics(ProjectileType.THROWABLE, 0.03, 0.99, 0.8, 0.25, 0.25, 0.25, 1.5, true, false);
      } else if (var1 == Items.experience_bottle) {
         return new ProjectilePhysics(ProjectileType.THROWABLE, 0.07, 0.99, 0.8, 0.25, 0.25, 0.25, 0.7, true, false);
      } else if (var1 == Items.potionitem) {
         return new ProjectilePhysics(ProjectileType.THROWABLE, 0.05, 0.99, 0.8, 0.25, 0.25, 0.25, 0.5, true, false);
      } else {
         return var1 == Items.fishing_rod ? new ProjectilePhysics(ProjectileType.FISH_HOOK, 0.04, 0.92, 0.92, 0.25, 0.25, 0.25, 1.5, false, false) : null;
      }
   }

   private boolean isBedwarsGame() {
      if (ClientUtils.isOnHypixel() && mc.theWorld != null) {
         Scoreboard var1 = mc.theWorld.getScoreboard();
         if (var1 == null) {
            return false;
         } else {
            ScoreObjective var2 = var1.getObjectiveInDisplaySlot(1);
            return var2 != null && ClientUtils.zaUnpz(var2.getDisplayName()).contains("BED WARS");
         }
      } else {
         return false;
      }
   }

   private boolean isBedBugSnowball(ItemStack var1) {
      if (var1 != null && var1.getItem() == Items.snowball) {
         String var2 = var1.getDisplayName();
         return var2 == null ? false : ClientUtils.zaUnpz(var2).toLowerCase().contains("bedbug");
      } else {
         return false;
      }
   }

   private FluidState TDWrrS(double var1, double var3, double var5, ProjectilePhysics var7) {
      AxisAlignedBB var8 = this.makeProjectileBox(var1, var3, var5, var7);
      AxisAlignedBB var9 = var8.expand(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001);
      int var10 = MathHelper.floor_double(var9.minX);
      int var11 = MathHelper.floor_double(var9.maxX + 1.0);
      int var12 = MathHelper.floor_double(var9.minY);
      int var13 = MathHelper.floor_double(var9.maxY + 1.0);
      int var14 = MathHelper.floor_double(var9.minZ);
      int var15 = MathHelper.floor_double(var9.maxZ + 1.0);
      if (!mc.theWorld.isAreaLoaded(new BlockPos(var10, var12, var14), new BlockPos(var11, var13, var15), true)) {
         return new FluidState(false, false, new Vec3(0.0, 0.0, 0.0));
      } else {
         boolean var16 = false;
         Vec3 var17 = new Vec3(0.0, 0.0, 0.0);
         MutableBlockPos var18 = new MutableBlockPos();

         for (int var19 = var10; var19 < var11; var19++) {
            for (int var20 = var12; var20 < var13; var20++) {
               for (int var21 = var14; var21 < var15; var21++) {
                  var18.set(var19, var20, var21);
                  IBlockState var22 = mc.theWorld.getBlockState(var18);
                  if (var22.getBlock().getMaterial() == Material.water) {
                     double var23 = var20 + 1 - BlockLiquid.getLiquidHeightPercent((Integer)var22.getValue(BlockLiquid.LEVEL));
                     if (var13 >= var23) {
                        var16 = true;
                        var17 = var22.getBlock().modifyAcceleration(mc.theWorld, var18, mc.thePlayer, var17);
                     }
                  }
               }
            }
         }

         if (var17.lengthVector() > 0.0) {
            var17 = var17.normalize();
         }

         boolean var25 = mc.theWorld.isMaterialInBB(var8.expand(-0.1F, -0.4F, -0.1F), Material.lava);
         return new FluidState(var16, var25, var17);
      }
   }

   private AxisAlignedBB makeProjectileBox(double var1, double var3, double var5, ProjectilePhysics var7) {
      return new AxisAlignedBB(var1 - var7.dHgx * 0.5, var3, var5 - var7.dHgx * 0.5, var1 + var7.dHgx * 0.5, var3 + var7.height, var5 + var7.dHgx * 0.5);
   }

   private double getWaterSubmersion(double var1, double var3, double var5, ProjectilePhysics var7) {
      AxisAlignedBB var8 = this.makeProjectileBox(var1, var3, var5, var7);
      double var9 = 0.0;

      for (int var11 = 0; var11 < 5; var11++) {
         double var12 = var8.maxY - var8.minY;
         double var14 = var8.minY + var12 * var11 / 5.0;
         double var16 = var8.minY + var12 * (var11 + 1) / 5.0;
         AxisAlignedBB var18 = new AxisAlignedBB(var8.minX, var14, var8.minZ, var8.maxX, var16, var8.maxZ);
         if (mc.theWorld.isAABBInMaterial(var18, Material.water)) {
            var9 += 0.2;
         }
      }

      return var9;
   }

   private void applyWaterCurrent(FluidState var1, double[] var2) {
      if (var1.inWater && !(var1.vec3.lengthVector() <= 0.0)) {
         var2[0] += var1.vec3.xCoord * 0.014;
         var2[1] += var1.vec3.yCoord * 0.014;
         var2[2] += var1.vec3.zCoord * 0.014;
      }
   }

   private void applyDragAndGravity(ProjectilePhysics var1, FluidState var2, double[] var3) {
      double var4 = var2.inWater ? var1.waterDrag : var1.SjX;
      var3[0] *= var4;
      var3[1] *= var4;
      var3[2] *= var4;
      var3[1] -= var1.gravity;
   }

   private void applyFishHookPhysics(double var1, double var3, double var5, ProjectilePhysics var7, double[] var8) {
      double var9 = var7.SjX;
      double var11 = this.getWaterSubmersion(var1, var3, var5, var7);
      double var13 = var11 * 2.0 - 1.0;
      var8[1] += 0.04F * var13;
      if (var11 > 0.0) {
         var9 *= 0.9;
         var8[1] *= 0.8;
      }

      var8[0] *= var9;
      var8[1] *= var9;
      var8[2] *= var9;
   }

   private void applyProjectileMotion(double var1, double var3, double var5, ProjectilePhysics var7, FluidState var8, double[] var9) {
      if (var7.projectileType == ProjectileType.FISH_HOOK) {
         this.applyFishHookPhysics(var1, var3, var5, var7, var9);
      } else {
         if (var8.inLava && !var8.inWater) {
         }

         this.applyDragAndGravity(var7, var8, var9);
      }
   }

   private AxisAlignedBB expandEntityBox(AxisAlignedBB var1) {
      return var1.expand(0.3, 0.3, 0.3);
   }

   private AxisAlignedBB boxAroundSegment(Vec3 var1, Vec3 var2) {
      return new AxisAlignedBB(
         Math.min(var1.xCoord, var2.xCoord),
         Math.min(var1.yCoord, var2.yCoord),
         Math.min(var1.zCoord, var2.zCoord),
         Math.max(var1.xCoord, var2.xCoord),
         Math.max(var1.yCoord, var2.yCoord),
         Math.max(var1.zCoord, var2.zCoord)
      );
   }

   private AxisAlignedBB getBlockBoundingBox(Block var1, BlockPos var2) {
      var1.setBlockBoundsBasedOnState(mc.theWorld, var2);
      return var1.getSelectedBoundingBox(mc.theWorld, var2);
   }

   private AxisAlignedBB intersectBoxes(AxisAlignedBB var1, AxisAlignedBB var2) {
      double var3 = Math.max(var1.minX, var2.minX);
      double var5 = Math.max(var1.minY, var2.minY);
      double var7 = Math.max(var1.minZ, var2.minZ);
      double var9 = Math.min(var1.maxX, var2.maxX);
      double var11 = Math.min(var1.maxY, var2.maxY);
      double var13 = Math.min(var1.maxZ, var2.maxZ);
      return !(var9 - var3 <= 1.0E-7) && !(var11 - var5 <= 1.0E-7) && !(var13 - var7 <= 1.0E-7)
         ? new AxisAlignedBB(var3, var5, var7, var9, var11, var13)
         : null;
   }

   private Trajectories$1 EdobD(Vec3 var1, Vec3 var2, ProjectilePhysics var3) {
      AxisAlignedBB var4 = this.boxAroundSegment(var1, var2);
      int var5 = MathHelper.floor_double(var4.minX);
      int var6 = MathHelper.floor_double(var4.maxX + 1.0);
      int var7 = MathHelper.floor_double(var4.minY);
      int var8 = MathHelper.floor_double(var4.maxY + 1.0);
      int var9 = MathHelper.floor_double(var4.minZ);
      int var10 = MathHelper.floor_double(var4.maxZ + 1.0);
      if (!mc.theWorld.isAreaLoaded(new BlockPos(var5, var7, var9), new BlockPos(var6, var8, var10), true)) {
         return new Trajectories$1(null, Double.MAX_VALUE);
      } else {
         ArrayList var11 = new ArrayList();
         MutableBlockPos var12 = new MutableBlockPos();
         MovingObjectPosition var13 = null;
         double var14 = Double.MAX_VALUE;

         for (int var16 = var5; var16 < var6; var16++) {
            for (int var17 = var7; var17 < var8; var17++) {
               for (int var18 = var9; var18 < var10; var18++) {
                  var12.set(var16, var17, var18);
                  IBlockState var19 = mc.theWorld.getBlockState(var12);
                  Block var20 = var19.getBlock();
                  if ((!var3.ignoreBlockWithoutBoundingBox || var20.getCollisionBoundingBox(mc.theWorld, var12, var19) != null) && var20.canCollideCheck(var19, false)) {
                     var11.clear();
                     AxisAlignedBB var21 = this.getBlockBoundingBox(var20, var12);
                     var20.addCollisionBoxesToList(mc.theWorld, var12, var19, var4, var11, null);

                     for (AxisAlignedBB var23 : (java.lang.Iterable<AxisAlignedBB>) (java.lang.Iterable<?>) (var11)) {
                        AxisAlignedBB var24 = this.intersectBoxes(var23, var21);
                        if (var24 != null) {
                           MovingObjectPosition var25 = var24.calculateIntercept(var1, var2);
                           if (var25 != null) {
                              double var26 = var1.squareDistanceTo(var25.hitVec);
                              if (var26 + 1.0E-7 < var14) {
                                 var14 = var26;
                                 var13 = new MovingObjectPosition(var25.hitVec, var25.sideHit, new BlockPos(var12));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return new Trajectories$1(var13, var14);
      }
   }

   private Trajectories$1 rayTraceCollision(Vec3 var1, Vec3 var2, ProjectilePhysics var3) {
      MovingObjectPosition var4 = mc.theWorld.rayTraceBlocks(var1, var2, false, var3.ignoreBlockWithoutBoundingBox, false);
      double var5 = var4 != null ? var1.squareDistanceTo(var4.hitVec) : Double.MAX_VALUE;
      Trajectories$1 var7 = this.EdobD(var1, var2, var3);
      return var7.hitResult != null && var7.squaredHitDistance + 1.0E-7 < var5 ? var7 : new Trajectories$1(var4, var5);
   }

   private AxisAlignedBB makeEntityBox(double var1, double var3, double var5, float var7, float var8) {
      double var9 = var7 * 0.5;
      return new AxisAlignedBB(var1 - var9, var3, var5 - var9, var1 + var9, var3 + var8, var5 + var9);
   }

   private AxisAlignedBB am71(double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14) {
      AxisAlignedBB var15 = this.makeEntityBox(var1, var3, var5, var13, var14);
      AxisAlignedBB var16 = var15.offset(var7, var9, var11);
      return new AxisAlignedBB(
         Math.min(var15.minX, var16.minX),
         Math.min(var15.minY, var16.minY),
         Math.min(var15.minZ, var16.minZ),
         Math.max(var15.maxX, var16.maxX),
         Math.max(var15.maxY, var16.maxY),
         Math.max(var15.maxZ, var16.maxZ)
      );
   }

   private AxisAlignedBB expandBox(AxisAlignedBB var1, double var2, double var4, double var6) {
      return new AxisAlignedBB(
         var1.minX - var2 - var6, var1.minY - var4 - var6, var1.minZ - var2 - var6, var1.maxX + var2 + var6, var1.maxY + var6, var1.maxZ + var2 + var6
      );
   }

   private FluidState scanFireballWaterEffects(EntityFireball var1, double var2, double var4, double var6, float var8, float var9) {
      AxisAlignedBB var10 = this.makeEntityBox(var2, var4, var6, var8, var9).expand(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001);
      int var11 = MathHelper.floor_double(var10.minX);
      int var12 = MathHelper.floor_double(var10.maxX + 1.0);
      int var13 = MathHelper.floor_double(var10.minY);
      int var14 = MathHelper.floor_double(var10.maxY + 1.0);
      int var15 = MathHelper.floor_double(var10.minZ);
      int var16 = MathHelper.floor_double(var10.maxZ + 1.0);
      if (!mc.theWorld.isAreaLoaded(new BlockPos(var11, var13, var15), new BlockPos(var12, var14, var16), true)) {
         return new FluidState(false, false, new Vec3(0.0, 0.0, 0.0));
      } else {
         boolean var17 = false;
         Vec3 var18 = new Vec3(0.0, 0.0, 0.0);
         MutableBlockPos var19 = new MutableBlockPos();

         for (int var20 = var11; var20 < var12; var20++) {
            for (int var21 = var13; var21 < var14; var21++) {
               for (int var22 = var15; var22 < var16; var22++) {
                  var19.set(var20, var21, var22);
                  IBlockState var23 = mc.theWorld.getBlockState(var19);
                  if (var23.getBlock().getMaterial() == Material.water) {
                     double var24 = var21 + 1 - BlockLiquid.getLiquidHeightPercent((Integer)var23.getValue(BlockLiquid.LEVEL));
                     if (var14 >= var24) {
                        var17 = true;
                        var18 = var23.getBlock().modifyAcceleration(mc.theWorld, var19, var1, var18);
                     }
                  }
               }
            }
         }

         if (var18.lengthVector() > 0.0) {
            var18 = var18.normalize();
         }

         return new FluidState(var17, false, var18);
      }
   }

   private Trajectories$2 rayTraceBlocksExpanded(Vec3 var1, Vec3 var2, AxisAlignedBB var3, double var4, double var6) {
      int var8 = MathHelper.floor_double(var3.minX);
      int var9 = MathHelper.floor_double(var3.maxX + 1.0);
      int var10 = MathHelper.floor_double(var3.minY);
      int var11 = MathHelper.floor_double(var3.maxY + 1.0);
      int var12 = MathHelper.floor_double(var3.minZ);
      int var13 = MathHelper.floor_double(var3.maxZ + 1.0);
      if (!mc.theWorld.isAreaLoaded(new BlockPos(var8, var10, var12), new BlockPos(var9, var11, var13), true)) {
         return new Trajectories$2(null, null, Double.MAX_VALUE);
      } else {
         ArrayList var14 = new ArrayList();
         MutableBlockPos var15 = new MutableBlockPos();
         MovingObjectPosition var16 = null;
         Vec3 var17 = null;
         double var18 = Double.MAX_VALUE;

         for (int var20 = var8; var20 < var9; var20++) {
            for (int var21 = var10; var21 < var11; var21++) {
               for (int var22 = var12; var22 < var13; var22++) {
                  var15.set(var20, var21, var22);
                  IBlockState var23 = mc.theWorld.getBlockState(var15);
                  Block var24 = var23.getBlock();
                  if (var24.canCollideCheck(var23, false)) {
                     var14.clear();
                     var24.addCollisionBoxesToList(mc.theWorld, var15, var23, var3, var14, null);

                     for (AxisAlignedBB var26 : (java.lang.Iterable<AxisAlignedBB>) (java.lang.Iterable<?>) (var14)) {
                        AxisAlignedBB var27 = this.expandBox(var26, var4, var6, 0.0);
                        MovingObjectPosition var28 = var27.calculateIntercept(var1, var2);
                        if (var28 == null && var27.isVecInside(var1)) {
                           var28 = new MovingObjectPosition(var1, EnumFacing.UP, new BlockPos(var15));
                        }

                        if (var28 != null) {
                           double var29 = var1.squareDistanceTo(var28.hitVec);
                           if (var29 + 1.0E-7 < var18) {
                              var18 = var29;
                              var17 = var28.hitVec;
                              var16 = new MovingObjectPosition(var28.hitVec, var28.sideHit, new BlockPos(var15));
                           }
                        }
                     }
                  }
               }
            }
         }

         return new Trajectories$2(var16, var17, var18);
      }
   }

   private Trajectories$4 SXdjHn5(EntityFireball var1, RenderManager var2, float var3, Trajectories$3 var4) {
      ArrayList var5 = new ArrayList();
      Vec3 var6 = this.smoothFireballPos(var4, this.getInterpolatedFireballPos(var1, var3));
      Vec3 var7 = this.smoothMotionVector(var4, new Vec3(var1.motionX, var1.motionY, var1.motionZ));
      double var8 = var6.xCoord;
      double var10 = var6.yCoord;
      double var12 = var6.zCoord;
      double var14 = var7.xCoord;
      double var16 = var7.yCoord;
      double var18 = var7.zCoord;
      double var20 = var14 * var14 + var16 * var16 + var18 * var18;
      if (var20 <= 1.0E-7) {
         var7 = this.smoothMotionVector(var4, new Vec3(var1.accelerationX, var1.accelerationY, var1.accelerationZ));
         var14 = var7.xCoord;
         var16 = var7.yCoord;
         var18 = var7.zCoord;
         var20 = var14 * var14 + var16 * var16 + var18 * var18;
      }

      float var22 = var1.width;
      float var23 = var1.height;
      double var24 = var22 * 0.5;
      EntityLivingBase var26 = var1.shootingEntity;
      int var27 = Math.max(0, var1.ticksExisted);
      new Vec3(var8, var10, var12);
      var5.add(new double[]{var8 - var2.viewerPosX, var10 - var2.viewerPosY, var12 - var2.viewerPosZ});
      if (var20 <= 1.0E-7) {
         return new Trajectories$4(var5, null, null, null, null, 0, 300.0);
      } else {
         for (int var29 = 0; var29 < 300; var29++) {
            Vec3 var30 = new Vec3(var8, var10, var12);
            Vec3 var31 = new Vec3(var8 + var14, var10 + var16, var12 + var18);
            AxisAlignedBB var32 = this.am71(var8, var10, var12, var14, var16, var18, var22, var23);
            Trajectories$2 var33 = this.rayTraceBlocksExpanded(var30, var31, var32, var24, var23);
            Vec3 var34 = var33.hitVec != null ? var33.hitVec : var31;
            double var35 = var33.squaredHitDistance;
            Entity var37 = null;
            Vec3 var38 = null;
            AxisAlignedBB var39 = null;
            AxisAlignedBB var40 = var32.expand(1.0, 1.0, 1.0);

            for (Entity var43 : mc.theWorld.getEntitiesWithinAABBExcludingEntity(var1, var40)) {
               if (var43.canBeCollidedWith()
                  && (var43 != var26 || var27 >= 25)
                  && !(var43 instanceof EntityArmorStand)
                  && (!(var43 instanceof EntityPlayer) || !AntiBot.shouldHideEntity(var43))) {
                  AxisAlignedBB var44 = this.expandBox(var43.getEntityBoundingBox(), var24, var23, 0.3);
                  MovingObjectPosition var45 = var44.calculateIntercept(var30, var34);
                  if (var45 == null && var44.isVecInside(var30)) {
                     var45 = new MovingObjectPosition(var43, var30);
                  }

                  if (var45 != null) {
                     double var46 = var30.squareDistanceTo(var45.hitVec);
                     if (var46 + 1.0E-7 < var35) {
                        var35 = var46;
                        var37 = var43;
                        var38 = var45.hitVec;
                        var39 = var44;
                     }
                  }
               }
            }

            if (var37 != null) {
               var5.add(new double[]{var38.xCoord - var2.viewerPosX, var38.yCoord - var2.viewerPosY, var38.zCoord - var2.viewerPosZ});
               int var50 = var37 instanceof EntityPlayer ? 1 : 0;
               return new Trajectories$4(var5, null, var37, var39, var38, var50, var29 + this.computeSegmentRatio(var30, var31, var38));
            }

            if (var33.movingObjectPosition != null) {
               Vec3 var49 = var33.hitVec != null ? var33.hitVec : var33.movingObjectPosition.hitVec;
               var5.add(new double[]{var49.xCoord - var2.viewerPosX, var49.yCoord - var2.viewerPosY, var49.zCoord - var2.viewerPosZ});
               int var51 = var33.movingObjectPosition.sideHit.getIndex();
               int var52 = var51 != 0 && var51 != 1 ? 2 : 3;
               return new Trajectories$4(var5, var33.movingObjectPosition, null, null, var49, var52, var29 + this.computeSegmentRatio(var30, var31, var49));
            }

            var8 += var14;
            var10 += var16;
            var12 += var18;
            new Vec3(var8, var10, var12);
            var5.add(new double[]{var8 - var2.viewerPosX, var10 - var2.viewerPosY, var12 - var2.viewerPosZ});
            var27++;
            if (var10 < -64.0) {
               break;
            }
         }

         return new Trajectories$4(var5, null, null, null, null, 0, 300.0);
      }
   }

   private double computeSegmentRatio(Vec3 var1, Vec3 var2, Vec3 var3) {
      return TrajectoryMath.computeSegmentRatio(var1, var2, var3);
   }

   private List<double[]> TAJm(List<double[]> var1, double var2) {
      if (!(var2 <= 0.0) && var1.size() >= 2) {
         ArrayList var4 = new ArrayList();
         double var5 = var2;
         double[] var7 = (double[])var1.get(0);

         for (int var8 = 1; var8 < var1.size(); var8++) {
            double[] var9 = (double[])var1.get(var8);
            double var10 = var9[0] - var7[0];
            double var12 = var9[1] - var7[1];
            double var14 = var9[2] - var7[2];
            double var16 = Math.sqrt(var10 * var10 + var12 * var12 + var14 * var14);
            if (var5 > 0.0) {
               if (var16 <= 1.0E-7) {
                  var7 = var9;
                  continue;
               }

               if (var16 <= var5) {
                  var5 -= var16;
                  var7 = var9;
                  continue;
               }

               double var18 = var5 / var16;
               var4.add(new double[]{var7[0] + var10 * var18, var7[1] + var12 * var18, var7[2] + var14 * var18});
               var4.add(var9);
               var5 = 0.0;
            } else {
               var4.add(var9);
            }

            var7 = var9;
         }

         return var4;
      } else {
         return var1;
      }
   }

   private Vec3 smoothFireballPos(Trajectories$3 var1, Vec3 var2) {
      if (var1.smoothedPosition == null) {
         var1.smoothedPosition = var2;
         return var1.smoothedPosition;
      } else {
         if (var1.smoothedPosition.squareDistanceTo(var2) <= 4.0) {
            var1.smoothedPosition = this.QSEpo(var1.smoothedPosition, var2, 0.45);
         } else {
            var1.smoothedPosition = var2;
         }

         return var1.smoothedPosition;
      }
   }

   private Vec3 getInterpolatedFireballPos(EntityFireball var1, float var2) {
      double var3 = var1.posX - var1.lastTickPosX;
      double var5 = var1.posY - var1.lastTickPosY;
      double var7 = var1.posZ - var1.lastTickPosZ;
      return var1.ticksExisted > 1 && !(var3 * var3 + var5 * var5 + var7 * var7 > 9.0)
         ? new Vec3(var1.lastTickPosX + var3 * var2, var1.lastTickPosY + var5 * var2, var1.lastTickPosZ + var7 * var2)
         : new Vec3(var1.posX, var1.posY, var1.posZ);
   }

   private Vec3 smoothMotionVector(Trajectories$3 var1, Vec3 var2) {
      if (var1.VmKjy == null) {
         var1.VmKjy = var2;
         return var1.VmKjy;
      } else {
         if (var1.VmKjy.squareDistanceTo(var2) <= 0.09) {
            var1.VmKjy = this.QSEpo(var1.VmKjy, var2, 0.5);
         } else {
            var1.VmKjy = var2;
         }

         return var1.VmKjy;
      }
   }

   private Vec3 QSEpo(Vec3 var1, Vec3 var2, double var3) {
      return TrajectoryMath.lerpVec(var1, var2, var3);
   }

   private double computeTrailStartOffset(double var1, double var3, double var5, double var7, double var9, double var11, RenderManager var13) {
      if (mc.gameSettings.thirdPersonView == 0) {
         return 0.5;
      } else if (var13 == null) {
         return 0.0;
      } else {
         double var14 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
         if (var14 <= 1.0E-7) {
            return 0.0;
         } else {
            double var16 = var13.viewerPosX - var1;
            double var18 = var13.viewerPosY - var3;
            double var20 = var13.viewerPosZ - var5;
            double var22 = var7 / var14;
            double var24 = var9 / var14;
            double var26 = var11 / var14;
            double var28 = var16 * var22 + var18 * var24 + var20 * var26;
            if (var28 <= 0.0) {
               return 0.0;
            } else {
               double var30 = var16 * var16 + var18 * var18 + var20 * var20;
               double var32 = var30 - var28 * var28;
               double var34 = 1.0;
               return var32 > var34 ? 0.0 : var28 + 1.0;
            }
         }
      }
   }

   private ItemStack byV2(EntityPlayer var1) {
      ItemStack var2 = var1.getHeldItem();
      if (var2 == null) {
         return null;
      } else {
         Item var3 = var2.getItem();
         if (!this.nyx7(var3)) {
            return null;
         } else {
            if (var3 instanceof ItemBow && this.onlyCharged.isToggled()) {
               ItemStack var4 = var1.getItemInUse();
               if (var4 == null || !(var4.getItem() instanceof ItemBow)) {
                  return null;
               }
            }

            if (var3 == Items.ender_pearl || var3 == Items.snowball || var3 == Items.egg || var3 == Items.experience_bottle) {
               return var2;
            } else if (var3 == Items.potionitem) {
               return ItemPotion.isSplash(var2.getMetadata()) ? var2 : null;
            } else if (var3 instanceof ItemBow) {
               return var2;
            } else {
               return var3 == Items.fishing_rod ? var2 : null;
            }
         }
      }
   }

   private boolean nyx7(Item var1) {
      if (var1 instanceof ItemBow) {
         return this.renderBows.isToggled();
      } else if (var1 instanceof ItemEnderPearl) {
         return this.renderEnderPearls.isToggled();
      } else if (var1 == Items.fishing_rod) {
         return this.renderFishingRods.isToggled();
      } else if (var1 instanceof ItemEgg) {
         return this.renderEggs.isToggled();
      } else {
         return var1 instanceof ItemSnowball ? this.renderSnowballs.isToggled() : true;
      }
   }

   private int getItemColorIndex(ItemStack var1) {
      if (var1 == null) {
         return -1;
      } else {
         Item var2 = var1.getItem();
         if (var2 == Items.iron_ingot) {
            return 0;
         } else if (var2 == Items.gold_ingot) {
            return 1;
         } else if (var2 == Items.emerald) {
            return 2;
         } else {
            return var2 == Items.diamond ? 3 : -1;
         }
      }
   }

   private AxisAlignedBB unionBoxes(AxisAlignedBB var1, AxisAlignedBB var2) {
      return TrajectoryMath.unionBoxes(var1, var2);
   }

   private AxisAlignedBB[] EwwNxt9(Vec3 var1) {
      AxisAlignedBB[] var2 = new AxisAlignedBB[ITEM_COLORS.length];
      if (var1 != null && mc.theWorld != null) {
         AxisAlignedBB var3 = new AxisAlignedBB(
            var1.xCoord - 2.75, var1.yCoord - 2.75, var1.zCoord - 2.75, var1.xCoord + 2.75, var1.yCoord + 2.75, var1.zCoord + 2.75
         );
         double var4 = 7.5625;

         for (EntityItem var8 : mc.theWorld.getEntitiesWithinAABB(EntityItem.class, var3)) {
            if (var8 != null && !var8.isDead) {
               int var9 = this.getItemColorIndex(var8.getEntityItem());
               if (var9 >= 0) {
                  double var10 = var8.posX - var1.xCoord;
                  double var12 = var8.posY - var1.yCoord;
                  double var14 = var8.posZ - var1.zCoord;
                  if (!(var10 * var10 + var12 * var12 + var14 * var14 > var4)) {
                     AxisAlignedBB var16 = var8.getEntityBoundingBox().expand(0.18, 0.18, 0.18);
                     var2[var9] = this.unionBoxes(var2[var9], var16);
                  }
               }
            }
         }

         return var2;
      } else {
         return var2;
      }
   }

   private boolean hasAnyBox(AxisAlignedBB[] var1) {
      if (var1 == null) {
         return false;
      } else {
         for (AxisAlignedBB var5 : var1) {
            if (var5 != null) {
               return true;
            }
         }

         return false;
      }
   }

   private int getHighlightColor(AxisAlignedBB[] var1) {
      if (var1 == null) {
         return this.trail.getArgb();
      } else {
         for (int var2 = var1.length - 1; var2 >= 0; var2--) {
            if (var1[var2] != null) {
               return ITEM_COLORS[var2];
            }
         }

         return this.trail.getArgb();
      }
   }

   private AxisAlignedBB LpHo(AxisAlignedBB[] var1) {
      AxisAlignedBB var2 = null;
      if (var1 == null) {
         return null;
      } else {
         for (AxisAlignedBB var6 : var1) {
            var2 = this.unionBoxes(var2, var6);
         }

         return var2;
      }
   }

   private void drawTargetHighlight(AxisAlignedBB var1, double var2, double var4, double var6, int var8) {
      this.renderBoxOutline(var1, var2, var4, var6, var8, 1.0F);
   }

   private void renderBoxOutline(AxisAlignedBB var1, double var2, double var4, double var6, int var8, float var9) {
      if (var1 != null) {
         float var10 = (var8 >> 16 & 0xFF) / 255.0F;
         float var11 = (var8 >> 8 & 0xFF) / 255.0F;
         float var12 = (var8 & 0xFF) / 255.0F;
         float var13 = getColorAlpha(var8) * Math.max(0.0F, Math.min(1.0F, var9));
         AxisAlignedBB var14 = var1.offset(-var2, -var4, -var6);
         if (this.VIuQ()) {
            ExternalRenderBuffer var15 = ExternalRenderer.getActiveRenderBuffer();
            ScreenProjector var16 = ExternalRenderer.getActiveScreenProjector();
            int var17 = ClientUtils.YVVZ(var8, Math.round(var13 * 255.0F));
            if (var15 != null
               && var16 != null
               && var16.drawProjectedBox(var14.minX, var14.minY, var14.minZ, var14.maxX, var14.maxY, var14.maxZ, var15, var17, 0.0F, false)) {
               var16.HIQRn(var15, ClientUtils.YVVZ(var8, Math.round(0.18F * var13 * 255.0F)));
               var16.drawProjectedBox(var14.minX, var14.minY, var14.minZ, var14.maxX, var14.maxY, var14.maxZ, var15, var17, 2.5F, true);
            }
         } else {
            GL11.glLineWidth(2.5F);
            GL11.glColor4f(var10, var11, var12, var13);
            RenderUtils.drawBoxOutline(var14);
            RenderUtils.drawFilledAabb(var14, var10, var11, var12, 0.18F * var13);
         }
      }
   }

   private void drawHighlightBoxes(Entity var1, AxisAlignedBB var2, AxisAlignedBB[] var3, double var4, double var6, double var8) {
      if (this.highlight.isToggled()) {
         if (var1 instanceof EntityPlayer && var2 != null) {
            this.drawTargetHighlight(var2.expand(0.05, 0.05, 0.05), var4, var6, var8, this.entityLanding.getArgb());
         }

         if (var3 != null) {
            this.drawTargetHighlight(this.LpHo(var3), var4, var6, var8, this.getHighlightColor(var3));
         }
      }
   }

   private void OIIU(Trajectories$3 var1, double var2, double var4, double var6, float var8) {
      if (var1.axisAlignedBB != null) {
         this.renderBoxOutline(var1.axisAlignedBB, var2, var4, var6, this.fireballColor.getArgb(), var8);
      }
   }

   private void RHayfx(Trajectories$3 var1, AxisAlignedBB var2) {
      if (var2 != null) {
         var1.lingerTicks = 0;
         if (var1.axisAlignedBB == null) {
            var1.axisAlignedBB = var2;
         } else {
            double var3 = this.getBoxDistanceSquared(var1.axisAlignedBB, var2);
            if (var3 <= 2.25) {
               AxisAlignedBB var5 = this.unionBoxes(var1.axisAlignedBB, var2);
               var1.axisAlignedBB = this.isSmallBox(var5) ? var5 : var2;
            } else {
               var1.axisAlignedBB = var2;
            }
         }
      } else {
         if (var1.axisAlignedBB != null && var1.lingerTicks < 3) {
            var1.lingerTicks++;
         } else {
            var1.axisAlignedBB = null;
         }
      }
   }

   private AxisAlignedBB zuWi(Trajectories$4 var1) {
      if (!this.slce(var1)) {
         return null;
      } else {
         AxisAlignedBB var2 = null;
         if (var1.entityHitBox != null) {
            var2 = this.unionBoxes(var2, var1.entityHitBox);
         }

         if (var1.mCji != null && var1.mCji.getBlockPos() != null) {
            AxisAlignedBB var3 = BlockUtils.getSelectedBounds(var1.mCji.getBlockPos());
            if (var3 != null) {
               var2 = this.unionBoxes(var2, var3);
            }
         }

         if (var2 != null && var1.mCji != null && var1.mCji.getBlockPos() != null) {
            BlockPos var9 = var1.mCji.getBlockPos();

            for (EnumFacing var7 : EnumFacing.values()) {
               AxisAlignedBB var8 = BlockUtils.getSelectedBounds(var9.offset(var7));
               if (var8 != null && var1.ydz != null && var8.expand(0.08, 0.08, 0.08).isVecInside(var1.ydz)) {
                  var2 = this.unionBoxes(var2, var8);
               }
            }

            return var2;
         } else {
            return var2;
         }
      }
   }

   private double getBoxDistanceSquared(AxisAlignedBB var1, AxisAlignedBB var2) {
      return TrajectoryMath.getCenterDistanceSquared(var1, var2);
   }

   private boolean isSmallBox(AxisAlignedBB var1) {
      return var1.maxX - var1.minX <= 3.25 && var1.maxY - var1.minY <= 3.25 && var1.maxZ - var1.minZ <= 3.25;
   }

   private boolean slce(Trajectories$4 var1) {
      return var1 != null && (var1.mCji != null || var1.entity != null);
   }

   private Vec3 updateHighlightCenter(Trajectories$3 var1) {
      if (var1.axisAlignedBB == null) {
         var1.boxCenter = null;
         return null;
      } else {
         var1.boxCenter = this.getBoxCenter(var1.axisAlignedBB);
         return var1.boxCenter;
      }
   }

   private Vec3 getBoxCenter(AxisAlignedBB var1) {
      return TrajectoryMath.esYv(var1);
   }

   private void fNarY(Trajectories$4 var1, Vec3 var2, RenderManager var3, float var4) {
      if (var2 != null && !var1.Ve6.isEmpty()) {
         ArrayList var5 = new ArrayList(2);
         var5.add(var1.Ve6.get(0));
         var5.add(new double[]{var2.xCoord - var3.viewerPosX, var2.yCoord - var3.viewerPosY, var2.zCoord - var3.viewerPosZ});
         this.drawTrajectoryWithThickness(var5, 0.0, this.fireballColor.getArgb(), var4);
      }
   }

   private int kcgag5(int var1) {
      switch (var1) {
         case 1:
            return this.entityLanding.getArgb();
         case 2:
            return this.wallLanding.getArgb();
         case 3:
            return this.groundLanding.getArgb();
         default:
            return this.trail.getArgb();
      }
   }

   private void drawTrajectory(List<double[]> var1, double var2, int var4) {
      this.drawTrajectoryWithThickness(var1, var2, var4, 1.0F);
   }

   private void drawTrajectoryWithThickness(List<double[]> var1, double var2, int var4, float var5) {
      this.drawTrajectoryLine(var1, var2, var4, var5, (float)this.thickness.getInput());
   }

   private void drawTrajectoryLine(List<double[]> var1, double var2, int var4, float var5, float var6) {
      List var7 = this.TAJm(var1, var2);
      if (var7.size() >= 2) {
         if (this.VIuQ()) {
            ExternalRenderBuffer var18 = ExternalRenderer.getActiveRenderBuffer();
            ScreenProjector var19 = ExternalRenderer.getActiveScreenProjector();
            if (var18 != null && var19 != null) {
               int var20 = ClientUtils.YVVZ(var4, Math.round(getColorAlpha(var4) * Math.max(0.0F, Math.min(1.0F, var5)) * 255.0F));
               boolean var21 = false;
               double var22 = 0.0;
               double var14 = 0.0;

               for (double[] var17 : (java.lang.Iterable<double[]>) (java.lang.Iterable<?>) (var7)) {
                  if (!var19.projectPoint(var17[0], var17[1], var17[2])) {
                     var21 = false;
                  } else {
                     if (var21) {
                        var18.drawLine(var22, var14, var19.projectedPoint[0], var19.projectedPoint[1], var20, var6);
                     }

                     var22 = var19.projectedPoint[0];
                     var14 = var19.projectedPoint[1];
                     var21 = true;
                  }
               }
            }
         } else {
            float var8 = (var4 >> 16 & 0xFF) / 255.0F;
            float var9 = (var4 >> 8 & 0xFF) / 255.0F;
            float var10 = (var4 & 0xFF) / 255.0F;
            float var11 = getColorAlpha(var4) * Math.max(0.0F, Math.min(1.0F, var5));
            GL11.glColor4f(var8, var9, var10, var11);
            GL11.glLineWidth(var6);
            GL11.glBegin(3);

            for (double[] var13 : (java.lang.Iterable<double[]>) (java.lang.Iterable<?>) (var7)) {
               GL11.glVertex3d(var13[0], var13[1], var13[2]);
            }

            GL11.glEnd();
         }
      }
   }

   private RenderUtils$1 beginProjectileGlState() {
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      RenderUtils$1 var1 = RenderUtils.uyB6();
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      return var1;
   }

   private void hjFy(RenderUtils$1 var1) {
      RenderUtils.restoreLightmapState(var1);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   private Trajectories$8 simulatePearlTrajectory(EntityEnderPearl var1, RenderManager var2, float var3) {
      ArrayList var4 = new ArrayList();
      ProjectilePhysics var5 = this.NVUX(Items.ender_pearl, mc.thePlayer, var3);
      double var6 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3;
      double var8 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var3;
      double var10 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3;
      double var12 = var1.motionX;
      double var14 = var1.motionY;
      double var16 = var1.motionZ;
      double[] var18 = new double[3];
      EntityLivingBase var19 = var1.getThrower();
      int var20 = Math.max(0, var1.ticksExisted);
      var4.add(new double[]{var6 - var2.viewerPosX, var8 - var2.viewerPosY, var10 - var2.viewerPosZ});

      for (int var21 = 0; var21 < 200; var21++) {
         FluidState var22 = this.TDWrrS(var6, var8, var10, var5);
         var18[0] = var12;
         var18[1] = var14;
         var18[2] = var16;
         this.applyWaterCurrent(var22, var18);
         var12 = var18[0];
         var14 = var18[1];
         var16 = var18[2];
         Vec3 var23 = new Vec3(var6, var8, var10);
         Vec3 var24 = new Vec3(var6 + var12, var8 + var14, var10 + var16);
         Trajectories$1 var25 = this.rayTraceCollision(var23, var24, var5);
         Vec3 var26 = var25.hitResult != null ? var25.hitResult.hitVec : var24;
         double var27 = var25.squaredHitDistance;
         Entity var29 = null;
         Vec3 var30 = null;
         AxisAlignedBB var31 = null;
         AxisAlignedBB var32 = this.makeProjectileBox(var6, var8, var10, var5).addCoord(var12, var14, var16).expand(1.0, 1.0, 1.0);

         for (Entity var35 : mc.theWorld.getEntitiesWithinAABBExcludingEntity(var1, var32)) {
            if (var35.canBeCollidedWith()
               && (var35 != var19 || var20 >= 5)
               && !(var35 instanceof EntityArmorStand)
               && (!(var35 instanceof EntityPlayer) || !AntiBot.shouldHideEntity(var35))) {
               AxisAlignedBB var36 = this.expandEntityBox(var35.getEntityBoundingBox());
               MovingObjectPosition var37 = var36.calculateIntercept(var23, var26);
               if (var37 == null && var36.isVecInside(var23)) {
                  var37 = new MovingObjectPosition(var35, var23);
               }

               if (var37 != null) {
                  double var38 = var23.squareDistanceTo(var37.hitVec);
                  if (var38 + 1.0E-7 < var27) {
                     var27 = var38;
                     var29 = var35;
                     var30 = var37.hitVec;
                     var31 = this.getInterpolatedEntityBox(var35, var3);
                  }
               }
            }
         }

         if (var29 != null) {
            var4.add(this.twAh(var30, var2));
            int var44 = var29 instanceof EntityPlayer ? 1 : 0;
            return new Trajectories$8(var4, null, var29, var31, var30, var44, var21 + this.computeSegmentRatio(var23, var24, var30));
         }

         if (var25.hitResult != null) {
            Vec3 var43 = var25.hitResult.hitVec;
            var4.add(this.twAh(var43, var2));
            int var45 = var25.hitResult.sideHit.getIndex();
            int var46 = var45 != 0 && var45 != 1 ? 2 : 3;
            return new Trajectories$8(var4, var25.hitResult, null, null, var43, var46, var21 + this.computeSegmentRatio(var23, var24, var43));
         }

         var6 += var12;
         var8 += var14;
         var10 += var16;
         var4.add(new double[]{var6 - var2.viewerPosX, var8 - var2.viewerPosY, var10 - var2.viewerPosZ});
         var20++;
         var18[0] = var12;
         var18[1] = var14;
         var18[2] = var16;
         this.applyProjectileMotion(var6, var8, var10, var5, var22, var18);
         var12 = var18[0];
         var14 = var18[1];
         var16 = var18[2];
         if (var8 < -64.0) {
            break;
         }
      }

      return new Trajectories$8(var4, null, null, null, null, 0, 200.0);
   }

   private Vec3 resolveStableLanding(Trajectories$7 var1, Vec3 var2, MovingObjectPosition var3) {
      if (var2 == null) {
         var1.smoothedPosition = null;
         return null;
      } else {
         EnumFacing var4 = var3 != null && var3.sideHit != null ? var3.sideHit : EnumFacing.UP;
         if (var1.smoothedPosition == null || var1.smoothedPosition.squareDistanceTo(var2) > 6.25) {
            var1.smoothedPosition = var2;
            var1.enumFacing = var4;
         }

         return var1.smoothedPosition;
      }
   }

   private void distributeTrailOffset(List<double[]> var1, Vec3 var2, Vec3 var3) {
      if (var1 != null && var1.size() >= 2 && var2 != null && var3 != null) {
         double var4 = var3.xCoord - var2.xCoord;
         double var6 = var3.yCoord - var2.yCoord;
         double var8 = var3.zCoord - var2.zCoord;
         int var10 = var1.size() - 1;

         for (int var11 = 1; var11 <= var10; var11++) {
            double var12 = (double)var11 / var10;
            double[] var14 = (double[])var1.get(var11);
            var14[0] += var4 * var12;
            var14[1] += var6 * var12;
            var14[2] += var8 * var12;
         }
      }
   }

   private double[] twAh(Vec3 var1, RenderManager var2) {
      return new double[]{var1.xCoord - var2.viewerPosX, var1.yCoord - var2.viewerPosY, var1.zCoord - var2.viewerPosZ};
   }

   private void renderPearlTrajectories(float var1) {
      if (this.renderEnderPearls.isToggled() && mc.theWorld != null) {
         this.pruneStalePearls();
         RenderManager var2 = mc.getRenderManager();

         for (Entity var4 : mc.theWorld.loadedEntityList) {
            if (var4 instanceof EntityEnderPearl && !var4.isDead) {
               EntityEnderPearl var5 = (EntityEnderPearl)var4;
               if (var5.getThrower() != mc.thePlayer) {
                  Trajectories$7 var6 = this.getOrCreatePearlState(var5);
                  Trajectories$8 var7 = this.simulatePearlTrajectory(var5, var2, var1);
                  if (var7.JKKFzO == null) {
                     this.resolveStableLanding(var6, null, null);
                  } else {
                     Vec3 var8 = this.resolveStableLanding(var6, var7.JKKFzO, var7.WqX);
                     this.distributeTrailOffset(var7.iH439, var7.JKKFzO, var8);
                     float var9 = this.normalizeAlpha(var7.flightTicks);
                     int var10 = this.kcgag5(var7.landingType);
                     this.drawTrajectoryWithThickness(var7.iH439, 0.0, var10, var9);
                     if (this.highlight.isToggled() && var7.hitEntity != null && var7.Xzc0 != null) {
                        this.renderBoxOutline(var7.Xzc0.expand(0.05, 0.05, 0.05), var2.viewerPosX, var2.viewerPosY, var2.viewerPosZ, var10, var9);
                     } else if (this.landingPoint.isToggled() && var7.hitEntity == null) {
                        this.drawLandingMarker(
                           var8,
                           var6.enumFacing,
                           var2.viewerPosX,
                           var2.viewerPosY,
                           var2.viewerPosZ,
                           (var10 >> 16 & 0xFF) / 255.0F,
                           (var10 >> 8 & 0xFF) / 255.0F,
                           (var10 & 0xFF) / 255.0F,
                           getColorAlpha(var10) * var9
                        );
                     }

                     this.JZiZz8(var8, var7.flightTicks, var9);
                  }
               }
            }
         }
      } else {
         this.SZxL.clear();
      }
   }

   private void gfS7(float var1) {
      if (this.projectedFireball.isToggled() && mc.theWorld != null) {
         RenderManager var2 = mc.getRenderManager();

         for (EntityPlayer var4 : mc.theWorld.playerEntities) {
            if (var4 != mc.thePlayer && !var4.isDead && !AntiBot.shouldHideEntity(var4)) {
               ItemStack var5 = var4.getHeldItem();
               if (var5 != null && var5.getItem() instanceof ItemFireball) {
                  float var6 = var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * var1;
                  float var7 = var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * var1;
                  float var8 = (float)Math.toRadians(var6);
                  float var9 = (float)Math.toRadians(var7);
                  double var10 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * var1;
                  double var12 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * var1 + var4.getEyeHeight();
                  double var14 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * var1;
                  Vec3 var16 = new Vec3(var10, var12, var14);
                  Vec3 var17 = new Vec3(
                     var10 - MathHelper.sin(var8) * MathHelper.cos(var9) * 80.0,
                     var12 - MathHelper.sin(var9) * 80.0,
                     var14 + MathHelper.cos(var8) * MathHelper.cos(var9) * 80.0
                  );
                  Trajectories$2 var18 = this.traceProjectedPath(var16, var17);
                  Vec3 var19 = var18.hitVec != null ? var18.hitVec : var17;
                  ArrayList var20 = new ArrayList(2);
                  var20.add(this.twAh(var16, var2));
                  var20.add(this.twAh(var19, var2));
                  this.drawTrajectoryLine(var20, 0.0, this.projectedFireballColor.getArgb(), 1.0F, Math.max(4.0F, (float)this.thickness.getInput()));
                  if (var18.movingObjectPosition != null) {
                     AxisAlignedBB var21 = this.NImOm8(var18.movingObjectPosition, var19);
                     this.drawTargetHighlight(var21, var2.viewerPosX, var2.viewerPosY, var2.viewerPosZ, this.projectedFireballColor.getArgb());
                  }
               }
            }
         }
      }
   }

   private AxisAlignedBB NImOm8(MovingObjectPosition var1, Vec3 var2) {
      if (var1 != null && var1.getBlockPos() != null) {
         BlockPos var3 = var1.getBlockPos();
         AxisAlignedBB var4 = BlockUtils.getSelectedBounds(var3);
         if (var4 != null && var2 != null) {
            for (EnumFacing var8 : EnumFacing.values()) {
               AxisAlignedBB var9 = BlockUtils.getSelectedBounds(var3.offset(var8));
               if (var9 != null && var9.expand(0.08, 0.08, 0.08).isVecInside(var2)) {
                  var4 = this.unionBoxes(var4, var9);
               }
            }

            return var4;
         } else {
            return var4;
         }
      } else {
         return null;
      }
   }

   private Trajectories$2 traceProjectedPath(Vec3 var1, Vec3 var2) {
      double var3 = var2.xCoord - var1.xCoord;
      double var5 = var2.yCoord - var1.yCoord;
      double var7 = var2.zCoord - var1.zCoord;
      double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
      int var11 = Math.max(1, (int)Math.ceil(var9));
      Vec3 var12 = var1;

      for (int var13 = 1; var13 <= var11; var13++) {
         double var14 = Math.min(1.0, (double)var13 / var11);
         Vec3 var16 = new Vec3(var1.xCoord + var3 * var14, var1.yCoord + var5 * var14, var1.zCoord + var7 * var14);
         double var17 = var16.xCoord - var12.xCoord;
         double var19 = var16.yCoord - var12.yCoord;
         double var21 = var16.zCoord - var12.zCoord;
         AxisAlignedBB var23 = this.am71(var12.xCoord, var12.yCoord, var12.zCoord, var17, var19, var21, 1.0F, 1.0F);
         Trajectories$2 var24 = this.rayTraceBlocksExpanded(var12, var16, var23, 0.5, 1.0);
         if (var24.movingObjectPosition != null) {
            return var24;
         }

         var12 = var16;
      }

      return new Trajectories$2(null, null, Double.MAX_VALUE);
   }

   private void JZiZz8(Vec3 var1, double var2, float var4) {
      if (this.collisionTimer.isToggled() && var1 != null && var2 >= 0.0 && var4 > 0.0F) {
         this.ysP7.add(new Trajectories$5(var1, var2, var4));
      }
   }

   private void renderImpactTimerLabel(Trajectories$5 var1, RenderManager var2) {
      if (var1 != null && var1.landingPosition != null) {
         String var3 = this.formatSeconds(var1.impactTicks / 20.0);
         double var4 = var1.landingPosition.xCoord - var2.viewerPosX;
         double var6 = var1.landingPosition.yCoord - var2.viewerPosY;
         double var8 = var1.landingPosition.zCoord - var2.viewerPosZ;
         double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
         float var12 = Math.min(Math.max(0.025F, (float)(0.0018 * var10)), 0.075F);
         int var13 = MathHelper.clamp_int(Math.round(255.0F * var1.IdkI), 0, 255);
         int var14 = MathHelper.clamp_int(Math.round(153.0F * var1.IdkI), 0, 153);
         int var15 = var13 << 24 | 16777215;
         IFont var16 = FontManager.getNametagRenderer("Modern");
         float var17 = var16.getStringWidth(var3) / 2.0F;
         float var18 = var16.getFontHeight() / 2.0F;
         if (this.VIuQ()) {
            ExternalRenderBuffer var25 = ExternalRenderer.getActiveRenderBuffer();
            ScreenProjector var20 = ExternalRenderer.getActiveScreenProjector();
            if (var25 != null && var20 != null && var20.projectPoint(var4, var6 + 0.85F, var8)) {
               float var21 = Math.max(8.0F, Math.min(28.0F, var12 * var20.getProjectedScale() * var16.getFontHeight()));
               FormattedTextRenderer.drawMultiLineLabel(var25, var16, var20.projectedPoint[0], var20.projectedPoint[1], var21, 1.0F, 2.0F, var14 / 255.0F, var15, 0, 5, var3, "", "");
            }
         } else {
            GL11.glPushAttrib(1048575);
            GlStateManager.pushMatrix();
            RenderUtils$1 var19 = null;

            try {
               var19 = RenderUtils.uyB6();
               GlStateManager.translate((float)var4, (float)var6 + 0.85F, (float)var8);
               GlStateManager.rotate(-var2.playerViewY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate((mc.gameSettings.thirdPersonView == 2 ? -1 : 1) * var2.playerViewX, 1.0F, 0.0F, 0.0F);
               GlStateManager.scale(-var12, -var12, var12);
               GlStateManager.disableLighting();
               GlStateManager.disableDepth();
               GlStateManager.depthMask(false);
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.enableTexture2D();
               RenderUtils.XNRNki(-var17 - 3.0F, -var18 - 2.0F, var17 + 3.0F, var18 + 2.0F, var14 << 24);
               GlStateManager.enableBlend();
               GlStateManager.enableTexture2D();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               var16.drawString(var3, -var17, -var18, var15, true);
            } finally {
               RenderUtils.restoreLightmapState(var19);
               GlStateManager.popMatrix();
               GL11.glPopAttrib();
               GlStateManager.enableTexture2D();
               GlStateManager.enableDepth();
               GlStateManager.depthMask(true);
               GlStateManager.disableBlend();
               GlStateManager.enableLighting();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   private void drawCollisionTimers() {
      if (!this.ysP7.isEmpty()) {
         RenderManager var1 = mc.getRenderManager();

         for (Trajectories$5 var3 : this.ysP7) {
            this.renderImpactTimerLabel(var3, var1);
         }
      }
   }

   private String formatSeconds(double var1) {
      int var3 = Math.max(1, (int)Math.ceil(var1 * 10.0));
      return var3 % 10 == 0 ? var3 / 10 + "s" : var3 / 10 + "." + var3 % 10 + "s";
   }

   private void Pwicxn(float var1) {
      if (this.renderFireballs.isToggled() && mc.theWorld != null) {
         this.pruneStaleFireballs();
         RenderManager var2 = mc.getRenderManager();

         for (Entity var4 : mc.theWorld.loadedEntityList) {
            if (var4 instanceof EntityFireball && !var4.isDead) {
               EntityFireball var5 = (EntityFireball)var4;
               Trajectories$3 var6 = this.ndbyL(var5);
               Trajectories$4 var7 = this.SXdjHn5(var5, var2, var1, var6);
               float var8 = this.normalizeAlpha(var7.opN);
               if (!(var8 <= 0.0F)) {
                  this.RHayfx(var6, this.zuWi(var7));
                  Vec3 var9 = this.updateHighlightCenter(var6);
                  this.fNarY(var7, var9, var2, var8);
                  this.OIIU(var6, var2.viewerPosX, var2.viewerPosY, var2.viewerPosZ, var8);
                  if (this.slce(var7)) {
                     this.JZiZz8(var9, var7.opN, var8);
                  }
               }
            }
         }
      } else {
         this.Ms14.clear();
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld() && mc.theWorld != null) {
         this.ysP7.clear();
         if (this.hasVisibleProjectiles()) {
            this.logGlErrors("before projectile render");
            RenderUtils$1 var2 = this.VIuQ() ? null : this.beginProjectileGlState();

            try {
               this.Pwicxn(var1.YDn0);
               this.renderPearlTrajectories(var1.YDn0);
               this.gfS7(var1.YDn0);
               this.logGlErrors("after projectile draw");
            } finally {
               if (!this.VIuQ()) {
                  this.hjFy(var2);
               }

               this.logGlErrors("after projectile restore");
            }
         } else {
            this.Ms14.clear();
            this.SZxL.clear();
         }

         this.drawCollisionTimers();
         EntityPlayerSP var86 = mc.thePlayer;
         float var3 = var1.YDn0;
         ItemStack var4 = this.byV2(var86);
         if (var4 != null) {
            Item var5 = var4.getItem();
            ProjectilePhysics var6 = this.NVUX(var5, var86, var3);
            if (var6 != null) {
               double var7 = var6.velocityScale;
               if (var5 == Items.snowball && this.isBedwarsGame() && this.isBedBugSnowball(var4)) {
                  var7 = 1.75;
               }

               float var9 = var86.prevRotationYaw + (var86.rotationYaw - var86.prevRotationYaw) * var3;
               float var10 = var86.prevRotationPitch + (var86.rotationPitch - var86.prevRotationPitch) * var3;
               float var11 = (float)Math.toRadians(var9);
               float var12 = (float)Math.toRadians(var10);
               double var13 = var86.lastTickPosX + (var86.posX - var86.lastTickPosX) * var3 - MathHelper.cos(var11) * 0.16F;
               double var15 = var86.lastTickPosY + (var86.posY - var86.lastTickPosY) * var3 + var86.getEyeHeight() - 0.1;
               double var17 = var86.lastTickPosZ + (var86.posZ - var86.lastTickPosZ) * var3 - MathHelper.sin(var11) * 0.16F;
               double var19 = -MathHelper.sin(var11) * MathHelper.cos(var12);
               double var21 = -MathHelper.sin(var12);
               double var23 = MathHelper.cos(var11) * MathHelper.cos(var12);
               double var25 = Math.sqrt(var19 * var19 + var21 * var21 + var23 * var23);
               var19 /= var25;
               var21 /= var25;
               var23 /= var25;
               var19 *= var7;
               var21 *= var7;
               var23 *= var7;
               double[] var27 = new double[]{var19, var21, var23};
               if (var6.applyWaterPhysics) {
                  FluidState var28 = this.TDWrrS(var13, var15, var17, var6);
                  this.applyWaterCurrent(var28, var27);
                  this.applyDragAndGravity(var6, var28, var27);
                  var19 = var27[0];
                  var21 = var27[1];
                  var23 = var27[2];
               }

               ArrayList var96 = new ArrayList();
               MovingObjectPosition var29 = null;
               Entity var30 = null;
               AxisAlignedBB var31 = null;
               int var32 = 0;
               Vec3 var33 = null;
               Vec3 var34 = null;
               RenderManager var35 = mc.getRenderManager();
               double var36 = this.computeTrailStartOffset(var13, var15, var17, var19, var21, var23, var35);
               byte var38 = 100;
               byte var39 = 4;
               double var40 = var6.FbwT;

               for (int var42 = 0; var42 < 100; var42++) {
                  FluidState var43 = this.TDWrrS(var13, var15, var17, var6);
                  var27[0] = var19;
                  var27[1] = var21;
                  var27[2] = var23;
                  this.applyWaterCurrent(var43, var27);
                  var19 = var27[0];
                  var21 = var27[1];
                  var23 = var27[2];
                  double var44 = var13 + var19;
                  double var46 = var15 + var21;
                  double var48 = var17 + var23;
                  Vec3 var50 = new Vec3(var13, var15, var17);
                  Vec3 var51 = new Vec3(var44, var46, var48);
                  Trajectories$1 var52 = this.rayTraceCollision(var50, var51, var6);
                  MovingObjectPosition var53 = var52.hitResult;
                  double var54 = var52.squaredHitDistance;
                  Vec3 var56 = var53 != null ? new Vec3(var53.hitVec.xCoord, var53.hitVec.yCoord, var53.hitVec.zCoord) : var51;
                  AxisAlignedBB var57 = new AxisAlignedBB(var13 - var40, var15 - var40, var17 - var40, var13 + var40, var15 + var40, var17 + var40)
                     .addCoord(var19, var21, var23)
                     .expand(1.0, 1.0, 1.0);
                  List var58 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), var57);
                  Entity var59 = null;
                  Vec3 var60 = null;
                  double var61 = var54;

                  for (Entity var64 : (java.lang.Iterable<Entity>) (java.lang.Iterable<?>) (var58)) {
                     if (var64 instanceof EntityLivingBase
                        && !(var64 instanceof EntityArmorStand)
                        && var64.canBeCollidedWith()
                        && ((EntityLivingBase)var64).deathTime == 0
                        && (!(var64 instanceof EntityPlayer) || !AntiBot.shouldHideEntity(var64))) {
                        AxisAlignedBB var65 = var64.getEntityBoundingBox();
                        AxisAlignedBB var66 = this.expandEntityBox(var65);
                        MovingObjectPosition var67 = var66.calculateIntercept(var50, var56);
                        if (var67 != null) {
                           double var68 = var50.squareDistanceTo(var67.hitVec);
                           if (var68 + 1.0E-7 < var61) {
                              var61 = var68;
                              var59 = var64;
                              var60 = var67.hitVec;
                           }
                        }
                     }
                  }

                  if (var59 != null) {
                     double var105 = Math.sqrt(var61) / Math.sqrt(var19 * var19 + var21 * var21 + var23 * var23);
                     var105 = Math.max(0.0, Math.min(1.0, var105));
                     int var110 = (int)Math.ceil(var105 * 4.0);

                     for (int var111 = 0; var111 < var110; var111++) {
                        double var113 = var111 / 4.0;
                        var96.add(
                           new double[]{
                              var13 + var19 * var113 - var35.viewerPosX, var15 + var21 * var113 - var35.viewerPosY, var17 + var23 * var113 - var35.viewerPosZ
                           }
                        );
                     }

                     var96.add(new double[]{var60.xCoord - var35.viewerPosX, var60.yCoord - var35.viewerPosY, var60.zCoord - var35.viewerPosZ});
                     var30 = var59;
                     var31 = this.getInterpolatedEntityBox(var59, var3);
                     var32 = 1;
                     var33 = var60;
                     break;
                  }

                  if (var53 != null) {
                     Vec3 var104 = var53.hitVec;
                     int var108 = var53.sideHit.getIndex();
                     var32 = var108 != 0 && var108 != 1 ? 2 : 3;
                     var33 = var104;
                     double var109 = var19 * var19 + var21 * var21 + var23 * var23;
                     double var112 = var104.xCoord - var13;
                     double var69 = var104.yCoord - var15;
                     double var71 = var104.zCoord - var17;
                     double var73 = var109 > 0.0 ? Math.sqrt((var112 * var112 + var69 * var69 + var71 * var71) / var109) : 0.0;
                     var73 = Math.max(0.0, Math.min(1.0, var73));
                     int var75 = (int)Math.ceil(var73 * 4.0);

                     for (int var76 = 0; var76 < var75; var76++) {
                        double var77 = var76 / 4.0;
                        var96.add(
                           new double[]{
                              var13 + var19 * var77 - var35.viewerPosX, var15 + var21 * var77 - var35.viewerPosY, var17 + var23 * var77 - var35.viewerPosZ
                           }
                        );
                     }

                     var96.add(new double[]{var104.xCoord - var35.viewerPosX, var104.yCoord - var35.viewerPosY, var104.zCoord - var35.viewerPosZ});
                     var29 = var53;
                     break;
                  }

                  for (int var103 = 0; var103 < 4; var103++) {
                     double var107 = var103 / 4.0;
                     var96.add(
                        new double[]{
                           var13 + var19 * var107 - var35.viewerPosX, var15 + var21 * var107 - var35.viewerPosY, var17 + var23 * var107 - var35.viewerPosZ
                        }
                     );
                  }

                  var13 = var44;
                  var15 = var46;
                  var17 = var48;
                  var34 = new Vec3(var44, var46, var48);
                  var27[0] = var19;
                  var27[1] = var21;
                  var27[2] = var23;
                  this.applyProjectileMotion(var44, var46, var48, var6, var43, var27);
                  var19 = var27[0];
                  var21 = var27[1];
                  var23 = var27[2];
                  if (var46 < -64.0) {
                     break;
                  }
               }

               if (var33 == null && var6.projectileType == ProjectileType.FISH_HOOK && var34 != null) {
                  var33 = var34;
               }

               int var97 = this.kcgag5(var32);
               float var98 = (var97 >> 16 & 0xFF) / 255.0F;
               float var99 = (var97 >> 8 & 0xFF) / 255.0F;
               float var45 = (var97 & 0xFF) / 255.0F;
               RenderUtils$1 var100 = this.VIuQ() ? null : this.beginProjectileGlState();

               try {
                  this.drawTrajectory(var96, var36, var97);
                  if (var30 != null && var31 != null) {
                     this.drawTargetHighlight(var31.expand(0.05, 0.05, 0.05), var35.viewerPosX, var35.viewerPosY, var35.viewerPosZ, this.entityLanding.getArgb());
                  } else if (var29 != null && !this.landingPoint.isToggled()) {
                     BlockPos var47 = var29.getBlockPos();
                     AxisAlignedBB var102 = BlockUtils.getSelectedBounds(var47);
                     if (var102 != null) {
                        if (this.VIuQ()) {
                           this.renderBoxOutline(var102, var35.viewerPosX, var35.viewerPosY, var35.viewerPosZ, var97, 1.0F);
                        } else {
                           GL11.glColor4f(var98, var99, var45, getColorAlpha(var97));
                           RenderUtils.drawOffsetBoxOutline(var102, var35.viewerPosX, var35.viewerPosY, var35.viewerPosZ);
                        }
                     }
                  }

                  if (var30 == null && this.landingPoint.isToggled() && var33 != null) {
                     Trajectories$6 var101 = this.Bps7(this.XJxtN, var33, var29);
                     this.drawLandingMarker(var101.vec3, var101.yzs, var35.viewerPosX, var35.viewerPosY, var35.viewerPosZ, var98, var99, var45, getColorAlpha(var97));
                  }

                  this.logGlErrors("after held projectile draw");
               } finally {
                  if (!this.VIuQ()) {
                     this.hjFy(var100);
                  }

                  this.logGlErrors("after held projectile restore");
               }
            }
         }
      }
   }

   private void logGlErrors(String var1) {
      if (GL_DEBUG_ENABLED) {
         int var2;
         while ((var2 = GL11.glGetError()) != 0) {
            ClientUtils.logger.warn("Trajectories GL error {} at {}", new Object[]{var2, var1});
         }
      }
   }

   private AxisAlignedBB getInterpolatedEntityBox(Entity var1, float var2) {
      AxisAlignedBB var3 = var1.getEntityBoundingBox();
      double var4 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2;
      double var6 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2;
      double var8 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2;
      return var3.offset(var4 - var1.posX, var6 - var1.posY, var8 - var1.posZ);
   }

   private boolean hasVisibleFireballs() {
      if (this.renderFireballs.isToggled() && mc.theWorld != null) {
         for (Entity var2 : mc.theWorld.loadedEntityList) {
            if (var2 instanceof EntityFireball && !var2.isDead) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean hasVisibleProjectiles() {
      return this.hasVisibleFireballs() || this.hasPearlTrajectories() || this.KQow();
   }

   private boolean hasPearlTrajectories() {
      if (this.renderEnderPearls.isToggled() && mc.theWorld != null) {
         for (Entity var2 : mc.theWorld.loadedEntityList) {
            if (var2 instanceof EntityEnderPearl && !var2.isDead && ((EntityEnderPearl)var2).getThrower() != mc.thePlayer) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean KQow() {
      if (this.projectedFireball.isToggled() && mc.theWorld != null) {
         for (EntityPlayer var2 : mc.theWorld.playerEntities) {
            ItemStack var3 = var2.getHeldItem();
            if (var2 != mc.thePlayer && !var2.isDead && !AntiBot.shouldHideEntity(var2) && var3 != null && var3.getItem() instanceof ItemFireball) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private Trajectories$3 ndbyL(EntityFireball var1) {
      Trajectories$3 var2 = this.Ms14.get(var1);
      if (var2 == null) {
         var2 = new Trajectories$3();
         this.Ms14.put(var1, var2);
      }

      return var2;
   }

   private Trajectories$7 getOrCreatePearlState(EntityEnderPearl var1) {
      Trajectories$7 var2 = this.SZxL.get(var1);
      if (var2 == null) {
         var2 = new Trajectories$7();
         this.SZxL.put(var1, var2);
      }

      return var2;
   }

   private void pruneStaleFireballs() {
      ArrayList var1 = null;

      for (EntityFireball var3 : this.Ms14.keySet()) {
         if (var3 == null || var3.isDead || mc.theWorld == null || !mc.theWorld.loadedEntityList.contains(var3)) {
            if (var1 == null) {
               var1 = new ArrayList();
            }

            var1.add(var3);
         }
      }

      if (var1 != null) {
         for (EntityFireball var5 : (java.lang.Iterable<EntityFireball>) (java.lang.Iterable<?>) (var1)) {
            this.Ms14.remove(var5);
         }
      }
   }

   private void pruneStalePearls() {
      ArrayList var1 = null;

      for (EntityEnderPearl var3 : this.SZxL.keySet()) {
         if (var3 == null || var3.isDead || mc.theWorld == null || !mc.theWorld.loadedEntityList.contains(var3)) {
            if (var1 == null) {
               var1 = new ArrayList();
            }

            var1.add(var3);
         }
      }

      if (var1 != null) {
         for (EntityEnderPearl var5 : (java.lang.Iterable<EntityEnderPearl>) (java.lang.Iterable<?>) (var1)) {
            this.SZxL.remove(var5);
         }
      }
   }

   private float normalizeAlpha(double var1) {
      return TrajectoryMath.normalizeRatio(var1, 4.0);
   }

   private Trajectories$6 Bps7(Trajectories$6 var1, Vec3 var2, MovingObjectPosition var3) {
      EnumFacing var4 = var3 != null && var3.sideHit != null ? var3.sideHit : EnumFacing.UP;
      if (var1.vec3 == null) {
         var1.vec3 = var2;
         var1.yzs = var4;
         return var1;
      } else {
         double var5 = var1.vec3.squareDistanceTo(var2);
         if (var5 <= 0.0144) {
            var1.vec3 = new Vec3(
               var1.vec3.xCoord + (var2.xCoord - var1.vec3.xCoord) * 0.35,
               var1.vec3.yCoord + (var2.yCoord - var1.vec3.yCoord) * 0.35,
               var1.vec3.zCoord + (var2.zCoord - var1.vec3.zCoord) * 0.35
            );
         } else {
            var1.vec3 = var2;
            var1.yzs = var4;
         }

         return var1;
      }
   }

   private void drawLandingMarker(Vec3 var1, EnumFacing var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12) {
      double var13 = var1.xCoord - var3;
      double var15 = var1.yCoord - var5;
      double var17 = var1.zCoord - var7;
      double var19 = 0.28;
      double var21 = 0.003;
      double var23 = var2.getFrontOffsetX();
      double var25 = var2.getFrontOffsetY();
      double var27 = var2.getFrontOffsetZ();
      double var29;
      double var31;
      double var33;
      double var35;
      double var37;
      double var39;
      if (var2.getAxis() == Axis.Y) {
         var29 = 1.0;
         var31 = 0.0;
         var33 = 0.0;
         var35 = 0.0;
         var37 = 0.0;
         var39 = 1.0;
      } else if (var2.getAxis() == Axis.X) {
         var29 = 0.0;
         var31 = 1.0;
         var33 = 0.0;
         var35 = 0.0;
         var37 = 0.0;
         var39 = 1.0;
      } else {
         var29 = 1.0;
         var31 = 0.0;
         var33 = 0.0;
         var35 = 0.0;
         var37 = 1.0;
         var39 = 0.0;
      }

      var13 += var23 * var21;
      var15 += var25 * var21;
      var17 += var27 * var21;
      if (this.VIuQ()) {
         ExternalRenderBuffer var63 = ExternalRenderer.getActiveRenderBuffer();
         ScreenProjector var64 = ExternalRenderer.getActiveScreenProjector();
         if (var63 != null && var64 != null) {
            int var43 = MathHelper.clamp_int(Math.round(var12 * 255.0F), 0, 255) << 24
               | MathHelper.clamp_int(Math.round(var9 * 255.0F), 0, 255) << 16
               | MathHelper.clamp_int(Math.round(var10 * 255.0F), 0, 255) << 8
               | MathHelper.clamp_int(Math.round(var11 * 255.0F), 0, 255);
            boolean var65 = false;
            double var45 = 0.0;
            double var47 = 0.0;
            double var49 = 0.0;
            double var51 = 0.0;

            for (int var53 = 0; var53 <= 48; var53++) {
               double var54 = (Math.PI * 2) * (var53 % 48) / 48.0;
               double var56 = Math.cos(var54) * var19;
               double var58 = Math.sin(var54) * var19;
               if (!var64.projectPoint(var13 + var29 * var56 + var35 * var58, var15 + var31 * var56 + var37 * var58, var17 + var33 * var56 + var39 * var58)) {
                  var65 = false;
               } else {
                  if (!var65) {
                     var45 = var64.projectedPoint[0];
                     var47 = var64.projectedPoint[1];
                  } else {
                     var63.drawLine(var49, var51, var64.projectedPoint[0], var64.projectedPoint[1], var43, 2.0F);
                  }

                  var49 = var64.projectedPoint[0];
                  var51 = var64.projectedPoint[1];
                  var65 = true;
               }
            }
         }
      } else {
         GL11.glLineWidth(2.0F);
         GL11.glColor4f(var9, var10, var11, var12);
         GL11.glBegin(2);

         for (int var41 = 0; var41 < 48; var41++) {
            double var42 = (Math.PI * 2) * var41 / 48.0;
            double var44 = Math.cos(var42) * var19;
            double var46 = Math.sin(var42) * var19;
            GL11.glVertex3d(var13 + var29 * var44 + var35 * var46, var15 + var31 * var44 + var37 * var46, var17 + var33 * var44 + var39 * var46);
         }

         GL11.glEnd();
      }
   }

   private static float getColorAlpha(int var0) {
      return TrajectoryMath.getAlphaFromColor(var0);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Items",
            "Thickness",
            new String[]{
               "render bows",
               "only charged",
               "render fireballs",
               "projected fireball",
               "render ender pearls",
               "render fishing rods",
               "render eggs",
               "render snowballs"
            },
            new String[]{"Bows", "Only Charged", "Fireballs", "Projected Fireball", "Ender Pearls", "Fishing Rods", "Eggs", "Snowballs"}
         ),
         buildSettingAlias(
            "Colors",
            "Items",
            new String[]{"trail", "fireball color", "projected fireball color", "entity landing", "wall landing", "ground landing"},
            new String[]{"Trail", "Fireballs", "Projected Fireball", "Entity", "Wall", "Ground"}
         ),
         buildSettingAlias(
            "Landing", "Colors", new String[]{"landing point", "collision timer", "highlight"}, new String[]{"Landing point", "Collision Timer", "Highlight"}
         )
      );
   }

   static {
      int[] var10000 = new int[4];
      var10000[0] = -1511950;
      var10000[1] = -11443;
      var10000[2] = -13307766;
      var10000[3] = -11147521;
      ITEM_COLORS = var10000;
   }
}
