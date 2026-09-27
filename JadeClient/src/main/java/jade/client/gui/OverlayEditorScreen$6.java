// Jade recovery: original class: jade.deps.eLz.OaDR5Ryor$6
package jade.client.gui;

public final class OverlayEditorScreen$6 {
   private final int gnf;
   private final int RAb;
   private final int width;
   private final int fVwrVd;

   OverlayEditorScreen$6(int var1, int var2, int var3, int var4) {
      this.gnf = var1;
      this.RAb = var2;
      this.width = var3;
      this.fVwrVd = var4;
   }

   private static OverlayEditorScreen$6 LWuT() {
      return new OverlayEditorScreen$6(-1000, -1000, 0, 0);
   }

   private boolean containsPoint(int var1, int var2) {
      return var1 >= this.gnf && var1 <= this.gnf + this.width && var2 >= this.RAb && var2 <= this.RAb + this.fVwrVd;
   }

   public static OverlayEditorScreen$6 createEmpty() {
      return LWuT();
   }

   public static int getX(OverlayEditorScreen$6 var0) {
      return var0.gnf;
   }

   public static int getY(OverlayEditorScreen$6 var0) {
      return var0.RAb;
   }

   public static int getWidth(OverlayEditorScreen$6 var0) {
      return var0.width;
   }

   public static int getHeight(OverlayEditorScreen$6 var0) {
      return var0.fVwrVd;
   }

   public static boolean isInside(OverlayEditorScreen$6 var0, int var1, int var2) {
      return var0.containsPoint(var1, var2);
   }
}
