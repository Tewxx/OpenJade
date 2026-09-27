// Jade recovery: original class: jade.deps.eLz.cWIpz1kd5v
package jade.client.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class ItemIdMatcher {
   private final Set<String> exactIds;
   private final Set<String> wildcardPrefixes;

   private ItemIdMatcher(Set<String> var1, Set<String> var2) {
      this.exactIds = var1;
      this.wildcardPrefixes = var2;
   }

   public static ItemIdMatcher EYgroY8(Collection<String> var0) {
      HashSet var1 = new HashSet();
      HashSet var2 = new HashSet();

      for (String var4 : var0) {
         if (var4.endsWith(":*")) {
            var2.add(var4.substring(0, var4.length() - 2));
         } else {
            var1.add(var4);
         }
      }

      return new ItemIdMatcher(var1, var2);
   }

   public boolean matches(String var1, int var2) {
      return this.wildcardPrefixes.contains(var1) ? true : var2 != 0 && this.exactIds.contains(var1 + ":" + var2) || this.exactIds.contains(var1);
   }
}
