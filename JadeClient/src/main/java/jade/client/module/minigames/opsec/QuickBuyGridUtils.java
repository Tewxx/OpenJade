// Jade recovery: original class: jade.deps.eLz.SgTipLSG
package jade.client.module.minigames.opsec;

import jade.client.common.QuickBuyLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class QuickBuyGridUtils {
   private QuickBuyGridUtils() {
   }

   public static boolean BebK(String var0, boolean var1, String var2) {
      return var1 && var0 != null && var2 != null && var2.equalsIgnoreCase(var0.trim());
   }

   public static List<Integer> extractQuickBuyGridSlots(List<Boolean> var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         return var1;
      } else {
         for (int var2 = 0; var2 < var0.size(); var2++) {
            if (Boolean.TRUE.equals(var0.get(var2))) {
               var1.add(var2);
            }
         }

         if (var1.size() != 21) {
            return new ArrayList<>();
         } else {
            for (int var5 = 0; var5 < 3; var5++) {
               int var3 = (Integer)var1.get(var5 * 7);

               for (int var4 = 0; var4 < 7; var4++) {
                  if ((Integer)var1.get(var5 * 7 + var4) != var3 + var4) {
                     return new ArrayList<>();
                  }
               }

               if (var5 > 0 && var3 != (Integer)var1.get((var5 - 1) * 7) + 9) {
                  return new ArrayList<>();
               }
            }

            return var1;
         }
      }
   }

   public static List<Integer> matchGridSlotsForLayout(List<String> var0, List<String> var1) {
      ArrayList var2 = new ArrayList();
      if (var0 != null && QuickBuyLayout.isValidLayout(var1)) {
         List var3 = computeGridSlotIndices(var0.size());
         if (var3.isEmpty()) {
            return var2;
         } else {
            HashSet var4 = new HashSet(var1);
            HashSet var5 = new HashSet();

            for (Integer var7 : (java.lang.Iterable<Integer>) (java.lang.Iterable<?>) (var3)) {
               String var8 = QuickBuyLayout.normalizeItemName((String)var0.get(var7));
               if (var8.isEmpty() || !var5.add(var8)) {
                  return var2;
               }
            }

            return (List<Integer>)(var5.equals(var4) ? var3 : var2);
         }
      } else {
         return var2;
      }
   }

   public static List<Integer> computeGridSlotIndices(int var0) {
      ArrayList var1 = new ArrayList(21);
      if (var0 <= 43) {
         return var1;
      } else {
         for (int var2 = 0; var2 < 3; var2++) {
            for (int var3 = 0; var3 < 7; var3++) {
               var1.add(19 + var2 * 9 + var3);
            }
         }

         return var1;
      }
   }

   public static boolean isSlotItemMatchingName(List<String> var0, int var1, String var2) {
      return var0 != null && var1 >= 0 && var1 < var0.size() && QuickBuyLayout.normalizeItemName(var2).equals(QuickBuyLayout.normalizeItemName((String)var0.get(var1)));
   }
}
