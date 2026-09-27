// Jade recovery: original class: jade.deps.eLz.qjxe9ZC1E
package jade.client.gui;

import jade.client.common.QuickBuyLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class QuickBuyLayoutComposer {
   private QuickBuyLayoutComposer() {
   }

   public static List<String> findMissingItems(List<String> var0, List<String> var1) {
      return computeDifference(var0, var1);
   }

   public static List<String> findExtraItems(List<String> var0, List<String> var1) {
      return computeDifference(var1, var0);
   }

   public static List<String> composeLayoutWithReplacements(List<String> var0, List<String> var1, Map<String, String> var2) {
      if (QuickBuyLayout.isValidLayout(var0) && QuickBuyLayout.isValidLayout(var1)) {
         List var3 = findMissingItems(var0, var1);
         List var4 = findExtraItems(var0, var1);
         if (var3.size() != var4.size()) {
            throw new IllegalArgumentException("Quick Buy layout differences are unbalanced");
         } else if (var2 != null && var2.size() == var3.size() && var2.keySet().equals(new LinkedHashSet(var3))) {
            HashSet var5 = new HashSet(var4);
            HashSet var6 = new HashSet();
            ArrayList var7 = new ArrayList(var1);

            for (String var9 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var3)) {
               String var10 = QuickBuyLayout.normalizeItemName((String)var2.get(var9));
               if (!var5.contains(var10) || !var6.add(var10)) {
                  throw new IllegalArgumentException("Replacement items must be unique larp-only items");
               }

               int var11 = var7.indexOf(var10);
               if (var11 < 0) {
                  throw new IllegalArgumentException("Replacement item is not present in the larp layout");
               }

               var7.set(var11, var9);
            }

            if (!QuickBuyLayout.ywZww(var0, var7)) {
               throw new IllegalArgumentException("Composed layout does not preserve the desired item set");
            } else {
               return Collections.unmodifiableList(var7);
            }
         } else {
            throw new IllegalArgumentException("Every missing item must have one replacement");
         }
      } else {
         throw new IllegalArgumentException("Both Quick Buy layouts must contain 21 unique items");
      }
   }

   private static List<String> computeDifference(List<String> var0, List<String> var1) {
      if (QuickBuyLayout.isValidLayout(var0) && QuickBuyLayout.isValidLayout(var1)) {
         HashSet var2 = new HashSet(var1);
         ArrayList var3 = new ArrayList();

         for (String var5 : var0) {
            if (!var2.contains(var5)) {
               var3.add(var5);
            }
         }

         return Collections.unmodifiableList(var3);
      } else {
         return Collections.emptyList();
      }
   }
}
