// Jade recovery: original class: jade.deps.eLz.FVLujVCsQb
package jade.client.common;

public final class FVLujVCsQb {
   public final String LZy;
   public final int textColor;

   private FVLujVCsQb(String var1, int var2) {
      this.LZy = var1;
      this.textColor = var2;
   }

   public static FVLujVCsQb parseColorCode(String var0) {
      if (var0.contains("&a")) {
         return new FVLujVCsQb(var0.replace("&a", ""), -16711936);
      } else if (var0.contains("&c")) {
         return new FVLujVCsQb(var0.replace("&c", ""), -65536);
      } else {
         return var0.contains("&e") ? new FVLujVCsQb(var0.replace("&e", ""), -256) : new FVLujVCsQb(var0, -1);
      }
   }
}
