// Jade recovery: module: Bed Plates (render); original class: jade.deps.eLz.VzpZlW
package jade.client.module.render;

import jade.client.common.BlockScanner;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.ExternalItemTextures;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils$1;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.bedplates.BedLocator;
import jade.client.module.render.bedplates.BedPlateCache;
import jade.client.module.render.bedplates.BedPlateEntry;
import jade.client.module.render.bedplates.BedPlateTracker;
import jade.client.module.render.bedplates.BedPlateShapes;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = "BedESP")
public class BedPlates extends Module implements ExternalRenderableModule {
   private static final float AUTO_SCALE_REFERENCE_DISTANCE = 8.0F;
   private static final float aM8 = 0.5625F;
   private static final double PLATE_HEIGHT_OFFSET = 0.6;
   private static final int GDWlHx = 8;
   private static final float OUTLINE_EXPANSION = 1.25F;
   private SliderSetting range;
   private SliderSetting rounding;
   private SliderSetting opacity;
   private BooleanSetting exposedOutline;
   private ColorSetting exposedColor;
   private BooleanSetting outlineExposedLayers;
   private BooleanSetting defenceHud;
   private BooleanSetting blockCount;
   private FontSetting blockCountFont;
   private SliderSetting scale;
   private BooleanSetting autoScale;
   private final List<BlockPos[]> Izc = new ArrayList<>();
   private static final int SOan = 16;
   private static final int ZegX = 18;
   private static final int SFu = 3;
   private final List<BlockPos[]> YKxI = new ArrayList<>();
   private boolean lvZ;
   private final BedPlateTracker bedPlateTracker = new BedPlateTracker();

   public BedPlates() {
      super("Bed Plates", Category.render);
      this.registerSetting(this.range = new SliderSetting("Range", 10.0, 2.0, 200.0, 2.0));
      this.registerSetting(this.rounding = new SliderSetting("Rounding", 4.0, 0.0, 10.0, 0.5));
      this.registerSetting(
         this.opacity = new SliderSetting(
            "Opacity", "%", 55.0, 5.0, 100.0, 5.0
         )
      );
      this.registerSetting(
         this.exposedOutline = new BooleanSetting(
            "Exposed Outline",
            true,
            new String[]{"Exposed outline"}
         )
      );
      this.registerSetting(
         this.exposedColor = new ColorSetting(
            "Exposed Color",
            80,
            255,
            120,
            90
         )
      );
      this.registerSetting(
         this.outlineExposedLayers = new BooleanSetting(
            "Outline Exposed Layers", true
         )
      );
      this.registerSetting(this.defenceHud = new BooleanSetting("Defence HUD", false, new String[]{"Show defense layers"}));
      this.registerSetting(
         this.blockCount = new BooleanSetting(
            "Block count",
            true,
            new String[]{"Show defense counts"}
         )
      );
      this.registerSetting(
         this.blockCountFont = new FontSetting("Block count font", "Modern")
      );
      this.registerSetting(this.scale = new SliderSetting("Scale", 1.0, 0.1, 2.0, 0.05));
      this.registerSetting(
         this.autoScale = new BooleanSetting(
            "Auto Scale", false
         )
      );
   }

   @Override
   public void guiUpdate() {
      this.exposedColor.setVisible(this.exposedOutline.isToggled(), this);
      boolean var1 = this.defenceHud.isToggled();
      this.opacity.setVisible(var1, this);
      this.outlineExposedLayers.setVisible(this.exposedOutline.isToggled(), this);
      this.blockCount.setVisible(var1, this);
      this.blockCountFont.setVisible(var1 && this.blockCount.isToggled(), this);
      this.scale.setVisible(var1, this);
      this.autoScale.setVisible(var1, this);
   }

   @Override
   public void onEnable() {
      this.setScanActive(dMne());
   }

   @Override
   public void onDisable() {
      ExternalRenderer.invalidateExternalFrame();
      this.setScanActive(false);
      this.YKxI.clear();
      this.Izc.clear();
      this.bedPlateTracker.VPpl();
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         this.setScanActive(false);
         this.YKxI.clear();
         this.Izc.clear();
         this.bedPlateTracker.VPpl();
      }
   }

   public int getScanPriority() {
      return this.isEnabled() && this.lvZ ? 8 : 0;
   }

   @Override
   public void onUpdate() {
      boolean var1 = dMne();
      this.setScanActive(var1);
      if (!var1) {
         this.YKxI.clear();
         this.Izc.clear();
         this.bedPlateTracker.VPpl();
      } else {
         BlockScanner var2 = BlockScanner.getInstance();
         double var3 = this.range.getInput() * this.range.getInput();
         double var5 = mc.thePlayer.posX;
         double var7 = mc.thePlayer.posY;
         double var9 = mc.thePlayer.posZ;
         List var11 = BedLocator.QBREh(var2, mc.theWorld, var5, var7, var9, var3);
         this.YKxI.clear();

         for (BlockPos[] var13 : (java.lang.Iterable<BlockPos[]>) (java.lang.Iterable<?>) (var11)) {
            this.YKxI.add(BedLocator.copyBedPositions(var13));
         }

         if (!this.defenceHud.isToggled()) {
            this.bedPlateTracker.VPpl();
         } else {
            this.bedPlateTracker.WUpzQ(mc, var11);
         }
      }
   }

   @Override
   public String getInfo() {
      if (!dMne()) {
         return "";
      } else {
         int var1 = BlockScanner.getInstance().getBedPlateCount();
         return var1 > 0 ? String.valueOf(var1) : "";
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.isEnabled() && dMne()) {
         float var2 = this.WXeb();
         double var3 = this.range.getInput() * this.range.getInput();
         double var5 = mc.thePlayer.posX;
         double var7 = mc.thePlayer.posY;
         double var9 = mc.thePlayer.posZ;
         ArrayList var11 = new ArrayList();
         HashSet var12 = new HashSet();

         for (BlockPos[] var14 : this.YKxI) {
            BlockPos var15 = var14[0];
            AxisAlignedBB var16 = BedLocator.createBedBoundingBox(var14[0], var14[1], var2);
            if (RenderUtils.XRxsYw(var16) && var12.add(var15)) {
               var11.add(BedLocator.copyBedPositions(var14));
            }
         }

         for (BlockPos[] var27 : new ArrayList<>(this.Izc)) {
            if (var27 != null && var27.length >= 2) {
               BlockPos var30 = var27[0];
               BlockPos var31 = var27[1];
               if (!var12.contains(var30) && BedLocator.euy0(mc.theWorld, var27)) {
                  double var17 = var30.getX() + 0.5 - var5;
                  double var19 = var30.getY() + 0.5 - var7;
                  double var21 = var30.getZ() + 0.5 - var9;
                  if (!(var17 * var17 + var19 * var19 + var21 * var21 > var3)) {
                     AxisAlignedBB var23 = BedLocator.createBedBoundingBox(var30, var31, var2);
                     if (RenderUtils.XRxsYw(var23)) {
                        var11.add(BedLocator.copyBedPositions(var27));
                        var12.add(var30);
                     }
                  }
               }
            }
         }

         for (BlockPos[] var28 : (java.lang.Iterable<BlockPos[]>) (java.lang.Iterable<?>) (var11)) {
            if (this.VIuQ()) {
               this.renderExternalBedPlates(var28, var2);
            } else {
               this.renderExposedLayerOutlines(var28, var2);
               if (this.defenceHud.isToggled() && BedLocator.hasFootPartFirst(mc.theWorld, var28)) {
                  this.XJAmO(var28, var2);
               }
            }
         }

         this.Izc.clear();

         for (BlockPos[] var29 : (java.lang.Iterable<BlockPos[]>) (java.lang.Iterable<?>) (var11)) {
            this.Izc.add(BedLocator.copyBedPositions(var29));
         }
      }
   }

   private static boolean dMne() {
      return ClientUtils.getBedWarsBoardType() == 2;
   }

   private void setScanActive(boolean var1) {
      if (this.lvZ != var1) {
         BlockScanner var2 = BlockScanner.getInstance();
         this.lvZ = var1;
         if (var1) {
            var2.addListener(this.bedPlateTracker);
            var2.enableBedPlateScan();
            var2.queueLoadedChunks();
         } else {
            var2.removeListener(this.bedPlateTracker);
            var2.disableBedPlateScan();
         }
      }
   }

   private void renderExternalBedPlates(BlockPos[] var1, float var2) {
      ExternalRenderBuffer var3 = ExternalRenderer.getActiveRenderBuffer();
      ScreenProjector var4 = ExternalRenderer.getActiveScreenProjector();
      if (var3 != null && var4 != null) {
         RenderManager var5 = mc.getRenderManager();
         List var6 = this.getAirLayerPositions(var1);
         int var7 = this.getLayerBlockCount(var1);
         int var8 = ClientUtils.YVVZ(this.exposedColor.getArgb(), Math.max(90, this.exposedColor.getArgb() >>> 24));
         if (this.exposedOutline.isToggled() && this.outlineExposedLayers.isToggled() && (var7 <= 0 || var6.size() <= var7 / 2)) {
            for (BlockPos var10 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (var6)) {
               double var11 = var10.getX() - var5.viewerPosX;
               double var13 = var10.getY() - var5.viewerPosY;
               double var15 = var10.getZ() - var5.viewerPosZ;
               var4.drawProjectedBox(var11, var13, var15, var11 + 1.0, var13 + 1.0, var15 + 1.0, var3, var8, 2.0F, true);
            }
         }

         if (this.defenceHud.isToggled() && BedLocator.hasFootPartFirst(mc.theWorld, var1)) {
            BedPlateCache var43 = this.bedPlateTracker.getCache(var1[0], var1[1]);
            if (var43 != null && !var43.getEntries().isEmpty()) {
               AxisAlignedBB var44 = BedLocator.createBedBoundingBox(var1[0], var1[1], var2);
               double var45 = (var44.minX + var44.maxX) * 0.5 - var5.viewerPosX;
               double var46 = var44.maxY + 0.6 - var5.viewerPosY;
               double var47 = (var44.minZ + var44.maxZ) * 0.5 - var5.viewerPosZ;
               if (var4.projectPoint(var45, var46, var47)) {
                  double var17 = var4.projectedPoint[0];
                  double var19 = var4.projectedPoint[1];
                  float var21 = (this.autoScale.isToggled() ? this.computeAutoScale((float)Math.sqrt(var45 * var45 + var46 * var46 + var47 * var47)) : this.getBaseScale())
                     * var4.getProjectedScale();
                  if (Float.isFinite(var21) && !(var21 <= 0.0F)) {
                     var21 = Math.min(var21, 16.0F);
                     double var22 = var43.getEntries().size() * 18 - 2;
                     double var24 = var17 - var22 * var21 / 2.0;
                     double var26 = var24 - 3.0F * var21;
                     double var28 = var19 - 11.0F * var21;
                     double var30 = var24 + (var22 + 3.0) * var21;
                     double var32 = var19 + 11.0F * var21;
                     float var34 = (float)this.rounding.getInput() * var21;
                     var3.fillRoundedRect(var26, var28, var30, var32, Math.round((float)this.opacity.getInput() * 2.55F) << 24, var34);
                     if (this.exposedOutline.isToggled() && this.outlineExposedLayers.isToggled() && !var6.isEmpty()) {
                        var3.strokeRoundedRect(var26, var28, var30, var32, var8, var34, 1.25F * var21);
                     }

                     for (int var35 = 0; var35 < var43.getEntries().size(); var35++) {
                        BedPlateEntry var36 = var43.getEntries().get(var35);
                        double var37 = var24 + var35 * 18 * var21;
                        double var39 = var19 - 8.0F * var21;
                        if (var36.hasItem()) {
                           ExternalItemTextures.drawItemTexture(var3, var36.getItemStack(), var37, var39, 16.0F * var21, 1.0F, 0.0F);
                        } else if (var36.hasSprite()) {
                           ExternalItemTextures.qlKvouG(var3, var36.getSprite(), var37, var39, 16.0F * var21);
                        }

                        if (this.blockCount.isToggled() && var36.GULE() > 1) {
                           String var41 = String.valueOf(var36.GULE());
                           float var42 = this.getBlockCountFont().getStringWidth(var41) * var21;
                           FormattedTextRenderer.drawTextAtHeight(
                              var3,
                              this.getBlockCountFont(),
                              var41,
                              var37 + 17.0F * var21 - var42,
                              var39 + 9.0F * var21,
                              this.getBlockCountFont().getFontHeight() * var21,
                              -1,
                              true,
                              var42
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void renderExposedLayerOutlines(BlockPos[] var1, float var2) {
      List var3 = this.getAirLayerPositions(var1);
      int var4 = this.getLayerBlockCount(var1);
      boolean var5 = var4 > 0 && var3.size() > var4 / 2;
      if (this.exposedOutline.isToggled() && this.outlineExposedLayers.isToggled() && !var3.isEmpty() && !var5) {
         RenderUtils$1 var6 = null;

         try {
            var6 = RenderUtils.uyB6();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            GL11.glLineWidth(2.0F);
            int var7 = this.exposedColor.getArgb();
            float var8 = (var7 >> 24 & 0xFF) / 255.0F;
            float var9 = (var7 >> 16 & 0xFF) / 255.0F;
            float var10 = (var7 >> 8 & 0xFF) / 255.0F;
            float var11 = (var7 & 0xFF) / 255.0F;

            for (BlockPos var13 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (var3)) {
               this.drawColoredBlockOutline(var13, var9, var10, var11, var8);
            }
         } finally {
            RenderUtils.restoreLightmapState(var6);
            GL11.glLineWidth(1.0F);
            this.restoreItemRenderState();
         }
      }
   }

   private void drawColoredBlockOutline(BlockPos var1, float var2, float var3, float var4, float var5) {
      double var6 = var1.getX() - mc.getRenderManager().viewerPosX;
      double var8 = var1.getY() - mc.getRenderManager().viewerPosY;
      double var10 = var1.getZ() - mc.getRenderManager().viewerPosZ;
      GlStateManager.color(var2, var3, var4, Math.max(var5, 0.35F));
      RenderUtils.drawBoxOutline(new AxisAlignedBB(var6, var8, var10, var6 + 1.0, var8 + 1.0, var10 + 1.0));
   }

   private boolean hasExposedLayers(BlockPos[] var1) {
      return !this.getAirLayerPositions(var1).isEmpty();
   }

   private int getLayerBlockCount(BlockPos[] var1) {
      if (var1 != null && var1.length >= 2) {
         List var2 = BedPlateShapes.getLayersForDirection(var1[0], var1[1]);
         if (var2 != null && !var2.isEmpty()) {
            HashSet var3 = new HashSet();

            for (BlockPos var5 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) ((List)var2.get(0))) {
               var3.add(var1[0].add(var5).toLong());
            }

            return var3.size();
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private List<BlockPos> getAirLayerPositions(BlockPos[] var1) {
      ArrayList var2 = new ArrayList();
      if (var1 != null && var1.length >= 2 && mc.theWorld != null) {
         List var3 = BedPlateShapes.getLayersForDirection(var1[0], var1[1]);
         if (var3 != null && !var3.isEmpty()) {
            HashSet var4 = new HashSet();

            for (BlockPos var6 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) ((List)var3.get(0))) {
               BlockPos var7 = var1[0].add(var6);
               if (mc.theWorld.getBlockState(var7).getBlock() == Blocks.air && var4.add(var7.toLong())) {
                  var2.add(var7);
               }
            }

            return var2;
         } else {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private void XJAmO(BlockPos[] var1, float var2) {
      BedPlateCache var3 = this.bedPlateTracker.getCache(var1[0], var1[1]);
      if (var3 != null && !var3.getEntries().isEmpty()) {
         RenderManager var4 = mc.getRenderManager();
         if (var4 != null) {
            AxisAlignedBB var5 = BedLocator.createBedBoundingBox(var1[0], var1[1], var2);
            double var6 = (var5.minX + var5.maxX) * 0.5 - var4.viewerPosX;
            double var8 = var5.maxY + 0.6 - var4.viewerPosY;
            double var10 = (var5.minZ + var5.maxZ) * 0.5 - var4.viewerPosZ;
            float var12 = this.getBaseScale();
            if (this.autoScale.isToggled()) {
               float var13 = (float)Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
               var12 = this.computeAutoScale(var13);
            }

            IFont var31 = this.getBlockCountFont();
            if (var31 != null) {
               List var14 = var3.getEntries();
               int var15 = var14.size() * 18 - 2;
               int var16 = -var15 / 2;
               byte var17 = -8;
               int var18 = var16 - 3;
               int var19 = var17 - 3;
               int var20 = var16 + var15 + 3;
               int var21 = var17 + 16 + 3;
               boolean var22 = this.exposedOutline.isToggled() && this.hasExposedLayers(var1);
               GlStateManager.pushMatrix();
               RenderUtils$1 var23 = null;

               try {
                  var23 = RenderUtils.uyB6();
                  GlStateManager.translate(var6, var8, var10);
                  GlStateManager.rotate(-var4.playerViewY, 0.0F, 1.0F, 0.0F);
                  GlStateManager.rotate(var4.playerViewX, 1.0F, 0.0F, 0.0F);
                  GlStateManager.scale(-var12, -var12, var12);
                  GlStateManager.disableLighting();
                  GlStateManager.depthMask(false);
                  GlStateManager.disableDepth();
                  GlStateManager.disableCull();
                  GlStateManager.enableBlend();
                  GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                  this.BtLcv(var18, var19, var20, var21, var22);
                  this.setupItemRenderState();

                  for (int var24 = 0; var24 < var14.size(); var24++) {
                     BedPlateEntry var25 = (BedPlateEntry)var14.get(var24);
                     int var26 = var16 + var24 * 18;
                     this.drawEntryIcon(var25, var26, var17);
                     this.setupItemRenderState();
                     if (this.blockCount.isToggled() && var25.GULE() > 1) {
                        String var27 = String.valueOf(var25.GULE());
                        var31.drawString(var27, var26 + 17 - var31.getStringWidth(var27), var17 + 9, -1, true);
                        this.setupItemRenderState();
                     }
                  }
               } finally {
                  RenderUtils.restoreLightmapState(var23);
                  GlStateManager.popMatrix();
                  this.restoreItemRenderState();
               }
            }
         }
      }
   }

   private void drawEntryIcon(BedPlateEntry var1, int var2, int var3) {
      if (var1 != null) {
         if (var1.hasItem()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderUtils.renderItemStackWithClear(var1.getItemStack(), var2, var3, false);
            RenderUtils.resetLightmapState();
         } else {
            if (var1.hasSprite()) {
               this.leru(var1.getSprite(), var2, var3);
            }
         }
      }
   }

   private void leru(TextureAtlasSprite var1, int var2, int var3) {
      if (var1 != null) {
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         GlStateManager.enableTexture2D();
         GlStateManager.enableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         Tessellator var4 = Tessellator.getInstance();
         WorldRenderer var5 = var4.getWorldRenderer();
         var5.begin(7, DefaultVertexFormats.POSITION_TEX);
         var5.pos(var2, var3 + 16, 0.0).tex(var1.getMinU(), var1.getMaxV()).endVertex();
         var5.pos(var2 + 16, var3 + 16, 0.0).tex(var1.getMaxU(), var1.getMaxV()).endVertex();
         var5.pos(var2 + 16, var3, 0.0).tex(var1.getMaxU(), var1.getMinV()).endVertex();
         var5.pos(var2, var3, 0.0).tex(var1.getMinU(), var1.getMinV()).endVertex();
         var4.draw();
      }
   }

   private void BtLcv(int var1, int var2, int var3, int var4, boolean var5) {
      float var6 = (float)Math.max(0.0, Math.min(1.0, this.opacity.getInput() / 100.0));
      float var7 = Math.min((float)this.rounding.getInput(), Math.min((var3 - var1) / 2.0F, (var4 - var2) / 2.0F));
      this.PXxSlx(var1, var2, var3, var4, var7, ClientUtils.YVVZ(0, Math.round(var6 * 255.0F)));
      if (var5 && this.outlineExposedLayers.isToggled()) {
         int var8 = this.exposedColor.getArgb();
         int var9 = Math.max(90, var8 >> 24 & 0xFF);
         this.drawStenciledOutline(var1, var2, var3, var4, var7, ClientUtils.YVVZ(var8, var9));
      }

      GlStateManager.enableTexture2D();
   }

   private void drawStenciledOutline(int var1, int var2, int var3, int var4, float var5, int var6) {
      float var7 = 1.25F;

      try {
         GL11.glClear(1024);
         GL11.glEnable(2960);
         GL11.glStencilMask(255);
         GL11.glColorMask(false, false, false, false);
         GL11.glDepthMask(false);
         GL11.glStencilFunc(519, 1, 255);
         GL11.glStencilOp(7681, 7681, 7681);
         this.PXxSlx(var1, var2, var3, var4, var5, -1);
         GL11.glColorMask(true, true, true, true);
         GL11.glStencilFunc(517, 1, 255);
         GL11.glStencilOp(7680, 7680, 7680);
         this.PXxSlx(var1 - var7, var2 - var7, var3 + var7, var4 + var7, var5 + var7, var6);
      } finally {
         GL11.glColorMask(true, true, true, true);
         GL11.glStencilMask(255);
         GL11.glDisable(2960);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void PXxSlx(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!(var3 <= var1) && !(var4 <= var2)) {
         float var7 = Math.max(0.0F, Math.min(var5, Math.min((var3 - var1) / 2.0F, (var4 - var2) / 2.0F)));
         GlStateManager.disableTexture2D();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glDisable(2884);
         GL11.glColor4f((var6 >> 16 & 0xFF) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F, (var6 >> 24 & 0xFF) / 255.0F);
         GL11.glBegin(9);
         this.emitArcVertices(var3 - var7, var4 - var7, var7, 0, 90);
         this.emitArcVertices(var1 + var7, var4 - var7, var7, 90, 180);
         this.emitArcVertices(var1 + var7, var2 + var7, var7, 180, 270);
         this.emitArcVertices(var3 - var7, var2 + var7, var7, 270, 360);
         GL11.glEnd();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void emitArcVertices(float var1, float var2, float var3, int var4, int var5) {
      if (var3 <= 0.0F) {
         GL11.glVertex2f(var1, var2);
      } else {
         for (int var6 = var4; var6 <= var5; var6 += 6) {
            double var7 = Math.toRadians(var6);
            GL11.glVertex2f(var1 + (float)Math.cos(var7) * var3, var2 + (float)Math.sin(var7) * var3);
         }
      }
   }

   private void setupItemRenderState() {
      RenderUtils.resetLightmapState();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.disableCull();
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glTexEnvi(8960, 8704, 8448);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void restoreItemRenderState() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableDepth();
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.disableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glTexEnvi(8960, 8704, 8448);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private float WXeb() {
      return 0.5625F;
   }

   private float getBaseScale() {
      return (float)this.scale.getInput() * 0.02F;
   }

   private float computeAutoScale(float var1) {
      float var2 = this.getBaseScale();
      float var3 = Math.max(1.0F, var1);
      float var4 = var2 * (var3 / 8.0F);
      return Math.max(var2, var4);
   }

   private String getBlockCountFontName() {
      return this.blockCountFont == null ? FontManager.getDefaultHudFontName() : this.blockCountFont.getResolvedFontName();
   }

   private IFont getBlockCountFont() {
      return FontManager.getNametagRenderer(this.getBlockCountFontName());
   }
}
