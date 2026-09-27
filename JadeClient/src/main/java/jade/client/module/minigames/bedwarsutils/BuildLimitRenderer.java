// Jade recovery: original class: jade.deps.eLz.Ky4N7R9
package jade.client.module.minigames.bedwarsutils;

import jade.client.common.BlockUtils;
import jade.client.common.RenderUtils$1;
import jade.client.common.RenderUtils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public final class BuildLimitRenderer {
   private static final double MUq = 14.0;
   private static final double VERTICAL_FADE_DISTANCE = 12.0;
   private static final double vLnf = 8.0;
   private static final int vBm = 64;
   private static final int GLOW_RING_COUNT = 20;
   private static final float GLOW_HEIGHT_SCALE = 0.62F;
   private static final double Esu9 = 0.01;
   private static final float YYw = 0.32F;
   private static final float GLOW_ALPHA_MID = 0.42F;
   private static final float GLOW_ALPHA_NEAR = 0.52F;

   private BuildLimitRenderer() {
   }

   public static void QWUZbXw(Minecraft var0, float var1, int var2, int var3, boolean var4) {
      if (var0 != null && var0.thePlayer != null && var0.theWorld != null && var2 < var3) {
         double var5 = lerp(var0.thePlayer.lastTickPosX, var0.thePlayer.posX, var1);
         double var7 = lerp(var0.thePlayer.lastTickPosY, var0.thePlayer.posY, var1);
         double var9 = lerp(var0.thePlayer.lastTickPosZ, var0.thePlayer.posZ, var1);
         if (var4) {
            ZDlE(var0, var5, var7, var9, var2, false, 255, 92, 92);
            ZDlE(var0, var5, var7, var9, var3, true, 255, 74, 74);
         }

         float var11 = cybBx(var7, var2);
         float var12 = cybBx(var7, var3);
         if (var11 > 0.0F || var12 > 0.0F) {
            renderLimitBlockOutlines(var0, var5, var9, var2, var3, var11, var12);
         }
      }
   }

   private static void ZDlE(Minecraft var0, double var1, double var3, double var5, int var7, boolean var8, int var9, int var10, int var11) {
      double var12 = var8 ? var7 + 0.01 : var7 - 0.01;
      float var14 = cybBx(var3, var12);
      if (!(var14 <= 0.0F)) {
         RenderManager var15 = var0.getRenderManager();
         double var16 = var1 - var15.viewerPosX;
         double var18 = var12 - var15.viewerPosY;
         double var20 = var5 - var15.viewerPosZ;
         float var22 = 0.62F * var14;
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);

         try {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2912);
            GL11.glDisable(2884);
            GL11.glDisable(3008);
            GL11.glEnable(2929);
            GL11.glDepthFunc(515);
            GL11.glDepthMask(false);
            GL11.glShadeModel(7425);
            VSGb(var16, var18, var20, var9, var10, var11, var22);
         } finally {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            restoreRenderState();
         }
      }
   }

   private static void restoreRenderState() {
      RenderUtils.resetGlPipelineState();
   }

   private static void renderLimitBlockOutlines(Minecraft var0, double var1, double var3, int var5, int var6, float var7, float var8) {
      int var9 = MathHelper.floor_double(var1);
      int var10 = MathHelper.floor_double(var3);
      ArrayList var11 = new ArrayList();
      if (var7 > 0.0F) {
         ebE8(var0, var11, var9, var10, var5 + 2, Uzcpp(255, 221, 64, 0.32F * var7));
         ebE8(var0, var11, var9, var10, var5 + 1, Uzcpp(255, 143, 42, 0.42F * var7));
         ebE8(var0, var11, var9, var10, var5, Uzcpp(255, 58, 58, 0.52F * var7));
      }

      if (var8 > 0.0F) {
         ebE8(var0, var11, var9, var10, var6 - 3, Uzcpp(255, 221, 64, 0.32F * var8));
         ebE8(var0, var11, var9, var10, var6 - 2, Uzcpp(255, 143, 42, 0.42F * var8));
         ebE8(var0, var11, var9, var10, var6 - 1, Uzcpp(255, 58, 58, 0.52F * var8));
      }

      renderFaceOutlines(var11);
   }

   private static void ebE8(Minecraft var0, List<BuildLimitRenderer$1> var1, int var2, int var3, int var4, int var5) {
      double var6 = var0.getRenderManager().viewerPosX;
      double var8 = var0.getRenderManager().viewerPosY;
      double var10 = var0.getRenderManager().viewerPosZ;
      int var12 = (int)Math.ceil(8.0);

      for (int var13 = -var12; var13 <= var12; var13++) {
         for (int var14 = -var12; var14 <= var12; var14++) {
            if (!(var13 * var13 + var14 * var14 > 64.0)) {
               BlockPos var15 = new BlockPos(var2 + var13, var4, var3 + var14);
               if (var0.theWorld.isBlockLoaded(var15) && var0.theWorld.getBlockState(var15).getBlock() == Blocks.wool) {
                  AxisAlignedBB var16 = BlockUtils.getSelectedBounds(var15);
                  if (var16 == null) {
                     var16 = new AxisAlignedBB(var15.getX(), var15.getY(), var15.getZ(), var15.getX() + 1.0, var15.getY() + 1.0, var15.getZ() + 1.0);
                  }

                  var16 = var16.expand(0.002, 0.002, 0.002).offset(-var6, -var8, -var10);

                  for (EnumFacing var20 : EnumFacing.values()) {
                     if (BlockUtils.isReplaceableAt(var15.offset(var20))) {
                        var1.add(new BuildLimitRenderer$1(var16, var20, var5));
                     }
                  }
               }
            }
         }
      }
   }

   private static void renderFaceOutlines(List<BuildLimitRenderer$1> var0) {
      if (!var0.isEmpty()) {
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         RenderUtils$1 var1 = null;

         try {
            var1 = RenderUtils.uyB6();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glEnable(2929);
            GL11.glEnable(2884);
            GL11.glDepthMask(true);
            GL11.glShadeModel(7424);
            GL11.glDepthFunc(515);
            GL11.glColorMask(false, false, false, false);

            for (BuildLimitRenderer$1 var3 : var0) {
               wmojS(BuildLimitRenderer$1.yfx0(var3), BuildLimitRenderer$1.VEflEja(var3), -1);
            }

            GL11.glColorMask(true, true, true, true);
            GL11.glDepthMask(false);

            for (BuildLimitRenderer$1 var8 : var0) {
               wmojS(BuildLimitRenderer$1.yfx0(var8), BuildLimitRenderer$1.VEflEja(var8), BuildLimitRenderer$1.getArgbColor(var8));
            }
         } finally {
            RenderUtils.restoreLightmapState(var1);
            GL11.glColorMask(true, true, true, true);
            GL11.glShadeModel(7424);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
         }
      }
   }

   private static void wmojS(AxisAlignedBB var0, EnumFacing var1, int var2) {
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      var4.begin(7, DefaultVertexFormats.POSITION_COLOR);
      switch (var1) {
         case UP:
            Zntya(var4, var0.minX, var0.maxY, var0.maxZ, var2);
            Zntya(var4, var0.maxX, var0.maxY, var0.maxZ, var2);
            Zntya(var4, var0.maxX, var0.maxY, var0.minZ, var2);
            Zntya(var4, var0.minX, var0.maxY, var0.minZ, var2);
            break;
         case DOWN:
            Zntya(var4, var0.maxX, var0.minY, var0.maxZ, var2);
            Zntya(var4, var0.minX, var0.minY, var0.maxZ, var2);
            Zntya(var4, var0.minX, var0.minY, var0.minZ, var2);
            Zntya(var4, var0.maxX, var0.minY, var0.minZ, var2);
            break;
         case NORTH:
            Zntya(var4, var0.maxX, var0.maxY, var0.minZ, var2);
            Zntya(var4, var0.maxX, var0.minY, var0.minZ, var2);
            Zntya(var4, var0.minX, var0.minY, var0.minZ, var2);
            Zntya(var4, var0.minX, var0.maxY, var0.minZ, var2);
            break;
         case SOUTH:
            Zntya(var4, var0.minX, var0.maxY, var0.maxZ, var2);
            Zntya(var4, var0.minX, var0.minY, var0.maxZ, var2);
            Zntya(var4, var0.maxX, var0.minY, var0.maxZ, var2);
            Zntya(var4, var0.maxX, var0.maxY, var0.maxZ, var2);
            break;
         case EAST:
            Zntya(var4, var0.maxX, var0.maxY, var0.minZ, var2);
            Zntya(var4, var0.maxX, var0.maxY, var0.maxZ, var2);
            Zntya(var4, var0.maxX, var0.minY, var0.maxZ, var2);
            Zntya(var4, var0.maxX, var0.minY, var0.minZ, var2);
            break;
         case WEST:
            Zntya(var4, var0.minX, var0.maxY, var0.maxZ, var2);
            Zntya(var4, var0.minX, var0.maxY, var0.minZ, var2);
            Zntya(var4, var0.minX, var0.minY, var0.minZ, var2);
            Zntya(var4, var0.minX, var0.minY, var0.maxZ, var2);
      }

      var3.draw();
   }

   private static void Zntya(WorldRenderer var0, double var1, double var3, double var5, int var7) {
      var0.pos(var1, var3, var5)
         .color((var7 >> 16 & 0xFF) / 255.0F, (var7 >> 8 & 0xFF) / 255.0F, (var7 & 0xFF) / 255.0F, (var7 >> 24 & 0xFF) / 255.0F)
         .endVertex();
   }

   private static int Uzcpp(int var0, int var1, int var2, float var3) {
      int var4 = MathHelper.clamp_int(Math.round(var3 * 255.0F), 0, 255);
      return var4 << 24 | var0 << 16 | var1 << 8 | var2;
   }

   private static void VSGb(double var0, double var2, double var4, int var6, int var7, int var8, float var9) {
      for (int var10 = 0; var10 < 20; var10++) {
         double var11 = 14.0 * var10 / 20.0;
         double var13 = 14.0 * (var10 + 1) / 20.0;
         float var15 = var9 * Yn77(var11);
         float var16 = var9 * Yn77(var13);
         GL11.glBegin(8);

         for (int var17 = 0; var17 <= 64; var17++) {
            double var18 = (Math.PI * 2) * var17 / 64.0;
            double var20 = Math.cos(var18);
            double var22 = Math.sin(var18);
            GL11.glColor4f(var6 / 255.0F, var7 / 255.0F, var8 / 255.0F, var15);
            GL11.glVertex3d(var0 + var20 * var11, var2, var4 + var22 * var11);
            GL11.glColor4f(var6 / 255.0F, var7 / 255.0F, var8 / 255.0F, var16);
            GL11.glVertex3d(var0 + var20 * var13, var2, var4 + var22 * var13);
         }

         GL11.glEnd();
      }
   }

   private static float cybBx(double var0, double var2) {
      double var4 = Math.abs(var0 - var2);
      return (float)smoothStep(1.0 - var4 / 12.0);
   }

   private static float Yn77(double var0) {
      return (float)smoothStep(1.0 - var0 / 14.0);
   }

   private static double smoothStep(double var0) {
      double var2 = Math.max(0.0, Math.min(1.0, var0));
      return var2 * var2 * (3.0 - 2.0 * var2);
   }

   private static double lerp(double var0, double var2, float var4) {
      return var0 + (var2 - var0) * var4;
   }
}
