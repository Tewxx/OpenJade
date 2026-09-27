// Jade recovery: original class: jade.deps.eLz.tFnEjgI
package jade.client.common;

public final class HoverHelper {
   private HoverHelper() {
   }

   public static boolean isHovering(int var0, int var1, float var2, float var3, float var4, float var5) {
      return HoverUtils.isPointInPaddedRect(var0, var1, var2, var3, var4, var5);
   }

   public static boolean isHoveringExpanded(int var0, int var1, float var2, float var3, float var4, float var5, float var6) {
      return HoverUtils.isPointInTallPaddedRect(var0, var1, var2, var3, var4, var5, var6);
   }

   public static boolean isInside(int var0, int var1, float var2, float var3, float var4, float var5) {
      return HoverUtils.isPointInRect(var0, var1, var2, var3, var4, var5);
   }

   public static boolean isInsideExpanded(int var0, int var1, float var2, float var3, float var4, float var5) {
      return HoverUtils.isPointInWideRect(var0, var1, var2, var3, var4, var5);
   }
}
