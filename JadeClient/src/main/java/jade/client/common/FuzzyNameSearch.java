// Jade recovery: original class: jade.deps.eLz.it82fo
package jade.client.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public final class FuzzyNameSearch {
   public static final Comparator<PotionLookup$0> comparator = Comparator.comparing(FuzzyNameSearch::getDisplayName, String.CASE_INSENSITIVE_ORDER).thenComparing(FuzzyNameSearch::SwDrdyt);

   private FuzzyNameSearch() {
   }

   public static String stripNamespace(String var0) {
      if (var0 == null) {
         return "";
      } else {
         int var1 = var0.lastIndexOf(46) + 1;
         return var1 == var0.length() ? var0 : var0.substring(var1);
      }
   }

   public static List<PotionLookup$0> searchAndRank(List<PotionLookup$0> var0, String var1, Predicate<String> var2) {
      String var3 = var1 == null ? "" : var1.trim().toLowerCase(Locale.ROOT);
      HashMap<PotionLookup$0, Integer> var4 = new HashMap<>();
      ArrayList var5 = new ArrayList();

      for (PotionLookup$0 var7 : var0) {
         if (!var2.test(var7.registryName)) {
            int var8 = FmsBd(var7.displayName, var7.registryName, var3);
            if (var8 != 0) {
               var5.add(var7);
               var4.put(var7, var8);
            }
         }
      }

      var5.sort(Comparator.<PotionLookup$0>comparingInt(var4::get).reversed().thenComparing(comparator));
      return new ArrayList<>(var5.subList(0, Math.min(100, var5.size())));
   }

   public static int FmsBd(String var0, String var1, String var2) {
      if (var2.isEmpty()) {
         return 1;
      } else {
         String var3 = var0.toLowerCase(Locale.ROOT);
         String var4 = stripNamespace(var1).toLowerCase(Locale.ROOT);
         String[] var5 = new String[]{var3, var4};

         for (int var6 = 0; var6 < var5.length; var6++) {
            if (var5[var6].equals(var2)) {
               return 1000 - 100 * var6;
            }
         }

         for (int var10 = 0; var10 < var5.length; var10++) {
            if (var5[var10].startsWith(var2)) {
               return 800 - 100 * var10;
            }
         }

         for (String var9 : var3.split("\\s+")) {
            if (var9.startsWith(var2)) {
               return 600;
            }
         }

         if (var3.contains(var2)) {
            return 500;
         } else {
            return !var4.contains(var2) && !var1.toLowerCase(Locale.ROOT).contains(var2) ? 0 : 400;
         }
      }
   }

   private static String SwDrdyt(PotionLookup$0 var0) {
      return var0.registryName;
   }

   private static String getDisplayName(PotionLookup$0 var0) {
      return var0.displayName;
   }
}
