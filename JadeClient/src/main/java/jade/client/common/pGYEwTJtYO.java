// Jade recovery: original class: jade.deps.eLz.pGYEwTJtYO
package jade.client.common;

public final class pGYEwTJtYO {
   public static final String COLORS_AND_STYLES = "0123456789abcdefklmnor";

   private pGYEwTJtYO() {
   }

   public static boolean malformedPrefix(String var0, int var1) {
      if (!artifact(var0.charAt(var1))) {
         return false;
      } else {
         for (int var2 = var1 + 1; var2 < var0.length(); var2++) {
            char var3 = var0.charAt(var2);
            if (var3 == 167) {
               return true;
            }

            if (!artifact(var3)) {
               return false;
            }
         }

         return false;
      }
   }

   public static int opaqueAlpha(int var0) {
      int var1 = var0 >>> 24 & 0xFF;
      return var1 == 0 ? 255 : var1;
   }

   public static int withAlpha(int var0, int var1) {
      return var1 << 24 | var0 & 16777215;
   }

   public static int shadow(int var0) {
      int var1 = opaqueAlpha(var0);
      return var1 << 24 | (var0 >>> 16 & 0xFF) / 4 << 16 | (var0 >>> 8 & 0xFF) / 4 << 8 | (var0 & 0xFF) / 4;
   }

   public static int minecraftColor(int var0, int var1, boolean var2) {
      int var3 = (var0 >> 3 & 1) * 85;
      int var4 = (var0 >> 2 & 1) * 170 + var3;
      int var5 = (var0 >> 1 & 1) * 170 + var3;
      int var6 = (var0 & 1) * 170 + var3;
      if (var0 == 6) {
         var4 += 85;
      }

      if (var2) {
         var4 /= 4;
         var5 /= 4;
         var6 /= 4;
      }

      return var1 << 24 | var4 << 16 | var5 << 8 | var6;
   }

   private static boolean artifact(char var0) {
      return var0 == 194 || var0 == 195 || var0 == 130 || var0 == 8218;
   }
}
