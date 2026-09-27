// Jade recovery: original class: jade.deps.eLz.LiFl4fl
package jade.client.common;

import java.util.Locale;

public final class ToolNames {
   private static final String[] AXE_TIERS = new String[]{"wooden", "stone", "iron", "diamond"};
   private static final String[] IsbZ = new String[]{"wooden", "iron", "gold", "golden", "diamond"};

   private ToolNames() {
   }

   public static boolean matchesToolName(String var0, String var1) {
      String var2 = QuickBuyLayout.normalizeItemName(var1);
      int var3 = var2.indexOf("_(");
      if (var3 >= 0) {
         var2 = var2.substring(0, var3);
      }

      String var4 = normalizeItemName(var2);
      String var5 = normalizeItemName(var0);
      if (!var4.isEmpty() && !var5.isEmpty()) {
         if (var5.equals(var4) || var5.startsWith(var4 + "_") || var5.endsWith("_" + var4) || var5.contains("_" + var4 + "_")) {
            return true;
         } else if ("wooden_axe".equals(var4) && matchesTieredToolName(var5, "axe", AXE_TIERS)) {
            return true;
         } else if ("wooden_pickaxe".equals(var4) && matchesTieredToolName(var5, "pickaxe", IsbZ)) {
            return true;
         } else {
            if (var4.endsWith("_boots")) {
               String var6 = var4.substring(0, var4.length() - "_boots".length());
               if (matchesNameVariant(var5, var6 + "_armor")) {
                  return true;
               }
            }

            if (var4.endsWith("_potion")) {
               String var7 = var4.substring(0, var4.length() - "_potion".length());
               if (var5.equals(var7) || var5.startsWith(var7 + "_")) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean matchesTieredToolName(String var0, String var1, String[] var2) {
      for (String var6 : var2) {
         String var7 = var6 + "_" + var1;
         if (var0.equals(var7) || var0.startsWith(var7 + "_")) {
            return true;
         }
      }

      return false;
   }

   private static boolean matchesNameVariant(String var0, String var1) {
      return var0.equals(var1) || var0.startsWith(var1 + "_") || var0.endsWith("_" + var1) || var0.contains("_" + var1 + "_");
   }

   private static String normalizeItemName(String var0) {
      return var0 == null ? "" : var0.toLowerCase(Locale.ROOT).replaceAll("\\u00a7.", "").replaceAll("[^a-z0-9]+", "_").replaceAll("^_+|_+$", "");
   }
}
