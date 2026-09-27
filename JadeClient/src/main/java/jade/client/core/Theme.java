// Jade recovery: original class: jade.deps.eLz.ZNOQ7RH
package jade.client.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Theme {
   private final String eknAk;
   private final String name;
   private final List<Integer> colors;
   private final boolean XUqI2;

   public Theme(String var1, String var2, List<Integer> var3, boolean var4) {
      this.eknAk = var1;
      this.name = var2;
      this.colors = Collections.unmodifiableList(new ArrayList<>(var3));
      this.XUqI2 = var4;
   }

   public String getId() {
      return this.eknAk;
   }

   public String getName() {
      return this.name;
   }

   public List<Integer> mKwci3() {
      return this.colors;
   }

   public boolean isCustom() {
      return this.XUqI2;
   }

   public int getPrimaryColor() {
      return this.colors.isEmpty() ? -15030151 : this.colors.get(0);
   }
}
