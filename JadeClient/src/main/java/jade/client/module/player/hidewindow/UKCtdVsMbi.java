// Jade recovery: original class: jade.deps.eLz.UKCtdVsMbi
package jade.client.module.player.hidewindow;

import jade.client.common.RenderUtils;
import net.minecraft.util.ResourceLocation;

public final class UKCtdVsMbi {
   private static final String TV_OFF_TEXTURE = "/assets/jade/textures/gui/tv_off.png";
   private static final int Mrh = 16;

   private UKCtdVsMbi() {
   }

   public static int ODZybV(FloatingWindowPosition var0, double var1, int var3) {
      ResourceLocation var4 = RenderUtils.getIconTexture("/assets/jade/textures/gui/tv_off.png");
      int var5 = SquareBounds.Yvhab2(16, var1);
      if (var4 == null) {
         return var5;
      } else {
         float var6 = var0.getPixelX() - var5 * 0.5F;
         float var7 = var0.getPixelY() - var5 * 0.5F;
         RenderUtils.drawIconTexture(var4, var6, var7, var5, 0xFF000000 | var3);
         return var5;
      }
   }

   public static int JqzE(double var0) {
      return SquareBounds.Yvhab2(16, var0);
   }
}
