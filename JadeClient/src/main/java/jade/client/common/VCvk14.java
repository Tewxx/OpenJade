// Jade recovery: original class: jade.deps.eLz.VCvk14
package jade.client.common;

public final class VCvk14 {
   private VCvk14() {
   }

   public static boolean hasWildcardSuffix(String var0) {
      return var0 != null && var0.endsWith(":*");
   }

   public static String stripWildcardSuffix(String var0) {
      return hasWildcardSuffix(var0) ? var0.substring(0, var0.length() - 2) : normalizeBlockId(var0);
   }

   public static String normalizeBlockId(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String[] var1 = var0.split(":");
         if (var1.length < 2) {
            return null;
         } else {
            return var1.length == 2 ? var0 : var1[0] + ':' + var1[1];
         }
      } else {
         return null;
      }
   }

   public static int DPdat(String var0) {
      if (var0 == null) {
         return 0;
      } else {
         String[] var1 = var0.split(":");

         try {
            return var1.length > 2 ? Integer.parseInt(var1[2]) : 0;
         } catch (NumberFormatException var3) {
            return 0;
         }
      }
   }

   public static String getBlockPathName(String var0) {
      String var1 = stripWildcardSuffix(var0);
      return var1 == null ? var0 : var1.substring(var1.indexOf(58) + 1);
   }
}
