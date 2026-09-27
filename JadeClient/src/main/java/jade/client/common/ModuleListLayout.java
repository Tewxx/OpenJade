// Jade recovery: original class: jade.deps.eLz.f6gWsW2B
package jade.client.common;

import java.util.List;

public final class ModuleListLayout {
   private ModuleListLayout() {
   }

   public static void positionModules(List<ModuleComponent> var0, float var1) {
      float var2 = var1 + 3.0F;

      for (ModuleComponent var4 : var0) {
         var4.setY(var2);
         var2 += var4.getHeight();
      }
   }

   public static float OvpqA(List<ModuleComponent> var0, float var1, float var2) {
      float var3 = var1 + var2 + 4.0F;

      for (ModuleComponent var5 : var0) {
         var3 += var5.getHeight();
      }

      return var3;
   }

   public static TooltipLayout$1 measureModules(List<ModuleComponent> var0, boolean var1, boolean var2, float var3, float var4, float var5, boolean var6) {
      boolean var7 = !var0.isEmpty() && (var1 || var2);
      if (!var7) {
         return TooltipLayout.FxGeuTn(false, var3, var4, var5, new float[0], new float[0]);
      } else {
         float[] var8 = new float[var0.size()];
         float[] var9 = new float[var0.size()];
         float var10 = var4 + 3.0F;

         for (int var11 = 0; var11 < var0.size(); var11++) {
            ModuleComponent var12 = (ModuleComponent)var0.get(var11);
            if (var6) {
               var12.setY(var10);
            }

            float var13 = var12.getHeight();
            var10 += var13;
            var8[var11] = var13;
            var9[var11] = var12.getExpandedContentHeight();
         }

         return TooltipLayout.FxGeuTn(true, var3, var4, var5, var8, var9);
      }
   }
}
