// Jade recovery: original class: jade.deps.eLz.hmwqjO
package jade.client.setting;

import java.util.ArrayList;
import java.util.List;

public final class ItemPatternSet {
   private final List<String> patterns = new ArrayList<>();

   public List<String> getPatterns() {
      return this.patterns;
   }

   public void addPattern(String var1) {
      if (this.patterns.indexOf(var1) < 0) {
         this.patterns.add(var1);
      }
   }

   public boolean matchesPattern(String var1) {
      if (this.patterns.contains(var1)) {
         return true;
      } else {
         String var2 = this.getWildcardKey(var1);
         return var2 != null && this.patterns.contains(var2.concat(":*"));
      }
   }

   private String getWildcardKey(String var1) {
      if (var1 == null || var1.length() == 0) {
         return null;
      } else if (var1.endsWith(":*")) {
         return var1.substring(0, var1.length() - 2);
      } else {
         int var2 = var1.length();

         while (var2 > 0 && var1.charAt(var2 - 1) == ':') {
            var2--;
         }

         int var3 = var1.indexOf(58);
         if (var3 >= 0 && var3 < var2) {
            int var4 = var1.indexOf(58, var3 + 1);
            return var4 >= 0 && var4 < var2 ? var1.substring(0, var4) : var1;
         } else {
            return null;
         }
      }
   }
}
