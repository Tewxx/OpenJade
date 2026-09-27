// Jade recovery: original class: jade.deps.eLz.BwH5QuXb
package jade.client.common;

public final class TooltipLayout {
   private TooltipLayout() {
   }

   public static TooltipLayout$1 FxGeuTn(boolean var0, float var1, float var2, float var3, float[] var4, float[] var5) {
      if (var0 && var4.length != 0) {
         float var6 = var3 * 0.9F - var2 - 4.0F;
         float var7 = 0.0F;

         for (float var11 : var4) {
            if (var7 >= var6) {
               break;
            }

            var7 += Math.min(var11, var6 - var7);
         }

         float var13 = 0.0F;

         for (float var12 : var5) {
            var13 += var12;
         }

         float var15 = Math.min(var6, var13);
         float var17 = Math.max(0.0F, var13 - var15);
         float var19 = var1 + var3 * 0.9F;
         float var20 = var1 + var2 + var7 + 4.0F;
         return new TooltipLayout$1(Math.max(0.0F, var7), var17 == 0.0F ? var1 : var1 - var17, Math.min(var20, var19));
      } else {
         return new TooltipLayout$1(0.0F, var1, var1 + var2 + 4.0F);
      }
   }

   public static float clamp(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }
}
