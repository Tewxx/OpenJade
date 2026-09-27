// Jade recovery: original class: jade.deps.eLz.Y3g95cl
package jade.client.common;

import java.util.Set;
import java.util.function.Consumer;

public final class RelationListHelper {
   private RelationListHelper() {
   }

   public static boolean BJi26(Set<String> var0, String var1, boolean var2, String var3, Consumer<String> var4, Runnable var5) {
      boolean var6 = var2 ? var0.add(var1.toLowerCase()) : var0.remove(var1.toLowerCase());
      if (!var6) {
         return false;
      } else {
         String var7 = var2 ? "&aadded" : "&cremoved";
         var4.accept(var7 + " &7" + var3 + ": &f" + var1);
         var5.run();
         return true;
      }
   }
}
