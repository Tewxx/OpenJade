// Jade recovery: original class: jade.deps.eLz.XgtRM9$1
package jade.client.module.shared;

import jade.client.common.TrueTypeFont;

public final class FormattedTextRenderer$1 {
   private final TrueTypeFont trueTypeFont;
   private final String text;

   FormattedTextRenderer$1(TrueTypeFont var1, String var2) {
      this.trueTypeFont = var1;
      this.text = var2;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof FormattedTextRenderer$1)) {
         return false;
      } else {
         FormattedTextRenderer$1 var2 = (FormattedTextRenderer$1)var1;
         return this.trueTypeFont == var2.trueTypeFont && this.text.equals(var2.text);
      }
   }

   @Override
   public int hashCode() {
      return 31 * System.identityHashCode(this.trueTypeFont) + this.text.hashCode();
   }
}
