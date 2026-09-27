// Jade recovery: original class: jade.deps.eLz.tsGdLG
package jade.client.common;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class ConditionalChatFormatter {
   private ConditionalChatFormatter() {
   }

   public static void emitFormattedMessage(
      String var0, boolean var1, boolean var2, Predicate<String> var3, Predicate<String> var4, UnaryOperator<String> var5, Consumer<String> var6
   ) {
      if (!var3.test(var0)) {
         boolean var7 = var1 && (var2 || var4.test(var0));
         var6.accept(var7 ? var5.apply(var0) : var0);
      }
   }
}
