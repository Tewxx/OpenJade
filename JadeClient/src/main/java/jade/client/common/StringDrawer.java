// Jade recovery: original class: jade.deps.eLz.u7MPvFax
package jade.client.common;

public final class StringDrawer {
   private StringDrawer() {
   }

   public static int plain(TextRenderer var0, String var1, float var2, float var3, int var4) {
      return var0.drawString(var1, var2, var3, var4, false);
   }

   public static int shadow(TextRenderer var0, String var1, float var2, float var3, int var4) {
      return var0.drawString(var1, var2, var3, var4, true);
   }
}
