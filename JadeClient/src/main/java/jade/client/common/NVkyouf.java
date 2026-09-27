// Jade recovery: original class: jade.deps.eLz.NVkyouf
package jade.client.common;

public final class NVkyouf {
   private NVkyouf() {
   }

   public static NVkyouf$1 computeHoverState(
      int var0,
      int var1,
      boolean var2,
      boolean var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12
   ) {
      float var13 = var6;
      float var14 = var7;
      if (var3) {
         var13 = HoverUtils.nrje(var0 - var4, var11, var8);
         var14 = HoverUtils.clampVerticalPosition(var1 - var5, var12, var9, true);
      }

      boolean var15 = var2 && HoverUtils.isPointInTallPaddedRect(var0, var1, var13, var14, var8, var9, var10);
      boolean var16 = var2 && HoverUtils.isPointInPaddedRect(var0, var1, var13, var14, var8, var9);
      return new NVkyouf$1(var13, var14, var16, var15);
   }
}
