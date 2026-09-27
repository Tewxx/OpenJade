// Jade recovery: original class: jade.deps.eLz.SqO4CW$1
package jade.client.module.render;

public final class ItemESP$1 {
   private final int Hdyvm;
   private final int uiCg;

   ItemESP$1(int var1, int var2) {
      this.Hdyvm = var1;
      this.uiCg = var2;
   }

   private static ItemESP$1 createOpaque(int var0, int var1) {
      return new ItemESP$1(0xFF000000 | var0 & 16777215, 0xFF000000 | var1 & 16777215);
   }

   public static int PinDj(ItemESP$1 var0) {
      return var0.Hdyvm;
   }

   public static int getSecondaryColor(ItemESP$1 var0) {
      return var0.uiCg;
   }

   public static ItemESP$1 ofOpaqueRgb(int var0, int var1) {
      return createOpaque(var0, var1);
   }
}
