// Jade recovery: original class: jade.deps.eLz.VCW0xTu
package jade.client.module.player.blockin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public final class CandidateSearch {
   private CandidateSearch() {
   }

   public static <T, K, R> R findWithNeighborExpansion(
      List<T> var0, List<T> var1, Predicate<T> var2, Function<List<T>, R> var3, Function<T, Iterable<T>> var4, Predicate<T> var5, Function<T, K> var6
   ) {
      if (var1 != null) {
         int var7 = 0;

         for (T var9 : var1) {
            if (var7 == 3) {
               break;
            }

            if (var2.test(var9)) {
               Object var10 = var3.apply(Collections.singletonList(var9));
               if (var10 != null) {
                  return (R)var10;
               }

               var7++;
            }
         }
      }

      Object var16 = var3.apply(var0);
      if (var16 != null) {
         return (R)var16;
      } else {
         ArrayList<T> var18 = new ArrayList<>(var0);
         HashSet var19 = new HashSet();

         for (T var11 : var18) {
            var19.add(var6.apply(var11));
         }

         for (int var21 = 0; var21 < 5 && !var18.isEmpty(); var21++) {
            ArrayList<T> var22 = new ArrayList<>();

            for (T var13 : var18) {
               for (T var15 : var4.apply(var13)) {
                  if (var5.test(var15) && var19.add(var6.apply(var15))) {
                     var22.add(var15);
                  }
               }
            }

            if (!var22.isEmpty()) {
               var16 = var3.apply(var22);
               if (var16 != null) {
                  return (R)var16;
               }
            }

            var18 = var22;
         }

         return null;
      }
   }
}
