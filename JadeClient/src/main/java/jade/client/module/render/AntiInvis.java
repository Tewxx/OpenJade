// Jade recovery: module: Anti Invis (render); original class: jade.deps.eLz.NM06dZtehC
package jade.client.module.render;

import jade.client.Jade;
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
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.FormattedTextRenderer;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

@ModuleInfo
public class AntiInvis extends Module implements ExternalRenderableModule {
   private static final ResourceLocation resourceLocation = new ResourceLocation(
      "textures/gui/container/inventory.png"
   );
   private static final int yfZok = 18;
   private static final float MARKER_SCALE = 0.025F;

   public AntiInvis() {
      super("Anti Invis", Category.render, 0);
   }

   public boolean isForceVisibleTarget(EntityLivingBase var1) {
      return this.isEnabled() && var1 instanceof EntityPlayer && var1 != mc.thePlayer;
   }

   public boolean isHiddenPlayer(EntityPlayer var1) {
      return this.isForceVisibleTarget(var1) && var1.isInvisible() && !var1.isDead && var1.deathTime <= 0;
   }

   public boolean shouldHideVanillaNametag(EntityPlayer var1) {
      Nametags var2 = Jade.getModuleManager().getModule(Nametags.class);
      return var2 != null && var2.shouldHideVanillaNametag(var1);
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (mc.theWorld != null && mc.thePlayer != null && mc.getRenderManager() != null) {
         for (EntityPlayer var3 : mc.theWorld.playerEntities) {
            if (this.isHiddenPlayer(var3) && !this.shouldHideVanillaNametag(var3) && RenderUtils.isEntityInView(var3)) {
               this.renderHiddenMarker(var3, var1.YDn0);
            }
         }
      }
   }

   private void renderHiddenMarker(EntityPlayer var1, float var2) {
      RenderManager var3 = mc.getRenderManager();
      double var4 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2 - var3.viewerPosX;
      double var6 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2 - var3.viewerPosY;
      double var8 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2 - var3.viewerPosZ;
      float var10 = var1.height + (var1.isSneaking() ? 0.85F : 1.05F);
      if (this.VIuQ()) {
         ExternalRenderBuffer var18 = ExternalRenderer.getActiveRenderBuffer();
         ScreenProjector var12 = ExternalRenderer.getActiveScreenProjector();
         if (var18 != null && var12 != null && var12.projectPoint(var4, var6 + var10, var8)) {
            IFont var13 = FontManager.getHudRenderer(FontManager.getDefaultHudFontName(), 1.0F);
            float var14 = Math.max(8.0F, Math.min(18.0F, var12.getProjectedScale() * 0.32F));
            FormattedTextRenderer.drawMultiLineLabel(var18, var13, var12.projectedPoint[0], var12.projectedPoint[1], var14, 1.0F, 3.0F, 0.72F, -2650881, -1438967988, 7, "INVIS", "", "");
         }
      } else {
         GlStateManager.pushMatrix();
         RenderUtils$1 var11 = null;

         try {
            var11 = RenderUtils.uyB6();
            GlStateManager.translate((float)var4, (float)var6 + var10, (float)var8);
            GlStateManager.rotate(-var3.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(var3.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-0.025F, -0.025F, 0.025F);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            xiJ8(-9, -9);
         } finally {
            RenderUtils.restoreLightmapState(var11);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
         }
      }
   }

   public static void xiJ8(int var0, int var1) {
      int var2 = Potion.invisibility.getStatusIconIndex();
      mc.getTextureManager().bindTexture(resourceLocation);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.drawModalRectWithCustomSizedTexture(var0, var1, var2 % 8 * 18, 198 + var2 / 8 * 18, 18, 18, 256.0F, 256.0F);
   }
}
