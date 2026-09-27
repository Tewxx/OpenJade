// Jade recovery: original class: jade.deps.eLz.Mikd5YiF
package jade.client.module.render.freelook;

import net.minecraft.client.Minecraft;

public final class PerspectiveController {
   private PerspectiveController() {
   }

   public static void wxaroH(Minecraft var0, int var1) {
      int var2 = FreeLookMath.clampPerspective(var1);
      var0.gameSettings.thirdPersonView = var2;
      refreshEntityShader(var0, var2);
      if (var0.renderGlobal != null) {
         var0.renderGlobal.setDisplayListEntitiesDirty();
      }
   }

   private static void refreshEntityShader(Minecraft var0, int var1) {
      if (var0.entityRenderer != null) {
         if (var1 == 0) {
            var0.entityRenderer.loadEntityShader(var0.getRenderViewEntity());
         } else if (var1 == 1) {
            var0.entityRenderer.loadEntityShader(null);
         }
      }
   }
}
