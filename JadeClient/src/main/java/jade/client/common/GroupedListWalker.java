// Jade recovery: original class: jade.deps.eLz.Q83TcjY9p3
package jade.client.common;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class GroupedListWalker {
   private GroupedListWalker() {
   }

   public static <T> void forEachGroup(List<T> var0, Predicate<T> var1, Predicate<T> var2, Function<T, T> var3, BiConsumer<T, List<T>> var4) {
      int var5 = 0;

      while (var5 < var0.size()) {
         T var6 = var0.get(var5++);
         if (var1.test(var6)) {
            int var7 = var5;
            if (var2.test(var6)) {
               while (var5 < var0.size()) {
                  T var8 = var0.get(var5);
                  if (var1.test(var8) && var3.apply(var8) != var6) {
                     break;
                  }

                  var5++;
               }
            }

            var4.accept(var6, var0.subList(var7, var5));
         }
      }
   }
}
