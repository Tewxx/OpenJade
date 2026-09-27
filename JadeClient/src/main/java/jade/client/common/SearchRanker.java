// Jade recovery: original class: jade.deps.eLz.jlDxGndz
package jade.client.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public final class SearchRanker {
   private SearchRanker() {
   }

   public static <E> List<QVfdNy<E>> rankMatches(
      FuzzyNameMatcher var0, List<E> var1, Function<E, String> var2, Function<E, String> var3, Function<E, String> var4, Predicate<E> var5, Predicate<E> var6
   ) {
      ArrayList var7 = new ArrayList();

      for (E var9 : var1) {
         if (var5.test(var9)) {
            int var10 = var0.score((String)var2.apply(var9), (String)var4.apply(var9));
            if (var10 > 0 && var6.test(var9)) {
               var7.add(new QVfdNy<>(var9, var10));
            }
         }
      }

      var7.sort(
         Comparator.comparingInt(SearchRanker::PvV2).reversed().thenComparing((recoveredArg0) -> SearchRanker.applyPrimarySortKey(var2, (jade.client.common.QVfdNy) recoveredArg0), String.CASE_INSENSITIVE_ORDER).thenComparing((recoveredArg0) -> SearchRanker.applySecondarySortKey(var3, (jade.client.common.QVfdNy) recoveredArg0))
      );
      return var7;
   }

   public static <E> List<E> takeTopItems(List<QVfdNy<E>> var0) {
      ArrayList var1 = new ArrayList();

      for (QVfdNy<E> var3 : var0) {
         if (var1.size() == 100) {
            break;
         }

         var1.add(var3.UIYc);
      }

      return var1;
   }

   public static <E> List<SearchGroup<E>> groupMatches(List<QVfdNy<E>> var0, Function<E, String> var1, Function<String, List<E>> var2) {
      HashSet var3 = new HashSet();
      ArrayList var4 = new ArrayList();

      for (QVfdNy<E> var6 : var0) {
         String var7 = (String)var1.apply(var6.UIYc);
         if (var7 != null && var3.add(var7)) {
            List var8 = (List)var2.apply(var7);
            if (!var8.isEmpty()) {
               var4.add(new SearchGroup(var7, var8, var6.tdw));
               if (var4.size() == 100) {
                  break;
               }
            }
         }
      }

      return var4;
   }

   private static String applySecondarySortKey(Function var0, QVfdNy var1) {
      return (String)var0.apply(var1.UIYc);
   }

   private static String applyPrimarySortKey(Function var0, QVfdNy var1) {
      return (String)var0.apply(var1.UIYc);
   }

   private static int PvV2(QVfdNy var0) {
      return var0.tdw;
   }
}
