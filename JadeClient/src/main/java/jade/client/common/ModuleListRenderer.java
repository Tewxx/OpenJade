// Jade recovery: original class: jade.deps.eLz.VpJhzw
package jade.client.common;

import jade.client.gui.ClickGui;
import jade.client.gui.Component;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class ModuleListRenderer {
   private ModuleListRenderer() {
   }

   public static void eZiz(List<Component> var0, ComponentLayout var1, CategoryComponent var2, ScissorStack var3, FloatTransition var4, float var5, boolean var6) {
      boolean var7 = var4.isAnimating();
      if (var7) {
         ScaledResolution var8 = new ScaledResolution(Minecraft.getMinecraft());
         int var9 = var8.getScaleFactor();
         double var10 = ClickGui.getActiveRenderScale();
         float var12 = var2.WHmq() - var2.getPanelY();
         int var13 = (int)Math.floor((var2.EWZxNc() - 2.0F) * var10 * var9);
         int var14 = (int)Math.floor((var8.getScaledHeight() - (var2.getPanelY() + var5 + var4.getValue() + var12) * var10) * var9);
         int var15 = (int)Math.ceil((var2.getPanelWidth() + 4.0F) * var10 * var9);
         int var16 = (int)Math.ceil(var4.getValue() * var10 * var9);
         var3.qweq(var13, var14, var15, var16);
      }

      if (var6 || var4.isAnimating()) {
         drawComponentList(var0, var1, var2, var3);
      }

      if (var7) {
         var3.popClip();
      }
   }

   public static void drawComponentList(List<Component> var0, ComponentLayout var1, CategoryComponent var2, ScissorStack var3) {
      GroupedListWalker.forEachGroup(var0, ModuleListRenderer::ySgoB56, ModuleListRenderer::isGroupFilterComponent, var1::uiyP, ModuleListRenderer::ZEYMTB);
      GroupedListWalker.forEachGroup(var0, ModuleListRenderer::isVisibleComponent, ModuleListRenderer::isGroupComponent, var1::uiyP, (recoveredArg0, recoveredArg1) -> ModuleListRenderer.drawGroupClip(var2, var1, var3, (jade.client.gui.Component) recoveredArg0, (java.util.List) recoveredArg1));
   }

   private static void drawGroupClip(CategoryComponent var0, ComponentLayout var1, ScissorStack var2, Component var3, List var4) {
      if (var3 instanceof GroupComponent) {
         GroupComponent var5 = (GroupComponent)var3;
         float var6 = var5.YVkqQ();
         float var7 = var0.WHmq() + var5.getWidth() + var1.fBnyl(var5);
         float var8 = 0.0F;

         for (Component var10 : (java.lang.Iterable<Component>) (java.lang.Iterable<?>) (var4)) {
            if (var10.ejbAn()) {
               var8 += var1.fBnyl(var10);
            }
         }

         if (var6 > 0.0F && var8 > 0.0F) {
            ScaledResolution var21 = new ScaledResolution(Minecraft.getMinecraft());
            int var22 = var21.getScaleFactor();
            double var11 = ClickGui.getActiveRenderScale();
            float var13 = var0.EWZxNc();
            float var14 = var0.getPanelWidth() + 4.0F;
            int var15 = (int)Math.floor(var13 * var11 * var22);
            int var16 = (int)Math.ceil((var13 + var14) * var11 * var22);
            int var17 = (int)Math.floor((var21.getScaledHeight() - (var7 + var8 * var6) * var11) * var22);
            int var18 = (int)Math.ceil((var21.getScaledHeight() - var7 * var11) * var22);
            var2.qweq(var15, var17, Math.max(0, var16 - var15), Math.max(0, var18 - var17));

            for (Component var20 : (java.lang.Iterable<Component>) (java.lang.Iterable<?>) (var4)) {
               if (var20.ejbAn() && var1.uiyP(var20) == var5) {
                  var20.renderComponent();
               }
            }

            var2.popClip();
         }
      }
   }

   private static boolean isGroupComponent(Component var0) {
      return var0 instanceof GroupComponent;
   }

   private static boolean isVisibleComponent(Object var0) {
      return ((Component)var0).ejbAn();
   }

   private static void ZEYMTB(Component var0, List var1) {
      var0.renderComponent();
   }

   private static boolean isGroupFilterComponent(Component var0) {
      return var0 instanceof GroupComponent;
   }

   private static boolean ySgoB56(Object var0) {
      return ((Component)var0).ejbAn();
   }
}
