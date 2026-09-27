// Jade recovery: original class: jade.deps.eLz.N1vLKkf
package jade.client.common;

import jade.client.gui.ClickGui;
import jade.client.module.player.Freecam;
import jade.deps.loader107.CoreResourceIndex;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RenderUtils implements IMinecraft {
   private static Frustum frustum = new Frustum();
   private static final FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
   private static final FloatBuffer modelViewMatrixBuffer = BufferUtils.createFloatBuffer(16);
   private static final IntBuffer vE1 = BufferUtils.createIntBuffer(16);
   private static final FloatBuffer GDU = BufferUtils.createFloatBuffer(3);
   private static final FloatBuffer lht = BufferUtils.createFloatBuffer(16);
   private static final FloatBuffer jgivP = BufferUtils.createFloatBuffer(16);
   private static final IntBuffer EOtL6 = BufferUtils.createIntBuffer(16);
   private static final ScissorRectPool ai2 = new ScissorRectPool();
   private static final Map<String, ResourceLocation> iconTextureCache = new HashMap<>();

   public static RenderUtils$1 uyB6() {
      boolean var0 = GL11.glIsEnabled(2896);
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      boolean var1 = GL11.glIsEnabled(3553);
      ((Buffer)jgivP).clear();
      GL11.glGetFloat(2819, jgivP);
      RenderUtils$1 var2 = new RenderUtils$1(var0, var1, jgivP.get(0), jgivP.get(1));
      resetLightmapState();
      return var2;
   }

   public static void resetLightmapState() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.enableTexture2D();
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.disableLighting();
   }

   public static void restoreLightmapState(RenderUtils$1 var0) {
      if (var0 == null) {
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      } else {
         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, RenderUtils$1.getLightmapU(var0), RenderUtils$1.getLightmapV(var0));
         if (RenderUtils$1.bGlc(var0)) {
            GlStateManager.enableTexture2D();
         } else {
            GlStateManager.disableTexture2D();
         }

         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         if (RenderUtils$1.isLightingEnabled(var0)) {
            GlStateManager.enableLighting();
         } else {
            GlStateManager.disableLighting();
         }
      }
   }

   public static void drawBlockBoundsBox(BlockPos var0, int var1, boolean var2, boolean var3) {
      iPds(var0.getX(), var0.getY(), var0.getZ(), 1.0, 1.0, 1.0, var1, var2, var3);
   }

   public static void drawInsetBlockBounds(BlockPos var0, int var1, boolean var2, boolean var3) {
      iPds(var0.getX() + 0.0625F, var0.getY(), var0.getZ() + 0.0625F, 0.875, 0.875, 0.875, var1, var2, var3);
   }

   public static void drawBlockPosBoxesUniform(List<BlockPos> var0, int var1, boolean var2, boolean var3) {
      drawBlockPosBoxes(var0, var1, var1, var2, var3);
   }

   public static void drawBlockPosBoxes(List<BlockPos> var0, int var1, int var2, boolean var3, boolean var4) {
      if (var0 != null && !var0.isEmpty()) {
         double var5 = mc.getRenderManager().viewerPosX;
         double var7 = mc.getRenderManager().viewerPosY;
         double var9 = mc.getRenderManager().viewerPosZ;
         GL11.glPushMatrix();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         float var11 = (var1 >> 24 & 0xFF) / 255.0F;
         float var12 = (var1 >> 16 & 0xFF) / 255.0F;
         float var13 = (var1 >> 8 & 0xFF) / 255.0F;
         float var14 = (var1 & 0xFF) / 255.0F;
         float var15 = (var2 >> 24 & 0xFF) / 255.0F;
         float var16 = (var2 >> 16 & 0xFF) / 255.0F;
         float var17 = (var2 >> 8 & 0xFF) / 255.0F;
         float var18 = (var2 & 0xFF) / 255.0F;

         for (BlockPos var20 : var0) {
            double var21 = var20.getX() + 0.0625 - var5;
            double var23 = var20.getY() - var7;
            double var25 = var20.getZ() + 0.0625 - var9;
            AxisAlignedBB var27 = new AxisAlignedBB(var21, var23, var25, var21 + 0.875, var23 + 0.875, var25 + 0.875);
            if (var3) {
               GL11.glColor4f(var12, var13, var14, var11);
               drawBoxOutline(var27);
            }

            if (var4) {
               drawFilledAabb(var27, var16, var17, var18, var15);
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
      }
   }

   public static void drawBlockBoxWithHeight(BlockPos var0, int var1, double var2, boolean var4, boolean var5) {
      iPds(var0.getX(), var0.getY(), var0.getZ(), 1.0, var2, 1.0, var1, var4, var5);
   }

   public static void applyScissorRect(double var0, double var2, double var4, double var6) {
      double var8 = ClickGui.getActiveRenderScale();
      var0 *= var8;
      var2 *= var8;
      var4 *= var8;
      var6 *= var8;
      ScaledResolution var10 = new ScaledResolution(mc);
      int var11 = var10.getScaleFactor();
      double var12 = var10.getScaledHeight();
      int var14 = (int)Math.floor(var0 * var11);
      int var15 = (int)Math.ceil((var0 + var4) * var11);
      int var16 = Math.max(0, var15 - var14);
      double var17 = var2 + var6;
      int var19 = (int)Math.floor((var12 - var17) * var11);
      int var20 = (int)Math.ceil((var12 - var2) * var11);
      int var21 = Math.max(0, var20 - var19);
      if (var16 >= 0 && var21 >= 0) {
         GL11.glScissor(var14, var19, var16, var21);
      }
   }

   public static void pushScissorRect(double var0, double var2, double var4, double var6) {
      double var8 = ClickGui.getActiveRenderScale();
      var0 *= var8;
      var2 *= var8;
      var4 *= var8;
      var6 *= var8;
      ScaledResolution var10 = new ScaledResolution(mc);
      int var11 = var10.getScaleFactor();
      double var12 = var10.getScaledHeight();
      int var14 = (int)Math.floor(var0 * var11);
      int var15 = (int)Math.ceil((var0 + var4) * var11);
      int var16 = Math.max(0, var15 - var14);
      double var17 = var2 + var6;
      int var19 = (int)Math.floor((var12 - var17) * var11);
      int var20 = (int)Math.ceil((var12 - var2) * var11);
      int var21 = Math.max(0, var20 - var19);
      boolean var22 = GL11.glIsEnabled(3089);
      int[] var23 = ai2.NyGw();
      if (var22) {
         ((Buffer)EOtL6).clear();
         GL11.glGetInteger(3088, EOtL6);
         var23[0] = 1;
         var23[1] = EOtL6.get(0);
         var23[2] = EOtL6.get(1);
         var23[3] = EOtL6.get(2);
         var23[4] = EOtL6.get(3);
         int var24 = Math.max(var23[1], var14);
         int var25 = Math.max(var23[2], var19);
         int var26 = Math.max(0, Math.min(var23[1] + var23[3], var14 + var16) - var24);
         int var27 = Math.max(0, Math.min(var23[2] + var23[4], var19 + var21) - var25);
         GL11.glScissor(var24, var25, var26, var27);
      } else {
         var23[0] = 0;
         GL11.glEnable(3089);
         GL11.glScissor(var14, var19, var16, var21);
      }
   }

   public static void restoreScissorState() {
      int[] var0 = ai2.Fu52();
      if (var0[0] == 1) {
         GL11.glScissor(var0[1], var0[2], var0[3], var0[4]);
      } else {
         GL11.glDisable(3089);
      }
   }

   public static boolean isEntityInView(Entity var0) {
      return var0 == null ? false : XRxsYw(var0.getEntityBoundingBox()) || var0.ignoreFrustumCheck;
   }

   public static boolean XRxsYw(AxisAlignedBB var0) {
      if (var0 == null) {
         return false;
      } else {
         Entity var1 = mc.getRenderViewEntity();
         if (var1 == null) {
            return true;
         } else {
            frustum.setPosition(var1.posX, var1.posY, var1.posZ);
            return frustum.isBoundingBoxInFrustum(var0);
         }
      }
   }

   public static boolean HVp0(Entity var0, double var1) {
      if (var0 == null) {
         return false;
      } else {
         Entity var3 = mc.getRenderViewEntity();
         return var3 == null ? false : var0.getDistanceSqToEntity(var3) <= var1;
      }
   }

   public static boolean isBlockWithinDistanceSq(BlockPos var0, double var1) {
      if (var0 == null) {
         return false;
      } else {
         Entity var3 = mc.getRenderViewEntity();
         if (var3 == null) {
            return false;
         } else {
            double var4 = var0.getX() + 0.5 - var3.posX;
            double var6 = var0.getY() + 0.5 - var3.posY;
            double var8 = var0.getZ() + 0.5 - var3.posZ;
            return var4 * var4 + var6 * var6 + var8 * var8 <= var1;
         }
      }
   }

   public static void XNRNki(double var0, double var2, double var4, double var6, int var8) {
      float var9 = (var8 >> 24 & 0xFF) / 255.0F;
      float var10 = (var8 >> 16 & 0xFF) / 255.0F;
      float var11 = (var8 >> 8 & 0xFF) / 255.0F;
      float var12 = (var8 & 0xFF) / 255.0F;
      GlStateManager.pushMatrix();
      Tessellator var13 = Tessellator.getInstance();
      WorldRenderer var14 = var13.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var10, var11, var12, var9);
      var14.begin(7, DefaultVertexFormats.POSITION);
      var14.pos(var0, var6, 0.0).endVertex();
      var14.pos(var4, var6, 0.0).endVertex();
      var14.pos(var4, var2, 0.0).endVertex();
      var14.pos(var0, var2, 0.0).endVertex();
      var13.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public static void drawPlayerBoxAt(Vec3 var0, int var1) {
      GlStateManager.pushMatrix();
      double var2 = var0.xCoord - mc.getRenderManager().viewerPosX;
      double var4 = var0.yCoord - mc.getRenderManager().viewerPosY;
      double var6 = var0.zCoord - mc.getRenderManager().viewerPosZ;
      AxisAlignedBB var8 = mc.thePlayer.getEntityBoundingBox().expand(0.1, 0.1, 0.1);
      AxisAlignedBB var9 = new AxisAlignedBB(
         var8.minX - mc.thePlayer.posX + var2,
         var8.minY - mc.thePlayer.posY + var4,
         var8.minZ - mc.thePlayer.posZ + var6,
         var8.maxX - mc.thePlayer.posX + var2,
         var8.maxY - mc.thePlayer.posY + var4,
         var8.maxZ - mc.thePlayer.posZ + var6
      );
      float var10 = (var1 >> 24 & 0xFF) / 255.0F;
      float var11 = (var1 >> 16 & 0xFF) / 255.0F;
      float var12 = (var1 >> 8 & 0xFF) / 255.0F;
      float var13 = (var1 & 0xFF) / 255.0F;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(2.0F);
      GL11.glColor4f(var11, var12, var13, var10);
      drawFilledAabb(var9, var11, var12, var13, var10);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GlStateManager.popMatrix();
   }

   public static void drawRectOutline(float var0, float var1, float var2, float var3, float var4, int var5) {
      float var6 = (var5 >> 24 & 0xFF) / 255.0F;
      float var7 = (var5 >> 16 & 0xFF) / 255.0F;
      float var8 = (var5 >> 8 & 0xFF) / 255.0F;
      float var9 = (var5 & 0xFF) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var7, var8, var9, var6);
      GL11.glLineWidth(var4);
      GL11.glBegin(1);
      GL11.glVertex2d(var0, var1);
      GL11.glVertex2d(var0, var3);
      GL11.glVertex2d(var2, var3);
      GL11.glVertex2d(var2, var1);
      GL11.glVertex2d(var0, var1);
      GL11.glVertex2d(var2, var1);
      GL11.glVertex2d(var0, var3);
      GL11.glVertex2d(var2, var3);
      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void iPds(double var0, double var2, double var4, double var6, double var8, double var10, int var12, boolean var13, boolean var14) {
      double var15 = var0 - mc.getRenderManager().viewerPosX;
      double var17 = var2 - mc.getRenderManager().viewerPosY;
      double var19 = var4 - mc.getRenderManager().viewerPosZ;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);

      try {
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         float var21 = (var12 >> 24 & 0xFF) / 255.0F;
         float var22 = (var12 >> 16 & 0xFF) / 255.0F;
         float var23 = (var12 >> 8 & 0xFF) / 255.0F;
         float var24 = (var12 & 0xFF) / 255.0F;
         GL11.glColor4f(var22, var23, var24, var21);
         AxisAlignedBB var25 = new AxisAlignedBB(var15, var17, var19, var15 + var6, var17 + var8, var19 + var10);
         if (var13) {
            drawBoxOutline(var25);
         }

         if (var14) {
            drawFilledBox(var25, var22, var23, var24);
         }
      } finally {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
         resetGlPipelineState();
      }
   }

   public static void drawBlockFaceQuads(BlockPos var0, int var1, boolean var2, boolean var3, Set<EnumFacing> var4) {
      if (var4 != null && !var4.isEmpty()) {
         double var5 = var0.getX() - mc.getRenderManager().viewerPosX;
         double var7 = var0.getY() - mc.getRenderManager().viewerPosY;
         double var9 = var0.getZ() - mc.getRenderManager().viewerPosZ;
         double var11 = var5 + 1.0;
         double var13 = var7 + 1.0;
         double var15 = var9 + 1.0;
         float var17 = (var1 >> 16 & 0xFF) / 255.0F;
         float var18 = (var1 >> 8 & 0xFF) / 255.0F;
         float var19 = (var1 & 0xFF) / 255.0F;
         float var20 = (var1 >> 24 & 0xFF) / 255.0F;
         float var21 = 0.25F;
         GL11.glPushMatrix();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         Tessellator var22 = Tessellator.getInstance();
         WorldRenderer var23 = var22.getWorldRenderer();
         if (var3) {
            var23.begin(7, DefaultVertexFormats.POSITION_COLOR);
            if (var4.contains(EnumFacing.DOWN)) {
               var23.pos(var5, var7, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var7, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var7, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var7, var15).color(var17, var18, var19, var21).endVertex();
            }

            if (var4.contains(EnumFacing.UP)) {
               var23.pos(var5, var13, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var13, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var13, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var13, var9).color(var17, var18, var19, var21).endVertex();
            }

            if (var4.contains(EnumFacing.NORTH)) {
               var23.pos(var5, var7, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var13, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var13, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var7, var9).color(var17, var18, var19, var21).endVertex();
            }

            if (var4.contains(EnumFacing.SOUTH)) {
               var23.pos(var11, var7, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var13, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var13, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var7, var15).color(var17, var18, var19, var21).endVertex();
            }

            if (var4.contains(EnumFacing.WEST)) {
               var23.pos(var5, var7, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var13, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var13, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var5, var7, var15).color(var17, var18, var19, var21).endVertex();
            }

            if (var4.contains(EnumFacing.EAST)) {
               var23.pos(var11, var7, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var13, var15).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var13, var9).color(var17, var18, var19, var21).endVertex();
               var23.pos(var11, var7, var9).color(var17, var18, var19, var21).endVertex();
            }

            var22.draw();
         }

         if (var2) {
            GL11.glColor4f(var17, var18, var19, var20);
            var23.begin(1, DefaultVertexFormats.POSITION);
            if (var4.contains(EnumFacing.DOWN)) {
               var23.pos(var5, var7, var9).endVertex();
               var23.pos(var11, var7, var9).endVertex();
               var23.pos(var11, var7, var9).endVertex();
               var23.pos(var11, var7, var15).endVertex();
               var23.pos(var11, var7, var15).endVertex();
               var23.pos(var5, var7, var15).endVertex();
               var23.pos(var5, var7, var15).endVertex();
               var23.pos(var5, var7, var9).endVertex();
            }

            if (var4.contains(EnumFacing.UP)) {
               var23.pos(var5, var13, var9).endVertex();
               var23.pos(var11, var13, var9).endVertex();
               var23.pos(var11, var13, var9).endVertex();
               var23.pos(var11, var13, var15).endVertex();
               var23.pos(var11, var13, var15).endVertex();
               var23.pos(var5, var13, var15).endVertex();
               var23.pos(var5, var13, var15).endVertex();
               var23.pos(var5, var13, var9).endVertex();
            }

            if (var4.contains(EnumFacing.NORTH)) {
               var23.pos(var5, var7, var9).endVertex();
               var23.pos(var5, var13, var9).endVertex();
               var23.pos(var5, var13, var9).endVertex();
               var23.pos(var11, var13, var9).endVertex();
               var23.pos(var11, var13, var9).endVertex();
               var23.pos(var11, var7, var9).endVertex();
               var23.pos(var11, var7, var9).endVertex();
               var23.pos(var5, var7, var9).endVertex();
            }

            if (var4.contains(EnumFacing.SOUTH)) {
               var23.pos(var5, var7, var15).endVertex();
               var23.pos(var5, var13, var15).endVertex();
               var23.pos(var5, var13, var15).endVertex();
               var23.pos(var11, var13, var15).endVertex();
               var23.pos(var11, var13, var15).endVertex();
               var23.pos(var11, var7, var15).endVertex();
               var23.pos(var11, var7, var15).endVertex();
               var23.pos(var5, var7, var15).endVertex();
            }

            if (var4.contains(EnumFacing.WEST)) {
               var23.pos(var5, var7, var9).endVertex();
               var23.pos(var5, var13, var9).endVertex();
               var23.pos(var5, var13, var9).endVertex();
               var23.pos(var5, var13, var15).endVertex();
               var23.pos(var5, var13, var15).endVertex();
               var23.pos(var5, var7, var15).endVertex();
               var23.pos(var5, var7, var15).endVertex();
               var23.pos(var5, var7, var9).endVertex();
            }

            if (var4.contains(EnumFacing.EAST)) {
               var23.pos(var11, var7, var9).endVertex();
               var23.pos(var11, var13, var9).endVertex();
               var23.pos(var11, var13, var9).endVertex();
               var23.pos(var11, var13, var15).endVertex();
               var23.pos(var11, var13, var15).endVertex();
               var23.pos(var11, var7, var15).endVertex();
               var23.pos(var11, var7, var15).endVertex();
               var23.pos(var11, var7, var9).endVertex();
            }

            var22.draw();
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
      }
   }

   private static void addColoredVertex(WorldRenderer var0, double var1, double var3, double var5, int var7) {
      var0.pos(var1, var3, var5).color(var7 >> 16 & 0xFF, var7 >> 8 & 0xFF, var7 & 0xFF, var7 >> 24 & 0xFF).endVertex();
   }

   private static void addFaceVertices(WorldRenderer var0, EnumFacing var1, AxisAlignedBB var2, int var3, int var4) {
      switch (var1) {
         case UP:
            addColoredVertex(var0, var2.minX, var2.maxY, var2.maxZ, var3);
            addColoredVertex(var0, var2.maxX, var2.maxY, var2.maxZ, var4);
            addColoredVertex(var0, var2.maxX, var2.maxY, var2.minZ, var3);
            addColoredVertex(var0, var2.minX, var2.maxY, var2.minZ, var4);
            break;
         case DOWN:
            addColoredVertex(var0, var2.maxX, var2.minY, var2.maxZ, var3);
            addColoredVertex(var0, var2.minX, var2.minY, var2.maxZ, var4);
            addColoredVertex(var0, var2.minX, var2.minY, var2.minZ, var3);
            addColoredVertex(var0, var2.maxX, var2.minY, var2.minZ, var4);
            break;
         case NORTH:
            addColoredVertex(var0, var2.maxX, var2.maxY, var2.minZ, var3);
            addColoredVertex(var0, var2.maxX, var2.minY, var2.minZ, var4);
            addColoredVertex(var0, var2.minX, var2.minY, var2.minZ, var3);
            addColoredVertex(var0, var2.minX, var2.maxY, var2.minZ, var4);
            break;
         case SOUTH:
            addColoredVertex(var0, var2.minX, var2.maxY, var2.maxZ, var3);
            addColoredVertex(var0, var2.minX, var2.minY, var2.maxZ, var4);
            addColoredVertex(var0, var2.maxX, var2.minY, var2.maxZ, var3);
            addColoredVertex(var0, var2.maxX, var2.maxY, var2.maxZ, var4);
            break;
         case EAST:
            addColoredVertex(var0, var2.maxX, var2.maxY, var2.minZ, var3);
            addColoredVertex(var0, var2.maxX, var2.maxY, var2.maxZ, var4);
            addColoredVertex(var0, var2.maxX, var2.minY, var2.maxZ, var3);
            addColoredVertex(var0, var2.maxX, var2.minY, var2.minZ, var4);
            break;
         case WEST:
            addColoredVertex(var0, var2.minX, var2.maxY, var2.maxZ, var3);
            addColoredVertex(var0, var2.minX, var2.maxY, var2.minZ, var4);
            addColoredVertex(var0, var2.minX, var2.minY, var2.minZ, var3);
            addColoredVertex(var0, var2.minX, var2.minY, var2.maxZ, var4);
      }
   }

   public static void drawBlockFace(AxisAlignedBB var0, EnumFacing var1, int var2, int var3, boolean var4, boolean var5) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      if (var4) {
         var7.begin(7, DefaultVertexFormats.POSITION_COLOR);
         addFaceVertices(var7, var1, var0, var2, var2);
         var6.draw();
      }

      if (var5) {
         var7.begin(2, DefaultVertexFormats.POSITION_COLOR);
         addFaceVertices(var7, var1, var0, var3, var3);
         var6.draw();
      }
   }

   public static void IfIae(BlockPos var0, IBlockState var1, int var2, boolean var3, boolean var4, Set<EnumFacing> var5) {
      AxisAlignedBB var6 = BlockUtils.getSelectedBounds(var0);
      if (var6 != null) {
         double var7 = mc.getRenderManager().viewerPosX;
         double var9 = mc.getRenderManager().viewerPosY;
         double var11 = mc.getRenderManager().viewerPosZ;
         int var13 = var2 & 16777215 | 1056964608;
         int var14 = var2 | 0xFF000000;
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         RenderUtils$1 var15 = null;

         try {
            var15 = uyB6();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            if (var1.getBlock() instanceof BlockStairs) {
               StairOutlineRenderer.renderStairOutline(var0, var1, var6, null, var7, var9, var11, var13, var14, var14, var14, var4, var3, (recoveredArg0, recoveredArg1, recoveredArg2, recoveredArg3, recoveredArg4, recoveredArg5, recoveredArg6, recoveredArg7) -> RenderUtils.renderBlockFaceQuad(var13, var14, (net.minecraft.util.AxisAlignedBB) recoveredArg0, (net.minecraft.util.EnumFacing) recoveredArg1, recoveredArg2, recoveredArg3, recoveredArg4, recoveredArg5, recoveredArg6, recoveredArg7));
            } else {
               AxisAlignedBB var16 = var6.offset(-var7, -var9, -var11);

               for (EnumFacing var18 : var5) {
                  drawBlockFace(var16, var18, var13, var14, var4, var3);
               }
            }
         } finally {
            restoreLightmapState(var15);
            GL11.glPopAttrib();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnable(3553);
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }
   }

   public static void drawDebugStatsOverlay(boolean var0, boolean var1) {
      ScaledResolution var2 = new ScaledResolution(mc);
      String var3 = "";
      int var4 = -1;
      if (var0) {
         double var5 = ClientUtils.getSpeedBlocksPerSecond((Entity)(Freecam.cameraEntity == null ? mc.thePlayer : Freecam.cameraEntity), 2);
         if (var5 < 10.0) {
            var4 = Color.green.getRGB();
         } else if (var5 < 30.0) {
            var4 = Color.yellow.getRGB();
         } else if (var5 < 60.0) {
            var4 = Color.orange.getRGB();
         } else if (var5 < 160.0) {
            var4 = Color.red.getRGB();
         } else {
            var4 = Color.black.getRGB();
         }

         var3 = var3 + var5 + "bps";
      }

      if (var1) {
         double var7 = ClientUtils.getHorizontalSpeed();
         if (!var3.isEmpty()) {
            var3 = var3 + " ";
         }

         var3 = var3 + ClientUtils.WXYd(var7, 3);
      }

      mc.fontRendererObj
         .drawString(var3, var2.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(var3) / 2, var2.getScaledHeight() / 2 + 15, var4, false);
   }

   public static void NqwY(Entity var0, int var1, double var2, double var4, int var6, boolean var7) {
      if (var0 instanceof EntityLivingBase) {
         float var8 = ((IAccessorMinecraft)mc).getTimer().renderPartialTicks;
         double var9 = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * var8 - mc.getRenderManager().viewerPosX;
         double var11 = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * var8 - mc.getRenderManager().viewerPosY;
         double var13 = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * var8 - mc.getRenderManager().viewerPosZ;
         float var15 = (float)var2 / 40.0F;
         if (var0 instanceof EntityPlayer && var7 && ((EntityPlayer)var0).hurtTime != 0) {
            var6 = Color.RED.getRGB();
         }

         GlStateManager.pushMatrix();
         if (var1 == 3) {
            GL11.glTranslated(var9, var11 - 0.2, var13);
            GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0, 1.0, 0.0);
            GlStateManager.disableDepth();
            GL11.glScalef(0.03F + var15, 0.03F + var15, 0.03F + var15);
            int var16 = Color.black.getRGB();
            Gui.drawRect(-20, -1, -26, 75, var16);
            Gui.drawRect(20, -1, 26, 75, var16);
            Gui.drawRect(-20, -1, 21, 5, var16);
            Gui.drawRect(-20, 70, 21, 75, var16);
            if (var6 != 0) {
               Gui.drawRect(-21, 0, -25, 74, var6);
               Gui.drawRect(21, 0, 25, 74, var6);
               Gui.drawRect(-21, 0, 24, 4, var6);
               Gui.drawRect(-21, 71, 25, 74, var6);
            } else {
               int var17 = ClientUtils.AIowEv(2L, 0L);
               int var18 = ClientUtils.AIowEv(2L, 1000L);
               drawVerticalGradientRect(-21, 0, -25, 74, var17, var18);
               drawVerticalGradientRect(21, 0, 25, 74, var17, var18);
               Gui.drawRect(-21, 0, 21, 4, var18);
               Gui.drawRect(-21, 71, 21, 74, var17);
            }

            GlStateManager.enableDepth();
         } else if (var1 == 4) {
            EntityLivingBase var28 = (EntityLivingBase)var0;
            double var30 = var28.getHealth() / var28.getMaxHealth();
            int var20 = (int)(74.0 * var30);
            int var21 = var30 < 0.3 ? Color.red.getRGB() : (var30 < 0.5 ? Color.orange.getRGB() : (var30 < 0.7 ? Color.yellow.getRGB() : Color.green.getRGB()));
            GL11.glTranslated(var9, var11 - 0.2, var13);
            GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0, 1.0, 0.0);
            GlStateManager.disableDepth();
            GL11.glScalef(0.03F + var15, 0.03F + var15, 0.03F + var15);
            int var27 = (int)(21.0 + var4 * 2.0);
            Gui.drawRect(var27, -1, var27 + 4, 75, Color.black.getRGB());
            Gui.drawRect(var27 + 1, var20, var27 + 3, 74, Color.darkGray.getRGB());
            Gui.drawRect(var27 + 1, 0, var27 + 3, var20, var21);
            GlStateManager.enableDepth();
         } else if (var1 == 6) {
            gfGg(var9, var11, var13, 0.7F, 45, 1.5F, var6, var6 == 0);
         } else {
            if (var6 == 0) {
               var6 = ClientUtils.AIowEv(2L, 0L);
            }

            float var29 = (var6 >> 24 & 0xFF) / 255.0F;
            float var31 = (var6 >> 16 & 0xFF) / 255.0F;
            float var19 = (var6 >> 8 & 0xFF) / 255.0F;
            float var32 = (var6 & 0xFF) / 255.0F;
            AxisAlignedBB var33 = var0.getEntityBoundingBox().expand(0.1 + var2, 0.1 + var2, 0.1 + var2);
            AxisAlignedBB var22 = new AxisAlignedBB(
               var33.minX - var0.posX + var9,
               var33.minY - var0.posY + var11,
               var33.minZ - var0.posZ + var13,
               var33.maxX - var0.posX + var9,
               var33.maxY - var0.posY + var11,
               var33.maxZ - var0.posZ + var13
            );
            RenderUtils$1 var23 = uyB6();

            try {
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(3042);
               GL11.glDisable(3553);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               GL11.glLineWidth(2.0F);
               GL11.glColor4f(var31, var19, var32, var29);
               if (var1 == 1) {
                  drawBoxOutline(var22);
               } else if (var1 == 2) {
                  drawFilledBox(var22, var31, var19, var32);
               }
            } finally {
               restoreLightmapState(var23);
               GL11.glEnable(3553);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glDisable(3042);
            }
         }

         GlStateManager.popMatrix();
      }
   }

   public static void drawFilledCircle(double var0, double var2, double var4, int var6, int var7) {
      if (var6 >= 3) {
         float var8 = (var7 >> 24 & 0xFF) / 255.0F;
         float var9 = (var7 >> 16 & 0xFF) / 255.0F;
         float var10 = (var7 >> 8 & 0xFF) / 255.0F;
         float var11 = (var7 & 0xFF) / 255.0F;
         Tessellator var12 = Tessellator.getInstance();
         WorldRenderer var13 = var12.getWorldRenderer();
         GlStateManager.enableBlend();
         GlStateManager.disableTexture2D();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glColor4f(var9, var10, var11, var8);
         var13.begin(6, DefaultVertexFormats.POSITION);

         for (int var14 = 0; var14 < var6; var14++) {
            double var15 = (Math.PI * 2) * var14 / var6 + Math.toRadians(180.0);
            var13.pos(var0 + Math.sin(var15) * var4, var2 + Math.cos(var15) * var4, 0.0).endVertex();
         }

         var12.draw();
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
      }
   }

   public static void drawBoxOutline(AxisAlignedBB var0) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      RenderUtils$1 var3 = uyB6();

      try {
         var2.begin(1, DefaultVertexFormats.POSITION);
         addLineSegment(var2, var0.minX, var0.minY, var0.minZ, var0.maxX, var0.minY, var0.minZ);
         addLineSegment(var2, var0.maxX, var0.minY, var0.minZ, var0.maxX, var0.minY, var0.maxZ);
         addLineSegment(var2, var0.maxX, var0.minY, var0.maxZ, var0.minX, var0.minY, var0.maxZ);
         addLineSegment(var2, var0.minX, var0.minY, var0.maxZ, var0.minX, var0.minY, var0.minZ);
         addLineSegment(var2, var0.minX, var0.maxY, var0.minZ, var0.maxX, var0.maxY, var0.minZ);
         addLineSegment(var2, var0.maxX, var0.maxY, var0.minZ, var0.maxX, var0.maxY, var0.maxZ);
         addLineSegment(var2, var0.maxX, var0.maxY, var0.maxZ, var0.minX, var0.maxY, var0.maxZ);
         addLineSegment(var2, var0.minX, var0.maxY, var0.maxZ, var0.minX, var0.maxY, var0.minZ);
         addLineSegment(var2, var0.minX, var0.minY, var0.minZ, var0.minX, var0.maxY, var0.minZ);
         addLineSegment(var2, var0.maxX, var0.minY, var0.minZ, var0.maxX, var0.maxY, var0.minZ);
         addLineSegment(var2, var0.maxX, var0.minY, var0.maxZ, var0.maxX, var0.maxY, var0.maxZ);
         addLineSegment(var2, var0.minX, var0.minY, var0.maxZ, var0.minX, var0.maxY, var0.maxZ);
         var1.draw();
      } finally {
         restoreLightmapState(var3);
      }
   }

   public static void drawOffsetBoxOutline(AxisAlignedBB var0, double var1, double var3, double var5) {
      drawBoxOutline(var0.offset(-var1, -var3, -var5));
   }

   private static void addLineSegment(WorldRenderer var0, double var1, double var3, double var5, double var7, double var9, double var11) {
      var0.pos(var1, var3, var5).endVertex();
      var0.pos(var7, var9, var11).endVertex();
   }

   public static void drawFilledBox(AxisAlignedBB var0, float var1, float var2, float var3) {
      drawFilledAabb(var0, var1, var2, var3, 0.25F);
   }

   public static void drawFilledAabb(AxisAlignedBB var0, float var1, float var2, float var3, float var4) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      RenderUtils$1 var7 = uyB6();

      try {
         var6.begin(7, DefaultVertexFormats.POSITION_COLOR);
         addQuadWithColor(
            var6,
            var0.minX,
            var0.minY,
            var0.minZ,
            var0.maxX,
            var0.minY,
            var0.minZ,
            var0.maxX,
            var0.maxY,
            var0.minZ,
            var0.minX,
            var0.maxY,
            var0.minZ,
            var1,
            var2,
            var3,
            var4
         );
         addQuadWithColor(
            var6,
            var0.maxX,
            var0.minY,
            var0.maxZ,
            var0.minX,
            var0.minY,
            var0.maxZ,
            var0.minX,
            var0.maxY,
            var0.maxZ,
            var0.maxX,
            var0.maxY,
            var0.maxZ,
            var1,
            var2,
            var3,
            var4
         );
         addQuadWithColor(
            var6,
            var0.minX,
            var0.minY,
            var0.maxZ,
            var0.minX,
            var0.minY,
            var0.minZ,
            var0.minX,
            var0.maxY,
            var0.minZ,
            var0.minX,
            var0.maxY,
            var0.maxZ,
            var1,
            var2,
            var3,
            var4
         );
         addQuadWithColor(
            var6,
            var0.maxX,
            var0.minY,
            var0.minZ,
            var0.maxX,
            var0.minY,
            var0.maxZ,
            var0.maxX,
            var0.maxY,
            var0.maxZ,
            var0.maxX,
            var0.maxY,
            var0.minZ,
            var1,
            var2,
            var3,
            var4
         );
         addQuadWithColor(
            var6,
            var0.minX,
            var0.maxY,
            var0.minZ,
            var0.maxX,
            var0.maxY,
            var0.minZ,
            var0.maxX,
            var0.maxY,
            var0.maxZ,
            var0.minX,
            var0.maxY,
            var0.maxZ,
            var1,
            var2,
            var3,
            var4
         );
         addQuadWithColor(
            var6,
            var0.minX,
            var0.minY,
            var0.maxZ,
            var0.maxX,
            var0.minY,
            var0.maxZ,
            var0.maxX,
            var0.minY,
            var0.minZ,
            var0.minX,
            var0.minY,
            var0.minZ,
            var1,
            var2,
            var3,
            var4
         );
         var5.draw();
      } finally {
         restoreLightmapState(var7);
      }
   }

   private static void addQuadWithColor(
      WorldRenderer var0,
      double var1,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      double var19,
      double var21,
      double var23,
      float var25,
      float var26,
      float var27,
      float var28
   ) {
      var0.pos(var1, var3, var5).color(var25, var26, var27, var28).endVertex();
      var0.pos(var7, var9, var11).color(var25, var26, var27, var28).endVertex();
      var0.pos(var13, var15, var17).color(var25, var26, var27, var28).endVertex();
      var0.pos(var19, var21, var23).color(var25, var26, var27, var28).endVertex();
   }

   public static void Tm32(IBlockState var0, BlockPos var1, int var2) {
      drawBlockStateModel(var0, var1.getX(), var1.getY(), var1.getZ(), var2);
   }

   public static void drawBlockStateModel(IBlockState var0, double var1, double var3, double var5, int var7) {
      Minecraft var8 = Minecraft.getMinecraft();
      BlockRendererDispatcher var9 = var8.getBlockRendererDispatcher();
      IBakedModel var10 = var9.getModelFromBlockState(var0, var8.theWorld, new BlockPos(var1, var3, var5));
      double var11 = var1 - var8.getRenderManager().viewerPosX;
      double var13 = var3 - var8.getRenderManager().viewerPosY;
      double var15 = var5 - var8.getRenderManager().viewerPosZ;
      float var17 = (var7 >> 24 & 0xFF) / 255.0F;
      float var18 = (var7 >> 16 & 0xFF) / 255.0F;
      float var19 = (var7 >> 8 & 0xFF) / 255.0F;
      float var20 = (var7 & 0xFF) / 255.0F;
      GlStateManager.pushMatrix();
      RenderUtils$1 var21 = null;

      try {
         var21 = uyB6();
         GlStateManager.translate(var11, var13, var15);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.disableTexture2D();
         GlStateManager.disableCull();
         GlStateManager.disableDepth();
         GlStateManager.depthMask(false);
         GlStateManager.color(var18, var19, var20, var17);
         cVgnK(var10, var18, var19, var20, var17);
         GlStateManager.depthMask(true);
         GlStateManager.enableDepth();
         GlStateManager.enableTexture2D();
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
      } finally {
         restoreLightmapState(var21);
         GlStateManager.popMatrix();
      }
   }

   private static void cVgnK(IBakedModel var0, float var1, float var2, float var3, float var4) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();

      for (EnumFacing var10 : EnumFacing.values()) {
         for (BakedQuad var12 : var0.getFaceQuads(var10)) {
            FCpk(var6, var12, var1, var2, var3, var4, var5);
         }
      }

      for (BakedQuad var14 : var0.getGeneralQuads()) {
         FCpk(var6, var14, var1, var2, var3, var4, var5);
      }
   }

   private static void FCpk(WorldRenderer var0, BakedQuad var1, float var2, float var3, float var4, float var5, Tessellator var6) {
      int[] var7 = var1.getVertexData();
      byte var8 = 4;
      int var9 = var7.length / 4;
      var0.begin(7, DefaultVertexFormats.POSITION_COLOR);

      for (int var10 = 0; var10 < 4; var10++) {
         int var11 = var10 * var9;
         float var12 = Float.intBitsToFloat(var7[var11]);
         float var13 = Float.intBitsToFloat(var7[var11 + 1]);
         float var14 = Float.intBitsToFloat(var7[var11 + 2]);
         var0.pos(var12, var13, var14).color(var2, var3, var4, var5).endVertex();
      }

      var6.draw();
   }

   public static void drawTracerToEntity(Entity var0, int var1, float var2, float var3) {
      if (var0 != null && mc.getRenderManager() != null) {
         Entity var4 = mc.getRenderViewEntity();
         if (var4 == null) {
            var4 = mc.thePlayer;
         }

         if (var4 != null) {
            double var5 = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * var3 - mc.getRenderManager().viewerPosX;
            double var7 = var0.lastTickPosY
               + (var0.posY - var0.lastTickPosY) * var3
               - mc.getRenderManager().viewerPosY
               + var0.getEyeHeight()
               + (var0.isSneaking() ? -0.125 : 0.0);
            double var9 = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * var3 - mc.getRenderManager().viewerPosZ;
            double var11 = 0.0;
            double var13 = var4.getEyeHeight();
            double var15 = 0.0;
            if (var4 == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) {
               float var17 = ((Entity)var4).rotationYaw;
               float var18 = ((Entity)var4).rotationPitch;
               double var19 = -Math.sin(Math.toRadians(var17)) * Math.cos(Math.toRadians(var18));
               double var21 = -Math.sin(Math.toRadians(var18));
               double var23 = Math.cos(Math.toRadians(var17)) * Math.cos(Math.toRadians(var18));
               var11 = var19;
               var13 += var21;
               var15 = var23;
            }

            float var28 = (var1 >> 24 & 0xFF) / 255.0F;
            float var29 = (var1 >> 16 & 0xFF) / 255.0F;
            float var30 = (var1 >> 8 & 0xFF) / 255.0F;
            float var20 = (var1 & 0xFF) / 255.0F;
            GL11.glPushMatrix();
            RenderUtils$1 var31 = null;

            try {
               var31 = uyB6();
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(2848);
               GL11.glDisable(3553);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               GL11.glLineWidth(var2);
               GL11.glColor4f(var29, var30, var20, var28);
               GL11.glBegin(1);
               GL11.glVertex3d(var11, var13, var15);
               GL11.glVertex3d(var5, var7, var9);
               GL11.glEnd();
               GL11.glLineWidth(1.0F);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDepthMask(true);
               GL11.glEnable(2929);
               GL11.glEnable(3553);
               GL11.glDisable(2848);
               GL11.glDisable(3042);
            } finally {
               restoreLightmapState(var31);
               GL11.glPopMatrix();
            }
         }
      }
   }

   public static void drawVerticalGradientRect(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 < var2) {
         int var6 = var0;
         var0 = var2;
         var2 = var6;
      }

      if (var1 < var3) {
         int var17 = var1;
         var1 = var3;
         var3 = var17;
      }

      float var7 = (var4 >> 24 & 0xFF) / 255.0F;
      float var8 = (var4 >> 16 & 0xFF) / 255.0F;
      float var9 = (var4 >> 8 & 0xFF) / 255.0F;
      float var10 = (var4 & 0xFF) / 255.0F;
      float var11 = (var5 >> 24 & 0xFF) / 255.0F;
      float var12 = (var5 >> 16 & 0xFF) / 255.0F;
      float var13 = (var5 >> 8 & 0xFF) / 255.0F;
      float var14 = (var5 & 0xFF) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator var15 = Tessellator.getInstance();
      WorldRenderer var16 = var15.getWorldRenderer();
      var16.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var16.pos(var2, var1, 0.0).color(var8, var9, var10, var7).endVertex();
      var16.pos(var0, var1, 0.0).color(var8, var9, var10, var7).endVertex();
      var16.pos(var0, var3, 0.0).color(var12, var13, var14, var11).endVertex();
      var16.pos(var2, var3, 0.0).color(var12, var13, var14, var11).endVertex();
      var15.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void NUNei(int var0, int var1, int var2) {
      int var3 = var2 == -1 ? -1089466352 : var2;
      Gui.drawRect(0, 0, var0, var1, var3);
   }

   public static void drawRainbowText(String var0, char var1, int var2, int var3, long var4, long var6, boolean var8, FontRenderer var9) {
      int var10 = var2;
      int var11 = 0;
      long var12 = 0L;

      for (int var14 = 0; var14 < var0.length(); var14++) {
         char var15 = var0.charAt(var14);
         if (var15 == var1) {
            var11++;
            var2 = var10;
            var3 += var9.FONT_HEIGHT + 5;
            var12 = var6 * var11;
         } else {
            var9.drawString(String.valueOf(var15), var2, var3, ClientUtils.AIowEv(var4, var12), var8);
            var2 += var9.getCharWidth(var15);
            if (var15 != ' ') {
               var12 -= 90L;
            }
         }
      }
   }

   public static void gfGg(double var0, double var2, double var4, double var6, int var8, float var9, int var10, boolean var11) {
      float var12 = (var10 >> 24 & 0xFF) / 255.0F;
      float var13 = (var10 >> 16 & 0xFF) / 255.0F;
      float var14 = (var10 >> 8 & 0xFF) / 255.0F;
      float var15 = (var10 & 0xFF) / 255.0F;
      RenderUtils$1 var16 = uyB6();

      try {
         GL11.glDisable(3553);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2929);
         GL11.glEnable(2848);
         GL11.glDepthMask(false);
         GL11.glLineWidth(var9);
         if (!var11) {
            GL11.glColor4f(var13, var14, var15, var12);
         }

         GL11.glBegin(1);
         long var17 = 0L;
         long var19 = 15000L / var8;
         long var21 = var19 / 2L;

         for (int var23 = 0; var23 < var8 * 2; var23++) {
            if (var11) {
               if (var23 % 2 != 0) {
                  if (var23 == 47) {
                     var17 = var21;
                  }

                  var17 += var19;
               }

               int var24 = ClientUtils.AIowEv(2L, var17);
               float var25 = (var24 >> 16 & 0xFF) / 255.0F;
               float var26 = (var24 >> 8 & 0xFF) / 255.0F;
               float var27 = (var24 & 0xFF) / 255.0F;
               GL11.glColor3f(var25, var26, var27);
            }

            double var31 = (Math.PI * 2) * var23 / var8 + Math.toRadians(180.0);
            GL11.glVertex3d(var0 + Math.cos(var31) * var6, var2, var4 + Math.sin(var31) * var6);
         }

         GL11.glEnd();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDepthMask(true);
         GL11.glDisable(2848);
         GL11.glEnable(2929);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
      } finally {
         restoreLightmapState(var16);
      }
   }

   public static void drawCheckMark(float var0, float var1, int var2, double var3, double var5) {
      GL11.glPushMatrix();
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GIWbDs(var2);
      GL11.glLineWidth((float)var3);
      float var7 = (float)(var3 / 2.0);
      float var8 = var7 / 2.0F;
      float var9 = var7 / 2.0F;
      GL11.glBegin(1);
      GL11.glVertex2d(var0 - var8, var1 + var9);
      GL11.glVertex2d(var0 + var5 - var8, var1 - var5 + var9);
      GL11.glVertex2d(var0 + var5 - var8, var1 - var5 + var9);
      GL11.glVertex2d(var0 + 2.0 * var5 - var8, var1 + var9);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void UWgp6(double var0, double var2, double var4, double var6, double var8, int var10) {
      boolean var11 = GL11.glIsEnabled(3042);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GIWbDs(var10);
      GL11.glBegin(7);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0 - var4 / var6, var2 + var4);
      GL11.glVertex2d(var0, var2 + var4 / var8);
      GL11.glVertex2d(var0 + var4 / var6, var2 + var4);
      GL11.glVertex2d(var0, var2);
      GL11.glEnd();
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.8F);
      GL11.glBegin(2);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0 - var4 / var6, var2 + var4);
      GL11.glVertex2d(var0, var2 + var4 / var8);
      GL11.glVertex2d(var0 + var4 / var6, var2 + var4);
      GL11.glVertex2d(var0, var2);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      if (!var11) {
         GL11.glDisable(3042);
      }

      GL11.glDisable(2848);
   }

   public static void GIWbDs(int var0) {
      GL11.glColor4f((var0 >> 16 & 0xFF) / 255.0F, (var0 >> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >> 24 & 0xFF) / 255.0F);
   }

   public static void drawRoundedRectWithOutline(float var0, float var1, float var2, float var3, float var4, int var5, int var6, int var7) {
      var0 *= 2.0F;
      var1 *= 2.0F;
      var2 *= 2.0F;
      var3 *= 2.0F;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      GL11.glScaled(0.5, 0.5, 0.5);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBegin(9);
      GIWbDs(var5);

      for (byte var8 = 0; var8 <= 90; var8 += 3) {
         double var9 = var8 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var0 + var4 + Math.sin(var9) * var4 * -1.0, var1 + var4 + Math.cos(var9) * var4 * -1.0);
      }

      for (int var15 = 90; var15 <= 180; var15 += 3) {
         double var22 = var15 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var0 + var4 + Math.sin(var22) * var4 * -1.0, var3 - var4 + Math.cos(var22) * var4 * -1.0);
      }

      for (byte var16 = 0; var16 <= 90; var16 += 3) {
         double var23 = var16 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var2 - var4 + Math.sin(var23) * var4, var3 - var4 + Math.cos(var23) * var4);
      }

      for (int var17 = 90; var17 <= 180; var17 += 3) {
         double var24 = var17 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var2 - var4 + Math.sin(var24) * var4, var1 + var4 + Math.cos(var24) * var4);
      }

      GL11.glEnd();
      GL11.glPushMatrix();
      GL11.glShadeModel(7425);
      GL11.glLineWidth(2.0F);
      GL11.glBegin(2);
      if (var6 != 0L) {
         GIWbDs(var6);
      }

      for (byte var18 = 0; var18 <= 90; var18 += 3) {
         double var25 = var18 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var0 + var4 + Math.sin(var25) * var4 * -1.0, var1 + var4 + Math.cos(var25) * var4 * -1.0);
      }

      for (int var19 = 90; var19 <= 180; var19 += 3) {
         double var26 = var19 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var0 + var4 + Math.sin(var26) * var4 * -1.0, var3 - var4 + Math.cos(var26) * var4 * -1.0);
      }

      if (var7 != 0) {
         GIWbDs(var7);
      }

      for (byte var20 = 0; var20 <= 90; var20 += 3) {
         double var27 = var20 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var2 - var4 + Math.sin(var27) * var4, var3 - var4 + Math.cos(var27) * var4);
      }

      for (int var21 = 90; var21 <= 180; var21 += 3) {
         double var28 = var21 * (float) (Math.PI / 180.0);
         GL11.glVertex2d(var2 - var4 + Math.sin(var28) * var4, var1 + var4 + Math.cos(var28) * var4);
      }

      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glPopAttrib();
      GL11.glPopMatrix();
      GL11.glLineWidth(1.0F);
      GL11.glShadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawSmoothFilledCircle(double var0, double var2, double var4, int var6, int var7) {
      if (var6 >= 3) {
         float var8 = (var7 >> 24 & 0xFF) / 255.0F;
         float var9 = (var7 >> 16 & 0xFF) / 255.0F;
         float var10 = (var7 >> 8 & 0xFF) / 255.0F;
         float var11 = (var7 & 0xFF) / 255.0F;
         Tessellator var12 = Tessellator.getInstance();
         WorldRenderer var13 = var12.getWorldRenderer();
         GlStateManager.enableBlend();
         GlStateManager.disableTexture2D();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glEnable(2848);
         GL11.glColor4f(var9, var10, var11, var8);
         double var14 = Math.toRadians(180.0);
         var13.begin(6, DefaultVertexFormats.POSITION);

         for (int var16 = 0; var16 < var6; var16++) {
            double var17 = (Math.PI * 2) * var16 / var6 + var14;
            var13.pos(var0 + Math.sin(var17) * var4, var2 + Math.cos(var17) * var4, 0.0).endVertex();
         }

         var12.draw();
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
      }
   }

   public static Framebuffer Gebxuy(Framebuffer var0) {
      return resizeFramebuffer(var0, false);
   }

   public static Framebuffer resizeFramebuffer(Framebuffer var0, boolean var1) {
      if (isFramebufferSizeStale(var0)) {
         if (var0 != null) {
            var0.deleteFramebuffer();
         }

         return new Framebuffer(mc.displayWidth, mc.displayHeight, var1);
      } else {
         return var0;
      }
   }

   public static boolean isFramebufferSizeStale(Framebuffer var0) {
      return var0 == null || var0.framebufferWidth != mc.displayWidth || var0.framebufferHeight != mc.displayHeight;
   }

   public static void drawFramebufferTexture(Framebuffer var0) {
      if (var0 != null) {
         ScaledResolution var1 = new ScaledResolution(mc);
         GlStateManager.bindTexture(var0.framebufferTexture);
         GL11.glBegin(7);
         GL11.glTexCoord2d(0.0, 1.0);
         GL11.glVertex2d(0.0, 0.0);
         GL11.glTexCoord2d(0.0, 0.0);
         GL11.glVertex2d(0.0, var1.getScaledHeight());
         GL11.glTexCoord2d(1.0, 0.0);
         GL11.glVertex2d(var1.getScaledWidth(), var1.getScaledHeight());
         GL11.glTexCoord2d(1.0, 1.0);
         GL11.glVertex2d(var1.getScaledWidth(), 0.0);
         GL11.glEnd();
      }
   }

   public static void lrTz(int var0) {
      GL11.glBindTexture(3553, var0);
   }

   public static void setAlphaThreshold(float var0) {
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, (float)(var0 * 0.01));
   }

   public static Color lerpColor(Color var0, Color var1, float var2) {
      var2 = Math.min(1.0F, Math.max(0.0F, var2));
      return new Color(
         lerpChannel(var0.getRed(), var1.getRed(), var2),
         lerpChannel(var0.getGreen(), var1.getGreen(), var2),
         lerpChannel(var0.getBlue(), var1.getBlue(), var2),
         lerpChannel(var0.getAlpha(), var1.getAlpha(), var2)
      );
   }

   public static int lerpChannel(int var0, int var1, double var2) {
      return lerp(var0, var1, (float)var2).intValue();
   }

   public static Double lerp(double var0, double var2, double var4) {
      return var0 + (var2 - var0) * var4;
   }

   public static void lTbf() {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void resetGlPipelineState() {
      int var0 = GL11.glGetInteger(34016);
      resetTextureUnitState(OpenGlHelper.defaultTexUnit);
      resetTextureUnitState(OpenGlHelper.lightmapTexUnit);
      rebindActiveTexture(var0);
      boolean var1 = GL11.glIsEnabled(3008);
      int var2 = GL11.glGetInteger(3009);
      float var3 = GL11.glGetFloat(3010);
      boolean var4 = GL11.glIsEnabled(2929);
      boolean var5 = GL11.glGetBoolean(2930);
      boolean var6 = GL11.glIsEnabled(2896);
      boolean var7 = GL11.glIsEnabled(32826);
      boolean var8 = GL11.glIsEnabled(3042);
      int var9 = GL11.glGetInteger(32969);
      int var10 = GL11.glGetInteger(32968);
      int var11 = GL11.glGetInteger(32971);
      int var12 = GL11.glGetInteger(32970);
      ((Buffer)lht).clear();
      GL11.glGetFloat(2816, lht);
      float var13 = lht.get(0);
      float var14 = lht.get(1);
      float var15 = lht.get(2);
      float var16 = lht.get(3);
      applyAlphaState(var1);
      GlStateManager.alphaFunc(519, 0.0F);
      GlStateManager.alphaFunc(var2, var3);
      applyDepthState(var4);
      GlStateManager.depthMask(!var5);
      GlStateManager.depthMask(var5);
      applyLightingState(var6);
      cIv778(var7);
      QvSfy6(var8);
      GlStateManager.tryBlendFuncSeparate(1, 0, 1, 0);
      GlStateManager.tryBlendFuncSeparate(var9, var10, var11, var12);
      GlStateManager.color(var13 == 0.0F ? 1.0F : 0.0F, var14 == 0.0F ? 1.0F : 0.0F, var15 == 0.0F ? 1.0F : 0.0F, var16 == 0.0F ? 1.0F : 0.0F);
      GlStateManager.color(var13, var14, var15, var16);
   }

   private static void resetTextureUnitState(int var0) {
      rebindActiveTexture(var0);
      boolean var1 = GL11.glIsEnabled(3553);
      int var2 = GL11.glGetInteger(32873);
      if (var1) {
         GlStateManager.disableTexture2D();
         GlStateManager.enableTexture2D();
      } else {
         GlStateManager.enableTexture2D();
         GlStateManager.disableTexture2D();
      }

      if (var2 != 0) {
         GlStateManager.bindTexture(0);
      }

      GlStateManager.bindTexture(var2);
   }

   private static void rebindActiveTexture(int var0) {
      int var1 = var0 == OpenGlHelper.defaultTexUnit ? OpenGlHelper.lightmapTexUnit : OpenGlHelper.defaultTexUnit;
      GlStateManager.setActiveTexture(var1);
      GlStateManager.setActiveTexture(var0);
   }

   private static void applyAlphaState(boolean var0) {
      if (var0) {
         GlStateManager.disableAlpha();
         GlStateManager.enableAlpha();
      } else {
         GlStateManager.enableAlpha();
         GlStateManager.disableAlpha();
      }
   }

   private static void applyDepthState(boolean var0) {
      if (var0) {
         GlStateManager.disableDepth();
         GlStateManager.enableDepth();
      } else {
         GlStateManager.enableDepth();
         GlStateManager.disableDepth();
      }
   }

   private static void applyLightingState(boolean var0) {
      if (var0) {
         GlStateManager.disableLighting();
         GlStateManager.enableLighting();
      } else {
         GlStateManager.enableLighting();
         GlStateManager.disableLighting();
      }
   }

   private static void cIv778(boolean var0) {
      if (var0) {
         GlStateManager.disableRescaleNormal();
         GlStateManager.enableRescaleNormal();
      } else {
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableRescaleNormal();
      }
   }

   private static void QvSfy6(boolean var0) {
      if (var0) {
         GlStateManager.disableBlend();
         GlStateManager.enableBlend();
      } else {
         GlStateManager.enableBlend();
         GlStateManager.disableBlend();
      }
   }

   public static Vec3 SYuCi(int var0, double var1, double var3, double var5) {
      GL11.glGetFloat(2982, projectionMatrixBuffer);
      GL11.glGetFloat(2983, modelViewMatrixBuffer);
      GL11.glGetInteger(2978, vE1);
      boolean var7 = GLU.gluProject((float)var1, (float)var3, (float)var5, projectionMatrixBuffer, modelViewMatrixBuffer, vE1, GDU);
      return var7 ? new Vec3(GDU.get(0) / var0, (Display.getHeight() - GDU.get(1)) / var0, GDU.get(2)) : null;
   }

   public static RenderUtils$2 captureProjectionMatrices(RenderUtils$2 var0, int var1) {
      if (var0 == null) {
         var0 = new RenderUtils$2();
      }

      RenderUtils$2.setGuiScale(var0, var1);
      ((Buffer)RenderUtils$2.Aqnrs(var0)).clear();
      ((Buffer)RenderUtils$2.getProjectionMatrix(var0)).clear();
      ((Buffer)RenderUtils$2.getViewportBuffer(var0)).clear();
      GL11.glGetFloat(2982, RenderUtils$2.Aqnrs(var0));
      GL11.glGetFloat(2983, RenderUtils$2.getProjectionMatrix(var0));
      GL11.glGetInteger(2978, RenderUtils$2.getViewportBuffer(var0));
      ((Buffer)RenderUtils$2.Aqnrs(var0)).rewind();
      ((Buffer)RenderUtils$2.getProjectionMatrix(var0)).rewind();
      ((Buffer)RenderUtils$2.getViewportBuffer(var0)).rewind();
      return var0;
   }

   public static boolean projectToScreen(RenderUtils$2 var0, double var1, double var3, double var5, double[] var7) {
      if (var0 != null && var7 != null && var7.length >= 3) {
         ((Buffer)RenderUtils$2.getProjectedCoords(var0)).clear();
         boolean var8 = GLU.gluProject(
            (float)var1, (float)var3, (float)var5, RenderUtils$2.Aqnrs(var0), RenderUtils$2.getProjectionMatrix(var0), RenderUtils$2.getViewportBuffer(var0), RenderUtils$2.getProjectedCoords(var0)
         );
         if (!var8) {
            return false;
         } else {
            var7[0] = RenderUtils$2.getProjectedCoords(var0).get(0) / RenderUtils$2.getGuiScale(var0);
            var7[1] = (Display.getHeight() - RenderUtils$2.getProjectedCoords(var0).get(1)) / RenderUtils$2.getGuiScale(var0);
            var7[2] = RenderUtils$2.getProjectedCoords(var0).get(2);
            return true;
         }
      } else {
         return false;
      }
   }

   public static void jxyoE(float var0, float var1, float var2, float var3, float var4, int var5) {
      if (!(var2 <= var0)) {
         float var6 = var2 - var0;
         if (var6 < 3.0F) {
            var4 = Math.min(var4, var6 / 2.0F);
         }

         var0 = (float)(var0 * 2.0);
         var1 = (float)(var1 * 2.0);
         var2 = (float)(var2 * 2.0);
         var3 = (float)(var3 * 2.0);
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         GL11.glScaled(0.5, 0.5, 0.5);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glEnable(2848);
         GL11.glBegin(9);
         GIWbDs(var5);

         for (byte var7 = 0; var7 <= 90; var7 += 3) {
            double var8 = var7 * (float) (Math.PI / 180.0);
            GL11.glVertex2d(var0 + var4 + Math.sin(var8) * var4 * -1.0, var1 + var4 + Math.cos(var8) * var4 * -1.0);
         }

         for (int var14 = 90; var14 <= 180; var14 += 3) {
            double var17 = var14 * (float) (Math.PI / 180.0);
            GL11.glVertex2d(var0 + var4 + Math.sin(var17) * var4 * -1.0, var3 - var4 + Math.cos(var17) * var4 * -1.0);
         }

         if (var2 - var0 >= 4.5) {
            for (int var15 = 0; var15 <= 90; var15++) {
               double var18 = var15 * (float) (Math.PI / 180.0);
               GL11.glVertex2d(var2 - var4 + Math.sin(var18) * var4, var3 - var4 + Math.cos(var18) * var4);
            }

            for (int var16 = 90; var16 <= 180; var16++) {
               double var19 = var16 * (float) (Math.PI / 180.0);
               GL11.glVertex2d(var2 - var4 + Math.sin(var19) * var4, var1 + var4 + Math.cos(var19) * var4);
            }
         }

         GL11.glEnd();
         GL11.glEnable(3553);
         GL11.glDisable(3042);
         GL11.glDisable(2848);
         GL11.glEnable(3553);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public static void drawFilledRect(float var0, float var1, float var2, float var3, int var4) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GIWbDs(var4);
      GL11.glBegin(7);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void drawRoundedGradientRect(float var0, float var1, float var2, float var3, float var4, int var5, int var6, int var7, int var8) {
      if (!(var2 <= var0)) {
         float var9 = var2 - var0;
         if (var9 < 3.0F) {
            var4 = Math.min(var4, var9 / 2.0F);
         }

         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         GL11.glShadeModel(7425);
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         GL11.glScaled(0.5, 0.5, 0.5);
         var0 = (float)(var0 * 2.0);
         var1 = (float)(var1 * 2.0);
         var2 = (float)(var2 * 2.0);
         var3 = (float)(var3 * 2.0);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GIWbDs(var5);
         GL11.glEnable(2848);
         GL11.glShadeModel(7425);
         GL11.glBegin(9);

         for (byte var10 = 0; var10 <= 90; var10 += 3) {
            double var11 = var10 * (float) (Math.PI / 180.0);
            GL11.glVertex2d(var0 + var4 + Math.sin(var11) * var4 * -1.0, var1 + var4 + Math.cos(var11) * var4 * -1.0);
         }

         GIWbDs(var6);

         for (int var17 = 90; var17 <= 180; var17 += 3) {
            double var20 = var17 * (float) (Math.PI / 180.0);
            GL11.glVertex2d(var0 + var4 + Math.sin(var20) * var4 * -1.0, var3 - var4 + Math.cos(var20) * var4 * -1.0);
         }

         if (var2 - var0 >= 4.5) {
            GIWbDs(var7);

            for (byte var18 = 0; var18 <= 90; var18 += 3) {
               double var21 = var18 * (float) (Math.PI / 180.0);
               GL11.glVertex2d(var2 - var4 + Math.sin(var21) * var4, var3 - var4 + Math.cos(var21) * var4);
            }

            GIWbDs(var8);

            for (int var19 = 90; var19 <= 180; var19 += 3) {
               double var22 = var19 * (float) (Math.PI / 180.0);
               GL11.glVertex2d(var2 - var4 + Math.sin(var22) * var4, var1 + var4 + Math.cos(var22) * var4);
            }
         }

         GL11.glEnd();
         GL11.glEnable(3553);
         GL11.glDisable(3042);
         GL11.glDisable(2848);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
         GL11.glEnable(3553);
         GL11.glDisable(3042);
         GL11.glDisable(2848);
         GL11.glShadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public static int applyAlphaToColor(int var0, double var1) {
      if (var1 < 0.0 || var1 > 1.0) {
         var1 = 0.5;
      }

      int var3 = var0 >> 16 & 0xFF;
      int var4 = var0 >> 8 & 0xFF;
      int var5 = var0 & 0xFF;
      int var6 = (int)(var1 * 255.0);
      return var6 << 24 | var3 << 16 | var4 << 8 | var5;
   }

   public static void ZHgAz(float var0, float var1, float var2, int var3, float var4, float var5, float var6, float var7, float var8) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(2884);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glColor4f(var5, var6, var7, var8);
      GL11.glLineWidth(var4);
      GL11.glBegin(2);

      for (int var9 = 0; var9 <= var3; var9++) {
         double var10 = (Math.PI * 2) * var9 / var3;
         float var12 = (float)(var2 * Math.cos(var10)) + var0;
         float var13 = (float)(var2 * Math.sin(var10)) + var1;
         GL11.glVertex2f(var12, var13);
      }

      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glDisable(2884);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glLineWidth(1.0F);
      GL11.glPopMatrix();
   }

   public static void drawArc(float var0, float var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = (var6 >> 16 & 0xFF) / 255.0F;
      float var8 = (var6 >> 8 & 0xFF) / 255.0F;
      float var9 = (var6 & 0xFF) / 255.0F;
      float var10 = (var6 >> 24 & 0xFF) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(2884);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glColor4f(var7, var8, var9, var10);
      GL11.glLineWidth(var5);
      GL11.glBegin(3);

      for (float var11 = var3; var11 <= var4; var11++) {
         double var12 = Math.toRadians(var11 + 180.0F);
         float var14 = (float)(var2 * Math.cos(var12)) + var0;
         float var15 = (float)(var2 * Math.sin(var12)) + var1;
         GL11.glVertex2f(var14, var15);
      }

      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glDisable(2884);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glLineWidth(1.0F);
      GL11.glPopMatrix();
   }

   public static void drawHorizontalGradient(float var0, float var1, float var2, float var3, int var4, int var5) {
      float var6 = (var4 >> 24 & 0xFF) / 255.0F;
      float var7 = (var4 >> 16 & 0xFF) / 255.0F;
      float var8 = (var4 >> 8 & 0xFF) / 255.0F;
      float var9 = (var4 & 0xFF) / 255.0F;
      float var10 = (var5 >> 24 & 0xFF) / 255.0F;
      float var11 = (var5 >> 16 & 0xFF) / 255.0F;
      float var12 = (var5 >> 8 & 0xFF) / 255.0F;
      float var13 = (var5 & 0xFF) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator var14 = Tessellator.getInstance();
      WorldRenderer var15 = var14.getWorldRenderer();
      var15.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var15.pos(var0, var3, 0.0).color(var7, var8, var9, var6).endVertex();
      var15.pos(var2, var3, 0.0).color(var11, var12, var13, var10).endVertex();
      var15.pos(var2, var1, 0.0).color(var11, var12, var13, var10).endVertex();
      var15.pos(var0, var1, 0.0).color(var7, var8, var9, var6).endVertex();
      var14.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void drawVerticalGradient(float var0, float var1, float var2, float var3, int var4, int var5) {
      float var6 = (var4 >> 24 & 0xFF) / 255.0F;
      float var7 = (var4 >> 16 & 0xFF) / 255.0F;
      float var8 = (var4 >> 8 & 0xFF) / 255.0F;
      float var9 = (var4 & 0xFF) / 255.0F;
      float var10 = (var5 >> 24 & 0xFF) / 255.0F;
      float var11 = (var5 >> 16 & 0xFF) / 255.0F;
      float var12 = (var5 >> 8 & 0xFF) / 255.0F;
      float var13 = (var5 & 0xFF) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator var14 = Tessellator.getInstance();
      WorldRenderer var15 = var14.getWorldRenderer();
      var15.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var15.pos(var2, var1, 0.0).color(var7, var8, var9, var6).endVertex();
      var15.pos(var0, var1, 0.0).color(var7, var8, var9, var6).endVertex();
      var15.pos(var0, var3, 0.0).color(var11, var12, var13, var10).endVertex();
      var15.pos(var2, var3, 0.0).color(var11, var12, var13, var10).endVertex();
      var14.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void renderItemStack(ItemStack var0, int var1, int var2) {
      renderItemStackWithClear(var0, var1, var2, true);
   }

   public static void renderItemStackWithClear(ItemStack var0, int var1, int var2, boolean var3) {
      if (var0 != null) {
         GlStateManager.pushMatrix();
         float var4 = mc.getRenderItem().zLevel;

         try {
            beginItemRenderState();
            if (var3) {
               GlStateManager.depthMask(true);
               GlStateManager.clear(256);
            } else {
               GlStateManager.disableDepth();
               GlStateManager.depthMask(false);
            }

            RenderHelper.enableStandardItemLighting();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, 1.0F, -0.01F);
            mc.getRenderItem().zLevel = -150.0F;
            mc.getRenderItem().renderItemAndEffectIntoGUI(var0, var1, var2);
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            finishItemRenderState();
            GlStateManager.disableBlend();
         } finally {
            mc.getRenderItem().zLevel = var4;
            GlStateManager.popMatrix();
         }
      }
   }

   public static void renderItemIconIntoGui(ItemStack var0, int var1, int var2) {
      if (var0 != null) {
         beginItemRenderState();
         mc.getRenderItem().zLevel = -150.0F;
         GlStateManager.enableDepth();
         RenderHelper.enableGUIStandardItemLighting();
         mc.getRenderItem().renderItemAndEffectIntoGUI(var0, var1, var2 - 8);
         mc.getRenderItem().zLevel = 0.0F;
         GlStateManager.disableDepth();
         finishItemRenderState();
         GlStateManager.disableBlend();
      }
   }

   public static int getHealthBarColor(float var0) {
      if (var0 > 0.6F) {
         return 65280;
      } else {
         return var0 > 0.3F ? 16776960 : 16711680;
      }
   }

   public static void dVum(int var0, int var1, float var2) {
      int var3 = (int)(var2 * 13.0F);
      int var4 = getHealthBarColor(var2);
      GlStateManager.disableTexture2D();
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      var6.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(var0 + 2, var1 + 15, 0.0).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
      var6.pos(var0 + 2, var1 + 16, 0.0).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
      var6.pos(var0 + 15, var1 + 16, 0.0).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
      var6.pos(var0 + 15, var1 + 15, 0.0).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
      var5.draw();
      float var7 = (var4 >> 16 & 0xFF) / 255.0F;
      float var8 = (var4 >> 8 & 0xFF) / 255.0F;
      float var9 = (var4 & 0xFF) / 255.0F;
      var6.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(var0 + 2, var1 + 15, 0.0).color(var7, var8, var9, 1.0F).endVertex();
      var6.pos(var0 + 2, var1 + 16, 0.0).color(var7, var8, var9, 1.0F).endVertex();
      var6.pos(var0 + 2 + var3, var1 + 16, 0.0).color(var7, var8, var9, 1.0F).endVertex();
      var6.pos(var0 + 2 + var3, var1 + 15, 0.0).color(var7, var8, var9, 1.0F).endVertex();
      var5.draw();
      GlStateManager.enableTexture2D();
   }

   public static int GDf3(int var0) {
      switch (var0) {
         case 1:
            return 16777215;
         case 2:
            return 5636095;
         case 3:
            return 43690;
         case 4:
            return 11141290;
         case 5:
            return 16755200;
         case 6:
         case 7:
         case 8:
         case 9:
         default:
            return var0 > 5 ? 16733695 : 16777215;
         case 10:
            return 16733695;
      }
   }

   public static int drawEnchantmentWithLevel(FontRenderer var0, String var1, int var2, int var3, int var4) {
      int var5 = var0.drawStringWithShadow(var1, var3, var4, 16777215);
      var0.drawStringWithShadow(String.valueOf(var2), var5, var4, GDf3(var2));
      return var5;
   }

   public static void finishItemRenderState() {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GL11.glEnable(3553);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void beginItemRenderState() {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.disableLighting();
      GL11.glEnable(3553);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.enableDepth();
      GlStateManager.depthMask(true);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static String getEnchantmentAbbreviation(int var0) {
      switch (var0) {
         case 0:
            return "pt";
         case 1:
            return "frp";
         case 2:
            return "ff";
         case 3:
            return "blp";
         case 4:
            return "prp";
         case 5:
            return "thr";
         case 6:
            return "res";
         case 7:
            return "aa";
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         default:
            return null;
         case 16:
            return "sh";
         case 17:
            return "smt";
         case 18:
            return "ban";
         case 19:
            return "kb";
         case 20:
            return "fa";
         case 21:
            return "lot";
         case 32:
            return "eff";
         case 33:
            return "sil";
         case 34:
            return "ub";
         case 35:
            return "for";
         case 48:
            return "pow";
         case 49:
            return "pun";
         case 50:
            return "flm";
         case 51:
            return "inf";
      }
   }

   public static ResourceLocation loadRecoloredIcon(String var0, String var1, ResourceLocation var2) {
      try (InputStream var3 = CoreResourceIndex.openResource(var0)) {
         if (var3 != null) {
            BufferedImage var24 = ImageIO.read(var3);
            int var6 = var24.getWidth();
            int var7 = var24.getHeight();
            BufferedImage var8 = new BufferedImage(var6, var7, 2);

            for (int var9 = 0; var9 < var7; var9++) {
               for (int var10 = 0; var10 < var6; var10++) {
                  int var11 = var24.getRGB(var10, var9) >>> 24 & 0xFF;
                  if (var11 > 0) {
                     var8.setRGB(var10, var9, var11 << 24 | 16777215);
                  }
               }
            }

            return mc.getTextureManager().getDynamicTextureLocation(var1, new DynamicTexture(var8));
         } else {
            return var2;
         }
      } catch (Exception var23) {
         var23.printStackTrace();
         return var2;
      }
   }

   public static ResourceLocation getIconTexture(String var0) {
      ResourceLocation var1 = iconTextureCache.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         String var2 = "jade_icon_" + var0.hashCode();
         ResourceLocation var3 = loadRecoloredIcon(var0, var2, null);
         if (var3 != null) {
            iconTextureCache.put(var0, var3);
         }

         return var3;
      }
   }

   public static void drawIconTexture(ResourceLocation var0, float var1, float var2, int var3, int var4) {
      if (var0 != null) {
         boolean var5 = GL11.glIsEnabled(2929);
         boolean var6 = GL11.glIsEnabled(3042);
         boolean var7 = GL11.glGetBoolean(2930);
         finishItemRenderState();
         mc.getTextureManager().bindTexture(var0);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         float var8 = (var4 >>> 24 & 0xFF) / 255.0F;
         float var9 = (var4 >> 16 & 0xFF) / 255.0F;
         float var10 = (var4 >> 8 & 0xFF) / 255.0F;
         float var11 = (var4 & 0xFF) / 255.0F;
         GlStateManager.color(var9, var10, var11, var8);
         GL11.glPushMatrix();
         GL11.glTranslatef(var1, var2, 0.0F);
         Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, var3, var3, var3, var3);
         GL11.glPopMatrix();
         restoreBlendDepthState(var5, var6, var7);
      }
   }

   public static void restoreBlendDepthState(boolean var0, boolean var1, boolean var2) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (var1) {
         GlStateManager.enableBlend();
      } else {
         GlStateManager.disableBlend();
      }

      if (var0) {
         GlStateManager.enableDepth();
      } else {
         GlStateManager.disableDepth();
      }

      GlStateManager.depthMask(var2);
   }

   private static void renderBlockFaceQuad(int var0, int var1, AxisAlignedBB var2, EnumFacing var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9) {
      drawBlockFace(var2, var3, var0, var1, var8, var9);
   }
}
