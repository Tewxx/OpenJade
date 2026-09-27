// Jade recovery: original class: jade.deps.eLz.bJRJJU9NnV
package jade.client.common;

import org.lwjgl.opengl.GL11;

public final class CategoryPanelRenderer {
   private CategoryPanelRenderer() {
   }

   public static void renderCategoryPanel(CategoryComponent var0, float var1, String var2, IFont var3, CategoryIcons var4) {
      GL11.glPushMatrix();
      RenderUtils.drawRoundedRectWithOutline(var0.panelX - 2.0F, var0.TFmJ1, var0.panelX + var0.EHA + 2.0F, var1, 1.0F, -1106309361, -13487566, -13487566);
      RenderUtils.jxyoE(var0.panelX - 2.0F, var0.TFmJ1, var0.panelX + var0.EHA + 2.0F, var0.TFmJ1 + var0.headerHeight + 2.0F, 1.0F, -936892376);
      var4.renderIcon(var0.category, (int)(var0.panelX + 1.0F), (int)(var0.TFmJ1 + 4.0F), var0.expanded || var0.hovered);
      var3.drawString(var2, var0.panelX + 12.0F, var0.TFmJ1 + 4.0F, -2302756, false);
      float var5 = var0.TFmJ1 + var0.headerHeight + 3.0F;
      float var6 = Math.max(0.0F, var1 - 2.0F - var5);
      if (var0.expanded || var0.animation != null) {
         GL11.glEnable(3089);
         RenderUtils.applyScissorRect(0.0, var5, var0.panelX + var0.EHA + 4.0F, var6);
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, var0.animatedPanelY - var0.TFmJ1, 0.0F);

         for (ModuleComponent var8 : var0.moduleComponents) {
            var8.renderComponent();
         }

         GL11.glPopMatrix();
         GL11.glDisable(3089);
      }

      GL11.glPopMatrix();
   }
}
