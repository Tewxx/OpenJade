// Jade recovery: original class: jade.deps.eLz.QhD7Ycs
package jade.client.module.render.nametags;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.IntUnaryOperator;

public final class FormattingState {
   private final Set<Character> styles = new LinkedHashSet<>();
   private Integer color;

   public void accept(char var1, IntUnaryOperator var2) {
      char var3 = Character.toLowerCase(var1);
      if ("0123456789abcdef".indexOf(var3) >= 0) {
         this.color = 0xFF000000 | var2.applyAsInt(var3);
         this.styles.clear();
      } else if (var3 == 'r') {
         this.color = null;
         this.styles.clear();
      } else if (var3 >= 'k' && var3 <= 'o') {
         this.styles.add(var3);
      }
   }

   public Integer color() {
      return this.color;
   }

   public int advance(int var1) {
      return var1 > 0 && this.styles.contains(Character.valueOf((char)108)) ? var1 + 1 : var1;
   }

   public String decorate(char var1) {
      StringBuilder var2 = new StringBuilder(this.styles.size() * 2 + 1);

      for (char var4 : this.styles) {
         var2.append('§').append(var4);
      }

      return var2.append(var1).toString();
   }
}
