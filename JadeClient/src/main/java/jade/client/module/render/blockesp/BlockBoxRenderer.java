// Jade recovery: original class: jade.deps.eLz.BDFYb0yO8
package jade.client.module.render.blockesp;

import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.ScreenProjector;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;

public final class BlockBoxRenderer {
   private BlockBoxRenderer() {
   }

   public static void QSHA(Minecraft var0, AxisAlignedBB var1, int var2, boolean var3, boolean var4) {
      ExternalRenderBuffer var5 = ExternalRenderer.getActiveRenderBuffer();
      ScreenProjector var6 = ExternalRenderer.getActiveScreenProjector();
      if (var5 != null && var6 != null && var1 != null) {
         BlockBoxRenderer$0 var7 = BlockBoxRenderer$0.createViewerRelative(var0, var1);
         if (var7.projectAndDraw(var6, var5, var2, 0.0F, false)) {
            if (var4) {
               var6.HIQRn(var5, clampAlpha(var2));
            }

            if (var3) {
               var7.projectAndDraw(var6, var5, var2, 2.0F, true);
            }
         }
      }
   }

   private static int clampAlpha(int var0) {
      int var1 = Math.min(90, var0 >>> 24);
      return var0 & 16777215 | var1 << 24;
   }
}
